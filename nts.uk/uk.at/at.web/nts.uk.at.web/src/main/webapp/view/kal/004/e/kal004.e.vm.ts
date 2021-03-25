module nts.uk.com.view.kal004.e.viewmodel {
    import getText = nts.uk.resource.getText;
    import share = nts.uk.at.view.kal004.share.model;
    
    export class ScreenModel {
        getCategoryId: KnockoutObservable<number>;
        getCategoryName: KnockoutObservable<string>;
        enable: boolean;
        dateSpecify: KnockoutObservableArray<any>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        strComboDay: KnockoutObservableArray<any>;
        endComboDay: KnockoutObservableArray<any>;

        //start
        strSelected: KnockoutObservable<number>;
        strDay: KnockoutObservable<number>;
        strMonth: KnockoutObservable<number>;
        strYearSpecifiedType: KnockoutObservable<number>; 
        strComboYearSpecifiedType: KnockoutObservableArray<any>;

        //End
        endSelected: KnockoutObservable<number>;
        endDay: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        endFromStrMonth: KnockoutObservable<number>;
        endComboFromStrMonth: KnockoutObservableArray<any>;

        getParam: share.ExtractionEDto;

        constructor() {
            var self = this;
            self.enable = true;
            self.dateSpecify = ko.observableArray([
               {value: 0, name: getText("KAL004_63")},
               {value: 1, name: getText("KAL004_32")}
            ]);
            
            self.strComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endComboFromStrMonth = ko.observableArray(__viewContext.enums.ExtractFromStartMonth);
            self.strComboYearSpecifiedType = ko.observableArray(__viewContext.enums.YearSpecifiedType);
            
            self.getParam = nts.uk.ui.windows.getShared("extractionScheYearDto");
            self.getCategoryName = nts.uk.ui.windows.getShared("categoryName");
            self.getCategoryId = ko.observable(nts.uk.ui.windows.getShared("categoryId"));
           
            //start
            self.strYearSpecifiedType = ko.observable(self.getParam.strYearSpecifiedType);
            self.strSelected = ko.observable(self.getParam.strSpecify);
            self.strDay = ko.observable(self.getParam.strDay);
            self.strMonth = ko.observable(self.getParam.strMonth);
            
            //End
            self.endSelected = ko.observable(self.getParam.endSpecify);
            self.endDay = ko.observable(self.getParam.endDay);
            self.endMonth = ko.observable(self.getParam.endMonth);
            self.endFromStrMonth = ko.observable(self.getParam.endFromStrMonth);
            
            self.registerClearInputError();
        }
        
        /**
         * Apply setting
         */
        btnDecide(): any {
            var self = this;
            if (self.strSelected() == 0) {
                $(".input-str").trigger("validate");
            }
            
            if (self.endSelected() == 0) {
                $(".input-end").trigger("validate");
            }
            
            if ($(".nts-input").ntsError("hasError")) {
                return;
            }
                
            if (self.checkPeriod()) {
                let dataSetShare = self.getData();
                nts.uk.ui.windows.setShared("extractionScheYear", dataSetShare);
                self.cancel_Dialog();
            }
        }

        getData(): any {
            var self = this;
            var extractionId = self.getParam.extractionId;
            var extractionRange = self.getParam.extractionRange;
            var strSpecify = self.strSelected();
            var strPreviousDay = null;
            var strMakeToDay = null;
            var strDay = null;
            var strPreviousMonth = null;
            var strCurrentMonth = null;
            var strMonth = null;
            var strYearSpecifiedType = null;
            var endSpecify = self.endSelected();
            var endPreviousDay = null;
            var endMakeToDay = null;
            var endDay = null;
            var endPreviousMonth = null;
            var endCurrentMonth = null;
            var endMonth = null;
            var endFromStrMonth = self.endFromStrMonth();
            
            //start
            if (self.strSelected() == 0) {
                strDay = self.strDay();
                if (strDay == 0) {
                    strMakeToDay = 1;
                } else {
                    strMakeToDay = 0;
                }
            } else {
                strPreviousMonth = 0;
                strMonth = self.strMonth();
                if (strMonth == 0) {
                    strCurrentMonth = 1;
                } else {
                    strCurrentMonth = 0;
                }
            }
            //end
            if (self.endSelected() == 0) {
                endDay = self.endDay();
                if (endDay == 0) {
                    endMakeToDay = 1;
                } else {
                    endMakeToDay = 0;
                }
            } else {
                endPreviousMonth = 0;
                endMonth = self.endMonth();
                if (endMonth == 0) {
                    endCurrentMonth = 1;
                } else {
                    endCurrentMonth = 0;
                }
            }

            return {
                extractionId: extractionId,
                extractionRange: extractionRange,
                strSpecify: strSpecify,
                strMakeToDay: strMakeToDay,
                strDay: strDay,
                strPreviousMonth: strPreviousMonth,
                strCurrentMonth: strCurrentMonth,
                strMonth: strMonth,
                strYearSpecifiedType: self.strYearSpecifiedType(),
                endSpecify: endSpecify,
                endMakeToDay: endMakeToDay,
                endDay: endDay,
                endPreviousMonth: endPreviousMonth,
                endCurrentMonth: endCurrentMonth,
                endMonth: endMonth,
                endFromStrMonth: endFromStrMonth,
            };
        }
        
        /**
         * Check pattern setting
         */
        checkPeriod(): boolean {
            var self = this;
            
            let checkPatternScheYear = self.checkPatternScheduleYear();
            if (!_.isNil(checkPatternScheYear)) {
                nts.uk.ui.dialog.alertError({ messageId: checkPatternScheYear });
                return false;
            }
            
            return true;
        }

        /**
         * 期間選択エラーチェック一覧
         * <CATEGORY=SCHEDULE_YEAR>
         */
        checkPatternScheduleYear() {
            let self = this;
            // (a）開始区分＝「本年月」　AND　終了区分＝「月数」
            if (self.strSelected() == 0 && self.endSelected() == 0) {
                return null;
            }
            
            // (b）開始区分＝「開始月」　AND　終了区分＝「終了月」 AND　開始の月　＋　終了の月　＞　12
            //      （例）１ヶ月前～11ヶ月先⇒NG
            //          １ヶ月前～10ヶ月先⇒OK
            let sumMonth = self.strMonth() + self.endMonth();
            if (self.strMonth() > 0) {
                sumMonth = sumMonth + 1;
            }
            if (self.strSelected() == 1 && self.endSelected() == 1 && sumMonth > 12) {
                return "Msg_814";
            }
            
            // (c）開始区分＝「開始月」　AND　終了区分＝「月数」
            if (self.strSelected() == 1 && self.endSelected() == 0) {
                return null;
            }
            
            // (d）開始区分＝「本年月」　AND　終了区分＝「終了月」
            if (self.strSelected() == 0 && self.endSelected() == 1) {
                return null;
            }
            
            return null;
        }
        
        /**
         * Clear error input when change radio
         */
        registerClearInputError() {
            let self = this;
            self.strSelected.subscribe((value) => {                
               $(".input-str").ntsError('clear');
            });
            
            self.endSelected.subscribe((value) => {                
               $(".input-end").ntsError('clear');
            });
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
    }
    
    /**
     * アラームリストで指定する年の種類
     */
    export enum YearSpecifiedType {
         // 本年
        FISCAL_YEAR = 0,
        // 本年度
        CURRENT_YEAR = 1    
    }
}
