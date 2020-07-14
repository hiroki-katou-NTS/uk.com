module nts.uk.at.view.kdp010.g.service {
    let paths: any = {
        saveStampPage: "at/record/stamp/management/saveStampPage",
        saveStampPageCommunal: "at/record/stamp/timestampinputsetting/stampsetcommunal/stamppagelayout/save",
        getStampPage: "at/record/stamp/management/getStampPage",
        deleteStampPage: "at/record/stamp/management/delete"
    }

    export function saveStampPage(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampPage, data);
    }
    
    export function saveStampPageCommunal(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.saveStampPageCommunal, data);
    }
    
    export function deleteStampPage(command: any) {
        return nts.uk.request.ajax("at", paths.deleteStampPage, command);
    }

    export function getStampPage(param : any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage, param);
    }
}
