var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //self.listBox = new ListBox();
                    self.itemList = ko.observableArray([]);
                    self.personalWages = ko.observableArray([]);
                    self.selectedCode = ko.observable(null);
                }
                ScreenModel.prototype.buildItemList = function () {
                    var self = this;
                    //            self.itemList.removeAll();
                    _.forEach(self.personalWages(), function (personalWage) {
                        var companyCode = personalWage.companyCode;
                        if (companyCode.length == 1) {
                            companyCode = "0" + personalWage.companyCode;
                        }
                        self.itemList.push(new ItemWage(personalWage.personalWageCode, personalWage.personalWageName));
                    });
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var categoryAtr = nts.uk.ui.windows.getShared('categoryAtr');
                    h.service.getPersonalWageNames(categoryAtr).done(function (data) {
                        self.personalWages(data);
                        self.buildItemList();
                        dfd.resolve();
                    }).fail(function (res) {
                        alert(res);
                    });
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.chooseItem = function () {
                    var self = this;
                    var item = _.find(self.itemList(), function (item) { return item.wageCode === self.selectedCode(); });
                    nts.uk.ui.windows.setShared('selectedName', item.wageName);
                    nts.uk.ui.windows.setShared('selectedCode', item.wageCode);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemWage = (function () {
                function ItemWage(wageCode, wageName) {
                    this.wageCode = wageCode;
                    this.wageName = wageName;
                }
                return ItemWage;
            }());
            viewmodel.ItemWage = ItemWage;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
//# sourceMappingURL=viewmodel.js.map