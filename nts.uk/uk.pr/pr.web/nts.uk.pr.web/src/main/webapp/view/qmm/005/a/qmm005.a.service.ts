module nts.uk.com.view.qmm005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getProcessInfomations: "nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls/findDisplayRegister/{0}",
        getSetDaySupports: "xxx/getSetDaySupports"
    };

    export function findDisplayRegister(processingCategoryNo: number): JQueryPromise<any> {
        let _path = format(paths.findDisplayRegister, processingCategoryNo);
        return ajax('com', _path);
    }

    export function getSetDaySupports(): JQueryPromise<any> {
        return ajax(paths.getSetDaySupports);
    }

}