var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
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
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.lst_001 = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                                        new Node('0003', 'Bangkok Thailand', []),
                                        new Node('0004', 'Tokyo Japan', []),
                                        new Node('0005', 'Jakarta Indonesia', []),
                                        new Node('0002', 'Seoul Korea', []),
                                        new Node('0006', 'Paris France', []),
                                        new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                                        new Node('0010', 'Beijing China', []),
                                        new Node('0011', 'London United Kingdom', []),
                                        new Node('0012', 'USA', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])])]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.lst_001(), "childs"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.index = 0;
                                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                                    self.lbl_005 = ko.observable('（平成29年01月） ~');
                                    self.inp_002_code = {
                                        value: ko.observable('001'),
                                        constraint: 'Code',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "CODE",
                                            width: "50px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_003_name = {
                                        value: ko.observable('ガソリン単価'),
                                        constraint: 'Name',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "NAME",
                                            width: "250px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_004_date = {
                                        value: ko.observable('2015-04'),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "START_DATE",
                                            width: "70px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_005_money = {
                                        value: ko.observable(120),
                                        constraint: 'Money',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                            grouplength: 3,
                                            decimallength: 2,
                                            currencyformat: "JPY",
                                            currencyposition: 'right',
                                            width: "100px",
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_006_memo = ko.observable('');
                                    self.switchButtonDataSource = ko.observableArray([
                                        { code: '1', name: '対象' },
                                        { code: '2', name: '対象外' }
                                    ]);
                                    self.sel_001_radio = ko.observable('2');
                                    self.sel_002_xxx = ko.observable('2');
                                    self.sel_003_monthly = ko.observable('2');
                                    self.sel_004_dayMonth = ko.observable('2');
                                    self.sel_005_daily = ko.observable('2');
                                    self.sel_006_hourly = ko.observable('2');
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.goToB = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 380, width: 450 });
                                };
                                ScreenModel.prototype.goToC = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 450, width: 560 });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
