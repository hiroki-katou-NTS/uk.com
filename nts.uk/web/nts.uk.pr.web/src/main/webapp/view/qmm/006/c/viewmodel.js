var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.currentCode = ko.observable();
                    self.currentCode1 = ko.observable();
                    self.items = ko.observableArray([]);
                    self.items1 = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'lineBankCode', width: 50 },
                        { headerText: '名称', key: 'lineBankName', width: 160 },
                        { headerText: '口座区分', key: 'accountAtr', width: 120 },
                        { headerText: '口座番号', key: 'accountNo', width: 100 }
                    ]);
                }
                ScreenModel.prototype.startPage = function () {
                    return this.findAll();
                };
                ScreenModel.prototype.findAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm006.c.service.findAll()
                        .done(function (data) {
                        if (data.length > 0) {
                            self.items(data);
                            self.items1(data);
                        }
                        else {
                            self.items([]);
                            self.items1([]);
                        }
                        dfd.resolve();
                    }).fail(function (res) { });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var LineBankC = (function () {
                function LineBankC(lineBankCode, lineBankName, accountAtr, accountNo) {
                    this.lineBankCode = ko.observable(lineBankCode);
                    this.lineBankName = ko.observable(lineBankName);
                    this.accountAtr = ko.observable(accountAtr);
                    this.accountNo = ko.observable(accountNo);
                }
                return LineBankC;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
;
