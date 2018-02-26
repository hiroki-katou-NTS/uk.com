module nts.uk.at.view.kal001.b {
    export module service {
        var paths = {
            getAllEmpCalAndSumExeLog : "at/record/log/getallbydate"
        }
        
        /**
         * get all EmpCalAndSumExeLog by startDate and endDate
         */
        export function getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllEmpCalAndSumExeLog,inputEmpCalAndSumByDate);
        }
        /**
         * save file csv
         */
        export function saveAsCsv(data:any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "alarmList", domainType: "alarmlist", languageId: 'ja', reportType: 3 ,data:data});
        }
        /**
         * save file excel
         */
        export function saveAsExcel(data:any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "alarmList", domainType: "alarmlist", languageId: 'ja', reportType: 0 ,data:data});
        }
    
    
    }//end module service
}//end module
