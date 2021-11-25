module nts.uk.ui.at.kdw013.e {
	import getText = nts.uk.resource.getText;

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
                vm.ouenWorkTime(_.filter(param.ouenWorkTimes, (t : OuenWorkTimeOfDailyAttendance) => { return t.no == param.workNo })[0]);
                vm.ouenWorkTimeSheet(_.filter(param.ouenWorkTimeSheets, (ts : OuenWorkTimeSheetOfDailyAttendance) => { return ts.workNo == param.workNo })[0]);
                vm.taskSettings(param.taskSettings);
                vm.date(param.date);
            }

            vm.getSetting();
            vm.startTime(vm.ouenWorkTimeSheet().timeSheet.start.timeWithDay);
            vm.endTime(vm.ouenWorkTimeSheet().timeSheet.end.timeWithDay);
            vm.totalTime(vm.ouenWorkTime() ? vm.ouenWorkTime().workTime.totalTime : 0);
            vm.selectedTaskCD1(vm.ouenWorkTimeSheet().workContent.work == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD1 == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD1);
            vm.selectedTaskCD2(vm.ouenWorkTimeSheet().workContent.work == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD2 == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD2);
            vm.selectedTaskCD3(vm.ouenWorkTimeSheet().workContent.work == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD3 == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD3);
            vm.selectedTaskCD4(vm.ouenWorkTimeSheet().workContent.work == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD4 == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD4);
            vm.selectedTaskCD5(vm.ouenWorkTimeSheet().workContent.work == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD5 == null ? '' : vm.ouenWorkTimeSheet().workContent.work.workCD5);
			vm.getCbbList();        
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
                taskCode: '',
                taskName: '未選択'
            });

            vm.taskLst2().push({
                taskCode: '',
                taskName: '未選択'
            });

            vm.taskLst3().push({
                taskCode: '',
                taskName: '未選択'
            });

            vm.taskLst4().push({
                taskCode: '',
                taskName: '未選択'
            });

            vm.taskLst5().push({
                taskCode: '',
                taskName: '未選択'
            });

			let selectedTask1 = _.find(vm.taskDtos(), t => t.taskFrameNo == 1 && t.code == vm.selectedTaskCD1());
			if (!selectedTask1) {
				vm.taskLst1().push({
                taskCode: vm.selectedTaskCD1(),
                taskName: vm.selectedTaskCD1() + ' ' + getText('KDW013_40')
            	});
			}
			
			let selectedTask2 = _.find(vm.taskDtos(), t => t.taskFrameNo == 2 && t.code == vm.selectedTaskCD2());
			if (!selectedTask2) {
				vm.taskLst2().push({
                taskCode: vm.selectedTaskCD2(),
                taskName: vm.selectedTaskCD2() + ' ' + getText('KDW013_40')
            	});
			}
			
			let selectedTask3 = _.find(vm.taskDtos(), t => t.taskFrameNo == 3 && t.code == vm.selectedTaskCD3());
			if (!selectedTask3) {
				vm.taskLst3().push({
                taskCode: vm.selectedTaskCD3(),
                taskName: vm.selectedTaskCD3() + ' ' + getText('KDW013_40')
            	});
			}
			
			let selectedTask4 = _.find(vm.taskDtos(), t => t.taskFrameNo == 4 && t.code == vm.selectedTaskCD4());
			if (!selectedTask4) {
				vm.taskLst4().push({
                taskCode: vm.selectedTaskCD4(),
                taskName: vm.selectedTaskCD4() + ' ' + getText('KDW013_40')
            	});
			}
			
			let selectedTask5 = _.find(vm.taskDtos(), t => t.taskFrameNo == 5 && t.code == vm.selectedTaskCD5());
			if (!selectedTask5) {
				vm.taskLst5().push({
                taskCode: vm.selectedTaskCD5(),
                taskName: vm.selectedTaskCD5() + ' ' + getText('KDW013_40')
            	});
			}

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
				if (vm.ouenWorkTimeSheet().workContent.work == null) {
					vm.ouenWorkTimeSheet().workContent.work = {workCD1: value, workCD2: null, workCD3: null, workCD4: null, workCD5: null};
					
				} else {
					 vm.ouenWorkTimeSheet().workContent.work.workCD1 = value;
				}
			});

            vm.selectedTaskCD2.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD2 = value == '' ? null : value;
            });

            vm.selectedTaskCD3.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD3 = value == '' ? null : value;
            });

            vm.selectedTaskCD4.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD4 = value == '' ? null : value;
            });

            vm.selectedTaskCD5.subscribe((value) => {
                vm.ouenWorkTimeSheet().workContent.work.workCD5 = value == '' ? null : value;
            });

            vm.startTime.subscribe((value)=> {
                vm.ouenWorkTimeSheet().timeSheet.start.timeWithDay = value;
            })

            vm.endTime.subscribe((value)=> {
                vm.ouenWorkTimeSheet().timeSheet.end.timeWithDay = value;
            })

            vm.totalTime.subscribe((value)=> {
	
				if (vm.ouenWorkTime()) {
					 vm.ouenWorkTime().workTime.totalTime = value;
				} else {
					let workTime = {totalTime: value};
					vm.ouenWorkTime({no: vm.ouenWorkTimeSheet().workNo, workTime: workTime});
				}
			
            })
            
        }

        decide() {
            const vm = this;
			vm.totalTime.valueHasMutated();
            let param = {
                empId: vm.$user.employeeId,
                date: vm.date(),
                ouenTimeSheet: ko.unwrap(vm.ouenWorkTimeSheet),
                ouenTime: {workNo: ko.unwrap(vm.ouenWorkTime).no, workTime: ko.unwrap(vm.ouenWorkTime).workTime},
            };

			vm.$blockui('show');
            $('.ntsControl').trigger('validate');
			if (!nts.uk.ui.errors.hasError()) {
                vm.$ajax('at', '/screen/at/kdw013/e/update_timezone', param)
                .done(() => {
                    vm.$dialog.info({ messageId: 'Msg_15' }).then(()=>{
						vm.close()
                    }); 
                }).fail((error: any) => {
                    vm.$dialog.error(error);
                }).always(() => {
                    vm.$blockui("hide");
                });

            } else {
                vm.$blockui("clear");
            }
        }
        
         // ダイアログを閉じる
        close() {
            const vm = this;
            vm.$window.close();
        }

    }










}