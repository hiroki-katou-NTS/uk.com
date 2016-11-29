var qmm019;
(function (qmm019) {
    var g;
    (function (g) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/layout/findlayoutwithmaxstartym",
                copylayoutPath: "pr/proto/layout/createlayout"
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
            function createLayout(layoutMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.copylayoutPath, layoutMaster).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.createLayout = createLayout;
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
        })(service = g.service || (g.service = {}));
    })(g = qmm019.g || (qmm019.g = {}));
})(qmm019 || (qmm019 = {}));
