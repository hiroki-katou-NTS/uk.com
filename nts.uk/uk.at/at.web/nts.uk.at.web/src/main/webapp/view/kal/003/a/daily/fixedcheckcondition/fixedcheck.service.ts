module nts.uk.at.view.kal003.a.daily.fixedcheckcondition {
    export module service {
        var paths = {
            getAllFixedConditionWorkRecord : "at/record/erroralarm/getallfixedconwr",
            getFixedConWRByCode :  "at/record/erroralarm/getallfixedconwrbycode",
            getAllWorkRecordExtraCon :  "at/record/condition/workRecordextracon/getallworkRecordextracon",
            getAllFixedConData : "at/record/erroralarm/fixeddata/getallfixedcondata"
        }
        
         /**
         * get all  AllFixedConditionWorkRecord
         */
        export function getAllFixedConData() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllFixedConData);
        }
        
        /**
         * get all  AllFixedConditionWorkRecord
         */
        export function getAllFixedConditionWorkRecord() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllFixedConditionWorkRecord);
        }
        
        /**
         * getFixedConWRByCode\
         * 
         */
        export function getFixedConWRByCode(errorAlarmCheckID : string ) : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getFixedConWRByCode+"/"+errorAlarmCheckID);
        }
         
         /**
         * get all FixedConWRByCode
         */
        export function  getAllWorkRecordExtraCon() : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getAllWorkRecordExtraCon);
        }
    }//end module service
}//end module
