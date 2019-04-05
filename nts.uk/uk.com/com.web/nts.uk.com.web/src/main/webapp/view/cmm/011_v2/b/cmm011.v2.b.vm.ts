module nts.uk.com.view.cmm011.v2.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        initMode: number = INIT_MODE.WORKPLACE;
        screenMode: number = SCREEN_MODE.NEW;
        lstWpkHistory: KnockoutObservableArray<HistoryItem>;
        selectedHistoryId: KnockoutObservable<string>;
        
        constructor() {
            let self = this, params = getShared("CMM011BParams");
            if (params) {
                self.initMode = params.initMode;
            }
            self.lstWpkHistory = ko.observableArray([]);
            self.selectedHistoryId = ko.observable(null);
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllConfiguration(self.initMode).done(data => {
                if (data) {
                    self.lstWpkHistory(_.map(data, i => new HistoryItem(i)));
                }
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear()
            });
            return dfd.promise();
        }
    }
    
    enum INIT_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }
    
    enum SCREEN_MODE {
        SELECT = 0,
        NEW = 1,
        UPDATE = 2
    }
    
    class HistoryItem {
        historyId: string;
        startDate: string;
        endDate: string;
        displayText: string;
        
        constructor(params) {
            if (params) {
                this.historyId = params.historyId;
                this.startDate = params.startDate;
                this.endDate = params.endDate;
                this.displayText = params.startDate + getText("CMM011-0_25") + params.endDate;
            }
        }
    }
}