var qmm019;
(function (qmm019) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ItemModel = (function () {
                function ItemModel(id, name) {
                    var self = this;
                    this.id = id;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var ListBox = (function () {
                function ListBox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new ItemModel('001', '名前１'),
                        new ItemModel('002', '名前2'),
                        new ItemModel('003', '名前3'),
                        new ItemModel('004', '名前4'),
                        new ItemModel('005', '名前5'),
                        new ItemModel('006', '名前6'),
                        new ItemModel('007', '名前7'),
                        new ItemModel('008', '名前8'),
                        new ItemModel('009', '名前9'),
                        new ItemModel('010', '名前10'),
                        new ItemModel('011', '名前1１'),
                        new ItemModel('012', '名前12'),
                        new ItemModel('013', '名前13'),
                        new ItemModel('014', '名前14'),
                        new ItemModel('015', '名前15'),
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(0);
                    self.selectedCode = ko.observable('001');
                    self.selectedName = ko.computed(function () {
                        var item = _.find(self.itemList(), function (item) {
                            return item.id === self.selectedCode();
                        });
                        return (item !== undefined) ? item.name : null;
                    });
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                }
                return ListBox;
            }());
            viewmodel.ListBox = ListBox;
            var ComboBox = (function () {
                function ComboBox() {
                    var self = this;
                    self.itemListSumScopeAtr = ko.observableArray([
                        new ItemModel(0, '対象外'),
                        new ItemModel(1, '対象内'),
                        new ItemModel(2, '対象外（現物）'),
                        new ItemModel(3, '対象内（現物）')
                    ]);
                    self.itemListCalcMethod0 = ko.observableArray([
                        new ItemModel(0, '手入力'),
                        new ItemModel(1, '個人情報'),
                        new ItemModel(2, '計算式'),
                        new ItemModel(3, '賃金テーブル'),
                        new ItemModel(4, '共通金額')
                    ]);
                    self.itemListCalcMethod1 = ko.observableArray([
                        new ItemModel(0, '手入力'),
                        new ItemModel(1, '個人情報'),
                        new ItemModel(2, '計算式'),
                        new ItemModel(3, '賃金テーブル'),
                        new ItemModel(4, '共通金額'),
                        new ItemModel(5, '支給相殺 ')
                    ]);
                    self.itemListDistributeWay = ko.observableArray([
                        new ItemModel(0, '割合で計算'),
                        new ItemModel(1, '日数控除'),
                        new ItemModel(2, '計算式')
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(3);
                    self.selectedCode = ko.observable(null);
                }
                return ComboBox;
            }());
            viewmodel.ComboBox = ComboBox;
            var SwitchButton = (function () {
                function SwitchButton() {
                    var self = this;
                    self.distributeSet = ko.observableArray([
                        { code: '0', name: '按分しない' },
                        { code: '1', name: '按分する' },
                        { code: '2', name: '月１回支給' }
                    ]);
                    self.selectedRuleCode = ko.observable('1');
                }
                return SwitchButton;
            }());
            viewmodel.SwitchButton = SwitchButton;
            var CheckBox = (function () {
                function CheckBox() {
                    var self = this;
                    self.errRangeLowAtr = ko.observable(true);
                    self.errRangeHighAtr = ko.observable(true);
                    self.alRangeLowAtr = ko.observable(true);
                    self.alRangeHighAtr = ko.observable(true);
                }
                return CheckBox;
            }());
            viewmodel.CheckBox = CheckBox;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.listBox = new ListBox();
                    self.comboBox = new ComboBox();
                    self.switchButton = new SwitchButton();
                    self.checkBox = new CheckBox();
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm019.f || (qmm019.f = {}));
})(qmm019 || (qmm019 = {}));
