var qmm020;
(function (qmm020) {
    var m;
    (function (m) {
        var service;
        (function (service) {
            var paths = {
                getLayoutHistory: "pr/core/allot/findallotlayouthistory/{0}",
                getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}"
            };
            function getAllAllotLayoutHist(baseYm) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getLayoutHistory, baseYm);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllAllotLayoutHist = getAllAllotLayoutHist;
            function getAllotLayoutName(stmtCode) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getLayoutName, stmtCode);
                var options = {
                    dataType: 'text',
                    contentType: 'text/plain'
                };
                nts.uk.request.ajax(_path, undefined, options)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllotLayoutName = getAllotLayoutName;
            var model;
            (function (model) {
                var LayoutHistoryDto = (function () {
                    function LayoutHistoryDto() {
                    }
                    return LayoutHistoryDto;
                }());
                model.LayoutHistoryDto = LayoutHistoryDto;
            })(model = service.model || (service.model = {}));
        })(service = m.service || (m.service = {}));
    })(m = qmm020.m || (qmm020.m = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.m.service.js.map