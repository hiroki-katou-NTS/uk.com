var qmm012;
(function (qmm012) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.taxLimitListItems = ko.observableArray([]);
                    this.taxLimitGridCurrentCode = ko.observable('');
                    this.GridlistCurrentItem = ko.observable(null);
                    var self = this;
                    self.taxLimitGridColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'commuNoTaxLimitCode', width: 40 },
                        { headerText: '名称', prop: 'commuNoTaxLimitName', width: 110 },
                        { headerText: '限度額', prop: 'commuNoTaxLimitValue', width: 110 }
                    ]);
                    self.LoadData();
                    self.taxLimitGridCurrentCode.subscribe(function (newValue) {
                        var item = _.find(self.taxLimitListItems(), function (ItemModel) {
                            return ItemModel.commuNoTaxLimitCode == newValue;
                        });
                        self.GridlistCurrentItem(item);
                    });
                }
                ScreenModel.prototype.LoadData = function () {
                    var self = this;
                    k.service.getCommutelimitsByCompanyCode().done(function (CommuteNoTaxLimits) {
                        self.taxLimitListItems(CommuteNoTaxLimits);
                        var selectedCode = nts.uk.ui.windows.getShared('commuNoTaxLimitCode');
                        if (!selectedCode) {
                            if (CommuteNoTaxLimits.length) {
                                self.taxLimitGridCurrentCode(CommuteNoTaxLimits[0].commuNoTaxLimitCode);
                            }
                            else {
                                nts.uk.ui.dialog.alert("対象データがありません。");
                            }
                        }
                        else
                            self.taxLimitGridCurrentCode(selectedCode);
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res);
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
//# sourceMappingURL=qmm012.k.vm.js.map