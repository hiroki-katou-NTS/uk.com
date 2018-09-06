module nts.uk.com.view.qmm011.e.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        startDate:              KnockoutObservable<string> = ko.observable('2010/1');
        endDate:                KnockoutObservable<string> = ko.observable('2010/1');
        
        constructor() {
        
        }
        
        register(){
            
        }
        
        cancel(){
            close();
        }
        
       // 「初期データ取得処理
    }
    
    export function getListPerFracClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF002_358')),
            new ItemModel(1, getText('CMF002_359')),
            new ItemModel(2, getText('CMF002_360')),
            new ItemModel(3, getText('CMF002_361')),
            new ItemModel(4, getText('CMF002_362'))
        ];
    }
    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}