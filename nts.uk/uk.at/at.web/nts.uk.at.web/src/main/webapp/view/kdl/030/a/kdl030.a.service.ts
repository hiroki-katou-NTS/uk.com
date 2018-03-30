module nts.uk.at.view.kdl030.a {
    export module service {
        var paths: any = {
            applicationForSendByAppID: "at/request/application/getApplicationForSendByAppID"
        }
        
        export function getApplicationForSendByAppID(appID): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.applicationForSendByAppID, appID);
        }
    }
}