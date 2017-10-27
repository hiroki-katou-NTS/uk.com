module nts.uk.at.view.kmk002.c {
    export module service {
        var paths: any = {
            findByAnyItem: "at/record/businesstype/attendanceItem/linking"
        }

        /**
         * call service to get list attendance item
         */
        export function findByAnyItem(request: model.AttdItemLinkRequest): JQueryPromise<model.DailyAttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findByAnyItem, request);
        }

        export module model {
            export interface DailyAttendanceItemDto {
                attendanceItemId: number;
                attendanceItemName: string;
            }
            export interface AttdItemLinkRequest {
                anyItemNos: string[];
                formulaAtr: number;
                performanceAtr: number;
            }
        }
    }
}