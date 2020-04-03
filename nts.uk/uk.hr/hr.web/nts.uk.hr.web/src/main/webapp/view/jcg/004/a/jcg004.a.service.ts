module nts.uk.at.view.jcg004.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        export class Service {
            paths = {
                start: "jcg004/start",
            }
            constructor() {}
            
            start(): JQueryPromise<any> {
                return ajax(this.paths.start);
            }
        }
    }
   
}