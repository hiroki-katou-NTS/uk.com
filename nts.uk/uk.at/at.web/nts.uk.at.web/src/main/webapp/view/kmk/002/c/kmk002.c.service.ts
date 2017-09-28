module nts.uk.at.view.kmk002.c {
    export module service {
        var paths: any = {
            findAllWorkType: "at/share/worktype/findNotDeprecated",
            findWorkTypeByCodes: "at/share/worktype/findNotDeprecatedByListCode",
            findAllWorkTime: "at/shared/worktime/findAll",
            findWorkTimeByCodes: "at/shared/worktime/findByCodes",
            findByTime: "at/shared/worktime/findByTime",
            isWorkTimeSettingNeeded: "at/schedule/basicschedule/isWorkTimeSettingNeeded",
            checkPairWorkTypeWorkTime: "at/schedule/basicschedule/checkPairWorkTypeWorkTime",
            findAllDailyAttendanceItem: "at/record/businesstype/attendanceItem/getAttendanceItems"
        }

        /**
         * Find all work type.
         */
        export function findAllWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkType);
        }

        /**
         * Find work type by list codes.
         */
        export function findWorkTypeByCodes(command: Array<string>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTypeByCodes, command);
        }

        /**
         * Find work time by list codes.
         */
        export function findWorkTimeByCodes(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTimeByCodes, command);
        }

        /**
         * Find all work time.
         */
        export function findAllWorkTime(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkTime);
        }

        /**
         * Search work time.
         */
        export function findByTime(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }

        /**
         * Check is work time setting needed
         */
        export function isWorkTimeSettingNeeded(workTypeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.isWorkTimeSettingNeeded + '/' + workTypeCode);
        }

        /**
         * Check pair work type & work time.
         */
        export function checkPairWorkTypeWorkTime(workTypeCode: string, workTimeCode): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.checkPairWorkTypeWorkTime + '/' + workTypeCode + '/' + workTimeCode);
        }
     
        /**
         * call service find all daily attendance item
         */
        export function findAllDailyAttendanceItem(): JQueryPromise<model.DailyAttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findAllDailyAttendanceItem);
        }
        
        export module model {
            export interface DailyAttendanceItemDto {
                attendanceItemId: number;
                attendanceItemName: string;
            }
            
            export interface SelectDailyAttendanceItemDto {
                operator: number;
                operatorText:  string;
                attendanceItemId: number;
                attendanceItemName: string;
            }
        }
    }
}