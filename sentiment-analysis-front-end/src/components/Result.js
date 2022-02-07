import React, {Component} from 'react';
import HomeService from "../services/HomeService";
import './Result.css';

import happy from "./happy.png";
import sad from "./sad.png";
import neutral from "./neutral.png";
import Loader from "./Loader";
import LoadingEffect from "react-loading-text";
import ThreeBounce from "better-react-spinkit/dist/ThreeBounce";


class Result extends Component{
//d
    constructor(props) {
        super(props);
        // this.state = {
        //
        // }
    }


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
                                                <td>{comment["text"]}</td>
                                                <td>{comment["score"]}</td>
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
                            <div>
                                <h2>Video Title : {this.props.numbers[0].videoTitle} </h2>
                            </div>
                            <div className="d-flex flex-column">
                                <div>
                                    <h3>Global Results</h3>
                                </div>
                                <div className="d-flex ">
                                    <div>
                                        <p>Global Score : {this.props.numbers[0].scoreGlobal}</p>

                                    </div>
                                    <div>
                                        <p>Video Sentiment : {this.props.numbers[0].emotionGlobal}</p>
                                    </div>
                                </div>

                            </div>
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
                                    "rgba(49,203,241,255)",

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