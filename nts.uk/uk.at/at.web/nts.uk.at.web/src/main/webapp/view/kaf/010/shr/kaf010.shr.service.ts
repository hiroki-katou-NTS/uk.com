module nts.uk.at.view.kaf010.shr.service {
    var paths: any = {
        getHolidayWorkByUI: "at/request/application/holidaywork/getHolidayWorkByUI",
        findByChangeAppDate: "at/request/application/holidaywork/findChangeAppDate",
        checkConvertPrePost: "at/request/application/overtime/checkConvertPrePost",
        getCaculationResult: "at/request/application/holidaywork/getcalculationresult",
        createOvertime: "at/request/application/holidaywork/create",
        deleteOvertime: "at/request/application/overtime/delete",
        updateOvertime: "at/request/application/overtime/update",
        checkBeforeRegister: "at/request/application/holidaywork/checkBeforeRegister",
        checkBeforeUpdate: "at/request/application/overtime/checkBeforeUpdate",
        findByAppID: "at/request/application/holidaywork/findByAppID",
        getRecordWork: "at/request/application/overtime/getRecordWork"
    }
    /** Get TitleMenu */
    export function getHolidayWorkByUI(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getHolidayWorkByUI, param);
    }
    
    export function findByChangeAppDate(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findByChangeAppDate, param);
    }
    
    export function checkConvertPrePost(prePostAtr: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.checkConvertPrePost, prePostAtr);
    }
    
    export function getCaculationResult(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getCaculationResult, param);
    }
    
    export function createOvertime(overtime: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.createOvertime,overtime);
    }
    
     export function deleteOvertime(appID : string): JQueryPromise<void> { 
        return nts.uk.request.ajax("at", paths.deleteOvertime,appID);
    }
    
     export function updateOvertime(overtime:any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.updateOvertime ,overtime);
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
}