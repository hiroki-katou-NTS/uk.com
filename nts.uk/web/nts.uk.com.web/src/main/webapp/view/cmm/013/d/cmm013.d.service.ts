module cmm013.d.service {
      var paths = {
   
        updateHist: "basic/organization/position/updateHist",

        
        }
    
     export function updateHist(listHist: model.UpdateHandler){
        var dfd = $.Deferred<model.UpdateHandler>();
        nts.uk.request.ajax("com",paths.updateHist, listHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }

    export module model {
        export class UpdateHandler{
            listHist: ListHistoryDto;
            checkUpdate: string;
            checkDelete: string;
            constructor(listHist: ListHistoryDto, checkUpdate: string, checkDelete: string){
                this.listHist = listHist;
                this.checkUpdate = checkUpdate;
                this.checkDelete = checkDelete;
            }    
        }
   
    export class ListHistoryDto{
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