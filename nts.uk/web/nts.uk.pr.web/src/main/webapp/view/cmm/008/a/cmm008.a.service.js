var cmm008;
(function (cmm008) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var path = {
                getAllEmployment: "basic/organization/employment/findallemployments",
                createEmployment: "basic/organization/employment/createemployment",
                updateEmployment: "basic/organization/employment/updateemployment",
                deleteEmployment: "basic/organization/employment/deleteemployment/",
                getEmploymentByCode: "basic/organization/employment/findemploymentbycode/",
                getAllProcessingNo: "pr/core/paydayrocessing/getbyccd"
            };
            //find all employment data
            function getAllEmployments() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.getAllEmployment)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllEmployments = getAllEmployments;
            function getEmploymentByCode(employmentCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.getEmploymentByCode + employmentCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getEmploymentByCode = getEmploymentByCode;
            //create new employment data
            function createEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.createEmployment, employment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.createEmployment = createEmployment;
            //update employment data
            function updateEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.updateEmployment, employment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateEmployment = updateEmployment;
            //delete employment data
            function deleteEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.deleteEmployment, employment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteEmployment = deleteEmployment;
            //get all 処理日区分
            function getProcessingNo() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(path.getAllProcessingNo).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getProcessingNo = getProcessingNo;
            var model;
            (function (model) {
                var employmentDto = (function () {
                    function employmentDto() {
                    }
                    return employmentDto;
                }());
                model.employmentDto = employmentDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
