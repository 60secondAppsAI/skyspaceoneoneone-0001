import http from "../http-common"; 

class ServiceRequestService {
  getAllServiceRequests(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/serviceRequest/serviceRequests`, searchDTO);
  }

  get(serviceRequestId) {
    return this.getRequest(`/serviceRequest/${serviceRequestId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/serviceRequest?field=${matchData}`, null);
  }

  addServiceRequest(data) {
    return http.post("/serviceRequest/addServiceRequest", data);
  }

  update(data) {
  	return http.post("/serviceRequest/updateServiceRequest", data);
  }
  
  uploadImage(data,serviceRequestId) {
  	return http.postForm("/serviceRequest/uploadImage/"+serviceRequestId, data);
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

export default new ServiceRequestService();
