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
            let dataSetShare = self.getData();
            nts.uk.ui.windows.setShared("extractionDaily", dataSetShare);
            if (self.strMonth() > self.endMonth()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_812" });
                return false;
            } else {
                self.closeDialog();
            }
        }

        getData(): any {
            var self = this;
            var extractionId = self.getParam.extractionId;
            var extractionRange = self.getParam.extractionRange;
            var strPreviousMonth = null;
            var strCurrentMonth = null;
            var strMonth = null;
            var endPreviousMonth = null;
            var endCurrentMonth = null;
            var endMonth = null;


            strPreviousMonth = 0;
            strMonth = self.strMonth();
            if (strMonth == 0) {
                strCurrentMonth = 1;
            } else {
                strCurrentMonth = 0;
            }

            endPreviousMonth = 0;
            endMonth = self.endMonth();
            if (endMonth == 0) {
                endCurrentMonth = 1;
            } else {
                endCurrentMonth = 0;
            }


            return {
                extractionId: extractionId,
                extractionRange: extractionRange,
                strPreviousMonth: strPreviousMonth,
                strCurrentMonth: strCurrentMonth,
                strMonth: strMonth,
                endPreviousMonth: endPreviousMonth,
                endCurrentMonth: endCurrentMonth,
                endMonth: endMonth
            };
        }
        checkPeriod(): boolean {
            var self = this;
            if (self.strMonth() > self.endMonth()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_812" });
                return false;
            } else {
                return true;
            }
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }


    }
    export interface ExtractionMonthDto {
        extractionId: string;
        extractionRange: number;
        strPreviousMonth?: number;
        strCurrentMonth?: number;
        strMonth?: number;
        endPreviousMonth?: number;
        endCurrentMonth?: number;
        endMonth?: number;
    }

}



