module nts.uk.pr.view.qmm018.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;


    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum CategoryAtr {

        PAYMENT_ITEM = 0,

        DEDUCTION_ITEM = 1,

        ATTEND_ITEM = 2,

        REPORT_ITEM = 3,

        OTHER_ITEM = 4
    }

    export enum AttendanceDays {

        FROM_STATEMENT_ITEM = 0,

        FROM_EMPLOYMENT = 1
    }
    export function getAttendanceDays(): Array<ItemModel> {
        return [
            new ItemModel(AttendanceDays.FROM_STATEMENT_ITEM, getText('FROM_STATEMENT_ITEM')),
            new ItemModel(AttendanceDays.FROM_EMPLOYMENT, getText('FROM_EMPLOYMENT'))
        ];
    }

    export enum DaysFractionProcessing {

        AFTER = 0,

        BEFORE = 1
    }

    export function getDaysFractionProcessing(): Array<ItemModel> {
        return [
            new ItemModel(DaysFractionProcessing.AFTER, getText('DAYSFRACTIONPROCESSING_AFTER')),
            new ItemModel(DaysFractionProcessing.BEFORE, getText('DAYSFRACTIONPROCESSING_BEFORE'))
        ];
    }
}