module cmm013.d.service {
        var paths = {
        updateJobHist: "basic/organization/position/updatejobhist"
        }
    
     /**
     * update job history
     */
    export function updateJobHist(jobHist: model.UpdateHandler){
        var dfd = $.Deferred<model.UpdateHandler>();
        nts.uk.request.ajax("com",paths.updateJobHist,jobHist)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export module model {
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
    }
}