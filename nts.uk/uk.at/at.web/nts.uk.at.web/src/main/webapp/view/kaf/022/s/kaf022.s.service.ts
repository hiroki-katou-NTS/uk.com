module nts.uk.at.view.kaf022.s.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        update: 'at/request/application-reason/update',
        insert: 'at/request/application-reason/insert',
        deleteReason: 'at/request/application-reason/delete',
    }
        
    export function getReason(appType: number) {
        return ajax(`at/request/application-reason/find/reason/${appType}`);
    }
    export function insert(command): JQueryPromise<void>{
        return ajax("at", paths.insert, command);
    }        
        
     export function deleteReason(command): JQueryPromise<void>{
        return ajax("at", paths.deleteReason, command);
    }  
    export function update(command): JQueryPromise<void>{
        return ajax("at", paths.update, command);  
    }
}