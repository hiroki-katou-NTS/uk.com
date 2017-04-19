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
                        var newhistory;
                        (function (newhistory) {
                            var viewmodel;
                            (function (viewmodel) {
                                var ScreenModel = (function () {
                                    function ScreenModel() {
                                        var self = this;
                                        self.dialogOptions = nts.uk.ui.windows.getShared('options');
                                        self.dialogOptions.screenMode = self.dialogOptions.screenMode || simplehistory.dialogbase.ScreenMode.MODE_MASTER_HISTORY;
                                        if (self.dialogOptions.lastest) {
                                            self.createType = ko.observable(ScreenModel.CREATE_TYPE_COPY_LATEST);
                                        }
                                        else {
                                            self.createType = ko.observable(ScreenModel.CREATE_TYPE_INIT);
                                        }
                                        if (self.dialogOptions.lastest) {
                                            self.startYearMonth = ko.observable(self.dialogOptions.lastest.start);
                                            self.lastYearMonth = nts.uk.time.formatYearMonth(self.dialogOptions.lastest.start);
                                        }
                                        else {
                                            self.startYearMonth = ko.observable(parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM')));
                                        }
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
                                            startYearMonth: self.startYearMonth()
                                        };
                                        if (self.createType() == ScreenModel.CREATE_TYPE_COPY_LATEST) {
                                            self.dialogOptions.onCopyCallBack(callBackData)
                                                .done(function () { return nts.uk.ui.windows.close(); })
                                                .fail(function (res) {
                                                if (res.messageId == 'ER010') {
                                                    $('#startYearMonth').ntsError('set', '対象データがありません。');
                                                }
                                            });
                                        }
                                        else {
                                            self.dialogOptions.onCreateCallBack(callBackData)
                                                .done(function () { return nts.uk.ui.windows.close(); })
                                                .fail(function (res) {
                                                if (res.messageId == 'ER010') {
                                                    $('#startYearMonth').ntsError('set', '対象データがありません。');
                                                }
                                            });
                                        }
                                    };
                                    ScreenModel.prototype.btnCancelClicked = function () {
                                        nts.uk.ui.windows.close();
                                    };
                                    ScreenModel.CREATE_TYPE_COPY_LATEST = 'COPY';
                                    ScreenModel.CREATE_TYPE_INIT = 'INIT';
                                    return ScreenModel;
                                }());
                                viewmodel.ScreenModel = ScreenModel;
                            })(viewmodel = newhistory.viewmodel || (newhistory.viewmodel = {}));
                        })(newhistory = simplehistory.newhistory || (simplehistory.newhistory = {}));
                    })(simplehistory = base.simplehistory || (base.simplehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
