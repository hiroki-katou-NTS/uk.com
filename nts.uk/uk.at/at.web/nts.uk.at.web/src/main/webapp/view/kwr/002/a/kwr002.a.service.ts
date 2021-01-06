module nts.uk.com.view.kwr002.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getAllAttendanceRecExpSet: "com/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
            getPermission: "com/function/attendancerecord/export/setting/getPermission",
            exportService: "at/function/attendancerecord/report/export",
            getClosureMonth: "com/function/attendancerecord/export/setting/getClosureMonth",
            getAuthorityOfWorkPerformance: "com/function/attendancerecord/export/setting/getAuthorityOfWorkPerformance"
        };

        export function getAllAttendanceRecExpSet(): JQueryPromise<a.viewModel.AttendanceRecordExportSettingWrapperDto> {
            return nts.uk.request.ajax("at", path.getAllAttendanceRecExpSet);
        }

        export function getPermission(): JQueryPromise<boolean> {
            return nts.uk.request.ajax("at", path.getPermission);
        }

        export function exportService(data: a.viewModel.ExportDto): JQueryPromise<a.viewModel.ExportDto> {
            return nts.uk.request.exportFile(path.exportService, data);
        }

        export function getClosureMonth() : JQueryPromise<any>{
            return nts.uk.request.ajax("at", path.getClosureMonth);
        }
        export function getAuthorityOfWorkPerformance() : JQueryPromise<any>{
            return nts.uk.request.ajax("at", path.getAuthorityOfWorkPerformance);
        }

        export function saveCharacteristic(companyId: string, userId: string, obj: any): void {
            nts.uk.characteristics.save("AttendanceRecordOutputConditions"
                                    + "_companyId_" + companyId
                                    + "_userId_" + userId, obj);
        }

        export function restoreCharacteristic(companyId: string, userId: string): JQueryPromise<any> {
            return nts.uk.characteristics.restore("AttendanceRecordOutputConditions"
                                    + "_companyId_" + companyId
                                    + "_userId_" + userId);
        }
    }
}