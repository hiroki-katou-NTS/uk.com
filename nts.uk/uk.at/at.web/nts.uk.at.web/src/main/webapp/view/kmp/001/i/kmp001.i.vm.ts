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
                        case 'default':
                            return {
                                color: '#ff0',
                                title: 'KMP001_155',
                                message: 'KDP005_4',
                                connected: true
                            };
                        case 'connect':
                            return {
                                color: '#0033cc',
                                connected: false,
                                message: '',
                                title: 'KMP001_154'
                            };
                        case "disconnect":
                        case 'open':
                        case 'read':
                        case 'status':
                            return {
                                color: '#0033cc',
                                connected: false,
                                message: '',
                                title: 'KMP001_154'
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

                if(command === 'read' && cardNo) {
                    vm.$window.close(no);
                }
            });

            $(vm.$el).find('input[type="text"]').get(0).focus();
        }

        /*public connectICCard() {
            const vm = this;
            device.felica((command: device.COMMAND, readyRead: boolean, cardNo: string) => {
                vm.value();

                switch (command) {
                    case "status":
                        if (readyRead) {
                            vm.color('#0033cc');
                            vm.notify(vm.$i18n('KMP001_154'));
                            vm.inforAuthent('');
                            vm.diplayBtnConnect(false);
                        }
                        break;
                    case "close":
                        vm.color('#ff0000');
                        vm.notify(vm.$i18n('KDP005_21'));
                        vm.inforAuthent('');
                        vm.diplayBtnConnect(true);
                        break;
                    case "connect":
                        vm.color('#0033cc');
                        vm.notify(vm.$i18n('KMP001_154'));
                        vm.inforAuthent('');
                        vm.diplayBtnConnect(false);
                        break;
                    case "read":
                        vm.color('#0033cc');
                        vm.notify(vm.$i18n('KMP001_154'));
                        vm.inforAuthent('');
                        vm.diplayBtnConnect(false);
                        vm.value(cardNo.replace(/-/g, ''));
                        vm.decision();
                        break;
                    default:
                        vm.color('#ff0000');
                        vm.notify(vm.$i18n('KMP001_155'));
                        vm.inforAuthent(vm.$i18n('KDP005_4'));
                        vm.diplayBtnConnect(true);
                }
            });
        }*/

    }
}