__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            //start combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //001
            self.ComboBoxItemList_001 = ko.observableArray([
                new ComboboxItemModel('1', '隱ｲ遞�'),
                new ComboboxItemModel('2', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ゅｊ�ｼ�'),
                new ComboboxItemModel('3', '髱櫁ｪｲ遞�(髯仙ｺｦ縺ｪ縺暦ｼ�'),
                new ComboboxItemModel('4', '騾壼共雋ｻ(謇句�･蜉�)'),
                new ComboboxItemModel('5', '騾壼共雋ｻ(螳壽悄蛻ｸ蛻ｩ逕ｨ)')
            ]);
            self.ComboBoxCurrentCode_001 = ko.observable(1);
            self.selectedCode_001 = ko.observable('1');
            //end combobox data
            //start checkbox Data
            self.checked_002 = ko.observable(true);
            self.checked_003 = ko.observable(true);
            self.checked_012 = ko.observable(true);
            self.checked_013 = ko.observable(true);
            self.checked_014 = ko.observable(true);
            self.checked_015 = ko.observable(true);
            self.checked_016 = ko.observable(true);
            //end checkbox data
            // start gridlist
            this.gridListItems = ko.observableArray([
                new GridItemModel('001', 'Item1'),
                new GridItemModel('002', 'Item2'),
                new GridItemModel('003', 'Item3'),
                new GridItemModel('004', 'Item4'),
                new GridItemModel('005', 'Item5'),
                new GridItemModel('006', 'Item6'),
                new GridItemModel('007', 'Item7'),
                new GridItemModel('008', 'Item8'),
                new GridItemModel('009', 'Item9'),
                new GridItemModel('010', 'Item10'),
                new GridItemModel('011', 'Item11'),
                new GridItemModel('012', 'Item12'),
                new GridItemModel('013', 'Item13')
            ]);
            self.columns = ko.observableArray([
                { headerText: '鬩幢ｽ｢�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｳ鬩幢ｽ｢隴趣ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｼ鬩幢ｽ｢隴趣ｽ｢�ｿｽ�ｽｽ�ｽｿ�ｿｽ�ｽｽ�ｽｽ', prop: 'code', width: 100 },
                { headerText: '鬮ｯ�ｽｷ�ｿｽ�ｽｽ�ｽｷ髯ｷ�ｽ･�ｿｽ�ｽｽ�ｽｲ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｰ', prop: 'name', width: 150 }
            ]);
            this.gridListCurrentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            //end gridlist
            //start Switch Data
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '隰ｾ�ｽｯ驍ｨ�ｽｦ' },
                { code: '2', name: '隰ｾ�ｽｯ驍ｨ�ｽｦ' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //005 006 007 008 009 010
            self.roundingRules_002_003_005To010 = ko.observableArray([
                { code: '1', name: '蟇ｾ雎｡' },
                { code: '2', name: '蟇ｾ雎｡螟�' }
            ]);
            self.selectedRuleCode_002 = ko.observable(1);
            self.selectedRuleCode_003 = ko.observable(1);
            self.selectedRuleCode_005 = ko.observable(1);
            self.selectedRuleCode_006 = ko.observable(1);
            self.selectedRuleCode_007 = ko.observable(1);
            self.selectedRuleCode_008 = ko.observable(1);
            self.selectedRuleCode_009 = ko.observable(1);
            self.selectedRuleCode_010 = ko.observable(1);
            //011
            self.roundingRules_011 = ko.observableArray([
                { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
            ]);
            self.selectedRuleCode_011 = ko.observable(1);
            //017
            self.roundingRules_017 = ko.observableArray([
                { code: '1', name: '鬆�逶ｮ蛹ｺ蛻�' },
                { code: '2', name: '鬆�逶ｮ蛹ｺ蛻�' },
                { code: '3', name: '鬆�逶ｮ蛹ｺ蛻�' },
                { code: '4', name: '鬆�逶ｮ蛹ｺ蛻�' },
            ]);
            self.selectedRuleCode_017 = ko.observable(1);
            //endSwitch Data
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '髫ｴ蟷｢�ｽｽ�ｽｬ鬩穂ｼ夲ｽｽ�ｽｾ'),
                new BoxModel(2, '髮主供萓幢ｿｽ�ｽｽ�ｽｮ陞溷･�ｽｽ�ｽｪ�ｿｽ�ｽｽ�ｽｿ髫ｴ蜴�ｽｽ�ｽｸ髯ｷ�ｿｽ�ｽｽ�ｽｺ髯ｷ迚呻ｽｸ�ｽｷ騾｡鬘瑚�ｳ陞溘ｑ�ｽｽ�ｽ､�ｿｽ�ｽｽ�ｽｾ')
            ]);
            self.selectedId = ko.observable(1);
            //004
            self.RadioItemList_004 = ko.observableArray([
                new BoxModel(1, '蜈ｨ蜩｡荳�蠕九〒謖�螳壹☆繧�'),
                new BoxModel(2, '邨ｦ荳主･醍ｴ�蠖｢諷九＃縺ｨ縺ｫ謖�螳壹☆繧�')
            ]);
            self.selectedId_004 = ko.observable(1);
            //end radiogroup data
            //currencyeditor
            self.currencyeditor = {
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
            // start search box 
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.gridListItems(), "childs"));
            // end search box 
        }
        return ScreenModel;
    }());
    var GridItemModel = (function () {
        function GridItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return GridItemModel;
    }());
    var ComboboxItemModel = (function () {
        function ComboboxItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return ComboboxItemModel;
    }());
    var BoxModel = (function () {
        function BoxModel(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
        return BoxModel;
    }());
    this.bind(new ScreenModel());
});
