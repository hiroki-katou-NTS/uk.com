var qmm020;
(function (qmm020) {
    var k;
    (function (k) {
        var service;
        (function (service) {
            var paths = {
                delAllotCompanySetting: "pr/core/allot/delete"
            };
            function delComAllot(delAllotCompanyCmd) {
                var dfd = $.Deferred();
                var command = {};
                command.payStmtCode = delAllotCompanyCmd.paymentDetailCode;
                command.bonusStmtCode = delAllotCompanyCmd.bonusDetailCode;
                command.startDate = delAllotCompanyCmd.startDate;
                command.endDate = delAllotCompanyCmd.endDate;
                command.historyId = delAllotCompanyCmd.historyId;
                debugger;
                nts.uk.request.ajax(paths.delAllotCompanySetting, command)
                    .done(function (res) {
                    debugger;
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.delComAllot = delComAllot;
        })(service = k.service || (k.service = {}));
    })(k = qmm020.k || (qmm020.k = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.k.service.js.map