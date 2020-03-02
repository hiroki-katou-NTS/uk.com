module nts.uk.com.view.jmm018.z.service {

    var paths: any = {
        getMenuApprovalSettings: "menuApprovalSettings/get",
        update: "menuApprovalSettings/update"
        
    }

    export function getMenuApprovalSettings(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getMenuApprovalSettings);
    }
    export function update(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.update, param);
    }
}