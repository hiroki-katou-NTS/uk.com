module nts.uk.com.view.cas014.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getAllData: "ctx/sys/auth/grant/rolesetjob/start",
            registerData: "ctx/sys/auth/grant/rolesetjob/register"
        }

        export function getAllData(refDate: any): JQueryPromise<any> {
            return ajax("com", paths.getAllData, { refDate: refDate });
        };

        export function registerData(data: any): JQueryPromise<any> {
            return ajax("com", paths.registerData, data);
        };

    }
}
