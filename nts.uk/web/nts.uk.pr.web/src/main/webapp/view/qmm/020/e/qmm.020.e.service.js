var qmm020;
(function (qmm020) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                getAllClassificationAllotSetting: "pr/core/allot/getallclassificationallotsetting",
                getAllClassificationAllotSettingHeader: "pr/core/allot/getallclassificationallotsettingheader",
                updateClassificationAllotSettingHeader: "pr/core/allot/updateclassificationallotsettingheader",
            };
            function getAllClassificationAllotSettingHeader() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllClassificationAllotSettingHeader, { companyCode: "0001" })
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllClassificationAllotSettingHeader = getAllClassificationAllotSettingHeader;
            function updateClassificationAllotSettingHeader(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateClassificationAllotSettingHeader, data)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateClassificationAllotSettingHeader = updateClassificationAllotSettingHeader;
            function getAllClassificationAllotSetting(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllClassificationAllotSetting, { historyId: historyId })
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllClassificationAllotSetting = getAllClassificationAllotSetting;
            var ClassificationAllotSettingHeader = (function () {
                function ClassificationAllotSettingHeader(companyCode, historyId, startDateYM, endDateYM) {
                    var self = this;
                    this.companyCode = companyCode;
                    this.historyId = historyId;
                    this.startDateYM = startDateYM;
                    this.endDateYM = endDateYM;
                }
                return ClassificationAllotSettingHeader;
            }());
            service.ClassificationAllotSettingHeader = ClassificationAllotSettingHeader;
        })(service = e.service || (e.service = {}));
    })(e = qmm020.e || (qmm020.e = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm.020.e.service.js.map