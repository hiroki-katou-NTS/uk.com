module nts.uk.at.view.kaf005.shr.service {
    var paths: any = {
        getOvertimeByUI: "at/request/application/overtime/getOvertimeByUI",
        findByChangeAppDate: "at/request/application/overtime/findByChangeAppDate",
        checkConvertPrePost: "at/request/application/overtime/checkConvertPrePost",
        getCaculationResult: "at/request/application/overtime/getCaculationResult",
        createOvertime: "at/request/application/overtime/create",
        deleteOvertime: "at/request/application/overtime/delete",
        updateOvertime: "at/request/application/overtime/update",
        checkBeforeRegister: "at/request/application/overtime/checkBeforeRegister",
        checkBeforeUpdate: "at/request/application/overtime/checkBeforeUpdate",
        findByAppID: "at/request/application/overtime/findByAppID",
        getRecordWork: "at/request/application/overtime/getRecordWork"
    }
    /** Get TitleMenu */
    export function getOvertimeByUI(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getOvertimeByUI, param);
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