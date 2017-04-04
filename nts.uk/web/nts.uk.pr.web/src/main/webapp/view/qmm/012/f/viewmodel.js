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
                    this.checked_F_002 = ko.observable(false);
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    var self = this;
                    self.roundingRules_F_001 = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.CurrentItemMaster.subscribe(function (ItemMaster) {
                        self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
                        self.checked_F_002(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
                    });
                    self.checked_F_002.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
                    });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm012.f || (qmm012.f = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=viewmodel.js.map