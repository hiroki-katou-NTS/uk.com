module cmm013.d.service {
      var paths = {
          //xem lai duong dan
          updateHist: "basic/organization/position/updateHist",
          deleteHist: "basic/organization/position/deleteHist"  
      }
    

    //xoa lich su position
    export function deleteHistory(history: viewmodel.model.ListHistoryDto){
        return nts.uk.request.ajax("com", paths.deleteHist, history);    
    }
    //update lich su position
    export function updateHistory(history: viewmodel.model.ListHistoryDto){
        return nts.uk.request.ajax("com", paths.updateHist, history);    
    }

}