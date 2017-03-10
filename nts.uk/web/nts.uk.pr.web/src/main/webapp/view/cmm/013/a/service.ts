module cmm013.a.service {
    var paths = {
        findAllPosition: "basic/position/findallposition/",
        addPosition: "basic/position/addPosition",
        deletePosition: "basic/position/deletePosition",
        updatePosition: "basic/position/updatePosition",
        getAllHistory: "basic/position/getallhist"
        
    /**
     * get all Position
     */
    }
    export function findAllPosition(historyId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.findAllPosition + historyId)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    /**
     * add Position
     */
    
    export function addPosition(position: model.DeletePositionCommand){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.addPosition, position)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }

    /**
     * update Position
     */

    export function updatePosition(position: model.ListPositionDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updatePosition, position).done(
            function(res: any) {
                dfd.resolve(res);
            }
        )
            .fail(function(res) {
                dfd.reject(res);
            })

        return dfd.promise();
    }

    /**
    * delete Position
    */

    export function deletePosition(position: model.DeletePositionCommand) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.deletePosition, position).done(
            function(res: any) {
                dfd.resolve(res);
            }
        )
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
     /**
     * get all history
     */
    export function getAllHistory():JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.getAllHistory)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    //model
    export module model {
        
       export class ListHistoryDto{
           startDate: string;
           endDate: string;
           historyId: string;
           constructor(startDate: string, endDate: string, historyId: string){
               var self = this;
               self.startDate = startDate;
               self.endDate = endDate;
               self.historyId = historyId;
           }    
       }
        
       export class ListPositionDto {
            jobCode: string;
            jobName: string;
            presenceCheckScopeSet: number;
            memo: string;
           historyId: string;
            constructor(code: string, name: string, presenceCheckScopeSet: number,memo: string, historyId: string) {
                var self = this;
                self.jobCode = code;
                self.jobName = name;
                self.presenceCheckScopeSet = presenceCheckScopeSet;
                self.memo = memo;
                self.historyId = historyId;
            }
        }
        
        export class DeletePositionCommand {
            jobCode: string;
            historyId: string
            constructor(jobCode: string,historyId: string) {
                this.jobCode = jobCode;
                this.historyId = historyId;
            }

        }
    }
}