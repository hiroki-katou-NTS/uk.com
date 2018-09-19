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
                   for(var i = 0 ; i < response.cusDataDtos.length ; i++) {
                       self.dataList.push(response.cusDataDtos[i]);
                   }
                   self.header(response.premiumRate);
               });
               $("#fixed-table").ntsFixedTable({ height: 300, width: 900 });
           }
           
           /**
            *  update
            */
           
           private update() : void {
               let self = this;
               let command = {
                cusDataDtos :    ko.toJS(self.dataList()) ,
                historyId : 'e091445c-a610-4362-a4e9-fa89db856fd2'
               };
               nts.uk.pr.view.qmm008.e.service.update(command).done(function(response) {
                   nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                   
                   });
               }
               
           }
           
           /**
            * count
            */
           private count() : void {
               let self = this;
               let command = {
                cusDataDtos :    ko.toJS(self.dataList()) ,
                premiumRate : ko.toJS(self.header())
               };
               nts.uk.pr.view.qmm008.e.service.count(command).done(function(response) {
                   nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                   
                   });
               }
           }
           
       }
    }
    
   interface IRowData {
        healthInsuranceGrade: KnockoutObservable<string>,
        standardMonthlyFee: KnockoutObservable<string>,
        rewardMonthlyLowerLimit: KnockoutObservable<string>,
        rewardMonthlyUpperLimit: KnockoutObservable<string>,
        inHealthInsurancePremium: KnockoutObservable<string>,
        inNursingCare: KnockoutObservable<string>,
        inSpecInsurancePremium: KnockoutObservable<string>,
        inBasicInsurancePremium: KnockoutObservable<string>,
        emHealthInsurancePremium: KnockoutObservable<string>,
        emNursingCare: KnockoutObservable<string>,
        emSpecInsurancePremium: KnockoutObservable<string>,
        emBasicInsurancePremium: KnockoutObservable<string>
   }
    
   export class RowData {
        healthInsuranceGrade: KnockoutObservable<string>  = ko.observable(null);
        standardMonthlyFee: KnockoutObservable<string>  = ko.observable(null);
        rewardMonthlyLowerLimit: KnockoutObservable<string>  = ko.observable(null);
        rewardMonthlyUpperLimit: KnockoutObservable<string>  = ko.observable(null);
        inHealthInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        inNursingCare: KnockoutObservable<string>  = ko.observable(null);
        inSpecInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        inBasicInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        emHealthInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        emNursingCare: KnockoutObservable<string>  = ko.observable(null);
        emSpecInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        emBasicInsurancePremium: KnockoutObservable<string>  = ko.observable(null);
        constructor(parameter?: IRowData) {
            this.healthInsuranceGrade(parameter ? parameter.healthInsuranceGrade : '');
            this.standardMonthlyFee(parameter ? parameter.standardMonthlyFee : '');
            this.rewardMonthlyLowerLimit(parameter ? parameter.rewardMonthlyLowerLimit : '');
            this.rewardMonthlyUpperLimit(parameter ? parameter.rewardMonthlyUpperLimit : '');
            this.inHealthInsurancePremium(parameter ? parameter.inHealthInsurancePremium : '');
            this.inNursingCare(parameter ? parameter.inNursingCare : '');
            this.inSpecInsurancePremium(parameter ? parameter.inSpecInsurancePremium : '');
            this.inBasicInsurancePremium(parameter ? parameter.inBasicInsurancePremium : '');
            this.emHealthInsurancePremium(parameter ? parameter.emHealthInsurancePremium : '');
            this.emNursingCare(parameter ? parameter.emNursingCare : '');
            this.emSpecInsurancePremium(parameter ? parameter.emSpecInsurancePremium : '');
            this.emBasicInsurancePremium(parameter ? parameter.emBasicInsurancePremium : '');
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