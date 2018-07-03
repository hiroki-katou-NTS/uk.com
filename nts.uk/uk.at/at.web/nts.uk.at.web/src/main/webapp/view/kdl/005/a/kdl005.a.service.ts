module nts.uk.at.view.kdl005.a {
    export module service {
        var paths: any = {
            getDetailsConfirm : "at/request/dialog/employmentsystem/getDetailsConfirm/{0}/{1}"
        }
                
        export function getDetailsConfirm(employeeId: string, baseDate: string): JQueryPromise<any> {
            var path = nts.uk.text.format(paths.getDetailsConfirm, employeeId, baseDate);
            return nts.uk.request.ajax(path);
        }  
    }
}