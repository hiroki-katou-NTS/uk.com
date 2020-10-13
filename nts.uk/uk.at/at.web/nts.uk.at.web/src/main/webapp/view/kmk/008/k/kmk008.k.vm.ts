/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.k {
    import getShared = nts.uk.ui.windows.getShared;

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
        isUpdate: boolean = true;
        updateEnable: KnockoutObservable<boolean> = ko.observable(true);
        newMode: KnockoutObservable<boolean> = ko.observable(false);
        isYearMonth: boolean;
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        yearLabel: string;
        yearErrorTimeLabel: string;
        yearAlarmTimeLabel: string;
        inputFormatYearOrYearMonth: string;
        dateFormat: string;
        listItemDataGrid: KnockoutObservableArray<ShowListModel> = ko.observableArray([]);
        deleteEnable: KnockoutObservable<boolean> = ko.observable(false);

        oneMonthOrYearUpperLimit: KnockoutObservable<string> = ko.observable();

        constraintTime: string;

        constructor() {
            super();
            const vm = this;
            let dto: IData = getShared("KMK_008_PARAMS") || {
                employeeCode: '',
                employeeId: '',
                employeeName: '',
                isYearMonth: false
            };

            vm.isYearMonth = dto.isYearMonth;
            vm.employeeId = dto.employeeId;
            vm.employeeCode = dto.employeeCode;
            vm.employeeName = dto.employeeName;

            vm.currentSelectItem = ko.observable(new SettingModel(null, vm.employeeId));
            vm.yearErrorTimeLabel = vm.$i18n("KMK008_19");
            vm.yearAlarmTimeLabel = vm.$i18n("KMK008_20");
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
                vm.newMode(false);
                vm.updateEnable(true);
                vm.deleteEnable(true);
                setTimeout(function () {
                    nts.uk.ui.errors.clearAll();
                }, 100);
            });

            vm.listItemDataGrid([]);
            let data = getShared("KMK_008_DATA") || null;
            if (data && data.length) {
                _.forEach(data, item => {
                    vm.listItemDataGrid.push(new ShowListModel(Number(item.year), Number(item.error), Number(item.alarm)));
                });
                vm.isUpdate = true;
                vm.updateEnable(true);
                vm.currentCodeSelect(vm.listItemDataGrid()[0].yearOrYearMonthValue);
            } else {
                vm.setNewMode();
            }

            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.getDetailYearMonth, {employeeId: vm.employeeId}).done(data => {
                    vm.oneMonthOrYearUpperLimit(nts.uk.time.parseTime(data.oneMonthUpperLimit, true).format());
                });
            } else {
                vm.$ajax(PATH_API.getDetailYear, {employeeId: vm.employeeId}).done(data => {
                    vm.oneMonthOrYearUpperLimit(nts.uk.time.parseTime(data.oneYearUpperLimit, true).format());
                });
            }
        }

        created() {
            const vm = this;

            $('#txt-year-error-time').trigger("validate");
            $('#txt-year-alarm-time').trigger("validate");
            $('#txt-year').trigger("validate");

            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
            if (vm.isUpdate) {
                $("#txt-year-error-time").focus();
            } else {
                $("#txt-year").focus();
            }
        }

        setNewMode() {
            const vm = this;
            vm.newMode(true);
            vm.isUpdate = false;
            vm.deleteEnable(false);
            vm.currentSelectItem(new SettingModel(null, vm.employeeId));
            vm.currentCodeSelect(null);
            vm.updateEnable(false);
            $('input.nts-input').trigger("validate");
            $("#txt-year").focus();
        }

        addOrUpdateClick() {
            const vm = this;
            $('input.nts-input').trigger("validate");

            setTimeout(() => {
                if (!$('.nts-editor').ntsError("hasError")) {
                    if (vm.isUpdate) {
                        vm.updateData();
                        return;
                    } else
                        vm.register();
                }
            }, 100);
        }

        register() {
            const vm = this;
            let yearOrYearMonth = vm.currentSelectItem().yearOrYearMonthValue().toString().length == 4 ?
                vm.currentSelectItem().yearOrYearMonthValue() : Number(vm.currentSelectItem().yearOrYearMonthValue().toString().replace("/", ""));
            if (yearOrYearMonth == 0 || nts.uk.text.isNullOrEmpty(vm.currentSelectItem().employeeId())) {
                return;
            }

            vm.$blockui("invisible");

            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.addAgreementMonthSetting, new AddUpdateMonthSettingModel(vm.currentSelectItem()))
                    .done(listError => {
                        if (listError.length > 0) {
                            let errorCode = _.split(listError[0], ',');
                            vm.$dialog.error({
                                messageId: errorCode[0],
                                messageParams: [vm.$i18n(errorCode[1]), vm.$i18n(errorCode[2])]
                            });
                            return;
                        }
                        vm.$dialog.info({messageId: "Msg_15"});
                        vm.reloadData(yearOrYearMonth);
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            } else {
                vm.$ajax(PATH_API.addAgreementYearSetting, new AddUpdateYearSettingModel(vm.currentSelectItem()))
                    .done(listError => {
                        if (listError.length > 0) {
                            let errorCode = _.split(listError[0], ',');
                            vm.$dialog.error({
                                messageId: errorCode[0],
                                messageParams: [vm.$i18n(errorCode[1]), vm.$i18n(errorCode[2])]
                            });
                            return;
                        }
                        vm.$dialog.info({messageId: "Msg_15"});
                        vm.reloadData(yearOrYearMonth);
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

            vm.$blockui("invisible");

            if (vm.isYearMonth) {
                vm.$ajax(PATH_API.updateAgreementMonthSetting, new AddUpdateMonthSettingModel(vm.currentSelectItem()))
                    .done(listError => {
                        if (listError.length > 0) {
                            let errorCode = _.split(listError[0], ',');
                            vm.$dialog.error({
                                messageId: errorCode[0],
                                messageParams: [vm.$i18n(errorCode[1]), vm.$i18n(errorCode[2])]
                            });
                            return;
                        }
                        vm.$dialog.info({messageId: "Msg_15"});
                        vm.reloadData(vm.currentCodeSelect(), true);
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => vm.$blockui("clear"));
            } else {
                vm.$ajax(PATH_API.updateAgreementYearSetting, new AddUpdateYearSettingModel(vm.currentSelectItem()))
                    .done(listError => {
                        if (listError.length > 0) {
                            let errorCode = _.split(listError[0], ',');
                            vm.$dialog.error({
                                messageId: errorCode[0],
                                messageParams: [vm.$i18n(errorCode[1]), vm.$i18n(errorCode[2])]
                            });
                            return;
                        }
                        vm.$dialog.info({messageId: "Msg_15"});
                        vm.reloadData(vm.currentCodeSelect(), true);

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
                                vm.$dialog.info(nts.uk.resource.getMessage("Msg_16", [])).then(() => {
                                    vm.reloadData(Number(vm.currentSelectItem().yearOrYearMonthValue().toString().replace("/", "")), true);
                                });
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => vm.$blockui("clear"));
                    } else {
                        vm.$ajax(PATH_API.removeAgreementYearSetting, new DeleteYearSettingModel(vm.employeeId, vm.currentSelectItem().yearOrYearMonthValue()))
                            .done(function () {
                                vm.$dialog.info(nts.uk.resource.getMessage("Msg_16", [])).then(() => {
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


        reloadData(oldSelectCode: number, isRemove?: boolean) {
            // const vm = this;
            // let oldSelectIndex = _.findIndex(vm.listItemDataGrid(), item => { return item.yearOrYearMonthValue == oldSelectCode; });
            //
            // vm.listItemDataGrid([]);
            // if (vm.isYearMonth) {
            //     vm.$ajax(PATH_API.getDetailYearMonth, { employeeId: vm.employeeId }).done(data => {
            //         if (data && data.length > 0) {
            //             _.forEach(data, item => {
            //                 vm.listItemDataGrid.push(new ShowListModel(item.yearMonthValue, item.errorOneMonth, item.alarmOneMonth));
            //             });
            //             vm.isUpdate = true;
            //             if (isRemove && isRemove == true) {
            //                 vm.currentCodeSelect(vm.getNewSelectRemove(oldSelectIndex));
            //             } else {
            //                 vm.currentCodeSelect(oldSelectCode);
            //             }
            //
            //         } else {
            //             vm.setNewMode();
            //         }
            //     });
            // } else {
            //     vm.$ajax(PATH_API.getDetailYear, { employeeId: vm.employeeId }).done(data => {
            //         if (data && data.length) {
            //             _.forEach(data, item => {
            //                 vm.listItemDataGrid.push(new ShowListModel(item.yearValue, item.errorOneYear, item.alarmOneYear));
            //             });
            //             vm.isUpdate = true;
            //             if (isRemove && isRemove == true) {
            //                 vm.currentCodeSelect(vm.getNewSelectRemove(oldSelectIndex));
            //             } else {
            //                 vm.currentCodeSelect(oldSelectCode);
            //             }
            //         } else {
            //             vm.setNewMode();
            //         }
            //     });
            // }
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

        closeDialogK() {
            const vm = this;
            vm.$window.close();
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
        errorOneYearOrYearMonth: number = null;
        alarmOneYearOrYearMonth: number = null;
        yearOrYearMonthFormat: string;

        constructor(yearOrYearMonthValue: number, errorOneYearOrYearMonth: number, alarmOneYearOrYearMonth: number) {
            this.yearOrYearMonthValue = yearOrYearMonthValue;
            this.errorOneYearOrYearMonth = errorOneYearOrYearMonth || null;
            this.alarmOneYearOrYearMonth = alarmOneYearOrYearMonth || null;
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

        employeeId: string = ""; //社員ID
        yearMonth: number; //年月
        errorTime: number; //年月エラー時間
        alarmTime: number; //年月アラーム時間

        constructor(data: SettingModel) {
            if (!data) return;
            this.employeeId = data.employeeId();
            // this.YearMonth = Number(data.yearOrYearMonthValue().toString().replace("/", ""));
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
            this.year = Number(data.yearOrYearMonthValue());
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
        employeeId?: string;
        employeeCode: string;
        employeeName: string;
        isYearMonth: boolean;
    }
}
