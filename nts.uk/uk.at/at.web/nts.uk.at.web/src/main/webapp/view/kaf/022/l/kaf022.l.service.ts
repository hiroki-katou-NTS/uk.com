module nts.uk.at.view.kmf022.l.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        findAllWorktype: "at/screen/worktype/findAll",
        findEmploymentSetByCid: "at/request/application/common/setting/findEmploymentSetByCompanyId",
        findEmploymentSetByEmpCode: "at/request/application/common/setting/findEmploymentSetByCode",
        copyEmploymentSet: "at/request/application/common/setting/copyEmploymentSetting",
        deleteEmploymentSet: "at/request/application/common/setting/deleteEmploymentSetting",
        updateEmploymentSet: "at/request/application/common/setting/updateEmploymentSetting",
        addEmploymentSet: "at/request/application/common/setting/addEmploymentSetting"
    }  
    export function findAllWorktype(): JQueryPromise<any> {
        return ajax("at", paths.findAllWorktype); 
    }  
    export function findEmploymentSetByCid(): JQueryPromise<any> {
        return ajax("at", paths.findEmploymentSetByCid); 
    }
    export function findEmploymentSetByEmpCode(empCode: string): JQueryPromise<any> {
        return ajax("at", paths.findEmploymentSetByEmpCode, empCode); 
    }
    export function addEmploymentSet(command: Array<any>): JQueryPromise<any> {
        return ajax("at", paths.addEmploymentSet, command); 
    }
    export function updateEmploymentSet(command: Array<any>): JQueryPromise<any> {
        return ajax("at", paths.updateEmploymentSet, command); 
    }
    export function deleteEmploymentSet(command: any): JQueryPromise<any> {
        return ajax("at", paths.deleteEmploymentSet, command); 
    }
    export function copyEmploymentSet(command: any): JQueryPromise<any> {
        return ajax("at", paths.copyEmploymentSet, command); 
    }
}