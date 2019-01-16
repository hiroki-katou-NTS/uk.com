module nts.uk.at.view.kmf004.x.service {
     var paths: any = {
        
    }
    
    export function saveAsExcel(languageId: string): JQueryPromise<any> {
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "specialholiday", domainType: "KMF004特別休暇情報の登録", languageId: languageId, reportType: 0});
    }
}