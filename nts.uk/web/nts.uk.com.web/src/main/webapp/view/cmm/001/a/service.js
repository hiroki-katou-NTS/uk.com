var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var cmm001;
                (function (cmm001) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getAllCompanys: "ctx/proto/company/findallcompany",
                                getCompanyDetail: "ctx/proto/company/find/{companyCode}",
                                deleteCompany: "ctx/proto/company/deletedata",
                                updateCompany: "ctx/proto/company/updatedata",
                                addCompany: "ctx/proto/company/adddata"
                            };
                            /**
                             * get list company
                             */
                            function getAllCompanys() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getAllCompanys)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getAllCompanys = getAllCompanys;
                            /**
                             * get a company
                             */
                            function getCompanyDetail() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getCompanyDetail)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getCompanyDetail = getCompanyDetail;
                            function addData() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.addCompany)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.addData = addData;
                            function updateData() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.updateCompany)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateData = updateData;
                            var model;
                            (function (model) {
                                // company
                                var CompanyDto = (function () {
                                    function CompanyDto() {
                                    }
                                    return CompanyDto;
                                }());
                                model.CompanyDto = CompanyDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = cmm001.a || (cmm001.a = {}));
                })(cmm001 = view.cmm001 || (view.cmm001 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
