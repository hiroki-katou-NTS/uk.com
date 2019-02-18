module nts.uk.pr.view.qmm019.l.viewmodel {
    import getText = nts.uk.resource.getText;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import validateLayout = nts.uk.pr.view.qmm019.share.model.validateLayout;
    import CategoryAtr = nts.uk.pr.view.qmm019.share.model.CategoryAtr;

    export class ScreenModel {

        itemList: KnockoutObservableArray<shareModel.BoxModel>;
        printSet: KnockoutObservable<number>;
        layoutPattern: number;
        totalLine: number;
        ctgAtr: number;
        printLineInCtg: number;
        noPrintLineInCtg: number;
        isAddCategory: boolean;

        constructor() {
            let self = this;
            let params = getShared("QMM019_A_TO_L_PARAMS");

            if(params) {
                self.layoutPattern = params.layoutPattern;
                self.totalLine = params.totalLine;
                self.ctgAtr = params.ctgAtr;
                self.printLineInCtg = params.printLineInCtg;
                self.noPrintLineInCtg = params.noPrintLineInCtg;
                self.isAddCategory = params.isAddCategory;

                if(params.isAddCategory) {
                    self.itemList = ko.observableArray([
                        new shareModel.BoxModel(shareModel.StatementPrintAtr.PRINT, getText("QMM019_194")),
                        new shareModel.BoxModel(shareModel.StatementPrintAtr.DO_NOT_PRINT, getText("QMM019_195"))
                    ]);
                } else {
                    self.itemList = ko.observableArray([
                        new shareModel.BoxModel(shareModel.StatementPrintAtr.PRINT, getText("QMM019_194")),
                        new shareModel.BoxModel(shareModel.StatementPrintAtr.DO_NOT_PRINT, getText("QMM019_195")),
                        new shareModel.BoxModel(2, getText("QMM019_196"))
                    ]);
                }
            }

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

            if(self.printSet() != 2) {
                let messageId;

                if(self.isAddCategory) {
                    messageId = validateLayout(self.layoutPattern, self.totalLine, self.ctgAtr, self.printLineInCtg, self.noPrintLineInCtg, self.printSet());
                } else {
                    messageId = validateLayout(self.layoutPattern, self.totalLine - 1, self.ctgAtr, self.printLineInCtg + self.noPrintLineInCtg - 1, self.printLineInCtg + self.noPrintLineInCtg - 1, self.printSet());
                }

                if(messageId != null) {
                    nts.uk.ui.dialog.alertError({ messageId: messageId });
                } else {
                    setShared("QMM019_L_TO_A_PARAMS", {isRegistered: true, printSet: self.printSet()});
                    nts.uk.ui.windows.close();
                }
            } else {
                setShared("QMM019_L_TO_A_PARAMS", {isRegistered: true, printSet: self.printSet()});
                nts.uk.ui.windows.close();
            }
        }

        cancel(){
            setShared("QMM019_L_TO_A_PARAMS", {isRegistered: false});
            nts.uk.ui.windows.close();
        }
    }
}