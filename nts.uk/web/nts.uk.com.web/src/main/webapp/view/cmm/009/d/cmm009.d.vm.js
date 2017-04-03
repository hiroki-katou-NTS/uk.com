var cmm009;
(function (cmm009) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.isEnable = ko.observable(true);
                    self.startDate = ko.observable(null);
                    self.startDateFromA = ko.observable(null);
                    self.valueSel001 = ko.observable("");
                    self.startYmHis = ko.observable(null);
                    self.endDate = ko.observable(null);
                    var data = nts.uk.ui.windows.getShared('itemHist');
                    self.startDate(data.startDate);
                    self.startDateFromA(data.startDate);
                    self.endDate(data.endDate);
                    console.log(data);
                    debugger;
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('履歴を削除する') },
                        { value: 2, text: ko.observable('履歴を修正する') }
                    ]);
                    self.isRadioCheck = ko.observable(2);
                    var index = data.index;
                    if (index != "0") {
                        self.isEnable(false);
                    }
                }
                ScreenModel.prototype.createHistory = function () {
                    var self = this;
                    var inputYm = $('#INP_001').val();
                    if (!nts.uk.time.parseYearMonthDate(inputYm).success) {
                        alert(nts.uk.time.parseYearMonthDate(inputYm).msg);
                        return false;
                    }
                    var selectYm = self.startDateFromA();
                    var inputYm2 = inputYm.replace('/', '');
                    inputYm2 = inputYm2.replace('/', '');
                    selectYm = selectYm.replace('/', '');
                    selectYm = selectYm.replace('/', '');
                    console.log(inputYm2);
                    if (+inputYm2 < +selectYm
                        || +inputYm2 == +selectYm) {
                        alert('履歴の期間が正しくありません。');
                        return false;
                    }
                    else {
                        var self = this;
                        var isRadio = self.isRadioCheck() + "";
                        nts.uk.ui.windows.setShared('newstartDate', self.startDate());
                        nts.uk.ui.windows.setShared('isradio', isRadio);
                        nts.uk.ui.windows.close();
                    }
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
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm009.d || (cmm009.d = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm009.d.vm.js.map