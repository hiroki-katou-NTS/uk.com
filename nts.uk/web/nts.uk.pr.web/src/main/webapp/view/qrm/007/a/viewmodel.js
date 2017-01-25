var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'itemCode', width: 50 },
                        { headerText: '名称', prop: 'itemName', width: 145 },
                        { headerText: '印刷用名称', prop: 'printName', width: 145 }
                    ]);
                    self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel('', '', '', '', '', 0, '', '')]);
                    self.currentCode = ko.observable();
                    self.currentItem = ko.observable(new RetirementPayItemModel('', '', '', '', '', 0, '', ''));
                    self.currentCode.subscribe(function (newValue) {
                        self.currentItem(_.find(self.retirementPayItemList(), function (item) { return item.itemCode === newValue; }));
                    });
                    self.texteditor = {
                        value: ko.computed({
                            read: function () { return self.currentItem().printName; },
                            write: function (newValue) {
                                self.currentItem().printName = newValue;
                                self.currentItem().itemCode = self.currentItem().itemCode;
                                self.currentItem().itemName = self.currentItem().itemName;
                            }
                        }),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            width: "200px",
                        }))
                    };
                    self.multilineeditor = {
                        value: ko.computed({
                            read: function () { return self.currentItem().memo; },
                            write: function (newValue) {
                                self.currentItem().memo = newValue;
                                self.currentItem().itemCode = self.currentItem().itemCode;
                                self.currentItem().itemName = self.currentItem().itemName;
                            }
                        }),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                        }))
                    };
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo, dataItem.companyCode, dataItem.category, dataItem.englishName, dataItem.fullName));
                            });
                            self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            self.currentItem(_.first(self.retirementPayItemList()));
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        self.retirementPayItemList.removeAll();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = {
                        itemCode: self.currentItem().itemCode,
                        itemName: self.currentItem().itemName,
                        printName: self.currentItem().printName,
                        memo: self.currentItem().memo,
                        companyCode: self.currentItem().companyCode,
                        category: self.currentItem().category,
                        englishName: self.currentItem().englishName,
                        fullName: self.currentItem().fullName
                    };
                    //var command = self.retirementPayItemList();
                    /*
                    qrm007.a.service.updateRetirementPayItemList(command).done(function(data) {
                        qrm007.a.service.getRetirementPayItemList().done(function(data) {
                        self.retirementPayItemList.removeAll();
                        if(data.length){
                            data.forEach(function(dataItem){
                                self.retirementPayItemList.push(
                                    new RetirementPayItemModel(
                                        dataItem.itemCode,
                                        dataItem.itemName,
                                        dataItem.printName,
                                        dataItem.memo,
                                        dataItem.companyCode,
                                        dataItem.category,
                                        dataItem.englishName,
                                        dataItem.fullName));
                            });
                            self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            self.currentItem(_.first(self.retirementPayItemList()));
                        }
                        dfd.resolve();
                        }).fail(function(res) {
                            self.retirementPayItemList.removeAll();
                        });
                    }).fail(function(res) {
                        
                    });
                    */
                    qrm007.a.service.updateRetirementPayItem(command).done(function (data) {
                        var currentCode = command.itemCode;
                        var currentItem = self.currentItem();
                        qrm007.a.service.getRetirementPayItemList().done(function (data) {
                            self.retirementPayItemList.removeAll();
                            if (data.length) {
                                data.forEach(function (dataItem) {
                                    self.retirementPayItemList.push(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo, dataItem.companyCode, dataItem.category, dataItem.englishName, dataItem.fullName));
                                });
                                self.currentCode(currentCode);
                                self.currentItem(currentItem);
                            }
                            dfd.resolve();
                        }).fail(function (res) {
                            self.retirementPayItemList.removeAll();
                        });
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItemModel = (function () {
                function RetirementPayItemModel(code, name, printName, memo, companyCode, category, englishName, fullName) {
                    this.itemCode = code;
                    this.itemName = name;
                    this.printName = printName;
                    this.memo = memo;
                    this.companyCode = companyCode;
                    this.category = category;
                    this.englishName = englishName;
                    this.fullName = fullName;
                }
                return RetirementPayItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
