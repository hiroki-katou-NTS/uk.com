var screenQmm019;
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                this.itemListFull = [];
                this.itemListSearch = [];
                this.queueSearchResult = [];
                this.textSearch = "";
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
                this.previousItemPosition = 0; //Vị trí của item trước khi bị move giữa các row.
                var self = this;
                screenQmm019 = ko.observable(self);
                self.itemList = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.layouts = ko.observableArray([]);
                self.layoutsMax = ko.observableArray([]);
                self.layoutMaster = ko.observable(new a.service.model.LayoutMasterDto());
                self.categories = ko.observableArray([new a.service.model.Category([], 0)]);
                self.singleSelectedCode.subscribe(function (codeChanged) {
                    var layoutFind = _.find(self.layouts(), function (layout) {
                        return layout.stmtCode === codeChanged.split(';')[0] && layout.startYm === parseInt(codeChanged.split(';')[1]);
                    });
                    if (layoutFind !== undefined) {
                        a.service.getLayout(layoutFind.stmtCode, layoutFind.historyId).done(function (layout) {
                            layoutFind.stmtName = layout.stmtName;
                            self.layoutMaster(layoutFind);
                            self.startYm(nts.uk.time.formatYearMonth(self.layoutMaster().startYm));
                            self.endYm(nts.uk.time.formatYearMonth(self.layoutMaster().endYm));
                            a.service.getCategoryFull(layoutFind.stmtCode, layoutFind.startYm)
                                .done(function (listResult) {
                                self.categories(listResult);
                                self.calculateLine();
                                self.checkKintaiKiji();
                                self.bindSortable();
                            });
                        });
                    }
                });
            }
            ScreenModel.prototype.searchLayout = function () {
                var self = this;
                var textSearch = $("#A_INP_001").val().trim();
                if (textSearch.length === 0) {
                    nts.uk.ui.dialog.alert("コード/名称が入力されていません。");
                }
                else {
                    if (self.textSearch !== textSearch) {
                        self.itemListSearch = _.filter(self.itemListFull, function (item) {
                            return _.includes(item.code, textSearch) || _.includes(item.name, textSearch);
                        });
                        self.queueSearchResult = [];
                        for (var _i = 0, _a = self.itemListSearch; _i < _a.length; _i++) {
                            var item = _a[_i];
                            for (var _b = 0, _c = item.childs; _b < _c.length; _b++) {
                                var child = _c[_b];
                                self.queueSearchResult.push(child);
                            }
                        }
                        self.textSearch = textSearch;
                    }
                    if (self.itemListSearch.length === 0) {
                        nts.uk.ui.dialog.alert("対象データがありません。");
                    }
                    else {
                        var firstResult = _.first(self.queueSearchResult);
                        self.singleSelectedCode(firstResult.code);
                        self.queueSearchResult.shift();
                        self.queueSearchResult.push(firstResult);
                    }
                }
            };
            ScreenModel.prototype.calculateLine = function () {
                var self = this;
                self.totalNormalLineNumber(0);
                self.totalGrayLineNumber(0);
                for (var _i = 0, _a = self.categories(); _i < _a.length; _i++) {
                    var category = _a[_i];
                    category.totalGrayLine = 0;
                    for (var _b = 0, _c = category.lines(); _b < _c.length; _b++) {
                        var line = _c[_b];
                        if (line.isRemoved || category.isRemoved)
                            continue;
                        if (!line.isDisplayOnPrint) {
                            self.totalGrayLineNumber(self.totalGrayLineNumber() + 1);
                            category.totalGrayLine += 1;
                        }
                        else {
                            self.totalNormalLineNumber(self.totalNormalLineNumber() + 1);
                        }
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
                _.forEach(self.categories(), function (category) {
                    _.forEach(category.lines(), function (line) {
                        $("#" + line.rowId).sortable({
                            items: "span:not(.ui-state-disabled)",
                            connectWith: ".categoryAtr" + line.categoryAtr,
                            receive: function (event, ui) {
                                if (ui.sender !== null) {
                                    //Trả ra vị trí index của item vừa được move đến trong array span
                                    var getItemIndex = $(this).find(".one-line").find("span").index(ui.item);
                                    //get item ở sau item vừa được move đến (get by index) -> di chuyển nó đến vị trí cũ của item vừa bị move
                                    var itemWillBeMoved_1 = $(this).find(".one-line").find("span")[getItemIndex + 1];
                                    if ($(itemWillBeMoved_1).hasClass("fixed-button") || itemWillBeMoved_1 === undefined) {
                                        //nếu item sẽ dùng đề move bù lại thằng vừa kéo đi -> mà là dạng fixed-button
                                        //hoặc là undefined thì nhảy lên lấy thằng index trước đó
                                        itemWillBeMoved_1 = $(this).find(".one-line").find("span")[getItemIndex - 1];
                                    }
                                    //thực hiện move item mới vào chỗ của thằng vừa kéo đi
                                    if (screenQmm019().previousItemPosition === 8) {
                                        //Nếu vị trí để insert vào là số 8 thì dùng cái này ↓
                                        $(itemWillBeMoved_1).insertAfter($(ui.sender[0]).find(".one-line").find("span")[7]);
                                    }
                                    else {
                                        $(itemWillBeMoved_1).insertBefore($(ui.sender[0]).find(".one-line").find("span")[screenQmm019().previousItemPosition]);
                                    }
                                    var currentCategoryId_1 = $(this).parent().parent().attr("id");
                                    var thisLineId_1 = $(this).attr("id");
                                    var senderLineId_1 = $(ui.sender).attr("id");
                                    var comeInItem = void 0, returnItem = void 0;
                                    var categoryFind = _.find(screenQmm019().categories(), function (categoryItem) {
                                        return categoryItem.categoryAtr === Number(currentCategoryId_1);
                                    });
                                    if (categoryFind !== undefined) {
                                        var lineFind = _.find(categoryFind.lines(), function (lineItem) {
                                            return lineItem.rowId === thisLineId_1;
                                        });
                                        var senderLineFind = _.find(categoryFind.lines(), function (lineItem) {
                                            return lineItem.rowId === senderLineId_1;
                                        });
                                        if (lineFind !== undefined) {
                                            returnItem = _.find(lineFind.details, function (item) {
                                                return item.itemCode() === $(itemWillBeMoved_1).attr("id");
                                            });
                                            //update autolineId mới
                                            returnItem.autoLineId(senderLineFind.autoLineId);
                                            _.remove(lineFind.details, function (item) {
                                                return item.itemCode() === $(itemWillBeMoved_1).attr("id");
                                            });
                                        }
                                        if (senderLineFind !== undefined) {
                                            comeInItem = _.find(senderLineFind.details, function (item) {
                                                return item.itemCode() === $(ui.item).attr("id");
                                            });
                                            //update autolineId mới 
                                            comeInItem.autoLineId(lineFind.autoLineId);
                                            _.remove(senderLineFind.details, function (item) {
                                                return item.itemCode() === $(ui.item).attr("id");
                                            });
                                        }
                                        var comeInItemFound = true, returnItemFound = true;
                                        //Tạo id mới cho comeInItem và returnItem
                                        var _loop_1 = function(i) {
                                            //Tìm xem nếu id mới chưa có thì set
                                            if (_.includes(comeInItem.itemCode(), "itemTemp-") && comeInItemFound) {
                                                var comeInItemFind = _.find(lineFind.details, function (item) {
                                                    return item.itemCode() === "itemTemp-" + i;
                                                });
                                                if (comeInItemFind === undefined) {
                                                    comeInItemFound = false;
                                                    comeInItem.itemCode("itemTemp-" + i);
                                                }
                                            }
                                            if (_.includes(returnItem.itemCode(), "itemTemp-") && returnItemFound) {
                                                var returnItemFind = _.find(senderLineFind.details, function (item) {
                                                    return item.itemCode() === "itemTemp-" + i;
                                                });
                                                if (returnItemFind === undefined) {
                                                    returnItemFound = false;
                                                    returnItem.itemCode("itemTemp-" + i);
                                                }
                                            }
                                            if (!comeInItemFound && !returnItemFound) {
                                                return "break";
                                            }
                                        };
                                        for (var i = 1; i <= 9; i++) {
                                            var state_1 = _loop_1(i);
                                            if (state_1 === "break") break;
                                        }
                                        //update datasource
                                        lineFind.details.push(comeInItem);
                                        senderLineFind.details.push(returnItem);
                                    }
                                }
                            },
                            start: function (event, ui) {
                                self.previousItemPosition = $(this).find(".one-line").find("span").index(ui.item);
                            }
                        });
                    });
                });
                //Setting sortable giữa các dòng với nhau
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
                if (self.validateOnRegister()) {
                    a.service.registerLayout(self.layoutMaster(), self.categories()).done(function (res) {
                        a.service.getCategoryFull(self.layoutMaster().stmtCode, self.layoutMaster().startYm)
                            .done(function (listResult) {
                            self.categories(listResult);
                            self.checkKintaiKiji();
                            self.bindSortable();
                        });
                    }).fail(function (err) {
                        alert(err);
                    });
                }
            };
            ScreenModel.prototype.validateOnRegister = function () {
                var self = this;
                if (self.layoutMaster().stmtName.length === 0) {
                    nts.uk.ui.dialog.alert("明細書名が入力されていません。");
                    return false;
                }
                return true;
            };
            ScreenModel.prototype.addKintaiCategory = function () {
                var self = this;
                var category = new a.service.model.Category([], 2);
                self.categories.push(category);
                self.notHasKintai(false);
                self.bindSortable();
            };
            ScreenModel.prototype.addKijiCategory = function () {
                var self = this;
                var category = new a.service.model.Category([], 3);
                self.categories.push(category);
                self.notHasKiji(false);
                self.bindSortable();
            };
            ScreenModel.prototype.openADialog = function () {
                var self = this;
                if (self.singleSelectedCode() == null)
                    return false;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.sub.modal('/view/qmm/019/d/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                    self.start(self.singleSelectedCode());
                });
            };
            ScreenModel.prototype.openEDialog = function () {
                var self = this;
                if (self.singleSelectedCode() === null)
                    return false;
                var singleSelectedCode = self.singleSelectedCode().split(';');
                if (singleSelectedCode[0] === undefined
                    || singleSelectedCode[1] === undefined
                    || self.layoutMaster().historyId === undefined)
                    return false;
                nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
                nts.uk.ui.windows.setShared('startYm', singleSelectedCode[1]);
                nts.uk.ui.windows.setShared('historyId', self.layoutMaster().historyId);
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
