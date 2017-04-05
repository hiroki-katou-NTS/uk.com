var qmm020;
(function (qmm020) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            //duong dan   
            var paths = {
                getAllotCompanySettingList: "pr/core/allot/findallcompanyallot"
            };
            /**
             * Get list allot company
             */
            function getAllotCompanyList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllotCompanySettingList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllotCompanyList = getAllotCompanyList;
            /**
             *
             *
             */
            var model;
            (function (model) {
                var CompanyAllotSettingDto = (function () {
                    function CompanyAllotSettingDto() {
                    }
                    return CompanyAllotSettingDto;
                }());
                model.CompanyAllotSettingDto = CompanyAllotSettingDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm020.a || (qmm020.a = {}));
})(qmm020 || (qmm020 = {}));
