var qmm019;
(function (qmm019) {
    var g;
    (function (g) {
        var service;
        (function (service) {
            var paths = {
                getLayoutHeadInfor: "pr/proto/layout/findlayoutwithmaxstartym",
                getAllLayoutHist: "pr/proto/layout/findalllayoutHist",
                copylayoutPath: "pr/proto/layout/createlayout"
            };
            /**
             * Get list layout master new history
             */
            function getLayoutHeadInfor() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getLayoutHeadInfor)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getLayoutHeadInfor = getLayoutHeadInfor;
            function getAllLayoutHist() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllLayoutHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllLayoutHist = getAllLayoutHist;
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
                var LayoutHeadDto = (function () {
                    function LayoutHeadDto() {
                    }
                    return LayoutHeadDto;
                }());
                model.LayoutHeadDto = LayoutHeadDto;
                // layout
                var LayoutMasterDto = (function () {
                    function LayoutMasterDto() {
                    }
                    return LayoutMasterDto;
                }());
                model.LayoutMasterDto = LayoutMasterDto;
                var LayoutHistory = (function () {
                    function LayoutHistory() {
                    }
                    return LayoutHistory;
                }());
                model.LayoutHistory = LayoutHistory;
                // layoutHistory
                var LayoutHistoryDto = (function () {
                    function LayoutHistoryDto() {
                    }
                    return LayoutHistoryDto;
                }());
                model.LayoutHistoryDto = LayoutHistoryDto;
            })(model = service.model || (service.model = {}));
        })(service = g.service || (g.service = {}));
    })(g = qmm019.g || (qmm019.g = {}));
})(qmm019 || (qmm019 = {}));
//# sourceMappingURL=service.js.map