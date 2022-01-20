import React, {Component} from 'react';
import HomeService from "../services/HomeService";
import './Result.css';

import happy from "./happy.png";
import sad from "./sad.png";
import neutral from "./neutral.png";
import Loader from "./Loader";
import LoadingEffect from "react-loading-text";

class Result extends Component{

    constructor(props) {
        super(props);
        this.state = {
            data : [],
            comments: [],
            numbers:[],
            loaded: false,
        }
    }


    componentDidMount() {
        if(this.props.videoId !== ''){
            let videoId = this.props.videoId.replace("https://www.youtube.com/watch?v=","");
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
                            console.log("COMMENTS : "+comment.text);
                            console.log("COMMENTS : "+comment["text"]);
                            this.state.comments.push(comment);
                            i++;
                            if(i>9){
                                break;
                            }

                        }
                        console.log("COMMENTS : "+this.state.comments[0]);

                        for(let i = 10 ;i<=12;i++){
                            this.state.numbers.push(all[i]);
                        }

                        this.setState({
                            loaded: true
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


    render(){

        return(
            <div className="container">
                {this.state.loaded ? (
                    <div className="d-flex flex-row result-section">
                        <div className="comments-table">
                            <table className="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Comment</th>
                                    <th>Sentiment</th>
                                </tr>
                                </thead>

                                {/* process data in commponent */}
                                <tbody>
                                {
                                    this.state.comments.map(
                                        comment =>
                                            <tr>
                                                <td>{comment["text"]}</td>
                                                {comment.emotion === "Positive" &&
                                                    <td style={{color:"green"}}>{comment.emotion}</td>
                                                }
                                                {comment.emotion === "Negative" &&
                                                <td style={{color:"red"}}>{comment.emotion}</td>
                                                }
                                                {comment.emotion === "Neutral" &&
                                                <td style={{color:"blue"}}>{comment.emotion}</td>
                                                }


                                            </tr>
                                    )
                                }


                                </tbody>
                            </table>
                        </div>
                        <div className="d-flex flex-row justify-content-around align-items-center result-numbers">
                            <div>
                                <img src={happy} alt="happy" className="emoji"/>
                                <p className="result-percentage" style={{color:"green"}}>Positive {this.state.numbers[0]}%</p>

                            </div>
                            <div>
                                <img src={sad} alt="sad" className="emoji"/>
                                <p className="result-percentage" style={{color:"red"}}>Negative {this.state.numbers[1]}%</p>

                            </div>
                            <div>
                                <img src={neutral} alt="neutral" className="emoji"/>
                                <p className="result-percentage" style={{color:"blue"}}>Neutral : {this.state.numbers[2]}%</p>

                            </div>

                        </div>
                    </div>
                ):<div className="result-section d-flex justify-content-around align-items-center ">
                    <div>
                        <LoadingEffect
                            style={{
                                default: [
                                    26,
                                    7.5,
                                    "rgba( 71, 147, 227, 1)",
                                    2,
                                    1,
                                    "linear",
                                    // () => alert("Loading finished!")
                                ]
                            }}
                        >
                            <p className="loading-text">
                                Tokenization and lower case ... done
                                <br/>
                                Normalization ... done
                                <br/>
                                Removing step words ... done
                                <br/>
                                Lemmatizing ... done
                                <br/>
                                Calculating percentages ...


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