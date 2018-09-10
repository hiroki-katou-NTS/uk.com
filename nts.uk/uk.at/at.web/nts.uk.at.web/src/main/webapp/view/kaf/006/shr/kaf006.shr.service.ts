module nts.uk.at.view.kaf006.shr.service {
    var paths: any = {
        getAppForLeaveStart: "at/request/application/appforleave/getAppForLeaveStart",
        getAllAppForLeave: "at/request/application/appforleave/getAllAppForLeave",
        findByChangeAppDate: "at/request/application/appforleave/findChangeAppdate",
        checkConvertPrePost: "at/request/application/overtime/checkConvertPrePost",
        findChangeAllDayHalfDay: "at/request/application/appforleave/getChangeAllDayHalfDay",
        getChangeDisplayHalfDay: "at/request/application/appforleave/findChangeDisplayHalfDay",
        getChangeWorkType: "at/request/application/appforleave/findChangeWorkType",
        getListWorkTime: "at/request/application/appforleave/getListWorkTime",
        getWorkingHours: "at/request/application/appforleave/getWorkingHours",
        createAbsence: "at/request/application/appforleave/insert",
        deleteOvertime: "at/request/application/overtime/delete",
        updateAbsence: "at/request/application/appforleave/update",
        checkBeforeRegister: "at/request/application/overtime/checkBeforeRegister",
        checkBeforeUpdate: "at/request/application/overtime/checkBeforeUpdate",
        findByAppID: "at/request/application/appforleave/getByAppID",
        getChangeAllDayHalfDayForDetail: "at/request/application/appforleave/getChangeAllDayHalfDayForDetail",
        getRecordWork: "at/request/application/overtime/getRecordWork",
        changeRelaCD: "at/request/application/appforleave/changeRela/{0}/{1}",
        checkRegister: "at/request/application/appforleave/checkRegister"
    }
    /** Get TitleMenu */
    export function getAppForLeaveStart(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAppForLeaveStart, param);
    }
    export function getAllAppForLeave(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAllAppForLeave, param);
    }
    /**
     * Khi thay doi appDate
     */
    export function findByChangeAppDate(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findByChangeAppDate, param);
    }
    
    export function checkConvertPrePost(prePostAtr: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkConvertPrePost, prePostAtr);
    }
    
    export function findChangeAllDayHalfDay(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findChangeAllDayHalfDay, param);
     }
     export function getChangeAllDayHalfDayForDetail(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getChangeAllDayHalfDayForDetail, param);
     }    
    export function getChangeDisplayHalfDay(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getChangeDisplayHalfDay, param);
    }
    export function getChangeWorkType(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getChangeWorkType, param);
    }
    export function getListWorkTime(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getListWorkTime, param);
    }
    export function getWorkingHours(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkingHours, param);
    }
    export function createAbsence(param: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.createAbsence,param);
    }
    
     export function deleteOvertime(appID : string): JQueryPromise<void> { 
        return nts.uk.request.ajax("at", paths.deleteOvertime,appID);
    }
    
     export function updateAbsence(absence: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.updateAbsence ,absence);
    }
    
    export function checkBeforeRegister(overtime:any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkBeforeRegister ,overtime);
    }
    
    export function findByAppID(appID: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findByAppID ,appID);
    }
    
    export function getRecordWork(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getRecordWork, param);
    }
    
     export function checkBeforeUpdate(overtime:any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkBeforeUpdate ,overtime);
    }
    /**
     * when change relation ship
     */
    export function changeRelaCD(workTypeCD: string, relationCD: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", nts.uk.text.format(paths.changeRelaCD, workTypeCD, relationCD));
    }
    /**
     * Khi thay doi appDate
     */
    export function checkRegister(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkRegister, param);
    }
}