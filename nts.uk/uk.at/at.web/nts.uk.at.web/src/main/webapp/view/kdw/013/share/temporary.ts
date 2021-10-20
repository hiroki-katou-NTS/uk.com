module nts.uk.ui.at.kdw013 {
	export type DisplayManHrRecordItem 			= { itemId: number; order: any; }
	export type ITaskItemValue 					= { itemId: number; value: any; }
	export type IManHrTaskDetail 				= { supNo: number; taskItemValues: ITaskItemValue[]; }
	export type ITimeSpanForCalc 				= { start: Date; end: Date; }
	export type IManHrPerformanceTaskBlock 		= { caltimeSpan: ITimeSpanForCalc; taskDetails: IManHrTaskDetail[]; }
	export type IDailyActualManHoursActualWork 	= { date: string; taskBlocks: IManHrPerformanceTaskBlock[]; }

	export type TaskBlockDetailContentDto = {
        // 開始時刻
        startTime: number;
        // 終了時刻
        endTime: number;
        //作業詳細
        taskContents: TaskContentForEachSupportFrameDto[];
    }

	export type TaskContentDto = {
        // 項目ID: 工数実績項目ID
        itemId: number;

        // 作業コード
        taskCode: string;
    };

    export type FavoriteTaskItemDto = {
        // 社員ID
        employeeId: string;
        // お気に入りID
        favoriteId: string;
        // お気に入り作業名称
        taskName: string;
        // お気に入り内容
        favoriteContents: TaskContentDto[]; 
    }

    export type StartTaskFavoriteRegisterParam = {
        // お気に入りID
        favId: string | null;
    }

    export type RegisterFavoriteCommand = {
        taskName: string;
	    contents: TaskContentDto[];
    }

    export type UpdateFavNameCommand = {
        favId: string;
        favName: string;
    }
    export type TaskContentForEachSupportFrameDto = {
        // 応援勤務枠No
        frameNo: number;
        // 作業内容
        taskContent: TaskContentDto;
    }

    export type OneDayFavoriteSetDto = {
        // 社員ID
        sId: string;
        // お気に入りID
        favId: string;
        // お気に入り作業名称
        taskName: string;
        // お気に入り内容
        taskBlockDetailContents: TaskBlockDetailContentDto[];
    }
	export type RegisterFavoriteForOneDayCommand = {
        employeeId: string;
        taskName: string;
        contents: TaskBlockDetailContentDto[];
    }

    export type TaskInfo = {
        // 作業枠NO 
        workNo: number;
        // 作業名
        name: string;
        // 作業時間
        time: number;
    }
    
    // 日別勤怠の応援作業時間
    export type OuenWorkTimeOfDailyAttendance = {
        // 応援勤務枠No
        workNo: number;
        // 勤務時間
        workTime: OuenAttendanceTimeEachTimeSheet ;
    }

    export type OuenAttendanceTimeEachTimeSheet = {
        // 総労働時間: 勤怠時間
        totalTime: number;
    }

    // 日別勤怠の応援作業時間帯
    export type OuenWorkTimeSheetOfDailyAttendance = {
        // 応援勤務枠No
        workNo: number;
        // 作業内容
        workContent: WorkContent;
		//時間帯
		timeSheet: TimeSheetOfAttendanceEachOuenSheet;
    }

	export type TimeSheetOfAttendanceEachOuenSheet = {
		// 応援勤務枠No
        workNo: number;
		// 開始
		start: number;
		// 終了
		end: number;
	}

    export type WorkContent = {
        // 作業
        workGroup: WorkGroupDto;
    }
	
	//日別実績の工数実績作業
	export class DailyActualManHoursActualWork {
		date: KnockoutObservable<string>;
		taskBlocks: KnockoutObservableArray<ManHrPerformanceTaskBlock>;

		constructor(dailyActualManHoursActualWork: IDailyActualManHoursActualWork) {
			this.date = ko.observable(dailyActualManHoursActualWork.date);
			this.taskBlocks = ko.observableArray(_.map(dailyActualManHoursActualWork.taskBlocks, (t: IManHrPerformanceTaskBlock) => new ManHrPerformanceTaskBlock(t)));
		}

		update(dailyActualManHoursActualWork: IDailyActualManHoursActualWork) {
			this.date(dailyActualManHoursActualWork.date);
			this.taskBlocks(_.map(dailyActualManHoursActualWork.taskBlocks, (t: IManHrPerformanceTaskBlock) => new ManHrPerformanceTaskBlock(t)));
		}
	}

	// 工数実績作業ブロック
	export class ManHrPerformanceTaskBlock {
		caltimeSpan: TimeSpanForCalc;
		taskDetails: KnockoutObservableArray<ManHrTaskDetail>;
		constructor(taskBlocks: IManHrPerformanceTaskBlock) {
			this.caltimeSpan = new TimeSpanForCalc(taskBlocks.caltimeSpan);
			this.taskDetails = ko.observableArray(_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetail(t)));
		}
	}

	// 計算用時間帯
	export class TimeSpanForCalc {
		start: Date;
		end: Date;
		workingHours: null
		constructor(caltimeSpan: ITimeSpanForCalc) {
			this.start = caltimeSpan.start;
			this.end = caltimeSpan.end;
		}
	}


	//工数実績作業詳細
	export class ManHrTaskDetail {
		supNo: number;
		taskItemValues: KnockoutObservableArray<TaskItemValue>;
		constructor(manHrTaskDetail: IManHrTaskDetail, data?: StartWorkInputPanelDto) {
			let vm = this;
			vm.supNo = manHrTaskDetail.supNo;
			if(!vm.supNo){
				vm.supNo = 1;
			}
			_.forEach([1,2,3,4,5,6,7,8], (i:number) => {
				let item = _.find(manHrTaskDetail.taskItemValues, e => e.itemId == i);
				if(!item){
					manHrTaskDetail.taskItemValues.push({itemId: i, value: null});
				}
			});
			
			//sap xep
			if (data) {
				// sap xep item co dinh
				let taskItemValues: TaskItemValue[] = 
				_.map(
					_.sortBy(
						_.filter(manHrTaskDetail.taskItemValues, (i: ITaskItemValue) => { 
							return i.itemId <= 8 
						}),
					['itemId']), 
				(t: ITaskItemValue) => new TaskItemValue(t));
				
				// sap xep thu tu item tuy y
				let manHourRecordAndAttendanceItemLink: ManHourRecordAndAttendanceItemLinkDto[] = _.filter(data.manHourRecordAndAttendanceItemLink, (l: ManHourRecordAndAttendanceItemLinkDto) => l.frameNo == vm.supNo);
				_.forEach(_.sortBy(data.attendanceItems, ['displayNumber']), (attendanceItem: DailyAttendanceItemDto) => {
					let itemAttendanceItemLink: ManHourRecordAndAttendanceItemLinkDto = _.find(manHourRecordAndAttendanceItemLink, (link: ManHourRecordAndAttendanceItemLinkDto) => {
						return link.attendanceItemId == attendanceItem.attendanceItemId;
					});
					if (itemAttendanceItemLink) {
						let item: ITaskItemValue = _.find(manHrTaskDetail.taskItemValues, (i: ITaskItemValue) => i.itemId = itemAttendanceItemLink.itemId);
						let t: TaskItemValue = new TaskItemValue(item, '', attendanceItem.dailyAttendanceAtr);
						taskItemValues.push(t);
					}
				});
				vm.taskItemValues = ko.observableArray(taskItemValues);
			} else {
				vm.taskItemValues = ko.observableArray(
					_.map(manHrTaskDetail.taskItemValues, (t: ITaskItemValue) => new TaskItemValue(t)));
			}
		}
	}

	//作業項目値
	export class TaskItemValue {
		itemId: number;
		lable: KnockoutObservable<string> = ko.observable('');
		use: KnockoutObservable<boolean> = ko.observable(true);
		type: number;
		value: KnockoutObservable<string>;
		//options only use C screen
		options: KnockoutObservableArray<c.DropdownItem | any> = ko.observableArray([]);
		constructor(taskItemValues: ITaskItemValue, name?: string, type?: number) {
			this.itemId = taskItemValues.itemId;
			this.value = ko.observable(taskItemValues.value);
			this.lable(name);
			this.type = type;
		}
	}
}