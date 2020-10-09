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

        constructor() {
            super();
            const vm = this;

            vm.specialConditionList = ko.observableArray([
                new RadioModel(true, vm.$i18n("KMK008_177")),
                new RadioModel(false, vm.$i18n("KMK008_178"))
            ]);

            vm.yearSpecialConditionList = ko.observableArray([
                new RadioModel(true, vm.$i18n("KMK008_181")),
                new RadioModel(false, vm.$i18n("KMK008_182"))
            ]);
        }

        created() {
            const vm = this;

            vm.$ajax(PATH_API.getData).done(data => {
                if (data) {
                    vm.operationSetting(new OperationSettingModel(data));
                }
            });

            _.extend(window, {vm});
        }


        mounted() {
            $("#combo-box-month").focus();
        }

        register() {
            const vm = this;

            // vm.$blockui("show");
            vm.$ajax(PATH_API.registerData, new OperationSettingModelUpdate(vm.operationSetting()))
                .done(res => {
                    vm.$dialog.info({messageId: "Msg_15"});
                    vm.closeDialog();
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    console.log("123213")
                })

            // vm.$blockui("hide");
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }
    }

    export class OperationSettingModel {
        startingMonthEnum: any;
        startingMonth: KnockoutObservable<number>;

        closureDateEnum: any;
        closureDate: KnockoutObservable<number>;

        specialConditionApplicationUse: KnockoutObservable<boolean>;
        yearSpecialConditionApplicationUse: KnockoutObservable<boolean>;

        constructor(data: any) {
            const vm = this;
            if (data) {
                vm.startingMonthEnum = data.startingMonthEnum;
                vm.startingMonth = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.startingMonth : 1);

                vm.closureDateEnum = data.closureDateEnum;
                vm.closureDate = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.closureDate : 1);

                vm.specialConditionApplicationUse = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.specialConditionApplicationUse : true);
                vm.yearSpecialConditionApplicationUse = ko.observable(data.agreementOperationSettingDetailDto ? data.agreementOperationSettingDetailDto.yearSpecicalConditionApplicationUse : true);
            } else {
                vm.startingMonthEnum = new Array();
                vm.startingMonth = ko.observable(1);

                vm.closureDateEnum = new Array();
                vm.closureDate = ko.observable(1);

                vm.specialConditionApplicationUse = ko.observable(true);
                vm.yearSpecialConditionApplicationUse = ko.observable(true);
            }
        }
    }

    export class OperationSettingModelUpdate {
        startingMonth: number;
        closureDay: number;
        specialConditionApplicationUse: boolean;
        yearSpecicalConditionApplicationUse: boolean;

        lastDayOfMonth: boolean;

        constructor(data: OperationSettingModel) {
            const vm = this;
            vm.startingMonth = Number(data.startingMonth());
            vm.closureDay = Number(data.closureDate());
            vm.specialConditionApplicationUse = Boolean(data.specialConditionApplicationUse());
            vm.yearSpecicalConditionApplicationUse = Boolean(data.yearSpecialConditionApplicationUse());

            vm.lastDayOfMonth = vm.closureDay === 30;
        }
    }

    export class ComboBoxModel {
        value: string;
        localizedName: string;

        constructor(value: string, localizedName: string) {
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
