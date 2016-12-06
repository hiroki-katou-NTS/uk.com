// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                this.itemListFull = [];
                this.itemListSearch = [];
                this.notHasKintai = ko.observable(false);
                this.notHasKiji = ko.observable(false);
                this.startYm = ko.observable("");
                this.endYm = ko.observable("");
                this.totalNormalLine = ko.observable("0行");
                this.totalNormalLineNumber = ko.observable(0);
                this.totalGrayLine = ko.observable("（+非表示0行）");
                this.totalGrayLineNumber = ko.observable(0);
                this.allowClick = ko.observable(true);
                this.firstLayoutCode = ""; //Dùng cho select item đầu tiên.
                var self = this;
                self.itemList = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.layouts = ko.observableArray([]);
                self.layoutsMax = ko.observableArray([]);
                self.layoutMaster = ko.observable(new a.service.model.LayoutMasterDto());
                self.categories = ko.observableArray([new a.service.model.Category([], 0, self)]);
                self.singleSelectedCode.subscribe(function (codeChanged) {
                    var layoutFind = _.find(self.layouts(), function (layout) {
                        return layout.stmtCode === codeChanged.split(';')[0] && layout.startYm === parseInt(codeChanged.split(';')[1]);
                    });
                    if (layoutFind !== undefined) {
                        self.layoutMaster(layoutFind);
                        self.startYm(nts.uk.time.formatYearMonth(self.layoutMaster().startYm));
                        self.endYm(nts.uk.time.formatYearMonth(self.layoutMaster().endYm));
                        a.service.getCategoryFull(layoutFind.stmtCode, layoutFind.startYm, self)
                            .done(function (listResult) {
                            self.categories(listResult);
                            self.calculateLine();
                            self.checkKintaiKiji();
                            self.bindSortable();
                        });
                    }
                });
            }
            ScreenModel.prototype.searchLayout = function () {
                var self = this;
                var textSearch = $("#A_INP_001").val();
                self.itemList([]);
                if (textSearch.trim().length === 0) {
                    self.itemList(self.itemListFull);
                }
                else {
                    self.itemListSearch = _.filter(self.itemListFull, function (item) {
                        return _.includes(item.code, textSearch) || _.includes(item.name, textSearch);
                    });
                    self.itemList(self.itemListSearch);
                    if (self.itemListSearch.length === 0) {
                        nts.uk.ui.dialog.alert("対象データがありません。");
                    }
                }
            };
            ScreenModel.prototype.calculateLine = function () {
                var self = this;
                self.totalNormalLineNumber(0);
                self.totalGrayLineNumber(0);
                for (var _i = 0, _a = self.categories(); _i < _a.length; _i++) {
                    var category = _a[_i];
                    for (var _b = 0, _c = category.lines(); _b < _c.length; _b++) {
                        var line = _c[_b];
                        if (line.isRemoved || category.isRemoved)
                            continue;
                        if (!line.isDisplayOnPrint)
                            self.totalGrayLineNumber(self.totalGrayLineNumber() + 1);
                        else
                            self.totalNormalLineNumber(self.totalNormalLineNumber() + 1);
                    }
                }
                self.totalNormalLine(self.totalNormalLineNumber() + "行");
                self.totalGrayLine("（+非表示" + self.totalGrayLineNumber() + "行）");
            };
            ScreenModel.prototype.checkKintaiKiji = function () {
                var self = this;
                var findKintai = _.find(self.categories(), function (category) {
                    return category.categoryAtr === 2;
                });
                self.notHasKintai(findKintai === undefined);
                var findKiji = _.find(self.categories(), function (category) {
                    return category.categoryAtr === 3;
                });
                self.notHasKiji(findKiji === undefined);
            };
            ScreenModel.prototype.bindSortable = function () {
                var self = this;
                $(".row").sortable({
                    items: "span:not(.ui-state-disabled)"
                });
                $(".all-line").sortable({
                    items: ".row"
                });
            };
            ScreenModel.prototype.destroySortable = function () {
                $(".row").sortable("destroy");
                $(".all-line").sortable("destroy");
            };
            // start function
            ScreenModel.prototype.start = function (currentLayoutSelectedCode) {
                var self = this;
                var dfd = $.Deferred();
                a.service.getAllLayout().done(function (layouts) {
                    if (layouts.length > 0) {
                        self.layouts(layouts);
                        a.service.getLayoutsWithMaxStartYm().done(function (layoutsMax) {
                            self.layoutsMax(layoutsMax);
                            self.buildTreeDataSource();
                            //let firstLayout: service.model.LayoutMasterDto = _.first(self.layouts());
                            if (currentLayoutSelectedCode === undefined) {
                                self.singleSelectedCode(self.firstLayoutCode);
                            }
                            else {
                                self.singleSelectedCode(currentLayoutSelectedCode);
                            }
                            dfd.resolve();
                        });
                    }
                    else {
                        self.allowClick(false);
                        dfd.resolve();
                    }
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            };
            ScreenModel.prototype.buildTreeDataSource = function () {
                var self = this;
                var items = [];
                _.forEach(self.layoutsMax(), function (layoutMax) {
                    var children = [];
                    var childLayouts = _.filter(self.layouts(), function (layout) {
                        return layout.stmtCode === layoutMax.stmtCode;
                    });
                    _.forEach(childLayouts, function (child) {
                        if (self.firstLayoutCode === "")
                            self.firstLayoutCode = child.stmtCode + ";" + child.startYm;
                        children.push(new NodeTest(child.stmtCode + ";" + child.startYm, child.stmtName, [], nts.uk.time.formatYearMonth(child.startYm) + " ~ " + nts.uk.time.formatYearMonth(child.endYm)));
                    });
                    items.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
                });
                self.itemList(items);
                self.itemListFull = items;
            };
            ScreenModel.prototype.registerLayout = function () {
                var self = this;
                a.service.registerLayout(self.layoutMaster(), self.categories()).done(function (res) {
                    a.service.getCategoryFull(self.layoutMaster().stmtCode, self.layoutMaster().startYm, self)
                        .done(function (listResult) {
                        self.categories(listResult);
                        self.checkKintaiKiji();
                        self.bindSortable();
                    });
                }).fail(function (err) {
                    alert(err);
                });
            };
            ScreenModel.prototype.addKintaiCategory = function () {
                var self = this;
                var category = new a.service.model.Category([], 2, self);
                self.categories.push(category);
                self.notHasKintai(false);
                self.bindSortable();
            };
            ScreenModel.prototype.addKijiCategory = function () {
                var self = this;
                var category = new a.service.model.Category([], 3, self);
                self.categories.push(category);
                self.notHasKiji(false);
                self.bindSortable();
            };
            ScreenModel.prototype.openADialog = function () {
                var self = this;
                if (self.singleSelectedCode() == null)
                    return;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.sub.modal('/view/qmm/019/d/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                    self.start(self.singleSelectedCode());
                });
            };
            ScreenModel.prototype.openEDialog = function () {
                var self = this;
                if (self.singleSelectedCode() == null)
                    return;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.setShared('startYm', singleSelectedCode[1]);
                nts.uk.ui.windows.sub.modal('/view/qmm/019/e/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function () {
                    self.start(self.singleSelectedCode());
                });
            };
            ScreenModel.prototype.openGDialog = function () {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qmm/019/g/index.xhtml', { title: '明細レイアウトの作成＞新規登録' }).onClosed(function () {
                    self.start(undefined);
                });
            };
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var NodeTest = (function () {
            function NodeTest(code, name, children, nodeText) {
                this.code = code;
                this.name = name;
                this.childs = children;
                this.nodeText = nodeText;
            }
            return NodeTest;
        }());
        a.NodeTest = NodeTest;
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
;
