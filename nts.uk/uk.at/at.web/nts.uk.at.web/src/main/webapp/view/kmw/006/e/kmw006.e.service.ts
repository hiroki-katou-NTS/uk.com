module nts.uk.at.view.kmw006.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getResults: "at/record/monthlyclosure/getResults/{0}/{1}",
        exportCsv: "at/record/monthlyclosure/exportLog"
    }

    export function getResults(id: string, atr): JQueryPromise<any> {
        let _path = format(paths.getResults, id, atr);
        return ajax("at", _path);
    }
    
    export function exportCsv(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportCsv, data);
    }
    
}