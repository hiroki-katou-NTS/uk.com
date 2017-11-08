module nts.uk.at.view.ksu001.c {

    export module service {
        let paths = {
            executionAlarmChecked: "at/schedule/shift/shiftCondition/shiftAlarm/execution"
        }
        export function executionAlarmChecked(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.executionAlarmChecked, command);
        }
        export function saveAsExcel(data): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "ShiftAlarm", domainType: "エラー出力", languageId: "ja", reportType: 0, data: data });
        }
    }
}