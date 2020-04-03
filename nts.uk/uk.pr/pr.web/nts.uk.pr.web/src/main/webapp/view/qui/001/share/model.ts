module nts.uk.pr.view.qui001.share.model {
    import getText = nts.uk.resource.getText;

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
    // 23: 出力順
    export function getEmpInsOutOrder(): Array<ItemModel> {
        return [
            // new ItemModel(0, getText('Enum_EmpInsOutOrder_INSURANCE_NUMBER')),
            new ItemModel(1, getText('Enum_EmpInsOutOrder_DEPARTMENT_EMPLOYEE')),
            new ItemModel(2, getText('Enum_EmpInsOutOrder_EMPLOYEE_CODE')),
            new ItemModel(3, getText('Enum_EmpInsOutOrder_EMPLOYEE'))
        ];
    }
    // 25: 印字区分
    export function isPrintMyNum(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('QUI001_A222_26')),
            new ItemModel(0, getText('QUI001_A222_27'))
        ];
    }

    // 32: 事業所区分
    export function getOfficeCls32(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
            new ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE')),
            new ItemModel(2, getText('Enum_OfficeCls_DO_NOT_OUTPUT'))
        ];
    }

    export function getOfficeCls47(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
            new ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE'))
        ];
    }
    // 48: 事業所情報

    export function getSubNameClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QUI001_A222_18')),
            new ItemModel(1, getText('QUI001_A222_19'))
        ];
    }

    export function getChangedName(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('QUI001_A222_35')),
            new ItemModel(0, getText('QUI001_A222_36'))
        ];
    }

    export function getNewLine(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QUI001_A222_41')),
            new ItemModel(1, getText('QUI001_A222_42')),
            new ItemModel(2, getText('QUI001_A222_43'))
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

    export function acquisitionAtr(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QUI001_C222_5')),
            new ItemModel(1, getText('QUI001_C222_6'))
        ];
    }

    export function contrPeriPrintAtr(): Array<ItemModel> {
        return [
            new ItemModel(1, getText('QUI001_C222_28')),
            new ItemModel(0, getText('QUI001_C222_29'))
        ];
    }

    export function insCauseAtr(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_InsuranceCause_NEW_EMPLOYMENT_NEW_GRADUATE')),
            new ItemModel(1, getText('Enum_InsuranceCause_NEW_EMPLOYMENT_OTHER')),
            new ItemModel(2, getText('Enum_InsuranceCause_SWITCHING_FROM_DAY_LABOR')),
            new ItemModel(3, getText('Enum_InsuranceCause_OTHER')),
            new ItemModel(4, getText('Enum_InsuranceCause_TEMPORARY_RETURN_OLDER_65'))
        ];
    }

    export function jobAtr(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_JobAtr_MANAGER')),
            new ItemModel(1, getText('Enum_JobAtr_TECHNICAL')),
            new ItemModel(2, getText('Enum_JobAtr_ADMINISTRATIVE')),
            new ItemModel(3, getText('Enum_JobAtr_SALES')),
            new ItemModel(4, getText('Enum_JobAtr_SERVICE')),
            new ItemModel(5, getText('Enum_JobAtr_SECURITY')),
            new ItemModel(6, getText('Enum_JobAtr_AGRICULTURE_FORESTRY_FISHERY')),
            new ItemModel(7, getText('Enum_JobAtr_PRODUCTION')),
            new ItemModel(8, getText('Enum_JobAtr_TRANSPORT_MACHINE_OPERATION')),
            new ItemModel(9, getText('Enum_JobAtr_CONSTRUCTION_MINING')),
            new ItemModel(10, getText('Enum_JobAtr_TRANSPORTATION_CLEANING_PACKAGING'))
        ];
    }

    export function jobPath(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_JobPath_STABLE_INTRODUCTION')),
            new ItemModel(1, getText('Enum_JobPath_SELF_EMPLOYMENT')),
            new ItemModel(2, getText('Enum_JobPath_PRIVATE_INTRODUCTION')),
            new ItemModel(3, getText('Enum_JobPath_DO_NOT_KNOW'))
        ];
    }

    export function wagePaymentMode(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_WagePaymentMode_MONTHLY_SALARY')),
            new ItemModel(1, getText('Enum_WagePaymentMode_WEEEKLY_SALARY')),
            new ItemModel(2, getText('Enum_WagePaymentMode_DAILY_WAGE')),
            new ItemModel(3, getText('Enum_WagePaymentMode_HOURLY_PAY')),
            new ItemModel(4, getText('Enum_WagePaymentMode_OTHER'))
        ];
    }

    export function employmentStatus(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_EmploymentStatus_DAILY_WORK')),
            new ItemModel(1, getText('Enum_EmploymentStatus_DISPATCH')),
            new ItemModel(2, getText('Enum_EmploymentStatus_PART_TIME')),
            new ItemModel(3, getText('Enum_EmploymentStatus_FIXED_TERM_CONTRACT')),
            new ItemModel(4, getText('Enum_EmploymentStatus_SEASONAL')),
            new ItemModel(5, getText('Enum_EmploymentStatus_SAILOR')),
            new ItemModel(6, getText('Enum_EmploymentStatus_OTHER')),
        ];
    }
}