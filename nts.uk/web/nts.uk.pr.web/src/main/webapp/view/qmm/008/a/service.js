var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {};
                            var model;
                            (function (model) {
                                var finder;
                                (function (finder) {
                                    var InsuranceOfficeItemDto = (function () {
                                        function InsuranceOfficeItemDto(id, name, code, childs) {
                                            this.id = id;
                                            this.name = name;
                                            this.code = code;
                                            this.childs = childs;
                                            this.codeName = code + "\u00A0" + "\u00A0" + "\u00A0" + name;
                                        }
                                        return InsuranceOfficeItemDto;
                                    }());
                                    finder.InsuranceOfficeItemDto = InsuranceOfficeItemDto;
                                    var HealthInsuranceRateDto = (function () {
                                        function HealthInsuranceRateDto() {
                                        }
                                        return HealthInsuranceRateDto;
                                    }());
                                    finder.HealthInsuranceRateDto = HealthInsuranceRateDto;
                                    var InsuranceRateItemDto = (function () {
                                        function InsuranceRateItemDto() {
                                        }
                                        return InsuranceRateItemDto;
                                    }());
                                    finder.InsuranceRateItemDto = InsuranceRateItemDto;
                                    var HealthInsuranceRoundingDto = (function () {
                                        function HealthInsuranceRoundingDto() {
                                        }
                                        return HealthInsuranceRoundingDto;
                                    }());
                                    finder.HealthInsuranceRoundingDto = HealthInsuranceRoundingDto;
                                    var HealthInsuranceAvgearnDto = (function () {
                                        function HealthInsuranceAvgearnDto() {
                                        }
                                        return HealthInsuranceAvgearnDto;
                                    }());
                                    finder.HealthInsuranceAvgearnDto = HealthInsuranceAvgearnDto;
                                    var RoundingItemDto = (function () {
                                        function RoundingItemDto() {
                                        }
                                        return RoundingItemDto;
                                    }());
                                    finder.RoundingItemDto = RoundingItemDto;
                                    var HealthInsuranceAvgearnValueDto = (function () {
                                        function HealthInsuranceAvgearnValueDto() {
                                        }
                                        return HealthInsuranceAvgearnValueDto;
                                    }());
                                    finder.HealthInsuranceAvgearnValueDto = HealthInsuranceAvgearnValueDto;
                                })(finder = model.finder || (model.finder = {}));
                                (function (PaymentType) {
                                    PaymentType[PaymentType["Salary"] = 0] = "Salary";
                                    PaymentType[PaymentType["Bonus"] = 1] = "Bonus";
                                })(model.PaymentType || (model.PaymentType = {}));
                                var PaymentType = model.PaymentType;
                                (function (HealthInsuranceType) {
                                    HealthInsuranceType[HealthInsuranceType["General"] = 0] = "General";
                                    HealthInsuranceType[HealthInsuranceType["Nursing"] = 1] = "Nursing";
                                    HealthInsuranceType[HealthInsuranceType["Basic"] = 2] = "Basic";
                                    HealthInsuranceType[HealthInsuranceType["Special"] = 3] = "Special";
                                })(model.HealthInsuranceType || (model.HealthInsuranceType = {}));
                                var HealthInsuranceType = model.HealthInsuranceType;
                                (function (InsuranceGender) {
                                    InsuranceGender[InsuranceGender["Male"] = 0] = "Male";
                                    InsuranceGender[InsuranceGender["Female"] = 1] = "Female";
                                    InsuranceGender[InsuranceGender["Unknow"] = 2] = "Unknow";
                                })(model.InsuranceGender || (model.InsuranceGender = {}));
                                var InsuranceGender = model.InsuranceGender;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
