module nts.uk.at.view.kaf018.a.service {
    var paths: any = {
        getAll: "at/request/application/setting/workplace/getall"
    }
    
    export function getAll(lstWkpId): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getAll, lstWkpId);
    }
}