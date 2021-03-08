module nts.uk.at.view.kdp010.b.service {
    let paths: any = {
        saveStampSetting: "at/record/stamp/management/saveStampSetting",
        updateStampSetting: "at/record/stamp/management/updateStampSetting",
        getStampSetting: "at/record/stamp/management/getStampSetting",
        getStampPage: "at/record/stamp/management/getStampPageByCid"
    }

    export function saveStampSetting(stampSettingPerson: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampSetting, stampSettingPerson);
    }

    export function updateStampSetting(stampSettingPerson: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.updateStampSetting, stampSettingPerson);
    }

    export function getStampSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampSetting);
    }

    export function getStampPage(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage);
    }
}
