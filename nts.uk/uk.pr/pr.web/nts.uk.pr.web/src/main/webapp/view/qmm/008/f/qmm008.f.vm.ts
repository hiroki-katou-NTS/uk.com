module nts.uk.pr.view.qmm008.f {
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {

            dataList: KnockoutObservableArray<RowData>;
            header : KnockoutObservable<HeaderData> = ko.observable(new HeaderData ());
            officeCode: KnockoutObservable<string> = ko.observable(null);
            socialInsuranceName : KnockoutObservable<string> = ko.observable(null);
            startMonth: KnockoutObservable<string> = ko.observable(null);
            endMonth : KnockoutObservable<string> = ko.observable(null);
            displayStart: KnockoutObservable<string> = ko.observable(null);
            displayEnd : KnockoutObservable<string> = ko.observable(null);
            historyId : KnockoutObservable<string> = ko.observable(null);
            display : KnockoutObservable<Boolean> = ko.observable(true);
            isEnableBtnPdf: KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                var self = this;
                self.dataList = ko.observableArray([]);
                let dataGetShare = getShared("QMM008_F_PARAMS");
                self.officeCode(dataGetShare.selectedOffice.socialInsuranceCode);
                self.socialInsuranceName(dataGetShare.selectedOffice.socialInsuranceName);
                self.displayStart(dataGetShare.selectedHistory.displayStart);
                self.displayEnd(dataGetShare.selectedHistory.displayEnd);
                self.historyId(dataGetShare.selectedHistory.historyId);
                self.startMonth(dataGetShare.selectedHistory.startMonth);
                let command = { historyId: self.historyId(),date : self.startMonth(),socialInsuranceCode: self.officeCode() };
                nts.uk.pr.view.qmm008.f.service.init(command).done(function(response) {
                    for (var i = 0; i < response.cusWelfarePensions.length; i++) {
                        self.dataList.push(new RowData(response.cusWelfarePensions[i]));
                    } 
                    self.header(response.insuranceRate);
                    self.display(response.display);
                    let currentScreen = nts.uk.ui.windows.getSelf();

                    setTimeout(function () {
                        $(".nts-fixed-table").attr('tabindex', '6');
                        $(".nts-fixed-table").focus();
                        if(self.dataList().length > 10) {
                            if (/Edge/.test(navigator.userAgent)) {
                                $('#f3_1_container .scroll-header').addClass('edge_scroll_header');
                                $('#f3_1_2_container .scroll-header').addClass('edge_scroll_header');
                            } else {
                                $('#f3_1_container .scroll-header').addClass('ci_scroll_header');
                                $('#f3_1_2_container .scroll-header').addClass('ci_scroll_header');
                            }

                        }
                    }, 100);

                    //Fixed table
                    if (/Chrome/.test(navigator.userAgent)) {
                        $("#fixed-table1").ntsFixedTable({height: 410, width: 1160});
                        $("#fixed-table").ntsFixedTable({height: 410, width: 1160});
                    } else {
                        $("#fixed-table1").ntsFixedTable({height: 407, width: 1160});
                        $("#fixed-table").ntsFixedTable({height: 407, width: 1160});
                    }





                 });



            }

            exportExcel(): void {
                let self = this;
                let data = { historyId: self.historyId(),date : self.startMonth(),socialInsuranceCode: self.officeCode(), socialInsuranceName: self.socialInsuranceName() };
                nts.uk.ui.block.grayout();
                service.exportExcel(data).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            
            genNumber(itemNumber: any, decimalPart: any) {
               let option: any;
               if (nts.uk.text.isNullOrEmpty(decimalPart)) {
                   option = new nts.uk.ui.option.NumberEditorOption({ grouplength: 3, decimallength: 0 });

               } else {
                   option = new nts.uk.ui.option.NumberEditorOption({ grouplength: 3, decimallength: decimalPart });

               }
               return nts.uk.ntsNumber.formatNumber(itemNumber, option);
           }
            
            
            /**
            *  update
            */
           
            private update(): void {
                $('#f3_1_2_container .nts-input').trigger("validate");
                $('#f3_1_container .nts-input').trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                let self = this;
                let command = {
                    cusWelfarePensions: ko.toJS(self.dataList()),
                    historyId: self.historyId(),
                    socialInsuranceCode: self.officeCode()
                };
                nts.uk.pr.view.qmm008.f.service.update(command).done(function(response) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        setTimeout(function () {
                            $(".nts-fixed-table").attr('tabindex', '0');
                            $(".nts-fixed-table").focus();

                        }, 500);
                    });
                });
               
           }
            
            /**
             *  close dialog
             */

            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            
            private countReview(): void {
                nts.uk.ui.errors.clearAll();
                let self = this;
                let command = { historyId: self.historyId(), date: self.startMonth(), socialInsuranceCode: self.officeCode() };
                nts.uk.pr.view.qmm008.f.service.count(command).done(function(response) {
                    self.dataList([]);
                    for (var i = 0; i < response.cusWelfarePensions.length; i++) {
                        self.dataList.push(new RowData(response.cusWelfarePensions[i]));
                    }
                    self.header(response.insuranceRate);
                    setTimeout(function () {
                        $(".nts-fixed-table").attr('tabindex', '0');
                        $(".nts-fixed-table").focus();
                    }, 500);
                });
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