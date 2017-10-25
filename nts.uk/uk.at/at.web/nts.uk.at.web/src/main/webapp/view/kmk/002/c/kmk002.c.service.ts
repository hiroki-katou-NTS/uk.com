module nts.uk.at.view.kmk002.c {
    export module service {
        var paths: any = {
            findAllDailyAttendanceItem: "at/record/businesstype/attendanceItem/getAttendanceItems"
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
        }
    }
}