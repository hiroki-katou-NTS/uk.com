module cmmhoa013.a.service {
    var paths = {
        getAllHistory: "basic/organization/position/getalljobhist",
        addJobHist: "basic/organization/position/addjobhist",
        updateJobHist: "basic/organization/position/updatejobhist",
        deleteJobHist: "basic/organization/position/deletejobhist"
        }
    
     /**
     * get all history
     */
    export function getAllHistory():JQueryPromise<Array<model.JobHistDto>> {
        var dfd = $.Deferred<Array<model.historyDto>>();
        nts.uk.request.ajax("com",paths.getAllHistory)
            .done(function(res: Array<model.historyDto>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    /**
     * add job history
     */
    
    export function addJobHist(jobHist: model.JobHistDto){
        var dfd = $.Deferred<model.historyDto>();
        nts.uk.request.ajax("com",paths.addJobHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    /**
     * update job history
     */
    export function updateJobHist(jobHist: model.JobHistDto){
        var dfd = $.Deferred<model.historyDto>();
        nts.uk.request.ajax("com",paths.updateJobHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    /**
     * delete job history
     */
    export function deleteJobHist(jobHist: model.JobHistDto){
        var dfd = $.Deferred<model.historyDto>();
        nts.uk.request.ajax("com",paths.deleteJobHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }

    /**
     * model 
     * historyDto
     * positionDto
     */
    export module model {
        
       export class historyDto{
           startDate: string;
           endDate: string;
           historyId: string; 
           constructor (startDate: string, endDate: string,historyId: string){
               this.startDate = startDate;
               this.endDate = endDate;
               this.historyId = historyId;
           }  
       }
        export class JobHistDto{
            companyCode: string;
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(companyCode: string, startDate: string, endDate: string, historyId: string){
                this.companyCode = companyCode;
                this.startDate = startDate;
                this.endDate = endDate;
                this.historyId = historyId;
            }    
        }
        
       export class ListPositionDto {
            jobCode: string;
            jobName: string;            
            presenceCheckScopeSet: number;
            memo: string;
            constructor(code: string, name: string, presenceCheckScopeSet: number,memo: string) {
                var self = this;
                self.jobCode = code;
                self.jobName = name;
                self.presenceCheckScopeSet = presenceCheckScopeSet;
                self.memo = memo;
            }
        }
        
        export class ProcessPosition{
            historyId: string;
            jobCode: string;
            jobName: string;            
            presenceCheckScopeSet: number;
            memo: string;
            constructor(historyId: string,
                jobCode: string, 
                jobName: string, 
                presenceCheckScopeSet: number,
                memo: string){
               var self = this;
               self.historyId = historyId;
               self.jobCode = jobCode;
               self.jobName = jobName;
               self.presenceCheckScopeSet = presenceCheckScopeSet;
               self.memo = memo;
           }
        }        
    }
}