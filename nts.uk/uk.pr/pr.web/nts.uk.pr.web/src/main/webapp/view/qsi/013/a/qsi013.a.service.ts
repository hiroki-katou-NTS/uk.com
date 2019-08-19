module nts.uk.pr.view.qsi013.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getSocialInsurNotiCreateSet: "ctx/pr/report/printdata/socinsurnoticreset/getSocialInsurNotiCreateSet",
            exportFile: "ctx/pr/file/printdata/notice/exportFile"
        };

        export function getSocialInsurNotiCreateSet(): JQueryPromise<any> {
            return ajax("pr", paths.getSocialInsurNotiCreateSet);
        }

        export function exportFile(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFile, data);
        }
    }

}
