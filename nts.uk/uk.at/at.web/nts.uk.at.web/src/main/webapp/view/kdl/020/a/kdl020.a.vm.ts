module nts.uk.at.view.kdl020.a.viewmodel {
	export class ScreenModel {
		date: KnockoutObservable<any>;
		empList: KnockoutObservableArray<string> = ko.observableArray([]);
		dataHoliday: KnockoutObservable<InforAnnualHolidaysAccHolidayDto> = ko.observable();

		//_____KCP005________
		listComponentOption: any = [];
		selectedCode: KnockoutObservable<string>;
		multiSelectedCode: KnockoutObservableArray<string>;
		isShowAlreadySet: KnockoutObservable<boolean>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		isDialog: KnockoutObservable<boolean>;
		isShowNoSelectRow: KnockoutObservable<boolean>;
		isMultiSelect: KnockoutObservable<boolean>;
		isShowWorkPlaceName: KnockoutObservable<boolean>;
		isShowSelectAllButton: KnockoutObservable<boolean>;
		disableSelection: KnockoutObservable<boolean>;

		employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
		employeeImports : any = [];
		paramData: any = nts.uk.ui.windows.getShared('KDL020_DATA');

		// search
		items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

		// area left
		employeeCodeName: KnockoutObservable<string> = ko.observable('');

		// Grid A_3
		columns: KnockoutObservableArray<any>;
		currentCode: KnockoutObservable<any>;
		currentCodeList: KnockoutObservableArray<any>;
		count: number = 100;
		holidayData: KnockoutObservableArray<HolidayInfo> = ko.observableArray([]);
		holidayDataOld: KnockoutObservableArray<HolidayInfo> = ko.observableArray([]);

		// Grid A_5
		columns2: KnockoutObservableArray<any>;
		currentCode2: KnockoutObservable<any>;
		currentCodeList2: KnockoutObservableArray<any>;
		count2: number = 100;
		switchOptions2: KnockoutObservableArray<any>;
		holidayData2: KnockoutObservableArray<HolidayInfo2> = ko.observableArray([]);
		holidayDataOld2: KnockoutObservableArray<HolidayInfo2> = ko.observableArray([]);

		checkSolid: number = 0;

		// A2_5 
		currentRemainNum: KnockoutObservable<string> = ko.observable('');
		// A2_8 
		nextScheDate: KnockoutObservable<string> = ko.observable('');
		// A2_11
		annMaxTime: KnockoutObservable<string> = ko.observable('');
		// A2_9
		annLimitStart: KnockoutObservable<string> = ko.observable('');
		// A2_12
		annLimitEnd: KnockoutObservable<string> = ko.observable('');

		//
		checkEnable: KnockoutObservable<boolean> = ko.observable(false);
		checkSub: KnockoutObservable<boolean> = ko.observable(false);
		changeMode: KnockoutObservable<boolean> = ko.observable(false);
		showGrid: KnockoutObservable<boolean> = ko.observable(false);

		searchText: KnockoutObservable<string> = ko.observable('');

		constructor() {
			let self = this;
			self.date = ko.observable(new Date());
			self.empList = ko.observableArray([]);

			//_____KCP005________
			self.selectedCode = ko.observable('1');
			self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
			self.isShowAlreadySet = ko.observable(false);
			self.alreadySettingList = ko.observableArray([
				{ code: '1', isAlreadySetting: true },
				{ code: '2', isAlreadySetting: true }
			]);
			self.isDialog = ko.observable(false);
			self.isShowNoSelectRow = ko.observable(false);
			self.isMultiSelect = ko.observable(false);
			self.isShowWorkPlaceName = ko.observable(false);
			self.isShowSelectAllButton = ko.observable(false);
			self.disableSelection = ko.observable(false);

			//Number of grants Heda
			self.columns = ko.observableArray([
				{ headerText: nts.uk.resource.getText('KDL020_52'), key: 'granddate', width: 125 },
				{ headerText: nts.uk.resource.getText('KDL020_53'), key: 'numbergrants', width: 110 },
				{ headerText: nts.uk.resource.getText('KDL020_54'), key: 'numberuses', width: 110 },
				{ headerText: nts.uk.resource.getText('KDL020_55'), key: 'remaining', width: 115 },
				{ headerText: nts.uk.resource.getText('KDL020_56'), key: 'expirationdate', width: 135 }
			]);

			//
			self.columns2 = ko.observableArray([
				{ headerText: nts.uk.resource.getText('KDL020_59'), key: 'digestionday', width: 150 },
				{ headerText: nts.uk.resource.getText('KDL020_60'), key: 'digestionuse', width: 70 }
			]);

			self.currentCode = ko.observable(0);
			self.currentCodeList = ko.observableArray([]);

			self.currentCode2 = ko.observable(0);
			self.currentCodeList2 = ko.observableArray([]);

			if (self.paramData.length > 1) {
				$("#area-right").show();
			} else {
				$("#area-right").hide();
			}

			//
			self.listComponentOption = {
				isShowAlreadySet: self.isShowAlreadySet(),
				isMultiSelect: false,
				listType: ListType.EMPLOYEE,
				employeeInputList: self.employeeList,
				selectType: SelectType.SELECT_FIRST_ITEM,
				selectedCode: self.selectedCode,
				isDialog: self.isDialog(),
				isShowNoSelectRow: self.isShowNoSelectRow(),
				alreadySettingList: self.alreadySettingList,
				isShowWorkPlaceName: self.isShowWorkPlaceName(),
				isShowSelectAllButton: self.isShowSelectAllButton(),
				disableSelection: self.disableSelection(),
				maxRows: 15.51
			};

			$('#kcp005component').ntsListComponent(self.listComponentOption);

			self.listComponentOption.selectedCode.subscribe((value: any) => {
				if (value == null) return;
				self.checkSolid = 0;

				if (self.checkSub()) {
					$("#grid-grand").ntsGrid("destroy")
					$("#grid-grand-2").ntsGrid("destroy")
					self.getData(value).done(() => {
						self.createGrid();	
					});
				}

				self.checkSub(true);
			})
		}

		startPage(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			self.checkSub(false);
			self.getData("").done(() => {
				setTimeout(function() {
					self.createGrid();
					dfd.resolve();
				}, 1);
			});
			dfd.resolve();
			return dfd.promise();
		}

		createGrid() {
			let self = this;
			$("#grid-grand").ntsGrid({
				height: '145px',
				name: '#KDL020_7',
				multiple: false,
				dataSource: self.holidayData(),
				primaryKey: 'granddate',
				virtualization: true,
				virtualizationMode: 'continuous',
				// virtualizationMode: "fixed",
				columns: [
					{ headerText: nts.uk.resource.getText('KDL020_52'), key: 'granddate', width: 125 },
					{ headerText: nts.uk.resource.getText('KDL020_53'), key: 'numbergrants', width: 110 },
					{ headerText: nts.uk.resource.getText('KDL020_54'), key: 'numberuses', width: 110 },
					{ headerText: nts.uk.resource.getText('KDL020_55'), key: 'remaining', width: 115 },
					{ headerText: nts.uk.resource.getText('KDL020_56'), key: 'expirationdate', width: 135 }
				],
				features: [{ name: 'Resizing', type: 'local' }]
			});

			$("#grid-grand-2").ntsGrid({
				height: '145px',
				name: '#KDL020_58',
				multiple: false,
				dataSource: self.holidayData2(),
				primaryKey: 'digestionday',
				virtualization: true,
				virtualizationMode: 'continuous',
				// virtualizationMode: "fixed",
				columns: [
					{ headerText: '', key: 'annualHolidayStatus', width: 200,hidden: true } ,
					{ headerText: nts.uk.resource.getText('KDL020_59'), key: 'digestionday', width: 150, formatter: function (digestionDate : any, record : any) {
                        return "<div style='margin-left: 1px;display: flex;'><div style='width: 35px;' >"+record.annualHolidayStatus.toString()+"</div> <div style='width: 155px;float:right;'> " + digestionDate + " </div></div>"; 
                	} },
					{ headerText: nts.uk.resource.getText('KDL020_60'), key: 'digestionuse', width: 100 ,formatter: (v : any) => {
                    	return '<div style="margin-left: 10px;">' + v + '</div>';
                    } } 
				],
				features: [{ name: 'Resizing', type: 'local' }]
			});
		}
		

		getData(value: any): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			nts.uk.ui.block.grayout();
			let param = self.paramData;
			if (value != "") {
				let idParam = _.filter(self.employeeImports, (x: any) => {
					return _.includes(x.employeeCode, value)
				})

				if (idParam.length > 0) {
					param = [idParam[0].employeeId];
				}
			}
			service.findAnnualHolidays(param).done((data: any) => {

				if (data.accHolidayDto != null) {
					self.dataHoliday(data.accHolidayDto);

					self.currentRemainNum(data.accHolidayDto.currentRemainNum);
					self.nextScheDate(data.accHolidayDto.nextScheDate);
					self.annMaxTime(data.accHolidayDto.annMaxTime);
					self.annLimitStart(data.accHolidayDto.annLimitStart);
					self.annLimitEnd(data.accHolidayDto.annLimitEnd);

					// ???2 , ???1
					if (!data.accHolidayDto.annAccManaAtr) {
						self.changeMode(true);
						self.showGrid(false);
					}
					else {
						self.showGrid(true);
						self.changeMode(false);
					}

					if (data.accHolidayDto.annLimitStart == "" && data.accHolidayDto.annLimitEnd == "") {
						self.checkEnable(false);
					} else {
						self.checkEnable(true);
					}

					// ???3	
					if (data.accHolidayDto.annManaAtr) {
						$("#annual-area").show();
					} else {
						$("#annual-area").hide();
					}
				} else {
					self.changeMode(true);
					self.checkEnable(false);
				}
				if (!self.checkSub()) {
					let dataEmp = _.sortBy(data.employeeImports, ['employeeCode']); // fix t???m ????? tr??nh miss sort
					self.employeeImports = data.employeeImports;
					_.forEach(dataEmp, (a: any, ind) => {
						self.employeeList.push({ id: ind, code: a.employeeCode, name: a.employeeName, workplaceName: 'HN' })
					});
				}

				self.bindDataToGrid();
				if (!self.checkSub())
					self.listComponentOption.selectedCode(self.employeeList()[0].code);

				let name = null;
				name = _.filter(self.employeeList(), (x: any) => {
					return _.isEqual(x.code, value == "" ? self.employeeList()[0].code : value);
				});

				if (name.length > 0)
					self.employeeCodeName(name[0].code + " " + name[0].name);

				$("#cancel-btn").focus();
				
				$(".ui-dialog-title").text(nts.uk.resource.getText('KDL020_1'));

				nts.uk.ui.block.clear();
				dfd.resolve();
			});
			return dfd.promise();
		}

		bindDataToGrid(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			self.holidayData([]);
			self.holidayData2([]);
			if (_.isNil(self.dataHoliday())) return;
			_.forEach(self.dataHoliday().lstRemainAnnAccHoliday, (x: AnnualAccumulatedHoliday /**  */, index) => {
				self.holidayData.push(new HolidayInfo(x.grandDate, x.numberGrants, x.numberOfUse, x.numberOfRemain, x.dateOfExpiry));
			})
			_.forEach(self.dataHoliday().lstAnnAccHoliday, (x: DetailsAnnuaAccumulatedHoliday, index) => {
				self.holidayData2.push(new HolidayInfo2(x.annualHolidayStatus , x.digestionDate, x.numberOfUse));
			})
			
			return dfd.promise();
		}

		bindDataToText(z: any, index: number) {

		}

		showHideItem(data: any) {

		}

		findData(data: any) {
			let self = this;

			let text = $("input.ntsSearchBox.nts-editor.ntsSearchBox_Component").val()
			if (text == "") {
				nts.uk.ui.dialog.info({ messageId: "MsgB_24" });
			} else {
				let lstFil = _.filter(self.employeeList(), (z: any) => {
					return _.includes(z.code, text);
				})

				if (lstFil.length > 0) {
					let index = _.findIndex(lstFil, (z: any) => _.isEqual(z.code, self.listComponentOption.selectedCode()));
					if (index == -1 || index == lstFil.length - 1) {
						self.listComponentOption.selectedCode(lstFil[0].code);
					} else {
						self.listComponentOption.selectedCode(lstFil[index + 1].code);
					}
					let scroll: any = _.findIndex(self.employeeList(), (z: any) => _.isEqual(z.code, self.listComponentOption.selectedCode())),
						id = _.filter($("table > tbody > tr > td > div"), (x: any) => {
							return _.includes(x.id, "_scrollContainer") && !_.includes(x.id, "single-list");
						})
					$("#" + id[0].id).scrollTop(scroll * 24);
				} else {
					nts.uk.ui.dialog.info({ messageId: "MsgB_25" });
				}
			}
		}

		cancel() {
			nts.uk.ui.windows.close();
		}
	}

	export interface IEmployeeParam {
		employeeIds: Array<string>;
		baseDate: string;
	}

	export class ListType {
		static EMPLOYMENT = 1;
		static Classification = 2;
		static JOB_TITLE = 3;
		static EMPLOYEE = 4;
	}

	export interface UnitModel {
		id?: number;
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
		optionalColumn?: any;
	}

	export class SelectType {
		static SELECT_BY_SELECTED_CODE = 1;
		static SELECT_ALL = 2;
		static SELECT_FIRST_ITEM = 3;
		static NO_SELECT = 4;
	}

	export interface UnitAlreadySettingModel {
		code: string;
		isAlreadySetting: boolean;
	}

	/** ?????????????????????????????????DTO */
	export class InforAnnualHolidaysAccHolidayDto {
		/** ???????????????????????????  (???????????????????????????) */
		lstRemainAnnAccHoliday: KnockoutObservableArray<AnnualAccumulatedHoliday>;
		/** ???????????????????????????  (???????????????????????????) */
		lstAnnAccHoliday: KnockoutObservableArray<DetailsAnnuaAccumulatedHoliday>;
		/** ??????????????????????????? */
		annAccManaAtr: KnockoutObservable<boolean>;
		/** ????????????????????????????????? */
		annMaxTime: KnockoutObservable<string>;
		/** ???????????????????????????????????? */
		annLimitStart: KnockoutObservable<string>;
		/** ???????????????????????????????????? */
		annLimitEnd: KnockoutObservable<string>;
		/** ???????????????????????? */
		annManaAtr: KnockoutObservable<boolean>;
		/** ????????????????????? */
		nextScheDate: KnockoutObservable<string>;
		/** ??????????????? */
		currentRemainNum: KnockoutObservable<string>;
		constructor(
			lstRemainAnnAccHoliday: Array<AnnualAccumulatedHoliday>,
			lstAnnAccHoliday: Array<DetailsAnnuaAccumulatedHoliday>,
			annAccManaAtr: boolean,
			annMaxTime: string,
			annLimitStart: string,
			annLimitEnd: string,
			annManaAtr: boolean,
			nextScheDate: string,
			currentRemainNum: string
		) {
			let self = this;
			self.lstRemainAnnAccHoliday = ko.observableArray(lstRemainAnnAccHoliday);
			self.lstAnnAccHoliday = ko.observableArray(lstAnnAccHoliday);
			self.annAccManaAtr = ko.observable(annAccManaAtr);
			self.annMaxTime = ko.observable(annMaxTime);
			self.annLimitStart = ko.observable(annLimitStart);
			self.annLimitEnd = ko.observable(annLimitEnd);
			self.annManaAtr = ko.observable(annManaAtr);
			self.nextScheDate = ko.observable(nextScheDate);
			self.currentRemainNum = ko.observable(currentRemainNum);
		}
	}

	export class AnnualAccumulatedHoliday {
		/** ????????? */
		numberGrants: string;
		/** ????????? */
		grandDate: string;
		/** ????????? */
		numberOfUse: string;
		/** ???????????? */
		dateOfExpiry: string;
		/** ?????? */
		numberOfRemain: string;

		constructor(
			numberGrants: string,
			grandDate: string,
			numberOfUse: string,
			dateOfExpiry: string,
			numberOfRemain: string
		) {
			let self = this;
			self.numberGrants = (numberGrants);
			self.grandDate = (grandDate);
			self.numberOfUse = (numberOfUse);
			self.dateOfExpiry = (dateOfExpiry);
			self.numberOfRemain = (numberOfRemain);
		}
	}

	export class DetailsAnnuaAccumulatedHoliday {
		/** ????????? */
		numberOfUse: string;
		/** ?????????????????? */
		annualHolidayStatus: string;
		/** ????????? */
		digestionDate: string;

		constructor(
			numberOfUse: string,
			annualHolidayStatus: string,
			digestionDate: string
		) {
			let self = this;
			self.numberOfUse = (numberOfUse);
			self.annualHolidayStatus = (annualHolidayStatus);
			self.digestionDate = (digestionDate);
		}
	}

	class HolidayInfo {
		granddate: string;
		numbergrants: string;
		numberuses: string;
		remaining: string;
		expirationdate: string;
		constructor(granddate: string, numbergrants: string, numberuses: string, remaining: string, expirationdate: string) {
			this.granddate = granddate;
			this.numbergrants = numbergrants;
			this.numberuses = numberuses;
			this.remaining = remaining;
			this.expirationdate = expirationdate;
		}
	}

	class HolidayInfo2 {
		digestionday: string;
		digestionuse: string;
		annualHolidayStatus: string;
		constructor(annualHolidayStatus: string, digestionday: string, digestionuse: string) {
			this.annualHolidayStatus = annualHolidayStatus;
			this.digestionday = digestionday;
			this.digestionuse = digestionuse;
		}
	}

	export class ItemModel {
		code: number;
		name: string;
		displayName?: string;
		constructor(code: number, name: string, displayName?: string) {
			this.code = code;
			this.name = name;
			this.displayName = displayName;
		}
	}
}