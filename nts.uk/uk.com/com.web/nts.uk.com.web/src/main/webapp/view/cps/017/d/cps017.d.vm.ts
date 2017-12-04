module nts.uk.com.view.cps017.d.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        listHistorySelection: KnockoutObservableArray<IHistorySelection> = ko.observableArray([]);
        selectionName: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        data: any;
        constructor() {
            let self = this;
            self.data = getShared('CPS017D_PARAMS');
            self.selectionName(self.data.sel_name);
            self.startDate(self.data.sel_history.startDate);
        }
        /**
         * close dialog when click button キャンセル
         */
        closeDialog() {
            nts.uk.ui.windows.close();
        }

        /**
         * update history when click button 決定 
         */
        editHistory() {
            block.invisible();
            let self = this;
            let history: IHistorySelection = self.data.sel_history;
            let data = { startDateNew: moment(self.startDate()).format("YYYY/MM/DD"),
                        startDate: history.startDate,
                        endDate: history.endDate,
                        histId: history.histId,
                        selectionItemId: history.selectionItemId,
            }
            service.editHistoryData(data).done(function() {
                nts.uk.ui.windows.close();
            }).fail(function(res){
                if(res.messageId == 'Msg_127'){
                    $('#start-date-sel').ntsError('set', {messageId: res.messageId});
                }else{
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }
            }).always(() => {
                block.clear();
            });
        }
    }

    // History:
    interface IHistorySelection {
        histId?: string;
        selectionItemId?: string;
        startDate: string;
        endDate: string;
    }
}
