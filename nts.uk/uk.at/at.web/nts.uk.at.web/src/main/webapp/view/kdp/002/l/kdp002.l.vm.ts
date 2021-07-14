/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp002.l {

    interface IParam {
        employeeId: string;
    }

    interface ITaskParam {
        employeeId: string; //社員ID
        workFrameNo: number; //作業枠NO
        upperWorkCode: string; //上位作業コード
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

        settingWorkFrame: KnockoutObservableArray<TaskFrameSetting> = ko.observableArray([]);

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

            let taskParam: ITaskParam = {employeeId: vm.empId, workFrameNo: 1, upperWorkCode: ''};
           
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

        getChildTask() {
            const vm = this;

             //Get from API
             let childTaskInfos: TaskInfo[]  = [{
                code: '1',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child1名駅広場'},
                childTasks: ['1','2']
            },
            {
                code: '2',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child2栄１丁目t'},
                childTasks: ['1','2']
            },
            {
                code: '3',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child3栄１丁目t'},
                childTasks: ['1','2']
            },
            {
                code: '4',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child4栄１丁目t'},
                childTasks: ['1','2']
            },
            {
                code: '5',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child5栄１丁目t'},
                childTasks: ['1','2']
            },
            {
                code: '6',
                taskFrameNo: 2,
                displayInfo: {taskName: 'child6栄１丁目t'},
                childTasks: ['1','2']
            },
            {
                code: "7",
                taskFrameNo: 2,
                displayInfo: {taskName: 'child7栄１丁目t'},
                childTasks: ['1','2']
            }
    ];
            vm.model(childTaskInfos);
            vm.taskArray = _.chunk(childTaskInfos, 6);
            vm.framePosition.valueHasMutated();
        }

        getTask(param: ITaskParam) {
            const vm = this;

             //Get from API
             let taskInfos: TaskInfo[]  = [{
                code: '1t',
                taskFrameNo: 1,
                displayInfo: {taskName: '名駅広場'},
                childTasks: ['1','2']
            },
            {
                code: '2a',
                taskFrameNo: 1,
                displayInfo: {taskName: '栄１丁目t'},
                childTasks: ['1','2']
            },
			{
                code: '3a',
                taskFrameNo: 1,
                displayInfo: {taskName: '名古屋市鶴舞図書館'},
                childTasks: ['1','2']
            },
			{
                code: '4t',
                taskFrameNo: 1,
                displayInfo: {taskName: '1広小路YYパーキング１２a'},
                childTasks: ['1','2']
            },
			{
                code: '5',
                taskFrameNo: 1,
                displayInfo: {taskName: '2広小路YYパーキング１２a'},
                childTasks: ['1','2']
            },
			{
                code: '6',
                taskFrameNo: 1,
                displayInfo: {taskName: '１２３４５６７８９０１２３４５６７８９０１２３４５t'},
                childTasks: ['1','2']
            },
            {
                code: '7',
                taskFrameNo: 1,
                displayInfo: {taskName: 'a3広小路YYパーキング１２３'},
                childTasks: ['1','2']
            },
            {
                code: '8',
                taskFrameNo: 1,
                displayInfo: {taskName: '4広小路YYパーキング１２３４５'},
                childTasks: ['1','2']
            },
            {
                code: '7t',
                taskFrameNo: 1,
                displayInfo: {taskName: '5広小路YYパーキング１２３'},
                childTasks: ['1','2']
            },
            {
                code: '8',
                taskFrameNo: 1,
                displayInfo: {taskName: '6広小路YYパーキング１２３４５'},
                childTasks: ['1','2']
            },
            {
                code: 'a9',
                taskFrameNo: 1,
                displayInfo: {taskName: '7広小路YYパーキング１２３'},
                childTasks: ['1','2']
            },
            {
                code: '10t',
                taskFrameNo: 1,
                displayInfo: {taskName: '8広小路YYパーキング１２３４５'},
                childTasks: ['1','2']
            },
            {
                code: '11',
                taskFrameNo: 1,
                displayInfo: {taskName: 'a9広小路YYパーキング１２３'},
                childTasks: ['1','2']
            },
            {
                code: '12',
                taskFrameNo: 1,
                displayInfo: {taskName: '10広小路YYパーキング１２３４５t'},
                childTasks: ['1','2']
            },
        ];
            vm.model(taskInfos);

            let settingWorkFrame: TaskFrameSetting[] = [{
                frameNo: 1,
                frameName: '枠名１.',
                useAtr: 1
            },
            {
                frameNo: 1,
                frameName: '枠名2',
                useAtr: 1
            }
        ];
            vm.settingWorkFrame(settingWorkFrame);
            vm.taskArray = _.chunk(taskInfos, 6);
            vm.framePosition.valueHasMutated();

        }

        getFrameName(frameNo: number) {
            const vm = this;
            return _.find(ko.unwrap(vm.settingWorkFrame), ['frameNo', frameNo]).frameName;
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
                let taskParam: ITaskParam = {employeeId: vm.empId, workFrameNo: 1, upperWorkCode: ''};
                vm.getTask(taskParam);
                let results =_.filter(ko.unwrap(vm.model), function(item){
                    return item.displayInfo.taskName.indexOf(ko.unwrap(vm.searchValue)) > -1 || item.code.indexOf(ko.unwrap(vm.searchValue)) > -1 ;
                    });
    
                // L2_1の文字を含む作業見つからなかった場合
                if (results.length == 0) {
                    vm.$dialog.error({ messageId: 'MsgB_25' });
                } else {
                    vm.taskArray = _.chunk(results, 6);
                   	vm.reload(0);
                }
            }

        }

		onClickCancel() {
            const vm = this;
            let taskParam: ITaskParam = {employeeId: vm.empId, workFrameNo: 1, upperWorkCode: ''};
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

                if (ko.unwrap(vm.model)[0].taskFrameNo != 1) {
                    vm.getTask({employeeId: vm.empId, workFrameNo: 1, upperWorkCode: ''});
                    vm.reload(0);
                } else {
                    vm.$window.close();
                }
                
            }
        }

        onSelect(code: string) {
            const vm = this;
            vm.getChildTask();
            vm.reload(0);
            vm.framePosition(0);
        }

    }

interface TaskInfo {
    code: string; // コード
    taskFrameNo: number; // 作業枠NO
    displayInfo: TaskDisplayInfo; // 表示情報 : 作業表示情報
    childTasks: string []; // 子作業一覧
}

interface TaskDisplayInfo {
    taskName: string; //名称
}

interface TaskFrameSetting {
    frameNo: number; // 作業枠NO
    frameName: string; // 作業枠名
    useAtr: number; // するしない区分
}


}