module nts.uk.at.view.kdw006 {
    export module service {
        export class Service {
        }
        //Export common excel
        export function saveAsExcelCommon(languageId: String): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1]!=null?program[1]:"";
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "OperationSetting", domainType: "KDW006" + programName, languageId: languageId, reportType: 0 });
        }
    }
}
