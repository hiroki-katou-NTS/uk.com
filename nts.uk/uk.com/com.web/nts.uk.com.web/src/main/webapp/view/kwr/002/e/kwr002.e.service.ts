module nts.uk.com.view.kwr002.e {
    export module service {
        
        var path: any = {
            getAttndRecList: "at/function/attendancerecord/item/getAttndRecItem/",
        }
        
        export function findAttndRecByScreen(screenUseAtr: number): JQueryPromise<Array<model.AttendanceRecordItemDto>> {
            return nts.uk.request.ajax("at", path.getAttndRecList + screenUseAtr);
        }
    }
    
    export module model {
        
        export class AttendanceRecordItemDto {
            attendanceItemId: string;
            attendanceItemName: string;
            screenUseItem: number;
        }
        
        export class GridItem {
            code: string;
            name: string;
            
            constructor(code: string, name: string){
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