module jcm007.z.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        'getData': 'databeforereflecting/getData',
        'findEmployeeInfo': 'employee-info/find',
        'checkStatusRegistration': 'databeforereflecting/checkStatusRegistration/{0}',
        'preCheck': 'databeforereflecting/register/preCheck',
        'registerNewEmployee': 'databeforereflecting/register-new-employee',
        'registerNewRetireesApproved': 'databeforereflecting/register-new-retirees-approved',
        'modifyRetireeInformation': 'databeforereflecting/modify-retiree-information',
        'remove': 'databeforereflecting/remove/{0}',
        'exportExcel': 'file/hr/report/databeforereflecting/export',
        'eventChangeRetirementDate': 'databeforereflecting/event-change-retirementdate',
        'approved': 'databeforereflecting/approved',
        'startPage': 'databeforereflecting/start-page'
    }

    export function startPage(): JQueryPromise<any> {
        return ajax(paths.startPage);
    }

    export function findEmployeeInfo(data): JQueryPromise<any> {
        return ajax(paths.findEmployeeInfo, data);
    }

    export function CheckStatusRegistration(sid: any): JQueryPromise<any> {
        return ajax('hr', format(paths.checkStatusRegistration, sid));
    }

    export function preCheck(command: any): JQueryPromise<any> {
        return ajax(paths.preCheck, command);
    }

    export function registerNewEmployee(command: any): JQueryPromise<any> {
        return ajax(paths.registerNewEmployee, command);
    }

    export function registerNewRetireesApproved(command: any): JQueryPromise<any> {
        return ajax(paths.registerNewRetireesApproved, command);
    }

    export function modifyRetireeInformation(command: any): JQueryPromise<any> {
        return ajax(paths.modifyRetireeInformation, command);
    }

    export function remove(hisId: any): JQueryPromise<any> {
        return ajax('hr', format(paths.remove, hisId));
    }

    export function exportExcel(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportExcel);
    }

    export function eventChangeRetirementDate(param): JQueryPromise<any> {
        return ajax(paths.eventChangeRetirementDate, param);
    }
    
    export function approved(param): JQueryPromise<any> {
        return ajax(paths.approved, param);
    }
    
    

}