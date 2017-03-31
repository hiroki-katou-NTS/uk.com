var qmm012;
(function (qmm012) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.GridlistItems_K_001 = ko.observableArray([]);
                    this.GridlistCurrentCode_K_001 = ko.observable('');
                    this.GridlistCurrentItem = ko.observable(null);
                    var self = this;
                    self.GridColumns_K_001 = ko.observableArray([
                        { headerText: 'コード', prop: 'commuNoTaxLimitCode', width: 40 },
                        { headerText: '名称', prop: 'commuNoTaxLimitName', width: 110 },
                        { headerText: '限度額', prop: 'commuNoTaxLimitValue', width: 110 }
                    ]);
                    self.LoadData();
                    self.GridlistCurrentCode_K_001.subscribe(function (newValue) {
                        var item = _.find(self.GridlistItems_K_001(), function (ItemModel) {
                            return ItemModel.commuNoTaxLimitCode == newValue;
                        });
                        self.GridlistCurrentItem(item);
                    });
                }
                ScreenModel.prototype.LoadData = function () {
                    var self = this;
                    k.service.getCommutelimitsByCompanyCode().done(function (CommuteNoTaxLimits) {
                        self.GridlistItems_K_001(CommuteNoTaxLimits);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.SubmitDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('CommuteNoTaxLimitDto', self.GridlistCurrentItem());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.CloseDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmm012.k || (qmm012.k = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=viewmodel.js.map