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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.code = ko.observable('1234');
                                    self.name = ko.observable('4312');
                                    self.items = ko.observableArray([]);
                                    this.currentCode = ko.observable();
                                    for (var i = 1; i < 100; i++) {
                                        this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "other" + i));
                                    }
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 100, hidden: true },
                                        { headerText: '名称', key: 'name', width: 150, hidden: true },
                                        { headerText: '説明', key: 'description', width: 150 },
                                        { headerText: '説明1', key: 'other1', width: 150 },
                                        { headerText: '説明2', key: 'other2', width: 150 }
                                    ]);
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-3', title: '勤怠', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-4', title: '記事・その他', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    self.selectedTab = ko.observable('tab-2');
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name, description, deletable, other1, other2) {
                                    this.code = code;
                                    this.name = name;
                                    this.description = description;
                                    this.other1 = other1;
                                    this.other2 = other2 || other1;
                                    this.deletable = deletable;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
