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
            new ItemModel(0, getText('Enum_EmpInsOutOrder_INSURANCE_NUMBER')),
            new ItemModel(1, getText('Enum_EmpInsOutOrder_DEPARTMENT_EMPLOYEE')),
            new ItemModel(2, getText('Enum_EmpInsOutOrder_EMPLOYEE_CODE')),
            new ItemModel(3, getText('Enum_EmpInsOutOrder_EMPLOYEE'))
        ];
    }
    // 25: 印字区分
    export function isPrintMyNum(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QUI001_A222_26')),
            new ItemModel(1, getText('QUI001_A222_27'))
        ];
    }

    // 32: 事業所区分
    export function getOfficeCls(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_OfficeCls_OUPUT_LABOR_OFFICE')),
            new ItemModel(1, getText('Enum_OfficeCls_OUTPUT_COMPANY')),
            new ItemModel(2, getText('Enum_OfficeCls_DO_NOT_OUTPUT'))
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
            new ItemModel(0, getText('QUI001_A222_35')),
            new ItemModel(1, getText('QUI001_A222_36'))
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

    export function getSubNameClassA(): Array<ItemModelA> {
        return [
            new ItemModelA(0, getText('QUI001_C222_5')),
            new ItemModelA(1, getText('QUI001_C222_6'))
        ];
    }
    export function getSubNameClassB(): Array<ItemModelB> {
        return [
            new ItemModelB(0, getText('QUI001_C222_28')),
            new ItemModelB(1, getText('QUI001_C222_29'))
        ];}


    export class ItemModelA {
        codeA: number;
        nameA: string;

        constructor(codeA: number, nameA: string) {
            this.codeA = codeA;
            this.nameA = nameA;
        }
    }

    export class ItemModelB {
        codeB: number;
        nameB: string;

        constructor(codeB: number, nameB: string) {
            this.codeB = codeB;
            this.nameB = nameB;
        }
    }

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }
}