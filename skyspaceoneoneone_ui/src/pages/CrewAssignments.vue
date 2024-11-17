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
            <crewAssignment-table
            v-if="crewAssignments && crewAssignments.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:crewAssignments="crewAssignments"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-crew-assignments="getAllCrewAssignments"
             >

            </crewAssignment-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CrewAssignmentTable from "@/components/CrewAssignmentTable";
import CrewAssignmentService from "../services/CrewAssignmentService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CrewAssignmentTable,
  },
  data() {
    return {
      crewAssignments: [],
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
    async getAllCrewAssignments(sortBy='crewAssignmentId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CrewAssignmentService.getAllCrewAssignments(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.crewAssignments.length) {
					this.crewAssignments = response.data.crewAssignments;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching crewAssignments:", error);
        }
        
      } catch (error) {
        console.error("Error fetching crewAssignment details:", error);
      }
    },
  },
  mounted() {
    this.getAllCrewAssignments();
  },
  created() {
    this.$root.$on('searchQueryForCrewAssignmentsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCrewAssignments();
    })
  }
};
</script>
<style></style>
