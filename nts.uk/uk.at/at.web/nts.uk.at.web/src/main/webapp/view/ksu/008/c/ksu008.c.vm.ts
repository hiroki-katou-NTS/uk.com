module nts.uk.at.ksu008.c {
    const API = {
        duplicate: "screen/at/ksu/008/form9/duplicate"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        sourceCode: KnockoutObservable<string>;
        sourceName: KnockoutObservable<string>;
        destinationCode: KnockoutObservable<string>;
        destinationName: KnockoutObservable<string>;
        isOverwrite: KnockoutObservable<boolean>;

        constructor() {
            super();
            const vm = this;
            // vm.sourceCode = ko.observable("Sample001");
            // vm.sourceName = ko.observable("dest name12336647899999999ee");
            vm.destinationCode = ko.observable("");
            vm.destinationName = ko.observable("");
            vm.isOverwrite = ko.observable(false);

        }

        created() {
            const vm = this;
            vm.$window.shared("dataShareKsu008C").done((data: any) => {
                if (data) {
                    vm.sourceCode(data.sourceCode);
                    vm.sourceName(data.sourceName);
                }
            });

        }

        mounted() {
            const vm = this;
        }

        duplicate(): void {
            let vm = this;
            let command: IDuplicateItemOutputSettingInfoCommand = {
                originalCode: vm.sourceCode(),
                destinationCode: vm.destinationCode(),
                destinationName: vm.destinationName(),
                isOverwrite: vm.isOverwrite()
            };

            vm.$ajax(API.duplicate, command).then(data => {
                vm.$dialog.info({messageId: 'Msg_15'});
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

    export interface IDuplicateItemOutputSettingInfoCommand {
        originalCode: string;

        destinationCode: string;

        destinationName: string;

        isOverwrite: boolean;
    }
}


