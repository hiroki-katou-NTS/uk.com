module nts.uk.at.view.kmk005.g {
    export module service {
        var paths: any = {
            findByCodeList: "at/shared/worktime/findByCodeList",
            findByTime: "at/shared/worktime/findByTime",
        }
        
        export function findByCodeList(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
        }
        
        export function findByTime(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }
    }
}