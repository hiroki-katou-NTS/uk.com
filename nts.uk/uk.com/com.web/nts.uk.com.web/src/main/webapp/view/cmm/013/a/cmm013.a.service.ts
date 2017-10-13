module nts.uk.com.view.cmm013.a {
    
    export module service {
            
        /**
         *  Service paths
         */
        var servicePath: any = {
            findJobHistoryList: "bs/employee/jobtitle/history/findByJobId",
            findJobInfoByJobIdAndHistoryId: "bs/employee/jobtitle/info/findByJobIdAndHistoryId",
        }
    
        /**
         * findJobHistoryList
         */
        export function findJobHistoryList(jobTitleId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findJobHistoryList, { jobTitleId: jobTitleId });
        }
        
        /**
         * findJobInfoByJobIdAndHistoryId
         */
        export function findJobInfoByJobIdAndHistoryId(jobTitleId: string, jobTitleHistoryId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findJobInfoByJobIdAndHistoryId, { jobTitleId: jobTitleId, jobTitleHistoryId: jobTitleHistoryId });
        }
    
        /**
         * Model namespace.
         */
        export module model {
    
            export interface JobTitleInfo {
                companyId: string;
                jobTitleId: string;
                jobTitleCode: string;
                jobTitleName: string;
                sequenceCode: string;
            }           
        }
    }
}