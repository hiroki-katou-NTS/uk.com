module nts.uk.pr.view.qmm020.j.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = qmm020.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        isFirst:              KnockoutObservable<boolean> = ko.observable(true);
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        startYearMonth:         KnockoutObservable<number> = ko.observable();

        endYearMonth:           KnockoutObservable<number> = ko.observable(999912);
        modeScreen : KnockoutObservable<number> = ko.observable(MODE_SCREEN.MODE_ONE);
        constructor(){


        }
        submit(){

        }
        cancel(){
            close();
        }
        getModeScreen(){
            let self = this;
            let params = getShared('CMF002_Y_PROCESINGID');
            if(params.modeScreen() == USAGE_MASTER.DEPARMENT && params.modeScreen() == USAGE_MASTER.POSITION || params.modeScreen() == USAGE_MASTER.DEPARMENT &&  params.modeScreen() != USAGE_MASTER.INDIVIDUAL ){
                self.modeScreen(MODE_SCREEN.MODE_ONE);
            }
            else{
                if(params.modeScreen() == USAGE_MASTER.DEPARMENT || params.modeScreen() == USAGE_MASTER.POSITION){
                    self.modeScreen(MODE_SCREEN.MODE_TWO);
                }
                else{
                    self.modeScreen(MODE_SCREEN.MODE_THREE);
                }
            }
        }
        checkValidate(){
            let self = this;
            let params = getShared('CMF002_Y_PROCESINGID');
            if(params.isPerson){
                if(self.startYearMonth() > params.endYearMonth && self.startYearMonth() <= self.endYearMonth()){
                    let data :any = {
                            startYearMonth : self.startYearMonth(),

                    }
                    setShared("PARAMESE_SCREENJ_OUTPUT",data);
                }
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
        MODE_ONE = 1,
        MODE_TWO = 2,
        MODE_THREE = 3
    }
    export enum USAGE_MASTER {
        /*"部門"*/
        DEPARMENT = 0,
        /*雇用*/
        EMPLOYEE = 1,
        /*分類*/
        CLASSIFICATION = 2,
        /*職位*/
        POSITION = 3,
        /*"給与分類"*/
        SALARY = 4,
        /* 個人*/
        INDIVIDUAL = 5
    }
}