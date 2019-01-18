module nts.uk.at.view.kmf004.x.service {
     var paths: any = {
        
    }
    
    export function saveAsExcel(languageId: string): JQueryPromise<any> {
        let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
        let domainType = program[1] != null ? "KMF004" + program[1] : "";
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "specialholiday", domainType: domainType, languageId: languageId, reportType: 0});
    }
}