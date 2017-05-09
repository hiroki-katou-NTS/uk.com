var qpp021;
(function (qpp021) {
    var f;
    (function (f) {
        var service;
        (function (service) {
            var paths = {
                save: "ctx/pr/report/payment/contact/personalsetting/save"
            };
            /**
             * Save
             */
            function save(data) {
                return nts.uk.request.ajax(paths.save, data);
            }
            service.save = save;
        })(service = f.service || (f.service = {}));
    })(f = qpp021.f || (qpp021.f = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.f.service.js.map