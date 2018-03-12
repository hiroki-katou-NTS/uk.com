module nts.uk.at.view.kal001.c {
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
    
    
    }//end module service
}//end module
