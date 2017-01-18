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
                            var servicePath = {
                                getListOfficeItem: "",
                                getAllRoundingItem: "",
                                getHealthInsuranceItemDetail: "",
                                getPensionItemDetail: ""
                            };
                            function findInsuranceOffice(key) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getListOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');
                                var data = null;
                                var OfficeItemList = [
                                    new model.finder.InsuranceOfficeItemDto('id01', 'A 事業所', 'code1', [
                                        new model.finder.InsuranceOfficeItemDto('child01', '~ 9999/12', 'chil1', []),
                                        new model.finder.InsuranceOfficeItemDto('child02', '~ 9999/12', 'chil2', [])
                                    ]),
                                    new model.finder.InsuranceOfficeItemDto('id02', 'B 事業所', 'code2', [])];
                                dfd.resolve(OfficeItemList);
                                return dfd.promise();
                            }
                            service.findInsuranceOffice = findInsuranceOffice;
                            function findAllRounding() {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllRoundingItem;
                                var data = null;
                                var roundingList = [
                                    new model.finder.RoundingItemDto('001', 'op1change'),
                                    new model.finder.RoundingItemDto('002', 'op2'),
                                    new model.finder.RoundingItemDto('003', 'op3')
                                ];
                                dfd.resolve(roundingList);
                                return dfd.promise();
                            }
                            service.findAllRounding = findAllRounding;
                            function getHealthInsuranceItemDetail(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getHealthInsuranceItemDetail;
                                if (code == "code1") {
                                    var rateItems = [
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(223, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(223, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(223, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(223, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                    ];
                                    var roundingMethods = [
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "roundingname1"), new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundingname3")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
                                    ];
                                    var data = new model.finder.HealthInsuranceRateDto(1, "companyCode", "code1", "applyRange", 1, rateItems, roundingMethods, 150000);
                                }
                                else {
                                    var rateItems = [
                                        new model.finder.HealthInsuranceRateItemDto(333, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(23323, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(2334, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(2423, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(123, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(2523, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(234, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(1523, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(2263, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                        new model.finder.HealthInsuranceRateItemDto(2634, model.PaymentType.Salary, model.HealthInsuranceType.General),
                                    ];
                                    var roundingMethods = [
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "op1"), new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "op2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "op3")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
                                    ];
                                    var data = new model.finder.HealthInsuranceRateDto(1, "companyCode", "code1", "applyRange", 2, rateItems, roundingMethods, 20000);
                                }
                                var healthInsuranceRateDetailData = data;
                                dfd.resolve(healthInsuranceRateDetailData);
                                return dfd.promise();
                            }
                            service.getHealthInsuranceItemDetail = getHealthInsuranceItemDetail;
                            function getPensionItemDetail(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getPensionItemDetail;
                                if (code == "code1") {
                                    var rateItems = [
                                        new model.finder.PensionRateItemDto(123, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(13423, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1523, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(123, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12653, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(123, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1723, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1263, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1223, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1823, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12223, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                    ];
                                    var fundRateItems = [
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                    ];
                                    var roundingMethods = [
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "roundingname1"), new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundingname3")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
                                    ];
                                    var data = new model.finder.PensionRateDto(1, "companyCode", "code1", "applyRange", 1, 1, rateItems, fundRateItems, roundingMethods, 150000, 150);
                                }
                                else {
                                    var rateItems = [
                                        new model.finder.PensionRateItemDto(234, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(13423, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1523, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(123, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12653, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(123, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1723, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1263, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1223, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(1823, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                        new model.finder.PensionRateItemDto(12223, model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                                    ];
                                    var fundRateItems = [
                                        new model.finder.FundRateItemDto(111, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(11222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(2222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(2242, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(2223, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(2522, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                        new model.finder.FundRateItemDto(222, model.GroupType.Personal, model.ChargeType.Burden, model.InsuranceGender.Male, model.PaymentType.Salary),
                                    ];
                                    var roundingMethods = [
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("rounding1", "rouname1"), new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roungname2")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundinge3")]),
                                        new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
                                    ];
                                    var data = new model.finder.PensionRateDto(1, "companyCode", "code1", "applyRange", 1, 2, rateItems, fundRateItems, roundingMethods, 200000, 1150);
                                }
                                var pensionRateDetailData = data;
                                dfd.resolve(pensionRateDetailData);
                                return dfd.promise();
                            }
                            service.getPensionItemDetail = getPensionItemDetail;
                            var model;
                            (function (model) {
                                var finder;
                                (function (finder) {
                                    var ChooseOption = (function () {
                                        function ChooseOption() {
                                        }
                                        return ChooseOption;
                                    }());
                                    finder.ChooseOption = ChooseOption;
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
                                    var PensionRateDto = (function () {
                                        function PensionRateDto(historyId, companyCode, officeCode, applyRange, autoCalculate, funInputOption, rateItems, fundRateItems, roundingMethods, maxAmount, officeRate) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.applyRange = applyRange;
                                            this.autoCalculate = autoCalculate;
                                            this.fundInputOption = funInputOption;
                                            this.rateItems = rateItems;
                                            this.fundRateItems = fundRateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                            this.officeRate = officeRate;
                                        }
                                        return PensionRateDto;
                                    }());
                                    finder.PensionRateDto = PensionRateDto;
                                    var PensionRateItemDto = (function () {
                                        function PensionRateItemDto(chargeRate, groupType, payType, genderType) {
                                            this.chargeRate = chargeRate;
                                            this.groupType = groupType;
                                            this.payType = payType;
                                            this.genderType = genderType;
                                        }
                                        return PensionRateItemDto;
                                    }());
                                    finder.PensionRateItemDto = PensionRateItemDto;
                                    var FundRateItemDto = (function () {
                                        function FundRateItemDto(chargeRate, groupType, chargeType, genderType, payType) {
                                            this.chargeRate = chargeRate;
                                            this.groupType = groupType;
                                            this.chargeType = chargeType;
                                            this.genderType = genderType;
                                            this.payType = payType;
                                        }
                                        return FundRateItemDto;
                                    }());
                                    finder.FundRateItemDto = FundRateItemDto;
                                    var HealthInsuranceRateDto = (function () {
                                        function HealthInsuranceRateDto(historyId, companyCode, officeCode, applyRange, autoCalculate, rateItems, roundingMethods, maxAmount) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.applyRange = applyRange;
                                            this.autoCalculate = autoCalculate;
                                            this.rateItems = rateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                        }
                                        return HealthInsuranceRateDto;
                                    }());
                                    finder.HealthInsuranceRateDto = HealthInsuranceRateDto;
                                    var HealthInsuranceRateItemDto = (function () {
                                        function HealthInsuranceRateItemDto(chargeRate, payType, healthInsuranceType) {
                                            this.chargeRate = chargeRate;
                                            this.payType = payType;
                                            this.healthInsuranceType = healthInsuranceType;
                                        }
                                        return HealthInsuranceRateItemDto;
                                    }());
                                    finder.HealthInsuranceRateItemDto = HealthInsuranceRateItemDto;
                                    var chargeRateItemDto = (function () {
                                        function chargeRateItemDto(companyRate, personalRate) {
                                            this.companyRate = companyRate;
                                            this.personalRate = personalRate;
                                        }
                                        return chargeRateItemDto;
                                    }());
                                    finder.chargeRateItemDto = chargeRateItemDto;
                                    var RoundingDto = (function () {
                                        function RoundingDto(payType, roundAtrs) {
                                            this.payType = payType;
                                            this.roundAtrs = roundAtrs;
                                        }
                                        return RoundingDto;
                                    }());
                                    finder.RoundingDto = RoundingDto;
                                    var HealthInsuranceAvgearnDto = (function () {
                                        function HealthInsuranceAvgearnDto() {
                                        }
                                        return HealthInsuranceAvgearnDto;
                                    }());
                                    finder.HealthInsuranceAvgearnDto = HealthInsuranceAvgearnDto;
                                    var RoundingItemDto = (function () {
                                        function RoundingItemDto(code, name) {
                                            this.code = code;
                                            this.name = name;
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
                                (function (ChargeType) {
                                    ChargeType[ChargeType["Burden"] = 0] = "Burden";
                                    ChargeType[ChargeType["Exemption"] = 1] = "Exemption";
                                })(model.ChargeType || (model.ChargeType = {}));
                                var ChargeType = model.ChargeType;
                                (function (GroupType) {
                                    GroupType[GroupType["Personal"] = 0] = "Personal";
                                    GroupType[GroupType["Company"] = 1] = "Company";
                                })(model.GroupType || (model.GroupType = {}));
                                var GroupType = model.GroupType;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
