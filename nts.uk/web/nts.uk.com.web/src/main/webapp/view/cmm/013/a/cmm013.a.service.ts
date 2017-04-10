module cmm013.a.service {
    var paths = {
        findAllPosition: "basic/organization/position/findallposition/",
        addPosition: "basic/organization/position/addPosition",
        deletePosition: "basic/organization/position/deletePosition",
        updatePosition: "basic/organization/position/updatePosition",
        getAllHistory: "basic/organization/position/getallhist",
        regitry: "basic/organization/position/registryPosition",
        updateHist: "basic/organization/position/updateHist",
        deleteHist: "basic/organization/position/deleteHist",
        getRef: "basic/organization/position/getalljobtitleref/",
        getAuthLevel: "basic/organization/position/getallauthlevel/",
        getAllUseKt : "ctx/proto/company/findByUseKtSet/",
        getAuth: "basic/organization/position/getalljobrefauth/"

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
        export function addPosition(position: viewmodel.model.ListPositionDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.addPosition, position)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
        export function updatePosition(position: viewmodel.model.ListPositionDto) {
        let dfd = $.Deferred();
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



       export function registry(addHandler: viewmodel.model.registryCommand){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.regitry, addHandler)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }
    
    
       export function getAllJobTitleRef(historyId: string, jobCode: string ): JQueryPromise<Array<GetJobAuth>>{
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
    
          export function getAllJobTitleAuth(historyId: string, jobCode: string ): JQueryPromise<Array<GetJobAuth>>{
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.getAuth + historyId + "/" + jobCode)
            .done(function(res: Array<any>) {
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
            referenceSettings: number;
            authScopeAtr: string;
            constructor(historyId: string,jobCode: string,authCode: string,
                        authName: string,referenceSettings: number,authScopeAtr: string){
                this.historyId = historyId;
                this.jobCode = jobCode;
                this.authCode = authCode;
                this.authName = authName;
                this.referenceSettings = referenceSettings;
                this.authScopeAtr = authScopeAtr;
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


    
     
    
}