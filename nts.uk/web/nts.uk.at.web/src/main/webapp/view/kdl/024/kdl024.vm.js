var kdl024;
(function (kdl024) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.constraint = ['ResidenceCode', 'PersonId'];
                    self.roundingRules = ko.observableArray([
                        { unitId: '1', unitName: '日別' },
                        { unitId: '2', unitName: '時間帯別' }
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                    self.itemListCbb = ko.observableArray([
                        new ItemModelCbb('時間'),
                        new ItemModelCbb('人数'),
                        new ItemModelCbb('金額'),
                        new ItemModelCbb('数値'),
                        new ItemModelCbb('単価')
                    ]);
                    self.selectedCodeCbb = ko.observable('時間');
                    self.isEnableCbb = ko.observable(true);
                    self.currentCode = ko.observable('0');
                    this.columns = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 40 },
                        { headerText: '名称', key: 'name', width: 150 }
                    ]);
                    this.items = ko.observableArray([]);
                    for (var i = 1; i < 20; i++) {
                        this.items.push(new Item('00' + i, '時間実績'));
                    }
                    self.start();
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    kdl024.service.getListExternalBudget().done(function (lstBudget) {
                        console.log(lstBudget);
                        debugger;
                        if (lstBuget.length > 0) {
                            alert('pass');
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
                function Item(code, name, attribute, unit) {
                    this.code = code;
                    this.name = name;
                    this.attribute = attribute;
                    this.unit = unit;
                }
                return Item;
            }());
            var ItemModelCbb = (function () {
                function ItemModelCbb(nameCbb) {
                    this.nameCbb = nameCbb;
                    this.labelCbb = nameCbb;
                }
                return ItemModelCbb;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kdl024.a || (kdl024.a = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.vm.js.map