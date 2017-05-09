module qmm020.k.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        displayMode: KnockoutObservable<number> = ko.observable(1);
        selectedMode: KnockoutObservable<number> = ko.observable(1);
        startYm: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());
        endYm: KnockoutObservable<Date> = ko.observable(moment.utc().toDate());

        constructor() {
            let self = this;


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

        historyProcess(): any {
            let self = this;
            debugger;
            //self.closeDialog();
        }

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

    //Previous Month 
    function previousYM(sYm: string): number {
        let preYm: number = 0;
        if (sYm.length == 6) {
            let sYear: string = sYm.substr(0, 4);
            let sMonth: string = sYm.substr(4, 2);
            //Trong truong hop thang 1 thi thang truoc la thang 12
            if (sMonth == "01") {
                preYm = parseInt((parseInt(sYear) - 1).toString() + "12");
                //Truong hop con lai thi tru di 1      
            } else {
                preYm = parseInt(sYm) - 1;
            }
        }
        return preYm;
    }

}