module qmm019.viewmodel {
    
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentLayoutAtr: KnockoutObservable<number>
        selectedLayoutAtr: KnockoutObservable<number>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('0','レーザープリンタ', 'Ａ４', '縦向き','1人','最大　30行ｘ9別まで設定可能','a'),
                new ItemModel('1','レーザープリンタ', 'Ａ４', '縦向き','最大2人','最大　17行ｘ9別まで設定可能','a'),
                new ItemModel('2','レーザープリンタ', 'Ａ４', '縦向き','最大3人','最大　10行ｘ9別まで設定可能','a'),
                new ItemModel('3','レーザープリンタ', 'Ａ４', '横向き','最大2人','最大　10行ｘ9別まで設定可能','a'),
                new ItemModel('4','レーザー（圧着式）', 'Ａ４', '縦向き','1人','最大　17行ｘ9別まで設定可能','圧着式：　Ｚ折り'),
                new ItemModel('5','レーザー（圧着式）', 'Ａ４', '横向き','1人','支給、控除、勤怠各52項目','圧着式：　はがき'),
                new ItemModel('6','ドットプリンタ', '連続用紙', '―','1人','支給、控除、勤怠各27項目','a')
            ]);
            self.itemName = ko.observable('');
            self.currentLayoutAtr = ko.observable(3);
            self.selectedLayoutAtr = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);
            
            $('#SEL_002').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#SEL_002').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
            
            
        }
        
//        /**
//         * Add options.
//         */
//        addOptions() {
//            var self = this;
//            var newLayoutAtr = self.selectedLayoutAtr
//            var layoutAtr = newLayoutAtr.toString();
//            var codeLength = layoutAtr.length;
//            while (codeLength < 4) {
//                itemCode = '0' + itemCode;
//                codeLength++;
//            }
//            self.itemList.push(new ItemModel(itemCode, self.itemName()));
//            self.currentLayoutAtr(newCode);
//        }
//        
//        deselectAll() {
//            $('#combo-box').ntsListBox('deselectAll');
//        }
//        
//        selectAll() {
//            $('#combo-box').ntsListBox('selectAll');
//        }
//        
//        /**
//         * Clear options.
//         */
//        clearOptions() {
//            this.itemList([]);
//        }
//        
//        /**
//         * Remove item by code;
//         */
//        remove() {
//            var self = this;
//            
//            // Remove by code.
//            var selected: ItemModel = self.itemList().filter(item => item.code == self.selectedLayoutAtr())[0];
//            self.itemList.remove(selected);
//            
//            // Remove by codes
//            var selecteds: ItemModel[] = self.itemList().filter(item => self.selectedCodes().indexOf(item.code) != -1);
//            self.itemList.removeAll(selecteds);
//        }
    }
    
        /**
     * Class Item model.
     */
    export class ItemModel {
        stt: string;
        printType: string;
        paperType: string;
        direction: string;
        numberPeople: string;
        numberDisplayItems: string;
        reference: string;
        
        
        constructor(stt: string,printType: string, paperType: string, direction: string, numberPeople: string, numberDisplayItems: string, reference: string) {
            this.stt = stt;
            this.printType = printType;
            this.paperType = paperType;
            this.direction = direction;
            this.numberPeople = numberPeople;
            this.numberDisplayItems = numberDisplayItems;
            this.reference = reference;
        }
    }
}