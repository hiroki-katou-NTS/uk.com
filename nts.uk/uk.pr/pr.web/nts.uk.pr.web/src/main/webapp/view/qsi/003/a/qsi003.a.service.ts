module nts.uk.pr.view.qsi003.a.service {
    import ajax = nts.uk.request.ajax;
    import exportFile = nts.uk.request.exportFile;

    let paths : any = {
        getRomajiNameNotiCreSettingById : "ctx/pr/file/printconfig/romaji/getRomajiNameNoti",
        exportData: "ctx/pr/file/printconfig/romaji/exportData"
    };

    export function getRomajiNameNotiCreSettingById(): JQueryPromise<any>{
        return ajax(paths.getRomajiNameNotiCreSettingById);
    }

    export function exportData(romajiNameNotiCreSetCommand: any): JQueryPromise<any>{
        return exportFile(paths.exportData, romajiNameNotiCreSetCommand);
    }
}