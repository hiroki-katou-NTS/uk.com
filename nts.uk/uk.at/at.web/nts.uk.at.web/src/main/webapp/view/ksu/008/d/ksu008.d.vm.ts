module nts.uk.at.ksu008.d {
    const API = {
        init: "screen/at/ksu/008/form9/init-detail-setting",
        update: "screen/at/ksu/008/form9/update-detail-setting"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        d12: any;                   // D1_2
        roundingUnit: KnockoutObservableArray<ItemModel>;   // D1_3
        roundingMode: KnockoutObservableArray<any>;         // D1_4
        selectedRoundUnit: KnockoutObservable<string>;      // D1_3_1
        selectedRoundMode: KnockoutObservable<number>;      // D1_4_1
        isAttrBlankIfZero: KnockoutObservable<boolean>;     // D2_2

        constructor() {
            super();
            let vm = this;
            vm.roundingUnit = ko.observableArray([
                new ItemModel('0', vm.$i18n('Enum_RoundingUnit_ONE_DIGIT')),
                new ItemModel('1', vm.$i18n('Enum_RoundingUnit_TWO_DIGIT')),
                new ItemModel('2', vm.$i18n('Enum_RoundingUnit_THREE_DIGIT')),
                new ItemModel('3', vm.$i18n('Enum_RoundingUnit_FOUR_DIGIT')),
                new ItemModel('4', vm.$i18n('Enum_RoundingUnit_FIVE_DIGIT'))
            ]);
            vm.roundingMode = ko.observableArray([
                new BoxModel(0, vm.$i18n('Enum_RoundingMethod_TRUNCATION')),  // D1_4_1
                new BoxModel(1, vm.$i18n('Enum_RoundingMethod_ROUND_UP')),    // D1_4_2
                new BoxModel(2, vm.$i18n('Enum_RoundingMethod_DOWN_4_UP_5'))  // D1_4_3
            ]);
            vm.d12 = ko.observable("小数点以下");
            vm.selectedRoundUnit = ko.observable('0');
            vm.selectedRoundMode = ko.observable(1);
            vm.isAttrBlankIfZero = ko.observable(true);

            vm.initData();
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
            $('#combo-box').focus();
        }

        initData(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API.init).then(data => {
                if (data) {
                    vm.selectedRoundUnit(data.roundingUnit.toString());
                    vm.selectedRoundMode(data.roundingMode);
                    vm.isAttrBlankIfZero(data.attributeBlankIfZero);
                }
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error);
                dfd.reject();
            }).always(() => vm.$blockui("clear"));

            return dfd.promise();
        }

        update(): void {
            let vm = this;
            let command: IUpdateDetailOutputSettingInfoCommand = {
                roundingUnit: parseInt(vm.selectedRoundUnit()),
                roundingMode: vm.selectedRoundMode(),
                attrBlankIfZero: vm.isAttrBlankIfZero(),
            };

            vm.$ajax(API.update, command).then(data => {
                vm.$dialog.info({messageId: 'Msg_15'}).then(function () {
                    vm.$window.close();
                });
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        closeDialog(): void {
            let vm = this;
            vm.$window.close();
        }

    }

    interface IUpdateDetailOutputSettingInfoCommand {
        roundingUnit: number;
        roundingMode: number;
        attrBlankIfZero: boolean;
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            let vm = this;
            vm.id = id;
            vm.name = name;
        }
    }
}


