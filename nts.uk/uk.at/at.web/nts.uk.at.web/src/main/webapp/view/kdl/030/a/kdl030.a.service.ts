module nts.uk.at.view.kdl030.a {
    export module service {
        var paths: any = {
            applicationForSendByAppID: "at/request/application/getApplicationForSendByAppID",
            sendMail: "at/request/mail/send"
        }
        
        export function getApplicationForSendByAppID(appID): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.applicationForSendByAppID, appID);
        }
        
        export function sendMail(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.sendMail, command);
        } 
    }
    
}