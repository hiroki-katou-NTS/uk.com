module nts.uk.at.view.ksu003.a.viewmodel {
	import getText = nts.uk.resource.getText;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	import errorDialog = nts.uk.ui.dialog.alertError;
	import dialog = nts.uk.ui.dialog;
	import bundledErrors = nts.uk.ui.dialog.bundledErrors;
	import block = nts.uk.ui.block;
	import exTable = nts.uk.ui.exTable;
	import chart = nts.uk.ui.chart;
	import storage = uk.localStorage;
	import formatById = nts.uk.time.format.byId;
	import parseTime = nts.uk.time.parseTime;
	import model = nts.uk.at.view.ksu003.a.model;
	import duration = nts.uk.time.minutesBased.duration; // convert time 
	/**
	 * ScreenModel
	 */
	var ruler: any;
	export class ScreenModel {

		KEY: string = 'USER_KSU003_INFOR';
		employeeIdLogin: string = __viewContext.user.employeeId; /** Employee login */
		
		targetDate: KnockoutObservable<string> = ko.observable('2020/05/02'); /*A2_1_3*/
		targetDateDay: KnockoutObservable<string> = ko.observable('');
		
		organizationName: KnockoutObservable<string> = ko.observable(''); /*A2_2*/
		selectedDisplayPeriod: KnockoutObservable<number> = ko.observable(1); /*A2_5*/
		
		itemList: KnockoutObservableArray<ItemModel>; /*A3_2*/
		selectOperationUnit: KnockoutObservable<string> = ko.observable('0');
		
		sortList: KnockoutObservableArray<ItemModel>; /*A3_4*/
		checked: KnockoutObservable<string> = ko.observable();
		
		checkedName: KnockoutObservable<boolean> = ko.observable(true); /*A3_3 */
		
		showA9: boolean;
		checkNext: KnockoutObservable<boolean> = ko.observable(true);
		checkPrv: KnockoutObservable<boolean> = ko.observable(true);
		indexBtnToLeft: KnockoutObservable<number> = ko.observable(0);// dùng cho xử lý của botton toLeft, toRight
		indexBtnToDown: KnockoutObservable<number> = ko.observable(0);// dùng cho xử lý của botton toDown, toUp
		dataFromA: KnockoutObservable<model.InforScreenADto> = ko.observable(); //Data get from Screen A
		dataScreen003A: KnockoutObservable<model.DataScreenA> = ko.observable();
		dataScreen003AFirst: KnockoutObservable<model.DataScreenA> = ko.observable();
		dataScreen045A: KnockoutObservable<model.DataFrom045> = ko.observable();
		dataA6: KnockoutObservable<model.A6Data> = ko.observable();
		dataInitStartKsu003Dto: KnockoutObservable<model.GetInfoInitStartKsu003Dto> = ko.observable();
		displayWorkInfoByDateDto: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		displayDataKsu003: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		getFixedWorkInformationDto: KnockoutObservable<Array<any>> = ko.observable();
		dataColorA6: Array<any> = [];
		employeeWorkInfo: KnockoutObservable<model.EmployeeWorkInfoDto> = ko.observable(); //社員勤務情報　dto
		employeeScheduleInfo: Array<any> = [];
		workingInfoColor: KnockoutObservable<string> = ko.observable(''); // A6_color①
		totalTimeColor: KnockoutObservable<string> = ko.observable(''); // A7 color
		/** A3_2 pixel */
		operationUnit: KnockoutObservable<number> = ko.observable(3.5);
		operationOneMinus: KnockoutObservable<number> = ko.observable(12);

		localStore: KnockoutObservable<ILocalStore> = ko.observable();
		lstEmpId: Array<IEmpidName> = [];
		timeRange: number = 0;
		
		timeBrkText: KnockoutObservable<string> = ko.observable();
		timeBrkMinu: KnockoutObservable<number> = ko.observable();

		checkGetInfo: boolean = false;
		dataOfGantChart: Array<ITimeGantChart> = [];
		midDataGC: Array<any> = [];
		disableDs: any = [];
		leftDs: any = [];
		tooltip: any = [];
		allGcShow: any = [];
		allTimeChart: any = [];
		// check when change work time, work type
		checkClearTime: boolean = true;
		checkUpdateMidChart: boolean = true;
		checkUpdateTime: any = []
		
		constructor() {
			let self = this;
			// get data from sc A
			self.dataFromA = ko.observable(getShared('dataFromA'));
			self.lstEmpId = _.flatMap(self.dataFromA().listEmp, c => [{ empId: c.id, name: c.name, code: c.code }]);
			self.targetDate(self.dataFromA().daySelect);
			if (self.targetDate() === (self.dataFromA().startDate)) {
				self.checkPrv(false);
				$("#icon-prev-all").css("filter", "contrast(0.7)");
				$("#icon-prev-one").css("filter", "contrast(0.7)");
			}
			if (self.targetDate() === (self.dataFromA().endDate)) {
				self.checkNext(false);
				$("#icon-next-one").css("filter", "contrast(0.7)");
				$("#icon-next-all").css("filter", "contrast(0.7)");
			}
			self.targetDateDay(self.targetDate() + moment(self.targetDate()).format('(ddd)'));
			self.tooltip = getShared("dataTooltip");
			self.itemList = ko.observableArray([
				new ItemModel('0', getText('KSU003_13')),
				new ItemModel('1', getText('KSU003_14')),
				new ItemModel('2', getText('KSU003_15')),
				new ItemModel('3', getText('KSU003_16')),
				new ItemModel('4', getText('KSU003_17'))
			]);

			self.sortList = ko.observableArray([
				new ItemModel('0', getText('KSU003_59')),
				new ItemModel('1', getText('KSU003_60'))
			]);
			self.showA9 = true;

			storage.getItem(self.KEY).ifPresent((data: any) => {
				let userInfor: ILocalStore = JSON.parse(data);
				self.indexBtnToLeft(userInfor.showHide);
				self.selectOperationUnit(userInfor.operationUnit);
				self.selectedDisplayPeriod(userInfor.displayFormat);
				self.checked(userInfor.startTimeSort);
				self.checkedName(userInfor.showWplName);
				self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
					return _.findIndex(userInfor.lstEmpIdSort, x => { return x.empId == a.empId }) - _.findIndex(userInfor.lstEmpIdSort, x => { return x.empId == b.empId });
				})
			});
			/* 開始時刻順に並び替える(A3_3)はチェックされている */
			self.checked.subscribe((value) => {
				let checkSort = [];
				storage.getItem(self.KEY).ifPresent((data: any) => {
					checkSort = $("#extable-ksu003").exTable('updatedCells');
				})
				if (checkSort.length > 0) {
					dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
						self.sortEmployee(value);
					}).ifNo(() => {
						return;
					})
				} else {
					self.sortEmployee(value);
				}

			});

			/** A3_4 */
			self.checkedName.subscribe((value) => {
				self.localStore().showWplName = value;
				storage.setItemAsJson(self.KEY, self.localStore());
			});

			/** A2_5 */
			self.selectedDisplayPeriod.subscribe((value) => {
				self.localStore().displayFormat = value;
				storage.setItemAsJson(self.KEY, self.localStore());
			});

			/** 操作単位選択に選択する A3_2 */
			self.selectOperationUnit.subscribe((value) => {
				let c = parseInt(value) + 1;
				if (value == '1') {
					self.operationOneMinus(6);
				}

				if (value == '2') {
					self.operationOneMinus(4);
				}

				if (value == '3') {
					self.operationOneMinus(2);
					c = 6
				}

				if (value == '4') {
					self.operationOneMinus(1);
					c = 12;
				}

				if (!_.isNil(ruler))
					ruler.setSnatchInterval(c);

				self.localStore().operationUnit = value;
				storage.setItemAsJson(self.KEY, self.localStore());

			});

			self.checkUpdateTime = {
				name: "",
				id: 0
			}
			// tính tổng thời gian khi thay đổi start và end time
			$("#extable-ksu003").on("extablecellupdated", (dataCell: any) => {
				let index: number = dataCell.originalEvent.detail.rowIndex;
				let dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[index];
				let empId = self.lstEmpId[dataCell.originalEvent.detail.rowIndex].empId;
				let dataFixed = _.filter(self.dataScreen003A().employeeInfo, x => { return x.empId === empId });

				if (self.checkGetInfo == false && self.checkUpdateMidChart == true) {
					if (dataCell.originalEvent.detail.columnKey === "worktimeCode" && dataMid.worktimeCode != "") {
						self.checkUpdateTime.name = "worktimeCode";
						self.checkUpdateTime.id = 1;
						self.inputWorkInfo(index, dataCell, dataFixed, empId);
					}

					// 勤務種類を変更する (nhập thủ công worktype code , worktime code )
					if ((dataCell.originalEvent.detail.columnKey === "worktypeCode" && dataMid.worktypeCode != "")) {
						self.checkUpdateTime.name = "worktypeCode";
						self.checkUpdateTime.id = 2;
						let targetOrgDto = {
							workTypeCode: dataMid.worktypeCode.length > 3 ? dataMid.worktypeCode.slice(0, 3) : dataMid.worktypeCode,
							workTimeCode: dataMid.worktimeCode.length > 3 ? dataMid.worktimeCode.slice(0, 3) : dataMid.worktimeCode
						}
						service.changeWorkType(targetOrgDto).done((data: any) => {

							if (data != null && !_.isNil(data)) {
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");

								if (dataCell.originalEvent.detail.columnKey !== "worktimeCode")
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");

								$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");

								if (data.holiday == false) {
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", "");
								} else {
									self.checkClearTime = false;
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", data.workTypeName);
									ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
										type: "Flex",
										options: {
											id: `lgc` + index,
											start: -1,
											end: -1,
											lineNo: index
										}
									}]);

									return;
								}
								self.inputWorkInfo(index, dataCell, dataFixed, empId);
							} else {
								self.getEmpWorkFixedWorkInfo($("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
									$("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode, empId, index);
								/*self.midContent.dataSource[index] = ({
									empId: empId, worktypeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode, worktypeName: "", worktimeCode: "", worktimeName: "",
									startTime1: "", endTime1: "", startTime2: "", endTime2: "", totalTime: "", breaktime: ""
								});
								$("#extable-ksu003").exTable("updateTable", "middle", self.headerMidExtable, self.midContent);*/
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", "");
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", "");
							}
						}).fail(function(error) {
							errorDialog({ messageId: error.messageId });
						}).always(function() {
						});
					}
					let columnKey = dataCell.originalEvent.detail.columnKey;
					if ((columnKey === "startTime1" || columnKey === "startTime2" ||
						columnKey === "endTime1" || columnKey === "endTime2") && self.checkUpdateTime.id == 0) {
						self.checkTimeInfo(dataMid.worktypeCode, dataMid.worktimeCode, dataMid.startTime1, dataMid.startTime2, dataMid.endTime1, dataMid.endTime2);
						let timeConvert = self.convertTime(dataMid.startTime1, dataMid.endTime1, dataMid.startTime2, dataMid.endTime2);
						self.employeeScheduleInfo.forEach((x, i) => {
							if (i === dataCell.originalEvent.detail.rowIndex) {
								if (columnKey === "startTime1") {
									x.startTime1 = timeConvert.start;
								}
								if (columnKey === "startTime2") {
									x.startTime2 = timeConvert.start2;
								}
								if (columnKey === "endTime1") {
									x.endTime1 = timeConvert.end;
								}
								if (columnKey === "endTime2") {
									x.endTime2 = timeConvert.end2;
								}
							}
						})
						let totalTime: number = null;

						if (!_.isNaN(timeConvert.end) && !_.isNaN(timeConvert.start) && timeConvert.end != "" && timeConvert.start != "" && !_.isNil(timeConvert.start) && !_.isNil(timeConvert.end)) {
							totalTime += (timeConvert.end - timeConvert.start)
						}
						if (!_.isNaN(timeConvert.end2) && !_.isNaN(timeConvert.start2) && timeConvert.end2 != "" && timeConvert.start2 != "" && !_.isNil(timeConvert.start2) && !_.isNil(timeConvert.end2)) {
							totalTime += (timeConvert.end2 - timeConvert.start2);
						}
						if (!self.checkClearTime == false && totalTime != null) {
							totalTime = formatById("Clock_Short_HM", totalTime);
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime != null ? totalTime : "");
						}
					}
				}
			});
		}

		/**
		 * startPage
		 */
		public startPage(): JQueryPromise<any> {
			block.grayout();
			let self = this, dfd = $.Deferred<any>();
			self.getData();
			self.hoverEvent(self.targetDate());
			dfd.resolve();
			block.clear();
			return dfd.promise();
		}

		public inputWorkInfo(index: number, dataCell: any, dataFixed: any, empId: string) {
			let self = this, dfd = $.Deferred<any>();
			self.getEmpWorkFixedWorkInfo($("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
				$("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode, empId, index).done(() => {
					self.removeChart(index);
					if (dataCell.originalEvent.detail.value != "" &&
						dataCell.originalEvent.detail.value != null) {
						self.checkClearTime = true;

						let lstBrkTime = dataFixed[0].workScheduleDto.listBreakTimeZoneDto, totalBrkTime = "", totalTimebr: any = null;
						if (lstBrkTime != null) {
							lstBrkTime.forEach((x: any) => {
								totalTimebr += (_.isNil(x.endTime) ? x.end : x.endTime) - (_.isNil(x.startTime) ? x.start : x.startTime);
							})
						}
						totalBrkTime = totalTimebr != null ? formatById("Clock_Short_HM", totalTimebr) : "";

						let totalTime = self.calcTotalTime(dataFixed[0].workScheduleDto),
							schedule: model.EmployeeWorkScheduleDto = dataFixed[0].workScheduleDto,
							fixed: model.FixedWorkInforDto = dataFixed[0].fixedWorkInforDto,
							info: model.EmployeeWorkInfoDto = dataFixed[0].workInfoDto;

						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeCode", schedule.workTypeCode);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", fixed.workTypeName);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", schedule.workTimeCode);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", fixed.workTimeName);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", formatById("Clock_Short_HM", (schedule.startTime1)));
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", formatById("Clock_Short_HM", (schedule.endTime1)));
						if (schedule.startTime2 != null)
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", formatById("Clock_Short_HM", (schedule.startTime2)));
						else
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");

						if (schedule.endTime2 != null)
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", formatById("Clock_Short_HM", (schedule.endTime2)));
						else
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime);

						self.dataScreen003A().employeeInfo[index].workScheduleDto.workTypeCode = schedule.workTypeCode;
						self.dataScreen003A().employeeInfo[index].workScheduleDto.workTimeCode = schedule.workTimeCode;
						self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime1 = schedule.startTime1;
						self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime1 = schedule.endTime1;
						self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2 = schedule.startTime2;
						self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 = schedule.endTime2;

						self.checkTimeInfo(schedule.workTypeCode, schedule.workTimeCode, schedule.startTime1, schedule.startTime2, schedule.endTime1, schedule.endTime2);

						if (lstBrkTime != null && lstBrkTime.length > 0) {
							self.dataScreen003A().employeeInfo[index].workScheduleDto.listBreakTimeZoneDto = lstBrkTime;
						}

						self.dataScreen003A().employeeInfo[index].workInfoDto.directAtr = info.directAtr;
						self.dataScreen003A().employeeInfo[index].workInfoDto.bounceAtr = info.bounceAtr;

						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workTypeName = fixed.workTypeName;
						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workTimeName = fixed.workTimeName;
						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType = fixed.workType;
						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.fixBreakTime = fixed.fixBreakTime;

						self.convertDataIntoExtable(index);

						let datafilter: Array<ITimeGantChart> = _.filter(self.dataOfGantChart, (x: any) => { return x.empId === empId });
						if (datafilter.length > 0) {
							//self.updateGantChart(datafilter, lineNo, fixedGc, lstBreak, indexS, indexF);
							self.addAllChart(datafilter, index, [], self.midDataGC, "");
							ruler.replaceAt(index, [
								...self.allGcShow
							]);
						}
						self.checkUpdateTime.name = "";
						self.checkUpdateTime.id = 0;
						return;
					}
				})
		}

		setDataToMidExtable(empId: string, schedule: model.EmployeeWorkScheduleDto, fixed: model.FixedWorkInforDto, totalTime: any, totalBrkTime: any) {
			let workTypeName = "", workTimeName = "";

			if (fixed != null) {
				if (fixed.workTypeName != null) {
					workTypeName = fixed.workTypeName;
				}
				if (fixed.workTimeName != null) {
					workTimeName = fixed.workTimeName;
				}
			}
			if (schedule.workTimeCode == null) {
				workTypeName = getText('KSU003_55');
			} else if (schedule.workTypeCode != null && fixed != null
				&& fixed.workTypeName == null) {
				workTypeName = schedule.workTypeCode + getText('KSU003_54')
			}
			// set work time name
			if (schedule.workTimeCode == null) {
				workTimeName = getText('KSU003_55');
			} else {
				if (fixed != null && fixed.workTimeName == null) {
					workTimeName = schedule.workTimeCode + getText('KSU003_54');
				}
			}
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeCode", schedule.workTypeCode);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", workTypeName);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", schedule.workTimeCode);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", workTimeName);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", formatById("Clock_Short_HM", (schedule.startTime1)));
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", formatById("Clock_Short_HM", (schedule.endTime1)));
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", formatById("Clock_Short_HM", (schedule.startTime2)));
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", formatById("Clock_Short_HM", (schedule.endTime2)));
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime);
		}

		removeChart(index: number) {
			ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
				type: "Flex",
				options: {
					id: `lgc` + index,
					start: -1,
					end: -1,
					lineNo: index
				}
			}]);
		}

		public getData() {
			let self = this;
			let canModified = 0;
			if (self.dataFromA().dayEdit < self.targetDate()) {
				canModified = 1;
			}
			let data003A: model.DataScreenA = {
				startDate: self.dataFromA().startDate, // 開始日		
				endDate: self.dataFromA().endDate, // 終了日
				/** 基準の組織 */
				unit: self.dataFromA().unit,
				id: self.dataFromA().unit == 0 ? self.dataFromA().workplaceId : self.dataFromA().workplaceGroupId,
				name: self.dataFromA().workplaceName,
				timeCanEdit: self.dataFromA().dayEdit, //いつから編集可能か
				/** 複数回勤務管理 */
				targetInfor: 0,//対象情報 : 複数回勤務 (1 :true,0:false)
				canModified: canModified,//修正可能 CanModified
				scheCorrection: [],//スケジュール修正の機能制御  WorkTimeForm
				employeeInfo: [],
			}
			self.dataScreen003A(data003A);
			self.dataScreen003AFirst(data003A);
			self.getInforFirt();
			let local: ILocalStore = {
				startTimeSort: self.checked(),
				showWplName: self.checkedName() == true,
				operationUnit: self.selectOperationUnit(),
				displayFormat: self.selectedDisplayPeriod(),
				showHide: self.indexBtnToLeft(),
				lstEmpIdSort: _.map(self.lstEmpId, (x: IEmpidName) => { return x.empId })
			}
			self.localStore(local);

			self.getWorkingByDate(self.targetDate(), 1).done(() => {
				self.convertDataIntoExtable();
				self.initExtableData();
			});
		}

		// Tạo data để truyền vào ExTable
		public convertDataIntoExtable(index?: number) {

			let self = this, disableDs: any = [], leftDs: any = [], middleDs: any = [],
				timeGantChart: Array<ITimeGantChart> = [], typeOfTime: string, startTimeArr: any = [];
			let gcFixedWorkTime: Array<model.IFixedFlowFlexTime> = [],
				gcBreakTime: Array<model.IBreakTime> = [],
				gcOverTime: Array<model.IOverTime> = [],
				gcSupportTime: any = null,
				gcFlowTime: Array<model.IFixedFlowFlexTime> = [],
				gcFlexTime: Array<model.IFixedFlowFlexTime> = [],
				gcCoreTime: Array<model.ICoreTime> = [],
				gcHolidayTime: Array<model.IHolidayTime> = [],
				gcShortTime: Array<model.IShortTime> = [];
			_.forEach(_.isNil(index) ? self.dataScreen003A().employeeInfo : [self.dataScreen003A().employeeInfo[index]], (schedule: model.DisplayWorkInfoByDateDto) => {
				gcFixedWorkTime = [], gcBreakTime = [], gcOverTime = [], gcSupportTime = null,
					gcFlowTime = [], gcFlexTime = [], gcCoreTime = [], gcHolidayTime = [],
					gcShortTime = [], typeOfTime = "";
				let color = self.setColorEmployee(schedule.workInfoDto.isNeedWorkSchedule, schedule.workInfoDto.isCheering);
				if (color === '#ddddd2') {
					disableDs.push({
						empId: schedule.empId,
						color: color
					});
				}
				leftDs.push({
					empId: schedule.empId,
					color: color
				});

				let startTime1 = "", startTime2 = "", endTime1 = "", endTime2 = "";
				if (schedule.workScheduleDto != null) {
					startTime1 = schedule.workScheduleDto.startTime1 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.startTime1) : "",
						startTime2 = schedule.workScheduleDto.startTime2 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.startTime2) : "",
						endTime1 = schedule.workScheduleDto.endTime1 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.endTime1) : "",
						endTime2 = schedule.workScheduleDto.endTime2 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.endTime2) : "";
				}
				let totalTime: any = "", totalBreakTime: any = 0, totalBreakTime1: any = 0, totalBreakTime2: any = 0;
				let workTypeName = "", workTimeName = "";
				if (schedule.fixedWorkInforDto != null) {
					if (schedule.fixedWorkInforDto.workTypeName != null) {
						workTypeName = schedule.fixedWorkInforDto.workTypeName;
					}
					if (schedule.fixedWorkInforDto.workTimeName != null) {
						workTimeName = schedule.fixedWorkInforDto.workTimeName;
					}
				}
				if (schedule.workScheduleDto != null && schedule.fixedWorkInforDto != null) {
					totalTime = self.calcTotalTime(schedule.workScheduleDto);
					self.setColorWorkingInfo(schedule.empId, schedule.workInfoDto.isConfirmed, schedule.workScheduleDto);
					// Bind *worktypecode - *worktimecode
					if (schedule.workScheduleDto.workTimeCode == null) {
						workTypeName = getText('KSU003_55');
					} else if (schedule.workScheduleDto.workTypeCode != null && schedule.fixedWorkInforDto != null
						&& schedule.fixedWorkInforDto.workTypeName == null) {
						workTypeName = schedule.workScheduleDto.workTypeCode + getText('KSU003_54')
					}
					// set work time name
					if (schedule.workScheduleDto.workTimeCode == null) {
						workTimeName = getText('KSU003_55');
					} else {
						if (schedule.fixedWorkInforDto != null && schedule.fixedWorkInforDto.workTimeName == null) {
							workTimeName = schedule.workScheduleDto.workTimeCode + getText('KSU003_54');
						}
					}
				}

				// Push data of gant chart
				// Thời gian cố định
				// 勤務固定情報　dto．Optional<勤務タイプ>==固定勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない

				if (schedule.workInfoDto != null && schedule.workScheduleDto != null && schedule.fixedWorkInforDto != null) {
					if (schedule.fixedWorkInforDto.workType == WorkTimeForm.FIXED &&
						(schedule.workInfoDto.isCheering == SupportAtr.TIMEZONE_SUPPORTER || schedule.workInfoDto.isCheering == SupportAtr.NOT_CHEERING)) {
						// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
						gcFixedWorkTime.push({
							empId: schedule.workInfoDto.employeeId,
							timeNo: 1,
							color: "#ccccff",
							// 社員勤務情報　dto > 応援か
							isCheering: schedule.workInfoDto.isCheering,
							// 勤務固定情報　dto > Optional<勤務タイプ>
							workType: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.workType : null,
							// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
							startTime: schedule.workScheduleDto.startTime1,
							endTime: schedule.workScheduleDto.endTime1,
							// 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>
							startTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.startTimeRange1 : null,
							endTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.endTimeRange1 : null

						});
						startTimeArr.add(schedule.workScheduleDto.startTime1);
						// 複数回勤務管理.使用区別＝＝true
						// 社員勤務予定dto.Optional<開始時刻2>, 社員勤務予定dto.Optional<終了時刻2>
						if (self.dataScreen003A().targetInfor == 1) {
							gcFixedWorkTime.push({
								empId: schedule.workInfoDto.employeeId,
								timeNo: 2,
								color: "#ccccff",
								isCheering: schedule.workInfoDto.isCheering,
								workType: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.workType : null,

								startTime: schedule.workScheduleDto.startTime2,
								endTime: schedule.workScheduleDto.endTime2,
								startTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.startTimeRange2 : null,
								endTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.endTimeRange2 : null
							});
							startTimeArr.add(schedule.workScheduleDto.startTime2);
						}
						typeOfTime = "Fixed"
					}
				}
				if (schedule.fixedWorkInforDto != null && schedule.workScheduleDto != null) {
					// Thời gian break time
					// 勤務固定情報　dto.Optional<休憩時間帯を固定にする>＝falseの時に、休憩時間横棒が生成されない。 (defaut gcBreakTime = [])
					// 勤務固定情報　dto.Optional<休憩時間帯を固定にする>＝trueの時に、社員勤務予定 dto．Optional<List<休憩時間帯>>から休憩時間横棒を生成する。
					if (schedule.fixedWorkInforDto.fixBreakTime == 1) {

						gcBreakTime.push({
							// 社員勤務情報　dto > 社員ID
							empId: schedule.workInfoDto.employeeId,
							// 社員勤務予定　dto > Optional<List<休憩時間帯>>
							lstBreakTime: schedule.workScheduleDto.listBreakTimeZoneDto,
							color: "#ff9999",
							// 勤務固定情報　dto > Optional<休憩時間帯を固定にする>
							fixBreakTime: schedule.fixedWorkInforDto.fixBreakTime
						})

						// Tính tổng BREAK-TIME khi khởi động
						schedule.workScheduleDto.listBreakTimeZoneDto.forEach((brkT: any) => {
							let brkTChart = self.convertTimeToChart(_.isNil(brkT.startTime) ? brkT.start : brkT.startTime, _.isNil(brkT.endTime) ? brkT.end : brkT.endTime),
								timeChart = self.convertTimeToChart(schedule.workScheduleDto.startTime1, schedule.workScheduleDto.endTime1),
								brkTChart2: any = null, timeChart2: any = null;

							if (_.inRange(brkTChart.startTime, timeChart.startTime, timeChart.endTime) ||
								_.inRange(brkTChart.endTime, timeChart.startTime, timeChart.endTime)) {
								totalBreakTime1 += self.calcBreakTime(brkTChart, timeChart);
							}

							if (schedule.workScheduleDto.startTime2 != null && schedule.fixedWorkInforDto.workType != WorkTimeForm.FLEX) {
								brkTChart2 = self.convertTimeToChart(_.isNil(brkT.startTime) ? brkT.start : brkT.startTime, _.isNil(brkT.endTime) ? brkT.end : brkT.endTime)
								timeChart2 = self.convertTimeToChart(schedule.workScheduleDto.startTime2, schedule.workScheduleDto.endTime2)
								if (_.inRange(brkTChart.startTime, timeChart2.startTime, timeChart2.endTime) ||
									_.inRange(brkTChart.endTime, timeChart2.startTime, timeChart2.endTime)) {
									totalBreakTime2 += self.calcBreakTime(brkTChart2, timeChart2);
								}
							}
							totalBreakTime = totalBreakTime2 + totalBreakTime1;
						})
					}

					// Thời gian làm thêm
					// 勤務固定情報 dto.Optional<List<残業時間帯>>から時間外労働時間横棒を生成する。
					gcOverTime.push({
						// 社員勤務情報　dto > 社員ID
						empId: schedule.workInfoDto.employeeId,
						// 勤務固定情報　dto > Optional<List<残業時間帯>>
						lstOverTime: schedule.fixedWorkInforDto.overtimeHours,
						color: "#ffff00"
					})

					// Thời gian lưu động 
					// 勤務固定情報　dto．Optional<勤務タイプ>== 流動勤務&&社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
					if (schedule.fixedWorkInforDto.workType == WorkTimeForm.FLOW
						&& (schedule.workInfoDto.isCheering == SupportAtr.TIMEZONE_SUPPORTER || schedule.workInfoDto.isCheering == SupportAtr.NOT_CHEERING)) {
						// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
						gcFlowTime.push({
							timeNo: 1,
							// 社員勤務情報　dto > 社員ID
							empId: schedule.workInfoDto.employeeId,
							// 勤務固定情報　dto > Optional<勤務タイプ>
							workType: schedule.fixedWorkInforDto.workType,
							color: "#ffc000",
							// 社員勤務情報　dto > 応援か
							isCheering: schedule.workInfoDto.isCheering,

							// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
							startTime: schedule.workScheduleDto.startTime1,
							endTime: schedule.workScheduleDto.endTime1,

							// // 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>, 
							startTimeRange: schedule.fixedWorkInforDto.startTimeRange1,
							endTimeRange: schedule.fixedWorkInforDto.endTimeRange1

						});

						startTimeArr.add(schedule.workScheduleDto.startTime2);
						// 複数回勤務管理.使用区別＝＝true
						// 社員勤務予定dto.Optional<開始時刻2>, 社員勤務予定dto.Optional<終了時刻2>
						if (self.dataScreen003A().targetInfor == 1) {
							gcFlowTime.push({
								timeNo: 1,
								empId: schedule.workInfoDto.employeeId,
								workType: schedule.fixedWorkInforDto.workType,
								color: "#ffc000",
								isCheering: schedule.workInfoDto.isCheering,
								startTime: schedule.workScheduleDto.startTime2,
								endTime: schedule.workScheduleDto.endTime2,
								startTimeRange: schedule.fixedWorkInforDto.startTimeRange2,
								endTimeRange: schedule.fixedWorkInforDto.endTimeRange2
							});
							startTimeArr.add(schedule.workScheduleDto.startTime2);
						}
						typeOfTime = "Changeable"
					}

					// Thời gian Flex time
					// 勤務固定情報　dto．Optional<勤務タイプ>==フレックス勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
					if (schedule.fixedWorkInforDto.workType == WorkTimeForm.FLEX
						&& (schedule.workInfoDto.isCheering == SupportAtr.TIMEZONE_SUPPORTER || schedule.workInfoDto.isCheering == SupportAtr.NOT_CHEERING)) {
						// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
						gcFlexTime.push({
							timeNo: 1,
							// 社員勤務情報　dto > 社員ID
							empId: schedule.workInfoDto.employeeId,
							// 勤務固定情報　dto > Optional<勤務タイプ>
							workType: schedule.fixedWorkInforDto.workType,
							color: "#ccccff",
							// 社員勤務情報　dto > 応援か
							isCheering: schedule.workInfoDto.isCheering,
							// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
							startTime: schedule.workScheduleDto.startTime1,
							endTime: schedule.workScheduleDto.endTime1,
							// // 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>
							startTimeRange: schedule.fixedWorkInforDto.startTimeRange1,
							endTimeRange: schedule.fixedWorkInforDto.endTimeRange1
						});
						startTimeArr.add(schedule.workScheduleDto.startTime1);
						typeOfTime = "Flex"
					}

					// Thời gian core time
					// 勤務固定情報　dto．Optional<勤務タイプ>== フレックス勤務 && 勤務固定情報　dto．Optional<コア開始時刻>とOptional<コア終了時刻>が存在する
					if (schedule.fixedWorkInforDto.workType == WorkTimeForm.FLEX
						&& schedule.fixedWorkInforDto.coreStartTime != null && schedule.fixedWorkInforDto.coreEndTime != null) {
						gcCoreTime.push({
							// 社員勤務情報　dto > 社員ID
							empId: schedule.workInfoDto.employeeId,
							color: "#00ffcc",
							// 勤務固定情報　dto > Optional<勤務タイプ>, Optional<コア開始時刻>, Optional<コア終了時刻>
							workType: schedule.fixedWorkInforDto.workType,
							coreStartTime: schedule.fixedWorkInforDto.coreStartTime,
							coreEndTime: schedule.fixedWorkInforDto.coreEndTime
						});
					}

					// Thời gian holiday time
					// 社員勤務情報　dto．Optional<Map<時間休暇種類, 時間休暇>> 存在する
					if (schedule.workInfoDto.listTimeVacationAndType.length > 0) {
						gcHolidayTime.push({
							// 社員勤務情報　dto > 社員ID, Optional<Map<時間休暇種類, 時間休暇>>
							empId: schedule.workInfoDto.employeeId,
							color: "#c4bd97",
							listTimeVacationAndType: schedule.workInfoDto.listTimeVacationAndType
						})
					}

					// Thời gian chăm sóc / giữ trẻ (short time)
					// 社員勤務情報　dto．Optional<育児介護短時間> が存在する
					if (schedule.workInfoDto.shortTime != null && schedule.workInfoDto.shortTime.length > 0) {
						gcShortTime.push({
							// 社員勤務情報　dto > 社員ID, Optional<Map<時間休暇種類, 時間休暇>>
							empId: schedule.workInfoDto.employeeId,
							color: "#6fa527",
							listShortTime: schedule.workInfoDto.shortTime
						})
					}
				}
				self.timeBrkMinu(totalBreakTime);
				let totalBreakTimeNum = formatById("Clock_Short_HM", (totalBreakTime * 5));
				self.timeBrkText(totalBreakTimeNum);
				if (schedule.workScheduleDto != null) {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'),
						worktypeCode: schedule.workScheduleDto.workTypeCode == null ? "" : schedule.workScheduleDto.workTypeCode,
						worktype: workTypeName,
						worktimeCode: schedule.workScheduleDto.workTimeCode == null ? "" : schedule.workScheduleDto.workTimeCode,
						worktime: workTimeName,
						startTime1: startTime1, endTime1: endTime1,
						startTime2: startTime2, endTime2: endTime2,
						totalTime: totalTime, breaktime: self.timeBrkText(),
						color: self.dataColorA6
					});
				} else {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'), worktypeCode: "",
						worktype: "", worktimeCode: "", worktime: "",
						startTime1: "", endTime1: "",
						startTime2: "", endTime2: "",
						totalTime: "", breaktime: "", color: ""
					});
				}
				// dữ liệu của chart
				timeGantChart.push({
					empId: schedule.empId,
					typeOfTime: typeOfTime,
					gantCharts: self.dataScreen003A().targetInfor,
					gcFixedWorkTime: gcFixedWorkTime,
					gcBreakTime: gcBreakTime,
					gcOverTime: gcOverTime,
					gcSupportTime: gcSupportTime,
					gcFlowTime: gcFlowTime,
					gcFlexTime: gcFlexTime,
					gcCoreTime: gcCoreTime,
					gcHolidayTime: gcHolidayTime,
					gcShortTime: gcShortTime
				});
			});
			self.leftDs = leftDs;
			self.disableDs = disableDs;
			self.dataOfGantChart = timeGantChart;
			self.midDataGC = middleDs;
			startTimeArr = _.sortBy(startTimeArr, [function(o: any) { return o; }]);
		}

		public nextDay() {
			let self = this;
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.nextDayImpl();
				}).ifNo(() => { return; });
			} else {
				self.nextDayImpl();
			}
		}

		public nextDayImpl() {
			let self = this;
			self.changeTargetDate(1, 1);
			self.checkNext(true);
			self.checkPrv(true);
			$("#icon-prev-all").css("filter", "contrast(1)");
			$("#icon-prev-one").css("filter", "contrast(1)");
			if (self.dataFromA().endDate <= self.targetDate()) {
				self.targetDate(self.dataFromA().endDate);
				self.checkNext(false);
				$("#icon-next-one").css("filter", "contrast(0.7)");
				$("#icon-next-all").css("filter", "contrast(0.7)");
			}
			self.hoverEvent(self.targetDate());
			self.convertDataIntoExtable();
			self.destroyAndCreateGrid(self.lstEmpId);
		}

		public nextAllDay() {
			let self = this, i = 7,
				nextDay: any = moment(moment(self.targetDate()).add(7, 'd').format('YYYY/MM/DD')),
				checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.nextAllDayImpl(i, nextDay);
				}).ifNo(() => { return; });
			} else {
				self.nextAllDayImpl(i, nextDay);
			}
		}

		public nextAllDayImpl(i: number, nextDay: any) {
			let self = this;
			self.checkNext(true);
			self.checkPrv(true);
			$("#icon-prev-all").css("filter", "contrast(1)");
			$("#icon-prev-one").css("filter", "contrast(1)");
			if (self.dataFromA().endDate <= nextDay._i) {
				let date: number = moment(self.dataFromA().endDate).date() - moment(self.targetDate()).date();
				self.checkNext(false);
				$("#icon-next-all").css("filter", "contrast(0.7)");
				$("#icon-next-one").css("filter", "contrast(0.7)");

				self.changeTargetDate(1, date);
			} else {
				self.changeTargetDate(1, i);
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId);
		}

		public prevDay() {
			let self = this;
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.prevDayImpl();
				}).ifNo(() => { return; });
			} else {
				self.prevDayImpl();
			}
		}

		public prevDayImpl() {
			let self = this;
			self.changeTargetDate(0, 1);
			self.checkPrv(true);
			self.checkNext(true);
			$("#icon-next-all").css("filter", "contrast(1)");
			$("#icon-next-one").css("filter", "contrast(1)");
			if (self.dataFromA().startDate >= self.targetDate()) {
				self.targetDate(self.dataFromA().startDate);
				self.checkPrv(false);
				$("#icon-prev-one").css("filter", "contrast(0.7)");
				$("#icon-prev-all").css("filter", "contrast(0.7)");
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId);
		}

		public prevAllDay() {
			let self = this, i = 7;
			let prvDay: any = moment(moment(self.targetDate()).subtract(7, 'd').format('YYYY/MM/DD'));
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.prevAllDayImpl(i, prvDay);
				}).ifNo(() => { return; });
			} else {
				self.prevAllDayImpl(i, prvDay);
			}
		}

		public prevAllDayImpl(i: number, prvDay: any) {
			let self = this;
			self.checkPrv(true);
			self.checkNext(true);
			$("#icon-next-all").css("filter", "contrast(1)");
			$("#icon-next-one").css("filter", "contrast(1)");
			if (self.dataFromA().startDate >= prvDay._i) {
				let date: number = moment(self.targetDate()).date() - moment(self.dataFromA().startDate).date();
				self.checkPrv(false);
				$("#icon-prev-all").css("filter", "contrast(0.7)");
				$("#icon-prev-one").css("filter", "contrast(0.7)");

				self.changeTargetDate(0, date);
			} else {
				self.changeTargetDate(0, 7);
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId);
		}

		public changeTargetDate(nextOrprev: number, index: number) {
			let self = this;
			if (nextOrprev === 1) {
				let time: any = moment(moment(self.targetDate()).add(index, 'd').format('YYYY/MM/DD'));
				self.targetDate(time._i);
			} else {
				let time: any = moment(moment(self.targetDate()).subtract(index, 'd').format('YYYY/MM/DD'));
				self.targetDate(time._i);
			}
			self.targetDateDay(self.targetDate() + moment(self.targetDate()).format('(ddd)'));
		}

		public checkTimeInfo(worktypeCode: any, worktimeCode: any, startTime1: any, startTime2: any, endTime1: any, endTime2: any) {
			let command: any = {
				workType: worktypeCode,
				workTime: worktimeCode,
				workTime1: startTime1 != "" && startTime1 != null ? new TimeZoneDto(new TimeOfDayDto(0, _.isString(startTime1) ? Math.round(duration.parseString(startTime1).toValue()) : startTime1),
					new TimeOfDayDto(0, _.isString(endTime1) ? Math.round(duration.parseString(endTime1).toValue()) : endTime1)) : null,
				workTime2: startTime2 != "" && startTime2 != null ? new TimeZoneDto(new TimeOfDayDto(0, _.isString(startTime2) ? Math.round(duration.parseString(startTime2).toValue()) : startTime2),
					new TimeOfDayDto(0, _.isString(endTime2) ? Math.round(duration.parseString(endTime2).toValue()) : endTime2)) : null,
			}
			service.checkTimeIsIncorrect(command).done(function(result) {
				let errors = [];
				for (let i = 0; i < result.length; i++) {
					if (!result[i].check) {
						if (result[i].timeSpan == null) {
							errors.push({
								message: nts.uk.resource.getMessage('Msg_439', getText('KDL045_12')),
								messageId: "Msg_439",
								supplements: {}
							});
						} else {
							if (result[i].timeSpan.startTime == result[i].timeSpan.endTime) {
								errors.push({
									message: nts.uk.resource.getMessage('Msg_2058', [result[i].nameError, result[i].timeInput]),
									messageId: "Msg_2058",
									supplements: {}
								});
							} else {
								errors.push({
									message: nts.uk.resource.getMessage('Msg_1772', [result[i].nameError, formatById("Clock_Short_HM", result[i].timeSpan.startTime), formatById("Clock_Short_HM", result[i].timeSpan.endTime)]),
									messageId: "Msg_1772",
									supplements: {}
								});
							}
						}
					}
				}

				if (errors.length > 0)
					bundledErrors({ errors: errors });
			}).fail(function(res: any) {
				errorDialog({ messageId: res.messageId, messageParams: res.parameterIds });
			}).always(function() {
				block.clear();
			});
		}

		public getInforFirt() {
			let self = this;
			let dfd = $.Deferred<any>();
			let targetOrgDto = {
				unit: self.dataFromA().unit,
				workplaceId: self.dataFromA().workplaceId,
				workplaceGroupId: self.dataFromA().workplaceGroupId
			}
			// ①<<ScreenQuery>> 初期起動の情報取得
			service.getDataStartScreen(targetOrgDto)
				.done((data: model.GetInfoInitStartKsu003Dto) => {
					self.dataInitStartKsu003Dto(data);
					self.organizationName(self.dataInitStartKsu003Dto().displayInforOrganization.displayName);
					self.dataScreen003A().targetInfor = data.manageMultiDto.useATR;
					self.timeRange = self.dataInitStartKsu003Dto().byDateDto.dispRange == 0 ? 24 : 48;
					self.dataScreen003A().targetInfor = self.dataInitStartKsu003Dto().manageMultiDto.useATR;
					self.dataScreen003A().scheCorrection = self.dataInitStartKsu003Dto().functionControlDto != null
						? self.dataInitStartKsu003Dto().functionControlDto.changeableWorks : [0, 1, 2, 3];
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
				});
		}

		// ②<<ScreenQuery>> 日付別勤務情報で表示する
		public getWorkingByDate(targetDate: any, check: number): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();
			block.grayout();
			let targetOrg = {
				unit: self.dataFromA().unit,
				workplaceId: self.dataFromA().workplaceId != null ? self.dataFromA().workplaceId : null,
				workplaceGroupId: self.dataFromA().workplaceGroupId != null ? self.dataFromA().workplaceGroupId : null
			}
			let lstEmpId = _.flatMap(self.dataFromA().listEmp, c => [c.id]);
			let param = {
				targetOrg: targetOrg,
				lstEmpId: lstEmpId,
				date: targetDate
			};
			service.displayDataKsu003(param)
				.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
					if (self.checked() === "0") {
						let dataSort = self.sortEmpByTime(data);
						dataSort = dataSort.sort(function(a, b) {
							return (a.startTime != null ? a.startTime : Infinity) - (b.startTime != null ? b.startTime : Infinity);
						});
						self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
							return _.findIndex(dataSort, x => { return x.empId == a.empId }) - _.findIndex(dataSort, x => { return x.empId == b.empId });
						});
					}
					data = data.sort(function(a: any, b: any) {
						return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) - _.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
					});
					self.dataScreen003A().employeeInfo = data;
					self.dataScreen003AFirst().employeeInfo = data;
					self.employeeScheduleInfo = _.map(self.dataScreen003A().employeeInfo, (x) => ({
						empId: x.empId,
						startTime1: x.workScheduleDto != null && x.workScheduleDto.startTime1 != null ? x.workScheduleDto.startTime1 : "",
						endTime1: x.workScheduleDto != null && x.workScheduleDto.endTime1 != null ? x.workScheduleDto.endTime1 : "",
						startTime2: x.workScheduleDto != null && x.workScheduleDto.startTime2 != null ? x.workScheduleDto.startTime2 : "",
						endTime2: x.workScheduleDto != null && x.workScheduleDto.endTime2 != null ? x.workScheduleDto.endTime2 : "",
						listBreakTimeZoneDto: x.workScheduleDto != null &&
							x.workScheduleDto.listBreakTimeZoneDto != null ? x.workScheduleDto.listBreakTimeZoneDto : ""
					}))

					self.employeeScheduleInfo = self.employeeScheduleInfo.sort(function(a, b) {
						return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) -
							_.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
					});
					dfd.resolve(data);
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
					block.clear();
				});
			return dfd.promise();
		}

		public initExtableData() {
			let self = this;
			setTimeout(() => {
				self.initExtableChart(self.dataOfGantChart, self.leftDs, self.midDataGC, self.disableDs);
				self.showHide();
				$("#extable-ksu003").exTable("scrollBack", 0, { h: Math.round(self.dataInitStartKsu003Dto().byDateDto.initDispStart * 12) });
				self.dataA6 = null;
			}, 200)
		}

		// 社員勤務予定と勤務固定情報を取得する
		public getEmpWorkFixedWorkInfo(workTypeCode: string, workTimeCode: string, empId?: string, index?: number): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>(), indexEmp = 0;
			let targetOrgDto = [{
				workTypeCode: workTypeCode,
				workTimeCode: workTimeCode
			}]
			indexEmp = _.findIndex(self.dataScreen003A().employeeInfo, x => { return x.empId === empId });
			service.getEmpWorkFixedWorkInfo(targetOrgDto)
				.done((data: model.DisplayWorkInfoByDateDto) => {
					if (data.fixedWorkInforDto.workType == null) return;
					self.dataScreen003A().employeeInfo[indexEmp].fixedWorkInforDto = data.fixedWorkInforDto;
					self.dataScreen003A().employeeInfo[indexEmp].workScheduleDto = data.workScheduleDto;

					dfd.resolve(data);
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
					ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
						type: "Flex",
						options: {
							id: `lgc` + index,
							start: -1,
							end: -1,
							lineNo: index
						}
					}]);

					$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", "");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", "");
				}).always(function() {
				});
			return dfd.promise();
		}

		// 勤務種類を変更する

		public sortEmpByTime(employeeInfo: Array<model.DisplayWorkInfoByDateDto>) {
			let dataSort = _.map(employeeInfo, (x: model.DisplayWorkInfoByDateDto) => ({
				empId: x.empId,
				startTime: (x.workScheduleDto != null && (x.workScheduleDto.startTime1 != null)) ? x.workScheduleDto.startTime1 : null
			}))

			return dataSort;
		}

		// 社員を並び替える
		public sortEmployee(value: any) {
			let self = this;
			let dataSort = self.sortEmpByTime(self.dataScreen003A().employeeInfo);
			let param = {
				ymd: self.targetDate(),
				lstEmpId: _.map(self.lstEmpId, (x: IEmpidName) => { return { empId: x.empId, code: x.code } })
			}
			if (value === '0') {
				dataSort = dataSort.sort(function(a: any, b: any) {
					return (a.startTime != null ? a.startTime : Infinity) - (b.startTime != null ? b.startTime : Infinity);
				});
				self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
					return _.findIndex(dataSort, x => { return x.empId == a.empId }) - _.findIndex(dataSort, x => { return x.empId == b.empId });
				});
				self.localStore().lstEmpIdSort = self.lstEmpId;
				storage.setItemAsJson(self.KEY, self.localStore());
				self.destroyAndCreateGrid(self.lstEmpId);
			} else {
				service.sortEmployee(param)
					.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
						self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
							return _.findIndex(data, x => { return x == a.empId }) - _.findIndex(data, x => { return x == b.empId });
						});
						self.localStore().lstEmpIdSort = self.lstEmpId;
						storage.setItemAsJson(self.KEY, self.localStore());
						self.destroyAndCreateGrid(self.lstEmpId);
					}).fail(function(error) {
						errorDialog({ messageId: error.messageId });
					}).always(function() {
					});
			}
			self.localStore().startTimeSort = value;
			storage.setItemAsJson(self.KEY, self.localStore());
		}

		destroyAndCreateGrid(lstId: any) {
			let self = this;
			let leftDs: any = [];
			leftDs.push({
				empId: _.map(lstId, (x: IEmpidName) => { return x.empId }),
				color: ""
			});
			$("#extable-ksu003").children().remove();
			$("#extable-ksu003").removeData();
			self.initExtableData();
		}

		// open dialog kdl045
		public openKdl045Dialog(empId: string) {
			let self = this, lineNo = _.findIndex(self.lstEmpId, (x) => { return x.empId === empId; }),
				dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[lineNo];
			block.grayout();
			if (self.dataScreen003A().employeeInfo[lineNo].workScheduleDto != null) {
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTypeCode = dataMid.worktypeCode.trim();
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTimeCode = dataMid.worktimeCode.trim();
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime1 = dataMid.startTime1;
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime1 = dataMid.endTime1;
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime2 = dataMid.startTime2;
				self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime2 = dataMid.endTime2;

				if (self.dataScreen003AFirst().employeeInfo[lineNo].workScheduleDto.workTimeCode == "") {
					self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.listBreakTimeZoneDto = [];
				}
			}

			let data: Array<model.DisplayWorkInfoByDateDto> = _.filter(self.dataScreen003A().employeeInfo, (x) => { return x.empId === empId; }); // lấy dữl iệu theo empId từ list dữ liệu của màn KSU003	

			let dataShare: any = {
				employeeInfo: data[0],
				targetInfor: self.dataScreen003A().targetInfor,
				canModified: 1, //self.dataScreen003A().canModified, // comment tạm để test
				scheCorrection: self.dataScreen003A().scheCorrection,
				unit: self.dataFromA().unit,
				targetId: self.dataFromA().unit === 0 ? self.dataFromA().workplaceId : self.dataFromA().workplaceGroupId,
				workplaceName: self.dataFromA().workplaceName
			};
			setShared('dataShareTo045', dataShare);
			nts.uk.ui.windows.sub.modal('/view/kdl/045/a/index.xhtml').onClosed(() => {
				self.dataScreen045A(getShared('dataFromKdl045'));
				if (!_.isNil(self.dataScreen045A())) {
					self.checkGetInfo = true;
					let lstBrkTime = self.dataScreen045A().workScheduleDto.listBreakTimeZoneDto, totalBrkTime = "",
						lstBreak: any = lstBrkTime, totalTimebr: any = null;
					lstBrkTime.forEach((x: any) => {
						totalTimebr += x.endTime - x.startTime;
					})

					totalBrkTime = totalTimebr != null ? formatById("Clock_Short_HM", totalTimebr) : ""

					let totalTime = self.calcTotalTime(self.dataScreen045A().workScheduleDto),
						schedule = self.dataScreen045A().workScheduleDto,
						fixed = self.dataScreen045A().fixedWorkInforDto,
						info = self.dataScreen045A().workInfoDto;

					self.setDataToMidExtable(empId, schedule, fixed, totalTime, totalBrkTime);
					self.dataScreen003A().employeeInfo[lineNo].workScheduleDto = {
						startTime1: schedule.startTime1,
						startTime1Status: schedule.endTime1,
						endTime1: schedule.endTime1,
						endTime1Status: null,
						startTime2: schedule.startTime2,
						startTime2Status: null,
						endTime2: schedule.endTime2,
						endTime2Status: null,
						listBreakTimeZoneDto: lstBrkTime.length > 0 ? lstBrkTime : [],
						workTypeCode: schedule.workTypeCode,
						breakTimeStatus: null,
						workTypeStatus: null,
						workTimeCode: schedule.workTimeCode,
						workTimeStatus: null
					}

					self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto = {
						workTimeName: fixed.workTimeName,
						coreStartTime: null,
						coreEndTime: null,
						overtimeHours: [],
						startTimeRange1: null,
						endTimeRange1: null,
						workTypeName: fixed.workTypeName,
						startTimeRange2: null,
						endTimeRange2: null,
						fixBreakTime: fixed.fixBreakTime,
						workType: fixed.workType
					}

					self.dataScreen003A().employeeInfo[lineNo].workInfoDto.directAtr = info.directAtr;
					self.dataScreen003A().employeeInfo[lineNo].workInfoDto.bounceAtr = info.bounceAtr;

					self.convertDataIntoExtable();

					let datafilter: Array<ITimeGantChart> = _.filter(self.dataOfGantChart, (x: any) => { return x.empId === empId });
					if (datafilter.length > 0) {
						//self.updateGantChart(datafilter, lineNo, fixedGc, lstBreak, indexS, indexF);
						self.addAllChart(datafilter, lineNo, [], self.midDataGC, "KDL045", lstBreak);
						ruler.replaceAt(lineNo, [
							...self.allGcShow
						]);
					}
				}
				self.checkGetInfo = false;
				block.clear();
			});
		}

		public calcTotalTime(workScheduleDto: model.EmployeeWorkScheduleDto) {
			let totalTime: any = null;
			if (!_.isNil(workScheduleDto)) {
				if (workScheduleDto.endTime2 != null && workScheduleDto.startTime2 != null)
					totalTime = (workScheduleDto.endTime1 - workScheduleDto.startTime1) + (workScheduleDto.endTime2 - workScheduleDto.startTime2);
				else if (workScheduleDto.endTime1 != null && workScheduleDto.startTime1 != null)
					totalTime = (workScheduleDto.endTime1 - workScheduleDto.startTime1);

				totalTime = totalTime != null ? formatById("Clock_Short_HM", totalTime) : "";
			}
			return totalTime;
		}

		/** A1_3 - Open dialog G */
		public openGDialog() {
			let self = this;
			block.grayout();
			let dataShare = {
				employeeIDs: _.map(self.lstEmpId, (x: IEmpidName) => { return x.empId }),
				startDate: self.targetDate(),
				endDate: self.targetDate(),
				employeeInfo: self.dataFromA().listEmp
			};
			setShared('dataShareDialogG', dataShare);
			nts.uk.ui.windows.sub.modal('/view/ksu/001/g/index.xhtml').onClosed(() => {
				block.clear();
			});
		}

		public openKdl003Dialog(workTypeCode: string, workTimeCode: string, empId: string) {
			let self = this;
			block.grayout();
			let dataShare = {
				workTypeCode: workTypeCode,
				workTimeCode: workTimeCode
			};
			setShared('parentCodes', dataShare, true);
			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(() => {
				let dataShareKdl003 = getShared('childData');

				if (!_.isNil(dataShareKdl003)) {
					let param = [{
						workTypeCode: dataShareKdl003.selectedWorkTypeCode,
						workTimeCode: dataShareKdl003.selectedWorkTimeCode
					}]
					// 社員勤務予定と勤務固定情報を取得する
					service.getEmpWorkFixedWorkInfo(param).done((data: model.DataFrom045) => {
						if (!_.isNil(data)) {
							if (data.workScheduleDto.startTime1) {
								self.checkClearTime = false;
								self.checkUpdateMidChart = false;
							}
							let lineNo = _.findIndex(self.dataScreen003A().employeeInfo, (x) => { return x.empId === empId; });
							self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto = data.fixedWorkInforDto;
							self.dataScreen003A().employeeInfo[lineNo].workScheduleDto = data.workScheduleDto;

							let totalTime = self.calcTotalTime(data.workScheduleDto), totalTimebr = 0;

							if (data.workScheduleDto.listBreakTimeZoneDto != null && data.workScheduleDto.listBreakTimeZoneDto.length > 0) {
								data.workScheduleDto.listBreakTimeZoneDto.forEach((x: any) => {
									totalTimebr += (!_.isNil(x.endTime) ? x.endTime : x.end) - (!_.isNil(x.startTime) ? x.startTime : x.start);
								})
							}

							let totalBrkTime = totalTimebr != null ? formatById("Clock_Short_HM", totalTimebr) : "";

							self.setDataToMidExtable(empId, data.workScheduleDto, data.fixedWorkInforDto, totalTime, totalBrkTime);
							self.convertDataIntoExtable(lineNo);

							let datafilter: Array<ITimeGantChart> = _.filter(self.dataOfGantChart, (x: any) => { return x.empId === empId });
							if (datafilter.length > 0) {
								//self.updateGantChart(datafilter, lineNo, fixedGc, lstBreak, indexS, indexF);
								self.addAllChart(datafilter, lineNo, [], self.midDataGC, "KDL003");
								if (self.allGcShow.length > 0) {
									ruler.replaceAt(lineNo, [
										...self.allGcShow
									]);
								} else {
									ruler.replaceAt(lineNo, [{ // xóa chart khi là ngày nghỉ
										type: "Flex",
										options: {
											id: `lgc` + lineNo,
											start: -1,
											end: -1,
											lineNo: lineNo
										}
									}]);
								}
							}

						}

					}).fail(function(error) {
						errorDialog({ messageId: error.messageId });
					}).always(function() {
						self.checkUpdateMidChart = true;
					});
				}
				block.clear();
			});
		}

		/** A1_4 - Close modal */
		public closeDialog(): void {
			nts.uk.ui.windows.close();
		}

		// Khởi tạo EXTABLE-GANTCHART
		initExtableChart(timeGantChart: Array<ITimeGantChart>, leftDs: any, midData: any, disableDs: any): void {

			let self = this;
			let displayRange = self.timeRange, totalBreakTime = "0:00";

			let middleContentDeco: any = [], leftContentDeco: any = [], detailContentDeco: any = [];

			// phần leftMost
			let leftmostColumns = [],
				leftmostHeader = {},
				leftmostContent = {}, disableDSFilter = [];

			leftmostColumns = [{
				key: "empName",
				icon: { for: "body", class: "icon-leftmost", width: "25px" },
				headerText: getText('KSU003_20'), width: "160px", control: "link",
				css: { whiteSpace: "pre" }
			}, {
				key: "cert", headerText: getText('KSU003_21'), width: "40px", control: "button", handler: function(e: any) {
					self.openKdl045Dialog(e.empId)
				}
			}];

			leftmostHeader = {
				columns: leftmostColumns,
				rowHeight: "33px",
				width: "200px"
			};

			let leftmostDs = [], middleDs = [];
			for (let i = 0; i < self.lstEmpId.length; i++) {
				let dataLeft: any = _.filter(self.lstEmpId, (x: any) => { return x.empId === self.lstEmpId[i].empId }),
					datafilter = _.filter(midData, (x: any) => { return x.empId === self.lstEmpId[i].empId }),
					dataMid = datafilter[0],
					eName: any = dataLeft[0].code + " " + dataLeft[0].name;
				totalBreakTime = _.isNil(dataMid) ? "" : dataMid.breaktime;
				let leftDSFilter = _.filter(leftDs, (x: any) => { return x.empId === self.lstEmpId[i].empId });
				disableDSFilter = _.filter(disableDs, (x: any) => { return x.empId === self.lstEmpId[i].empId }); // list không dùng schedule

				leftmostDs.push({ empId: self.lstEmpId[i].empId, empName: eName, cert: getText('KSU003_22') });

				leftContentDeco.push(new CellColor("empName", leftDSFilter[0].empId, leftDSFilter[0].color)); // set màu cho emp name
				leftContentDeco.push(new CellColor("cert", leftDSFilter[0].empId, leftDSFilter[0].color));
				if (disableDSFilter.length > 0) {
					leftContentDeco.push(new CellColor("empName", disableDSFilter[0].empId, "xseal", leftDSFilter[0].color)); // set màu cho emp name khi bị dis
					leftContentDeco.push(new CellColor("cert", disableDSFilter[0].empId, "xseal", leftDSFilter[0].color));

					middleContentDeco.push(new CellColor("worktypeCode", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktypeName", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktimeCode", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktimeName", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("startTime1", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("endTime1", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("startTime2", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("endTime2", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("totalTime", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("breaktime", disableDSFilter[0].empId, "xseal", disableDSFilter[0].color));
					for (let z = self.dataInitStartKsu003Dto().byDateDto.dispStart; z <= (displayRange + self.dataInitStartKsu003Dto().byDateDto.dispStart); z++) {
						detailContentDeco.push(new CellColor(z.toString(), disableDSFilter[0].empId, disableDSFilter[0].color));
					}
				}
				
				
				// set ẩn hiện A6, A7, A8
				if (self.dataScreen003A().employeeInfo[i].workInfoDto.isNeedWorkSchedule != 0) { // ※2
					if (self.dataScreen003A().canModified == 0) { // パラメータ．修正可能==０：参照 // ※4
						if (self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed == 1) { // 社員勤務情報　dto．確定済みか == true // ※4
							middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i].empId, "xseal", 0));
						}

						if (_.isNil(dataMid.worktimeCode) || dataMid.worktimeCode == "") {  // ※9 none 就業時間帯コードが存在する
							middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId, "xseal", 0));
						}

						if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null &&
							self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != null &&
							(self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != 0 &&
								self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != 1 &&
								self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != 2)) { // ※5
							middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal", 0));
							if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType === 1) {
								middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
								middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
							}
						}
					}
				}

				if (dataMid.color != "" && dataMid.color !== null) {
					let dataColor_A6 = _.filter(dataMid.color, (x: any) => { return x.empId === self.lstEmpId[i].empId });

					middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i], dataColor_A6[0].workTypeColor));
					middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i], dataColor_A6[0].workTypeColor));
					middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i], dataColor_A6[0].workTimeColor));
					middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i], dataColor_A6[0].workTimeColor));
					middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i], dataColor_A6[0].startTime1Color));
					middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i], dataColor_A6[0].endTime1Color));
					middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i], dataColor_A6[0].startTime2Color));
					middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i], dataColor_A6[0].endTime2Color));
					middleContentDeco.push(new CellColor("breaktime", self.lstEmpId[i], dataColor_A6[0].breakTimeColor));
				}


				if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.fixBreakTime === 0) { // ※8
					middleContentDeco.push(new CellColor("breaktime", disableDSFilter[0].empId, "xseal", 0));
				}

				middleDs.push({
					empId: dataMid.empId, worktypeCode: dataMid.worktypeCode + " ",
					worktypeName: dataMid.worktype, worktimeCode: dataMid.worktimeCode + " ", worktimeName: dataMid.worktime,
					startTime1: dataMid.startTime1, endTime1: dataMid.endTime1,
					startTime2: dataMid.startTime2, endTime2: dataMid.endTime2,
					totalTime: dataMid.totalTime, breaktime: totalBreakTime === "" ? "0:00" : totalBreakTime
				});
			}

			leftmostContent = {
				columns: leftmostColumns,
				dataSource: leftmostDs,
				primaryKey: "empId",
				features: [{
					name: "BodyCellStyle",
					decorator: leftContentDeco
				}]
			};
			// Phần middle
			let middleColumns = [], middleHeader = {}, middleContent = {};
			middleColumns = [
				{
					headerText: getText('KSU003_23'), group: [
						{ headerText: "", key: "worktypeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTypeCode" },
						{
							headerText: "", key: "worktypeName", width: "38px", control: "link", primitiveValue: "WorkTypeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
								self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId);
							}
						}]
				},
				{
					headerText: getText('KSU003_25'), group: [
						{ headerText: "", key: "worktimeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTimeCode" },
						{
							headerText: "", key: "worktimeName", width: "38px", control: "link", primitiveValue: "WorkTimeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
								self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId);
							}
						}]
				},
				{
					headerText: getText('KSU003_27'), group: [
						{ headerText: "", key: "startTime1", width: "40px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				},
				{
					headerText: getText('KSU003_28'), group: [
						{ headerText: "", key: "endTime1", width: "40px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				},
				{
					headerText: getText('KSU003_29'), group: [
						{ headerText: "", key: "startTime2", width: "40px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				},
				{
					headerText: getText('KSU003_30'), group: [
						{ headerText: "", key: "endTime2", width: "40px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				},
				{
					headerText: getText('KSU003_31'), group: [
						{ headerText: "", key: "totalTime", width: "40px", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				},
				{
					headerText: getText('KSU003_32'), group: [
						{ headerText: "", key: "breaktime", width: "35px", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
				}
			];
			middleHeader = {
				columns: middleColumns,
				width: "391px",
				rowHeight: "33px"
			};
			middleContent = {
				columns: middleColumns,
				dataSource: middleDs,
				primaryKey: "empId",
				features: [{
					name: "BodyCellStyle",
					decorator: middleContentDeco
				}]
			};

			let width = "42px", detailColumns = [], detailHeaderDs = [], detailContent = {}, detailHeader = {};

			// A8_2 TQP
			// let timesOfHeader : model.DisplaySettingByDateDto  = self.dataInitStartKsu003Dto().byDateDto();
			for (let y = self.dataInitStartKsu003Dto().byDateDto.dispStart; y <= (displayRange + self.dataInitStartKsu003Dto().byDateDto.dispStart); y++) {
				if (y == self.dataInitStartKsu003Dto().byDateDto.dispStart) {
					detailColumns.push({ key: "empId", width: "0px", headerText: "ABC", visible: false });
				} else {
					detailColumns.push({ key: (y - 1).toString(), width: width });
				}
			}
			// Phần detail
			detailHeaderDs = [{ empId: "" }];
			let detailHeaders = {};
			for (let y = self.dataInitStartKsu003Dto().byDateDto.dispStart; y <= (displayRange + self.dataInitStartKsu003Dto().byDateDto.dispStart); y++) {
				detailHeaderDs[0][y] = y.toString();
				detailHeaders[y] = "";
			}

			detailHeader = {
				columns: detailColumns,
				dataSource: detailHeaderDs,
				rowHeight: "33px",
				width: "1008px"
			};

			let detailContentDs = [];
			for (let z = 0; z < self.lstEmpId.length; z++) {
				let datafilter = _.filter(timeGantChart, (x: any) => { return x.empId === self.lstEmpId[z].empId });
				detailContentDs.push({ empId: datafilter[0].empId, ...detailHeaders });
			}

			detailContent = {
				columns: detailColumns,
				dataSource: detailContentDs,
				primaryKey: "empId",
				features: [{
					name: "BodyCellStyle",
					decorator: detailContentDeco
				}]
			};

			let extable = new exTable.ExTable($("#extable-ksu003"), {
				headerHeight: "33px",
				bodyRowHeight: "30px",
				bodyHeight: "400px",
				horizontalSumHeaderHeight: "75px",
				horizontalSumBodyHeight: "140px",
				horizontalSumBodyRowHeight: "20px",
				areaResize: false,
				manipulatorId: self.employeeIdLogin,
				manipulatorKey: "empId",
				bodyHeightMode: "fixed",
				showTooltipIfOverflow: true,
				errorMessagePopup: true,
				windowXOccupation: 40,
				windowYOccupation: 200
			}).LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
				.MiddleHeader(middleHeader).MiddleContent(middleContent)
				.DetailHeader(detailHeader).DetailContent(detailContent);

			extable.create();
			ruler = extable.getChartRuler();
			self.addTypeOfChart(ruler);

			let lstBreakTime: any = [], lstTimeChart: any = [], totalTimeBrk = 0, dfd = $.Deferred();;
			// (5' ~ 3.5 pixel ~ 12 khoảng trong 60', 10' ~ 7 ~ 6, 15' ~ 10.5 ~ 4, 30' ~ 21 ~ 2, 60' ~ 42 ~ 1)	
			// unitToPx = khoảng pixel theo số phút 
			// start theo pixel = unitToPx * start * (khoảng-pixel/ phút)
			// end theo pixel = unitToPx * end * (khoảng-pixel/ phút)
			for (let i = 0; i < self.lstEmpId.length; i++) {
				let datafilter: Array<ITimeGantChart> = _.filter(timeGantChart, (x: any) => { return x.empId === self.lstEmpId[i].empId });
				self.addAllChart(datafilter, i, lstBreakTime, midData, "");
			}
			// Thay đổi gant chart khi thay đổi giờ
			let recharge = function(detail: any) {
				let index = detail.rowIndex, dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[index];
				let empId = self.lstEmpId[detail.rowIndex].empId, time = null, timeChart: any = null, timeChart2: any = null;
				lstTimeChart = _.filter(self.allTimeChart, (x: any) => { return x.empId === empId })

				if (lstTimeChart.length > 0) {
					if (detail.columnKey === "startTime1" || detail.columnKey === "endTime1" || detail.columnKey === "startTime2" || detail.columnKey === "endTime2")
						time = duration.parseString(detail.value).toValue();
					timeChart = lstTimeChart[0].timeChart;
					timeChart2 = lstTimeChart[0].timeChart2;
					let startMinutes: any = null, endMinutes: any = null, startMinutes2: any = null, endMinutes2: any = null;
					if (detail.columnKey === "startTime1") {
						ruler.extend(detail.rowIndex, `lgc${detail.rowIndex}`, Math.round(time / 5));
						startMinutes = Math.round(time / 5);
						endMinutes = timeChart.endTime;
					} else if (detail.columnKey === "endTime1") {
						ruler.extend(detail.rowIndex, `lgc${detail.rowIndex}`, null, Math.round(time / 5));
						startMinutes = timeChart.startTime;
						endMinutes = Math.round(time / 5);
					} else if (detail.columnKey === "startTime2" && timeChart2 != null) {
						ruler.extend(detail.rowIndex, `rgc${detail.rowIndex}`, Math.round(time / 5));
						startMinutes2 = Math.round(time / 5);
						endMinutes2 = timeChart2.endTime;
					} else if (detail.columnKey === "endTime2" && timeChart2 != null) {
						ruler.extend(detail.rowIndex, `rgc${detail.rowIndex}`, null, Math.round(time / 5));
						startMinutes2 = timeChart2.startTime;
						endMinutes2 = Math.round(time / 5);
					}

					// Tính lại tổng BREAK-TIME
					let totalTimeNews = 0, totalTimeNews1 = 0;
					if (detail.columnKey === "startTime1" || detail.columnKey === "endTime1" || detail.columnKey === "startTime2" ||
						detail.columnKey === "endTime2") {
						let lstBreakByEmpid = _.filter(lstBreakTime, (x: any) => { return x.empId === empId })
						lstBreakByEmpid.forEach((brk: any) => {
							let brkChart = {
								startTime: brk.startTime,
								endTime: brk.endTime
							}, timeCharts = {
								startTime: startMinutes == null ? timeChart.startTime : startMinutes,
								endTime: endMinutes == null ? timeChart.endTime : endMinutes
							}
							if (_.inRange(brk.startTime, timeCharts.startTime, timeCharts.endTime) || _.inRange(brk.endTime, timeCharts.startTime, timeCharts.endTime)) {
								totalTimeNews += self.calcBreakTime(brkChart, timeCharts);
							}

							if (timeChart2 != null) {
								if (_.inRange(brk.startTime, startMinutes2, endMinutes2) || _.inRange(brk.endTime, startMinutes2, endMinutes2)) {
									timeCharts = {
										startTime: startMinutes2 == null ? timeChart2.startTime : startMinutes2,
										endTime: endMinutes2 == null ? timeChart2.endTime : endMinutes2
									}
									totalTimeNews1 += self.calcBreakTime(brkChart, timeCharts);
								}
							}
						})
						totalTimeBrk = formatById("Clock_Short_HM", ((totalTimeNews + totalTimeNews1) * 5));
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalTimeBrk === 0 ? "0:00" : totalTimeBrk);
					}
				}
			};

			$("#extable-ksu003").on("extablecellupdated", (e: any) => {
				recharge(e.detail);
			});

			$("#extable-ksu003").on("extablecellretained", (e: any) => {
				recharge(e.detail);
			});

			storage.getItem(self.KEY).ifPresent((data: any) => {
				let userInfor: ILocalStore = JSON.parse(data);
				if (userInfor.operationUnit === "0") {
					ruler.setSnatchInterval(1);
				} else if (userInfor.operationUnit === "1") {
					ruler.setSnatchInterval(2);
				} else if (userInfor.operationUnit === "2") {
					ruler.setSnatchInterval(3);
				} else if (userInfor.operationUnit === "3") {
					ruler.setSnatchInterval(6);
				} else if (userInfor.operationUnit === "4") {
					ruler.setSnatchInterval(12);
				}
			});

			// set lock slide and resize chart
			//ruler.setLock([0, 1, 3], true);

			// set height grid theo localStorage đã lưu
			self.setPositionButonDownAndHeightGrid();
		}

		checkTime(command: any, errors: any): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();
			service.checkTimeIsIncorrect(command).done(function(result) {
			}).fail(function(res: any) {
				errors.push({
					message: res.message,
					messageId: res.messageId,
					supplements: {}
				})

			}).always(function() {
				block.clear();
			});

			return dfd.promise();
		}

		/*		checkTimeOfChart(time : any, timeRangeLimit : any){
					
				}*/

		/** ADD-CHART-ZONE */
		addAllChart(datafilter: Array<ITimeGantChart>, i: number, lstBreakTime: any, midData: any, screen: string, lstBrkNew?: any) {
			let self = this, fixedGc: any = [], totalBreakTimeNew: any = 0, timeRangeLimit = Math.round((self.timeRange * 60) / 5);
			let timeChart: any = null, timeChart2: any = null, lgc = "", rgc = "", timeChartOver: any = null, timeChartCore: any = null,
				timeChartBrk: any = null, timeChartHoliday: any = null, timeChartShort: any = null, indexLeft = 0, indexRight = 0;
			let timeMinus: any = [], timeMinus2: any = [];
			if (datafilter != null) {
				if (datafilter[0].typeOfTime != "" || datafilter[0].typeOfTime != null) {
					// add chart for FIXED-TIME - thời gian cố định
					if (datafilter[0].typeOfTime === "Fixed" || datafilter[0].gcFixedWorkTime.length > 0) {
						let fixed = datafilter[0].gcFixedWorkTime;
						timeChart = self.convertTimeToChart(fixed[0].startTime, fixed[0].endTime);
						timeMinus.push({
							startTime: fixed[0].startTime,
							endTime: fixed[0].endTime
						})

						if ((self.timeRange === 24 && timeMinus[0].startTime < 1440 && timeMinus[0].startTime != null || self.timeRange === 48 && timeMinus[0].startTime < 2880 && timeMinus[0].startTime != null)) {
							lgc = ruler.addChartWithType("Fixed", {
								id: `lgc${i}`,
								lineNo: i,
								start: timeChart.startTime > timeRangeLimit ? timeRangeLimit : timeChart.startTime,
								end: timeChart.endTime > timeRangeLimit ? timeRangeLimit : timeChart.endTime,
								limitStartMin: self.checkRangeLimitTime(fixed, timeRangeLimit, 1, 0) ? Math.round((fixed[0].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : 0,
								limitStartMax: self.checkRangeLimitTime(fixed, timeRangeLimit, 2, 0) ? Math.round((fixed[0].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								limitEndMin: self.checkRangeLimitTime(fixed, timeRangeLimit, 3, 0) ? Math.round((fixed[0].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								limitEndMax: self.checkRangeLimitTime(fixed, timeRangeLimit, 4, 0) ? Math.round((fixed[0].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								resizeFinished: (b: any, e: any, p: any) => {
									console.log("test");
								},
								dropFinished: (b: any, e: any) => {
									console.log("test");
								}
							});
							fixedGc.push(self.addChartWithType045(ruler, "Fixed", `lgc${i}`, timeChart, i, null, 0, 9999, 0, 9999));
							indexLeft = indexLeft++;
						}

						if (fixed.length > 1 && fixed[1].startTime != null && fixed[1].endTime != null) {
							timeChart2 = self.convertTimeToChart(fixed[1].startTime, fixed[1].endTime);
							timeMinus2.push({
								startTime: fixed[1].startTime,
								endTime: fixed[1].endTime
							})
							//lgc = self.addChartWithTypes(ruler, "Fixed", `lgc${i}`, timeChart, i);
							if ((self.timeRange === 24 && timeMinus2[0].startTime < 1440 && timeMinus2[0].startTime != null || self.timeRange === 48 && timeMinus2[0].startTime < 2880 && timeMinus2[0].startTime != null)) {
								rgc = ruler.addChartWithType("Fixed", {
									id: `rgc${i}`,
									lineNo: i,
									start: timeChart2.startTime > timeRangeLimit ? timeRangeLimit : timeChart2.startTime,
									end: timeChart2.endTime > timeRangeLimit ? timeRangeLimit : timeChart2.endTime,
									limitStartMin: self.checkRangeLimitTime(fixed, timeRangeLimit, 1, 1) ? Math.round((fixed[1].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : 0,
									limitStartMax: self.checkRangeLimitTime(fixed, timeRangeLimit, 2, 1) ? Math.round((fixed[1].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
									limitEndMin: self.checkRangeLimitTime(fixed, timeRangeLimit, 3, 1) ? Math.round((fixed[1].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
									limitEndMax: self.checkRangeLimitTime(fixed, timeRangeLimit, 4, 1) ? Math.round((fixed[1].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
									resizeFinished: (b: any, e: any, p: any) => {
										console.log("test");
									},
									dropFinished: (b: any, e: any) => {
										console.log("test");
									}
								});
							}

							fixedGc.push(self.addChartWithType045(ruler, "Fixed", `rgc${i}`, timeChart2, i, null, 0, 9999, 0, 9999));
							indexRight = indexRight++;
						}
					}
					// add chart for CHANGEABLE-TIME - thời gian lưu động
					if (datafilter[0].typeOfTime === "Changeable" || datafilter[0].gcFlowTime.length > 0) {
						let changeable = datafilter[0].gcFlowTime
						timeChart = self.convertTimeToChart(changeable[0].startTime, changeable[0].endTime);
						timeMinus.push({
							startTime: changeable[0].startTime,
							endTime: changeable[0].endTime
						})

						lgc = ruler.addChartWithType("Changeable", {
							id: `lgc${i}`,
							lineNo: i,
							start: timeChart.startTime > timeRangeLimit ? timeRangeLimit : timeChart.startTime,
							end: timeChart.endTime > timeRangeLimit ? timeRangeLimit : timeChart.endTime,
							limitStartMin: self.checkRangeLimitTime(changeable, timeRangeLimit, 1, 0) ? Math.round((changeable[0].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : 0,
							limitStartMax: self.checkRangeLimitTime(changeable, timeRangeLimit, 2, 0) ? Math.round((changeable[0].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
							limitEndMin: self.checkRangeLimitTime(changeable, timeRangeLimit, 3, 0) ? Math.round((changeable[0].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
							limitEndMax: self.checkRangeLimitTime(changeable, timeRangeLimit, 4, 0) ? Math.round((changeable[0].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
							resizeFinished: (b: any, e: any, p: any) => {
								console.log("test");
							},
							dropFinished: (b: any, e: any) => {
								console.log("test");
							}
						});

						fixedGc.push(self.addChartWithType045(ruler, "Changeable", `lgc${i}`, timeChart, i, null, 0, 9999, 0, 9999));
						indexLeft = indexLeft++;
						if (changeable.length > 1 && changeable[1].startTime != null && changeable[1].endTime != null) {
							timeChart2 = self.convertTimeToChart(changeable[1].startTime, changeable[1].endTime);
							timeMinus2.push({
								startTime: changeable[1].startTime,
								endTime: changeable[1].endTime
							})
							rgc = ruler.addChartWithType("Changeable", {
								id: `rgc${i}`,
								lineNo: i,
								start: timeChart2.startTime > timeRangeLimit ? timeRangeLimit : timeChart2.startTime,
								end: timeChart2.endTime > timeRangeLimit ? timeRangeLimit : timeChart2.endTime,
								limitStartMin: self.checkRangeLimitTime(changeable, timeRangeLimit, 1, 1) ? Math.round((changeable[1].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : 0,
								limitStartMax: self.checkRangeLimitTime(changeable, timeRangeLimit, 2, 1) ? Math.round((changeable[1].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								limitEndMin: self.checkRangeLimitTime(changeable, timeRangeLimit, 3, 1) ? Math.round((changeable[1].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								limitEndMax: self.checkRangeLimitTime(changeable, timeRangeLimit, 4, 1) ? Math.round((changeable[1].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
								resizeFinished: (b: any, e: any, p: any) => {
									console.log("test");
								},
								dropFinished: (b: any, e: any) => {
									console.log("test");
								}
							});
							fixedGc.push(self.addChartWithType045(ruler, "Changeable", `rgc${i}`, timeChart2, i, null, 0, 9999, 0, 9999));
							indexRight = indexRight++;
						}
					}

					// add chart for FLEX-TIME - thời gian flex
					if (datafilter[0].typeOfTime === "Flex" || datafilter[0].gcFlexTime.length > 0) {
						let flex = datafilter[0].gcFlexTime;
						let coreTime = datafilter[0].gcCoreTime;
						timeChart = self.convertTimeToChart(flex[0].startTime, flex[0].endTime);
						timeMinus.push({
							startTime: flex[0].startTime,
							endTime: flex[0].endTime
						})
						if (coreTime.length > 0) {
							timeChartCore = self.convertTimeToChart(coreTime[0].coreStartTime, coreTime[0].coreEndTime);
						}
						let limitStartMin = self.checkRangeLimitTime(flex, timeRangeLimit, 1, 0) ? Math.round((flex[0].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : 0,
							limitStartMax = self.checkRangeLimitTime(flex, timeRangeLimit, 2, 0) ? Math.round((flex[0].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
							limitEndMin = self.checkRangeLimitTime(flex, timeRangeLimit, 3, 0) ? Math.round((flex[0].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit,
							limitEndMax = self.checkRangeLimitTime(flex, timeRangeLimit, 4, 0) ? Math.round((flex[0].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) : timeRangeLimit;
						if (timeChart.startTime < timeRangeLimit) {
							lgc = ruler.addChartWithType("Flex", {
								id: `lgc${i}`,
								lineNo: i,
								start: timeChart.startTime >= timeRangeLimit ? timeRangeLimit : timeChart.startTime,
								end: timeChart.endTime >= timeRangeLimit ? timeRangeLimit : timeChart.endTime,
								limitStartMin: limitStartMin,
								limitStartMax: limitStartMax,
								limitEndMin: limitEndMin,
								limitEndMax: limitEndMax
							});

							fixedGc.push(self.addChartWithType045(ruler, "Flex", `lgc${i}`, timeChart, i));
							indexLeft = ++indexLeft;
							// CORE-TIME
							if (coreTime.length > 0 && (_.inRange(coreTime[0].coreStartTime, timeMinus[0].startTime, timeMinus[0].endTime) ||
								_.inRange(coreTime[0].coreEndTime, timeMinus[0].startTime, timeMinus[0].endTime)) && _.inRange(timeChartCore.startTime, limitStartMin, limitStartMax)) {
								ruler.addChartWithType("CoreTime", {
									id: `lgc${i}_` + indexLeft,
									parent: `lgc${i}`,
									lineNo: i,
									start: timeChartCore.startTime,
									end: timeChartCore.endTime,
									pin: true
								});

								fixedGc.push(self.addChartWithType045(ruler, "CoreTime", `lgc${i}_` + indexLeft, timeChartCore, i, `lgc${i}`));
								indexLeft = ++indexLeft;
							}
						}
					}
					if (timeChart != null) {
						self.allTimeChart.push({
							empId: datafilter[0].empId,
							timeChart: timeChart,
							timeChart2: timeChart2
						})
					}
				};

				// Lần này chưa đối ứng
				/*let suportTime = datafilter[0].gcSupportTime;
				if (suportTime != null) {
					timeChart = self.convertTimeToChart(suportTime[0].coreStartTime, suportTime[0].coreEndTime);
					spgc = self.addChildChartWithTypes(ruler, "SupportTime", `lgc${i}_` + indexF, timeChart, i, `lgc${i}`)
		
					indexF = ++indexF;
				}*/

				let overTime = datafilter[0].gcOverTime;
				if (overTime.length > 0 && datafilter[0].typeOfTime !== "Changeable") {
					overTime[0].lstOverTime.forEach(y => {
						timeChartOver = self.convertTimeToChart(y.startTime, y.endTime);
						let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;
						if ((timeMinus.length > 0 && timeMinus[0].startTime != null && timeMinus[0].endTime != null) &&
							(timeChart.startTime < Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)) &&
							(_.inRange(timeChartOver.startTime, 0, Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)) ||
								_.inRange(timeChartOver.endTime, 0, Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)))) {
							indexLeft = ++indexLeft;
							self.addChildChartWithTypes(ruler, "OT", id, timeChartOver, i, parent, 0, 9999, 0, 9999, null, 1002)
							fixedGc.push(self.addChartWithType045(ruler, "OT", id, timeChartOver, i, parent, 0, 9999, 0, 9999, 1002));
						}

						if ((timeMinus2.length > 0 && timeMinus2.startTime != null && timeMinus2.endTime != null) &&
							(timeChart2.startTime < Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)) &&
							(_.inRange(timeChartOver.startTime, 0, Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)) ||
								_.inRange(timeChartOver.endTime, 0, Math.round(timeRangeLimit - self.dataInitStartKsu003Dto().byDateDto.dispStart * 12)))) {
							id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
							indexRight = ++indexRight;
							self.addChildChartWithTypes(ruler, "OT", id, timeChartOver, i, parent, 0, 9999, 0, 9999, null, 1002)
							fixedGc.push(self.addChartWithType045(ruler, "OT", id, timeChartOver, i, parent, 0, 9999, 0, 9999, 1002));
						}
					})
				};

				let breakTime: any = [];
				if (datafilter[0].gcBreakTime.length > 0) {
					breakTime = datafilter[0].gcBreakTime[0].lstBreakTime;
					if (screen === "KDL045") {
						breakTime = lstBrkNew;
						datafilter[0].gcBreakTime[0].lstBreakTime = lstBrkNew;
					}
				}

				if (breakTime.length > 0) {
					breakTime.forEach((y: any) => {
						timeChartBrk = self.convertTimeToChart(_.isNil(y.startTime) ? y.start : y.startTime, _.isNil(y.endTime) ? y.end : y.endTime);
						let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;

						if (timeMinus.length > 0 && (_.inRange(y.startTime != null ? y.startTime : y.start, timeMinus[0].startTime, timeMinus[0].endTime) ||
							_.inRange(y.endTimeTime != null ? y.endTime : y.endTime, timeMinus[0].startTime, timeMinus[0].endTime))) {

							ruler.addChartWithType("BreakTime", {
								id: id,
								parent: parent,
								lineNo: i,
								start: timeChartBrk.startTime,
								end: timeChartBrk.endTime,
								limitStartMin: 0,
								limitStartMax: 9999,
								limitEndMin: 0,
								limitEndMax: 9999,
								zindex: 1001,
								resizeFinished: (b: any, e: any, p: any) => {
								},
								dropFinished: (b: any, e: any) => {
									let datafilterBrk = _.filter(midData, (x: any) => { return x.empId === self.lstEmpId[i].empId }),
										dataMidBrk = datafilterBrk[0];
									breakTime = dataMidBrk.breaktime;
									if (b !== timeChartBrk.startTime && e !== timeChartBrk.endTime) {
										$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", breakTime == null ? "0:00" : breakTime + " "); // + " " để phân biệt khi thay đổi vị trí nhưng không thay đổi giá trị
									} else {
										$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", breakTime == null ? "0:00" : breakTime);
									}
								}
							});
							fixedGc.push(self.addChartWithType045(ruler, "BreakTime", id, timeChartBrk, i, parent, 0, 9999, 0, 9999, 1001));
							indexLeft = ++indexLeft;
						}

						if (datafilter[0].gantCharts === 1) {
							if (timeMinus2.length > 0 && (_.inRange(y.startTime != null ? y.startTime : y.start, timeMinus2[0].startTime, timeMinus2[0].endTime) ||
								_.inRange(y.endTimeTime != null ? y.endTime : y.endTime, timeMinus[0].startTime, timeMinus[0].endTime))) {
								id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
								indexRight = ++indexRight;

								ruler.addChartWithType("BreakTime", {
									id: id,
									parent: parent,
									lineNo: i,
									start: timeChartBrk.startTime,
									end: timeChartBrk.endTime,
									limitStartMin: 0,
									limitStartMax: 9999,
									limitEndMin: 0,
									limitEndMax: 9999,
									zindex: 1001,
									resizeFinished: (b: any, e: any, p: any) => {
									},
									dropFinished: (b: any, e: any) => {
										let datafilterBrk = _.filter(midData, (x: any) => { return x.empId === self.lstEmpId[i].empId }),
											dataMidBrk = datafilterBrk[0];
										breakTime = dataMidBrk.breaktime;
										if (b !== timeChartBrk.startTime && e !== timeChartBrk.endTime) {
											$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", self.timeBrkText() == null ? "0:00" : self.timeBrkText() + " ");
										} else {
											$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", self.timeBrkText() == null ? "0:00" : self.timeBrkText());
										}
									}
								});
								fixedGc.push(self.addChartWithType045(ruler, "BreakTime", id, timeChartBrk, i, parent, 0, 9999, 0, 9999, 1001));
								indexRight = ++indexRight;
							}
						}

						lstBreakTime.push({
							startTime: timeChartBrk.startTime,
							endTime: timeChartBrk.endTime,
							id: id,
							empId: datafilter[0].empId
						})

					})
				} else {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", "0:00");
				}

				let shortTime = datafilter[0].gcShortTime;
				if (shortTime.length > 0) {
					shortTime[0].listShortTime.forEach(y => {
						timeChartShort = self.convertTimeToChart(y.startTime.time, y.endTime.time);
						let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;

						if (timeMinus.length > 0 && (_.inRange(y.startTime.time, timeMinus[0].startTime, timeMinus[0].endTime) ||
							_.inRange(y.endTime.time, timeMinus[0].startTime, timeMinus[0].endTime))) {
							indexLeft = ++indexLeft;
							self.addChildChartWithTypes(ruler, "ShortTime", id, timeChartShort, i, parent, 0, 9999, 0, 9999, null, 1002)
							fixedGc.push(self.addChartWithType045(ruler, "ShortTime", id, timeChartShort, i, parent, 0, 9999, 0, 9999, 1002));
						}

						if (timeMinus2.length > 0 && (_.inRange(y.startTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime) ||
							_.inRange(y.endTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime))) {
							id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
							indexRight = ++indexRight;
							self.addChildChartWithTypes(ruler, "ShortTime", id, timeChartShort, i, parent, 0, 9999, 0, 9999, null, 1002)
							fixedGc.push(self.addChartWithType045(ruler, "ShortTime", id, timeChartShort, i, parent, 0, 9999, 0, 9999, 1002));
						}

					})
				}

				let holidayTime = datafilter[0].gcHolidayTime;
				if (holidayTime.length > 0) {
					holidayTime[0].listTimeVacationAndType.forEach(x => {
						x.timeVacation.timeZone.forEach(hld => {
							timeChartHoliday = self.convertTimeToChart(hld.startTime.time, hld.endTime.time);
							let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;

							if (timeMinus.length > 0 && (_.inRange(hld.startTime.time, timeMinus[0].startTime, timeMinus[0].endTime) ||
								_.inRange(hld.endTime.time, timeMinus[0].startTime, timeMinus[0].endTime))) {
								if ((self.timeRange === 24 && hld.startTime.time < 1440 || self.timeRange === 48 && hld.endTime.time < 2880)) {
									indexLeft = ++indexLeft;
								}
							}

							if (timeMinus2.length > 0 && (_.inRange(hld.startTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime) ||
								_.inRange(hld.endTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime))) {
								id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
								if ((self.timeRange === 24 && hld.startTime.time < 1440 || self.timeRange === 48 && hld.endTime.time < 2880)) {
									indexRight = ++indexRight;
								}
							}
							self.addChildChartWithTypes(ruler, "HolidayTime", id, timeChartHoliday, i, parent, 0, 9999, 0, 9999, null, 1003);
							fixedGc.push(self.addChartWithType045(ruler, "HolidayTime", id, timeChartHoliday, i, parent, 0, 9999, 0, 9999, 1003));
						})
					})
				}
			}

			// thay đổi giá trị khi kéo thanh chart
			let timeLeft = timeChart, timeRight = timeChart2;
			$(lgc).on("gcresize", (e: any) => {
				let param = e.detail;
				let startMinute = 0, endMinute = 0;
				startMinute = duration.create(param[0] * 5).text;
				endMinute = duration.create(param[1] * 5).text;
				if (param[2]) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime1", startMinute);
				} else {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime1", endMinute);
				}
				let totalTimeNew = 0;

				let lstBreakByEmpid = _.filter(lstBreakTime, (x: any) => { return x.empId === datafilter[0].empId })
				lstBreakByEmpid.forEach((brk: any) => {
					let startMinutes = param[0], endMinutes = param[1];
					if (_.inRange(brk.startTime, startMinutes, endMinutes) || _.inRange(brk.endTime, startMinutes, endMinutes)) {
						timeLeft = {
							startTime: startMinutes,
							endTime: endMinutes
						}
						totalTimeNew += self.calcBreakTime(brk, timeLeft);
					}

					if (timeChart2 != null && datafilter[0].typeOfTime != "Flex" &&
						(_.inRange(brk.startTime, timeRight.startTime, timeRight.endTime) ||
							_.inRange(brk.endTime, timeRight.startTime, timeRight.endTime))) {
						totalTimeNew += self.calcBreakTime(brk, timeRight);
					}
				})

				totalBreakTimeNew = formatById("Clock_Short_HM", (totalTimeNew * 5));
				$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", totalBreakTimeNew === 0 ? "0:00" : totalBreakTimeNew);
			});

			$(rgc).on("gcresize", (e: any) => {
				let param = e.detail;
				let startMinute, endMinute;
				startMinute = duration.create(param[0] * 5).text;
				endMinute = duration.create(param[1] * 5).text;
				if (param[2]) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime2", startMinute);
				} else {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime2", endMinute);
				}
				let totalTimeNew = 0;
				let lstBreakByEmpid = _.filter(lstBreakTime, (x: any) => { return x.empId === datafilter[0].empId })
				lstBreakByEmpid.forEach((brk: any) => {
					let startMinutes = param[0], endMinutes = param[1];
					if (_.inRange(brk.startTime, startMinutes, endMinutes) || _.inRange(brk.endTime, startMinutes, endMinutes)) {
						timeRight = {
							startTime: startMinutes,
							endTime: endMinutes
						}
						totalTimeNew += self.calcBreakTime(brk, timeRight);
					}

					if (timeChart != null && datafilter[0].typeOfTime != "Flex" &&
						(_.inRange(brk.startTime, timeLeft.startTime, timeLeft.endTime) ||
							_.inRange(brk.endTime, timeLeft.startTime, timeLeft.endTime))) {
						totalTimeNew += self.calcBreakTime(brk, timeLeft);
					}
				})
				totalBreakTimeNew = formatById("Clock_Short_HM", (totalTimeNew * 5));
				$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", totalBreakTimeNew === 0 ? "0:00" : totalBreakTimeNew);
			});

			$(lgc).on("gcdrop", (e: any) => {
				let param = e.detail;
				let minutes = duration.create(param[0] * 5).text;
				$("#extable").exTable("cellValue", "middle", i + "", "startTime1", minutes);
				minutes = duration.create(param[1] * 5).text;
				$("#extable").exTable("cellValue", "middle", i + "", "endTime1", minutes);
			});

			$(rgc).on("gcdrop", (e: any) => {
				let param = e.detail;
				let minutes = duration.create(param[0] * 5).text;
				$("#extable").exTable("cellValue", "middle", i + "", "startTime1", minutes);
				minutes = duration.create(param[1] * 5).text;
				$("#extable").exTable("cellValue", "middle", i + "", "endTime1", minutes);
			});
			self.allGcShow = fixedGc;

			if (window.outerWidth <= 1280) {
				$("#note-sort").css("margin-left", "1030px");
			} else {
				$("#note-sort").css("margin-left", "1100px");
			}
		}

		addChartWithType045(ruler: any, type: any, id: any, timeChart: any, lineNo: any, parent?: any,
			limitStartMin?: any, limitStartMax?: any, limitEndMin?: any, limitEndMax?: any, zIndex?: any) {
			let self = this, timeEnd = self.convertTimePixel(self.timeRange === 24 ? "24:00" : "48:00");
			return {
				type: type,
				options: {
					id: id,
					start: timeChart.startTime <= timeEnd ? timeChart.startTime : timeEnd,
					end: timeChart.endTime <= timeEnd ? timeChart.endTime : timeEnd,
					lineNo: lineNo,
					parent: parent,
					limitStartMin: limitStartMin,
					limitStartMax: limitStartMax,
					limitEndMin: limitEndMin,
					limitEndMax: limitEndMax,
					zIndex: !_.isNil(zIndex) ? zIndex : 1000
				}
			}
		}

		addChildChartWithTypes(ruler: any, type: any, id: any, timeChart: any, lineNo: any, parent?: any,
			limitStartMin?: any, limitStartMax?: any, limitEndMin?: any, limitEndMax?: any, title?: any, zIndex?: any) {
			let self = this, timeEnd = self.convertTimePixel(self.timeRange === 24 ? "24:00" : "48:00");
			return ruler.addChartWithType(type, {
				id: id,
				parent: parent,
				start: timeChart.startTime <= timeEnd ? timeChart.startTime : timeEnd,
				end: timeChart.endTime <= timeEnd ? timeChart.endTime : timeEnd,
				lineNo: lineNo,
				limitStartMin: limitStartMin,
				limitStartMax: limitStartMax,
				limitEndMin: limitEndMin,
				limitEndMax: limitEndMax,
				title: title,
				zIndex: !_.isNil(zIndex) ? zIndex : 1000
			});
		}
		/** ADD-TYPE-CHART */
		addTypeOfChart(ruler: any) {
			let self = this;
			ruler.addType({
				name: "Fixed", color: "#ccccff", lineWidth: 30, canSlide: false, unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "Changeable", color: "#ffc000", lineWidth: 30, canSlide: true, unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "Flex", color: "#ccccff", lineWidth: 30, canSlide: true, unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "BreakTime", followParent: true, color: "#ff9999", lineWidth: 30, canSlide: true,
				unitToPx: self.operationUnit(), pin: true, rollup: true, roundEdge: true, fixed: "Both"
			});

			ruler.addType({
				name: "OT", followParent: true, color: "#ffff00", lineWidth: 30, canSlide: false,
				unitToPx: self.operationUnit(), pin: true, rollup: true, fixed: "Both"
			});

			ruler.addType({
				name: "HolidayTime", followParent: true, color: "#c4bd97", lineWidth: 30, canSlide: false,
				unitToPx: self.operationUnit(), pin: true, rollup: true, roundEdge: false, fixed: "Both"
			});

			ruler.addType({
				name: "ShortTime", followParent: true, color: "#6fa527", lineWidth: 30, canSlide: false,
				unitToPx: self.operationUnit(), pin: true, rollup: true, roundEdge: false, fixed: "Both"
			});

			ruler.addType({
				name: "CoreTime", color: "#00ffcc", lineWidth: 30, unitToPx: self.operationUnit(), fixed: "Both"
			});
		}

		// Kiểm tra giới hạn hiển thị của thanh chart
		checkRangeLimitTime(flex: any, timeRangeLimit: number, type: number, index: number) {
			let check = false, self = this;

			if (flex.length > 0) {
				if (type == 1 && flex[index].startTimeRange != null && Math.round((flex[index].startTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) <= timeRangeLimit) {
					check = true;
				}
				if (type == 2 && flex[index].startTimeRange != null && Math.round((flex[index].startTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) < timeRangeLimit) {
					check = true;
				}
				if (type == 3 && flex[index].endTimeRange != null && Math.round((flex[index].endTimeRange.startTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) <= timeRangeLimit) {
					check = true;
				}
				if (type == 4 && flex[index].endTimeRange != null && Math.round((flex[index].endTimeRange.endTime.time - self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5) < timeRangeLimit) {
					check = true;
				}
			}
			return check;
		}
		/** COLOR-ZONE */
		/*A4_color*/
		setColorEmployee(needSchedule: number, cheering: number) {
			if (needSchedule == 0) {
				return '#ddddd2';
			}

			switch (cheering) {
				case SupportAtr.ALL_DAY_SUPPORTER:
					return '#c3d69b';

				case SupportAtr.ALL_DAY_RESPONDENT:
					return '#fedfe6';

				case SupportAtr.TIMEZONE_SUPPORTER:
					return '#ebf1de';

				case SupportAtr.TIMEZONE_RESPONDENT:
					return '#ffccff';
			}
		}
		checkColorA6(workInfo: any) {
			let workTypeColor = "";
			if (workInfo === EditStateSetting.HAND_CORRECTION_MYSELF) {
				workTypeColor = "94b7fe"
			} else if (workInfo === EditStateSetting.HAND_CORRECTION_OTHER) {
				workTypeColor = "cee6ff"
			} else if (workInfo === EditStateSetting.REFLECT_APPLICATION) {
				workTypeColor = "bfea60"
			}
			return workTypeColor;
		}
		/** A6_color① - chua xong o phan login va xin bang don */
		setColorWorkingInfo(empId: string, isConfirmed: number, workScheduleDto: any) {
			let self = this, workTypeColor = "", workTimeColor = "", startTime1Color = "", endTime1Color = "",
				startTime2Color = "", endTime2Color = "", breakTimeColor = "";

			if (isConfirmed == 0) {
				self.workingInfoColor('#ddddd2');
			} else {
				self.workingInfoColor('#eccefb');
			}
			// work type
			if (workScheduleDto.workTypeStatus != null) {
				workTypeColor = self.checkColorA6(workScheduleDto.workTypeStatus);
			}
			// work time
			if (workScheduleDto.workTimeStatus != null) {
				workTimeColor = self.checkColorA6(workScheduleDto.workTimeStatus);
			}
			// start time 1
			if (workScheduleDto.startTime1Status != null) {
				startTime1Color = self.checkColorA6(workScheduleDto.startTime1Status);
			}
			// end time 1
			if (workScheduleDto.endTime1Status != null) {
				endTime1Color = self.checkColorA6(workScheduleDto.endTime1Status);
			}
			// start time 2
			if (workScheduleDto.startTime2Status != null) {
				startTime2Color = self.checkColorA6(workScheduleDto.startTime2Status);
			}
			// end time 2
			if (workScheduleDto.endTime2Status != null) {
				endTime2Color = self.checkColorA6(workScheduleDto.endTime2Status);
			}
			// break time
			if (workScheduleDto.breakTimeStatus != null) {
				breakTimeColor = self.checkColorA6(workScheduleDto.endTime2Status);
			}

			self.dataColorA6.push({
				empId: empId,
				workTypeColor: workTypeColor,
				workTimeColor: workTimeColor,
				startTime1Color: startTime1Color,
				endTime1Color: endTime1Color,
				startTime2Color: startTime2Color,
				endTime2Color: endTime2Color,
				breakTimeColor: breakTimeColor

			});
		}

		// A7_color①
		a7SetColor(empId: string, isConfirmed: number, workScheduleDto: any) {
			let self = this, breakTimeColor = "";

			if (isConfirmed == 0) {
				self.totalTimeColor('#ddddd2');
			} else {
				self.totalTimeColor('#eccefb');
			}

			// break time
			if (workScheduleDto.breakTimeStatus != null) {
				if (workScheduleDto.breakTimeStatus === EditStateSetting.HAND_CORRECTION_MYSELF) {
					breakTimeColor = "94b7fe"
				} else if (workScheduleDto.breakTimeStatus === EditStateSetting.HAND_CORRECTION_OTHER) {
					breakTimeColor = "cee6ff"
				}
			}
			return breakTimeColor;
		}

		setPositionButonToRightToLeft() {
			let self = this, marginleftOfbtnToRight: number = 0;
			self.indexBtnToLeft(0);
			$(".toLeft").css("display", "none");
			marginleftOfbtnToRight = $("#extable-ksu003").width() - 32;
			$(".toRight").css('margin-left', marginleftOfbtnToRight + 'px');
		}

		setPositionButonDownAndHeightGrid() {
			let self = this;

			$("#extable-ksu003").exTable("setHeight", 10 * 30 + 18);
			$(".toDown").css({ "margin-top": 10 * 30 + 8 + 'px' });
			/*$("#master-wrapper").css({ 'overflow-y': 'auto' });
			$("#master-wrapper").css({ 'overflow-x': 'hidden' });*/
			//}
			$("#extable-ksu003").exTable("scrollBack", 0, { h: Math.round(self.dataInitStartKsu003Dto().byDateDto.initDispStart * 12) });
		}

		setWidth(): any {
			$(".ex-header-detail").width(window.innerWidth - 572);
			$(".ex-body-detail").width(window.innerWidth - 554);
			$("#extable-ksu003").width(window.innerWidth - 554);
		}

		toLeft() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (self.showA9) {
					$("#extable-ksu003").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "210px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 100 + 'px');
				}
				if (window.innerHeight < 700) {
					$(".ex-header-detail").css({ "width": 1009 + 'px' });
					$(".ex-body-detail").css({ "width": 1026 + 'px' });
				}
			} else {
				if (self.showA9) {
					$("#extable-ksu003").exTable("showMiddle");
				}
				$(".ex-header-middle").css("width", 560 + 'px' + '!important')
				$(".toLeft").css("margin-left", 577 + 'px');
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 562 + 'px');
				}
				if (window.innerHeight < 700) {
					$(".ex-header-detail").css({ "width": 615 + 'px' });
					$(".ex-body-detail").css({ "width": 632 + 'px' });
				}
				/*$(".ex-body-detail").css({ 'overflow-x': 'scroll hidden' + '!important' });
				$(".ex-body-detail").css({ 'overflow': 'none' + '!important' });
				$(".ex-body-detail").css({ 'overflow-y': 'auto' + '!important' });*/
			}

			self.indexBtnToLeft(self.indexBtnToLeft() + 1);
			self.localStore().showHide = self.indexBtnToLeft();
			storage.setItemAsJson(self.KEY, self.localStore());
			$("#extable-ksu003").exTable("scrollBack", 0, { h: Math.round(self.dataInitStartKsu003Dto().byDateDto.initDispStart * 12) });
		}

		toDown() {
			let self = this;
			let exTableHeight = window.innerHeight - 92 - 31 - 50 - 50 - 35 - 27 - 27 - 20;
			exTableHeight = 17 * 30 + 18;
			$("#master-wrapper").css({ 'overflow-x': 'hidden' });
			if (self.indexBtnToDown() % 2 == 0) {
				//$(".toDown").css("background", "url() no-repeat center");
				$("#extable-ksu003").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');
				$("#master-wrapper").css({ 'overflow-y': 'auto' });

			} else {
				exTableHeight = 10 * 30 + 18;
				//$(".toDown").css("background", "url() no-repeat center");
				$("#extable-ksu003").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');

			}
			self.indexBtnToDown(self.indexBtnToDown() + 1);
		}

		showHide() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (!self.showA9) {
					$("#extable-ksu003").exTable("showMiddle");
				}
				$(".toLeft").css("margin-left", 577 + 'px');
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 562 + 'px');
				}
			} else {
				if (self.showA9) {
					$("#extable-ksu003").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "210px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 200 + 'px');
				}
			}

			if (window.innerHeight > 700) {
				$("#hr-row2").css({ "width": 1278 + 'px' });
			} else {
				if (self.indexBtnToLeft() % 2 == 0) {
					$(".ex-header-detail").css({ "width": 615 + 'px' });
					$(".ex-body-detail").css({ "width": 632 + 'px' });
				} else {
					$(".ex-header-detail").css({ "width": 1009 + 'px' });
					$(".ex-body-detail").css({ "width": 1026 + 'px' });
				}

			}
		}

		// Tính tổng BREAK-TIME khi kéo gant chart
		calcBreakTime(brkTChart: any, timeChart: any) {
			let totalBreak = 0;

			if (brkTChart.startTime <= timeChart.startTime && brkTChart.endTime <= timeChart.endTime)
				totalBreak = brkTChart.endTime - timeChart.startTime;

			if (brkTChart.startTime <= timeChart.startTime && brkTChart.endTime >= timeChart.endTime)
				totalBreak = timeChart.endTime - timeChart.startTime;

			if (brkTChart.startTime >= timeChart.startTime && brkTChart.endTime >= timeChart.endTime)
				totalBreak = timeChart.endTime - brkTChart.startTime;

			if (brkTChart.startTime >= timeChart.startTime && brkTChart.endTime <= timeChart.endTime)
				totalBreak = brkTChart.endTime - brkTChart.startTime;

			return totalBreak;
		}

		convertTime(timeStart: any, timeEnd: any, timeStart2: any, timeEnd2: any) {
			if (timeStart != "") timeStart = Math.round(duration.parseString(timeStart).toValue());
			if (timeEnd != "") timeEnd = Math.round(duration.parseString(timeEnd).toValue());
			if (timeStart2 != "") timeStart2 = Math.round(duration.parseString(timeStart2).toValue());
			if (timeEnd2 != "") timeEnd2 = Math.round(duration.parseString(timeEnd2).toValue());

			let timeConvert = {
				start: timeStart != "" ? timeStart : "",
				end: timeEnd != "" ? timeEnd : "",
				start2: timeStart2 != "" ? timeStart2 : "",
				end2: timeEnd2 != "" ? timeEnd2 : ""
			}
			return timeConvert;
		}

		convertTimeToChart(startTime: any, endTime: any) {
			let self = this, convertTime = null;

			startTime = startTime != null ? formatById("Clock_Short_HM", startTime) : "0:00";
			endTime = endTime != null ? formatById("Clock_Short_HM", endTime) : "0:00";
			startTime = self.convertTimePixel(startTime);
			endTime = self.convertTimePixel(endTime);
			convertTime = {
				startTime: startTime,
				endTime: endTime
			}
			return convertTime;
		}

		public convertTimePixel(timeStart: any) {
			let time = 0, self = this;
			if (timeStart != null) {
				// convert string to pixel
				time = Math.round((duration.parseString(timeStart).toValue() - (self.dataInitStartKsu003Dto().byDateDto.dispStart * 60)) / 5);
			}
			return time;
		}

		/** 行事(A2_1_6)をクリックする  (Click "event"(A2_1_6) */
		hoverEvent(targetDate: any) {
			let self = this, tooltip = _.filter(self.tooltip, (x: any) => { return x.ymd === targetDate });
			if (tooltip.length > 0) {
				let htmlToolTip = tooltip[0].htmlTooltip;
				$('#event').tooltip({
					content: htmlToolTip != null ? htmlToolTip : "",
					tooltipClass: "tooltip-styling"
				});
			}
		}

	}

	/**
	* 応援区分
	*/
	export enum SupportAtr {
		/**
		 * 応援ではない
		 */
		NOT_CHEERING = 1,
		/**
		 * 時間帯応援元
		 */
		TIMEZONE_SUPPORTER = 2,
		/**
		 * 時間帯応援先
		 */
		TIMEZONE_RESPONDENT = 3,
		/**
		 * 終日応援元
		 */
		ALL_DAY_SUPPORTER = 4,
		/**
		 * 終日応援先
		 */
		ALL_DAY_RESPONDENT = 5
	}

	export enum EditStateSetting {
		/**
		* 手修正（本人）
		*/
		HAND_CORRECTION_MYSELF = 0,
		/**
		 * 手修正（他人） 
		 */
		HAND_CORRECTION_OTHER = 1,
		/**
		 * 申請反映
		 */
		REFLECT_APPLICATION = 2,
		/**
		 * 打刻反映
		 */
		IMPRINT = 3
	}

	export enum WorkTimeForm {
		/**
		* 固定勤務 
		*/
		FIXED = 0,
		/**
		 * フレックス勤務
		 */
		FLEX = 1,
		/**
		 * 流動勤務
		 */
		FLOW = 2,
		/**
		 * 時差勤務
		 */
		TIMEDIFFERENCE = 3
	}

	class ItemModel {
		code: string;
		name: string;

		constructor(code: string, name: string) {
			this.code = code;
			this.name = name;
		}
	}

	interface ILocalStore {
		startTimeSort: string;
		showWplName: boolean;
		operationUnit: string;
		displayFormat: number;
		showHide: number;
		lstEmpIdSort: Array<any>;
	}

	interface ITimeGantChart {
		empId: string,
		typeOfTime: string,
		gantCharts: number,
		gcFixedWorkTime: Array<model.IFixedFlowFlexTime>,
		gcBreakTime: Array<model.IBreakTime>,
		gcOverTime: Array<model.IOverTime>,
		gcSupportTime: any,
		gcFlowTime: Array<model.IFixedFlowFlexTime>,
		gcFlexTime: Array<model.IFixedFlowFlexTime>,
		gcCoreTime: Array<model.ICoreTime>,
		gcHolidayTime: Array<model.IHolidayTime>,
		gcShortTime: Array<model.IShortTime>;
	};

	interface IEmpidName {
		empId: string,
		name: string,
		code: string
	}

	//時間帯(実装コードなし/使用不可)
	export class TimeZoneDto {
		startTime: TimeOfDayDto;//開始時刻 
		endTime: TimeOfDayDto;//終了時刻
		constructor(startTime: TimeOfDayDto,
			endTime: TimeOfDayDto) {
			this.startTime = startTime;
			this.endTime = endTime;
		}
	}

	//時刻(日区分付き)
	export class TimeOfDayDto {
		dayDivision: number;//日区分   : DayDivision
		time: number;//時刻
		constructor(dayDivision: number,
			time: number) {
			this.dayDivision = dayDivision;
			this.time = time;
		}
	}

	class CellColor {
		columnKey: any;
		rowId: any;
		innerIdx: any;
		clazz: any;
		constructor(columnKey: any, rowId: any, clazz: any, innerIdx?: any) {
			this.columnKey = columnKey;
			this.rowId = rowId;
			this.innerIdx = innerIdx;
			this.clazz = clazz;
		}
	}
}
