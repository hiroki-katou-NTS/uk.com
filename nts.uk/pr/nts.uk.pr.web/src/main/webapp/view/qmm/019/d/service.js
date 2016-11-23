var qmm019;
(function (qmm019) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/layout/findlayoutwithmaxstartym"
            };
            /**
             * Get list layout master new history
             */
            function getLayoutWithMaxStartYm() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getLayoutInfor)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayoutWithMaxStartYm = getLayoutWithMaxStartYm;
        })(service = d.service || (d.service = {}));
    })(d = qmm019.d || (qmm019.d = {}));
})(qmm019 || (qmm019 = {}));
