module nts.uk.at.view.ksu011.b.viewmodel {

    const API = {
        getAllSetting: "ctx/at/aggregation/scheduledailytable/printsetting/get-all",
        getSetting: "ctx/at/aggregation/scheduledailytable/printsetting/get",
        registerSetting: "ctx/at/aggregation/scheduledailytable/printsetting/register",
        deleteSetting: "ctx/at/aggregation/scheduledailytable/printsetting/delete",
        getCountItems: "at/shared/scherec/totaltimes/getallitem"
    };

    @bean()
    class Ksu011BViewModel extends ko.ViewModel {
        outputItems: KnockoutObservableArray<any>;
        selectedOutputItemCode: KnockoutObservable<string>;

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        useSignStamp: KnockoutObservable<boolean>;
        columnName1: KnockoutObservable<string>;
        columnName2: KnockoutObservable<string>;
        columnName3: KnockoutObservable<string>;
        columnName4: KnockoutObservable<string>;
        columnName5: KnockoutObservable<string>;
        columnName6: KnockoutObservable<string>;
        personalCounter: KnockoutObservableArray<number>;
        readonlyText1: KnockoutObservable<string>;
        workplaceCounter: KnockoutObservableArray<number>;
        readonlyText2: KnockoutObservable<string>;
        comment: KnockoutObservable<string>;
        transferDisplay: KnockoutObservable<boolean>;
        supporterSchedulePrintMethod: KnockoutObservable<number>;
        supporterRecordPrintMethod: KnockoutObservable<number>;

        newMode: KnockoutObservable<boolean>;
        screenUpdated: boolean = false;

        constructor() {
            super();
            const vm = this;
            vm.outputItems = ko.observableArray([]);
            vm.selectedOutputItemCode = ko.observable(null);

            vm.code = ko.observable(null);
            vm.name = ko.observable(null);
            vm.useSignStamp = ko.observable(false);
            vm.columnName1 = ko.observable(null);
            vm.columnName2 = ko.observable(null);
            vm.columnName3 = ko.observable(null);
            vm.columnName4 = ko.observable(null);
            vm.columnName5 = ko.observable(null);
            vm.columnName6 = ko.observable(null);
            vm.personalCounter = ko.observableArray([]);
            vm.readonlyText1 = ko.observable(null);
            vm.workplaceCounter = ko.observableArray([]);
            vm.readonlyText2 = ko.observable(null);
            vm.comment = ko.observable(null);
            vm.transferDisplay = ko.observable(false);
            vm.supporterSchedulePrintMethod = ko.observable(0);
            vm.supporterRecordPrintMethod = ko.observable(0);

            vm.newMode = ko.observable(true);
        }

        created(param: any) {
            const vm = this;
            vm.selectedOutputItemCode.subscribe(value => {
                nts.uk.ui.errors.clearAll();
                if (value) {
                    vm.getSettingByCode(value);
                    vm.newMode(false);
                    $("#B3_3").focus();
                } else {
                    vm.code(null);
                    vm.name(null);
                    vm.useSignStamp(false);
                    vm.columnName1(null);
                    vm.columnName2(null);
                    vm.columnName3(null);
                    vm.columnName4(null);
                    vm.columnName5(null);
                    vm.columnName6(null);
                    vm.personalCounter([]);
                    vm.readonlyText1(null);
                    vm.workplaceCounter([]);
                    vm.readonlyText2(null);
                    vm.comment(null);
                    vm.transferDisplay(false);
                    vm.supporterSchedulePrintMethod(0);
                    vm.supporterRecordPrintMethod(0);
                    vm.newMode(true);
                    $("#B3_2").focus();
                }
            });
            vm.useSignStamp.subscribe(value => {
                if (!value) {
                    vm.$errors("clear");
                }
            });
            vm.getAllSetting(param.itemCode);
        }

        getAllSetting(code?: string) {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.getAllSetting).done(settings => {
                vm.outputItems(settings || []);
                if (vm.outputItems().length > 0) {
                    if (code) {
                        vm.selectedOutputItemCode() == code ? vm.selectedOutputItemCode.valueHasMutated() : vm.selectedOutputItemCode(code);
                    } else {
                        vm.selectedOutputItemCode(vm.outputItems()[0].code);
                    }
                } else {
                    vm.selectedOutputItemCode() == null ? vm.selectedOutputItemCode.valueHasMutated() : vm.selectedOutputItemCode(null);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        getSettingByCode(code: string) {
            const vm = this;
            vm.$blockui("show");
            $.when(vm.$ajax("at", API.getSetting, code), vm.$ajax("at", API.getCountItems)).done((setting: any, countItems: Array<any>) => {
                if (setting) {
                    vm.code(setting.code);
                    vm.name(setting.name);
                    vm.useSignStamp(setting.signStampUseAtr == 1);
                    vm.columnName1(_.size(setting.titles) > 0 ? setting.titles[0] : null);
                    vm.columnName2(_.size(setting.titles) > 1 ? setting.titles[1] : null);
                    vm.columnName3(_.size(setting.titles) > 2 ? setting.titles[2] : null);
                    vm.columnName4(_.size(setting.titles) > 3 ? setting.titles[3] : null);
                    vm.columnName5(_.size(setting.titles) > 4 ? setting.titles[4] : null);
                    vm.columnName6(_.size(setting.titles) > 5 ? setting.titles[5] : null);
                    vm.personalCounter(setting.personalCounter || []);
                    vm.workplaceCounter(setting.workplaceCounter || []);
                    vm.comment(setting.comment);
                    vm.transferDisplay(setting.transferDisplay == 1);
                    vm.supporterSchedulePrintMethod(setting.supporterSchedulePrintMethod);
                    vm.supporterRecordPrintMethod(setting.supporterRecordPrintMethod);

                    let pCountNames: Array<string> = [];
                    vm.personalCounter().forEach((no: number) => {
                        const item = _.find(countItems, i => i.totalCountNo == no);
                        if (item) pCountNames.push(item.totalTimesName);
                    });
                    vm.readonlyText1(pCountNames.join("、"));

                    let wCountNames: Array<string> = [];
                    vm.workplaceCounter().forEach((no: number) => {
                        const item = _.find(countItems, i => i.totalCountNo == no);
                        if (item) wCountNames.push(item.totalTimesName);
                    });
                    vm.readonlyText2(wCountNames.join("、"));
                } else {
                    vm.createNew();
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        createNew() {
            const vm = this;
            vm.selectedOutputItemCode(null);
        }

        registerSetting() {
            const vm = this;
            const titles: Array<string> = [];
            if (!_.isEmpty(vm.columnName1())) titles.push(vm.columnName1());
            if (!_.isEmpty(vm.columnName2())) titles.push(vm.columnName2());
            if (!_.isEmpty(vm.columnName3())) titles.push(vm.columnName3());
            if (!_.isEmpty(vm.columnName4())) titles.push(vm.columnName4());
            if (!_.isEmpty(vm.columnName5())) titles.push(vm.columnName5());
            if (!_.isEmpty(vm.columnName6())) titles.push(vm.columnName6());
            if (vm.useSignStamp() && titles.length == 0) {
                this.$errors({ "#B4_3": { messageId: "Msg_2222" } });
            }
            vm.$validate().then((valid: boolean) => {
                if (valid) {
                    const command = {
                        code: vm.code(),
                        name: vm.name(),
                        signStampUseAtr: vm.useSignStamp() ? 1 : 0,
                        titles: titles,
                        personalCounter: vm.personalCounter(),
                        workplaceCounter: vm.workplaceCounter(),
                        comment: vm.comment(),
                        transferDisplay: vm.transferDisplay() ? 1 : 0,
                        supporterSchedulePrintMethod: vm.supporterSchedulePrintMethod(),
                        supporterRecordPrintMethod: vm.supporterRecordPrintMethod(),
                        newMode: vm.newMode()
                    };
                    vm.$blockui("show");
                    vm.$ajax(API.registerSetting, command).done(() => {
                        vm.screenUpdated = true;
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.getAllSetting(command.code);
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        deleteSetting() {
            const vm = this;
            vm.$dialog.confirm({messageId: 'Msg_18'}).then((result) => {
                if (result == 'yes') {
                    vm.$blockui("show");
                    vm.$ajax("at", API.deleteSetting, vm.selectedOutputItemCode()).done(() => {
                        vm.screenUpdated = true;
                        vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                            const index = _.findIndex(vm.outputItems(), i => i.code == vm.selectedOutputItemCode());
                            let nextCode = null;
                            if (vm.outputItems().length > 1) {
                                nextCode = index == vm.outputItems().length - 1 ? vm.outputItems()[index - 1].code : vm.outputItems()[index + 1].code;
                            }
                            vm.getAllSetting(nextCode);
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        copySetting() {
            const vm = this;
            const setting = _.find(vm.outputItems(), i => i.code == vm.selectedOutputItemCode());
            vm.$window.modal("/view/ksu/011/d/index.xhtml", {sourceCode: setting.code, sourceName: setting.name}).then((newCode) => {
                if (newCode) {
                    vm.screenUpdated = true;
                    vm.getAllSetting(newCode);
                }
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close(vm.screenUpdated ? {itemCode: vm.selectedOutputItemCode()} : null);
        }

        openDialogC(target: string) {
            const vm = this;
            const params = {
                target: target,
                totalNos: target == "personal" ? vm.personalCounter() : vm.workplaceCounter()
            };
            vm.$window.modal("/view/ksu/011/c/index.xhtml", params).then((selectedItems: Array<any>) => {
                if (selectedItems) {
                    if (target == "personal") {
                        vm.personalCounter(selectedItems.map(i => parseInt(i.no)));
                        vm.readonlyText1(selectedItems.map(i => i.name).join("、"));
                    } else {
                        vm.workplaceCounter(selectedItems.map(i => parseInt(i.no)));
                        vm.readonlyText2(selectedItems.map(i => i.name).join("、"));
                    }
                }
            });
        }
    }
}