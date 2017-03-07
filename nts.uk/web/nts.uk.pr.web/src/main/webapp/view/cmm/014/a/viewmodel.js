var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dataSource = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'classificationCode', width: 100 },
                        { headerText: '名称', key: 'classificationName', width: 80 }
                    ]);
                    self.currentCode = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.multilineeditor = ko.observable(null);
                    self.INP_002_code = ko.observable(null);
                    self.INP_002_enable = ko.observable(false);
                    self.INP_003_name = ko.observable(null);
                    self.INP_004_notes = ko.observable(null);
                    self.itemdata_add = ko.observable(null);
                    self.itemdata_update = ko.observable(null);
                    self.currentCode.subscribe((function (codeChanged) {
                        self.currentItem(self.findObj(codeChanged));
                        if (self.currentItem() != null) {
                            self.INP_002_code(self.currentItem().classificationCode);
                            self.INP_002_enable(false);
                            self.INP_003_name(self.currentItem().classificationName);
                            self.INP_004_notes(self.currentItem().memo);
                        }
                    }));
                }
                ScreenModel.prototype.findObj = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.dataSource(), function (obj) {
                        if (obj.classificationCode == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.initRegisterClassification = function () {
                    var self = this;
                    self.INP_002_enable(true);
                    self.INP_002_code("");
                    self.INP_003_name("");
                    self.INP_004_notes("");
                    self.currentCode(null);
                    $("#test input").val("");
                    $("#A_INP_002").focus();
                };
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (self.INP_002_code().length === 0) {
                        alert("が入力されていません。");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.RegisterClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkInput()) {
                        if (self.dataSource().length === 0) {
                            var classification = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                            a.service.addClassification(classification).done(function () {
                                self.getClassificationList_first();
                            }).fail(function (res) {
                                dfd.reject(res);
                            });
                        }
                        for (var i = 0; i < self.dataSource().length; i++) {
                            if (self.INP_002_code() == self.dataSource()[i].classificationCode) {
                                var classification_old = self.dataSource()[i];
                                var classification_update = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                                a.service.updateClassification(classification_update).done(function () {
                                    self.itemdata_update(classification_update);
                                    self.getClassificationList_afterUpdateClassification();
                                }).fail(function (res) {
                                    alert("更新対象のデータが存在しません。");
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.INP_002_code() != self.dataSource()[i].classificationCode && i == self.dataSource().length - 1) {
                                var classification_new = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                                a.service.addClassification(classification_new).done(function () {
                                    self.itemdata_add(classification_new);
                                    self.getClassificationList_afterAddClassification();
                                }).fail(function (res) {
                                    alert("入力した は既に存在しています。");
                                    dfd.reject(res);
                                });
                                break;
                            }
                        }
                    }
                };
                ScreenModel.prototype.DeleteClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var item = new model.RemoveClassificationCommand(self.currentItem().classificationCode);
                    self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                    a.service.removeClassification(item).done(function (res) {
                        self.getClassificationList_aftefDelete();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        if (classification_arr.length > 0) {
                            self.dataSource(classification_arr);
                            self.INP_002_code(self.dataSource()[0].classificationCode);
                            self.INP_003_name(self.dataSource()[0].classificationName);
                            self.INP_004_notes(self.dataSource()[0].memo);
                            self.currentCode(self.dataSource()[0].classificationCode);
                        }
                        else if (classification_arr.length === 0) {
                            self.INP_002_enable(true);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getClassificationList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        self.INP_002_code(self.dataSource()[0].classificationCode);
                        self.INP_003_name(self.dataSource()[0].classificationName);
                        self.INP_004_notes(self.dataSource()[0].memo);
                        if (self.dataSource().length > 1) {
                            self.currentCode(self.dataSource()[0].classificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                // get list Classification after insert
                ScreenModel.prototype.getClassificationList_afterUpdateClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        if (self.dataSource().length > 1) {
                            self.currentCode(self.itemdata_update().classificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                // get list Classification after insert
                ScreenModel.prototype.getClassificationList_first = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        self.currentCode(self.dataSource()[0].classificationCode);
                        var i = self.dataSource().length;
                        if (i > 0) {
                            self.INP_002_enable(false);
                            self.INP_002_code(self.dataSource()[0].classificationCode);
                            self.INP_003_name(self.dataSource()[0].classificationName);
                            self.INP_004_notes(self.dataSource()[0].memo);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                // get list Classification after insert
                ScreenModel.prototype.getClassificationList_afterAddClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        self.INP_002_code(self.dataSource()[0].classificationCode);
                        self.INP_002_enable = ko.observable(false);
                        self.INP_003_name(self.dataSource()[0].classificationName);
                        self.INP_004_notes(self.dataSource()[0].memo);
                        if (self.dataSource().length > 1) {
                            var i = self.dataSource().length - 1;
                            self.currentCode(self.itemdata_add().classificationCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                // get list Classification after remove
                ScreenModel.prototype.getClassificationList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].classificationCode);
                                self.INP_002_code(self.dataSource()[self.index_of_itemDelete - 1].classificationCode);
                                self.INP_003_name(self.dataSource()[self.index_of_itemDelete - 1].classificationName);
                                self.INP_004_notes(self.dataSource()[self.index_of_itemDelete - 1].memo);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete].classificationCode);
                                self.INP_002_code(self.dataSource()[self.index_of_itemDelete].classificationCode);
                                self.INP_003_name(self.dataSource()[self.index_of_itemDelete].classificationName);
                                self.INP_004_notes(self.dataSource()[self.index_of_itemDelete].memo);
                            }
                        }
                        else {
                            self.initRegisterClassification();
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
            /**
            *  model
            */
            var model;
            (function (model) {
                var ClassificationDto = (function () {
                    function ClassificationDto(classificationCode, classificationName, memo) {
                        this.classificationCode = classificationCode;
                        this.classificationName = classificationName;
                        this.memo = memo;
                    }
                    return ClassificationDto;
                }());
                model.ClassificationDto = ClassificationDto;
                var RemoveClassificationCommand = (function () {
                    function RemoveClassificationCommand(classificationCode) {
                        this.classificationCode = classificationCode;
                    }
                    return RemoveClassificationCommand;
                }());
                model.RemoveClassificationCommand = RemoveClassificationCommand;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
