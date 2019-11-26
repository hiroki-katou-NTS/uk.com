module nts.uk.pr.view.qmm020.share.model {
    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export function convertMonthYearToString(yearMonth: any) {
        let year: string, month: string;
        yearMonth = yearMonth.toString();
        year = yearMonth.slice(0, 4);
        month = yearMonth.slice(4, 6);
        return year + "/" + month;
    }

    export class StateLinkSettingMaster {
        id: string;
        hisId: string;
        masterCode: string;
        categoryName: string;
        salaryCode: string;
        bonusCode: string;
        bonusName:string;
        salaryName: string;
        displayE3_4: string;
        displayE3_5: string;
        constructor(masterCode: string, categoryName: string) {
            this.masterCode = masterCode;
            this.categoryName = categoryName;
        }

    }

    export function displayCodeAndName(code: string, name: string){
        let display : string;
        name = _.escape(name);
        display = "";
        if(code != null)
            display = display + code.toString();
        if(name != null) {
            display = display + "    " + name;
        }
        return display;
    }

    export function convertToDisplay(item){
        let listStateLinkSettingMaster = [];
        _.each(item, (item,key) => {
            let dto: StateLinkSettingMaster = new StateLinkSettingMaster("", "");
            dto.hisId = item.hisId;
            dto.id = key;
            dto.masterCode = item.masterCode;
            dto.categoryName = _.escape(item.categoryName);
            dto.salaryCode = item.salaryCode;
            dto.bonusCode = item.bonusCode;
            dto.bonusName = item.bonusName;
            dto.salaryName = item.salaryName;
            dto.displayE3_4 = displayCodeAndName(item.salaryCode, item.salaryName);
            dto.displayE3_5 = displayCodeAndName(item.bonusCode, item.bonusName);
            listStateLinkSettingMaster.push(dto);
        });
        return _.orderBy(listStateLinkSettingMaster, ['masterCode'],['asc']);
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        NO_REGIS = 2,
        NO_EXIST = 3
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
        EMPLOYEE = 0,
        DEPARMENT = 1,
        CLASSIFICATION = 2,
        POSITION = 3,
        SALARY = 4,
        COMPANY = 5,
        INDIVIDUAL = 6
}
    export enum PARAMETERS_SCREEN_M {
        /* When another screen open*/
        INPUT = "PARAM_INPUT_SCREEN_M",
        /* When screen D, F open */
        OUTPUT = "PARAM_OUTPUT_SCREEN_M"
    }

    export enum PARAMETERS_SCREEN_L {

        INPUT = "PARAM_INPUT_SCREEN_L",

        OUTPUT = "PARAM_OUTPUT_SCREEN_L"
    }
    export enum PARAMETERS_SCREEN_I {

        INPUT = "PARAM_INPUT_SCREEN_I",

        OUTPUT = "PARAM_OUTPUT_SCREEN_I"
    }
}