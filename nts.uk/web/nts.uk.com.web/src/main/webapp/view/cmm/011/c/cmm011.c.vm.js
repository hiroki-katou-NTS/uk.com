var cmm009;
(function (cmm009) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.selectStartYm = ko.observable(null);
                    self.C_INP_002 = ko.observable(null);
                    self.valueSel001 = ko.observable("");
                    self.startYmHis = ko.observable(null);
                    self.object = ko.observable(null);
                    self.data = nts.uk.ui.windows.getShared('datanull');
                    var startDateofHisFromScreena = nts.uk.ui.windows.getShared('startDateOfHis');
                    startDateofHisFromScreena = new Date(startDateofHisFromScreena);
                    var year = startDateofHisFromScreena.getFullYear();
                    var month = startDateofHisFromScreena.getMonth() + 1;
                    if (month < 10)
                        month = "0" + month;
                    var day = startDateofHisFromScreena.getDate();
                    if (day < 10)
                        day = "0" + day;
                    startDateofHisFromScreenatoString = year + month + day;
                    if (self.data == "datanull") {
                        self.isRadioCheck = ko.observable(2);
                        self.enable = ko.observable(false);
                    }
                    else {
                        self.enable = ko.observable(true);
                        self.selectStartYm(nts.uk.ui.windows.getShared('startDateOfHis'));
                        self.isRadioCheck = ko.observable(1);
                    }
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ') },
                        { value: 2, text: ko.observable('初めから作成する') }
                    ]);
                }
                ScreenModel.prototype.createHistory = function () {
                    var self = this;
                    var inputYm = $('#INP_001').val();
                    if (!nts.uk.time.parseYearMonthDate(inputYm).success) {
                        alert(nts.uk.time.parseYearMonthDate(inputYm).msg);
                        return false;
                    }
                    var selectYm = self.startYmHis();
                    var inputYm2 = inputYm.replace('/', '');
                    inputYm2 = inputYm2.replace('/', '');
                    if (+inputYm2 < +startDateofHisFromScreenatoString
                        || +inputYm2 == +startDateofHisFromScreenatoString) {
                        alert('履歴の期間が正しくありません。');
                        return false;
                    }
                    else {
                        self.createData();
                        nts.uk.ui.windows.close();
                    }
                };
                ScreenModel.prototype.createData = function () {
                    var self = this;
                    var startYearMonthDay = $('#INP_001').val();
                    var checked = null;
                    if (self.isRadioCheck() === 1 && self.enable() === true) {
                        checked = true;
                    }
                    else {
                        checked = false;
                    }
                    var memo = self.C_INP_002();
                    var obj = new Object(startYearMonthDay, checked, memo);
                    self.object(obj);
                    nts.uk.ui.windows.setShared('itemHistory', self.object());
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Object = (function () {
                function Object(startYearMonth, checked, memo) {
                    this.startYearMonth = startYearMonth;
                    this.memo = memo;
                    this.checked = checked;
                }
                return Object;
            }());
            viewmodel.Object = Object;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm009.c || (cmm009.c = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm011.c.vm.js.map