module nts.uk.at.view.kdw001.f {
    export module service {
        var paths = {
            getAllEmpCalAndSumExeLog : "at/record/log/getallbydate",
            getCaseSpecExeContentById : "at/record/case/getcasebyid"
        }
        
        /**
         * get all EmpCalAndSumExeLog by startDate and endDate
         */
        export function getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllEmpCalAndSumExeLog,inputEmpCalAndSumByDate);
        }
    
        /**
         * get CaseSpecExeContent By Id
         */
        export function getCaseSpecExeContentById(caseSpecExeContentID: string ) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getCaseSpecExeContentById+"/"+caseSpecExeContentID);
        }
    
    
    }//end module service
}//end module
