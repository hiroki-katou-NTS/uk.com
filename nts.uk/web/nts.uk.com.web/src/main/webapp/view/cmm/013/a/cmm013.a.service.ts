
module cmm013.a.service {
    var paths = {
        findAllPosition: "basic/position/findallposition/",
        addPosition: "basic/position/addPosition",
        deletePosition: "basic/position/deletePosition",
        updatePosition: "basic/position/updatePosition",
        getAllHistory: "basic/position/getallhist",
        addHist: "basic/position/addHist",
        updateHist: "basic/position/updateHist",
        deleteHist: "basic/position/deleteHist",
        getRef: "basic/position/getalljobtitleref/",
        getAuthLevel: "basic/organization/position/getallauthlevel/",
        getAllUseKt : "ctx/proto/company/findByUseKtSet/"

    }
    export function findAllPosition(historyId: string): JQueryPromise<Array<viewmodel.model.ListPositionDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.ListPositionDto>>();
        nts.uk.request.ajax("com", paths.findAllPosition + historyId)
            .done(function(res: Array<viewmodel.model.ListPositionDto>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


    
        export function findByUseKt(): JQueryPromise<Array<any>>{
        var dfd = $.Deferred<Array<any>>();
        
        nts.uk.request.ajax("com",paths.getAllUseKt + "1")
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
            
            
    }


    export function deletePosition(position: viewmodel.model.DeleteJobTitle) {
        var dfd = $.Deferred<viewmodel.model.DeleteJobTitle>();
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
    export function getAllHistory(): JQueryPromise<Array<viewmodel.model.ListHistoryDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.ListHistoryDto>>();
        nts.uk.request.ajax("com", paths.getAllHistory)
            .done(function(res: Array<viewmodel.model.ListHistoryDto>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }



      export function addHist(addJobHist: viewmodel.model.AfterAdd){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.addHist, addJobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    
    
       export function getAllJobTitleRef(historyId: string, jobCode: string): JQueryPromise<Array<GetJobAuth>>{
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.getRef + historyId + "/" + jobCode)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
        export function getAllAuthLevel(authCode: string): JQueryPromise<Array<viewmodel.model.AuthLevel>>{
        var dfd = $.Deferred<Array<viewmodel.model.AuthLevel>>();
        nts.uk.request.ajax("com",paths.getAuthLevel + authCode)
            .done(function(res: Array<viewmodel.model.AuthLevel>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
        export class GetJobAuth{
            historyId: string;
            jobCode: string;
            authCode: string;
            authName: string;
            refSet: number;
            constructor(historyId: string,jobCode: string,authCode: string,
                        authName: string,refSet: number){
                this.historyId = historyId;
                this.jobCode = jobCode;
                this.authCode = authCode;
                this.authName = authName;
                this.refSet = refSet;
            }
        }
    
    
    export function updateHist(update: viewmodel.model.ListHistoryDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.updateHist, update)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }

    export function deleteHist(jobHist:viewmodel.model.DeleteHistoryCommand){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.deleteHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    
     
    
}