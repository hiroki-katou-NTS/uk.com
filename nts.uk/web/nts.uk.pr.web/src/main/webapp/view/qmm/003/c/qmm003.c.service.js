var qmm003;
(function (qmm003) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                getResidentalTaxList: "pr/core/residential/findallresidential",
                getResidentialDetail: "pr/core/residential/findResidentialTax/{0}",
                getRegionPrefecture: "pr/core/residential/getlistLocation"
            };
            /**
             * Get list payment date processing.
             */
            function getResidentialTax() {
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
            service.getResidentialTax = getResidentialTax;
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
                var ResidentialTax = (function () {
                    function ResidentialTax() {
                    }
                    ResidentialTax.prototype.contructor = function (companyCode, resiTaxCode, resiTaxAutonomy, prefectureCode, resiTaxReportCode, registeredName, companyAccountNo, companySpecifiedNo, cordinatePostalCode, cordinatePostOffice, memo) {
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
                    return ResidentialTax;
                }());
                model.ResidentialTax = ResidentialTax;
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
        })(service = c.service || (c.service = {}));
    })(c = qmm003.c || (qmm003.c = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=qmm003.c.service.js.map