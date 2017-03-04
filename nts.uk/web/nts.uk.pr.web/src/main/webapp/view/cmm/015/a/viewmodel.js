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
                    self.currentCode = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.multilineeditor = ko.observable(null);
                    self.INP_002_code = ko.observable(null);
                    self.INP_002_enable = ko.observable(false);
                    self.INP_003_name = ko.observable(null);
                    self.INP_004_notes = ko.observable(null);
                    self.currentCode.subscribe((function (codeChanged) {
                        self.currentItem(self.findObj(codeChanged));
                        if (self.currentItem() != null) {
                            self.INP_002_code(self.currentItem().payClassificationCode);
                            self.INP_003_name(self.currentItem().payClassificationName);
                            self.INP_004_notes(self.currentItem().memo);
                        }
                    }));
                }
                ScreenModel.prototype.findObj = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.dataSource(), function (obj) {
                        if (obj.payClassificationCode == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.initRegisterPayClassification = function () {
                    var self = this;
                    self.INP_002_enable(true);
                    self.INP_002_code("");
                    self.INP_003_name("");
                    self.INP_004_notes("");
                    self.currentCode(null);
                    $("#test input").val("");
                };
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (self.INP_002_code() == '' || self.INP_003_name() == '') {
                        console.log("input is null");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.RegisterPayClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkInput()) {
                        console.log("");
                        for (var i = 0; i < self.dataSource().length; i++) {
                            if (self.INP_002_code() == self.dataSource()[i].payClassificationCode) {
                                var payclassification_old = self.dataSource()[i];
                                var payclassification_update = new viewmodel.model.PayClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                                a.service.updatePayClassification(payclassification_update).done(function () {
                                    self.getPayClassificationList();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.INP_002_code() != self.dataSource()[i].payClassificationCode && i == self.dataSource().length - 1) {
                                var classification_new = new viewmodel.model.PayClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                                a.service.addPayClassification(classification_new).done(function () {
                                    self.getPayClassificationList();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                break;
                            }
                        }
                    }
                };
                ScreenModel.prototype.DeletePayClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var item = new model.RemovePayClassificationCommand(self.currentItem().payClassificationCode);
                    self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                    console.log(self.index_of_itemDelete);
                    a.service.removePayClassification(item).done(function (res) {
                        self.getPayClassificationList_aftefDelete();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 0) {
                            self.INP_002_code(self.dataSource()[0].payClassificationCode);
                            self.INP_003_name(self.dataSource()[0].payClassificationName);
                            self.INP_004_notes(self.dataSource()[0].memo);
                            self.currentCode(self.dataSource()[0].payClassificationCode);
                            dfd.resolve();
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getClassificationList_first = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        self.currentCode(self.dataSource()[0].payClassificationCode);
                        var i = self.dataSource().length;
                        if (i > 0) {
                            self.INP_002_enable(false);
                            self.INP_002_code(self.dataSource()[0].payClassificationCode);
                            self.INP_003_name(self.dataSource()[0].payClassificationName);
                            self.INP_004_notes(self.dataSource()[0].memo);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        self.INP_002_code(self.dataSource()[0].payClassificationCode);
                        self.INP_003_name(self.dataSource()[0].payClassificationName);
                        self.INP_004_notes(self.dataSource()[0].memo);
                        if (self.dataSource().length > 1) {
                            self.currentCode(self.dataSource()[0].payClassificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_afterUpdate = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 1) {
                            var i = self.currentItem().payClassificationCode;
                            var j = self.dataSource().indexOf(self.currentItem());
                            self.currentCode(i);
                            self.INP_002_code(self.dataSource()[j].payClassificationCode);
                            self.INP_003_name(self.dataSource()[j].payClassificationName);
                            self.INP_004_notes(self.dataSource()[j].memo);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_afterAdd = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        self.INP_002_code(self.dataSource()[0].payClassificationCode);
                        self.INP_002_enable = ko.observable(false);
                        self.INP_003_name(self.dataSource()[0].payClassificationName);
                        self.INP_004_notes(self.dataSource()[0].memo);
                        if (self.dataSource().length > 1) {
                            var i = self.dataSource().length - 1;
                            self.currentCode(self.dataSource()[i].payClassificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPayClassificationList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllPayClassification().done(function (payClassification_arr) {
                        self.dataSource(payClassification_arr);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].payClassificationCode);
                                self.INP_002_code(self.dataSource()[self.index_of_itemDelete - 1].payClassificationCode);
                                self.INP_003_name(self.dataSource()[self.index_of_itemDelete - 1].payClassificationName);
                                self.INP_004_notes(self.dataSource()[self.index_of_itemDelete - 1].memo);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete].payClassificationCode);
                                self.INP_002_code(self.dataSource()[self.index_of_itemDelete].payClassificationCode);
                                self.INP_003_name(self.dataSource()[self.index_of_itemDelete].payClassificationName);
                                self.INP_004_notes(self.dataSource()[self.index_of_itemDelete].memo);
                            }
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
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
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
