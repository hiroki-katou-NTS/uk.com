module nts.uk.at.view.kmw006.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getResults: "at/record/monthlyclosure/getListEmpInfoById"
    }
        
    export function getResults(listId: Array<string>): JQueryPromise<any> {
//        let _path = format(paths.getResults, id);
        return ajax("at", paths.getResults, listId);
    }
    
}