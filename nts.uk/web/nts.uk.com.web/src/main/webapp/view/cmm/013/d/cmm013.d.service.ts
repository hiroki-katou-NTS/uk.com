module cmm013.d.service {
      var paths = {
        updateHist: "basic/organization/position/updateHist", 
        }
    
   
    export function updateHist(jobHist: viewmodel.model.AfUpdate){
        var dfd = $.Deferred<viewmodel.model.AfUpdate>();
        nts.uk.request.ajax("com",paths.updateHist,jobHist)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

}