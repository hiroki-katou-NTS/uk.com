var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findall",
                                findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
                                addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
                                updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
                                deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
                                findAllSocialInsuranceOffice: "pr/insurance/social/findall/detail"
                            };
                            //Function connection service FindAll Labor Insurance Office
                            function findAllLaborInsuranceOffice() {
                                //call service server 
                                return nts.uk.request.ajax(paths.findAllLaborInsuranceOffice);
                            }
                            service.findAllLaborInsuranceOffice = findAllLaborInsuranceOffice;
                            //Function connection service FindAll Social Insurance Office Service
                            function findAllSocialInsuranceOffice() {
                                //set respone
                                return nts.uk.request.ajax(paths.findAllSocialInsuranceOffice);
                            }
                            service.findAllSocialInsuranceOffice = findAllSocialInsuranceOffice;
                            //Function connection service findLaborInsuranceOffice By Code
                            function findLaborInsuranceOffice(officeCode) {
                                return nts.uk.request.ajax(paths.findLaborInsuranceOffice + "/" + officeCode);
                            }
                            service.findLaborInsuranceOffice = findLaborInsuranceOffice;
                            //Function connection service add LaborInsuranceOffice
                            function addLaborInsuranceOffice(laborInsuranceOfficeDto) {
                                //set up data request
                                var data = { laborInsuranceOfficeDto: laborInsuranceOfficeDto };
                                //call service server
                                return nts.uk.request.ajax(paths.addLaborInsuranceOffice, data);
                            }
                            service.addLaborInsuranceOffice = addLaborInsuranceOffice;
                            //Function connection service update LaborInsuranceOffice
                            function updateLaborInsuranceOffice(laborInsuranceOfficeDto) {
                                //set up data request
                                var data = { laborInsuranceOfficeDto: laborInsuranceOfficeDto };
                                //call service server
                                return nts.uk.request.ajax(paths.updateLaborInsuranceOffice, data);
                            }
                            service.updateLaborInsuranceOffice = updateLaborInsuranceOffice;
                            //Function connection service delete LaborInsuranceOffice  
                            function deleteLaborInsuranceOffice(laborInsuranceOfficeDeleteDto) {
                                //set up data request
                                var data = { laborInsuranceOfficeDeleteDto: laborInsuranceOfficeDeleteDto };
                                //call service server
                                return nts.uk.request.ajax(paths.deleteLaborInsuranceOffice, data);
                            }
                            service.deleteLaborInsuranceOffice = deleteLaborInsuranceOffice;
                            /*Model namespace. LaborInsuranceOfficeDto*/
                            var model;
                            (function (model) {
                                var LaborInsuranceOfficeDto = (function () {
                                    function LaborInsuranceOfficeDto() {
                                    }
                                    return LaborInsuranceOfficeDto;
                                }());
                                model.LaborInsuranceOfficeDto = LaborInsuranceOfficeDto;
                                var LaborInsuranceOfficeFindOutDto = (function () {
                                    function LaborInsuranceOfficeFindOutDto() {
                                    }
                                    return LaborInsuranceOfficeFindOutDto;
                                }());
                                model.LaborInsuranceOfficeFindOutDto = LaborInsuranceOfficeFindOutDto;
                                (function (TypeActionLaborInsuranceOffice) {
                                    TypeActionLaborInsuranceOffice[TypeActionLaborInsuranceOffice["add"] = 1] = "add";
                                    TypeActionLaborInsuranceOffice[TypeActionLaborInsuranceOffice["update"] = 2] = "update";
                                })(model.TypeActionLaborInsuranceOffice || (model.TypeActionLaborInsuranceOffice = {}));
                                var TypeActionLaborInsuranceOffice = model.TypeActionLaborInsuranceOffice;
                                var LaborInsuranceOfficeDeleteDto = (function () {
                                    function LaborInsuranceOfficeDeleteDto() {
                                    }
                                    return LaborInsuranceOfficeDeleteDto;
                                }());
                                model.LaborInsuranceOfficeDeleteDto = LaborInsuranceOfficeDeleteDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm010.a || (qmm010.a = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
