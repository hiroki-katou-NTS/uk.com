var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var k;
                    (function (k) {
                        var viewmodel;
                        (function (viewmodel) {
                            /**
                             * Add simple history screen model.
                             */
                            var ScreenModel = (function () {
                                /**
                                 * Constructor.
                                 */
                                function ScreenModel() {
                                    var self = this;
                                    self.dialogOptions = nts.uk.ui.windows.getShared('options');
                                    self.demensionItemList = ko.observableArray([]);
                                    self.selectedDemension = ko.observable(undefined);
                                }
                                /**
                                 * Start page.
                                 */
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    qmm016.service.instance.loadDemensionList().done(function (res) {
                                        self.demensionItemList(res);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                /**
                                 * Create history and then dialog.
                                 */
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    var self = this;
                                    if (self.selectedDemension()) {
                                        var callBackData = {
                                            demension: self.selectedDemension()
                                        };
                                        self.dialogOptions.onSelectItem(callBackData);
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                /**
                                 * Close dialog.
                                 */
                                ScreenModel.prototype.btnCancelClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
                    })(k = qmm016.k || (qmm016.k = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
