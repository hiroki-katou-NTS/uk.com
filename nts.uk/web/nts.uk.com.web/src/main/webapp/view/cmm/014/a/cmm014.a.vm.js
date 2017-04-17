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
                    self.currentItem = ko.observable(new viewmodel.model.InputField(new viewmodel.model.ClassificationDto(), true));
                    self.multilineeditor = ko.observable(null);
                    self.itemdata_add = ko.observable(null);
                    self.itemdata_update = ko.observable(null);
                    self.hasCellphone = ko.observable(true);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.notAlert = ko.observable(true);
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
                                self.currentItem(new viewmodel.model.InputField(self.findObj(codeChanged), false));
                                self.dirty.reset();
                            }).ifNo(function () {
                                self.notAlert(false);
                                self.currentCode(self.currentItem().INP_002_code());
                            });
                        }
                        else {
                            self.currentItem(new viewmodel.model.InputField(self.findObj(codeChanged), false));
                            self.dirty.reset();
                        }
                        if (self.currentItem() != null) {
                            self.hasCellphone(true);
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
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            self.currentItem().refresh();
                            self.dirty.reset();
                            self.currentCode(null);
                            $("#A_INP_002").focus();
                            $("#test input").val("");
                            self.hasCellphone(false);
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.currentItem().refresh();
                        self.dirty.reset();
                        self.currentCode(null);
                        $("#A_INP_002").focus();
                        $("#test input").val("");
                        self.hasCellphone(false);
                    }
                };
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (!self.currentItem().INP_002_code()) {
                        alert("コードが入力されていません。");
                        $("#A_INP_002").focus();
                        return false;
                    }
                    else if (!self.currentItem().INP_003_name()) {
                        alert("名称が入力されていません。");
                        $("#A_INP_003").focus();
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.RegisterClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkInput()) {
                        if (self.dataSource().length === 0) {
                            var classification = new viewmodel.model.ClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                            a.service.addClassification(classification).done(function () {
                                self.getClassificationList_first();
                            }).fail(function (res) {
                                if (res.message == "ER05") {
                                    alert("入力したコードは既に存在しています。\r\n コードを確認してください。 ");
                                }
                                dfd.reject(res);
                            });
                        }
                        for (var i = 0; i < self.dataSource().length; i++) {
                            if (self.currentItem().INP_002_code() == self.dataSource()[i].classificationCode && self.currentItem().INP_002_enable() == false) {
                                var classification_old = self.dataSource()[i];
                                var classification_update = new viewmodel.model.ClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                                a.service.updateClassification(classification_update).done(function () {
                                    self.itemdata_update(classification_update);
                                    self.getClassificationList_afterUpdateClassification();
                                }).fail(function (res) {
                                    if (res.message == "ER026") {
                                        alert("更新対象のデータが存在しません。");
                                    }
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.currentItem().INP_002_code() != self.dataSource()[i].classificationCode
                                && i == self.dataSource().length - 1
                                && self.currentItem().INP_002_enable() == true) {
                                var classification_new = new viewmodel.model.ClassificationDto(self.currentItem().INP_002_code(), self.currentItem().INP_003_name(), self.currentItem().INP_004_notes());
                                a.service.addClassification(classification_new).done(function () {
                                    self.itemdata_add(classification_new);
                                    self.dirty.reset();
                                    self.getClassificationList_afterAddClassification();
                                }).fail(function (res) {
                                    if (res.message == "ER05") {
                                        alert("入力したコードは既に存在しています。\r\n コードを確認してください。");
                                    }
                                    dfd.reject(res);
                                });
                                break;
                            }
                            else if (self.currentItem().INP_002_code() == self.dataSource()[i].classificationCode && self.currentItem().INP_002_enable() == true) {
                                alert("入力したコードは既に存在しています。\r\n コードを確認してください。  ");
                                break;
                            }
                        }
                        self.dirty.reset();
                        self.hasCellphone(true);
                    }
                };
                ScreenModel.prototype.DeleteClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.dataSource().length > 0) {
                        var item = new model.RemoveClassificationCommand(self.currentItem().INP_002_code());
                        self.index_of_itemDelete = self.dataSource().indexOf(self.findObj(self.currentItem().INP_002_code()));
                        nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                            a.service.removeClassification(item).done(function (res) {
                                self.getClassificationList_aftefDelete();
                            }).fail(function (res) {
                                if (res.message == "ER06") {
                                    alert("対象データがありません。");
                                }
                                dfd.reject(res);
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }).ifNo(function () { });
                    }
                    else { }
                    self.dirty.reset();
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        if (classification_arr.length > 0) {
                            self.dataSource(classification_arr);
                            self.currentCode(self.dataSource()[0].classificationCode);
                        }
                        else if (classification_arr.length === 0) {
                            self.currentItem().INP_002_enable(true);
                            $("#A_INP_002").focus();
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
                        self.currentItem().INP_002_code(self.dataSource()[0].classificationCode);
                        self.currentItem().INP_003_name(self.dataSource()[0].classificationName);
                        self.currentItem().INP_004_notes(self.dataSource()[0].memo);
                        if (self.dataSource().length > 0) {
                            self.currentCode(self.dataSource()[0].classificationCode);
                        }
                        self.notAlert(true);
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
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
                    self.notAlert(true);
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getClassificationList_first = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        self.currentCode(self.dataSource()[0].classificationCode);
                        var i = self.dataSource().length;
                        if (i > 0) {
                            self.currentItem().INP_002_enable(false);
                            self.currentItem().INP_002_code(self.dataSource()[0].classificationCode);
                            self.currentItem().INP_003_name(self.dataSource()[0].classificationName);
                            self.currentItem().INP_004_notes(self.dataSource()[0].memo);
                        }
                        self.notAlert(true);
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getClassificationList_afterAddClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        self.currentCode(self.itemdata_add().classificationCode);
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getClassificationList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].classificationCode);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete].classificationCode);
                            }
                        }
                        else {
                            self.initRegisterClassification();
                        }
                        self.notAlert(true);
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
                var InputField = (function () {
                    function InputField(classification, enable) {
                        this.INP_002_code = ko.observable(classification.classificationCode);
                        this.INP_003_name = ko.observable(classification.classificationName);
                        this.INP_004_notes = ko.observable(classification.memo);
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
