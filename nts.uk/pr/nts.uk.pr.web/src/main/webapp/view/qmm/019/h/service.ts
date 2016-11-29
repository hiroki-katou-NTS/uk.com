module qmm019.h.service {
    var paths = {
        getLayoutInfor: "pr/proto/layout/findalllayout"
    }
    export function getAllLayout(): JQueryPromise<Array<model.LayoutMasterModel>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getLayoutInfor)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
      export module model {
    export interface LayoutMasterModel {
        personalWageName: String;
        companyCode: String;
    }
          }
}