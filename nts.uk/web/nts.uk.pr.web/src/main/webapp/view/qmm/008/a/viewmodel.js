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
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var InsuranceOfficeItem = a.service.model.finder.InsuranceOfficeItemDto;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.healthInsuranceRateModel = new HealthInsuranceRateModel("code", "name", true, null, null);
                                    self.InsuranceOfficeList = ko.observableArray([
                                        new InsuranceOfficeItem('id01', 'A 事業所', 'code1', [
                                            new InsuranceOfficeItem('child01', '~ 9999/12', '2016/04', []),
                                            new InsuranceOfficeItem('child02', '~ 9999/12', '2016/04', [])
                                        ]),
                                        new InsuranceOfficeItem('id02', 'B 事業所', 'code2', [])]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.InsuranceOfficeList(), "childs"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.index = 0;
                                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                                    self.roundingList = ko.observableArray([
                                        new RoundingItemModel('001', 'op1change'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.healthInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.enable = ko.observable(true);
                                    self.roundingRules = ko.observableArray([
                                        { code: '1', name: 'する' },
                                        { code: '2', name: 'しない' }
                                    ]);
                                    self.selectedRuleCode = ko.observable(1);
                                    self.pensionFundInputEnable = ko.observable(true);
                                    self.pensionFundInputOptions = ko.observableArray([
                                        { code: '1', name: '有' },
                                        { code: '2', name: '無' }
                                    ]);
                                    self.pensionFundInputSelectedCode = ko.observable(1);
                                    self.pensionCalculateEnable = ko.observable(true);
                                    self.pensionCalculateOptions = ko.observableArray([
                                        { code: '1', name: 'する' },
                                        { code: '2', name: 'しない' }
                                    ]);
                                    self.pensionCalculateSelectedCode = ko.observable(1);
                                    self.date = ko.observable(new Date('2016/12/01'));
                                    self.show = ko.observable(true);
                                    self.itemList = ko.observableArray([]);
                                    self.btnText = ko.computed(function () { if (self.show())
                                        return "-"; return "+"; });
                                    for (var i = 1; i <= 12; i++) {
                                        self.itemList.push({ index: i });
                                    }
                                    self.value = ko.observable("Hello world!");
                                    self.isTransistReturnData = ko.observable(false);
                                    self.healthTimeInput = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.pensionTimeInput = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.healthSalaryPersonalGeneral = ko.observable(100000);
                                    self.healthSalaryCompanyGeneral = ko.observable(100000);
                                    self.healthBonusPersonalGeneral = ko.observable(100000);
                                    self.healthBonusCompanyGeneral = ko.observable(100000);
                                    self.comboBox1 = ko.observableArray(self.roundingList());
                                    self.comboBox1ItemName = ko.observable('');
                                    self.comboBox1CurrentCode = ko.observable(3);
                                    self.comboBox1SelectedCode = ko.observable('002');
                                    self.comboBox2 = ko.observableArray(self.roundingList());
                                    self.comboBox2ItemName = ko.observable('');
                                    self.comboBox2CurrentCode = ko.observable(3);
                                    self.comboBox2SelectedCode = ko.observable('002');
                                    self.comboBox3 = ko.observableArray(self.roundingList());
                                    self.comboBox3ItemName = ko.observable('');
                                    self.comboBox3CurrentCode = ko.observable(3);
                                    self.comboBox3SelectedCode = ko.observable('002');
                                    self.comboBox4 = ko.observableArray(self.roundingList());
                                    self.comboBox4ItemName = ko.observable('');
                                    self.comboBox4CurrentCode = ko.observable(3);
                                    self.comboBox4SelectedCode = ko.observable('002');
                                    self.comboBox5 = ko.observableArray(self.roundingList());
                                    self.comboBox5ItemName = ko.observable('');
                                    self.comboBox5CurrentCode = ko.observable(3);
                                    self.comboBox5SelectedCode = ko.observable('002');
                                    self.comboBox6 = ko.observableArray(self.roundingList());
                                    self.comboBox6ItemName = ko.observable('');
                                    self.comboBox6CurrentCode = ko.observable(3);
                                    self.comboBox6SelectedCode = ko.observable('002');
                                    self.comboBox7 = ko.observableArray(self.roundingList());
                                    self.comboBox7ItemName = ko.observable('');
                                    self.comboBox7CurrentCode = ko.observable(3);
                                    self.comboBox7SelectedCode = ko.observable('002');
                                    self.comboBox8 = ko.observableArray(self.roundingList());
                                    self.comboBox8ItemName = ko.observable('');
                                    self.comboBox8CurrentCode = ko.observable(3);
                                    self.comboBox8SelectedCode = ko.observable('002');
                                    self.healthTotal = {
                                        value: ko.observable(5400000),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                            grouplength: 3,
                                            currencyformat: "JPY",
                                            currencyposition: 'right'
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.pensionCurrency = {
                                        value: ko.observable(1500000),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                            grouplength: 3,
                                            currencyformat: "JPY",
                                            currencyposition: 'right'
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.pensionOwnerRate = {
                                        value: ko.observable(1.5),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                            grouplength: 3,
                                            decimallength: 2
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                }
                                ScreenModel.prototype.testObservable = function () {
                                    this.healthSalaryPersonalGeneral(this.healthTimeInput.value());
                                };
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAllInsuranceOffice().done(function () {
                                        if (self.InsuranceOfficeList().length > 0) {
                                            self.selectedInsuranceOfficeId(_self.InsuranceOfficeList()[0].id);
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
                                ScreenModel.prototype.loadAllInsuranceOffice = function () {
                                    var _self = this;
                                    var dfd = $.Deferred();
                                    a.service.findInsuranceOffice(_self.searchKey()).done(function (data) {
                                        _self.InsuranceOfficeList(data);
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.getAllRounding = function () {
                                    var _self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllRounding().done(function (data) {
                                        _self.roundingList(data);
                                        dfd.resolve(null);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.resetSelection = function () {
                                    var self = this;
                                    self.filteredData(self.dataSource());
                                    self.singleSelectedCode('0002');
                                    self.selectedCodes(['002']);
                                };
                                ScreenModel.prototype.changeDataSource = function () {
                                    var self = this;
                                    var i = 0;
                                    var newArrays = new Array();
                                    while (i < 50) {
                                        self.index++;
                                        i++;
                                        newArrays.push(new Node(self.index.toString(), 'Name ' + self.index.toString(), []));
                                    }
                                    ;
                                    self.dataSource(newArrays);
                                    self.filteredData(newArrays);
                                };
                                ScreenModel.prototype.toggle = function () {
                                    this.show(!this.show());
                                };
                                ScreenModel.prototype.resize = function () {
                                    if ($("#tabs-complex").width() > 700)
                                        $("#tabs-complex").width(700);
                                    else
                                        $("#tabs-complex").width("auto");
                                };
                                ScreenModel.prototype.convertListToParentChilds = function () {
                                };
                                ScreenModel.prototype.OpenModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml", { title: "会社保険事業所の登録＞履歴の追加" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    nts.uk.ui.windows.setShared("listOfficeOfParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/d/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalConfigHistory = function () {
                                    nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml", { title: "会社保険事業所の登録＞履歴の編集" }).onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.childs = childs;
                                    self.custom = 'Random' + new Date().getTime();
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                            var RoundingItemModel = (function () {
                                function RoundingItemModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return RoundingItemModel;
                            }());
                            viewmodel.RoundingItemModel = RoundingItemModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel(companyCode, officeCode, autoCaculate, rateItems, roundingMethods) {
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
                            var HealthInsuranceRateItemModel = (function () {
                                function HealthInsuranceRateItemModel() {
                                }
                                return HealthInsuranceRateItemModel;
                            }());
                            viewmodel.HealthInsuranceRateItemModel = HealthInsuranceRateItemModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                        var HealthInsuranceAvgearn = (function () {
                            function HealthInsuranceAvgearn() {
                            }
                            return HealthInsuranceAvgearn;
                        }());
                        a.HealthInsuranceAvgearn = HealthInsuranceAvgearn;
                        var HealthInsuranceRounding = (function () {
                            function HealthInsuranceRounding() {
                            }
                            return HealthInsuranceRounding;
                        }());
                        a.HealthInsuranceRounding = HealthInsuranceRounding;
                        var ChargeRateItem = (function () {
                            function ChargeRateItem() {
                            }
                            return ChargeRateItem;
                        }());
                        a.ChargeRateItem = ChargeRateItem;
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
