module qmm020.j.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        displayMode: KnockoutObservable<number> = ko.observable(1);
        startYm: KnockoutObservable<number> = ko.observable(197001);
        endYm: KnockoutObservable<number> = ko.observable(197001);
        endDate: KnockoutObservable<Date> = ko.observable(new Date());
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        startDate: KnockoutObservable<Date> = ko.observable(new Date());
        selectedMode: KnockoutObservable<number> = ko.observable(2);
        txtCopyHistory: KnockoutObservable<string> = ko.observable(undefined);
        constructor() {
            var self = this;
            let dto: IDTOModel = nts.uk.ui.windows.getShared("J_DATA");

            // close dialog if dialog hasn't param 
            if (!dto) {
                self.closeDialog();
                return;
            }

            // observable value
            let startYm = nts.uk.time.parseYearMonth(dto.startYm);
            if (startYm.success) {
                self.startYm(dto.startYm);
                self.startDate(moment.utc(Date.UTC(startYm.year, startYm.month, 0)).toDate());
            }

            let endYm = nts.uk.time.parseYearMonth(dto.endYm);
            if (endYm.success) {
                self.endYm(dto.endYm);
                self.endDate(moment.utc(Date.UTC(endYm.year, endYm.month, 0)).toDate());
            }

            // display mode
            self.displayMode(dto.displayMode || 1);

            // resize window
            self.displayMode.subscribe((v) => {
                if (v == 2) {
                    nts.uk.ui.windows.getSelf().setHeight(420);
                } else {
                    nts.uk.ui.windows.getSelf().setHeight(300);
                }

                if (v == 3) {
                    nts.uk.ui.windows.getSelf().setWidth(500);
                } else {
                    nts.uk.ui.windows.getSelf().setWidth(490);
                }

                if (self.displayMode() != 3) {
                    self.txtCopyHistory("最新の履歴（" + nts.uk.time.formatYearMonth(self.startYm()) + "）から引き継ぐ");
                } else {
                    self.txtCopyHistory("最新の履歴（" + nts.uk.time.formatYearMonth(self.startYm()) + "~" + nts.uk.time.formatYearMonth(self.endYm()) + "）から引き継ぐ");
                }
            });

            // trigger resize window
            self.displayMode.valueHasMutated();
        }

        validate($root): boolean {
            let self = this;
            return $root.errors.isEmpty();
        }

        createHistoryDocument() {
            let self = this, model: IDTOModel = {};
            switch (self.displayMode()) {
                case 1:
                    model.startDate = parseInt(moment.utc(self.startDate()).format("YYYYMM"));
                    model.selectedMode = self.selectedMode();
                    break;
                case 2:
                    model.startDate = parseInt(moment.utc(self.startDate()).format("YYYYMM"));
                    model.baseDate = self.baseDate();
                    model.selectedMode = self.selectedMode();
                    break;
                case 3:
                    model.startDate = parseInt(moment.utc(self.startDate()).format("YYYYMM"));
                    model.endDate = parseInt(moment.utc(self.endDate()).format("YYYYMM"));
                    model.selectedMode = self.selectedMode();
                    break;
            }
            nts.uk.ui.windows.setShared('J_RETURN', model);
            self.closeDialog();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    interface IDTOModel {
        displayMode?: number;
        startYm?: number;
        endYm?: number;
        startDate?: any;
        endDate?: any;
        baseDate?: Date;
        selectedMode?: number;
    }

    enum Error {
        ER023 = <any>"履歴の期間が重複しています。"
    }
}