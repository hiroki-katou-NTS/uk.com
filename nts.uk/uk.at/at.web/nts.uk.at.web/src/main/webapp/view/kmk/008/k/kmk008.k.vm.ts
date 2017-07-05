module nts.uk.at.view.kmk008.k {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            currentCodeSelect: KnockoutObservable<string>;
            currentSelectItem: KnockoutObservable<SettingModel>;
            count: number = 4;

            newMode: KnockoutObservable<boolean>;
            isYearMonth: boolean = false;
            employeeId: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            yearLabel: KnockoutObservable<string>;
            yearErrorTimeLabel: KnockoutObservable<string>;
            yearAlarmTimeLabel: KnockoutObservable<string>;
            listItemDataGrid: KnockoutObservableArray<ShowListModel>;

            constructor() {
                let self = this,
                    dto: IData = nts.uk.ui.windows.getShared("KMK_008_PARAMS") || { employeeId: '', employeeName: '', isYearMonth : false };

                self.newMode = ko.observable(false);
                
                self.isYearMonth = dto.isYearMonth;
                self.employeeId = dto.employeeId;
                self.employeeName = dto.employeeName;
                self.date = ko.observable(200001);
                self.listItemDataGrid = ko.observableArray([]);
                self.currentCodeSelect = ko.observable(dto.employeeId);
                self.currentSelectItem = ko.observable(new SettingModel(null, self.employeeId));

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
                    let itemSelect = _.find(self.listItemDataGrid(), item => { return item.yearOrYearMonthValue == Number(newValue); });
                    self.currentSelectItem(new SettingModel(itemSelect, self.employeeId));
                });

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                if (self.isYearMonth) {
                    new service.Service().getDetailYearMonth(self.employeeId).done(data => {
                        if (data && data.length > 0) {
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearMonthValue, item.errorOneMonth, item.alarmOneMonth));
                            });
                        } else {
                            self.setNewMode();    
                        }
                        dfd.resolve();
                    });
                } else {
                    new service.Service().getDetailYear(self.employeeId).done(data => {
                        if (data && data.length) {
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearValue, item.errorOneYear, item.alarmOneYear));
                            });
                        } else {
                            self.setNewMode();    
                        }
                        dfd.resolve();
                    });
                }

                return dfd.promise();
            }

            setNewMode() {
                let self = this;
                self.newMode(true);
                self.currentSelectItem(new SettingModel([], self.employeeId);
            }
            
            register(){
                let self = this; 
                if(self.isYearMonth){
                    new service.Service().addAgreementYearSetting(self.currentSelectItem())    
                } else {
                    new service.Service().addAgreementMonthSetting(self.currentSelectItem());    
                }
                
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

        interface IData {
            employeeId?: string;
            employeeName: string;
            isYearMonth : boolean;
        }
    }

}
