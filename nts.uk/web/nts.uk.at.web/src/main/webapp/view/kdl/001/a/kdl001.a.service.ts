module nts.uk.at.view.kdl001.a {
    export module service {
        var paths: any = {
            findByCodeList: "at/shared/worktime/findByCodeList"
        }
        
        export function findByCodeList(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
        }
    }
}