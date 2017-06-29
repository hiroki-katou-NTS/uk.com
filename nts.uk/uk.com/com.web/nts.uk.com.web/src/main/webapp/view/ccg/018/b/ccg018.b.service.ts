module ccg018.b.service {
    var paths: any = {
        findBySystemMenuCls: "sys/portal/standardmenu/findBySystemMenuCls",
        findTopPagePersonSet: "sys/portal/toppagesetting/personset/findBySid"
    }

    export function findBySystemMenuCls(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findBySystemMenuCls);
    }
    
    export function findTopPagePersonSet(listSid: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.findTopPagePersonSet, listSid);
    }
}