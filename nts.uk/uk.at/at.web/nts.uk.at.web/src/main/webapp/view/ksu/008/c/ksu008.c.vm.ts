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
            vm.sourceCode = ko.observable("");
            vm.sourceName = ko.observable("");
            vm.destinationCode = ko.observable("");
            vm.destinationName = ko.observable("");
            vm.isOverwrite = ko.observable(false);
        }

        created(params: any) {
            const vm = this;
            vm.sourceCode(params.sourceCode);
            vm.sourceName(params.sourceName);
        }

        mounted() {
            $("#C2_3").focus();
        }

        duplicate(): void {
            let vm = this;
            vm.$validate(".nts-input:not(:disabled)").then((valid: boolean) => {
                if (valid) {
                    let command: IDuplicateItemOutputSettingInfoCommand = {
                        originalCode: vm.sourceCode(),
                        destinationCode: vm.destinationCode(),
                        destinationName: vm.destinationName(),
                        overwrite: vm.isOverwrite()
                    };
                    if(_.isEqual(vm.destinationCode(), vm.sourceCode())){
                        vm.$dialog.error({ messageId: 'Msg_355' });
                        vm.$blockui("clear");
                        return;
                    }

                    vm.$ajax(API.duplicate, command).then(data => {
                        let result = {
                            destinationCode: vm.destinationCode()
                        };
                        vm.$dialog.info({messageId: 'Msg_15'}).then(function () {
                            vm.$window.close(result);
                        });
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("clear");
                    });
                }
            });
        }

        closeDialog(): void {
            let vm = this;
            vm.$window.close();
        }

    }

    interface IDuplicateItemOutputSettingInfoCommand {
        originalCode: string;

        destinationCode: string;

        destinationName: string;

        overwrite: boolean;
    }
}


