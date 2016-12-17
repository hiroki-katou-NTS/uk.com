module qmmm019.k.viewmodel{
    export class ScreenModel {
            itemList: KnockoutObservableArray<any>;
    selectedId: KnockoutObservable<number>;
    enable: KnockoutObservable<boolean>;
    
        constructor() {
           var self = this;
        self.itemList = ko.observableArray([
            new BoxModel(1, '明細書に印字する行'),
            new BoxModel(2, '明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）'),
            new BoxModel(3, 'レイアウトから行を削除（登録処理を行うまでは元に戻せます）')
        ]);
        self.selectedId = ko.observable(1);
        self.enable = ko.observable(true);
    }          
        
        
        
        closeDialog() {
            nts.uk.ui.windows.setShared('selectedCode', undefined);
            nts.uk.ui.windows.close();
        }
    }
    
    class BoxModel {
    id: number;
    name: string;
    constructor(id, name){
        var self = this;
        self.id = id;
        self.name = name;
    }
}
}
