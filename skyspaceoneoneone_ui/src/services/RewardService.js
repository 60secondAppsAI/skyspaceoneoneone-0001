import http from "../http-common"; 

class RewardService {
  getAllRewards(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/reward/rewards`, searchDTO);
  }

  get(rewardId) {
    return this.getRequest(`/reward/${rewardId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/reward?field=${matchData}`, null);
  }

  addReward(data) {
    return http.post("/reward/addReward", data);
  }

  update(data) {
  	return http.post("/reward/updateReward", data);
  }
  
  uploadImage(data,rewardId) {
  	return http.postForm("/reward/uploadImage/"+rewardId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new RewardService();
