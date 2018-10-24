module nts.uk.com.view.kal004.d.viewmodel {


    export class ScreenModel {
        getCategoryId: KnockoutObservable<number>;
        getCategoryName: KnockoutObservable<string>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;
        strMonth: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        getParam: ExtractionMonthDto;
        constructor() {
            var self = this;

            self.strComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.getParam = nts.uk.ui.windows.getShared("extractionMonthDto");
            self.getCategoryName = nts.uk.ui.windows.getShared("categoryName");
            self.getCategoryId = ko.observable(nts.uk.ui.windows.getShared("categoryId"));
            self.strMonth = ko.observable(self.getParam.strMonth);
            self.endMonth = ko.observable(self.getParam.endMonth);
        }
        Decide(): any {
            var self = this;               
               let dataSetShare = self.getDataShare();
            nts.uk.ui.windows.setShared("extractionMonthly", dataSetShare);

            if (self.checkPeriod() == false) {
                nts.uk.ui.windows.setShared("validateMonthly", false);        
                return false;
            } else {
                 nts.uk.ui.windows.setShared("validateMonthly", true);

                self.closeDialog();
            }
        
            }
        private validateSelectMonth(startMonth: number, endMonth: number):boolean {
            let self = this;
            const month7 = 7;
            if (startMonth <= month7 && (endMonth < startMonth || endMonth > month7)) {
                return true;
            } else if (startMonth >= month7 && endMonth > startMonth) {
                return true;
            } else if (startMonth == endMonth) {
                return true;
            }
            else {
                return false;
            }
        }

        getDataShare(): ExtractionMonthDto {
            var self = this;

            return {
                extractionId: self.getParam.extractionId,
                extractionRange: self.getParam.extractionRange,
                strMonth: self.strMonth(),
                endMonth: self.endMonth()
            };
        }
        checkPeriod(): boolean {
            var self = this;
            if (self.strMonth() < self.endMonth()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_812" });
                return false;
            } else {
                return true;
            }
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }


    }
    export interface ExtractionMonthDto {
        extractionId: string;
        extractionRange: number;
        strMonth?: number;
        endMonth?: number;
    }

}



