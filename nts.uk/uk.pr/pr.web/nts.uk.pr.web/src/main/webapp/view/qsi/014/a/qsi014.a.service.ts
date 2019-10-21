module nts.uk.pr.view.qsi014.a.service {
    import ajax = nts.uk.request.ajax;
    let paths : any = {
        initScreen: "ctx/pr/report/printdata/socinsurnoticreset/loadingscreen001",
        exportExcel: "ctx/pr/report/printdata/socialinsurnoticreset/export"

    };

    export function getDataInitScreen(): JQueryPromise<any> {
        return ajax(paths.initScreen);
    }
    export function exportFile(data: any): JQueryPromise<any>{
        return nts.uk.request.exportFile(paths.exportExcel, data);
    }
}
