module nts.uk.com.view.qmm011.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    export class ScreenModel {
        startYearMonth:         KnockoutObservable<number> = ko.observable();
        endYearMonth:           KnockoutObservable<number> = ko.observable();
        name:                   KnockoutObservable<string> = ko.observable('');
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        selectedId:             KnockoutObservable<string> = ko.observable('');
        methodEditing:          KnockoutObservable<number> = ko.observable();
        insurrance:             KnockoutObservable<number> = ko.observable();
        hisId:                   KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
            self.startYearMonth(201809);
            let params = getShared('QMM011_F_PARAMS');
            if (params) {
                self.insurrance(params.insurrance);
                self.startYearMonth(params.startYearMonth);
                self.endYearMonth(params.endYearMonth);
                if (params.hisId) {
                    self.hisId(params.hisId);
                }
            }
            
        }
        
        update() {
            let self = this;
            if (self.insurrance() == INSURRANCE.EMPLOYMENT_INSURRANCE_RATE) {
                block.invisible();
                let param: any = {
                    hisId: self.hisId(),
                    methodEditing: self.methodEditing()
                }
                service.updateEmpInsurHis(param).done(() => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        self.isNewMode(false);
                        close();
                    }).fail(function(res: any) {
                        if (res)
                            dialog.alertError(res);
                }).always(() => {
                    block.clear();
                });

            }
        }
        
        cancel(){
            close();
        }
        
       // 「初期データ取得処理
    }
    
    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM011_61')),
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM011_62'))
        ];
    }
    
    export enum EDIT_METHOD {
        DELETE = 1,
        UPDATE = 0
    }
    
    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }
    
}