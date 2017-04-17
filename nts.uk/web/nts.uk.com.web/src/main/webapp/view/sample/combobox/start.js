__viewContext.ready(function () {
    var ItemModelCbb1 = (function () {
        function ItemModelCbb1(codeCbb1, nameCbb1) {
            this.codeCbb1 = codeCbb1;
            this.nameCbb1 = nameCbb1;
        }
        return ItemModelCbb1;
    }());
    var ItemModelCbb2 = (function () {
        function ItemModelCbb2(nameCbb2) {
            this.nameCbb2 = nameCbb2;
            this.labelCbb2 = nameCbb2;
        }
        return ItemModelCbb2;
    }());
    var ItemModelCbb3 = (function () {
        function ItemModelCbb3(codeCbb3, nameCbb3) {
            this.codeCbb3 = codeCbb3;
            this.nameCbb3 = nameCbb3;
        }
        return ItemModelCbb3;
    }());
    var ItemModel2 = (function () {
        function ItemModel2(name) {
            this.name = name;
        }
        return ItemModel2;
    }());
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0003');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.yearmontheditor = {
                value: ko.observable(200001),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                    inputFormat: 'yearmonth'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.items = ko.observableArray([
                new ItemModel2('基本給'),
                new ItemModel2('基本給3'),
                new ItemModel2('基本給2'),
                new ItemModel2('基本給1')
            ]);
            self.columns = ko.observableArray([
                { headerText: '名称', prop: 'name', width: 150 }
            ]);
            self.currentCode2 = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
            self.itemListCbb1 = ko.observableArray([
                new ItemModelCbb1('1', '基本給'),
                new ItemModelCbb1('2', '役職手当'),
                new ItemModelCbb1('3', '基本給')
            ]);
            self.itemNameCbb1 = ko.observable('');
            self.currentCodeCbb1 = ko.observable(3);
            self.selectedCodeCbb1 = ko.observable('0002');
            self.isEnableCbb1 = ko.observable(true);
            self.isEditableCbb1 = ko.observable(true);
            self.itemListCbb2 = ko.observableArray([
                new ItemModelCbb2('基本給'),
                new ItemModelCbb2('役職手当'),
                new ItemModelCbb2('基本給2')
            ]);
            self.selectedCodeCbb2 = ko.observable('基本給');
            self.isEnableCbb2 = ko.observable(true);
            self.isEditableCbb2 = ko.observable(true);
            self.itemListCbb3 = ko.observableArray([
                new ItemModelCbb3('基本給1', '基本給'),
                new ItemModelCbb3('基本給2', '役職手当'),
                new ItemModelCbb3('0003', '基本給')
            ]);
            self.itemNameCbb3 = ko.observable('');
            self.currentCodeCbb3 = ko.observable(3);
            self.selectedCodeCbb3 = ko.observable('');
            self.isEnableCbb3 = ko.observable(true);
            self.isEditableCbb3 = ko.observable(true);
            self.texteditor1 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "2016/03",
                    width: "50px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor2 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "2016/03",
                    width: "50px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        ScreenModel.prototype.addOptions = function () {
            var self = this;
            var newCode = self.currentCode() + 1;
            var itemCode = newCode.toString();
            var codeLength = itemCode.length;
            while (codeLength < 4) {
                itemCode = '0' + itemCode;
                codeLength++;
            }
            self.itemList.push(new ItemModel(itemCode, self.itemName()));
            self.currentCode(newCode);
        };
        ScreenModel.prototype.clearOptions = function () {
            this.itemList([]);
        };
        ScreenModel.prototype.removeByCode = function () {
            var self = this;
            var selected = self.itemListCbb1().filter(function (item) { return item.codeCbb1 == self.selectedCode(); })[0];
            self.itemListCbb1.remove(selected);
        };
        return ScreenModel;
    }());
    ;
    var ItemModel = (function () {
        function ItemModel(code, name) {
            this.code = code;
            this.name = name;
            this.label = "ete";
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map