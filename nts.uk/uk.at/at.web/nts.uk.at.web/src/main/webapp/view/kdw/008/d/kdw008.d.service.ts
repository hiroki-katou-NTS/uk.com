module nts.uk.at.view.kdw008.d {
    export module service {
        var paths: any = {
            getSettingUnit: "at/record/DailyRecordOperation/getSettingUnit",
            }
        export function getSettingUnit(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getSettingUnit);
        }
    }
}
