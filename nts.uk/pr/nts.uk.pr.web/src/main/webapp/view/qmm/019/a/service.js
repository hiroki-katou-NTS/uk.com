var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllLayout: "pr/proto/layout/findalllayout",
                getLayoutsWithMaxStartYm: "pr/proto/layout/findlayoutwithmaxstartym",
                getCategoryFull: "pr/proto/layout/findCategoies/full",
                registerLayout: "pr/proto/layout/register"
            };
            /**
             * Get list payment date processing.
             */
            function getAllLayout() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllLayout)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllLayout = getAllLayout;
            /**
             * Get list payment date processing.
             */
            function getLayoutsWithMaxStartYm() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getLayoutsWithMaxStartYm)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayoutsWithMaxStartYm = getLayoutsWithMaxStartYm;
            /**
             * Get list getCategoryFull.
             */
            function getCategoryFull(layoutCode, startYm, screenModel) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getCategoryFull + "/" + layoutCode + "/" + startYm)
                    .done(function (res) {
                    var result = _.map(res, function (category) {
                        return new model.Category(category.lines, category.categoryAtr, screenModel);
                    });
                    dfd.resolve(result);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCategoryFull = getCategoryFull;
            /**
             * Register Layout
             */
            function registerLayout(layout, categories) {
                var dfd = $.Deferred();
                var categoryCommand = [], lineCommand = [], detailCommand = [];
                var listCategoryAtrDeleted = [], listAutoLineIdDeleted = [], listItemCodeDeleted = [];
                var categoryPosition = 1;
                for (var _i = 0, categories_1 = categories; _i < categories_1.length; _i++) {
                    var category = categories_1[_i];
                    if (category.isRemoved === true) {
                        // Truong hop remove category thi remove luon line va detail
                        listCategoryAtrDeleted.push(category.categoryAtr);
                    }
                    else {
                        categoryCommand.push({ categoryAtr: category.categoryAtr, categoryPosition: categoryPosition });
                        var linePosition = 1;
                        var sortedLines = $("#" + category.categoryAtr).sortable("toArray");
                        var _loop_1 = function(itemLine) {
                            var line = _.find(category.lines(), function (lineDetail) {
                                return lineDetail.rowId === itemLine.toString();
                            });
                            if (line.isRemoved !== true) {
                                lineCommand.push({ categoryAtr: category.categoryAtr,
                                    autoLineId: line.autoLineId,
                                    linePosition: linePosition,
                                    lineDisplayAtr: line.lineDispayAtr });
                            }
                            else {
                                if (_.includes(line.autoLineId, "lineIdTemp-") === false) {
                                    listAutoLineIdDeleted.push({ categoryAtr: category.categoryAtr, autoLineId: line.autoLineId });
                                    return "continue";
                                }
                            }
                            linePosition++;
                            var itemPosColumn = 1;
                            var sortedItemCodes = $("#" + line.rowId).sortable("toArray");
                            var _loop_2 = function(item) {
                                var detail = _.find(line.details, function (itemDetail) {
                                    return itemDetail.itemCode() === item.toString();
                                });
                                if (detail.isRemoved !== true) {
                                    detailCommand.push({
                                        categoryAtr: category.categoryAtr,
                                        itemCode: detail.itemCode(),
                                        updateItemCode: detail.updateItemCode(),
                                        added: detail.added(),
                                        autoLineId: detail.autoLineId(),
                                        itemPosColumn: itemPosColumn,
                                        calculationMethod: detail.calculationMethod(),
                                        displayAtr: line.lineDispayAtr,
                                        sumScopeAtr: detail.sumScopeAtr(),
                                        setOffItemCode: detail.setOffItemCode(),
                                        commuteAtr: detail.commuteAtr(),
                                        personalWageCode: detail.personalWageCode(),
                                        distributeWay: detail.distributeWay(),
                                        distributeSet: detail.distributeSet(),
                                        isErrorUseHigh: detail.isUseHighError(),
                                        errorRangeHigh: detail.errRangeHigh(),
                                        isErrorUserLow: detail.isUseLowError(),
                                        errorRangeLow: detail.errRangeLow(),
                                        isAlamUseHigh: detail.isUseHighAlam(),
                                        alamRangeHigh: detail.alamRangeHigh(),
                                        isAlamUseLow: detail.isUseLowAlam(),
                                        alamRangeLow: detail.alamRangeLow() });
                                }
                                else {
                                    listItemCodeDeleted.push({ categoryAtr: category.categoryAtr, itemCode: detail.itemCode() });
                                }
                                itemPosColumn++;
                            };
                            for (var _a = 0, sortedItemCodes_1 = sortedItemCodes; _a < sortedItemCodes_1.length; _a++) {
                                var item = sortedItemCodes_1[_a];
                                _loop_2(item);
                            }
                        };
                        for (var _b = 0, sortedLines_1 = sortedLines; _b < sortedLines_1.length; _b++) {
                            var itemLine = sortedLines_1[_b];
                            var state_2 = _loop_1(itemLine);
                            if (state_2 === "continue") continue;
                        }
                    }
                    categoryPosition++;
                }
                var command = {
                    layoutCommand: {
                        stmtCode: layout.stmtCode,
                        startYm: layout.startYm,
                        stmtName: layout.stmtName,
                        endYm: layout.endYm
                    },
                    categoryCommand: categoryCommand,
                    lineCommand: lineCommand,
                    detailCommand: detailCommand,
                    listCategoryAtrDeleted: listCategoryAtrDeleted,
                    listAutoLineIdDeleted: listAutoLineIdDeleted,
                    listItemCodeDeleted: listItemCodeDeleted
                };
                nts.uk.request.ajax(paths.registerLayout, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.registerLayout = registerLayout;
            /**
               * Model namespace.
            */
            var model;
            (function (model) {
                // layout
                var LayoutMasterDto = (function () {
                    function LayoutMasterDto() {
                    }
                    return LayoutMasterDto;
                }());
                model.LayoutMasterDto = LayoutMasterDto;
                var Category = (function () {
                    function Category(lines, categoryAtr, screenModel) {
                        this.hasSetting = false;
                        this.isRemoved = false;
                        this.screenModel = ko.observable(screenModel);
                        this.lines = ko.observableArray([]);
                        this.lines(_.map(lines, function (line) {
                            var details = _.map(line.details, function (detail) {
                                return new model.ItemDetail(detail, screenModel);
                            });
                            return new model.Line(line.categoryAtr, details, line.autoLineId, line.lineDispayAtr, line.linePosition, screenModel);
                        }));
                        this.categoryAtr = categoryAtr;
                        switch (categoryAtr) {
                            case 0:
                                this.categoryName = "支給";
                                break;
                            case 1:
                                this.categoryName = "控除";
                                break;
                            case 2:
                                this.categoryName = "勤怠";
                                this.hasSetting = true;
                                break;
                            case 3:
                                this.categoryName = "記事";
                                this.hasSetting = true;
                                break;
                            default:
                                this.categoryName = "その他";
                                this.hasSetting = true;
                                break;
                        }
                    }
                    Category.prototype.categoryClick = function (data, event) {
                        var _this = this;
                        var self = this;
                        nts.uk.ui.windows.sub.modal('/view/qmm/019/k/index.xhtml', { title: '明細レイアウトの作成＞カテゴリの設定' }).onClosed(function () {
                            var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                            if (selectedCode === "1") {
                                // cho phep print all row
                                for (var _i = 0, _a = self.lines(); _i < _a.length; _i++) {
                                    var line = _a[_i];
                                    line.setPrint(true);
                                }
                            }
                            else if (selectedCode === "2") {
                                // Gray - Khong cho print all row
                                for (var _b = 0, _c = self.lines(); _b < _c.length; _b++) {
                                    var line = _c[_b];
                                    line.setPrint(false);
                                }
                            }
                            else if (selectedCode === "3") {
                                // Xoa category
                                $("#group-" + data.categoryAtr).addClass("removed");
                                self.isRemoved = true;
                                if (data.categoryAtr === 2)
                                    self.screenModel().notHasKintai(true);
                                if (data.categoryAtr === 3)
                                    self.screenModel().notHasKiji(true);
                            }
                            self.screenModel().calculateLine();
                            return _this;
                        });
                    };
                    Category.prototype.addLine = function () {
                        var _this = this;
                        var self = this;
                        if (self.screenModel().totalNormalLineNumber() + self.screenModel().totalGrayLineNumber() === 10) {
                            return this;
                        }
                        nts.uk.ui.windows.sub.modal('/view/qmm/019/i/index.xhtml', { title: '明細レイアウトの作成＞＋行追加' }).onClosed(function () {
                            var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                            if (selectedCode === undefined)
                                return _this;
                            var autoLineId = "lineIdTemp-" + self.lines().length;
                            var listItemDetail = new Array;
                            for (var i = 1; i <= 9; i++) {
                                listItemDetail.push(new ItemDetail({ itemCode: "itemTemp-" + i, itemAbName: "+", isRequired: false, itemPosColumn: i,
                                    categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0,
                                    setOffItemCode: "", commuteAtr: 0, calculationMethod: 0,
                                    distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                    errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                    alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 }, self.screenModel()));
                            }
                            var line = new Line(self.categoryAtr, listItemDetail, autoLineId, 1, self.lines.length, self.screenModel());
                            if (selectedCode === "1") {
                                // cho phep print
                                line.setPrint(true);
                            }
                            else if (selectedCode === "2") {
                                // Gray - Khong cho print
                                line.setPrint(false);
                            }
                            self.lines.push(line);
                            self.screenModel().calculateLine();
                            self.screenModel().bindSortable();
                            self.screenModel().destroySortable();
                            self.screenModel().bindSortable();
                            return _this;
                        });
                    };
                    return Category;
                }());
                model.Category = Category;
                var Line = (function () {
                    function Line(categoryAtr, itemDetails, autoLineId, lineDispayAtr, linePosition, screenModel) {
                        this.hasRequiredItem = false;
                        this.isRemoved = false;
                        this.screenModel = ko.observable(screenModel);
                        this.details = itemDetails;
                        this.autoLineId = autoLineId;
                        this.rowId = categoryAtr + autoLineId;
                        this.lineDispayAtr = lineDispayAtr;
                        if (lineDispayAtr === 0) {
                            this.isDisplayOnPrint = false;
                        }
                        else {
                            this.isDisplayOnPrint = true;
                        }
                        var checkRequired = _.find(itemDetails, function (findItem) {
                            return findItem.isRequired() === true;
                        });
                        if (checkRequired !== undefined) {
                            this.hasRequiredItem = true;
                        }
                        this.linePosition = linePosition;
                        this.categoryAtr = categoryAtr;
                    }
                    Line.prototype.lineClick = function (data, event) {
                        var _this = this;
                        var self = this;
                        nts.uk.ui.windows.sub.modal('/view/qmm/019/j/index.xhtml', { title: '明細レイアウトの作成＞行の設定' }).onClosed(function () {
                            var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                            if (selectedCode === "1") {
                                // cho phep print
                                self.setPrint(true);
                            }
                            else if (selectedCode === "2") {
                                // Gray - Khong cho print
                                self.setPrint(false);
                            }
                            else if (selectedCode === "3") {
                                // Xoa line
                                if (data.hasRequiredItem === false) {
                                    $("#" + data.rowId).addClass("removed");
                                    self.isRemoved = true;
                                }
                            }
                            self.screenModel().calculateLine();
                            return _this;
                        });
                    };
                    Line.prototype.setPrint = function (allowPrint) {
                        var self = this;
                        if (allowPrint === true) {
                            // cho phep print
                            $("#" + self.rowId).removeClass("ground-gray");
                            self.isDisplayOnPrint = true;
                            self.lineDispayAtr = 1;
                        }
                        else {
                            // Gray - Khong cho print
                            $("#" + self.rowId).addClass("ground-gray");
                            self.isDisplayOnPrint = false;
                            self.lineDispayAtr = 0;
                        }
                    };
                    return Line;
                }());
                model.Line = Line;
                var ItemDetail = (function () {
                    function ItemDetail(itemObject, screenModel) {
                        this.updateItemCode = ko.observable("");
                        this.added = ko.observable(false);
                        this.isRequired = ko.observable(false);
                        this.isRemoved = false;
                        this.screenModel = ko.observable(screenModel);
                        this.itemCode = ko.observable(itemObject.itemCode);
                        this.itemAbName = ko.observable(itemObject.itemAbName);
                        if (itemObject.categoryAtr === 0 &&
                            (itemObject.itemCode === "F001" || itemObject.itemCode === "F002" || itemObject.itemCode === "F003")) {
                            this.isRequired = ko.observable(true);
                        }
                        if (itemObject.categoryAtr === 1 &&
                            (itemObject.itemCode === "F114")) {
                            this.isRequired = ko.observable(true);
                        }
                        this.itemPosColumn = ko.observable(itemObject.itemPosColumn);
                        this.categoryAtr = ko.observable(itemObject.categoryAtr);
                        this.autoLineId = ko.observable(itemObject.autoLineId);
                        this.sumScopeAtr = ko.observable(itemObject.sumScopeAtr);
                        this.setOffItemCode = ko.observable(itemObject.setOffItemCode);
                        this.commuteAtr = ko.observable(itemObject.commuteAtr);
                        this.calculationMethod = ko.observable(itemObject.calculationMethod);
                        this.distributeSet = ko.observable(itemObject.distributeSet);
                        this.distributeWay = ko.observable(itemObject.distributeWay);
                        this.personalWageCode = ko.observable(itemObject.personalWageCode);
                        this.isUseHighError = ko.observable(itemObject.isUseHighError);
                        this.errRangeHigh = ko.observable(itemObject.errRangeHigh);
                        this.isUseLowError = ko.observable(itemObject.isUseLowError);
                        this.errRangeLow = ko.observable(itemObject.errRangeLow);
                        this.isUseHighAlam = ko.observable(itemObject.isUseHighAlam);
                        this.alamRangeHigh = ko.observable(itemObject.alamRangeHigh);
                        this.isUseLowAlam = ko.observable(itemObject.isUseLowAlam);
                        this.alamRangeLow = ko.observable(itemObject.alamRangeLow);
                    }
                    ItemDetail.prototype.itemClick = function (data, event) {
                        var _this = this;
                        var self = this;
                        var param = {
                            categoryId: data.categoryAtr(),
                            itemCode: data.itemCode(),
                            isUpdate: data.itemAbName() === "+" ? false : true,
                            startYm: self.screenModel().layoutMaster().startYm,
                            stmtCode: self.screenModel().layoutMaster().stmtCode
                        };
                        nts.uk.ui.windows.setShared('param', param);
                        nts.uk.ui.windows.sub.modal('/view/qmm/019/f/index.xhtml', { title: '項目の選択・設定' }).onClosed(function () {
                            var itemResult = nts.uk.ui.windows.getShared('itemResult');
                            if (itemResult === undefined)
                                return _this;
                            if (data.itemAbName() === "+") {
                                // Them moi
                                self.itemCode(itemResult.itemCode);
                                self.added(true);
                            }
                            else {
                                if (self.added()) {
                                    // Sửa một detail đang được Thêm mới
                                    self.itemCode(itemResult.itemCode);
                                }
                                else if (itemResult.itemCode !== self.itemCode()) {
                                    // Update
                                    self.updateItemCode(itemResult.itemCode);
                                }
                            }
                            self.itemAbName(itemResult.itemAbName);
                            self.sumScopeAtr(itemResult.sumScopeAtr);
                            //self.setOffItemCode(itemResult.setOffItemCode);
                            //self.commuteAtr(itemResult.commuteAtr);
                            self.calculationMethod(itemResult.calculationMethod);
                            self.distributeSet(itemResult.distributeSet);
                            self.distributeWay(itemResult.distributeWay);
                            self.personalWageCode(itemResult.personalWageCode);
                            self.isUseHighError(itemResult.isUseHighError ? 1 : 0);
                            self.errRangeHigh(itemResult.errRangeHigh);
                            self.isUseLowError(itemResult.isUseLowError ? 1 : 0);
                            self.errRangeLow(itemResult.errRangeLow);
                            self.isUseHighAlam(itemResult.isUseHighAlam ? 1 : 0);
                            self.alamRangeHigh(itemResult.alamRangeHigh);
                            self.isUseLowAlam(itemResult.isUseLowAlam ? 1 : 0);
                            self.alamRangeLow(itemResult.alamRangeLow);
                            return _this;
                        });
                    };
                    return ItemDetail;
                }());
                model.ItemDetail = ItemDetail;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
