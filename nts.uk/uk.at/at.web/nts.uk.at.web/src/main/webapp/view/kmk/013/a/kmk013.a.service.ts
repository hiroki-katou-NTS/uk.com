module nts.uk.at.view.kmk013.a {
    export module service {
        let paths: any = {
            getDomainSet: "shared/selection/func/loadAllSetting"
        }
        export function getDomainSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDomainSet);
        }
        export function saveAsExcel(): JQueryPromise<any> {
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "CalculationSetting", domainType: "KMK013"+__viewContext.program.programName,languageId: 'ja', reportType: 0});
    }
}