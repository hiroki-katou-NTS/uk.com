// TreeGrid Node
var qpp014;
(function (qpp014) {
    var g;
    (function (g) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //DatePicker
                self.date_G_INP_001 = ko.observable(new Date('2016/12/01'));
                //combobox
                //G_SEL_001
                self.itemList_G_SEL_001 = ko.observableArray([
                    new ItemModel_G_SEL_001('���{��1', '���{��'),
                    new ItemModel_G_SEL_001('���{��2', '���E�蓖'),
                    new ItemModel_G_SEL_001('0003', '���{��')
                ]);
                self.selectedCode_G_SEL_001 = ko.observable('0002');
                //G_SEL_002
                self.itemList_G_SEL_002 = ko.observableArray([
                    new ItemModel_G_SEL_002('���{��1', '���{��'),
                    new ItemModel_G_SEL_002('���{��2', '���E�蓖'),
                    new ItemModel_G_SEL_002('0003', '���{��')
                ]);
                self.selectedCode_G_SEL_002 = ko.observable('0002');
                //switch
                //SEL_003
                self.roundingRules_G_SEL_003 = ko.observableArray([
                    { code: '1', name: '�l�̌ܓ�' },
                    { code: '2', name: '�؂��グ' }
                ]);
                self.selectedRuleCode_G_SEL_003 = ko.observable(1);
                //numbereditor
                self.numbereditor_G_INP_002 = {
                    value: ko.observable(12),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                        grouplength: 3,
                        decimallength: 2,
                        placeholder: "Placeholder for number editor",
                        width: "",
                        textalign: "left"
                    })),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            }
            return ScreenModel;
        }());
        g.ScreenModel = ScreenModel;
        var ItemModel_G_SEL_001 = (function () {
            function ItemModel_G_SEL_001(code, name) {
                this.code = code;
                this.name = name;
            }
            return ItemModel_G_SEL_001;
        }());
        g.ItemModel_G_SEL_001 = ItemModel_G_SEL_001;
        var ItemModel_G_SEL_002 = (function () {
            function ItemModel_G_SEL_002(code, name) {
                this.code = code;
                this.name = name;
            }
            return ItemModel_G_SEL_002;
        }());
        g.ItemModel_G_SEL_002 = ItemModel_G_SEL_002;
    })(g = qpp014.g || (qpp014.g = {}));
})(qpp014 || (qpp014 = {}));
;
