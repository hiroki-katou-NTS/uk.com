module nts.uk.pr.view.qmm008.a {

    export module service {

        // Service paths.
        var servicePath = {
            getAllOfficeItem: "pr/insurance/social/findall",
            getAllHistoryOfOffice: "pr/insurance/social/history",
            //get all heal insurance for get history
            getAllHealthOfficeAndHistory: "ctx/pr/core/insurance/social/healthrate/findAllHistory",
            getAllPensionOfficeAndHistory: "ctx/pr/core/insurance/social/pensionrate/findAllHistory",
            //get health and pension data 
            getHealthInsuranceItemDetail: "ctx/pr/core/insurance/social/healthrate/find",
            getPensionItemDetail: "ctx/pr/core/insurance/social/pensionrate/find",
            //register+ update health
            registerHealthRate: "ctx/pr/core/insurance/social/healthrate/create",
            updateHealthRate: "ctx/pr/core/insurance/social/healthrate/update",
            removeHealthRate: "ctx/pr/core/insurance/social/healthrate/remove",
            //register+ update pension
            registerPensionRate: "ctx/pr/core/insurance/social/pensionrate/create",
            updatePensionRate: "ctx/pr/core/insurance/social/pensionrate/update",
            removePensionRate: "ctx/pr/core/insurance/social/pensionrate/remove",
            getAllRoundingItem: "pr/insurance/social/find/rounding"
        };
        /**
         * Function is used to load all InsuranceOfficeItem by key.
         */
        export function findInsuranceOffice(key: string): JQueryPromise<Array<model.finder.InsuranceOfficeItemDto>> {
            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.InsuranceOfficeItemDto>>();
            var findPath = servicePath.getAllOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data:any) {
                // Convert json to model here.
                // Resolve.
                dfd.resolve(data);
            });
            // Ret promise.
            return dfd.promise();
        }

        /**
         * Function is used to load all RoundingOption.
         */
        export function findAllRounding(): JQueryPromise<Array<model.finder.Enum>> {
            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.Enum>>();
            // Call ajax.
            //            nts.uk.request.ajax(findPath).done(function(data) {
            // Convert json to model here.
            var roundingList: Array<model.finder.Enum> = [
                new model.finder.Enum('0', '切り上げ'),
                new model.finder.Enum('1', '切捨て'),
                new model.finder.Enum('2', '四捨五入'),
                new model.finder.Enum('3', '五捨五超入'),
                new model.finder.Enum('4', '五捨六入')
            ];
            // Resolve.
            dfd.resolve(roundingList);
            //            });

            // Ret promise.
            return dfd.promise();
        }
        /**
         * Function is used to load health data of Office by office code.
         */
        export function getHealthInsuranceItemDetail(code: string): JQueryPromise<model.finder.HealthInsuranceRateDto> {
            // Init new dfd.
            var dfd = $.Deferred<model.finder.HealthInsuranceRateDto>();
            var findPath = servicePath.getHealthInsuranceItemDetail + "/" + code;
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data:any) {
                // Convert json to model here.
                var healthInsuranceRateDetailData: model.finder.HealthInsuranceRateDto = data;
                // Resolve.
                dfd.resolve(healthInsuranceRateDetailData);
            });

            // Ret promise.
            return dfd.promise();
        }
        /**
        * Function is used to load health data of Office by office code.
        */
        export function getAllHealthOfficeItem(): JQueryPromise<Array<model.finder.OfficeItemDto>> {
            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.OfficeItemDto>>();
            var findPath = servicePath.getAllHealthOfficeAndHistory;
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data:any) {
                // Convert json to model here.
                var returnData: Array<model.finder.OfficeItemDto> = data;
                // Resolve.
                dfd.resolve(returnData);
            });
            // Ret promise.
            return dfd.promise();
        }



        /**
        * Function is used to load pension  data of Office by office code.
        */
        export function getPensionItemDetail(code: string): JQueryPromise<model.finder.PensionRateDto> {
            // Init new dfd.
            var dfd = $.Deferred<model.finder.PensionRateDto>();
            var findPath = servicePath.getPensionItemDetail + "/" + code;
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data:any) {
                // Convert json to model here.
                var pensionRateDetailData: model.finder.PensionRateDto = data;
                // Resolve.
                dfd.resolve(pensionRateDetailData);
            });
            // Ret promise.
            return dfd.promise();
        }

        /**
        * Function is used to load health data of Office by office code.
        */
        export function getAllPensionOfficeItem(): JQueryPromise<Array<model.finder.OfficeItemDto>> {
            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.OfficeItemDto>>();
            var findPath = servicePath.getAllPensionOfficeAndHistory;
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data:any) {
                // Convert json to model here.
                var returnData: Array<model.finder.OfficeItemDto> = data;
                // Resolve.
                dfd.resolve(returnData);
            });
            // Ret promise.
            return dfd.promise();
        }


        /**
        * Function is used to save new Health insurance rate with office code and history id.
        */
        export function registerHealthRate(data: model.finder.AddNewHistoryDto): JQueryPromise<model.finder.AddNewHistoryDto> {
            return nts.uk.request.ajax(servicePath.registerHealthRate, data);
        }

        /**
        * Function is used to update new Health insurance rate with office code and history id.
        */
        export function updateHealthRate(data: model.finder.HealthInsuranceRateDto): JQueryPromise<model.finder.HealthInsuranceRateDto> {
            return nts.uk.request.ajax(servicePath.updateHealthRate, data);
        }
        
        /**
        * Function is used to update new Health insurance rate with office code and history id.
        */
        export function removeHealthRate(historyId: string): JQueryPromise<model.finder.HealthInsuranceRateDto> {
            var data = {historyId: historyId};
            return nts.uk.request.ajax(servicePath.removeHealthRate,data);
        }
        /**
        * Function is used to save new Pension rate with office code and history id.
        */
        export function registerPensionRate(data: model.finder.PensionRateDto): JQueryPromise<model.finder.PensionRateDto> {
            return nts.uk.request.ajax(servicePath.registerPensionRate, data);
        }

        /**
        * Function is used to update new Pension rate with office code and history id.
        */
        export function updatePensionRate(data: model.finder.PensionRateDto): JQueryPromise<model.finder.PensionRateDto> {
            return nts.uk.request.ajax(servicePath.updatePensionRate, data);
        }
        /**
        * Function is used to update new Health insurance rate with office code and history id.
        */
        export function removePensionRate(historyId: string): JQueryPromise<model.finder.HealthInsuranceRateDto> {
            var data = {historyId: historyId};
            return nts.uk.request.ajax(servicePath.removePensionRate, data);
        }
        /**
        * Model namespace.
        */
        export module model {
            export module finder {

                //office DTO
                export class InsuranceOfficeItemDto {
                    id: string;
                    name: string;
                    code: string;
                    childs: any;
                    codeName: string;
                    constructor(id: string, name: string, code: string, childs: Array<InsuranceOfficeItemDto>, codeName: string) {
                        this.id = id;
                        this.name = name;
                        this.code = code;
                        this.childs = childs;
                        this.codeName = codeName;
                    }
                }

                //Pension DTO
                export class PensionRateDto {
                    historyId: string;
                    companyCode: string;
                    officeCode: string;
                    startMonth: string;
                    endMonth: string;
                    autoCalculate: number;
                    fundInputApply: boolean;
                    premiumRateItems: Array<PensionRateItemDto>;
                    fundRateItems: Array<FundRateItemDto>;
                    roundingMethods: Array<RoundingDto>;
                    maxAmount: number;
                    childContributionRate: number;
                    constructor(historyId: string, companyCode: string, officeCode: string, startMonth: string, endMonth: string, autoCalculate: number, fundInputApply: boolean, premiumRateItems: Array<PensionRateItemDto>, fundRateItems: Array<FundRateItemDto>, roundingMethods: Array<RoundingDto>, maxAmount: number, childContributionRate: number) {
                        this.historyId = historyId;
                        this.companyCode = companyCode;
                        this.officeCode = officeCode;
                        this.startMonth = startMonth;
                        this.endMonth = endMonth;
                        this.autoCalculate = autoCalculate;
                        this.fundInputApply = fundInputApply;
                        this.premiumRateItems = premiumRateItems;
                        this.fundRateItems = fundRateItems;
                        this.roundingMethods = roundingMethods;
                        this.maxAmount = maxAmount;
                        this.childContributionRate = childContributionRate;
                    }
                }
                export class PensionRateItemDto {
                    payType: string;
                    genderType: string;
                    personalRate: number;
                    companyRate: number;
                    constructor(payType: string, genderType: string, personalRate: number, companyRate: number) {
                        this.payType = payType;
                        this.genderType = genderType;
                        this.personalRate = personalRate;
                        this.companyRate = companyRate;
                    }
                }
                export class FundRateItemDto {
                    payType: string;
                    genderType: string;
                    burdenChargePersonalRate: number;
                    burdenChargeCompanyRate: number;
                    exemptionChargePersonalRate: number;
                    exemptionChargeCompanyRate: number;
                    constructor(payType: string, genderType: string, burdenChargePersonalRate: number, burdenChargeCompanyRate: number, exemptionChargePersonalRate: number, exemptionChargeCompanyRate: number) {
                        this.payType = payType;
                        this.genderType = genderType;
                        this.burdenChargePersonalRate = burdenChargePersonalRate;
                        this.burdenChargeCompanyRate = burdenChargeCompanyRate;
                        this.exemptionChargePersonalRate = exemptionChargePersonalRate;
                        this.exemptionChargeCompanyRate = exemptionChargeCompanyRate;
                    }
                }

                //health DTO
                export class HealthInsuranceRateDto {
                    historyId: string;
                    companyCode: string;
                    officeCode: string;
                    startMonth: string;
                    endMonth: string;
                    autoCalculate: number;
                    rateItems: Array<HealthInsuranceRateItemDto>;
                    roundingMethods: Array<RoundingDto>;
                    maxAmount: number;

                    constructor(historyId: string, companyCode: string, officeCode: string, startMonth: string, endMonth: string, autoCalculate: number, rateItems: Array<HealthInsuranceRateItemDto>, roundingMethods: Array<RoundingDto>, maxAmount: number) {
                        this.historyId = historyId;
                        this.companyCode = companyCode;
                        this.officeCode = officeCode;
                        this.startMonth = startMonth;
                        this.endMonth = endMonth;
                        this.autoCalculate = autoCalculate;
                        this.rateItems = rateItems;
                        this.roundingMethods = roundingMethods;
                        this.maxAmount = maxAmount;
                    }
                }
                export class HealthInsuranceRateItemDto {
                    payType: string;
                    insuranceType: string;
                    chargeRate: ChargeRateItemDto;
                    constructor(payType: string, insuranceType: string, chargeRate: ChargeRateItemDto) {
                        this.chargeRate = chargeRate;
                        this.payType = payType;
                        this.insuranceType = insuranceType;
                    }
                }
                export class ChargeRateItemDto {
                    companyRate: number;
                    personalRate: number;
                    constructor(companyRate: number, personalRate: number) {
                        this.companyRate = companyRate;
                        this.personalRate = personalRate;
                    }
                }
                export class OfficeItemDto {
                    officeCode: string;
                    officeName: string;
                    listHistory: Array<HistoryDto>;
                }
                export class HistoryDto {
                    historyId: string;
                    startMonth: string;
                    endMonth: string;
                }

                //common class for health and pension
                export class RoundingDto {
                    payType: string;
                    roundAtrs: RoundingItemDto;
                    constructor(payType: string, roundAtrs: RoundingItemDto) {
                        this.payType = payType;
                        this.roundAtrs = roundAtrs;
                    }
                }
                export class RoundingItemDto {
                    personalRoundAtr: string;
                    companyRoundAtr: string;
                    constructor(personalRoundAtr: string, companyRoundAtr: string) {
                        this.personalRoundAtr = personalRoundAtr;
                        this.companyRoundAtr = companyRoundAtr;
                    }
                }
                export class AddNewHistoryDto{
                    officeCode: string;
                    startMonth: string;
                    endMonth: string;
                    isCloneData: boolean;
                }
                export class Enum {
                    code: string;
                    name: string;
                    label: string;

                    constructor(code: string, name: string) {
                        this.code = code;
                        this.name = name;
                    }
                }
            }
        }

    }
}
