module qmm020.k.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        displayMode: KnockoutObservable<number> = ko.observable(1);
        selectedMode: KnockoutObservable<number> = ko.observable(2);
        startYm: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());
        endYm: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());

        constructor() {
            let self = this;
            let dto: IDTOModel = nts.uk.ui.windows.getShared("K_DATA");

            // close dialog if dialog hasn't param 
            if (!dto) {
                self.closeDialog();
                return;
            }
            // observable value
            let startYm = nts.uk.time.parseYearMonth(dto.startYm);
            if (startYm.success) {
                self.startYm(moment.utc(Date.UTC(startYm.year, startYm.month, 0)).toDate());
            }

            let endYm = nts.uk.time.parseYearMonth(dto.endYm);
            if (endYm.success) {
                self.endYm(moment.utc(Date.UTC(endYm.year, endYm.month, 0)).toDate());
            }

            // display mode
            self.displayMode(dto.displayMode || 1);

            // resize window
            self.displayMode.subscribe((v) => {
                if (v == 2) {
                    nts.uk.ui.windows.getSelf().setHeight(340);
                } else {
                    nts.uk.ui.windows.getSelf().setHeight(300);
                }
                nts.uk.ui.windows.getSelf().setWidth(490);
            });

            // trigger resize window
            self.displayMode.valueHasMutated();
        }


        // push data to parent view
        historyProcess(): any {
            let self = this, model: IDTOModel = {};

            switch (self.displayMode()) {
                case 1:
                case 2:
                    model.selectedMode = self.selectedMode();
                    model.startYm = parseInt(moment.utc(self.startYm()).format("YYYYMM"));
                    break;
                case 3:
                    model.selectedMode = self.selectedMode();
                    model.startYm = parseInt(moment.utc(self.startYm()).format("YYYYMM"));
                    model.endYm = parseInt(moment.utc(self.endYm()).format("YYYYMM"));
                    break;
            }

            nts.uk.ui.windows.setShared('K_RETURN', model);
            self.closeDialog();
        }

        // cancel action
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    interface IDTOModel {
        displayMode?: number;
        selectedMode?: number;
        startYm?: number;
        endYm?: number;
    }

    enum Error {
        ER023 = <any>"履歴の期間が重複しています。",
    }
}