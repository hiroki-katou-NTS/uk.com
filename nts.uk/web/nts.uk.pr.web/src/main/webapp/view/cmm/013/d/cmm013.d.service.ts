module cmm013.d.service {
      var paths = {
        findAllPosition: "basic/position/findallposition/",
        addPosition: "basic/position/addPosition",
        deletePosition: "basic/position/deletePosition",
        updatePosition: "basic/position/updatePosition",
        getAllHistory: "basic/position/getallhist",
        addHist: "basic/organization/position/addHist",
        updateHist: "basic/organization/position/updateHist",
        deleteHist: "basic/organization/position/deleteHist",
        findAllPosition2: "basic/position/findall"
        
        }
    
     export function getAllHistory(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.getAllHistory)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }



     export function updateJobHist(jobHist: viewmodel.model.ListHistoryDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.updateHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
     }

    export function deleteJobHist(jobHist:viewmodel.model.ListHistoryDto){
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