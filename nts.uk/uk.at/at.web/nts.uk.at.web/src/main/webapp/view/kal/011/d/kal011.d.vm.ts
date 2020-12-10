module nts.uk.at.kal011.d {

    const API = {
        extractStart: "at/function/alarm-workplace/alarm-list/extract/start",
        extractExecute: "at/function/alarm-workplace/alarm-list/extract/execute",
        extractUpdateStatus: "at/function/alarm-workplace/alarm-list/extract/update-status",
    }

    @bean()
    export class Kal011DViewModel extends ko.ViewModel {

        modalDto: ModalDto = new ModalDto();
        processId: string;
        dialogMode: KnockoutObservable<number>;
        // interval 1000ms request to server
        interval: any;

        constructor(props: any) {
            super();
            const vm = this;
            let modalData = nts.uk.ui.windows.getShared("KAL011DModalData");
            vm.modalDto.executionStartDateTime(modalData.executionStartDateTime);
            vm.modalDto.processingState(modalData.processingState);
            vm.modalDto.alarmCode(modalData.selectedCode);
            vm.dialogMode = ko.observable(AlarmExtraStatus.PROCESSING);
            // mock data
            vm.processId = Math.floor(Math.random() * 15).toString();
            console.log(modalData);
        }

        created() {
            const vm = this;
            _.extend(window, { vm });
            vm.extractAlarm().fail((err: any) => {
                vm.$dialog.error(err);
            });
        }

        /**
         * action perform for interval
         * @return JQueryPromise
         */
        extractAlarm(): JQueryPromise<any> {
            let vm = this,
                dfd = $.Deferred();
            // Management deletion monitoring process
            //  vm.interval = setInterval(vm.countTime, 1000, self);

            // vm.$ajax(API.extractStart).done((processStatusId: string) => {

            // })

            let parram: any = {
                workplaceIds: [],
                alarmPatternCode: "01",
                categoryPeriods: [],
                processStatusId: ""
            }
            vm.$ajax(API.extractExecute, parram).done((task: any) => {
                console.log(task);
                nts.uk.deferred.repeat(conf => conf.task(() => {
                    return nts.uk.request.asyncTask.getInfo(task.id).done(function(res: any) {
                        let taskData = res.taskDatas;
                        console.log(taskData);
                        if(res.succeeded){
                            let data = {};
                            
                            dfd.resolve(data);
                        } else if(res.failed){
                            dfd.reject(res.error);
                        }
                    });
                }).while(infor => {
                    return (infor.pending || infor.running) && infor.status != "REQUESTED_CANCEL";
                }).pause(100));
            });
            return dfd.promise();
        }

        /**
         * set current time
         * @return void
         */
        public setFinished(): void {
            let vm = this;
            window.clearInterval(vm.interval);
            vm.modalDto.executionEndtDateTime(moment(new Date()).format("YYYY/MM/DD H:mm"));
        }

        /**
         * Action perform for terminate the process
         * @return void
         */
        interruptProcess() {
            const vm = this;
            vm.$dialog.confirm({ messageId: "Msg_1412" }).then((result: 'yes' | 'cancel') => {
                if (result === 'yes') {
                    //TODO server side logic to suspens
                    vm.dialogMode(AlarmExtraStatus.INTERRUPT);
                }
            });
        }

        /**
         * This function is responsible to close the modal         *
         * @return type void         *
         */
        cancel_Dialog(): any {
            var vm = this;
            let modalData = {
                isClose: true
            }
            vm.$window.storage('KAL011DModalData', modalData).done(() => {
                nts.uk.ui.windows.close();
            });
        }

        /**
         * This function is responsible to perform modal data action
         * @return type void         *
         * */
        confirmProcess(): any {
            var vm = this;
            let modalData = {
                selectedCode: vm.modalDto.alarmCode(),
                processId: vm.processId,
                isClose: false
            }
            vm.$window.storage('KAL011DModalData', modalData).done(() => {
                nts.uk.ui.windows.close();
            })
        }

        public countTime(self: any): void {
            // F2_1_2 set time over
            let timeNow = new Date();
            let over = (timeNow.getSeconds() + timeNow.getMinutes() * 60 + timeNow.getHours() * 60 * 60) - (self.timeStart.getSeconds() + self.timeStart.getMinutes() * 60 + self.timeStart.getHours() * 60 * 60);
            let time = new Date();
            time.setSeconds(over); // setting value for SECONDS here
            let result = time.toISOString().substr(11, 8);
            self.timeProcess(result);
        }
    }

    export enum AlarmExtraStatus {
        END_NORMAL = 0, /**正常終了*/
        END_ABNORMAL = 1, /**異常終了*/
        PROCESSING = 2, /**処理中*/
        INTERRUPT = 3,    /**中断*/
    }

    class ModalDto {
        /*実行開始日時*/
        executionStartDateTime: KnockoutObservable<any>;
        /*処理状態*/
        processingState: KnockoutObservable<any>;
        /*実行終了日時*/
        executionEndtDateTime: KnockoutObservable<any>;
        /*経過時間*/
        elapsedTime: KnockoutObservable<any>;
        alarmCode: KnockoutObservable<string>;

        constructor() {
            this.executionStartDateTime = ko.observable('');
            this.processingState = ko.observable('');
            this.executionEndtDateTime = ko.observable('');
            this.elapsedTime = ko.observable('');
            this.alarmCode = ko.observable('');
        }
    }
}