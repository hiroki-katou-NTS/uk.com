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
            function getCategoryFull(layoutCode, startYm) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getCategoryFull + "/" + layoutCode + "/" + startYm)
                    .done(function (res) {
                    var result = _.map(res, function (category) {
                        return new model.Category(category.lines, category.categoryAtr);
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
                var command = {
                    layoutCommand: {
                        stmtCode: layout.stmtCode,
                        startYm: layout.startYm,
                        stmtName: layout.stmtName,
                        endYm: layout.endYm
                    },
                    categoryCommand: [],
                    lineCommand: [],
                    detailCommand: [],
                    listCategoryAtrDeleted: [],
                    listAutoLineIdDeleted: [],
                    listItemCodeDeleted: []
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
                    function Category(lines, categoryAtr) {
                        this.hasSetting = false;
                        this.lines = ko.observableArray([]);
                        this.lines(_.map(lines, function (line) {
                            var details = _.map(line.details, function (detail) {
                                return new model.ItemDetail(detail);
                            });
                            return new model.Line(line.categoryAtr, details, line.autoLineId, line.lineDispayAtr, line.linePosition);
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
                        //TODO: di den man hinh ...
                        alert(data.categoryName);
                    };
                    Category.prototype.addLine = function () {
                        var self = this;
                        var autoLineId = self.categoryAtr + "-lineId" + self.lines().length;
                        var itemDetailObj1 = { itemCode: "1", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj2 = { itemCode: "2", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj3 = { itemCode: "3", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj4 = { itemCode: "4", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj5 = { itemCode: "5", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj6 = { itemCode: "6", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj7 = { itemCode: "7", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj8 = { itemCode: "8", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        var itemDetailObj9 = { itemCode: "9", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                            categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                            distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                            errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                            alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0 };
                        self.lines.push(new Line(self.categoryAtr, ([
                            new ItemDetail(itemDetailObj1),
                            new ItemDetail(itemDetailObj2),
                            new ItemDetail(itemDetailObj3),
                            new ItemDetail(itemDetailObj4),
                            new ItemDetail(itemDetailObj5),
                            new ItemDetail(itemDetailObj6),
                            new ItemDetail(itemDetailObj7),
                            new ItemDetail(itemDetailObj8),
                            new ItemDetail(itemDetailObj9)
                        ]), autoLineId, 1, self.lines.length));
                        a.ScreenModel.prototype.bindSortable();
                        a.ScreenModel.prototype.destroySortable();
                        a.ScreenModel.prototype.bindSortable();
                    };
                    return Category;
                }());
                model.Category = Category;
                var Line = (function () {
                    function Line(categoryAtr, itemDetails, autoLineId, lineDispayAtr, linePosition) {
                        this.hasRequiredItem = false;
                        this.details = itemDetails;
                        this.autoLineId = autoLineId;
                        this.rowId = categoryAtr + autoLineId;
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
                        //TODO: goi man hinh khac
                        if (data.hasRequiredItem === false) {
                            $("#" + data.rowId).addClass("ground-gray");
                        }
                    };
                    return Line;
                }());
                model.Line = Line;
                var ItemDetail = (function () {
                    //                itemCode: string, itemAbName: string, isRequired: boolean, itemPosColumn: number,
                    //                            categoryAtr: number, autoLineId: string, sumScopeAtr: number, calculationMethod: number,
                    //                            distributeSet: number, distributeWay: number, personalWageCode: string, isUseHighError: number,
                    //                            errRangeHigh: number, isUseLowError: number, errRangeLow: number, isUseHighAlam: number,
                    //                            alamRangeHigh: number, isUseLowAlam: number, alamRangeLow: number
                    function ItemDetail(itemObject) {
                        this.isRequired = ko.observable(false);
                        this.itemCode = ko.observable(itemObject.itemCode);
                        this.itemAbName = ko.observable(itemObject.itemAbName);
                        if (itemObject.itemCode === "F003" || itemObject.itemCode === "F114") {
                            this.isRequired = ko.observable(true);
                        }
                        this.itemPosColumn = ko.observable(itemObject.itemPosColumn);
                        this.categoryAtr = ko.observable(itemObject.categoryAtr);
                        this.autoLineId = ko.observable(itemObject.autoLineId);
                        this.sumScopeAtr = ko.observable(itemObject.sumScopeAtr);
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
                    //TODO: goi man hinh chi tiet
                    ItemDetail.prototype.itemClick = function (data, event) {
                        alert(data.itemAbName() + " ~~~ " + data.itemPosColumn());
                    };
                    return ItemDetail;
                }());
                model.ItemDetail = ItemDetail;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
