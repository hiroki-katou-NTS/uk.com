module nts.uk.com.view.cmm013.a {
    
    export module service {
            
        /**
         *  Service paths
         */
        var servicePath: any = {
            findListJobTitle: "",
        }
    
        /**
         * find list JobTitle by Date
         */
        export function findListJobTitle(baseDate: Date): JQueryPromise<Array<model.JobTitleInfo>> {
            return nts.uk.request.ajax(servicePath.findListJobTitle, { startDate: baseDate });
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
    
            export interface JobTitle {
                companyId: string;
                jobTitleId: string;
                jobTitleHistory: Array<JobTitleHistory>;
            }
    
            export interface JobTitleHistory {
                historyId: string;
                period: Period;
            }
    
            export interface Period {
                startDate: string;
                endDate: string;
            }
    
            export interface SequenceCode {
                companyId: string;
                sequenceCode: string;
                sequenceName: string;
                order: number;
            }
        }
    }
}