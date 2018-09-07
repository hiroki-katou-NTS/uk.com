module nts.uk.com.view.qmm005.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getProcessInfomations: "xxx/getProcessInfomations",
        getSetDaySupports:"xxx/getSetDaySupports"
    };

    export function getProcessInfomations(): JQueryPromise<any> {
        return ajax(paths.getProcessInfomations);
    }

    export function getSetDaySupports(): JQueryPromise<any> {
        return ajax(paths.getSetDaySupports);
    }

}