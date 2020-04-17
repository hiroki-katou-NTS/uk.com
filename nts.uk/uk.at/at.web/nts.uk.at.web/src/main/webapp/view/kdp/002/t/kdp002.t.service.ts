module nts.uk.at.view.kdp002.t.service {
    let paths: any = {
        saveStampPage: "at/record/stamp/management/saveStampPage",
        getStampSetting: "at/record/stamp/management/getStampSetting",
        getStampPage: "at/record/stamp/management/getStampPage",
        deleteStampPage: "at/record/stamp/management/delete"
    }

    export function saveStampPage(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampPage, data);
    }

    export function getStampSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampSetting);
    }
    
    export function deleteStampPage(command: any) {
        return nts.uk.request.ajax("at", paths.deleteStampPage, command);
    }

    export function getStampPage(pageNo : number, buttonLayoutType : number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage + "/" + pageNo + "/" + buttonLayoutType);
    }
}
