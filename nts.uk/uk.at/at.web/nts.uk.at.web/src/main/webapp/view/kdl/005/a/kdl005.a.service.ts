module nts.uk.at.view.kdl005.a {
    export module service {
        var paths: any = {
            getAbsRecGenDigesHis : "at/request/dialog/employmentsystem/getAbsRecGenDigesHis/{0}/{1}"
        }
                
        export function getAbsRecGenDigesHis(employeeId: string, baseDate: string): JQueryPromise<any> {
            var path = nts.uk.text.format(paths.getAbsRecGenDigesHis, employeeId, baseDate);
            return nts.uk.request.ajax(path);
        }  
    }
}