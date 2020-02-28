module nts.uk.pr.view.qmm019.k.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import validateLayout = nts.uk.pr.view.qmm019.share.model.validateLayout;
    import CategoryAtr = nts.uk.pr.view.qmm019.share.model.CategoryAtr;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        oldPrintSet: number;
        printSet: KnockoutObservable<number>;
        layoutPattern: number;
        totalLine: number;
        ctgAtr: number;
        printLineInCtg: number;
        noPrintLineInCtg: number;
        haveItemBreakdownInsite: boolean;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_K_PARAMS");

            self.itemList = ko.observableArray([
                new shareModel.BoxModel(shareModel.StatementPrintAtr.PRINT, getText("QMM019_188")),
                new shareModel.BoxModel(shareModel.StatementPrintAtr.DO_NOT_PRINT, getText("QMM019_189")),
                new shareModel.BoxModel(2, getText("QMM019_191"))
            ]);

            if(params) {
                self.printSet = ko.observable(params.printSet);
                self.oldPrintSet = params.printSet;

                self.layoutPattern = params.layoutPattern;
                self.totalLine = params.totalLine;
                self.ctgAtr = params.ctgAtr;
                self.printLineInCtg = params.printLineInCtg;
                self.noPrintLineInCtg = params.noPrintLineInCtg;
                self.haveItemBreakdownInsite = params.haveItemBreakdownInsite;
            } else {
                self.printSet = ko.observable(shareModel.StatementPrintAtr.DO_NOT_PRINT);
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            let self = this;

            if(self.printSet() == self.oldPrintSet) {
                setShared("QMM019_K_TO_A_PARAMS", {isRegistered: false});
                nts.uk.ui.windows.close();
            } else {
                if(self.printSet() != 2) {
                    let messageId = validateLayout(self.layoutPattern, self.totalLine, self.ctgAtr, self.printLineInCtg, self.noPrintLineInCtg, self.printSet());
                    if(messageId != null) {
                        nts.uk.ui.dialog.alertError({ messageId: messageId });
                    } else if((self.printSet() == shareModel.StatementPrintAtr.DO_NOT_PRINT) && ((self.ctgAtr == CategoryAtr.PAYMENT_ITEM) || (self.ctgAtr == CategoryAtr.DEDUCTION_ITEM)) && self.haveItemBreakdownInsite) {
                        nts.uk.ui.dialog.info({ messageId: "MsgQ_246" });
                    } else {
                        setShared("QMM019_K_TO_A_PARAMS", {isRegistered: true, printSet: self.printSet()});
                        nts.uk.ui.windows.close();
                    }
                } else {
                    setShared("QMM019_K_TO_A_PARAMS", {isRegistered: true, printSet: self.printSet()});
                    nts.uk.ui.windows.close();
                }
            }
        }

        cancel(){
            setShared("QMM019_K_TO_A_PARAMS", {isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }
}