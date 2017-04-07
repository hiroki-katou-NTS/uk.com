var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getResidentialTaxList: "pr/core/residential/findallresidential",
                getRegionPrefecture: "pr/core/residential/getlistLocation",
                getResidentialDetail: "pr/core/residential/findResidentialTax/{0}",
                getResidentalTaxList: "pr/core/residential/findallByCompanyCode",
                addResidential: "pr/core/residential/addresidential",
                updateResidential: "pr/core/residential/updateresidential",
                deleteResidential: "pr/core/residential/deleteresidential"
            };
            /**
             * Get list residential companyCode !=0000.
             */
            function getResidentialTax() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getResidentialTaxList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getResidentialTax = getResidentialTax;
            /**
         * Get list  ResidentialTax companyCode == 0000.
         */
            function getResidentialTaxCCD() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getResidentalTaxList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getResidentialTaxCCD = getResidentialTaxCCD;
            /**
             * get japan location data
             */
            function getRegionPrefecture() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getRegionPrefecture)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getRegionPrefecture = getRegionPrefecture;
            /**
             * add  Residential
             */
            function addResidential(residential) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addResidential, residential).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addResidential = addResidential;
            /**
             * update ResidentialTax
             */
            function updateData(residential) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateResidential, residential)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateData = updateData;
            /**
             * delete Residential
             */
            function deleteResidential(param) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteResidential, { resiTaxCodes: param }).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteResidential = deleteResidential;
            /**
             * get ResidentialTax Detail
             */
            function getResidentialTaxDetail(resiTaxCode) {
                var dfd = $.Deferred();
                var objectLayout = { resiTaxCode: resiTaxCode };
                var _path = nts.uk.text.format(paths.getResidentialDetail, resiTaxCode);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getResidentialTaxDetail = getResidentialTaxDetail;
            var model;
            (function (model) {
                var ResidentialTaxDetailDto = (function () {
                    function ResidentialTaxDetailDto() {
                    }
                    ResidentialTaxDetailDto.prototype.contructor = function (companyCode, resiTaxCode, resiTaxAutonomy, prefectureCode, resiTaxReportCode, registeredName, companyAccountNo, companySpecifiedNo, cordinatePostalCode, cordinatePostOffice, memo) {
                        this.companyCode = companyCode;
                        this.resiTaxCode = resiTaxCode;
                        this.resiTaxAutonomy = resiTaxAutonomy;
                        this.prefectureCode = prefectureCode;
                        this.resiTaxReportCode = resiTaxReportCode;
                        this.registeredName = registeredName;
                        this.companyAccountNo = companyAccountNo;
                        this.companySpecifiedNo = companySpecifiedNo;
                        this.cordinatePostalCode = cordinatePostalCode;
                        this.cordinatePostOffice = cordinatePostOffice;
                        this.memo = memo;
                    };
                    return ResidentialTaxDetailDto;
                }());
                model.ResidentialTaxDetailDto = ResidentialTaxDetailDto;
                var RegionObject = (function () {
                    function RegionObject() {
                    }
                    RegionObject.prototype.contructor = function (regionCode, regionName, prefectures) {
                        this.regionCode = regionCode;
                        this.regionName = regionName;
                        this.prefectures = prefectures;
                    };
                    return RegionObject;
                }());
                model.RegionObject = RegionObject;
                var PrefectureObject = (function () {
                    function PrefectureObject() {
                    }
                    PrefectureObject.prototype.contructor = function (prefectureCode, prefectureName) {
                        this.prefectureCode = prefectureCode;
                        this.prefectureName = prefectureName;
                    };
                    return PrefectureObject;
                }());
                model.PrefectureObject = PrefectureObject;
                var ResidentialTaxDto = (function () {
                    function ResidentialTaxDto() {
                    }
                    ResidentialTaxDto.prototype.contructor = function (resiTaxCode, resiTaxAutonomy, prefectureCode) {
                        this.resiTaxCode = resiTaxCode;
                        this.resiTaxAutonomy = resiTaxAutonomy;
                        this.prefectureCode = prefectureCode;
                    };
                    return ResidentialTaxDto;
                }());
                model.ResidentialTaxDto = ResidentialTaxDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=qmm003.a.service.js.map