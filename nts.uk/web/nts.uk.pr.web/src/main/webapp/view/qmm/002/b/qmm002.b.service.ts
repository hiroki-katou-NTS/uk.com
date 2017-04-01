module nts.uk.pr.view.qmm002.b {
    export module service {
        var paths = {
            removeListBank: "basic/system/bank/remove/list"
        };
        
        export function removeBank(data): JQueryPromise<any> {
            var path = paths.removeListBank;
            return nts.uk.request.ajax("com", path, data);
        }
    }    
}