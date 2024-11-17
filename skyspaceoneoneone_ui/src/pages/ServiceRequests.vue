<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <serviceRequest-table
            v-if="serviceRequests && serviceRequests.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:serviceRequests="serviceRequests"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-service-requests="getAllServiceRequests"
             >

            </serviceRequest-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ServiceRequestTable from "@/components/ServiceRequestTable";
import ServiceRequestService from "../services/ServiceRequestService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ServiceRequestTable,
  },
  data() {
    return {
      serviceRequests: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllServiceRequests(sortBy='serviceRequestId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ServiceRequestService.getAllServiceRequests(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.serviceRequests.length) {
					this.serviceRequests = response.data.serviceRequests;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching serviceRequests:", error);
        }
        
      } catch (error) {
        console.error("Error fetching serviceRequest details:", error);
      }
    },
  },
  mounted() {
    this.getAllServiceRequests();
  },
  created() {
    this.$root.$on('searchQueryForServiceRequestsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllServiceRequests();
    })
  }
};
</script>
<style></style>
