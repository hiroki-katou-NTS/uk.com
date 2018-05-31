module nts.uk.at.view.kmw006.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getResults: "at/record/monthlyclosure/getErrInfor",
        exportCsv: "at/record/monthlyclosure/exportLog"
    }

    export function getResults(id: string, atr: Array<string>): JQueryPromise<any> {
        return ajax("at", paths.getResults, {logId: id, listEmpId: atr});
    }
    
    export function exportCsv(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportCsv, data);
    }
    
}