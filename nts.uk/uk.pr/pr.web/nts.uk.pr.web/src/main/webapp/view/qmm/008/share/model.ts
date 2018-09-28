module nts.uk.pr.view.qmm008.share.model {
    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    // 厚生年金基金加入区分
    export enum FUND_CLASSIFICATION {
        NOT_JOIN = 0,
        JOIN = 1
    }

    // 自動計算実施区分
    export enum AUTOMATIC_CALCULATE_CLASSIFICATION {
        NOT_USE = 0,
        USE = 1
    }

    //保険料端数区分
    export enum INSU_FRACTION_CLASSIFICATION {
        TRUNCATION = 0, // 切り捨て
        ROUND_UP = 1, // 切り上げ
        ROUND4_UP5 = 2, // 四捨五入
        ROUND5_UP6 = 3, // 五捨六入
        ROUND_LESS_OR_EQUAL_5 // 五捨五超入
    }

    // 事業主負担分計算方法
    export enum SHARE_AMOUNT_METHOD {
        SUBTRACT_OVERALL_INSURANCE = 0, // 全体の保険料から被保険者分を差し引く
        EMPLOYER_CONTRIBUTION_RATIO = 1 // 事業主負担率を用いて計算する
    }

    export class ItemModel {
        code: string;
        name: string;
        constructor(code, name) {
            this.code = code;
            this.name = name;
        }
    }

    export class EnumModel {
        value: number;
        name: string;
        constructor(value, name) {
            this.value = value;
            this.name = name;
        }
    }

    export enum TAKEOVER_METHOD {
        FROM_LASTEST_HISTORY = 0,
        FROM_BEGINNING = 1
    }

    export enum MOFIDY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    //Social Office

    // 社会保険事業所
    export interface ISocialInsuranceOffice {
        socialInsuranceCode: string;
        socialInsuranceName: string;
        companyID: string
        basicInfomation: IBasicInfomation;
        insuranceMasterInfomation: string;
        welfareInsuranceRateHistory: IWelfarePensionInsuranceRateHistory;
        healthInsuranceFeeRateHistory: IHealthInsuranceFeeRateHistory;
    }

    // 年月期間の汎用履歴項目
    export interface IGenericHistoryYearMonthPeiod {
        startMonth: string;
        endMonth: string;
        historyId: string;
    }

    // 年月期間の汎用履歴項目
    export class GenericHistoryYearMonthPeiod {

        // Item
        startMonth: KnockoutObservable<string> = ko.observable(null);
        endMonth: KnockoutObservable<string> = ko.observable(null);
        historyId: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IGenericHistoryYearMonthPeiod) {
            this.startMonth(params.startMonth);
            this.endMonth(params.endMonth);
            this.historyId(params.historyId);
        }
    }


    // Screen B Domain

    // 健康保険料率履歴
    export interface IHealthInsuranceFeeRateHistory {
        socialInsuranceOfficeCode: string;
        history: Array<IGenericHistoryYearMonthPeiod>;
    }

    // 健康保険料率履歴
    export class HealthInsuranceFeeRateHistory {
        cid: KnockoutObservable<string> = ko.observable(null);
        socialInsuranceOfficeCode: KnockoutObservable<string> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeiod> = ko.observable([]);
        constructor(params: IHealthInsuranceFeeRateHistory) {
            this.cid(params ? params.cid : null);
            this.socialInsuranceOfficeCode(params ? params.socialInsuranceOfficeCode : null);
            this.history(params ? params.history.map(function(item) {
                return new GenericHistoryYearMonthPeiod(item);
            }) : []);
        }
    }

    // 健康保険各保険負担率
    export interface IHealthContributionRate {
        longCareInsuranceRate: number;
        basicInsuranceRate: number;
        healthInsuranceRate: number;
        fractionCls: number;
        specialInsuranceRate: number;
    }

    // 健康保険各保険負担率
    export class HealthContributionRate {
        longCareInsuranceRate: KnockoutObservable<number> = ko.observable(null);
        basicInsuranceRate: KnockoutObservable<number> = ko.observable(null);
        healthInsuranceRate: KnockoutObservable<number> = ko.observable(null);
        fractionCls: KnockoutObservable<number> = ko.observable(null);
        specialInsuranceRate: KnockoutObservable<number> = ko.observable(null);
        
        // Control item
        insurancePremiumFractionClassification: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(INSU_FRACTION_CLASSIFICATION.TRUNCATION, getText('Enum_InsuPremiumFractionClassification_TRUNCATION')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_UP, getText('Enum_InsuPremiumFractionClassification_ROUND_UP')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND4_UP5, getText('Enum_InsuPremiumFractionClassification_ROUND_4_UP_5')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND5_UP6, getText('Enum_InsuPremiumFractionClassification_ROUND_5_UP_6')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_LESS_OR_EQUAL_5, getText('Enum_InsuPremiumFractionClassification_ROUND_SUPER_5'))
        ]);

        constructor(params: IHealthContributionRate) {
            this.longCareInsuranceRate(params ? params.longCareInsuranceRate : 0.000);
            this.basicInsuranceRate(params ? params.basicInsuranceRate : 0.000);
            this.healthInsuranceRate(params ? params.healthInsuranceRate : 0.000);
            this.fractionCls(params ? params.fractionCls : null);
            this.specialInsuranceRate(params ? params.specialInsuranceRate : 0.000);
        }
    }

    // 各負担料
    export interface IHealthContributionFee {
        nursingCare: number;
        basicInsurancePremium: number;
        healthInsurancePremium: number;
        specInsurancePremium: number;
    }

    // 各負担料
    export class HealthContributionFee {
        nursingCare: KnockoutObservable<number> = ko.observable(null);
        basicInsurancePremium: KnockoutObservable<number> = ko.observable(null);
        healthInsurancePremium: KnockoutObservable<number> = ko.observable(null);
        specInsurancePremium: KnockoutObservable<number> = ko.observable(null);
        constructor(params: I) {
            this.nursingCare(params ? params.nursingCare : null);
            this.basicInsurancePremium(params ? params.basicInsurancePremium : null);
            this.healthInsurancePremium(params ? params.healthInsurancePremium : null);
            this.specInsurancePremium(params ? params.specInsurancePremium : null);
        }
    }

    // 賞与健康保険料率
    export interface IBonusHealthInsuranceRate {
        historyId: string;
        employeeShareAmountMethod: number;
        individualBurdenRatio: HealthContributionRate;
        employeeBurdenRatio: HealthContributionRate;
    }

    // 賞与健康保険料率
    export class BonusHealthInsuranceRate {
        historyId: KnockoutObservable<string> = ko.observable(null);
        employeeShareAmountMethod: KnockoutObservable<number> = ko.observable(null);
        individualBurdenRatio: KnockoutObservable<HealthContributionRate> = ko.observable(null);
        employeeBurdenRatio: KnockoutObservable<HealthContributionRate> = ko.observable(null);
        
         // Control item
        shareAmountMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, getText('Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM')),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, getText('Enum_EmployeeShareAmountMethod_EMPLOYEE_CONTRIBUTION_RATIO'))
        ]);
        constructor(params: IBonusHealthInsuranceRate) {
            this.historyId(params ? params.historyId : null);
            this.employeeShareAmountMethod(params ? params.employeeShareAmountMethod : null);
            this.individualBurdenRatio(new HealthContributionRate(params ? params.individualBurdenRatio : null));
            this.employeeBurdenRatio(new HealthContributionRate(params ? params.employeeBurdenRatio : null));
        }
    }

    // 給与健康保険料率
    export interface ISalaryHealthInsurancePremiumRate {
        individualBurdenRatio: HealthContributionRate;
        employeeShareAmountMethod: number;
        employeeBurdenRatio: HealthContributionRate;
    }

    // 給与健康保険料率
    export class SalaryHealthInsurancePremiumRate {
        individualBurdenRatio: KnockoutObservable<HealthContributionRate> = ko.observable(null);
        employeeShareAmountMethod: KnockoutObservable<number> = ko.observable(null);
        employeeBurdenRatio: KnockoutObservable<HealthContributionRate> = ko.observable(null);
        // Control item
        shareAmountMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, getText('Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM')),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, getText('Enum_EmployeeShareAmountMethod_EMPLOYEE_CONTRIBUTION_RATIO'))
        ]);
        constructor(params: ISalaryHealthInsurancePremiumRate) {
            this.employeeShareAmountMethod(params ? params.employeeShareAmountMethod : null);
            this.individualBurdenRatio(new HealthContributionRate(params ? params.individualBurdenRatio : null));
            this.employeeBurdenRatio(new HealthContributionRate(params ? params.employeeBurdenRatio : null));
        }
    }

    // 健康保険月額保険料額
    export interface IHealthInsuranceMonthlyFee {
        healthInsuranceRate: ISalaryHealthInsurancePremiumRate;
        autoCalculationCls: number;
        historyId: string;
        healthInsurancePerGradeFee: Array<IHealthInsurancePerGradeFee>;
    }

    // 健康保険月額保険料額
    export class HealthInsuranceMonthlyFee {
        healthInsuranceRate: KnockoutObservable<SalaryHealthInsurancePremiumRate> = ko.observable(null);
        autoCalculationCls: KnockoutObservable<number> = ko.observable(null);
        historyId: KnockoutObservable<string> = ko.observable(null);
        healthInsurancePerGradeFee: KnockoutObservableArray<HealthInsurancePerGradeFee> = ko.observableArray(null);

        // Control item
        autoCalculationClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.USE, getText('QMM008_14')),
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.NOT_USE, getText('QMM008_15'))
        ]);
        
        constructor(params: IHealthInsuranceMonthlyFee) {
            this.autoCalculationCls(params ? params.autoCalculationCls : 1);
            this.historyId(params ? params.historyId : null);
            this.healthInsuranceRate(new SalaryHealthInsurancePremiumRate(params ? params.healthInsuranceRate : null));
            this.healthInsurancePerGradeFee(params ? params.healthInsurancePerGradeFee.map(function(item) {
                return new HealthInsurancePerGradeFee(item);
            }) : []);
        }
    }
    
    
    //拠出金率
    export interface IContributionRate {
        childContributionRatio: number;
        automaticCalculationCls: number;
        historyId: string;
        contributionByGrade: Array<IContributionByGrade>;
    }
    
     //拠出金率
    export class ContributionRate {
        childContributionRatio: KnockoutObservable<number> = ko.observable(null);
        automaticCalculationCls: KnockoutObservable<number> = ko.observable(null);
        historyId: KnockoutObservable<string> = ko.observable(null);
        contributionByGrade: KnockoutObservableArray<ContributionByGrade> = ko.observableArray(null);

        // Control item
        autoCalculationClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.USE, getText('QMM008_14')),
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.NOT_USE, getText('QMM008_15'))
        ]);
        
        constructor(params: IContributionRate) {
            this.automaticCalculationCls(params ? params.automaticCalculationCls : 1);
            this.historyId(params ? params.historyId : null);
            this.childContributionRatio(params ? params.childContributionRatio : 0.000);
            this.contributionByGrade(params ? params.contributionByGrade.map(function(item) {
                return new ContributionByGrade(item);
            }) : []);
        }
    }
    
    //等級毎拠出金
    export interface IContributionByGrade {
        welfarePensionGrade: number;
        childCareContribution: number;
    }

    export class ContributionByGrade {
        welfarePensionGrade: KnockoutObservable<number> = ko.observable(null);
        childCareContribution: KnockoutObservable<number> = ko.observable(null);
        constructor(params: IContributionByGrade) {
            this.welfarePensionGrade(params ? params.welfarePensionGrade : null);
            this.childCareContribution(params ? params.childCareContribution : null);
        }
    }
    
    
    

    // 等級毎健康保険料
    export interface IHealthInsurancePerGradeFee {
        healthInsuranceGrade: number;
        employeeBurden: IHealthContributionFee;
        insuredBurden: IHealthContributionFee;
    }

    export class HealthInsurancePerGradeFee {
        healthInsuranceGrade: KnockoutObservable<number> = ko.observable(null);
        employeeBurden: KnockoutObservable<HealthContributionFee> = ko.observable(null);
        insuredBurden: KnockoutObservable<HealthContributionFee> = ko.observable(null);
        constructor(params: IHealthInsurancePerGradeFee) {
            this.healthInsuranceGrade(params ? params.healthInsuranceGrade : null);
            this.employeeBurden(new HealthContributionFee(params ? params.healthInsuranceGrade : null));
            this.insuredBurden(new HealthContributionFee(params ? params.healthInsuranceGrade : null));
        }
    }
    
    
    //Screen I Domain
    //拠出金率履歴
    export interface IContributionRateHistory {
        socialInsuranceCode: string;
        history: IGenericHistoryYearMonthPeiod[];
    }
    //拠出金率履歴
    export class ContributionRateHistory {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeiod> = ko.observableArray([]);

        constructor(params: IWelfarePensionInsuranceRateHistory) {
            this.socialInsuranceCode(params ? params.socialInsuranceCode : null);
            this.history(params ? params.history.map(function(item) {
                return new GenericHistoryYearMonthPeiod(item)
            }) : []);
        }
    }
    
    
    
    // Screen C Domain

    // 厚生年金保険料率履歴
    export interface IWelfarePensionInsuranceRateHistory {
        socialInsuranceCode: string;
        history: IGenericHistoryYearMonthPeiod[];
    }

    // 厚生年金保険料率履歴
    export class WelfarePensionInsuranceRateHistory {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeiod> = ko.observableArray([]);

        constructor(params: IWelfarePensionInsuranceRateHistory) {
            this.socialInsuranceCode(params ? params.socialInsuranceCode : null);
            this.history(params ? params.history.map(function(item) {
                return new GenericHistoryYearMonthPeiod(item)
            }) : []);
        }
    }

    // 厚生年金保険区分
    export interface IWelfarePensionInsuranceClassification {
        fundClassification: number;
        historyId: string;
    }

    // 厚生年金保険区分
    export class WelfarePensionInsuranceClassification {
        // Fields
        fundClassification: KnockoutObservable<number> = ko.observable(0);
        historyId: KnockoutObservable<string> = ko.observable(null);

        // Control Item
        fundClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(FUND_CLASSIFICATION.JOIN, getText('QMM008_54')),
            new EnumModel(FUND_CLASSIFICATION.NOT_JOIN, getText('QMM008_55'))
        ]);
        constructor(params: IWelfarePensionInsuranceClassification) {
            this.fundClassification(params ? params.fundClassification : 1);
            this.historyId(params ? params.historyId : null);
        }
    }

    // 各負担料
    export interface IContributionFee {
        maleInsurancePremium: number;
        femaleInsurancePremium: number;
        maleExemptionInsurance: number;
        femaleExemptionInsurance: number;
    }

    // 各負担料
    export class ContributionFee {
        maleInsurancePremium: KnockoutObservable<number> = ko.observable(null);
        femaleInsurancePremium: KnockoutObservable<number> = ko.observable(null);
        maleExemptionInsurance: KnockoutObservable<number> = ko.observable(null);
        femaleExemptionInsurance: KnockoutObservable<number> = ko.observable(null);
        constructor(params: IContributionFee) {
            this.maleInsurancePremium(params ? params.maleInsurancePremium : null);
            this.femaleInsurancePremium(params ? params.femaleInsurancePremium : null);
            this.maleExemptionInsurance(params ? params.maleExemptionInsurance : null);
            this.femaleExemptionInsurance(params ? params.femaleExemptionInsurance : null);
        }
    }

    // 等級毎厚生年金保険料
    export interface IGradeWelfarePensionInsurancePremium {
        welfarePensionGrade: number;
        employeeBurden: IContributionFee;
        insuredBurden: IContributionFee;
    }

    // 等級毎厚生年金保険料
    export class GradeWelfarePensionInsurancePremium {
        welfarePensionGrade: KnockoutObservable<number> = ko.observable(null);
        employeeBurden: KnockoutObservable<ContributionFee> = ko.observable(null);
        insuredBurden: KnockoutObservable<ContributionFee> = ko.observable(null);
        constructor(params: IGradeWelfarePensionInsurancePremium) {
            this.welfarePensionGrade(params ? params.welfarePensionGrade : null);
            this.employeeBurden(new ContributionFee(params ? params.employeeBurden : null));
            this.insuredBurden(new ContributionFee(params ? params.insuredBurden : null));
        }
    }

    // 厚生年金各負担率
    export interface IEmployeePensionContributionRate {
        individualBurdenRatio: number;
        employeeContributionRatio: number;
        individualExemptionRate: number;
        employeeExemptionRate: number;
    }

    // 厚生年金各負担率
    export class EmployeePensionContributionRate {

        // Fields
        individualBurdenRatio: KnockoutObservable<number> = ko.observable(null);
        employeeContributionRatio: KnockoutObservable<number> = ko.observable(null);
        individualExemptionRate: KnockoutObservable<number> = ko.observable(null);
        employeeExemptionRate: KnockoutObservable<number> = ko.observable(null);

        // Display remain ratio
        remainBurdenRatio: any;
        remainEmployeeContributionRatio: any;
        constructor(params: IEmployeePensionContributionRate) {
            this.individualBurdenRatio(params ? params.individualBurdenRatio : 0.000);
            this.employeeContributionRatio(params ? params.employeeContributionRatio : 0.000);
            this.individualExemptionRate(params ? params.individualExemptionRate : 0.000);
            this.employeeExemptionRate(params ? params.employeeExemptionRate : 0.000);

            this.remainBurdenRatio = ko.computed(function() {
                if (isNaN(this.individualBurdenRatio())|| isNaN(this.individualExemptionRate())) return 0;
                if (this.individualBurdenRatio() < this.individualExemptionRate()) return 0;
                return this.individualBurdenRatio() - this.individualExemptionRate();
            }, this);
            this.remainEmployeeContributionRatio = ko.computed(function() {
                if (isNaN(this.employeeContributionRatio()) || isNaN(this.employeeExemptionRate())) return 0;
                if (this.employeeContributionRatio() < this.employeeExemptionRate()) return 0;
                return this.employeeContributionRatio() - this.employeeExemptionRate();
            }, this);
        }
    }

    // 厚生年金端数区分
    export interface IEmployeePensionClassification {
        personalFraction: number;
        businessOwnerFraction: number;
    }

    // 厚生年金端数区分
    export class EmployeePensionClassification {

        // Fields
        personalFraction: KnockoutObservable<number> = ko.observable(null);
        businessOwnerFraction: KnockoutObservable<number> = ko.observable(null);

        // Control item
        insurancePremiumFractionClassification: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(INSU_FRACTION_CLASSIFICATION.TRUNCATION, getText('Enum_InsuPremiumFractionClassification_TRUNCATION')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_UP, getText('Enum_InsuPremiumFractionClassification_ROUND_UP')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND4_UP5, getText('Enum_InsuPremiumFractionClassification_ROUND_4_UP_5')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND5_UP6, getText('Enum_InsuPremiumFractionClassification_ROUND_5_UP_6')),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_LESS_OR_EQUAL_5, getText('Enum_InsuPremiumFractionClassification_ROUND_SUPER_5'))
        ]);

        constructor(params: IEmployeePensionClassification) {
            this.personalFraction(params ? params.personalFraction : null);
            this.businessOwnerFraction(params ? params.businessOwnerFraction : null);
        }
    }

    // 給与厚生年金保険料率
    export interface ISalaryEmployeePensionInsuRate {
        employeeShareAmountMethod: number;
        femaleContributionRate: IEmployeePensionContributionRate;
        maleContributionRate: IEmployeePensionContributionRate;
        fractionClassification: IEmployeePensionClassification;
    }

    // 給与厚生年金保険料率
    export class SalaryEmployeePensionInsuRate {

        // Fields
        employeeShareAmountMethod: KnockoutObservable<number> = ko.observable(null);
        femaleContributionRate: KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        maleContributionRate: KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        fractionClassification: KnockoutObservable<EmployeePensionClassification> = ko.observable(null);

        // Control item
        shareAmountMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, getText('Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM')),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, getText('Enum_EmployeeShareAmountMethod_EMPLOYEE_CONTRIBUTION_RATIO'))
        ]);
        constructor(params: ISalaryEmployeePensionInsuRate) {
            this.employeeShareAmountMethod(params ? params.employeeShareAmountMethod : null);
            this.femaleContributionRate(new EmployeePensionContributionRate(params ? params.femaleContributionRate : null));
            this.maleContributionRate(new EmployeePensionContributionRate(params ? params.maleContributionRate : null));
            this.fractionClassification(new EmployeePensionClassification(params ? params.fractionClassification : null));
        }
    }

    // 厚生年金月額保険料額
    export interface IEmployeePensionMonthlyInsuFee {
        autoCalculationCls: number;
        pensionInsurancePremium: Array<IGradeWelfarePensionInsurancePremium>;
        historyId: string;
        salaryEmployeesPensionInsuranceRate: ISalaryEmployeePensionInsuRate;
    }

    // 厚生年金月額保険料額
    export class EmployeePensionMonthlyInsuFee {
        // Fields
        autoCalculationCls: KnockoutObservable<number> = ko.observable(null);
        pensionInsurancePremium: KnockoutObservableArray<GradeWelfarePensionInsurancePremium> = ko.observableArray([]);
        historyId: KnockoutObservable<string> = ko.observable(null);
        salaryEmployeesPensionInsuranceRate: KnockoutObservable<SalaryEmployeePensionInsuRate> = ko.observable(null);

        // Control item
        autoCalculationClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.USE, getText('QMM008_14')),
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.NOT_USE, getText('QMM008_15'))
        ]);

        constructor(params: IEmployeePensionMonthlyInsuFee) {
            this.autoCalculationCls(params ? params.autoCalculationCls : 1);
            this.pensionInsurancePremium(params ? params.pensionInsurancePremium.map(function(item) {
                return new GradeWelfarePensionInsurancePremium(item);
            }) : []);
            this.historyId(params ? params.historyId : null);
            this.salaryEmployeesPensionInsuranceRate(new SalaryEmployeePensionInsuRate(params ? params.salaryEmployeesPensionInsuranceRate : null));
        }
    }

    // 賞与厚生年金保険料率
    export interface IBonusEmployeePensionInsuranceRate {
        employeeShareAmountMethod: number;
        fractionClassification: IEmployeePensionClassification;
        femaleContributionRate: IEmployeePensionContributionRate;
        maleContributionRate: IEmployeePensionContributionRate;
        historyId: string;
    }

    // 賞与厚生年金保険料率
    export class BonusEmployeePensionInsuranceRate {

        // Fields
        employeeShareAmountMethod: KnockoutObservable<number> = ko.observable(null);
        fractionClassification: KnockoutObservable<EmployeePensionClassification> = ko.observable(null);
        femaleContributionRate: KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        maleContributionRate: KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        historyId: KnockoutObservable<string> = ko.observable(null);

        // Control item
        shareAmountMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, getText('Enum_EmployeeShareAmountMethod_SUBTRACT_INSURANCE_PREMIUM')),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, getText('Enum_EmployeeShareAmountMethod_EMPLOYEE_CONTRIBUTION_RATIO'))
        ]);
        constructor(params: IBonusEmployeePensionInsuranceRate) {
            this.employeeShareAmountMethod(params ? params.employeeShareAmountMethod : null);
            this.fractionClassification(new EmployeePensionClassification(params ? params.fractionClassification : null));
            this.femaleContributionRate(new EmployeePensionContributionRate(params ? params.femaleContributionRate : null));
            this.maleContributionRate(new EmployeePensionContributionRate(params ? params.maleContributionRate : null));
            this.historyId(params ? params.historyId : null);
        }
    }


    export class TreeGridNode {
        code: string;
        displayText: string;
        child: Array<TreeGridNode>;
        officeCode: string;
        officeName: string;;;
        constructor(code: string, displayText: string, child: Array<TreeGridNode>, officeCode, officeName) {
            this.code = code;
            this.displayText = displayText;
            this.child = child;
            this.officeCode = officeCode;
            this.officeName = officeName;
        }
    }
    // 社会保険事業所
    export class SocialInsuranceOffice {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        socialInsuranceName: KnockoutObservable<string> = ko.observable(null);
        basicInfomation: KnockoutObservable<BasicInfomation> = ko.observable(null);
        insuranceMasterInfomation: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        welfareInsuranceRateHistory: KnockoutObservable<WelfarePensionInsuranceRateHistory> = ko.observable(null);
        healthInsuranceFeeRateHistory: KnockoutObservable<HealthInsuranceFeeRateHistory> = ko.observable(null);
        contributionRateHistory: KnockoutObservable<ContributionRateHistory> = ko.observable(null);
        constructor(params: ISocialInsuranceOffice) {
            this.socialInsuranceCode(params ? params.socialInsuranceCode : null);
            this.socialInsuranceName(params ? params.socialInsuranceName : null);
            this.companyID(params ? params.companyID: null);
            this.basicInfomation(new BasicInfomation(params ? params.basicInfomation : null));
            this.insuranceMasterInfomation(params ? params.insuranceMasterInfomation : null);
            this.welfareInsuranceRateHistory(new WelfarePensionInsuranceRateHistory(params ? params.welfareInsuranceRateHistory: null));
            this.healthInsuranceFeeRateHistory(new HealthInsuranceFeeRateHistory(params ? params.healthInsuranceFeeRateHistory : null));
            this.contributionRateHistory(new ContributionRateHistory(params ? params.contributionRateHistory: null));
        }
    }

    // 基本情報
    export interface IBasicInfomation {
        representativePosition: string;
        notes: string;
        streetAddress: ISocialInsuranceBusinessAddress;
        representativeName: string;
        abbreviatedName: string;
    }

    // 基本情報
    export class BasicInfomation {
        representativePosition: KnockoutObservable<string> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable(null);
        streetAddress: KnockoutObservable<SocialInsuranceBusinessAddress> = ko.observable(null);
        representativeName: KnockoutObservable<string> = ko.observable(null);
        abbreviatedName: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IBasicInfomation) {
            this.representativePosition(params ? params.representativePosition : null);
            this.notes(params ? params.notes : params);
            this.streetAddress(new SocialInsuranceBusinessAddress(params ? params.streetAddress : null));
            this.representativeName(params ? params.representativeName: null);
            this.abbreviatedName(params ? params.abbreviatedName : null);

        }
    }

    // 社会保険事業所住所
    export interface ISocialInsuranceBusinessAddress {
        address1: string;
        address2: string;
        addressKana1: string;
        addressKana2: string;
        phoneNumber: string;
        postalCode: string;
    }

    // 社会保険事業所住所
    export class SocialInsuranceBusinessAddress {
        address1: KnockoutObservable<string> = ko.observable(null);
        address2: KnockoutObservable<string> = ko.observable(null);
        addressKana1: KnockoutObservable<string> = ko.observable(null);
        addressKana2: KnockoutObservable<string> = ko.observable(null);
        phoneNumber: KnockoutObservable<string> = ko.observable(null);
        postalCode: KnockoutObservable<string> = ko.observable(null);
        constructor(params: ISocialInsuranceBusinessAddress) {
            this.address1(params ? params.address1 : null);
            this.address2(params ? params.address2 : null);
            this.addressKana1(params ? params.addressKana1 : null);
            this.addressKana2(params ? params.addressKana2 : null);
            this.phoneNumber(params ? params.phoneNumber : null);
            this.postalCode(params ? params.postalCode : null);
        }
    }

}