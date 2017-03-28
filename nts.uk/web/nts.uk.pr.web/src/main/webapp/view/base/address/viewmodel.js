var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var address;
                    (function (address) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.addressList = ko.observableArray([]);
                                    self.zipCode = ko.observable('');
                                    var strZipCode = nts.uk.ui.windows.getShared('zipCode');
                                    address.service.findAddressZipCode(strZipCode).done(function (data) {
                                        self.addressList(data);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                }
                                ScreenModel.prototype.chooseZipCode = function () {
                                    var self = this;
                                    for (var _i = 0, _a = self.addressList(); _i < _a.length; _i++) {
                                        var itemZipCode = _a[_i];
                                        if (itemZipCode.id == self.zipCode()) {
                                            nts.uk.ui.windows.setShared('zipCodeRes', itemZipCode);
                                            break;
                                        }
                                    }
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = address.viewmodel || (address.viewmodel = {}));
                    })(address = base.address || (base.address = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
