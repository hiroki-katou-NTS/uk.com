module nts.uk.com.view.qmm011.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.errors;
    import model = qmm011.share.model;
    export class ScreenModel {
        startYearMonth:         KnockoutObservable<number> = ko.observable();
        endYearMonth:           KnockoutObservable<number> = ko.observable();
        startLastYearMonth:     KnockoutObservable<number> = ko.observable();
        name:                   KnockoutObservable<string> = ko.observable('');
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        selectedId:             KnockoutObservable<string> = ko.observable('');
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        insurrance:             KnockoutObservable<number> = ko.observable();
        hisId:                  KnockoutObservable<string> = ko.observable('');
        canDelete:              KnockoutObservable<boolean> = ko.observable('');
        constructor() {
            let self = this;
            self.startYearMonth();
            let params = getShared('QMM011_F_PARAMS_INPUT');
            if (params) {
                self.insurrance(params.insurrance);
                self.startYearMonth(params.startYearMonth);
                self.endYearMonth(params.endYearMonth);
                self.startLastYearMonth(params.startLastYearMonth);
                self.canDelete(params.canDelete);
                self.hisId(params.hisId);
            }
            
        }
        
        update() {
            let self = this;
            if(self.validateYearMonth()) {
                return;
            }
            
            let param: any = {
                    hisId: self.hisId(),
                    methodEditing: self.methodEditing(),
                    startMonthYear: self.startYearMonth(),
                    endMonthYear: self.endYearMonth()
            }
            block.invisible();
            if (self.insurrance() == INSURRANCE.EMPLOYMENT_INSURRANCE_RATE) {
                service.updateEmpInsurHis(param).done(() => {
                    if (self.methodEditing() == EDIT_METHOD.DELETE) {
                        dialog.info({ messageId: "Msg_16" }).then(() => {
                            setShared('QMM011_F_PARAMS_OUTPUT', {
                                methodEditing: self.methodEditing()
                            });
                            close();
                        });
                    } else {
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            setShared('QMM011_F_PARAMS_OUTPUT', {
                                methodEditing: self.methodEditing()
                            });
                            close();
                        });
                    }
                }).fail(function(res: any) {
                    if (res)
                        dialog.alertError(res);
                }).always(() => {
                    block.clear();
                });
            } else {
                service.updateAccInsurHis(param).done(() => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('QMM011_F_PARAMS_OUTPUT', {
                            methodEditing: self.methodEditing()
                        });
                        close();
                    });
                }).fail(function(res: any) {
                    if (res)
                        dialog.alertError(res);
                }).always(() => {
                    block.clear();
                });

            }
        }
        
        hasRequired(){
            if(this.methodEditing() != EDIT_METHOD.UPDATE) {
                $('#F1_9').ntsError('clear');
                return false;
            }
            return true;
        }
        
        validateYearMonth(){
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("#F1_9").trigger("validate");
            if ((self.startYearMonth() == self.endYearMonth() || Number(self.startYearMonth()) > Number(self.endYearMonth()) || 
                    Number(self.startLastYearMonth()) > Number(self.startYearMonth())) && (this.methodEditing() == EDIT_METHOD.UPDATE)){
                $('#F1_9').ntsError('set', { messageId: "Msg_107" });
                return true;
            }
            return error.hasError();      
        }
        
        cancel(){
            close();
        }
        
       // 「初期データ取得処理
    }
    
    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM011_61')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM011_62'))
        ];
    }
    
    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }
    
    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }
    
}