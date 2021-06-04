module nts.uk.at.view.kal001.b {
    export module service {
        var paths = {
            getExtractAlarmWebMenu: "at/function/alarm/kal/001/alarmlist/webmenu",
            exportAlarmData: "at/function/alarm/kal/001/export-alarm-data/"
        }
 
        /**
         * save file excel
         */
        export function exportAlarmData(processId, alarmCode): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportAlarmData + processId + '/' + alarmCode);
        };

        export function getExtractAlarmWebMenuData(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getExtractAlarmWebMenu, data);
        }
    }
}
