module nts.uk.pr.view.qmm020.j.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = qmm020.share.model;
    export class ScreenModel {
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        isFirst:              KnockoutObservable<boolean> = ko.observable(true);
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        startYearMonth:         KnockoutObservable<number> = ko.observable();

        endYearMonth:           KnockoutObservable<number> = ko.observable(999912);
        constructor(){

        }
        submit(){

        }
        cancel(){
            close();
        }
    }
    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM020_59')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM020_60'))
        ];
    }
    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }
}