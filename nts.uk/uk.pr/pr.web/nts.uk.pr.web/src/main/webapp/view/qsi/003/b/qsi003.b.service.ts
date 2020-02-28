module nts.uk.pr.view.qsi003.b.service {
    import ajax = nts.uk.request.ajax;

    let paths : any = {
        getReasonRomajiName : "ctx/pr/file/reason/romaji/getReasonRomajiName/{0}",
        updateReasonRomajiName : "ctx/pr/file/reason/romaji/updateReasonRomajiName"
    };

    export function getReasonRomajiName(empId: any): JQueryPromise<any>{
        let _path  =  nts.uk.text.format(paths.getReasonRomajiName, empId);
        return ajax("pr", _path);
    }

    export function updateReasonRomajiName(reasonRomajiNameCommand): JQueryPromise<any>{
        return ajax(paths.updateReasonRomajiName, reasonRomajiNameCommand);
    }
}