module nts.uk.com.view.cmm053.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        employeeId: string;
        pastHistoryItems: KnockoutObservableArray<PastHistory> = ko.observableArray([]);
        selectedPastHistory: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        departmentCode: KnockoutObservable<string> = ko.observable('');
        departmentName: KnockoutObservable<string> = ko.observable('');
        dailyApprovalCode: KnockoutObservable<string> = ko.observable('');
        dailyApprovalName: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.initScreen();
            self.selectedPastHistory.subscribe(selected => {
                if (selected) {
                    let _pastHistory = _.find(self.pastHistoryItems(), x => { return x.startEndDate === selected });
                    if (_pastHistory) {
                        self.startDate(_pastHistory.startDate());
                        self.endDate(_pastHistory.endDate());
                        self.departmentCode(_pastHistory.departmentCode());
                        self.departmentName(_pastHistory.departmentName());
                        self.dailyApprovalCode(_pastHistory.dailyApprovalCode());
                        self.dailyApprovalName(_pastHistory.dailyApprovalName());
                    }
                }
            });
            self.selectedPastHistory.subscribe(value => {
                $('#B1_1').focus();
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        //起動する
        initScreen() {
            let self = this;
            self.employeeId = getShared("CMM053A_employeeId");
            block.invisible();
            service.getPastHistory(self.employeeId).done((result: Array<any>) => {
                if (result && result.length > 0) {
                    result.sort(function compare(a, b) {
                        let dateA: any = new Date(a.startDate);
                        let dateB: any = new Date(b.startDate);
                        return dateB - dateA;
                    });
                    let _pastHistoryList: Array<PastHistory> = _.map(result, x => {
                        return new PastHistory(x.startDate, x.endDate, x.departmentCode, x.departmentName, x.dailyApprovalCode, x.dailyApprovalName)
                    });
                    self.pastHistoryItems(_pastHistoryList);
                    self.selectedPastHistory(_pastHistoryList[0].startEndDate);
                }
            }).fail(function(error) {
                dialog.alertError(error);
            }).always(function() {
                block.clear();
            });
        }

        //終了する
        closeDialog() {
            close();
        }
    }

    export class PastHistory {
        startEndDate: string;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        departmentCode: KnockoutObservable<string>;
        departmentName: KnockoutObservable<string>;
        dailyApprovalCode: KnockoutObservable<string>;
        dailyApprovalName: KnockoutObservable<string>;
        constructor(startDate: string, endDate: string, departmentCode: string, departmentName: string, dailyApprovalCode: string, dailyApprovalName: string) {
            this.startEndDate = startDate + '～' + endDate;
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.departmentCode = ko.observable(departmentCode);
            this.departmentName = ko.observable(departmentName);
            this.dailyApprovalCode = ko.observable(dailyApprovalCode);
            this.dailyApprovalName = ko.observable(dailyApprovalName);
        }
    }
}