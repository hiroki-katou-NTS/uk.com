module nts.uk.at.view.kaf018_old.c.service {
    var paths: any = {
        getDataSpecDateAndHoliday: "screen/at/schedule/basicschedule/getDataSpecDateAndHoliday",
        initApprovalSttByEmployee: "at/request/application/approvalstatus/initApprovalSttByEmployee"
    }
    export function getDataSpecDateAndHoliday(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataSpecDateAndHoliday, obj);
    }
    
    export function initApprovalSttByEmployee(obj): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initApprovalSttByEmployee, obj);
    }
}