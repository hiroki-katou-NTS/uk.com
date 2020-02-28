module nts.uk.pr.view.qmm019.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import validateLayout = nts.uk.pr.view.qmm019.share.model.validateLayout;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        printSet: KnockoutObservable<number>;
        layoutPattern: number;
        totalLine: number;
        ctgAtr: number;
        printLineInCtg: number;
        noPrintLineInCtg: number;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_J_PARAMS");

            if(params) {
                self.layoutPattern = params.layoutPattern;
                self.totalLine = params.totalLine;
                self.ctgAtr = params.ctgAtr;
                self.printLineInCtg = params.printLineInCtg;
                self.noPrintLineInCtg = params.noPrintLineInCtg;
            }

            self.itemList = ko.observableArray([
                new shareModel.BoxModel(shareModel.StatementPrintAtr.PRINT, getText("QMM019_188")),
                new shareModel.BoxModel(shareModel.StatementPrintAtr.DO_NOT_PRINT, getText("QMM019_189")),
            ]);

            self.printSet = ko.observable(shareModel.StatementPrintAtr.PRINT);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            let self = this;

            let messageId = validateLayout(self.layoutPattern, self.totalLine, self.ctgAtr, self.printLineInCtg, self.noPrintLineInCtg, self.printSet());
            if(messageId != null) {
                nts.uk.ui.dialog.alertError({ messageId: messageId });
            } else {
                setShared("QMM019_J_TO_A_PARAMS", {isRegistered: true, printSet: self.printSet()});
                nts.uk.ui.windows.close();
            }
        }

        cancel(){
            setShared("QMM019_J_TO_A_PARAMS", {isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }
}