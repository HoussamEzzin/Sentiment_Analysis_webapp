import React, {Component} from 'react';

import Result from "./Result";
import logo from './index.jpg';
import './Home.css'
import './home.sass'
import './home.scss'
import HomeService from "../services/HomeService";



class Home extends Component {

    constructor(props) {
        super(props);

        this.state = {
            value: '',
            submit: '',
            empty: false,
            valid: true,
            data : [],
            comments: [],
            numbers:[],
            loaded: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.callApi = this.callApi.bind(this);

    }

    handleChange(event) {
        this.setState({
            value: event.target.value
        });
    }

    callApi(url){
        if(url !== '' ){
            this.setState({
                data : [],
                comments: [],
                numbers:[],
            })
            if(this.state.loaded === true){
                this.setState({
                    loaded: false
                })
            }
            let videoId = url.replace("https://www.youtube.com/watch?v=","");
            HomeService.getResult(videoId)
                .then(res => {
                    if(res.status === 200){
                        this.setState({
                            data: res.data,
                        });
                        let all = this.state.data;
                        let i = 0;
                        while(true){
                            let comment = {
                                "text":all[i],
                                "emotion":all[++i]
                            }

                            this.state.comments.push(comment);
                            i++;
                            if(i>9){
                                break;
                            }

                        }


                        for(let i = 10 ;i<=12;i++){
                            this.state.numbers.push(all[i]);
                        }

                        this.setState({
                            loaded:true,
                        })

                        // console.log("DATA : "+current[0]);
                    }else{
                        console.log('problem')
                    }
                }).catch(error => console.log(error));
        }
        else{
            console.log("nothing yet");
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let validationPattern = /^(http(s)??\:\/\/)?(www\.)?((youtube\.com\/watch\?v=)|(youtu.be\/))([a-zA-Z0-9\-_])+$/g;


        if (this.state.value === '') {
            this.setState({
                empty: true,
                valid:true
            })
        } else {
            if (this.state.value.match(validationPattern) === null) {
                this.setState({
                    valid: false,
                    empty: false
                });
            } else {
                this.callApi(this.state.value);

                this.setState({
                    submit: this.state.value,
                    value: '',
                    valid: true,
                    empty: false,
                });

            }

        }




    }



    render() {
        return (
            <div>
                <div
                    className="input-url-video container d-flex flex-column justify-content-center align-items-center align-content-center">
                    <div>
                        <img src={logo} alt="logo" className={"logo"}/>
                    </div>

                    <div>
                        <form onSubmit={this.handleSubmit}>
                            <div
                                className="d-flex flex-column align-content-center justify-content-center align-items-center">

                                <div className="d-flex flex-column">
                                        <label htmlFor="inp" className="inp">
                                            <input
                                                id="inp"
                                                placeholder="&nbsp;"
                                                size={70}
                                                value={this.state.value}
                                                onChange={this.handleChange}/>
                                            <span className="label">
                                                Video URL
                                            </span>
                                            <span className="focus-bg">

                                        </span>
                                        </label>
                                        {this.state.empty ? (
                                                <span style={{
                                                    color: "red",
                                                    fontSize: "14px"
                                                }}>Insert a video URL please !</span>
                                            ) :
                                            null

                                        }
                                        {this.state.valid ? null :
                                            <span style={{color: "red", fontSize: "14px"}}>Invalid URL !</span>

                                        }
                                    </div>



                                <div className="mt-3 ">

                                    <button type='submit'
                                                    className="button analyze-button">Analyze !</button>


                                </div>
                            </div>


                        </form>
                    </div>


                </div>
                <div>

                    {this.state.submit !== '' && <Result comments={this.state.comments}
                                                         numbers ={this.state.numbers}
                                                         loaded ={this.state.loaded}
                                                    />}

                </div>
            </div>
        )
    }
}

export default Home;