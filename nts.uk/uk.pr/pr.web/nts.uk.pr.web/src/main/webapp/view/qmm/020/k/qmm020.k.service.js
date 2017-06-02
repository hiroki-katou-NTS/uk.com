var qmm020;
(function (qmm020) {
    var k;
    (function (k) {
        var service;
        (function (service) {
            var paths = {
                delAllotCompanySetting: "pr/core/allot/delete",
                updateAllotCompanySetting: "pr/core/allot/update"
            };
            //delete
            function delComAllot(delAllotCompanyCmd) {
                var dfd = $.Deferred();
                var command = {};
                command.payStmtCode = delAllotCompanyCmd.paymentDetailCode;
                command.bonusStmtCode = delAllotCompanyCmd.bonusDetailCode;
                command.startDate = delAllotCompanyCmd.startDate;
                command.endDate = delAllotCompanyCmd.endDate;
                command.historyId = delAllotCompanyCmd.historyId;
                nts.uk.request.ajax(paths.delAllotCompanySetting, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.delComAllot = delComAllot;
            //Update ALLOT company  
            function updateComAllot(updateAllotCompanyCommand) {
                var dfd = $.Deferred();
                var command = {};
                debugger;
                command.payStmtCode = updateAllotCompanyCommand.paymentDetailCode;
                command.bonusStmtCode = updateAllotCompanyCommand.bonusDetailCode;
                command.startDate = updateAllotCompanyCommand.startDate;
                command.endDate = updateAllotCompanyCommand.endDate;
                command.historyId = updateAllotCompanyCommand.historyId;
                nts.uk.request.ajax(paths.updateAllotCompanySetting, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateComAllot = updateComAllot;
        })(service = k.service || (k.service = {}));
    })(k = qmm020.k || (qmm020.k = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.k.service.js.map