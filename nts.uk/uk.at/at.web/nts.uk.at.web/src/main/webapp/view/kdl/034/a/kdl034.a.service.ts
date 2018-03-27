module nts.uk.at.view.kdl034.a {
    export module service {
        var paths: any = {
            remand: "at/request/application/remandapp"
        }
        
        export function remand(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.remand, command);
        }
    }
}