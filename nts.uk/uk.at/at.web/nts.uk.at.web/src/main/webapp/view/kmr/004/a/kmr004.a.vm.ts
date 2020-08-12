module nts.uk.at.view.kmr004.a {
    import getText = nts.uk.resource.getText;
    import tree = kcp.share.tree;
    import block = nts.uk.ui.block;

    @bean()
	export class KMR004AViewModel extends ko.ViewModel {

        dateRangeValue: KnockoutObservable<any>;

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<number>;

        multiSelectedId: KnockoutObservable<any>;
        baseDate: KnockoutObservable<Date>;
        alreadySettingList: KnockoutObservableArray<tree.UnitAlreadySettingModel>;
        treeGrid: tree.TreeComponentOption;

		checked1: KnockoutObservable<boolean> = ko.observable(true);
		enable1: KnockoutObservable<boolean> = ko.observable(true);

        checked2: KnockoutObservable<boolean> = ko.observable(false);
        enable2: KnockoutObservable<boolean> = ko.observable(true);

		tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
		selectedTab: KnockoutObservable<string>;
		
		texteditorOrderTotal: any;
        texteditorOrderStatement: any;

		// radio group 1
        totalRadio: KnockoutObservableArray<any>;
        totalRadioSelectedId: KnockoutObservable<number>;
        totalRadioEnable: KnockoutObservable<boolean>;
		
		// radio group 2
        conditionRadio: KnockoutObservableArray<any>;
        conditionRadioSelected: KnockoutObservable<number>;
        conditionRadioEnable: KnockoutObservable<boolean>;

		// extraction condition checkbox
        extractionConditionChecked: KnockoutObservable<boolean>;
        extractionConditionEnable: KnockoutObservable<boolean>;

        // extraction condition checkbox
        separatePageCheckboxChecked: KnockoutObservable<boolean>;
        separatePageCheckboxEnable: KnockoutObservable<boolean>;

        conditionListCcb: KnockoutObservableArray<any>;
		conditionListCcbEnable: KnockoutObservable<boolean>;
        conditionCode: KnockoutObservable<number>;

        constructor() {
            super();
            var self = this;

            self.dateRangeValue = ko.observable({ startDate: new Date(), endDate: new Date() });

            self.roundingRules = ko.observableArray([
                { code: '1', name: getText('KMR004_8') },
                { code: '2', name: getText('KMR004_9') }
            ]);
            self.selectedRuleCode = ko.observable(1);
			
            // tree grid
            self.baseDate =  ko.observable(new Date());
            self.multiSelectedId = ko.observableArray([]);
            //self.alreadySettingList = ko.observableArray([]);
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
                //alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 3,
                systemType: tree.SystemType.EMPLOYMENT
            }

			$('#tree-grid').ntsTreeComponent(self.treeGrid).done(()=>{
                $('#tree-grid').getDataList();
            });

            self.selectedRuleCode.subscribe((value) => {
                if(value == 1){
                    $('#tree-grid').show();
                } else {
                    $('#tree-grid').hide();
                }
            });

            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('KMR004_12'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('KMR004_13'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
            ]);

		    self.selectedTab = ko.observable('tab-1');

			self.texteditorOrderTotal = {
				simpleValue: ko.observable(''),
				constraint: 'PrimitiveValue',
				option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
					textmode: "text",
					placeholder: getText('KMR004_21'),
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
                    placeholder: getText('KMR004_22'),
                    width: "100px",
                    textalign: "left"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            // A8-2
            self.totalRadio = ko.observableArray([
                new BoxModel(1, getText('KMR004_17')),
                new BoxModel(2, getText('KMR004_18')),
                new BoxModel(4, getText('KMR004_19'))
            ]);

            self.totalRadioEnable = ko.observable(true);
			self.totalRadioSelectedId = ko.observable(0);
			self.extractionConditionChecked = ko.observable(false);
            self.extractionConditionEnable = ko.observable(false);
            self.totalRadioSelectedId.subscribe((newValue)=>{
                if (newValue == 4){
                    self.extractionConditionEnable(true);
                } else {
                    self.extractionConditionEnable(false);
                }
            });

    		self.conditionRadio = ko.observableArray([
                new BoxModel(1, getText('KMR004_17')),
				new BoxModel(6, getText('KMR004_24'))
            ]);

            self.conditionRadioSelected = ko.observable(0);
            self.conditionRadioEnable = ko.observable(true);
			self.conditionListCcbEnable = ko.observable(false);
			self.conditionRadioSelected.subscribe((newValue)=>{
				console.log(newValue);
                if (newValue == 1){
					self.separatePageCheckboxEnable(true);
                } else {
					self.separatePageCheckboxEnable(false);
				}

				if (newValue == 6){
					self.conditionListCcbEnable(true);
                } else {
					self.conditionListCcbEnable(false);
				}
            });


            self.separatePageCheckboxChecked = ko.observable(false);
			self.separatePageCheckboxChecked.subscribe((newValue)=>{
				if (newValue){
                    self.conditionRadioSelected = ko.observable(0);
                }
                
            });
			
            self.separatePageCheckboxEnable = ko.observable(false);
            self.totalRadioSelectedId.subscribe((newValue)=>{
                if (newValue == 4){
                    self.separatePageCheckboxEnable(true);
                } else {
                    self.separatePageCheckboxEnable(false);
                }
            });

			self.conditionListCcb = ko.observableArray([
                new ItemModel('1', '商品１'),
				new ItemModel('2', '商品２')
			]);

			self.conditionCode = ko.observable(1);

        }

        created() {
            const vm = this;

            _.extend(window, { vm });
        }

        mounted() {
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
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