var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getResidentialTaxList: "pr/core/residential/findallresidential",
                getRegionPrefecture: "pr/core/residential/getlistLocation",
                addResidential: "pr/core/residential/addresidential",
                updateResidential: "pr/core/residential/updateresidential",
                deleteResidential: "pr/core/residential/deleteresidential"
            };
            /**
             * Get list residential date processing.
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
        })(service = a.service || (a.service = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
