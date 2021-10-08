/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.l {

    const DATE_FORMAT = 'YYYY/MM/DD';

    const API = {
        RESISTER: 'at/record/kdw006/view-l/register-history'
    }

    @bean()
    export class ViewModel extends ko.ViewModel {
        screenMode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.SELECT);
        initMode: KnockoutObservable<number> = ko.observable(INIT_MODE.WORKPLACE);
        lstWpkHistory: KnockoutObservableArray<HistoryItem> = ko.observableArray([]);
        selectedHistoryId: KnockoutObservable<string> = ko.observable('');
        bkHistoryId: string;
        bkStartDate: string;
        bkEndDate: string;
        selectedStartDateInput: KnockoutObservable<string | null> = ko.observable('');
        selectedStartDateText: KnockoutObservable<string | null> = ko.observable('');
        selectedEndDate: KnockoutObservable<string> = ko.observable('');
        copyPreviousConfig: KnockoutObservable<boolean> = ko.observable(true);
        isLatestHistory: KnockoutObservable<boolean> = ko.observable(true);
        public model: HistoryItem = new HistoryItem();
        public itemId: String = '';

        created(param: any) {
            const vm = this;

            vm.itemId = param.itemId;
            _.forEach(param.history.dateHistoryItems, ((value: IHistoryInfo) => {
                vm.lstWpkHistory.push(new HistoryItem({
                    historyId: value.historyId,
                    startDate: moment(value.startDate).format(DATE_FORMAT),
                    endDate: moment(value.endDate).format(DATE_FORMAT)
                }));
            }));

            vm.selectedHistoryId.subscribe((value: string) => {
                vm.model.update(_.find(ko.unwrap(vm.lstWpkHistory), ((f: HistoryItem) => { return f.historyId === value; })));
                vm.selectedEndDate(vm.model.endDate);
                vm.selectedStartDateText(vm.model.startDate);
                // vm.selectedStartDateInput(vm.model.startDate);
            });

            if (ko.unwrap(vm.lstWpkHistory).length > 0) {
                vm.selectedHistoryId(ko.unwrap(vm.lstWpkHistory)[0].historyId);
            }
        }

        mounted() {
            const vm = this;
        }

        addHistory() {
            const vm = this;
            vm.screenMode(SCREEN_MODE.ADD);
            vm.selectedStartDateText(null);
        }

        updateHistory() {
            const vm = this;
            vm.screenMode(SCREEN_MODE.UPDATE);
            vm.selectedStartDateText(vm.model.startDate);
        }

        deleteHistory() {
            const vm = this;
        }

        cancel() {
            const vm = this;
            vm.$window.close();
        }

        AddOrUpdate() {
            const vm = this;
            const param = {
                itemId: vm.itemId,
                historyId: ko.unwrap(vm.model.historyId),
                startDate: new Date(ko.unwrap(vm.selectedStartDateText)),
                endDate: new Date(ko.unwrap(vm.selectedEndDate))
            }

            if (ko.unwrap(vm.screenMode) === SCREEN_MODE.ADD) {
                vm.$blockui('invisible')
                    .then(() => {
                        vm.$ajax('at', API.RESISTER, param)
                            .then(() => vm.$dialog.info('Msg_15'))
                    })
            }
        }

        setEndDate(param: string) {
            const vm = this;
            switch (ko.unwrap(vm.screenMode)) {
                case SCREEN_MODE.SELECT:
                case SCREEN_MODE.UPDATE:
                    vm.selectedEndDate(param);
                    break
                case SCREEN_MODE.NEW:
                case SCREEN_MODE.ADD:
                    vm.selectedEndDate('9999/12/31');
                    break;
            }
        }

    }

    enum SCREEN_MODE {
        SELECT = 0,
        NEW = 1,
        ADD = 2,
        UPDATE = 3
    }

    enum INIT_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }

    class HistoryItem {
        historyId: string = '';
        startDate: string = '';
        endDate: string = '';
        displayText: string = '';

        constructor(params?: IHistoryItem) {
            const vm = new ko.ViewModel();
            if (params) {
                this.historyId = params.historyId;
                this.startDate = params.startDate;
                this.endDate = params.endDate;
                this.displayText = params.startDate + " " + vm.$i18n("CMM011_125") + " " + params.endDate;
            }
        }

        public update(params?: IHistoryItem) {
            const vm = new ko.ViewModel();
            if (params) {
                this.historyId = params.historyId;
                this.startDate = params.startDate;
                this.endDate = params.endDate;
                this.displayText = params.startDate + " " + vm.$i18n("CMM011_125") + " " + params.endDate;
            }
        }
    }

    interface IHistoryItem {
        historyId: string;
        startDate: string;
        endDate: string;
    }

    interface IHistory {
        /** 項目ID */
        itemId: String;
        /** 履歴項目 */
        dateHistoryItems: IHistoryInfo[];
    }

    class History {
        itemId: KnockoutObservable<String> = ko.observable('');
        dateHistoryItems: KnockoutObservableArray<HistoryInfo> = ko.observableArray([]);

        constructor(param: IHistory) {
            this.itemId(param.itemId);
            var historyInfos: HistoryInfo[] = [];
            _.forEach(param.dateHistoryItems, ((value: IHistoryInfo) => {
                historyInfos.push(new HistoryInfo(value));
            }));
            this.dateHistoryItems(historyInfos);
        }
    }

    interface IHistoryInfo {
        historyId: string;
        startDate: Date;
        endDate: Date;
    }

    class HistoryInfo {
        historyId: KnockoutObservable<String> = ko.observable('');
        startDate: KnockoutObservable<Date> = ko.observable(new Date);;
        endDate: KnockoutObservable<Date> = ko.observable(new Date);
        period: KnockoutObservable<String> = ko.observable('');

        constructor(param: IHistoryInfo) {
            this.historyId(param.historyId);
            this.startDate(param.startDate);
            this.endDate(param.endDate);
            this.period(param.startDate.toString() + ' ~ ' + param.endDate.toString());
        }
    }
}
