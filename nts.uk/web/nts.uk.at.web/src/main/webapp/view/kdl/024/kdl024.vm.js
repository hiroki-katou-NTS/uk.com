var kdl024;
(function (kdl024) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.roundingRules = ko.observableArray([
                        { unitId: '1', unitName: '日別' },
                        { unitId: '2', unitName: '時間帯別' }
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                    self.itemListCbb = ko.observableArray([
                        new ItemModelCbb('1', '時間'),
                        new ItemModelCbb('2', '人数'),
                        new ItemModelCbb('3', '金額'),
                        new ItemModelCbb('4', '数値'),
                        new ItemModelCbb('5', '単価')
                    ]);
                    self.selectedCodeCbb = ko.observable('1');
                    self.isEnableCbb = ko.observable(true);
                    self.currentCode = ko.observable('0');
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'externalBudgetCode', width: 40 },
                        { headerText: '名称', key: 'externalBudgetName', width: 150 }
                    ]);
                    self.items = ko.observableArray([]);
                    self.currentItem = ko.observable(new Item({
                        externalBudgetCode: '',
                        externalBudgetName: '',
                        attribute: 0,
                        unit: 0
                    }));
                    self.start();
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    kdl024.service.getListExternalBudget().done(function (lstBudget) {
                        if (lstBudget.length > 0) {
                            self.items(lstBudget);
                            self.currentItem().setSource(lstBudget);
                            self.currentItem().externalBudgetCode(lstBudget[0].externalBudgetCode);
                            console.log(self.currentItem().externalBudgetCode());
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Item = (function () {
                function Item(p) {
                    var self = this;
                    self.externalBudgetCode = ko.observable(p.externalBudgetCode);
                    self.externalBudgetName = ko.observable(p.externalBudgetName);
                    self.attribute = ko.observable(p.attribute);
                    self.unit = ko.observable(p.unit);
                    console.log(self.externalBudgetCode());
                    self.externalBudgetCode.subscribe(function (newValue) {
                        var current = _.find(self.listSource, function (item) { return item.externalBudgetCode == newValue; });
                        if (current) {
                            self.externalBudgetCode(current.externalBudgetCode);
                            self.externalBudgetName(current.externalBudgetName);
                            self.attribute(current.attribute);
                            self.unit(current.unit);
                        }
                    });
                }
                Item.prototype.setSource = function (list) {
                    var self = this;
                    self.listSource = list || [];
                };
                return Item;
            }());
            var ItemModelCbb = (function () {
                function ItemModelCbb(codeCbb, nameCbb) {
                    this.codeCbb = codeCbb;
                    this.nameCbb = nameCbb;
                }
                return ItemModelCbb;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kdl024.a || (kdl024.a = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.vm.js.map