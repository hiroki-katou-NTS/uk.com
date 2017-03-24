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
            function addData(company) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addCompany, company).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addData = addData;
            function updateData(company) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateCompany, company)
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
                    function CompanyDto(companyCode, companyName, companyNameGlobal, address1, address2, addressKana1, addressKana2, companyNameAbb, companyNameKana, corporateMyNumber, depWorkPlaceSet, displayAttribute, // cot thu 3
                        faxNo, postal, presidentName, presidentJobTitle, telephoneNo, termBeginMon, useGrSet, useKtSet, useQySet, useJjSet, useAcSet, useGwSet, useHcSet, useLcSet, useBiSet, useRs01Set, useRs02Set, useRs03Set, useRs04Set, useRs05Set, useRs06Set, useRs07Set, useRs08Set, useRs09Set, useRs10Set) {
                        this.companyCode = companyCode;
                        this.companyName = companyName;
                        this.companyNameGlobal = companyNameGlobal;
                        this.companyNameAbb = companyNameAbb;
                        this.companyNameKana = companyNameKana;
                        this.address1 = (address1);
                        this.address2 = (address2);
                        this.addressKana1 = (addressKana1);
                        this.addressKana2 = (addressKana2);
                        this.use_Gr_Set = useGrSet;
                        this.use_Kt_Set = useKtSet;
                        this.use_Qy_Set = useQySet;
                        this.use_Jj_Set = useJjSet;
                        this.use_Ac_Set = useAcSet;
                        this.use_Gw_Set = useGwSet;
                        this.use_Hc_Set = useHcSet;
                        this.use_Lc_Set = useLcSet;
                        this.use_Bi_Set = useBiSet;
                        this.use_Rs01_Set = useRs01Set;
                        this.use_Rs02_Set = useRs02Set;
                        this.use_Rs03_Set = useRs03Set;
                        this.use_Rs04_Set = useRs04Set;
                        this.use_Rs05_Set = useRs05Set;
                        this.use_Rs06_Set = useRs06Set;
                        this.use_Rs07_Set = useRs07Set;
                        this.use_Rs08_Set = useRs08Set;
                        this.use_Rs09_Set = useRs09Set;
                        this.use_Rs10_Set = useRs10Set;
                    }
                    return CompanyDto;
                }());
                model.CompanyDto = CompanyDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = cmm001.a || (cmm001.a = {}));
})(cmm001 || (cmm001 = {}));
//# sourceMappingURL=service.js.map