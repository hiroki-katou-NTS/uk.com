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
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
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
            new ItemModel(ReasonsForLossHealthyIns.BLANK.toString(), getText('Enum_ReasonsForLossHealthyIns_BLANK')),
            new ItemModel(ReasonsForLossHealthyIns.RETIREMENT.toString(), getText('Enum_ReasonsForLossHealthyIns_RETIREMENT')),
            new ItemModel(ReasonsForLossHealthyIns.DEATH.toString(), getText('Enum_ReasonsForLossHealthyIns_DEATH')),
            new ItemModel(ReasonsForLossHealthyIns.ONLY_HEALTHY_INSURANCE.toString(), getText('Enum_ReasonsForLossHealthyIns_ONLY_HEALTHY_INSURANCE')),
            new ItemModel(ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION.toString(), getText('Enum_ReasonsForLossHealthyIns_DISABILITY_AUTHORIZATION'))
        ];
    }

    export function getCauseTypePensionLossInfo(): Array<ItemModel> {
        return [
            new ItemModel(ReasonsForLossPensionIns.BLANK.toString(), getText('Enum_ReasonsForLossPensionIns_BLANK')),
            new ItemModel(ReasonsForLossPensionIns.RETIREMENT.toString(), getText('Enum_ReasonsForLossPensionIns_RETIREMENT')),
            new ItemModel(ReasonsForLossPensionIns.DEATH.toString(), getText('Enum_ReasonsForLossPensionIns_DEATH')),
            new ItemModel(ReasonsForLossPensionIns.ONLY_HEALTHY_INSURANCE.toString(), getText('Enum_ReasonsForLossPensionIns_ONLY_PENSION_INSURANCE'))
        ];
    }
}