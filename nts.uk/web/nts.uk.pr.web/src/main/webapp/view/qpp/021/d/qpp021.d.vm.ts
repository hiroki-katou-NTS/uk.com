module nts.uk.pr.view.qpp021.d {
    export module viewmodel {
        export class ScreenModel {
            isVisibleShowCompName: KnockoutObservable<boolean>;
            isEnableShadedBorder: KnockoutObservable<boolean>;
            isVisibleShadedBorder: KnockoutObservable<boolean>;
            isvisibleItem: KnockoutObservable<boolean>;
            isEnableShowZeroInMny: KnockoutObservable<boolean>;
            isEnableShowZeroInAttend: KnockoutObservable<boolean>;
            isEnableShowMnyItemName: KnockoutObservable<boolean>;
            isEnableShowAttendItemName: KnockoutObservable<boolean>;

            usingZeroSettingCtg: KnockoutObservable<number>;
            showZeroInMny: KnockoutObservable<number>;
            showZeroInAttend: KnockoutObservable<number>;
            refundLayoutItem: KnockoutObservable<RefundLayoutModel>;

            zeroItemSetting: KnockoutObservableArray<ItemModel>;
            switchItemList: KnockoutObservableArray<ItemModel>;
            selectPrintYearMonth: KnockoutObservableArray<ItemModel>;
            outputNameDesignation: KnockoutObservableArray<ItemModel>;
            outputDepartment: KnockoutObservableArray<ItemModel>;
            borderLineWidth: KnockoutObservableArray<ItemModel>;
            printType: number;
            constructor() {
                let self = this;
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
                self.usingZeroSettingCtg.subscribe(function(changeValue) {
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
                    } else {
                        self.isEnableShowZeroInMny(false);
                        self.isEnableShowZeroInAttend(false);
                        self.isEnableShowMnyItemName(false);
                        self.isEnableShowAttendItemName(false);
                    }
                });

                self.showZeroInMny.subscribe(function(changeValue) {
                    self.refundLayoutItem().showZeroInMny(changeValue);
                    if (changeValue && changeValue == 1 && self.usingZeroSettingCtg() == 1) {
                        self.isEnableShowMnyItemName(true);
                    } else {
                        self.isEnableShowMnyItemName(false);
                    }
                });

                self.showZeroInAttend.subscribe(function(changeValue) {
                    self.refundLayoutItem().showZeroInAttend(changeValue);
                    if (changeValue && changeValue == 1 && self.usingZeroSettingCtg() == 1) {
                        self.isEnableShowAttendItemName(true);
                    } else {
                        self.isEnableShowAttendItemName(false);
                    }
                });
                self.getShared();
            }

            _init(): void {
                let self = this;
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
            }

            getShared() {
                let self = this;
                let visibleEnable = nts.uk.ui.windows.getShared('QPP021_visible_Enable');
                let isVisible = nts.uk.ui.windows.getShared('QPP021_visible');
                self.isvisibleItem(isVisible);
                self.isVisibleShowCompName(!isVisible);
                if (visibleEnable == 1) {
                    self.isEnableShadedBorder(true);
                    self.isVisibleShadedBorder(true);
                } else if (visibleEnable == 2) {
                    self.isEnableShadedBorder(false);
                    self.isVisibleShadedBorder(true);
                } else {
                    self.isVisibleShadedBorder(false);
                }
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                new service.Service().getRefundLayout(self.printType).done(function(data: any) {
                    self.refundLayoutItem(new RefundLayoutModel(data, self.printType));
                    self.refundLayoutItem().printType(self.printType);
                    self.usingZeroSettingCtg(self.refundLayoutItem().usingZeroSettingCtg());
                    self.showZeroInMny(self.refundLayoutItem().showZeroInMny());
                    self.showZeroInAttend(self.refundLayoutItem().showZeroInAttend());
                    dfd.resolve();
                }).fail(function(error: any) {
                });
                return dfd.promise();
            }

            registration() {
                let self = this;
                new service.Service().insertUpdateData(new RegisRefundLayoutModel(self.refundLayoutItem())).done(function() {
                    nts.uk.ui.windows.close();
                }).fail(function(error: any) {
                });
            }

            closeDialogD() {
                nts.uk.ui.windows.close();
            }
        }

        export class ItemModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class RefundLayoutModel {
            printType: KnockoutObservable<number>;
            usingZeroSettingCtg: KnockoutObservable<number>;
            printYearMonth: KnockoutObservable<number>;
            paymentCellNameCtg: KnockoutObservable<number>;
            isShaded: KnockoutObservable<number>;
            bordWidth: KnockoutObservable<number>;
            showCompName: KnockoutObservable<number>;
            showCompAddInSurface: KnockoutObservable<number>;
            showCompNameInSurface: KnockoutObservable<number>;
            showDependencePerNum: KnockoutObservable<number>;
            showInsuranceLevel: KnockoutObservable<number>;
            showMnyItemName: KnockoutObservable<boolean>;
            showPerAddInSurface: KnockoutObservable<number>;
            showPerNameInSurface: KnockoutObservable<number>;
            showRemainAnnualLeave: KnockoutObservable<number>;
            showTotalTaxMny: KnockoutObservable<number>;
            showZeroInAttend: KnockoutObservable<number>;
            showPerTaxCatalog: KnockoutObservable<number>;
            showDepartment: KnockoutObservable<number>;
            showZeroInMny: KnockoutObservable<number>;
            showProductsPayMny: KnockoutObservable<number>;
            showAttendItemName: KnockoutObservable<boolean>;
            constructor(refundMapping: any, printType: number) {
                let self = this;
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
                } else {
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
        }
        export class RegisRefundLayoutModel {
            printType: number;
            usingZeroSettingCtg: number;
            printYearMonth: number;
            paymentCellNameCtg: number;
            isShaded: number;
            bordWidth: number;
            showCompName: number;
            showCompAddInSurface: number;
            showCompNameInSurface: number;
            showDependencePerNum: number;
            showInsuranceLevel: number;
            showMnyItemName: number;
            showPerAddInSurface: number;
            showPerNameInSurface: number;
            showRemainAnnualLeave: number;
            showTotalTaxMny: number;
            showZeroInAttend: number;
            showPerTaxCatalog: number;
            showDepartment: number;
            showZeroInMny: number;
            showProductsPayMny: number;
            showAttendItemName: number;
            constructor(refundLayout: RefundLayoutModel) {
                let self = this;
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
        }
    }
}