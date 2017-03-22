var qmm023;
(function (qmm023) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getListByCompanyCode: "core/commutelimit/find/bycompanycode",
                insertData: "core/commutelimit/insert",
                updateData: "core/commutelimit/update",
                deleteData: "core/commutelimit/delete"
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
            function insertUpdateData(isUpdate, commuteNoTaxLimitCommand) {
                var dfd = $.Deferred();
                var _path = isUpdate ? paths.updateData : paths.insertData;
                var command = ko.toJS(commuteNoTaxLimitCommand);
                nts.uk.request.ajax(_path, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.insertUpdateData = insertUpdateData;
            function deleteData(deleteCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteData, ko.toJS(deleteCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteData = deleteData;
            var model;
            (function (model) {
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
        })(service = a.service || (a.service = {}));
    })(a = qmm023.a || (qmm023.a = {}));
})(qmm023 || (qmm023 = {}));
//# sourceMappingURL=qmm023.b.service.js.map