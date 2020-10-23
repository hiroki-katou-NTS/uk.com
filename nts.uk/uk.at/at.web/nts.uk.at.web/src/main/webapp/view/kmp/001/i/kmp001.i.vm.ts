/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
import device = nts.uk.devices;

module nts.uk.at.view.kmp001.i {
    type STATE = device.COMMAND | 'default';

    export interface ReturnData {
        stampCardDigitNumber: number;
        stampCardEditMethod: StampCardEditMethod;
    }

    interface DataModel {
        color: string;
        title: string;
        message: string;
        connected: boolean;
    }

    @bean()
    export class ViewModel extends ko.ViewModel {
        public model!: KnockoutComputed<DataModel>;
        public value: KnockoutObservable<string> = ko.observable('');
        public state: KnockoutObservable<STATE> = ko.observable('default');

        created() {
            var vm = this;

            vm.model = ko.computed({
                read: () => {
                    const st = ko.unwrap(vm.state);

                    switch (st) {
                        default:
                        case 'close':
                            return {
                                color: '#ff0',
                                title: 'KMP001_156',
                                message: '',
                                connected: true
                            }
                        case 'default':
                            return {
                                color: '#ff0',
                                title: 'KMP001_156',
                                message: '',
                                connected: true
                            };
                        case 'connect':
                            return {
                                color: '#0033cc',
                                title: 'KMP001_154',
                                message: '',
                                connected: false
                            };

                        case 'read':
                            return {
                                color: '#0033cc',
                                title: 'KMP001_154',
                                message: '',
                                connected: false
                            }
                        case "disconnect":
                        case 'open':
                        case 'status':
                            return {
                                color: '#ff0',
                                title: 'KMP001_155',
                                message: 'KDP005_4',
                                connected: true
                            };
                    }
                }
            });
        }

        mounted() {
            const vm = this;

            device.felica((command: device.COMMAND, __: boolean, cardNo: string) => {
                const no = (cardNo || '').replace(/-/g, '');

                vm.value(no);
                vm.state(command);
                vm.validate()
                .then((valid: boolean) => {
                    if (valid) {
                        if (command === 'read' && cardNo) {
                            if (this.value)
                                vm.$window.close(no);
                        }
                    }
                })
            });

            $(vm.$el).find('input[type="text"]').get(0).focus();
        }

        closeDialog() {
            const vm = this;

            vm.validate()
                .then((valid: boolean) => {
                    if (valid) {
                        vm.$window.close(ko.unwrap(vm.value));
                    }
                })
        }

        public validate(action: 'clear' | undefined = undefined) {
            if (action === 'clear') {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').ntsError('clear'));
            } else {
                return $.Deferred().resolve()
                    /** Gọi xử lý validate của kiban */
                    .then(() => $('.nts-input').trigger("validate"))
                    /** Nếu có lỗi thì trả về false, không thì true */
                    .then(() => !$('.nts-input').ntsError('hasError'));
            }
        }
    }
}