module nts.uk.at.view.kdp010.h.service {
    let paths: any = {
        saveStampPage: "at/record/stamp/management/saveStampPage",
        getStampSetting: "at/record/stamp/management/getStampSetting",
        getStampPage: "at/record/stamp/management/getStampPage"
    }

    export function saveStampPage(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampPage, data);
    }

    export function getStampSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampSetting);
    }

    export function getStampPage(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage);
    }
}
