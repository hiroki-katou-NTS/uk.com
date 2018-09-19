module nts.uk.pr.view.qmm008.e {
    export module viewmodel {
       export class ScreenModel {
           dataList: KnockoutObservableArray<RowData>;
           header : KnockoutObservable<HeaderData> = ko.observable(new HeaderData ());
           constructor(){
               var self = this;
               self.dataList = ko.observableArray([]);
               let command = { historyId: 'e091445c-a610-4362-a4e9-fa89db856fd2',date : 201802 };
               nts.uk.pr.view.qmm008.e.service.startScreen(command).done(function(response) {
                   for(var i = 0 ; i < response.responseHealthAndMonthlies.length ; i++) {
                       self.dataList.push(new RowData(response.responseHealthAndMonthlies[i].healthInsuranceGrade,
                        response.responseHealthAndMonthlies[i].standardMonthlyFee,      
                        response.responseHealthAndMonthlies[i].rewardMonthlyLowerLimit, 
                        response.responseHealthAndMonthlies[i].rewardMonthlyUpperLimit,    
                        response.responseHealthAndMonthlies[i].inHealthInsurancePremium,
                        response.responseHealthAndMonthlies[i].inNursingCare, 
                        response.responseHealthAndMonthlies[i].inSpecInsurancePremium, 
                        response.responseHealthAndMonthlies[i].inBasicInsurancePremium, 
                        response.responseHealthAndMonthlies[i].emHealthInsurancePremium,
                        response.responseHealthAndMonthlies[i].emNursingCare, 
                        response.responseHealthAndMonthlies[i].emSpecInsurancePremium, 
                        response.responseHealthAndMonthlies[i].emBasicInsurancePremium));
                       
                        
                   }
                   let data = new HeaderData(response.responseHealthInsurancePerGradeFee);
                   console.log(data);
                   self.header(data);
                   
                   
               });
               $("#fixed-table").ntsFixedTable({ height: 300, width: 900 });
           }
       }
    }
    
   
    
   export class RowData {
        healthInsuranceGrade: KnockoutObservable<string>;
        standardMonthlyFee: KnockoutObservable<string>;
        rewardMonthlyLowerLimit: KnockoutObservable<string>;
        rewardMonthlyUpperLimit: KnockoutObservable<string>;
        inHealthInsurancePremium: KnockoutObservable<string>;
        inNursingCare: KnockoutObservable<string>;
        inSpecInsurancePremium: KnockoutObservable<string>;
        inBasicInsurancePremium: KnockoutObservable<string>;
        emHealthInsurancePremium: KnockoutObservable<string>;
        emNursingCare: KnockoutObservable<string>;
        emSpecInsurancePremium: KnockoutObservable<string>;
        emBasicInsurancePremium: KnockoutObservable<string>;
        constructor(healthInsuranceGrade: string, standardMonthlyFee: string, rewardMonthlyLowerLimit: string, rewardMonthlyUpperLimit: string,
         inHealthInsurancePremium: string, inNursingCare: string, inSpecInsurancePremium: string, inBasicInsurancePremium: string, emHealthInsurancePremium: string, emNursingCare: string, 
         emSpecInsurancePremium: string, emBasicInsurancePremium: string) {
            this.healthInsuranceGrade = ko.observable(healthInsuranceGrade);
            this.standardMonthlyFee = ko.observable(standardMonthlyFee);
            this.rewardMonthlyLowerLimit = ko.observable(rewardMonthlyLowerLimit);
            this.rewardMonthlyUpperLimit = ko.observable(rewardMonthlyUpperLimit);
            this.inHealthInsurancePremium = ko.observable(inHealthInsurancePremium);
            this.inNursingCare = ko.observable(inNursingCare);
            this.inSpecInsurancePremium = ko.observable(inSpecInsurancePremium);
            this.inBasicInsurancePremium = ko.observable(inBasicInsurancePremium);
            this.emHealthInsurancePremium = ko.observable(emHealthInsurancePremium);
            this.emNursingCare = ko.observable(emNursingCare);
            this.emSpecInsurancePremium = ko.observable(emSpecInsurancePremium);
            this.emBasicInsurancePremium = ko.observable(emBasicInsurancePremium);
        }
    }
    
    interface IHeaderData {
        emBasicInsuranceRate: KnockoutObservable<string>,
        emHealthInsuranceRate: KnockoutObservable<string>,
        emLongCareInsuranceRate: KnockoutObservable<string>,
        emSpecialInsuranceRate: KnockoutObservable<string>,
        inBasicInsuranceRate: KnockoutObservable<string>,
        inHealthInsuranceRate: KnockoutObservable<string>,
        inLongCareInsuranceRate: KnockoutObservable<string>,
        inSpecialInsuranceRate: KnockoutObservable<string>
    }
    
    export class HeaderData {
        emBasicInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        emHealthInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        emLongCareInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        emSpecialInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        inBasicInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        inHealthInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        inLongCareInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        inSpecialInsuranceRate: KnockoutObservable<string>  = ko.observable(null);
        
        constructor(parameter?: IHeaderData) {
             this.emBasicInsuranceRate(parameter ? parameter.emBasicInsuranceRate : '');
             this.emHealthInsuranceRate(parameter ? parameter.emHealthInsuranceRate : '');
             this.emLongCareInsuranceRate(parameter ? parameter.emLongCareInsuranceRate : '');
             this.emSpecialInsuranceRate(parameter ? parameter.emSpecialInsuranceRate : '');
             this.inBasicInsuranceRate(parameter ? parameter.inBasicInsuranceRate : '');
             this.inHealthInsuranceRate(parameter ? parameter.inHealthInsuranceRate : '');
             this.inLongCareInsuranceRate(parameter ? parameter.inLongCareInsuranceRate : '');
             this.inSpecialInsuranceRate(parameter? parameter.inSpecialInsuranceRate : '');
        }
        
    }
    
}