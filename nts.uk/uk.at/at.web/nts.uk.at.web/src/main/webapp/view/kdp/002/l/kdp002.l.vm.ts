/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp002.l {

    interface IParam {
        employeeId: string;
    }

    interface ITaskParam {
        sid: string; //社員ID
        workFrameNo: number; //作業枠NO
        upperFrameWorkCode: string; //上位作業コード
    }

    const API = {
		GET_EMPLOYEE_TASKS: 'at/record/stamp/employee_work_by_stamping'
    }

    @handler({
        bindingName: 'firstFocus'
    })
    export class FocusButtonFirstBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement,
            valueAccessor: () => KnockoutObservableArray<any>,
            allBindingsAccessor: KnockoutAllBindingsAccessor,
            viewModel: any,
            bindingContext: KnockoutBindingContext) {
            let focused: boolean = false;

            const accessor = valueAccessor();

            ko.computed({
                read: () => {
                    const buttons = ko.unwrap(accessor);

                    if (focused === false && buttons.length) {
                        ko.tasks
                            .schedule(() => {
                                focused = true;
                                $(element).find('button:first').focus();
                            });
                    }
                },
                disposeWhenNodeIsRemoved: element
            });
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: KnockoutObservableArray<TaskInfo> = ko.observableArray([]);

        taskButtons!: KnockoutComputed<TaskInfo[][]>;

        framePosition: KnockoutObservable<number> = ko.observable(0);

        checkBack: KnockoutObservable<boolean> = ko.observable(false);

        checkNext: KnockoutObservable<boolean> = ko.observable(false);

        checkReturn: KnockoutObservable<boolean> = ko.observable(false);
        
        searchValue: KnockoutObservable<string> = ko.observable('');
        
        taskArray: TaskInfo[][] = [];

        workFrameSetting: KnockoutObservableArray<TaskFrameSetting> = ko.observableArray([]);

		currentCodeList: KnockoutObservableArray<string>;

        empId: string;

        frameName: KnockoutObservable<string> = ko.observable('');

        created(param: IParam) {
            const vm = this;
            vm.taskButtons = ko.computed({
                read: () => {
                    const buttons = ko.unwrap<TaskInfo[]>(vm.model);

                    return _.chunk(buttons, 3);
                }
            });
            vm.empId = param.employeeId

            let taskParam: ITaskParam = {sid: vm.empId, workFrameNo: 1, upperFrameWorkCode: ''};
           
            vm.getTask(taskParam);
            vm.frameName(vm.getFrameName(1));
			vm.reload(0);

            if (ko.unwrap(vm.framePosition) < vm.taskArray.length - 1) {
                vm.checkNext(true);
            } else {
                vm.checkNext(false);
            }

            if (vm.framePosition() == 0) {
                vm.checkReturn(true);
            }
           
        }

        mounted() {
            const vm = this;

            $(document).ready(function () {
                $( "#L2_1" ).focus();
            });

            vm.framePosition
                .subscribe(() => {
                    if (vm.taskArray.length > 0) {
                        if (ko.unwrap(vm.framePosition) <= vm.taskArray.length - 1) {
                            vm.reload(ko.unwrap(vm.framePosition));
                        }
                        if (ko.unwrap(vm.framePosition) == 0) {
                            vm.checkBack(false);
                        } else {
                            vm.checkBack(true);
                        }
                        if (ko.unwrap(vm.framePosition) < vm.taskArray.length - 1) {
                            vm.checkNext(true);
                        } else {
                            vm.checkNext(false);
                        }
                    }

                    if (vm.framePosition() == 0) {
                        vm.checkReturn(true);
                    }
                });

            // Trigger button click on enter
            $( "#L2_1" ).keyup((event: any) => {
                if (event.keyCode === 13) {
                    vm.onClickSearch();
                }
            });
        }

        getTask(param: ITaskParam) {
            const vm = this;

        vm.$ajax('at', API.GET_EMPLOYEE_TASKS, param)
            .then((result: Result) => {

                if (result) {
                    if (result.taskFrameUsageSetting) {
                        vm.workFrameSetting(result.taskFrameUsageSetting.taskFrameSetting);
                    }
                    vm.model(result.task);
                    vm.taskArray = _.chunk(result.task, 6);
                    vm.framePosition.valueHasMutated();
                }

            });

        }

        getFrameName(frameNo: number) {
            const vm = this;

            if (ko.unwrap(vm.workFrameSetting).length > 0) {
                return _.find(ko.unwrap(vm.workFrameSetting), ['taskFrameNo', frameNo]).taskFrameName;
            } else {
                return '';
            }
            
        }

        reload(index: number) {
            const vm = this;
            vm.$blockui('invisible')
                .then(() => {
                    vm.model(vm.taskArray[index])
                })
                .always(() => {
                    vm.$blockui('clear');
                });
        }

        onClickSearch() {
            const vm = this;

            // L2_1が未入力の場合
            if (vm.searchValue() == '') {
                vm.$dialog.error({ messageId: 'MsgB_24' });

            } else {
             
                let taskParam: ITaskParam = {sid: vm.empId, workFrameNo: 1, upperFrameWorkCode: ''};

                vm.$ajax('at', API.GET_EMPLOYEE_TASKS, taskParam)
                    .then((result: Result) => {
    
                        if (result) {
                            if (result.taskFrameUsageSetting) {
                                vm.workFrameSetting(result.taskFrameUsageSetting.taskFrameSetting);
                            }
                            vm.model(result.task);
                        
                        }
    
                    }).then(()=> {
                        let results =_.filter(ko.unwrap(vm.model), function(item){
                            return item.displayInfo.taskName.indexOf(ko.unwrap(vm.searchValue)) > -1 || item.code.indexOf(ko.unwrap(vm.searchValue)) > -1 ;
                            });
        
                        // L2_1の文字を含む作業見つからなかった場合
                        if (results.length == 0) {
                            vm.$dialog.error({ messageId: 'MsgB_25' });
                            vm.taskArray = _.chunk(ko.unwrap(vm.model), 6);
                        } else {
                            vm.taskArray = _.chunk(results, 6);
                        }

                        vm.reload(0);
                        vm.framePosition.valueHasMutated();
                    })
            }

        }

		onClickCancel() {
            const vm = this;
            let taskParam: ITaskParam = {sid: vm.empId, workFrameNo: 1, upperFrameWorkCode: ''};
            if (ko.unwrap(vm.searchValue) != '') {
                vm.getTask(taskParam);
                vm.searchValue('');
                vm.reload(0);
            }
			
		}

        onClickBack() {
            const vm = this;
            vm.framePosition(ko.unwrap(vm.framePosition) - 1);
        }

        onClickNext() {
            const vm = this;
            vm.framePosition(ko.unwrap(vm.framePosition) + 1);
        }

        onClickReturn() {
            const vm = this;
            if (vm.framePosition() != 0) {
                vm.onClickBack();
            } else {

                if (ko.unwrap(vm.model)[0].frameNo != 1) {
                    vm.getTask({sid: vm.empId, workFrameNo: 1, upperFrameWorkCode: ''});
                    vm.reload(0);
                } else  {
                    vm.closeDialogL();
                }
                
            }
        }

        onSelect(code: string) {
            const vm = this;

            let frameNo = _.find(ko.unwrap(vm.model), ['code', code]).frameNo;

            vm.getTask({sid: vm.empId, workFrameNo: frameNo + 1, upperFrameWorkCode: code})
          
            if (ko.unwrap(vm.model).length == 0) {
                vm.closeDialogL();
            }
            vm.reload(0);
            vm.framePosition(0);
         }

         closeDialogL() {
            const vm = this;
            vm.$window.close();
         }

    }

interface TaskInfo {
    code: string; // コード
    frameNo: number; // 作業枠NO
    displayInfo: TaskDisplayInfo; // 表示情報 : 作業表示情報
    childTaskList: string []; // 子作業一覧
}

interface TaskDisplayInfo {
    taskName: string; //名称
}

interface TaskFrameSetting {
    taskFrameNo: number; // 作業枠NO
    taskFrameName: string; // 作業枠名
    useAtr: number; // するしない区分
}

interface TaskFrameSet {
    taskFrameSetting: TaskFrameSetting [];
}

interface Result {
    task: TaskInfo []; //List＜作業＞
    taskFrameUsageSetting: TaskFrameSet; //作業枠利用設定

}


}