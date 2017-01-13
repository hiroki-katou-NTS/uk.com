var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
                            var UnitPriceHistoryDto = a.service.model.UnitPriceHistoryDto;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.unitPriceDetailModel = ko.observable(new UnitPriceDetailModel());
                                    self.historyList = ko.observableArray([
                                        new Node('001', 'ガソリン単価', '2016/04 ~ 9999/12', false, [new Node('0011', 'ガソリン単価', '2016/04 ~ 9999/12', true), new Node('0012', 'ガソリン単価', '2015/04 ~ 2016/03', true)]),
                                        new Node('002', '宿直単価', '2016/04 ~ 9999/12', false, [new Node('0021', '宿直単価', '2016/04 ~ 9999/12', true), new Node('0022', '宿直単価', '2015/04 ~ 2016/03', true)])]);
                                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.lbl_005 = ko.observable('（平成29年01月） ~');
                                    self.inp_002_code = {
                                        value: ko.observable(self.unitPriceDetailModel().unitPriceCode),
                                        constraint: 'UnitPriceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: " ",
                                            width: "50px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_003_name = {
                                        value: ko.observable(self.unitPriceDetailModel().unitPriceName),
                                        constraint: 'UnitPriceName',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "",
                                            width: "250px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_004_date = {
                                        value: ko.observable(self.unitPriceDetailModel().startDate),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "",
                                            width: "70px",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                    self.inp_005_money = {
                                        value: ko.observable(self.unitPriceDetailModel().budget),
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
                                    self.sel_001_settingType = ko.observable(0);
                                    self.sel_002_payAtr = ko.observable(0);
                                    self.sel_003_payAtrMonthly = ko.observable(0);
                                    self.sel_004_payAtrDayMonth = ko.observable(0);
                                    self.sel_005_payAtrDaily = ko.observable(0);
                                    self.sel_006_payAtrHourly = ko.observable(0);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.goToB = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 350, width: 450 });
                                };
                                ScreenModel.prototype.goToC = function () {
                                    nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 410, width: 560 });
                                };
                                ScreenModel.prototype.test = function () {
                                    //this.unitPriceDetailModel().unitPriceCode = this.inp_002_code.value();
                                    //alert(this.unitPriceDetailModel().unitPriceCode);
                                    var self = this;
                                    self.inp_002_code.value(1);
                                    self.inp_003_name.value('ガソリン単価');
                                    self.inp_004_date.value('2015-04');
                                    self.inp_005_money.value(120);
                                    self.sel_001_settingType(2);
                                    self.sel_002_payAtr(2);
                                    self.sel_003_payAtrMonthly(2);
                                    self.sel_004_payAtrDayMonth(2);
                                    self.sel_005_payAtrDaily(2);
                                    self.sel_006_payAtrHourly(2);
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = new UnitPriceHistoryDto();
                                    data.unitPriceCode = self.inp_002_code.value();
                                    console.log(data);
                                    return null;
                                };
                                ScreenModel.prototype.clearUnitPriceDetail = function () {
                                    var self = this;
                                    self.inp_002_code.value(null);
                                    self.inp_003_name.value('');
                                    self.inp_004_date.value('');
                                    self.inp_005_money.value(null);
                                    self.sel_001_settingType(0);
                                    self.sel_002_payAtr(0);
                                    self.sel_003_payAtrMonthly(0);
                                    self.sel_004_payAtrDayMonth(0);
                                    self.sel_005_payAtrDaily(0);
                                    self.sel_006_payAtrHourly(0);
                                };
                                ScreenModel.prototype.loadUnitPriceDetail = function (unitPricecode, startDate) {
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var Node = (function () {
                                function Node(code, name, monthRange, isChild, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.monthRange = monthRange;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.isChild = isChild;
                                    self.childs = childs;
                                    if (self.isChild == true) {
                                        self.nodeText = self.monthRange;
                                    }
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                            /*
                                    export class UnitPriceDetailModel {
                                        unitPriceCode: KnockoutObservable<string>;
                                        startDate: KnockoutObservable<string>;
                                        budget: KnockoutObservable<number>;
                                        fixPaySettingType: KnockoutObservable<SettingType>;
                                        fixPayAtr: KnockoutObservable<ApplySetting>;
                                        fixPayAtrMonthly: KnockoutObservable<ApplySetting>;
                                        fixPayAtrDayMonth: KnockoutObservable<ApplySetting>;
                                        fixPayAtrDaily: KnockoutObservable<ApplySetting>;
                                        fixPayAtrHourly: KnockoutObservable<ApplySetting>;
                                        memo: KnockoutObservable<string>;
                            
                                        constructor(unitPriceCode: string, startDate: number, budget: number, fixPaySettingType: number, fixPayAtr: number,
                                            fixPayAtrMonthly: number, fixPayAtrDayMonth: number, fixPayAtrDaily: number, fixPayAtrHourly: number, memo: string) {
                                            this.unitPriceCode = ko.observable(unitPriceCode);
                                            this.budget = ko.observable(budget);
                                            this.fixPaySettingType = ko.observable(fixPaySettingType);
                                            this.fixPayAtr = ko.observable(fixPayAtr);
                                            this.fixPayAtrMonthly = ko.observable(fixPayAtrMonthly);
                                            this.fixPayAtrDayMonth = ko.observable(fixPayAtrDayMonth);
                                            this.fixPayAtrDaily = ko.observable(fixPayAtrDaily);
                                            this.fixPayAtrHourly = ko.observable(fixPayAtrHourly);
                                            this.memo = ko.observable(memo);
                                        }
                                    }
                            */
                            var UnitPriceDetailModel = (function (_super) {
                                __extends(UnitPriceDetailModel, _super);
                                function UnitPriceDetailModel() {
                                    _super.apply(this, arguments);
                                }
                                return UnitPriceDetailModel;
                            }(UnitPriceHistoryDto));
                            viewmodel.UnitPriceDetailModel = UnitPriceDetailModel;
                            var UnitPriceHistoryModel = (function () {
                                function UnitPriceHistoryModel() {
                                }
                                return UnitPriceHistoryModel;
                            }());
                            viewmodel.UnitPriceHistoryModel = UnitPriceHistoryModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
