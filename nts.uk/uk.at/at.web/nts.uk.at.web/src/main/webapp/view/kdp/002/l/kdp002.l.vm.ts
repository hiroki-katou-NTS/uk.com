/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp002.l {

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: KnockoutObservableArray<TaskInfo> = ko.observableArray([]);

        taskButtons!: KnockoutComputed<TaskInfo[][]>;

        position: KnockoutObservable<number> = ko.observable(0);

        checkBack: KnockoutObservable<boolean> = ko.observable(false);

        checkNext: KnockoutObservable<boolean> = ko.observable(true);

        tasks: TaskInfo[][] = [];

        displayBackNextBtn: KnockoutObservable<boolean | null> = ko.observable(true);

        settingWorkFrame: KnockoutObservableArray<TaskFrameSetting> = ko.observableArray([]);
        
        frameNo: KnockoutObservable<number> = ko.observable(1);

		currentCodeList: KnockoutObservableArray<string>;
		searchValue: KnockoutObservable<string>;

        created() {
            const vm = this;
            let taskInfos: TaskInfo[]  = [{
                code: '1',
                taskFrameNo: 1,
                displayInfo: {taskName: '名駅広場'},
                childTasks: ['1','2']
            },
            {
                code: '2',
                taskFrameNo: 1,
                displayInfo: {taskName: '栄１丁目'},
                childTasks: ['1','2']
            },
			{
                code: '3',
                taskFrameNo: 1,
                displayInfo: {taskName: '名古屋市鶴舞図書館'},
                childTasks: ['1','2']
            },
			{
                code: '4',
                taskFrameNo: 1,
                displayInfo: {taskName: '広小路YYパーキング１２'},
                childTasks: ['1','2']
            },
			{
                code: '5',
                taskFrameNo: 1,
                displayInfo: {taskName: '広小路YYパーキング１２'},
                childTasks: ['1','2']
            },
			{
                code: '6',
                taskFrameNo: 1,
                displayInfo: {taskName: '１２３４５６７８９０１２３４５６７８９０１２３４５'},
                childTasks: ['1','2']
            }
        ];
            vm.model(taskInfos);

            let settingWorkFrame: TaskFrameSetting[] = [{
                frameNo: 1,
                frameName: '枠名１',
                useAtr: 1
            },
            {
                frameNo: 2,
                frameName: '枠名2',
                useAtr: 1
            }
        ];
            vm.settingWorkFrame(settingWorkFrame);

            vm.taskButtons = ko.computed({
                read: () => {
                    const buttons = ko.unwrap<TaskInfo[]>(vm.model);

                    return _.chunk(buttons, 3);
                }
            });
        }

        mounted() {

        }

        onClickSearch() {

        }

		onClickCancel() {
	
		}

        onClickReturn() {
			const vm = this;
			vm.$window.close();
        }

        onClickBack() {
            const vm = this;
            vm.position(ko.unwrap(vm.position) - 1);
        }

        onClickNext() {
            const vm = this;
            vm.position(ko.unwrap(vm.position) + 1);
        }

    }

interface TaskInfo {
    code: String; // コード
    taskFrameNo: number; // 作業枠NO
    displayInfo: TaskDisplayInfo; // 表示情報 : 作業表示情報
    childTasks: String []; // 子作業一覧
}

interface TaskDisplayInfo {
    taskName: String; //名称
}

interface TaskFrameSetting {
    frameNo: number; // 作業枠NO
    frameName: String; // 作業枠名
    useAtr: number; // するしない区分
}


}