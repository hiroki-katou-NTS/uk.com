var qpp008;
(function (qpp008) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.printSetting = ko.observable(new PrintSettingModel(null));
                    self.selectedPaymentDate = ko.observable(null);
                    self.departmentDate = ko.observable('2017/01/13' + 'の部門構成で集計します。');
                    self.roundingRules = ko.observableArray([
                        { code: '0', name: '表示する' },
                        { code: '1', name: '表示しない' },
                    ]);
                    self.roundingRules1 = ko.observableArray([
                        { code1: '0', name1: '表示する' },
                        { code1: '1', name1: '表示しない' },
                    ]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.reload();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.reload = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    b.service.getComparingPrintSet().done(function (data) {
                        self.printSetting(new PrintSettingModel(data));
                        dfd.resolve(data);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var PrintSettingModel = (function () {
                function PrintSettingModel(settingMapping) {
                    this.hrchyIndex1 = ko.observable(false);
                    this.hrchyIndex2 = ko.observable(false);
                    this.hrchyIndex3 = ko.observable(false);
                    this.hrchyIndex4 = ko.observable(false);
                    this.hrchyIndex5 = ko.observable(false);
                    this.hrchyIndex6 = ko.observable(false);
                    this.hrchyIndex7 = ko.observable(false);
                    this.hrchyIndex8 = ko.observable(false);
                    this.hrchyIndex9 = ko.observable(false);
                    if (settingMapping) {
                        this.plushBackColor = ko.observable(settingMapping.plushBackColor);
                        this.minusBackColor = ko.observable(settingMapping.minusBackColor);
                        this.showItemIfCfWithNull = ko.observable(settingMapping.showItemIfCfWithNull);
                        this.showItemIfSameValue = ko.observable(settingMapping.showItemIfSameValue);
                        this.showPayment = ko.observable(settingMapping.showPayment === 0 ? false : true);
                        this.totalSet = ko.observable(settingMapping.totalSet === 0 ? false : true);
                        this.sumEachDeprtSet = ko.observable(settingMapping.sumEachDeprtSet === 0 ? false : true);
                        this.sumDepHrchyIndexSet = ko.observable(settingMapping.sumDepHrchyIndexSet === 0 ? false : true);
                        this.mappingHrchyIndex(settingMapping.hrchyIndexList);
                    }
                    else {
                        this.plushBackColor = ko.observable("#cfe2f3");
                        this.minusBackColor = ko.observable("#f4cccc");
                        this.showItemIfCfWithNull = ko.observable(0);
                        this.showItemIfSameValue = ko.observable(0);
                        this.showPayment = ko.observable(true);
                        this.totalSet = ko.observable(true);
                        this.sumEachDeprtSet = ko.observable(false);
                        this.sumDepHrchyIndexSet = ko.observable(false);
                    }
                }
                PrintSettingModel.prototype.mappingHrchyIndex = function (hrchyIndexList) {
                    _.forEach(hrchyIndexList, function (value, i) {
                        switch (value) {
                            case 1:
                                if (i == 0) {
                                    this.hrchyIndex1(true);
                                }
                                break;
                            case 2:
                                if (i == 0 || i == 1) {
                                    this.hrchyIndex2(true);
                                }
                                break;
                            case 3:
                                if (i == 0 || i == 1 || i == 2) {
                                    this.hrchyIndex3(true);
                                }
                                break;
                            case 4:
                                if (i != 4) {
                                    this.hrchyIndex4(false);
                                }
                                break;
                            case 5:
                                this.hrchyIndex5(true);
                                break;
                            case 6:
                                this.hrchyIndex6(true);
                                break;
                            case 7:
                                this.hrchyIndex7(true);
                                break;
                            case 8:
                                this.hrchyIndex8(true);
                                break;
                            case 9:
                                this.hrchyIndex9(true);
                                break;
                        }
                    });
                };
                return PrintSettingModel;
            }());
            viewmodel.PrintSettingModel = PrintSettingModel;
            var PrintSettingMapping = (function () {
                function PrintSettingMapping(plushBackColor, minusBackColor, showItemIfCfWithNull, showItemIfSameValue, showPayment, totalSet, sumEachDeprtSet, sumDepHrchyIndexSet, hrchyIndex1, hrchyIndex2, hrchyIndex3, hrchyIndex4, hrchyIndex5) {
                    this.plushBackColor = plushBackColor;
                    this.minusBackColor = minusBackColor;
                    this.showItemIfCfWithNull = showItemIfCfWithNull;
                    this.showItemIfSameValue = showItemIfSameValue;
                    this.showPayment = showPayment;
                    this.totalSet = totalSet;
                    this.sumEachDeprtSet = sumEachDeprtSet;
                    this.sumDepHrchyIndexSet = sumDepHrchyIndexSet;
                    this.hrchyIndexList.push(hrchyIndex1);
                    this.hrchyIndexList.push(hrchyIndex2);
                    this.hrchyIndexList.push(hrchyIndex3);
                    this.hrchyIndexList.push(hrchyIndex4);
                    this.hrchyIndexList.push(hrchyIndex5);
                }
                return PrintSettingMapping;
            }());
            viewmodel.PrintSettingMapping = PrintSettingMapping;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp008.b || (qpp008.b = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.b.vm.js.map