module nts.uk.com.view.cas014.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        export class Service {
            paths = {
                getAllData: "ctx/sys/auth/grant/rolesetjob/start",
                registerData: "ctx/sys/auth/grant/rolesetjob/register"
            }
            constructor() {

            }

            getAllData(refDate: any): JQueryPromise<any> {
                return ajax("com", this.paths.getAllData, { refDate: refDate });
            };

            registerData(data: any): JQueryPromise<any> {
                return ajax("com", this.paths.registerData, data);
            };

        }
    }
}
