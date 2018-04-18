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
        cid: string;
        cd: number;
        dispSettAgr36: number;
        name: string;
        outNumExceedTime36Agr: number;
        displayFormat: number;
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