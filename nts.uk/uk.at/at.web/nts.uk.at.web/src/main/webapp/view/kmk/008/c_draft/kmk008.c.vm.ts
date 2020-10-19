module nts.uk.at.view.kmk008.c {
	import getText = nts.uk.resource.getText;
	import alertError = nts.uk.ui.dialog.alertError;

	@bean()
	export class ScreenModel extends ko.ViewModel {
		timeOfCompany: KnockoutObservable<TimeOfCompanyModel>;
		isUpdate: boolean;
		laborSystemAtr: number = 0;
		deleteEnable = true;
		textOvertimeName: KnockoutObservable<string>;
		nameErrorWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_42"));
		nameAlarmWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_43"));
		nameLimitWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_44"));
		nameErrorTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_42"));
		nameAlarmTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_43"));
		nameLimitTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_44"));
		nameErrorFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_42"));
		nameAlarmFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_43"));
		nameLimitFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_44"));
		nameErrorOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_42"));
		nameAlarmOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_43"));
		nameLimitOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_44"));
		nameErrorTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_42"));
		nameAlarmTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_43"));
		nameLimitTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_44"));
		nameErrorThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_42"));
		nameAlarmThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_43"));
		nameLimitThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_44"));
		nameErrorOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_42"));
		nameAlarmOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_43"));
		nameLimitOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_44"));
		nameUpperMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_120"));
		nameUpperMonthAverage: KnockoutObservable<string> = ko.observable(getText("KMK008_122"));

		selectedLimit: KnockoutObservable<number> = ko.observable(0);
		someText: string;

		theTime: KnockoutObservable<number> = ko.observable(0);

		empListCmp: EmpListCmp;

		constructor() {
			super();
			let vm = this;

			// self.laborSystemAtr = laborSystemAtr;
			vm.isUpdate = true;
			vm.timeOfCompany = ko.observable(new TimeOfCompanyModel(null));
			vm.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Company']));
			vm.empListCmp = new EmpListCmp();
		}

		created() {
			// extend window with view model
			const vm = this;
			_.extend(window, {vm});

			// extend window with view constant
			const kmk008c = nts.uk.at.view.kmk008.c;
			_.extend(window, {kmk008c});
		}


		mounted() {
			const vm = this;

			vm.startPage();
		}


		startPage(): JQueryPromise<any> {
			let vm = this;
			let dfd = $.Deferred();

			nts.uk.ui.errors.clearAll();
			if (vm.laborSystemAtr == 0) {
				vm.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Company}']));
			} else {
				vm.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Company}']));
			}

			// new service.Service().getAgreementTimeOfCompany(vm.laborSystemAtr).done(data => {
			// 	vm.timeOfCompany(new TimeOfCompanyModel(data));
			// 	if (data.updateMode) {
			// 		vm.isUpdate = true;
			// 	} else {
			// 		vm.isUpdate = false;
			// 	}
			// 	$("#errorCheckInput").focus();
			// 	dfd.resolve();
			// }).fail(error => {
			//
			// }); TODO

			$('#empt-list-setting').ntsListComponent(vm.empListCmp.listComponentOption);
			$("#C4_3").ntsFixedTable({});
			$("#C4_6").ntsFixedTable({});
			$("#C4_31").ntsFixedTable({});

			return dfd.promise();
		}

		saveEmpSetting() {
			alert(0);
		}

		copyEmpSetting() {
			alert(1);
		}

		deleteEmpSetting() {
			alert(2);
		}

		addUpdateData() {
			let self = this;
			let timeOfCompanyNew = new UpdateInsertTimeOfCompanyModel(self.timeOfCompany(), self.laborSystemAtr);
			nts.uk.ui.block.invisible();
			if (self.isUpdate) {
				new service.Service().updateAgreementTimeOfCompany(timeOfCompanyNew).done(function (listError) {
					if (listError.length > 0) {
						self.showDialogError(listError);
						nts.uk.ui.block.clear();
						return;
					}
					nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function (data) {
						self.startPage();
					});
				});
				nts.uk.ui.block.clear();
				return;
			}
			new service.Service().addAgreementTimeOfCompany(timeOfCompanyNew).done(function (listError) {
				if (listError.length > 0) {
					self.showDialogError(listError);
					nts.uk.ui.block.clear();
					return;
				}
				nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function (data) {
					self.startPage();
				});
				nts.uk.ui.block.clear();
			});
			nts.uk.ui.block.clear();
		}

		showDialogError(listError: any) {
			let errorCode = _.split(listError[0], ',');
			if (errorCode[0] === 'Msg_59') {
				let periodName = getText(errorCode[1]);
				let param1 = "期間: " + getText(errorCode[1]) + "<br>" + getText(errorCode[2]);
				alertError({messageId: errorCode[0], messageParams: [param1, getText(errorCode[3])]});
			} else {
				alertError({
					messageId: errorCode[0],
					messageParams: [getText(errorCode[1]), getText(errorCode[2]), getText(errorCode[3])]
				});
			}
		}

	}

	export class TimeOfCompanyModel {
			alarmWeek: KnockoutObservable<string> = ko.observable(null);
			errorWeek: KnockoutObservable<string> = ko.observable(null);
			limitWeek: KnockoutObservable<string> = ko.observable(null);
			alarmTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			errorTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			limitTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			alarmFourWeeks: KnockoutObservable<string> = ko.observable(null);
			errorFourWeeks: KnockoutObservable<string> = ko.observable(null);
			limitFourWeeks: KnockoutObservable<string> = ko.observable(null);
			alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
			errorOneMonth: KnockoutObservable<string> = ko.observable(null);
			limitOneMonth: KnockoutObservable<string> = ko.observable(null);
			alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
			errorTwoMonths: KnockoutObservable<string> = ko.observable(null);
			limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
			alarmThreeMonths: KnockoutObservable<string> = ko.observable(null);
			errorThreeMonths: KnockoutObservable<string> = ko.observable(null);
			limitThreeMonths: KnockoutObservable<string> = ko.observable(null);
			alarmOneYear: KnockoutObservable<string> = ko.observable(null);
			errorOneYear: KnockoutObservable<string> = ko.observable(null);
			limitOneYear: KnockoutObservable<string> = ko.observable(null);
			upperMonth: KnockoutObservable<string> = ko.observable(null);
			upperMonthAverage: KnockoutObservable<string> = ko.observable(null);

			constructor(data: any) {
				let self = this;
				if (!data) return;
				self.alarmWeek(data.alarmWeek);
				self.errorWeek(data.errorWeek);
				self.limitWeek(data.limitWeek);
				self.alarmTwoWeeks(data.alarmTwoWeeks);
				self.errorTwoWeeks(data.errorTwoWeeks);
				self.limitTwoWeeks(data.limitTwoWeeks);
				self.alarmFourWeeks(data.alarmFourWeeks);
				self.errorFourWeeks(data.errorFourWeeks);
				self.limitFourWeeks(data.limitFourWeeks);
				self.alarmOneMonth(data.alarmOneMonth);
				self.errorOneMonth(data.errorOneMonth);
				self.limitOneMonth(data.limitOneMonth);
				self.alarmTwoMonths(data.alarmTwoMonths);
				self.errorTwoMonths(data.errorTwoMonths);
				self.limitTwoMonths(data.limitTwoMonths);
				self.alarmThreeMonths(data.alarmThreeMonths);
				self.errorThreeMonths(data.errorThreeMonths);
				self.limitThreeMonths(data.limitThreeMonths);
				self.alarmOneYear(data.alarmOneYear);
				self.errorOneYear(data.errorOneYear);
				self.limitOneYear(data.limitOneYear);
				self.upperMonth(data.upperMonth);
				self.upperMonthAverage(data.upperMonthAverage);
			}
		}

	export class UpdateInsertTimeOfCompanyModel {
			laborSystemAtr: number = 0;
			alarmWeek: number = 0;
			errorWeek: number = 0;
			limitWeek: number = 0;
			alarmTwoWeeks: number = 0;
			errorTwoWeeks: number = 0;
			limitTwoWeeks: number = 0;
			alarmFourWeeks: number = 0;
			errorFourWeeks: number = 0;
			limitFourWeeks: number = 0;
			alarmOneMonth: number = 0;
			errorOneMonth: number = 0;
			limitOneMonth: number = 0;
			alarmTwoMonths: number = 0;
			errorTwoMonths: number = 0;
			limitTwoMonths: number = 0;
			alarmThreeMonths: number = 0;
			errorThreeMonths: number = 0;
			limitThreeMonths: number = 0;
			alarmOneYear: number = 0;
			errorOneYear: number = 0;
			limitOneYear: number = 0;
			upperMonth: number = 0;
			upperMonthAverage: number = 0;

			constructor(data: TimeOfCompanyModel, laborSystemAtr: number) {
				let self = this;
				self.laborSystemAtr = laborSystemAtr;
				if (!data) return;
				self.alarmWeek = +data.alarmWeek() || 0;
				self.errorWeek = +data.errorWeek() || 0;
				self.limitWeek = +data.limitWeek() || 0;
				self.alarmTwoWeeks = +data.alarmTwoWeeks() || 0;
				self.errorTwoWeeks = +data.errorTwoWeeks() || 0;
				self.limitTwoWeeks = +data.limitTwoWeeks() || 0;
				self.alarmFourWeeks = +data.alarmFourWeeks() || 0;
				self.errorFourWeeks = +data.errorFourWeeks() || 0;
				self.limitFourWeeks = +data.limitFourWeeks() || 0;
				self.alarmOneMonth = +data.alarmOneMonth() || 0;
				self.errorOneMonth = +data.errorOneMonth() || 0;
				self.limitOneMonth = +data.limitOneMonth() || 0;
				self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
				self.errorTwoMonths = +data.errorTwoMonths() || 0;
				self.limitTwoMonths = +data.limitTwoMonths() || 0;
				self.alarmThreeMonths = +data.alarmThreeMonths() || 0;
				self.errorThreeMonths = +data.errorThreeMonths() || 0;
				self.limitThreeMonths = +data.limitThreeMonths() || 0;
				self.alarmOneYear = +data.alarmOneYear() || 0;
				self.errorOneYear = +data.errorOneYear() || 0;
				self.limitOneYear = +data.limitOneYear() || 0;
				self.upperMonth = +data.upperMonth() || 0;
				self.upperMonthAverage = +data.upperMonthAverage() || 0;
			}
		}


	class EmpListCmp {
		listComponentOption: any;
		selectedCode: KnockoutObservable<string>;
		multiSelectedCode: KnockoutObservableArray<string>;
		isShowAlreadySet: KnockoutObservable<boolean>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		isDialog: KnockoutObservable<boolean>;
		isShowNoSelectRow: KnockoutObservable<boolean>;
		isMultiSelect: KnockoutObservable<boolean>;
		employmentList: KnockoutObservableArray<UnitModel>;
		isDisplayClosureSelection: KnockoutObservable<boolean>;
		isDisplayFullClosureOption: KnockoutObservable<boolean>;
		closureSelectionType: KnockoutObservable<number>;
		selectClosureTypeList: KnockoutObservableArray<any>;

		constructor(){
			const self = this;
			self.selectedCode = ko.observable('1');
			self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
			self.isShowAlreadySet = ko.observable(false);
			self.alreadySettingList = ko.observableArray([
				{code: '1', isAlreadySetting: true},
				{code: '2', isAlreadySetting: true}
			]);
			self.isDialog = ko.observable(false);
			self.isShowNoSelectRow = ko.observable(false);
			self.isMultiSelect = ko.observable(false);
			self.isDisplayClosureSelection = ko.observable(false);
			self.isDisplayFullClosureOption = ko.observable(false);
			self.closureSelectionType = ko.observable(1);
			self.selectClosureTypeList = ko.observableArray([
				{code: 1, name: 'Select Full Closure option'},
				{code: 2, name: 'Select by selected closure code'},
				{code: 3, name: 'Nothing (Select first option)'},
			]);

			self.listComponentOption = {
				isShowAlreadySet: self.isShowAlreadySet(),
				isMultiSelect: self.isMultiSelect(),
				listType: ListType.EMPLOYMENT,
				selectType: SelectType.SELECT_BY_SELECTED_CODE,
				selectedCode: self.selectedCode,
				isDialog: self.isDialog(),
				isShowNoSelectRow: self.isShowNoSelectRow(),
				alreadySettingList: self.alreadySettingList,
				maxRows: 12
			};

			self.employmentList = ko.observableArray<UnitModel>([]);
		}
	}

	export class ListType {
			static EMPLOYMENT = 1;
			static Classification = 2;
			static JOB_TITLE = 3;
			static EMPLOYEE = 4;
		}

	export interface UnitModel {
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
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

	export enum MonthlyLimit {
		LIMIT_0_TIME = <number> 0,
		LIMIT_1_TIME = <number> 1,
		LIMIT_2_TIME = <number> 2,
		LIMIT_3_TIME = <number> 3,
		LIMIT_4_TIME = <number> 4,
		LIMIT_5_TIME = <number> 5,
		LIMIT_6_TIME = <number> 6,
		LIMIT_7_TIME = <number> 7,
		LIMIT_8_TIME = <number> 8,
		LIMIT_9_TIME = <number> 9,
		LIMIT_10_TIME = <number> 10,
		LIMIT_11_TIME = <number> 11,
		LIMIT_12_TIME = <number> 12
	}
}
