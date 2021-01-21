module nts.uk.at.view.kaf000.b.service {
    var paths = {
        getAllDataByAppID: "at/request/application/getalldatabyappid",
        getAllReasonByAppID: "at/request/application/getreasonforremand",
        getDetailCheck: "at/request/application/getdetailcheck",
        getMessageDeadline: "at/request/application/getmessagedeadline",
        approveApp: "at/request/application/approveapp",
        denyApp: "at/request/application/denyapp",
        releaseApp: "at/request/application/releaseapp",
        cancelApp: "at/request/application/cancelapp",
        deleteApp: "at/request/application/deleteapp",
        getAppDataDate: "at/request/application/getAppDataByDate",
        getAppByID: "at/request/application/getAppInfoByAppID",
        getAppByListID: "at/request/application/getAppInfoByListAppID",
        holidayShipmentRemove: "at/request/application/holidayshipment/remove",
        holidayShipmentCancel: "at/request/application/holidayshipment/cancel",
        holidayShipmentApprove: "at/request/application/holidayshipment/approve",
        holidayShipmentDeny: "at/request/application/holidayshipment/deny",
        holidayShipmentRelease: "at/request/application/holidayshipment/release",
        reflectAppSingle: "at/request/application/reflect-app",
        writeLog: "at/request/application/write-log"
    }

    export function getAppDataDate(command): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAppDataDate, command);
    }

    /**
     * approve application
     */
    export function approveApp(command, appType): JQueryPromise<any> {
        let approveUrl = appType != 10 ? paths.approveApp : paths.holidayShipmentApprove;
        return nts.uk.request.ajax("at", approveUrl, command);
    }

    /**
     * deny  application
     */
    export function denyApp(command, appType): JQueryPromise<any> {
        let denyUrl = appType != 10 ? paths.denyApp : paths.holidayShipmentDeny;
        return nts.uk.request.ajax("at", denyUrl, command);
    }

    /**
    * release   application
    */
    export function releaseApp(command, appType): JQueryPromise<any> {
        let releaseUrl = appType != 10 ? paths.releaseApp : paths.holidayShipmentRelease;
        return nts.uk.request.ajax("at", releaseUrl, command);
    }

    /**
    * cancel application
    */
    export function cancelApp(command, appType): JQueryPromise<any> {

        let cancelUrl = appType != 10 ? paths.cancelApp : paths.holidayShipmentCancel;
        return nts.uk.request.ajax("at", cancelUrl, command);
    }

    /**
     * delete application
     */
    export function deleteApp(command, appType): JQueryPromise<any> {

        let deleteUrl = appType != 10 ? paths.deleteApp : paths.holidayShipmentRemove;
        return nts.uk.request.ajax("at", deleteUrl, command);
    }


    /**
     * get all phase by applicationID
     */
    export function getAllDataByAppID(appID): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAllDataByAppID + "/" + appID);
    }
    /**
     * get all reason by applicationID
     */
    export function getAllReasonByAppID(appID): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax("at", paths.getAllReasonByAppID + "/" + appID);
    }

    /**
     * get detail check
     */
    export function getDetailCheck(inputGetDetailCheck): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDetailCheck, inputGetDetailCheck);
    }
    /**
    * get getMessageDeadline
    */
    export function getMessageDeadline(applicationMeta: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getMessageDeadline, applicationMeta);
    }

    export function getAppByID(appID: string): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAppByID, appID);
    }
    
    export function getAppByListID(listAppID: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getAppByListID, listAppID);
    }
    
    export function reflectAppSingle(appID: Array<string>): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.reflectAppSingle, appID);
    }
    //write log
    export function writeLog(paramLog: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.writeLog, paramLog);
    }
}