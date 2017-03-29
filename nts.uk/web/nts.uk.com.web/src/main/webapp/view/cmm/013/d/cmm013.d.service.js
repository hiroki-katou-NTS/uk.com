var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                updateHist: "basic/organization/position/updateHist",
            };
            function updateHist(listHist) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateHist, listHist)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateHist = updateHist;
            var model;
            (function (model) {
                var UpdateHandler = (function () {
                    function UpdateHandler(listHist, checkUpdate, checkDelete) {
                        this.listHist = listHist;
                        this.checkUpdate = checkUpdate;
                        this.checkDelete = checkDelete;
                    }
                    return UpdateHandler;
                }());
                model.UpdateHandler = UpdateHandler;
                var ListHistoryDto = (function () {
                    function ListHistoryDto(companyCode, startDate, endDate, historyId) {
                        this.companyCode = companyCode;
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.service.js.map