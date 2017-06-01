var kml001;
(function (kml001) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var servicebase = kml001.shr.servicebase;
            var vmbase = kml001.shr.vmbase;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.premiumItemList = ko.observableArray([]);
                    self.isInsert = nts.uk.ui.windows.getShared('isInsert');
                    self.textKML001_18 = nts.uk.resource.getText("KML001_18", [__viewContext.primitiveValueConstraints.PremiumName.maxLength / 2]);
                }
                /**
                 * get data on start page
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    servicebase.premiumItemSelect()
                        .done(function (data) {
                        data.forEach(function (item) {
                            self.premiumItemList.push(new vmbase.PremiumItem(item.companyID, item.id, item.attendanceID, item.name, item.displayNumber, item.useAtr));
                        });
                        self.allUse = ko.pureComputed(function () {
                            var x = 0;
                            self.premiumItemList().forEach(function (item) {
                                x += parseInt(item.useAtr().toString());
                            });
                            return x;
                        });
                        self.allUse.subscribe(function (value) {
                            if (value == 0) {
                                $("#premium-set-tbl").ntsError('set', { messageId: "Msg_66" });
                            }
                            else {
                                $("#premium-set-tbl").ntsError('clear');
                            }
                        });
                        dfd.resolve();
                    })
                        .fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                /**
                 * save data and close dialog
                 */
                ScreenModel.prototype.submitAndCloseDialog = function () {
                    var self = this;
                    var premiumItemListCommand = [];
                    ko.utils.arrayForEach(self.premiumItemList(), function (item) {
                        premiumItemListCommand.push(ko.mapping.toJS(item));
                    });
                    servicebase.premiumItemUpdate(premiumItemListCommand)
                        .done(function (res) {
                        if (self.isInsert) {
                            nts.uk.ui.windows.setShared('premiumSets', ko.mapping.toJS(premiumItemListCommand));
                        }
                        nts.uk.ui.windows.setShared('updatePremiumSeting', true);
                        nts.uk.ui.windows.close();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                /**
                 * close dialog and do nothing
                 */
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = kml001.b || (kml001.b = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.b.vm.js.map