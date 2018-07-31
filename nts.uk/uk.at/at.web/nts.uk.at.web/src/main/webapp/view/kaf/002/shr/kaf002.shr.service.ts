module nts.uk.at.view.kaf002.shr {
    import ajax = nts.uk.request.ajax;
    export module service {
        var paths: any = {
            getAllWorkLocation: "at/record/worklocation/findall",
            findByAppID: "at/request/application/stamp/findByID",
            newScreenFind: "at/request/application/stamp/newAppStampInitiative",
            insert: "at/request/application/stamp/insert",
            update: "at/request/application/stamp/update",
            getStampCombinationAtr: "at/request/application/stamp/enum/stampCombination",
            getAttendance: "at/request/application/stamp/getAttendanceItem"
        }
        
        export function findAllWorkLocation(): JQueryPromise<any> {
            return ajax("at", paths.getAllWorkLocation);
        }
        
        export function findByAppID(appID: string): JQueryPromise<any> {
            return ajax("at", paths.findByAppID, appID);
        }
        
        export function newScreenFind(employeeID, date): JQueryPromise<any> {
            return ajax(paths.newScreenFind, { employeeID: employeeID, date: date });
        }
        
        export function insert(command): JQueryPromise<any> {
            return ajax(paths.insert, command);
        }
        
        export function update(command): JQueryPromise<any> {
            return ajax(paths.update, command);
        }
        
        export function getStampCombinationAtr(): JQueryPromise<any> {
            return ajax(paths.getStampCombinationAtr);    
        }
        
        export function getAttendanceItem(param: any): JQueryPromise<any> {
            return ajax(paths.getAttendance, param);    
        }
    }
}