module nts.uk.at.view.kaf022.s.service {
    import ajax = nts.uk.request.ajax;

    const paths: any = {
        getReasons: 'at/request/setting/company/appreasonstd/all',
        saveReason: 'at/request/setting/company/appreasonstd/item/save',
        deleteReason: 'at/request/setting/company/appreasonstd/item/delete',
    };
        
    export function getReason() {
        return ajax("at", paths.getReasons);
    }
    export function saveReason(command): JQueryPromise<void>{
        return ajax("at", paths.saveReason, command);
    }        
    export function deleteReason(command): JQueryPromise<void>{
        return ajax("at", paths.deleteReason, command);
    }
}