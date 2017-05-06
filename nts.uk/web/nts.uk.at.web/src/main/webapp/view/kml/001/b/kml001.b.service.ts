module kml001.b.service {
    var paths: any = {
        extraTimeSelect: "at/budget/premium/extraTime/findBycompanyID",
        extraTimeUpdate: "at/budget/premium/extraTime/update"
    }
    
    export function extraTimeSelect(): JQueryPromise<any> {
        return nts.uk.request.ajax("at",paths.extraTimeSelect);
    }
    
    export function extraTimeUpdate(command): JQueryPromise<any> {
        return nts.uk.request.ajax("at",paths.extraTimeUpdate, command);
    }
    
}