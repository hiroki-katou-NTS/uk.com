var qmm019;
(function (qmm019) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/layout/findlayout"
            };
            /**
             * Get list layout master new history
             */
            function getLayoutWithMaxStartYm() {
                var dfd = $.Deferred();
                var objectLayout = [{ 'stmtCode': '1', 'startYm': 201605 }];
                nts.uk.request.ajax(paths.getLayoutInfor + objectLayout)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayoutWithMaxStartYm = getLayoutWithMaxStartYm;
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
        })(service = e.service || (e.service = {}));
    })(e = qmm019.e || (qmm019.e = {}));
})(qmm019 || (qmm019 = {}));
