var qpp021;
(function (qpp021) {
    var a;
    (function (a) {
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
            var Listbox = (function () {
                function Listbox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000001', '日通　社員１'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000002', '日通　社員2'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000003', '日通　社員3'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000004', '日通　社員4'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000005', '日通　社員5'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000006', '日通　社員6'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000007', '日通　社員7'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000008', '日通　社員8'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000009', '日通　社員9'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000010', '日通　社員10'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000011', '日通　社員１1'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000012', '日通　社員12'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000013', '日通　社員13'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000014', '日通　社員14'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000015', '日通　社員15'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000016', '日通　社員16'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000017', '日通　社員17'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000018', '日通　社員18'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000019', '日通　社員19'),
                        new qpp021.a.viewmodel.ItemModel('A00000000000000000000000000000000020', '日通　社員20')
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(3);
                    self.selectedCode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                }
                return Listbox;
            }());
            viewmodel.Listbox = Listbox;
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.isEnable = ko.observable(true);
                    var self = this;
                    self.listBox = new Listbox();
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp021.a || (qpp021.a = {}));
})(qpp021 || (qpp021 = {}));
