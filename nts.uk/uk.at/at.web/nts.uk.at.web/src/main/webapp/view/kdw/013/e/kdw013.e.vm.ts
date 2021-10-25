module nts.uk.ui.at.kdw013.e {

    interface IParams {
        workNo: number,
        taskDtos: TaskDto[];
        ouenWorkTimes: OuenWorkTimeOfDailyAttendance[];
        ouenWorkTimeSheets: OuenWorkTimeSheetOfDailyAttendance[];
        taskSettings: a.TaskFrameSettingDto[];
        date: Date
    }

    type TaskCbb = {
        taskCode: string,
        taskName: string
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        taskDtos: KnockoutObservableArray<TaskDto> = ko.observableArray();
        ouenWorkTime: KnockoutObservable<OuenWorkTimeOfDailyAttendance> = ko.observable();
        ouenWorkTimeSheet: KnockoutObservable<OuenWorkTimeSheetOfDailyAttendance> = ko.observable();
		taskSettings: KnockoutObservableArray<a.TaskFrameSettingDto> = ko.observableArray();
        date: KnockoutObservable<Date> = ko.observable();

        useTask1: KnockoutObservable<boolean> = ko.observable(false);
        useTask2: KnockoutObservable<boolean> = ko.observable(false);
        useTask3: KnockoutObservable<boolean> = ko.observable(false);
        useTask4: KnockoutObservable<boolean> = ko.observable(false);
        useTask5: KnockoutObservable<boolean> = ko.observable(false);

        setTaskName1: KnockoutObservable<string> = ko.observable();
        setTaskName2: KnockoutObservable<string> = ko.observable();
        setTaskName3: KnockoutObservable<string> = ko.observable();
        setTaskName4: KnockoutObservable<string> = ko.observable();
        setTaskName5: KnockoutObservable<string> = ko.observable();

        taskLst1: KnockoutObservableArray<TaskCbb> = ko.observableArray([]);
        taskLst2: KnockoutObservableArray<TaskCbb> = ko.observableArray([]);
        taskLst3: KnockoutObservableArray<TaskCbb> = ko.observableArray([]);
        taskLst4: KnockoutObservableArray<TaskCbb> = ko.observableArray([]);
        taskLst5: KnockoutObservableArray<TaskCbb> = ko.observableArray([]);

        selectedTaskCD1: KnockoutObservable<string> = ko.observable();
        selectedTaskCD2: KnockoutObservable<string> = ko.observable();
        selectedTaskCD3: KnockoutObservable<string> = ko.observable();
        selectedTaskCD4: KnockoutObservable<string> = ko.observable();
        selectedTaskCD5: KnockoutObservable<string> = ko.observable();

        startTime: KnockoutObservable<number> =  ko.observable();
        endTime: KnockoutObservable<number> =  ko.observable();
        totalTime: KnockoutObservable<number> =  ko.observable();

        created(param: IParams){
            const vm = this;

            if (param) {
                vm.taskDtos(param.taskDtos);
                vm.ouenWorkTime(_.filter(param.ouenWorkTimes, (t : OuenWorkTimeOfDailyAttendance) => { return t.workNo == param.workNo })[0]);
                vm.ouenWorkTimeSheet(_.filter(param.ouenWorkTimeSheets, (ts : OuenWorkTimeSheetOfDailyAttendance) => { return ts.workNo == param.workNo })[0]);
                vm.taskSettings(param.taskSettings);
                vm.date(param.date);
            }

            vm.getSetting();
            vm.getCbbList();
            vm.startTime(vm.ouenWorkTimeSheet().timeSheet.start.timeWithDay);
            vm.endTime(vm.ouenWorkTimeSheet().timeSheet.end.timeWithDay);
            vm.totalTime(vm.ouenWorkTime().workTime.totalTime);
            vm.selectedTaskCD1(vm.ouenWorkTimeSheet().workContent.work.workCD1 == null ? '-1' : vm.ouenWorkTimeSheet().workContent.work.workCD1);
            vm.selectedTaskCD2(vm.ouenWorkTimeSheet().workContent.work.workCD2 == null ? '-1' : vm.ouenWorkTimeSheet().workContent.work.workCD2);
            vm.selectedTaskCD3(vm.ouenWorkTimeSheet().workContent.work.workCD3 == null ? '-1' : vm.ouenWorkTimeSheet().workContent.work.workCD3);
            vm.selectedTaskCD4(vm.ouenWorkTimeSheet().workContent.work.workCD4 == null ? '-1' : vm.ouenWorkTimeSheet().workContent.work.workCD4);
            vm.selectedTaskCD5(vm.ouenWorkTimeSheet().workContent.work.workCD5 == null ? '-1' : vm.ouenWorkTimeSheet().workContent.work.workCD5);

        }

        getSetting() {
            const vm = this;

            _.forEach(vm.taskSettings(), s => {
                if (s.frameNo == 1) {
                    vm.useTask1(s.useAtr == 1);
                    vm.setTaskName1(s.frameName);
                }

                if (s.frameNo == 2) {
                    vm.useTask2(s.useAtr == 1);
                    vm.setTaskName2(s.frameName);
                }

                if (s.frameNo == 3) {
                    vm.useTask3(s.useAtr == 1);
                    vm.setTaskName3(s.frameName);
                }

                if (s.frameNo == 4) {
                    vm.useTask4(s.useAtr == 1);
                    vm.setTaskName4(s.frameName);
                }

                if (s.frameNo == 5) {   
                    vm.useTask5(s.useAtr == 1);
                    vm.setTaskName5(s.frameName);
                }

            });
        }

        getCbbList() {
            const vm = this;

            vm.taskLst1().push({
                taskCode: '-1',
                taskName: '未選択'
            });

            vm.taskLst2().push({
                taskCode: '-1',
                taskName: '未選択'
            });

            vm.taskLst3().push({
                taskCode: '-1',
                taskName: '未選択'
            });

            vm.taskLst4().push({
                taskCode: '-1',
                taskName: '未選択'
            });

            vm.taskLst5().push({
                taskCode: '-1',
                taskName: '未選択'
            });

            _.forEach(vm.taskDtos(), task => {
                if (task.taskFrameNo == 1) {

                    vm.taskLst1().push({
                        taskCode: task.code,
                        taskName: task.code + ' ' + task.displayInfo.taskName
                    });
                }
                if (task.taskFrameNo == 2) {

                    vm.taskLst2().push({
                        taskCode: task.code,
                        taskName: task.code + ' ' + task.displayInfo.taskName
                    });
                }
                if (task.taskFrameNo == 3) {

                    vm.taskLst3().push({
                        taskCode: task.code,
                        taskName: task.code + ' ' + task.displayInfo.taskName
                    });
                }
                if (task.taskFrameNo == 4) {

                    vm.taskLst4().push({
                        taskCode: task.code,
                        taskName: task.code + ' ' + task.displayInfo.taskName
                    });
                }
                if (task.taskFrameNo == 5) {

                    vm.taskLst5().push({
                        taskCode: task.code,
                        taskName: task.code + ' ' + task.displayInfo.taskName
                    });
                }


            });


            // Sort list combobox by code in ascending order
            vm.taskLst1().sort((a, b) => parseInt(a.taskCode) - parseInt(b.taskCode));
            vm.taskLst2().sort((a, b) => parseInt(a.taskCode) - parseInt(b.taskCode));
            vm.taskLst3().sort((a, b) => parseInt(a.taskCode) - parseInt(b.taskCode));
            vm.taskLst4().sort((a, b) => parseInt(a.taskCode) - parseInt(b.taskCode));
            vm.taskLst5().sort((a, b) => parseInt(a.taskCode) - parseInt(b.taskCode));
        }
       
        mounted() {
            const vm = this;
            $('#editor-start').focus();

            vm.selectedTaskCD1.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD1 = value;
            });

            vm.selectedTaskCD2.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD2 = value == '-1' ? null : value;
            });

            vm.selectedTaskCD3.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD3 = value == '-1' ? null : value;
            });

            vm.selectedTaskCD4.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD4 = value == '-1' ? null : value;
            });

            vm.selectedTaskCD5.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD5 = value == '-1' ? null : value;
            });

            vm.startTime.subscribe((value)=> {
                vm.ouenWorkTimeSheet().timeSheet.start.timeWithDay = value;
            })

            vm.endTime.subscribe((value)=> {
                vm.ouenWorkTimeSheet().timeSheet.end.timeWithDay = value;
            })

            vm.totalTime.subscribe((value)=> {
                vm.ouenWorkTime().workTime.totalTime = value;
            })
            
        }

        decide() {
            const vm = this;

            let param = {
                empId: vm.$user.employeeId,
                date: vm.date(),
                ouenTimeSheet: ko.unwrap(vm.ouenWorkTimeSheet),
                ouenTime: ko.unwrap(vm.ouenWorkTime)
            };

            vm.$blockui('grayout').then(() => vm.$ajax('at', '/screen/at/kdw013/e/update_timezone', param))
            .done(() => {
                vm.$dialog.info({ messageId: 'Msg_15' });
            }).then (() => {
                vm.close();
            }).always(() => vm.$blockui('clear'));
            
        }
        
         // ダイアログを閉じる
        close() {
            const vm = this;
            vm.$window.close();
        }

    }










}