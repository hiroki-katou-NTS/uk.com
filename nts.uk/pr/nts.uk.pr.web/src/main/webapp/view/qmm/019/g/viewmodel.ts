module qmm019.g.viewmodel {
    import option = nts.uk.ui.option;
    
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCodes: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        comboboxList: KnockoutObservableArray<ItemCombobox>;
        selectLayoutCode: KnockoutObservable<string>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutAtrStr: KnockoutObservable<string>;
        selectStmtCode : KnockoutObservable<string>;
        selectStmtName : KnockoutObservable<string>;
        selectStartYm: KnockoutObservable<string>;
        timeEditorOption: KnockoutObservable<any>;
        createlayout: KnockoutObservable<service.model.LayoutMasterDto>;
        createNewSelect: KnockoutObservable<string>;
        
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observable("2");
            self.layouts = ko.observableArray([]);
            self.itemList = ko.observableArray([]);   
            self.comboboxList = ko.observableArray([]);
            self.selectLayoutCode = ko.observable("");
            self.layoutAtrStr = ko.observable(null);
            self.selectStmtCode = ko.observable(null);
            self.selectStmtName = ko.observable(null);
            self.selectStartYm =  ko.observable(null);
            console.log(option);
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({inputFormat: "yearmonth"}));
            self.createlayout = ko.observable(null);
            self.createNewSelect = ko.observable(null);
        }   
        
        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            self.buildItemList();
            $('#LST_001').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#LST_001').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
            //combobox
            service.getLayoutWithMaxStartYm().done(function(layout: Array<service.model.LayoutMasterDto>){
                self.layouts(layout);
                self.buildCombobox();
            });
            //change combobox
            self.selectLayoutCode.subscribe(function(newValue){
                self.selectLayoutCode(newValue);
                self.createNewSelect(newValue);
            })
            //start ym
            var d = new Date();
            var ym = d.getFullYear() + '/' + (d.getMonth() + 1);
            self.selectStartYm(ym);
            
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
        
        buildCombobox() :any{
            var self = this;
            self.comboboxList.removeAll();
            _.forEach(self.layouts(), function(layout){
                var stmtCode = layout.stmtCode;
                if(stmtCode.length == 1){
                    stmtCode = "0" + layout.stmtCode;
                }
                    
                self.comboboxList.push(new ItemCombobox(stmtCode, layout.stmtName));
            });          
        }
        
        buildComboboxChange(layoutCd) : void {
            var self = this;
//            _.forEach(self.layouts(), function(layout){
//                if(layout.stmtCode.trim() == layoutCd){
//                    self.selectStmtCode(layoutCd);
//                    self.selectStmtName(layout.stmtName);
//                    
//                    if(layout.layoutAtr == 0) {
//                        self.layoutAtrStr("（レーザー　A4　縦向き　1人）");
//                    }else if (layout.layoutAtr == 1){
//                        self.layoutAtrStr("（レーザー　A4　縦向き　2人）");
//                    }else if (layout.layoutAtr == 2){
//                        self.layoutAtrStr("（レーザー　A4　縦向き　3人）");
//                    }else if (layout.layoutAtr == 3){
//                        self.layoutAtrStr("（レーザー　A4　横向き　2人）");
//                    }else if (layout.layoutAtr == 4){
//                        self.layoutAtrStr("（レーザー(圧着式)　縦向き　1人）");
//                    }else if (layout.layoutAtr == 5){
//                        self.layoutAtrStr("（レーザー(圧着式)　横向き　1人）");
//                    }else if (layout.layoutAtr == 6){
//                        self.layoutAtrStr("（ドットプリンタ　連続用紙　1人）");
//                    }else if (layout.layoutAtr == 7){
//                        self.layoutAtrStr("（PAYS単票）");
//                    }else if (layout.layoutAtr == 8){
//                        self.layoutAtrStr("（PAYS連続）");
//                    }
//                    return false;
//                }    
//            });
        }
        
        createNewLayout(): any{
            var self = this;
            
            if(self.checkData())
            {
                   if($("#newCreate").is(":checked")){
                       self.createNewData();
                    }else{
                       //印字行数　＞　最大印字行数　の場合
                       //メッセージ( ER030 )を表示する
                       
                       
                        self.createCopyData();    
                    }
                    service.createLayout(self.createlayout()).done(function(){
                        alert('追加しました。');    
                        nts.uk.ui.windows.close();
                    }).fail(function(res){
                        alert(res);    
                    })
             }
        }
        
        checkData(): any{
            var self = this;
              //登録時チェック処理
            var mess = "が入力されていません";
            //必須項目の未入力チェックを行う
            if($('#INP_001').val() == ''){
               alert("コード" + mess);
                $('#INP_001').focus();
                return false;
            }
            if( $('#INP_002').val() == ''){
                alert('名称' + mess);
                $('#INP_002').focus();
                return false;    
            }
            if($('INP_003').val() == ''){
                alert('開始年月' + mess);
                $('#INP_003').focus();
                return false;    
            }
            //コードの重複チェックを行う
            var isStorage = false;
            _.forEach(self.layouts(), function(layout){
                if(+$('#INP_001').val() === +layout.stmtCode){
                    alert('入力した'+$('#INP_001').val()+'は既に存在しています。\r\n'+$('#INP_001').val()+'を確認してください。');
                    $('#INP_001').focus();
                    isStorage = true;
                    return false;
                }
            }); 
            if(isStorage){
                return false;    
            }
                
            return true; 
        }
        
        createNewData(): any{
            var self = this;         
            self.createlayout({
                checkCopy: false,
                stmtCodeCopied: self.createNewSelect() + "",
                startYmCopied: + self.selectStartYm().replace('/', ''),
                stmtCode: $('#INP_001').val(),
                startYm: + $('#INP_003').val().replace('/',''),
                layoutAtr: 3,
                stmtName: $('#INP_002').val() + "",
                endYm: 999912
            });   
        }
        
        createCopyData(): any{
             var self = this;
            self.createlayout({
                checkCopy: true,
                stmtCodeCopied: self.createNewSelect() + "",
                startYmCopied: + self.selectStartYm().replace('/', ''),
                stmtCode: $('#INP_001').val() + "",
                startYm: + $('#INP_003').val().replace('/',''),
                layoutAtr: 3,
                stmtName: $('#INP_002').val() + "",
                endYm: 999912
            });             
        }
        
       closeDialog(): any{
             nts.uk.ui.windows.close();
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