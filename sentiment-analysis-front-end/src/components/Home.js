import React, {Component} from 'react';

import Result from "./Result";
import logo from './index.png';
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

                        for(let i=0; i<all.length; i++){
                            if(i === all.length -1) {
                                let numbers = {
                                    "scoreGlobal": all[i][0],
                                    "emotionGlobal":all[i][1],
                                    "posPourcentage": all[i][2],
                                    "negPourcentage":all[i][3],
                                    "neutralPourcentage":all[i][4],
                                    "videoTitle":all[i][5],
                                }
                                this.state.numbers.push(numbers);
                            }else{
                                let comment= {
                                    "text": all[i][0],
                                    "tokens":all[i][1],
                                    "score":all[i][2],
                                    "emotion":all[i][3],
                                }
                                this.state.comments.push(comment);
                            }

                        }
                        // this needs some real re-construction
                        //
                        /*
                        * let comment = {
                                "text":,
                                "tokens";,
                                "score":,
                                "emotion":,
                                *
                            }
                        * */
                        console.log(all);
                        console.log("****");
                        console.log(all[0]);

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
                <div className="logo">
                    <img src={logo} className="main-logo" alt=""/>
                </div>
                <div
                    className="input-url-video container d-flex flex-column justify-content-center align-items-center align-content-center">


                    <div>
                        <form onSubmit={this.handleSubmit} className="url-input-form">
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
                                                Youtube Video URL
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