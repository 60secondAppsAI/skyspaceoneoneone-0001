import http from "../http-common"; 

class CrewAssignmentService {
  getAllCrewAssignments(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/crewAssignment/crewAssignments`, searchDTO);
  }

  get(crewAssignmentId) {
    return this.getRequest(`/crewAssignment/${crewAssignmentId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/crewAssignment?field=${matchData}`, null);
  }

  addCrewAssignment(data) {
    return http.post("/crewAssignment/addCrewAssignment", data);
  }

  update(data) {
  	return http.post("/crewAssignment/updateCrewAssignment", data);
  }
  
  uploadImage(data,crewAssignmentId) {
  	return http.postForm("/crewAssignment/uploadImage/"+crewAssignmentId, data);
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

export default new CrewAssignmentService();
