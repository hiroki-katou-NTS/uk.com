module nts.uk.pr.view.qmm008.f {
    export module viewmodel {
        export class ScreenModel {

            dataList: KnockoutObservableArray<RowData>;
            header : KnockoutObservable<HeaderData> = ko.observable(new HeaderData ());
            constructor() {
                var self = this;
                self.dataList = ko.observableArray([]);
                let command = { historyId: 'e091445c-a610-4362-a4e9-fa89db856fd2',date : 201802 };
                nts.uk.pr.view.qmm008.f.service.init(command).done(function(response) {
                    for (var i = 0; i < response.cusDataDtos.length; i++) {
                        self.dataList.push(response.cusDataDtos[i]);
                    } 
                    self.header(response.premiumRate);
                });

                $("#fixed-table").ntsFixedTable({ height: 300, width: 900 });
            }
        }
    }
    
    interface IRowData {
        welfarePensionGrade: KnockoutObservable<string> ,
        standardMonthlyFee: KnockoutObservable<string> ,
        rewardMonthlyLowerLimit: KnockoutObservable<string> ,
        rewardMonthlyUpperLimit: KnockoutObservable<string> ,
        inMaleInsurancePremium: KnockoutObservable<string>,
        emMaleInsurancePremium: KnockoutObservable<string>,
        inMaleExemptionInsurance: KnockoutObservable<string>,
        emMaleExemptionInsurance: KnockoutObservable<string>,
        inFemaleInsurancePremium: KnockoutObservable<string> ,
        emFemaleInsurancePremium: KnockoutObservable<string> ,
        inFemaleExemptionInsurance: KnockoutObservable<string> ,
        emFemaleExemptionInsurance: KnockoutObservable<string>
    
    }
    

    export class RowData {
        welfarePensionGrade: KnockoutObservable<string>  = ko.observable(null);
        standardMonthlyFee: KnockoutObservable<string>  = ko.observable(null);
        rewardMonthlyLowerLimit: KnockoutObservable<string>  = ko.observable(null);
        rewardMonthlyUpperLimit: KnockoutObservable<string> = ko.observable(null);
        inMaleInsurancePremium: KnockoutObservable<string> = ko.observable(null);
        emMaleInsurancePremium: KnockoutObservable<string> = ko.observable(null);
        inMaleExemptionInsurance: KnockoutObservable<string> = ko.observable(null);
        emMaleExemptionInsurance: KnockoutObservable<string> = ko.observable(null);
        inFemaleInsurancePremium: KnockoutObservable<string> = ko.observable(null);
        emFemaleInsurancePremium: KnockoutObservable<string> = ko.observable(null);
        inFemaleExemptionInsurance: KnockoutObservable<string> = ko.observable(null);
        emFemaleExemptionInsurance: KnockoutObservable<string> = ko.observable(null);
        constructor(parameter? :IRowData) {
            this.welfarePensionGrade(parameter ? parameter.welfarePensionGrade : '');
            this.standardMonthlyFee(parameter ? parameter.standardMonthlyFee : '');
            this.rewardMonthlyLowerLimit(parameter ? parameter.rewardMonthlyLowerLimit : '');
            this.rewardMonthlyUpperLimit(parameter ? parameter.rewardMonthlyUpperLimit : '');
            this.inMaleInsurancePremium(parameter ? parameter.inMaleInsurancePremium : '');
            this.emMaleInsurancePremium (parameter ? parameter.emMaleInsurancePremium : '');
            this.inMaleExemptionInsurance(parameter ? parameter.inMaleExemptionInsurance : '');
            this.emMaleExemptionInsurance (parameter ? parameter.emMaleExemptionInsurance : '');
            this.inFemaleInsurancePremium(parameter ? parameter.inFemaleInsurancePremium : '');
            this.emFemaleInsurancePremium (parameter ? parameter.emFemaleInsurancePremium : '');
            this.inFemaleExemptionInsurance (parameter ? parameter.inFemaleExemptionInsurance : '');
            this.emFemaleExemptionInsurance(parameter ? parameter.emFemaleExemptionInsurance : '');
        }
    }
    
     interface IHeaderData {
        fIndividualBurdenRatio: KnockoutObservable<string>,
        mIndividualBurdenRatio: KnockoutObservable<string>,
        fEmployeeContributionRatio: KnockoutObservable<string>,
        mEmployeeContributionRatio: KnockoutObservable<string>,
        fIndividualExemptionRate: KnockoutObservable<string>,
        mIndividualExemptionRate: KnockoutObservable<string>,
        fEmployeeExemptionRate: KnockoutObservable<string>,
        mEmployeeExemptionRate: KnockoutObservable<string>
    }
    
    export class HeaderData {
        fIndividualBurdenRatio: KnockoutObservable<string>  = ko.observable(null);
        mIndividualBurdenRatio: KnockoutObservable<string>  = ko.observable(null);
        fEmployeeContributionRatio: KnockoutObservable<string>  = ko.observable(null);
        mEmployeeContributionRatio: KnockoutObservable<string>  = ko.observable(null);
        fIndividualExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        mIndividualExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        fEmployeeExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        mEmployeeExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        
        constructor(parameter?: IHeaderData) {
             this.fIndividualBurdenRatio(parameter ? parameter.fIndividualBurdenRatio : '');
             this.mIndividualBurdenRatio(parameter ? parameter.mIndividualBurdenRatio : '');
             this.fEmployeeContributionRatio(parameter ? parameter.fEmployeeContributionRatio : '');
             this.mEmployeeContributionRatio(parameter ? parameter.mEmployeeContributionRatio : '');
             this.fIndividualExemptionRate(parameter ? parameter.fIndividualExemptionRate : '');
             this.mIndividualExemptionRate(parameter ? parameter.mIndividualExemptionRate : '');
             this.fEmployeeExemptionRate(parameter ? parameter.fEmployeeExemptionRate : '');
             this.mEmployeeExemptionRate(parameter? parameter.mEmployeeExemptionRate : '');
        }
        
    }

}