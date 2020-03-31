module nts.uk.at.view.kdp010.e.service {
    let paths: any = {
        saveStampApp: "at/record/stamp/application/saveStampApp",
        getStampApp: "at/record/stamp/application/getStampApp",
        getStampFunc: "at/record/stamp/application/getStampFunc",
        saveStampFunc: "at/record/stamp/application/saveStampFunc",
        deleteStampFunc: "at/record/stamp/application/delete",
        getAttendNameByIds:"at/record/attendanceitem/daily/getattendnamebyids",
        getOptItemByAtr: "at/record/attendanceitem/daily/getlistattendcomparison"
    }

    export function saveStampApp(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampApp, command);
    }

    export function getStampApp(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampApp);
    }
    
     export function saveStampFunc(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampFunc, command);
    }

    export function getStampFunc(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampFunc);
    }

    export function getAttendNameByIds(command) : JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getAttendNameByIds, command);
    }
    
    export function findWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findWorkType);
    }
    
    export function getOptItemByAtr() {
        return nts.uk.request.ajax("at", paths.getOptItemByAtr);
    }
    
     export function deleteStampFunc(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteStampFunc, command);
    }
}