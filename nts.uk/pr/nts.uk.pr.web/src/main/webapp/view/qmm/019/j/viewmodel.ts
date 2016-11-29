module qmmm019.j.viewmodel{
    export class ScreenModel1 {
        boxes1: BoxModel1[];
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            this.boxes1 = [];
            this.boxes1.push({id: 1, text: "明細書に印字する行"});
            this.boxes1.push({id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）"});
            this.boxes1.push({id: 3, text: "レイアウトから行を削除（登録処理を行うまでは元に戻せます）"});
        }
              closeDialog(): any{
             nts.uk.ui.windows.close();
       }
    }
    
    
    export class BoxModel1 {
        id: any;
        text: string;
    }
}