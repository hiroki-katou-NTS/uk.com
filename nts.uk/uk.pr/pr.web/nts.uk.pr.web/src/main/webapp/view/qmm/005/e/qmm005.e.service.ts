module nts.uk.pr.view.qmm005.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        findReflectSystemReferenceDateInfo: "ctx/pr/processdatecls/findReflectSystemReferenceDateInfo/{0}/{1}",

    };
    export function findReflectSystemReferenceDateInfo(processingCategoryNo: number, processDate: number): JQueryPromise<any> {
        let _path = format(paths.findReflectSystemReferenceDateInfo, processingCategoryNo, processDate);
        return ajax('pr', _path);
    }
}