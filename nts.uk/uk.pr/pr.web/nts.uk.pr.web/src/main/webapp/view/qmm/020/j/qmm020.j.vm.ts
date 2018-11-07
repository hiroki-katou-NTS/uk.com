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
        transferMethod:          KnockoutObservable<number> = ko.observable(1);
        startYearMonthPeriod: KnockoutObservable<number> = ko.observable();
        startYearMonthMasterDate: KnockoutObservable<number> = ko.observable();
        endYearMonthPeriod: KnockoutObservable<number> = ko.observable(999912);
        modeScreen : KnockoutObservable<number> = ko.observable(null);
        height : KnockoutObservable<number> = ko.observable(null;
        params : any = null;
        constructor(){

            let params = getShared(model.PARAMETERS_SCREEN_J.INPUT);
            if (params == null || params === undefined) {
                return;
            }
            this.params = params;
            this.modeScreen(this.getMode(this.params.modeScreen));
            if(this.modeScreen() == MODE_SCREEN.MODE_ONE){
                let windowSize = nts.uk.ui.windows.getSelf();
                windowSize.$dialog.height(250);
            }


        }
        submit(){
            let self = this;
            if(self.params.isPerson){
                if (self.startYearMonthPeriod() > self.params.endYearMonth && self.startYearMonthPeriod() <= self.endYearMonthPeriod()) {
                    let data :any = {
                        start: self.startYearMonthPeriod(),
                        end: self.endYearMonthPeriod(),
                        baseDate: self.startYearMonthMasterDate(),
                        transferMethod: self.transferMethod()
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
                    start: self.startYearMonthPeriod(),
                    end: self.endYearMonthPeriod(),
                    baseDate: self.startYearMonthMasterDate(),
                    transferMethod: self.transferMethod()
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
        getMode(modeScreen : number ){
            switch (modeScreen) {
                case model.MODE_SCREEN.INDIVIDUAL : {
                    return 3;
                }
                case model.MODE_SCREEN.DEPARMENT : {
                    return 2;
                }
                case model.MODE_SCREEN.POSITION : {
                    return 2;
                }
                default : return 1;
            }
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