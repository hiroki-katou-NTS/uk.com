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
                                    self.items = ko.observableArray([]);
                                    self.currentCode = ko.observable();
                                    self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                                    for (var i = 1; i < 30; i++) {
                                        this.items.push(new ItemModel('00' + i, '基本給', "name " + i, i % 3 === 0));
                                    }
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 50, hidden: true },
                                        { headerText: '名称', key: 'name', width: 50, hidden: true },
                                        { headerText: '説明', key: 'description', width: 100 }
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.commonSettingBtnClick = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name, description, deletable) {
                                    this.code = code;
                                    this.name = name;
                                    this.description = description;
                                    this.deletable = deletable;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                            var OutputSettingDetailModel = (function () {
                                function OutputSettingDetailModel() {
                                    this.settingCode = ko.observable('code');
                                    this.settingName = ko.observable('name 123');
                                    this.categorySetting = ko.observable(new CategorySetting());
                                    this.categorySettingTabs = ko.observableArray([
                                        { id: 'supply', title: '支給', content: '#supply', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'deduction', title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'attendance', title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'article-others', title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    this.selectedCategory = ko.observable('supply');
                                }
                                return OutputSettingDetailModel;
                            }());
                            viewmodel.OutputSettingDetailModel = OutputSettingDetailModel;
                            var CategorySetting = (function () {
                                function CategorySetting() {
                                    var self = this;
                                    self.items = ko.observableArray([]);
                                    self.currentCode = ko.observable();
                                    for (var i = 1; i < 15; i++) {
                                        this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0));
                                    }
                                    this.columns = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 50, hidden: true },
                                        { headerText: '名称', key: 'name', width: 50, hidden: true },
                                        { headerText: '説明', key: 'description', width: 100 }
                                    ]);
                                }
                                return CategorySetting;
                            }());
                            viewmodel.CategorySetting = CategorySetting;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
