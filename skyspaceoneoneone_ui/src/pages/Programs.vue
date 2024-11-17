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
            <program-table
            v-if="programs && programs.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:programs="programs"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-programs="getAllPrograms"
             >

            </program-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ProgramTable from "@/components/ProgramTable";
import ProgramService from "../services/ProgramService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ProgramTable,
  },
  data() {
    return {
      programs: [],
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
    async getAllPrograms(sortBy='programId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ProgramService.getAllPrograms(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.programs.length) {
					this.programs = response.data.programs;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching programs:", error);
        }
        
      } catch (error) {
        console.error("Error fetching program details:", error);
      }
    },
  },
  mounted() {
    this.getAllPrograms();
  },
  created() {
    this.$root.$on('searchQueryForProgramsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPrograms();
    })
  }
};
</script>
<style></style>
