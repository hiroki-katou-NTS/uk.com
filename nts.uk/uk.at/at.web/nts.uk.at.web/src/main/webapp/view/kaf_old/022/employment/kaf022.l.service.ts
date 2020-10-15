module nts.uk.at.view.kaf022.l.service{
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        findAllWorktype: "at/screen/worktype/findAll",
        findEmploymentSetByCid: "at/request/application/common/setting/findEmploymentSetByCompanyId",
        findEmploymentSetByEmpCode: "at/request/application/common/setting/findEmploymentSetByCode",
        copyEmploymentSet: "at/request/application/common/setting/copyEmploymentSetting",
        deleteEmploymentSet: "at/request/application/common/setting/deleteEmploymentSetting",
        updateEmploymentSet: "at/request/application/common/setting/updateEmploymentSetting",
        addEmploymentSet: "at/request/application/common/setting/addEmploymentSetting",
        findOtKaf022: "at/screen/worktype/find-ot-kaf022",
        findAbsenceKaf022: "at/screen/worktype/find-absence-kaf022",
        findWkChangeKaf022: "at/screen/worktype/find-work-change-kaf022",
        findBounceKaf022: "at/screen/worktype/find-bounce-kaf022",
        findHdTimeKaf022: "at/screen/worktype/find-hdtime-kaf022",
        findHdShipKaf022: "at/screen/worktype/find-hd-ship-kaf022",
        findBusinessTrip: "at/screen/worktype/find-business-trip-kaf022"
    };
    
    export function findHdShipKaf022(hdShip: any): JQueryPromise<any> {
        return ajax("at", paths.findHdShipKaf022, hdShip); 
    }
    
    export function findHdTimeKaf022(): JQueryPromise<any> {
        return ajax("at", paths.findHdTimeKaf022); 
    }
    
    export function findBounceKaf022(halfDay: Array<number>): JQueryPromise<any> {
        return ajax("at", paths.findBounceKaf022, halfDay);
    }
    
    export function findWkChangeKaf022(): JQueryPromise<any> {
        return ajax("at", paths.findWkChangeKaf022); 
    } 
    
    export function findAbsenceKaf022(absenceKAF022: any): JQueryPromise<any> {
        return ajax("at", paths.findAbsenceKaf022, absenceKAF022); 
    } 
    
    export function findOtKaf022(): JQueryPromise<any> {
        return ajax("at", paths.findOtKaf022); 
    }

    export function findBusinessTripKaf022(command): JQueryPromise<any> {
        return ajax("at", paths.findBusinessTrip, command);
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
    export function addEmploymentSet(command: any): JQueryPromise<any> {
        return ajax("at", paths.addEmploymentSet, command); 
    }
    export function updateEmploymentSet(command:any): JQueryPromise<any> {
        return ajax("at", paths.updateEmploymentSet, command); 
    }
    export function deleteEmploymentSet(command: any): JQueryPromise<any> {
        return ajax("at", paths.deleteEmploymentSet, command); 
    }
    export function copyEmploymentSet(command: any): JQueryPromise<any> {
        return ajax("at", paths.copyEmploymentSet, command); 
    }
}