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
		cacheData:KnockoutObservable<any> = ko.observable();
		selectedRuleCode: KnockoutObservable<string> = ko.observable('1');
		baseDate: KnockoutObservable<Date> = ko.observable(new Date()); // base date for KCP004, KCP012
		treeGrid: tree.TreeComponentOption; // tree grid properties object
		listComponentOption: list.ComponentOption;
		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([]);
		selectedTab: KnockoutObservable<string> = ko.observable('');
		outputConditionChecked: KnockoutObservable<number> = ko.observable(OUTPUT_CONDITION.TOTAL); // output condition

		extractionConditionEnable: KnockoutObservable<boolean> = ko.observable(false);
		separatePageCheckboxEnable: KnockoutObservable<boolean> = ko.observable(true);
		conditionListCcb: KnockoutObservableArray<any> = ko.observableArray([]);
		conditionListCcbEnable: KnockoutObservable<boolean> = ko.observable(false);
		closingTimeOptions: KnockoutObservableArray<any> = ko.observableArray([]);
		reservationTimeRange1: string = '';
		reservationTimeRange2: string = '';
		reservationTimeRange: KnockoutObservable<string> = ko.observable('');
		selectedWorkLocationCode: KnockoutObservableArray<string> = ko.observableArray([]); // KCP012 selected codes
		totalExtractConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);
		itemExtractConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);
		outputConditionOptions: KnockoutObservableArray<OptionModel> = ko.observableArray([]);

		constructor() {
			super();
			var self = this;
			
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
			var companyId: string = __viewContext.user.companyId;
			var employeeId: string = __viewContext.user.employeeId;
			var cacheKey:string =  "kmr004aCache_" + companyId + "_" + employeeId;

			nts.uk.characteristics.restore(cacheKey).done((cacheData: any) => {
				if (cacheData != undefined) {
					self.startKMR004aScreen(cacheData);
				} else {
					// Call init API
					self.$ajax(API.START).done((newData) => {
						nts.uk.characteristics.save(cacheKey, newData);
						self.startKMR004aScreen(newData);
					}).fail(function(res) {
						self.showErrorMessage(res);
					}).always(() => {
						nts.uk.ui.block.clear();
					});
				}
			}).always(() => {
				nts.uk.ui.block.clear();
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
			} else {
				vm.initWorkLocationList();
			}
			vm.initConditionListComboBox(data);
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
			vm.$blockui("invisible");
			let data = vm.prepareData();
			nts.uk.request.exportFile("at", API.EXCEL,data).done(() => {
				vm.$blockui("clear");
			}).fail((res: any) => {
				vm.$dialog.error({ messageId : res.messageId }).then(function(){
					vm.$blockui("clear");
				});
			});
		}

		printPDF(){
			let vm = this;
			vm.$blockui("invisible");
			let data = vm.prepareData();
			$("#exportTitle").trigger("validate");
			nts.uk.request.exportFile("at", API.PDF, data).done(() => {
				vm.$blockui("clear");
			}).fail((res: any) => {
				vm.$dialog.error({ messageId : res.messageId }).then(function(){
					vm.$blockui("clear");
				});
			});
		}

		initClosingTimeSwitch(data:any) {
			let vm = this;
			var switchButtons: ItemModel[] = [new ItemModel('1', data.closingTime.reservationFrameName1)]
			if (data.closingTime.reservationFrameName2.length > 0) {
				switchButtons.push(new ItemModel('2', data.closingTime.reservationFrameName2));
			}
			vm.closingTimeOptions(switchButtons);
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
			vm.selectedRuleCode.subscribe((value) => {
				if (value == '1') {
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
			}

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
			let dfd = $.Deferred<void>();
			//Return Dialog Error
			if (!res.businessException) {
				return;
			}

			// show error message
			nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
		}

		initConditionListComboBox(data: any) {
			const vm = this;
			// bento list
			vm.conditionListCcb(data.menu.map((item) => {
				return {
					code: (new Number(item.code)).toString(),
					name: item.name
				}
			}));
		}
	}
	
	// define OUTPUT_FORMAT
	enum OUTPUT_CONDITION {
		TOTAL = <number> 1,
		STATEMENT = <number> 2,
	}

	// define EXTRACT_CONDITION
	enum EXTRACT_CONDITION {
		UNSPECIFIED = <number> -1,
		MORE_THAN_1_PRODUCT = <number> 0,
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
		code: string;
		name: string;

		constructor(code: string, name: string) {
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
			this.reservationClosingTimeFrame = ko.observable(1);
			this.totalTitle = ko.observable("");
			this.detailTitle = ko.observable("");
			this.totalExtractCondition = ko.observable(EXTRACT_CONDITION.ORDERED);
			this.itemExtractCondition = ko.observable(EXTRACT_CONDITION.ALL);
			this.isBreakPage = ko.observable(false);
			this.frameNo = ko.observable(-1);
			this.extractionConditionChecked = ko.observable(false);
		};
	}
}