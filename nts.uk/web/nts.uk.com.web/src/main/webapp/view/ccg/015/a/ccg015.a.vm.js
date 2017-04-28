var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var com;
        (function (com) {
            var view;
            (function (view) {
                var ccg015;
                (function (ccg015) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listTopPage = ko.observableArray([]);
                                    self.toppageSelectedCode = ko.observable(null);
                                    self.topPageModel = ko.observable(new TopPageModel());
                                    self.columns = ko.observableArray([
                                        { headerText: "コード", width: "50px", key: 'code', dataType: "string", hidden: false },
                                        { headerText: "名称", width: "300px", key: 'nodeText', dataType: "string" }
                                    ]);
                                    self.isNewMode = ko.observable(true);
                                    self.toppageSelectedCode.subscribe(function (selectedTopPageCode) {
                                        a.service.loadDetailTopPage(selectedTopPageCode).done(function (data) {
                                            self.loadTopPageItemDetail(data);
                                        });
                                    });
                                    self.languageListOption = ko.observableArray([
                                        new ItemCbbModel("0", "日本語"),
                                        new ItemCbbModel("1", "英語"),
                                        new ItemCbbModel("2", "ベトナム語")
                                    ]);
                                    self.languageSelectedCode = ko.observable("0");
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.loadTopPage().done(function (data) {
                                        data.forEach(function (item, index) {
                                            self.listTopPage.push(new Node(item.topPageCode, item.topPageName, null));
                                            dfd.resolve();
                                        });
                                        if (self.listTopPage().length > 0) {
                                            self.toppageSelectedCode(self.listTopPage()[0].code);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadTopPageItemDetail = function (data) {
                                    var self = this;
                                    self.topPageModel().topPageCode(data.topPageCode);
                                    self.topPageModel().topPageName(data.topPageName);
                                    if (data.placements) {
                                        data.placements.forEach(function (item, index) {
                                            var placementModel = new PlacementModel();
                                            var topPagePartModel = new TopPagePartModel();
                                            topPagePartModel.topPagePartType(item.topPagePartDto.topPagePartType);
                                            topPagePartModel.topPagePartCode(item.topPagePartDto.topPagePartCode);
                                            topPagePartModel.topPagePartName(item.topPagePartDto.topPagePartName);
                                            topPagePartModel.width(item.topPagePartDto.width);
                                            topPagePartModel.height(item.topPagePartDto.height);
                                            placementModel.row(item.row);
                                            placementModel.column(item.column);
                                            placementModel.topPagePart(topPagePartModel);
                                            self.topPageModel().placement().push(placementModel);
                                        });
                                    }
                                };
                                ScreenModel.prototype.collectData = function () {
                                    return null;
                                };
                                ScreenModel.prototype.collectDataForCreateNew = function () {
                                    return null;
                                };
                                ScreenModel.prototype.saveTopPage = function () {
                                    var self = this;
                                    if (self.isNewMode()) {
                                        a.service.registerTopPage(self.collectData()).done(function () {
                                        });
                                    }
                                    else {
                                        a.service.updateTopPage(self.collectData()).done(function () {
                                        });
                                    }
                                };
                                ScreenModel.prototype.openMyPageSettingDialog = function () {
                                    nts.uk.ui.windows.sub.modal("/view/ccg/015/b/index.xhtml", {
                                        height: 700, width: 850,
                                        title: "マイページの設定",
                                        dialogClass: 'no-close'
                                    }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.copyTopPage = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared('topPageCode', self.topPageModel().topPageCode());
                                    nts.uk.ui.windows.setShared('topPageName', self.topPageModel().topPageName());
                                    nts.uk.ui.windows.sub.modal("/view/ccg/015/c/index.xhtml", {
                                        height: 350, width: 650,
                                        title: "他のトップページコピー",
                                        dialogClass: 'no-close'
                                    }).onClosed(function () {
                                    });
                                };
                                ScreenModel.prototype.newTopPage = function () {
                                    var self = this;
                                    self.topPageModel(new TopPageModel());
                                };
                                ScreenModel.prototype.removeTopPage = function () {
                                    var self = this;
                                    a.service.deleteTopPage(self.toppageSelectedCode()).done(function () {
                                    }).fail();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = name;
                                    self.childs = childs;
                                    self.custom = 'Random' + new Date().getTime();
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                            var TopPageModel = (function () {
                                function TopPageModel() {
                                    this.topPageCode = ko.observable('');
                                    this.topPageName = ko.observable('');
                                    this.placement = ko.observableArray([]);
                                }
                                return TopPageModel;
                            }());
                            viewmodel.TopPageModel = TopPageModel;
                            var PlacementModel = (function () {
                                function PlacementModel() {
                                    this.row = ko.observable(0);
                                    this.column = ko.observable(0);
                                    this.topPagePart = ko.observable(new TopPagePartModel());
                                }
                                return PlacementModel;
                            }());
                            viewmodel.PlacementModel = PlacementModel;
                            var TopPagePartModel = (function () {
                                function TopPagePartModel() {
                                    this.topPagePartType = ko.observable(0);
                                    this.topPagePartCode = ko.observable("");
                                    this.topPagePartName = ko.observable("");
                                    this.width = ko.observable(0);
                                    this.height = ko.observable(0);
                                }
                                return TopPagePartModel;
                            }());
                            viewmodel.TopPagePartModel = TopPagePartModel;
                            var ItemCbbModel = (function () {
                                function ItemCbbModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemCbbModel;
                            }());
                            viewmodel.ItemCbbModel = ItemCbbModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = ccg015.a || (ccg015.a = {}));
                })(ccg015 = view.ccg015 || (view.ccg015 = {}));
            })(view = com.view || (com.view = {}));
        })(com = uk.com || (uk.com = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ccg015.a.vm.js.map