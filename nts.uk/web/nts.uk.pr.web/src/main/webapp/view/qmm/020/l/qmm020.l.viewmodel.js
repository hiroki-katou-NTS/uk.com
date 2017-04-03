var qmm020;
(function (qmm020) {
    var l;
    (function (l) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.selectLayoutAtr = ko.observable("3");
                    self.itemList = ko.observableArray([]);
                    self.isEnable = ko.observable(true);
                    self.roundingRulesMaster = ko.observableArray([
                        { code: '1', name: '利用する' },
                        { code: '2', name: '利用しない' }
                    ]);
                    self.selectedRuleCodeMaster = ko.observable(1);
                    self.roundingRulesEmployee = ko.observableArray([
                        { code: '1', name: '利用する' },
                        { code: '2', name: '利用しない' }
                    ]);
                    self.selectedRuleCodeEmployee = ko.observable(1);
                    self.selectedRuleCodeMaster.subscribe(function (codeChange) {
                    });
                    self.itemListRadio = ko.observableArray([
                        new BoxModel(1, '雇用'),
                        new BoxModel(2, '部門'),
                        new BoxModel(3, '分類'),
                        new BoxModel(4, '職位'),
                        new BoxModel(5, '給与分類')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.startDialog = function () {
                    var self = this;
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.settingScreen = function () {
                    var self = this;
                    var settingValue = self.selectedRuleCodeMaster() + "~" + self.selectedId() + "~" + self.selectedRuleCodeEmployee();
                    nts.uk.ui.windows.setShared('arrSettingVal', settingValue);
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            var ItemModel = (function () {
                function ItemModel(stt, printType, paperType, direction, numberPeople, numberDisplayItems, reference) {
                    this.stt = stt;
                    this.printType = printType;
                    this.paperType = paperType;
                    this.direction = direction;
                    this.numberPeople = numberPeople;
                    this.numberDisplayItems = numberDisplayItems;
                    this.reference = reference;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
    })(l = qmm020.l || (qmm020.l = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.l.viewmodel.js.map