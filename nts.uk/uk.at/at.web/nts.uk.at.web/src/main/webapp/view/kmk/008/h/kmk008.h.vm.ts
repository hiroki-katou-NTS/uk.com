/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.h {

    const PATH_API = {
        getData: 'screen/at/kmk008/h/getInitDisplay',
        registerData: 'monthly/estimatedtime/unit/Register',
    };

    @bean()
    export class KMK008HViewModel extends ko.ViewModel {
        // Init
        classificationUseAtr: KnockoutObservable<boolean> = ko.observable(true); //３６協定単位設定.分類利用区分
        employmentUseAtr: KnockoutObservable<boolean> = ko.observable(true); //３６協定単位設定.雇用利用区分
        workPlaceUseAtr: KnockoutObservable<boolean> = ko.observable(true); //３６協定単位設定.職場利用区分


        constructor() {
            super();
            const vm = this;

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getData)
                .done(data => {
                    if (data) {
                        vm.classificationUseAtr(data.classificationUseAtr);
                        vm.employmentUseAtr(data.employmentUseAtr);
                        vm.workPlaceUseAtr(data.workPlaceUseAtr);
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    vm.$blockui("clear");
                });
        }

        created() {
            const vm = this;

            _.extend(window, {vm});
        }


        mounted() {
            $("#checkboxEmp").focus();
        }

        submitAndCloseDialog() {
            var vm = this;

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.registerData,
                {
                    classificationUseAtr: vm.classificationUseAtr() ? 1 : 0,
                    employmentUseAtr: vm.employmentUseAtr() ? 1 : 0,
                    workPlaceUseAtr: vm.workPlaceUseAtr() ? 1 : 0
                })
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"});
                    vm.closeDialog();
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => vm.$blockui("clear"));
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }
    }
}



