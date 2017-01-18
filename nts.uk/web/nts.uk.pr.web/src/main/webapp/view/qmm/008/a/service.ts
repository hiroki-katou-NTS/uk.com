module nts.uk.pr.view.qmm008.a {

    export module service {

        // Service paths.
        var servicePath = {
            getListOfficeItem: "",
            getAllRoundingItem: "",
            getHealthInsuranceItemDetail:"",
            getPensionItemDetail:""
        };
        /**
         * Function is used to load all InsuranceOfficeItem by key.
         */
        export function findInsuranceOffice(key: string): JQueryPromise<Array<model.finder.InsuranceOfficeItemDto>> {

            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.InsuranceOfficeItemDto>>();

            var findPath = servicePath.getListOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');

            // Call ajax.
//            nts.request.ajax(findPath).done(function(data) {
                var data = null ;
                // Convert json to model here.
                var OfficeItemList: Array<model.finder.InsuranceOfficeItemDto> =  [
                    new model.finder.InsuranceOfficeItemDto('id01', 'A 事業所', 'code1',[
                        new model.finder.InsuranceOfficeItemDto('child01', '~ 9999/12', 'chil1',[]),
                        new model.finder.InsuranceOfficeItemDto('child02', '~ 9999/12', 'chil2',[])
                    ]),
                    new model.finder.InsuranceOfficeItemDto('id02', 'B 事業所', 'code2',[])];

                // Resolve.
                dfd.resolve(OfficeItemList);
//            });

            // Ret promise.
            return dfd.promise();
        }
        /**
         * Function is used to load all RoundingOption.
         */
        export function findAllRounding(): JQueryPromise<Array<model.finder.RoundingItemDto>> {

            // Init new dfd.
            var dfd = $.Deferred<Array<model.finder.RoundingItemDto>>();

            var findPath = servicePath.getAllRoundingItem;

            // Call ajax.
//            nts.request.ajax(findPath).done(function(data) {
                var data = null ;
                // Convert json to model here.
                var roundingList: Array<model.finder.RoundingItemDto> =[
                    new model.finder.RoundingItemDto('001', 'op1change'),
                    new model.finder.RoundingItemDto('002', 'op2'),
                    new model.finder.RoundingItemDto('003', 'op3')
                ];

                // Resolve.
                dfd.resolve(roundingList);
//            });

            // Ret promise.
            return dfd.promise();
        }
        /**
         * Function is used to load all task.
         */
        export function getHealthInsuranceItemDetail(code:string): JQueryPromise<model.finder.HealthInsuranceRateDto> {

            // Init new dfd.
            var dfd = $.Deferred<model.finder.HealthInsuranceRateDto>();

            var findPath = servicePath.getHealthInsuranceItemDetail;

            // Call ajax.
//            nts.request.ajax(findPath).done(function(data) {
            //mock data
            if(code=="code1")
            {
            var rateItems: Array<model.finder.HealthInsuranceRateItemDto> = [
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

            var roundingMethods: Array<model.finder.RoundingDto> = [
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "roundingname1"),new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundingname3")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
            ];
            var data: model.finder.HealthInsuranceRateDto = new model.finder.HealthInsuranceRateDto(1, "companyCode", "code1", "applyRange", 1, rateItems, roundingMethods, 150000);
            
                }
            else
                {
            var rateItems: Array<model.finder.HealthInsuranceRateItemDto> = [
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

            var roundingMethods: Array<model.finder.RoundingDto> = [
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "op1"),new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "op2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "op3")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
            ];
            var data: model.finder.HealthInsuranceRateDto = new model.finder.HealthInsuranceRateDto(1, "companyCode", "code1", "applyRange", 2, rateItems, roundingMethods, 20000);
                
            }
                // Convert json to model here.
            var healthInsuranceRateDetailData: model.finder.HealthInsuranceRateDto = data;

                // Resolve.
                dfd.resolve(healthInsuranceRateDetailData);
//            });

            // Ret promise.
            return dfd.promise();
        }
        
         /**
         * Function is used to load all task.
         */
        export function getPensionItemDetail(code:string): JQueryPromise<model.finder.PensionRateDto> {

            // Init new dfd.
            var dfd = $.Deferred<model.finder.PensionRateDto>();

            var findPath = servicePath.getPensionItemDetail;

            // Call ajax.
//            nts.request.ajax(findPath).done(function(data) {
                
            //mock data
            if(code=="code1")
            {
            var rateItems: Array<model.finder.PensionRateItemDto> = [
                new model.finder.PensionRateItemDto(123,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(13423,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1523,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(123,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12653,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(123,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1723,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1263,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1223,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1823,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12223,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                
                ];
            var fundRateItems : Array<model.finder.FundRateItemDto> = [
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                ];
            var roundingMethods: Array<model.finder.RoundingDto> = [
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode1", "roundingname1"),new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundingname3")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
            ];
            var data: model.finder.PensionRateDto = new model.finder.PensionRateDto(1, "companyCode", "code1", "applyRange", 1,1, rateItems,fundRateItems, roundingMethods, 150000,150);
            
                }
            else
                {
            var rateItems: Array<model.finder.PensionRateItemDto> = [
                new model.finder.PensionRateItemDto(234,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(13423,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1523,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(123,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12653,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(123,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1723,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1263,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1223,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(1823,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                new model.finder.PensionRateItemDto(12223,model.GroupType.Personal, model.PaymentType.Salary, model.InsuranceGender.Male),
                
                ];
            var fundRateItems : Array<model.finder.FundRateItemDto> = [
                new model.finder.FundRateItemDto(111,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(11222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(2222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(2242,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(2223,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(2522,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                new model.finder.FundRateItemDto(222,model.GroupType.Personal,model.ChargeType.Burden,model.InsuranceGender.Male,model.PaymentType.Salary),
                ];
            var roundingMethods: Array<model.finder.RoundingDto> = [
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("rounding1", "rouname1"),new model.finder.RoundingItemDto("roundingcode2", "roundingname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode2", "roungname2")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode3", "roundinge3")]),
                new model.finder.RoundingDto(model.PaymentType.Salary, [new model.finder.RoundingItemDto("roundingcode4", "roundingname4")]),
            ];
            var data: model.finder.PensionRateDto = new model.finder.PensionRateDto(1, "companyCode", "code1", "applyRange", 1,2, rateItems,fundRateItems, roundingMethods, 200000,1150);
                
            }
                // Convert json to model here.
            var pensionRateDetailData: model.finder.PensionRateDto = data;

                // Resolve.
                dfd.resolve(pensionRateDetailData);
//            });

            // Ret promise.
            return dfd.promise();
        }
       
        /**
        * Model namespace.
        */
        export module model {
            export module finder {
                export class ChooseOption{
                    code:string;
                    name:string;   
                }
                //office DTO
                export class InsuranceOfficeItemDto {
                    id: string;
                    name: string;
                    code: string;
                    childs: any;
                    codeName: string;
                    constructor(id: string, name: string, code: string,childs: Array<InsuranceOfficeItemDto>) {
                        this.id = id;
                        this.name = name;
                        this.code = code;
                        this.childs=childs;
                        this.codeName=code+"\u00A0"+"\u00A0"+"\u00A0"+name;
                    }
                }
                
                //Pension DTO
                export class PensionRateDto{
                    historyId: number;
                    companyCode: string;
                    officeCode: string;
                    applyRange: string;
                    autoCalculate: number;
                    fundInputOption: number;
                    rateItems: Array<PensionRateItemDto>;
                    fundRateItems:Array<FundRateItemDto>;
                    roundingMethods: Array<RoundingDto>;
                    maxAmount:number;
                    officeRate:number;
                    //TODO this contructor for mock data,delete after use
                    constructor(historyId: number, companyCode: string, officeCode: string, applyRange: string, autoCalculate: number,funInputOption:number, rateItems: Array<PensionRateItemDto>,fundRateItems:Array<FundRateItemDto>, roundingMethods: Array<RoundingDto>,maxAmount:number,officeRate:number) {
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
                }
                export class PensionRateItemDto {
                    chargeRate:number;
                    groupType: GroupType;
                    genderType: InsuranceGender;
                    payType: PaymentType;
                    constructor(chargeRate: number, groupType: GroupType, payType: PaymentType, genderType: InsuranceGender) {
                        this.chargeRate = chargeRate;
                        this.groupType = groupType;
                        this.payType = payType;
                        this.genderType = genderType;
                    }   
                }
                export class FundRateItemDto {
                    chargeRate: number;
                    groupType: GroupType;                    
                    chargeType: ChargeType;
                    genderType: InsuranceGender;
                    payType: PaymentType;
                    constructor(chargeRate: number,groupType: GroupType,chargeType : ChargeType, genderType: InsuranceGender, payType: PaymentType) {
                        this.chargeRate = chargeRate;
                        this.groupType = groupType;
                        this.chargeType = chargeType; 
                        this.genderType = genderType;
                        this.payType = payType; 
                    }
                }
                
                //health DTO
                export class HealthInsuranceRateDto {
                    historyId: number;
                    companyCode: string;
                    officeCode: string;
                    applyRange: string;
                    autoCalculate: number;
                    rateItems: Array<HealthInsuranceRateItemDto>;
                    roundingMethods: Array<RoundingDto>;
                    maxAmount:number;
                    
                    //TODO this contructor for mock data,delete after use
                    constructor(historyId:number,companyCode:string,officeCode:string,applyRange:string,autoCalculate:number,rateItems: Array<HealthInsuranceRateItemDto>,roundingMethods: Array<RoundingDto>,maxAmount:number){
                        this.historyId=historyId;
                        this.companyCode=companyCode;
                        this.officeCode=officeCode;
                        this.applyRange= applyRange;
                        this.autoCalculate= autoCalculate;
                        this.rateItems=rateItems;
                        this.roundingMethods=roundingMethods;
                        this.maxAmount=maxAmount;
                    } 
                }
                export class HealthInsuranceRateItemDto {
                    chargeRate: number; //value of person or company
                    payType: PaymentType;
                    healthInsuranceType: HealthInsuranceType;
                    constructor(chargeRate:number,payType:PaymentType,healthInsuranceType:HealthInsuranceType)
                    {
                        this.chargeRate=chargeRate;
                        this.payType=payType;
                        this.healthInsuranceType=healthInsuranceType;
                    }
                }
                export class chargeRateItemDto{
                    companyRate:number;
                    personalRate:number;
                    constructor(companyRate:number,personalRate:number)
                    {
                        this.companyRate= companyRate;
                        this.personalRate =personalRate;    
                    }
                }
                //common class for health and pension
                export class RoundingDto {
                    payType: PaymentType;
                    roundAtrs: Array<RoundingItemDto>;
                    constructor(payType: PaymentType,roundAtrs: Array<RoundingItemDto>){
                        this.payType=payType;
                        this.roundAtrs= roundAtrs;
                    }
                }
                export class HealthInsuranceAvgearnDto {
                    historyId: number;
                    levelCode: number;
                    companyAvg: HealthInsuranceAvgearnValueDto;
                    personalAvg: HealthInsuranceAvgearnValueDto;
                }

//                export class RoundingItemDto {
//                    companyRoundAtr: number;
//                    personalRoundAtr: number;
//                }
                export class RoundingItemDto {
                    code: string;
                    name: string;
                    label: string;

                    constructor(code: string, name: string) {
                        this.code = code;
                        this.name = name;
                    }
                }
                export class HealthInsuranceAvgearnValueDto {
                    healthGeneralMny: number;
                    healthNursingMny: number;
                    healthBasicMny: number;
                    healthSpecificMny: number;
                }
            }


            export enum PaymentType {
                Salary = 0,
                Bonus = 1
            }

            export enum HealthInsuranceType {
                General = 0,
                Nursing = 1,
                Basic = 2,
                Special = 3
            }
            export enum InsuranceGender {
                Male = 0,
                Female = 1,
                Unknow = 2
            }
            export enum ChargeType {
                Burden = 0,
                Exemption = 1
            }
            export enum GroupType {
                Personal = 0,
                Company = 1   
            }
        }

    }
}
