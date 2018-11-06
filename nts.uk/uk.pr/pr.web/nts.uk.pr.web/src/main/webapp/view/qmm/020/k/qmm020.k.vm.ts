module nts.uk.pr.view.qmm020.k.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = qmm020.share.model;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm020.k.service;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        isFirst:              KnockoutObservable<boolean> = ko.observable(null);
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        startYearMonthPeriod:         KnockoutObservable<number> = ko.observable();
        startYearMonthMasterDate:         KnockoutObservable<number> = ko.observable();
        endYearMonthPeriod:           KnockoutObservable<number> = ko.observable(999912);
        modeScreen : KnockoutObservable<number> = ko.observable(null);
        isUpdate : KnockoutObservable<boolean> = ko.observable(null);
        params : KnockoutObservable<any> = ko.observable(null);
        constructor(){
            let self = this;
            let params = getShared(model.PARAMETERS_SCREEN_K.INPUT);
            if(params == null || params == undefined)
                return;
            self.innitView(params);

        }
        submit(){
            let self = this;
            let data : any = {
                cid: '',
                historyID : self.params.historyID,
                startYearMonth : self.startYearMonthPeriod,
                endYearMonth : self.endYearMonthPeriod,
                modeEditHistory : self.methodEditing,
                type : self.params.modeScreen,
                masterCode : self.params.masterCode,
                isUpdate : self.isUpdate()
            };
            service.deleteStateCorrelationHis(data).done(()=>{
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    close();
                });
            }).fail((err) =>{
                if(err)
                    dialog.alertError(err);
            });
        }
        innitView(params:any){
            let self = this;
            self.params(params);
            self.modeScreen(self.getMode(self.params().modeScreen));
            self.startYearMonthMasterDate( self.params().startYearMonthMasterDate);
            self.isUpdate((self.startYearMonthPeriod() > self.startYearMonthMasterDate())?true :false);
            self.isFirst(self.params().isFirst);
        }
        cancel(){
            close();
        }
        isCanPickDate(){
            let self = this;
            if(self.methodEditing() == EDIT_METHOD.DELETE){
                return false;
            }
            return true;
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
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM020_69')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM020_70'))
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