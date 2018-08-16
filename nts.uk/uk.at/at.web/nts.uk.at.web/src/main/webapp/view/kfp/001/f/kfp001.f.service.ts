module nts.uk.at.view.kfp001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getTargets: "ctx/at/record/optionalaggr/findtarget/{0}"
    }
        
    export function getTargets(aggrId: string): JQueryPromise<any> {
        let _path = format(paths.getTargets, aggrId);
        return ajax("at", _path);
    }
    
}