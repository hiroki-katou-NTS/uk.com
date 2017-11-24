module nts.uk.com.view.cps017.c.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import formatDate = nts.uk.time.formatDate;

    export class ScreenModel {
        listHistorySelection: KnockoutObservableArray<IHistorySelection> = ko.observableArray([]);
        historySelection: KnockoutObservable<HistorySelection> = ko.observable(new HistorySelection({ histId: '', selectionItemId: '' }));
        selectionName: KnockoutObservable<string> = ko.observable('');
        data: any;

        constructor() {
            let self = this;
            self.data = getShared('CPS017C_PARAMS');
            //get name from screen main
            self.selectionName(self.data.name);
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        addHistory() {
            block.invisible();
            let self = this,
                currentItem: HistorySelection = self.historySelection(),
                selectHistory = self.data.selectHistory;
            currentItem.companyId(selectHistory.companyId);
            currentItem.selectionItemId(selectHistory.selectionItemId);
            currentItem.histId(selectHistory.histId);
            let command = ko.toJS(currentItem);
            service.addHistoryData(command).done(function() {
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    nts.uk.ui.windows.close();
                });

            }).fail(function(res) {
                $('#start-date-sel').ntsError('set', { messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
        }
    }

    // History:
    interface IHistorySelection {
        histId?: string;
        selectionItemId?: string;
        companyId: string;
        startDate: string;
        endDate: string;
    }

    class HistorySelection {
        histId: KnockoutObservable<string> = ko.observable('');
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        companyId: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable(formatDate(new Date()) || undefined);
        endDate: KnockoutObservable<string> = ko.observable('');

        constructor(param: IHistorySelection) {
            let self = this;
            self.histId(param.histId || '');
            self.selectionItemId(param.selectionItemId || '');
            self.companyId(param.companyId || '');
            self.startDate(formatDate(new Date()) || undefined);
            self.endDate(param.endDate || '');
        }

    }

}
