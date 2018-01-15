module nts.uk.at.view.ksu001.c {

    export module service {
        let paths = {
            executionAlarmChecked: "at/schedule/shift/shiftCondition/shiftAlarm/execution"
        }
        export function executionAlarmChecked(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.executionAlarmChecked, command);
        }
        export function saveAsExcel(data): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "ShiftAlarm", domainType: "KSU001_alarm", languageId: "ja", reportType: 3, data: data });
        }
    }
}