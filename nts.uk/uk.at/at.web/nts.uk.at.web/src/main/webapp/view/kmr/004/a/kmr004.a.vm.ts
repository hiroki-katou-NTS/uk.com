module nts.uk.at.view.kmr004.a {
    const API = {
        START: "screen/at/record/reservation-conf-list/start"
    };

    // const PATH = {
    //     REDIRECT : '/view/ccg/008/a/index.xhtml'
    // }

    import tree = kcp.share.tree;
    import formatDate = nts.uk.time.formatDate;

    @bean()
    export class KMR004AViewModel extends ko.ViewModel {

		// date range picker
        dateRangeValue: KnockoutObservable<any> = ko.observable({
            startDate: formatDate( new Date(), 'yyyy/MM/dd'),
            endDate: formatDate( new Date(), 'yyyy/MM/dd')
        });

        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);

		// base date for KCP004, KCP012
		baseDate: KnockoutObservable<Date> = ko.observable(new Date());

		// selected codes
        multiSelectedId: KnockoutObservable<any> = ko.observableArray([]);
        
		// tree grid properties object
		treeGrid: tree.TreeComponentOption;

		// total checkbox
        //totalChecked: KnockoutObservable<boolean> = ko.observable(true);
        //totalEnable: KnockoutObservable<boolean> = ko.observable(true);

		// conditional checkbox
        //conditionalChecked: KnockoutObservable<boolean> = ko.observable(false);
        //conditionalEnable: KnockoutObservable<boolean> = ko.observable(true);

		// tabs
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([]);
        selectedTab: KnockoutObservable<string> = ko.observable('');

		// text editors
        texteditorOrderTotal: any = ko.observableArray([]);
        texteditorOrderStatement: any = ko.observableArray([]);

        // output condition
        outputConditionChecked: KnockoutObservable<number> = ko.observable(1);

        // total radio group default selected value
        totalRadioSelectedId: KnockoutObservable<number> = ko.observable(2); // Default selected: A8_4 注文済み

        // condition radio group default selected value
        conditionRadioSelected: KnockoutObservable<number> = ko.observable(1); // Default selected: A10_3 全件

        // extraction condition checkbox
        extractionConditionChecked: KnockoutObservable<boolean> = ko.observable(false);
        extractionConditionEnable: KnockoutObservable<boolean> = ko.observable(false);

        // extraction condition checkbox
        separatePageCheckboxChecked: KnockoutObservable<boolean> = ko.observable(false);
        separatePageCheckboxEnable: KnockoutObservable<boolean> = ko.observable(true);

		// condition list checkbox
        conditionListCcb: KnockoutObservableArray<any> = ko.observableArray([]);
        conditionListCcbEnable: KnockoutObservable<boolean> = ko.observable(false);
        conditionCode: KnockoutObservable<number> = ko.observable(1);

        closingTimeOptions: KnockoutObservableArray<any>;

        constructor() {
            super();
            var self = this;

            var strDate: string = formatDate( self.baseDate(), 'yyyy/MM/dd');
            // var strDateForm = ko.toJS({strDate: strDate});
            // self.$ajax(API.START, strDateForm).done((data) => {
            //     if(data.operationDistinction == "BY_COMPANY"){
            //         self.initWorkplaceList();
            //     } else {
            //         console.log(data);
            //         self.initClosingTimeSwitch(data);
            //         self.initWorkLocationList();
            //     }
            // });

            self.selectedRuleCode.subscribe((value) => {
                if (value == 1) {
                    // 開始:弁当予約時間、終了:弁当予約時間 6:00~10:00
                } else {
                    // 開始:弁当予約時間、終了:弁当予約時間 12:00~15:00
                }
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

            self.texteditorOrderTotal = {
                simpleValue: ko.observable(''),
                constraint: 'PrimitiveValue',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: self.$i18n('KMR004_21'),
                    width: "100px",
                    textalign: "left"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            self.texteditorOrderStatement = {
                simpleValue: ko.observable(''),
                constraint: 'PrimitiveValue',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: self.$i18n('KMR004_22'),
                    width: "100px",
                    textalign: "left"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            self.totalRadioSelectedId.subscribe((newValue) => {
                if (newValue == 4) {
                    self.extractionConditionEnable(true);
                } else {
                    self.extractionConditionEnable(false);
                }
            });


            self.conditionRadioSelected.subscribe((newValue) => {
                if (newValue == 1) {
                    self.separatePageCheckboxEnable(true);
                } else {
                    self.separatePageCheckboxEnable(false);
                }

                if (newValue == 6) {
                    self.conditionListCcbEnable(true);
                } else {
                    self.conditionListCcbEnable(false);
                }
            });

            // bento list
            self.conditionListCcb = ko.observableArray([
                new ItemModel('1', '商品１'),
                new ItemModel('2', '商品２')
            ]);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {
        }

        printExcel(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.EXCEL).done((data) => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

        printPDF(){
            let vm = this;
            $("#exportTitle").trigger("validate");
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", API.PDF_ALL).done(() => {
                vm.$blockui("clear");
            }).fail((res: any) => {
                vm.$dialog.error({ messageId : res.messageId }).then(function(){
                    vm.$blockui("clear");
                });
            });
        }

        initClosingTimeSwitch(data:any) {
            let vm = this;
            let closingTime1Name: string = data.closingTime1.reservationTimeName;
            let closingTime2Name: string = data.closingTime2.reservationTimeName;
            console.log(closingTime1Name);
            console.log(closingTime2Name);
            vm.closingTimeOptions = ko.observableArray([
                new ItemModel('1', 'closingTime1Name'),
                new ItemModel('2', 'closingTime2Name')
            ]);
        }

        initWorkplaceList() {
            const self = this;
            self.treeGrid = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: true,
                startMode: tree.StartMode.WORKPLACE,
                selectedId: self.multiSelectedId,
                baseDate: self.baseDate,
                selectType: tree.SelectionType.NO_SELECT,
                isShowSelectButton: true,
                isDialog: false,
                maxRows: 10,
                tabindex: 3,
                systemType: tree.SystemType.EMPLOYMENT
            }

            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                //$('#tree-grid').getDataList();
            });
        }

        initWorkLocationList() {
            $('#tree-grid').append("<div style='width: 514px; height: 365px; text-align: center; font-size: x-large'>Waiting for KCP012...</div>");
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

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
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
}