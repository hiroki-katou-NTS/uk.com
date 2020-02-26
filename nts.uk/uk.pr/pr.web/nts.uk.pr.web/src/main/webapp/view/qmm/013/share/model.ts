module nts.uk.pr.view.qmm013.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export enum Abolition {
        NOT_ABOLISH = 0,
        ABOLISH = 1
    }

    export enum CoveredAtr {
        NOT_COVERED = 0,
        COVERED = 1
    }

    export enum SettingClassification {
        DESIGNATE_FOR_EACH_SALARY_CONTRACT_TYPE = 0,
        DESIGNATE_BY_ALL_MEMBERS = 1
    }

    export enum PerUnitPriceType {
        HOUR = 0,
        DAILY_AMOUNT = 1,
        OTHER = 2
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}