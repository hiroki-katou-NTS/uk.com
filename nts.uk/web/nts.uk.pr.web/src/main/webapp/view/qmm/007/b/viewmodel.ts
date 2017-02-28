module nts.uk.pr.view.qmm007.b {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;
        import UnitPriceHistoryDto = nts.uk.pr.view.qmm007.a.service.model.UnitPriceHistoryDto;

        export class ScreenModel {
            unitPriceHistoryModel: KnockoutObservable<any>;

            historyTakeOver: KnockoutObservable<string>;

            constructor() {
                var self = this;
                var unitPriceHistoryDto: UnitPriceHistoryDto = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                self.unitPriceHistoryModel = ko.mapping.fromJS(unitPriceHistoryDto);

                self.historyTakeOver = ko.observable('lastest');

            }

            private btnApplyClicked(): void {
                var self = this;
                nts.uk.ui.windows.setShared("childValue", ko.toJS(self.unitPriceHistoryModel));
                service.create(ko.toJS(self.unitPriceHistoryModel)).done(() => {
                    nts.uk.ui.windows.close();
                });

            }

            private btnCancelClicked(): void {
                nts.uk.ui.windows.close();
            }

        }

        export class UnitPriceHistoryModel {
            unitPriceCode: string;
            unitPriceName: string;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            constructor(
                unitPriceCode: string,
                unitPriceName: string,
                startMonth: string,
                endMonth: string) {
                this.unitPriceCode = unitPriceCode;
                this.unitPriceName = unitPriceName;
                this.startMonth = ko.observable(startMonth);
                this.endMonth = ko.observable(endMonth);
            };
        }
    }
}