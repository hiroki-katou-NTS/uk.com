module nts.uk.at.view.kwr008.share.model {
    export enum SCREEN_MODE{
        NEW = 0,
        UPDATE = 1    
    }
    
    export interface EnumConstantDto {
        value: number;
        fieldName: string;
        localizedName: string;
    }

    export interface OutputSettingCodeDto {
        cd: string;
        name: string;
        outNumExceedTime36Agr: boolean;
        displayFormat: number;
    }
    
    export class setOutputSettingCode implements OutputSettingCodeDto{
        cd: string;
        name: string;
        outNumExceedTime36Agr: boolean;
        displayFormat: number;
        
        constructor(cd: string, name: string, outNumExceedTime36Agr: boolean, displayFormat: number){
            this.cd = cd;
            this.name = name;
            this.outNumExceedTime36Agr = outNumExceedTime36Agr;
            this.displayFormat = displayFormat;
        }
    }

    export class ItemModel {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}