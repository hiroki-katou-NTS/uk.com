module nts.uk.com.view.cas014.l {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getAllData: "ctx/exio/dom/exi/condset/start",
            registerData: "ctx/exio/dom/exi/condset//register"
        }

        export function getAllData(refDate: any): JQueryPromise<any> {
            return ajax("com", paths.getAllData, { refDate: refDate });
        };

        export function registerData(data: any): JQueryPromise<any> {
            return ajax("com", paths.registerData, data);
        };

    }
}
