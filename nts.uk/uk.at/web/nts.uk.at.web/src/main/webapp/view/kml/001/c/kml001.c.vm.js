var kml001;
(function (kml001) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var vmbase = kml001.shr.vmbase;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.copyDataFlag = ko.observable(true);
                    self.lastestStartDate = ko.observable(nts.uk.ui.windows.getShared('lastestStartDate'));
                    self.newStartDate = ko.observable(null);
                    self.newStartDate.subscribe(function (value) {
                        if (self.errorStartDate())
                            $("#startDateInput").ntsError('set', { messageId: "Msg_102" });
                    });
                    self.size = ko.observable(nts.uk.ui.windows.getShared('size'));
                    self.textKML001_47 = ko.observable(nts.uk.resource.getText('KML001_47', [self.lastestStartDate()]));
                }
                /**
                 * check error on new input date
                 */
                ScreenModel.prototype.errorStartDate = function () {
                    var self = this;
                    return ((self.newStartDate() == null) || vmbase.ProcessHandler.validateDateInput(self.newStartDate(), self.lastestStartDate()));
                };
                /**
                 * process parameter and close dialog
                 */
                ScreenModel.prototype.submitAndCloseDialog = function () {
                    var self = this;
                    if (self.errorStartDate())
                        $("#startDateInput").ntsError('set', { messageId: "Msg_102" });
                    else {
                        nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                        nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
                        nts.uk.ui.windows.close();
                    }
                };
                /**
                 * close dialog and do nothing
                 */
                ScreenModel.prototype.closeDialog = function () {
                    $("#startDateInput").ntsError('clear');
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = kml001.c || (kml001.c = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.c.vm.js.map