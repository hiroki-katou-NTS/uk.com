module nts.uk.at.view.kdp010.d.service {
    let paths: any = {
        getData: "at/record/stamp/timestampinputsetting/portalstampsettings/get",
        save: "at/record/stamp/timestampinputsetting/portalstampsettings/save"
    }

    export function save(param: any): void {
        return nts.uk.request.ajax("at", paths.save, param);
    }

    export function getData(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getData);
    }
}
