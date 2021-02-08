module nts.uk.at.view.ksu003.a.viewmodel {
	import getText = nts.uk.resource.getText;
	import setShared = nts.uk.ui.windows.setShared;
	import getShared = nts.uk.ui.windows.getShared;
	import errorDialog = nts.uk.ui.dialog.alertError;
	import dialog = nts.uk.ui.dialog;
	import bundledErrors = nts.uk.ui.dialog.bundledErrors;
	import block = nts.uk.ui.block;
	import exTable = nts.uk.ui.exTable;
	import caution = nts.uk.ui.dialog.caution;
	import formatById = nts.uk.time.format.byId;
	import model = nts.uk.at.view.ksu003.a.model;
	import duration = nts.uk.time.minutesBased.duration; // convert time 
	import characteristics = nts.uk.characteristics;
	import alertError = nts.uk.ui.dialog.alertError
	/**
	 * ScreenModel
	 */
	var ruler: any;
	export class ScreenModel {

		KEY: string = 'USER_KSU003_INFOR';
		employeeIdLogin: string = __viewContext.user.employeeId; // Employee login

		targetDate: KnockoutObservable<string> = ko.observable('2020/05/02'); // A2_1_3
		targetDateDay: KnockoutObservable<string> = ko.observable('');

		organizationName: KnockoutObservable<string> = ko.observable(''); // A2_2
		selectedDisplayPeriod: KnockoutObservable<number> = ko.observable(1); // A2_5

		itemList: KnockoutObservableArray<ItemModel>; /*A3_2*/
		selectOperationUnit: KnockoutObservable<string> = ko.observable('0');

		sortList: KnockoutObservableArray<ItemModel>; /*A3_4*/
		checked: KnockoutObservable<string> = ko.observable('1');

		checkedName: KnockoutObservable<boolean> = ko.observable(false); // A3_3

		showA9: boolean;
		checkNext: KnockoutObservable<boolean> = ko.observable(true); // xử lý next day
		checkPrv: KnockoutObservable<boolean> = ko.observable(true);

		indexBtnToLeft: KnockoutObservable<number> = ko.observable(0);// xử lý của botton toLeft, toRight
		indexBtnToDown: KnockoutObservable<number> = ko.observable(0);// xử lý của botton toDown, toUp

		dataFromA: KnockoutObservable<model.InforScreenADto> = ko.observable(); //Data get from Screen A
		dataScreen003A: KnockoutObservable<model.DataScreenA> = ko.observable();
		dataScreen003AFirst: any = [];
		dataScreen045A: KnockoutObservable<model.DataFrom045> = ko.observable();
		dataInitStartKsu003Dto: KnockoutObservable<model.GetInfoInitStartKsu003Dto> = ko.observable();
		fixedWorkInformationDto: Array<model.FixedWork> = [];
		employeeScheduleInfo: Array<any> = [];

		operationUnit: KnockoutObservable<number> = ko.observable(3.5); // A3_2 pixel (mỗi ô đang để 40px)

		localStore: KnockoutObservable<ILocalStore> = ko.observable();
		lstEmpId: Array<IEmpidName> = [];
		timeRange: number = 0; // tổng số cột ở phần detail (24 or 48)
		initDispStart: number = 0; // thời gian bắt đầu của scroll phần detail
		dispStart: number = 0; // thời gian bắt đầu ở header phần detail
		dispStartHours : number =0;
		checkGetInfo: boolean = false;

		dataOfGantChart: Array<ITimeGantChart> = []; // data của từng phần trên extable
		midDataGC: Array<any> = [];
		disableDs: any = [];
		leftDs: any = [];

		tooltip: any = [];

		allGcShow: any = []; // lưu gant chart và data để tạo lại
		allTimeChart: any = [];
		allTimeBrk: any = [];
		timeOfGantChart: any =[];

		checkClearTime: boolean = true; // check when change work time, work type
		checkUpdateMidChart: boolean = true;
		checkUpdateTime: any = [];

		lstDis: any = [];
		lstBreakSum: any = []; // list break time xuất hiện trên màn hình
		lstHolidayShort: any = [];

		totalTimeWork: any = 0;
		checkDisByDate: boolean = true;

		lstAllChildShow: any = [];// tất cả thanh gant chart con show trên màn hình
		checkTypeChange: any = [];
		sumBreaks: any = [];

		checkDragDrog: boolean = false; // phân biệt resize = false vs drop = true
		checkDragDrogAgain: boolean = false; // phân biệt resize = false vs drop = true khi tạo chart
		bindTypeTime: any = [];
		holidayShort: any = [];
		checkMes: number = 0;
		checkHoliday : boolean = false;
		checkNeedTime : string = "";
		workTypeName : string = "";
		
		check045003 : boolean = true;
		timesOfInput : number = 0;
		timesOfInputTime : number = 0;

		enableSave: KnockoutObservable<boolean> = ko.observable(false); // ver 2
		checkEnableSave : boolean = true;
		checkEnableWork : boolean = true;
		checkCalcSum : boolean = true;
		checkOpenDialog : boolean = true; // check open dialog when have error
		constructor(data: any) {
			let self = this;
			// get data from sc A
			self.localStore(data);
			self.dataFromA = ko.observable(getShared('dataFromA'));
			if ((self.dataFromA().daySelect < self.dataFromA().dayEdit && self.dataFromA().dayEdit !== "1900/01/01") || (self.dataFromA().daySelect < moment(new Date()).format('YYYY/MM/DD') && self.dataFromA().dayEdit !== "1900/01/01")) {
				self.checkDisByDate = false;
			}
			self.lstEmpId = _.flatMap(self.dataFromA().listEmp, c => [{ empId: c.id, name: c.name, code: c.code }]);
			self.targetDate(self.dataFromA().daySelect);
			if (self.targetDate() === (self.dataFromA().startDate)) {
				self.checkPrv(false);
			}
			if (self.targetDate() === (self.dataFromA().endDate)) {
				self.checkNext(false);
			}
			
			 let shortW = moment(self.targetDate()).format('(ddd)');
                if (shortW == "(土)") {
                    shortW = "<span style='color:#0000ff;'>" + shortW + "</span>";
                } else if (shortW == "(日)") {
                    shortW = "<span style='color:#ff0000;'>" + shortW + "</span>";
                } 
			
			self.targetDateDay(self.targetDate() + shortW);
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
			if (!_.isNil(data)) {
				self.indexBtnToLeft(data.showHide);
				self.selectOperationUnit(data.operationUnit);
				self.selectedDisplayPeriod(data.displayFormat);
				self.checked(data.startTimeSort);
				self.checkedName(data.showWplName);
/*				self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
					return _.findIndex(data.lstEmpIdSort, (x: any) => { return x.empId == a.empId }) - _.findIndex(data.lstEmpIdSort, (x: any) => { return x.empId == b.empId });
				})*/

				localStorage.removeItem(nts.uk.request.location.siteRoot.rawUrl + "nts.uk.at.web/view/ksu/003/a/index.xhtml/extable-ksu003/areawidths");
			};
			/* 開始時刻順に並び替える(A3_3)はチェックされている */
			self.checked.subscribe((value) => {
				let checkSort = [];
				if (!_.isNil(data)) {
					checkSort = $("#extable-ksu003").exTable('updatedCells');
				}
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
				characteristics.save(self.KEY, self.localStore());
			});

			/** A2_5 */
			self.selectedDisplayPeriod.subscribe((value) => {
				self.localStore().displayFormat = value;
				characteristics.save(self.KEY, self.localStore());
			});

			/** 操作単位選択に選択する A3_2 */
			self.selectOperationUnit.subscribe((value) => {
				let c = parseInt(value) + 1;
				if (value == '3') c = 6
				if (value == '4') c = 12;
				if (!_.isNil(ruler))
					ruler.setSnatchInterval(c);
				self.localStore().operationUnit = value;
				characteristics.save(self.KEY, self.localStore());

			});

			self.checkUpdateTime = {
				name: "",
				id: 0
			}
			
			// tính tổng thời gian khi thay đổi start và end time
			$("#extable-ksu003").on("extablecellupdated", (dataCell: any) => {
				let index: number = dataCell.originalEvent.detail.rowIndex, color = "";
				let dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[index];
				let empId = self.lstEmpId[dataCell.originalEvent.detail.rowIndex].empId;
				let dataFixed = _.filter(self.dataScreen003A().employeeInfo, x => { return x.empId === empId }),
					dataFixInfo = _.filter(self.fixedWorkInformationDto, x => { return x.empId === empId });
				let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(1)",
					cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(3)";
				if(self.check045003 == false) return;
				
				let checkErr = _.filter($("#extable-ksu003").data("errors"), (x: any) => {
					return x.rowIndex === index;
				})

				let checkErr2 = _.filter(checkErr, (x: any) => {
					return x.columnKey === dataCell.detail.columnKey;
				})
				
				let columnKey = dataCell.originalEvent.detail.columnKey;
					if ((columnKey === "startTime1" || columnKey === "startTime2" ||
						columnKey === "endTime1" || columnKey === "endTime2") && self.checkUpdateTime.id == 0) {
							
					if (checkErr2.length > 0 && checkErr.length > 0 && $("#extable-ksu003").data("errors").length > 0 && dataCell.originalEvent.detail.value != "") {
					return;
					}
						self.checkTimeInfo(index, dataMid.worktypeCode, dataMid.worktimeCode, dataMid.startTime1.trim(), dataMid.startTime2.trim(), dataMid.endTime1.trim(), dataMid.endTime2.trim(), columnKey).done(() => {

							let timeConvert = self.convertTime(dataMid.startTime1.trim(), dataMid.endTime1.trim(), dataMid.startTime2.trim(), dataMid.endTime2.trim());
							self.employeeScheduleInfo.forEach((x, i) => {

								if (i === dataCell.originalEvent.detail.rowIndex) {
									if (columnKey === "startTime1") {
										x.startTime1 = timeConvert.start;
										dataFixed[0].workScheduleDto.startTime1 = x.startTime1;
										self.checkTypeChange.push({ type: "startTime1" });
										if (x.startTime1 == "") {
											self.checkMes += 1;
										}
									}
									if (columnKey === "startTime2") {
										x.startTime2 = timeConvert.start2;
										dataFixed[0].workScheduleDto.startTime2 = x.startTime2;
										self.checkTypeChange.push({ type: "startTime2" });
										if (x.startTime2 == "") {
											self.checkMes += 1;

										}
									}
									if (columnKey === "endTime1") {
										x.endTime1 = timeConvert.end;
										dataFixed[0].workScheduleDto.endTime1 = x.endTime1;
										self.checkTypeChange.push({ type: "endTime1" });
										if (x.endTime1 == "") {
											self.checkMes += 1000;

										}
									}
									if (columnKey === "endTime2") {
										x.endTime2 = timeConvert.end2;
										dataFixed[0].workScheduleDto.endTime2 = x.endTime2;
										self.checkTypeChange.push({ type: "endTime2" });
										if (x.endTime2 == "") {
											self.checkMes += 1000;

										}
									}
								}
							})
							if ((timeConvert.start == "" && timeConvert.end != "") || (timeConvert.start != "" && timeConvert.end == "")
								|| (timeConvert.start2 == "" && timeConvert.end2 != "") || (timeConvert.start2 != "" && timeConvert.end2 == ""))
								return;
							// tính lại tổng time
							let lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5), totalBrkTime: any = null;
							self.lstBreakSum = [], self.lstAllChildShow = [], self.lstHolidayShort = [];
							lstTime = self.calcChartTypeTime(dataFixed[0], dataFixed[0].workScheduleDto.listBreakTimeZoneDto,
								timeRangeLimit, lstTime, "BREAK", index);
							for (let e = 0; e < dataFixed[0].workInfoDto.listTimeVacationAndType.length; e++) {
								let y = dataFixed[0].workInfoDto.listTimeVacationAndType[e];
								lstTime = self.calcChartTypeTime(dataFixed[0], y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", index);
							}
							lstTime = self.calcChartTypeTime(dataFixed[0], dataFixed[0].workInfoDto.shortTime,
								timeRangeLimit, lstTime, "SHORT", index);

							lstTime = self.calcChartTypeTime(dataFixed[0], dataFixInfo[0].fixedWorkInforDto == null ? [] : dataFixInfo[0].fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", index);
							let totalTime = self.calcAllTime(dataFixed[0], lstTime, timeRangeLimit);
							if (self.checkDragDrog != true) {
								totalBrkTime = self.calcAllBrk(lstTime);
								totalBrkTime = totalBrkTime != null ? formatById("Clock_Short_HM", Math.round(totalBrkTime * 5)) : "";

								$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime == totalBrkTime + " " ? totalBrkTime : totalBrkTime + " ");
								if (!self.checkClearTime == false) {
									let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" :
									"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)";
									
									if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
									$(cssTotalTime).css("background-color", "#ffffff");
									
									if($("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode != "" && totalTime == ""){
										totalTime = "0:00"
									}
									
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime != null ? totalTime : "");
								}
							} 

						});
						return;
					}
				
				let format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
				if (checkErr2.length > 0 && checkErr.length > 0 && $("#extable-ksu003").data("errors").length > 0 && format.test(dataCell.detail.value) == true) {
					return;
				}
				
				if((dataCell.originalEvent.detail.columnKey === "worktypeCode" && dataMid.worktypeCode == "") /*|| (dataCell.originalEvent.detail.columnKey === "worktimeCode" && dataMid.worktimeCode == "")*/){
						let errors = [];
						errors.push({
							message: nts.uk.resource.getMessage('Msg_1780', [dataCell.originalEvent.detail.columnKey === "worktypeCode" ? nts.uk.resource.getText("KSU003_24") : nts.uk.resource.getText("KSU003_26")]),
							messageId: "Msg_1780",
							supplements: {}
						}); 
						
							if(dataMid.worktypeCode == "" && dataCell.originalEvent.detail.columnKey === "worktypeCode"){
								bundledErrors({ errors: errors }).then(() => {
									$(cssWorkType).click();
									$(cssWorkType).click();
								});
							}
						return;
				}
				
				if ((dataCell.originalEvent.detail.columnKey === "worktypeCode" && dataMid.worktypeCode != "")) {
						self.changeWorkType(dataCell.originalEvent.detail.columnKey,empId,index );
				};

				if (empId === self.employeeIdLogin) {
					color = "#94b7fe";
				} else {
					color = "#cee6ff";
				}

				// Nếu không cần work time	
				if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isNeedWorkTime == "NOT_REQUIRED") {
					if(self.checkHoliday == true)
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
					
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
					color = "#DDDDD2"
				} else if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isNeedWorkTime != "NOT_REQUIRED") {
					$("#extable-ksu003").exTable("enableCell", "middle", empId, "worktimeCode");
					$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime1");
					$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime1");
					if (dataFixed[0].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 != null) {
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime2");
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime2");
					}
				}

				if (self.checkGetInfo == false && self.checkUpdateMidChart == true) {

					// 勤務種類を変更する (nhập thủ công worktype code)
					if ((dataCell.originalEvent.detail.columnKey === "worktypeCode")) {
						if(self.timesOfInput > 0) {
							self.timesOfInput = 0;
						};
						
						if(self.timesOfInputTime > 0) {
							self.timesOfInputTime = 0;
						};
						self.bindTypeTime.push({
							index: index,
							value: true
						})
						self.checkUpdateTime.name = "worktypeCode";
						self.checkUpdateTime.id = 2;
						self.inputWorkInfo(dataMid, index, dataCell, dataFixed, empId, dataCell.originalEvent.detail.columnKey);

						// Nếu là ngày nghỉ	
						if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isHoliday != null && dataFixed[0].fixedWorkInforDto.isHoliday == true) {
							self.checkClearTime = false;
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
							ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
								type: "Flex",
								options: {
									id: `lgc` + index,
									start: -1000,
									end: -1000,
									lineNo: index
								}
							}]);
							return;
						}

						// Nếu không phải ngày nghỉ
						if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isHoliday != null && dataFixed[0].fixedWorkInforDto.isHoliday == false) {
							self.checkMes += 1;
						}
					}

					if (dataCell.originalEvent.detail.columnKey === "worktimeCode") {
						if(self.timesOfInput > 0) {
							self.timesOfInput = 0;
						};
						
						if(self.timesOfInputTime > 0) {
							self.timesOfInputTime = 0;
						};
						self.checkUpdateTime.name = "worktimeCode";
						self.checkUpdateTime.id = 1;
						self.inputWorkInfo(dataMid, index, dataCell, dataFixed, empId, dataCell.originalEvent.detail.columnKey);
						self.bindTypeTime.push({
							index: index,
							value: true
						})
						self.checkMes += 100;
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
			self.getData().done(() => {
				self.hoverEvent(self.targetDate());
				block.clear();
				dfd.resolve();
				block.clear();
				self.showHide();
			});
			$("#extable-ksu003").exTable("scrollBack", 0, { h: (self.initDispStart * 42 - self.dispStart * 3.5) });
			return dfd.promise();
		}


		public inputWorkInfo(dataMid: any, index: number, dataCell: any, dataFixed: any, empId: string, columnKey: string) {
			let self = this, color = "";
			let workTimeCode = $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode;
			let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(1)",
				cssWorkTypeName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(2)",
				cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(3)",
				cssWorkTName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(4)",
				cssStartTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(5)",
				cssEndTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(6)",
				cssStartTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)" : "",
				cssEndTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(8)" : "",
				cssbreakTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(10)" :
				"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(8)";
			/*if(columnKey === "worktypeCode"){
				workTimeCode = self.dataScreen003AFirst().employeeInfo[index].workScheduleDto.workTimeCode;
			}*/
			

			if(self.check045003 == false) return;

			if (empId === self.employeeIdLogin) {
				color = "#94b7fe";
			} else {
				color = "#cee6ff";
			}
				self.getChangeWorkType(columnKey, empId, index).done((data) => {
					self.getEmpWorkFixedWorkInfo(columnKey, empId, index).done(() => {
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", workTimeCode);
				if(self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.fixBreakTime == 0) {
				$(cssbreakTime).css("background-color", "#DDDDD2");
				} else {
					$(cssbreakTime).css("background-color", color);
				}
					if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.isHoliday == true) {
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
				} else {
					if (columnKey === "worktypeCode") {
						$(cssWorkType).css("background-color", color);
						$(cssWorkTypeName).css("background-color", color);
					}
						$(cssStartTime1).css("background-color", color);
						$(cssEndTime1).css("background-color", color);
						if ($(cssStartTime2).css("background-color") != "rgb(221, 221, 210)" || $("#extable-ksu003").exTable('dataSource', 'middle').body[index].startTime2 != "") {
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2 != null) {
							$(cssStartTime2).css("background-color", color);
							$(cssStartTime2).removeClass("xseal");
							}
						}

						if ($(cssEndTime2).css("background-color") != "rgb(221, 221, 210)" || $("#extable-ksu003").exTable('dataSource', 'middle').body[index].endTime2 != "") {
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 != null) {
							$(cssEndTime2).css("background-color", color);
							$(cssEndTime2).removeClass("xseal");
							}
						}
						
						if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType == WorkTimeForm.FLEX) {
							$(cssEndTime2).css("background-color", "#DDDDD2");
							$(cssStartTime2).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
						} else {
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 != null) {
							$(cssEndTime2).css("background-color", color);
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime2");
							$(cssEndTime2).removeClass("xseal");
							}
							
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 == null) {
							$(cssEndTime2).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
							}
							
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2 != null) {
							$(cssStartTime2).css("background-color", color);
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime2");
							$(cssStartTime2).removeClass("xseal");
							}
							
							if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2 == null) {
							$(cssStartTime2).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
							
							}
							
						}
						if(columnKey === "worktimeCode"){
							$(cssWorkTime).css("background-color", color);
							$(cssWorkTName).css("background-color", color);
						}
				}

				if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.isNeedWorkTime == "NOT_REQUIRED") {
					$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
				}

				self.removeChart(index);
				if (dataCell.originalEvent.detail.value != "" &&
					dataCell.originalEvent.detail.value != null) {
					self.checkClearTime = true;

					let lstBrkTime = dataFixed[0].workScheduleDto.listBreakTimeZoneDto, totalBrkTime: any = null;

					let lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5);
					self.lstBreakSum = [], self.lstAllChildShow = [], self.lstHolidayShort = [];
					lstTime = self.calcChartTypeTime(dataFixed[0], dataFixed[0].workScheduleDto.listBreakTimeZoneDto,
						timeRangeLimit, lstTime, "BREAK", index);
					for (let e = 0; e < dataFixed[0].workInfoDto.listTimeVacationAndType.length; e++) {
						let y = dataFixed[0].workInfoDto.listTimeVacationAndType[e];
						lstTime = self.calcChartTypeTime(dataFixed[0], y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", index);
					}
					lstTime = self.calcChartTypeTime(dataFixed[0], dataFixed[0].workInfoDto.shortTime,
						timeRangeLimit, lstTime, "SHORT", index);
					let dataFixInfo = _.filter(self.fixedWorkInformationDto, x => { return x.empId === empId });
					lstTime = self.calcChartTypeTime(dataFixed[0], dataFixInfo[0].fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", index);
					let totalTime = self.calcAllTime(dataFixed[0], lstTime, timeRangeLimit);

					totalBrkTime = self.calcAllBrk(lstTime);
					totalBrkTime = totalBrkTime != null ? formatById("Clock_Short_HM", Math.round(totalBrkTime * 5)) : "";

					let schedule: model.EmployeeWorkScheduleDto = dataFixed[0].workScheduleDto,
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

					let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" : 
					"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)";
					
					if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
					$(cssTotalTime).css("background-color", "#ffffff");
					
					$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime == totalBrkTime + " " ? totalBrkTime : totalBrkTime + " ");

					self.dataScreen003A().employeeInfo[index].workScheduleDto.workTypeCode = schedule.workTypeCode;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.workTimeCode = schedule.workTimeCode;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime1 = schedule.startTime1;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime1 = schedule.endTime1;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2 = schedule.startTime2;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 = schedule.endTime2;
					let dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[index];

					self.checkTimeInfo(index, dataMid.worktypeCode, dataMid.worktimeCode, dataMid.startTime1.trim(),
						dataMid.startTime2.trim(), dataMid.endTime1.trim(), dataMid.endTime2.trim(), columnKey);

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
					$(".xcell").removeClass("x-error");

					// set work time name
					if ((schedule.workTimeCode == null || schedule.workTimeCode == "")) {
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
					} else {
						if (fixed != null && (fixed.workTimeName == null || fixed.workTimeName == "")) {
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", schedule.workTimeCode + getText('KSU003_54'));
						}
					}

					if (dataFixed[0].fixedWorkInforDto.isNeedWorkTime == false) {
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
						/*let cssTimeName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(4)";
						$(cssTimeName).css("pointer-events", "none");*/
					} else {
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "worktimeCode");
					}
				}
			})
				});

		}

		setDataToMidExtable(index: number, empId: string, schedule: model.EmployeeWorkScheduleDto, fixed: model.FixedWorkInforDto) {
			let workTypeName = "", workTimeName = "", self = this, dataFixed = self.dataScreen003A().employeeInfo[index];
			if (schedule == null) {
				workTimeName = getText('KSU003_55');
			}
			if (fixed != null) {
				if (fixed.workTypeName != null && fixed.workTypeName != "") {
					workTypeName = fixed.workTypeName;
				}
				if (fixed.workTimeName != null && fixed.workTimeName != "") {
					workTimeName = fixed.workTimeName;
				}
			}
			if (schedule.workTypeCode != null && schedule.workTypeCode != "" && fixed != null
				&& (fixed.workTypeName == null || fixed.workTypeName == "")) {
				workTypeName = schedule.workTypeCode + getText('KSU003_54')
			}
			// set work time name
			if ((schedule.workTimeCode == null || schedule.workTimeCode == "")) {
				workTimeName = getText('KSU003_55');
			} else {
				if (fixed != null && (fixed.workTimeName == null || fixed.workTimeName == "")) {
					workTimeName = schedule.workTimeCode + getText('KSU003_54');
				}
			}
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", workTypeName);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", workTimeName);
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", schedule.startTime1 == null ? "" : formatById("Clock_Short_HM", (schedule.startTime1)));
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", schedule.endTime1 == null ? "" :formatById("Clock_Short_HM", (schedule.endTime1)));
			if (schedule.startTime2 != null)
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", formatById("Clock_Short_HM", (schedule.startTime2)));
			else	
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
				
			if (schedule.endTime2 != null)
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", formatById("Clock_Short_HM", (schedule.endTime2)));
			else 
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
			
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeCode", schedule.workTypeCode);	
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", schedule.workTimeCode);

			let lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5), totalBrkTime: any = null;
			self.lstBreakSum = [], self.lstAllChildShow = [], self.lstHolidayShort = [];
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workScheduleDto.listBreakTimeZoneDto,
				timeRangeLimit, lstTime, "BREAK", index);
			for (let e = 0; e < dataFixed.workInfoDto.listTimeVacationAndType.length; e++) {
				let y = dataFixed.workInfoDto.listTimeVacationAndType[e];
				lstTime = self.calcChartTypeTime(dataFixed, y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", index);
			}
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workInfoDto.shortTime,
				timeRangeLimit, lstTime, "SHORT", index);

			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", index);
			let totalTime = self.calcAllTime(dataFixed, lstTime, timeRangeLimit);
			totalBrkTime = self.calcAllBrk(lstTime);
			totalBrkTime = totalBrkTime != null ? formatById("Clock_Short_HM", Math.round(totalBrkTime * 5)) : "";

			if (totalBrkTime === (totalBrkTime + " "))
				totalBrkTime = totalBrkTime.trim();
			else
				totalBrkTime = (totalBrkTime + " ");

			if (totalBrkTime === ("0:00") || totalBrkTime === "")
				totalBrkTime = totalBrkTime;
			else
				totalBrkTime = totalBrkTime.trim();

			$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime == totalBrkTime ? totalBrkTime + " " : totalBrkTime);
			
			let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" : 
			"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)";
			
			if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
			$(cssTotalTime).css("background-color", "#ffffff");
			
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime != null ? totalTime : "");
		}

		removeChart(index: number) {
			ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
				type: "Flex",
				options: {
					id: `lgc` + index,
					start: -1000,
					end: -1000,
					lineNo: index
				}
			}]);
		}

		public getData(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			let canModified = 0;
			// 修正可能 - Check ngày có thể chỉnh sửa
			// 対象年月日(A2_1_3)>＝パラメータ.いつから編集可能か.修正可能年月日 
			if ((self.targetDate() >= self.dataFromA().dayEdit)) {
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
				self.initExtableData().done(() => {
					dfd.resolve();
				});
			});

			return dfd.promise();
		}

		// ①<<ScreenQuery>> 初期起動の情報取得
		public getInforFirt() {
			let self = this;
			let dfd = $.Deferred<any>();
			let targetOrgDto = {
				unit: self.dataFromA().unit,
				workplaceId: self.dataFromA().workplaceId,
				workplaceGroupId: self.dataFromA().workplaceGroupId
			}
			service.getDataStartScreen(targetOrgDto)
				.done((data: model.GetInfoInitStartKsu003Dto) => {
					self.dataInitStartKsu003Dto(data);
					self.organizationName(self.dataInitStartKsu003Dto().displayInforOrganization.displayName);
					self.dataScreen003A().targetInfor = data.manageMultiDto.useATR;
					self.timeRange = self.dataInitStartKsu003Dto().byDateDto.dispRange == 0 ? 24 : 48;
					self.initDispStart = self.dataInitStartKsu003Dto().byDateDto.initDispStart;
					self.dispStart = (self.dataInitStartKsu003Dto().byDateDto.dispStart * 60) / 5;
					self.dispStartHours = self.dataInitStartKsu003Dto().byDateDto.dispStart;
					self.dataScreen003A().targetInfor = self.dataInitStartKsu003Dto().manageMultiDto.useATR;
					self.dataScreen003A().scheCorrection = self.dataInitStartKsu003Dto().functionControlDto != null
						? self.dataInitStartKsu003Dto().functionControlDto.changeableWorks : [0,1,2];
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
				});
		}

		// ②<<ScreenQuery>> 日付別勤務情報で表示する
		public getWorkingByDate(targetDate: any, check?: number): JQueryPromise<any> {
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
				date: targetDate
			};
			service.displayDataKsu003(param)
				.done((data: any) => {
					const dataFirt = data;
					if (self.checked() === "0") {
						let dataSort = self.sortEmpByTime(data);
						dataSort = _.sortBy(dataSort, ['startTime' ,'empCode']);
						self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
							return _.findIndex(dataSort, x => { return x.empId == a.empId }) - _.findIndex(dataSort, x => { return x.empId == b.empId });
						});
					}
					data = data.sort(function(a: any, b: any) {
						return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) - _.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
					});
					self.dataScreen003AFirst = dataFirt;
					self.dataScreen003A().employeeInfo = data;

					self.fixedWorkInformationDto = _.map(self.dataScreen003A().employeeInfo, (z) => ({
						empId: z.empId,
						fixedWorkInforDto: z.fixedWorkInforDto
					}))
					self.employeeScheduleInfo = _.map(self.dataScreen003A().employeeInfo, (x) => ({
						empId: x.empId,
						startTime1: x.workScheduleDto != null && x.workScheduleDto.startTime1 != null && x.workScheduleDto.startTime1 != 0 ? x.workScheduleDto.startTime1 : "",
						endTime1: x.workScheduleDto != null && x.workScheduleDto.endTime1 != null && x.workScheduleDto.endTime1 != 0 ? x.workScheduleDto.endTime1 : "",
						startTime2: x.workScheduleDto != null && x.workScheduleDto.startTime2 != null && x.workScheduleDto.startTime2 != 0 ? x.workScheduleDto.startTime2 : "",
						endTime2: x.workScheduleDto != null && x.workScheduleDto.endTime2 != null && x.workScheduleDto.endTime2 != 0 ? x.workScheduleDto.endTime2 : "",
						listBreakTimeZoneDto: x.workScheduleDto != null &&
							x.workScheduleDto.listBreakTimeZoneDto != null ? x.workScheduleDto.listBreakTimeZoneDto : ""
					}));

					self.employeeScheduleInfo = self.employeeScheduleInfo.sort(function(a, b) {
						return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) -
							_.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
					});
					dfd.resolve();
				}).fail(function(error) {
					errorDialog({ messageId: error.messageId });
					dfd.reject();
				}).always(function() {
					block.clear();
				});
			return dfd.promise();
		}

		public getChangeWorkType(columnKey: string, empId: string, index: number) : JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			let targetOrgDto = {
				workTypeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
				workTimeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode
			}
			let cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(3)",
				cssWorkTName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(4)",
				cssStartTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(5)",
				cssEndTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(6)",
				cssStartTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)" : "",
				cssEndTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(8)" : "",
				cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" : 
				"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)",
				cssBreak: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(10)" : 
				"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(8)";
			if (columnKey == "worktypeCode") {
				service.checkWorkType(targetOrgDto).done((data: any) => {
					self.checkNeedTime = data.typeWork;
					if (!_.isNil(data) && data.typeWork == "NOT_REQUIRED") {
						self.enableSave(true);
						self.checkHoliday = true;
							ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
							type: "Flex",
							options: {
								id: `lgc` + index,
								start: -1000,
								end: -1000,
								lineNo: index
							}
						}]);
						
						self.changeWorkType(columnKey,empId,index );
						
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", $("#extable-ksu003").exTable('dataSource', 'middle').body[index].breaktime == "" ? "" : "0:00" );
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
						
						$(cssStartTime1).css("background-color","#DDDDD2");
						$(cssEndTime1).css("background-color","#DDDDD2");
						$(cssStartTime2).css("background-color","#DDDDD2");
						$(cssEndTime2).css("background-color","#DDDDD2");
						$(cssWorkTime).css("background-color","#DDDDD2");
						
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
						
						let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" : 
						"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)";
					
						if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
						$(cssTotalTime).css("background-color", "#ffffff");

						$(".xcell").removeClass("x-error");
					} else if (!_.isNil(data) && data.typeWork == SetupType.REQUIRED) {
						self.checkHoliday = false;
						$(cssWorkTime).removeClass("xseal");
						$(cssStartTime1).removeClass("xseal");
						$(cssEndTime1).removeClass("xseal");
						$(cssStartTime1).css("background-color","#FFFFFF");
						$(cssEndTime1).css("background-color","#FFFFFF");
						
						if($(cssWorkTime).css("background-color") == "rgb(221, 221, 210)")
						$(cssWorkTime).css("background-color","#FFFFFF");
						
						//$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", self.workTypeName == "" ? $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode + getText('KSU003_54') : self.workTypeName);
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "worktimeCode");
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime1");
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime1");
						if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && 
						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 != null) {
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime2");
							$(cssStartTime2).removeClass("xseal");
							$(cssEndTime2).removeClass("xseal");
						if($(cssStartTime2).css("background-color") == "rgb(221, 221, 210)")
						$(cssStartTime2).css("background-color","#FFFFFF");
						
						if($(cssEndTime2).css("background-color") == "rgb(221, 221, 210)")
						$(cssEndTime2).css("background-color","#FFFFFF");
						}
						
						
					} else if (!_.isNil(data) && data.typeWork == SetupType.OPTIONAL){
						self.enableSave(true);
						ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
							type: "Flex",
							options: {
								id: `lgc` + index,
								start: -1000,
								end: -1000,
								lineNo: index
							}
						}]);
						self.check045003 = false;
						//$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", self.workTypeName == "" ? $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode + getText('KSU003_54') : self.workTypeName);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", $("#extable-ksu003").exTable('dataSource', 'middle').body[index].breaktime == "" ? "" : "0:00");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
						
						$(cssWorkTime).removeClass("xseal");
						$(cssStartTime1).removeClass("xseal");
						$(cssEndTime1).removeClass("xseal");
						$(cssStartTime1).css("background-color","#FFFFFF");
						$(cssEndTime1).css("background-color","#FFFFFF");
						$(cssWorkTime).css("background-color","#FFFFFF");
						$(cssWorkTName).css("background-color","#FFFFFF");
						$(cssTotalTime).css("background-color","#FFFFFF");
						$(cssBreak).css("background-color","#FFFFFF");
						
						if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != null && 
						self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[index].workScheduleDto.endTime2 != null) {
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime2");
							$(cssStartTime2).css("background-color","#FFFFFF");
							$(cssEndTime2).css("background-color","#FFFFFF");
							$(cssStartTime2).removeClass("xseal");
							$(cssEndTime2).removeClass("xseal");
						}
						//self.getEmpWorkFixedWorkInfo("worktypeCode", empId, index);
						self.check045003 = true;
						$(".xcell").removeClass("x-error");
					}
					dfd.resolve(data);
				})

			} else {
				dfd.resolve();
			}
			return dfd.promise();
		}
		
		changeWorkType(columnKey: string, empId?: string, index?: number, type?: string): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>(),
			targetOrgDto = {
				workTypeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
				workTimeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode
			};
			
			service.changeWorkType(targetOrgDto).done((data: any) => {
				if(!_.isNil(data))
				self.workTypeName = (data.workTypeName == null || data.workTypeName == "") ? $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode + getText('KSU003_54') : data.workTypeName;
				$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", self.workTypeName);
			})
			
			return dfd.promise();
		}

		// 社員勤務予定と勤務固定情報を取得する
		public getEmpWorkFixedWorkInfo(columnKey: string, empId?: string, index?: number, type?: string): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred<any>(), indexEmp = 0;
			let targetOrgDto = [{
				workTypeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
				workTimeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode
			}]
			indexEmp = _.findIndex(self.dataScreen003A().employeeInfo, x => { return x.empId === empId });
			
			service.getEmpWorkFixedWorkInfo(targetOrgDto)
				.done((data: model.DisplayWorkInfoByDateDto) => {
					self.checkOpenDialog = true;
					$(".xcell").removeClass("x-error");
					if (self.checkNeedTime === "NOT_REQUIRED") $("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
					self.dataScreen003A().employeeInfo[indexEmp].fixedWorkInforDto = data.fixedWorkInforDto;
					self.dataScreen003A().employeeInfo[indexEmp].workScheduleDto = data.workScheduleDto;
					self.fixedWorkInformationDto[indexEmp].fixedWorkInforDto = data.fixedWorkInforDto;
					if (data.fixedWorkInforDto.workType == null) return; // kiểm tra có dữ liệu không

					// ver 2
					self.checkEnableSave = true;
					self.checkEnableWork = true;

					// ver 2
					let checkSort = $("#extable-ksu003").exTable('updatedCells');
					if(checkSort.length > 0 && (_.isNil($("#extable-ksu003").data("errors")) || (!_.isNil($("#extable-ksu003").data("errors")) && $("#extable-ksu003").data("errors").length == 0)) 
					&& self.checkEnableSave == true){
						self.enableSave(true);
					}

					dfd.resolve();
				}).fail(function(error) {
					// ver 2
					/*self.enableSave(false);
					self.checkEnableWork = false;*/
					block.invisible();
					ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
						type: "Flex",
						options: {
							id: `lgc` + index,
							start: -1000,
							end: -1000,
							lineNo: index
						}
					}]);
					
					if(error.messageId == "Msg_591" || error.messageId == "Msg_590"){
						self.checkOpenDialog = false;
					}

					if ((columnKey == "worktimeCode" || (error.messageId != "Msg_434" && columnKey == "worktypeCode")) && self.checkMes != 10) {
						self.checkMes = 0;
						if(self.timesOfInput > 0 && columnKey === "worktypeCode") return;
						
						if(self.timesOfInputTime > 0 && columnKey === "worktimeCode") return;
						if(columnKey === "worktypeCode"){
							self.timesOfInput += 1;
						}
						
						if(columnKey === "worktimeCode"){
							self.timesOfInputTime += 1;
						}
						
						if($("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode == "" && columnKey == "worktimeCode" && error.messageId == "Msg_434"){
							
						}
						errorDialog({ messageId: error.messageId }).then(() => {
							block.clear();
							let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(1)",
								cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(3)";

							if (columnKey == "worktypeCode" && error.messageId != "Msg_29" && error.messageId != "Msg_591") {
								$(cssWorkType).click();
								$(cssWorkType).click();
							}
							// nhập worktypeCode và show ra mess 29 thì focus vào work time code
							if (((columnKey == "worktypeCode" && error.messageId == "Msg_29") || (columnKey == "worktypeCode" && error.messageId == "Msg_591")) && self.checkNeedTime != "OPTIONAL") {
								$(cssWorkTime).click();
								$(cssWorkTime).click();
							}
							
							if (columnKey == "worktimeCode" && self.checkNeedTime != "OPTIONAL")  {
								$(cssWorkTime).click();
								$(cssWorkTime).click();
							}

						});
					}

					if (error.messageId == "Msg_29" && columnKey == "worktypeCode") {
						let color = "";
						if (empId === self.employeeIdLogin) {
							color = "#94b7fe";
						} else {
							color = "#cee6ff";
						}
						let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(1)",
							cssWorkTypeName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(2)";
						$(cssWorkType).css("background-color", color);
						$(cssWorkTypeName).css("background-color", color);
					}

					if (error.messageId == "Msg_434" && columnKey == "worktypeCode") {
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
						targetOrgDto = [{
							workTypeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktypeCode,
							workTimeCode: $("#extable-ksu003").exTable('dataSource', 'middle').body[index].worktimeCode
						}]
						service.getEmpWorkFixedWorkInfo(targetOrgDto).done((data: model.DisplayWorkInfoByDateDto) => {
							self.check045003 = false;
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", data.fixedWorkInforDto.workTypeName);

							$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
							$(".xcell").removeClass("x-error");
							self.check045003 = false;
						})
					}

					if (self.checkMes == 101) {
						self.checkMes = 0;
					}

				}).always(function() {
					block.clear();
				});

			return dfd.promise();
		}

		// 勤務種類を変更する

		public sortEmpByTime(employeeInfo: Array<model.DisplayWorkInfoByDateDto>) {
			let self = this;
			let dataSort = _.map(employeeInfo, (x: model.DisplayWorkInfoByDateDto) => ({
				empId: x.empId,
				empCode : _.filter(self.lstEmpId, y => {return y.empId == x.empId})[0].code,
				startTime: (x.workScheduleDto != null && (x.workScheduleDto.startTime1 != null && x.workScheduleDto.startTime1 != 0)) ? x.workScheduleDto.startTime1 : null
			}))

			return dataSort;
		}

		// 社員を並び替える
		public sortEmployee(value: any) {
			block.grayout();
			let self = this;
			let dataSort = self.sortEmpByTime(self.dataScreen003A().employeeInfo);
			let param = {
				ymd: self.targetDate(),
				lstEmpId: _.map(self.lstEmpId, (x: IEmpidName) => { return { empId: x.empId, code: x.code } })
			}
			if (value === '0') {
				dataSort = _.sortBy(dataSort, ['startTime' ,'empCode']);
				self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
					return _.findIndex(dataSort, x => { return x.empId == a.empId }) - _.findIndex(dataSort, x => { return x.empId == b.empId });
				});
				self.dataScreen003A().employeeInfo = self.dataScreen003A().employeeInfo.sort(function(a: any, b: any) {
					return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) - _.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
				});	
				self.localStore().lstEmpIdSort = self.lstEmpId;
				characteristics.save(self.KEY, self.localStore());
				self.destroyAndCreateGrid(self.lstEmpId, 1);
			} else {
				
				self.lstEmpId = _.flatMap(self.dataFromA().listEmp, c => [{ empId: c.id, name: c.name, code: c.code }]);
				self.dataScreen003A().employeeInfo = self.dataScreen003A().employeeInfo.sort(function(a: any, b: any) {
					return _.findIndex(self.lstEmpId, x => { return x.empId == a.empId }) - _.findIndex(self.lstEmpId, x => { return x.empId == b.empId });
				});		
				self.localStore().lstEmpIdSort = self.lstEmpId;
						characteristics.save(self.KEY, self.localStore());
						self.destroyAndCreateGrid(self.lstEmpId, 1);
				/*service.sortEmployee(param)
					.done((data: Array<model.DisplayWorkInfoByDateDto>) => {
						self.lstEmpId = self.lstEmpId.sort(function(a: any, b: any) {
							return _.findIndex(data, x => { return x == a.empId }) - _.findIndex(data, x => { return x == b.empId });
						});
						self.localStore().lstEmpIdSort = self.lstEmpId;
						characteristics.save(self.KEY, self.localStore());
						self.destroyAndCreateGrid(self.lstEmpId, 1);
					}).fail(function(error) {
						errorDialog({ messageId: error.messageId });
					}).always(function() {
					});*/
			}
			self.localStore().startTimeSort = value;
			characteristics.save(self.KEY, self.localStore());
			block.clear();
		}

		// Tạo data để truyền vào ExTable
		public convertDataIntoExtable(index?: number) {

			let self = this, disableDs: any = [], leftDs: any = [], middleDs: any = [],
				timeGantChart: Array<ITimeGantChart> = [], typeOfTime: string, startTimeArr: any = [], lstTime: any = [];
			let gcFixedWorkTime: Array<model.IFixedFlowFlexTime> = [],
				gcBreakTime: Array<model.IBreakTime> = [],
				gcOverTime: Array<model.IOverTime> = [],
				gcSupportTime: any = null,
				gcFlowTime: Array<model.IFixedFlowFlexTime> = [],
				gcFlexTime: Array<model.IFixedFlowFlexTime> = [],
				gcCoreTime: Array<model.ICoreTime> = [],
				gcHolidayTime: Array<model.IHolidayTime> = [],
				gcShortTime: Array<model.IShortTime> = [];
			_.forEach(_.isNil(index) ? self.dataScreen003A().employeeInfo : [self.dataScreen003A().employeeInfo[index]], (schedule: model.DisplayWorkInfoByDateDto, index) => {
				gcFixedWorkTime = [], gcBreakTime = [], gcOverTime = [], gcSupportTime = null,
					gcFlowTime = [], gcFlexTime = [], gcCoreTime = [], gcHolidayTime = [],
					gcShortTime = [], typeOfTime = "", self.lstBreakSum = [], self.lstHolidayShort = [];
				let lineNo = _.findIndex(self.lstEmpId, (x) => { return x.empId === schedule.empId; });
				let color = self.setColorEmployee(schedule.workInfoDto.isNeedWorkSchedule, schedule.workInfoDto.isCheering);
				let colorA6 = self.setColorWorkingInfo(schedule.empId, schedule.workInfoDto.isConfirmed
					, schedule.workScheduleDto != null ? schedule.workScheduleDto : null, schedule.workInfoDto.isNeedWorkSchedule);
				if (schedule.workInfoDto.isConfirmed == 0 && schedule.workInfoDto.isNeedWorkSchedule == 0) {
					disableDs.push({
						empId: schedule.empId,
						color: "#ddddd2",
					});
				}
				leftDs.push({
					empId: schedule.empId,
					color: color,
					colorA6: colorA6
				});

				let startTime1 = "", startTime2 = "", endTime1 = "", endTime2 = "";
				if (schedule.workScheduleDto != null) {
					startTime1 = schedule.workScheduleDto.startTime1 != null && schedule.workScheduleDto.startTime1 != 0 ? formatById("Clock_Short_HM", schedule.workScheduleDto.startTime1) : "",
						startTime2 = schedule.workScheduleDto.startTime2 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.startTime2) : "",
						endTime1 = schedule.workScheduleDto.endTime1 != null && schedule.workScheduleDto.endTime1 != 0  ? formatById("Clock_Short_HM", schedule.workScheduleDto.endTime1) : "",
						endTime2 = schedule.workScheduleDto.endTime2 != null ? formatById("Clock_Short_HM", schedule.workScheduleDto.endTime2) : "";
				}
				let workTypeName = "", workTimeName = "";
				if (schedule.fixedWorkInforDto != null) {
					if (schedule.fixedWorkInforDto.workTypeName != null && schedule.fixedWorkInforDto.workTypeName != "") {
						workTypeName = schedule.fixedWorkInforDto.workTypeName;
					}
					if (schedule.fixedWorkInforDto.workTimeName != null && schedule.fixedWorkInforDto.workTimeName != "") {
						workTimeName = schedule.fixedWorkInforDto.workTimeName;
					}
				}
				if (schedule.workScheduleDto == null && schedule.fixedWorkInforDto == null) {
					workTimeName = getText('KSU003_55');
				}
				if (schedule.workScheduleDto != null && schedule.fixedWorkInforDto != null) {
					// Bind *worktypecode - *worktimecode
					if (schedule.workScheduleDto.workTypeCode == null || schedule.workScheduleDto.workTypeCode == "") {
						workTypeName = getText('KSU003_55');
					} else if (schedule.workScheduleDto.workTypeCode != null && schedule.workScheduleDto.workTypeCode != "" && schedule.fixedWorkInforDto != null
						&& (schedule.fixedWorkInforDto.workTypeName == null || schedule.fixedWorkInforDto.workTypeName == "")) {
						workTypeName = schedule.workScheduleDto.workTypeCode + getText('KSU003_54')
					}
					// set work time name
					if (schedule.workScheduleDto.workTimeCode == null || schedule.workScheduleDto.workTimeCode == "") {
						workTimeName = getText('KSU003_55');
					} else {
						if (schedule.fixedWorkInforDto != null && (schedule.fixedWorkInforDto.workTimeName == "" || schedule.fixedWorkInforDto.workTimeName == null)) {
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

				// Tính tổng thời gian làm việc
				let lstBreak = 1, lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5);
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
						lstTime = self.calcChartTypeTime(schedule, schedule.workScheduleDto.listBreakTimeZoneDto, timeRangeLimit, lstTime, "BREAK", lineNo);
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
					// Tính tổng thời gian làm thêm
					lstTime = self.calcChartTypeTime(schedule, schedule.fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", lineNo);

					// Thời gian lưu động 
					// 勤務固定情報　dto．Optional<勤務タイプ>== 流動勤務&&社員勤務情報　dto．応援か＝＝時間帯応援元　or 応援ではない
					if (schedule.fixedWorkInforDto.workType == WorkTimeForm.FLOW
						&& (schedule.workInfoDto.isCheering == SupportAtr.TIMEZONE_SUPPORTER || schedule.workInfoDto.isCheering == SupportAtr.NOT_CHEERING)) {
						// 社員勤務予定dto.Optional<開始時刻1>, 社員勤務予定dto.Optional<終了時刻1>
						gcFlowTime.push({
							timeNo: 1,
							empId: schedule.workInfoDto.employeeId,
							workType: schedule.fixedWorkInforDto.workType,
							color: "#ffc000",
							isCheering: schedule.workInfoDto.isCheering,
							startTime: schedule.workScheduleDto.startTime1,
							endTime: schedule.workScheduleDto.endTime1,
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

					// Tính tổng thời gian holiday time
					for (let e = 0; e < schedule.workInfoDto.listTimeVacationAndType.length; e++) {
						let y = schedule.workInfoDto.listTimeVacationAndType[e];
						lstTime = self.calcChartTypeTime(schedule, y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", lineNo);
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
					// Tính tổng thời gian short time
					lstTime = self.calcChartTypeTime(schedule, schedule.workInfoDto.shortTime, timeRangeLimit, lstTime, "SHORT", lineNo);
				}

				// Tổng thời gian break time
				let brkTotal: any = 0;
				brkTotal = self.calcAllBrk(lstTime);

				self.totalTimeWork = self.calcAllTime(schedule, lstTime, timeRangeLimit);
				brkTotal = formatById("Clock_Short_HM", (brkTotal * 5));
				self.sumBreaks.push({ sumBrk: brkTotal });
				if (schedule.workScheduleDto != null) {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'),
						worktypeCode: schedule.workScheduleDto.workTypeCode == null ? "" : schedule.workScheduleDto.workTypeCode,
						worktype: workTypeName,
						worktimeCode: schedule.workScheduleDto.workTimeCode == null ? "" : schedule.workScheduleDto.workTimeCode,
						worktime: workTimeName,
						startTime1: startTime1, endTime1: endTime1,
						startTime2: startTime2, endTime2: endTime2,
						totalTime: self.totalTimeWork, breaktime: brkTotal,
						color: colorA6
					});
				} else {
					middleDs.push({
						empId: schedule.empId, cert: getText('KSU003_22'), worktypeCode: "",
						worktype: workTypeName, worktimeCode: "", worktime: workTimeName,
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
			// tạo dữ liệu để truyền vào init extable
			self.leftDs = leftDs;
			self.disableDs = disableDs;
			self.dataOfGantChart = timeGantChart;
			self.midDataGC = middleDs;
			startTimeArr = _.sortBy(startTimeArr, [function(o: any) { return o; }]);
		}

		public initExtableData(type ?: string) : JQueryPromise<any>{
			let self = this, dfd = $.Deferred();
				if (!_.isEmpty(self.leftDs))
					self.initExtableChart(self.dataOfGantChart, self.leftDs, self.midDataGC, self.disableDs).done(() => {
						dfd.resolve();
					});
					return dfd.promise();
		}

		destroyAndCreateGrid(lstId: any, check: any) {
			let self = this;
			let leftDs: any = [];
			leftDs.push({
				empId: _.map(lstId, (x: IEmpidName) => { return x.empId }),
				color: ""
			});
			$("#extable-ksu003").children().remove();
			$("#extable-ksu003").removeData();
			
			if ((self.targetDate() < self.dataFromA().dayEdit && self.dataFromA().dayEdit !== "1900/01/01") || (self.targetDate() < moment(new Date()).format('YYYY/MM/DD') && self.dataFromA().dayEdit !== "1900/01/01")) {
				self.checkDisByDate = false;
			} else {
				self.checkDisByDate = true;
				self.dataScreen003A().canModified = 1;
			}

			if (check == 1) {
				self.initExtableData();
				self.showHide();
				block.clear();
			}
			else {
				self.getWorkingByDate(self.targetDate(), 1).done(() => {
					self.convertDataIntoExtable();
					self.initExtableData();
					self.showHide();
					block.clear();
				});
			}
		}
		
		setShowHideCell(dataMid : any, checkColor : any, middleContentDeco : any, i : any, disableDSFilter : any, leftContentDeco : any, leftDSFilter : any){
			let self  = this;
			// SET COLOR A6
				if (dataMid.color != "" && dataMid.color !== null && self.dataScreen003A().employeeInfo[i].workInfoDto.isNeedWorkSchedule == 1 && self.checkDisByDate != false) {
					if (checkColor.worktypeCode != 0)
						middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.workTypeColor));

					if (checkColor.worktypeName != 0)
						middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.workTypeColor));

					if (checkColor.worktimeCode != 0)
						middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.workTimeColor));

					if (checkColor.worktimeName != 0)
						middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.workTimeColor));

					if (checkColor.startTime1 != 0)
						middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.startTime1Color));

					if (checkColor.endTime1 != 0)
						middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.endTime1Color));

					if (checkColor.startTime2 != 0){
						
						if(self.dataScreen003A().employeeInfo[i].workScheduleDto != null && self.dataScreen003A().employeeInfo[i].workScheduleDto.startTime2 != null && self.dataScreen003A().employeeInfo[i].workScheduleDto.startTime2 != 0){
							middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.startTime2Color));
						}else {
							if(dataMid.color.workingInfoColor === "#eccefb"){
								middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId,"#eccefb"));
							} else{
								middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId,"xseal"));
							}
						}
					}
						
					if (checkColor.endTime2 != 0){
						if(self.dataScreen003A().employeeInfo[i].workScheduleDto != null && self.dataScreen003A().employeeInfo[i].workScheduleDto.endTime2 != null && self.dataScreen003A().employeeInfo[i].workScheduleDto.endTime2 != 0){
							middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.endTime2Color));
						}else {
							if(dataMid.color.workingInfoColor === "#eccefb"){
								middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId,"#eccefb"));
							} else{
								middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId,"xseal"));
							}
						}
					}

					if (checkColor.breaktime != 0)
						middleContentDeco.push(new CellColor("breaktime", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.breakTimeColor));

					if (checkColor.totalTime != 0)
						middleContentDeco.push(new CellColor("totalTime", self.lstEmpId[i].empId,
							dataMid.color.workingInfoColor === "#eccefb" ? "#eccefb" : dataMid.color.totalTimeColor));
				}

				if (disableDSFilter.length > 0) {
					leftContentDeco.push(new CellColor("empName", disableDSFilter[0].empId, "xseal", leftDSFilter[0].color)); // set màu cho emp name khi bị dis
					leftContentDeco.push(new CellColor("cert", disableDSFilter[0].empId, "xseal", leftDSFilter[0].color));
					middleContentDeco.push(new CellColor("worktypeCode", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("worktypeName", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("worktimeCode", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("worktimeName", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("startTime1", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("endTime1", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("startTime2", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("endTime2", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("totalTime", disableDSFilter[0].empId, "xseal"));
					middleContentDeco.push(new CellColor("breaktime", disableDSFilter[0].empId, "xseal"));
					$("#extable-ksu003").exTable("cellValue", "middle", disableDSFilter[0].empId, "worktimeName", getText('KSU003_55'));
					// set dis for detail DS
					/*for (let z = self.dispStartHours; z <= (displayRange + self.dispStartHours); z++) {
						detailContentDeco.push(new CellColor(z.toString(), disableDSFilter[0].empId, disableDSFilter[0].color));
					}*/
				} else {
					let isNeedWorkSchedule = self.dataScreen003A().employeeInfo[i].workInfoDto.isNeedWorkSchedule,
						canModified = self.dataScreen003A().canModified, // 修正可能
						isConfirmed = self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed,
						lstType = self.dataScreen003A().scheCorrection; // 確定済みか
					// set ẩn hiện A6, A7, A8
					
					if (self.dataScreen003A().employeeInfo[i].workScheduleDto == null || (self.dataScreen003A().employeeInfo[i].workScheduleDto != null &&
						self.dataScreen003A().employeeInfo[i].workScheduleDto.startTime2 == null)) {
						middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
						middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
						checkColor.startTime2 = 0;
						checkColor.endTime2 = 0;
					}
					

					if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto == null || (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null &&
						self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == null && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.isHoliday == false)) {
						middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
						middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
						checkColor.startTime2 = 0;
						checkColor.endTime2 = 0;

						middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal", 0));
						middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal", 0));
						checkColor.startTime1 = 0;
						checkColor.endTime1 = 0;
					}


					if (isNeedWorkSchedule != 1) { // ※2 & ※3
						middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i].empId, "xseal", 0)); // 4 
						middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i].empId, "xseal", 0)); // 4
						middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i].empId, "xseal", 0)); // 4
						checkColor.worktypeCode = 0;
						checkColor.worktypeName = 0;
						checkColor.worktimeName = 0;
						self.lstDis.push({
							empId: self.lstEmpId[i].empId,
							worktypeName: true,
							worktimeName: true
						})

					} else {// [※2]=〇
						if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null &&
							self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != null &&
							self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType === WorkTimeForm.FIXED) { // 10

						}
						if (canModified == 0 || isConfirmed == 1) { // [※3]=〇 ※4 x x 確定済みか
							middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
							checkColor.worktypeCode = 0;
							checkColor.worktypeName = 0;
							checkColor.worktimeName = 0;
							checkColor.worktimeCode = 0;
							checkColor.startTime2 = 0;
							checkColor.endTime2 = 0;
							checkColor.startTime1 = 0;
							checkColor.endTime1 = 0;
							self.lstDis.push({
								empId: self.lstEmpId[i].empId,
								worktypeName: true,
								worktimeName: true
							})

							//self.checkDisByDate = false;
						}

						if (canModified == 1) { // [※4]=〇 // 修正可能
							if (_.isNil(dataMid.worktimeCode) || dataMid.worktimeCode == "") {
								middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId, "xseal", 0)); // ※9 x
								checkColor.worktimeCode = 0;
								self.lstDis.push({
									empId: self.lstEmpId[i].empId,
									worktypeName: false,
									worktimeName: false
								})
							}
							if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != null) {
								if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == 0 || // [※5]=〇
									self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == 1 ||
									self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == 2) {
									if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != null &&
										self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType === WorkTimeForm.FLEX) { // ※7 x
										middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
										middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
										checkColor.startTime2 = 0;
										checkColor.endTime2 = 0;
									}
								} else {
									middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
									middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
									checkColor.startTime2 = 0;
									checkColor.endTime2 = 0;
								}
							}
						} else {

							middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
							checkColor.startTime2 = 0;
							checkColor.endTime2 = 0;

							middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal", 0));
							checkColor.startTime1 = 0;
							checkColor.endTime1 = 0;

							middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId, "xseal", 0)); // ※9 x
							checkColor.worktimeCode = 0;
							self.lstDis.push({
								empId: self.lstEmpId[i].empId,
								worktypeName: false,
								worktimeName: false
							});
						}
						if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null &&
							self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.fixBreakTime === 0) { // ※8
							middleContentDeco.push(new CellColor("breaktime", self.lstEmpId[i].empId, "xseal", 0));
							$("#extable-ksu003").exTable("cellValue", "middle", self.lstEmpId[i].empId, "breakTime", " ");
							checkColor.breaktime = 0;

						}
					}
					
					let fix = _.filter(lstType, (x: any) => { return x === 0 }),
						flex = _.filter(lstType, (x: any) => { return x === 1 }),
						flow = _.filter(lstType, (x: any) => { return x === 2 });
					
					if (self.dataScreen003A().employeeInfo[i].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType != null) {
						if ((fix.length == 0 && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == WorkTimeForm.FIXED) ||
						(flex.length == 0 && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == WorkTimeForm.FLEX) ||
						(flow.length == 0 && self.dataScreen003A().employeeInfo[i].fixedWorkInforDto.workType == WorkTimeForm.FLOW)) {
							middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal", 0));
							checkColor.startTime2 = 0;
							checkColor.endTime2 = 0;
	
							middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal", 0));
							middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal", 0));
							checkColor.startTime1 = 0;
							checkColor.endTime1 = 0;
						}
					}

					if (self.checkDisByDate == false) {
						middleContentDeco.push(new CellColor("worktypeCode", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("worktypeName", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("worktimeCode", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("worktimeName", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("startTime1", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("endTime1", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("startTime2", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("endTime2", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("totalTime", self.lstEmpId[i].empId, "xseal"));
						middleContentDeco.push(new CellColor("breaktime", self.lstEmpId[i].empId, "xseal"));
					}
					
					

					//self.checkDisByDate = true;
					if (isConfirmed == 1) {
						//$("#extable-ksu003 > div.ex-body-leftmost > table > tbody > tr:nth-child" + "(" + (i + 2).toString() + ")" + " > td:nth-child(2) > button").css("pointer-events", "none")
						//leftContentDeco.push(new CellColor("cert", self.lstEmpId[i].empId, "xseal"));
					}
				}

				/*if(self.checkDisByDate == false) {
						for (let z = self.dispStartHours; z <= (displayRange + self.dispStartHours); z++) {
						detailContentDeco.push(new CellColor(z.toString(), self.lstEmpId[i].empId, "#ddddd2"));
						}
				}*/
		}

		// Khởi tạo EXTABLE-GANTCHART
		initExtableChart(timeGantChart: Array<ITimeGantChart>, leftDs: any, midData: any, disableDs: any, type ?: any) : JQueryPromise<any>{
			
			let self = this, dfd = $.Deferred();
			let displayRange = self.timeRange, totalBreakTime = "";

			let middleContentDeco: any = [], leftContentDeco: any = [], detailContentDeco: any = [];

			// phần leftMost
			let leftmostColumns = [],
				leftmostHeader = {},
				leftmostContent = {}, disableDSFilter: any = [];
			
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
				if (!_.isEmpty(leftDSFilter)) {
					leftContentDeco.push(new CellColor("empName", leftDSFilter[0].empId, leftDSFilter[0].color)); // set màu cho emp name
					leftContentDeco.push(new CellColor("cert", leftDSFilter[0].empId, leftDSFilter[0].color));
				}

				let checkColor = ({
					worktypeCode: 1,
					worktypeName: 1,
					worktimeCode: 1,
					worktimeName: 1,
					startTime1: 1,
					endTime1: 1,
					startTime2: 1,
					endTime2: 1,
					totalTime: 1,
					breaktime: 1
				})

				self.setShowHideCell(dataMid, checkColor, middleContentDeco, i, disableDSFilter, leftContentDeco, leftDSFilter);	

				middleDs.push({
					empId: dataMid.empId, worktypeCode: dataMid.worktypeCode,
					worktypeName: dataMid.worktype, worktimeCode: dataMid.worktimeCode, worktimeName: disableDSFilter.length > 0 ? "" : dataMid.worktime,
					startTime1: dataMid.startTime1 == "0:00" ? "" : dataMid.startTime1, endTime1: dataMid.endTime1 == "0:00" ? "" : dataMid.endTime1,
					startTime2: dataMid.startTime2 == "0:00" ? "" : dataMid.startTime2, endTime2: dataMid.endTime2 == "0:00" ? "" : dataMid.endTime2,
					totalTime: dataMid.totalTime, breaktime: totalBreakTime
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
			let detailHeaders : any = {}, startHours = self.dispStartHours, range = displayRange + self.dispStartHours, index = 1;
			let width = "42px", detailColumns : any = [], detailHeaderDs : any = [], detailContent = {}, detailHeader = {};
			// Phần detail
			detailHeaderDs = [{ empId: "" }];
			// A8_2 TQP
			detailColumns = [{ key: "empId", width: "0px", headerText: "ABC", visible: false }];
			
			if (self.dataScreen003A().targetInfor == 1) {
				middleColumns = [
					{
						headerText: getText('KSU003_23'), group: [
							{ headerText: "", key: "worktypeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTypeCode", required: true },
							{
								headerText: "", key: "worktypeName", width: "38px", control: "link", primitiveValue: "WorkTypeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
									self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId, "WorkTypeName");
								}
							}]
					},
					{
						headerText: getText('KSU003_25'), group: [
							{ headerText: "", key: "worktimeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTimeCode", required: true },
							{
								headerText: "", key: "worktimeName", width: "38px", control: "link", primitiveValue: "WorkTimeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
									self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId, "WorkTimeName");
								}
							}]
					},
					{
						headerText: getText('KSU003_27'), group: [
							{ headerText: "", key: "startTime1", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},
					{
						headerText: getText('KSU003_28'), group: [
							{ headerText: "", key: "endTime1", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},
					{
						headerText: getText('KSU003_29'), group: [
							{ headerText: "", key: "startTime2", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},
					{
						headerText: getText('KSU003_30'), group: [
							{ headerText: "", key: "endTime2", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},
					{
						headerText: getText('KSU003_31'), group: [
							{ headerText: "", key: "totalTime", width: "40px", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
					},
					{
						headerText: getText('KSU003_32'), group: [
							{ headerText: "", key: "breaktime", width: "39px", dataType: "duration", primitiveValue: "TimeWithDayAttr" }]
					}
				];
			} else {
				middleColumns = [
					{
						headerText: getText('KSU003_23'), group: [
							{ headerText: "", key: "worktypeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTypeCode", required: true },
							{
								headerText: "", key: "worktypeName", width: "38px", control: "link", primitiveValue: "WorkTypeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
									self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId, "WorkTypeName");
								}
							}]
					},
					{
						headerText: getText('KSU003_25'), group: [
							{ headerText: "", key: "worktimeCode", width: "40px", handlerType: "input", dataType: "text", primitiveValue: "WorkTimeCode", required: true },
							{
								headerText: "", key: "worktimeName", width: "38px", control: "link", primitiveValue: "WorkTimeName", css: { whiteSpace: "pre" }, handler: function(e: any) {
									self.openKdl003Dialog(e.worktypeCode, e.worktimeCode, e.empId, "WorkTimeName");
								}
							}]
					},
					{
						headerText: getText('KSU003_27'), group: [
							{ headerText: "", key: "startTime1", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},
					{
						headerText: getText('KSU003_28'), group: [
							{ headerText: "", key: "endTime1", width: "41px", handlerType: "input", dataType: "duration", primitiveValue: "TimeWithDayAttr", required: true }]
					},

					{
						headerText: getText('KSU003_31'), group: [
							{ headerText: "", key: "totalTime", width: "39px", dataType: "duration", primitiveValue: "TimeWithDayAttr"}]
					},
					{
						headerText: getText('KSU003_32'), group: [
							{ headerText: "", key: "breaktime", width: "39px", dataType: "duration", primitiveValue: "TimeWithDayAttr"}]
					}
				];
			}
			
			middleHeader = {
				columns: middleColumns,
				width: self.dataScreen003A().targetInfor == 1 ? "399px" : "316px",
				rowHeight: "33px"
			};
			middleContent = {
				columns: middleColumns,
				dataSource: middleDs,
				primaryKey: "empId",
				features: [{
					name: "BodyCellStyle",
					decorator: middleContentDeco
				}, {
            name: "Click",
            handler: function(ui : any) {
                if (ui.columnKey == "worktypeName" || ui.columnKey == "worktimeName"){
					self.openKdl003Dialog($("#extable-ksu003").exTable('dataSource', 'middle').body[ui.rowIdx].worktimeCode,
					$("#extable-ksu003").exTable('dataSource', 'middle').body[ui.rowIdx].worktypeCode, self.lstEmpId[ui.rowIdx].empId, ui.columnKey == "worktypeName" ? "WorkTypeName" : "WorkTimeName");
				}
            }
        }]
			};

			for (let y = startHours; y < range; y++) {
				//detailColumns.push({ key: (y).toString() + "_", width: width });
				detailColumns[index++] = { key: (y).toString() + "_", width: width, headerText: "", visible: true  };
				detailHeaderDs[0][y + "_"] = y.toString();
				detailHeaders[y + "_"] = "";
			}

			detailHeader = {
				columns: detailColumns,
				dataSource: detailHeaderDs,
				rowHeight: "33px",
				width: "966px"
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
			let extable = null;
			self.createExtable(leftmostHeader, leftmostContent, middleHeader, middleContent, detailHeader, detailContent, extable).done(() => {
				self.initGantChart(self.dataOfGantChart, self.midDataGC);
				
				$("#hr-row2").css("width", window.innerWidth - 40 + 'px');
			/*if (window.innerWidth == 1280) {
				$("#note-sort").css("margin-left", "1171px");
			}
			if (window.innerWidth == 1320) {
				$("#note-sort").css("margin-left", "1245px");
				if (navigator.userAgent.indexOf("Chrome") == -1) {

				}
			}*/
			if (!_.isNil(self.localStore)) {
				if (self.localStore().operationUnit === "0") {
					ruler.setSnatchInterval(1);
				} else if (self.localStore().operationUnit === "1") {
					ruler.setSnatchInterval(2);
				} else if (self.localStore().operationUnit === "2") {
					ruler.setSnatchInterval(3);
				} else if (self.localStore().operationUnit === "3") {
					ruler.setSnatchInterval(6);
				} else if (self.localStore().operationUnit === "4") {
					ruler.setSnatchInterval(12);
				}
			};

			// set lock slide and resize chart
			//ruler.setLock([0, 1, 3], true);

			// set height grid theo localStorage đã lưu
			self.setPositionButonDownAndHeightGrid();
			
			dfd.resolve();
			});
			return dfd.promise();
		}
		
		createExtable(leftmostHeader : any, leftmostContent : any, middleHeader : any, middleContent : any, detailHeader : any, detailContent : any, extable : any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
			let width = "42px";
			//setTimeout(function(){
				if(detailContent.columns.length < 24 ){
					self.addColumn(1, detailContent, width);
					if(self.timeRange > 24){
						self.addColumn(25, detailContent, width);
					}
				}
				
				
				extable = new exTable.ExTable($("#extable-ksu003"), {
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
				//errorMessagePopup: true,
				windowXOccupation: 40,
				windowYOccupation: 200
			}).LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
				.MiddleHeader(middleHeader).MiddleContent(middleContent)
				.DetailHeader(detailHeader).DetailContent(detailContent);
			
			extable.create();
			ruler = extable.getChartRuler();
			
			console.log(detailContent);
			//}, 10);

			dfd.resolve();
			return dfd.promise();
		}
		
		initGantChart(timeGantChart : any, midData : any){
			let self = this;
			self.addTypeOfChart(ruler);

			let lstBreakTime: any = [], lstTimeChart: any = [], totalTimeBrk = 0;
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
				if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[index].workInfoDto.isConfirmed == 1)
					return;

				let empId = self.lstEmpId[detail.rowIndex].empId, time = null, timeChart: any = null, timeChart2: any = null, timeRangeLimit = ((self.timeRange * 60) / 5);
				lstTimeChart = _.filter(self.allTimeChart, (x: any) => { return x.empId === empId });
				let columnKey = detail.columnKey;

				if (lstTimeChart.length > 0) {
					if (detail.columnKey === "startTime1" || detail.columnKey === "endTime1" || detail.columnKey === "startTime2" || detail.columnKey === "endTime2")
						time = duration.parseString(detail.value).toValue();
					timeChart = lstTimeChart[lstTimeChart.length - 1].timeChart;
					timeChart2 = lstTimeChart[lstTimeChart.length - 1].timeChart2;
					if (detail.columnKey === "startTime1") {
						ruler.extend(detail.rowIndex, `lgc${detail.rowIndex}`, Math.floor((time / 5 - self.dispStart) > timeRangeLimit ? timeRangeLimit : Math.floor((time / 5 - self.dispStart))));
						if (time == "") return;
					} else if (detail.columnKey === "endTime1") {
						ruler.extend(detail.rowIndex, `lgc${detail.rowIndex}`, null, Math.floor((time / 5 - self.dispStart) > timeRangeLimit ? timeRangeLimit : Math.floor((time / 5 - self.dispStart))));
						if (time == "") return;
					} else if (detail.columnKey === "startTime2" && timeChart2 != null) {
						ruler.extend(detail.rowIndex, `rgc${detail.rowIndex}`, Math.floor((time / 5 - self.dispStart) > timeRangeLimit ? timeRangeLimit : Math.floor((time / 5 - self.dispStart))));
						if (time == "") return;
					} else if (detail.columnKey === "endTime2" && timeChart2 != null) {
						ruler.extend(detail.rowIndex, `rgc${detail.rowIndex}`, null, Math.floor((time / 5 - self.dispStart) > timeRangeLimit ? timeRangeLimit : Math.floor((time / 5 - self.dispStart))));
						if (time == "") return;
					}
				}
			};

			$("#extable-ksu003").on("extablecellupdated", (e: any) => {
				recharge(e.detail);
			});
			$("#extable-ksu003").on("extablecellretained", (e: any) => {
				let index: number = e.originalEvent.detail.rowIndex, color = "";
				let dataMid = $("#extable-ksu003").exTable('dataSource', 'middle').body[index];
				let empId = self.lstEmpId[e.originalEvent.detail.rowIndex].empId;
				let dataFixed = _.filter(self.dataScreen003A().employeeInfo, x => { return x.empId === empId }),
					dataFixInfo = _.filter(self.fixedWorkInformationDto, x => { return x.empId === empId });
				if (empId === self.employeeIdLogin) {
					color = "#94b7fe";
				} else {
					color = "#cee6ff";
				}

				if (self.dataScreen003AFirst[index].workScheduleDto.workTypeCode == dataMid.worktypeCode && e.originalEvent.detail.columnKey === "worktypeCode") {
					return;
				}
				
				if (self.dataScreen003AFirst[index].workScheduleDto.workTimeCode == dataMid.worktimeCode && e.originalEvent.detail.columnKey === "worktimeCode") {
					return;
				}


				// 勤務種類を変更する (nhập thủ công worktype code)
				if ((e.originalEvent.detail.columnKey === "worktypeCode" && dataMid.worktypeCode != "")) {

					self.changeWorkType(e.originalEvent.detail.columnKey,empId,index );
					self.bindTypeTime.push({
						index: index,
						value: true
					})
					self.checkUpdateTime.name = "worktypeCode";
					self.checkUpdateTime.id = 2;
					self.inputWorkInfo(dataMid, index, e, dataFixed, empId, e.originalEvent.detail.columnKey);

					// Nếu là ngày nghỉ	
					if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isHoliday != null && dataFixed[0].fixedWorkInforDto.isHoliday == true) {
						self.checkClearTime = false;
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
						ruler.replaceAt(index, [{ // xóa chart khi là ngày nghỉ
							type: "Flex",
							options: {
								id: `lgc` + index,
								start: -1000,
								end: -1000,
								lineNo: index
							}
						}]);
						return;
					}

					// Nếu không phải ngày nghỉ
					if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isHoliday != null && dataFixed[0].fixedWorkInforDto.isHoliday == false) {
						self.checkMes += 1;
					}
				}

				if (e.originalEvent.detail.columnKey === "worktimeCode") {
					self.checkUpdateTime.name = "worktimeCode";
					self.checkUpdateTime.id = 1;
					self.inputWorkInfo(dataMid, index, e, dataFixed, empId, e.originalEvent.detail.columnKey);
					self.bindTypeTime.push({
						index: index,
						value: true
					})
					self.checkMes += 100;
				}

				let columnKey = e.originalEvent.detail.columnKey;
				if ((columnKey === "startTime1" || columnKey === "startTime2" ||
					columnKey === "endTime1" || columnKey === "endTime2") && self.checkUpdateTime.id == 0) {

					self.checkTimeInfo(index, dataMid.worktypeCode, dataMid.worktimeCode, dataMid.startTime1.trim(), dataMid.startTime2.trim(), dataMid.endTime1.trim(), dataMid.endTime2.trim(), columnKey).done(() => {

						let timeConvert = self.convertTime(dataMid.startTime1.trim(), dataMid.endTime1.trim(), dataMid.startTime2.trim(), dataMid.endTime2.trim());
						self.employeeScheduleInfo.forEach((x, i) => {

							if (i === e.originalEvent.detail.rowIndex) {
								if (columnKey === "startTime1") {
									x.startTime1 = timeConvert.start;
									dataFixed[0].workScheduleDto.startTime1 = x.startTime1;
									self.checkTypeChange.push({ type: "startTime1" });
									if (x.startTime1 == "") {
										self.checkMes += 1;
									}
								}
								if (columnKey === "startTime2") {
									x.startTime2 = timeConvert.start2;
									dataFixed[0].workScheduleDto.startTime2 = x.startTime2;
									self.checkTypeChange.push({ type: "startTime2" });
									if (x.startTime2 == "") {
										self.checkMes += 1;

									}
								}
								if (columnKey === "endTime1") {
									x.endTime1 = timeConvert.end;
									dataFixed[0].workScheduleDto.endTime1 = x.endTime1;
									self.checkTypeChange.push({ type: "endTime1" });
									if (x.endTime1 == "") {
										self.checkMes += 1000;

									}
								}
								if (columnKey === "endTime2") {
									x.endTime2 = timeConvert.end2;
									dataFixed[0].workScheduleDto.endTime2 = x.endTime2;
									self.checkTypeChange.push({ type: "endTime2" });
									if (x.endTime2 == "") {
										self.checkMes += 1000;

									}
								}
							}
						})
						if ((timeConvert.start == "" && timeConvert.end != "") || (timeConvert.start != "" && timeConvert.end == "")
							|| (timeConvert.start2 == "" && timeConvert.end2 != "") || (timeConvert.start2 != "" && timeConvert.end2 == ""))
							return;
						// tính lại tổng time
						self.calcSum(index);

					});
				}
				recharge(e.detail);
			});
		}
		
		saveData(type : any) : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.grayout();
            let updatedCells = $("#extable-ksu003").exTable("updatedCells");
            let params = [];

            let dataReg = self.buidDataReg(updatedCells);
            service.regWorkSchedule(dataReg).done((rs : any) => {
                console.log(rs);
                if(rs.hasError == false){
					if(type != 1){
						 nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
							self.enableSave(false);
							let $grid = $('div.ex-body-detail');
							self.updateAfterSaveData($grid[0]).done(() => {
								block.clear();
							});
						});
					} else {
						self.enableSave(false);
							let $grid = $('div.ex-body-detail');
							self.updateAfterSaveData($grid[0]).done(() => {
								block.clear();
							});
					}
                } else {
                    self.openKDL053(rs);
					block.clear();
                }
            }).fail(function(error : any) {
                block.clear();
                alertError(error);
                dfd.reject();
            });
			dfd.resolve();
            return dfd.promise();
        }

		updateAfterSaveData($grid: HTMLElement)  : JQueryPromise<any>{
			let self = this, dfd = $.Deferred();
            // Clear states
            $.data($grid, "copy-history", null);
            $.data($grid, "redo-stack", null);
            $.data($grid, "edit-history", null);
            $.data($grid, "edit-redo-stack", null);
            $.data($grid, "stick-history", null);
            $.data($grid, "stick-redo-stack", null);

            // remove cells updated
            $('#extable-ksu003').data('extable').modifications = null;
			dfd.resolve();
			 return dfd.promise();
        }


		buidDataReg( cellsGroup : any) {
            let self = this;
            let dataReg : any = [];
			let groupData = _.groupBy(cellsGroup, 'rowIndex');
                _.forEach(groupData, function(cells : any) {
                    if (cells.length > 0) {

                        let dataCell : any = {
                            sid: self.dataScreen003A().employeeInfo[cells[0].rowIndex].empId,
                            ymd: self.dataScreen003A().employeeInfo[cells[0].rowIndex].workInfoDto.date,
                            workTypeCd: self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.workTypeCode,
                            workTimeCd: self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.workTimeCode,
                            startTime : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.startTime1,
                            endTime   : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.endTime1,
							startTime2 : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.startTime2,
                            endTime2   : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.endTime2,
							listBreakTime : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workScheduleDto.listBreakTimeZoneDto,
							directAtr : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workInfoDto.directAtr,
							bounceAtr : self.dataScreen003A().employeeInfo[cells[0].rowIndex].workInfoDto.bounceAtr
                        }
                        dataReg.push(dataCell);
                    }
                });
            console.log(dataReg);
            return dataReg;
        }

		/** ADD-CHART-ZONE */
		addAllChart(datafilter: Array<ITimeGantChart>, i: number, lstBreakTime: any, midData: any, screen: string, lstBrkNew?: any) {
			let self = this, fixedGc: any = [], totalBreakTimeNew: any = 0, timeRangeLimit = ((self.timeRange * 60) / 5 + self.dispStart);
			let timeChart: any = null, timeChart2: any = null, lgc = null, rgc = null, timeChartOver: any = null, timeChartCore: any = null,
				timeChartBrk: any = null, timeChartHoliday: any = null, timeChartShort: any = null, indexLeft = 0, indexRight = 0;
			let timeMinus: any = [], timeMinus2: any = [], start1 = null, start2 = null, end1 = null, end2 = null, dispStart = (self.dispStartHours * 60) / 5;
			let fixed = "None", slide = true, isConfirmed = self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed;
			if (self.checkDisByDate == false || isConfirmed == 1) {
				fixed = "Both"
				slide = false
			}

			if (datafilter != null) {
				if (datafilter[0].typeOfTime != "" && datafilter[0].typeOfTime != null) {
					// add chart for FIXED-TIME - thời gian cố định
					if (datafilter[0].typeOfTime === "Fixed" || datafilter[0].gcFixedWorkTime.length > 0) {
						let fixed = datafilter[0].gcFixedWorkTime;
						timeChart = self.convertTimeToChart(fixed[0].startTime, fixed[0].endTime);
						timeMinus.push({
							startTime: fixed[0].startTime,
							endTime: fixed[0].endTime
						})
						let limitTime = self.checkLimitTime(fixed, timeRangeLimit, 0)

						if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && timeMinus[0].startTime < 1440 && timeMinus[0].startTime != null ||
							self.timeRange === 48 && timeMinus[0].startTime < 2880 && timeMinus[0].startTime != null)) {
							start1 = self.checkTimeOfChart(timeChart.startTime, timeRangeLimit);
							end1 = self.checkTimeOfChart(timeChart.endTime, timeRangeLimit);
						}
						let limitStartMin = isConfirmed == 1 ? start1 : limitTime.limitStartMin - dispStart,
							limitStartMax = isConfirmed == 1 ? start1 : start1 - dispStart,
							limitEndMin = isConfirmed == 1 ? end1 : end1 - dispStart,
							limitEndMax = isConfirmed == 1 ? end1 : limitTime.limitEndMax - dispStart;
						if (start1 != null) {
							lgc = ruler.addChartWithType("Fixed", {
								id: `lgc${i}`,
								lineNo: i,
								start: start1 - dispStart,
								end: end1 - dispStart,
								limitStartMin: limitStartMin,
								limitStartMax: limitStartMax,
								limitEndMin: limitEndMin,
								limitEndMax: limitEndMax,
								canSlide: slide,
								fixed: fixed
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "Fixed", `lgc${i}`, { startTime: timeChart.startTime - dispStart, endTime: timeChart.endTime - dispStart }, i, null,
								limitStartMin, limitStartMax, limitEndMin, limitEndMax));
							indexLeft = indexLeft++;
						}

						if (fixed.length > 1 && fixed[1].startTime != null && fixed[1].endTime != null && fixed[1].startTime != 0 && fixed[1].endTime != 0) {
							timeChart2 = self.convertTimeToChart(fixed[1].startTime, fixed[1].endTime);
							timeMinus2.push({
								startTime: fixed[1].startTime,
								endTime: fixed[1].endTime
							})

							if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && timeMinus2[0].startTime < 1440 && timeMinus2[0].startTime != null ||
								self.timeRange === 48 && timeMinus2[0].startTime < 2880 && timeMinus2[0].startTime != null)) {
								start2 = self.checkTimeOfChart(timeChart2.startTime, timeRangeLimit);
								end2 = self.checkTimeOfChart(timeChart2.endTime, timeRangeLimit);
							}
							//lgc = self.addChartWithTypes(ruler, "Fixed", `lgc${i}`, timeChart, i);
							let limitTime = self.checkLimitTime(fixed, timeRangeLimit, 1),
								limitStartMin = isConfirmed == 1 ? start2 : end1 - dispStart,
								limitStartMax = isConfirmed == 1 ? start2 : start2 - dispStart,
								limitEndMin = isConfirmed == 1 ? end2 : end2 - dispStart,
								limitEndMax = isConfirmed == 1 ? end2 : limitTime.limitEndMax - dispStart;
							if (start2 != null) {
								rgc = ruler.addChartWithType("Fixed", {
									id: `rgc${i}`,
									lineNo: i,
									start: start2 - dispStart,
									end: end2 - dispStart,
									limitStartMin: limitStartMin,
									limitStartMax: limitStartMax,
									limitEndMin: limitEndMin,
									limitEndMax: limitEndMax,
									canSlide: slide,
									fixed: fixed
								});
							}

							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "Fixed", `rgc${i}`, { startTime: timeChart2.startTime - dispStart, endTime: timeChart2.endTime - dispStart }, i, null, limitStartMin,
								limitStartMax, limitEndMin, limitEndMax));
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
						let limitTime = self.checkLimitTime(changeable, timeRangeLimit, 0);
						if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && timeMinus[0].startTime < 1440 && timeMinus[0].startTime != null ||
							self.timeRange === 48 && timeMinus[0].startTime < 2880 && timeMinus[0].startTime != null)) {
							start1 = self.checkTimeOfChart(timeChart.startTime, timeRangeLimit);
							end1 = self.checkTimeOfChart(timeChart.endTime, timeRangeLimit);
						}
						
						if (changeable.length > 1 && changeable[1].startTime != null && changeable[1].endTime != null && changeable[1].startTime != 0 && changeable[1].endTime != 0) {
							timeChart2 = self.convertTimeToChart(changeable[1].startTime, changeable[1].endTime);
							if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && changeable[1].startTime < 1440 && changeable[1].startTime != null ||
								self.timeRange === 48 && changeable[1].startTime < 2880 && changeable[1].startTime != null)) {
								start2 = self.checkTimeOfChart(timeChart2.startTime, timeRangeLimit);
								end2 = self.checkTimeOfChart(timeChart2.endTime, timeRangeLimit);
							}
						}

						if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && timeMinus[0].startTime < 1440 && timeMinus[0].startTime != null ||
							self.timeRange === 48 && timeMinus[0].startTime < 2880 && timeMinus[0].startTime != null)) {
							let limitStartMin = isConfirmed == 1 ? start1 : limitTime.limitStartMin - dispStart,
								limitStartMax = isConfirmed == 1 ? start1 : limitTime.limitStartMax - dispStart,
								limitEndMin = isConfirmed == 1 ? end1 : limitTime.limitEndMin - dispStart,
								limitEndMax = isConfirmed == 1 ? end1 : limitTime.limitEndMax - dispStart;
							lgc = ruler.addChartWithType("Changeable", {
								id: `lgc${i}`,
								lineNo: i,
								start: start1 - dispStart,
								end: end1 - dispStart,
								limitStartMin: limitStartMin,
								limitStartMax: limitStartMax,
								limitEndMin: limitEndMin,
								limitEndMax: limitEndMax,
								resizeFinished: (b: any, e: any, p: any) => {
									if (self.checkDisByDate == false)
										return;
								},
								dropFinished: (b: any, e: any) => {
									if (self.checkDisByDate == false)
										return;
								},
								canSlide: slide,
								fixed: fixed
							});

							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "Changeable", `lgc${i}`, { startTime: timeChart.startTime - dispStart, endTime: timeChart.endTime - dispStart }, i, null, limitStartMin, limitStartMax,
								limitEndMin,limitEndMax));
							indexLeft = indexLeft++;
						}

						if (changeable.length > 1 && changeable[1].startTime != null && changeable[1].endTime != null && changeable[1].startTime != 0 && changeable[1].endTime != 0) {
							//timeChart2 = self.convertTimeToChart(changeable[1].startTime, changeable[1].endTime);
							timeMinus2.push({
								startTime: changeable[1].startTime,
								endTime: changeable[1].endTime
							})
							let limitTime = self.checkLimitTime(changeable, timeRangeLimit, 1);

							if (timeMinus[0].startTime < timeMinus[0].endTime && (self.timeRange === 24 && timeMinus2[0].startTime < 1440 && timeMinus2[0].startTime != null ||
								self.timeRange === 48 && timeMinus2[0].startTime < 2880 && timeMinus2[0].startTime != null)) {
								start2 = self.checkTimeOfChart(timeChart2.startTime, timeRangeLimit);
								end2 = self.checkTimeOfChart(timeChart2.endTime, timeRangeLimit);
							}
							let limitStartMin = isConfirmed == 1 ? start2 : limitTime.limitStartMin - dispStart,
									limitStartMax = isConfirmed == 1 ? start2 : limitTime.limitStartMax - dispStart,
									limitEndMin = isConfirmed == 1 ? end2 : limitTime.limitEndMin - dispStart,
									limitEndMax = isConfirmed == 1 ? end2 : limitTime.limitEndMax - dispStart;

							if (start2 != null) {
								rgc = ruler.addChartWithType("Changeable", {
									id: `rgc${i}`,
									lineNo: i,
									start: start2 - dispStart,
									end: end2 - dispStart,
									limitStartMin: limitStartMin,
									limitStartMax: limitStartMax,
									limitEndMin: limitEndMin,
									limitEndMax: limitEndMax,
									resizeFinished: (b: any, e: any, p: any) => {
										if (self.checkDisByDate == false)
											return;
									},
									dropFinished: (b: any, e: any) => {
										if (self.checkDisByDate == false)
											return;
									},
									canSlide: slide,
									fixed: fixed
								});
								fixedGc.push(self.addChartWithType045(datafilter[0].empId, "Changeable", `rgc${i}`, { startTime: timeChart2.startTime - dispStart, endTime: timeChart2.endTime - dispStart }, i, null, 
								limitStartMin, limitStartMax, limitEndMin, limitEndMax));
								indexRight = indexRight++;
							}

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
						let limitTime = self.checkLimitTime(flex, timeRangeLimit, 0),
						limitStartMin = isConfirmed == 1 ? self.checkTimeOfChart(timeChart.startTime, timeRangeLimit) - dispStart : limitTime.limitStartMin - dispStart,
						limitStartMax = isConfirmed == 1 ? self.checkTimeOfChart(timeChart.startTime, timeRangeLimit) - dispStart : limitTime.limitStartMax - dispStart,
						limitEndMin = isConfirmed == 1 ? self.checkTimeOfChart(timeChart.endTime, timeRangeLimit) - dispStart : limitTime.limitEndMin - dispStart,
						limitEndMax = isConfirmed == 1 ? self.checkTimeOfChart(timeChart.endTime, timeRangeLimit) - dispStart : limitTime.limitEndMax - dispStart;
						if (timeMinus[0].startTime < timeMinus[0].endTime && timeChart.startTime < timeRangeLimit) {
							lgc = ruler.addChartWithType("Flex", {
								id: `lgc${i}`,
								lineNo: i,
								start: self.checkTimeOfChart(timeChart.startTime, timeRangeLimit) - dispStart,
								end: self.checkTimeOfChart(timeChart.endTime, timeRangeLimit) - dispStart,
								limitStartMin: limitStartMin,
								limitStartMax: limitStartMax,
								limitEndMin: limitEndMin,
								limitEndMax: limitEndMax,
								resizeFinished: (b: any, e: any, p: any) => {
									if (self.checkDisByDate == false)
										return;
								},
								dropFinished: (b: any, e: any) => {
									if (self.checkDisByDate == false)
										return;
								},
								canSlide: slide,
								fixed: fixed
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "Flex", `lgc${i}`, { startTime: timeChart.startTime - dispStart, endTime: timeChart.endTime - dispStart }, i, null, limitStartMin, limitStartMax, limitEndMin, limitEndMax));
							indexLeft = ++indexLeft;
							// CORE-TIME
							if (coreTime.length > 0 && (_.inRange(coreTime[0].coreStartTime, timeMinus[0].startTime, timeMinus[0].endTime) ||
								_.inRange(coreTime[0].coreEndTime, timeMinus[0].startTime, timeMinus[0].endTime))) {
								ruler.addChartWithType("CoreTime", {
									id: `lgc${i}_` + indexLeft,
									parent: `lgc${i}`,
									lineNo: i,
									start: timeChartCore.startTime - dispStart,
									end: timeChartCore.endTime - dispStart,
									pin: true
								});
								fixedGc.push(self.addChartWithType045(datafilter[0].empId, "CoreTime", `lgc${i}_` + indexLeft, { startTime: timeChartCore.startTime - dispStart, endTime: timeChartCore.endTime - dispStart }, i, `lgc${i}`));
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
				// OVER-TIME
				let overTime = datafilter[0].gcOverTime;
				if (overTime.length > 0 && datafilter[0].typeOfTime !== "Changeable" && datafilter[0].typeOfTime != "Flex") {
					for (let o = 0; o < overTime[0].lstOverTime.length; o++) {
						let y = overTime[0].lstOverTime[o];
						timeChartOver = self.convertTimeToChart(y.startTime, y.endTime);
						let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;
						if ((timeMinus.length > 0 && timeMinus[0].startTime != null && timeMinus[0].endTime != null) &&
							(timeChart.startTime < (timeRangeLimit + self.dispStartHours * 12)) &&
							(_.inRange(timeChartOver.startTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)) ||
								_.inRange(timeChartOver.endTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)))) {
							ruler.addChartWithType("OT", {
								id: id,
								parent: parent,
								lineNo: i,
								start: timeChartOver.startTime - dispStart < dispStart ? dispStart : timeChartOver.startTime - dispStart,
								end: timeChartOver.endTime - dispStart > timeRangeLimit ? timeRangeLimit : timeChartOver.endTime
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "OT", id, { startTime: timeChartOver.startTime - dispStart, endTime: timeChartOver.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1000));
							indexLeft = ++indexLeft;
						}

						if (((timeMinus2.length > 0 && timeMinus2[0].startTime != null && timeMinus2[0].endTime != null) && (timeMinus2.length > 0 && timeMinus2[0].startTime != 0 && timeMinus2[0].endTime != 0))  &&
							(timeChart2.startTime < (timeRangeLimit - self.dispStartHours * 12)) &&
							(_.inRange(timeChartOver.startTime, 0, (timeRangeLimit - self.dispStartHours * 12)) ||
								_.inRange(timeChartOver.endTime, 0, (timeRangeLimit - self.dispStartHours * 12)))) {
							id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
							ruler.addChartWithType("OT", {
								id: id,
								parent: parent,
								lineNo: i,
								start: timeChartOver.startTime - dispStart < dispStart ? dispStart : timeChartOver.startTime - dispStart,
								end: timeChartOver.endTime - dispStart > timeRangeLimit ? timeRangeLimit : timeChartOver.endTime
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "OT", id, { startTime: timeChartOver.startTime - dispStart, endTime: timeChartOver.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1000));
							indexRight = ++indexRight;
						}
					}
				};
				let startTime1 = 0, startTime2 = 0, endTime1 = 0, endTime2 = 0;
				let breakTime: any = [];
				if (datafilter[0].gcBreakTime.length > 0) {
					breakTime = datafilter[0].gcBreakTime[0].lstBreakTime;
					if (screen === "KDL045") {
						breakTime = lstBrkNew;
						datafilter[0].gcBreakTime[0].lstBreakTime = lstBrkNew;
					}
				}

				// Add BREAK-TIME
				if (breakTime.length > 0) {
					for (let o = 0; o < breakTime.length; o++) {
						let y = breakTime[o];
						breakTime = _.sortBy(breakTime, [function(o: any) { return o.start; }])
						timeChartBrk = self.convertTimeToChart(_.isNil(y.startTime) ? y.start : y.startTime, _.isNil(y.endTime) ? y.end : y.endTime);
						let parent = `lgc${i}`;
						startTime1 = self.checkTimeOfChart(timeChartBrk.startTime, timeRangeLimit);
						endTime1 = self.checkTimeOfChart(timeChartBrk.endTime, timeRangeLimit);
						const indexBrks = indexLeft;
						self.allTimeBrk.push(self.addChartWithType045(datafilter[0].empId, "BreakTime", `lgc${i}_` + indexBrks, { startTime: timeChartBrk.startTime - dispStart, endTime: timeChartBrk.endTime - dispStart }, i, parent));
						if ((timeMinus.length > 0 && timeMinus[0].startTime != null && timeMinus[0].endTime != null) &&
							(timeChart.startTime < (timeRangeLimit + self.dispStartHours * 12)) &&
							(_.inRange(timeChartBrk.startTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)) ||
								_.inRange(timeChartBrk.endTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)))) {
							let timeRange = self.checkRangeBreakTime(self.holidayShort, { start: startTime1, end: endTime1 }, i);
							ruler.addChartWithType("BreakTime", {
								id: `lgc${i}_` + indexBrks,
								parent: parent,
								lineNo: i,
								start: startTime1 - dispStart,
								end: endTime1 - dispStart,
								limitStartMin: isConfirmed == 1 ? startTime1 - dispStart : timeRange.start - dispStart,
								limitStartMax: isConfirmed == 1 ? startTime1 - dispStart : timeRange.end - dispStart,
								limitEndMin: isConfirmed == 1 ? endTime1 - dispStart : timeRange.start - dispStart,
								limitEndMax: isConfirmed == 1 ? endTime1 - dispStart : timeRange.end - dispStart,
								zIndex: 1001,
								resizeFinished: (b: any, e: any, p: any) => {
								},
								dropFinished: (b: any, e: any) => {
									self.dropBreakTime(i, indexBrks, b, e, slide, fixed, `lgc${i}_` + indexBrks);
								},
								canSlide: slide,
								fixed: fixed
							});
							lstBreakTime.push({
								startTime: timeChartBrk.startTime,
								endTime: timeChartBrk.endTime,
								id: `lgc${i}_` + indexBrks,
								empId: datafilter[0].empId
							})
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "BreakTime", `lgc${i}_` + indexBrks, { startTime: timeChartBrk.startTime - dispStart, endTime: timeChartBrk.endTime - dispStart }, i, parent, timeRange.start, timeRange.end, timeRange.start, timeRange.end, 1001));
							if ((startTime1 - dispStart) >= (timeChart.startTime - dispStart) && (endTime1 - dispStart) <= (timeChart.endTime - dispStart) && (endTime1 - dispStart) >= (timeChart.startTime - dispStart)) {
								
							}
							indexLeft = ++indexLeft;
						}

						if (datafilter[0].gantCharts === 1) {
							const indexBrkr = indexRight;
							if ((timeMinus2.length > 0 && timeMinus2[0].startTime != null && timeMinus2[0].endTime != null) && (timeMinus2.length > 0 && timeMinus2[0].startTime != 0 && timeMinus2[0].endTime != 0) &&
								(timeChart2.startTime < (timeRangeLimit + self.dispStartHours * 12)) &&
								(_.inRange(timeChartBrk.startTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)) ||
									_.inRange(timeChartBrk.endTime, self.dispStartHours * 12, (timeRangeLimit + self.dispStartHours * 12)))) {
								let timeRange = self.checkRangeBreakTime(self.holidayShort, { start: startTime1, end: endTime1 }, i);
								ruler.addChartWithType("BreakTime", {
									id: `rgc${i}_` + indexBrkr,
									parent: `rgc${i}`,
									lineNo: i,
									start: startTime1 - dispStart,
									end: endTime1 - dispStart,
									limitStartMin: isConfirmed == 1 ? startTime1 - dispStart : timeRange.start - dispStart,
									limitStartMax: isConfirmed == 1 ? startTime1 - dispStart : timeRange.end - dispStart,
									limitEndMin: isConfirmed == 1 ? endTime1 - dispStart : timeRange.start - dispStart,
									limitEndMax: isConfirmed == 1 ? endTime1 - dispStart : timeRange.end - dispStart,
									zIndex: 1001,
									resizeFinished: (b: any, e: any, p: any) => {
									},
									dropFinished: (b: any, e: any) => {
										self.dropBreakTime(i, indexBrks, b, e, slide, fixed, `rgc${i}_` + indexBrkr);
									},
									canSlide: slide,
									fixed: fixed
								});
								lstBreakTime.push({
									startTime: timeChartBrk.startTime,
									endTime: timeChartBrk.endTime,
									id: `rgc${i}_` + indexBrkr,
									empId: datafilter[0].empId
								})
								fixedGc.push(self.addChartWithType045(datafilter[0].empId, "BreakTime", `rgc${i}_` + indexBrkr, { startTime: timeChartBrk.startTime - dispStart, endTime: timeChartBrk.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1001));
								if ((startTime1 - dispStart) >= (timeChart2.startTime - dispStart) && (endTime1 - dispStart) <= (timeChart2.endTime - dispStart) && (endTime1 - dispStart) >= (timeChart2.startTime - dispStart)) {
								}
								indexRight = ++indexRight;
							}
						}

					}
				} else {
					if (datafilter[0].typeOfTime != "" && datafilter[0].typeOfTime != null) {
						$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", "0:00");
					} else {
						$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", "");
						let dataFixed = _.filter(self.dataScreen003A().employeeInfo, x => { return x.empId === datafilter[0].empId });
						if (dataFixed[0].fixedWorkInforDto != null && dataFixed[0].fixedWorkInforDto.isHoliday != null && dataFixed[0].fixedWorkInforDto.isHoliday == true) {
							$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "breaktime", "0:00");
						}
					}
				}

				let shortTime = datafilter[0].gcShortTime;
				if (shortTime.length > 0) {
					for (let o = 0; o < shortTime[0].listShortTime.length; o++) {
						let y = shortTime[0].listShortTime[o];
						timeChartShort = self.convertTimeToChart(y.startTime, y.endTime);
						startTime1 = self.checkTimeOfChart(timeChartShort.startTime, timeRangeLimit);
						endTime1 = self.checkTimeOfChart(timeChartShort.endTime, timeRangeLimit);
						let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;

						if (timeMinus.length > 0 && (_.inRange(y.startTime, timeMinus[0].startTime, timeMinus[0].endTime) ||
							_.inRange(y.endTime, timeMinus[0].startTime, timeMinus[0].endTime))) {
							ruler.addChartWithType("ShortTime", {
								id: id,
								parent: parent,
								lineNo: i,
								start: timeChartShort.startTime - dispStart,
								end: timeChartShort.endTime - dispStart,
								zIndex: 1052
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "ShortTime", id, { startTime: timeChartShort.startTime - dispStart, endTime: timeChartShort.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1052));
							indexLeft = ++indexLeft;
						}

						if (timeMinus2.length > 0 && (_.inRange(y.startTime, timeMinus2[0].startTime, timeMinus2[0].endTime) ||
							_.inRange(y.endTime, timeMinus2[0].startTime, timeMinus2[0].endTime))) {
							id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
							ruler.addChartWithType("ShortTime", {
								id: id,
								parent: parent,
								lineNo: i,
								start: timeChartShort.startTime - dispStart,
								end: timeChartShort.endTime - dispStart,
								zIndex: 1052
							});
							fixedGc.push(self.addChartWithType045(datafilter[0].empId, "ShortTime", id, { startTime: timeChartShort.startTime - dispStart, endTime: timeChartShort.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1052));
							indexRight = ++indexRight;
						}

					}
				}

				let holidayTime = datafilter[0].gcHolidayTime;
				if (holidayTime.length > 0) {
					for (let o = 0; o < holidayTime[0].listTimeVacationAndType.length; o++) {
						let y = holidayTime[0].listTimeVacationAndType[o];
						for (let e = 0; e < y.timeVacation.timeZone.length; e++) {
							let hld = y.timeVacation.timeZone[e];
							timeChartHoliday = self.convertTimeToChart(hld.startTime.time, hld.endTime.time);

							startTime1 = self.checkTimeOfChart(timeChartHoliday.startTime, timeRangeLimit);
							endTime1 = self.checkTimeOfChart(timeChartHoliday.endTime, timeRangeLimit);
							let id = `lgc${i}_` + indexLeft, parent = `lgc${i}`;

							if (timeMinus.length > 0 && (_.inRange(hld.startTime.time, timeMinus[0].startTime, timeMinus[0].endTime) ||
								_.inRange(hld.endTime.time, timeMinus[0].startTime, timeMinus[0].endTime))) {
								if ((self.timeRange === 24 && hld.startTime.time < 1440 || self.timeRange === 48 && hld.endTime.time < 2880)) {
									ruler.addChartWithType("HolidayTime", {
										id: id,
										parent: parent,
										lineNo: i,
										start: timeChartHoliday.startTime - dispStart,
										end: timeChartHoliday.endTime - dispStart,
										zIndex: 1103
									});
									fixedGc.push(self.addChartWithType045(datafilter[0].empId, "HolidayTime", id, { startTime: timeChartHoliday.startTime - dispStart, endTime: timeChartHoliday.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1103));
									indexLeft = ++indexLeft;
								}
							}

							if (timeMinus2.length > 0 && (_.inRange(hld.startTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime) ||
								_.inRange(hld.endTime.time, timeMinus2[0].startTime, timeMinus2[0].endTime))) {
								id = `rgc${i}_` + indexRight, parent = `rgc${i}`;
								if ((self.timeRange === 24 && hld.startTime.time < 1440 || self.timeRange === 48 && hld.endTime.time < 2880)) {
									ruler.addChartWithType("HolidayTime", {
										id: id,
										parent: parent,
										lineNo: i,
										start: timeChartHoliday.startTime - dispStart,
										end: timeChartHoliday.endTime - dispStart,
										zIndex: 1103
									});
									fixedGc.push(self.addChartWithType045(datafilter[0].empId, "HolidayTime", id, { startTime: timeChartHoliday.startTime - dispStart, endTime: timeChartHoliday.endTime - dispStart }, i, parent, 0, 9999, 0, 9999, 1103));
									indexRight = ++indexRight;
								}
							}
						}
					}
				}
			}

			// thay đổi giá trị khi kéo thanh chart
			$(lgc).on("gcresize", (e: any) => {
				if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed == 1)
					return;
				self.enableSave(true);
				let param = e.detail;
				let startMinute = 0, endMinute = 0;
				self.checkDragDrog = false;
				startMinute = duration.create(param[0] * 5 + self.dispStart * 5).text;
				endMinute = duration.create(param[1] * 5 + self.dispStart * 5).text;
				if (param[2]) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime1", startMinute);
				} else {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime1", endMinute);
				}
				self.checkTypeChange = [];
			});

			$(rgc).on("gcresize", (e: any) => {
				if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed == 1)
					return;
				self.enableSave(true);
				let param = e.detail;
				let startMinute = 0, endMinute = 0;
				self.checkDragDrog = false;
				startMinute = duration.create(param[0] * 5 + self.dispStart * 5).text;
				endMinute = duration.create(param[1] * 5 + self.dispStart * 5).text;
				if (param[2]) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime2", startMinute);
				} else {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime2", endMinute);
				}
				self.checkTypeChange = [];
			});

			$(lgc).on("gcdrop", (e: any) => {
				if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed == 1)
					return;
				self.enableSave(true);
				let param = e.detail, startMinute = 0, endMinute = 0;
				startMinute = duration.create(param[0] * 5 + self.dispStart * 5).text;
				endMinute = duration.create(param[1] * 5 + self.dispStart * 5).text;
				self.checkDragDrog = true;
				let index = e.currentTarget.id.split("-")[0], lstGcShow = _.filter(self.lstAllChildShow, (x: any) => { return x.index === parseInt(index) });

				self.checkChartHide(lstGcShow, index, param, "lgc");

				if (param) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime1", startMinute);
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime1", endMinute);
				}
				self.checkTypeChange = [];
			});

			$(rgc).on("gcdrop", (e: any) => {
				if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[i].workInfoDto.isConfirmed == 1)
					return;
				self.enableSave(true);
				let param = e.detail, startMinute = 0, endMinute = 0;
				startMinute = duration.create(param[0] * 5 + self.dispStart * 5).text;
				endMinute = duration.create(param[1] * 5 + self.dispStart * 5).text;
				self.checkDragDrog = true;
				let index = e.currentTarget.id.split("-")[0], lstGcShow = _.filter(self.lstAllChildShow, (x: any) => { return x.index === parseInt(index) });

				self.checkChartHide(lstGcShow, index, param, "rgc");

				if (param) {
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "startTime2", startMinute);
					$("#extable-ksu003").exTable("cellValue", "middle", datafilter[0].empId, "endTime2", endMinute);
				}
				self.checkTypeChange = [];
			});

			self.allGcShow = fixedGc;
		}
		
		dropBreakTime(i : any, indexBrks : any, b : any, e : any, slide : any, fixed : any,id : any){
			let self = this;
			let idNew = indexBrks.length > 5 ? indexBrks : `lgc${i}_` + indexBrks;
			self.enableSave(true);
			let breakChange = _.filter(self.allTimeBrk, (x: any) => { return x.options.lineNo === i });
			breakChange = _.sortBy(breakChange, [function(o: any) { return o.options.start; }]);
			let indexBrk = _.findIndex(breakChange, (x: any) => { return x.options.id === (idNew) });
			if(breakChange[indexBrk].options.start == b + self.dispStart && breakChange[indexBrk].options.end == e + self.dispStart)
			return;
			
			breakChange[indexBrk].options.start = b;
			breakChange[indexBrk].options.end = e;
			self.dataScreen003A().employeeInfo[i].workScheduleDto.listBreakTimeZoneDto[indexBrk].start = b * 5 + self.dispStart * 5;
			self.dataScreen003A().employeeInfo[i].workScheduleDto.listBreakTimeZoneDto[indexBrk].end = e * 5 + self.dispStart * 5;
			$("#extable-ksu003").exTable("cellValue", "middle", self.dataScreen003A().employeeInfo[i].empId, "breaktime", $("#extable-ksu003").exTable('dataSource', 'middle').body[i].breaktime + " "); // + " " để phân biệt khi thay đổi vị trí nhưng không thay đổi giá trị
			let dataFixed = self.dataScreen003A().employeeInfo[i];
			let lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5), totalBrkTime: any = null;
			self.lstBreakSum = [], self.lstAllChildShow = [], self.lstHolidayShort = [];
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workScheduleDto.listBreakTimeZoneDto,
				timeRangeLimit, lstTime, "BREAK", i);
			for (let e = 0; e < dataFixed.workInfoDto.listTimeVacationAndType.length; e++) {
				let y = dataFixed.workInfoDto.listTimeVacationAndType[e];
				lstTime = self.calcChartTypeTime(dataFixed, y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", i);
			}
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workInfoDto.shortTime,
				timeRangeLimit, lstTime, "SHORT", i);

			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", i);
			let totalTime = self.calcAllTime(dataFixed, lstTime, timeRangeLimit);

			let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (i + 2).toString() + ")" + " > td:nth-child(9)" : 
			"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (i + 2).toString() + ")" + " > td:nth-child(7)";
			
			if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
			$(cssTotalTime).css("background-color", "#ffffff");
			
			$("#extable-ksu003").exTable("cellValue", "middle", self.lstEmpId[i].empId, "totalTime", totalTime != null ? totalTime : "");
		}

		checkChartHide(lstGcShow: any, index: any, param: any, type: string) {
			let self = this;
			let timeMinus = duration.create(param[0] * 5 + self.dispStart * 5).asMinutes - self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime1;
			if(type == "rgc"){
				timeMinus = duration.create(param[0] * 5 + self.dispStart * 5).asMinutes - self.dataScreen003A().employeeInfo[index].workScheduleDto.startTime2;
			}
			if (self.dataScreen003A().employeeInfo[index].workScheduleDto.listBreakTimeZoneDto.length > 0) {
				_.forEach(self.dataScreen003A().employeeInfo[index].workScheduleDto.listBreakTimeZoneDto, (brk: any, idx) => {
					self.dataScreen003A().employeeInfo[index].workScheduleDto.listBreakTimeZoneDto[idx].start += timeMinus;
					self.dataScreen003A().employeeInfo[index].workScheduleDto.listBreakTimeZoneDto[idx].end += timeMinus;
				});
			}

			if (self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.overtimeHours.length > 0) {
				_.forEach(self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.overtimeHours, (brk: any, idx) => {
					self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.overtimeHours[idx].startTime += timeMinus;
					self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.overtimeHours[idx].endTime += timeMinus;
				});
			}

			if (self.dataScreen003A().employeeInfo[index].workInfoDto.shortTime.length > 0) {
				if (self.dataScreen003A().employeeInfo[index].workInfoDto.shortTime.length > 0) {
					_.forEach(self.dataScreen003A().employeeInfo[index].workInfoDto.shortTime, (brk: any, idx) => {
						self.dataScreen003A().employeeInfo[index].workInfoDto.shortTime[idx].startTime += timeMinus;
						self.dataScreen003A().employeeInfo[index].workInfoDto.shortTime[idx].endTime += timeMinus;
					});
				}
			}

			if (self.dataScreen003A().employeeInfo[index].workInfoDto.listTimeVacationAndType.length > 0) {
				_.forEach(self.dataScreen003A().employeeInfo[index].workInfoDto.listTimeVacationAndType, (brks: any, idxs) => {
					_.forEach(self.dataScreen003A().employeeInfo[index].workInfoDto.listTimeVacationAndType[idxs].timeVacation.timeZone, (brk: any, idx) => {
						self.dataScreen003A().employeeInfo[index].workInfoDto.listTimeVacationAndType[idxs].timeVacation.timeZone[idx].startTime.time += timeMinus;
						self.dataScreen003A().employeeInfo[index].workInfoDto.listTimeVacationAndType[idxs].timeVacation.timeZone[idx].endTime.time += timeMinus;
					})
				})
			}
		}

		// Kiểm tra giới hạn của thời gian 
		checkLimitTime(time: any, timeRangeLimit: any, index: number) {
			let self = this;
			let timeLimit = null, limitStartMin = 0, limitStartMax = timeRangeLimit, limitEndMin = 0, limitEndMax = timeRangeLimit;
			let dispStart = (self.dispStartHours * 60) / 5;
			if (time[index].startTimeRange != null) {
				if (index > 0) {
					limitStartMin = time[index].endTime;
				}
				limitStartMin = self.checkRangeLimitTime(time, timeRangeLimit, 1, 0) ? ((time[index].startTimeRange.startTime.time) / 5) : self.dispStartHours;
				limitStartMax = self.checkRangeLimitTime(time, timeRangeLimit, 2, 0) ? ((time[index].startTimeRange.endTime.time) / 5) : timeRangeLimit;
				if (time[index].startTimeRange.startTime.time <= self.dispStartHours) {
					limitStartMin = self.dispStartHours;
				}
				if (time[index].startTimeRange.endTime.time <= self.dispStartHours) {
					limitStartMax = self.dispStartHours;
				}
			} else if (time[0].startTimeRange != null) {

				limitStartMin = ((time[0].startTimeRange.startTime.time) / 5)
			}

			if (time[index].endTimeRange != null) {
				limitEndMin = self.checkRangeLimitTime(time, timeRangeLimit, 3, 0) ? ((time[index].endTimeRange.startTime.time) / 5) : timeRangeLimit;
				limitEndMax = self.checkRangeLimitTime(time, timeRangeLimit, 4, 0) ? ((time[index].endTimeRange.endTime.time) / 5) : timeRangeLimit;
				if (time[index].endTimeRange.startTime.time <= self.dispStartHours) {
					limitEndMin = self.dispStartHours;
				}
				if (time[index].endTimeRange.endTime.time <= self.dispStartHours) {
					limitEndMax = self.dispStartHours;
				}
			}

			timeLimit = {
				limitStartMin: limitStartMin < dispStart ? dispStart : limitStartMin,
				limitStartMax: limitStartMax < dispStart ? dispStart : limitStartMax,
				limitEndMin: limitEndMin < dispStart ? dispStart : limitEndMin,
				limitEndMax: limitEndMax < dispStart ? dispStart : limitEndMax
			}
			return timeLimit;
		}

		checkTimeOfChart(time: any, timeRangeLimit: any) {
			// check start time
			let self = this;
			if (time > timeRangeLimit) {
				time = timeRangeLimit;
			}

			if (time < ((self.dispStartHours * 60) / 5)) {
				time = ((self.dispStartHours * 60) / 5);
			}

			return time;
		}

		checkRangeBreakTime(lstHoliday: any[], breakTime: any, index: any) {
			let rangeBreak: any = { start: 0, end: 9999 };
			lstHoliday = _.filter(lstHoliday, x => { return x.index === index });

			for (let i = 0; i < lstHoliday.length; i++) {
				if (lstHoliday[i].start > breakTime.end) {
					rangeBreak.end = lstHoliday[i].start;
				}
				if (lstHoliday[i].end < breakTime.start) {
					rangeBreak.start = lstHoliday[i].end;
				}
			}
			return rangeBreak;
		}

		// tạo tại chart
		addChartWithType045(empId: string, type: any, id: any, timeChart: any, lineNo: any, parent?: any,
			limitStartMin?: any, limitStartMax?: any, limitEndMin?: any, limitEndMax?: any, zIndex?: any) {
			let self = this, timeEnd = self.convertTimePixel(self.timeRange === 24 ? "24:00" : "48:00");
			let fixed = "None", canSlide = false, pin =false;
			
			if(type == "Fixed" || type == "Changeable" || type == "Flex"){
				if (self.checkDisByDate == false) {
				fixed = "Both"
				}
			}
			
			if(type == "BreakTime" || type == "Changeable" || type == "Flex"){
				canSlide = self.checkDisByDate == false ? false : true;
			} 
			
			if(type == "CoreTime"){
				pin = true;
			} 
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
					zIndex: !_.isNil(zIndex) ? zIndex : 1000,
					canSlide : canSlide,
					fixed : fixed,
					pin : pin,
					resizeFinished: (b: any, e: any, p: any) => {
						
						if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[lineNo].workInfoDto.isConfirmed == 1) return;
						self.enableSave(true);
						self.checkDragDrog = false;
						if (!_.isNil(id) && _.includes(id, 'lgc')) {
							if (p == true)
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", formatById("Clock_Short_HM", (b) * 5));
							else
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", formatById("Clock_Short_HM", (e) * 5));
						} else {
							if (p == true)
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", formatById("Clock_Short_HM", (b) * 5));
							else
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", formatById("Clock_Short_HM", (e) * 5));
						}
						self.checkTypeChange = [];
					},
					// kéo lại chart
					dropFinished: (b: any, e: any) => {
						let fixed = "None", slide = true, isConfirmed = self.dataScreen003A().employeeInfo[lineNo].workInfoDto.isConfirmed;
						self.enableSave(true);
						if (self.checkDisByDate == false || isConfirmed == 1) {
							fixed = "Both"
							slide = false
						}
						if (_.includes(id, '_')) {
							let indexBrks = 0;
							self.dropBreakTime(lineNo, id, b, e, slide, fixed, id);
							return;
						};
						if (self.checkDisByDate == false || self.dataScreen003A().employeeInfo[lineNo].workInfoDto.isConfirmed == 1) return;
						
						if (_.includes(id, 'lgc')) {
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", formatById("Clock_Short_HM", (b) * 5));
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", formatById("Clock_Short_HM", (e) * 5));
						} else {
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", formatById("Clock_Short_HM", (b) * 5));
							$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", formatById("Clock_Short_HM", (e) * 5));	
						}
							
						let param = {0 : b, 1 : e}, startMinute = 0, endMinute = 0;
						startMinute = duration.create(param[0] * 5 + self.dispStart * 5).text;
						endMinute = duration.create(param[1] * 5 + self.dispStart * 5).text;
						self.checkDragDrog = true;
						self.checkDragDrogAgain = true;
						let index = lineNo, lstGcShow = _.filter(self.lstAllChildShow, (x: any) => { return x.index === parseInt(index) });
						if (_.includes(id, 'lgc')) {
							self.checkChartHide(lstGcShow, index, param, "lgc");
							if (param) {
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", startMinute);
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", endMinute);
							}
							self.checkTypeChange = [];
						} else {

							self.checkChartHide(lstGcShow, index, param, "rgc");
							if (param) {
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", startMinute);
								$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", endMinute);
							}
							self.checkTypeChange = [];
						}
					}
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
			let self = this, fixed = "None";
			if (self.checkDisByDate == false) {
				fixed = "Both"
			}

			ruler.addType({
				name: "Fixed",
				color: "#ccccff",
				lineWidth: 30,
				canSlide: false,
				unitToPx: self.operationUnit(),
				fixed: fixed
			});

			ruler.addType({
				name: "Changeable",
				color: "#ffc000",
				lineWidth: 30,
				canSlide: self.checkDisByDate == false ? false : true,
				unitToPx: self.operationUnit(),
				fixed: fixed
			});

			ruler.addType({
				name: "Flex",
				color: "#ccccff",
				lineWidth: 30,
				canSlide: self.checkDisByDate == false ? false : true,
				unitToPx: self.operationUnit(),
				fixed: fixed
			});

			ruler.addType({
				name: "BreakTime",
				followParent: true,
				color: "#ff9999",
				lineWidth: 30,
				canSlide: self.checkDisByDate == false ? false : true,
				unitToPx: self.operationUnit(),
				pin: true,
				rollup: true,
				roundEdge: true,
				fixed: "Both",
				bePassedThrough: false
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
				followParent: true, color: "#c4bd97",
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
				pin : true // cố định gant chart
			});
		}

		// Kiểm tra giới hạn hiển thị của thanh chart
		checkRangeLimitTime(flex: any, timeRangeLimit: number, type: number, index: number) {
			let check = false, self = this;

			if (flex.length > 0) {
				if (type == 1 && flex[index].startTimeRange != null && ((flex[index].startTimeRange.startTime.time - self.dispStartHours * 60) / 5) <= timeRangeLimit) {
					check = true;
				}
				if (type == 2 && flex[index].startTimeRange != null && ((flex[index].startTimeRange.endTime.time - self.dispStartHours * 60) / 5) < timeRangeLimit) {
					check = true;
				}
				if (type == 3 && flex[index].endTimeRange != null && ((flex[index].endTimeRange.startTime.time - self.dispStartHours * 60) / 5) <= timeRangeLimit) {
					check = true;
				}
				if (type == 4 && flex[index].endTimeRange != null && ((flex[index].endTimeRange.endTime.time - self.dispStartHours * 60) / 5) < timeRangeLimit) {
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
			return "";
		}
		checkColorA6(workInfo: any) {
			let workTypeColor = "";
			if (workInfo === EditStateSetting.HAND_CORRECTION_MYSELF) {
				workTypeColor = "#94b7fe"
			} else if (workInfo === EditStateSetting.HAND_CORRECTION_OTHER) {
				workTypeColor = "#cee6ff"
			} else if (workInfo === EditStateSetting.REFLECT_APPLICATION) {
				workTypeColor = "#bfea60"
			}
			return workTypeColor;
		}
		/** A6_color① - chua xong o phan login va xin bang don */
		setColorWorkingInfo(empId: string, isConfirmed: number, workScheduleDto: any, isNeedWorkSchedule: any) {
			let self = this, workTypeColor = "", workTimeColor = "", startTime1Color = "", endTime1Color = "",
				startTime2Color = "", endTime2Color = "", breakTimeColor = "", workingInfoColor = "";

			if (isConfirmed == 0 && isNeedWorkSchedule == 0) {
				workingInfoColor = '#ddddd2';
			}
			if (isConfirmed == 1) {
				workingInfoColor = '#eccefb';
			}
			if (workScheduleDto != null) {
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
					breakTimeColor = self.checkColorA6(workScheduleDto.breakTimeStatus);
				}
			}

			return ({
				workTypeColor: workTypeColor,
				workTimeColor: workTimeColor,
				startTime1Color: startTime1Color,
				endTime1Color: endTime1Color,
				startTime2Color: startTime2Color,
				endTime2Color: endTime2Color,
				breakTimeColor: breakTimeColor,
				workingInfoColor: workingInfoColor

			});
		}

		setPositionButonDownAndHeightGrid() {
			let self = this;

			$("#extable-ksu003").exTable("setHeight", 10 * 30 + 18);
			$(".toDown").css({ "margin-top": 10 * 30 + 8 + 'px' });

			$("#extable-ksu003").exTable("scrollBack", 0, { h: (self.initDispStart * 42 - self.dispStart * 3.5) });
			//$("#functon-area-row2-left").focus();
		}

		toLeft() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (self.showA9) {
					$("#extable-ksu003").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", 193 + 'px');
				if (window.innerWidth >= 1320) {
					$(".toLeft").css('margin-left', 188 + 'px');
				}
				if (window.innerHeight < 700) {
					$(".ex-header-detail").css({ "width": 966 + 'px' });
					$(".ex-body-detail").css({ "width": 983 + 'px' });
				}

			} else {
				if (self.showA9) {
					$("#extable-ksu003").exTable("showMiddle");
				}
				$(".ex-header-middle").css("width", 560 + 'px' + '!important')
				if (window.innerWidth >= 1320) {
					$(".toLeft").css('margin-left', 587 + 'px');
					if (self.dataScreen003A().targetInfor == 0) {
						$(".toLeft").css("margin-left", 504 + 'px');
					}
				} else {
					$(".toLeft").css("margin-left", 592 + 'px');
					if (self.dataScreen003A().targetInfor == 0) {
						$(".toLeft").css("margin-left", 509 + 'px');
					}
				}
				if (window.innerHeight < 700) {
					$(".ex-header-detail").css({ "width": 588 + 'px' });
					$(".ex-body-detail").css({ "width": 604 + 'px' });
					if (self.dataScreen003A().targetInfor == 0) {
						$(".ex-header-detail").css({ "width": 672 + 'px' });
						$(".ex-body-detail").css({ "width": 689 + 'px' });
					}
				}
			}

			self.indexBtnToLeft(self.indexBtnToLeft() + 1);
			self.localStore().showHide = self.indexBtnToLeft();
			characteristics.save(self.KEY, self.localStore());
			$("#extable-ksu003").exTable("scrollBack", 0, { h: (self.initDispStart * 42 - self.dispStart * 3.5) });
		}

		toDown() {
			let self = this;
			let exTableHeight = window.innerHeight - 92 - 31 - 50 - 50 - 35 - 27 - 27 - 20;
			exTableHeight = 17 * 30 + 18;
			$("#master-wrapper").css({ 'overflow-x': 'hidden' });
			if (self.indexBtnToDown() % 2 == 0) {
				$("#extable-ksu003").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');
				$("#master-wrapper").css({ 'overflow-y': 'auto' });
				$("#master-wrapper").css({ 'overflow-x': 'hidden' });
				$("#contents-area").css({ 'overflow-x': 'hidden' });
				if (window.innerWidth >= 1320) {
					$("#A1_4").css("margin-right", "0px")
					$("#note-color").css("margin-right", "18px")
					$("#hr-row2").css("width", "1264px")
				}
				/*if (window.innerWidth < 1320) {
					$("#note-sort").css("margin-left", "1158px");
				}*/
			} else {
				exTableHeight = 10 * 30 + 18;
				$("#extable-ksu003").exTable("setHeight", exTableHeight);
				$(".toDown").css('margin-top', exTableHeight - 8 + 'px');
				$("#contents-area").css({ 'overflow-x': 'hidden' });

				if (navigator.userAgent.indexOf("Chrome") == -1) {
					$("#master-wrapper").css({ 'overflow-y': 'hidden' });
				}

				if (window.innerWidth >= 1320) {
					$("#A1_4").css("margin-right", "20px")
					$("#note-color").css("margin-right", "35px")
				}

				/*if (window.innerWidth < 1320) {
					$("#note-sort").css("margin-left", "1171px");
				}*/
			}
			self.indexBtnToDown(self.indexBtnToDown() + 1);
		}

		showHide() {
			let self = this;
			if (self.indexBtnToLeft() % 2 == 0) {
				if (!self.showA9) {
					$("#extable-ksu003").exTable("showMiddle");
				}
				if (window.innerWidth >= 1320) {
					$(".toLeft").css('margin-left', 587 + 'px');
					if (self.dataScreen003A().targetInfor == 0) {
						$(".toLeft").css("margin-left", 504 + 'px');
					}
				} else {
					$(".toLeft").css("margin-left", 592 + 'px');
					if (self.dataScreen003A().targetInfor == 0) {
						$(".toLeft").css("margin-left", 509 + 'px');
					}
				}
			} else {
				if (self.showA9) {
					$("#extable-ksu003").exTable("hideMiddle");
				}
				$(".toLeft").css("margin-left", 193 + 'px');
				if (window.innerWidth >= 1320) {
					$(".toLeft").css('margin-left', 188 + 'px');
				}
			}

			if (window.innerHeight < 700) {
				if (window.innerWidth < 1320) {
					if (self.indexBtnToLeft() % 2 == 0) {
						$(".ex-header-detail").css({ "width": 588 + 'px' });
						$(".ex-body-detail").css({ "width": 604 + 'px' });
						if (self.dataScreen003A().targetInfor == 0) {
							$(".ex-header-detail").css({ "width": 672 + 'px' });
							$(".ex-body-detail").css({ "width": 689 + 'px' });
						}
					} else {
						$(".ex-header-detail").css({ "width": 966 + 'px' });
						$(".ex-body-detail").css({ "width": 983 + 'px' });
					}

					$("#label-display").css("margin-left", 55 + 'px');
					$(".toDown").css('margin-left', 0 + 'px');
					if (navigator.userAgent.indexOf("Chrome") == -1) {
						$(".toDown").css('margin-left', 0 + 'px');
					}
				}
			} else {
				$(".toDown").css('margin-left', -5 + 'px');
			}
			//$("#functon-area-row2-left").focus();
		}

		// tính tổng tất cả các loại thời gian
		calcSum(index: number, dataFixed?: any) {
			let self = this;
			if (_.isNil(dataFixed))
				dataFixed = self.dataScreen003A().employeeInfo[index];

			let empId = dataFixed.empId;

			let lstTime: any = [], timeRangeLimit = ((self.timeRange * 60) / 5), totalBrkTime: any = null;
			self.lstBreakSum = [], self.lstAllChildShow = [], self.lstHolidayShort = [];
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workScheduleDto.listBreakTimeZoneDto,
				timeRangeLimit, lstTime, "BREAK", index);
			for (let e = 0; e < dataFixed.workInfoDto.listTimeVacationAndType.length; e++) {
				let y = dataFixed.workInfoDto.listTimeVacationAndType[e];
				lstTime = self.calcChartTypeTime(dataFixed, y.timeVacation.timeZone, timeRangeLimit, lstTime, "HOLIDAY", index);
			}
			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.workInfoDto.shortTime,
				timeRangeLimit, lstTime, "SHORT", index);

			lstTime = self.calcChartTypeTime(dataFixed, dataFixed.fixedWorkInforDto.overtimeHours, timeRangeLimit, lstTime, "OT", index);
			let totalTime = self.calcAllTime(dataFixed, lstTime, timeRangeLimit);
			totalBrkTime = self.calcAllBrk(lstTime);
			totalBrkTime = totalBrkTime != null ? formatById("Clock_Short_HM", Math.round(totalBrkTime * 5)) : "";

			if (totalBrkTime === (totalBrkTime + " "))
				totalBrkTime = totalBrkTime.trim();
			else
				totalBrkTime = (totalBrkTime + " ");

			if (totalBrkTime === ("0:00") || totalBrkTime === "")
				totalBrkTime = totalBrkTime;
			else
				totalBrkTime = totalBrkTime.trim();

			$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", totalBrkTime == totalBrkTime + " " ? totalBrkTime : totalBrkTime + " ");
			let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(9)" :
			"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (index + 2).toString() + ")" + " > td:nth-child(7)";
			
			if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
			$(cssTotalTime).css("background-color", "#ffffff");
			
			$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", totalTime != null ? totalTime : "");
		}

		calcTimeDuplicate(timeStart: any, timeEnd: any, startTime: any, endTime: any, type: number) {
			let duplicateTime: any = [];
			duplicateTime = ({
				startTime: 0,
				endTime: 0
			});
			if (_.inRange(timeStart, startTime, endTime) || _.inRange(timeEnd, startTime, endTime)) {
				if (timeStart <= startTime && timeEnd <= endTime) {
					duplicateTime = ({
						startTime: startTime,
						endTime: timeEnd
					});
				}

				if (timeStart >= startTime && timeEnd >= endTime) {
					duplicateTime = ({
						startTime: timeStart,
						endTime: endTime
					});
				}

				if (timeStart >= startTime && timeEnd <= endTime) {
					duplicateTime = ({
						startTime: timeStart,
						endTime: timeEnd
					});
				}
			}
			return duplicateTime;
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

		calcTotalTime(workScheduleDto: model.EmployeeWorkScheduleDto, whenCall?: any) {
			let totalTime: any = null;
			if (!_.isNil(workScheduleDto)) {
				if (workScheduleDto.endTime2 != null && workScheduleDto.startTime2 != null && workScheduleDto.endTime2 != 0 && workScheduleDto.startTime2 != 0)
					totalTime = (workScheduleDto.endTime1 - workScheduleDto.startTime1) + (workScheduleDto.endTime2 - workScheduleDto.startTime2);
				else if (workScheduleDto.endTime1 != null && workScheduleDto.startTime1 != null && workScheduleDto.endTime1 != 0 && workScheduleDto.startTime1 != 0)
					totalTime = (workScheduleDto.endTime1 - workScheduleDto.startTime1);
				if (_.isNil(whenCall))
					totalTime = totalTime != null ? formatById("Clock_Short_HM", totalTime) : "";
			}
			return totalTime;
		}

		calcAllBrk(lstTime: any) {
			let self = this, brkTotal = 0, lstTimeFilter: any = [];
			for (let br = 0; br < self.lstBreakSum.length; br++) {
				let lstBreakMinus: any = [];
				for (let ho = 0; ho < self.lstHolidayShort.length; ho++) {
					if (self.lstBreakSum.length > 0 && self.lstHolidayShort.length > 0) {
						if (self.lstBreakSum[br].start >= self.lstHolidayShort[ho].start && self.lstBreakSum[br].end <= self.lstHolidayShort[ho].end) {
							self.lstBreakSum[br].start = 0;
							self.lstBreakSum[br].end = 0;
						}
					}

					if (self.lstBreakSum[br].start >= self.lstHolidayShort[ho].start && self.lstBreakSum[br].end >= self.lstHolidayShort[ho].end && self.lstBreakSum[br].start <= self.lstHolidayShort[ho].end) {
						self.lstBreakSum[br].start = self.lstHolidayShort[ho].end;
					}

					if (self.lstBreakSum[br].start <= self.lstHolidayShort[ho].start && self.lstBreakSum[br].end >= self.lstHolidayShort[ho].end) {
						self.lstBreakSum.push({
							start: self.lstHolidayShort[ho].end,
							end: self.lstBreakSum[br].end,
							type: "BREAK",
							index: self.lstBreakSum.length + 1
						})
						self.lstBreakSum[br].end = self.lstHolidayShort[ho].start;
					}

					if (self.lstBreakSum[br].start <= self.lstHolidayShort[ho].start && self.lstBreakSum[br].end <= self.lstHolidayShort[ho].end && self.lstHolidayShort[ho].start <= self.lstBreakSum[br].end) {
						self.lstBreakSum[br].end = self.lstHolidayShort[ho].start;
					}
				}

				lstTimeFilter = _.filter(lstTime, (x: any) => {
					return (x.start == self.lstBreakSum[br].start && x.end < self.lstBreakSum[br].end) ||
						(x.start > self.lstBreakSum[br].start && x.end == self.lstBreakSum[br].end)
				});
				if (!_.isEmpty(lstTimeFilter)) {
					for (let brk = 0; brk < lstTimeFilter.length; brk++) {
						brkTotal += (self.lstBreakSum[br].end - self.lstBreakSum[br].start) - (lstTimeFilter[brk].end - lstTimeFilter[brk].start)
					}
				} else {
					brkTotal += self.lstBreakSum[br].end - self.lstBreakSum[br].start;
				}
			}
			return brkTotal;
		}

		calcAllTime(schedule: any, lstTime: any, timeRangeLimit: any) {
			// Tính tổng thời gian làm việc
			let self = this;
			let totalTimeAll = 0, totalTimeWork = 0,
				start1 = (schedule.workScheduleDto != null && schedule.workScheduleDto.startTime1 != null && schedule.workScheduleDto.startTime1 != 0) ? (self.checkTimeOfChart(schedule.workScheduleDto.startTime1, timeRangeLimit * 5)) : 0,
				end1 = (schedule.workScheduleDto != null && schedule.workScheduleDto.endTime1 != null && schedule.workScheduleDto.endTime1 != 0) ? (self.checkTimeOfChart(schedule.workScheduleDto.endTime1, timeRangeLimit * 5)) : 0,
				start2 = (schedule.workScheduleDto != null && schedule.workScheduleDto.startTime2 != null && schedule.workScheduleDto.startTime2 != 0) ? (self.checkTimeOfChart(schedule.workScheduleDto.startTime2, timeRangeLimit * 5)) : 0,
				end2 = (schedule.workScheduleDto != null && schedule.workScheduleDto.endTime2 != null && schedule.workScheduleDto.endTime2 != 0) ? (self.checkTimeOfChart(schedule.workScheduleDto.endTime2, timeRangeLimit * 5)) : 0;

			lstTime = _.sortBy(lstTime, [function(o: any) { return o.end; }]).reverse();
			lstTime = _.uniqWith(lstTime, function(arrVal: any, othVal: any) {
				return (arrVal.start === othVal.start);
			});

			lstTime = _.sortBy(lstTime, [function(o: any) { return o.start; }]);
			lstTime = _.uniqWith(lstTime, function(arrVal: any, othVal: any) {
				return (arrVal.end === othVal.end);
			});

			lstTime.forEach((total: any) => {
				totalTimeAll += (total.end * 5) - (total.start * 5)
			});
			start1 = start1 <= self.dispStart * 5 ? self.dispStart * 5 : start1;

			if (start2 != null && start2 != 0)
				start2 = start2 <= self.dispStart * 5 ? self.dispStart * 5 : start2;

			if (start2 != 0 && end2 != 0)
				totalTimeWork = ((end2) - (start2)) + ((end1) - (start1));
			else if (end1 != 0 && (start2 == 0 || end2 == 0))
				totalTimeWork = ((end1) - (start1));
			totalTimeWork = totalTimeWork - totalTimeAll;

			let totalTimeWorks = totalTimeWork != 0 ? formatById("Clock_Short_HM", totalTimeWork) : "";

			return totalTimeWorks;
		}

		// Tính tổng từng loại thời gian
		calcChartTypeTime(schedule: any, typeChart: any, timeRangeLimit: any, lstTime: any, type: string, index: any) {
			let self = this, startCalc = 0, endCalc = 0, lstTimeFilter: any = [];
			if (type == "BREAK" && self.dataScreen003A().employeeInfo[index].fixedWorkInforDto.fixBreakTime == 0) return lstTime;
			for (let o = 0; o < typeChart.length; o++) {
				let brkT: any = typeChart[o];
				let timeChartBrk = self.convertTimeToChart(_.isNil(brkT.startTime) ? brkT.start : (_.isNil(brkT.startTime.time) ? brkT.startTime : brkT.startTime.time),
					_.isNil(brkT.endTime) ? brkT.end : (_.isNil(brkT.endTime.time) ? brkT.endTime : brkT.endTime.time)),
					timeChart = self.convertTimeToChart(schedule.workScheduleDto.startTime1, schedule.workScheduleDto.endTime1), timeChart2: any = null;


				startCalc = self.checkTimeOfChart(timeChartBrk.startTime, timeRangeLimit);
				endCalc = self.checkTimeOfChart(timeChartBrk.endTime, timeRangeLimit);
				if (_.inRange(startCalc, timeChart.startTime, timeChart.endTime) || _.inRange(endCalc, timeChart.startTime, timeChart.endTime)) {
					let duplicateTime = self.calcTimeDuplicate(startCalc, endCalc, timeChart.startTime, timeChart.endTime, 1);

					startCalc = duplicateTime.startTime;
					endCalc = duplicateTime.endTime;

					self.lstAllChildShow.push({
						start: startCalc,
						end: endCalc,
						type: type,
						index: index,
						position: o
					})
					if (startCalc != endCalc) {
						if ((timeChart != null && timeChart.startTime != null && timeChart.endTime != null) &&
							(timeChart.startTime < (timeRangeLimit - self.dispStartHours * 12)) &&
							(_.inRange(timeChart.startTime, 0, (timeRangeLimit - self.dispStartHours * 12)) ||
								_.inRange(timeChart.endTime, 0, (timeRangeLimit - self.dispStartHours * 12)))) {

							if (!_.isNil(type) && type == "BREAK" && (_.inRange(startCalc, timeChart.startTime, timeChart.endTime) || _.inRange(endCalc, timeChart.startTime, timeChart.endTime))) {
								self.lstBreakSum.push({
									start: startCalc,
									end: endCalc,
									type: type,
									index: index
								})

							}

							if (!_.isNil(type) && (type == "SHORT" || type == "HOLIDAY") && (_.inRange(startCalc, timeChart.startTime, timeChart.endTime) || _.inRange(endCalc, timeChart.startTime, timeChart.endTime))) {
								self.lstHolidayShort.push({
									start: startCalc,
									end: endCalc,
									type: type,
									index: index
								})

								self.holidayShort.push({
									start: startCalc,
									end: endCalc,
									type: type,
									index: index
								})

							}

							lstTimeFilter = _.filter(lstTime, (x: any) => { return (x.start == timeChartBrk.startTime && x.end < timeChartBrk.endTime) || (x.start < timeChartBrk.startTime && x.end == timeChartBrk.endTime) })
							if ((_.isEmpty(lstTimeFilter) && (startCalc != timeChart.endTime && endCalc != timeChart.startTime) && (_.inRange(startCalc, timeChart.startTime, timeChart.endTime) || _.inRange(endCalc, timeChart.startTime, timeChart.endTime)))) {

								_.map(lstTime, (x: any) => {
									// xs > tcs & xe < tce
									if (x.start >= startCalc && x.end <= endCalc) {
										x.start = startCalc;
										x.end = endCalc;
									}
									// xs < tcs & xe < tce
									else if (x.start <= startCalc && x.end <= endCalc && _.inRange(startCalc, x.start, x.end)) {
										x.end = endCalc;
									}
									// xs > tcs & xe > tce
									else if (x.start >= startCalc && x.end >= endCalc && _.inRange(endCalc, x.start, x.end)) {
										x.start = startCalc;
									}
									else if (!_.inRange(x.start, startCalc, timeChartBrk.endTime) && !_.inRange(x.end, startCalc, endCalc)) {
										let lstTimeFilter2 = _.filter(lstTime, (x: any) => { return (x.start == startCalc) && (x.end == endCalc) })
										if (_.isEmpty(lstTimeFilter2)) {
											lstTime.push({
												start: startCalc,
												end: endCalc,
												type: type,
												index: index
											})
										}
									}
								})
							}

							if ((!_.isEmpty(lstTimeFilter) && (startCalc != timeChart.endTime && endCalc != timeChart.startTime)
								&& (_.inRange(startCalc, startCalc, timeChart.endTime) || _.inRange(endCalc, startCalc, timeChart.endTime)))) {
								_.map(lstTime, (x: any) => {
									if (x.start == startCalc && x.end < endCalc) {
										x.end = endCalc;
									}
								})
							}


							if (_.isEmpty(lstTime) && !_.isNil(type) && (startCalc != timeChart.endTime && endCalc != timeChart.startTime) && (_.inRange(startCalc, timeChart.startTime, timeChart.endTime) || _.inRange(endCalc, timeChart.startTime, timeChart.endTime))) {
								lstTime.push({
									start: startCalc,
									end: endCalc,
									type: type,
									index: index
								})
							}
						}
					}
				}

				if (self.dataScreen003A().targetInfor === 1) {
					timeChart2 = self.convertTimeToChart(schedule.workScheduleDto.startTime2, schedule.workScheduleDto.endTime2)
					if ((schedule.workScheduleDto.startTime2 != null && schedule.workScheduleDto.endTime2 != null) &&
						(timeChart2.startTime < (timeRangeLimit - self.dispStartHours * 12)) &&
						(_.inRange(timeChartBrk.startTime, 0, (timeRangeLimit - self.dispStartHours * 12)) ||
							_.inRange(timeChartBrk.endTime, 0, (timeRangeLimit - self.dispStartHours * 12)))) {
						let startCalc2 = self.checkTimeOfChart(timeChartBrk.startTime, timeRangeLimit), endCalc2 = self.checkTimeOfChart(timeChartBrk.endTime, timeRangeLimit);
						let duplicateTime2 = self.calcTimeDuplicate(startCalc2, endCalc2, timeChart2.startTime, timeChart2.endTime, 2);
						startCalc2 = duplicateTime2.startTime, endCalc2 = duplicateTime2.endTime;
						self.lstAllChildShow.push({
							start: startCalc2,
							end: endCalc2,
							type: type,
							index: index,
							position: o
						})

						if (startCalc2 != endCalc2) {

							if (!_.isNil(type) && type == "BREAK" && (_.inRange(startCalc2, timeChart2.startTime, timeChart2.endTime)
								|| _.inRange(endCalc2, timeChart2.startTime, timeChart2.endTime))) {
								self.lstBreakSum.push({
									start: startCalc2,
									end: endCalc2,
									type: type,
									index: index
								})
							}

							if (!_.isNil(type) && (type == "SHORT" || type == "HOLIDAY") && (_.inRange(startCalc2, timeChart2.startTime, timeChart2.endTime)
								|| _.inRange(endCalc2, timeChart2.startTime, timeChart2.endTime))) {
								self.lstHolidayShort.push({
									start: startCalc2,
									end: endCalc2,
									lstBreak: type,
									index: index
								})

								self.holidayShort.push({
									start: startCalc2,
									end: endCalc2,
									lstBreak: type,
									index: index
								})
							}

							lstTimeFilter = _.filter(lstTime, (x: any) => { return (x.start == timeChartBrk.startTime && x.end < timeChartBrk.endTime) || (x.start < timeChartBrk.startTime && x.end == timeChartBrk.endTime) });
							if ((_.isEmpty(lstTimeFilter) && (startCalc2 != timeChart2.endTime && endCalc2 != timeChart2.startTime)
								&& (_.inRange(startCalc2, timeChart2.startTime, timeChart2.endTime) || _.inRange(endCalc2, timeChart2.startTime, timeChart2.endTime)))) {
								_.map(lstTime, (x: any) => {
									// xs > tcs & xe < tce
									if (x.start >= startCalc2 && x.end <= endCalc2) {
										x.start = startCalc2;
										x.end = endCalc2;
									}
									// xs < tcs & xe < tce
									else if (x.start <= startCalc2 && x.end <= endCalc2 && _.inRange(startCalc2, x.start, x.end)) {
										x.end = endCalc2;
									}
									// xs > tcs & xe > tce
									else if (x.start >= startCalc2 && x.end >= endCalc2 && _.inRange(endCalc2, x.start, x.end)) {
										x.start = startCalc2;
									}
									else if (!_.inRange(x.start, startCalc2, timeChartBrk.endTime) && !_.inRange(x.end, startCalc2, endCalc2)) {
										let lstTimeFilter2 = _.filter(lstTime, (x: any) => { return (x.start == startCalc2) && (x.end == endCalc2) })
										if (_.isEmpty(lstTimeFilter2)) {

											lstTime.push({
												start: startCalc2,
												end: endCalc2,
												type: type,
												index: index
											})
										}
									}
								})
							}

							if ((!_.isEmpty(lstTimeFilter) && (startCalc2 != timeChart2.endTime && endCalc2 != timeChart2.startTime)
								&& (_.inRange(startCalc2, startCalc2, timeChart2.endTime) || _.inRange(endCalc2, startCalc2, timeChart2.endTime)))) {
								_.map(lstTime, (x: any) => {
									if (x.start == startCalc2 && x.end < endCalc2) {
										x.end = endCalc2;
									}
								})
							}

							if (_.isEmpty(lstTime) && !_.isNil(type) && (startCalc2 != timeChart2.endTime && endCalc2 != timeChart2.startTime)
								&& (_.inRange(startCalc2, timeChart2.startTime, timeChart2.endTime) || _.inRange(endCalc2, timeChart2.startTime, timeChart2.endTime))) {
								lstTime.push({
									start: startCalc2,
									end: endCalc2,
									type: type,
									index: index
								})
							}
						}
					}
				}
			}
			return lstTime;
		}

		public nextDay() {
			let self = this;
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.saveData(1).done(()=> {
						self.nextDayImpl();
					});
				}).ifNo(() => { self.nextDayImpl(); });
			} else {
				self.nextDayImpl();
			}
		}

		public nextDayImpl() {
			let self = this;
			block.grayout();
			self.changeTargetDate(1, 1);
			self.checkNext(true);
			self.checkPrv(true);
			/*$("#icon-prev-all").css("filter", "contrast(1)");
			$("#icon-prev-one").css("filter", "contrast(1)");*/
			if (self.dataFromA().endDate <= self.targetDate()) {
				self.targetDate(self.dataFromA().endDate);
				self.checkNext(false);
				/*$("#icon-next-one").css("filter", "contrast(0.7)");
				$("#icon-next-all").css("filter", "contrast(0.7)");*/
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId, 0);
		}

		public nextAllDay() {
			block.grayout();
			let self = this, i = 7,
				nextDay: any = moment(moment(self.targetDate()).add(7, 'd').format('YYYY/MM/DD')),
				checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.saveData(1).done(()=> {
						self.nextAllDayImpl(i, nextDay);
					})
				}).ifNo(() => { self.nextAllDayImpl(i, nextDay); });
			} else {
				self.nextAllDayImpl(i, nextDay);
			}
			block.clear();
		}

		public nextAllDayImpl(i: number, nextDay: any) {
			let self = this;
			block.grayout();
			self.checkNext(true);
			self.checkPrv(true);

			/*$("#icon-prev-all").css("filter", "contrast(1)");
			$("#icon-prev-one").css("filter", "contrast(1)");*/
			if (self.dataFromA().endDate <= nextDay._i) {
				let date: number = moment(self.dataFromA().endDate).date() - moment(self.targetDate()).date();
				self.checkNext(false);
				/*$("#icon-next-all").css("filter", "contrast(0.7)");
				$("#icon-next-one").css("filter", "contrast(0.7)");*/

				self.changeTargetDate(1, date);
			} else {
				self.changeTargetDate(1, i);
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId, 0);
		}

		public prevDay() {
			let self = this;
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.saveData(1).done(()=> {
						self.prevDayImpl();
					})
				}).ifNo(() => { self.prevDayImpl(); });
			} else {
				self.prevDayImpl();
			}
		}

		public prevDayImpl() {
			let self = this;
			block.grayout();
			self.changeTargetDate(0, 1);
			self.checkPrv(true);
			self.checkNext(true);
			/*$("#icon-next-all").css("filter", "contrast(1)");
			$("#icon-next-one").css("filter", "contrast(1)");*/
			if (self.dataFromA().startDate >= self.targetDate()) {
				self.targetDate(self.dataFromA().startDate);
				self.checkPrv(false);
				/*$("#icon-prev-one").css("filter", "contrast(0.7)");
				$("#icon-prev-all").css("filter", "contrast(0.7)");*/
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId, 0);
		}

		public prevAllDay() {
			let self = this, i = 7;
			let prvDay: any = moment(moment(self.targetDate()).subtract(7, 'd').format('YYYY/MM/DD'));
			let checkSort = $("#extable-ksu003").exTable('updatedCells');
			if (checkSort.length > 0) {
				dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
					self.saveData(1).done(()=> {
						self.prevAllDayImpl(i, prvDay);
					})
				}).ifNo(() => { self.prevAllDayImpl(i, prvDay) });
			} else {
				self.prevAllDayImpl(i, prvDay);
			}
		}

		public prevAllDayImpl(i: number, prvDay: any) {
			let self = this;
			block.grayout();
			self.checkPrv(true);
			self.checkNext(true);
			/*$("#icon-next-all").css("filter", "contrast(1)");
			$("#icon-next-one").css("filter", "contrast(1)");*/
			if (self.dataFromA().startDate >= prvDay._i) {
				let date: number = moment(self.targetDate()).date() - moment(self.dataFromA().startDate).date();
				self.checkPrv(false);
				/*$("#icon-prev-all").css("filter", "contrast(0.7)");
				$("#icon-prev-one").css("filter", "contrast(0.7)");*/

				self.changeTargetDate(0, date);
			} else {
				self.changeTargetDate(0, 7);
			}
			self.hoverEvent(self.targetDate());
			self.destroyAndCreateGrid(self.lstEmpId, 0);
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
			let shortW = moment(self.targetDate()).format('(ddd)');
                if (shortW == "(土)") {
                    shortW = "<span style='color:#0000ff;'>" + shortW + "</span>";
                } else if (shortW == "(日)") {
                    shortW = "<span style='color:#ff0000;'>" + shortW + "</span>";
                }
			self.targetDateDay(self.targetDate() + shortW);
		}

		public checkTimeInfo(index: any, worktypeCode: any, worktimeCode: any, startTime1: any, startTime2: any, endTime1: any, endTime2: any, columnKey: string): JQueryPromise<any> {
			let self = this;
			let dataFilter = self.dataScreen003A().employeeInfo[index];
			let dfd = $.Deferred<any>(), command: any = {
				workType: worktypeCode.trim(),
				workTime: worktimeCode.trim(),
				workTime1: new TimeZoneDto(new TimeOfDayDto(0, (duration.parseString(startTime1).toValue())),
					new TimeOfDayDto(0, (duration.parseString(endTime1).toValue()))),
				workTime2: (dataFilter.workScheduleDto.startTime2 == null) ? null : new TimeZoneDto(new TimeOfDayDto(0, (duration.parseString(startTime2).toValue())),
					new TimeOfDayDto(0, (duration.parseString(endTime2).toValue()))),
			}

			if ((columnKey === "startTime1") || /*&& startTime1 != "" && endTime1 != "") || */
				(columnKey === "startTime2" /*&& startTime2 != "" && endTime2 != ""*/) ||
				(columnKey === "endTime1" /*&& endTime1 != "" && startTime1 != ""*/) ||
				(columnKey === "endTime2" /*&& endTime2 != "" && startTime2 != ""*/)) {
				service.checkTimeIsIncorrect(command).done(function(result) {
					block.invisible();
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
										message: nts.uk.resource.getMessage('Msg_2058', [result[i].nameError, formatById("Clock_Short_HM", result[i].timeSpan.startTime)]),
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

					if (errors.length > 0) {
						if(self.checkNeedTime == "NOT_REQUIRED") return;
						let errorsInfo = _.uniqBy(errors, x => { return x.message });
						bundledErrors({ errors: errorsInfo }).then(() => {
							block.clear();
						});
						// ver 2
						self.checkEnableSave = false;
						self.enableSave(false);
					} else {
						block.clear();
						self.checkEnableSave = true;
						let checkSort = $("#extable-ksu003").exTable('updatedCells');
						if(checkSort.length > 0 && (_.isNil($("#extable-ksu003").data("errors")) || (!_.isNil($("#extable-ksu003").data("errors")) && $("#extable-ksu003").data("errors").length == 0)) 
						&& self.checkEnableSave == true && self.checkEnableWork == true){
						self.enableSave(true);
						}
					}

					dfd.resolve();
				}).fail(function(res: any) {
					errorDialog({ messageId: res.messageId, messageParams: res.parameterIds });
				}).always(function() {
				});
			} else {
				dfd.resolve();
			}
			return dfd.promise();
		}

		convertTime(timeStart: any, timeEnd: any, timeStart2: any, timeEnd2: any) {
			if (timeStart != "") timeStart = (duration.parseString(timeStart).toValue());
			if (timeEnd != "") timeEnd = (duration.parseString(timeEnd).toValue());
			if (timeStart2 != "") timeStart2 = (duration.parseString(timeStart2).toValue());
			if (timeEnd2 != "") timeEnd2 = (duration.parseString(timeEnd2).toValue());

			let timeConvert = {
				start: timeStart != "" ? timeStart : "",
				end: timeEnd != "" ? timeEnd : "",
				start2: timeStart2 != "" ? timeStart2 : "",
				end2: timeEnd2 != "" ? timeEnd2 : ""
			}
			return timeConvert;
		}

		convertTimeChart(startTime: any, endTime: any) {
			let convertTime = null;
			convertTime = {
				startTime: startTime,
				endTime: endTime
			}
			return convertTime;
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

		convertTimePixel(timeStart: any) {
			let time = 0, self = this;
			if (timeStart != null) {
				// convert string to pixel
				time = ((duration.parseString(timeStart).toValue()) / 5);
			}
			return time;
		}

		/** 行事(A2_1_6)をクリックする  (Click "event"(A2_1_6) */
		hoverEvent(targetDate: any) {
			let self = this, tooltip = _.filter(self.tooltip, (x: any) => { return x.ymd === self.targetDate() });
			let check = 0;
			if (tooltip.length > 0 && tooltip[0].htmlTooltip != null) {
				let htmlToolTip = tooltip[0].htmlTooltip;
				$('#event').on({
					"click": function() {
						tooltip = _.filter(self.tooltip, (x: any) => { return x.ymd === self.targetDate() })
						if (tooltip[0].htmlTooltip != null) {
							$(this).tooltip({
								items: "#event", content: htmlToolTip != null ? htmlToolTip : "",
								tooltipClass: "tooltip-styling"
							});
							$(this).tooltip("open");
							check = 1;
						}
					},
					"mouseout": function(a: any) {
						if (check == 1)
							$(this).tooltip("disable");
					}
				});
			}
		}
		
		// open dialog kdl053
		 openKDL053(dataReg : any) {
            let self = this;
            let param = {
                employeeIds: self.lstEmpId,                  // 社員の並び順
                isRegistered: dataReg.isRegistered == true ? 1 : 0,           // 登録されたか
                errorRegistrationList: dataReg.listErrorInfo, // エラー内容リスト 
            }
            setShared('dataShareDialogKDL053', param);
            nts.uk.ui.windows.sub.modal('/view/kdl/053/index.xhtml').onClosed(function(): any {
                console.log('closed');
            });
           block.clear();
        }

		// open dialog kdl045
		public openKdl045Dialog(empId: string) {
			let self = this, lineNo = _.findIndex(self.lstEmpId, (x) => { return x.empId === empId; });
			
			if(self.checkOpenDialog == false) return;
			
			let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(1)",
						cssWorkTypeName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(2)",
						cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(3)",
						cssWorkTName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(4)",
						cssStartTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(5)",
						cssEndTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(6)",
						cssStartTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(7)" : "",
						cssEndTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(8)" : "";
			block.grayout();
			let dataEmployee = _.filter(self.dataFromA().listEmp, (x: any) => { return x.id === empId; });
			let dataShare: any = {
				employeeInfo: self.dataScreen003A().employeeInfo[lineNo],
				targetInfor: self.dataScreen003A().targetInfor,
				canModified: self.dataScreen003A().canModified, // comment tạm để test
				scheCorrection: self.dataScreen003A().scheCorrection,
				unit: self.dataFromA().unit,
				targetId: self.dataFromA().unit === 0 ? self.dataFromA().workplaceId : self.dataFromA().workplaceGroupId,
				workplaceName: self.dataFromA().workplaceName
			};
			dataShare.employeeInfo.employeeCode = dataEmployee[0].code;
			dataShare.employeeInfo.employeeName = dataEmployee[0].name;
			setShared('dataShareTo045', dataShare);
			nts.uk.ui.windows.sub.modal('/view/kdl/045/a/index.xhtml').onClosed(() => {
				self.dataScreen045A(getShared('dataFromKdl045'));
				self.check045003 = false;
				if (!_.isNil(self.dataScreen045A())) {

					self.enableSave(true);
					self.checkGetInfo = true;
					let lstBrkTime = self.dataScreen045A().workScheduleDto.listBreakTimeZoneDto, totalBrkTime = "",
						lstBreak: any = lstBrkTime, totalTimebr: any = null;
					lstBrkTime.forEach((x: any) => {
						totalTimebr += x.end - x.start;
					})

					totalBrkTime = totalTimebr != null ? formatById("Clock_Short_HM", totalTimebr) : ""

					let totalTime = self.calcTotalTime(self.dataScreen045A().workScheduleDto),
						schedule = self.dataScreen045A().workScheduleDto,
						fixed = self.dataScreen045A().fixedWorkInforDto,
						info = self.dataScreen045A().workInfoDto;
						
					if (self.dataScreen003A().employeeInfo[lineNo].workScheduleDto == null) {
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
					} 
						// _.isNull(schedule) || 
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime1 = schedule.startTime1;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime1Status = schedule.endTime1;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime1 = schedule.endTime1;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime2 = schedule.startTime2;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime2 = schedule.endTime2;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.listBreakTimeZoneDto = lstBrkTime.length > 0 ? lstBrkTime : [];
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTypeCode = schedule.workTypeCode;
						self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTimeCode = schedule.workTimeCode;
					

					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto == null) {
						// _.isNull(fixed) || 
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
							fixBreakTime: fixed.fixBreakTime == true ? 1 : 0,
							workType: fixed.workType,
							isHoliday: true,
							isNeedWorkTime: schedule.startTime1 == null || schedule.startTime1 == 0 ? "NOT_REQUIRED" : "REQUIRED"
						}
					} 
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workTimeName = fixed.workTimeName;
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workTypeName = fixed.workTypeName;
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.fixBreakTime = fixed.fixBreakTime == true ? 1 : 0;
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType = fixed.workType;
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isHoliday = fixed.isHoliday;
						self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isNeedWorkTime  = schedule.startTime1 == null || schedule.startTime1 == 0 ? "NOT_REQUIRED" : "REQUIRED";
					

					self.dataScreen003A().employeeInfo[lineNo].workInfoDto.directAtr = info.directAtr;
					self.dataScreen003A().employeeInfo[lineNo].workInfoDto.bounceAtr = info.bounceAtr;

					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isNeedWorkTime == "NOT_REQUIRED") {
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
					} else {
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "worktimeCode");
					}
					
						
					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isNeedWorkTime == "NOT_REQUIRED") {
						self.check045003 = false;
						ruler.replaceAt(lineNo, [{ // xóa chart khi là ngày nghỉ
											type: "Flex",
											options: {
												id: `lgc` + lineNo,
												start: -1000,
												end: -1000,
												lineNo: lineNo
											}
						}]);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
						let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(9)" :
						"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(7)";
						
						if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
						$(cssTotalTime).css("background-color", "#ffffff");
						
						$(cssStartTime1).css("background-color", "#DDDDD2");
						$(cssEndTime1).css("background-color", "#DDDDD2");
						$(cssWorkTime).css("background-color", "#DDDDD2");
						$(cssStartTime2).css("background-color", "#DDDDD2");
						$(cssEndTime2).css("background-color", "#DDDDD2");
						
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workTypeName);
						$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeCode", self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTypeCode);

						$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
						$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
						
						
						$(".xcell").removeClass("x-error");
						self.checkGetInfo = false;
						self.check045003 = true;
						return;
					}
					
					self.setDataToMidExtable(lineNo, empId, schedule, fixed);
					
					self.convertDataIntoExtable();

					let datafilter: Array<ITimeGantChart> = _.filter(self.dataOfGantChart, (x: any) => { return x.empId === empId });
					if (datafilter.length > 0) {
						//self.updateGantChart(datafilter, lineNo, fixedGc, lstBreak, indexS, indexF);
						self.addAllChart(datafilter, lineNo, [], self.midDataGC, "KDL045", lstBreak);
						ruler.replaceAt(lineNo, [
							...self.allGcShow
						]);
					}

					self.setColorDialog(cssWorkType, cssWorkTypeName, cssWorkTime, cssWorkTName, cssStartTime1, cssEndTime1, cssStartTime2, cssEndTime2, empId, lineNo);
					
				}
				self.checkGetInfo = false;
				self.check045003 = true;
				block.clear();
			});
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

		public openKdl003Dialog(workTypeCode: string, workTimeCode: string, empId: string, type: string) {
			let self = this;
			if(self.checkOpenDialog == false) return;
			block.grayout();
			let lineNo = _.findIndex(self.dataScreen003A().employeeInfo, (x) => { return x.empId === empId; });
			let workTimeName = $("#extable-ksu003").exTable('dataSource', 'middle').body[lineNo].worktimeName;
			let cssWorkType: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(1)",
				cssWorkTypeName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(2)",
				cssWorkTime: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(3)",
				cssWorkTName: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(4)",
				cssStartTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(5)",
				cssEndTime1: string = "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(6)",
				cssStartTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(7)" : "",
				cssEndTime2: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(8)" : "";
			nts.uk.ui.windows.setShared('parentCodes', {
				workTypeCodes: [],
				selectedWorkTypeCode: workTypeCode,
				workTimeCodes: [],
				selectedWorkTimeCode: workTimeCode
			}, true);
			if(self.dataScreen003A().employeeInfo[lineNo].workInfoDto.isConfirmed == 1){
				block.clear();
				return;
			}
			/*if ((workTimeCode == "" && workTypeCode == "") || (type != "WorkTypeName" && workTimeCode == "" && workTimeName == "なし") || (workTimeCode == null && workTypeCode == null)) {
				block.clear();
				return;
			}*/

			let checkOpen = _.filter(self.disableDs, (x: any) => { return x.empId === empId });
			let checkOpen2: any = [];
			if (type === "WorkTypeName")
				checkOpen2 = _.filter(self.lstDis, (x: any) => { return x.empId === empId && x.worktypeName == true });

			if (type === "WorkTimeName")
				checkOpen2 = _.filter(self.lstDis, (x: any) => { return x.empId === empId && x.worktimeName == true });

			if (checkOpen.length < 1 && checkOpen2.length < 1) {
				nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(() => {
					let dataShareKdl003 = getShared('childData');
					if (!_.isNil(dataShareKdl003)) {
						self.check045003 = false;
						self.enableSave(true);
						let param = [{
							workTypeCode: dataShareKdl003.selectedWorkTypeCode,
							workTimeCode: dataShareKdl003.selectedWorkTimeCode
						}]
						// 社員勤務予定と勤務固定情報を取得する
						service.getEmpWorkFixedWorkInfo(param).done((data: any) => {
							if (!_.isNil(data)) {
								self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto = data.fixedWorkInforDto;
								self.dataScreen003A().employeeInfo[lineNo].workScheduleDto = data.workScheduleDto;

								if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isNeedWorkTime == "NOT_REQUIRED") {
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
								}

								if (data.fixedWorkInforDto.workType == null) {
									self.check045003 = false;
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime1", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime1", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "startTime2", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "endTime2", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeCode", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "totalTime", "");
									let cssTotalTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(9)" : 
									"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(7)";
									
									if($(cssTotalTime).css("background-color") != "rgb(221, 221, 210)" && $(cssTotalTime).css("background-color") != "rgb(236, 206, 251)")
									$(cssTotalTime).css("background-color", "#ffffff");
									
									$(cssStartTime1).css("background-color", "#DDDDD2");
									$(cssEndTime1).css("background-color", "#DDDDD2");
									$(cssWorkTime).css("background-color", "#DDDDD2");
									$(cssStartTime2).css("background-color", "#DDDDD2");
									$(cssEndTime2).css("background-color", "#DDDDD2");
									
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "breaktime", "");
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktimeName", getText('KSU003_55'));
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeName", self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workTypeName);
									$("#extable-ksu003").exTable("cellValue", "middle", empId, "worktypeCode", self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.workTypeCode);
			
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "worktimeCode");
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
									$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
									$(".xcell").removeClass("x-error");
									self.check045003 = true;
									if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isHoliday == true) $(".xcell").removeClass("x-error");
									return;
								}

								if (data.workScheduleDto.startTime1) {
									self.checkClearTime = false;
									self.checkUpdateMidChart = false;
								}

								self.setDataToMidExtable(lineNo, empId, self.dataScreen003A().employeeInfo[lineNo].workScheduleDto, self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto);
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
												start: -1000,
												end: -1000,
												lineNo: lineNo
											}
										}]);
									}
								}

							}
							self.setColorDialog(cssWorkType, cssWorkTypeName, cssWorkTime, cssWorkTName, cssStartTime1, cssEndTime1, cssStartTime2, cssEndTime2, empId, lineNo);

							block.clear();
						}).fail(function(error) {
							block.clear();
							errorDialog({ messageId: error.messageId });
						}).always(function() {
							self.checkUpdateMidChart = true;
							self.check045003 = true;
						});
					}
				});
			}
			self.check045003 = true;
			block.clear();
			$(".xcell").removeClass("x-error");
		}
		
		public setColorDialog(cssWorkType : string, cssWorkTypeName : string, cssWorkTime : string, cssWorkTName : string, cssStartTime1 : string, cssEndTime1 : string, cssStartTime2 : string, cssEndTime2 : string, empId : string, lineNo : number){
			let self = this, breakTime: string = self.dataScreen003A().targetInfor == 1 ? "#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(10)" :
			"#extable-ksu003 > .ex-body-middle > table > tbody tr:nth-child" + "(" + (lineNo + 2).toString() + ")" + " > td:nth-child(8)";
			let color = "";
				if (empId === self.employeeIdLogin) {
					color = "#94b7fe";
				} else {
					color = "#cee6ff";
				}
			if(self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.fixBreakTime == 0) {
				$(breakTime).css("background-color", "#DDDDD2");
			} else {
				$(breakTime).css("background-color", color);
			}
			$(cssWorkTime).css("background-color", color);
					$(cssWorkTName).css("background-color", color);
					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.isHoliday == false) {
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "worktimeCode");
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime1");
						$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime1");
						$(cssStartTime1).removeClass("xseal");
						$(cssEndTime1).removeClass("xseal");
						if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType != WorkTimeForm.FLEX && self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime2 != null) {
							$(cssStartTime2).css("background-color", "#FFFFFF");
							$(cssEndTime2).css("background-color", "#FFFFFF");
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("enableCell", "middle", empId, "endTime2");
							$(cssStartTime2).removeClass("xseal");
							$(cssEndTime2).removeClass("xseal");
						} else {
							$(cssStartTime2).css("background-color", "#DDDDD2");
							$(cssEndTime2).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
							$(cssStartTime2).addClass("xseal");
							$(cssEndTime2).addClass("xseal");
						}
					}
					
					$(cssWorkType).css("background-color", color);
					$(cssWorkTypeName).css("background-color", color);
					$(cssStartTime1).css("background-color", color);
					$(cssEndTime1).css("background-color", color);
					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType != WorkTimeForm.FLEX) {
						if(self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime2 != null)
						$(cssStartTime2).css("background-color", color);
						
						if($(cssStartTime2).css("background-color") != "rgb(221, 221, 210)")
						$(cssStartTime2).css("background-color", color);
						
						if(self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.startTime2 == null)
						$(cssStartTime2).css("background-color", "#DDDDD2");
						
					}

					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType != WorkTimeForm.FLEX) {
						if(self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime2 != null)
						$(cssEndTime2).css("background-color", color);
						
						if($(cssEndTime2).css("background-color") != "rgb(221, 221, 210)")
						$(cssEndTime2).css("background-color", color);
						
						if(self.dataScreen003A().employeeInfo[lineNo].workScheduleDto.endTime2 == null)
						$(cssEndTime2).css("background-color", "#DDDDD2");
					}
					let lstType = self.dataScreen003A().scheCorrection; // 確定済みか
					let fix = _.filter(lstType, (x: any) => { return x === 0 }),
						flex = _.filter(lstType, (x: any) => { return x === 1 }),
						flow = _.filter(lstType, (x: any) => { return x === 2 });
					
					if (self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto != null && self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType != null) {
						if ((fix.length == 0 && self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType == WorkTimeForm.FIXED) ||
						(flex.length == 0 && self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType == WorkTimeForm.FLEX) ||
						(flow.length == 0 && self.dataScreen003A().employeeInfo[lineNo].fixedWorkInforDto.workType == WorkTimeForm.FLOW)) {
							$(cssStartTime2).css("background-color", "#DDDDD2");
							$(cssEndTime2).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime2");
							$(cssStartTime2).addClass("xseal");
							$(cssEndTime2).addClass("xseal");
							
							$(cssStartTime1).css("background-color", "#DDDDD2");
							$(cssEndTime1).css("background-color", "#DDDDD2");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "startTime1");
							$("#extable-ksu003").exTable("disableCell", "middle", empId, "endTime1");
							$(cssStartTime1).addClass("xseal");
							$(cssEndTime1).addClass("xseal");
						}
					}
					$(".xcell").removeClass("x-error");
		}
		
		addColumn(index : any, detailContent : any, width : any){
			let self =this, y = self.dispStartHours;
			detailContent.columns[index] = { key: (y).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
			detailContent.columns[index += 1] = { key: (y += 1).toString() + "_", width: width, headerText: "", visible: true  };
		}

		/** A1_4 - Close modal */
		public closeDialog(): void {
			let self = this;
			if(self.checkOpenDialog == false) return;
			nts.uk.ui.windows.close();
		}

	}

	export enum SupportAtr {
		NOT_CHEERING = 1,
		TIMEZONE_SUPPORTER = 2,
		TIMEZONE_RESPONDENT = 3,
		ALL_DAY_SUPPORTER = 4,
		ALL_DAY_RESPONDENT = 5
	}
	
	export enum SetupType {
		REQUIRED = "REQUIRED",
		OPTIONAL = "OPTIONAL",
		NOT_REQUIRED = "NOT_REQUIRED"
	}

	export enum EditStateSetting {
		HAND_CORRECTION_MYSELF = 0,
		HAND_CORRECTION_OTHER = 1,
		REFLECT_APPLICATION = 2,
		IMPRINT = 3
	}

	export enum WorkTimeForm {
		FIXED = 0,
		FLEX = 1,
		FLOW = 2,
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

// 114258 : xóa làm tròn Math.floor