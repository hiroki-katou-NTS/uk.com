module nts.uk.com.view.kwr002.e {
    export module service {

        var path: any = {
            getAttndRecList: "at/function/attendancerecord/item/getAttndRecItem/",
            getCalculateAttndRecInfo: "at/function/attendancerecord/item/getCalculateAttndRecInfo",
        }

        export function findAttndRecByScreen(screenUseAtr: number): JQueryPromise<Array<model.AttendanceRecordItemDto>> {
            return nts.uk.request.ajax("at", path.getAttndRecList + screenUseAtr);
        }

        export function getCalculateAttndRecInfo(attendanceRecordKey: model.AttendanceRecordKeyDto): JQueryPromise<model.CalculateAttendanceRecordDto> {
            return nts.uk.request.ajax("at", path.getCalculateAttndRecInfo, attendanceRecordKey);
        }
    }

    export module model {

        export interface AttendanceRecordKeyDto {
            code: number;
            columnIndex: number;
            position: number;
            exportAtr: number;
        }

        export interface CalculateAttendanceRecordDto {
            name: string;
            addedItem: Array<model.AttendanceRecordItemDto>;
            subtractedItem: Array<model.AttendanceRecordItemDto>;
            attribute: number;
        }

        export class AttendanceRecordItemDto {
            attendanceItemId: string;
            attendanceItemName: string;
            screenUseItem: number;
            typeOfAttendanceItem: number;
        }

        export class GridItem {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class SelectedItem {
            action: string;
            code: string;
            name: string;
            constructor(action: string, code: string, name: string) {
                this.action = action;
                this.code = code;
                this.name = name;
            }
        }

        export class SelectionType {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export enum Action {
            ADDITION = nts.uk.resource.getText('KWR002_178'),
            SUBTRACTION = nts.uk.resource.getText('KWR002_179')
        }

    }
}