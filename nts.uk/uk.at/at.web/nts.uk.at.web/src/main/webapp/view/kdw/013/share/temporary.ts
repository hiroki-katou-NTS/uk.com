module nts.uk.ui.at.kdw013 {
	export type DisplayManHrRecordItem 			= { itemId: number; order: any; }
	export type ITaskItemValue 					= { itemId: number; value: any; }
	export type IManHrTaskDetail 				= { supNo: number; taskItemValues: ITaskItemValue[]; }
	export type ITimeSpanForCalc 				= { start: Date; end: Date; }
	export type IManHrPerformanceTaskBlock 		= { caltimeSpan: ITimeSpanForCalc; taskDetails: IManHrTaskDetail[]; }
	export type IDailyActualManHoursActualWork 	= { date: string; taskBlocks: IManHrPerformanceTaskBlock[]; }
	
	//日別実績の工数実績作業
	export class DailyActualManHoursActualWork {
		date: KnockoutObservable<string>;
		taskBlocks: KnockoutObservableArray<ManHrPerformanceTaskBlock>;
		
		constructor(dailyActualManHoursActualWork: IDailyActualManHoursActualWork) {
			this.date = ko.observable(dailyActualManHoursActualWork.date);
			this.taskBlocks = ko.observableArray(_.map(dailyActualManHoursActualWork.taskBlocks, (t: IManHrPerformanceTaskBlock) => new ManHrPerformanceTaskBlock(t)));
		}
		
		update(dailyActualManHoursActualWork: IDailyActualManHoursActualWork){
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
		constructor(manHrTaskDetail: IManHrTaskDetail, attendanceItems?: DailyAttendanceItemDto[], manHourRecordAndAttendanceItemLinks?: ManHourRecordAndAttendanceItemLinkDto[]) {
			let vm = this;
			vm.supNo = manHrTaskDetail.supNo;
			//sap xep
			if(attendanceItems && manHourRecordAndAttendanceItemLinks) {				
				// sap xep item co dinh
				let taskItemValues: ITaskItemValue[] = _.sortBy(_.filter(manHrTaskDetail.taskItemValues,(i: ITaskItemValue)=>{return i.itemId <= 8}),  ['itemId']);
				
				// sap xep thu tu item tuy y
				let manHourRecordAndAttendanceItemLink: ManHourRecordAndAttendanceItemLinkDto[] = _.filter(manHourRecordAndAttendanceItemLinks, (l : ManHourRecordAndAttendanceItemLinkDto) => l.frameNo == vm.supNo);
				_.forEach(_.sortBy(attendanceItems, ['displayNumber']), (attendanceItem: DailyAttendanceItemDto) => {
					let itemAttendanceItemLink: ManHourRecordAndAttendanceItemLinkDto = _.find(manHourRecordAndAttendanceItemLink, (link: ManHourRecordAndAttendanceItemLinkDto) => {
						return link.attendanceItemId == attendanceItem.attendanceItemId;
					});
					if(itemAttendanceItemLink){
						let item: ITaskItemValue = _.find(manHrTaskDetail.taskItemValues, (i: ITaskItemValue) => i.itemId = itemAttendanceItemLink.itemId);
						taskItemValues.push(item);	
					}
				});
			}else{
				this.taskItemValues = ko.observableArray(
				_.map(manHrTaskDetail.taskItemValues,(t: ITaskItemValue) => new TaskItemValue(t)));	
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
		constructor(taskItemValues: ITaskItemValue) {
			this.itemId = taskItemValues.itemId;
			this.value = ko.observable(taskItemValues.value);
		}
	}
}