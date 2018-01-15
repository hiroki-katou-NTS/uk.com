module nts.uk.at.view.kdw001.h {
    export module service {
        var paths = {
            getAllErrMessageInfoByEmpID: "at/record/message/getallbyempid",
            getListPersonInforLog : "at/record/personlog/getallbylistsid"
        }
        /**
         * get getAllErrMessageInfoByEmpID by  empCalAndSumExeLogId
         */
        export function getAllErrMessageInfoByEmpID(empCalAndSumExecLogID: any): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("at", paths.getAllErrMessageInfoByEmpID + "/" + empCalAndSumExecLogID);
        }
        export function saveAsCsv(data:any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "personError", domainType: "personerror", languageId: 'ja', reportType: 3 ,data:data});
        }
        /**
         * get all person infor by list sid 
         */
        export function getListPersonInforLog(listSid : Array<string>) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getListPersonInforLog,listSid);
        }
    }
}
