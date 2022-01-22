import axios from 'axios';

const API_BASE_URL = "http://localhost:8082/";

class HomeService{



    getResult(videoId){

        return axios.get(API_BASE_URL,{params: { videoId: videoId }});
    }
}

export default new HomeService()