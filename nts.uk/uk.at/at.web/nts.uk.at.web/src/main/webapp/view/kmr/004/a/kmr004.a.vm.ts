/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmr004.a {
	const API = {
		START: "screen/at/record/reservation-conf-list/start",
		PDF : "order/report/print/pdf",
		EXCEL : "order/report/print/excel",
		DATE_FORMAT: "yyyy/MM/dd"
	};

	import tree = kcp.share.tree;
	import list = kcp.share.list;
	import formatDate = nts.uk.time.formatDate;
	import parseTime = nts.uk.time.parseTime;

	@bean()
	export class KMR004AViewModel extends ko.ViewModel {
		model : KnockoutObservable<OutputCondition> = ko.observable(new OutputCondition());
		baseDate: KnockoutObservable<Date> = ko.observable(new Date()); // base date for KCP004, KCP012
		treeGrid: tree.TreeComponentOption; // tree grid properties object
		listComponentOption: list.ComponentOption;
		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([]);
		selectedTab: KnockoutObservable<string> = ko.observable('');
		outputConditionChecked: KnockoutObservable<number> = ko.observable(OUTPUT_CONDITION.TOTAL); // output condition
		extractionConditionEnable: KnockoutObservable<boolean> = ko.observable(false);
		separatePageCheckboxEnable: KnockoutObservable<boolean> = ko.observable(true);
		conditionListCcb: KnockoutObservableArray<any> = ko.observableArray([]);
		conditionListCcbAll: any[];
		conditionListCcbEnable: KnockoutObservable<boolean> = ko.observable(false);
		closingTimeOptions: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		reservationTimeRange1: string = '';
		reservationTimeRange2: string = '';
		reservationTimeRange: KnockoutObservable<string> = ko.observable('');
		selectedWorkLocationCode: KnockoutObservableArray<string> = ko.observableArray([]); // KCP012 selected codes
		cacheKey:string;
		displayingWorkplaceList:number = 0;

		constructor() {
			super();
			const vm = this;

			vm.cacheKey = "kmr004aCache_" + vm.$user.companyId + "_" + vm.$user.employeeId;

			vm.tabs = ko.observableArray([
				{
					id: 'tab-1',
					title: vm.$i18n('KMR004_12'),
					content: '.tab-content-1',
					enable: ko.observable(true),
					visible: ko.observable(true)},
				{
					id: 'tab-2',
					title: vm.$i18n('KMR004_13'),
					content: '.tab-content-2',
					enable: ko.observable(true),
					visible: ko.observable(true)}
			]);
			vm.selectedTab = ko.observable('tab-1');
		}

		created() {
			const vm = this;
			_.extend(window, {vm});

			const module = nts.uk.at.view.kmr004.a;
			_.extend(window, {module});
		}

		mounted() {
			const vm = this;

			vm.$blockui("grayout");

			// Call init API
			vm.$ajax(API.START).done((data) => {
				vm.startKMR004aScreen(data);
			}).fail(function(res) {
				vm.showErrorMessage(res);
			}).always(() => {
				vm.$blockui("clear");
			});

			nts.uk.characteristics.restore(vm.cacheKey).done((c13sData: any) => {
				vm.restoreScreenState(c13sData);
			});

			vm.model().totalExtractCondition.subscribe((newValue) => {
				if (newValue == EXTRACT_CONDITION.UN_ORDERED) {
					vm.extractionConditionEnable(true);
				} else {
					vm.extractionConditionEnable(false);
				}
			});

			vm.model().itemExtractCondition.subscribe((newValue) => {
				if (newValue == EXTRACT_CONDITION.ALL) {
					vm.separatePageCheckboxEnable(true);
				} else {
					vm.separatePageCheckboxEnable(false);
				}

				if (newValue == EXTRACT_CONDITION.UNSPECIFIED) {
					vm.conditionListCcbEnable(true);
				} else {
					vm.conditionListCcbEnable(false);
				}
			});

			vm.model().workLocationCodes.subscribe((selectedWorkLocationCode) => {
				if (selectedWorkLocationCode == null) {
					vm.conditionListCcb(vm.conditionListCcbAll);
				} else {
					let filtered = vm.conditionListCcbAll.filter((item) => {
						if (selectedWorkLocationCode == item.locationCode) {
							return item;
						}
					});
					vm.conditionListCcb(filtered);
				}
			});
		}

		startKMR004aScreen(data: any) {
			const vm = this;
			vm.initClosingTimeLable(data);
			vm.initClosingTimeSwitch(data);

			if(data.operationDistinction == "BY_COMPANY"){
				vm.initWorkplaceList();
				vm. displayingWorkplaceList = 1;
			} else {
				vm.initWorkLocationList();
				vm. displayingWorkplaceList = 2;
			}
			vm.initConditionListComboBox(data);
		}

		initConditionListComboBox(data: any) {
			const vm = this;
			// bento list
			vm.conditionListCcbAll = data.menu.map((item) => {
				return {
					code: item.code,
					locationCode: item.locationCode,
					name: item.name
				}
			})
			vm.conditionListCcb(vm.conditionListCcbAll);
		}

		prepareData():any{
			let vm = this;
			let frameNo = -1;
			let totalTitle = '';
			let detailTitle = '';
			let isBreakPage = false;
			let totalExtractCondition = -1;
			let itemExtractCondition = -1;
			let extractionConditionChecked = false;
			let workLocationCodes = [];

			if(vm.outputConditionChecked() === OUTPUT_CONDITION.TOTAL){
				totalTitle = vm.model().totalTitle();
				totalExtractCondition = vm.model().totalExtractCondition();
				if(totalExtractCondition === EXTRACT_CONDITION.UN_ORDERED){
					extractionConditionChecked = vm.model().extractionConditionChecked();
				}
			}else{
				detailTitle = vm.model().detailTitle();
				if(vm.model().itemExtractCondition() === EXTRACT_CONDITION.ALL){
					isBreakPage = vm.model().isBreakPage();
					itemExtractCondition = vm.model().itemExtractCondition();
				}else{
					frameNo = vm.model().frameNo();
				}
			}
			if(vm.model().workLocationCodes() !== null && vm.model().workLocationCodes().length > 0){
				workLocationCodes.push(vm.model().workLocationCodes())
			}
			let data = {
				workplaceIds: vm.model().workplaceIds(),
				workLocationCodes: workLocationCodes,
				period: vm.model().period.peek(),
				totalExtractCondition: totalExtractCondition,
				itemExtractCondition: itemExtractCondition,
				frameNo: frameNo,
				totalTitle:  totalTitle,
				detailTitle: detailTitle,
				isBreakPage: isBreakPage,
				reservationClosingTimeFrame: vm.model().reservationClosingTimeFrame.peek(),
				extractionConditionChecked: extractionConditionChecked
			};
			return data;
		}

		printExcel(){
			let vm = this;
			vm.saveCharacteristics();
			let data = vm.prepareData();

			let checkResult = vm.checkBeforeExtract(data);
			if (!checkResult) {
				vm.showCheckEmptyListSubmitMessage();
			} else {
				vm.$blockui("invisible");
				nts.uk.request.exportFile("at", API.EXCEL, data).done(() => {
					vm.$blockui("clear");
				}).fail((res: any) => {
					vm.$blockui("clear").then(function () {
						vm.showErrorMessage(res);
					});
				});
			}
		}

		printPDF(){
			let vm = this;
			vm.saveCharacteristics();
			let data = vm.prepareData();
			$("#exportTitle").trigger("validate");
			let checkResult = vm.checkBeforeExtract(data);
			if (!checkResult) {
				vm.showCheckEmptyListSubmitMessage();
			} else {
				vm.$blockui("invisible");
				nts.uk.request.exportFile("at", API.PDF, data).done(() => {
					vm.$blockui("clear");
				}).fail((res: any) => {
					vm.$blockui("clear").then(function () {
						vm.showErrorMessage(res);
					});
				});
			}
		}

		checkBeforeExtract(data:any): boolean{
			if ((data.workplaceIds.length < 1) && (data.workLocationCodes.length < 1)) {
				return false;
			}
			return true;
		}

		showCheckEmptyListSubmitMessage(){
			let vm = this;
			let msgParam :string;
			if (vm.displayingWorkplaceList == 1) {
				msgParam = vm.$i18n('Com_Workplace');
			} else if (vm.displayingWorkplaceList == 2) {
				msgParam = vm.$i18n('KMR004_41');
			}
			vm.$dialog.info({ messageId: "Msg_1856", messageParams: [msgParam]});
		}

		initClosingTimeSwitch(data:any) {
			let vm = this;
			let closingTime2Exists:boolean = false;

			// Init options
			let switchButtons: ItemModel[] = [new ItemModel(1, data.closingTime.reservationFrameName1)]
			if (data.closingTime.reservationFrameName2) {
				switchButtons.push(new ItemModel(2, data.closingTime.reservationFrameName2));
				closingTime2Exists = true;
			}
			vm.closingTimeOptions(switchButtons);

			// init selected
			if (!closingTime2Exists){
				vm.model().reservationClosingTimeFrame(1);
			}
		}

		initClosingTimeLable(data:any) {
			const vm = this;
			let start1 = data.closingTime.reservationStartTime1;
			let end1 = data.closingTime.reservationEndTime1;
			let start2 = data.closingTime.reservationStartTime2;
			let end2 = data.closingTime.reservationEndTime2;
			vm.reservationTimeRange1 = parseTime(start1, true).format()
				+ "～" + parseTime(end1, true).format();
			if (data.closingTime.reservationFrameName2) {
				vm.reservationTimeRange2 = parseTime(start2, true).format()
					+ "～" + parseTime(end2, true).format();
			} else {
				vm.reservationTimeRange2 = "";
			}

			vm.reservationTimeRange(vm.reservationTimeRange1);
			vm.model().reservationClosingTimeFrame.subscribe((value) => {
				if (value == 1) {
					vm.reservationTimeRange(vm.reservationTimeRange1);
				}
				if (value == 2) {
					vm.reservationTimeRange(vm.reservationTimeRange2);
				}
			});
		}

		initWorkplaceList() {
			const vm = this;
			vm.treeGrid = {
				isShowAlreadySet: false,
				isMultipleUse: true,
				isMultiSelect: true,
				startMode: tree.StartMode.WORKPLACE,
				selectedId: vm.model().workplaceIds,
				baseDate: vm.baseDate,
				selectType: tree.SelectionType.SELECT_ALL,
				isShowSelectButton: true,
				isDialog: false,
				maxRows: 10,
				tabindex: -1,
				systemType: tree.SystemType.EMPLOYMENT
			};

			$('#tree-grid').ntsTreeComponent(vm.treeGrid).done(() => {
				if ($('#tree-grid').getDataList().length <= 0) {
					vm.$dialog.info({ messageId: "Msg_177" });
				}
			});
		}

		initWorkLocationList() {
			const vm = this;
			vm.listComponentOption = {
				isShowAlreadySet: false,
				isMultiSelect: false,
				isMultipleUse: true,
				listType: list.ListType.WORKPLACE,
				selectType: list.SelectType.SELECT_FIRST_ITEM ,
				selectedCode: vm.model().workLocationCodes,
				isDialog: false,
				isShowNoSelectRow: false,
				maxRows: 10,
				tabindex: -1
			}

			$('#tree-grid').ntsListComponent(vm.listComponentOption).done(() => {
				if ( $('#tree-grid').getDataList().length <= 0) {
					vm.$dialog.info({ messageId: "Msg_177" });
				}
			});
		}


		showErrorMessage(res: any) {
			//Return Dialog Error
			if (!res.businessException) {
				return;
			}

			let vm = this;

			if (res.messageId == "Msg_1856") {

				if (vm.displayingWorkplaceList == 1) {
					res.parameterIds = [vm.$i18n('Com_Workplace')];
				} else if (vm.displayingWorkplaceList == 2) {
					res.parameterIds = [vm.$i18n('KMR004_41')];
				}
			}

			// show error message
			nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
		}

		saveCharacteristics(){
			const vm = this;
			let c13sData:Characteristics = new Characteristics();
			c13sData.reservationClosingTimeFrame = vm.model().reservationClosingTimeFrame();
			c13sData.outputConditionChecked = vm.outputConditionChecked();
			c13sData.selectedTab = vm.selectedTab();
			c13sData.totalTitle = vm.model().totalTitle();
			c13sData.totalExtractCondition = vm.model().totalExtractCondition();
			c13sData.extractionConditionChecked = vm.model().extractionConditionChecked();
			c13sData.detailTitle = vm.model().detailTitle();
			c13sData.itemExtractCondition = vm.model().itemExtractCondition();
			c13sData.isBreakPage = vm.model().isBreakPage();

			nts.uk.characteristics.save(vm.cacheKey, c13sData);

		}

		restoreScreenState(c13sData){
			const vm = this;
			if (c13sData == undefined) {
				vm.model().reservationClosingTimeFrame(1);
				return;
			}

			if (c13sData.reservationClosingTimeFrame != 1 && c13sData.reservationClosingTimeFrame != 2) {
				vm.model().reservationClosingTimeFrame(1);
			}

			if (vm.closingTimeOptions().length == 1) {
				vm.model().reservationClosingTimeFrame(1);
			}

			vm.model().reservationClosingTimeFrame(c13sData.reservationClosingTimeFrame);
			vm.outputConditionChecked(c13sData.outputConditionChecked);
			vm.selectedTab(c13sData.selectedTab);
			vm.model().totalTitle(c13sData.totalTitle);
			vm.model().totalExtractCondition(c13sData.totalExtractCondition);
			vm.model().extractionConditionChecked(c13sData.extractionConditionChecked);
			vm.model().detailTitle(c13sData.detailTitle);
			vm.model().itemExtractCondition(c13sData.itemExtractCondition);
			vm.model().isBreakPage(c13sData.isBreakPage);
		}
	}

	class Characteristics {
		reservationClosingTimeFrame: number; // A4_3, A4_4
		outputConditionChecked: number; // A5_2, A5_3
		selectedTab: string; // A6_2, A6_3
		totalTitle: string; // A7_2 <-> model().totalTitle
		totalExtractCondition: number; // A8_3, A8_4, A8_5  <-> model().totalExtractCondition
		extractionConditionChecked: boolean; // A8_6  <-> model().extractionConditionChecked
		detailTitle: string; // A9_2 <-> model().detailTitle
		itemExtractCondition: number; // A10_3, A10_4  <-> model().itemExtractCondition
		isBreakPage: boolean; // A10_5  <-> model().isBreakPage
	}

	// define OUTPUT_FORMAT
	export enum OUTPUT_CONDITION {
		TOTAL = <number> 1,
		STATEMENT = <number> 2,
	}

	// define EXTRACT_CONDITION
	export enum EXTRACT_CONDITION {
		UNSPECIFIED = <number> -1,
		ALL = <number> 4,
		ORDERED = <number> 1,
		UN_ORDERED = <number> 2
	}

	class OptionModel {
		id: number;
		name: string;

		constructor(id: number, name: string) {
			this.id = id;
			this.name = name;
		}
	}

	class ItemModel {
		code: number;
		name: string;

		constructor(code: number, name: string) {
			this.code = code;
			this.name = name;
		}
	}

	class OutputCondition{
		workplaceIds: KnockoutObservableArray<string>;
		workLocationCodes: KnockoutObservableArray<string>;
		period: KnockoutObservable<any>;
		reservationClosingTimeFrame: KnockoutObservable<number>;
		totalTitle: KnockoutObservable<string>;
		detailTitle: KnockoutObservable<string>;
		totalExtractCondition: KnockoutObservable<number>;
		itemExtractCondition: KnockoutObservable<number>;
		isBreakPage: KnockoutObservable<boolean>;
		frameNo: KnockoutObservable<number>;
		extractionConditionChecked: KnockoutObservable<boolean>;

		constructor(){
			this.workplaceIds = ko.observableArray([]);
			this.workLocationCodes = ko.observableArray([]);
			this.period = ko.observable({
				startDate: formatDate( new Date(), 'yyyy/MM/dd'),
				endDate: formatDate( new Date(), 'yyyy/MM/dd')
			});
			this.reservationClosingTimeFrame = ko.observable(1); // A4_3, A4_4
			this.totalTitle = ko.observable(""); // A5_2
			this.detailTitle = ko.observable(""); // A5_3
			this.totalExtractCondition = ko.observable(EXTRACT_CONDITION.ORDERED);
			this.itemExtractCondition = ko.observable(EXTRACT_CONDITION.ALL);
			this.isBreakPage = ko.observable(false);
			this.frameNo = ko.observable(-1);
			this.extractionConditionChecked = ko.observable(false);
		};
	}

}