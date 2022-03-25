module nts.uk.at.view.ksu011.d.viewmodel {

    const API = {
        duplicateSetting: "ctx/at/aggregation/scheduledailytable/printsetting/duplicate"
    };

    @bean()
    class Ksu011DViewModel extends ko.ViewModel {
        sourceCode: KnockoutObservable<string>;
        sourceName: KnockoutObservable<string>;
        desCode: KnockoutObservable<string>;
        desName: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;

        constructor(params: any) {
            super();
            const vm = this;
            vm.sourceCode = ko.observable(params.sourceCode);
            vm.sourceName = ko.observable(params.sourceName);
            vm.desCode = ko.observable(null);
            vm.desName = ko.observable(null);
            vm.checked = ko.observable(false);
        }

        mounted() {
            $("#D1_1").width($("#D2_1").width());
            $("#D1_2").width($("#D2_2").width());
            $("#D1_3").width($("#D2_3").width());
            $("#D1_4").width($("#D2_4").width());
            $("#D2_3 input")[0].focus();
        }

        decideCopy() {
            const vm = this;
            if (vm.sourceCode() == vm.desCode()) {
                vm.$dialog.error({messageId: "Msg_355"}).then(() => {
                    $("#D2_3 input")[0].focus();
                });
                return;
            }
            vm.$validate().then((valid: boolean) => {
                if (valid) {
                    const command = {
                        fromCode: vm.sourceCode(),
                        toCode: vm.desCode(),
                        toName: vm.desName(),
                        overwrite: vm.checked()
                    };
                    vm.$blockui("show");
                    vm.$ajax(API.duplicateSetting, command).done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.$window.close(command.toCode);
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error).then(() => {
                            if (error.messageId == "Msg_2117") $("#D2_3 input")[0].focus();
                        });
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        cancel() {
            const vm = this;
            vm.$window.close();
        }
    }
}