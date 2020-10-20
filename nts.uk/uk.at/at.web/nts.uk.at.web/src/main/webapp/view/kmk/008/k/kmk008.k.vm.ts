/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.k {
    const PATH_API = {
        getDetailYearMonth: "screen/at/kmk008/k/getYearMonth",
        addAgreementMonthSetting: "monthly/estimatedtime/monthSetting/register",
        removeAgreementMonthSetting: "monthly/estimatedtime/monthSetting/delete",
        updateAgreementMonthSetting: "monthly/estimatedtime/monthSetting/update",

        getDetailYear: "screen/at/kmk008/k/getYear",
        addAgreementYearSetting: "monthly/estimatedtime/yearSetting/register",
        removeAgreementYearSetting: "monthly/estimatedtime/yearSetting/delete",
        updateAgreementYearSetting: "monthly/estimatedtime/yearSetting/update",
    };

    @bean()
    export class KMK008KViewModel extends ko.ViewModel {
        currentCodeSelect: KnockoutObservable<number> = ko.observable(null);
        currentSelectItem: KnockoutObservable<SettingModel>;
        updateEnable: KnockoutObservable<boolean> = ko.observable(true);
        deleteEnable: KnockoutObservable<boolean> = ko.observable(false);

        isYearMonth: boolean;
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        yearLabel: string;
        inputFormatYearOrYearMonth: string;
        dateFormat: string;
        listItemDataGrid: KnockoutObservableArray<ShowListModel> = ko.observableArray([]);


        //３６協定基本設定.３６協定1年間.特例条項による上限.上限時間
        //３６協定基本設定.３６協定1ヶ月.特例条項による上限.上限時間
        oneMonthOrYearUpperLimit: KnockoutObservable<Number> = ko.observable(-1);
        oneMonthOrYearUpperLimitString: KnockoutObservable<string> = ko.observable();

        constraintTime: string;

        constructor(params: any) {
            super();
            const vm = this;
            let dto: IData = params || {
                employeeCode: '',
                employeeId: '',
                employeeName: '',
                isYearMonth: false,
                itemData: null
            };

            vm.isYearMonth = dto.isYearMonth;
            vm.employeeId = dto.employeeId;
            vm.employeeCode = dto.employeeCode;
            vm.employeeName = dto.employeeName;

            vm.currentSelectItem = ko.observable(new SettingModel(null, vm.employeeId));
            if (vm.isYearMonth) {
                vm.yearLabel = vm.$i18n("KMK008_30");
                vm.inputFormatYearOrYearMonth = 'YYYYMM';
                vm.dateFormat = 'yearmonth';
                vm.constraintTime = "AgreementOneMonthTime";
            } else {
                vm.yearLabel = vm.$i18n("KMK008_29");
                vm.inputFormatYearOrYearMonth = 'YYYY';
                vm.dateFormat = 'YYYY';
                vm.constraintTime = "AgreementOneYearTime";
            }

            vm.currentCodeSelect.subscribe(newValue => {
                if (nts.uk.text.isNullOrEmpty(newValue)) return;

                let newValueNum = Number(newValue.toString().replace("/", ""));
                let itemSelect = _.find(vm.listItemDataGrid(), item => {
                    return item.yearOrYearMonthValue == newValueNum;
                });
                vm.currentSelectItem(new SettingModel(itemSelect, vm.employeeId));
                vm.updateEnable(true);
                vm.deleteEnable(true);
                vm.$errors("clear");
                $("#txt-year-error-time").focus();
            });


            vm.listItemDataGrid([]);
            if (dto.itemData && dto.itemData.length) {
                _.forEach(dto.itemData, item => {
                    vm.listItemDataGrid.push(new ShowListModel(Number(item.year), Number(item.error), Number(item.alarm)));
                });
                vm.updateEnable(true);
                vm.currentCodeSelect(vm.listItemDataGrid()[0].yearOrYearMonthValue);
            } else {
                vm.setNewMode();
            }

            vm.oneMonthOrYearUpperLimitString(vm.$i18n("KMK008_185", [""]));
            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.getDetailYearMonth, {employeeId: vm.employeeId}).done(data => {
                    vm.oneMonthOrYearUpperLimit(data.oneMonthUpperLimit);
                    vm.oneMonthOrYearUpperLimitString(vm.$i18n("KMK008_185", [nts.uk.time.parseTime(data.oneMonthUpperLimit, true).format()]));
                });
            } else {
                vm.$ajax(PATH_API.getDetailYear, {employeeId: vm.employeeId}).done(data => {
                    vm.oneMonthOrYearUpperLimit(data.oneYearUpperLimit);
                    vm.oneMonthOrYearUpperLimitString(vm.$i18n("KMK008_185", [nts.uk.time.parseTime(data.oneYearUpperLimit, true).format()]));
                });
            }
        }

        created(params: any) {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;

            if (vm.updateEnable()) {
                $("#txt-year-error-time").focus();
            } else {
                $("#txt-year").focus();
            }
        }

        setNewMode() {
            const vm = this;
            vm.deleteEnable(false);
            vm.currentSelectItem(new SettingModel(null, vm.employeeId));
            vm.currentCodeSelect(null);
            vm.updateEnable(false);
            $("#txt-year").focus();
        }

        addOrUpdateClick() {
            const vm = this;
            vm.$validate('.nts-editor').then((valid: boolean) => {
                if (valid) {
                    if (vm.updateEnable()) {
                        vm.updateData();
                        return;
                    } else
                        vm.register();
                }
            });
        }

        register() {
            const vm = this;
            let yearOrYearMonth = vm.currentSelectItem().yearOrYearMonthValue().toString().length == 4 ?
                vm.currentSelectItem().yearOrYearMonthValue() : Number(vm.currentSelectItem().yearOrYearMonthValue().toString().replace("/", ""));
            if (yearOrYearMonth == 0 || nts.uk.text.isNullOrEmpty(vm.currentSelectItem().employeeId())) {
                return;
            }

            if (vm.currentSelectItem().errorOneYearOrYearMonth() > vm.oneMonthOrYearUpperLimit()) {
                let param:string;
                if (vm.isYearMonth) {
                    param = vm.$i18n("KMK008_25");
                }
                else {
                    param = vm.$i18n("KMK008_28");
                }

                return vm.$dialog.error({
                    messageId: "Msg_59",
                    messageParams: [param, vm.$i18n("KMK008_42"), vm.$i18n("KMK008_185", [""])]
                });
            }
            else if (vm.currentSelectItem().alarmOneYearOrYearMonth() > vm.currentSelectItem().errorOneYearOrYearMonth()) {
                let param:string;
                if (vm.isYearMonth) {
                    param = vm.$i18n("KMK008_25");
                }
                else {
                    param = vm.$i18n("KMK008_28");
                }

                return vm.$dialog.error({
                    messageId: "Msg_59",
                    messageParams: [param, vm.$i18n("KMK008_43"), vm.$i18n("KMK008_42")]
                });
            }

            vm.$blockui("invisible");

            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.addAgreementMonthSetting, new AddUpdateMonthSettingModel(vm.currentSelectItem()))
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.reloadData(yearOrYearMonth);
                        });
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            } else {
                vm.$ajax(PATH_API.addAgreementYearSetting, new AddUpdateYearSettingModel(vm.currentSelectItem()))
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.reloadData(yearOrYearMonth);
                        });
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            }
        }

        updateData() {
            const vm = this;
            if (vm.currentSelectItem().yearOrYearMonthValue() == 0 || nts.uk.text.isNullOrEmpty(vm.currentSelectItem().employeeId())) {
                return;
            }

            if (vm.currentSelectItem().errorOneYearOrYearMonth() > vm.oneMonthOrYearUpperLimit()) {
                let param:string;
                if (vm.isYearMonth) {
                    param = vm.$i18n("KMK008_25");
                }
                else {
                    param = vm.$i18n("KMK008_28");
                }

                return vm.$dialog.error({
                    messageId: "Msg_59",
                    messageParams: [param, vm.$i18n("KMK008_42"), vm.$i18n("KMK008_185", [""])]
                });
            }
            else if (vm.currentSelectItem().alarmOneYearOrYearMonth() > vm.currentSelectItem().errorOneYearOrYearMonth()) {
                let param:string;
                if (vm.isYearMonth) {
                    param = vm.$i18n("KMK008_25");
                }
                else {
                    param = vm.$i18n("KMK008_28");
                }

                return vm.$dialog.error({
                    messageId: "Msg_59",
                    messageParams: [param, vm.$i18n("KMK008_43"), vm.$i18n("KMK008_42")]
                });
            }

            vm.$blockui("invisible");

            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.updateAgreementMonthSetting, new AddUpdateMonthSettingModel(vm.currentSelectItem()))
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.listItemDataGrid.replace(
                                _.find(vm.listItemDataGrid(), item => {
                                    return item.yearOrYearMonthValue == vm.currentSelectItem().yearOrYearMonthValue();
                                }),
                                new ShowListModel(vm.currentSelectItem().yearOrYearMonthValue(),
                                    vm.currentSelectItem().errorOneYearOrYearMonth(),
                                    vm.currentSelectItem().alarmOneYearOrYearMonth()
                                )
                            );
                        });
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            } else {
                vm.$ajax(PATH_API.updateAgreementYearSetting, new AddUpdateYearSettingModel(vm.currentSelectItem()))
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.listItemDataGrid.replace(
                                _.find(vm.listItemDataGrid(), item => {
                                    return item.yearOrYearMonthValue == vm.currentSelectItem().yearOrYearMonthValue();
                                }),
                                new ShowListModel(vm.currentSelectItem().yearOrYearMonthValue(),
                                    vm.currentSelectItem().errorOneYearOrYearMonth(),
                                    vm.currentSelectItem().alarmOneYearOrYearMonth()
                                )
                            );
                        });
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            }
        }

        removeData() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {

                    vm.$blockui("invisible");

                    if (vm.isYearMonth) {
                        vm.$ajax(PATH_API.removeAgreementMonthSetting, new DeleteMonthSettingModel(vm.employeeId, vm.currentSelectItem().yearOrYearMonthValue()))
                            .done(function () {
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    vm.reloadData(vm.currentSelectItem().yearOrYearMonthValue(), true);
                                });
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => vm.$blockui("clear"));
                    } else {
                        vm.$ajax(PATH_API.removeAgreementYearSetting, new DeleteYearSettingModel(vm.employeeId, vm.currentSelectItem().yearOrYearMonthValue()))
                            .done(function () {
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    vm.reloadData(vm.currentSelectItem().yearOrYearMonthValue(), true);
                                });
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => vm.$blockui("clear"));
                    }
                }
            });
        }


        reloadData(oldSelectCode: number, isRemove: boolean = false) {
            const vm = this;
            let oldSelectIndex = _.findIndex(vm.listItemDataGrid(), item => {
                return item.yearOrYearMonthValue == oldSelectCode;
            });

            if (isRemove) {
                vm.listItemDataGrid.remove(
                    vm.listItemDataGrid()[oldSelectIndex]
                );
                if (vm.listItemDataGrid().length) {
                    vm.currentCodeSelect(vm.getNewSelectRemove(oldSelectIndex));
                }
                else {
                    vm.setNewMode();
                }
            } else {
                vm.listItemDataGrid.push(new ShowListModel(
                    vm.currentSelectItem().yearOrYearMonthValue(),
                    vm.currentSelectItem().errorOneYearOrYearMonth(),
                    vm.currentSelectItem().alarmOneYearOrYearMonth()
                ));
                vm.listItemDataGrid(_.orderBy(vm.listItemDataGrid(), ['yearOrYearMonthValue'], ['desc']));
                if (!vm.updateEnable()) {
                    vm.updateEnable(true);
                    vm.deleteEnable(true);
                }
                vm.currentCodeSelect(oldSelectCode);
            }
        }

        getNewSelectRemove(oldSelectIndex: number): number {
            const vm = this;
            let dataLength = vm.listItemDataGrid().length;
            if (dataLength == 1 || oldSelectIndex > dataLength) {
                return vm.listItemDataGrid()[0].yearOrYearMonthValue;
            }
            if (oldSelectIndex <= dataLength - 1) {
                return vm.listItemDataGrid()[oldSelectIndex].yearOrYearMonthValue;
            }
            if (oldSelectIndex == dataLength) {
                return vm.listItemDataGrid()[oldSelectIndex - 1].yearOrYearMonthValue;
            }
            return null;
        }

        closeDialog() {
            const vm = this;
            vm.$window.close({});
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
        yearOrYearMonthValue: KnockoutObservable<number> = ko.observable();
        errorOneYearOrYearMonth: KnockoutObservable<number> = ko.observable();
        alarmOneYearOrYearMonth: KnockoutObservable<number> = ko.observable();

        constructor(data: ShowListModel, employeeId: string) {
            this.employeeId(employeeId);
            if (!data) return;
            this.yearOrYearMonthValue(data.yearOrYearMonthValue);
            this.errorOneYearOrYearMonth(data.errorOneYearOrYearMonth);
            this.alarmOneYearOrYearMonth(data.alarmOneYearOrYearMonth);
        }
    }

    export class AddUpdateMonthSettingModel {

        employeeId: string = ""; //社員ID
        yearMonth: number; //年月
        errorTime: number; //年月エラー時間
        alarmTime: number; //年月アラーム時間

        constructor(data: SettingModel) {
            if (!data) return;
            this.employeeId = data.employeeId();
            this.yearMonth = data.yearOrYearMonthValue();
            this.errorTime = data.errorOneYearOrYearMonth();
            this.alarmTime = data.alarmOneYearOrYearMonth();
        }
    }

    export class AddUpdateYearSettingModel {
        employeeId: string = ""; //社員ID
        year: number; //年度
        errorTime: number; //年度エラー時間
        alarmTime: number; //年度アラーム時間

        constructor(data: SettingModel) {
            if (!data) return;
            this.employeeId = data.employeeId();
            this.year = data.yearOrYearMonthValue();
            this.errorTime = data.errorOneYearOrYearMonth();
            this.alarmTime = data.alarmOneYearOrYearMonth();
        }
    }

    export class DeleteMonthSettingModel {
        employeeId: string; //社員ID
        yearMonth: number; //年月

        constructor(employeeId: string, yearMonthValue: number) {
            this.employeeId = employeeId;
            this.yearMonth = Number(yearMonthValue.toString().replace("/", ""));
        }
    }

    export class DeleteYearSettingModel {
        employeeId: string; //社員ID
        year: number; //年度

        constructor(employeeId: string, yearValue: number) {
            this.employeeId = employeeId;
            this.year = yearValue;
        }
    }

    interface IData {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        isYearMonth: boolean;
        itemData: any;
    }
}
