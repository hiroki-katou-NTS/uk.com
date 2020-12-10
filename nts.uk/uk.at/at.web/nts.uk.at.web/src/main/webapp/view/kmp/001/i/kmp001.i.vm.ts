/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
import device = nts.uk.devices;

module nts.uk.at.view.kmp001.i {
    // type STATE = device.COMMAND | 'default';

    // export interface ReturnData {
    //     stampCardDigitNumber: number;
    //     stampCardEditMethod: StampCardEditMethod;
    // }

    // interface DataModel {
    //     color: string;
    //     title: string;
    //     message: string;
    //     connected: boolean;
    // }

    @bean()
    export class ViewModel extends ko.ViewModel {

            value = ko.observable('');
            notify = ko.observable(this.$i18n('KDP005_21'));
            color = ko.observable('#ff0000');
            inforAuthent = ko.observable('');
            diplayBtnConnect = ko.observable(true);
        
        // public model!: KnockoutComputed<DataModel>;
        // public value: KnockoutObservable<string> = ko.observable('');
        // public state: KnockoutObservable<STATE> = ko.observable('default');

        created() {
            var vm = this;

                // $(vm.$el).find('input[type="text"]').get(0).focus();
                $(document).ready(function() {
                    $('#iCCard').focus();
                });
                vm.connectICCard();

            // vm.model = ko.computed({
            //     read: () => {
            //         const st = ko.unwrap(vm.state);
                    
            //         console.log(st);

            //         switch (st) {
            //             default:
            //             case 'close':
            //                 return {
            //                     color: '#ff0',
            //                     title: 'KMP001_156',
            //                     message: '',
            //                     connected: true
            //                 }
            //             case 'default':
            //                 return {
            //                     color: '#ff0',
            //                     title: 'KMP001_156',
            //                     message: '',
            //                     connected: true
            //                 };
            //             case 'connect':
            //                 return {
            //                     color: '#0033cc',
            //                     title: 'KMP001_154',
            //                     message: '',
            //                     connected: false
            //                 };

            //             case 'read':
            //                 return {
            //                     color: '#0033cc',
            //                     title: 'KMP001_154',
            //                     message: '',
            //                     connected: false
            //                 }
            //             case "disconnect":
            //             case 'open':
            //                 return {
            //                     color: '#ff0000',
            //                     title: 'KMP001_155',
            //                     message: 'KDP005_4',
            //                     connected: true
            //                 }
            //             case 'status':
            //                 return {
            //                     color: '#0033cc',
            //                     title: 'KMP001_154',
            //                     message: '',
            //                     connected: false
            //                 };
            //         }
            //     }
            // });
        }

        mounted() {
            const vm = this;

            // device.felica((command: device.COMMAND, __: boolean, cardNo: string) => {
            //     const no = (cardNo || '').replace(/-/g, '');

            //     vm.value(no);
            //     vm.state(command);
            //     vm.validate()
            //     .then((valid: boolean) => {
            //         if (valid) {
            //             if (command === 'read' && cardNo) {
            //                 if (this.value)
            //                     vm.$window.close(no);
            //             }
            //         }
            //     })
            // });

            // $(vm.$el).find('input[type="text"]').get(0).focus();
        }

        public connectICCard(){
            const vm = this;
            device.felica((command: device.COMMAND, readyRead: boolean, cardNo: string) => {
                vm.value();
                if(command === 'disconnect' || (command === 'status' && readyRead == false)){
                    vm.color('#ff0000');
                    vm.notify(vm.$i18n('KDP005_6'));
                    vm.inforAuthent(vm.$i18n('KDP005_4'));
                    vm.diplayBtnConnect(true);
                }else if(command === 'status'){
                    if(readyRead){
                        vm.color('#0033cc');
                        vm.notify(vm.$i18n('KDP005_5'));
                        vm.inforAuthent('');
                        vm.diplayBtnConnect(false);
                    }
                }else if(command === 'close'){
                    vm.color('#ff0000');
                    vm.notify(vm.$i18n('KDP005_21'));
                    vm.inforAuthent('');
                    vm.diplayBtnConnect(true);
                }else if(command === 'connect'){
                    vm.color('#0033cc');
                    vm.notify(vm.$i18n('KDP005_5'));
                    vm.inforAuthent('');
                    vm.diplayBtnConnect(false);
                }else if(command === 'read'){
                    vm.color('#0033cc');
                    vm.notify(vm.$i18n('KDP005_5'));
                    vm.inforAuthent('');
                    vm.diplayBtnConnect(false);
                    vm.value(_.replace(cardNo, new RegExp("-","g"), ''));
                    vm.closeDialog();
                }
            });    
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