var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var i;
                    (function (i) {
                        var viewmodel;
                        (function (viewmodel) {
                            var commonService = nts.uk.pr.view.qmm008._0.common.service;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                                    self.listPensionAvgearn = ko.observableArray([]);
                                    self.leftShow = ko.observable(true);
                                    self.rightShow = ko.observable(true);
                                    self.leftBtnText = ko.computed(function () { if (self.leftShow())
                                        return "—"; return "+"; });
                                    self.rightBtnText = ko.computed(function () { if (self.rightShow())
                                        return "—"; return "+"; });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    commonService.getAvgEarnLevelMasterSettingList().done(function (data) {
                                        self.listAvgEarnLevelMasterSetting(data);
                                        i.service.findPensionAvgearn('a').done(function (zz) {
                                            self.listPensionAvgearn(zz);
                                        });
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = [];
                                    self.listPensionAvgearn().forEach(function (item) {
                                        data.push(ko.toJS(item));
                                    });
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    i.service.updatePensionAvgearn(this.collectData());
                                };
                                ScreenModel.prototype.leftToggle = function () {
                                    this.leftShow(!this.leftShow());
                                };
                                ScreenModel.prototype.rightToggle = function () {
                                    this.rightShow(!this.rightShow());
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
                    })(i = qmm008.i || (qmm008.i = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
