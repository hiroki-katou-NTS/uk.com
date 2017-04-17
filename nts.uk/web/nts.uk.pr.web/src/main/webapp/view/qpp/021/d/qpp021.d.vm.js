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
                    self.isEnableShadedBorder = ko.observable(false);
                    self.isVisibleShadedBorder = ko.observable(false);
                    self.isvisibleItem = ko.observable(false);
                    self.isEnableShowZeroInMny = ko.observable(false);
                    self.isEnableShowZeroInAttend = ko.observable(false);
                    self.isEnableShowMnyItemName = ko.observable(false);
                    self.isEnableShowAttendItemName = ko.observable(false);
                    self.usingZeroSettingCtg = ko.observable(1);
                    self.showZeroInMny = ko.observable(1);
                    self.showZeroInAttend = ko.observable(1);
                    self.refundLayoutItem = ko.observable(new RefundLayoutModel(null));
                    self.usingZeroSettingCtg.subscribe(function (changeValue) {
                        self.refundLayoutItem().usingZeroSettingCtg(changeValue);
                        if (changeValue == 2) {
                            self.isEnableShowZeroInMny(true);
                            self.isEnableShowZeroInAttend(true);
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
                        if (changeValue && changeValue == 2) {
                            self.isEnableShowMnyItemName(true);
                        }
                        else {
                            self.isEnableShowMnyItemName(false);
                        }
                    });
                    self.showZeroInAttend.subscribe(function (changeValue) {
                        self.refundLayoutItem().showZeroInAttend(changeValue);
                        if (changeValue && changeValue == 2) {
                            self.isEnableShowAttendItemName(true);
                        }
                        else {
                            self.isEnableShowAttendItemName(false);
                        }
                    });
                }
                ScreenModel.prototype._init = function () {
                    var self = this;
                    self.zeroItemSetting = ko.observableArray([
                        new ItemModel(1, "項目名の登録の設定を優先する"),
                        new ItemModel(2, "個別にッ設定する")
                    ]);
                    self.switchItemList = ko.observableArray([
                        new ItemModel(1, "する"),
                        new ItemModel(2, "しない")
                    ]);
                    self.selectPrintYearMonth = ko.observableArray([
                        new ItemModel(1, "現在処理年月の2ヶ月前"),
                        new ItemModel(2, "現在処理年月の1か月前"),
                        new ItemModel(3, "現在処理年月"),
                        new ItemModel(4, "現在処理年月の翌月"),
                        new ItemModel(5, "現在処理年月の2ヶ月後")
                    ]);
                    self.outputNameDesignation = ko.observableArray([
                        new ItemModel(1, "個人情報より取得する"),
                        new ItemModel(2, "項目名より取得する"),
                    ]);
                    self.outputDepartment = ko.observableArray([
                        new ItemModel(1, "部門コードを出力する"),
                        new ItemModel(2, "部門名を出力する"),
                        new ItemModel(3, "出力しない"),
                    ]);
                    self.borderLineWidth = ko.observableArray([
                        new ItemModel(1, "太い"),
                        new ItemModel(2, "標準"),
                        new ItemModel(3, "細い    "),
                    ]);
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var printTyle = nts.uk.ui.windows.getShared('QPP021_print_type');
                    var visibleEnable = nts.uk.ui.windows.getShared('QPP021_visible_Enable');
                    var isVisible = nts.uk.ui.windows.getShared('QPP021_visible');
                    self.isvisibleItem(true);
                    if (visibleEnable == 1) {
                        self.isEnableShadedBorder(true);
                        self.isVisibleShadedBorder(true);
                    }
                    else if (visibleEnable == 2) {
                        alert(self.isEnableShadedBorder());
                        self.isEnableShadedBorder(false);
                        self.isVisibleShadedBorder(true);
                    }
                    d.service.getRefundLayout(1).done(function (data) {
                        self.refundLayoutItem(new RefundLayoutModel(data));
                        dfd.resolve();
                    }).fail(function (error) {
                    });
                    self.usingZeroSettingCtg(self.refundLayoutItem().usingZeroSettingCtg());
                    self.showZeroInMny(self.refundLayoutItem().showZeroInMny());
                    self.showZeroInAttend(self.refundLayoutItem().showZeroInAttend());
                    return dfd.promise();
                };
                ScreenModel.prototype.registration = function () {
                    var self = this;
                    d.service.insertUpdateData(new RegisRefundLayoutModel(self.refundLayoutItem())).done(function () {
                        alert("registration complete");
                        nts.uk.ui.windows.close();
                    }).fail(function (error) {
                        alert("registration error");
                    });
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
                function RefundLayoutModel(refundMapping) {
                    var self = this;
                    if (refundMapping) {
                        self.printType = ko.observable(refundMapping.printType);
                        self.usingZeroSettingCtg = ko.observable(refundMapping.usingZeroSettingCtg);
                        self.printYearMonth = ko.observable(refundMapping.printYearMonth);
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
                        self.printType = ko.observable(1);
                        self.usingZeroSettingCtg = ko.observable(1);
                        self.printYearMonth = ko.observable(3);
                        self.paymentCellNameCtg = ko.observable(1);
                        self.isShaded = ko.observable(1);
                        self.bordWidth = ko.observable(1);
                        self.showCompName = ko.observable(1);
                        self.showCompAddInSurface = ko.observable(1);
                        self.showCompNameInSurface = ko.observable(1);
                        self.showDependencePerNum = ko.observable(1);
                        self.showInsuranceLevel = ko.observable(1);
                        self.showMnyItemName = ko.observable(false);
                        self.showPerAddInSurface = ko.observable(1);
                        self.showPerNameInSurface = ko.observable(1);
                        self.showRemainAnnualLeave = ko.observable(1);
                        self.showTotalTaxMny = ko.observable(1);
                        self.showZeroInAttend = ko.observable(1);
                        self.showPerTaxCatalog = ko.observable(1);
                        self.showDepartment = ko.observable(1);
                        self.showZeroInMny = ko.observable(1);
                        self.showProductsPayMny = ko.observable(1);
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
                    self.showMnyItemName = refundLayout.showMnyItemName() === true ? 1 : 2;
                    self.showPerAddInSurface = refundLayout.showPerAddInSurface();
                    self.showPerNameInSurface = refundLayout.showPerNameInSurface();
                    self.showRemainAnnualLeave = refundLayout.showRemainAnnualLeave();
                    self.showTotalTaxMny = refundLayout.showTotalTaxMny();
                    self.showZeroInAttend = refundLayout.showZeroInAttend();
                    self.showPerTaxCatalog = refundLayout.showPerTaxCatalog();
                    self.showDepartment = refundLayout.showDepartment();
                    self.showZeroInMny = refundLayout.showZeroInMny();
                    self.showProductsPayMny = refundLayout.showProductsPayMny();
                    self.showAttendItemName = refundLayout.showAttendItemName() === true ? 1 : 2;
                }
                return RegisRefundLayoutModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qpp021.d || (qpp021.d = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.d.vm.js.map