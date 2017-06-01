var qmm020;
(function (qmm020) {
    var j;
    (function (j) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.displayMode = ko.observable(1);
                    this.startYm = ko.observable(197001);
                    this.endDate = ko.observable(undefined);
                    this.baseDate = ko.observable(undefined);
                    this.startDate = ko.observable(undefined);
                    this.selectedMode = ko.observable(1);
                    this.txtCopyHistory = ko.observable(undefined);
                    var self = this;
                    // display mode
                    self.displayMode(nts.uk.ui.windows.getShared('J_MODE') || 1);
                    self.startYm(nts.uk.ui.windows.getShared("J_BASEDATE") || 197001);
                    // resize window
                    self.displayMode.subscribe(function (v) {
                        nts.uk.ui.windows.getSelf().setWidth(490);
                        if (v == 2) {
                            nts.uk.ui.windows.getSelf().setHeight(420);
                        }
                        else {
                            nts.uk.ui.windows.getSelf().setHeight(300);
                        }
                    });
                    // trigger resize window
                    self.displayMode.valueHasMutated();
                    // start first method
                    self.start();
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    self.txtCopyHistory("最新の履歴（" + nts.uk.time.formatYearMonth(self.startYm()) + "）から引き継ぐ");
                };
                ScreenModel.prototype.createHistoryDocument = function () {
                    var self = this;
                    self.closeDialog();
                };
                ScreenModel.prototype.closeDialog = function () { nts.uk.ui.windows.close(); };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Error;
            (function (Error) {
                Error[Error["ER023"] = "履歴の期間が重複しています。"] = "ER023";
            })(Error || (Error = {}));
        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
    })(j = qmm020.j || (qmm020.j = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.j.viewmodel.js.map