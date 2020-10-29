/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.g {

    const PATH_API = {
        getData: "screen/at/kmk008/g/getInitDisplay",
        registerData: "monthly/estimatedtime/operationSet/register",
    };

    @bean()
    export class KMK008GViewModel extends ko.ViewModel {
        // Init
        operationSetting: KnockoutObservable<OperationSettingModel> = ko.observable(new OperationSettingModel(null));
        specialConditionList: KnockoutObservableArray<RadioModel>;
        yearSpecialConditionList: KnockoutObservableArray<RadioModel>;
        isUpdate: boolean = false;

        constructor() {
            super();
            const vm = this;

            vm.specialConditionList = ko.observableArray([
                new RadioModel(false, vm.$i18n("KMK008_177")),
                new RadioModel(true, vm.$i18n("KMK008_178"))
            ]);

            vm.yearSpecialConditionList = ko.observableArray([
                new RadioModel(false, vm.$i18n("KMK008_181")),
                new RadioModel(true, vm.$i18n("KMK008_182"))
            ]);

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getData)
                .done(data => {
                    if (data) {
                        vm.operationSetting(new OperationSettingModel(data));
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res);
                })
                .always(() => {
                    $('#combo-box-month').focus();
                    vm.$blockui("clear");
                });
        }

        created() {
            const vm = this;

            _.extend(window, {vm});
        }


        mounted() {

        }

        register() {
            const vm = this;

            vm.$blockui("invisible");

            vm.$ajax(PATH_API.registerData, new OperationSettingModelUpdate(vm.operationSetting()))
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.isUpdate = true;
                        vm.closeDialog();
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res).then(() => {
                        $('#combo-box-month').focus();
                    })
                })
                .always(() => vm.$blockui("clear"));
        }

        closeDialog() {
            const vm = this;
            if (vm.isUpdate) {
                vm.isUpdate = false;
                vm.$window.close({
                    specialConditionApplicationUse: vm.operationSetting().specialConditionApplicationUse()
                })
            } else {
                vm.$window.close()
            }
        }
    }

    export class OperationSettingModel {
        startingMonthEnum: Array<ComboBoxModel>; //Enum: ３６協定起算月
        startingMonth: KnockoutObservable<number>; //３６協定運用設定.起算月

        closureDateEnum: Array<ComboBoxModel>; //Enum: ３６協定締め日
        closureDate: KnockoutObservable<number>; //３６協定運用設定.締め日

        lastDayOfMonth: boolean;
        specialConditionApplicationUse: KnockoutObservable<boolean>; //３６協定運用設定.特別条項申請を使用する
        yearSpecialConditionApplicationUse: KnockoutObservable<boolean>; //３６協定運用設定.年間の特別条項申請を使用する

        constructor(data: any) {
            const vm = this;
            if (data) {
                vm.startingMonthEnum = data.startingMonthEnum;
                vm.startingMonth = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.startingMonth : 1);

                vm.closureDateEnum = data.closureDateEnum;
                vm.lastDayOfMonth = data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.lastDayOfMonth : false;
                if (vm.lastDayOfMonth) {
                    vm.closureDate = ko.observable(data.closureDateEnum[data.closureDateEnum.length - 1].value);
                } else {
                    vm.closureDate = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.closureDate : 1);
                }

                vm.specialConditionApplicationUse = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.specialConditionApplicationUse : true);
                vm.yearSpecialConditionApplicationUse = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.yearSpecicalConditionApplicationUse : true);
            } else {
                vm.startingMonthEnum = [];
                vm.startingMonth = ko.observable(1);

                vm.closureDateEnum = [];
                vm.closureDate = ko.observable(1);

                vm.specialConditionApplicationUse = ko.observable(true);
                vm.yearSpecialConditionApplicationUse = ko.observable(true);
            }
        }
    }

    export class OperationSettingModelUpdate {
        startingMonth: number; //３６協定運用設定.起算月
        closureDay: number; //３６協定運用設定.締め日
        specialConditionApplicationUse: boolean; //３６協定運用設定.特別条項申請を使用する
        yearSpecicalConditionApplicationUse: boolean; //３６協定運用設定.年間の特別条項申請を使用する

        lastDayOfMonth: boolean;

        constructor(data: OperationSettingModel) {
            const vm = this;
            vm.startingMonth = Number(data.startingMonth());
            vm.closureDay = Number(data.closureDate());
            vm.specialConditionApplicationUse = Boolean(data.specialConditionApplicationUse());
            vm.yearSpecicalConditionApplicationUse = Boolean(data.yearSpecialConditionApplicationUse());

            vm.lastDayOfMonth = vm.closureDay === data.closureDateEnum[data.closureDateEnum.length - 1].value;
        }
    }

    export class ComboBoxModel {
        value: number;
        localizedName: string;

        constructor(value: number, localizedName: string) {
            this.value = value;
            this.localizedName = localizedName;
        }
    }

    class RadioModel {
        value: boolean;
        localizedName: string;

        constructor(value: boolean, localizedName: string) {
            const vm = this;
            vm.value = value;
            vm.localizedName = localizedName;
        }
    }
}
