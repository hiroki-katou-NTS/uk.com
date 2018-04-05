module nts.uk.at.view.kmk013.j {
    export module service {
        let paths: any = {
            loadAllSetting: "record/monthly/vtotalmethod/read",
            registerVertical: "record/monthly/vtotalmethod/registerVertical",
            registerPayItem: "record/monthly/vtotalmethod/registerPayItem",
            findPayItem: "record/monthly/vtotalmethod/readPayItem",
            findWorkTypeDisp: "at/screen/worktype/findAllDisp",
            findWorkTypeName: "record/monthly/vtotalmethod/getWorkType"
        }
        export function loadAllSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.loadAllSetting);
        }
        export function registerPayItem(setting: any) {
            return nts.uk.request.ajax("at", paths.registerPayItem, setting);
        }
        export function registerVertical(setting: any) {
            return nts.uk.request.ajax("at", paths.registerVertical, setting);
        }

        export function findWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findWorkTypeDisp);
        }
        export function findPayItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findPayItem);
        }
        export function findWorkTypeName(workType: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTypeName + "/" + workType);
        }
    }
}