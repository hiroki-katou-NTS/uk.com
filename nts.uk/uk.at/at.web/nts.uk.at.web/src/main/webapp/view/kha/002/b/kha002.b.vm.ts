module nts.uk.at.kha002.b {
    const API = {
        getItems: "at/screen/kha002/b/attendanceItems",
        getAll: "at/function/supportworklist/outputsetting/all",
        getOne: "at/function/supportworklist/outputsetting/",
        register: "at/function/supportworklist/outputsetting/register",
        delete: "at/function/supportworklist/outputsetting/delete/"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        aggregateUnit: KnockoutObservable<number>;
        layoutSettings: KnockoutObservableArray<any>;
        selectedLayout: KnockoutObservable<string>;

        setting: SupportWorkOutputSetting;

        allItemsBk: Array<any> = [];
        allItems: KnockoutObservableArray<any> = ko.observableArray([]);

        created(param?: any) {
            const vm = this;
            vm.aggregateUnit = ko.observable(param ? param.aggregateUnit : 0);
            vm.layoutSettings = ko.observableArray([]);
            vm.selectedLayout = ko.observable(null);

            vm.setting = new SupportWorkOutputSetting();

            vm.selectedLayout.subscribe(code => {
                vm.$errors("clear");
                if (code) {
                    vm.$blockui("show");
                    vm.$ajax(API.getOne + code).done(setting => {
                        vm.setting.bindData(setting, vm.allItemsBk);
                        _.defer(() => {
                            vm.allItems(_.cloneDeep(vm.allItemsBk));
                        });
                        $("#B3_3").focus();
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                } else {
                    vm.setting.bindData(null);
                    _.defer(() => {
                        vm.allItems(_.cloneDeep(vm.allItemsBk));
                    });
                    $("#B3_2").focus();
                }
            });

            vm.$ajax(API.getItems).done(items => {
                vm.allItemsBk = items || [];
                vm.allItems(_.cloneDeep(vm.allItemsBk));
                vm.getAllData(param ? param.code : null);
            }).fail(error => {
                vm.$dialog.error(error);
            });
        }

        getAllData(code?: string) {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.getAll).done(settings => {
                vm.layoutSettings(settings);
                if (_.isEmpty(settings)) {
                    vm.createNewSetting();
                } else {
                    if (code && _.find(settings, (s: any) => s.code == code)) {
                        vm.selectedLayout() == code ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(code);
                    } else {
                        vm.selectedLayout() == settings[0].code ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(settings[0].code);
                    }
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        createNewSetting() {
            const vm = this;
            vm.selectedLayout() == null ? vm.selectedLayout.valueHasMutated() : vm.selectedLayout(null);
        }

        registerSetting() {
            const vm = this;
            vm.$validate(".nts-input").then((valid: boolean) => {
                if (valid) {
                    if (vm.setting.items().length == 0) {
                        vm.$dialog.error({messageId: "Msg_3264"});
                        return;
                    }
                    vm.$blockui("show");
                    const command: any = vm.setting.toCommand();
                    command.mode = !!vm.selectedLayout() ? 1 : 0;
                    vm.$ajax(API.register, command).done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.getAllData(command.code);
                        });
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        deleteSetting() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: any) => {
                if (result === 'yes') {
                    vm.$blockui("show");
                    const currentIndex = _.findIndex(vm.layoutSettings(), i => i.code == vm.selectedLayout());
                    let nextCode: string = null;
                    if (vm.layoutSettings().length > 1) {
                        if (currentIndex == vm.layoutSettings().length - 1) nextCode = vm.layoutSettings()[currentIndex - 1].code;
                        else nextCode = vm.layoutSettings()[currentIndex + 1].code;
                    }
                    vm.$ajax(API.delete + vm.selectedLayout()).done(() => {
                        vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                            vm.getAllData(nextCode);
                        });
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close({code: vm.selectedLayout()});
        }
    }

    class SupportWorkOutputSetting {
        // コード
        code: KnockoutObservable<string>;

        // 名称
        name: KnockoutObservable<string>;

        // レイアウト詳細設定.社員抽出条件
        extractCondition: KnockoutObservable<number>;

        // レイアウト詳細設定.明細表示設定.明細を表示する
        displayDetailAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.職場合計の表示設定.1日の合計を表示する
        displaySumOneDayAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.職場合計の表示設定.職場・応援計を表示する
        displaySumSupportWorkplaceAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.職場合計の表示設定.応援内訳を表示する
        displaySumSupportDetailAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.職場合計の表示設定.職場計を表示する
        displaySumWorkplaceAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.総合計の表示設定.総合計を表示する
        displayTotalSumAtr: KnockoutObservable<boolean>;

        // レイアウト詳細設定.総合計の表示設定.職場・応援総合計を表示する
        displayTotalSumSupportWorkplaceAtr: KnockoutObservable<boolean>;

        // 改ページする
        breakPageAtr: KnockoutObservable<number>;

        items: KnockoutObservableArray<any>;

        constructor() {
            this.code = ko.observable("");
            this.name = ko.observable("");
            this.extractCondition = ko.observable(0);
            this.displayDetailAtr = ko.observable(false);
            this.displaySumOneDayAtr = ko.observable(false);
            this.displaySumSupportWorkplaceAtr = ko.observable(false);
            this.displaySumSupportDetailAtr = ko.observable(false);
            this.displaySumWorkplaceAtr = ko.observable(false);
            this.displayTotalSumAtr = ko.observable(false);
            this.displayTotalSumSupportWorkplaceAtr = ko.observable(false);
            this.breakPageAtr = ko.observable(0);
            this.items = ko.observableArray([]);
        }

        bindData(data?: any, allItems?: Array<any>) {
            this.code(data ? data.code : "");
            this.name(data ? data.name : "");
            this.extractCondition(data ? data.extractCondition : 0);
            this.displayDetailAtr(data ? data.displayDetailAtr == 1 : false);
            this.displaySumOneDayAtr(data ? data.displaySumOneDayAtr == 1 : false);
            this.displaySumSupportWorkplaceAtr(data ? data.displaySumSupportWorkplaceAtr == 1 : false);
            this.displaySumSupportDetailAtr(data ? data.displaySumSupportDetailAtr == 1 : false);
            this.displaySumWorkplaceAtr(data ? data.displaySumWorkplaceAtr == 1 : false);
            this.displayTotalSumAtr(data ? data.displayTotalSumAtr == 1 : false);
            this.displayTotalSumSupportWorkplaceAtr(data ? data.displayTotalSumSupportWorkplaceAtr == 1 : false);
            this.breakPageAtr(data ? data.breakPageAtr : 0);
            this.items([]);
            if (data && data.itemIds) {
                data.itemIds.forEach((id: number) => {
                    const item = _.find(allItems, i => i.attendanceItemId == id);
                    if (item) this.items.push(item);
                });
            }

        }

        toCommand() {
            return {
                code: this.code(),
                name: this.name(),
                extractCondition: this.extractCondition(),
                displayDetailAtr: this.displayDetailAtr() ? 1 : 0,
                displaySumOneDayAtr: this.displaySumOneDayAtr() ? 1 : 0,
                displaySumSupportWorkplaceAtr: this.displaySumSupportWorkplaceAtr() ? 1 : 0,
                displaySumSupportDetailAtr: this.displaySumSupportDetailAtr() ? 1 : 0,
                displaySumWorkplaceAtr: this.displaySumWorkplaceAtr() ? 1 : 0,
                displayTotalSumAtr: this.displayTotalSumAtr() ? 1 : 0,
                displayTotalSumSupportWorkplaceAtr: this.displayTotalSumSupportWorkplaceAtr() ? 1 : 0,
                breakPageAtr: this.breakPageAtr(),
                itemIds: this.items().map(i => i.attendanceItemId),
            }
        }
    }
}


