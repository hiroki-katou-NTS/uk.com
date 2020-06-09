module nts.uk.at.view.kdp002.b.service {
    let paths: any = {
        saveStampPage: "at/record/stamp/management/saveStampPage",
        getStampSetting: "at/record/stamp/management/getStampSetting",
        getStampPage: "at/record/stamp/management/getStampPage",
        deleteStampPage: "at/record/stamp/management/delete",
        getAllStampingResult: "at/record/workrecord/stamp/management/getAllStampingResult/{0}",
        getInfo: 'ctx/sys/auth/grant/rolesetperson/getempinfo/'
        
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

    export function getStampPage(pageNo : number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampPage + "/" + pageNo);
    }
    
    export function getAllStampingResult(sid: string): JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllStampingResult, sid);
        return nts.uk.request.ajax("at", _path);
    }
    
    export function getEmpInfo(id: string) {
        return nts.uk.request.ajax("com", paths.getInfo+id); 
    }
}
