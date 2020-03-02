module nts.uk.pr.view.qmm020.j.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = qmm020.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod(false));
        isFirst:              KnockoutObservable<boolean> = ko.observable(true);
        transferMethod:          KnockoutObservable<number> = ko.observable(0);
        startYearMonthPeriod: KnockoutObservable<number> = ko.observable(null);
        startDateMaster: KnockoutObservable<string> = ko.observable();
        endYearMonthPeriod: KnockoutObservable<number> = ko.observable(999912);
        modeScreen : KnockoutObservable<number> = ko.observable(null);
        height: KnockoutObservable<number> = ko.observable(null);
        params: KnockoutObservable<any> = ko.observable(null);

        constructor(){
            let self = this;
            let params = getShared(model.PARAMETERS_SCREEN_J.INPUT);
            if (params == null || params === undefined) {
                return;
            }
            self.initView(params);
        }
        submit(){
            let self = this;
            self.validate();
            if(errors.hasError()){
                return;
            }
            if(self.params().isPerson){
                if (self.startYearMonthPeriod() > self.params().endYearMonth && self.startYearMonthPeriod() <= self.endYearMonthPeriod()) {
                    let data :any = {
                        start: self.startYearMonthPeriod(),
                        end: self.endYearMonthPeriod(),
                        baseDate: self.startDateMaster(),
                        transferMethod: self.transferMethod()
                    };
                    setShared(model.PARAMETERS_SCREEN_J.OUTPUT,data);
                    close();
                }
                else {
                    nts.uk.ui.dialog.info({messageId: 'Msg_106'}).then(()=>{
                        $('#J1_3').focus();
                    });

                    return;
                }
            }
            if (self.startYearMonthPeriod() > self.params().startYearMonth) {
                let data: any = {
                    start: self.startYearMonthPeriod(),
                    end: self.endYearMonthPeriod(),
                    baseDate: self.startDateMaster(),
                    transferMethod: self.transferMethod()
                };
                setShared(model.PARAMETERS_SCREEN_J.OUTPUT, data);
                close();
            }
            else {
                nts.uk.ui.dialog.info({messageId: 'Msg_106'}).then(()=>{
                    $('#J1_3').focus();
                });

            }
        }
        initView(params: any) {
            let self = this;
            self.params(params);
            self.modeScreen(self.getMode(self.params().modeScreen));
            if (self.modeScreen() == nts.uk.pr.view.qmm020.j.viewmodel.MODE_SCREEN.MODE_TWO) {
                self.startDateMaster(self.params().startDateMaster);
            }
            self.isFirst(self.params().isFirst);
            self.startYearMonthPeriod(self.params().startYearMonth == 0 ? null : self.params().startYearMonth);
            self.endYearMonthPeriod(self.params().endYearMonth == 0 ? null : self.params().endYearMonth);
            if(self.isFirst()){
                self.itemList(getHistoryEditMethod(true));
            }
            if(self.startYearMonthPeriod()!= null){
                let display = self.convertMonthYearToString(self.startYearMonthPeriod());
                if(self.modeScreen() == MODE_SCREEN.MODE_THREE){
                    display = self.convertMonthYearToString(self.startYearMonthPeriod()) + getText('QMM020_56') + self.convertMonthYearToString(self.endYearMonthPeriod());
                }
                self.itemList()[0] = new model.ItemModel(EDIT_METHOD.COPY, getText('QMM020_59', [display]));
            }
            else{
                self.itemList()[0] = new model.ItemModel(EDIT_METHOD.COPY, getText('QMM020_59', ['']));
                self.transferMethod = ko.observable(1);
            }
        }
        cancel(){
            close();
        }
        getMode(modeScreen : number ){
            switch (modeScreen) {
                case model.MODE_SCREEN.INDIVIDUAL : {
                    return MODE_SCREEN.MODE_THREE;
                }
                case model.MODE_SCREEN.DEPARMENT : {
                    return MODE_SCREEN.MODE_TWO;
                }
                case model.MODE_SCREEN.POSITION : {
                    return MODE_SCREEN.MODE_TWO;
                }
                default : return MODE_SCREEN.MODE_ONE;
            }
        }
        validate() {
            $("#J1_3").trigger("validate");
            $("#J1_12").trigger("validate");
            $("#J1_13").trigger("validate");
        }

        convertMonthYearToString(yearMonth: any) {
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
    }

    export function getHistoryEditMethod(isFirts :boolean): Array<model.ItemModel> {
        if(isFirts){
            return [
                new model.ItemModel(EDIT_METHOD.COPY, getText('QMM020_59'))
            ];
        }
        return [
            new model.ItemModel(EDIT_METHOD.COPY, getText('QMM020_59')),
            new model.ItemModel(EDIT_METHOD.DONTCOPY, getText('QMM020_60'))
        ];
    }
    export enum EDIT_METHOD {
        COPY = 0,
        DONTCOPY = 1
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