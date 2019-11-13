module nts.uk.pr.view.qui001.a {
    import ajax = nts.uk.request.ajax;

    export module service {
        let paths: any = {
            getSocialInsurNotiCreateSet: "ctx/pr/report/printconfig/socinsurnoticreset/getSocialInsurNotiCreateSet",
            exportPDF: ""
        };

        export function getSocialInsurNotiCreateSet(): JQueryPromise<any> {
            return ajax("pr", paths.getSocialInsurNotiCreateSet);
        }

        export function exportPDF(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportPDF, data);
        }
    }
}
