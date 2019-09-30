module nts.uk.pr.view.qsi003.share.model {
    import getText = nts.uk.resource.getText;

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    export function getNotificationTarget(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('QSI003_8')),
            new ItemModel(1, getText('QSI003_9'))
        ];
    }

    export function getAddressOutClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_BusinessDivision_OUTPUT_COMPANY_NAME')),
            new ItemModel(1, getText('Enum_BusinessDivision_OUTPUT_SIC_INSURES')),
            new ItemModel(2, getText('Enum_BusinessDivision_DO_NOT_OUTPUT')),
            new ItemModel(3, getText('Enum_BusinessDivision_DO_NOT_OUTPUT_BUSINESS'))
        ];
    }

    export function roundingRules(): Array<ItemModel> {
        return [
            new ItemModel(2, getText('QSI003_18')),
            new ItemModel(1, getText('QSI003_19'))

        ];
    }
}