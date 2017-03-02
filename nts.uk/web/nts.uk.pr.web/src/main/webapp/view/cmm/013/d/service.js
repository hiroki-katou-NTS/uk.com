var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getLayoutInfor: "/pr/proto/layout/findlayout/{0}/{1}",
                pathDeleteLayout: "/pr/proto/layout/deletedata",
                pathUpdateLayout: "/pr/proto/layout/updatedata"
            };
            /**
             * Get list layout master new history
             */
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
                //        var dto : model.LayoutMasterDto = {
                //            companyCode: layoutMaster.companyCode,
                //            stmtCode: layoutMaster.stmtCode,
                //            startYm: layoutMaster.startYm,
                //            stmtName: layoutMaster.stmtName,
                //            endYM: layoutMaster.endYM,
                //            layoutAtr: layoutMaster.layoutAtr
                //        };
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
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
