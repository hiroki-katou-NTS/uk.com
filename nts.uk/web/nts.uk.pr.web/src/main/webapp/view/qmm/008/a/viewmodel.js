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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                                        new Node('0003', 'Bangkok Thailand', []),
                                        new Node('0004', 'Tokyo Japan', []),
                                        new Node('0005', 'Jakarta Indonesia', []),
                                        new Node('0002', 'Seoul Korea', []),
                                        new Node('0006', 'Paris France', []),
                                        new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                                        new Node('0010', 'Beijing China', []),
                                        new Node('0011', 'London United Kingdom', []),
                                        new Node('0012', '', [])]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.index = 0;
                                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
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
                                    self.health_inp_002 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.pension_inp_002 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_003 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_004 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_005 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_006 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_007 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_008 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_009 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_010 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_011 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_012 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_013 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_014 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_015 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_016 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_017 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.health_inp_018 = {
                                        value: ko.observable('40.900'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "90",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.comboBox1 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox1ItemName = ko.observable('');
                                    self.comboBox1CurrentCode = ko.observable(3);
                                    self.comboBox1SelectedCode = ko.observable('002');
                                    self.comboBox2 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox2ItemName = ko.observable('');
                                    self.comboBox2CurrentCode = ko.observable(3);
                                    self.comboBox2SelectedCode = ko.observable('002');
                                    self.comboBox3 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox3ItemName = ko.observable('');
                                    self.comboBox3CurrentCode = ko.observable(3);
                                    self.comboBox3SelectedCode = ko.observable('002');
                                    self.comboBox4 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox4ItemName = ko.observable('');
                                    self.comboBox4CurrentCode = ko.observable(3);
                                    self.comboBox4SelectedCode = ko.observable('002');
                                    self.comboBox5 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2k'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox5ItemName = ko.observable('');
                                    self.comboBox5CurrentCode = ko.observable(3);
                                    self.comboBox5SelectedCode = ko.observable('002');
                                    self.comboBox6 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox6ItemName = ko.observable('');
                                    self.comboBox6CurrentCode = ko.observable(3);
                                    self.comboBox6SelectedCode = ko.observable('002');
                                    self.comboBox7 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
                                    self.comboBox7ItemName = ko.observable('');
                                    self.comboBox7CurrentCode = ko.observable(3);
                                    self.comboBox7SelectedCode = ko.observable('002');
                                    self.comboBox8 = ko.observableArray([
                                        new RoundingItemModel('001', 'op1'),
                                        new RoundingItemModel('002', 'op2'),
                                        new RoundingItemModel('003', 'op3')
                                    ]);
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
                                            decimallength: 2 })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                }
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
                                ScreenModel.prototype.OpenModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/b/index.xhtml").onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("addHistoryChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalOfficeRegister = function () {
                                    nts.uk.ui.windows.setShared("listOfficeOfParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/c/index.xhtml").onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPriceHealth = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/d/index.xhtml").onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalStandardMonthlyPricePension = function () {
                                    nts.uk.ui.windows.setShared("dataParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml").onClosed(function () {
                                        var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                                    });
                                };
                                ScreenModel.prototype.OpenModalConfigHistory = function () {
                                    nts.uk.ui.windows.setShared("addHistoryParentValue", this.value());
                                    nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                                    nts.uk.ui.windows.sub.modal("/view/qmm/008/f/index.xhtml").onClosed(function () {
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
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
