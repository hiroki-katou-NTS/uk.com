module nts.uk.at.view.ktg029.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        export class Service {
            paths = {
                getOptionalWidgetDisplay: "screen/at/OptionalWidget/getOptionalWidgetDisplay",
                getOptionalWidgetInfo:  "screen/at/OptionalWidget/getOptionalWidgetInfo"
            }
            constructor() {}
            
            getOptionalWidgetDisplay(code: any): JQueryPromise<any> {
                return ajax("at", this.paths.getOptionalWidgetDisplay, code);
            }
            getOptionalWidgetInfo(param: any): JQueryPromise<any> {
                return ajax("at", this.paths.getOptionalWidgetInfo, param);
            }
            
        }
    }
   
}