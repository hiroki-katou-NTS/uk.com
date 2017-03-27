var cmm008;
(function (cmm008) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var path = {
                getAllEmployment: "basic/employment/findallemployments",
                createEmployment: "basic/employment/createemployment",
                updateEmployment: "basic/employment/updateemployment",
                deleteEmployment: "basic/employment/deleteemployment"
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
            //create new employment data
            function createEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.createEmployment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.resolve(res);
                });
                return dfd.promise();
            }
            service.createEmployment = createEmployment;
            //update employment data
            function updateEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.updateEmployment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.resolve(res);
                });
                return dfd.promise();
            }
            service.updateEmployment = updateEmployment;
            //delete employment data
            function deleteEmployment(employment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", path.deleteEmployment).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.resolve(res);
                });
                return dfd.promise();
            }
            service.deleteEmployment = deleteEmployment;
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
//# sourceMappingURL=service.js.map