var qmm012;
(function (qmm012) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentZeroDisplaySet = ko.observable(1);
                    this.checked_NoDisplay = ko.observable(false);
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    this.noDisplayNames_Enable = ko.observable(false);
                    var self = this;
                    //F_001
                    self.roundingRules_ZeroDisplay = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.checked_NoDisplay.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
                    });
                    self.CurrentZeroDisplaySet.subscribe(function (newValue) {
                        if (newValue == 0) {
                            self.noDisplayNames_Enable(true);
                        }
                        else {
                            self.noDisplayNames_Enable(false);
                        }
                    });
                }
                ScreenModel.prototype.loadData = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    self.CurrentZeroDisplaySet(itemMaster ? itemMaster.zeroDisplaySet : 1);
                    self.checked_NoDisplay(itemMaster ? itemMaster.itemDisplayAtr == 0 ? true : false : false);
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm012.f || (qmm012.f = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=qmm012.f.vm.js.map