var qmm012;
(function (qmm012) {
    var k;
    (function (k) {
        var service;
        (function (service) {
            var paths = {
                getListByCompanyCode: "core/commutelimit/find/bycompanycode"
            };
            function getCommutelimitsByCompanyCode() {
                var dfd = $.Deferred();
                var _path = paths.getListByCompanyCode;
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCommutelimitsByCompanyCode = getCommutelimitsByCompanyCode;
            var model;
            (function (model) {
                // layout
                var CommuteNoTaxLimitDto = (function () {
                    function CommuteNoTaxLimitDto(companyCode, commuNoTaxLimitCode, commuNoTaxLimitName, commuNoTaxLimitValue) {
                        this.companyCode = companyCode;
                        this.commuNoTaxLimitCode = commuNoTaxLimitCode;
                        this.commuNoTaxLimitName = commuNoTaxLimitName;
                        this.commuNoTaxLimitValue = commuNoTaxLimitValue;
                    }
                    return CommuteNoTaxLimitDto;
                }());
                model.CommuteNoTaxLimitDto = CommuteNoTaxLimitDto;
            })(model = service.model || (service.model = {}));
        })(service = k.service || (k.service = {}));
    })(k = qmm012.k || (qmm012.k = {}));
})(qmm012 || (qmm012 = {}));
