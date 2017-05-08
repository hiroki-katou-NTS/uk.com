module ccg031.b.service {
    var paths = {
        findAll: "sys/portal/topagepart/findAll",
    }
    
    export function findAll(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.findAll);
    }
}