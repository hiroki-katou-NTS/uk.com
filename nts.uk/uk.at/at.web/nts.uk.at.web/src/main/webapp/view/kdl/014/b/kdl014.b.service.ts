module kdl014.b.service {
    var paths = {
        getListStampDetail : "at/record/stamp/getListStampDetail"
    }
        
    export function getListStampDetail(stampParam: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getListStampDetail, stampParam);
    }
}