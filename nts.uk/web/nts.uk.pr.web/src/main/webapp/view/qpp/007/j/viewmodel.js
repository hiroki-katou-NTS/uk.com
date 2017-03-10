var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var j;
                    (function (j) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.name = ko.observable('');
                                    self.tabs1 = ko.observableArray([
                                        { id: '1', title: '支給集計', content: '#1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: '2', title: '控除集計', content: '#2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.tabs2 = ko.observableArray([
                                        { id: 'tab-1', title: '集計項目1', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '集計項目2', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-3', title: '集計項目3', content: '#tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-4', title: '集計項目4', content: '#tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab1 = ko.observable('1');
                                    self.selectedTab2 = ko.observable('tab-1');
                                    this.itemsSwap = ko.observableArray([]);
                                    var array = [];
                                    for (var i = 0; i < 20; i++) {
                                        array.push(new ItemModel(i, '基本給', "description"));
                                    }
                                    this.itemsSwap(array);
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 100 },
                                        { headerText: '名称', key: 'name', width: 150 }
                                    ]);
                                    this.currentCodeListSwap = ko.observableArray([]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.closeDialogBtnClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name, description) {
                                    this.code = code;
                                    this.name = name;
                                    this.description = description;
                                    this.deletable = code % 3 === 0;
                                }
                                return ItemModel;
                            }());
                        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
                    })(j = qpp007.j || (qpp007.j = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
