module nts.uk.at.view.knr002.l {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.l.service;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<string> = ko.observable('');
            time: KnockoutObservable<number> = ko.observable();
            dataShared: any;
            modeBulk: KnockoutObservable<boolean> = ko.observable(false); 
            l42Text: string = '';
            l34Text: KnockoutObservable<string> = ko.observable('');

            constructor() {
                const vm = this;

                vm.dataShared = getShared('dataShareL');
                vm.modeBulk(vm.dataShared.mode == 'bulk' ? true : false);
                vm.loadData();   
            }

            public startPage(): JQueryPromise<void> {
                const vm = this;        
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            private loadData() {
                const vm = this;

                blockUI.invisible();
                let input;
                if (vm.modeBulk()) {
                    input = {
                        mode: 'bulk',
                        empInfoTerminalCode: ''
                    }
                } else {
                    input = {
                        mode: 'individual',
                        empInfoTerminalCode: vm.dataShared.empInfoTerminalCode
                    }
                    vm.l42Text = '端末No.' + input.empInfoTerminalCode +'へ送信します。'
                }

                service.getProductionSwitchDate(input).done((res: Array<GetProductionSwitchDateDto>) => {
                    if (res.length > 0) {
                        console.log(res, 'res L');
                        $('#L3_2').focus(); 
                        if (vm.modeBulk()) {
                            let switchDateList = res.map((item: GetProductionSwitchDateDto) => item.switchDateTime);
                            let duplicateList = res.filter((item: GetProductionSwitchDateDto, index: number) => {
                                return switchDateList.indexOf(item.switchDateTime) !== index;
                            });

                            if ((switchDateList.length - duplicateList.length) === 1) {
                                // 全ての端末の「切替日時」がNULL
                                if (switchDateList[0] === '') {
                                    vm.l34Text(getText('KNR002_281'));
                                } else { // 全ての端末に「切替日時」が設定されており、すべての端末同じ日時がセットされている
                                    vm.date(res[0].switchDateTime.split(' ')[0].split('/').join(''));
                                    let time = parseInt(res[0].switchDateTime.split(' ')[1].split(':')[0]) * 60 + parseInt(res[0].switchDateTime.split(' ')[1].split(':')[1]);
                                    vm.time(time);
                                    vm.l34Text(getText('KNR002_284'));
                                }
                            } else {
                                // 一部の端末の「切替日時」がNULL
                                if (_.includes(switchDateList, '')) {
                                    vm.l34Text(getText('KNR002_282'));
                                } else { // 全ての端末に「切替日時」が設定されているが、端末により異なる日時がセットされている
                                    vm.l34Text(getText('KNR002_283'));
                                }
                            }
                        } else {
                            if (res[0].switchDateTime !== '') {
                                vm.date(res[0].switchDateTime.split(' ')[0].split('/').join(''));
                                let time = parseInt(res[0].switchDateTime.split(' ')[1].split(':')[0]) * 60 + parseInt(res[0].switchDateTime.split(' ')[1].split(':')[1]);
                                vm.time(time);
                            } 
                        }
                        
                    }    
                }).fail(() => {})
                .always(() => blockUI.clear())
            }

            public closeDialog() {
                nts.uk.ui.errors.clearAll();      
                nts.uk.ui.windows.close();
            }

            public hasError() {
                const vm = this;

                $('#L3_2').ntsEditor('validate');
                $('#L3_3').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            public register() {
                const vm = this;
                // blockUI.invisible();
                let dateTime = '';
                let time = '';

                if (vm.hasError()) {
                    return;
                }
                
                if (vm.time() === 0) {
                    time = '00:00:00'
                } else if (vm.time() < 60 && vm.time() > 0) {
                    if (vm.time() < 10) {
                        time = '00:0' + vm.time() + ':00';
                    } else {
                        time = '00:' + vm.time() + ':00';
                    }
                } else if (vm.time() >= 60) {
                    time = ((vm.time() - (vm.time() % 60)) / 60 < 10 ? '0' + (vm.time() - (vm.time() % 60)) / 60 : (vm.time() - (vm.time() % 60)) / 60)
                            + ':' + ((vm.time() % 60) < 10 ? '0' + (vm.time() % 60) : (vm.time() % 60)) + ':00';
                }

                dateTime = moment(vm.date()).format("YYYY/MM/DD") + ' ' + time;
                

                let command = {
                    timeSwitchUKMode: dateTime,
                    empInfoTerminalCode: vm.dataShared.empInfoTerminalCode
                }

                console.log(command);

                if (vm.modeBulk()) {
                    service.registerAll(command).done(() => {
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                            nts.uk.ui.windows.close();
                        });
                    }).fail(() => {})
                    .always(() => blockUI.clear());
                } else {
                    service.register(command).done(() => {
                        nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                            nts.uk.ui.windows.close();
                        });
                    }).fail(() => {})
                    .always(() => blockUI.clear());
                }
            }
            
        }

        export interface GetProductionSwitchDateDto {
            empInfoTerminalCode: string;
            switchDateTime: string;
        }
    }
}