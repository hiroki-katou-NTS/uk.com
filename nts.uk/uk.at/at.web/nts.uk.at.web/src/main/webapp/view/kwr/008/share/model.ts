module nts.uk.at.view.kwr008.share.model {
    export enum SCREEN_MODE {
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
        outNumExceedTime36Agr: number;
        displayFormat: number;
    }

    export class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}