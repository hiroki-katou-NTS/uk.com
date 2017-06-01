var qmm020;
(function (qmm020) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.listTreeColumns = [];
                    this.model = ko.observable(undefined);
                    this.dirty = new nts.uk.ui.DirtyChecker(this.model);
                    var self = this;
                    self.listTreeColumns = [{ headerText: '', key: 'code', dataType: 'string', hidden: true },
                        { headerText: 'コード/名称', key: 'name', dataType: 'string', hidden: false, width: '450px', template: '<span>${code} ${name}</span>' },
                        { headerText: '給与明細書', key: 'bonus', dataType: 'object', hidden: false, width: '250px', template: '<button class="d-btn-001" onclick="__viewContext.viewModel.viewmodelD.openMDialog()">選択</button><span>${bonus.code} ${bonus.name}</span>' },
                        { headerText: '賞与明細書', key: 'payment', dataType: 'object', hidden: false, width: '250px', template: '<button class="d-btn-002" onclick="__viewContext.viewModel.viewmodelD.openMDialog()">選択</button><span>${payment.code} ${payment.name}</span>' }];
                    self.start();
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    self.model(new ItemModel({ ItemTrees: [], ItemLists: [] }));
                    // replace below code by service            
                    self.model().ItemLists([
                        new ItemList({ code: '1', name: '2016/04 ~ 9999/12' }),
                        new ItemList({ code: '2', name: '2015/04 ~ 2016/03' }),
                        new ItemList({ code: '3', name: '2014/04 ~ 2015/03' })
                    ]);
                    self.model().ItemListSelected('1');
                    self.model().ItemTrees([
                        new ItemTree({
                            code: '001', name: 'Name 0001',
                            bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                            payment: new ItemList({ code: '50', name: 'Payment 50' }),
                            childs: [{
                                    code: '001001', name: 'Child 001',
                                    bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                                    payment: new ItemList({ code: '50', name: 'Payment 50' })
                                },
                                {
                                    code: '001002', name: 'Child 002',
                                    bonus: new ItemList({ code: '01', name: 'Bonus 01' }),
                                    payment: new ItemList({ code: '50', name: 'Payment 50' })
                                }]
                        }),
                        new ItemTree({
                            code: '002', name: 'Name 0002',
                            bonus: new ItemList({ code: '01', name: 'Bonus Name 01' }),
                            payment: new ItemList({ code: '50', name: 'Payment Name 50' })
                        }),
                        new ItemTree({
                            code: '003', name: 'Name 0003',
                            bonus: new ItemList({ code: '01', name: 'Bonus Name 01' }),
                            payment: new ItemList({ code: '50', name: 'Payment Name 50' })
                        })
                    ]);
                    self.model().ItemTreeSelected('002');
                    // reset dirty check
                    self.dirty.reset();
                };
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('J_MODE', 2);
                    nts.uk.ui.windows.setShared("J_BASEDATE", 201701);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { width: 485, height: 550, title: '履歴の追加', dialogClass: "no-close" })
                        .onClosed(function () {
                        var value = nts.uk.ui.windows.getShared('J_RETURN');
                        // set data into gridview
                    });
                };
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { width: 485, height: 550, title: '履歴の編集', dialogClass: "no-close" });
                };
                ScreenModel.prototype.openMDialog = function () {
                    var self = this;
                    var currentItemTree = self.model().currentItemTree();
                    if (!!currentItemTree) {
                        currentItemTree.dialog(true);
                    }
                    nts.uk.ui.windows.setShared('M_BASEYM', 201707);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { width: 485, height: 550, title: '明細書の選択', dialogClass: "no-close" })
                        .onClosed(function () {
                        currentItemTree.dialog(false);
                        var value = nts.uk.ui.windows.getShared('M_RETURN');
                        // set data into gridview
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(param) {
                    this.ItemTrees = ko.observableArray([]);
                    this.ItemTreeSelected = ko.observable(undefined);
                    this.ItemLists = ko.observableArray([]);
                    this.ItemListSelected = ko.observable(undefined);
                    var self = this;
                    self.ItemTrees(param.ItemTrees.map(function (m) { return new ItemTree(m); }));
                    self.ItemLists(param.ItemLists.map(function (m) { return new ItemList(m); }));
                }
                ItemModel.prototype.currentItemTree = function () {
                    var self = this;
                    return _.find(self.ItemTrees(), function (m) { return m.code == self.ItemTreeSelected(); });
                };
                ItemModel.prototype.currentItemList = function () {
                    var self = this;
                    return _.find(self.ItemLists(), function (m) { return m.code == self.ItemListSelected(); });
                };
                return ItemModel;
            }());
            var ItemTree = (function () {
                function ItemTree(param) {
                    this.dialog = ko.observable(false);
                    this.code = param.code;
                    this.name = param.name;
                    this.bonus = param.bonus;
                    this.payment = param.payment;
                    this.childs = (param.childs || []).map(function (c) { return new ItemTree(c); });
                }
                return ItemTree;
            }());
            var ItemList = (function () {
                function ItemList(param) {
                    this.code = param.code;
                    this.name = param.name;
                }
                return ItemList;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm020.d || (qmm020.d = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.d.viewmodel.js.map