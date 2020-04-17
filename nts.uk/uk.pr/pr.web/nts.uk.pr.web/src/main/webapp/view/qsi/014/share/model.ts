module nts.uk.pr.view.qsi014.share.model {
    import getText = nts.uk.resource.getText;

    export enum OutputFormatClass {
        PEN_OFFICE = 0,
        HEAL_INSUR_ASSO = 1,
        THE_WELF_PEN = 2
    }

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export function getBusinessDivision(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_BusinessDivision_OUTPUT_COMPANY_NAME')),
            new ItemModel(1, getText('Enum_BusinessDivision_OUTPUT_SIC_INSURES')),
            new ItemModel(2, getText('Enum_BusinessDivision_DO_NOT_OUTPUT')),
            new ItemModel(3, getText('Enum_BusinessDivision_DO_NOT_OUTPUT_BUSINESS'))
        ];
    }
    export function getBussEsimateClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL')),
            new ItemModel(1, getText('Enum_BussEsimateClass_EMPEN_ESTAB_REARSIGN'))
        ];
    }

    export function getSocialInsurOutOrder(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_SocialInsurOutOrder_DIVISION_EMP_ORDER')),
            new ItemModel(1, getText('Enum_SocialInsurOutOrder_EMPLOYEE_CODE_ORDER')),
            new ItemModel(2, getText('Enum_SocialInsurOutOrder_EMPLOYEE_KANA_ORDER')),
            new ItemModel(3, getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_ORDER')),
            new ItemModel(4, getText('Enum_SocialInsurOutOrder_WELF_AREPEN_NUMBER_ORDER')),
            new ItemModel(5, getText('Enum_SocialInsurOutOrder_HEAL_INSUR_NUMBER_UNION_ORDER')),
            new ItemModel(6, getText('Enum_SocialInsurOutOrder_ORDER_BY_FUND')),
            new ItemModel(7, getText('Enum_SocialInsurOutOrder_INSURED_PER_NUMBER_ORDER'))
        ];
    }

    export function getPersonalNumClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_PersonalNumClass_OUTPUT_PER_NUMBER')),
            new ItemModel(1, getText('Enum_PersonalNumClass_OUTPUT_BASIC_PER_NUMBER')),
            new ItemModel(2, getText('Enum_PersonalNumClass_OUTPUT_BASIC_PEN_NOPER')),
            new ItemModel(3, getText('Enum_PersonalNumClass_DO_NOT_OUTPUT'))
        ];
    }

    export function getSubNameClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QSI014_A222_18')),
            new ItemModel(1, getText('QSI014_A222_19'))
        ];
    }


    export function getInsurPersonNumDivision(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_InsurPersonNumDivision_DO_NOT_OUPUT')),
            new ItemModel(1, getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_NUM')),
            new ItemModel(2, getText('Enum_InsurPersonNumDivision_OUTPUT_THE_WELF_PENNUMBER')),
            new ItemModel(3, getText('Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_UNION')),
            new ItemModel(4, getText('Enum_InsurPersonNumDivision_OUTPUT_THE_FUN_MEMBER'))
        ];
    }

    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

}
