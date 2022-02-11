import React, {Component} from 'react';
import './Result.css';
import {  Popup } from 'semantic-ui-react'


import Loader from "./Loader";
import LoadingEffect from "react-loading-text";
import ThreeBounce from "better-react-spinkit/dist/ThreeBounce";


class Result extends Component{
//d



    // componentDidMount() {
    //     // let validationPattern = /https:\/\/www.youtube.com\/watch?v=/i;
    //     console.log(this.props.numbers);
    //     console.log("**")
    //     console.log(this.props.numbers.posPourcentage);
    //     console.log(this.props.numbers["posPourcentage"]);
    // }


    render(){

        return(
            <div className="container d-flex align-items-center justify-content-center align-content-center">
                {this.props.loaded ? (
                    <div className="d-flex  result-section ">
                        <div className="help">
                            <Popup className="dev-pop-up" content={<ul className="skills-list-pop">
                                <div className="help-pop-up">
                                    Hover on comments to see the tokens
                                </div>
                            </ul>} trigger={<i className="fa fa-question-circle fa-3x"/>} />

                        </div>
                        <div className="comments-table">
                            <table className="table table-bordered border border-dark  table-hover">
                                <thead>
                                <tr>
                                    <th className="arabic-comment">Comment</th>
                                    <th>Score</th>
                                    <th>Sentiment</th>
                                </tr>
                                </thead>

                                {/* process data in commponent */}
                                <tbody>
                                {
                                    this.props.comments.map(
                                        comment =>
                                            <tr>
                                                <Popup className="dev-pop-up" content={<ul className="skills-list-pop">
                                                    <div className="tokens-pop-up">
                                                        {comment["tokens"]}
                                                    </div>
                                                </ul>} trigger={<td>{comment["text"]}</td>} />



                                                {comment.score > 0 &&
                                                <td  style={{color:"green"}}>{comment["score"]}</td>
                                                }
                                                {comment.score <0 &&
                                                <td  style={{color:"red"}}>{comment["score"]}</td>
                                                }
                                                {comment.score === "0" &&
                                                <td  style={{color:"blue"}}>{comment["score"]}</td>
                                                }
                                                {comment.emotion === "Positive" &&
                                                    <td className="table-emotion" style={{color:"green"}}>{comment.emotion}</td>
                                                }
                                                {comment.emotion === "Negative" &&
                                                    <td className="table-emotion" style={{color:"red"}}>{comment.emotion}</td>
                                                }
                                                {comment.emotion === "Neutral" &&
                                                    <td className="table-emotion" style={{color:"blue"}}>{comment.emotion}</td>
                                                }


                                            </tr>
                                    )
                                }

                                </tbody>
                            </table>
                        </div>
                        <div className="global-results">
                            <table className="table table-bordered border border-dark  table-hover">
                                <thead>
                                    <tr>
                                        <th>Video Title</th>
                                        <th>{this.props.numbers[0].videoTitle}</th>

                                    </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Global Score</td>


                                    {this.props.numbers[0].scoreGlobal> 0 &&
                                    <th  style={{color:"green"}}>{this.props.numbers[0].scoreGlobal}</th>
                                    }
                                    {this.props.numbers[0].scoreGlobal <0 &&
                                    <th  style={{color:"red"}}>{this.props.numbers[0].scoreGlobal}</th>
                                    }
                                    {this.props.numbers[0].scoreGlobal === 0 &&
                                    <th  style={{color:"blue"}}>{this.props.numbers[0].scoreGlobal}</th>
                                    }


                                </tr>
                                <tr>
                                    <td>Global Emotion</td>

                                    {this.props.numbers[0].emotionGlobal === "Positive" &&
                                    <td className="table-emotion" style={{color:"green"}}>{this.props.numbers[0].emotionGlobal}</td>
                                    }
                                    {this.props.numbers[0].emotionGlobal === "Negative" &&
                                    <td className="table-emotion" style={{color:"red"}}>{this.props.numbers[0].emotionGlobal}</td>
                                    }
                                    {this.props.numbers[0].emotionGlobal === "Neutral" &&
                                    <td className="table-emotion" style={{color:"blue"}}>{this.props.numbers[0].emotionGlobal}</td>
                                    }
                                </tr>

                                </tbody>
                            </table>
                        </div>
                        <div className="result-numbers">
                            <div className="pourcentage-title">
                                Comments Percentages
                            </div>
                            <div className="d-flex  justify-content-around align-items-center ">
                                <div>
                                    {/*<img src={happy} alt="happy" className="emoji"/>*/}
                                    <i className="fas fa-smile-beam fa-4x"/>
                                    <p className="result-percentage" style={{color:"green"}}>Positive {this.props.numbers[0]["posPourcentage"]}%</p>

                                </div>
                                <div>
                                    <i className="fas fa-frown fa-4x"/>
                                    <p className="result-percentage" style={{color:"red"}}>Negative {this.props.numbers[0]["negPourcentage"]}%</p>

                                </div>
                                <div>
                                    <i className="fas fa-meh fa-4x"/>
                                    <p className="result-percentage" style={{color:"blue"}}>Neutral  {this.props.numbers[0]["neutralPourcentage"]}%</p>

                                </div>

                            </div>
                        </div>

                    </div>
                ):<div className="loading-section d-flex justify-content-evenly align-items-center ">
                    <div>
                        <LoadingEffect
                            style={{
                                default: [
                                    30,
                                    7,
                                    "white",

                                    2,
                                    1,
                                    "linear",
                                    // () => alert("Loading finished!")
                                ]
                            }}
                        >
                            <p className="loading-text">
                                Tokenization and lower case
                                <ThreeBounce className="bounce-three"  timingFunction='linear' gutter={20} color={"black"} />
                                <br/>
                                Normalization
                                <ThreeBounce className="bounce-three" timingFunction='linear' gutter={20} color={"black"}/>
                                <br/>
                                Removing step words
                                <ThreeBounce className="bounce-three" timingFunction='linear' gutter={20} color={"black"}/>
                                <br/>
                                Lemmatizing
                                <ThreeBounce className="bounce-three" timingFunction='linear' gutter={20} color={"black"}/>
                                <br/>
                                Calculating score
                                <ThreeBounce className="bounce-three" timingFunction='linear' gutter={20} color={"black"}/>
                                <br/>
                                Determining sentiments
                                <ThreeBounce className="bounce-three" timingFunction='linear' gutter={20} color={"black"}/>

                            </p>

                        </LoadingEffect>
                    </div>
                    <div style={{margin:60}}>
                        <Loader/>

                    </div>


                </div>

                }
            </div>


        )
    }
}

export default Result;