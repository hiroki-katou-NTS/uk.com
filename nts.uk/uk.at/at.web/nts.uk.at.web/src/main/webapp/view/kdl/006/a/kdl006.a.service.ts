module nts.uk.at.view.kdl006.a.service {
    var paths = {
        startPage : "screen/at/kdl006/startpage",
        getWorkplace : "screen/at/kdl006/getworkplace",
        save : "screen/at/kdl006/save"
    }
        
    export function startPage(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startPage);
    }
    
       export function getWorkplace(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkplace, param);
    }
    
       export function save(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.save, param);
    }
}

