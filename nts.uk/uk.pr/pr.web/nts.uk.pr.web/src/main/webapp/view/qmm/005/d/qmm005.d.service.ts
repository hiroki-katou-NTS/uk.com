module nts.uk.pr.view.qmm005.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        findDisplayRegister: "nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls/findDisplayRegister/{0}",
        getSetDaySupports: "xxx/getSetDaySupports",
        registerprocessingsegment :"ctx/pr/core/ws/wageprovision/registerprocessingsegment"
    };

    export function findDisplayRegister(processingCategoryNo: number): JQueryPromise<any> {
        let _path = format(paths.findDisplayRegister, processingCategoryNo);
        return ajax('com', _path);
    }

    export function registerprocessingsegment(command): JQueryPromise<any> {
        return ajax('pr', paths.registerprocessingsegment, command);
    }

    export function getSetDaySupports(): JQueryPromise<any> {
        return ajax(paths.getSetDaySupports);
    }
}