
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
        findAllPosition2: "basic/position/findall"


    }
    export function findAllPosition(historyId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.findAllPosition + historyId)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function findAllPosition2(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.findAllPosition2)
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

    /**
    * delete Position
    */

    export function deletePosition(position: viewmodel.model.DeletePositionCommand) {
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



      export function addHist(jobHist: viewmodel.model.ListHistoryDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.addHist, jobHist)
            .done(function(res: any){
                dfd.resolve(res);    
            })    
            .fail(function(res){
                dfd.reject(res);
            })
        return dfd.promise();
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