var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllLayout: "/ctx/pr/proto/layout/findalllayout"
            };
            /**
             * Get list payment date processing.
             */
            function getAllLayout(id) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllLayout + "/" + id)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllLayout = getAllLayout;
            /**
               * Model namespace.
            */
            var model;
            (function (model) {
                // layout
                var LayoutMasterDto = (function () {
                    function LayoutMasterDto() {
                    }
                    return LayoutMasterDto;
                }());
                model.LayoutMasterDto = LayoutMasterDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
