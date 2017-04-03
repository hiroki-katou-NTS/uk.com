module cmm013.d.service {
      var paths = {
          //xem lai duong dan
          updateHist: "basic/organization/position/updateHist",
          deleteHist: "basic/organization/position/deleteHist"  
      }
    
   //dudt
//    export function updateHist(jobHist: viewmodel.model.AfUpdate){
//        var dfd = $.Deferred<viewmodel.model.AfUpdate>();
//        nts.uk.request.ajax("com",paths.updateHist,jobHist)
//            .done(function(res: any) {
//                dfd.resolve(res);
//            })
//            .fail(function(res) {
//                dfd.reject(res);
//            })
//        return dfd.promise();
//    }
    
    
    //xoa lich su position
    export function deleteHistory(history: viewmodel.model.ListHistoryDto){
        return nts.uk.request.ajax("com", paths.deleteHist, history);    
    }
    //update lich su position
    export function updateHistory(history: viewmodel.model.ListHistoryDto){
        return nts.uk.request.ajax("com", paths.updateHist, history);    
    }
    //dudt
}