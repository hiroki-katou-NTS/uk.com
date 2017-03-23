var qmm019;
(function (qmm019) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "pr/proto/layout/findlayoutwithmaxstartym",
                createlayouthistory: "pr/proto/layout/createlayouthistory"
            };
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
            function createLayoutHistory(layoutMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.createlayouthistory, layoutMaster).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.createLayoutHistory = createLayoutHistory;
            var model;
            (function (model) {
                var LayoutMasterDto = (function () {
                    function LayoutMasterDto() {
                    }
                    return LayoutMasterDto;
                }());
                model.LayoutMasterDto = LayoutMasterDto;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = qmm019.d || (qmm019.d = {}));
})(qmm019 || (qmm019 = {}));
//# sourceMappingURL=service.js.map