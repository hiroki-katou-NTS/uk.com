module qpp004.a.service {
    var paths = {
        getAllCompanys: "ctx/proto/company/ctx/proto/company",
        getCompanyDetail: "ctx/proto/company/"
    }
    /**
     * get list company
     */
    export function getAllCompanys(): any{
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getAllCompanys)
              .done(function(res: Array<any>){
                  dfd.resolve(res);
                  })
              .fail(function(res){
                  dfd.reject(res);
                  })
         return dfd.promise();
        
        }
//    export function getCompanyDetail


}