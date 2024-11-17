import http from "../http-common"; 

class ProgramService {
  getAllPrograms(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/program/programs`, searchDTO);
  }

  get(programId) {
    return this.getRequest(`/program/${programId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/program?field=${matchData}`, null);
  }

  addProgram(data) {
    return http.post("/program/addProgram", data);
  }

  update(data) {
  	return http.post("/program/updateProgram", data);
  }
  
  uploadImage(data,programId) {
  	return http.postForm("/program/uploadImage/"+programId, data);
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

export default new ProgramService();
