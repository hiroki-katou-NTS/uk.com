var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var e;
                    (function (e) {
                        var viewmodel;
                        (function (viewmodel) {
                            var bservice = nts.uk.pr.view.qmm008.b.service;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.enabled = ko.observable(true);
                                    self.deleteButtonControll = ko.observable(true);
                                    self.officeItems = ko.observableArray([]);
                                    self.columns2 = ko.observableArray([
                                        { headerText: 'コード', key: 'code', width: 100 },
                                        { headerText: '名称', key: 'name', width: 150 }
                                    ]);
                                    self.listOptions = ko.observableArray([new optionsModel(1, "基本情報"), new optionsModel(2, "保険料マスタの情報")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                    self.officeModel = ko.observable(new SocialInsuranceOfficeModel('', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''));
                                    self.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        width: "100",
                                        textalign: "center"
                                    }));
                                    self.selectedOfficeCode = ko.observable('');
                                    self.selectedOfficeCode.subscribe(function (selectedOfficeCode) {
                                        $('.save-error').ntsError('clear');
                                        if (selectedOfficeCode != null && selectedOfficeCode != undefined && selectedOfficeCode != "") {
                                            self.enabled(false);
                                            self.deleteButtonControll(true);
                                            $.when(self.load(selectedOfficeCode)).done(function () {
                                            }).fail(function (res) {
                                            });
                                        }
                                    });
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAllInsuranceOfficeData().done(function () {
                                        if (self.officeItems().length > 0) {
                                        }
                                        else {
                                            self.addNew();
                                        }
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAllInsuranceOfficeData = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    bservice.findInsuranceOffice('').done(function (data) {
                                        if (data != null) {
                                            self.officeItems([]);
                                            data.forEach(function (item, index) {
                                                self.officeItems.push(new ItemModel(item.code, item.name));
                                            });
                                            dfd.resolve(data);
                                        }
                                        else {
                                            dfd.resolve(null);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.load = function (officeCode) {
                                    if (officeCode != null && officeCode != '') {
                                        var self = this;
                                        e.service.getOfficeItemDetail(officeCode).done(function (data) {
                                            self.officeModel().officeCode(data.code);
                                            self.officeModel().officeName(data.name);
                                            self.officeModel().shortName(data.shortName);
                                            self.officeModel().PicName(data.picName);
                                            self.officeModel().PicPosition(data.picPosition);
                                            self.officeModel().potalCode(data.potalCode);
                                            self.officeModel().prefecture(data.prefecture);
                                            self.officeModel().address1st(data.address1st);
                                            self.officeModel().kanaAddress1st(data.kanaAddress1st);
                                            self.officeModel().address2nd(data.address2nd);
                                            self.officeModel().kanaAddress2nd(data.kanaAddress2nd);
                                            self.officeModel().phoneNumber(data.phoneNumber);
                                            self.officeModel().healthInsuOfficeRefCode1st(data.healthInsuOfficeRefCode1st);
                                            self.officeModel().healthInsuOfficeRefCode2nd(data.healthInsuOfficeRefCode2nd);
                                            self.officeModel().pensionOfficeRefCode1st(data.pensionOfficeRefCode1st);
                                            self.officeModel().pensionOfficeRefCode2nd(data.pensionOfficeRefCode2nd);
                                            self.officeModel().welfarePensionFundCode(data.welfarePensionFundCode);
                                            self.officeModel().officePensionFundCode(data.officePensionFundCode);
                                            self.officeModel().healthInsuCityCode(data.healthInsuCityCode);
                                            self.officeModel().healthInsuOfficeSign(data.healthInsuOfficeSign);
                                            self.officeModel().pensionCityCode(data.pensionCityCode);
                                            self.officeModel().pensionOfficeSign(data.pensionOfficeSign);
                                            self.officeModel().healthInsuOfficeCode(data.healthInsuOfficeCode);
                                            self.officeModel().healthInsuAssoCode(data.healthInsuAssoCode);
                                            self.officeModel().memo(data.memo);
                                        });
                                    }
                                    return;
                                };
                                ScreenModel.prototype.convertDatatoList = function (data) {
                                    var OfficeItemList = [];
                                    data.forEach(function (item, index) {
                                        OfficeItemList.push(new ItemModel(item.code, item.name));
                                    });
                                    return OfficeItemList;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (!self.enabled())
                                        self.updateOffice();
                                    else {
                                        self.registerOffice();
                                        self.loadAllInsuranceOfficeData().done(function () {
                                            self.selectedOfficeCode(self.officeModel().officeCode());
                                        });
                                    }
                                };
                                ScreenModel.prototype.updateOffice = function () {
                                    var self = this;
                                    e.service.update(self.collectData()).done(function () {
                                    }).fail(function () {
                                    });
                                };
                                ScreenModel.prototype.registerOffice = function () {
                                    var self = this;
                                    e.service.register(self.collectData()).done(function () {
                                    }).fail(function (res) {
                                        alert(res.message);
                                    });
                                };
                                ScreenModel.prototype.remove = function () {
                                    var self = this;
                                    if (self.selectedOfficeCode() != '') {
                                        e.service.remove(self.selectedOfficeCode()).done(function () {
                                        }).fail(function () {
                                        });
                                        self.loadAllInsuranceOfficeData().done(function () {
                                            if (self.officeItems().length == 0) {
                                                self.addNew();
                                            }
                                            else {
                                                self.selectedOfficeCode(self.officeItems()[0].code);
                                            }
                                        });
                                    }
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var a = new e.service.model.finder.OfficeItemDto("company code", self.officeModel().officeCode(), self.officeModel().officeName(), self.officeModel().shortName(), self.officeModel().PicName(), self.officeModel().PicPosition(), self.officeModel().potalCode(), self.officeModel().prefecture(), self.officeModel().address1st(), self.officeModel().address2nd(), self.officeModel().kanaAddress1st(), self.officeModel().kanaAddress2nd(), self.officeModel().phoneNumber(), self.officeModel().healthInsuOfficeRefCode1st(), self.officeModel().healthInsuOfficeRefCode2nd(), self.officeModel().pensionOfficeRefCode1st(), self.officeModel().pensionOfficeRefCode2nd(), self.officeModel().welfarePensionFundCode(), self.officeModel().officePensionFundCode(), self.officeModel().healthInsuCityCode(), self.officeModel().healthInsuOfficeSign(), self.officeModel().pensionCityCode(), self.officeModel().pensionOfficeSign(), self.officeModel().healthInsuOfficeCode(), self.officeModel().healthInsuAssoCode(), self.officeModel().memo());
                                    return a;
                                };
                                ScreenModel.prototype.addNew = function () {
                                    var self = this;
                                    self.officeModel().officeCode('');
                                    self.officeModel().officeName('');
                                    self.officeModel().shortName('');
                                    self.officeModel().PicName('');
                                    self.officeModel().PicPosition('');
                                    self.officeModel().potalCode('');
                                    self.officeModel().prefecture('');
                                    self.officeModel().address1st('');
                                    self.officeModel().kanaAddress1st('');
                                    self.officeModel().address2nd('');
                                    self.officeModel().kanaAddress2nd('');
                                    self.officeModel().phoneNumber('');
                                    self.officeModel().healthInsuOfficeRefCode1st('');
                                    self.officeModel().healthInsuOfficeRefCode2nd('');
                                    self.officeModel().pensionOfficeRefCode1st('');
                                    self.officeModel().pensionOfficeRefCode2nd('');
                                    self.officeModel().welfarePensionFundCode('');
                                    self.officeModel().officePensionFundCode('');
                                    self.officeModel().healthInsuCityCode('');
                                    self.officeModel().healthInsuOfficeSign('');
                                    self.officeModel().pensionCityCode('');
                                    self.officeModel().pensionOfficeSign('');
                                    self.officeModel().healthInsuOfficeCode('');
                                    self.officeModel().healthInsuAssoCode('');
                                    self.officeModel().memo('');
                                    self.enabled(true);
                                    self.deleteButtonControll(false);
                                    self.selectedOfficeCode('');
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.setShared("insuranceOfficeChildValue", "return value", this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var SocialInsuranceOfficeModel = (function () {
                                function SocialInsuranceOfficeModel(officeCode, officeName, shortName, PicName, PicPosition, potalCode, prefecture, address1st, kanaAddress1st, address2nd, kanaAddress2nd, phoneNumber, healthInsuOfficeRefCode1st, healthInsuOfficeRefCode2nd, pensionOfficeRefCode1st, pensionOfficeRefCode2nd, welfarePensionFundCode, officePensionFundCode, healthInsuCityCode, healthInsuOfficeSign, pensionCityCode, pensionOfficeSign, healthInsuOfficeCode, healthInsuAssoCode, memo) {
                                    this.officeCode = ko.observable(officeCode);
                                    this.officeName = ko.observable(officeName);
                                    this.shortName = ko.observable(shortName);
                                    this.PicName = ko.observable(PicName);
                                    this.PicPosition = ko.observable(PicPosition);
                                    this.potalCode = ko.observable(potalCode);
                                    this.prefecture = ko.observable(prefecture);
                                    this.address1st = ko.observable(address1st);
                                    this.kanaAddress1st = ko.observable(kanaAddress1st);
                                    this.address2nd = ko.observable(address2nd);
                                    this.kanaAddress2nd = ko.observable(kanaAddress2nd);
                                    this.phoneNumber = ko.observable(phoneNumber);
                                    this.healthInsuOfficeRefCode1st = ko.observable(healthInsuOfficeRefCode1st);
                                    this.healthInsuOfficeRefCode2nd = ko.observable(healthInsuOfficeRefCode2nd);
                                    this.pensionOfficeRefCode1st = ko.observable(pensionOfficeRefCode1st);
                                    this.pensionOfficeRefCode2nd = ko.observable(pensionOfficeRefCode2nd);
                                    this.welfarePensionFundCode = ko.observable(welfarePensionFundCode);
                                    this.officePensionFundCode = ko.observable(officePensionFundCode);
                                    this.healthInsuCityCode = ko.observable(healthInsuCityCode);
                                    this.healthInsuOfficeSign = ko.observable(healthInsuOfficeSign);
                                    this.pensionCityCode = ko.observable(pensionCityCode);
                                    this.pensionOfficeSign = ko.observable(pensionOfficeSign);
                                    this.healthInsuOfficeCode = ko.observable(healthInsuOfficeCode);
                                    this.healthInsuAssoCode = ko.observable(healthInsuAssoCode);
                                    this.memo = ko.observable(memo);
                                }
                                return SocialInsuranceOfficeModel;
                            }());
                            viewmodel.SocialInsuranceOfficeModel = SocialInsuranceOfficeModel;
                            var ItemModel = (function () {
                                function ItemModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModel;
                            }());
                            viewmodel.ItemModel = ItemModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                            viewmodel.optionsModel = optionsModel;
                        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
                    })(e = qmm008.e || (qmm008.e = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.e.vm.js.map