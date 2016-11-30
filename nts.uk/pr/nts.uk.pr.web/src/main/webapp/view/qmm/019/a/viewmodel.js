// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
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
                        a.service.getCategoryFull(layoutFind.stmtCode, layoutFind.startYm, self)
                            .done(function (listResult) {
                            self.categories(listResult);
                            self.bindSortable();
                        });
                    }
                });
            }
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
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                a.service.getAllLayout().done(function (layouts) {
                    self.layouts(layouts);
                    a.service.getLayoutsWithMaxStartYm().done(function (layoutsMax) {
                        self.layoutsMax(layoutsMax);
                        self.buildTreeDataSource();
                        var firstLayout = _.first(self.layouts());
                        self.singleSelectedCode(firstLayout.stmtCode + ";" + firstLayout.startYm);
                        dfd.resolve();
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            };
            ScreenModel.prototype.buildTreeDataSource = function () {
                var self = this;
                self.itemList.removeAll();
                _.forEach(self.layoutsMax(), function (layoutMax) {
                    var children = [];
                    var childLayouts = _.filter(self.layouts(), function (layout) {
                        return layout.stmtCode === layoutMax.stmtCode;
                    });
                    _.forEach(childLayouts, function (child) {
                        children.push(new NodeTest(child.stmtCode + ";" + child.startYm, child.stmtName, [], child.startYm + " ~ " + child.endYm));
                    });
                    self.itemList.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
                });
            };
            ScreenModel.prototype.registerLayout = function () {
                var self = this;
                a.service.registerLayout(self.layoutMaster(), self.categories()).done(function (res) {
                }).fail(function (err) {
                    alert(err);
                });
            };
            ScreenModel.prototype.openADialog = function () {
                var self = this;
                if (self.singleSelectedCode() == null)
                    return;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.sub.modal('/view/qmm/019/d/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () { return void {}; });
            };
            ScreenModel.prototype.openEDialog = function () {
                var self = this;
                if (self.singleSelectedCode() == null)
                    return;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.setShared('startYm', singleSelectedCode[1]);
                nts.uk.ui.windows.sub.modal('/view/qmm/019/e/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () { return void {}; });
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
