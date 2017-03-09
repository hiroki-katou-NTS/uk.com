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
                                        self.createType = ko.observable(ScreenModel.CREATE_TYPE_COPY_LATEST);
                                        self.startYearMonth = ko.observable(nts.uk.time.formatYearMonth(self.dialogOptions.lastest.start));
                                        self.lastYearMonth = nts.uk.time.formatYearMonth(self.dialogOptions.lastest.start);
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
                                            masterCode: self.dialogOptions.master.code,
                                            startYearMonth: nts.uk.time.parseYearMonth(self.startYearMonth()).toValue()
                                        };
                                        if (self.createType() == ScreenModel.CREATE_TYPE_COPY_LATEST) {
                                            self.dialogOptions.onCopyCallBack(callBackData);
                                        }
                                        else {
                                            self.dialogOptions.onCreateCallBack(callBackData);
                                        }
                                        nts.uk.ui.windows.close();
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
//# sourceMappingURL=viewmodel.js.map