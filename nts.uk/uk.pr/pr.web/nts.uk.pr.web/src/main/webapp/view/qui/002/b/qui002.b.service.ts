module nts.uk.pr.view.qui002.b {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getPersonInfo: "ctx/pr/report/printconfig/empinsurreportcreset/getPersonInfo"
        };

        export function getPersonInfo(data): JQueryPromise<any> {
            return ajax(paths.getPersonInfo, data);
        }
    }

}
