module nts.uk.at.view.kmk002.c {
    export module service {

        /**
        *  Service paths
        */
        let paths: any = {
            findDailyAttdItem: "at/record/businesstype/attendanceItem/daily/findbyanyitem",
            findMonthlyAttdItem: "at/record/attendanceitem/monthly/findbyanyitem"
        }

        /**
         * call service to get list daily attendance item.
         */
        export function findDailyAttdItem(request: model.AttdItemLinkRequest): JQueryPromise<model.AttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findDailyAttdItem, request);
        }

        /**
         * call service to get list monthly attendance item.
         */
        export function findMonthlyAttdItem(request: model.AttdItemLinkRequest): JQueryPromise<model.AttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findMonthlyAttdItem, request);
        }

        /**
         * Data Model
         */
        export module model {
            export interface AttendanceItemDto {
                attendanceItemId: number;
                attendanceItemName: string;
                attendanceItemDisplayNumber?: number;
            }
            export interface AttdItemLinkRequest {
                anyItemNos: number[];
                formulaAtr: number;
                performanceAtr: number;
            }
        }
    }
}