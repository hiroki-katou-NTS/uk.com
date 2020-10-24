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
	/**
	 * ScreenModel
	 */
	export class ScreenModel {

		KEY: string = 'USER_KSU003_INFOR';
		employeeIdLogin: string = __viewContext.user.employeeId;

		/*A2_1_3*/
		targetDate: KnockoutObservable<string> = ko.observable('2020/05/02');
		/*A2_2*/
		organizationName: KnockoutObservable<string> = ko.observable('THẾ GIỚI NÀY LÀ CỦA CHÚNG MÌNH HỐ HỐ HỐ');
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
		getFixedWorkInformationDto : KnockoutObservable<Array<any>> = ko.observable();

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
			self.dataFromA = getShared('dataFromA')

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
			
			$("#extable").on("extablecellupdated", (dataCell : any) => {
					let index = dataCell.originalEvent.detail.rowIndex;
					let dataMid = $("#extable").exTable('dataSource', 'middle').body[index];
					if(dataCell.originalEvent.detail.columnKey === "worktypeCode" ||  dataCell.originalEvent.detail.columnKey === "worktimeCode"){
						self.getEmpWorkFixedWorkInfo(dataMid.workTypeCode, dataMid.worktimeCode);
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
			let self = this;
			let dfd = $.Deferred<any>();
			let local: ILocalStore = {
				startTimeSort: self.checked() == true,
				showWplName: self.checkedName() == true,
				operationUnit: self.selectOperationUnit(),
				displayFormat: self.selectedDisplayPeriod(),
				showHide: self.indexBtnToLeft()
			}
			self.localStore(local);
			storage.getItem(self.KEY).ifPresent((data: any) => {
				let userInfor: ILocalStore = JSON.parse(data);
				self.indexBtnToLeft(userInfor.showHide);
				self.selectOperationUnit(userInfor.operationUnit);
				self.selectedDisplayPeriod(userInfor.displayFormat);
				self.checked(userInfor.startTimeSort);
				self.checkedName(userInfor.showWplName);
				self.showHide();
			});
			self.dataA6 = null;
			self.initExTable([], "", "");
			// A2_2 TQP 
			// self.organizationName(self.dataInitStartKsu003Dto().displayInforOrganization().displayName());
			dfd.resolve(1);
			return dfd.promise();
		}
		
		public getDataExtable(){
			let self = this;
			let dataSource = $("#extable").exTable('dataSource', 'detail').body;
			console.log("aaa");
		}

		// Tạo data để truyền vào ExTable
		public convertDataIntoExtable() {

			let self = this, middleDs = [], timeGantChart: Array<any> = [];
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
				middleDs.push({
					empId: schedule.employeeId, // 社員勤務情報　dto > 社員ID
					cert: getText('KSU003_22'),
					worktypeCode: schedule.employeeWorkScheduleDto.workTypeCode, // 社員勤務予定dto > Optional<勤務種類コード>
					worktype: schedule.employeeWorkScheduleDto.workTypeStatus, //勤務種類編集状態
					worktimeCode: schedule.employeeWorkScheduleDto.workTimeCode, //就業時間帯コード
					worktime: schedule.employeeWorkScheduleDto.workTimeStatus, //就業時間帯編集状態
					startTime1: schedule.employeeWorkScheduleDto.startTime1, //開始時刻１
					endTime1: schedule.employeeWorkScheduleDto.endTime1, //終了時刻１
					startTime2: schedule.employeeWorkScheduleDto.startTime2,  //開始時刻2
					endTime2: schedule.employeeWorkScheduleDto.endTime2, //終了時刻2
					totalTime: 0, // tính tổng làm sau hihih
					breaktime: schedule.employeeWorkScheduleDto.breakTimeStatus //休憩時間帯編集状態
				});

				// Push data of gant chart
				// Thời gian cố định
				// 勤務固定情報　dto．Optional<勤務タイプ>==固定勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
				if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FIXED
					&& (schedule.employeeWorkInfoDto.isCheering == (model.SupportAtr.TIMEZONE_SUPPORTER || model.SupportAtr.NOT_CHEERING))) {
					// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
					gcFixedWorkTime.push({
						empId: schedule.employeeWorkInfoDto.employeeId,
						timeNo: 1,
						color: "#ccccff",
						// 社員勤務情報　dto > 応援か
						isCheering: schedule.employeeWorkInfoDto.isCheering,
						// 勤務固定情報　dto > Optional<勤務タイプ>
						workType: schedule.fixedWorkInforDto.workType,
						// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
						startTime: schedule.employeeWorkScheduleDto.startTime1,
						endTime: schedule.employeeWorkScheduleDto.endTime1,
						// 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>
						startTimeRange: schedule.fixedWorkInforDto.startTimeRange1,
						etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange1

					});
					// 複数回勤務管理.使用区別＝＝true
					// 社員勤務予定dto.Optional<開始時刻2>, 社員勤務予定dto.Optional<終了時刻2>
					if (self.dataScreen003A().targetInfor == 1) {
						gcFixedWorkTime.push({
							empId: schedule.employeeWorkInfoDto.employeeId,
							timeNo: 2,
							color: "#ccccff",
							isCheering: schedule.employeeWorkInfoDto.isCheering,
							workType: schedule.fixedWorkInforDto.workType,

							startTime: schedule.employeeWorkScheduleDto.startTime2,
							endTime: schedule.employeeWorkScheduleDto.endTime2,
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
						empId: schedule.employeeWorkInfoDto.employeeId,
						// 社員勤務予定　dto > Optional<List<休憩時間帯>>
						lstBreakTime: schedule.employeeWorkScheduleDto.listBreakTimeZoneDto,
						color: "#ff9999",
						// 勤務固定情報　dto > Optional<休憩時間帯を固定にする>
						fixBreakTime: schedule.fixedWorkInforDto.fixBreakTime
					})
				}

				// Thời gian làm thêm
				// 勤務固定情報 dto.Optional<List<残業時間帯>>から時間外労働時間横棒を生成する。
				gcOverTime.push({
					// 社員勤務情報　dto > 社員ID
					empId: schedule.employeeWorkInfoDto.employeeId,
					// 勤務固定情報　dto > Optional<List<残業時間帯>>
					lstOverTime: schedule.fixedWorkInforDto.overtimeHours,
					color: "#ffff00"
				})

				// Thời gian lưu động 
				// 勤務固定情報　dto．Optional<勤務タイプ>== 流動勤務&&社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
				if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLOW
					&& (schedule.employeeWorkInfoDto.isCheering == (model.SupportAtr.TIMEZONE_SUPPORTER || model.SupportAtr.NOT_CHEERING))) {
					// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
					gcFlowTime.push({
						timeNo: 1,
						// 社員勤務情報　dto > 社員ID
						empId: schedule.employeeWorkInfoDto.employeeId,
						// 勤務固定情報　dto > Optional<勤務タイプ>
						workType: schedule.fixedWorkInforDto.workType,
						color: "#ffc000",
						// 社員勤務情報　dto > 応援か
						isCheering: schedule.employeeWorkInfoDto.isCheering,

						// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
						startTime: schedule.employeeWorkScheduleDto.startTime1,
						endTime: schedule.employeeWorkScheduleDto.endTime1,

						// // 勤務固定情報　dto > Optional<日付開始時刻範囲時間帯1>, Optional<日付終了時刻範囲時間帯1>, 
						startTimeRange: schedule.fixedWorkInforDto.startTimeRange1,
						etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange1

					});
					// 複数回勤務管理.使用区別＝＝true
					// 社員勤務予定dto.Optional<開始時刻2>, 社員勤務予定dto.Optional<終了時刻2>
					if (self.dataScreen003A().targetInfor == 1) {
						gcFlowTime.push({
							timeNo: 1,
							empId: schedule.employeeWorkInfoDto.employeeId,
							workType: schedule.fixedWorkInforDto.workType,
							color: "#ffc000",
							isCheering: schedule.employeeWorkInfoDto.isCheering,
							startTime: schedule.employeeWorkScheduleDto.startTime2,
							endTime: schedule.employeeWorkScheduleDto.endTime2,
							startTimeRange: schedule.fixedWorkInforDto.startTimeRange2,
							etartTimeRange: schedule.fixedWorkInforDto.etartTimeRange2
						});
					}
				}

				// Thời gian Flex time
				// 勤務固定情報　dto．Optional<勤務タイプ>==フレックス勤務 && 社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
				if (schedule.fixedWorkInforDto.workType == model.WorkTimeForm.FLEX
					&& (schedule.employeeWorkInfoDto.isCheering == (model.SupportAtr.TIMEZONE_SUPPORTER || model.SupportAtr.NOT_CHEERING))) {
					// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
					gcFlexTime.push({
						timeNo: 1,
						// 社員勤務情報　dto > 社員ID
						empId: schedule.employeeWorkInfoDto.employeeId,
						// 勤務固定情報　dto > Optional<勤務タイプ>
						workType: schedule.fixedWorkInforDto.workType,
						color: "#ccccff",
						// 社員勤務情報　dto > 応援か
						isCheering: schedule.employeeWorkInfoDto.isCheering,
						// 社員勤務予定　dto > Optional<開始時刻1>, Optional<終了時刻1>
						startTime: schedule.employeeWorkScheduleDto.startTime1,
						endTime: schedule.employeeWorkScheduleDto.endTime1,
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
						empId: schedule.employeeWorkInfoDto.employeeId,
						color: "#00ffcc",
						// 勤務固定情報　dto > Optional<勤務タイプ>, Optional<コア開始時刻>, Optional<コア終了時刻>
						workType: schedule.fixedWorkInforDto.workType,
						coreStartTime: schedule.fixedWorkInforDto.coreStartTime,
						coreEndTime: schedule.fixedWorkInforDto.coreEndTime
					});
				}

				// Thời gian holiday time
				// 社員勤務情報　dto．Optional<Map<時間休暇種類, 時間休暇>> 存在する
				if (schedule.employeeWorkInfoDto.listTimeVacationAndType.length > 0) {
					gcHolidayTime.push({
						// 社員勤務情報　dto > 社員ID, Optional<Map<時間休暇種類, 時間休暇>>
						empId: schedule.employeeWorkInfoDto.employeeId,
						color: "#c4bd97",
						listTimeVacationAndType: schedule.employeeWorkInfoDto.listTimeVacationAndType
					})
				}

				// Thời gian chăm sóc / giữ trẻ (short time)
				// 社員勤務情報　dto．Optional<育児介護短時間> が存在する
				if (schedule.employeeWorkInfoDto.shortTime.length > 0) {
					gcShortTime.push({
						// 社員勤務情報　dto > 社員ID, Optional<Map<時間休暇種類, 時間休暇>>
						empId: schedule.employeeWorkInfoDto.employeeId,
						color: "#6fa527",
						listShortTime: schedule.employeeWorkInfoDto.shortTime
					})
				}

				timeGantChart.push({
					empID: schedule.employeeId,
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
				workplaceId: self.dataFromA().id,
				workplaceGroupId: self.dataFromA().id
			}
			// ①<<ScreenQuery>> 初期起動の情報取得
			service.getDataStartScreen(targetOrgDto)
				.done((data: model.GetInfoInitStartKsu003Dto) => {
					self.dataInitStartKsu003Dto(data);
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
				id: self.dataFromA().id,
				name: self.dataFromA().name
			}
			let lstEmpId = _.map(self.dataFromA().employeeInfor, c => { return { id: c.employeeId }; })
			let param = {
				targetOrg: targetOrg,
				lstEmpId: lstEmpId,
				date: self.targetDate()
			};
			service.displayDataKsu003(param)
				.done((data) => {
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
				}).always(function() {
				});

			return dfd.promise();
		}
		
		// 社員勤務予定と勤務固定情報を取得する
		public getEmpWorkFixedWorkInfo(workTypeCode : string, workTimeCode : string): JQueryPromise<any> {
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
			let lstEmpId = _.map(self.dataFromA().employeeInfor, c => { return { id: c.employeeId }; })
			let param = {
				ymd : self.targetDate(),
				lstEmpid : lstEmpId
			}
			service.displayDataKsu003(param)
				.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
					// set lại giá trị theo ①<<ScreenQuery>> 初期起動の情報取得
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

			let data: any = _.filter(self.dataScreen003A().employeeInfo, (x) => { return x.employeeId === "1"; })

			let dataShare: model.ParamKsu003 = {
				employeeInfo: data,
				targetInfor: self.dataScreen003A().targetInfor,
				canModified: self.dataScreen003A().canModified,
				scheCorrection: self.dataScreen003A().scheCorrection
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
				lstEmployeeInfo: self.dataFromA().employeeInfor,
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
		public setColorEmployee() {
			let self = this;

			if (self.employeeWorkInfo().isNeedWorkSchedule == 0) {
				self.employeeColor('#ddddd2');
				return;
			}

			switch (self.employeeWorkInfo().isCheering) {
				case model.SupportAtr.ALL_DAY_SUPPORTER:
					self.employeeColor('#c3d69b')
					break;

				case model.SupportAtr.ALL_DAY_RESPONDENT:
					self.employeeColor('#fedfe6')
					break;

				case model.SupportAtr.TIMEZONE_SUPPORTER:
					self.employeeColor('#ebf1de')
					break;

				case model.SupportAtr.TIMEZONE_RESPONDENT:
					self.employeeColor('#ffccff')
					break;
			}
		}

		/** A6_color① - chua xong o phan login va xin bang don */
		public setColerWorkingInfo() {
			let self = this;

			if (self.employeeWorkInfo().isConfirmed == 0) {
				self.workingInfoColor('#ddddd2');
			} else {
				self.workingInfoColor('#eccefb');
			}
			// my self login
			if (self.employeeIdLogin === self.dataFromA().employeeInfor[0].employeeId) {
				self.workingInfoColor('#94b7fe');
			} else {
				// another login
				self.workingInfoColor('#cee6ff');
			}
			// if app reflect
			let appReflect = true;
			if (appReflect) {
				self.workingInfoColor('#bfea60');
			}
		}

		/*A4_color*/
		public setSupportColor() {
			let self = this;

			switch (self.employeeWorkInfo().isCheering) {
				case model.SupportAtr.ALL_DAY_SUPPORTER:
					self.suportColor('#c3d69b')
					break;

				case model.SupportAtr.ALL_DAY_RESPONDENT:
					self.suportColor('#fedfe6')
					break;

				case model.SupportAtr.TIMEZONE_SUPPORTER:
					self.suportColor('#ebf1de')
					break;

				case model.SupportAtr.TIMEZONE_RESPONDENT:
					self.suportColor('#ffccff')
					break;
			}
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

		initExTable(dataBindGrid: any, viewMode: string, updateMode: string): void {
			let ruler = new chart.Ruler($("#gc")[0]);
			let self = this;
			// phần leftMost
			let leftmostColumns = [],
				leftmostHeader = {},
				leftmostContent = {};

			leftmostColumns = [{
				key: "empName",
				icon: { for: "body", class: "icon-leftmost", width: "25px" },
				headerText: getText('KSU003_20'), width: "160px", control: "link",
				css: { whiteSpace: "pre" }
			}];

			leftmostHeader = {
				columns: leftmostColumns,
				rowHeight: "33px",
				width: "170px"
			};

			let leftmostDs = [], middleDs = [];
			for (let i = 0; i < 20; i++) {
				let eName = nts.uk.text.padRight("名前" + i, " ", 10) + "AAAAAAAAAAAAAAAAAA";
				leftmostDs.push({ empId: i.toString(), empName: eName });
				middleDs.push({
					empId: i.toString(), cert: getText('KSU003_22'), worktypeCode: 10 + i + "",
					worktype: 1 + i + "", worktimeCode: i, worktime: 10 + i + "", 
					startTime1: 1 + i + "", endTime1: 2 + i, 
					startTime2: i / 5 == 2 ? 10 + i : "" + "", endTime2: i / 5 == 2 ? 2 + i : "" + "", 
					totalTime: (1 + i) + (2 + i) + (i / 5 == 2 ? 10 + i : 0) + (i / 5 == 2 ? 2 + i : 0) + "", breaktime: 1 + ""
				});
			}

			leftmostContent = {
				columns: leftmostColumns,
				dataSource: leftmostDs,
				primaryKey: "empId"
			};

			// Phần middle
			let middleColumns = [], middleHeader = {}, middleContent = {};
			middleColumns = [
				{ headerText: getText('KSU003_21'), key: "cert", width: "50px" },
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
				width: "560px",
				rowHeight: "33px"
			};
			middleContent = {
				columns: middleColumns,
				dataSource: middleDs,
				primaryKey: "empId"
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
			for (let i = 0; i < 20; i++) {
				detailContentDs.push({ empId: i.toString(), 0: "", 1: "", 2: "", 3: "", 4: "", 5: "", 6: "", 7: "", 8: "", 9: "", 10: "", 11: "", 12: "", 13: "", 14: "", 15: "", 16: "", 17: "", 18: "", 19: "", 20: "", 21: "", 22: "", 23: "" });
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
				manipulatorId: "6",
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
			for (let i = 0; i < 20; i++) {
				let timeStart = '10:30';
				let timeEnd = '19:05';
				self.convertTimePixel(timeStart, timeEnd, null, null);
				let start = self.timeToPixelStart();
				let end = self.timeToPixelEnd();
				startTime = start;

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


				$(gc).on("gcResize", (e) => {
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

				this.ruler.addChartWithType("CoreTime", {
					id: `rgc${i}_0`,
					parent: `rgc${i}`,
					lineNo: i,
					start: end - 30,
					end: end - 10
				});

				this.ruler.addChartWithType("OT", {
					id: `rgc${i}_1`,
					parent: `rgc${i}`,
					lineNo: i,
					start: start - 20,
					end: start
				});

				this.ruler.addChartWithType("BreakTime", {
					id: `rgc${i}_2`,
					parent: `rgc${i}`,
					lineNo: i,
					start: start2,
					end: end2
				});

				this.ruler.addChartWithType("ShortTime", {
					id: `rgc${i}_3`,
					parent: `rgc${i}`,
					lineNo: i,
					start: end,
					end: end + 40
				});

				this.ruler.addChartWithType("HolidayTime", {
					id: `rgc${i}_4`,
					parent: `rgc${i}`,
					lineNo: i,
					start: start2 + 20,
					end: start2 + 40
				});

				// tạo thêm 1 chart với kiểu 
			}

			self.startScrollGc(startTime * self.operationUnit() - 42);
			$("#extable").exTable("scrollBack", 0, { h: self.startScrollGc() });

			// set lock slide and resize chart
			ruler.setLock([0, 1, 2, 3], true);

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
			} else {
				if (self.showA9) {
					$("#extable").exTable("showMiddle");
				}
				$(".ex-header-middle").css("width", 560 + 'px' + '!important')
				$(".toLeft").css("margin-left", 639 + 'px');
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
				$(".toLeft").css("margin-left", 639 + 'px');
			} else {
				if (self.showA9) {
					$("#extable").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", "179px");
			}
		}

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

}