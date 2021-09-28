/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.l {



    @bean()
    export class ViewModel extends ko.ViewModel {
        screenMode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.SELECT);
        initMode: KnockoutObservable<number> = ko.observable(INIT_MODE.WORKPLACE);
        lstWpkHistory: KnockoutObservableArray<HistoryItem> = ko.observableArray([]);
        selectedHistoryId: KnockoutObservable<string> = ko.observable('');
        bkHistoryId: string;
        bkStartDate: string;
        bkEndDate: string;
        selectedStartDateInput: KnockoutObservable<string> = ko.observable('');
        selectedStartDateText: KnockoutObservable<string> = ko.observable('');
        selectedEndDate: KnockoutObservable<string> = ko.observable('');
        copyPreviousConfig: KnockoutObservable<boolean> = ko.observable(true);
        isLatestHistory: KnockoutObservable<boolean> = ko.observable(true);

        created() {
            const vm = this;
            for (var i = 1 ; i < 13; i ++) {
                vm.lstWpkHistory.push(new HistoryItem({ historyId: i + '', startDate: '1990/' + i + '/28', endDate: '9999/12/31' }));
            }

            vm.selectedHistoryId.subscribe((value: string) => {
                const model = _.find(ko.unwrap(vm.lstWpkHistory), ((f: HistoryItem) => { return f.historyId === value; }));
                if (model) {
                    vm.selectedEndDate(model.endDate);
                    vm.selectedStartDateText(model.startDate);
                    vm.selectedStartDateInput(model.startDate);
                }else {
                    vm.selectedEndDate('');
                }
            });

            if (ko.unwrap(vm.lstWpkHistory).length > 0) {
                vm.selectedHistoryId(ko.unwrap(vm.lstWpkHistory)[0].historyId);
            }

            vm.selectedEndDate('');

        }

        mounted() {
            const vm = this;

        }

        addHistory() {
            const vm = this;
            vm.screenMode(SCREEN_MODE.ADD);
        }

        updateHistory() {
            const vm = this;
            vm.screenMode(SCREEN_MODE.UPDATE);

        }

        deleteHistory() {
            const vm = this;
        }

        cancel() {
            const vm = this;
            vm.$window.close();
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
        historyId: string;
        startDate: string;
        endDate: string;
        displayText: string;

        constructor(params: IHistoryItem) {
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
}
