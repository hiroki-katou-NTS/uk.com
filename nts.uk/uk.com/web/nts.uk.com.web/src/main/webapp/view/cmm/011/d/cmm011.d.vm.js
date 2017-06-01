var cmm011;
(function (cmm011) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.isEnable = ko.observable(true);
                    self.startDate = ko.observable("1900");
                    self.enableInput = ko.observable(true);
                    self.yearmonthdayeditor = {
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                            inputFormat: 'date'
                        })),
                    };
                    self.startDateFromA = ko.observable(null);
                    self.valueSel001 = ko.observable("");
                    self.startYmHis = ko.observable(null);
                    self.endDate = ko.observable(null);
                    var data = nts.uk.ui.windows.getShared('itemHist');
                    self.startDate(data.startDate);
                    self.startDateFromA(data.startDate);
                    self.endDate(data.endDate);
                    //---radio
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('履歴を削除する') },
                        { value: 2, text: ko.observable('履歴を修正する') }
                    ]);
                    self.isRadioCheck = ko.observable(2);
                    var index = data.index;
                    if (index != "0") {
                        self.isEnable(false);
                    }
                    self.isRadioCheck.subscribe(function (codeChangeds) {
                        if (codeChangeds == 1) {
                            self.enableInput(false);
                        }
                        else if (codeChangeds == 2) {
                            self.enableInput(true);
                        }
                    });
                }
                ScreenModel.prototype.createHistory = function () {
                    var self = this;
                    if (self.isRadioCheck() == 2) {
                        self.confirmDelete();
                    }
                    else if (self.isRadioCheck() == 1) {
                        nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                            self.confirmDelete();
                        }).ifNo(function () { });
                    }
                };
                ScreenModel.prototype.confirmDelete = function () {
                    var self = this;
                    var inputYm = $('#INP_STARTYMD').val();
                    //check YM
                    if (self.isRadioCheck() == 2) {
                        if (inputYm == "") {
                            alert("開始年月日 が入力されていません。");
                            return false;
                        }
                        if (!nts.uk.time.parseYearMonthDate(inputYm).success) {
                            alert(nts.uk.time.parseYearMonthDate(inputYm).msg);
                            return false;
                        }
                        var selectYm = self.startDateFromA();
                        var inputYm2 = inputYm.replace('/', '');
                        inputYm2 = inputYm2.replace('/', '');
                        selectYm = selectYm.replace('/', '');
                        selectYm = selectYm.replace('/', '');
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
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm011.d || (cmm011.d = {}));
})(cmm011 || (cmm011 = {}));
//# sourceMappingURL=cmm011.d.vm.js.map