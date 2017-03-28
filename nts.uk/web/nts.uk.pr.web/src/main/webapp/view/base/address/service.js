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
                    (function (address_1) {
                        var service;
                        (function (service) {
                            var pathService = {
                                findAddressZipCode: "ctx/pr/core/address/find"
                            };
                            function findAddressZipCode(zipCode) {
                                return nts.uk.request.ajax(pathService.findAddressZipCode + '/' + zipCode);
                            }
                            service.findAddressZipCode = findAddressZipCode;
                            function findAddressZipCodeToRespone(zipCode) {
                                var dfd = $.Deferred();
                                var addressRespone;
                                addressRespone = new model.AddressRespone('0', '', null);
                                if (zipCode && zipCode != '') {
                                    service.findAddressZipCode(zipCode).done(function (data) {
                                        var datalength = 0;
                                        if (data != null) {
                                            datalength = data.length;
                                        }
                                        if (datalength == 0) {
                                            addressRespone = new model.AddressRespone('0', 'ER010', null);
                                            dfd.resolve(addressRespone);
                                        }
                                        if (datalength == 1) {
                                            addressRespone = new model.AddressRespone('1', '', data[0]);
                                            dfd.resolve(addressRespone);
                                        }
                                        if (datalength >= 2) {
                                            addressRespone = new model.AddressRespone('2', '', null);
                                            dfd.resolve(addressRespone);
                                        }
                                    }).fail(function (error) {
                                        addressRespone = new model.AddressRespone('0', error.messageId, null);
                                        dfd.resolve(addressRespone);
                                    });
                                }
                                else {
                                    addressRespone = new model.AddressRespone('0', 'ER001', null);
                                    dfd.resolve(addressRespone);
                                }
                                return dfd.promise();
                            }
                            service.findAddressZipCodeToRespone = findAddressZipCodeToRespone;
                            function findAddressZipCodeSelection(zipCode) {
                                var dfd = $.Deferred();
                                var addressRespone;
                                addressRespone = new model.AddressRespone('0', '', null);
                                nts.uk.ui.windows.setShared('zipCode', zipCode);
                                nts.uk.ui.windows.sub.modal("/view/base/address/index.xhtml", { height: 400, width: 530, title: "郵便番号" }).onClosed(function () {
                                    var zipCodeRes = nts.uk.ui.windows.getShared('zipCodeRes');
                                    if (zipCodeRes) {
                                        addressRespone = new model.AddressRespone('1', '', zipCodeRes);
                                        dfd.resolve(addressRespone);
                                    }
                                    else {
                                        addressRespone = new model.AddressRespone('0', 'ER010', null);
                                        dfd.resolve(addressRespone);
                                    }
                                });
                                return dfd.promise();
                            }
                            service.findAddressZipCodeSelection = findAddressZipCodeSelection;
                            function getinfor(address) {
                                return address.id + ' ' + address.prefecture + ' ' + address.town
                                    + ' ' + address.prefecture + ' ' + address.zipCode;
                            }
                            service.getinfor = getinfor;
                            var model;
                            (function (model) {
                                var AddressSelection = (function () {
                                    function AddressSelection() {
                                    }
                                    return AddressSelection;
                                }());
                                model.AddressSelection = AddressSelection;
                                var AddressRespone = (function () {
                                    function AddressRespone(errorCode, message, address) {
                                        this.errorCode = errorCode;
                                        this.message = message;
                                        this.address = address;
                                    }
                                    return AddressRespone;
                                }());
                                model.AddressRespone = AddressRespone;
                            })(model = service.model || (service.model = {}));
                        })(service = address_1.service || (address_1.service = {}));
                    })(address = base.address || (base.address = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map