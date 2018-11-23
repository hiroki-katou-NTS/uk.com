module nts.uk.pr.view.qmm041.share.model {

    export interface ISalPerUnitPriceName {
        code: string,
        name: string
    }


    export class SalPerUnitPriceName {
        code: string;
        name: string;

        constructor(param: ISalPerUnitPriceName) {
            if (param) {
                this.code = param.code;
                this.name = param.name;
            }
        }
    }

    export class EnumModel {
        value: number;
        name: string;

        constructor(value, name) {
            this.value = value;
            this.name = name;
        }
    }

    export enum HISTORY_STATUS {
        NO_HISTORY = 0,
        WITH_HISTORY = 1
    }

    export enum INHERITANCE {
        NO = 0,
        YES = 1
    }

    export enum MODIFY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum MODE {
        NORMAL = 0,
        HISTORY_UNREGISTERED = 1,
        ADD_HISTORY = 2
    }
}