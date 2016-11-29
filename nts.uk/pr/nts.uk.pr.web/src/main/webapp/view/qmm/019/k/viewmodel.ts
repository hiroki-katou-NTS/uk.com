module qmmm019.k.viewmodel{
    export class ScreenModel2 {
        boxes2: BoxModel2[];
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            this.boxes2 = [];
            this.boxes2.push({id: 1, text: "明細書に印字する行"});
            this.boxes2.push({id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）"});
            this.boxes2.push({id: 3, text: "レイアウトから行を削除（登録処理を行うまでは元に戻せます）"});
           
        }
              closeDialog(): any{
             nts.uk.ui.windows.close();
       }
    }
    
    
    export class BoxModel2 {
        id: any;
        text: string;
    }
}