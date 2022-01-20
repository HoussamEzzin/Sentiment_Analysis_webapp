import React, {Component} from 'react';

import Result from "./Result";
import logo from './index.jpg';
import './Home.css'
import './home.sass'

class Home extends Component{

    constructor(props) {
        super(props);

        this.state={
            value:'',
            submit:'',
            refresh: false,
            empty: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    handleChange(event){
        this.setState({
            value: event.target.value
        });
    }

    handleSubmit = (event) =>{
        event.preventDefault();
        if(this.state.refresh === false){
            this.setState({
                refresh:true
            });
            if(this.state.value === ''){
                this.setState({
                    empty: true,
                })
            }
        }else{
            this.setState({
                refresh:false
            });

        }



        this.setState({
            submit: this.state.value,
            value:'',

        });


    }


    render(){
        return(
            <div>
                <div className="input-url-video container d-flex flex-column justify-content-center align-items-center align-content-center">
                    <div>
                        <img src={logo} alt="logo" className={"logo"}/>
                    </div>

                    <div>
                        <form onSubmit={this.handleSubmit}>
                            <div className="d-flex flex-column align-content-center justify-content-center align-items-center">
                                {this.state.refresh ? (
                                    <div>

                                    </div>
                                ):
                                    <div className="d-flex flex-column">
                                        <label htmlFor="inp" className="inp">
                                            <input
                                                id="inp"
                                                placeholder="&nbsp;"
                                                size={70}
                                                value={this.state.value}
                                                onChange={this.handleChange} />
                                            <span className="label">
                                                Video URL
                                            </span>
                                            <span className="focus-bg">

                                        </span>
                                        </label>
                                        {this.state.empty ? (
                                            <span style={{color:"red",fontSize:"14px"}}>Insert a video URL please.</span>
                                        ):
                                            null

                                        }
                                    </div>

                                }

                                <div className="mt-3 ">
                                    {this.state.refresh ? (
                                        <button type='submit' onClick={this.handleSubmit}  className="btn btn-info refresh-button" >REFRESH</button>
                                    ):
                                        <button type='submit'  className="btn btn-primary analyze-button" >ANALYZE!</button>
                                    }

                                </div>
                            </div>


                        </form>
                    </div>


                </div>
                <div>
                    {this.state.submit ? (
                        <Result videoId={this.state.submit}/>
                    ): null}


                </div>
            </div>
        )
    }
}

export default Home;