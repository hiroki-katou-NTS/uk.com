var kdl024;
(function (kdl024) {
    var viewmodel;
    (function (viewmodel) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.isNew = false;
                self.roundingRules = ko.observableArray([
                    { unitId: '0', unitName: '日別' },
                    { unitId: '1', unitName: '時間帯別' }
                ]);
                self.isEnableInp = ko.observable(false);
                self.itemListCbb = ko.observableArray([
                    new ItemModelCbb('0', '時間'),
                    new ItemModelCbb('1', '人数'),
                    new ItemModelCbb('2', '金額'),
                    new ItemModelCbb('3', '数値'),
                    new ItemModelCbb('4', '単価')
                ]);
                self.isEnableCbb = ko.observable(true);
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'externalBudgetCode', width: 40 },
                    { headerText: '名称', key: 'externalBudgetName', width: 150 }
                ]);
                self.items = ko.observableArray([]);
                self.currentItem = ko.observable(new Item({
                    externalBudgetCode: '',
                    externalBudgetName: '',
                    budgetAtr: 0,
                    unitAtr: 0
                }));
                self.start();
            }
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                kdl024.service.getListExternalBudget().done(function (lstBudget) {
                    if (lstBudget.length > 0) {
                        self.isNew = false;
                        var _items = [];
                        for (var i in lstBudget) {
                            var item = lstBudget[i];
                            _items.push(new TempItem(item.externalBudgetCode, item.externalBudgetName, item.budgetAtr, item.unitAtr));
                        }
                        self.items(_items);
                        self.currentSource = _items;
                        self.currentItem().setSource(_items);
                        self.currentItem().externalBudgetCode(lstBudget[0].externalBudgetCode);
                    }
                    else {
                        addNew();
                        dfd.resolve();
                    }
                }).fail(function (res) {
                    alert(res);
                });
                return dfd.promise();
            };
            ScreenModel.prototype.update = function () {
                var self = this;
                if (self.isNew) {
                    kdl024.service.insertExternalBudget(self.currentItem()).done(function () {
                    }).fail(function (res) {
                        alert(res);
                    });
                    self.currentSource.push(new TempItem(self.currentItem().externalBudgetCode(), self.currentItem().externalBudgetName(), self.currentItem().budgetAtr(), self.currentItem().unitAtr()));
                    self.items([]);
                    self.items(self.currentSource);
                    self.isNew = false;
                }
                else {
                    kdl024.service.updateExternalBudget(self.currentItem()).done(function () {
                    }).fail(function (res) {
                        alert(res);
                    });
                    self.start();
                }
                $("#btnDel").prop('disabled', false);
            };
            ScreenModel.prototype.addNew = function () {
                var self = this;
                self.isEnableInp = ko.observable(true);
                self.currentItem().externalBudgetCode('@');
                $('#inpName').val('');
                $('#inpCode').val('');
                $('#inpCode').focus();
                self.currentItem().budgetAtr('0');
                self.currentItem().unitAtr(0);
                $("#btnDel").prop('disabled', true);
                $("#inpCode").prop("disabled", false);
                self.isNew = true;
            };
            ScreenModel.prototype.del = function () {
                var self = this;
                kdl024.service.deleteExternalBudget(self.currentItem()).done(function () {
                }).fail(function (res) {
                    alert(res);
                });
                self.start();
            };
            return ScreenModel;
        }());
        viewmodel.ScreenModel = ScreenModel;
        var TempItem = (function () {
            function TempItem(externalBudgetCode, externalBudgetName, budgetAtr, unitAtr) {
                var self = this;
                self.externalBudgetCode = externalBudgetCode;
                self.externalBudgetName = externalBudgetName;
                self.budgetAtr = budgetAtr;
                self.unitAtr = unitAtr;
            }
            return TempItem;
        }());
        var Item = (function () {
            function Item(p) {
                var self = this;
                self.externalBudgetCode = ko.observable(p.externalBudgetCode);
                self.externalBudgetName = ko.observable(p.externalBudgetName);
                self.budgetAtr = ko.observable(p.budgetAtr);
                self.unitAtr = ko.observable(p.unitAtr);
                self.externalBudgetCode.subscribe(function (newValue) {
                    var current = _.find(self.listSource, function (item) { return item.externalBudgetCode == newValue; });
                    if (current) {
                        self.externalBudgetCode(current.externalBudgetCode);
                        self.externalBudgetName(current.externalBudgetName);
                        self.unitAtr(current.unitAtr);
                        self.budgetAtr(current.budgetAtr.toString());
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
                var self = this;
                self.codeCbb = codeCbb;
                self.nameCbb = nameCbb;
            }
            return ItemModelCbb;
        }());
    })(viewmodel = kdl024.viewmodel || (kdl024.viewmodel = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.vm.js.map