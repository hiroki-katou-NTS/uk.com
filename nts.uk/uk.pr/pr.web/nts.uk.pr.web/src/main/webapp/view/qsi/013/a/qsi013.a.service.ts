module nts.uk.pr.view.qsi013.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths : any = {
        getSocialInsurNotiCreateSet: "ctx/pr/report/printdata/socinsurnoticreset/getSocialInsurNotiCreateSet",
        exportFile: "ctx/pr/report/printdata/socinsurnoticreset/getSocialInsurNotiCreateSet"
    };

    export function getSocialInsurNotiCreateSet(): JQueryPromise<any> {
        return ajax("pr", paths.getSocialInsurNotiCreateSet);
    }

    export function exportFile(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportFile);
    };

}
