var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var simplehistory;
                    (function (simplehistory) {
                        var updatehistory;
                        (function (updatehistory) {
                            var viewmodel;
                            (function (viewmodel) {
                                var ScreenModel = (function () {
                                    function ScreenModel() {
                                        var self = this;
                                        self.dialogOptions = nts.uk.ui.windows.getShared('options');
                                        self.dialogOptions.screenMode = self.dialogOptions.screenMode || simplehistory.dialogbase.ScreenMode.MODE_MASTER_HISTORY;
                                        self.actionType = ko.observable(ScreenModel.ACTION_TYPE_UPDATE);
                                        self.startYearMonth = ko.observable(self.dialogOptions.history.start);
                                        self.endYearMonth = nts.uk.time.formatYearMonth(self.dialogOptions.history.end);
                                    }
                                    ScreenModel.prototype.startPage = function () {
                                        var self = this;
                                        var dfd = $.Deferred();
                                        dfd.resolve();
                                        return dfd.promise();
                                    };
                                    ScreenModel.prototype.btnApplyClicked = function () {
                                        var self = this;
                                        var callBackData = {
                                            masterCode: self.dialogOptions.master ? self.dialogOptions.master.code : undefined,
                                            historyId: self.dialogOptions.history.uuid,
                                            startYearMonth: self.startYearMonth()
                                        };
                                        if (self.actionType() == ScreenModel.ACTION_TYPE_DELETE) {
                                            nts.uk.ui.dialog.confirm("データを削除します。\r\n よろしいですか？").ifYes(function () {
                                                self.dialogOptions.onDeleteCallBack(callBackData);
                                                nts.uk.ui.windows.close();
                                            });
                                        }
                                        else {
                                            self.dialogOptions.onUpdateCallBack(callBackData);
                                            nts.uk.ui.windows.close();
                                        }
                                    };
                                    ScreenModel.prototype.btnCancelClicked = function () {
                                        nts.uk.ui.windows.close();
                                    };
                                    ScreenModel.ACTION_TYPE_DELETE = 'DELETE';
                                    ScreenModel.ACTION_TYPE_UPDATE = 'UPDATE';
                                    return ScreenModel;
                                }());
                                viewmodel.ScreenModel = ScreenModel;
                            })(viewmodel = updatehistory.viewmodel || (updatehistory.viewmodel = {}));
                        })(updatehistory = simplehistory.updatehistory || (simplehistory.updatehistory = {}));
                    })(simplehistory = base.simplehistory || (base.simplehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map