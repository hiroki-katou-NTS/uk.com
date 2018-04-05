module nts.uk.at.view.kdl034.a {
    export module service {
        var paths: any = {
            remand: "at/request/application/remandapp",
            getAppInfoByAppID: "at/request/application/getAppInfoForRemandByAppId" 
        }
        
        export function remand(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.remand, command);
        }
        export function getAppInfoByAppID(appId): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.getAppInfoByAppID, appId);
        }
    }
}