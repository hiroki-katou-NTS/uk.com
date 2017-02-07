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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(selectedOfficeCode) {
                                    var self = this;
                                    this.currentCode = ko.observable();
                                    this.items = ko.observableArray([]);
                                    for (var i = 1; i < 100; i++) {
                                        this.items.push(new ItemModel('00' + i, 'name' + i));
                                    }
                                    this.columns2 = ko.observableArray([
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
                                    self.officeModel = ko.observable(new SocialInsuranceOfficeModel('', '', '', '', '', 1, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''));
                                    self.textArea = ko.observable("");
                                    this.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        width: "100",
                                        textalign: "center"
                                    }));
                                    self.selectedOfficeCode = ko.observable(selectedOfficeCode);
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadInsuranceOfficeData().done(function () {
                                        if (self.InsuranceOfficeData().length > 0) {
                                        }
                                        else {
                                        }
                                        dfd.resolve(null);
                                    });
                                    self.getAllRounding().done(function () {
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadInsuranceOfficeData = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    service.findInsuranceOffice(self.selectedOfficeCode()).done(function (data) {
                                        self.InsuranceOfficeList(data);
                                        dfd.resolve(data);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.setShared("insuranceOfficeChildValue", "return value", this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var SocialInsuranceOfficeModel = (function () {
                                function SocialInsuranceOfficeModel(officeCode, officeName, shortName, PicName, PicPosition, portCode, prefecture, address1st, kanaAddress1st, address2nd, kanaAddress2nd, phoneNumber, healthInsuOfficeRefCode1st, healthInsuOfficeRefCode2nd, pensionOfficeRefCode1st, pensionOfficeRefCode2nd, welfarePensionFundCode, officePensionFundCode, healthInsuCityCode, healthInsuOfficeSign, pensionCityCode, pensionOfficeSign, healthInsuOfficeCode, healthInsuAssoCode, memo) {
                                    this.officeCode = ko.observable(officeCode);
                                    this.officeName = ko.observable(officeName);
                                    this.shortName = ko.observable(shortName);
                                    this.PicName = ko.observable(PicName);
                                    this.PicPosition = ko.observable(PicPosition);
                                    this.portCode = ko.observable(portCode);
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
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qmm008.c || (qmm008.c = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
