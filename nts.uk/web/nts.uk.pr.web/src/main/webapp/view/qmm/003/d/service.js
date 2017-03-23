var qmm003;
(function (qmm003) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getResidentalTaxList: "pr/core/residential/findallresidential",
                getRegionPrefecture: "pr/core/residential/getlistLocation",
                getResidentialTaxByResidential: "pr/core/residential/findResidentialTax/{0}/{1}",
                deleteResidential: "pr/core/residential/deleteresidential"
            };
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
            function getResidentialTaxByResiTaxCode(resiTaxCode, resiReportCode) {
                var dfd = $.Deferred();
                var objectLayout = { resiTaxCode: resiTaxCode, resiReportCode: resiReportCode };
                var _path = nts.uk.text.format(paths.getResidentialTaxByResidential, resiTaxCode, resiReportCode);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getResidentialTaxByResiTaxCode = getResidentialTaxByResiTaxCode;
            function getResidentialTaxByResidential() {
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
            service.getResidentialTaxByResidential = getResidentialTaxByResidential;
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
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = qmm003.d || (qmm003.d = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=service.js.map