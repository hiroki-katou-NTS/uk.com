var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllEras: "ctx/basic/era/finderas",
                getEraDetail: "ctx/basic/era/find/{startDate}",
                deleteEra: "ctx/basic/era/deleteData",
                updateEra: "ctx/basic/era/updateData",
                addEra: "ctx/basic/era/addData"
            };
            function getAllEras() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllEras).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllEras = getAllEras;
            function getEraDetail() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getEraDetail)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getEraDetail = getEraDetail;
            function addData(isCreated, command) {
                var dfd = $.Deferred();
                var path = isCreated ? paths.addEra : paths.updateEra;
                nts.uk.request.ajax(path, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addData = addData;
            function deleteData(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteEra, command)
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
                var EraDto = (function () {
                    function EraDto(eraName, eraMark, startDate, endDate, fixAttribute) {
                        this.eraName = eraName;
                        this.eraMark = eraMark;
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.fixAttribute = fixAttribute;
                    }
                    return EraDto;
                }());
                model.EraDto = EraDto;
            })(model = service.model || (service.model = {}));
            var model;
            (function (model) {
                var EraDtoDelete = (function () {
                    function EraDtoDelete(startDate) {
                        this.startDate = startDate;
                    }
                    return EraDtoDelete;
                }());
                model.EraDtoDelete = EraDtoDelete;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
//# sourceMappingURL=service.js.map