var qmm020;
(function (qmm020) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.isInsert = ko.observable(false);
                    self.itemList = ko.observableArray([]);
                    self.currentItem = ko.observable(new ComHistItem({
                        historyId: '',
                        startYm: '',
                        endYm: '',
                        payCode: '',
                        bonusCode: ''
                    }));
                    self.maxItem = ko.observable(new b.service.model.CompanyAllotSettingDto());
                    self.start();
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    b.service.getAllotCompanyList().done(function (companyAllots) {
                        if (companyAllots.length > 0) {
                            var _items = [];
                            for (var i_1 in companyAllots) {
                                var item = companyAllots[i_1];
                                if (item) {
                                    _items.push(new ComHistItem({
                                        historyId: (item.historyId || "").toString(),
                                        startYm: (item.startDate || "").toString(),
                                        endYm: (item.endDate || "").toString(),
                                        payCode: (item.paymentDetailCode || "").toString(),
                                        bonusCode: (item.bonusDetailCode || "").toString()
                                    }));
                                }
                            }
                            self.itemList(_items);
                            self.companyAllots = companyAllots;
                            self.currentItem().setSource(self.companyAllots);
                            self.currentItem().historyId(companyAllots[0].historyId);
                        }
                        else {
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                    b.service.getAllotCompanyMaxDate().done(function (itemMax) {
                        debugger;
                        self.maxItem(itemMax);
                    }).fail(function (res) {
                        alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.register = function () {
                    var self = this;
                    if (self.isInsert()) {
                        b.service.insertComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                    else {
                        b.service.updateComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                };
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    var historyScreenType = "1";
                    var valueShareJDialog = historyScreenType + "~" + self.currentItem().startYm();
                    nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { title: '明細書の紐ずけ＞履歴追加' })
                        .onClosed(function () {
                        var returnJDialog = nts.uk.ui.windows.getShared('returnJDialog');
                        if (returnJDialog != undefined) {
                            var modeRadio = returnJDialog.split("~")[0];
                            var returnValue = returnJDialog.split("~")[1];
                            if (returnValue != '') {
                                var items = [];
                                var addItem = new ComHistItem({
                                    historyId: new Date().getTime().toString(),
                                    startYm: returnValue,
                                    endYm: '999912',
                                    payCode: '',
                                    bonusCode: ''
                                });
                                items.push(addItem);
                                for (var i_2 in self.itemList()) {
                                    items.push(self.itemList()[i_2]);
                                }
                                var lastValue = _.find(self.companyAllots, function (item) { return item.endDate == 999912; });
                                if (lastValue != undefined) {
                                    lastValue.endDate = previousYM(returnValue);
                                    var updateValue = _.find(items, function (item) { return item.endYm() == "999912" && item.startYm() != returnValue; });
                                    if (updateValue != undefined) {
                                        updateValue.endYm(lastValue.endDate.toString());
                                    }
                                }
                                if (modeRadio === "1") {
                                    self.currentItem().historyId(addItem.historyId());
                                    self.currentItem().startYm(returnValue);
                                    self.currentItem().endYm('999912');
                                    self.currentItem().payCode(self.maxItem().paymentDetailCode);
                                    self.currentItem().bonusCode(self.maxItem().bonusDetailCode);
                                    if (self.currentItem().payCode() != '') {
                                        b.service.getAllotLayoutName(self.currentItem().payCode()).done(function (stmtName) {
                                            self.currentItem().payName(stmtName);
                                        }).fail(function (res) {
                                            self.currentItem().payName('');
                                        });
                                    }
                                    else {
                                        self.currentItem().payName('');
                                    }
                                    if (self.currentItem().bonusCode() != '') {
                                        b.service.getAllotLayoutName(self.currentItem().bonusCode()).done(function (stmtName) {
                                            self.currentItem().bonusName(stmtName);
                                        }).fail(function (res) {
                                            self.currentItem().bonusName('');
                                        });
                                    }
                                    else {
                                        self.currentItem().bonusName('');
                                    }
                                }
                                else {
                                    self.currentItem().historyId(addItem.historyId());
                                    self.currentItem().startYm(returnValue);
                                    self.currentItem().endYm('999912');
                                    self.currentItem().payCode('');
                                    self.currentItem().bonusCode('');
                                    self.currentItem().payName('');
                                    self.currentItem().bonusName('');
                                }
                            }
                            self.itemList([]);
                            self.itemList(items);
                            self.isInsert = ko.observable(true);
                        }
                    });
                };
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared("endYM", self.currentItem().endYm());
                    nts.uk.ui.windows.setShared('scrType', '1');
                    nts.uk.ui.windows.setShared('startYM', self.currentItem().startYm());
                    var current = _.find(self.companyAllots, function (item) { return item.historyId == self.currentItem().historyId(); });
                    var previousItem = _.find(self.companyAllots, function (item) { return item.endDate == parseInt(self.currentItem().startYm()) - 1; });
                    if (current) {
                        nts.uk.ui.windows.setShared('currentItem', current);
                    }
                    if (previousItem) {
                        nts.uk.ui.windows.setShared('previousItem', previousItem);
                    }
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function () {
                        self.start();
                    });
                };
                ScreenModel.prototype.openLDialog = function () {
                    alert('2017');
                };
                ScreenModel.prototype.openPaymentMDialog = function () {
                    var self = this;
                    var valueShareMDialog = self.currentItem().startYm();
                    nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '明細書の選択' }).onClosed(function () {
                        var stmtCodeSelected = nts.uk.ui.windows.getShared('stmtCodeSelected');
                        self.currentItem().payCode(stmtCodeSelected);
                        b.service.getAllotLayoutName(self.currentItem().payCode()).done(function (stmtName) {
                            self.currentItem().payName(stmtName);
                        }).fail(function (res) {
                            alert(res);
                        });
                    });
                };
                ScreenModel.prototype.openBonusMDialog = function () {
                    var self = this;
                    var valueShareMDialog = self.currentItem().startYm();
                    nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '明細書の選択' }).onClosed(function () {
                        var stmtCodeSelected = nts.uk.ui.windows.getShared('stmtCodeSelected');
                        self.currentItem().bonusCode(stmtCodeSelected);
                        b.service.getAllotLayoutName(self.currentItem().bonusCode()).done(function (stmtName) {
                            self.currentItem().bonusName(stmtName);
                        }).fail(function (res) {
                            alert(res);
                        });
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            function previousYM(sYm) {
                var preYm = 0;
                if (sYm.length == 6) {
                    var sYear = sYm.substr(0, 4);
                    var sMonth = sYm.substr(4, 2);
                    if (sMonth == "01") {
                        preYm = parseInt((parseInt(sYear) - 1).toString() + "12");
                    }
                    else {
                        preYm = parseInt(sYm) - 1;
                    }
                }
                return preYm;
            }
            var ComHistItem = (function () {
                function ComHistItem(param) {
                    var self = this;
                    self.id = param.historyId;
                    self.historyId = ko.observable(param.historyId);
                    self.startYm = ko.observable(param.startYm);
                    self.endYm = ko.observable(param.endYm);
                    self.payCode = ko.observable(param.payCode);
                    self.bonusCode = ko.observable(param.bonusCode);
                    self.startEnd = param.startYm + '~' + param.endYm;
                    self.payName = ko.observable(param.payName || '');
                    self.bonusName = ko.observable(param.bonusName || '');
                    self.historyId.subscribe(function (newValue) {
                        if (typeof newValue != 'string') {
                            return;
                        }
                        var current = _.find(self.listSource, function (item) { return item.historyId == newValue; });
                        if (current) {
                            self.startYm(current.startDate);
                            self.endYm(current.endDate);
                            self.payCode(current.paymentDetailCode);
                            self.bonusCode(current.bonusDetailCode);
                            self.startEnd = self.startYm() + '~' + self.endYm();
                            if (current.paymentDetailCode != '') {
                                b.service.getAllotLayoutName(current.paymentDetailCode).done(function (stmtName) {
                                    self.payName(stmtName);
                                }).fail(function (res) {
                                    self.payName('');
                                });
                            }
                            else {
                                self.payName('');
                            }
                            if (current.bonusDetailCode != '') {
                                b.service.getAllotLayoutName(current.bonusDetailCode).done(function (stmtName) {
                                    self.bonusName(stmtName);
                                }).fail(function (res) {
                                    self.bonusName('');
                                });
                            }
                            else {
                                self.bonusName('');
                            }
                        }
                        else {
                            var newItem = {
                                paymentDetailCode: '',
                                bonusDetailCode: '',
                                startDate: 0,
                                endDate: 0,
                                historyId: self.historyId()
                            };
                            self.listSource.push(newItem);
                            self.payName('');
                            self.bonusName('');
                        }
                    });
                    self.payCode.subscribe(function (newValue) {
                        var current = _.find(self.listSource, function (item) { return item.historyId == self.historyId(); });
                        if (current) {
                            current.paymentDetailCode = newValue;
                        }
                    });
                    self.bonusCode.subscribe(function (newValue) {
                        var current = _.find(self.listSource, function (item) { return item.historyId == self.historyId(); });
                        if (current) {
                            current.bonusDetailCode = newValue;
                        }
                    });
                    self.startYm.subscribe(function (newValue) {
                        self.startEnd = self.startYm() + '~' + self.endYm();
                        var current = _.find(self.listSource, function (item) { return item.historyId == self.historyId(); });
                        if (current) {
                            current.startDate = newValue;
                        }
                    });
                    self.endYm.subscribe(function (newValue) {
                        self.startEnd = self.startYm() + '~' + self.endYm();
                        var current = _.find(self.listSource, function (item) { return item.historyId == self.historyId(); });
                        if (current) {
                            current.endDate = newValue;
                        }
                    });
                }
                ComHistItem.prototype.setSource = function (list) {
                    this.listSource = list || [];
                };
                return ComHistItem;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm020.b || (qmm020.b = {}));
})(qmm020 || (qmm020 = {}));
