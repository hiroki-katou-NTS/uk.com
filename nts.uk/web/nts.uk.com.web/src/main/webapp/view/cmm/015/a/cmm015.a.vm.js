var cmm015;
(function (cmm015) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dataSource = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'payClassificationCode', width: 100 },
                        { headerText: '名称', key: 'payClassificationName', width: 80 }
                    ]);
                    self.index_of_itemDelete = ko.observable(-1);
                    self.currentCode = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    self.currentItem = ko.observable(new viewmodel.model.InputField(new viewmodel.model.PayClassificationDto(), true));
                    self.isDeleteEnable = ko.observable(false);
                    self.findIndex = ko.observable(null);
                    self.adddata = ko.observable(null);
                    self.updatedata = ko.observable(null);
                    self.notAlert = ko.observable(true);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.currentCode.subscribe((function (codeChanged) {
                        if (codeChanged == null) {
                            return;
                        }
                        if (!self.notAlert()) {
                            self.notAlert(true);
                            return;
                        }
                        if (self.dirty.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                                self.currentItem(new viewmodel.model.InputField(self.find(codeChanged), false));
                                self.dirty.reset();
                            }).ifNo(function () {
                                self.notAlert(false);
                                self.currentCode(self.currentItem().INP_002_code());
                            });
                        }
                        else {
                            self.currentItem(new viewmodel.model.InputField(self.find(codeChanged), false));
                            self.dirty.reset();
                        }
                        if (self.currentItem() != null) {
                            self.isDeleteEnable(true);
                        }
                    }));
                }
                ;
                ScreenModel.prototype.initRegisterPayClassification = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            self.currentItem().refresh();
                            self.dirty.reset();
                            self.currentCode(null);
                            $("#code").focus();
                            self.isDeleteEnable(false);
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.currentItem().refresh();
                        self.dirty.reset();
                        self.currentCode(null);
                        $("#code").focus();
                        self.isDeleteEnable(false);
                    }
                };
                ScreenModel.prototype.checkPage = function () {
                    var self = this;
                    if (self.currentItem().INP_002_code() == '') {
                        alert("コードが入力されていません。");
                        $("#code").focus();
                        return false;
                    }
                    else if (self.currentItem().INP_003_name() == '') {
                        alert("名称が入力されていません。");
                        $("#name").focus();
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.find = function (value) {
                    var self = this;
                    return _.find(self.dataSource(), function (obj) {
                        return obj.payClassificationCode == value;
                    });
                };
                ScreenModel.prototype.addPayClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkPage()) {
                        if (self.dataSource().length === 0) {
                            var payClassification = new viewmodel.model.PayClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                            a.service.addPayClassification(payClassification).done(function () {
                                self.dirty.reset();
                                self.getPayClassificationList_first();
                                self.isDeleteEnable(true);
                            }).fail(function (res) {
                                alert(res.message);
                                dfd.reject(res);
                            });
                        }
                        for (var i = 0; i < self.dataSource().length; i++) {
                            if (self.currentItem().INP_002_code() == self.dataSource()[i].payClassificationCode && self.currentItem().INP_002_enable() == false) {
                                var payClassification_before = self.dataSource()[i];
                                var payClassification_update = new viewmodel.model.PayClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                                a.service.updatePayClassification(payClassification_update).done(function () {
                                    self.updatedata(payClassification_update);
                                    self.dirty.reset();
                                    self.getPayClassificationList_afterUpdate();
                                }).fail(function (res) {
                                    alert(res.message);
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.currentItem().INP_002_code() != self.dataSource()[i].payClassificationCode
                                && self.currentItem().INP_002_enable() == true) {
                                var payClassification_new = new viewmodel.model.PayClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                                a.service.addPayClassification(payClassification_new).done(function () {
                                    self.adddata(payClassification_new);
                                    self.dirty.reset();
                                    self.getPayClassificationList_afterAdd().done(function () {
                                    });
                                }).fail(function (res) {
                                    $("#code").focus();
                                    alert(res.message);
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.currentItem().INP_002_code() == self.dataSource()[i].payClassificationCode && self.currentItem().INP_002_enable() == true) {
                                alert("入力したコードは既に存在しています。\r\n コードを確認してください。  ");
                                break;
                            }
                        }
                    }
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 0) {
                            self.currentCode(self.dataSource()[0].payClassificationCode);
                        }
                        else {
                            self.initRegisterPayClassification();
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.deletePayClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.dataSource().length > 0) {
                        var item = new model.RemovePayClassificationCommand(self.currentItem().INP_002_code());
                        self.index_of_itemDelete(_.findIndex(self.dataSource(), function (item) { return item.payClassificationCode === self.currentItem().INP_002_code(); }));
                        nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                            a.service.removePayClassification(item).done(function (res) {
                                self.dirty.reset();
                                self.getPayClassificationList_aftefDelete();
                            }).fail(function (res) {
                                alert(res.message);
                                dfd.reject(res);
                            });
                        }).ifNo(function () {
                        });
                    }
                    else {
                        return null;
                    }
                };
                ScreenModel.prototype.getPayClassificationList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete() === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete() - 1].payClassificationCode);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete()].payClassificationCode);
                            }
                            dfd.resolve();
                        }
                        else {
                            self.initRegisterPayClassification();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_first = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        self.currentCode(self.dataSource()[0].payClassificationCode);
                        var i = self.dataSource().length;
                        if (i > 0) {
                            self.isDeleteEnable(true);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 0) {
                            self.currentCode(self.dataSource()[0].payClassificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res.message);
                    });
                    self.notAlert(true);
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_afterUpdate = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 1) {
                            self.currentCode(self.updatedata().payClassificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_afterAdd = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        self.currentCode(self.adddata().payClassificationCode);
                        self.isDeleteEnable(true);
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var InputField = (function () {
                    function InputField(payClassification, enable) {
                        this.INP_002_code = ko.observable(payClassification.payClassificationCode);
                        this.INP_003_name = ko.observable(payClassification.payClassificationName);
                        this.INP_004_notes = ko.observable(payClassification.memo);
                        this.INP_002_enable = ko.observable(enable);
                    }
                    InputField.prototype.refresh = function () {
                        var self = this;
                        self.INP_002_enable(true);
                        self.INP_002_code("");
                        self.INP_003_name("");
                        self.INP_004_notes("");
                    };
                    return InputField;
                }());
                model.InputField = InputField;
                var PayClassificationDto = (function () {
                    function PayClassificationDto(payClassificationCode, payClassificationName, memo) {
                        this.payClassificationCode = payClassificationCode;
                        this.payClassificationName = payClassificationName;
                        this.memo = memo;
                    }
                    return PayClassificationDto;
                }());
                model.PayClassificationDto = PayClassificationDto;
                var RemovePayClassificationCommand = (function () {
                    function RemovePayClassificationCommand(payClassificationCode) {
                        this.payClassificationCode = payClassificationCode;
                    }
                    return RemovePayClassificationCommand;
                }());
                model.RemovePayClassificationCommand = RemovePayClassificationCommand;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm015.a || (cmm015.a = {}));
})(cmm015 || (cmm015 = {}));
//# sourceMappingURL=cmm015.a.vm.js.map