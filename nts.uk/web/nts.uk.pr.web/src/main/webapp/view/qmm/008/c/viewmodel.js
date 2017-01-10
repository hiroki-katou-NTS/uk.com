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
                                function ScreenModel() {
                                    var self = this;
                                    self.listOptions = ko.observableArray([new optionsModel(1, "基本情報"), new optionsModel(2, "保険料マスタの情報")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.modalValue = ko.observable("Goodbye world!");
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
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
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                    self.basicInfo_inp_001 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "200",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_002 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "300",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_003 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_004 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_005 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_006 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_007 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_008 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_009 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_010 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_011 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.basicInfo_inp_012 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_001 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_002 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_003 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_004 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_005 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_006 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_007 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_008 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_009 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_010 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_011 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.insuranceInfo_inp_012 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                    self.textArea = ko.observable("");
                                }
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                            viewmodel.optionsModel = optionsModel;
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
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qmm008.c || (qmm008.c = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
