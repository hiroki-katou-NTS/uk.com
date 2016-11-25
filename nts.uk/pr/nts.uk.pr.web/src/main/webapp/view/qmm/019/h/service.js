var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/layout/findalllayout"
            };
            function getAllLayout() {
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
            service.getAllLayout = getAllLayout;
        })(service = h.service || (h.service = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
