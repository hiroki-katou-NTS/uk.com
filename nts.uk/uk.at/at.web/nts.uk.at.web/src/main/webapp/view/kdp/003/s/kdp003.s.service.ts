module kdp003.s.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getAllStampingResult: "at/record/workrecord/stamp/management/getAllStampingResult/{0}",

    }

    export function getAllStampingResult(sid: string): JQueryPromise<any> {
        let _path = nts.uk.text.format(paths.getAllStampingResult, sid);
        return nts.uk.request.ajax("at", _path);
    }

   
}