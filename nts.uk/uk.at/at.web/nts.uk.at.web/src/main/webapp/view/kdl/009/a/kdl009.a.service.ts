module nts.uk.at.view.kdl009.a {
    export module service {
        var paths: any = {
            getAcquisitionNumberRestDays : "at/request/dialog/employmentsystem/getAcquisitionNumberRestDays/{0}/{1}"
        }
                
        export function getAcquisitionNumberRestDays(employeeId: string, baseDate: string): JQueryPromise<any> {
            var path = nts.uk.text.format(paths.getAcquisitionNumberRestDays, employeeId, baseDate);
            return nts.uk.request.ajax(path);
        }  
    }
}