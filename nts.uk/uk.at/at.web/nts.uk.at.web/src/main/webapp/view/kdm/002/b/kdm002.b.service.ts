module nts.uk.at.view.kdm002.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        var paths = {
            execution: "at/record/remainnumber/checkFunc/execution"
        }
        /**
         * call service execution 
         */
        export function execution(command: any): JQueryPromise<any> {
            return ajax('com', paths.execution, command);
        }
    }
}