module nts.uk.at.view.kal001.b {
    export module service {
        var paths = {
            getExtractAlarmData : ""
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
        export function saveAsExcel(data: Array<model.ValueExtractAlarmDto>): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "alarmList", domainType: "AlarmList", languageId: 'ja', reportType: 0 ,data:data});
            
        }
    
    }
}
