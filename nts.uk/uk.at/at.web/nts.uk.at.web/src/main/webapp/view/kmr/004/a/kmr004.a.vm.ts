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
        selectedRuleCode: KnockoutObservable<string> = ko.observable('1');
		baseDate: KnockoutObservable<Date> = ko.observable(new Date()); // base date for KCP004, KCP012
		treeGrid: tree.TreeComponentOption; // tree grid properties object
		listComponentOption: list.ComponentOption;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([]);
        selectedTab: KnockoutObservable<string> = ko.observable('');
        outputConditionChecked: KnockoutObservable<number> = ko.observable(1); // output condition

        extractionConditionEnable: KnockoutObservable<boolean> = ko.observable(false);
        separatePageCheckboxEnable: KnockoutObservable<boolean> = ko.observable(true);
        conditionListCcb: KnockoutObservableArray<any> = ko.observableArray([]);
        conditionListCcbEnable: KnockoutObservable<boolean> = ko.observable(false);
        closingTimeOptions: KnockoutObservableArray<any> = ko.observableArray([]);
        reservationTimeRange1: string = '';
        reservationTimeRange2: string = '';
        reservationTimeRange: KnockoutObservable<string> = ko.observable('');
		selectedWorkLocationCode: KnockoutObservableArray<string> = ko.observableArray([]); // KCP012 selected codes

        constructor() {
            super();
            var self = this;

            self.$ajax(API.START).done((data) => {
                // self.$blockui("invisible");
                nts.uk.ui.block.grayout();
                self.initClosingTimeLable(data);
                self.initClosingTimeSwitch(data);
                if(data.operationDistinction == "BY_COMPANY"){
                    self.initWorkplaceList();
                } else {
                    self.initWorkLocationList();
                }
                self.$blockui("clear");
                self.initConditionListComboBox(data);
            }).fail(function(res) {
                //Return Dialog Error
                if (!res.businessException) {
                    return;
                }

                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }

                self.$blockui("clear");
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
                if (newValue == 4) {
                    self.extractionConditionEnable(true);
                } else {
                    self.extractionConditionEnable(false);
                }
            });

            self.model().itemExtractCondition.subscribe((newValue) => {
                if (newValue == 1) {
                    self.separatePageCheckboxEnable(true);
                } else {
                    self.separatePageCheckboxEnable(false);
                }

                if (newValue == -1) {
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

        prepareData():any{
            let vm = this;
            if(vm.outputConditionChecked() === 1){
                vm.model().frameNo = ko.observable(-1);
                vm.model().itemExtractCondition = ko.observable(-1);
                vm.model().detailTitle = ko.observable('');
                vm.model().isBreakPage = ko.observable(false);
            }else{
                vm.model().totalExtractCondition = ko.observable(-1);
                vm.model().totalTitle = ko.observable('');
                vm.model().extractionConditionChecked = ko.observable(false);
                if(vm.model().itemExtractCondition === ko.observable(1)){
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
            vm.$blockui("invisible");
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
               if ( $('#tree-grid').getDataList().length <= 0) {
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
                $('#tree-grid').getDataList().done(() => {
                    if ( $('#tree-grid').getDataList().length <= 0) {
                        vm.showDialogForEmptyData();
                    }
                });
            });
        }

        showDialogForEmptyData(){
            nts.uk.ui.dialog.info({ messageId: "Msg_177" });
        };

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

        saveCharacteristic(companyId: string, userId: string, obj: any): void {
            nts.uk.characteristics.save("kmr004a" +
                "_companyId_" + companyId +
                "_userId_" + userId, obj);
        }

        restoreCharacteristic(companyId: string, userId: string): JQueryPromise<any> {
            return nts.uk.characteristics.restore("kmr004a" +
                "_companyId_" + companyId +
                "_userId_" + userId);
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
            this.totalExtractCondition = ko.observable(2); // Default selected: A8_4 注文済み
            this.itemExtractCondition = ko.observable(1); // Default selected: A10_3 全件
            this.isBreakPage = ko.observable(false);
            this.frameNo = ko.observable(-1);
            this.extractionConditionChecked = ko.observable(false);
        };
    }
}