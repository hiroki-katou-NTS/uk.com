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
                        if ($('.nts-editor').ntsError("hasError")) {
                            $('.save-error').ntsError('clear');
                        }
                        var oldCode = ko.mapping.toJS(self.currentTax().code);
                        if (ko.mapping.toJS(codeChanged) !== oldCode && self.currentTaxDirty.isDirty()) {
                            self.flatDirty = false;
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                                self.flatDirty = true;
                                self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                                self.allowEditCode(false);
                                self.isUpdate(true);
                                self.isEnableDeleteBtn(true);
                                self.currentTaxDirty = new nts.uk.ui.DirtyChecker(self.currentTax);
                            }).ifCancel(function () {
                                self.flatDirty = false;
                                self.currentCode(ko.mapping.toJS(self.currentTax().code));
                            });
                        }
                        if (!self.flatDirty) {
                            self.flatDirty = true;
                            return;
                        }
                        self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                        self.allowEditCode(false);
                        self.isUpdate(true);
                        self.isEnableDeleteBtn(true);
                        self.currentTaxDirty = new nts.uk.ui.DirtyChecker(self.currentTax);
                    });
                }
                ScreenModel.prototype._init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 120 },
                        { headerText: '限度額', prop: 'taxLimit', width: 170 }
                    ]);
                    self.currentCode = ko.observable(null);
                    self.currentTax = ko.observable(new TaxModel('', '', 0));
                    self.isUpdate = ko.observable(true);
                    self.allowEditCode = ko.observable(false);
                    self.isEnableDeleteBtn = ko.observable(true);
                    self.flatDirty = true;
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
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    $('.save-error').ntsError('clear');
                    self.allowEditCode(true);
                    self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
                    self.currentCode(null);
                    self.isUpdate(false);
                    self.isEnableDeleteBtn(false);
                    self.flatDirty = true;
                    self.currentTaxDirty = new nts.uk.ui.DirtyChecker(self.currentTax);
                };
                ScreenModel.prototype.createButtonClick = function () {
                    var self = this;
                    if (self.currentTaxDirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
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
                    var newCode = ko.mapping.toJS(self.currentTax().code);
                    var newName = ko.mapping.toJS(self.currentTax().name);
                    var newTaxLimit = ko.mapping.toJS(self.currentTax().taxLimit);
                    if (nts.uk.text.isNullOrEmpty(newCode)) {
                        $('#INP_002').ntsError('set', nts.uk.text.format('{0}が入力されていません。', 'コード'));
                        return;
                    }
                    if (nts.uk.text.isNullOrEmpty(newName)) {
                        $('#INP_003').ntsError('set', nts.uk.text.format('{0}が入力されていません。', '名称'));
                        return;
                    }
                    var insertUpdateModel = new InsertUpdateModel(nts.uk.text.padLeft(newCode, '0', 2), newName, newTaxLimit);
                    a.service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function () {
                        $.when(self.getCommuteNoTaxLimitList()).done(function () {
                            self.currentCode(nts.uk.text.padLeft(newCode, '0', 2));
                        });
                        if (self.isUpdate() === false) {
                            self.isUpdate(true);
                            self.allowEditCode(false);
                        }
                    }).fail(function (error) {
                        if (error.message === '1') {
                            $('#INP_002').ntsError('set', nts.uk.text.format('{0}が入力されていません。', 'コード'));
                        }
                        else if (error.message === '2') {
                            $('#INP_003').ntsError('set', nts.uk.text.format('{0}が入力されていません。', '名称'));
                        }
                        else if (error.message === '3') {
                            $('#INP_002').ntsError('set', nts.uk.text.format('入力した{0}は既に存在しています。\r\n{1}を確認してください。', 'コード', 'コード'));
                        }
                        else if (error.message === '4') {
                            $('#INP_002').ntsError('set', "対象データがありません。");
                        }
                        else {
                            $('#INP_002').ntsError('set', error.message);
                        }
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var deleteCode = ko.mapping.toJS(self.currentTax().code);
                    if (nts.uk.text.isNullOrEmpty(deleteCode)) {
                        $('#INP_002').ntsError('set', nts.uk.text.format('{0}が入力されていません。', 'コード'));
                        return;
                    }
                    a.service.deleteData(new DeleteModel(deleteCode)).done(function () {
                        var indexItemDelete = _.findIndex(self.items(), function (item) { return item.code == deleteCode; });
                        $.when(self.getCommuteNoTaxLimitList()).done(function () {
                            if (self.items().length === 0) {
                                self.allowEditCode(true);
                                self.isUpdate(false);
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
                            $('#INP_002').ntsError('set', "対象データがありません。");
                        }
                        else {
                            $('#INP_002').ntsError('set', error.message);
                        }
                    });
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        self.deleteData();
                    });
                };
                // startpage
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    $.when(self.getCommuteNoTaxLimitList()).done(function () {
                        self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
                        if (self.items().length > 0) {
                            self.currentTax(_.first(self.items()));
                            self.currentCode(self.currentTax().code);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getCommuteNoTaxLimitList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getCommutelimitsByCompanyCode().done(function (data) {
                        self.items([]);
                        _.forEach(data, function (item) {
                            self.items.push(new TaxModel(item.commuNoTaxLimitCode, item.commuNoTaxLimitName, item.commuNoTaxLimitValue));
                        });
                        dfd.resolve(data);
                    }).fail(function (error) {
                        alert(error.message);
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
