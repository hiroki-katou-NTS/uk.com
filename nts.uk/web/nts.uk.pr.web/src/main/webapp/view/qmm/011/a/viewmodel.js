var qmm011;
(function (qmm011) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.clst001 = ko.observableArray([
                        new CItemModelLST001("2016/01:9999/12", "2016/01 ~ 9999/12"),
                        new CItemModelLST001("2016/01:2015/12", "2016/01 ~ 2015/12"),
                        new CItemModelLST001("2016/01:2015/03", "2016/01 ~ 2015/03"),
                        new CItemModelLST001("2016/02:2015/11", "2016/02 ~ 2015/11")
                    ]);
                    self.csel001 = ko.observableArray([
                        new CItemModelSEL001("0", "切り捨て"),
                        new CItemModelSEL001("1", "切り上げ"),
                        new CItemModelSEL001("2", "四捨五入"),
                        new CItemModelSEL001("3", "五捨六入"),
                        new CItemModelSEL001("4", "五捨五超入")
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(2);
                    self.selectedCode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm011.a || (qmm011.a = {}));
})(qmm011 || (qmm011 = {}));
