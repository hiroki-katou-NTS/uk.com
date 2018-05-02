module nts.uk.at.view.kaf018.d.service {
    var paths: any = {
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
        initApprovalSttByEmployee: "at/request/application/approvalstatus/initApprovalSttByEmployee",
        initApprovalSttRequestContentDis: "at/request/application/approvalstatus/initApprovalSttRequestContentDis"
    }
    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }
    
    export function initApprovalSttByEmployee(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initApprovalSttByEmployee, obj);
    }
    export function initApprovalSttRequestContentDis(obj) : JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initApprovalSttRequestContentDis, obj);
    }
}