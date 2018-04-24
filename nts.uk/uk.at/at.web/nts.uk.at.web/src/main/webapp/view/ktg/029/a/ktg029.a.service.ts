module nts.uk.at.view.ktg029.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        export class Service {
            paths = {
                getPeriod: "screen/at/OptionalWidget/getCurrentMonth",
                getOptionalWidget: "screen/at/OptionalWidget/getOptionalWidget"
            }
            constructor() {}
            
            getPeriod(): JQueryPromise<any> {
                return ajax("at", this.paths.getPeriod);
            }
            getOptionalWidget(code: any): JQueryPromise<any> {
                return ajax("at", this.paths.getOptionalWidget, code);
            }
            
        }
    }
   
}