var qmm023;
(function (qmm023) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.constraint = 'CommuNoTaxLimitCode';
                    this.isUpdate = ko.observable(true);
                    this.allowEditCode = ko.observable(false);
                    var self = this;
                    //constructor of gridList
                    this.items = ko.observableArray([]);
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 120 },
                        { headerText: '説明', prop: 'taxLimit', width: 170 }
                    ]);
                    self.currentCode = ko.observable(null);
                    //finding the first object
                    self.currentTax = ko.observable(ko.mapping.fromJS(_.first(self.items())));
                    self.codeValue = ko.observable(self.currentTax().code);
                    self.nameValue = ko.observable(self.currentTax().name);
                    self.taxLimitValue = ko.observable(self.currentTax().taxLimit);
                    //get event when hover on table by subcribe
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                        if (self.currentTax()) {
                            self.codeValue(self.currentTax().code);
                            self.nameValue(self.currentTax().name);
                            self.allowEditCode(false);
                            self.isUpdate(true);
                        }
                    });
                }
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
                    self.currentTax(ko.mapping.fromJS(new TaxModel('', '', null)));
                    self.isUpdate(false);
                };
                ScreenModel.prototype.insertUpdateData = function () {
                    var self = this;
                    var insertUpdateModel = new InsertUpdateModel(self.currentTax().code(), self.currentTax().name(), self.currentTax().taxLimit());
                    a.service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function () {
                        if (self.isUpdate() === false) {
                            self.items.push(_.cloneDeep(ko.mapping.toJS(self.currentTax())));
                            self.isUpdate(true);
                            self.allowEditCode(false);
                            self.currentCode(self.currentTax().code());
                        }
                        else {
                            var indexItemUpdate = _.findIndex(self.items(), function (item) { return item.code == self.currentTax().code(); });
                            self.items().splice(indexItemUpdate, 1, _.cloneDeep(ko.mapping.toJS(self.currentTax())));
                            self.items.valueHasMutated();
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
                        self.items.remove(function (item) {
                            return item.code == deleteCode;
                        });
                        self.items.valueHasMutated();
                        if (self.items().length === 0) {
                            self.allowEditCode(true);
                            self.isUpdate(false);
                        }
                        else if (self.items().length === indexItemDelete) {
                            self.currentCode(self.items()[indexItemDelete - 1].code);
                        }
                        else {
                            self.currentCode(self.items()[indexItemDelete].code);
                        }
                    }).fail(function (error) {
                        alert(error.message);
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
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                };
                // startpage
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    return self.getCommuteNoTaxLimitList();
                };
                ScreenModel.prototype.getCommuteNoTaxLimitList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getCommutelimitsByCompanyCode().done(function (data) {
                        self.buildItemList(data);
                        if (self.items().length > 0) {
                            self.currentTax = ko.observable(ko.mapping.fromJS(_.first(self.items())));
                            self.currentCode(self.currentTax().code());
                        }
                        dfd.resolve(data);
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.buildItemList = function (data) {
                    var self = this;
                    _.forEach(data, function (item) {
                        self.items.push(new TaxModel(item.commuNoTaxLimitCode, item.commuNoTaxLimitName, item.commuNoTaxLimitValue));
                    });
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
