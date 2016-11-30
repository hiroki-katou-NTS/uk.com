var qmmm019;
(function (qmmm019) {
    var i;
    (function (i) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.selectedBox = ko.observable("1");
                }
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedCode', self.selectedBox());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.setShared('selectedCode', undefined);
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
    })(i = qmmm019.i || (qmmm019.i = {}));
})(qmmm019 || (qmmm019 = {}));
