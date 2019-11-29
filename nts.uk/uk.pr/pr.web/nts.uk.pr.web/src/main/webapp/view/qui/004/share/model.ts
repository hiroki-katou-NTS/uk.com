module nts.uk.pr.view.qui004.share.model {
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

    export function getRequestForInsurance() : Array<ItemModel> {
        return [
            new ItemModel(1, getText('QUI004_C222_5')),
            new ItemModel(0, getText('QUI004_C222_6'))
        ];
    }

    export function getScheduleForReplenishment() : Array<ItemModel>{
        return [
            new ItemModel(1, getText('QUI004_C222_9')),
            new ItemModel(0, getText('QUI004_C222_10'))
        ];
    }

    export function getCauseOfLossAtr() : Array<ItemModel> {
        return [
            new ItemModel(0, getText('Enum_CauseOfLossAtr_OTHER_REASON')),
            new ItemModel(1, getText('Enum_CauseOfLossAtr_TURN_OVER_THAN_3')),
            new ItemModel(2, getText('Enum_CauseOfLossAtr_TURNOVER_AT_CONVENIENCE')),
        ];
    }
}