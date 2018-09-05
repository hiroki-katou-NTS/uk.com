module nts.uk.com.view.qmm012.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export enum CategoryAtr {

        PAYMENT_ITEM = 0,

        DEDUCTION_ITEM = 1,

        ATTEND_ITEM = 2,

        REPORT_ITEM = 3,

        OTHER_ITEM = 4,
    }

    export function getCategoryAtr(): Array<ItemModel> {
        return [
            new model.ItemModel(CategoryAtr.PAYMENT_ITEM, getText('PAYMENT_ITEM')),
            new model.ItemModel(CategoryAtr.DEDUCTION_ITEM, getText('DEDUCTION_ITEM')),
            new model.ItemModel(CategoryAtr.ATTEND_ITEM, getText('ATTEND_ITEM')),
            new model.ItemModel(CategoryAtr.REPORT_ITEM, getText('REPORT_ITEM')),
            new model.ItemModel(CategoryAtr.OTHER_ITEM, getText('OTHER_ITEM')),
        ];
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}