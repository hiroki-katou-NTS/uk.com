var qmm012;
(function (qmm012) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //start Switch Data
                    self.enable = ko.observable(true);
                    self.roundingRules = ko.observableArray([
                        { code: 'group1', name: '�x������' },
                        { code: 'group2', name: '�T������' },
                        { code: 'group3', name: '�ΑӍ���' }
                    ]);
                    self.selectedRuleCode = ko.observable('group1');
                    //endSwitch Data
                }
                ScreenModel.prototype.submitInfo = function () {
                    var self = this;
                    var groupName = self.selectedRuleCode;
                    nts.uk.ui.windows.setShared('groupName', groupName());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    //dropdownlist event
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm012.a || (qmm012.a = {}));
})(qmm012 || (qmm012 = {}));
