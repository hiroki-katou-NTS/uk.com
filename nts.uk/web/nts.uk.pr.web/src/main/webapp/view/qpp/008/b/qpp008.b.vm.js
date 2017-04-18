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
                    self.printSettingDirty = new nts.uk.ui.DirtyChecker(self.printSetting);
                    self.roundingRules = ko.observableArray([
                        { code: '0', name: '表示する' },
                        { code: '1', name: '表示しない' },
                    ]);
                    self.roundingRules1 = ko.observableArray([
                        { code1: '0', name1: '表示する' },
                        { code1: '1', name1: '表示しない' },
                    ]);
                    self.hrchyIndexArray = ko.computed(function () {
                        var itemsDetail = [0, 0, 0, 0, 0];
                        var hrchyIndexSelect = self.printSetting().hrchyIndexSelectId().sort();
                        _.forEach(hrchyIndexSelect, function (item, i) {
                            for (var x = 0; x < 5; x++) {
                                if (itemsDetail[x] === 0 && i < 5) {
                                    itemsDetail[x] = item;
                                    break;
                                }
                            }
                        });
                        return itemsDetail;
                    }, self).extend({ deferred: true });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.loadData().done(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    b.service.getComparingPrintSet().done(function (data) {
                        self.printSetting(new PrintSettingModel(data));
                        self.printSettingDirty.reset();
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.configurationPrintSetting = function () {
                    var self = this;
                    b.service.insertUpdateData(new ConfigPrintSettingModel(self.printSetting(), self.hrchyIndexArray())).done(function () {
                        self.loadData();
                    });
                };
                ScreenModel.prototype.closeDialog = function () {
                    var self = this;
                    if (!self.printSettingDirty.isDirty()) {
                        nts.uk.ui.windows.close();
                        return;
                    }
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                        self.printSettingDirty.reset();
                        nts.uk.ui.windows.close();
                    }).ifCancel(function () {
                        return;
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var PrintSettingModel = (function () {
                function PrintSettingModel(settingMapping) {
                    this.plushBackColor = ko.observable("#cfe2f3");
                    this.minusBackColor = ko.observable("#f4cccc");
                    this.showItemIfCfWithNull = ko.observable(0);
                    this.showItemIfSameValue = ko.observable(0);
                    this.showPayment = ko.observable(true);
                    this.totalSet = ko.observable(true);
                    this.sumEachDeprtSet = ko.observable(false);
                    this.sumDepHrchyIndexSet = ko.observable(false);
                    this.hrchyIndexList = ko.observableArray([]);
                    this.hrchyIndexSelectId = ko.observableArray([]);
                    var self = this;
                    for (var i = 1; i < 10; i++) {
                        self.hrchyIndexList.push(new HrchyIndexModel(i, i + " 階層"));
                    }
                    if (settingMapping) {
                        self.plushBackColor(settingMapping.plushBackColor);
                        self.minusBackColor(settingMapping.minusBackColor);
                        self.showItemIfCfWithNull(settingMapping.showItemIfCfWithNull);
                        self.showItemIfSameValue(settingMapping.showItemIfSameValue);
                        self.showPayment(settingMapping.showPayment === 0 ? false : true);
                        self.totalSet(settingMapping.totalSet === 0 ? false : true);
                        self.sumEachDeprtSet(settingMapping.sumEachDeprtSet === 0 ? false : true);
                        self.sumDepHrchyIndexSet(settingMapping.sumDepHrchyIndexSet === 0 ? false : true);
                        if (settingMapping.hrchyIndex1 > 0) {
                            self.hrchyIndexSelectId.push(settingMapping.hrchyIndex1);
                        }
                        if (settingMapping.hrchyIndex2 > 1) {
                            self.hrchyIndexSelectId.push(settingMapping.hrchyIndex2);
                        }
                        if (settingMapping.hrchyIndex3 > 2) {
                            self.hrchyIndexSelectId.push(settingMapping.hrchyIndex3);
                        }
                        if (settingMapping.hrchyIndex4 > 3) {
                            self.hrchyIndexSelectId.push(settingMapping.hrchyIndex4);
                        }
                        if (settingMapping.hrchyIndex5 > 4) {
                            self.hrchyIndexSelectId.push(settingMapping.hrchyIndex5);
                        }
                    }
                }
                return PrintSettingModel;
            }());
            viewmodel.PrintSettingModel = PrintSettingModel;
            var HrchyIndexModel = (function () {
                function HrchyIndexModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return HrchyIndexModel;
            }());
            viewmodel.HrchyIndexModel = HrchyIndexModel;
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
                    this.hrchyIndex1 = hrchyIndex1;
                    this.hrchyIndex2 = hrchyIndex2;
                    this.hrchyIndex3 = hrchyIndex3;
                    this.hrchyIndex4 = hrchyIndex4;
                    this.hrchyIndex5 = hrchyIndex5;
                }
                return PrintSettingMapping;
            }());
            viewmodel.PrintSettingMapping = PrintSettingMapping;
            var ConfigPrintSettingModel = (function () {
                function ConfigPrintSettingModel(printSettingModel, hrchyIndexArray) {
                    this.plushBackColor = "#cfe2f3";
                    this.minusBackColor = "#f4cccc";
                    this.showItemIfCfWithNull = 0;
                    this.showItemIfSameValue = 0;
                    this.showPayment = 1;
                    this.totalSet = 1;
                    this.sumEachDeprtSet = 0;
                    this.sumDepHrchyIndexSet = 0;
                    this.hrchyIndex1 = 0;
                    this.hrchyIndex2 = 0;
                    this.hrchyIndex3 = 0;
                    this.hrchyIndex4 = 0;
                    this.hrchyIndex5 = 0;
                    if (printSettingModel) {
                        this.plushBackColor = printSettingModel.plushBackColor();
                        this.minusBackColor = printSettingModel.minusBackColor();
                        this.showItemIfCfWithNull = printSettingModel.showItemIfCfWithNull();
                        this.showItemIfSameValue = printSettingModel.showItemIfSameValue();
                        this.showPayment = (printSettingModel.showPayment() === true ? 1 : 0);
                        this.totalSet = (printSettingModel.totalSet() === true ? 1 : 0);
                        this.sumEachDeprtSet = (printSettingModel.sumEachDeprtSet() === true ? 1 : 0);
                        this.sumDepHrchyIndexSet = (printSettingModel.sumDepHrchyIndexSet() === true ? 1 : 0);
                        this.hrchyIndex1 = hrchyIndexArray[0];
                        this.hrchyIndex2 = hrchyIndexArray[1];
                        this.hrchyIndex3 = hrchyIndexArray[2];
                        this.hrchyIndex4 = hrchyIndexArray[3];
                        this.hrchyIndex5 = hrchyIndexArray[4];
                    }
                }
                return ConfigPrintSettingModel;
            }());
            viewmodel.ConfigPrintSettingModel = ConfigPrintSettingModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp008.b || (qpp008.b = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.b.vm.js.map