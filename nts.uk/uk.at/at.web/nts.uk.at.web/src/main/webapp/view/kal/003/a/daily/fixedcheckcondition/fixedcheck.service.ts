module nts.uk.at.view.kal003.a.daily.fixedcheckcondition {
    export module service {
        var paths = {
            getAllFixedConditionWorkRecord : "at/record/erroralarm/getallfixedconwr",
            getAllFixedConWRByAlarmID : "at/record/erroralarm/getallfixedconwrbyalarmid",
            getFixedConWRByCode :  "at/record/erroralarm/getallfixedconwrbycode",
            getAllWorkRecordExtraCon :  "at/record/condition/workRecordextracon/getallworkRecordextracon"
        }
        
        /**
         * get all CaseSpecExeContent 
         */
        export function getAllFixedConditionWorkRecord() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllFixedConditionWorkRecord);
        }
        
        /**
         * get CaseSpecExeContent By Id
         */
        export function getAllFixedConWRByAlarmID(errorAlarmCode: string ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllFixedConWRByAlarmID+"/"+errorAlarmCode);
        }
        
        /**
         * get all EmpCalAndSumExeLog by startDate and endDate
         */
        export function getFixedConWRByCode(inputParamGetFixedCon : any ) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getFixedConWRByCode,inputParamGetFixedCon);
        }
         
         /**
         * get all FixedConWRByCode
         */
        export function getAllWorkRecordExtraCon() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllWorkRecordExtraCon);
        }
    }//end module service
}//end module
