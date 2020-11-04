module nts.uk.at.view.ksu003.a.viewmodel {
	import getText = nts.uk.resource.getText;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	import errorDialog = nts.uk.ui.dialog.alertError;
	import block = nts.uk.ui.block;
	import kdl045 = nts.uk.at.view.kdl045.share.model;
	import exTable = nts.uk.ui.exTable;
	import chart = nts.uk.ui.chart;
	import storage = uk.localStorage;
	import formatById = nts.uk.time.format.byId;
	import parseTime = nts.uk.time.parseTime;
	/**
	 * ScreenModel
	 */
	export class ScreenModel {

		KEY: string = 'USER_KSU003_INFOR';
		employeeIdLogin: string = __viewContext.user.employeeId;

		/*A2_1_3*/
		targetDate: KnockoutObservable<string> = ko.observable('2020/05/02');
		targetDateDay: KnockoutObservable<string> = ko.observable('');
		/*A2_2*/
		organizationName: KnockoutObservable<string> = ko.observable('');
		/*A2_5*/
		selectedDisplayPeriod: KnockoutObservable<number> = ko.observable(1);
		/*A3_2*/
		itemList: KnockoutObservableArray<ItemModel>;
		selectOperationUnit: KnockoutObservable<string> = ko.observable('0');
		/*A3_3*/
		checked: KnockoutObservable<boolean> = ko.observable(true);

		checkedName: KnockoutObservable<boolean> = ko.observable(true);

		showA9: boolean;

		// dùng cho xử lý của botton toLeft, toRight, toDown
		indexBtnToLeft: KnockoutObservable<number> = ko.observable(0);
		indexBtnToDown: KnockoutObservable<number> = ko.observable(0);

		/*Data get from Screen A*/
		dataFromA: KnockoutObservable<model.InforScreenADto> = ko.observable();
		dataScreen003A: KnockoutObservable<model.DataScreenA> = ko.observable();
		dataScreen045A: KnockoutObservable<model.DataFrom045> = ko.observable();
		dataA6: KnockoutObservable<model.A6Data> = ko.observable();
		dataInitStartKsu003Dto: KnockoutObservable<model.GetInfoInitStartKsu003Dto> = ko.observable();
		displayWorkInfoByDateDto: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		displayDataKsu003: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		getFixedWorkInformationDto: KnockoutObservable<Array<any>> = ko.observable();
		dataColorA6: Array<any> = [];

		ruler = new chart.Ruler($("#gc")[0]);

		/** 社員勤務情報　dto */
		employeeWorkInfo: KnockoutObservable<model.EmployeeWorkInfoDto> = ko.observable();

		employeeScheduleInfo: Array<any> = [];

		/** Color */
		employeeColor: KnockoutObservable<string> = ko.observable('');
		// suport color
		suportColor: KnockoutObservable<string> = ko.observable('');

		/** A6_color① */
		workingInfoColor: KnockoutObservable<string> = ko.observable('');
		/** A7 color */
		totalTimeColor: KnockoutObservable<string> = ko.observable('');
		/** A3_2 pixel */
		operationUnit: KnockoutObservable<number> = ko.observable(3.5);
		operationOneMinus: KnockoutObservable<number> = ko.observable(12);

		timeToPixelStart: KnockoutObservable<number> = ko.observable();
		timeToPixelEnd: KnockoutObservable<number> = ko.observable();

		localStore: KnockoutObservable<ILocalStore> = ko.observable();
		startScrollGc: KnockoutObservable<number> = ko.observable();

		/** tất cả các loại time của gantt chart */
		typeTimeGc: KnockoutObservable<any> = ko.observable();

		lstEmpId: Array<any> = [];
		startScrollY: number = 8;

		/** Các loại thời gian */
		constructor() {
			let self = this;
			// get data from sc A
			self.dataFromA = ko.observable(getShared('dataFromA'));
			self.lstEmpId = _.flatMap(self.dataFromA().listEmp, c => [c.id]);
			self.targetDate(self.dataFromA().daySelect);
			self.targetDateDay(self.targetDate() + moment(self.targetDate()).format('(ddd)'));

			self.itemList = ko.observableArray([
				new ItemModel('0', getText('KSU003_13')),
				new ItemModel('1', getText('KSU003_14')),
				new ItemModel('2', getText('KSU003_15')),
				new ItemModel('3', getText('KSU003_16')),
				new ItemModel('4', getText('KSU003_17'))
			]);
			self.showA9 = true;

			/* 開始時刻順に並び替える(A3_3)はチェックされている */
			self.checked.subscribe((value) => {
				self.localStore().startTimeSort = value;
				storage.setItemAsJson(self.KEY, self.localStore());
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
				if (value == '1') {
					self.operationUnit(7);
					self.operationOneMinus(6);
				}

				if (value == '2') {
					self.operationUnit(10.5);
					self.operationOneMinus(4);
				}

				if (value == '3') {
					self.operationUnit(21);
					self.operationOneMinus(2);
				}

				if (value == '4') {
					self.operationUnit(42);
					self.operationOneMinus(1);
				}
				self.localStore().operationUnit = value;
				storage.setItemAsJson(self.KEY, self.localStore());
			});

			$("#extable").on("extablecellupdated", (dataCell: any) => {
				let index = dataCell.originalEvent.detail.rowIndex;
				let dataMid = $("#extable").exTable('dataSource', 'middle').body[index];
				if (dataCell.originalEvent.detail.columnKey === "worktypeCode" || dataCell.originalEvent.detail.columnKey === "worktimeCode") {
					self.getEmpWorkFixedWorkInfo(dataMid.workTypeCode, dataMid.worktimeCode);
				}
				if (dataCell.originalEvent.detail.columnKey === "startTime1" || dataCell.originalEvent.detail.columnKey === "startTime2" ||
					dataCell.originalEvent.detail.columnKey === "endTime1" || dataCell.originalEvent.detail.columnKey === "endTime2") {
					let timeConvert = self.convertTime(dataMid.startTime1, dataMid.endTime1, dataMid.startTime2, dataMid.endTime2);
					self.employeeScheduleInfo.forEach((x, i) => {
						if (i === dataCell.originalEvent.detail.rowIndex) {
							if (dataCell.originalEvent.detail.columnKey === "startTime1") {
								x.startTime1 = timeConvert.start;
							}
							if (dataCell.originalEvent.detail.columnKey === "startTime2") {
								x.startTime2 = timeConvert.start2;
							}
							if (dataCell.originalEvent.detail.columnKey === "endTime1") {
								x.endTime1 = timeConvert.end;
							}
							if (dataCell.originalEvent.detail.columnKey === "endTime2") {
								x.endTime2 = timeConvert.end2;
							}
						}
					})


					let totalTime = null;
					if (timeConvert.end != "" || (timeConvert.end2 != "" && timeConvert.start2 != "")) {
						totalTime = (timeConvert.end - timeConvert.start) + (timeConvert.end2 - timeConvert.start2);
						totalTime = formatById("Clock_Short_HM", totalTime);
					}

					$("#extable").exTable("cellValue", "LEFT_TBL", dataCell.originalEvent.detail.rowIndex, "totalTime", totalTime != null ? totalTime : "");
				}

				// check thời gian Tú chưa làm - TQP 時刻が不正かチェックする
			});

			$("#extable").on("extablerowupdated", (dataCell) => {
				console.log("aaa");
			});

		}


		/**
		 * startPage
		 */
		public startPage(): JQueryPromise<any> {
			block.grayout();
			let self = this;
			let dfd = $.Deferred<any>();
			let data003A: model.DataScreenA = {
				startDate: self.dataFromA().startDate, // 開始日		
				endDate: self.dataFromA().endDate, // 終了日
				/** 基準の組織 */
				unit: self.dataFromA().unit,
				id: self.dataFromA().unit == 0 ? self.dataFromA().workplaceId : self.dataFromA().workplaceGroupId,
				name: self.dataFromA().workplaceName,
				timeCanEdit: self.dataFromA().timeCanEdit, //いつから編集可能か
				/** 複数回勤務管理 */
				targetInfor: 0,//対象情報 : 複数回勤務 (1 :true,0:false)
				canModified: 0,//修正可能 CanModified
				scheCorrection: [],//スケジュール修正の機能制御  WorkTimeForm
				employeeInfo: [],
			}
			self.dataScreen003A(data003A);
			self.getInforFirt();
			self.getWorkingByDate();
			// A2_2 TQP 
			// self.organizationName(self.dataInitStartKsu003Dto().displayInforOrganization().displayName());
			dfd.resolve();
			block.clear();
			return dfd.promise();
		}

		public getDataExtable() {
			let self = this;
			let dataSource = $("#extable").exTable('dataSource', 'detail').body;
			console.log("aaa");
		}

		// Tạo data để truyền vào ExTable
		public convertDataIntoExtable() {

			let self = this, disableDs: any = [], leftDs: any = [], middleDs: any = [], timeGantChart: Array<ITimeGantChart> = [], typeOfTime: string, startTimeArr: any = [];
			let gcFixedWorkTime: Array<model.IFixedFlowFlexTime> = [],
				gcBreakTime: Array<model.IBreakTime> = [],
				gcOverTime: Array<model.IOverTime> = [],
				gcSupportTime: any = null,
				gcFlowTime: Array<model.IFixedFlowFlexTime> = [],
				gcFlexTime: Array<model.IFixedFlowFlexTime> = [],
				gcCoreTime: Array<model.ICoreTime> = [],
				gcHolidayTime: Array<model.IHolidayTime> = [],
				gcShortTime: Array<model.IShortTime> = [];

			_.forEach(self.dataScreen003A().employeeInfo, (schedule: model.DisplayWorkInfoByDateDto) => {
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


				let totalTime: any = "";

				if (schedule.workScheduleDto != null) {
					if (schedule.workScheduleDto.endTime2 != null && schedule.workScheduleDto.startTime2 != null)
						totalTime = (schedule.workScheduleDto.endTime1 - schedule.workScheduleDto.startTime1) + (schedule.workScheduleDto.endTime2 - schedule.workScheduleDto.startTime2);
					else if (schedule.workScheduleDto.endTime1 != null && schedule.workScheduleDto.startTime1 != null)
						totalTime = (schedule.workScheduleDto.endTime1 - schedule.workScheduleDto.startTime1);

					totalTime = totalTime != "" ? formatById("Clock_Short_HM", totalTime) : "";
				}

				if (schedule.workScheduleDto != null) {
					self.setColorWorkingInfo(schedule.empId, schedule.workInfoDto.isConfirmed, schedule.workScheduleDto);
				}

				let workTypeName = "", workTimeName = "";
				if (schedule.workScheduleDto != null) {
					if (schedule.workScheduleDto.workTypeCode != null && schedule.fixedWorkInforDto != null && schedule.fixedWorkInforDto.workTypeName != null) {
						workTypeName = schedule.fixedWorkInforDto.workTypeName;
					} else {
						workTypeName = schedule.workScheduleDto.workTypeCode + getText('KSU003_54')
					}
					// set work time name
					if (schedule.workScheduleDto.workTimeCode == null) {
						workTimeName = getText('KSU003_55');
					} else {
						if (schedule.fixedWorkInforDto != null && schedule.fixedWorkInforDto.workTimeName != null) {
							workTimeName = schedule.fixedWorkInforDto.workTimeName;
						} else {
							workTimeName = schedule.workScheduleDto.workTimeCode + getText('KSU003_54');
						}
					}
				}


				if (schedule.workScheduleDto != null) {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'), worktypeCode: schedule.workScheduleDto.workTypeCode,
						worktype: workTypeName, worktimeCode: schedule.workScheduleDto.workTimeCode, worktime: workTimeName,
						startTime1: startTime1, endTime1: endTime1,
						startTime2: startTime2, endTime2: endTime2,
						totalTime: totalTime, breaktime: schedule.workScheduleDto.breakTimeStatus, color: self.dataColorA6
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
				// Push data of gant chart
				// Thời gian cố định
				// 勤務固定情報　dto．Optional<勤務タイプ>==固定勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない

				if (//schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FIXED &&  => comment tạm khi chưa có dữ liệu - TQP
					schedule.workScheduleDto != null && schedule.workScheduleDto != null && // thêm tạm để chạy được
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
						etartTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.etartTimeRange1 : null

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
							etartTimeRange: schedule.fixedWorkInforDto != null ? schedule.fixedWorkInforDto.etartTimeRange2 : null
						});
						startTimeArr.add(schedule.workScheduleDto.startTime2);
					}
					typeOfTime = "Fixed"
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
					if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLOW
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
							etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange1

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
								etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange2
							});
							startTimeArr.add(schedule.workScheduleDto.startTime2);
						}
						typeOfTime = "Changeable"
					}

					// Thời gian Flex time
					// 勤務固定情報　dto．Optional<勤務タイプ>==フレックス勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
					if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLEX
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
							etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange1
						});
						startTimeArr.add(schedule.workScheduleDto.startTime1);
						typeOfTime = "Flex"
					}

					// Thời gian core time
					// 勤務固定情報　dto．Optional<勤務タイプ>== フレックス勤務 && 勤務固定情報　dto．Optional<コア開始時刻>とOptional<コア終了時刻>が存在する
					if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLEX
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
					if (schedule.workInfoDto.shortTime.length > 0) {
						gcShortTime.push({
							// 社員勤務情報　dto > 社員ID, Optional<Map<時間休暇種類, 時間休暇>>
							empId: schedule.workInfoDto.employeeId,
							color: "#6fa527",
							listShortTime: schedule.workInfoDto.shortTime
						})
					}
				}

				timeGantChart.push({
					empId: schedule.empId,
					typeOfTime: typeOfTime,
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
			startTimeArr = _.sortBy(startTimeArr, [function(o: any) { return o; }]);
			self.startScrollY = startTimeArr[0];
			self.initExtableData(timeGantChart, leftDs, middleDs, disableDs);
		}

		public nextDay() {
			let self = this;
			self.changeTargetDate(1, 1);
		}

		public nextAllDay() {
			let self = this;
			self.changeTargetDate(1, 7);
		}

		public prevDay() {
			let self = this;
			self.changeTargetDate(0, 1);
		}

		public prevAllDay(index: any) {
			let self = this;
			self.changeTargetDate(0, 7);

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
		/** Tính tổng thời gian làm việc */
		public calcTotalTime() {

		}

		public getHoverListEvent() {

		}

		public getInforFirt(): JQueryPromise<any> {
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
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});
			return dfd.promise();
		}

		// ②<<ScreenQuery>> 日付別勤務情報で表示する
		public getWorkingByDate(): JQueryPromise<any> {
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
				date: self.targetDate()
			};
			service.displayDataKsu003(param)
				.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
					self.dataScreen003A().employeeInfo = data;

					self.employeeScheduleInfo = _.map(self.dataScreen003A().employeeInfo, (x) => ({
						empId: x.empId,
						startTime1: x.workScheduleDto != null && x.workScheduleDto.startTime1 != null ? x.workScheduleDto.startTime1 : "",
						endTime1: x.workScheduleDto != null && x.workScheduleDto.endTime1 != null ? x.workScheduleDto.endTime1 : "",
						startTime2: x.workScheduleDto != null && x.workScheduleDto.startTime2 != null ? x.workScheduleDto.startTime2 : "",
						endTime2: x.workScheduleDto != null && x.workScheduleDto.endTime2 != null ? x.workScheduleDto.endTime2 : "",
						listBreakTimeZoneDto: x.workScheduleDto != null && x.workScheduleDto.listBreakTimeZoneDto != null ? x.workScheduleDto.listBreakTimeZoneDto : ""
					}))

					self.employeeScheduleInfo = self.employeeScheduleInfo.sort(function(a, b) {
						return _.findIndex(self.dataFromA().listEmp, x => { return x.id == a.empId }) - _.findIndex(self.dataFromA().listEmp, x => { return x.id == b.empId });
					});

				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
					self.convertDataIntoExtable();
					block.clear();
				});

			return dfd.promise();
		}

		public initExtableData(dataBindChart: Array<ITimeGantChart>, leftDs: any, middleDs: any, disableDs: any) {
			let self = this;
			let local: ILocalStore = {
				startTimeSort: self.checked() == true,
				showWplName: self.checkedName() == true,
				operationUnit: self.selectOperationUnit(),
				displayFormat: self.selectedDisplayPeriod(),
				showHide: self.indexBtnToLeft()
			}
			block.grayout()
			setTimeout(() => {
				self.localStore(local);
				storage.getItem(self.KEY).ifPresent((data: any) => {
					let userInfor: ILocalStore = JSON.parse(data);
					self.indexBtnToLeft(userInfor.showHide);
					self.selectOperationUnit(userInfor.operationUnit);
					self.selectedDisplayPeriod(userInfor.displayFormat);
					self.checked(userInfor.startTimeSort);
					self.checkedName(userInfor.showWplName);
				});
				self.initExTable(dataBindChart, leftDs, middleDs, disableDs);
				self.showHide();
				if (!_.isNil(self.startScrollY)){
				self.startScrollY = formatById("Clock_Short_HM", self.startScrollY);
				self.convertTimePixel(self.startScrollY, "0:00");
				self.startScrollGc(self.timeToPixelStart() * self.operationUnit() - 42);
				}
				$("#extable").exTable("scrollBack", 0, { h: self.startScrollGc() });
				self.dataA6 = null;
			}, 200)
			block.clear()
		}

		// 社員勤務予定と勤務固定情報を取得する
		public getEmpWorkFixedWorkInfo(workTypeCode: string, workTimeCode: string): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();
			let targetOrgDto = {
				workType: workTypeCode,
				workTime: workTimeCode
			}
			service.getEmpWorkFixedWorkInfo(targetOrgDto)
				.done((data: any) => {
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});
			return dfd.promise();
		}

		// 勤務種類を変更する
		public changeWorkType(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();
			let targetOrgDto = {
				workType: self.dataA6().workTypeCode,
				workTime: self.dataA6().workTimeCode
			}
			service.changeWorkType(targetOrgDto)
				.done((data: any) => {
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});
			return dfd.promise();
		}

		// イベント情報取得
		public hoverEvent(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();

			return dfd.promise();
		}

		// 社員を並び替える
		public sortEmployee(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>();
			let lstEmpId = _.map(self.dataFromA().listEmp, c => { return { id: c.id }; })
			let param = {
				ymd: self.targetDate(),
				lstEmpid: lstEmpId
			}
			service.displayDataKsu003(param)
				.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
					self.displayWorkInfoByDateDto(data);
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});
			return dfd.promise();
		}

		public execute() {
			let self = this;
			let dfd = $.Deferred<any>();
		}

		// open dialog kdl045
		// cần làm lại sau
		public openKdl045Dialog() {
			let self = this;
			block.grayout();

			let data: any = _.filter(self.dataScreen003A().employeeInfo, (x) => { return x.empId === "1"; })

			let dataShare: model.ParamKsu003 = {
				employeeInfo: data,
				targetInfor: self.dataScreen003A().targetInfor,
				canModified: self.dataScreen003A().canModified,
				scheCorrection: self.dataScreen003A().scheCorrection,
				unit: self.dataFromA().unit,
				targetId: self.dataFromA().unit === 0 ? self.dataFromA().workplaceId :  self.dataFromA().workplaceGroupId,
				workplaceName: self.dataFromA().workplaceName
			};
			setShared('dataShareTo045', dataShare);
			nts.uk.ui.windows.sub.modal('/view/kdl/045/a/index.xhtml').onClosed(() => {
				self.dataScreen045A = getShared('dataFromKdl045');
				block.clear();
			});
		}

		/** A1_3 - Open dialog G */
		public openGDialog() {
			let self = this;
			block.grayout();
			let dataShare = {
				employeeIDs: self.lstEmpId,
				startDate: self.targetDate(),
				endDate: self.targetDate(),
				employeeInfo: self.dataFromA().listEmp
			};
			setShared('dataShareDialogG', dataShare);
			nts.uk.ui.windows.sub.modal('/view/ksu/001/g/index.xhtml').onClosed(() => {
				block.clear();
			});
		}

		public openKdl003Dialog() {
			let self = this;
			block.grayout();
			let dataShare = {
				workTypeCode: "009",
				workTimeCode: "012"
			};
			setShared('parentCodes', dataShare, true);
			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(() => {
				let dataShare = getShared('childData');
				// 勤務固定情報を取得する
				service.getFixedWorkInformation(dataShare).done((data: any) => {
					self.getFixedWorkInformationDto(data);
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});
				block.clear();
			});
		}

		/** A1_4 - Close modal */
		public closeDialog(): void {
			let self = this;
			nts.uk.ui.windows.close();
		}

		/*A4_color*/
		public setColorEmployee(needSchedule: number, cheering: number) {
			let self = this;
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

		/** A6_color① - chua xong o phan login va xin bang don */
		public setColorWorkingInfo(empId: string, isConfirmed: number, workScheduleDto: any) {
			let self = this, workTypeColor = "", workTimeColor = "", startTime1Color = "", endTime1Color = "",
				startTime2Color = "", endTime2Color = "", breakTimeColor = "";


			if (isConfirmed == 0) {
				self.workingInfoColor('#ddddd2');
			} else {
				self.workingInfoColor('#eccefb');
			}
			// work type
			if (workScheduleDto.workTypeStatus != null) {
				if (workScheduleDto.workTypeStatus === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					workTypeColor = "94b7fe"
				} else if (workScheduleDto.workTypeStatus === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					workTypeColor = "cee6ff"
				} else if (workScheduleDto.workTypeStatus === model.EditStateSetting.REFLECT_APPLICATION) {
					workTypeColor = "bfea60"
				}
			}

			// work time
			if (workScheduleDto.workTimeStatus != null) {
				if (workScheduleDto.workTimeStatus === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					workTimeColor = "94b7fe"
				} else if (workScheduleDto.workTimeStatus === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					workTimeColor = "cee6ff"
				} else if (workScheduleDto.workTimeStatus === model.EditStateSetting.REFLECT_APPLICATION) {
					workTimeColor = "bfea60"
				}
			}

			// start time 1
			if (workScheduleDto.startTime1Status != null) {
				if (workScheduleDto.startTime1Status === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					startTime1Color = "94b7fe"
				} else if (workScheduleDto.startTime1Status === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					startTime1Color = "cee6ff"
				} else if (workScheduleDto.startTime1Status === model.EditStateSetting.REFLECT_APPLICATION) {
					startTime1Color = "bfea60"
				}
			}

			// end time 1
			if (workScheduleDto.endTime1Status != null) {
				if (workScheduleDto.endTime1Status === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					endTime1Color = "94b7fe"
				} else if (workScheduleDto.endTime1Status === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					endTime1Color = "cee6ff"
				} else if (workScheduleDto.endTime1Status === model.EditStateSetting.REFLECT_APPLICATION) {
					endTime1Color = "bfea60"
				}
			}

			// start time 2
			if (workScheduleDto.startTime2Status != null) {
				if (workScheduleDto.startTime2Status === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					startTime2Color = "94b7fe"
				} else if (workScheduleDto.startTime2Status === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					startTime2Color = "cee6ff"
				} else if (workScheduleDto.startTime2Status === model.EditStateSetting.REFLECT_APPLICATION) {
					startTime2Color = "bfea60"
				}
			}

			// end time 2
			if (workScheduleDto.endTime2Status != null) {
				if (workScheduleDto.endTime2Status === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					endTime2Color = "94b7fe"
				} else if (workScheduleDto.endTime2Status === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					endTime2Color = "cee6ff"
				} else if (workScheduleDto.endTime2Status === model.EditStateSetting.REFLECT_APPLICATION) {
					endTime2Color = "bfea60"
				}
			}

			// break time
			if (workScheduleDto.breakTimeStatus != null) {
				if (workScheduleDto.breakTimeStatus === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					breakTimeColor = "94b7fe"
				} else if (workScheduleDto.breakTimeStatus === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					breakTimeColor = "cee6ff"
				}
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
		public a7SetColor(empId: string, isConfirmed: number, workScheduleDto: any) {
			let self = this, breakTimeColor = "";

			if (isConfirmed == 0) {
				self.totalTimeColor('#ddddd2');
			} else {
				self.totalTimeColor('#eccefb');
			}

			// break time
			if (workScheduleDto.breakTimeStatus != null) {
				if (workScheduleDto.breakTimeStatus === model.EditStateSetting.HAND_CORRECTION_MYSELF) {
					breakTimeColor = "94b7fe"
				} else if (workScheduleDto.breakTimeStatus === model.EditStateSetting.HAND_CORRECTION_OTHER) {
					breakTimeColor = "cee6ff"
				}
			}
			return breakTimeColor;
		}

		public convertTimePixel(timeStart: any, timeEnd: any) {
			let self = this,
				time_format_end =timeEnd.match(/([\d]*):([\d]+)/),
				time_format =timeStart.match(/([\d]*):([\d]+)/),
				time = 0, time_end = 0;
			if (time_format != null) {
				let hours = parseInt(time_format[1]),
					minutes = parseFloat(time_format[2] / 60);
				time = hours + minutes;
			}
			if (time_format_end != null) {
				let hours_end = parseInt(time_format_end[1]),
					minutes_end = parseFloat(time_format_end[2] / 60);
				time_end = hours_end + minutes_end
			}
			self.timeToPixelStart(time * self.operationOneMinus());
			self.timeToPixelEnd(time_end * self.operationOneMinus());

		}

		initExTable(timeGantChart: Array<ITimeGantChart>, leftDs: any, midData: any, disableDs: any): void {
			let ruler = new chart.Ruler($("#gc")[0]);
			let self = this;
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
			}, { headerText: getText('KSU003_21'), key: "cert", width: "40px" }];

			leftmostHeader = {
				columns: leftmostColumns,
				rowHeight: "33px",
				width: "200px"
			};

			let leftmostDs = [], middleDs = [];

			for (let i = 0; i < self.dataFromA().listEmp.length; i++) {

				let dataLeft = self.dataFromA().listEmp[i],
					datafilter = _.filter(midData, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id }),
					dataMid = datafilter[0],
					eName: any = dataLeft.code + " " + dataLeft.name;

				let leftDSFilter = _.filter(leftDs, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id });
				disableDSFilter = _.filter(disableDs, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id });

				leftmostDs.push({ empId: self.dataFromA().listEmp[i].id, empName: eName, cert: getText('KSU003_22') });

				leftContentDeco.push(new CellColor("empName", leftDSFilter[0].empId, leftDSFilter[0].color));
				leftContentDeco.push(new CellColor("cert", leftDSFilter[0].empId, leftDSFilter[0].color));
				if (disableDSFilter.length > 0) {
					middleContentDeco.push(new CellColor("worktypeCode", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktype", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktimeCode", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktime", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("startTime1", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("endTime1", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("startTime2", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("endTime2", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("totalTime", disableDSFilter[0].empId, disableDSFilter[0].color));
					middleContentDeco.push(new CellColor("breaktime", disableDSFilter[0].empId, disableDSFilter[0].color));
					for (let z = 0; z < 57; z++) {
						detailContentDeco.push(new CellColor(z.toString(), disableDSFilter[0].empId, disableDSFilter[0].color));
					}

				}

				middleDs.push({
					empId: dataMid.empId, worktypeCode: dataMid.worktypeCode,
					worktype: dataMid.worktype, worktimeCode: dataMid.worktimeCode, worktime: dataMid.worktime,
					startTime1: dataMid.startTime1, endTime1: dataMid.endTime1,
					startTime2: dataMid.startTime2, endTime2: dataMid.endTime2,
					totalTime: dataMid.totalTime, breaktime: dataMid.breaktime
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
				{ headerText: getText('KSU003_23'), key: "worktypeCode", width: "40px", handlerType: "input", dataType: "text" },
				{ headerText: getText('KSU003_25'), key: "worktype", width: "38px" },
				{ headerText: getText('KSU003_23'), key: "worktimeCode", width: "40px", handlerType: "input", dataType: "text" },
				{ headerText: getText('KSU003_25'), key: "worktime", width: "38px" },
				{ headerText: getText('KSU003_27'), key: "startTime1", width: "40px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_28'), key: "endTime1", width: "40px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_29'), key: "startTime2", width: "40px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_30'), key: "endTime2", width: "40px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_31'), key: "totalTime", width: "40px" },
				{ headerText: getText('KSU003_33'), key: "breaktime", width: "35px" }
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
			for (let y = 0; y < 57; y++) {
				if (y == 0) {
					detailColumns.push({ key: "empId", width: "0px", headerText: "ABC", visible: false });
				} else {
					detailColumns.push({ key: (y - 1).toString(), width: width });
				}
			}
			// Phần detail
			detailHeaderDs = [{ empId: "" }];
			let detailHeaders = {};
			for (let y = 0; y < 57; y++) {
				detailHeaderDs[0][y] = y.toString();
				detailHeaders[y] = "";
			}

			detailHeader = {
				columns: detailColumns,
				dataSource: detailHeaderDs,
				rowHeight: "33px",
				width: "700px"
			};

			let detailContentDs = [];
			for (let z = 0; z < self.dataFromA().listEmp.length; z++) {
				let datafilter = _.filter(timeGantChart, (x: any) => { return x.empId === self.dataFromA().listEmp[z].id });
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

			let extable = new exTable.ExTable($("#extable"), {
				headerHeight: "33px",
				bodyRowHeight: "30px",
				bodyHeight: "400px",
				horizontalSumHeaderHeight: "75px", horizontalSumBodyHeight: "140px",
				horizontalSumBodyRowHeight: "20px",
				areaResize: false,
				manipulatorId: self.employeeIdLogin,
				manipulatorKey: "empId",
				bodyHeightMode: "dynamic",
				showTooltipIfOverflow: true,
				windowXOccupation: 40,
				windowYOccupation: 200
			}).LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
				.MiddleHeader(middleHeader).MiddleContent(middleContent)
				.DetailHeader(detailHeader).DetailContent(detailContent);
			extable.create();

			this.ruler = extable.getChartRuler();
			ruler.setSnatchInterval(self.operationUnit());

			self.addTypeOfChart(this.ruler);

			// (5' ~ 3.5 pixel ~ 12 khoảng trong 60', 10' ~ 7 ~ 6, 15' ~ 10.5 ~ 4, 30' ~ 21 ~ 2, 60' ~ 42 ~ 1)	
			// unitToPx = khoảng pixel theo số phút 
			// start theo pixel = unitToPx * start * (khoảng-pixel/ phút)
			// end theo pixel = unitToPx * end * (khoảng-pixel/ phút)
			for (let i = 0; i < self.dataFromA().listEmp.length; i++) {
				let datafilter: Array<ITimeGantChart> = _.filter(timeGantChart, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id });
				let timeChart = null, gc = "", lgc = "";

				if (datafilter != null) {
					if (datafilter[0].typeOfTime != "") {
						// add chart for FIXED TIME - thời gian cố định
						if (datafilter[0].typeOfTime === "Fixed") {
							let fixed = datafilter[0].gcFixedWorkTime;
							timeChart = self.convertTimeToChart(fixed[0].startTime, fixed[0].endTime);

							gc = self.addChartWithTypes(this.ruler, "Fixed", `rgc${i}`, timeChart, i);

							if (fixed.length > 1 && fixed[1].startTime != null && fixed[1].endTime != null) {
								timeChart = self.convertTimeToChart(fixed[1].startTime, fixed[1].endTime);
								lgc = self.addChartWithTypes(this.ruler, "Fixed", `lgc${i}`, timeChart, i);
							}
						}

						// add chart for CHANGEABLE TIME - thời gian lưu động
						if (datafilter[0].typeOfTime === "Changeable") {
							let changeable = datafilter[0].gcFlowTime
							timeChart = self.convertTimeToChart(changeable[0].startTime, changeable[0].endTime);

							gc = self.addChartWithTypes(this.ruler, "Changeable", `rgc${i}`, timeChart, i);

							if (changeable.length > 1 && changeable[1].startTime != null && changeable[1].endTime != null) {
								timeChart = self.convertTimeToChart(changeable[1].startTime, changeable[1].endTime);
								lgc = self.addChartWithTypes(this.ruler, "Changeable", `lgc${i}`, timeChart, i);
							}
						}

						// add chart for FLEX TIME - thời gian flex
						if (datafilter[0].typeOfTime === "Flex") {
							let flex = datafilter[0].gcFlexTime
							timeChart = self.convertTimeToChart(flex[0].startTime, flex[0].endTime);

							gc = self.addChartWithTypes(this.ruler, "Flex", `rgc${i}`, timeChart, i);
						}
					};

					let coreTime = datafilter[0].gcCoreTime;
					if (coreTime.length > 0) {
						timeChart = self.convertTimeToChart(coreTime[0].coreStartTime, coreTime[0].coreStartTime);
						self.addChildChartWithTypes(this.ruler, "CoreTime", `rgc${i}_0`, timeChart, i, `rgc${i}`)
					}

					let suportTime = datafilter[0].gcSupportTime;
					if (suportTime != null) {
						timeChart = self.convertTimeToChart(suportTime[0].coreStartTime, suportTime[0].coreStartTime);
						self.addChildChartWithTypes(this.ruler, "CoreTime", `rgc${i}_1`, timeChart, i, `rgc${i}`)
					}

					let overTime = datafilter[0].gcOverTime;
					if (overTime.length > 0) {
						overTime[0].lstOverTime.forEach(x => {
							timeChart = self.convertTimeToChart(x.changeableStartTime, x.changeableEndTime);
							self.addChildChartWithTypes(this.ruler, "OT", `rgc${i}_2`, timeChart, i, `rgc${i}`)
						})
					}

					let breakTime = datafilter[0].gcBreakTime;
					if (breakTime.length > 0) {
						breakTime[0].lstBreakTime.forEach(x => {
							x.breakTimeSheets.forEach(y => {
								timeChart = self.convertTimeToChart(y.startTime, y.startTime);
								self.addChildChartWithTypes(this.ruler, "BreakTime", `rgc${i}_3`, timeChart, i, `rgc${i}`)
							})
						})
					}

					let shortTime = datafilter[0].gcShortTime;
					if (shortTime.length > 0) {
						shortTime[0].listShortTime.forEach(x => {
							timeChart = self.convertTimeToChart(x.startTime, x.endTime);
							self.addChildChartWithTypes(this.ruler, "HolidayTime", `rgc${i}_4`, timeChart, i, `rgc${i}`)
						})
					}

					let holidayTime = datafilter[0].gcHolidayTime;
					if (holidayTime.length > 0)  {
						holidayTime[0].listTimeVacationAndType.forEach(x => {
							timeChart = self.convertTimeToChart(x.timeVacation.timeZone.startTime, x.timeVacation.timeZone.endTime);
							self.addChildChartWithTypes(this.ruler, "CoreTime", `rgc${i}_5`, timeChart, i, `rgc${i}`);
						})
					}
				}
				$(gc).on("gcResize", (e: any) => {
					let param = e.detail;
				});
			}


			// set lock slide and resize chart
			//ruler.setLock([0, 1, 3], true);

			// set height grid theo localStorage đã lưu
			self.setPositionButonDownAndHeightGrid();
		}

		addChartWithTypes(ruler: any, type: any, id: any, timeChart: any, lineNo: any,
			limitStartMin?: any, limitStartMax?: any, limitEndMin?: any, limitEndMax?: any, title?: any) {
			return ruler.addChartWithType(type, {
				id: id,
				parent: parent,
				start: (timeChart.startTime),
				end: (timeChart.endTime),
				lineNo: lineNo,
				limitStartMin: limitStartMin,
				limitStartMax: limitStartMax,
				limitEndMin: limitEndMin,
				limitEndMax: limitEndMax,
				title: title
			});
		}

		addChildChartWithTypes(ruler: any, type: any, id: any, timeChart: any, lineNo: any, parent?: any,
			limitStartMin?: any, limitStartMax?: any, limitEndMin?: any, limitEndMax?: any, title?: any) {
			return ruler.addChartWithType(type, {
				id: id,
				parent: parent,
				start: (timeChart.startTime),
				end: (timeChart.endTime),
				lineNo: lineNo,
				limitStartMin: limitStartMin,
				limitStartMax: limitStartMax,
				limitEndMin: limitEndMin,
				limitEndMax: limitEndMax,
				title: title
			});
		}


		convertTimeToChart(startTime: any, endTime: any) {
			let self = this, convertTime = null;

			startTime = formatById("Clock_Short_HM", startTime);
			endTime = formatById("Clock_Short_HM", endTime);
			self.convertTimePixel(startTime, endTime);
			startTime = self.timeToPixelStart();
			endTime = self.timeToPixelEnd();

			return convertTime = {
				startTime: startTime,
				endTime: endTime
			}
		}

		addTypeOfChart(ruler: any) {
			let self = this;
			ruler.addType({
				name: "Fixed",
				color: "#ccccff",
				lineWidth: 30,
				canSlide: false,
				unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "Changeable",
				color: "#ffc000",
				lineWidth: 30,
				canSlide: true,
				unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "Flex",
				color: "#ccccff",
				lineWidth: 30,
				canSlide: true,
				unitToPx: self.operationUnit()
			});

			ruler.addType({
				name: "BreakTime",
				followParent: true,
				color: "#ff9999",
				lineWidth: 30,
				canSlide: true,
				unitToPx: self.operationUnit(),
				pin: true,
				rollup: true,
				roundEdge: true,
				fixed: "Both"
			});

			ruler.addType({
				name: "OT",
				followParent: true,
				color: "#ffff00",
				lineWidth: 30,
				canSlide: false,
				unitToPx: self.operationUnit(),
				pin: true,
				rollup: true,
				fixed: "Both"
			});

			ruler.addType({
				name: "HolidayTime",
				followParent: true,
				color: "#c4bd97",
				lineWidth: 30,
				canSlide: false,
				unitToPx: self.operationUnit(),
				pin: true,
				rollup: true,
				roundEdge: false,
				fixed: "Both"
			});

			ruler.addType({
				name: "ShortTime",
				followParent: true,
				color: "#6fa527",
				lineWidth: 30,
				canSlide: false,
				unitToPx: self.operationUnit(),
				pin: true,
				rollup: true,
				roundEdge: false,
				fixed: "Both"
			});

			ruler.addType({
				name: "CoreTime",
				color: "#00ffcc",
				lineWidth: 30,
				unitToPx: self.operationUnit(),
				fixed: "Both"
			});
		}


		setPositionButonToRightToLeft() {
			let self = this;
			self.indexBtnToLeft(0);

			let marginleftOfbtnToRight: number = 0;

			$(".toLeft").css("display", "none");
			marginleftOfbtnToRight = $("#extable").width() - 32;
			$(".toRight").css('margin-left', marginleftOfbtnToRight + 'px');
		}

		setPositionButonDownAndHeightGrid() {
			let self = this;
			let exTableHeight = window.innerHeight - 92 - 31 - 50 - 50 - 35 - 27 - 27 - 20;
			let rows = (exTableHeight - 34) / 30;
			if (rows > 17) {
				exTableHeight = 17 * 30 + 18;
				//$(".toDown").css("background", "url() no-repeat center");
				self.indexBtnToDown(1);
			} else {
				exTableHeight = (rows - 1) * 30 - 18;
				self.indexBtnToDown(0);
				//$(".toDown").css("background", "url() no-repeat center");
			}
			if (window.innerHeight > 800) {
				$("#master-wrapper").css({ 'overflow-y': 'hidden' });
			} else {
				$("#master-wrapper").css({ 'overflow-y': 'auto' });
				$("#master-wrapper").css({ 'overflow-x': 'hidden' });
				if (rows > 7) {
					exTableHeight = 10 * 30 + 18;
				}
				if (window.innerHeight == 667) {
					$("#extable").width(window.innerWidth - 100);
				}
			}
			$("#extable").exTable("setHeight", exTableHeight);
			let heightExtable = $("#extable").height();
			let margintop = heightExtable - 52;
			$(".toDown").css({ "margin-top": margintop + 'px' });
			$("#extable").exTable("scrollBack", 0, { h: self.startScrollGc() });
		}

		setWidth(): any {
			$(".ex-header-detail").width(window.innerWidth - 572);
			$(".ex-body-detail").width(window.innerWidth - 554);
			$("#extable").width(window.innerWidth - 554);
		}

		toLeft() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (self.showA9) {
					$("#extable").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "210px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 100 + 'px');
				}
			} else {
				if (self.showA9) {
					$("#extable").exTable("showMiddle");
				}
				$(".ex-header-middle").css("width", 560 + 'px' + '!important')
				$(".toLeft").css("margin-left", 577 + 'px');
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 562 + 'px');
				}
			}
			self.indexBtnToLeft(self.indexBtnToLeft() + 1);
			self.localStore().showHide = self.indexBtnToLeft();
			storage.setItemAsJson(self.KEY, self.localStore());
			$("#extable").exTable("scrollBack", 0, { h: self.startScrollGc() });
		}

		toDown() {
			let self = this;
			let exTableHeight = window.innerHeight - 92 - 31 - 50 - 50 - 35 - 27 - 27 - 20;
			exTableHeight = 17 * 30 + 18;
			if (self.indexBtnToDown() % 2 == 0) {
				//$(".toDown").css("background", "url() no-repeat center");
				$("#extable").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');

			} else {
				exTableHeight = 10 * 30 + 18;
				//$(".toDown").css("background", "url() no-repeat center");
				$("#extable").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');

			}
			self.indexBtnToDown(self.indexBtnToDown() + 1);
		}

		showHide() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (!self.showA9) {
					$("#extable").exTable("showMiddle");
				}
				$(".toLeft").css("margin-left", 577 + 'px');

				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 562 + 'px');
				}
			} else {
				if (self.showA9) {
					$("#extable").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "210px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 200 + 'px');
				}
			}
		}

		public convertTime(timeStart: any, timeEnd: any, timeStart2: any, timeEnd2: any) {

			if (timeStart != "") {
				timeStart = parseTime(timeStart).hours * 60 + parseTime(timeStart).minutes;
			}

			if (timeEnd != "") {
				timeEnd = parseTime(timeEnd).hours * 60 + parseTime(timeEnd).minutes;
			}

			if (timeStart2 != "") {
				timeStart2 = parseTime(timeStart2).hours * 60 + parseTime(timeStart2).minutes;
			}

			if (timeEnd2 != "") {
				timeEnd2 = parseTime(timeEnd2).hours * 60 + parseTime(timeEnd2).minutes;
			}
			let timeConvert = {
				start: timeStart != "" ? timeStart : "",
				end: timeEnd != "" ? timeEnd : "",
				start2: timeStart2 != "" ? timeStart2 : "",
				end2: timeEnd2 != "" ? timeEnd2 : ""
			}
			return timeConvert;
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

	class ItemModel {
		code: string;
		name: string;

		constructor(code: string, name: string) {
			this.code = code;
			this.name = name;
		}
	}

	interface ILocalStore {
		startTimeSort: boolean;
		showWplName: boolean;
		operationUnit: string;
		displayFormat: number;
		showHide: number;
	}

	interface ITimeGantChart {
		empId: string,
		typeOfTime: string,
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
