/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.i {

    const PATH_API = {
        getData: 'screen/at/kmk008/i/getInitDisplay',
        registerData: 'monthly/estimatedtime/unitOfApprove/register',
    };

    @bean()
    export class KMK008IViewModel extends ko.ViewModel {
        useWorkPlace: KnockoutObservable<boolean> = ko.observable(true); //職場を利用する

        constructor() {
            super();
            const vm = this;

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getData)
                .done(data => {
                    if (data) {
                        vm.useWorkPlace(data.useWorkplace);
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res);
                })
                .always(() => {
                    vm.$blockui("clear");
                });
        }

        created() {
            const vm = this;
        }

        mounted() {
            $('.chk_I13').focus();
        }

        submitAndCloseDialog() {
            let vm = this;

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.registerData, {useWorkplace: vm.useWorkPlace()})
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.closeDialog();
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res).then(() => {
                        $('.chk_I13').focus();
                    })
                })
                .always(() => vm.$blockui("clear"));
        }

        closeDialog() {
            let vm = this;
            vm.$window.close();
        }
    }
}