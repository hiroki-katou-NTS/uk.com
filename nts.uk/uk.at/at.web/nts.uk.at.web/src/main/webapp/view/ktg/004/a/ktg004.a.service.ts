module nts.uk.at.view.ktg004.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        export class Service {
            paths = {
                getOptionalWidgetDisplay: "screen/at/OptionalWidget/getOptionalWidgetDisplay",
                getOptionalWidgetInfo:  "screen/at/OptionalWidget/getOptionalWidgetInfo",
                getRequestList609: "at/auth/workplace/initdisplayperiod/get-request-list-609",
            }
            constructor() {}
            
            getOptionalWidgetDisplay(code: any): JQueryPromise<any> {
                return ajax("at", this.paths.getOptionalWidgetDisplay, code);
            }
            getOptionalWidgetInfo(param: any): JQueryPromise<any> {
                return ajax("at", this.paths.getOptionalWidgetInfo, param);
            }
            public getRequestList609(): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.getRequestList609);
            }
        }
    }
   
}