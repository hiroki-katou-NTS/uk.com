var qmm020;
(function (qmm020) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.count = 100;
                    var self = this;
                    self.itemList = ko.observableArray([]);
                    self.selectedCode = ko.observable(null);
                    self.singleSelectedCode = ko.observable(null);
                    self.items = ko.observableArray([]);
                    self.companyCode = ko.observable(3);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                    self.columns2 = ko.observableArray([
                        { headerText: 'コード', key: 'classificationCode', dataType: "string", width: 100 },
                        { headerText: '名称', key: 'classificationName', dataType: "string", width: 150 },
                        {
                            headerText: "給与明細書", key: "paymentDetailCode", dataType: "string", width: "250px",
                            template: "<input type='button' id='E_BTN_001' value='選択'/><label style='margin-left:5px;'>${paymentDetailCode}</label><label style='margin-left:15px;'></label>"
                        },
                        {
                            headerText: "賞与明細書", key: "bonusDetailCode", dataType: "string", width: "250px",
                            template: "<input type='button' id='E_BTN_001' value='選択'/><label style='margin-left:5px;'>${bonusDetailCode}</label><label style='margin-left:15px;'></label>"
                        },
                    ]);
                    self.companyCode = ko.observable();
                    self.selectedCode.subscribe(function (codeChanged) {
                        self.getClassificationAllotSettingList(codeChanged);
                    });
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    e.service.getAllClassificationAllotSettingHeader().done(function (classificationAllotSettingHeaders) {
                        if (classificationAllotSettingHeaders.length > 0) {
                            var test = _.map(classificationAllotSettingHeaders, function (value) {
                                return new ClassificationAllotSettingHeaderDto(value.companyCode, value.historyId, value.startDateYM, value.endDateYM);
                            });
                            self.itemList(test);
                            self.selectedCode(ko.unwrap(classificationAllotSettingHeaders[0].historyId));
                            e.service.getAllClassificationAllotSetting(self.selectedCode()).done(function (classificationAllotSettings) {
                                self.items(classificationAllotSettings);
                                dfd.resolve();
                            });
                        }
                        else {
                            dfd.resolve();
                        }
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getCurrentSelectItem = function () {
                    var self = this;
                    return _.find(self.itemList(), function (item) {
                        return item.historyId == self.selectedCode();
                    });
                };
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    var history = _.find(self.itemList(), function (history) {
                        return history.historyId == self.selectedCode();
                    });
                    var startYm = history.startDateYM;
                    var valueShareJDialog = "1~" + startYm;
                    nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { title: '明細書の紐ずけ＞履歴追加' }).onClosed(function () {
                        var jDialogResult = nts.uk.ui.windows.getShared('returnJDialog');
                        if (jDialogResult !== undefined) {
                            var action = jDialogResult.split("~")[0];
                            var yearmonth = jDialogResult.split("~")[1].replace("/", "");
                            var updateItem = self.getCurrentSelectItem();
                            updateItem.startDateYM = yearmonth;
                            console.log(updateItem);
                            e.service.updateClassificationAllotSettingHeader(updateItem).done(function () {
                                e.service.getAllClassificationAllotSettingHeader().done(function (classificationAllotSettingHeaders) {
                                    debugger;
                                    var test = _.map(classificationAllotSettingHeaders, function (value) {
                                        return new ClassificationAllotSettingHeaderDto(value.companyCode, value.historyId, value.startDateYM, value.endDateYM);
                                    });
                                    self.itemList(test);
                                });
                            });
                        }
                    });
                };
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function () {
                    });
                };
                ScreenModel.prototype.getClassificationAllotSettingList = function (historyId) {
                    var self = this;
                    e.service.getAllClassificationAllotSetting(self.selectedCode()).done(function (classificationAllotSettings) {
                        self.items(classificationAllotSettings);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ClassificationAllotSettingHeaderDto = (function () {
                function ClassificationAllotSettingHeaderDto(companyCode, historyId, startDateYM, endDateYM) {
                    var self = this;
                    this.companyCode = companyCode;
                    this.historyId = historyId;
                    this.uniqueKey = companyCode + "-" + historyId;
                    this.startDateYM = startDateYM;
                    this.endDateYM = endDateYM;
                    this.displayDate = nts.uk.time.parseYearMonth(self.startDateYM).format() + " ~ " + nts.uk.time.parseYearMonth(self.endDateYM).format();
                }
                return ClassificationAllotSettingHeaderDto;
            }());
            viewmodel.ClassificationAllotSettingHeaderDto = ClassificationAllotSettingHeaderDto;
            var ClassificationAllotSettingDto = (function () {
                function ClassificationAllotSettingDto(bonusDetailCode, classificationCode, companyCode, historyId, paymentDetailCode) {
                    this.bonusDetailCode = bonusDetailCode;
                    this.classificationCode = classificationCode;
                    this.companyCode = companyCode;
                    this.historyId = historyId;
                    this.paymentDetailCode = paymentDetailCode;
                }
                return ClassificationAllotSettingDto;
            }());
            viewmodel.ClassificationAllotSettingDto = ClassificationAllotSettingDto;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm020.e || (qmm020.e = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm.020.e.viewmodel.js.map