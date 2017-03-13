var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.selectedPositionAtr = ko.observable(null);
                    self.selectPositionStartDate = ko.observable(null);
                    self.selectPositionEndDate = ko.observable(null);
                    self.selectPosition = ko.observable(null);
                    self.positionStartDate = ko.observable(null);
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    self.historyId = ko.observable(null);
                    self.enableDate = ko.observable(true);
                    //---radio
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: '履歴を削除する' },
                        { value: 2, text: '履歴を修正する' }
                    ]);
                    self.isRadioCheck = ko.observable(2);
                }
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var layoutCode = nts.uk.ui.windows.getShared('stmtCode');
                    var startYm = nts.uk.ui.windows.getShared('startYm');
                    self.historyId(nts.uk.ui.windows.getShared('historyId'));
                    self.positionStartDate(nts.uk.time.formatYearMonth(startYm));
                    d.service.getPosition(self.historyId()).done(function (position) {
                        self.selectPosition(position);
                        self.startDiaglog();
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res);
                    });
                    //checkbox change
                    self.isRadioCheck.subscribe(function (newValue) {
                        if (newValue === 2) {
                            self.enableDate(true);
                        }
                        else {
                            self.enableDate(false);
                        }
                    });
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.startDiaglog = function () {
                    var self = this;
                    var position = self.selectPosition();
                    self.selectPositionStartDate(nts.uk.time.formatYearMonth(position.startDate));
                    self.selectPositionEndDate(nts.uk.time.formatYearMonth(position.endDate));
                };
                ScreenModel.prototype.positionProcess = function () {
                    var self = this;
                    if (self.isRadioCheck() === 1) {
                        self.dataDelete();
                    }
                    else {
                        self.dataUpdate();
                    }
                };
                ScreenModel.prototype.dataDelete = function () {
                    var self = this;
                    d.service.deletePosition(self.selectPosition()).done(function () {
                        nts.uk.ui.windows.close();
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.dataUpdate = function () {
                    var self = this;
                    var positionInfor = self.selectPosition();
                    var inputYm = $("#INP_001").val();
                    if (!nts.uk.time.parseYearMonth(inputYm).success) {
                        alert(nts.uk.time.parseYearMonth(inputYm).msg);
                        return false;
                    }
                    positionInfor.startDate = +$("#INP_001").val().replace('/', '');
                    if (positionInfor.startDate > +self.selectPositionEndDate().replace('/', '')) {
                        alert("履歴の期間が重複しています。");
                        return false;
                    }
                    else {
                        d.service.updatePosition(positionInfor).done(function () {
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
