var qmm019;
(function (qmm019) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "/pr/proto/layout/findlayout/{0}/{1}",
                pathDeleteLayout: "/pr/proto/layout/deletedata",
                pathUpdateLayout: "/pr/proto/layout/updatedata"
            };
            function getLayout(stmtCode, historyId) {
                var dfd = $.Deferred();
                var objectLayout = { stmtCode: stmtCode, historyId: historyId };
                var _path = nts.uk.text.format(paths.getLayoutInfor, stmtCode, historyId);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayout = getLayout;
            function deleteLayout(layoutMaster) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.pathDeleteLayout, layoutMaster);
                nts.uk.request.ajax(paths.pathDeleteLayout, layoutMaster).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteLayout = deleteLayout;
            function updateLayout(layoutMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.pathUpdateLayout, layoutMaster).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateLayout = updateLayout;
            var model;
            (function (model) {
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
