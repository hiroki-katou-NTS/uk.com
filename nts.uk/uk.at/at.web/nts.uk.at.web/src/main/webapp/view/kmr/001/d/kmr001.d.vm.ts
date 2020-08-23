/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.d {
    const API = {
        SETTING: 'at/record/stamp/management/personal/startPage',
        HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
        START: 'bento/bentomenuhist/get/all',
        ADDNEW: 'bento/bentomenuhist/add',
        UPDATE: 'bento/bentomenuhist/update',
        DELETE: 'bento/bentomenuhist/delete'
    };
    const PATH = {
        REDIRECT: '/view/ccg/008/a/index.xhtml'
    }
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    const DEFAULT_END = "9999/12/31";

    @bean()
    export class KMR001DViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        initMode: KnockoutObservable<number> = ko.observable(INIT_MODE.WORKPLACE);
        screenMode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.NEW);
        lstWpkHistory: KnockoutObservableArray<HistoryItem>;
        selectedHistoryId: KnockoutObservable<string>;
        bkHistoryId: string;
        bkStartDate: string;
        bkEndDate: string;
        selectedStartDateInput: KnockoutObservable<string>;
        selectedStartDateText: KnockoutObservable<string>;
        selectedEndDate: KnockoutObservable<string>;
        copyPreviousConfig: KnockoutObservable<boolean>;
        isLatestHistory: KnockoutObservable<boolean>;
        params: {
            historyId: string,
            startDate: string,
            endDate: string
        };

        constructor(params: any) {
            super();
            var vm = this;
            let self = this;
            console.log("paramKMR001C",params);
            self.lstWpkHistory = ko.observableArray([]);
            self.selectedHistoryId = ko.observable(null);
            self.selectedStartDateInput = ko.observable(null);
            self.selectedStartDateText = ko.observable(null);
            self.selectedEndDate = ko.observable(DEFAULT_END);
            self.copyPreviousConfig = ko.observable(false);
            if (params) {
                self.initMode(params.initMode);
                self.selectedHistoryId(params.historyId);
                self.bkHistoryId = params.historyId;
            }
            if (self.initMode() == INIT_MODE.DEPARTMENT) {
                let currentScreen = nts.uk.ui.windows.getSelf();
                currentScreen.setTitle(getText(""));
            }
            vm.$blockui('clear');
            self.selectedHistoryId.subscribe(value => {
                if (value) {
                    let history: HistoryItem = _.find(self.lstWpkHistory(), i => i.historyId == value);
                    if (history) {
                        self.selectedStartDateInput(history.startDate);
                        self.selectedStartDateText(history.startDate);
                        self.selectedEndDate(history.endDate);
                    }
                    self.screenMode(SCREEN_MODE.SELECT);
                } else {
                }
            });
            self.isLatestHistory = ko.computed(() => {
                return !_.isEmpty(self.lstWpkHistory()) && self.selectedHistoryId() == self.lstWpkHistory()[0].historyId;
            }, this);
        }

        created(params: any) {
            const vm = this;
            let self = this, dfd = $.Deferred();
            block.invisible();
            if (params) {
                self.initMode(params.initMode);
                self.selectedHistoryId(params.historyId);
                self.bkHistoryId = params.historyId;
            }
            vm.$ajax(API.START).done(data => {
                if (data) {
                    if (!nts.uk.util.isNullOrEmpty(data.historyItems)) {
                        let rs = _.orderBy(data.historyItems, function (o) {
                            return new Date(o.endDate);
                        }, ['desc']);
                        self.lstWpkHistory(_.map(rs,
                            i => new HistoryItem(i)));
                        let selectedHist = _.find(data, h => h.historyId == self.bkHistoryId);
                        if (selectedHist && self.bkStartDate == null && self.bkEndDate == null) {
                            self.bkStartDate = selectedHist.startDate;
                            self.bkEndDate = selectedHist.endDate;
                        }
                        if (self.selectedHistoryId() != null)
                            self.selectedHistoryId.valueHasMutated();
                        else
                            self.selectedHistoryId(self.lstWpkHistory()[0].historyId);
                    }
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

        addHistory() {
            let self = this;
            self.selectedHistoryId(self.lstWpkHistory()[0].historyId);
            self.screenMode(SCREEN_MODE.ADD);
            self.selectedStartDateInput(null)
            self.selectedEndDate(DEFAULT_END);
        }

        updateHistory() {
            let self = this;
            self.screenMode(SCREEN_MODE.UPDATE);
        }

        deleteHistory() {
            const vm = this;
            let self = this;
            self.params = {
                historyId: self.selectedHistoryId(),
                startDate: self.selectedStartDateText(),
                endDate: self.selectedEndDate()
            };
            confirm({messageId: "Msg_18"}).ifYes(() => {
                block.invisible();
                let data = new CommandDelete(self.selectedHistoryId());
                vm.$ajax(API.DELETE, data).done(() => {
                    self.created(self.params).done(() => {
                        self.selectedHistoryId(self.lstWpkHistory()[0].historyId);
                    });
                    nts.uk.ui.dialog.info({messageId: "Msg_16"});
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }).ifNo(() => {
            });
        }

        registerConfig() {
            let self = this, data = null;
            const vm = this;
            $(".nts-input").trigger("validate");
            if (nts.uk.ui.errors.hasError())
                return;
            block.invisible();
            self.params = {
                historyId: self.selectedHistoryId(),
                startDate: self.selectedStartDateText(),
                endDate: self.selectedEndDate()
            };
            let startDate = moment.utc(self.selectedStartDateInput(), "YYYY/MM/DD"),
                endDate = moment.utc(self.selectedEndDate(), "YYYY/MM/DD");
            switch (self.screenMode()) {
                case SCREEN_MODE.NEW:
                    data = new BentoMenuHistCommand(startDate);
                    vm.$ajax(API.ADDNEW, data).done((historyId) => {
                        self.created(self.params).done(() => {
                            self.selectedHistoryId(historyId);
                            nts.uk.ui.dialog.info({messageId: "Msg_15"});
                        });
                    }).fail((error) => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                    break;
                case SCREEN_MODE.ADD:
                    data = new BentoMenuHistCommand(startDate);
                    vm.$ajax(API.ADDNEW, data).done((historyId) => {
                        self.created(self.params).done(() => {
                            self.selectedHistoryId(historyId);
                            nts.uk.ui.dialog.info({messageId: "Msg_15"});
                        });
                    }).fail((error) => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                    break;
                case SCREEN_MODE.UPDATE:
                    data = new CommandUpdate(startDate.toISOString(), endDate.toISOString(), self.selectedHistoryId());
                    vm.$ajax(API.UPDATE, data).done(() => {
                        self.created(self.params).done(() => {
                            self.selectedHistoryId.valueHasMutated();
                            nts.uk.ui.dialog.info({messageId: "Msg_15"});
                        });
                    }).fail((error) => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                    break;
                default:
                    block.clear();
                    if (self.selectedHistoryId()) {
                        let preSelectHist = _.find(self.lstWpkHistory(), h => h.historyId == self.selectedHistoryId());
                        let params = {
                            historyId: preSelectHist.historyId,
                            startDate: preSelectHist.startDate,
                            endDate: preSelectHist.endDate
                        };
                        self.$window.close({
                            params
                        });
                    } else {
                        let params = {
                            historyId: self.lstWpkHistory()[0].historyId,
                            startDate: self.lstWpkHistory()[0].startDate,
                            endDate: self.lstWpkHistory()[0].endDate
                        };
                        self.$window.close({
                            params
                        });
                    }
                    ;
                    break;
            }
        }

        cancel() {
            let self = this;
            let preSelectHist = _.find(self.lstWpkHistory(), h => h.historyId == self.bkHistoryId);
            if (preSelectHist && (preSelectHist.startDate != self.bkStartDate || preSelectHist.endDate != self.bkEndDate)) {
                let params = {
                    historyId: preSelectHist.historyId,
                    startDate: preSelectHist.startDate,
                    endDate: preSelectHist.endDate
                };
                self.$window.close({
                    params
                });

            } else if (preSelectHist == null) {
                let params = {
                    historyId: self.lstWpkHistory()[0].historyId,
                    startDate: self.lstWpkHistory()[0].startDate,
                    endDate: self.lstWpkHistory()[0].endDate
                };
                self.$window.close({
                    params
                });
            }
            nts.uk.ui.windows.close();
        }

        deselectAll() {
            $('#list-box').ntsListBox('deselectAll');
        }

        selectAll() {
            $('#list-box').ntsListBox('selectAll');
        }

        /**
         * event Decide Data
         */
        public decideData(): void {

        }

        /**
         * event close dialog
         */

    }

    interface BentoMenuHisItemDto {
        companyId: string;
        historyItems: Array<DateHistoryItemDto>;
    }

    interface DateHistoryItemDto {
        historyId: string;
        startDate: Date;
        endDate: Date;
    }

    class CommandDelete {
        historyId: string;

        constructor(historyId: string) {
            this.historyId = historyId;
        }
    }

    class CommandUpdate {
        startDatePerio: string;
        endDatePerio: string;
        historyId: string;

        constructor(startDatePerio: string, endDatePerio: string, historyId: string) {
            this.startDatePerio = startDatePerio;
            this.endDatePerio = endDatePerio;
            this.historyId = historyId;
        }
    }

    class ItemModel {
        from: string;
        to: string;
        historyId: string;

        constructor(from: string, to: string, historyId: string) {
            this.from = from;
            this.to = to;
            this.historyId = historyId;
        }
    }

    class Param {
        historyId: string;
        startDate: string;
        endDate: string;

        constructor(historyId: string, startDate: string, endDate: string,) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.historyId = historyId;
        }
    }

    enum INIT_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }

    enum SCREEN_MODE {
        SELECT = 0,
        NEW = 1,
        ADD = 2,
        UPDATE = 3
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
                this.displayText = params.startDate + " " + getText("CMM011_125") + " " + params.endDate;
            }
        }

    }

    class BentoMenuHistCommand {
        date: string;

        constructor(date) {
            this.date = date;
        }
    }

}

