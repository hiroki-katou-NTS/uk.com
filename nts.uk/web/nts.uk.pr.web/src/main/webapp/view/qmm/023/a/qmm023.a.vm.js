var qmm023;
(function (qmm023) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self._init();
                    self.currentCode.subscribe(function (codeChanged) {
                        if (nts.uk.text.isNullOrEmpty(codeChanged)) {
                            if (self.isUpdate() === true) {
                                self.refreshLayout();
                            }
                            return;
                        }
                        self.CheckError();
                        if (self.isError) {
                            self.isError = false;
                            $('.save-error').ntsError('clear');
                            return;
                        }
                        var oldCode = ko.mapping.toJS(self.currentTax().code);
                        if (ko.mapping.toJS(codeChanged) === oldCode && self.flatDirty === false) {
                            return;
                        }
                        if (self.flatDirty == true) {
                            self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                            self.allowEditCode(false);
                            self.isUpdate(true);
                            self.isEnableDeleteBtn(true);
                            self.currentTaxDirty.reset();
                            self.flatDirty = false;
                            return;
                        }
                        self.alertCheckDirty(oldCode, codeChanged);
                    });
                }
                ScreenModel.prototype._init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 75 },
                        { headerText: '名称', prop: 'name', width: 175 },
                        { headerText: '限度額', prop: 'taxLimit', width: 115 }
                    ]);
                    self.currentTax = ko.observable(new TaxModel('', '', 0));
                    self.currentCode = ko.observable(null);
                    self.isUpdate = ko.observable(true);
                    self.allowEditCode = ko.observable(false);
                    self.isEnableDeleteBtn = ko.observable(true);
                    self.flatDirty = true;
                    self.isError = false;
                    self.currentTaxDirty = new nts.uk.ui.DirtyChecker(self.currentTax);
                    if (self.items.length <= 0) {
                        self.allowEditCode = ko.observable(true);
                        self.isUpdate = ko.observable(false);
                        self.isEnableDeleteBtn = ko.observable(false);
                    }
                };
                ScreenModel.prototype.getTax = function (codeNew) {
                    var self = this;
                    var tax = _.find(self.items(), function (item) {
                        return item.code === codeNew;
                    });
                    return tax;
                };
                ScreenModel.prototype.CheckError = function () {
                    var self = this;
                    $('#INP_002').ntsEditor("validate");
                    $('#INP_003').ntsEditor("validate");
                    if ($('.nts-editor').ntsError("hasError")) {
                        self.isError = true;
                    }
                };
                ScreenModel.prototype.alertCheckDirty = function (oldCode, codeChanged) {
                    var self = this;
                    if (!self.currentTaxDirty.isDirty()) {
                        self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                        self.allowEditCode(false);
                        self.isUpdate(true);
                        self.isEnableDeleteBtn(true);
                        self.currentTaxDirty.reset();
                        self.flatDirty = false;
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                            self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                            self.allowEditCode(false);
                            self.isUpdate(true);
                            self.isEnableDeleteBtn(true);
                            self.currentTaxDirty.reset();
                        }).ifCancel(function () {
                            self.currentCode(oldCode);
                        });
                    }
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.allowEditCode(true);
                    if (self.isError) {
                        self.isError = false;
                        $('.save-error').ntsError('clear');
                    }
                    self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
                    self.currentCode(null);
                    self.isUpdate(false);
                    self.isEnableDeleteBtn(false);
                    self.currentTaxDirty.reset();
                    self.flatDirty = true;
                };
                ScreenModel.prototype.createButtonClick = function () {
                    var self = this;
                    $('.save-error').ntsError('clear');
                    if (self.currentTaxDirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                            self.refreshLayout();
                        }).ifCancel(function () {
                            return;
                        });
                    }
                    else {
                        self.refreshLayout();
                    }
                };
                ScreenModel.prototype.insertUpdateData = function () {
                    var self = this;
                    self.CheckError();
                    if (self.isError) {
                        return;
                    }
                    var newCode = ko.mapping.toJS(self.currentTax().code);
                    var newName = ko.mapping.toJS(self.currentTax().name);
                    var newTaxLimit = ko.mapping.toJS(self.currentTax().taxLimit);
                    var insertUpdateModel = new InsertUpdateModel(nts.uk.text.padLeft(newCode, '0', 2), newName, newTaxLimit);
                    a.service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function () {
                        self.reload(false, nts.uk.text.padLeft(newCode, '0', 2));
                        self.flatDirty = true;
                        self.currentTaxDirty.reset();
                        if (self.isUpdate() === false) {
                            self.isUpdate(true);
                            self.allowEditCode(false);
                            return;
                        }
                    }).fail(function (error) {
                        if (error.message === '3') {
                            var _message = "入力した{0}は既に存在しています。\r\n {1}を確認してください。";
                            nts.uk.ui.dialog.alert(nts.uk.text.format(_message, 'コード', 'コード'));
                        }
                        else if (error.message === '4') {
                            nts.uk.ui.dialog.alert("対象データがありません。").then(function () {
                                self.reload(true);
                            });
                        }
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var deleteCode = ko.mapping.toJS(self.currentTax().code);
                    a.service.deleteData(new DeleteModel(deleteCode)).done(function () {
                        var indexItemDelete = _.findIndex(self.items(), function (item) { return item.code == deleteCode; });
                        $.when(self.reload(false)).done(function () {
                            self.flatDirty = true;
                            if (self.items().length === 0) {
                                self.refreshLayout();
                                return;
                            }
                            if (self.items().length == indexItemDelete) {
                                self.currentCode(self.items()[indexItemDelete - 1].code);
                                return;
                            }
                            if (self.items().length < indexItemDelete) {
                                self.currentCode(self.items()[0].code);
                                return;
                            }
                            if (self.items().length > indexItemDelete) {
                                self.currentCode(self.items()[indexItemDelete].code);
                                return;
                            }
                        });
                    }).fail(function (error) {
                        if (error.message === '1') {
                            nts.uk.ui.dialog.alert("対象データがありません。").then(function () {
                                self.reload(true);
                            });
                        }
                    });
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        self.deleteData();
                    });
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    return self.reload(true);
                };
                ScreenModel.prototype.reload = function (isReload, reloadCode) {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getCommutelimitsByCompanyCode().done(function (data) {
                        self.items([]);
                        _.forEach(data, function (item) {
                            self.items.push(new TaxModel(item.commuNoTaxLimitCode, item.commuNoTaxLimitName, item.commuNoTaxLimitValue));
                        });
                        self.flatDirty = true;
                        if (self.items().length <= 0) {
                            self.refreshLayout();
                            dfd.resolve();
                            return;
                        }
                        if (isReload) {
                            self.currentCode(self.items()[0].code);
                        }
                        else if (!nts.uk.text.isNullOrEmpty(reloadCode)) {
                            self.currentCode(reloadCode);
                        }
                        dfd.resolve(data);
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var TaxModel = (function () {
                function TaxModel(code, name, taxLimit) {
                    this.code = code;
                    this.name = name;
                    this.taxLimit = taxLimit;
                }
                return TaxModel;
            }());
            var InsertUpdateModel = (function () {
                function InsertUpdateModel(commuNoTaxLimitCode, commuNoTaxLimitName, commuNoTaxLimitValue) {
                    this.commuNoTaxLimitCode = commuNoTaxLimitCode;
                    this.commuNoTaxLimitName = commuNoTaxLimitName;
                    this.commuNoTaxLimitValue = commuNoTaxLimitValue;
                }
                return InsertUpdateModel;
            }());
            viewmodel.InsertUpdateModel = InsertUpdateModel;
            var DeleteModel = (function () {
                function DeleteModel(commuNoTaxLimitCode) {
                    this.commuNoTaxLimitCode = commuNoTaxLimitCode;
                }
                return DeleteModel;
            }());
            viewmodel.DeleteModel = DeleteModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm023.a || (qmm023.a = {}));
})(qmm023 || (qmm023 = {}));
//# sourceMappingURL=qmm023.a.vm.js.map