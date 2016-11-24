module qmm019.g.viewmodel {
    
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentLayoutAtr: KnockoutObservable<number>
        selectedLayoutAtr: KnockoutObservable<number>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        comboboxList: KnockoutObservableArray<ItemCombobox>;
        selectLayoutCode: KnockoutObservable<string>;
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
            
            self.comboboxList = ko.observableArray([
                new ItemCombobox('001', "layout 001"),
                new ItemCombobox('002', "layout 002"),
                new ItemCombobox('003', "layout 003"),
            ]); 
            self.selectLayoutCode = ko.observable("001");           
        } 
//        startPage(): JQueryPromise<any> {
//            var self = this;
//            // Page load dfd.
//            var dfd = $.Deferred();
//            $.when(qmm019.g.service.getLayoutWithMaxStartYm()).done(function(data){
//                self.comboboxList(data);
//                dfd.resolve();   
//            }).fail(function res() {
//                    
//            });
//            return dfd.promise();
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
    
    export class ItemCombobox{
        layoutCode: string;
        layoutName: string;
        
        constructor(layoutCode: string, layoutName: string){
            this.layoutCode = layoutCode;
            this.layoutName = layoutName;    
        }
    }
}