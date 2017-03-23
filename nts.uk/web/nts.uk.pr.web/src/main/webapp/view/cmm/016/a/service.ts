module cmmhoa013.a.service {
    var paths = {
        getAllHistory: "basic/organization/position/getalljobhist",
        addJobHist: "basic/organization/position/addjobhist",
        updateJobHist: "basic/organization/position/updatejobhist",
        deleteJobHist: "basic/organization/position/deletejobhist",
        getAllJobTitle: "basic/organization/position/getalljobtitle/",
        addJobTitle: "basic/organization/position/addjobtitle",
        deleteJobTitle: "basic/organization/position/deletejobtitle"
        }
    
     /**
     * get all history
     */
    export function getAllHistory():JQueryPromise<Array<model.JobHistDto>> {
        var dfd = $.Deferred<Array<model.JobHistDto>>();
        nts.uk.request.ajax("com",paths.getAllHistory)
            .done(function(res: Array<model.JobHistDto>) {
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
    
    export function addJobHist(addHandler: model.AddHandler){
        var dfd = $.Deferred<model.JobHistDto>();
        nts.uk.request.ajax("com",paths.addJobHist, addHandler)
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
    export function updateJobHist(updateHandler: model.UpdateHandler){
        var dfd = $.Deferred<model.UpdateHandler>();
        nts.uk.request.ajax("com",paths.updateJobHist, ko.mapping.toJS(updateHandler))
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    /**
     * get all job title
     */
    export function getAllJobTitle(historyId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.getAllJobTitle + historyId)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    /**
     * delete job title
     */
       export function deleteJobTitle(jobTitle: model.DeleteJobTitle){
        var dfd = $.Deferred<model.DeleteJobTitle>();
        nts.uk.request.ajax("com",paths.deleteJobTitle, jobTitle)
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
        export class AddHandler{
            jobHist: JobHistDto;
            jobTitle: ListPositionDto;
            checkAddJhist: string;//check add job history: L1(1)/L(n)(2)
            checkAddJtitle: string;//check add job title: not add(0)/add old(copy)(1)/add new(2)/
                                    //add new and add old(3)/update(5)
            constructor(jHist: JobHistDto, jTitle: ListPositionDto, checkAddJhist: string, checkAddJtitle: string){
                this.jobHist = jHist;
                this.jobTitle = jTitle;
                this.checkAddJhist = checkAddJhist;
                this.checkAddJtitle = checkAddJtitle;
            }
        }
        export class UpdateHandler{
            jHist: JobHistDto;
            checkUpdate: string;//check update jHist: not update (0)/update L.i (1)/update L.n (2)
            checkDelete: string;//check delete jHist: not delete (0)/delete L1 (1)
            constructor(jHist: JobHistDto, checkUpdate: string, checkDelete: string){
                this.jHist = jHist;
                this.checkUpdate = checkUpdate;
                this.checkDelete = checkDelete;
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
        export class DeleteJobTitle{
            historyId: string;
            jobCode: string;
            constructor(historyId: string, jobCode: string){
                this.historyId = historyId;
                this.jobCode = jobCode;
            }    
        }
     
    }
}