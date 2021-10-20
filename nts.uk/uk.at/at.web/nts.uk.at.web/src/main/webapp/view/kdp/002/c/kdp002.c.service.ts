module nts.uk.at.view.kdp002.c.service {
    let paths: any = {
        startScreen: "screen/at/personalengraving/startCScreen",
        registerDailyIdentify: "screen/at/personalengraving/registerDailyIdentify",
        getStampSetting: "at/record/stamp/management/getStampSetting",
        getStampPage: "at/record/stamp/management/getStampPage",
        deleteStampPage: "at/record/stamp/management/delete",
        getInfo: 'ctx/sys/auth/grant/rolesetperson/getempinfo/',
        NOTIFICATION_STAMP: 'at/record/stamp/notification_by_stamp',
        GET_SETTING: 'at/record/stamp/settingNoti'
    }

    export function registerDailyIdentify(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerDailyIdentify, command);
    }

    export function startScreen(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startScreen, data);
    }

    export function getStampSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampSetting);
    }
    
    export function deleteStampPage(command: any) {
       return nts.uk.request.ajax("at", paths.deleteStampPage, command);
    }

    export function getStampPage(pageNo : number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage + "/" + pageNo);
    }

    export function getEmpInfo(id: string) {
        return nts.uk.request.ajax("com", paths.getInfo + id);
    }

    export function getNotification(param: any) {
        return nts.uk.request.ajax("at", paths.NOTIFICATION_STAMP, param);
    }
    
    export function getNotificationSetting() {
        return nts.uk.request.ajax("at", paths.GET_SETTING);
    }
}
