var cmm008;
(function (cmm008) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var path = {
                getAllEmployment: "pr/proto/basic/employment/findallemployments",
                createEmployment: "pr/proto/basic/employment/createemployment",
                updateEmployment: "pr/proto/basic/employment/updateemployment",
                deleteEmployment: "pr/proto/basic/employment/deleteemployment"
            };
            function getAllEmployments() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(path.getAllEmployment)
                    .done(function (res) {
                });
                return dfd.promise();
            }
            service.getAllEmployments = getAllEmployments;
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
