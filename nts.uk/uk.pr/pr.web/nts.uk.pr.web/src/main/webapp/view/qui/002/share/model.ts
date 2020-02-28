module nts.uk.pr.view.qsi013.share.model {
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

    export function getSubmitNameAtr(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QUI002_A222_18')),
            new ItemModel(1, getText('QUI002_A222_19'))
        ];
    }
    export function getOfficeClsAtr(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
            new ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE')),
            new ItemModel(2, getText('Enum_OfficeCls_DO_NOT_OUTPUT'))
        ];
    }

    export function getEmpInsOutOrder(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_EmpInsOutOrder_INSURANCE_NUMBER')),
            new ItemModel(1, getText('Enum_EmpInsOutOrder_DEPARTMENT_EMPLOYEE')),
            new ItemModel(2, getText('Enum_EmpInsOutOrder_EMPLOYEE_CODE')),
            new ItemModel(3, getText('Enum_EmpInsOutOrder_EMPLOYEE'))
        ];
    }

    export function getSubNameClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QSI013_18')),
            new ItemModel(1, getText('QSI013_19'))
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

    export function getTextPerNumberClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_TextPerNumberClass_OUTPUT_NUMBER')),
            new ItemModel(1, getText('Enum_TextPerNumberClass_OUPUT_BASIC_PEN_NUMBER')),
            new ItemModel(2, getText('Enum_TextPerNumberClass_OUTPUT_BASIC_NO_PERSONAL'))
        ];
    }

    export function getOutputFormatClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_OutputFormatClass_PEN_OFFICE')),
            new ItemModel(1, getText('Enum_OutputFormatClass_HEAL_INSUR_ASSO')),
            new ItemModel(2, getText('Enum_OutputFormatClass_OUTPUT_THE_WELF_PEN'))
        ];
    }

    export function getLineFeedCode(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_LineFeedCode_ADD')),
            new ItemModel(1, getText('Enum_LineFeedCode_DO_NOT_ADD')),
            new ItemModel(2, getText('Enum_LineFeedCode_E_GOV'))
        ]
    }
    export enum ReasonsForLossHealthyIns {
        BLANK = 0,
        RETIREMENT = 4,
        DEATH = 5,
        ONLY_HEALTHY_INSURANCE = 7,
        DISABILITY_AUTHORIZATION = 9
    }

    export enum ReasonsForLossPensionIns {
        BLANK = 0,
        RETIREMENT = 4,
        DEATH = 5,
        ONLY_HEALTHY_INSURANCE = 6
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

    //for combo box
    export function getCauseTypeHealthLossInfo(): Array<ItemModel> {
        return [
            new ItemModel(ReasonsForLossHealthyIns.BLANK, getText('Enum_ReasonsForLossHealthyIns_BLANK')),
            new ItemModel(ReasonsForLossHealthyIns.RETIREMENT, getText('Enum_ReasonsForLossHealthyIns_RETIREMENT')),
            new ItemModel(ReasonsForLossHealthyIns.DEATH, getText('Enum_ReasonsForLossHealthyIns_DEATH')),
            new ItemModel(ReasonsForLossHealthyIns.ONLY_HEALTHY_INSURANCE, getText('Enum_ReasonsForLossHealthyIns_ONLY_HEALTHY_INSURANCE')),
            new ItemModel(ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION, getText('Enum_ReasonsForLossHealthyIns_DISABILITY_AUTHORIZATION'))
        ];
    }

    export function getCauseTypePensionLossInfo(): Array<ItemModel> {
        return [
            new ItemModel(ReasonsForLossPensionIns.BLANK, getText('Enum_ReasonsForLossPensionIns_BLANK')),
            new ItemModel(ReasonsForLossPensionIns.RETIREMENT, getText('Enum_ReasonsForLossPensionIns_RETIREMENT')),
            new ItemModel(ReasonsForLossPensionIns.DEATH, getText('Enum_ReasonsForLossPensionIns_DEATH')),
            new ItemModel(ReasonsForLossPensionIns.ONLY_HEALTHY_INSURANCE, getText('Enum_ReasonsForLossPensionIns_ONLY_PENSION_INSURANCE'))
        ];
    }
}
