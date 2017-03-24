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
                                    //panel
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                    self.officeModel = ko.observable(new SocialInsuranceOfficeModel());
                                    //text input options
                                    self.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        width: "100",
                                        textalign: "center"
                                    }));
                                    self.selectedOfficeCode = ko.observable('');
                                    self.errorList = ko.observableArray([
                                        { messageId: "ER001", message: "＊が入力されていません。" },
                                        { messageId: "ER007", message: "＊が選択されていません。" },
                                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" },
                                        { messageId: "ER008", message: "選択された＊は使用されているため削除できません。" },
                                    ]);
                                    self.selectedOfficeCode.subscribe(function (selectedOfficeCode) {
                                        $('.save-error').ntsError('clear');
                                        if (selectedOfficeCode != null && selectedOfficeCode != undefined && selectedOfficeCode != "") {
                                            self.enabled(false);
                                            self.deleteButtonControll(true);
                                            $.when(self.load(selectedOfficeCode)).done(function () {
                                                //load data success
                                            }).fail(function (res) {
                                                //when load data error
                                            });
                                        }
                                    });
                                }
                                // start
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    //first load
                                    self.loadAllInsuranceOfficeData().done(function () {
                                        // Load first result.
                                        if (self.officeItems().length > 0) {
                                        }
                                        else {
                                            //register new office mode
                                            self.addNew();
                                        }
                                        // Resolve
                                        dfd.resolve();
                                    });
                                    // Return.
                                    return dfd.promise();
                                };
                                //
                                ScreenModel.prototype.loadAllInsuranceOfficeData = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    // find all insurance office 
                                    bservice.findInsuranceOffice('').done(function (data) {
                                        //check list office is empty
                                        if (data != null) {
                                            self.officeItems([]);
                                            // Set data get from service to list.
                                            data.forEach(function (item, index) {
                                                self.officeItems.push(new ItemModel(item.code, item.name));
                                            });
                                            dfd.resolve(data);
                                        }
                                        else {
                                            dfd.resolve(null);
                                        }
                                    });
                                    // Return.
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.load = function (officeCode) {
                                    if (officeCode != null && officeCode != '') {
                                        var self = this;
                                        e.service.getOfficeItemDetail(officeCode).done(function (data) {
                                            //Convert data get from service to screen
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
                                    // 
                                    data.forEach(function (item, index) {
                                        OfficeItemList.push(new ItemModel(item.code, item.name));
                                    });
                                    return OfficeItemList;
                                };
                                //save (mode: update or create new)
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    //if update office
                                    if (!self.enabled())
                                        self.updateOffice();
                                    else {
                                        self.registerOffice();
                                    }
                                };
                                //update office
                                ScreenModel.prototype.updateOffice = function () {
                                    var self = this;
                                    e.service.update(self.collectData()).done(function () {
                                        //when update done
                                        self.loadAllInsuranceOfficeData().done(function () {
                                            //focus add new item
                                            self.selectedOfficeCode(self.officeModel().officeCode());
                                        });
                                    }).fail(function () {
                                        //update fail    
                                    });
                                };
                                //create new Office
                                ScreenModel.prototype.registerOffice = function () {
                                    var self = this;
                                    e.service.register(self.collectData()).done(function () {
                                        // when register done
                                        self.loadAllInsuranceOfficeData().done(function () {
                                            //focus add new item
                                            self.selectedOfficeCode(self.officeModel().officeCode());
                                        });
                                    }).fail(function (res) {
                                        if (res.messageId == self.errorList()[2].messageId) {
                                            $('#officeCode').ntsError('set', self.errorList()[2].message);
                                        }
                                        if (res.messageId == self.errorList()[0].messageId) {
                                            if (!self.officeModel().officeCode())
                                                $('#officeCode').ntsError('set', self.errorList()[0].message);
                                            if (!self.officeModel().officeName())
                                                $('#officeName').ntsError('set', self.errorList()[0].message);
                                            if (!self.officeModel().PicPosition())
                                                $('#picPosition').ntsError('set', self.errorList()[0].message);
                                        }
                                    });
                                };
                                //remove office  by office Code
                                ScreenModel.prototype.remove = function () {
                                    var self = this;
                                    if (self.selectedOfficeCode() != '') {
                                        e.service.remove(self.selectedOfficeCode()).done(function () {
                                            //if remove success
                                        }).fail(function () {
                                            // if remove fail    
                                        });
                                        //reload list
                                        self.loadAllInsuranceOfficeData().done(function () {
                                            // if empty list -> add new mode
                                            if (self.officeItems().length == 0) {
                                                self.addNew();
                                            }
                                            else {
                                                self.selectedOfficeCode(self.officeItems()[0].code);
                                            }
                                        });
                                    }
                                };
                                //collect all data
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var a = new e.service.model.finder.OfficeItemDto("company code", self.officeModel().officeCode(), self.officeModel().officeName(), self.officeModel().shortName(), self.officeModel().PicName(), self.officeModel().PicPosition(), self.officeModel().potalCode(), self.officeModel().prefecture(), self.officeModel().address1st(), self.officeModel().address2nd(), self.officeModel().kanaAddress1st(), self.officeModel().kanaAddress2nd(), self.officeModel().phoneNumber(), self.officeModel().healthInsuOfficeRefCode1st(), self.officeModel().healthInsuOfficeRefCode2nd(), self.officeModel().pensionOfficeRefCode1st(), self.officeModel().pensionOfficeRefCode2nd(), self.officeModel().welfarePensionFundCode(), self.officeModel().officePensionFundCode(), self.officeModel().healthInsuCityCode(), self.officeModel().healthInsuOfficeSign(), self.officeModel().pensionCityCode(), self.officeModel().pensionOfficeSign(), self.officeModel().healthInsuOfficeCode(), self.officeModel().healthInsuAssoCode(), self.officeModel().memo());
                                    return a;
                                };
                                //reset all field when click add new office button
                                ScreenModel.prototype.addNew = function () {
                                    var self = this;
                                    //reset all input fields to blank
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
                                    //set enabled code input
                                    self.enabled(true);
                                    //disable remove
                                    self.deleteButtonControll(false);
                                    //reset selected officeCode
                                    self.selectedOfficeCode('');
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    // Set child value
                                    nts.uk.ui.windows.setShared("insuranceOfficeChildValue", "return value", this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            //Models
                            var SocialInsuranceOfficeModel = (function () {
                                function SocialInsuranceOfficeModel() {
                                    //basic info input
                                    this.officeCode = ko.observable('');
                                    this.officeName = ko.observable('');
                                    this.shortName = ko.observable('');
                                    this.PicName = ko.observable('');
                                    this.PicPosition = ko.observable('');
                                    this.potalCode = ko.observable('');
                                    this.prefecture = ko.observable('');
                                    this.address1st = ko.observable('');
                                    this.kanaAddress1st = ko.observable('');
                                    this.address2nd = ko.observable('');
                                    this.kanaAddress2nd = ko.observable('');
                                    this.phoneNumber = ko.observable('');
                                    //insurance info input 
                                    this.healthInsuOfficeRefCode1st = ko.observable('');
                                    this.healthInsuOfficeRefCode2nd = ko.observable('');
                                    this.pensionOfficeRefCode1st = ko.observable('');
                                    this.pensionOfficeRefCode2nd = ko.observable('');
                                    this.welfarePensionFundCode = ko.observable('');
                                    this.officePensionFundCode = ko.observable('');
                                    this.healthInsuCityCode = ko.observable('');
                                    this.healthInsuOfficeSign = ko.observable('');
                                    this.pensionCityCode = ko.observable('');
                                    this.pensionOfficeSign = ko.observable('');
                                    this.healthInsuOfficeCode = ko.observable('');
                                    this.healthInsuAssoCode = ko.observable('');
                                    this.memo = ko.observable('');
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