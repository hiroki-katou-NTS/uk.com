module nts.uk.ui.at.kdw013.e {

    interface IParams {
        workNo: number,
        taskDtos: TaskDto[];
        ouenWorkTimes: OuenWorkTimeOfDailyAttendance[];
        ouenWorkTimeSheets: OuenWorkTimeSheetOfDailyAttendance[];
        taskSettings: a.TaskFrameSettingDto[];
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

        created(param: IParams){
            const vm = this;

            if (param) {
                vm.taskDtos(param.taskDtos);
                vm.ouenWorkTime(_.filter(param.ouenWorkTimes, (t : OuenWorkTimeOfDailyAttendance) => { return t.workNo == param.workNo })[0]);
                vm.ouenWorkTimeSheet(_.filter(param.ouenWorkTimeSheets, (ts : OuenWorkTimeSheetOfDailyAttendance) => { return ts.workNo == param.workNo })[0]);
                vm.taskSettings(param.taskSettings);
            }

            vm.getSetting();
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
        }
       
        mounted() {

        }

        decide() {
            
        }
        
         // ダイアログを閉じる
        close() {
            const vm = this;
            vm.$window.close();
        }

    }










}