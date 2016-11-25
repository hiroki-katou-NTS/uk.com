module qmmm019.i.viewmodel{
    export class ScreenModel {
        boxes: BoxModel[];
                               

        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            this.boxes = [];
            this.boxes.push({id: 1, text: "明細書に印字する行"});
            this.boxes.push({id: 2, text: "明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）"});
           
        }
    }
    
    
    export class BoxModel {
        id: any;
        text: string;
    }
}