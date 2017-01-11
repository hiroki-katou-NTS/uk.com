module cmm013.a.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        selectLayoutAtr: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<ItemModel>;
        isEnable: KnockoutObservable<boolean>;
        selectStmtCode: KnockoutObservable<string>;
        selectStmtName: KnockoutObservable<string>;
     
        constructor() {
            var self = this;
            self.selectLayoutAtr = ko.observable("3");
            self.itemList = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            self.selectStmtCode = ko.observable(null);
            self.selectStmtName = ko.observable(null);
            
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //list data
            self.buildItemList();
            $('#LST_001').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#LST_001').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
            //fill data to dialog


            dfd.resolve();
            // Return.
            return dfd.promise();
        }

        buildItemList(): any {
            var self = this;
            self.itemList.removeAll();
            self.itemList.push(new ItemModel('0', 'レーザープリンタ', 'Ａ４', '縦向き', '1人', '最大　30行ｘ9別まで設定可能', ''));
            self.itemList.push(new ItemModel('1', 'レーザープリンタ', 'Ａ４', '縦向き', '最大2人', '最大　17行ｘ9別まで設定可能', ''));
            self.itemList.push(new ItemModel('2', 'レーザープリンタ', 'Ａ４', '縦向き', '最大3人', '最大　10行ｘ9別まで設定可能', ''));
            self.itemList.push(new ItemModel('3', 'レーザープリンタ', 'Ａ４', '横向き', '最大2人', '最大　10行ｘ9別まで設定可能', ''));
            self.itemList.push(new ItemModel('4', 'レーザー（圧着式）', 'Ａ４', '縦向き', '1人', '最大　17行ｘ9別まで設定可能', '圧着式：　Ｚ折り'));
            self.itemList.push(new ItemModel('5', 'レーザー（圧着式）', 'Ａ４', '横向き', '1人', '支給、控除、勤怠各52項目', '圧着式：　はがき'));
            self.itemList.push(new ItemModel('6', 'ドットプリンタ', '連続用紙', '―', '1人', '支給、控除、勤怠各27項目', ''));
        }

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


        constructor(stt: string, printType: string, paperType: string, direction: string, numberPeople: string, numberDisplayItems: string, reference: string) {
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