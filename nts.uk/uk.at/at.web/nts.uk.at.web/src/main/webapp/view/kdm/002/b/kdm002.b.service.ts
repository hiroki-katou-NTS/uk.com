module nts.uk.at.view.kdm002.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        var paths = {
            execution: ""
        }
        /**
         * call service execution 
         */
        export function execution(): JQueryPromise<any> {
            return ajax('com', paths.execution);
        }
    }
}