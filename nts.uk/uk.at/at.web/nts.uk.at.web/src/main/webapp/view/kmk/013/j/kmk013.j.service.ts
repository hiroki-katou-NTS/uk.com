module nts.uk.at.view.kmk013.j {
    export module service {
        let paths: any = {
            loadAllSetting : "record/monthly/vtotalmethod/read",
        }
        export function loadAllSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.loadAllSetting);
        }
    }
}