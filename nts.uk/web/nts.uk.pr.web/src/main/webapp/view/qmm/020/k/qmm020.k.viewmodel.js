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
                    $('#F_LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#F_LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
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
                    if (self.isRadioCheck() == 1) {
                        k.service.delComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                    else {
                        alert('update');
                    }
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmm020.k || (qmm020.k = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.k.viewmodel.js.map