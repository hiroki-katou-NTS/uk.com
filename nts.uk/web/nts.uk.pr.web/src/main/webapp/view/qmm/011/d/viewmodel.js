var qmm011;
(function (qmm011) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.items = ko.observableArray([
                        new ItemModel('001', '基本給', "description 1"),
                        new ItemModel('150', '役職手当', "description 2"),
                        new ItemModel('ABC', '基12本ghj給', "description 3")
                    ]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 },
                        { headerText: '説明', prop: 'description', width: 200 }
                    ]);
                    this.currentCode = ko.observable();
                    this.currentCodeList = ko.observableArray([]);
                }
                ScreenModel.prototype.selectSomeItems = function () {
                    this.currentCode('150');
                    this.currentCodeList.removeAll();
                    this.currentCodeList.push('001');
                    this.currentCodeList.push('ABC');
                };
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                    this.currentCodeList.removeAll();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, description) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm011.d || (qmm011.d = {}));
})(qmm011 || (qmm011 = {}));
