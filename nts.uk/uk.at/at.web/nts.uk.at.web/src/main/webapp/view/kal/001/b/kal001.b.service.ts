module nts.uk.at.view.kal001.b {
    export module service {
        var paths = {
            getExtractAlarmData : "",
            exportAlarmData: "at/function/alarm/kal/001/export-alarm-data"
        }
        
        /**
         * 
         */
        export function getExtractAlarmData(query: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getExtractAlarmData, query);
        }
 
        /**
         * save file excel
         */
        export function exportAlarmData(param): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportAlarmData, param);
        };
    }
}
