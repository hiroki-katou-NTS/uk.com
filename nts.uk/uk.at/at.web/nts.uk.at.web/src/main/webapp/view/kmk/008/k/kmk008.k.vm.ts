module nts.uk.at.view.kmk008.k {
    export module viewmodel {
        export class ScreenModel {
            currentCodeSelect: KnockoutObservable<string>;
            currentSelectItem: KnockoutObservable<SettingModel>;
            count: number = 4;

            isYearMonth: boolean = false;
            employeeCode: string = "A0000001";
            employeeName: string = "test cho vui";
            yearLabel: string;
            yearErrorTimeLabel: string;
            yearAlarmTimeLabel: string;
            listItemDataGrid: KnockoutObservableArray<ShowListModel>;

            constructor() {
                let self = this;
                self.date = ko.observable(200001);
                self.listItemDataGrid = ko.observableArray([]);
                self.currentCodeSelect = ko.observable("");
                self.currentSelectItem = ko.observable(new SettingModel(null, self.employeeCode));

                self.isYearMonth = false;
                self.yearErrorTimeLabel = nts.uk.resource.getText("KMK008_19");
                self.yearAlarmTimeLabel = nts.uk.resource.getText("KMK008_20");
                if (self.isYearMonth) {
                    self.yearLabel = nts.uk.resource.getText("KMK008_29");
                } else {
                    self.yearLabel = nts.uk.resource.getText("KMK008_30");
                }

                self.currentCodeSelect.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                   let itemSelect = _.find(self.listItemDataGrid(), item => { return item.yearOrYearMonthValue == Number(newValue);});
                    self.currentSelectItem(new SettingModel(itemSelect, self.employeeCode));
                });

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                if (self.isYearMonth) {
                    new service.Service().getDetailYearMonth(self.employeeCode).done(data => {
                        if (data && data.length > 0) {
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearMonthValue, item.errorOneMonth, item.alarmOneMonth));
                            });
                        }
                        dfd.resolve();
                    });
                } else {
                    new service.Service().getDetailYear(self.employeeCode).done(data => {
                        if (data && data.length) {
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearValue, item.errorOneYear, item.alarmOneYear));
                            });
                        }
                        dfd.resolve();
                    });
                }

                return dfd.promise();
            }
        }

        export class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }

        }

        export class ShowListModel {
            yearOrYearMonthValue: number;
            errorOneYearOrYearMonth: number;
            alarmOneYearOrYearMonth: number;
            constructor(yearOrYearMonthValue: number, errorOneYearOrYearMonth: number, alarmOneYearOrYearMonth: number) {
                this.yearOrYearMonthValue = yearOrYearMonthValue;
                this.errorOneYearOrYearMonth = errorOneYearOrYearMonth;
                this.alarmOneYearOrYearMonth = alarmOneYearOrYearMonth;
            }
        }

        export class SettingModel {
            employeeId: KnockoutObservable<string> = ko.observable("");
            yearOrYearMonthValue: KnockoutObservable<number> = ko.observable(0);
            errorOneYearOrYearMonth: KnockoutObservable<number> = ko.observable(0);
            alarmOneYearOrYearMonth: KnockoutObservable<number> = ko.observable(0);
            constructor(data: ShowListModel, employeeId: string) {
                this.employeeId(employeeId);
                if (!data) return;
                this.yearOrYearMonthValue(data.yearOrYearMonthValue);
                this.errorOneYearOrYearMonth(data.errorOneYearOrYearMonth);
                this.alarmOneYearOrYearMonth(data.alarmOneYearOrYearMonth);
            }
        }
        export class AddUpdateMonthSettingModel {
            employeeId: string = "";
            yearMonthValue: number = 0;
            errorOneMonth: number = 0;
            alarmOneMonth: number = 0;
            constructor(data: SettingModel) {
                if (!data) return;
                this.employeeId = data.employeeId();
                this.yearMonthValue = data.yearOrYearMonthValue();
                this.errorOneMonth = data.errorOneYearOrYearMonth();
                this.alarmOneMonth = data.alarmOneYearOrYearMonth();
            }
        }

        export class AddUpdateYearSettingModel {
            employeeId: string = "";
            yearValue: number = 0;
            errorOneYear: number = 0;
            alarmOneYear: number = 0;
            constructor(data: SettingModel) {
                if (!data) return;
                this.employeeId = data.employeeId();
                this.yearValue = data.yearOrYearMonthValue();
                this.errorOneYear = data.errorOneYearOrYearMonth();
                this.alarmOneYear = data.alarmOneYearOrYearMonth();
            }
        }

        export class DeleteMonthSettingModel {
            employeeId: string;
            yearMonthValue: number;
            constructor(employeeId: string, yearMonthValue: number) {
                this.employeeId = employeeId;
                this.yearMonthValue = yearMonthValue;
            }
        }

        export class DeleteYearSettingModel {
            employeeId: string;
            yearValue: number;
            constructor(employeeId: string, yearValue: number) {
                this.employeeId = employeeId;
                this.yearValue = yearValue;
            }
        }
    }
}
