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
                getAllProcessingNo: "pr/core/paydayrocessing/getbyccd",
                getCompanyInfor: "ctx/proto/company/findCompany"
            };
            function getAllEmployments() {
                return nts.uk.request.ajax("com", path.getAllEmployment);
            }
            service.getAllEmployments = getAllEmployments;
            function getEmploymentByCode(employmentCode) {
                return nts.uk.request.ajax("com", path.getEmploymentByCode + employmentCode);
            }
            service.getEmploymentByCode = getEmploymentByCode;
            function createEmployment(employment) {
                return nts.uk.request.ajax("com", path.createEmployment, employment);
            }
            service.createEmployment = createEmployment;
            function updateEmployment(employment) {
                return nts.uk.request.ajax("com", path.updateEmployment, employment);
            }
            service.updateEmployment = updateEmployment;
            function deleteEmployment(employment) {
                return nts.uk.request.ajax("com", path.deleteEmployment, employment);
            }
            service.deleteEmployment = deleteEmployment;
            function getProcessingNo() {
                return nts.uk.request.ajax(path.getAllProcessingNo);
            }
            service.getProcessingNo = getProcessingNo;
            function getCompanyInfor() {
                return nts.uk.request.ajax('com', path.getCompanyInfor);
            }
            service.getCompanyInfor = getCompanyInfor;
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
//# sourceMappingURL=cmm008.a.service.js.map