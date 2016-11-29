module qmm019.d.viewmodel {
    
    export class ScreenModel {
        selectLayoutAtr: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<ItemModel>;
        isEnable: KnockoutObservable<boolean>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        selectStmtCode : KnockoutObservable<string>;
        selectStmtName : KnockoutObservable<string>;
        selectStartYm: KnockoutObservable<string>;
        layoutSelect:  KnockoutObservable<string>;
        valueSel001:   KnockoutObservable<string>;
        createlayout:  KnockoutObservable<service.model.LayoutMasterDto>;
        startYmHis: KnockoutObservable<number>;
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.selectLayoutAtr = ko.observable("3");
            self.itemList = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            self.layouts = ko.observableArray([]);
            self.selectStmtCode = ko.observable(null);
            self.selectStmtName = ko.observable(null);
            self.selectStartYm =  ko.observable(null);
            //sau nay gan lai
            self.layoutSelect = ko.observable("01");
            self.valueSel001 = ko.observable("");
            self.createlayout = ko.observable(null);
            self.startYmHis = ko.observable(null);
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
            service.getLayoutWithMaxStartYm().done(function(layout: Array<service.model.LayoutMasterDto>){
                self.layouts(layout);
                self.startDialog();
            });
            
             dfd.resolve();
            // Return.
            return dfd.promise();    
        }
        
        buildItemList(): any{
            var self = this;
            self.itemList.removeAll();
            self.itemList.push(new ItemModel('0','レーザープリンタ', 'Ａ４', '縦向き','1人','最大　30行ｘ9別まで設定可能',''));
            self.itemList.push(new ItemModel('1','レーザープリンタ', 'Ａ４', '縦向き','最大2人','最大　17行ｘ9別まで設定可能',''));
            self.itemList.push(new ItemModel('2','レーザープリンタ', 'Ａ４', '縦向き','最大3人','最大　10行ｘ9別まで設定可能',''));
            self.itemList.push(new ItemModel('3','レーザープリンタ', 'Ａ４', '横向き','最大2人','最大　10行ｘ9別まで設定可能',''));
            self.itemList.push(new ItemModel('4','レーザー（圧着式）', 'Ａ４', '縦向き','1人','最大　17行ｘ9別まで設定可能','圧着式：　Ｚ折り'));
            self.itemList.push(new ItemModel('5','レーザー（圧着式）', 'Ａ４', '横向き','1人','支給、控除、勤怠各52項目','圧着式：　はがき'));
            self.itemList.push(new ItemModel('6','ドットプリンタ', '連続用紙', '―','1人','支給、控除、勤怠各27項目',''));
        }
        
        startDialog() : any{
            var self = this;
            _.forEach(self.layouts(), function(layout){
                var stmtCode = layout.stmtCode;
                if(stmtCode == self.layoutSelect()){
                    self.selectStmtCode(stmtCode);
                    self.selectStmtName(layout.stmtName);
                    self.selectStartYm(nts.uk.time.formatYearMonth(layout.startYm));
                    self.valueSel001('最新の履歴'+ layout.startYm +'から引き継ぐ')
                    self.startYmHis = layout.startYm;
                    return false;                    
                }
            });     
        }
        
       createHistoryLayout(): any{
           var self = this;
           var selectYm = self.startYmHis;
            var inputYm =$("#INP_001").val().replace('/','');
            if(+inputYm < +selectYm
              || + inputYm == +selectYm){
               alert('履歴の期間が正しくありません。');
                return false;
            }
           else{
                self.createData();
               if($("#copyCreate").is(":checked")){
                    self.createlayout().checkContinue = true;
                }else{
                    self.createlayout().checkContinue = false; 
                }
               service.createLayoutHistory(self.createlayout()).done(function(){
                   alert("追加しました。"); 
                   nts.uk.ui.windows.close();   
               }).fail(function(res){
                   alert(res);
                   nts.uk.ui.windows.close();
               })
           }
       }
       createData(): any{
           var self = this;
           var startPreviou = +$('#INP_001').val().replace('/','');
           self.createlayout({
               checkContinue: false,
               stmtCode: self.selectStmtCode(),
               startYm: +self.startYmHis,
               endYm: (startPreviou - 1),
               startPrevious: startPreviou,
               layoutAtr: 3
          });
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