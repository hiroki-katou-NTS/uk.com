module qmm012.b.viewmodel {
    export class ScreenModel {
        //combobox
        //001
        ComboBoxItemList_B_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_B_001: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //gridlist
        GridlistItems_B_001: KnockoutObservableArray<ItemModel>;
        GridColumns_B_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        GridlistCurrentCode_B_001: KnockoutObservable<any>;
        GridCurrentName_B_001: KnockoutObservable<any>;
        GridCurrentGroup_B_001: KnockoutObservable<any>;
        GridCurrentGroupAndCode_B_001: KnockoutObservable<any>;
        GridCurrentCodeAndName_B_001: KnockoutObservable<any>;
        //Checkbox
        //B_002
        checked_B_002: KnockoutObservable<boolean>;
        //B_003
        checked_B_003: KnockoutObservable<boolean>;
        //E_004
        checked_E_004: KnockoutObservable<boolean>;
        //E_005
        checked_E_005: KnockoutObservable<boolean>;
        //E_006
        checked_E_006: KnockoutObservable<boolean>;
        //E_007
        checked_E_007: KnockoutObservable<boolean>;
        //E_008
        checked_E_008: KnockoutObservable<boolean>;
        //F_002
        checked_F_002: KnockoutObservable<boolean>;
        //search box 
        filteredData: any;
        //combobox
        //001
        ComboBoxItemList_C_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_C_001: KnockoutObservable<string>;
        //001
        ComboBoxItemList_D_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_D_001: KnockoutObservable<string>;
        //Checkbox
        //012
        checked_C_012: KnockoutObservable<boolean>;
        //013
        checked_C_013: KnockoutObservable<boolean>;
        //014
        checked_C_014: KnockoutObservable<boolean>;
        //015
        checked_C_015: KnockoutObservable<boolean>;
        //016
        checked_C_016: KnockoutObservable<boolean>;
        //D_003
        checked_D_003: KnockoutObservable<boolean>;
        //D_003
        checked_D_004: KnockoutObservable<boolean>;
        //D_003
        checked_D_005: KnockoutObservable<boolean>;
        //D_003
        checked_D_006: KnockoutObservable<boolean>;
        //D_003
        checked_D_007: KnockoutObservable<boolean>;
        //Switch
        //002 003 005 006 007 008 009 010
        roundingRules_C_002_003_005To010: KnockoutObservableArray<any>;
        selectedRuleCode_C_002: any;
        selectedRuleCode_C_003: any;
        selectedRuleCode_C_005: any;
        selectedRuleCode_C_006: any;
        selectedRuleCode_C_007: any;
        selectedRuleCode_C_008: any;
        selectedRuleCode_C_009: any;
        selectedRuleCode_C_010: any;
        //011
        roundingRules_C_011: KnockoutObservableArray<any>;
        selectedRuleCode_C_011: any;
        //017
        roundingRules_C_017: KnockoutObservableArray<any>;
        selectedRuleCode_C_017: any;
        //D_002
        roundingRules_D_002: KnockoutObservableArray<any>;
        selectedRuleCode_D_002: any;
        //E_001To003
        roundingRules_E_001To003: KnockoutObservableArray<any>;
        selectedRuleCode_E_001: any;
        selectedRuleCode_E_002: any;
        selectedRuleCode_E_003: any;
        //F 001
        roundingRules_F_001: KnockoutObservableArray<any>;
        selectedRuleCode_F_001: any;
        //end switch
        //radiogroup
        //004
        RadioItemList_C_004: KnockoutObservableArray<any>;
        selectedId_C_004: KnockoutObservable<number>;
        //currencyeditor_C_001
        currencyeditor_C_001: any;
        //textarea
        textArea: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //start combobox data
            //001
            self.ComboBoxItemList_B_001 = ko.observableArray([
                new ComboboxItemModel('1', '隱ｲ遞�'),
                new ComboboxItemModel('2', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ゅｊ�ｼ�'),
                new ComboboxItemModel('3', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ｪ縺暦ｼ�'),
                new ComboboxItemModel('4', '騾壼共雋ｻ(謇句�･蜉�)'),
                new ComboboxItemModel('5', '騾壼共雋ｻ(螳壽悄蛻ｸ蛻ｩ逕ｨ)')
            ]);
            self.selectedCode_B_001 = ko.observable('1')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            // start gridlist
            self.GridlistItems_B_001 = ko.observableArray([
                new ItemModel('001', 'group1', '蝓ｺ譛ｬ邨ｦ1', "description 1"),
                new ItemModel('002', 'group1', '蝓ｺ譛ｬ邨ｦ2', "description 2"),
                new ItemModel('003', 'group2', '蝓ｺ譛ｬ邨ｦ3', "description 3"),
                new ItemModel('004', 'group2', '蝓ｺ譛ｬ邨ｦ4', "description 4"),
                new ItemModel('005', 'group3', '蝓ｺ譛ｬ邨ｦ5', "description 5"),
                new ItemModel('006', 'group3', '蝓ｺ譛ｬ邨ｦ6', "description 6"),
                new ItemModel('007', 'group4', '蝓ｺ譛ｬ邨ｦ5', "description 7"),
                new ItemModel('008', 'group4', '蝓ｺ譛ｬ邨ｦ6', "description 8"),
                new ItemModel('009', 'group5', '蝓ｺ譛ｬ邨ｦ5', "description 7"),
                new ItemModel('010', 'group5', '蝓ｺ譛ｬ邨ｦ6', "description 8")
            ]);
            self.GridColumns_B_001 = ko.observableArray([
                { headerText: '蜷咲ｧｰ', prop: 'group', width: 150 },
                { headerText: '繧ｳ繝ｼ繝�', prop: 'code', width: 100 },
                { headerText: '隱ｬ譏�', prop: 'name', width: 200 }
            ]);
            self.GridlistCurrentCode_B_001 = ko.observable();
            self.GridCurrentName_B_001 = ko.computed(function() {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel) {
                    return ItemModel.code == self.GridlistCurrentCode_B_001();
                });
                return item != null ? item.name : '';
            });
            self.GridCurrentGroup_B_001 = ko.computed(function() {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel) {
                    return ItemModel.code == self.GridlistCurrentCode_B_001();
                });
                return item != null ? item.group : '';
            });
            self.GridCurrentGroupAndCode_B_001 = ko.computed(function() {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel) {
                    return ItemModel.code == self.GridlistCurrentCode_B_001();
                });
                return item != null ? item.group + item.code : '';
            });
            self.GridCurrentCodeAndName_B_001 = ko.computed(function() {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel) {
                    return ItemModel.code == self.GridlistCurrentCode_B_001();
                });
                return item != null ? 'T' + item.code + ' ' + item.name : '';
            });
            //end gridlist
            //checkbox 
            //002
            self.checked_B_002 = ko.observable(true);
            //003
            self.checked_B_003 = ko.observable(true);
            //end checkbox
            //combobox data
            ComboBoxItemList_C_001 = ko.observableArray([
                new ComboboxItemModel('1', '隱ｲ遞�'),
                new ComboboxItemModel('2', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ゅｊ�ｼ�'),
                new ComboboxItemModel('3', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ｪ縺暦ｼ�'),
                new ComboboxItemModel('4', '騾壼共雋ｻ(謇句�･蜉�)'),
                new ComboboxItemModel('5', '騾壼共雋ｻ(螳壽悄蛻ｸ蛻ｩ逕ｨ)')
            ]);
            self.selectedCode_C_001 = ko.observable('1');
            self.selectedCode_D_001 = ko.observable('1');
            self.ComboBoxItemList_D_001 = ko.observableArray([
                new ComboboxItemModel('1', '隱ｲ遞�'),
                new ComboboxItemModel('2', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ゅｊ�ｼ�'),
                new ComboboxItemModel('3', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ｪ縺暦ｼ�'),
                new ComboboxItemModel('4', '騾壼共雋ｻ(謇句�･蜉�)'),
                new ComboboxItemModel('5', '騾壼共雋ｻ(螳壽悄蛻ｸ蛻ｩ逕ｨ)')
            ]);
            self.selectedCode_D_001 = ko.observable('1');
            //end combobox data
            //start checkbox Data
            self.checked_C_012 = ko.observable(true);
            self.checked_C_013 = ko.observable(true);
            self.checked_C_014 = ko.observable(true);
            self.checked_C_015 = ko.observable(true);
            self.checked_C_016 = ko.observable(true);
            //D_003
            self.checked_D_003 = ko.observable(true);
            //D_004
            self.checked_D_004 = ko.observable(true);
            //D_005
            self.checked_D_005 = ko.observable(true);
            //D_006
            self.checked_D_006 = ko.observable(true);
            //D_006
            self.checked_D_007 = ko.observable(true);
            //E_004
            self.checked_E_004 = ko.observable(true);
            //E_005
            self.checked_E_005 = ko.observable(true);
            //E_005
            self.checked_E_006 = ko.observable(true);
            //E_005
            self.checked_E_007 = ko.observable(true);
            //E_005
            self.checked_E_008 = ko.observable(true);
            //E_005
            self.checked_F_002 = ko.observable(true);
            //end checkbox data
            //start Switch Data
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '髫ｰ�ｽｾ�ｿｽ�ｽｽ�ｽｯ鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ' },
                { code: '2', name: '髫ｰ�ｽｾ�ｿｽ�ｽｽ�ｽｯ鬩搾ｽｨ�ｿｽ�ｽｽ�ｽｦ' }
            ]);
            //005 006 007 008 009 010
            self.roundingRules_C_002_003_005To010 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '蟇ｾ雎｡螟�' }
            ]);
            self.selectedRuleCode_C_002 = ko.observable(1);
            self.selectedRuleCode_C_003 = ko.observable(1);
            self.selectedRuleCode_C_005 = ko.observable(1);
            self.selectedRuleCode_C_006 = ko.observable(1);
            self.selectedRuleCode_C_007 = ko.observable(1);
            self.selectedRuleCode_C_008 = ko.observable(1);
            self.selectedRuleCode_C_009 = ko.observable(1);
            self.selectedRuleCode_C_010 = ko.observable(1);
            //011
            self.roundingRules_C_011 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
            ]);
            self.selectedRuleCode_C_011 = ko.observable(1);
            //017
            self.roundingRules_C_017 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '3', name: '繧ｼ繝ｭ繧定｡ｨ' },
                { code: '4', name: '繧ｼ繝ｭ繧定｡ｨ' },
            ]);
            self.selectedRuleCode_C_017 = ko.observable(1);
            //D_002
            self.roundingRules_D_002 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '蟇ｾ雎｡螟�' }
            ]);
            self.selectedRuleCode_D_002 = ko.observable(1);
            //E_001 To 003
            //E_001To003
            self.roundingRules_E_001To003 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '蟇ｾ雎｡螟�' }
            ]);
            self.selectedRuleCode_E_001 = ko.observable(1);
            self.selectedRuleCode_E_002 = ko.observable(1);
            self.selectedRuleCode_E_003 = ko.observable(1);
            //F_001
            self.roundingRules_F_001 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '蟇ｾ雎｡螟�' }
            ]);
            self.selectedRuleCode_F_001 = ko.observable(1);
            //endSwitch Data

                //start radiogroup data
            //004
            self.RadioItemList_C_004 = ko.observableArray([
                new BoxModel(1, '蜈ｨ蜩｡荳�蠕九〒謖�螳壹☆繧�'),
                new BoxModel(2, '邨ｦ荳主･醍ｴ�蠖｢諷九＃縺ｨ縺ｫ謖�螳壹☆繧�')
            ]);
            self.selectedId_C_004 = ko.observable(1);
            //end radiogroup data
            //currencyeditor_C_001
            self.currencyeditor_C_001 = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            //end currencyeditor
            //start textarea
            this.textArea = ko.observable("");
            //end textarea
        }

        start(): JQueryPromise<any> {
            var self = this;
            // Page load dfd.
            var dfd = $.Deferred();
            //dropdownlist event
            //selectedCode_001 changed event
            self.GridCurrentGroup_B_001.subscribe(function(newValue) {
                var itemGroup = self.GridCurrentGroup_B_001();
                $('#Group1').hide();
                $('#Group2').hide();
                $('#Group3').hide();
                $('#Group4').hide();
                if (itemGroup === 'group1') {
                    $('#Group1').show();
                }
                if (itemGroup === 'group2') {
                    $('#Group2').show();
                }
                if (itemGroup === 'group3') {
                    $('#Group3').show();
                }
                if (itemGroup === 'group4') {
                    $('#Group4').show();
                }

            })
            self.selectedRuleCode_C_017.subscribe(function(newValue) {
                if (newValue === '1' || newValue === '3') {
                    $('#C_Div_002').show();
                    $('#C_Div_003').hide();
                }
                if (newValue === '2' || newValue === '4') {
                    $('    #C_Div_002').hide();
                    $('#C_Div_003').show();
                }

            })
            //end switch event
            return dfd.promise();
        }
    }

    class ItemModel {
        code: string;
        group: string;
        name: string;
        description: string;

        constructor(code: string, group: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.group = group;
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

    class GridItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}