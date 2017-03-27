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
                            function getinfor(address) {
                                return address.id + address.prefecture + address.town + address.prefecture + address.zipCode;
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
                            })(model = service.model || (service.model = {}));
                        })(service = address_1.service || (address_1.service = {}));
                    })(address = base.address || (base.address = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map