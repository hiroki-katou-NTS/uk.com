module nts.uk.at.view.kal003.a.daily.dailyperformance {
    export module service {
        var paths = {
            getAllFixedConditionWorkRecord : "at/record/erroralarm/getallfixedconwr",
            getFixedConWRByCode :  "at/record/erroralarm/getallfixedconwrbycode",
            getAllWorkRecordExtraCon :  "at/record/condition/workRecordextracon/getallworkRecordextracon"
        }
        
        /**
         * get all  
         */
        export function getAllFixedConditionWorkRecord() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllFixedConditionWorkRecord);
        }
        
        /**
         * get all  by startDate and endDate
         */
        export function getFixedConWRByCode(errorAlarmCheckID : string ) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getFixedConWRByCode+"/"+errorAlarmCheckID);
        }
         
         /**
         * get all FixedConWRByCode
         */
        export function getAllWorkRecordExtraCon() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllWorkRecordExtraCon);
        }
    }//end module service
}//end module
