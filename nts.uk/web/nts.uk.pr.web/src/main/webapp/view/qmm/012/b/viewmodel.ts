module qmm012.b.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
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
        //search box 
        filteredData: any;
        //combobox
        //end switch
        //radiogroup

        //textarea
        textArea: KnockoutObservable<any>;
        //shared
        groupName: KnockoutObservable<any>;
        constructor() {
            let self = this;
            //start combobox data
            //001
            self.ComboBoxItemList_B_001 = ko.observableArray([
                new ComboboxItemModel('1', '蜈ｨ莉ｶ'),
                new ComboboxItemModel('2', '謾ｯ邨ｦ鬆�逶ｮ'),
                new ComboboxItemModel('3', '謗ｧ髯､鬆�逶ｮ'),
                new ComboboxItemModel('4', '蜍､諤�鬆�逶ｮ'),
                new ComboboxItemModel('5', '險倅ｺ矩��逶ｮ'),
                new ComboboxItemModel('6', '縺昴�ｮ莉夜��逶ｮ')
            ]);
            self.selectedCode_B_001 = ko.observable('1')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //start textarea
            self.textArea = ko.observable("");
            // start gridlist
            self.GridlistItems_B_001 = ko.observableArray([
                new ItemModel('001', 'group1', '謾ｯ邨ｦ鬆�逶ｮ1', "description 1"),
                new ItemModel('002', 'group1', '謾ｯ邨ｦ鬆�逶ｮ2', "description 2"),
                new ItemModel('003', 'group2', '謾ｯ邨ｦ鬆�逶ｮ3', "description 3"),
                new ItemModel('004', 'group2', '謾ｯ邨ｦ鬆�逶ｮ4', "description 4"),
                new ItemModel('005', 'group3', '謾ｯ邨ｦ鬆�逶ｮ5', "description 5"),
                new ItemModel('006', 'group3', '謾ｯ邨ｦ鬆�逶ｮ6', "description 6"),
                new ItemModel('007', 'group4', '謾ｯ邨ｦ鬆�逶ｮ7', "description 7"),
                new ItemModel('008', 'group4', '謾ｯ邨ｦ鬆�逶ｮ8', "description 8"),
                new ItemModel('009', 'group5', '謾ｯ邨ｦ鬆�逶ｮ9', "description 9"),
                new ItemModel('010', 'group5', '謾ｯ邨ｦ鬆�逶ｮ10', "description 10")
            ]);
            self.GridColumns_B_001 = ko.observableArray([
                { headerText: '陷ｷ蜥ｲ�ｽｧ�ｽｰ', prop: 'group', width: 150 },
                { headerText: '郢ｧ�ｽｳ郢晢ｽｼ郢晢ｿｽ', prop: 'code', width: 100 },
                { headerText: '髫ｱ�ｽｬ隴擾ｿｽ', prop: 'name', width: 200 }
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
            //end checkbox data
            //start Switch Data
            self.enable = ko.observable(true);
            //endSwitch Data
            //end currencyeditor
            //start textarea
            self.textArea = ko.observable("");
            //end textarea
            self.GridCurrentGroup_B_001.subscribe(function(newValue) {
                $('#Group1').hide();
                $('#Group2').hide();
                $('#Group3').hide();
                $('#Group4').hide();
                switch (newValue) {
                    case 'group1':
                        $('#Group1').show();
                        break;
                    case 'group2':
                        $('#Group2').show();
                        break;
                    case 'group3':
                        $('#Group3').show();
                        break;
                    case 'group4':
                        $('#Group4').show();
                        break;
                }

            });

        }

        start(): JQueryPromise<any> {
            var self = this;
            // Page load dfd.
            var dfd = $.Deferred();
            //dropdownlist event
        }

        openADialog() {
            nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function(): any {
                let newValue = nts.uk.ui.windows.getShared('groupName');
                $('#Group1').hide();
                $('#Group2').hide();
                $('#Group3').hide();
                $('#Group4').hide();
                switch (newValue) {
                    case 'group1':
                        $('#Group1').show();
                        break;
                    case 'group2':
                        $('#Group2').show();
                        break;
                    case 'group3':
                        $('#Group3').show();
                        break;
                    case 'group4':
                        $('#Group4').show();
                        break;
                }
            });
        }
    }

    class ItemModel {
        code: string;
        group: string;
        name: string; 0
        description: string;

        constructor(code: string, group: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.group = group;
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