var qmm011;
(function (qmm011) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    var valueblst001 = ko.observableArray([
                        new BItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                        new BItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                        new BItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                        new BItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
                    ]);
                    var valuebsel001 = ko.observableArray([
                        new RoundingMethod("0", "切り捨て"),
                        new RoundingMethod("1", "切り上げ"),
                        new RoundingMethod("2", "四捨五入"),
                        new RoundingMethod("3", "五捨六入"),
                        new RoundingMethod("4", "五捨五超入")
                    ]);
                    self.blst001 = valueblst001;
                    self.blstsel001 = ko.observableArray([]);
                    self.bsel001 = valuebsel001;
                    self.bsel002 = valuebsel001;
                    self.bsel003 = valuebsel001;
                    self.bsel004 = valuebsel001;
                    self.bsel005 = valuebsel001;
                    self.bsel006 = valuebsel001;
                    self.inp_historyb = ko.observable('');
                    self.rateGeneralBusinessPerson = ko.observable('40.009');
                    self.binp003 = ko.observable('40.009');
                    self.binp004 = ko.observable('40.009');
                    self.binp005 = ko.observable('40.009');
                    self.binp006 = ko.observable('40.009');
                    self.bsel001Code = ko.observable(null);
                    self.bsel002Code = ko.observable(null);
                    self.bsel003Code = ko.observable(null);
                    self.bsel004Code = ko.observable(null);
                    self.bsel005Code = ko.observable(null);
                    self.bsel006Code = ko.observable(null);
                    var valueclst001 = ko.observableArray([
                        new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                        new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                        new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                        new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
                    ]);
                    var valuecsel001 = ko.observableArray([
                        new CItemModelSEL001("0", "切り捨て"),
                        new CItemModelSEL001("1", "切り上げ"),
                        new CItemModelSEL001("2", "四捨五入"),
                        new CItemModelSEL001("3", "五捨六入"),
                        new CItemModelSEL001("4", "五捨五超入")
                    ]);
                    self.clst001 = valueclst001;
                    self.csel001 = valuecsel001;
                    self.csel0011 = valuecsel001;
                    self.csel0012 = valuecsel001;
                    self.csel0013 = valuecsel001;
                    self.csel0014 = valuecsel001;
                    self.csel0015 = valuecsel001;
                    self.csel0016 = valuecsel001;
                    self.csel0017 = valuecsel001;
                    self.csel0018 = valuecsel001;
                    self.csel0019 = valuecsel001;
                    self.clstsel001 = ko.observableArray([]);
                    self.csel001Code = ko.observable(null);
                    self.csel0011Code = ko.observable(null);
                    self.csel0012Code = ko.observable(null);
                    self.csel0013Code = ko.observable(null);
                    self.csel0014Code = ko.observable(null);
                    self.csel0015Code = ko.observable(null);
                    self.csel0016Code = ko.observable(null);
                    self.csel0017Code = ko.observable(null);
                    self.csel0018Code = ko.observable(null);
                    self.csel0019Code = ko.observable(null);
                    self.cinp001 = ko.observable(null);
                    self.cinp002 = ko.observable('40.009');
                    self.cinp003 = ko.observable('40.009');
                    self.cinp004 = ko.observable('40.009');
                    self.cinp005 = ko.observable('40.009');
                    self.cinp006 = ko.observable('40.009');
                    self.cinp007 = ko.observable('40.009');
                    self.cinp008 = ko.observable('40.009');
                    self.cinp009 = ko.observable('40.009');
                    self.cinp010 = ko.observable('40.009');
                    self.cinp011 = ko.observable('40.009');
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(2);
                    self.isEnable = ko.observable(true);
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BItemModelLST001 = (function () {
                function BItemModelLST001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return BItemModelLST001;
            }());
            viewmodel.BItemModelLST001 = BItemModelLST001;
            var RoundingMethod = (function () {
                function RoundingMethod(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return RoundingMethod;
            }());
            viewmodel.RoundingMethod = RoundingMethod;
            var CItemModelLST001 = (function () {
                function CItemModelLST001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return CItemModelLST001;
            }());
            viewmodel.CItemModelLST001 = CItemModelLST001;
            var CItemModelSEL001 = (function () {
                function CItemModelSEL001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return CItemModelSEL001;
            }());
            viewmodel.CItemModelSEL001 = CItemModelSEL001;
            var UnemployeeInsuranceRate = (function () {
                function UnemployeeInsuranceRate(historyId, companyCode, monthRange) {
                    this.historyId = historyId;
                    this.companyCode = companyCode;
                    this.monthRange = monthRange;
                }
                return UnemployeeInsuranceRate;
            }());
            viewmodel.UnemployeeInsuranceRate = UnemployeeInsuranceRate;
            var MonthRange = (function () {
                function MonthRange(startMonth, endMonth) {
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                }
                return MonthRange;
            }());
            viewmodel.MonthRange = MonthRange;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm011.a || (qmm011.a = {}));
})(qmm011 || (qmm011 = {}));
