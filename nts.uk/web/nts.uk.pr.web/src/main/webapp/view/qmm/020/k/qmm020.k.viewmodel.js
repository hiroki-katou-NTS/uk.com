var qmm020;
(function (qmm020) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.startYmHis = ko.observable(null);
                    self.enableYm = ko.observable(false);
                    self.scrType = ko.observable(nts.uk.ui.windows.getShared('scrType'));
                    self.startYM = ko.observable(nts.uk.ui.windows.getShared('startYM'));
                    self.endYM = ko.observable(nts.uk.ui.windows.getShared('endYM'));
                    self.currentItem = ko.observable(nts.uk.ui.windows.getShared('currentItem'));
                    self.selectStartYm = ko.observable(nts.uk.ui.windows.getShared('startYM'));
                    if (self.scrType() === '1') {
                        $('#K_LBL_005').parent().hide();
                        $('#K_LBL_006').hide();
                    }
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: '履歴を削除する' },
                        { value: 2, text: '履歴を修正する' }
                    ]);
                    self.isRadioCheck = ko.observable(1);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.isRadioCheck.subscribe(function (newValue) {
                        if (newValue === 2) {
                            self.enableYm(true);
                        }
                        else {
                            self.enableYm(false);
                        }
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.historyProcess = function () {
                    var self = this;
                    debugger;
                    if (parseInt(self.selectStartYm()) > parseInt($('#K_INP_001').val())) {
                        nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
                    }
                    if (self.isRadioCheck() == 1) {
                        k.service.delComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                    else {
                        self.currentItem();
                        var previousItem = nts.uk.ui.windows.getShared('previousItem');
                        var startValue = $('#K_INP_001').val().replace('/', '');
                        self.currentItem().startDate = startValue;
                        k.service.updateComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                        if (previousItem != undefined) {
                            previousItem.endDate = previousYM(startValue);
                            k.service.updateComAllot(previousItem).done(function () {
                            }).fail(function (res) {
                                alert(res);
                            });
                        }
                    }
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Error;
            (function (Error) {
                Error[Error["ER023"] = "履歴の期間が重複しています。"] = "ER023";
            })(Error || (Error = {}));
            function previousYM(sYm) {
                var preYm = 0;
                if (sYm.length == 6) {
                    var sYear = sYm.substr(0, 4);
                    var sMonth = sYm.substr(4, 2);
                    if (sMonth == "01") {
                        preYm = parseInt((parseInt(sYear) - 1).toString() + "12");
                    }
                    else {
                        preYm = parseInt(sYm) - 1;
                    }
                }
                return preYm;
            }
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmm020.k || (qmm020.k = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.k.viewmodel.js.map