module nts.uk.at.view.kmk008.k {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            currentCodeSelect: KnockoutObservable<number>;
            currentSelectItem: KnockoutObservable<SettingModel>;
            isUpdate: boolean;
            updateEnable: KnockoutObservable<boolean>;
            newMode: KnockoutObservable<boolean>;
            isYearMonth: boolean;
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            yearLabel: string;
            yearErrorTimeLabel: string;
            yearAlarmTimeLabel: string;
            inputFormatYearOrYearMonth: string;
            dateFormat : string;
            listItemDataGrid: KnockoutObservableArray<ShowListModel>;
            deleteEnable: KnockoutObservable<boolean>;

            constraintErrorTime: string;
            constraintAlarmTime: string;

            constructor() {
                let self = this,
                    dto: IData = nts.uk.ui.windows.getShared("KMK_008_PARAMS") || { employeeCode: '', employeeId: '', employeeName: '', isYearMonth: false };

                self.newMode = ko.observable(false);
                self.deleteEnable = ko.observable(false);

                self.isYearMonth = dto.isYearMonth;
                self.employeeId = dto.employeeId;
                self.employeeCode = dto.employeeCode;
                self.employeeName = dto.employeeName;
                self.isUpdate = true;
                self.updateEnable = ko.observable(true);

                self.listItemDataGrid = ko.observableArray([]);
                self.currentCodeSelect = ko.observable(null);
                self.currentSelectItem = ko.observable(new SettingModel(null, self.employeeId));
                self.yearErrorTimeLabel = nts.uk.resource.getText("KMK008_19");
                self.yearAlarmTimeLabel = nts.uk.resource.getText("KMK008_20");
                if (self.isYearMonth) {
                    self.yearLabel = nts.uk.resource.getText("KMK008_30");
                    self.inputFormatYearOrYearMonth = 'YYYYMM';
                    self.dateFormat = 'yearmonth';
                    self.constraintErrorTime = "ErrorOneMonth";
                    self.constraintAlarmTime = "AlarmOneMonth";
                } else {
                    self.yearLabel = nts.uk.resource.getText("KMK008_29");
                    self.inputFormatYearOrYearMonth = 'YYYY';
                    self.dateFormat = 'YYYY';
                    self.constraintErrorTime = "ErrorOneYear";
                    self.constraintAlarmTime = "AlarmOneYear";
                }

                self.currentCodeSelect.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    let newValueNum = Number(newValue.toString().replace("/", ""));
                    let itemSelect = _.find(self.listItemDataGrid(), item => { return item.yearOrYearMonthValue == newValueNum; });
                    self.currentSelectItem(new SettingModel(itemSelect, self.employeeId));
                    self.newMode(false);
                    self.updateEnable(true);
                    self.deleteEnable(true);
                });

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.listItemDataGrid([]);
                if (self.isYearMonth) {
                    new service.Service().getDetailYearMonth(self.employeeId).done(data => {
                        if (data && data.length > 0) {
                            //                            data = _.sortBy(data, item => { return data.yearMonthValue });
                            //                            data.reverse();
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearMonthValue, item.errorOneMonth, item.alarmOneMonth));
                            });
                            self.isUpdate = true;
                            self.updateEnable(true);
                            self.currentCodeSelect(self.listItemDataGrid()[0].yearOrYearMonthValue);
                        } else {
                            self.setNewMode();
                        }
                        dfd.resolve();
                    });
                } else {
                    new service.Service().getDetailYear(self.employeeId).done(data => {
                        if (data && data.length) {
                            //                            data = _.sortBy(data, item => { return data.yearValue });
                            //                            data.reverse();
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearValue, item.errorOneYear, item.alarmOneYear));
                            });
                            self.isUpdate = true;
                            self.updateEnable(true);
                            self.currentCodeSelect(self.listItemDataGrid()[0].yearOrYearMonthValue);
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
                //                if (nts.uk.ui.errors.hasError()) {
                //                    nts.uk.ui.errors.clearAll();
                //                }
                self.newMode(true);
                self.isUpdate = false;
                self.deleteEnable(false);
                self.currentSelectItem(new SettingModel(null, self.employeeId));
                self.currentCodeSelect(null);
                self.updateEnable(false);
                $("#txt-year").focus();
            }

            addOrUpdateClick() {
                let self = this;
                if (self.isUpdate) {
                    self.updateData();
                    return;
                }
                self.register();
            }

            register() {
                let self = this;
                let yearOrYearMonth = self.currentSelectItem().yearOrYearMonthValue().toString().length == 4 ?
                    self.currentSelectItem().yearOrYearMonthValue() : Number(self.currentSelectItem().yearOrYearMonthValue().toString().replace("/", ""));
                if (yearOrYearMonth == 0 || nts.uk.text.isNullOrEmpty(self.currentSelectItem().employeeId())) {
                    return;
                }
                if (self.isYearMonth) {
//                    new service.Service().addAgreementMonthSetting(new AddUpdateMonthSettingModel(self.currentSelectItem())).done((res) => {
//                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                        self.updateEnable(true);
//                        self.reloadData(yearOrYearMonth);
//                    }).fail((res) => {
//                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage(res.message));
//                    });
                    let updateParam = new AddUpdateMonthSettingModel(self.currentSelectItem());
                    let command = {
                        updateParam : updateParam,
                        yearMonthValueOld : self.currentCodeSelect()
                    }
                    new service.Service().addAgreementMonthSetting(command).done(listError => {
                            if (listError.length > 0) {
                                let errorCode = _.split(listError[0], ',');
                                nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [nts.uk.resource.getText(errorCode[1]), nts.uk.resource.getText(errorCode[2])] });
                                return;
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.reloadData(yearOrYearMonth);

                        });
                } else {
//                    new service.Service().addAgreementYearSetting(new AddUpdateYearSettingModel(self.currentSelectItem())).done((res) => {
//                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                        self.updateEnable(true);
//                        self.reloadData(yearOrYearMonth);
//                    }).fail((res) => {
//                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage(res.message));
//                    });
                    new service.Service().addAgreementYearSetting(new AddUpdateYearSettingModel(self.currentSelectItem())).done(listError => {
                            if (listError.length > 0) {
                                let errorCode = _.split(listError[0], ',');
                                nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [nts.uk.resource.getText(errorCode[1]), nts.uk.resource.getText(errorCode[2])] });
                                return;
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.reloadData(yearOrYearMonth);

                        });
                }
            }

            updateData() {
                let self = this;
                if (self.currentSelectItem().yearOrYearMonthValue() == 0 || nts.uk.text.isNullOrEmpty(self.currentSelectItem().employeeId())) {
                    return;
                }
                if (self.isYearMonth) {
//                    new service.Service().updateAgreementMonthSetting(new AddUpdateMonthSettingModel(self.currentSelectItem())).done((res) => {
//                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                        self.updateEnable(true);
//                        self.reloadData(self.currentCodeSelect());
//                    }).fail((res) => {
//                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage(res.messageId, ['{#KMK008_42}', '{#KMK008_44}']));
//                    });
                   let specialHoliday = {
                        employeeId: self.currentSelectItem().employeeId(),
                        yearMonthValue: self.currentSelectItem().yearOrYearMonthValue(),
                        errorOneMonth: self.currentSelectItem().errorOneYearOrYearMonth(),
                        alarmOneMonth: self.currentSelectItem().alarmOneYearOrYearMonth(),
                        yearMonthValueOld: self.currentCodeSelect()
                    }
                    
                    new service.Service().updateAgreementMonthSetting(specialHoliday).done(listError => {
                            if (listError.length > 0) {
                                let errorCode = _.split(listError[0], ',');
                                nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [nts.uk.resource.getText(errorCode[1]), nts.uk.resource.getText(errorCode[2])] });
                                return;
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.reloadData(self.currentCodeSelect());

                        });
                } else {
//                    new service.Service().updateAgreementYearSetting(new AddUpdateYearSettingModel(self.currentSelectItem())).done((res) => {
//                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                        self.updateEnable(true);
//                        self.reloadData(self.currentCodeSelect());
//                    }).fail((res) => {
//                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage(res.messageId, ['{#KMK008_42}', '{#KMK008_44}']));
//                    });
                    new service.Service().updateAgreementYearSetting(new AddUpdateYearSettingModel(self.currentSelectItem())).done(listError => {
                            if (listError.length > 0) {
                                let errorCode = _.split(listError[0], ',');
                                nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [nts.uk.resource.getText(errorCode[1]), nts.uk.resource.getText(errorCode[2])] });
                                return;
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.reloadData(self.currentCodeSelect());

                        });
                }
            }

            removeData() {
                let self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                    .ifYes(() => {
                        if (self.isYearMonth) {
                            new service.Service().removeAgreementMonthSetting(new DeleteMonthSettingModel(self.employeeId, self.currentSelectItem().yearOrYearMonthValue()))
                                .done(function() {
                                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", [])).then(() => {
                                         self.reloadData(Number(self.currentSelectItem().yearOrYearMonthValue().toString().replace("/", "")), true);
                                    });
                                });
                        } else {
                            new service.Service().removeAgreementYearSetting(new DeleteYearSettingModel(self.employeeId, self.currentSelectItem().yearOrYearMonthValue()))
                                .done(function() {
                                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", [])).then(() => {
                                        self.reloadData(self.currentSelectItem().yearOrYearMonthValue(), true);
                                    });
                                });
                        }

                    });
            }

            closeDialogK() {
                nts.uk.ui.windows.close();
            }

            reloadData(oldSelectCode: number, isRemove?: boolean) {
                let self = this;
                let dfd = $.Deferred();
                let oldSelectIndex = _.findIndex(self.listItemDataGrid(), item => { return item.yearOrYearMonthValue == oldSelectCode; });
                self.listItemDataGrid([]);
                if (self.isYearMonth) {
                    new service.Service().getDetailYearMonth(self.employeeId).done(data => {
                        if (data && data.length > 0) {
                            _.forEach(data, item => {
                                self.listItemDataGrid.push(new ShowListModel(item.yearMonthValue, item.errorOneMonth, item.alarmOneMonth));
                            });
                            self.isUpdate = true;
                            if (isRemove && isRemove == true) {
                                self.currentCodeSelect(self.getNewSelectRemove(oldSelectIndex));
                            } else {
                                self.currentCodeSelect(oldSelectCode);
                            }

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
                            self.isUpdate = true;
                            if (isRemove && isRemove == true) {
                                self.currentCodeSelect(self.getNewSelectRemove(oldSelectIndex));
                            } else {
                                self.currentCodeSelect(oldSelectCode);
                            }
                        } else {
                            self.setNewMode();
                        }
                        dfd.resolve();
                    });
                }

                return dfd.promise();
            }

            getNewSelectRemove(oldSelectIndex: number): number {
                let self = this;
                let dataLength = self.listItemDataGrid().length;
                if (dataLength == 1 || oldSelectIndex > dataLength) {
                    return self.listItemDataGrid()[0].yearOrYearMonthValue;
                }
                if (oldSelectIndex <= dataLength - 1) {
                    return self.listItemDataGrid()[oldSelectIndex].yearOrYearMonthValue;
                }
                if (oldSelectIndex == dataLength) {
                    return self.listItemDataGrid()[oldSelectIndex - 1].yearOrYearMonthValue;
                }
                return null;
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
        yearOrYearMonthFormat: string;
        constructor(yearOrYearMonthValue: number, errorOneYearOrYearMonth: number, alarmOneYearOrYearMonth: number) {
            this.yearOrYearMonthValue = yearOrYearMonthValue;
            this.errorOneYearOrYearMonth = errorOneYearOrYearMonth;
            this.alarmOneYearOrYearMonth = alarmOneYearOrYearMonth;
            this.yearOrYearMonthFormat = yearOrYearMonthValue.toString().length > 4 ? nts.uk.time.parseYearMonth(yearOrYearMonthValue).format() : yearOrYearMonthValue.toString();
        }
    }

    export class SettingModel {
        employeeId: KnockoutObservable<string> = ko.observable("");
        yearOrYearMonthValue: KnockoutObservable<number> = ko.observable(null);
        errorOneYearOrYearMonth: KnockoutObservable<number> = ko.observable(null);
        alarmOneYearOrYearMonth: KnockoutObservable<number> = ko.observable(null);
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
            this.yearMonthValue = Number(data.yearOrYearMonthValue().toString().replace("/", ""));
            this.errorOneMonth = data.errorOneYearOrYearMonth() || 0;
            this.alarmOneMonth = data.alarmOneYearOrYearMonth() || 0;
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
            this.yearValue = Number(data.yearOrYearMonthValue());
            this.errorOneYear = data.errorOneYearOrYearMonth() || 0;
            this.alarmOneYear = data.alarmOneYearOrYearMonth() || 0;
        }
    }

    export class DeleteMonthSettingModel {
        employeeId: string;
        yearMonthValue: number;
        constructor(employeeId: string, yearMonthValue: number) {
            this.employeeId = employeeId;
            this.yearMonthValue = Number(yearMonthValue.toString().replace("/", ""));
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
        employeeCode: string;
        employeeName: string;
        isYearMonth: boolean;
    }
}

