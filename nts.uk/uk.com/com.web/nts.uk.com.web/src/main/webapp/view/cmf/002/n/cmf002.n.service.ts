module nts.uk.com.view.cmf002.n.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getIdtSetting:              "exio/exo/initial/idsetting"
    }
    
    export function getIdtSetting(): JQueryPromise<any>{
        return ajax("com", format(paths.getIdtSetting));    
    }
}