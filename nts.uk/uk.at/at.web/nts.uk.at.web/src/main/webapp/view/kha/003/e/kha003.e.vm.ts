module nts.uk.at.kha003.e {

    const API = {
        update: 'at/screen/kha003/e/update',
        create: 'at/screen/kha003/e/register'
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        enable: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        isCopy: KnockoutObservable<boolean>;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        manHour: CodeName = new CodeName('', '');
        e1718ManHour: CodeName = new CodeName('', '');
        isUpdateMode: KnockoutObservable<boolean>;

        constructor() {
            super();
            const vm = this;
            vm.enable = ko.observable(true);
            vm.required = ko.observable(true);
            vm.isCopy = ko.observable(false);

            vm.startDateString = ko.observable("");
            vm.endDateString = ko.observable("");
            vm.dateValue = ko.observable({});

            vm.startDateString.subscribe(function (value) {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });

            vm.endDateString.subscribe(function (value) {
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });
            vm.isUpdateMode = ko.observable(false);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('kha003ERequiredData').done((data: any) => {
                console.log("data:" + data.code)
                vm.manHour.code(data.code);
                vm.manHour.name(data.name);
            })
        }

        mounted() {
            const vm = this;
        }

        /**
         * Event on click cancel button.
         */
        public cancel(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Event on click decide button.
         */
        public decide(): void {
            const vm = this;
            vm.$validate('#E1_7', '#E1_8').then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                else {
                    let command = {
                        sourcecode: vm.e1718ManHour.code(),
                        destinationCode: vm.manHour.code(),
                        name: vm.e1718ManHour.name(),
                        isCopy: vm.isCopy(),
                    };
                    vm.$blockui("invisible");
                    vm.$ajax(vm.isUpdateMode() ? API.update : API.create, command).done((data) => {
                        vm.$dialog.info({messageId: "Msg_15"})
                            .then(() => {
                                let shareData={
                                    duplicatedCode:vm.e1718ManHour.code()
                                }
                                vm.$window.storage('kha003EShareData',shareData).then(() => {
                                    nts.uk.ui.windows.close();
                                })
                            });
                    }).fail(function (error) {
                        vm.$dialog.error({messageId: error.messageId});
                    }).always(() => {
                        vm.$blockui("clear");
                    });
                }
            });
        }
    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }
}


