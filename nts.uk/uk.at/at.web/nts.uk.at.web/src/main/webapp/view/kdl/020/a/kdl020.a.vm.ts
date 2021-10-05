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
		paramData: any = nts.uk.ui.windows.getShared('KDL005_DATA');

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
		holidayDataOld2: KnockoutObservableArray<HolidayInfo> = ko.observableArray([]);

		checkSolid: number = 0;

		// A2_5 
		currentRemainNum: KnockoutObservable<string> = ko.observable('6日 と6:00');
		// A2_8 
		nextScheDate: KnockoutObservable<string> = ko.observable('2020/09/10 (木)');
		// A2_11
		annMaxTime: KnockoutObservable<string> = ko.observable('16:00');
		// A2_9
		annLimitStart: KnockoutObservable<string> = ko.observable('2020/08/10');
		// A2_12
		annLimitEnd: KnockoutObservable<string> = ko.observable('2021/09/10');

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
				{ headerText: nts.uk.resource.getText('KDL020_52'), key: 'grantdate', width: 130 },
				{ headerText: nts.uk.resource.getText('KDL020_53'), key: 'numbergrants', width: 70 },
				{ headerText: nts.uk.resource.getText('KDL020_54'), key: 'numberuses', width: 120 },
				{ headerText: nts.uk.resource.getText('KDL020_55'), key: 'remaining', width: 120 },
				{ headerText: nts.uk.resource.getText('KDL020_56'), key: 'expirationdate', width: 130 }
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
				maxRows: 15
			};

			$('#kcp005component').ntsListComponent(self.listComponentOption);

			self.listComponentOption.selectedCode.subscribe((value: any) => {

			})

		}

		startPage(): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();
			nts.uk.ui.block.grayout();
			service.findAnnualHolidays(self.paramData).done((data: any) => {
				self.dataHoliday(data);
				_.forEach(data.employeeImports, (a: any, ind) => {
					self.employeeList.push({ id: ind, code: a.employeeCode, name: a.employeeName, workplaceName: 'HN' })
				});
				self.listComponentOption.selectedCode(self.employeeList()[0].code);
			});
			dfd.resolve();
			return dfd.promise();
		}

		bindDataToGrid(value: any): JQueryPromise<any> {
			let self = this, dfd = $.Deferred<any>();


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
		id?: string;
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

	/** 年休・積休残数詳細情報DTO */
	export class InforAnnualHolidaysAccHolidayDto {
		/** 年休・積休残数一覧  (年休・積休残数詳細) */
		lstRemainAnnAccHoliday: KnockoutObservableArray<AnnualAccumulatedHoliday>;
		/** 年休・積休消化一覧  (年休・積休消化詳細) */
		lstAnnAccHoliday: KnockoutObservableArray<DetailsAnnuaAccumulatedHoliday>;
		/** 年休・積休管理区分 */
		annAccManaAtr: KnockoutObservable<boolean>;
		/** 時間年休の年間上限時間 */
		annMaxTime: KnockoutObservable<string>;
		/** 時間年休の年間上限開始日 */
		annLimitStart: KnockoutObservable<string>;
		/** 時間年休の年間上限終了日 */
		annLimitEnd: KnockoutObservable<string>;
		/** 時間年休管理区分 */
		annManaAtr: KnockoutObservable<boolean>;
		/** 次回付与予定日 */
		nextScheDate: KnockoutObservable<string>;
		/** 現時点残数 */
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
		/** 付与数 */
		numberGrants: KnockoutObservable<string>;
		/** 付与日 */
		grandDate: KnockoutObservable<string>;
		/** 使用数 */
		numberOfUse: KnockoutObservable<string>;
		/** 有効期限 */
		dateOfExpiry: KnockoutObservable<string>;
		/** 残数 */
		numberOfRemain: KnockoutObservable<string>;

		constructor(
			numberGrants: string,
			grandDate: string,
			numberOfUse: string,
			dateOfExpiry: string,
			numberOfRemain: string
		) {
			let self = this;
			self.numberGrants = ko.observable(numberGrants);
			self.grandDate = ko.observable(grandDate);
			self.numberOfUse = ko.observable(numberOfUse);
			self.dateOfExpiry = ko.observable(dateOfExpiry);
			self.numberOfRemain = ko.observable(numberOfRemain);
		}
	}

	export class DetailsAnnuaAccumulatedHoliday {
		/** 使用数 */
		numberOfUse: KnockoutObservable<string>;
		/** 年休消化状況 */
		annualHolidayStatus: KnockoutObservable<string>;
		/** 消化日 */
		digestionDate: KnockoutObservable<string>;

		constructor(
			numberOfUse: string,
			annualHolidayStatus: string,
			digestionDate: string
		) {
			let self = this;
			self.numberOfUse = ko.observable(numberOfUse);
			self.annualHolidayStatus = ko.observable(annualHolidayStatus);
			self.digestionDate = ko.observable(digestionDate);
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

	class HolidayInfo {
		grantdate: string;
		numbergrants: string;
		numberuses: string;
		remaining: string;
		expirationdate: string;
		constructor(grantdate: string, numbergrants: string, numberuses: string, remaining: string, expirationdate: string) {
			this.grantdate = grantdate;
			this.numbergrants = numbergrants;
			this.numberuses = numberuses;
			this.remaining = remaining;
			this.expirationdate = expirationdate;
		}
	}

	class HolidayInfo2 {
		digestionday: string;
		digestionuse: string;
		constructor(digestionday: string, digestionuse: string) {
			this.digestionday = digestionday;
			this.digestionuse = digestionuse;
		}
	}

}