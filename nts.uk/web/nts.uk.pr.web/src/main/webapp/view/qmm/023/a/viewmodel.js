var qmm023;
(function (qmm023) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.init();
                    //get event when hover on table by subcribe
                    self.currentCode.subscribe(function (codeChanged) {
                        if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                            self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                            self.allowEditCode(false);
                            self.isUpdate(true);
                            self.isEnableDeleteBtn(true);
                        }
                        else {
                            self.refreshLayout();
                        }
                    });
                }
                ScreenModel.prototype.init = function () {
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
                    self.allowEditCode(true);
                    self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
                    self.currentCode(null);
                    self.isUpdate(false);
                    self.isEnableDeleteBtn(false);
                };
                ScreenModel.prototype.insertUpdateData = function () {
                    var self = this;
                    if (nts.uk.text.isNullOrEmpty(self.currentTax().code())) {
                        return;
                    }
                    var insertUpdateModel = new InsertUpdateModel(nts.uk.text.padLeft(self.currentTax().code(), '0', 2), self.currentTax().name, self.currentTax().taxLimit);
                    a.service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function () {
                        $.when(self.getCommuteNoTaxLimitList()).done(function () {
                            self.currentCode(nts.uk.text.padLeft(self.currentTax().code(), '0', 2));
                        });
                        if (self.isUpdate() === false) {
                            self.isUpdate(true);
                            self.allowEditCode(false);
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var deleteCode = self.currentTax().code();
                    a.service.deleteData(new DeleteModel(deleteCode)).done(function () {
                        var indexItemDelete = _.findIndex(self.items(), function (item) { return item.code == self.currentTax().code(); });
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
                    });
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    if (confirm("do you wanna delete") === true) {
                        self.deleteData();
                    }
                    else {
                        alert("you didn't delete!");
                    }
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
                    }).fail(function (res) {
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
