module nts.uk.at.view.kdr001.b {
    export module service {
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
    }
}

