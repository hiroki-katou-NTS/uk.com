module nts.uk.pr.view.qmm020.j.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = qmm020.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import currentScreen = nts.uk.ui.windows.getSelf;
    export class ScreenModel {
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        isFirst:              KnockoutObservable<boolean> = ko.observable(true);
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        startYearMonthPeriod: KnockoutObservable<number> = ko.observable();
        startYearMonthMasterDate: KnockoutObservable<number> = ko.observable();
        endYearMonthPeriod: KnockoutObservable<number> = ko.observable(999912);
        modeScreen : KnockoutObservable<number> = ko.observable(null);
        height : KnockoutObservable<number> = ko.observable(200);
        params : any = null;
        constructor(){
            if(1 == MODE_SCREEN.MODE_ONE){
                let windowSize = nts.uk.ui.windows.getSelf();
                windowSize.$dialog.height(200);
            }
            let params = getShared(model.PARAMETERS_SCREEN_J.INPUT);
            if (params == null || params === undefined) {
                return;
            }
            this.params = params;
            this.modeScreen(this.params.modeScreen);

        }
        submit(){
            let self = this;
            if(self.params.isPerson){
                if (self.startYearMonthPeriod() > self.params.endYearMonth && self.startYearMonthPeriod() <= self.endYearMonthPeriod()) {
                    let data :any = {
                        startYearMonth: self.startYearMonthPeriod(),
                        endYearMonth: self.endYearMonthPeriod(),
                        startYearMonthMasterDate: self.startYearMonthMasterDate(),
                        methodEditing: self.methodEditing()
                    };
                    setShared(model.PARAMETERS_SCREEN_J.OUTPUT,data);

                }
                else {
                    nts.uk.ui.dialog.info({messageId: 'Msg_16'});
                }
                close();
            }
            if (self.startYearMonthPeriod() > self.params.endYearMonth) {
                let data: any = {
                    startYearMonth: self.startYearMonthPeriod(),
                    endYearMonth: self.endYearMonthPeriod(),
                    startYearMonthMasterDate: self.startYearMonthMasterDate(),
                    methodEditing: self.methodEditing()
                };
                setShared("PARAMESE_SCREENJ_OUTPUT", data);
            }
            else {
                nts.uk.ui.dialog.info({messageId: 'Msg_16'});
            }
            close();
        }
        cancel(){
            close();
        }


    }
    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM020_59')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM020_60'))
        ];
    }
    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }
    export enum MODE_SCREEN {
        /* When another screen open*/
        MODE_ONE = 1,
        /* When screen D, F open */
        MODE_TWO = 2,
        /*When screen H open*/
        MODE_THREE = 3
    }


}