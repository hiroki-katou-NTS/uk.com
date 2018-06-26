module nts.uk.at.view.kfp001.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getErrorInfos: "ctx/at/record/optionalaggr/finderrorinfo/{0}",
        exportCsv: "at/record/monthlyclosure/exportLog"
    }

    export function getErrorInfos(aggrId: string): JQueryPromise<any> {
        let _path = format(paths.getErrorInfos, aggrId);
        return ajax("at", _path);
    }
    
    export function exportCsv(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportCsv, data);
    }
    
}