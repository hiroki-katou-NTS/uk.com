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

    import getText = nts.uk.resource.getText;
    const DEFAULT_END = "9999/12/31";

    @bean()
    export class KMR001DViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
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
            const vm = this;
            vm.lstWpkHistory = ko.observableArray([]);
            vm.selectedHistoryId = ko.observable(null);
            vm.selectedStartDateInput = ko.observable(null);
            vm.selectedStartDateText = ko.observable(null);
            vm.selectedEndDate = ko.observable(DEFAULT_END);
            vm.copyPreviousConfig = ko.observable(false);
            if (params) {
                vm.selectedHistoryId(params.historyId);
                vm.bkHistoryId = params.historyId;
            }
            vm.$blockui('clear');
            vm.selectedHistoryId.subscribe(value => {
                if (value) {
                    let history: HistoryItem = _.find(vm.lstWpkHistory(), i => i.historyId == value);
                    if (history) {
                        vm.selectedStartDateInput(history.startDate);
                        vm.selectedStartDateText(history.startDate);
                        vm.selectedEndDate(history.endDate);
                    }
                    vm.screenMode(SCREEN_MODE.SELECT);
                } else {
                }
            });
            vm.isLatestHistory = ko.computed(() => {
                return !_.isEmpty(vm.lstWpkHistory()) && vm.selectedHistoryId() == vm.lstWpkHistory()[0].historyId;
            }, this);
        }

        created(params: any) {
            const vm = this;
            let dfd = $.Deferred();
            vm.$blockui("invisible");
            if (params) {
                vm.selectedHistoryId(params.historyId);
                vm.bkHistoryId = params.historyId;
            }
            vm.$ajax(API.START).done(data => {
                if (data) {
                    if (!nts.uk.util.isNullOrEmpty(data.historyItems)) {
                        let rs = _.orderBy(data.historyItems, function (o) {
                            return new Date(o.endDate);
                        }, ['desc']);
                        vm.lstWpkHistory(_.map(rs,
                            i => new HistoryItem(i)));
                        let selectedHist = _.find(data, h => h.historyId == vm.bkHistoryId);
                        if (selectedHist && vm.bkStartDate == null && vm.bkEndDate == null) {
                            vm.bkStartDate = selectedHist.startDate;
                            vm.bkEndDate = selectedHist.endDate;
                        }
                        if (vm.selectedHistoryId() != null)
                            vm.selectedHistoryId.valueHasMutated();
                        else
                            vm.selectedHistoryId(vm.lstWpkHistory()[0].historyId);
                    }
                }
                dfd.resolve();
            }).fail((error) => {
                vm.$dialog.error(error);
                dfd.reject();
            }).always(() => {
                vm.$blockui("clear");
            });
            return dfd.promise();
        }

        addHistory() {
            const vm = this;
            if (vm.lstWpkHistory().length > 0) {
                vm.selectedHistoryId(vm.lstWpkHistory()[0].historyId);
            }
            vm.screenMode(SCREEN_MODE.ADD);
            vm.selectedStartDateInput(null)
            vm.selectedEndDate(DEFAULT_END);
        }

        updateHistory() {
            const vm = this;
            vm.screenMode(SCREEN_MODE.UPDATE);
        }

        deleteHistory() {
            const vm = this;
            vm.params = {
                historyId: vm.selectedHistoryId(),
                startDate: vm.selectedStartDateText(),
                endDate: vm.selectedEndDate()
            };
            vm.$dialog.confirm({ messageId: 'Msg_18' }).then(res => {
                if (res == "yes") {
                    vm.$blockui("invisible");
                    let data = new CommandDelete(vm.selectedStartDateText(), vm.selectedHistoryId());
                    if (vm.lstWpkHistory().length > 1) {
                        vm.$ajax(API.DELETE, data).done(() => {
                            vm.created(vm.params).done(() => {
                                vm.selectedHistoryId(vm.lstWpkHistory()[0].historyId);
                            });
                            vm.$dialog.info({ messageId: "Msg_16" });
                        }).fail(error => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                    }
                    if (vm.lstWpkHistory().length <= 1) {
                        vm.$ajax(API.DELETE, data).done(() => {
                            vm.created(vm.params).done(() => {
                                vm.screenMode(SCREEN_MODE.ADD);
                                vm.selectedStartDateInput(null);
                                vm.selectedEndDate(DEFAULT_END);
                                vm.lstWpkHistory([]);
                            });
                            vm.$dialog.info({ messageId: "Msg_16" });
                        }).fail(error => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                    }
                }
            });
        }

        registerConfig() {
            let data = null;
            const vm = this;
            vm.$validate(".nts-input").then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                vm.$blockui("invisible");
                vm.params = {
                    historyId: vm.selectedHistoryId(),
                    startDate: vm.selectedStartDateText(),
                    endDate: vm.selectedEndDate()
                };
                let startDate = moment.utc(vm.selectedStartDateInput(), "YYYY/MM/DD"),
                    endDate = moment.utc(vm.selectedEndDate(), "YYYY/MM/DD");
                switch (vm.screenMode()) {
                    case SCREEN_MODE.NEW:
                        data = new BentoMenuHistCommand(startDate.format('YYYY/MM/DD'), endDate.format('YYYY/MM/DD'));
                        vm.$ajax(API.ADDNEW, data).done((historyId) => {
                            vm.created(vm.params).done(() => {
                                vm.selectedHistoryId(historyId);
                                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                                    let params = {
                                        date: data.startDate,
                                        isLasted: true
                                    };
                                    vm.$window.close({params});
                                });
                            });
                        }).fail((error) => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                        break;
                    case SCREEN_MODE.ADD:
                        data = new BentoMenuHistCommand(startDate.format('YYYY/MM/DD'), endDate.format('YYYY/MM/DD'));
                        vm.$ajax(API.ADDNEW, data).done((historyId) => {
                            vm.created(vm.params).done(() => {
                                vm.selectedHistoryId(historyId);
                                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                                    let params = {
                                        date: data.startDate,
                                        isLasted: true
                                    };
                                    vm.$window.close({params});
                                });
                            });
                        }).fail((error) => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                        break;
                    case SCREEN_MODE.UPDATE:
                        data = new CommandUpdate(
							moment(startDate).format('YYYY/MM/DD'), 
							moment(endDate).format('YYYY/MM/DD'), 
							_.find(vm.lstWpkHistory(), o => o.historyId==vm.selectedHistoryId()).startDate);
                        vm.$ajax(API.UPDATE, data).done(() => {
                            vm.created(vm.params).done(() => {
                                vm.selectedHistoryId.valueHasMutated();
                                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                                    vm.focusUi(data);
                                });
                            });
                        }).fail((error) => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                        break;
                    default:
                        vm.$blockui("clear");
                        if (vm.selectedHistoryId()) {
                            let preSelectHist = _.find(vm.lstWpkHistory(), h => h.historyId == vm.selectedHistoryId());
                            let params = {
								date: preSelectHist.startDate,
								isLasted: vm.isLatestHistory()
							};
										
							if(_.isEmpty(params)) {
								vm.$window.close();	
							} else {
								vm.$window.close({ params });		
							}
                        } else {
							vm.$window.close();
//                            let params = {
//                                historyId: vm.lstWpkHistory()[0].historyId,
//                                startDate: vm.lstWpkHistory()[0].startDate,
//                                endDate: vm.lstWpkHistory()[0].endDate
//                            };
//                            vm.$window.close({
//                                params
//                            });
                        }
                        break;
                }
            })
        }

        cancel() {
            const vm = this;
//            if (vm.lstWpkHistory().length == 0) {
//                let params = {
//                    historyId: null,
//                    startDate: null,
//                    endDate: null
//                };
//                vm.$window.close({
//                    params
//                });
//            }
//            let preSelectHist = _.find(vm.lstWpkHistory(), h => h.historyId == vm.bkHistoryId);
//            if (preSelectHist && (preSelectHist.startDate != vm.bkStartDate || preSelectHist.endDate != vm.bkEndDate)) {
//                let params = {
//                    historyId: preSelectHist.historyId,
//                    startDate: preSelectHist.startDate,
//                    endDate: preSelectHist.endDate
//                };
//                vm.$window.close({
//                    params
//                });
//
//            } else if (preSelectHist == null && vm.lstWpkHistory().length > 0) {
//                let params = {
//                    historyId: vm.lstWpkHistory()[0].historyId,
//                    startDate: vm.lstWpkHistory()[0].startDate,
//                    endDate: vm.lstWpkHistory()[0].endDate
//                };
//                vm.$window.close({
//                    params
//                });
//            }
            vm.$window.close();
        }

        focusUi(data: any) {
            const vm = this;
            let selectedHist = _.find(data, h => h.historyId == vm.bkHistoryId);
            if (selectedHist && vm.bkStartDate == null && vm.bkEndDate == null) {
                vm.bkStartDate = selectedHist.startDate;
                vm.bkEndDate = selectedHist.endDate;
            }
            if (vm.selectedHistoryId() != null)
                vm.selectedHistoryId.valueHasMutated();
            else
                vm.selectedHistoryId(vm.lstWpkHistory()[0].historyId);
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
		startDate: string;
		historyId: string;

        constructor(startDate: string, historyId: string) {
			this.startDate = startDate;
            this.historyId = historyId;
        }
    }

    class CommandUpdate {
		startDatePerio: string;
		endDatePerio: string;
		originalStartDate: string;

        constructor(startDatePerio: string, endDatePerio: string, originalStartDate: string) {
            this.startDatePerio = startDatePerio;
            this.endDatePerio = endDatePerio;
            this.originalStartDate = originalStartDate;
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
                this.displayText = params.startDate + " " + getText("KMR001_58") + " " + params.endDate;
            }
        }

    }

    class BentoMenuHistCommand {
        startDate: string;
		endDate: string;

        constructor(startDate: string, endDate: string) {
            this.startDate = startDate;
			this.endDate = endDate;
        }
    }

}

