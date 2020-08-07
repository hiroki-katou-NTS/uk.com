module nts.uk.at.view.kdl006.a.service {
    var paths = {
        startPage : "screen/at/kdl006/startpage",
        getworkplace : "screen/at/kdl006/getworkplace",
        save : "screen/at/kdl006/save"
    }
        
    export function startPage(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startPage, param);
    }
    
       export function getworkplace(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getworkplace, param);
    }
    
       export function save(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.save, param);
    }
}

