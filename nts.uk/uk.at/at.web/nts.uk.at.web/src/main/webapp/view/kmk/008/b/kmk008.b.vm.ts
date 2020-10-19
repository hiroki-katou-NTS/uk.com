/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.b {
	@bean()
	export class ScreenModel extends ko.ViewModel{
		show: KnockoutObservable<boolean>;
		enable: KnockoutObservable<boolean>;
		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
		selectedTab: KnockoutObservable<string>;
		viewmodelC: any;
		//viewmodelD: any;
		// viewmodelE: any;
		//viewmodelF: any;
		laborSystemAtr: number = 0;
		useEmployment: KnockoutObservable<boolean>;
		useWorkPlace: KnockoutObservable<boolean>;
		useClasss: KnockoutObservable<boolean>;
		selectedLimit: KnockoutObservable<number> = ko.observable(0);

		theTime: KnockoutObservable<number> = ko.observable(0);
		empListCmp: EmpListCmp;

		constructor() {
			super();

			let self = this;
			self.show = ko.observable(true);
			self.show.subscribe(function(newVal) {
				if (newVal)
					$("#sidebar").ntsSideBar("show", 1);
				else
					$("#sidebar").ntsSideBar("hide", 1);
			});

			self.enable = ko.observable(true);
			self.enable.subscribe(function(newVal) {
				if (newVal) {
					$("#sidebar").ntsSideBar("enable", 1);
					$("#sidebar").ntsSideBar("enable", 2);
				}
				else {
					$("#sidebar").ntsSideBar("disable", 1);
					$("#sidebar").ntsSideBar("disable", 2);
				}
			});

			self.useEmployment = ko.observable(true);
			self.useWorkPlace = ko.observable(true);
			self.useClasss = ko.observable(true);

			self.tabs = ko.observableArray([
				{ id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
				{ id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
				{ id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
				{ id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
			]);
			//self.selectedTab = ko.observable('tab-2');
			self.empListCmp = new EmpListCmp();
		}

		created() {
			// extend window with view model
			const vm = this;
			_.extend(window, {vm});

			// extend window with view constant
			const kmk008b = nts.uk.at.view.kmk008.b;
			_.extend(window, {kmk008b});
		}

		mounted() {
			const vm = this;

			vm.startPage().done(() => {
				$('#work-place-base-date').prop('tabIndex', -1);
				$(document).ready(function() {
					$('tabindex').removeAttr("tabindex");
				});
			});

			$('#empt-list-setting').ntsListComponent(vm.empListCmp.listComponentOption);
			$("#C4_3").ntsFixedTable({});
			$("#C4_6").ntsFixedTable({});
			$("#C4_31").ntsFixedTable({});
		}


		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();
			$('#work-place-base-date').prop('tabIndex', -1);
			nts.uk.ui.errors.clearAll();
			self.laborSystemAtr = __viewContext.transferred.value.laborSystemAtr;
			// self.viewmodelC = new kmk008.c.viewmodel.ScreenModel(self.laborSystemAtr);
			// self.viewmodelD = new kmk008.d.viewmodel.ScreenModel(self.laborSystemAtr);
			// self.viewmodelE = new kmk008.e.viewmodel.ScreenModel(self.laborSystemAtr);
			//self.viewmodelF = new kmk008.f.viewmodel.ScreenModel(self.laborSystemAtr);
			// self.viewmodelC.startPage();
			// self.viewmodelE.startPage();

			service.getData().done(function(item) {
				if (item) {
					if (item.employmentUseAtr == 0) {
						$("#sidebar").ntsSideBar("hide", 1);
					}

					if (item.workPlaceUseAtr == 0) {
						$("#sidebar").ntsSideBar("hide", 2);
					}

					if (item.classificationUseAtr == 0) {

						$("#sidebar").ntsSideBar("hide", 3);
					}
				} else {
					self.useEmployment(true);
					self.useWorkPlace(true);
					self.useClasss(true);
				}
				dfd.resolve();
			});

			dfd.resolve();
			return dfd.promise();
		}

		tabpanel1Click() {
			let self = this;
			self.viewmodelC.startPage();
		}

		tabpanel2Click() {
			let self = this;
			//self.viewmodelD.startPage();
		}

		tabpanel3Click() {
			let self = this;
			// self.viewmodelE.startPage();
		}

		tabpanel4Click() {
			let self = this;
			//self.viewmodelF.startPage();
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
