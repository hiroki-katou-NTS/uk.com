module nts.uk.at.view.kdl001.a {
    export module service {
        var paths: any = {
            findByCodeList: "at/shared/worktime/findByCodeListVer1",
            findByTime: "at/shared/worktime/findByTimeVer1",
        }
        
        export function findByCodeList(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
        }
        
        export function findByTime(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }
    }
}