module nts.uk.com.view.qmm008.share.model {
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

    // 社会保険事業所
    export interface ISocialInsuranceOffice {
        code: string;
        name: string;
        companyID: string
        basicInfomation: IBasicInfomation;
        insuranceMasterInfomation: string;
        welfareInsuranceRateHistory: IWelfarePensionInsuranceRateHistory;
    }

    // 社会保険事業所
    export class SocialInsuranceOffice {
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        basicInfomation: KnockoutObservable<BasicInfomation> = ko.observable(null);
        insuranceMasterInfomation: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        welfareInsuranceRateHistory: KnockoutObservable<WelfarePensionInsuranceRateHistory> = ko.observable(null);
        constructor(params: ISocialInsuranceOffice) {
            this.code(params.code);
            this.name(params.name);
            this.companyID(params.companyID);
            if (params.basicInfomation) {
                this.basicInfomation(new BasicInfomation(params.basicInfomation));
            }
            this.insuranceMasterInfomation(params.insuranceMasterInfomation);
            if (params.welfareInsuranceRateHistory) {
                this.welfareInsuranceRateHistory(new WelfarePensionInsuranceRateHistory(params.welfareInsuranceRateHistory));
            }
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
            this.representativePosition(params.representativePosition);
            this.notes(params.notes);
            this.streetAddress(new SocialInsuranceBusinessAddress(params.streetAddress));
            this.representativeName(params.representativeName);
            this.abbreviatedName(params.abbreviatedName);

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
            this.address1(params.address1);
            this.address2(params.address2);
            this.addressKana1(params.addressKana1);
            this.addressKana2(params.addressKana2);
            this.phoneNumber(params.phoneNumber);
            this.postalCode(params.postalCode);
        }
    }

    // 年月期間の汎用履歴項目
    export interface IGenericHistoryYearMonthPeiod {
        start: string;
        end: string;
        historyID: string;
    }

    // 年月期間の汎用履歴項目
    export class GenericHistoryYearMonthPeiod {

        // Item
        start: KnockoutObservable<string> = ko.observable(null);
        end: KnockoutObservable<string> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IGenericHistoryYearMonthPeiod) {
            this.start(params.start);
            this.end(params.end);
            this.historyID(params.historyID);
        }
    }

    // 厚生年金保険料率履歴
    export interface IWelfarePensionInsuranceRateHistory {
        socialInsuranceCode: string;
        companyID: string;
        history: IGenericHistoryYearMonthPeiod [];
    }

    // 厚生年金保険料率履歴
    export class WelfarePensionInsuranceRateHistory {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeiod> = ko.observableArray([]);

        constructor(params: IWelfarePensionInsuranceRateHistory) {
            this.socialInsuranceCode(params.socialInsuranceCode);
            this.companyID(params.companyID);
            this.history(params.history.map(function (item) {
                return new GenericHistoryYearMonthPeiod(item)
            }));
        }
    }

    // 厚生年金保険区分
    export interface IWelfarePensionInsuranceClassification {
        fundClassification: number;
        historyID: string;
    }

    // 厚生年金保険区分
    export class WelfarePensionInsuranceClassification {
        // Fields
        fundClassification: KnockoutObservable<number> = ko.observable(0);
        historyID: KnockoutObservable<string> = ko.observable(null);

        // Control Item
        fundClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(FUND_CLASSIFICATION.NOT_JOIN, getText('QMM008_54')),
            new EnumModel(FUND_CLASSIFICATION.JOIN, getText('QMM008_55'))
        ]);
        constructor(params: IWelfarePensionInsuranceClassification) {
            this.fundClassification(params.fundClassification);
            this.historyID(params.historyID);
        }
    }

    // 各負担料
    export interface IContributionFee {
        maleInsuPremium: number;
        femaleInsuPremium: number;
        maleExemptionInsu: number;
        femaleExemptionInsu: number;
    }

    // 各負担料
    export class ContributionFee {
        maleInsuPremium: KnockoutObservable<number> = ko.observable(null);
        femaleInsuPremium: KnockoutObservable<number> = ko.observable(null);
        maleExemptionInsu: KnockoutObservable<number> = ko.observable(null);
        femaleExemptionInsu: KnockoutObservable<number> = ko.observable(null);
        constructor(params: IContributionFee) {
            this.maleInsuPremium(params.maleInsuPremium);
            this.femaleInsuPremium(params.femaleInsuPremium);
            this.maleExemptionInsu(params.maleExemptionInsu);
            this.femaleExemptionInsu(params.femaleExemptionInsu);
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
            this.welfarePensionGrade(params.welfarePensionGrade);
            this.employeeBurden(new ContributionFee(params.employeeBurden));
            this.insuredBurden(new ContributionFee(params.insuredBurden));
        }
    }

    // 厚生年金各負担率
    export interface IEmployeePensionContributionRate {
        individualBurdenRatio: number;
        employeeContributionRatio: number;
        individualExcemtionRate: number;
        employeeExcemtionRate: number;
    }

    // 厚生年金各負担率
    export class EmployeePensionContributionRate {
            
        // Fields
        individualBurdenRatio: KnockoutObservable<number> = ko.observable(null);
        employeeContributionRatio: KnockoutObservable<number> = ko.observable(null);
        individualExcemtionRate: KnockoutObservable<number> = ko.observable(null);
        employeeExcemtionRate: KnockoutObservable<number> = ko.observable(null);
        
        // Display remain ratio
        remainBurdenRatio: any;
        remainEmployeeContributionRatio: any;
        constructor(params: IEmployeePensionContributionRate) {
            this.individualBurdenRatio(params.individualBurdenRatio);
            this.employeeContributionRatio(params.employeeContributionRatio);
            this.individualExcemtionRate(params.individualExcemtionRate);
            this.employeeExcemtionRate(params.employeeExcemtionRate);
            
            this.remainBurdenRatio = ko.computed(function() {
                return this.individualBurdenRatio() - this.individualExcemtionRate();    
            }, this);
            this.remainEmployeeContributionRatio = ko.computed(function() {
                return this.employeeContributionRatio() - this.employeeExcemtionRate();    
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
            new EnumModel(INSU_FRACTION_CLASSIFICATION.TRUNCATION, '切り捨て'),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_UP, '切り上げ'),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND4_UP5, '四捨五入'),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND5_UP6, '五捨六入'),
            new EnumModel(INSU_FRACTION_CLASSIFICATION.ROUND_LESS_OR_EQUAL_5, '五捨五超入')
        ]);
        
        constructor(params: IEmployeePensionClassification) {
            this.personalFraction(params.personalFraction);
            this.businessOwnerFraction(params.businessOwnerFraction);
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
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, '全体の保険料から被保険者分を差し引く'),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, '事業主負担率を用いて計算する')
        ]);
        constructor(params: ISalaryEmployeePensionInsuRate) {
            this.employeeShareAmountMethod(params.employeeShareAmountMethod);
            this.femaleContributionRate(new EmployeePensionContributionRate(params.femaleContributionRate));
            this.maleContributionRate(new EmployeePensionContributionRate(params.maleContributionRate));
            this.fractionClassification(new EmployeePensionClassification(params.fractionClassification));
        }
    }

    // 厚生年金月額保険料額
    export interface IEmployeePensionMonthlyInsuFee {
        autoCalculation: number;
        pensionInsurancePremium: IGradeWelfarePensionInsurancePremium;
        historyID: string;
        salaryEmployeePensionInsuranceRate: ISalaryEmployeePensionInsuRate;
    }

    // 厚生年金月額保険料額
    export class EmployeePensionMonthlyInsuFee {
        // Fields
        autoCalculation: KnockoutObservable<number> = ko.observable(null);
        pensionInsurancePremium: KnockoutObservable<GradeWelfarePensionInsurancePremium> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        salaryEmployeePensionInsuranceRate: KnockoutObservable<SalaryEmployeePensionInsuRate> = ko.observable(null);
        
        // Control item
        autoCalculationClsItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.USE, getText('QMM008_14')),
            new EnumModel(AUTOMATIC_CALCULATE_CLASSIFICATION.NOT_USE, getText('QMM008_15'))
        ]);
        
        constructor(params: IEmployeePensionMonthlyInsuFee) {
            this.autoCalculation(params.autoCalculation);
            if (params.pensionInsurancePremium) this.pensionInsurancePremium(new GradeWelfarePensionInsurancePremium(params.pensionInsurancePremium));
            this.historyID(params.historyID);
            if (params.salaryEmployeePensionInsuranceRate) this.salaryEmployeePensionInsuranceRate(new SalaryEmployeePensionInsuRate(params.salaryEmployeePensionInsuranceRate));
        }
    }
    
    // 賞与厚生年金保険料率
    export interface IBonusEmployeePensionInsuranceRate {
        employeeShareAmountMethod: number;
        fractionClassification: IEmployeePensionClassification;
        femaleContributionRate: IEmployeePensionContributionRate ;
        maleContributionRate:  IEmployeePensionContributionRate;
        historyID: string;
    }
    
    // 賞与厚生年金保険料率
    export class BonusEmployeePensionInsuranceRate {
        
        // Fields
        employeeShareAmountMethod: KnockoutObservable<number> = ko.observable(null);
        fractionClassification: KnockoutObservable<EmployeePensionClassification> = ko.observable(null);
        femaleContributionRate: KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        maleContributionRate:  KnockoutObservable<EmployeePensionContributionRate> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        
        // Control item
        shareAmountMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray([
            new EnumModel(SHARE_AMOUNT_METHOD.SUBTRACT_OVERALL_INSURANCE, '全体の保険料から被保険者分を差し引く'),
            new EnumModel(SHARE_AMOUNT_METHOD.EMPLOYER_CONTRIBUTION_RATIO, '事業主負担率を用いて計算する')
        ]);
        constructor (params: IBonusEmployeePensionInsuranceRate) {
            this.employeeShareAmountMethod(params.employeeShareAmountMethod);
            this.fractionClassification(new EmployeePensionClassification(params.fractionClassification));
            this.femaleContributionRate(new EmployeePensionContributionRate(params.femaleContributionRate));
            this.maleContributionRate(new EmployeePensionContributionRate(params.maleContributionRate));
            this.historyID(params.historyID);
        }
    }
        

    export class TreeGridNode {
        code: string;
        displayText: string;
        child: Array<TreeGridNode>;
        constructor(code: string, displayText: string, child: Array<TreeGridNode>) {
            this.code = code;
            this.displayText = displayText;
            this.child = child;
        }
    }


}