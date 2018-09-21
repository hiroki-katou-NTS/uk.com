module nts.uk.pr.view.qmm008.f {
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {

            dataList: KnockoutObservableArray<RowData>;
            header : KnockoutObservable<HeaderData> = ko.observable(new HeaderData ());
            constructor() {
                var self = this;
                self.dataList = ko.observableArray([]);
                let command = { historyId: 'e091445c-a610-4362-a4e9-fa89db856fd2',date : 201802 };
                nts.uk.pr.view.qmm008.f.service.init(command).done(function(response) {
                    for (var i = 0; i < response.cusWelfarePensions.length; i++) {
                        self.dataList.push(response.cusWelfarePensions[i]);
                    } 
                    self.header(response.insuranceRate);
                    console.log(response.insuranceRate);
                });

                $("#fixed-table").ntsFixedTable({ height: 300, width: 900 });
            }
            
            
            /**
            *  update
            */
           
            private update(): void {
                let self = this;
                let command = {
                    cusWelfarePensions: ko.toJS(self.dataList()),
                    historyId: 'e091445c-a610-4362-a4e9-fa89db856fd2'
                };
                nts.uk.pr.view.qmm008.f.service.update(command).done(function(response) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {

                    });
                }
               
           }
            
            private countReview() :void {
                let self = this;
                let command = { historyId: 'e091445c-a610-4362-a4e9-fa89db856fd2', date: 201802 };
                nts.uk.pr.view.qmm008.f.service.count(command).done(function(response) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.dataList([]);
                        for (var i = 0; i < response.cusWelfarePensions.length; i++) {
                            self.dataList.push(response.cusWelfarePensions[i]);
                        }
                        self.header(response.insuranceRate);

                    });
                }
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
        mindividualBurdenRatio: KnockoutObservable<string>,
        findividualBurdenRatio: KnockoutObservable<string>,
        memployeeContributionRatio: KnockoutObservable<string>,
        femployeeContributionRatio: KnockoutObservable<string>,
        mindividualExemptionRate: KnockoutObservable<string>,
        findividualExemptionRate: KnockoutObservable<string>,
        memployeeExemptionRate: KnockoutObservable<string>,
        femployeeExemptionRate: KnockoutObservable<string>
        
    }
    
    export class HeaderData {
        findividualBurdenRatio: KnockoutObservable<string>  = ko.observable(null);
        mindividualBurdenRatio: KnockoutObservable<string>  = ko.observable(null);
        femployeeContributionRatio: KnockoutObservable<string>  = ko.observable(null);
        memployeeContributionRatio: KnockoutObservable<string>  = ko.observable(null);
        findividualExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        mindividualExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        femployeeExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        memployeeExemptionRate: KnockoutObservable<string>  = ko.observable(null);
        
        constructor(parameter?: IHeaderData) {
             this.findividualBurdenRatio(parameter ? parameter.findividualBurdenRatio : '');
             this.mindividualBurdenRatio(parameter ? parameter.mindividualBurdenRatio : '');
             this.femployeeContributionRatio(parameter ? parameter.femployeeContributionRatio : '');
             this.memployeeContributionRatio(parameter ? parameter.memployeeContributionRatio : '');
             this.findividualExemptionRate(parameter ? parameter.findividualExemptionRate : '');
             this.mindividualExemptionRate(parameter ? parameter.mindividualExemptionRate : '');
             this.femployeeExemptionRate(parameter ? parameter.femployeeExemptionRate : '');
             this.memployeeExemptionRate(parameter? parameter.memployeeExemptionRate : '');
        }
        
    }

}