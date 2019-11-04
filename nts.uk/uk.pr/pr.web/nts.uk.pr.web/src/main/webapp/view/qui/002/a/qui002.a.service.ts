module nts.uk.pr.view.qui002.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getSocialInsurNotiCreateSet: "ctx/pr/report/printconfig/socinsurnoticreset/getSocialInsurNotiCreateSet",
            exportFilePDF: "ctx/pr/file/printconfig/notice/exportFilePDF"
        };

        export function start(): JQueryPromise<any> {
            return ajax("pr", paths.getSocialInsurNotiCreateSet);
        }

        export function exportFilePDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportFilePDF, data);
        }



    }

}
