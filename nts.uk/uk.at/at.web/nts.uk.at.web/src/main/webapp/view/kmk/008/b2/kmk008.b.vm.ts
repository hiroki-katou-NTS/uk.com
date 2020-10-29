module nts.uk.at.view.kmk008.b {
	import getText = nts.uk.resource.getText;
	import alertError = nts.uk.ui.dialog.alertError;
	import AgreementTimeOfCompanyDto = nts.uk.at.view.kmk008.b.service.AgreementTimeOfCompanyDto;

	@bean()
	export class ScreenModel extends ko.ViewModel {
		timeOfCompany: KnockoutObservable<TimeOfCompanyModel> = ko.observable(new TimeOfCompanyModel(null));
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

		selectedLimit: KnockoutObservable<number> = ko.observable(Limit.LIMIT_6_TIME);
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

			if (__viewContext.transferred.value.laborSystemAtr) {
				vm.laborSystemAtr = __viewContext.transferred.value.laborSystemAtr;
			} else {
				vm.laborSystemAtr = 0;
			}
		}

		created() {
			// extend window with view model
			const vm = this;
			_.extend(window, {vm});

			// extend window with view constant
			const kmk008 = nts.uk.at.view.kmk008.b;
			_.extend(window, {kmk008});
		}

		mounted() {
			const vm = this;

			vm.startPage();
		}

		startPage(): JQueryPromise<any> {
			let vm = this;
			let dfd = $.Deferred();

			$("#B3_3").ntsFixedTable({});
			$("#B3_6").ntsFixedTable({});
			$("#B3_31").ntsFixedTable({});

			$('#empt-list-setting').ntsListComponent(vm.empListCmp.listComponentOption);
			$("#C4_3").ntsFixedTable({});
			$("#C4_6").ntsFixedTable({});
			$("#C4_31").ntsFixedTable({});

			vm.initB().done(()=>{
			});

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

		private validateTimes(): void {
			// TODO
		}

		addUpdateData() {
			let vm = this;

			vm.validateTimes();
			// let timeOfCompanyNew = new UpdateInsertTimeOfCompanyModel(vm.timeOfCompany(), vm.laborSystemAtr);
			let timeOfCompanyNew = ko.unwrap(vm.timeOfCompany());
			timeOfCompanyNew.laborSystemAtr = vm.laborSystemAtr;
			nts.uk.ui.block.invisible();
			new service.Service().saveCpnAgrTime(timeOfCompanyNew).done(function (listError) {
				if (listError.length > 0) {
					vm.showDialogError(listError);
					nts.uk.ui.block.clear();
					return;
				}

				// // Msg_59
				// nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(function (data) {
				// 	vm.initB();
				// });
			}).fail(()=>{
				alert(1);
			});
			nts.uk.ui.block.clear();

				return;
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

		tabpanel1Click() {
			let vm = this;
			vm.initB().done(()=>{
			});
		}

		initB(): JQueryPromise<any> {
			let vm = this;
			let dfd = $.Deferred();

			vm.$blockui("grayout");

			nts.uk.ui.errors.clearAll();
			if (vm.laborSystemAtr == 0) {
				vm.textOvertimeName(vm.$i18n('KMK008_12', ['{#KMK008_8}', '{#Com_Company}']));
			} else {
				vm.textOvertimeName(vm.$i18n('KMK008_12', ['{#KMK008_8}', '{#Com_Company}']));
			}

			new service.Service().getB(vm.laborSystemAtr).done(data => {
				vm.timeOfCompany(data);

				// self.timeOfCompany(new TimeOfCompanyModel(data));
				// if (data.updateMode) {
				//     self.isUpdate = true;
				// } else {
				//     self.isUpdate = false;
				// }
				// $("#errorCheckInput").focus();

				vm.$blockui("clear");
				dfd.resolve();
			}).fail(error => {
			}).always(() => {
				vm.$blockui("clear");
			});

			return dfd.promise();
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

		errorTwoYear: KnockoutObservable<string> = ko.observable(null);
		alarmTwoYear: KnockoutObservable<string> = ko.observable(null);
		limitTwoYear: KnockoutObservable<string> = ko.observable(null);
		overMaxTimes : KnockoutObservable<number> = ko.observable(Limit.LIMIT_6_TIME);

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
			self.overMaxTimes(parseInt(data.overMaxTimes));
		}
	}

	export class UpdateInsertTimeOfCompanyModel {
		laborSystemAtr: number = 0;
		alarmOneMonth: number = 0;
		errorOneMonth: number = 0;
		limitOneMonth: number = 0;
		alarmTwoMonths: number = 0;
		errorTwoMonths: number = 0;
		limitTwoMonths: number = 0;
		alarmOneYear: number = 0;
		errorOneYear: number = 0;
		limitOneYear: number = 0;
		errorTwoYear: number = 0;
		alarmTwoYear: number = 0;
		limitTwoYear: number = 0;
		overMaxTimes: number = 0;

		constructor(data: TimeOfCompanyModel, laborSystemAtr: number) {
			let self = this;
			self.laborSystemAtr = laborSystemAtr;
			if (!data) return;
			self.alarmOneMonth 	= parseInt(data.alarmOneMonth());
			self.errorOneMonth 	= parseInt(data.errorOneMonth());
			self.limitOneMonth 	= parseInt(data.limitOneMonth());
			self.alarmTwoMonths = parseInt(data.alarmTwoMonths());
			self.errorTwoMonths = parseInt(data.errorTwoMonths());
			self.limitTwoMonths = parseInt(data.limitTwoMonths());
			self.alarmOneYear 	= parseInt(data.alarmOneYear());
			self.errorOneYear 	= parseInt(data.errorOneYear());
			self.limitOneYear 	= parseInt(data.limitOneYear());

			self.errorTwoYear = parseInt(data.errorTwoYear());
			self.alarmTwoYear = parseInt(data.alarmTwoYear());
			self.limitTwoYear = parseInt(data.limitTwoYear());
			self.overMaxTimes = data.overMaxTimes();
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

	export enum Limit {
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
