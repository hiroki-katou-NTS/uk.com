module nts.uk.at.view.kaf002.shr {
    import ajax = nts.uk.request.ajax;
    export module service {
        var paths: any = {
            findByAppID: "at/request/application/stamp/findByAppID",
            newScreenFind: "at/request/application/stamp/newAppStampInitiative",
            insert: "at/request/application/stamp/insert",
            update: "at/request/application/stamp/update",
            getStampCombinationAtr: "at/request/application/stamp/enum/stampCombination"
        }
        
        export function findByAppID(): JQueryPromise<any> {
            return ajax(paths.findByAppID);
        }
        
        export function newScreenFind(): JQueryPromise<any> {
            return ajax(paths.newScreenFind);
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
    }
}