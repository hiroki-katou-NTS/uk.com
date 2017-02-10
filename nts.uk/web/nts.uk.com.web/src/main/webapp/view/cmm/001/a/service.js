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
            function addData(layoutMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addCompany, layoutMaster).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addData = addData;
            //        export function addData(): JQueryPromise<companyDto: model.CompanyDto>{
            //           let dfd = $.Deferred<Array<any>>();
            //            nts.uk.request.ajax(paths.addCompany,company)
            //                    .done(function(res: Array<any>){
            //                        dfd.resolve(res);
            //                    })
            //                    .fail(function(res){
            //                        dfd.reject(res);
            //                    })
            //            return dfd.promise();
            //        }
            //    
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
})(cmm001 || (cmm001 = {}));
