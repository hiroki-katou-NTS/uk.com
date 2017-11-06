module nts.uk.com.view.cps017.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        listHistorySelection: KnockoutObservableArray<IHistorySelection> = ko.observableArray([]);
        historySelection: KnockoutObservable<HistorySelection> = ko.observable(new HistorySelection({ histId: '', selectionItemId: '' }));

        constructor() {
            let self = this;

        }

        start(): JQueryPromise<any> {

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        editHistory() {
            let self = this,
                currentItem: HistorySelection = self.historySelection(),
                listHistorySelection: Array<HistorySelection> = self.listHistorySelection(),
                selectHistory = getShared('selectHistory');

            currentItem.companyCode(selectHistory.companyCode);
            currentItem.selectionItemId(selectHistory.selectionItemId);
            currentItem.histId(selectHistory.histId);
            //currentItem.endDate(selectHistory.endDate);
            command = ko.toJS(currentItem);

            service.editHistoryData(command).done(function() {
                self.AddHistoryList.removeAll();
                service.getAllPerInfoHistorySelection(self.historySelection().selectionItemId()).done((itemList: Array<IHistorySelection>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listHistorySelection.push(x));
                    }
                });

                nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                self.listHistorySelection.valueHasMutated();
            });

        }
    }

    

    // History:
    interface IHistorySelection {
        histId?: string;
        selectionItemId?: string;
        companyCode: string;
        startDate: string;
        endDate: string;
    }

    class HistorySelection {
        histId: KnockoutObservable<string> = ko.observable('');
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        companyCode: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');

        constructor(param: IHistorySelection) {
            let self = this;
            self.histId(param.histId || '');
            self.selectionItemId(param.selectionItemId || '');
            self.companyCode(param.companyCode || '');
            self.startDate(param.startDate || '');
            self.endDate(param.endDate || '');
        }

    }

}
