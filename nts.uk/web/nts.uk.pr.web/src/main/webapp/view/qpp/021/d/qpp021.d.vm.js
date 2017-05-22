var qpp021;
(function (qpp021) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self._init();
                    self.printType = nts.uk.ui.windows.getShared('QPP021_print_type');
                    self.isVisibleShowCompName = ko.observable(true);
                    self.isEnableShadedBorder = ko.observable(true);
                    self.isVisibleShadedBorder = ko.observable(true);
                    self.isvisibleItem = ko.observable(false);
                    self.isEnableShowZeroInMny = ko.observable(false);
                    self.isEnableShowZeroInAttend = ko.observable(false);
                    self.isEnableShowMnyItemName = ko.observable(false);
                    self.isEnableShowAttendItemName = ko.observable(false);
                    self.usingZeroSettingCtg = ko.observable(0);
                    self.showZeroInMny = ko.observable(0);
                    self.showZeroInAttend = ko.observable(0);
                    self.refundLayoutItem = ko.observable(new RefundLayoutModel(null, self.printType));
                    self.usingZeroSettingCtg.subscribe(function (changeValue) {
                        self.refundLayoutItem().usingZeroSettingCtg(changeValue);
                        if (changeValue == 1) {
                            self.isEnableShowZeroInMny(true);
                            self.isEnableShowZeroInAttend(true);
                            self.isEnableShowMnyItemName(false);
                            self.isEnableShowAttendItemName(false);
                            if (self.refundLayoutItem().showZeroInMny() == 1) {
                                self.isEnableShowMnyItemName(true);
                            }
                            if (self.refundLayoutItem().showZeroInAttend() == 1) {
                                self.isEnableShowAttendItemName(true);
                            }
                        }
                        else {
                            self.isEnableShowZeroInMny(false);
                            self.isEnableShowZeroInAttend(false);
                            self.isEnableShowMnyItemName(false);
                            self.isEnableShowAttendItemName(false);
                        }
                    });
                    self.showZeroInMny.subscribe(function (changeValue) {
                        self.refundLayoutItem().showZeroInMny(changeValue);
                        if (changeValue && changeValue == 1 && self.usingZeroSettingCtg() == 1) {
                            self.isEnableShowMnyItemName(true);
                        }
                        else {
                            self.isEnableShowMnyItemName(false);
                        }
                    });
                    self.showZeroInAttend.subscribe(function (changeValue) {
                        self.refundLayoutItem().showZeroInAttend(changeValue);
                        if (changeValue && changeValue == 1 && self.usingZeroSettingCtg() == 1) {
                            self.isEnableShowAttendItemName(true);
                        }
                        else {
                            self.isEnableShowAttendItemName(false);
                        }
                    });
                    self.getShared();
                }
                ScreenModel.prototype._init = function () {
                    var self = this;
                    self.zeroItemSetting = ko.observableArray([
                        new ItemModel(0, "項目名の登録の設定を優先する"),
                        new ItemModel(1, "個別にッ設定する")
                    ]);
                    self.switchItemList = ko.observableArray([
                        new ItemModel(0, "する"),
                        new ItemModel(1, "しない")
                    ]);
                    self.selectPrintYearMonth = ko.observableArray([
                        new ItemModel(1, "現在処理年月の2ヶ月前"),
                        new ItemModel(2, "現在処理年月の1か月前"),
                        new ItemModel(3, "現在処理年月"),
                        new ItemModel(4, "現在処理年月の翌月"),
                        new ItemModel(5, "現在処理年月の2ヶ月後")
                    ]);
                    self.outputNameDesignation = ko.observableArray([
                        new ItemModel(0, "個人情報より取得する"),
                        new ItemModel(1, "項目名より取得する"),
                    ]);
                    self.outputDepartment = ko.observableArray([
                        new ItemModel(0, "部門コードを出力する"),
                        new ItemModel(1, "部門名を出力する"),
                        new ItemModel(2, "出力しない"),
                    ]);
                    self.borderLineWidth = ko.observableArray([
                        new ItemModel(0, "太い"),
                        new ItemModel(1, "標準"),
                        new ItemModel(2, "細い    "),
                    ]);
                };
                ScreenModel.prototype.getShared = function () {
                    var self = this;
                    var visibleEnable = nts.uk.ui.windows.getShared('QPP021_visible_Enable');
                    var isVisible = nts.uk.ui.windows.getShared('QPP021_visible');
                    self.isvisibleItem(isVisible);
                    self.isVisibleShowCompName(!isVisible);
                    if (visibleEnable == 1) {
                        self.isEnableShadedBorder(true);
                        self.isVisibleShadedBorder(true);
                    }
                    else if (visibleEnable == 2) {
                        self.isEnableShadedBorder(false);
                        self.isVisibleShadedBorder(true);
                    }
                    else {
                        self.isVisibleShadedBorder(false);
                    }
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    d.service.getRefundLayout(self.printType).done(function (data) {
                        self.refundLayoutItem(new RefundLayoutModel(data, self.printType));
                        self.refundLayoutItem().printType(self.printType);
                        self.usingZeroSettingCtg(self.refundLayoutItem().usingZeroSettingCtg());
                        self.showZeroInMny(self.refundLayoutItem().showZeroInMny());
                        self.showZeroInAttend(self.refundLayoutItem().showZeroInAttend());
                        dfd.resolve();
                    }).fail(function (error) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.registration = function () {
                    var self = this;
                    d.service.insertUpdateData(new RegisRefundLayoutModel(self.refundLayoutItem())).done(function () {
                        nts.uk.ui.windows.close();
                    }).fail(function (error) {
                    });
                };
                ScreenModel.prototype.closeDialogD = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            var RefundLayoutModel = (function () {
                function RefundLayoutModel(refundMapping, printType) {
                    var self = this;
                    if (refundMapping) {
                        self.printType = ko.observable(refundMapping.printType);
                        self.usingZeroSettingCtg = ko.observable(refundMapping.usingZeroSettingCtg);
                        self.printYearMonth = ko.observable(refundMapping.printYearMonth);
                        console.log(refundMapping.printYearMonth);
                        self.paymentCellNameCtg = ko.observable(refundMapping.paymentCellNameCtg);
                        self.isShaded = ko.observable(refundMapping.isShaded);
                        self.bordWidth = ko.observable(refundMapping.bordWidth);
                        self.showCompName = ko.observable(refundMapping.showCompName);
                        self.showCompAddInSurface = ko.observable(refundMapping.showCompAddInSurface);
                        self.showCompNameInSurface = ko.observable(refundMapping.showCompNameInSurface);
                        self.showDependencePerNum = ko.observable(refundMapping.showDependencePerNum);
                        self.showInsuranceLevel = ko.observable(refundMapping.showInsuranceLevel);
                        self.showMnyItemName = ko.observable(refundMapping.showMnyItemName === 1 ? true : false);
                        self.showPerAddInSurface = ko.observable(refundMapping.showPerAddInSurface);
                        self.showPerNameInSurface = ko.observable(refundMapping.showPerNameInSurface);
                        self.showRemainAnnualLeave = ko.observable(refundMapping.showRemainAnnualLeave);
                        self.showTotalTaxMny = ko.observable(refundMapping.showTotalTaxMny);
                        self.showZeroInAttend = ko.observable(refundMapping.showZeroInAttend);
                        self.showPerTaxCatalog = ko.observable(refundMapping.showPerTaxCatalog);
                        self.showDepartment = ko.observable(refundMapping.showDepartment);
                        self.showZeroInMny = ko.observable(refundMapping.showZeroInMny);
                        self.showProductsPayMny = ko.observable(refundMapping.showProductsPayMny);
                        self.showAttendItemName = ko.observable(refundMapping.showAttendItemName === 1 ? true : false);
                    }
                    else {
                        self.printType = ko.observable(printType);
                        self.usingZeroSettingCtg = ko.observable(0);
                        self.printYearMonth = ko.observable(3);
                        self.paymentCellNameCtg = ko.observable(0);
                        self.isShaded = ko.observable(0);
                        self.bordWidth = ko.observable(1);
                        self.showCompName = ko.observable(0);
                        self.showCompAddInSurface = ko.observable(0);
                        self.showCompNameInSurface = ko.observable(0);
                        self.showDependencePerNum = ko.observable(0);
                        self.showInsuranceLevel = ko.observable(0);
                        self.showMnyItemName = ko.observable(false);
                        self.showPerAddInSurface = ko.observable(0);
                        self.showPerNameInSurface = ko.observable(0);
                        self.showRemainAnnualLeave = ko.observable(0);
                        self.showTotalTaxMny = ko.observable(0);
                        self.showZeroInAttend = ko.observable(0);
                        self.showPerTaxCatalog = ko.observable(0);
                        self.showDepartment = ko.observable(1);
                        self.showZeroInMny = ko.observable(0);
                        self.showProductsPayMny = ko.observable(0);
                        self.showAttendItemName = ko.observable(false);
                    }
                }
                return RefundLayoutModel;
            }());
            var RegisRefundLayoutModel = (function () {
                function RegisRefundLayoutModel(refundLayout) {
                    var self = this;
                    self.printType = refundLayout.printType();
                    self.usingZeroSettingCtg = refundLayout.usingZeroSettingCtg();
                    self.printYearMonth = refundLayout.printYearMonth();
                    self.paymentCellNameCtg = refundLayout.paymentCellNameCtg();
                    self.isShaded = refundLayout.isShaded();
                    self.bordWidth = refundLayout.bordWidth();
                    self.showCompName = refundLayout.showCompName();
                    self.showCompAddInSurface = refundLayout.showCompAddInSurface();
                    self.showCompNameInSurface = refundLayout.showCompNameInSurface();
                    self.showDependencePerNum = refundLayout.showDependencePerNum();
                    self.showInsuranceLevel = refundLayout.showInsuranceLevel();
                    self.showMnyItemName = refundLayout.showMnyItemName() === true ? 1 : 0;
                    self.showPerAddInSurface = refundLayout.showPerAddInSurface();
                    self.showPerNameInSurface = refundLayout.showPerNameInSurface();
                    self.showRemainAnnualLeave = refundLayout.showRemainAnnualLeave();
                    self.showTotalTaxMny = refundLayout.showTotalTaxMny();
                    self.showZeroInAttend = refundLayout.showZeroInAttend();
                    self.showPerTaxCatalog = refundLayout.showPerTaxCatalog();
                    self.showDepartment = refundLayout.showDepartment();
                    self.showZeroInMny = refundLayout.showZeroInMny();
                    self.showProductsPayMny = refundLayout.showProductsPayMny();
                    self.showAttendItemName = refundLayout.showAttendItemName() === true ? 1 : 0;
                }
                return RegisRefundLayoutModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qpp021.d || (qpp021.d = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.d.vm.js.map