module nts.uk.at.view.kdl009.a {
    export module service {
        var paths: any = {
            getAbsRecGenDigesHis : "at/request/dialog/employmentsystem/getAbsRecGenDigesHis"
        }
        
        export function getAbsRecGenDigesHis(employeeId: string, baseDate: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getAbsRecGenDigesHis, employeeId, baseDate);
        } 
    }
}