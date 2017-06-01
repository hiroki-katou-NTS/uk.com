var qpp014;
(function (qpp014) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                addBankTransfer: "pr/proto/payment/banktransfer/add"
            };
            /**
             * insert data into DB BANK_TRANSFER
             */
            function addBankTransfer(command) {
                return nts.uk.request.ajax(paths.addBankTransfer, command);
            }
            service.addBankTransfer = addBankTransfer;
        })(service = e.service || (e.service = {}));
    })(e = qpp014.e || (qpp014.e = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.e.service.js.map