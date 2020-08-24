module nts.uk.at.view.kmr004.a {
	const API = {
		START: "screen/at/record/reservation-conf-list/start",
		PDF : "order/report/print/pdf",
		EXCEL : "order/report/print/excel",
		DATE_FORMAT: "yyyy/MM/dd"
	};

	import getText = nts.uk.resource.getText;
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
		closingTimeOptions: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedClosingTime: KnockoutObservable<number> = ko.observable(1);
		reservationTimeRange1: string = '';
		reservationTimeRange2: string = '';
		reservationTimeRange: KnockoutObservable<string> = ko.observable('');
		selectedWorkLocationCode: KnockoutObservableArray<string> = ko.observableArray([]); // KCP012 selected codes
		totalExtractConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);
		itemExtractConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);
		outputConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);
		cacheKey:string;
		displayingWorkplaceList:number = 0;

		constructor() {
			super();
			const self = this;

			self.cacheKey = "kmr004aCache_" + __viewContext.user.companyId + "_" + __viewContext.user.employeeId;

			// Init radios options
			self.totalExtractConditionOptions([
				{id: EXTRACT_CONDITION.ALL, name: getText('KMR004_17')},
				{id: EXTRACT_CONDITION.ORDERED, name: getText('KMR004_18')},
				{id: EXTRACT_CONDITION.UN_ORDERED, name: getText('KMR004_19')}
			]);
			self.itemExtractConditionOptions([
				{id: EXTRACT_CONDITION.ALL, name: getText('KMR004_17')},
				{id: EXTRACT_CONDITION.UNSPECIFIED, name: getText('KMR004_24')}
			]);
			self.outputConditionOptions([
				{id: OUTPUT_CONDITION.TOTAL, name: getText('KMR004_12')},
				{id: OUTPUT_CONDITION.STATEMENT, name: getText('KMR004_13')},
			]);

			nts.uk.ui.block.grayout();
			// Call init API
			self.$ajax(API.START).done((data) => {
				self.startKMR004aScreen(data);
			}).fail(function(res) {
				self.showErrorMessage(res);
			}).always(() => {
				nts.uk.ui.block.clear();
			});

			nts.uk.characteristics.restore(self.cacheKey).done((c13sData: any) => {
				self.restoreScreenState(c13sData);
			});

			self.tabs = ko.observableArray([
				{
					id: 'tab-1',
					title: self.$i18n('KMR004_12'),
					content: '.tab-content-1',
					enable: ko.observable(true),
					visible: ko.observable(true)
				},
				{
					id: 'tab-2',
					title: self.$i18n('KMR004_13'),
					content: '.tab-content-2',
					enable: ko.observable(true),
					visible: ko.observable(true)
				}
			]);

			self.selectedTab = ko.observable('tab-1');

			self.model().totalExtractCondition.subscribe((newValue) => {
				if (newValue == EXTRACT_CONDITION.UN_ORDERED) {
					self.extractionConditionEnable(true);
				} else {
					self.extractionConditionEnable(false);
				}
			});

			self.model().itemExtractCondition.subscribe((newValue) => {
				if (newValue == EXTRACT_CONDITION.ALL) {
					self.separatePageCheckboxEnable(true);
				} else {
					self.separatePageCheckboxEnable(false);
				}

				if (newValue == EXTRACT_CONDITION.UNSPECIFIED) {
					self.conditionListCcbEnable(true);
				} else {
					self.conditionListCcbEnable(false);
				}
			});

			self.model().workLocationCodes.subscribe((selectedWorkLocationCode) => {
				if (selectedWorkLocationCode == null) {
					self.conditionListCcb(self.conditionListCcbAll);
				} else {
					let filtered = self.conditionListCcbAll.filter((item) => {
						if (selectedWorkLocationCode == item.locationCode) {
							return item;
						}
					});
					self.conditionListCcb(filtered);
				}
			});
		}

		created() {
			const vm = this;
			_.extend(window, {vm});
		}

		mounted() {
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
			if(vm.outputConditionChecked() === OUTPUT_CONDITION.TOTAL){
				vm.model().frameNo(-1);
				vm.model().itemExtractCondition(EXTRACT_CONDITION.UNSPECIFIED);
				vm.model().detailTitle = ko.observable('');
				vm.model().isBreakPage = ko.observable(false);
			}else{
				vm.model().totalExtractCondition(EXTRACT_CONDITION.UNSPECIFIED);
				vm.model().totalTitle = ko.observable('');
				vm.model().extractionConditionChecked = ko.observable(false);
				if(vm.model().itemExtractCondition() === EXTRACT_CONDITION.ALL){
					vm.model().frameNo = ko.observable(-1);
				}
			}
			let data = {
				workplaceIds: vm.model().workplaceIds(),
				workLocationCodes: vm.model().workLocationCodes(),
				period: vm.model().period.peek(),
				totalExtractCondition: vm.model().totalExtractCondition.peek(),
				itemExtractCondition: vm.model().itemExtractCondition.peek(),
				frameNo: vm.model().frameNo.peek(),
				totalTitle:  vm.model().totalTitle.peek(),
				detailTitle: vm.model().detailTitle.peek(),
				isBreakPage: true,
				reservationClosingTimeFrame: vm.model().reservationClosingTimeFrame.peek(),
				extractionConditionChecked: vm.model().extractionConditionChecked.peek()
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
					vm.$dialog.error({messageId: res.messageId}).then(function () {
						vm.$blockui("clear");
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
					vm.$dialog.error({messageId: res.messageId}).then(function () {
						vm.$blockui("clear");
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
				msgParam = getText('Com_Workplace');
			} else if (vm.displayingWorkplaceList == 2) {
				msgParam = getText('KMR004_41');
			}
			nts.uk.ui.dialog.info({ messageId: "Msg_1856", messageParams: [msgParam]});
		}

		initClosingTimeSwitch(data:any) {
			let vm = this;
			let closingTime2Exists:boolean = false;

			// Init options
			let switchButtons: ItemModel[] = [new ItemModel(1, data.closingTime.reservationFrameName1)]
			if (data.closingTime.reservationFrameName2.length > 0) {
				switchButtons.push(new ItemModel(2, data.closingTime.reservationFrameName2));
				closingTime2Exists = true;
			}
			vm.closingTimeOptions(switchButtons);

			// init selected
			if (data.closingTime.selectedClosingTime == 2 && closingTime2Exists){
				vm.selectedClosingTime(2);
			} else {
				vm.selectedClosingTime(1);
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
			vm.reservationTimeRange2 = parseTime(start2, true).format()
				+ "～" + parseTime(end2, true).format();

			vm.reservationTimeRange(vm.reservationTimeRange1);
			vm.selectedClosingTime.subscribe((value) => {
				if (value == 1) {
					vm.reservationTimeRange(vm.reservationTimeRange1);
				} else {
					vm.reservationTimeRange(vm.reservationTimeRange2);
				}
			});
		}

		initWorkplaceList() {
			const self = this;
			self.treeGrid = {
				isShowAlreadySet: false,
				isMultipleUse: true,
				isMultiSelect: true,
				startMode: tree.StartMode.WORKPLACE,
				selectedId: self.model().workplaceIds,
				baseDate: self.baseDate,
				selectType: tree.SelectionType.NO_SELECT,
				isShowSelectButton: true,
				isDialog: false,
				maxRows: 10,
				tabindex: 3,
				systemType: tree.SystemType.EMPLOYMENT
			};

			$('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
			   if ($('#tree-grid').getDataList().length <= 0) {
				   self.showDialogForEmptyData();
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
				selectType: list.SelectType.NO_SELECT,
				selectedCode: vm.model().workLocationCodes,
				isDialog: false,
				isShowNoSelectRow: false,
				maxRows: 10
			}

			$('#tree-grid').ntsListComponent(vm.listComponentOption).done(() => {
				if ( $('#tree-grid').getDataList().length <= 0) {
					vm.showDialogForEmptyData();
				}
			});
		}

		showDialogForEmptyData(){
			nts.uk.ui.dialog.info({ messageId: "Msg_177" });
		};
		
		showErrorMessage(res: any) {
			//Return Dialog Error
			if (!res.businessException) {
				return;
			}

			// show error message
			nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
		}

   		saveCharacteristics(){
			const vm = this;
			let c13sData:Characteristics = new Characteristics();
			c13sData.selectedClosingTime = vm.selectedClosingTime();
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
			if (c13sData == undefined) {
				return;
			}

			const vm = this;
			vm.selectedClosingTime(c13sData.selectedClosingTime);
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
		selectedClosingTime: number; // A4_3, A4_4
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
	enum OUTPUT_CONDITION {
		TOTAL = <number> 1,
		STATEMENT = <number> 2,
	}

	// define EXTRACT_CONDITION
	enum EXTRACT_CONDITION {
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