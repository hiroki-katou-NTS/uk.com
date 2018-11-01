module nts.uk.pr.view.qmm020.share.model {
    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        NO_REGIS = 2
    }

    export enum TRANSFER_MOTHOD {
        CREATE_NEW = 1,
        TRANSFER = 0
    }

    export enum PARAMETERS_SCREEN_J {
        /* When another screen open*/
        INPUT = "PARAM_INPUT_SCREENJ_QMM020_QWE!@#$",
        /* When screen D, F open */
        OUTPUT = "PARAM_OUTPUT_SCREENJ_QMM020_QWE!@#$"
    }

    export enum PARAMETERS_SCREEN_K {
        /* When another screen open*/
        INPUT = "PARAM_INPUT_SCREEN_K_QMM020_QWE!@#$$%^",
        /* When screen D, F open */
        OUTPUT = "PARAM_OUTPUT_SCREEN_K_QMM020_QWE!@#$$%^"
    }

    export enum MODE_SCREEN {
        COMPANY = 0,
        EMPLOYEE = 1,
        DEPARMENT = 2,
        CLASSIFICATION = 3,
        POSITION = 4,
        SALARY = 5,
        INDIVIDUAL = 6


    }
}