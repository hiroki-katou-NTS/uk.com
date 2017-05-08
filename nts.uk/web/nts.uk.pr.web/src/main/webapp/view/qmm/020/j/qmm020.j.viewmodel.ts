module qmm020.j.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        displayMode: KnockoutObservable<number> = ko.observable(1);
        startYm: KnockoutObservable<number> = ko.observable(197001);
        endYm: KnockoutObservable<number> = ko.observable(197001);
        endDate: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());
        baseDate: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());
        startDate: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());
        selectedMode: KnockoutObservable<number> = ko.observable(1);
        txtCopyHistory: KnockoutObservable<string> = ko.observable(undefined);
        constructor() {
            var self = this;
            // display mode
            self.displayMode(nts.uk.ui.windows.getShared('J_MODE') || 1);

            // close dialog if dialog hasn't param 
            if (!nts.uk.ui.windows.getShared("J_BASEDATE")) {
                self.closeDialog();
                return;
            }

            if (typeof nts.uk.ui.windows.getShared("J_BASEDATE") == "number") {
                self.startYm(nts.uk.ui.windows.getShared("J_BASEDATE") || 197001);
            } else {
                self.startYm(nts.uk.ui.windows.getShared("J_BASEDATE")[0] || 197001);
                self.endYm(nts.uk.ui.windows.getShared("J_BASEDATE")[1] || 197001);
            }
            // resize window
            self.displayMode.subscribe((v) => {
                nts.uk.ui.windows.getSelf().setWidth(490);
                if (v == 2) {
                    nts.uk.ui.windows.getSelf().setHeight(420);
                } else {
                    nts.uk.ui.windows.getSelf().setHeight(300);
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

        createHistoryDocument() {
            let self = this;
            nts.uk.ui.windows.setShared('J_RETURN', {
                startDate: self.startDate(),
                endDate: self.endDate(),
                baseDate: self.baseDate(),
                selectedMode: self.selectedMode(),
            });
            self.closeDialog();
        }

        closeDialog() { nts.uk.ui.windows.close(); }
    }

    enum Error {
        ER023 = <any>"履歴の期間が重複しています。"
    }
}