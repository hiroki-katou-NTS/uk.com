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
		dataFromSc: KnockoutObservable<model.EmployeeInformation> = ko.observable();
		dataA6: KnockoutObservable<model.A6Data> = ko.observable();
		dataInitStartKsu003Dto: KnockoutObservable<model.GetInfoInitStartKsu003Dto> = ko.observable();
		displayWorkInfoByDateDto: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		displayDataKsu003: KnockoutObservable<Array<model.DisplayWorkInfoByDateDto>> = ko.observable();
		getFixedWorkInformationDto: KnockoutObservable<Array<any>> = ko.observable();
		dataColorA6: Array<any> = [];

		ruler = new chart.Ruler($("#gc")[0]);

		/** 社員勤務情報　dto */
		employeeWorkInfo: KnockoutObservable<model.EmployeeWorkInfoDto> = ko.observable();

		/** Color */
		employeeColor: KnockoutObservable<string> = ko.observable('');
		// suport color
		suportColor: KnockoutObservable<string> = ko.observable('');

		/** A6_color① */
		workingInfoColor: KnockoutObservable<string> = ko.observable('');
		/** A3_2 pixel */
		operationUnit: KnockoutObservable<number> = ko.observable(3.5);
		operationOneMinus: KnockoutObservable<number> = ko.observable(12);

		timeToPixelStart: KnockoutObservable<number> = ko.observable();
		timeToPixelEnd: KnockoutObservable<number> = ko.observable();
		timeToPixelStart2: KnockoutObservable<number> = ko.observable();
		timeToPixelEnd2: KnockoutObservable<number> = ko.observable();

		localStore: KnockoutObservable<ILocalStore> = ko.observable();
		startScrollGc: KnockoutObservable<number> = ko.observable();

		/** tất cả các loại time của gantt chart */
		typeTimeGc: KnockoutObservable<any> = ko.observable();

		/** Các loại thời gian */
		constructor() {
			let self = this;
			// get data from sc A
			self.dataFromA = ko.observable(getShared('dataFromA'));

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
					
					let timeConvert = self.convertTime(dataMid.startTime1, dataMid.endTime1 ,dataMid.startTime2 , dataMid.endTime2);
					let totalTime = (timeConvert.end - timeConvert.start) + (timeConvert.end2 - timeConvert.start2);
					totalTime = formatById("Clock_Short_HM", totalTime * 60);
					$("#extable").exTable("cellValue", "LEFT_TBL", dataCell.originalEvent.detail.rowIndex, "totalTime", totalTime);
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

			let self = this, leftDs: any = [], middleDs: any = [], timeGantChart: Array<any> = [];
			let gcFixedWorkTime: Array<model.IFixedFlowFlexTime> = [],
				gcBreakTime: Array<model.IBreakTime> = [],
				gcOverTime: Array<model.IOverTime> = [],
				gcSupportTime: any = null,
				gcFlowTime: Array<model.IFixedFlowFlexTime> = [],
				gcFlexTime: Array<model.IFixedFlowFlexTime> = [],
				gcCoreTime: Array<model.ICoreTime> = [],
				gcHolidayTime: Array<model.IHolidayTime> = [],
				gcShortTime: Array<model.IShortTime> = [];

			_.forEach(self.dataScreen003A().employeeInfo, schedule => {
				self.setColorEmployee(schedule.workInfoDto.isNeedWorkSchedule, schedule.workInfoDto.isCheering);
				leftDs.push({
					empId: schedule.empId,
					color: self.employeeColor()
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

				if (schedule.workScheduleDto != null) {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'), worktypeCode: schedule.workScheduleDto.workTypeCode,
						worktype: schedule.workScheduleDto.workTypeStatus, worktimeCode: schedule.workScheduleDto.workTimeCode, worktime: schedule.workScheduleDto.workTimeStatus,
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
				if (schedule.fixedWorkInforDto != null && schedule.workScheduleDto != null) {
					if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FIXED
						&& (schedule.workInfoDto.isCheering == (SupportAtr.TIMEZONE_SUPPORTER || SupportAtr.NOT_CHEERING))) {
						// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
						gcFixedWorkTime.push({
							empId: schedule.workInfoDto.employeeId,
							timeNo: 1,
							color: "#ccccff",
							// 社員勤務情報　dto > 応援か
							isCheering: schedule.workInfoDto.isCheering,
							// 勤務固定情報　dto > Optional<勤務タイプ>
							workType: schedule.fixedWorkInforDto.workType,
							// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
							startTime: schedule.workScheduleDto.startTime1,
							endTime: schedule.workScheduleDto.endTime1,
							// 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>
							startTimeRange: schedule.fixedWorkInforDto.startTimeRange1,
							etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange1

						});
						// 複数回勤務管理.使用区別＝＝true
						// 社員勤務予定dto.Optional<開始時刻2>, 社員勤務予定dto.Optional<終了時刻2>
						if (self.dataScreen003A().targetInfor == 1) {
							gcFixedWorkTime.push({
								empId: schedule.workInfoDto.employeeId,
								timeNo: 2,
								color: "#ccccff",
								isCheering: schedule.workInfoDto.isCheering,
								workType: schedule.fixedWorkInforDto.workType,

								startTime: schedule.workScheduleDto.startTime2,
								endTime: schedule.workScheduleDto.endTime2,
								startTimeRange: schedule.fixedWorkInforDto.startTimeRange2,
								etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange2
							});
						}
					}

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
						&& (schedule.workInfoDto.isCheering == (SupportAtr.TIMEZONE_SUPPORTER || SupportAtr.NOT_CHEERING))) {
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
						}
					}

					// Thời gian Flex time
					// 勤務固定情報　dto．Optional<勤務タイプ>==フレックス勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
					if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLEX
						&& (schedule.workInfoDto.isCheering == (SupportAtr.TIMEZONE_SUPPORTER || SupportAtr.NOT_CHEERING))) {
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
					empID: schedule.empId,
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
			self.initExtableData(timeGantChart, leftDs, middleDs);
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
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
					self.convertDataIntoExtable();
				});

			return dfd.promise();
		}

		public initExtableData(dataBindChart: any, leftDs: any, middleDs: any) {
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
				self.initExTable([], leftDs, middleDs);
				self.showHide();
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
				workplaceId: self.dataFromA().workplaceId != null ? self.dataFromA().workplaceId : null,
				workplaceGroupId: self.dataFromA().workplaceGroupId != null ? self.dataFromA().workplaceGroupId : null,
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
				lstEmployeeInfo: self.dataFromA().listEmp,
				baseDate: self.targetDate()
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
				workTypeCode: self.dataA6().workTypeCode,
				workTimeCode: self.dataA6().workTimeCode
			};
			setShared('dataShareKDL003', dataShare);
			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(() => {
				let dataShare = getShared('dataFromShareKDL003');
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
				self.employeeColor('#ddddd2');
				return;
			}

			switch (cheering) {
				case SupportAtr.ALL_DAY_SUPPORTER:
					self.employeeColor('#c3d69b')
					break;

				case SupportAtr.ALL_DAY_RESPONDENT:
					self.employeeColor('#fedfe6')
					break;

				case SupportAtr.TIMEZONE_SUPPORTER:
					self.employeeColor('#ebf1de')
					break;

				case SupportAtr.TIMEZONE_RESPONDENT:
					self.employeeColor('#ffccff')
					break;
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
				} else if (workScheduleDto.breakTimeStatus === model.EditStateSetting.REFLECT_APPLICATION) {
					breakTimeColor = "bfea60"
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

		public convertTimePixel(timeStart: any, timeEnd: any, timeStart2: any, timeEnd2: any) {
			let self = this,
				time_format_end = timeEnd != null ? timeEnd.match(/([\d]*):([\d]+)/) : timeEnd2.match(/([\d]*):([\d]+)/),
				time_format = timeStart != null ? timeStart.match(/([\d]*):([\d]+)/) : timeStart2.match(/([\d]*):([\d]+)/),
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
			timeStart != null ? self.timeToPixelStart(time * self.operationOneMinus()) : self.timeToPixelStart2(time * self.operationOneMinus());
			timeEnd != null ? self.timeToPixelEnd(time_end * self.operationOneMinus()) : self.timeToPixelEnd2(time_end * self.operationOneMinus());

		}

		initExTable(dataBindChart: any, leftDs: any, midData: any): void {
			let ruler = new chart.Ruler($("#gc")[0]);
			let self = this;
			let middleContentDeco: any = [], leftContentDeco: any = [];

			// phần leftMost
			let leftmostColumns = [],
				leftmostHeader = {},
				leftmostContent = {};

			leftmostColumns = [{
				key: "empName",
				icon: { for: "body", class: "icon-leftmost", width: "25px" },
				headerText: getText('KSU003_20'), width: "160px", control: "link",
				css: { whiteSpace: "pre" }
			},{ headerText: getText('KSU003_21'), key: "cert", width: "50px" }];

			leftmostHeader = {
				columns: leftmostColumns,
				rowHeight: "33px",
				width: "210px"
			};

			let leftmostDs = [], middleDs = [];

			for (let i = 0; i < self.dataFromA().listEmp.length; i++) {

				let dataLeft = self.dataFromA().listEmp[i],
					datafilter = _.filter(midData, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id }),
					dataMid = datafilter[0],
					eName: any = dataLeft.code + " " + dataLeft.name;

				let leftDSFilter = _.filter(leftDs, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id })

				leftmostDs.push({ empId: self.dataFromA().listEmp[i].id, empName: eName, cert : getText('KSU003_22') });
				
				leftContentDeco.push(new CellColor("empName", dataMid.empId, leftDSFilter[0].color));
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
				{ headerText: getText('KSU003_23'), key: "worktypeCode", width: "50px", handlerType: "input", dataType: "text" },
				{ headerText: getText('KSU003_25'), key: "worktype", width: "50px" },
				{ headerText: getText('KSU003_23'), key: "worktimeCode", width: "50px", handlerType: "input", dataType: "text" },
				{ headerText: getText('KSU003_25'), key: "worktime", width: "50px" },
				{ headerText: getText('KSU003_27'), key: "startTime1", width: "50px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_28'), key: "endTime1", width: "50px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_29'), key: "startTime2", width: "50px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_30'), key: "endTime2", width: "50px", handlerType: "input", dataType: "time" },
				{ headerText: getText('KSU003_31'), key: "totalTime", width: "50px" },
				{ headerText: getText('KSU003_33'), key: "breaktime", width: "50px" }
			];

			middleHeader = {
				columns: middleColumns,
				width: "500px",
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
			for (let i = 0; i < 25; i++) {
				if (i == 0) {
					detailColumns.push({ key: "empId", width: "0px", headerText: "ABC", visible: false });
				} else {
					detailColumns.push({ key: (i - 1).toString(), width: width });
				}
			}
			// Phần detail
			detailHeaderDs = [{ empId: "", 0: "0", 1: "1", 2: "2", 3: "3", 4: "4", 5: "5", 6: "6", 7: "7", 8: "8", 9: "9", 10: "10", 11: "11", 12: "12", 13: "13", 14: "14", 15: "15", 16: "16", 17: "17", 18: "18", 19: "19", 20: "20", 21: "21", 22: "22", 23: "23" }];
			detailHeader = {
				columns: detailColumns,
				dataSource: detailHeaderDs,
				rowHeight: "33px",
				width: "700px"
			};

			let detailContentDs = [];
			for (let i = 0; i < self.dataFromA().listEmp.length; i++) {
				let datafilter = _.filter(midData, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id });
				detailContentDs.push({ empId: datafilter[0].empId, 0: "", 1: "", 2: "", 3: "", 4: "", 5: "", 6: "", 7: "", 8: "", 9: "", 10: "", 11: "", 12: "", 13: "", 14: "", 15: "", 16: "", 17: "", 18: "", 19: "", 20: "", 21: "", 22: "", 23: "" });
			}

			detailContent = {
				columns: detailColumns,
				dataSource: detailContentDs,
				primaryKey: "empId"
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
			let startTime = 0;
			for (let i = 0; i < self.dataFromA().listEmp.length; i++) {
				let datafilter = _.filter(midData, (x: any) => { return x.empId === self.dataFromA().listEmp[i].id });
				let timeStart = '10:30';
				let timeEnd = '19:05';
				self.convertTimePixel(timeStart, timeEnd, null, null);
				let start = self.timeToPixelStart();
				let end = self.timeToPixelEnd();
				startTime = start;
				if (datafilter[0].startTime1 != "" && datafilter[0].endTime1 != "") {
					if (i == 2) {
						let gc = this.ruler.addChartWithType("Flex", {
							id: `rgc${i}`,
							start: self.timeToPixelStart(),
							end: self.timeToPixelEnd(),
							lineNo: i
						});

					}

					let gc = this.ruler.addChartWithType("Fixed", {
						id: `rgc${i}`,
						start: (start),
						end: (end),
						lineNo: i,
						title: "固定勤務"
					});


					$(gc).on("gcResize", (e: any) => {
						let param = e.detail;
					});

					if (i == 10) {
						let timeStartLeft = '4:00';
						let timeEndLeft = '8:05';
						self.convertTimePixel(timeStartLeft, timeEndLeft, null, null);
						let lgc = this.ruler.addChartWithType("Fixed", {
							id: `lgc${i}`,
							start: self.timeToPixelStart(),
							end: self.timeToPixelEnd(),
							lineNo: i
						});
					}


					let timeStart2 = '11:45';
					let timeEnd2 = '12:45';
					self.convertTimePixel(null, null, timeStart2, timeEnd2);
					let start2 = self.timeToPixelStart2();
					let end2 = self.timeToPixelEnd2();


					this.ruler.addChartWithType("OT", {
						id: `rgc${i}_0`,
						parent: `rgc${i}`,
						lineNo: i,
						start: start - 20,
						end: start
					});

					this.ruler.addChartWithType("BreakTime", {
						id: `rgc${i}_1`,
						parent: `rgc${i}`,
						lineNo: i,
						start: start2,
						end: end2
					});
				}
			}

			self.startScrollGc(startTime * self.operationUnit() - 42);
			$("#extable").exTable("scrollBack", 0, { h: self.startScrollGc() });

			// set lock slide and resize chart
			ruler.setLock([0, 1, 3], true);

			// set height grid theo localStorage đã lưu
			self.setPositionButonDownAndHeightGrid();
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
				roundEdge: false,
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
				fixed: "Both",
				pin: true,
				rollup: true,
				roundEdge: false
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
				$(".toLeft").css("margin-left", "179px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 170 + 'px');
				}
			} else {
				if (self.showA9) {
					$("#extable").exTable("showMiddle");
				}
				$(".ex-header-middle").css("width", 560 + 'px' + '!important')
				$(".toLeft").css("margin-left", 715 + 'px');
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 700 + 'px');
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
				$(".toLeft").css("margin-left", 715 + 'px');

				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 700 + 'px');
				}
			} else {
				if (self.showA9) {
					$("#extable").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "179px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$(".toLeft").css("margin-left", 170 + 'px');
				}
			}
		}
		
		public convertTime(timeStart : any, timeEnd : any,timeStart2 : any, timeEnd2 : any) {
			
			if (timeStart != null) {
				timeStart = parseTime(timeStart).hours + parseTime(timeStart).minutes;
			}

			if (timeEnd != null) {
				timeEnd = parseTime(timeStart).hours + parseTime(timeStart).minutes;
			}
			
			if (timeStart2 != null) {
				timeStart2 = parseTime(timeStart).hours + parseTime(timeStart).minutes;
			}

			if (timeEnd2 != null) {
				timeEnd2 = parseTime(timeStart).hours + parseTime(timeStart).minutes;
			}
			let timeConvert = {
				start : timeStart,
				end : timeEnd,
				start2 : timeStart2,
				end2 : timeEnd2
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
