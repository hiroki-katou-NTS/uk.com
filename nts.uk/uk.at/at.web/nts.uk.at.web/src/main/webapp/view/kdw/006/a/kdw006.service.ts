module nts.uk.at.view.kdw006 {
    export module service {
        export class Service {
        }
        //Export common excel
        export function saveAsExcelCommon(languageId: String): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KDW006";
                if (program.length > 1){
                   program.shift();
                   domainType = domainType + program.join(" ");
                }
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "OperationSetting", domainType: domainType, languageId: languageId, reportType: 0 });
        }
    }
}
 