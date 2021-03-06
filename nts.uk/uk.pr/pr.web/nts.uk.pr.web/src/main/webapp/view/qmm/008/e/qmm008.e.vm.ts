module nts.uk.pr.view.qmm008.e {
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export module viewmodel {
       export class ScreenModel {
           dataList: KnockoutObservableArray<RowData>;
           header : KnockoutObservable<HeaderData> = ko.observable(new HeaderData ());
           officeCode: KnockoutObservable<string> = ko.observable(null);
           socialInsuranceName: KnockoutObservable<string> = ko.observable(null);
           startMonth: KnockoutObservable<string> = ko.observable(null);
           endMonth: KnockoutObservable<string> = ko.observable(null);
           displayStart: KnockoutObservable<string> = ko.observable(null);
           displayEnd: KnockoutObservable<string> = ko.observable(null);
           historyId: KnockoutObservable<string> = ko.observable(null);
           isEnableBtnPdf: KnockoutObservable<boolean> = ko.observable(false);
           constructor(){
               var self = this;
               self.dataList = ko.observableArray([]);
               let dataGetShare = getShared("QMM008_E_PARAMS");
               self.officeCode(dataGetShare.selectedOffice.socialInsuranceCode);
               self.socialInsuranceName(dataGetShare.selectedOffice.socialInsuranceName);
               self.displayStart(dataGetShare.selectedHistory.displayStart);
               self.displayEnd(dataGetShare.selectedHistory.displayEnd);
               self.historyId(dataGetShare.selectedHistory.historyId);
               self.startMonth(dataGetShare.selectedHistory.startMonth);
               let command = { historyId: self.historyId(), date: self.startMonth(),socialInsuranceCode: self.officeCode() };
               nts.uk.pr.view.qmm008.e.service.startScreen(command).done(function(response) {
                   for(var i = 0 ; i < response.cusDataDtos.length ; i++) {
                       self.dataList.push(new RowData(response.cusDataDtos[i]));
                   }
                   self.header(response.premiumRate);
                   setTimeout(function () {
                       $(".nts-fixed-table").attr('tabindex', '5');
                       $(".nts-fixed-table").focus();
                       if(self.dataList().length > 8) {
                           if (/Edge/.test(navigator.userAgent)) {
                               $('#E3_1_container .scroll-header').addClass('edge_scroll_header');
                               $('#E3_1_container .scroll-header').addClass('edge_scroll_header');
                           } else {
                               $('#E3_1_container .scroll-header').addClass('ci_scroll_header');
                               $('#E3_1_container .scroll-header').addClass('ci_scroll_header');
                           }

                       }
                   }, 100);

               });

               //Fixed table
               if (/Chrome/.test(navigator.userAgent)) {
                   $("#fixed-table").ntsFixedTable({height: 407, width: 1150});
               } else {
                   $("#fixed-table").ntsFixedTable({height: 406, width: 1150});
               }

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
           
           private update() : void {
               let self = this;
               $('#E3_1_container .nts-input').trigger("validate");
               if (nts.uk.ui.errors.hasError()) {
                   return;
               }
               block.invisible();
               let command = {
                cusDataDtos :    ko.toJS(self.dataList()) ,
                historyId : self.historyId(),
                   socialInsuranceCode   :self.officeCode()
               };
               nts.uk.pr.view.qmm008.e.service.update(command).done(function(response) {
                   nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {

                       setTimeout(function () {
                           $(".nts-fixed-table").attr('tabindex', '0');
                           $(".nts-fixed-table").focus();

                       }, 500);
                       block.clear();
                   });
               });
               
           }
           
           /**
            *  close dialog
            */

           private closeDialog(): void {
               nts.uk.ui.windows.close();
           }
           /*
           * Export excel
           * */
           public exportExcel(): void {
               let self = this;
               block.invisible();
               let data = {
                   targetStartYm: self.startMonth(),
                   hisId : self.historyId(),
                   officeCode :self.officeCode(),
                   socialInsuranceName : self.socialInsuranceName(),
                   displayStart : self.displayStart(),
                   displayEnd: self.displayEnd()
               }
               nts.uk.pr.view.qmm008.e.service.exportExcel(data)
                   .done(function () {
                       block.clear();

                   })
                   .fail(function (error) {
                       dialog.alertError({messageId: error.messageId});
                       block.clear();
                   })
                   .always(function () {
                       block.clear();
                   });
               ;
           }
           /**
            * count
            */
           private count(): void {
               nts.uk.ui.errors.clearAll();
               let self = this;
               block.invisible();
               let command = { historyId: self.historyId(), date: self.startMonth(), socialInsuranceCode: self.officeCode() };
               nts.uk.pr.view.qmm008.e.service.count(command).done(function(response) {
                   self.dataList([]);
                   for (var i = 0; i < response.cusDataDtos.length; i++) {
                       self.dataList.push(new RowData(response.cusDataDtos[i]));
                   }
                   self.header(response.premiumRate);
                   setTimeout(function () {
                       $(".nts-fixed-table").attr('tabindex', '0');
                       $(".nts-fixed-table").focus();

                   }, 500);
                   block.clear();
               });
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
        inHealthInsuranceRate: KnockoutObservable<string>,
        emBasicInsuranceRate: KnockoutObservable<string>,
        emHealthInsuranceRate: KnockoutObservable<string>,
        emLongCareInsuranceRate: KnockoutObservable<string>,
        emSpecialInsuranceRate: KnockoutObservable<string>,
        inBasicInsuranceRate: KnockoutObservable<string>,
        
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
             this.inHealthInsuranceRate(parameter ? parameter.inHealthInsuranceRate : '');
             this.emBasicInsuranceRate(parameter ? parameter.emBasicInsuranceRate : '');
             this.emHealthInsuranceRate(parameter ? parameter.emHealthInsuranceRate : '');
             this.emLongCareInsuranceRate(parameter ? parameter.emLongCareInsuranceRate : '');
             this.emSpecialInsuranceRate(parameter ? parameter.emSpecialInsuranceRate : '');
             this.inBasicInsuranceRate(parameter ? parameter.inBasicInsuranceRate : '');
             this.inLongCareInsuranceRate(parameter ? parameter.inLongCareInsuranceRate : '');
             this.inSpecialInsuranceRate(parameter? parameter.inSpecialInsuranceRate : '');
        }
        
    }
    
    
}