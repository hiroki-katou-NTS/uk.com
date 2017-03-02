module cmm008.a.viewmodel{
    import option = nts.uk.ui.option;
    
    export class ScreenModel {
        //employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        isCheckbox: KnockoutObservable<Boolean>;
        
        //search box
        dataSource: KnockoutObservableArray<service.model.employmentDto>;;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<string>;
        singleSelectedCode: any;
//        headers: any;
        //締め日 DATA COMBOBOX
        closeDateList: KnockoutObservableArray<ItemCloseDate>;
        selectedCloseCode: KnockoutObservable<number>;
        //公休の管理
        managementHolidays: KnockoutObservableArray<any>;
        holidayCode: KnockoutObservable<number>;
        //処理日区分
        processingDateList: KnockoutObservableArray<ItemProcessingDate>;
        selectedProcessCode: KnockoutObservable<number>;
        //外部コード
        employmentOutCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        //memo
        multilineeditor: any;        
        //list values
        //listResult : KnockoutObservableArray<service.model.employmentDto>;
        
        constructor(){
            var self = this;
            //self.employmentCode = ko.observable("");
            self.employmentName = ko.observable("");
            self.isCheckbox = ko.observable(true);
            self.closeDateList = ko.observableArray([]);
            self.selectedCloseCode = ko.observable(0);
            self.managementHolidays = ko.observableArray([]);
            self.holidayCode = ko.observable(1);
            self.processingDateList = ko.observableArray([]);
            self.selectedProcessCode = ko.observable(0);
            self.employmentOutCode = ko.observable("");
            self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            self.dataSource = ko.observableArray([]);
            //self.listResult = ko.observableArray([]);
            self.isEnable = ko.observable(false);
            self.multilineeditor = {
                memoValue: ko.observable(""),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
            };
            
            
        }
        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            var heightScreen = $(window).height();
            var widthScreen =  $(window).width();
            var heightHeader = $('#header').height() + $('#functions-area').height();
            var height = heightScreen-heightHeader - 75;
            $('#contents-left').css({height: height, width: widthScreen*30/100});
            $('#contents-right').css({height: height, width: widthScreen*70/100});
            self.closeDateListItem();
            self.managementHolidaylist();
            self.processingDateItem();
            self.dataSourceItem();
            
            //list data click
            self.currentCode.subscribe(function(newValue){
                let newEmployment = _.find(self.dataSource(), function(employ){
                    if(employ.employmentCode === newValue){
                        self.isEnable(false);
                        self.currentCode(employ.employmentCode);                        
                        self.employmentName(employ.employmentName);
                        self.selectedCloseCode(employ.closeDateNo);
                        self.selectedProcessCode(employ.processingNo);
                        self.multilineeditor.memoValue(employ.memo);
                        self.employmentOutCode(employ.employementOutCd); 
                        if(employ.displayFlg == 1){
                            self.isCheckbox(true);    
                        }else{
                            self.isCheckbox(false);    
                        }
                        return;
                    }
                })
            });
            dfd.resolve();
            // Return.
            return dfd.promise();
        }
        
        closeDateListItem(): any{
            var self = this;
            self.closeDateList.removeAll();
            self.closeDateList.push(new ItemCloseDate(0,'0'));
            self.closeDateList.push(new ItemCloseDate(1,'1'));
            self.closeDateList.push(new ItemCloseDate(2,'2'));
        }
        managementHolidaylist(): any{
            var self = this;
            self.managementHolidays = ko.observableArray([
                {code: 1, name: 'する'},
                {code: 2, name: 'しない'}
            ]);
            self.holidayCode = ko.observable(1);
        }
        
        processingDateItem(): any{
            var self = this;
            self.processingDateList.push(new ItemProcessingDate(0, '0'));
            self.processingDateList.push(new ItemProcessingDate(1, '1')); 
            self.processingDateList.push(new ItemProcessingDate(2, '2')); 
        }
        
        dataSourceItem(): any{
            var self = this;
            self.dataSource = ko.observableArray([]);
            service.getAllEmployments().done(function(listResult : Array<service.model.employmentDto>){
                //self.listResult(listResult);
                if(listResult.length === 0 || listResult === undefined){
                    self.isEnable(true);    
                }else{
                    self.isEnable(false);
                    for(let employ of listResult){
                        if(employ.displayFlg == 1){
                            employ.displayStr = "●";    
                        }else{
                            employ.displayStr = "";    
                        }
                            
                        self.dataSource.push(employ);                        
                    }
                    if(self.currentCode() === ""){
                        var obEmployment =_.first(self.dataSource()); 
                        self.currentCode(obEmployment.employmentCode);    
                    }
                }
            })            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'employmentCode', width: 100 },
                { headerText: '名称', prop: 'employmentName', width: 150 },
                { headerText: '締め日', prop: 'closeDateNo', width: 150 },
                { headerText: '処理日区分', prop: 'processingNo', width: 150 },
                { headerText: '初期表示', prop: 'displayStr', width: 100 }
            ]);
            this.currentCode = ko.observable("");
            self.singleSelectedCode = ko.observable(null);
        }
        
         //登録ボタンを押す
        createEmployment() : any{
            var self = this;
            //self.employmentCode(nts.uk.text.padLeft(self.employmentCode(),'0',10));
            self.currentCode(nts.uk.text.padLeft(self.currentCode(),'0',10));
            //必須項目の未入力チェック
            if(self.currentCode() === ""
                || self.employmentName() === ""){
                alert("コード/名称が入力されていません。");    
                return;
            }
            var employment = new service.model.employmentDto();
            employment.employmentCode = self.currentCode();
            employment.employmentName = self.employmentName();
            employment.closeDateNo = self.selectedCloseCode();
            employment.processingNo = self.selectedProcessCode();
            employment.statutoryHolidayAtr = self.holidayCode();
            employment.employementOutCd = self.employmentOutCode();
            employment.memo = self.multilineeditor.memoValue();
            if(self.isCheckbox())
                employment.displayFlg = 1;
            else
                employment.displayFlg = 0;
            //新規の時
            if(self.isEnable()){
                var isCheck = false;
                //コード重複チェック
                service.getEmploymentByCode(self.currentCode()).done(function(employmentChk: service.model.employmentDto){
                     if(employmentChk !== undefined && employmentChk !== null){
                         alert("入力したコードは既に存在しています。\r\nコードを確認してください。");
                         isCheck = true;
                         return;   
                     }
                })
                if(isCheck){
                    $("#INP_002").focus();
                    return;                    
                }
                
                service.createEmployment(employment).done(function(){
                    self.dataSource.push(employment);
                    self.currentCode(employment.employmentCode);
                })   
            //更新の時 
            }else{
                service.updateEmployment(employment).done(function(){      
                    
                    let indexItemUpdate = _.findIndex(self.dataSource(), function(item) { return item.employmentCode == employment.employmentCode; });
//                    if(employment.displayFlg == 1){
//                        employment.displayStr = "●";    
//                    }else{
//                        employment.displayStr = "";    
//                    }
//                    self.dataSource().splice(indexItemUpdate, 1, _.cloneDeep(employment));
//                    self.dataSource.valueHasMutated();           
                    self.dataSourceItem();
                    //var curentEmployment =  self.dataSource()[indexItemUpdate];
                    self.currentCode(employment.employmentCode);
                    self.dataSource().splice(indexItemUpdate, 1, _.cloneDeep(employment));
                    self.dataSource.valueHasMutated(); 
                })                
            }
            
        }
        //新規ボタンを押す
        newCreateEmployment(): any{
            var self = this;
            self.currentCode("");
            self.employmentName("");
            self.isEnable(true);
            self.multilineeditor.memoValue("");
            self.employmentOutCode("");
            self.currentCode("");
            self.isCheckbox(false);
            $("#INP_002").focus();
        }
        //削除
        deleteEmployment(): any{
            var self = this;
            var employment = new service.model.employmentDto();
            employment.employmentCode = self.currentCode();
            if(self.isCheckbox())
                employment.displayFlg = 1;
            else
                employment.displayFlg = 0;
            let indexItemDelete = _.findIndex(self.dataSource(), function(item) { return item.employmentCode == self.currentCode(); });
            service.deleteEmployment(employment).done(function(){
                self.dataSource.remove(function(item) {
                    return item.employmentCode == self.currentCode();
                });
                self.dataSource.valueHasMutated();
                self.dataSource.remove(function(item) {
                    return item.employmentCode == self.currentCode();
                });
                self.dataSource.valueHasMutated();
                if (self.dataSource().length === 0) {
                    self.isEnable(true);
                    self.newCreateEmployment();
                } else if (self.dataSource().length === indexItemDelete) {
                    self.currentCode(self.dataSource()[indexItemDelete - 1].employmentCode);
                } else {
                    self.currentCode(self.dataSource()[indexItemDelete].employmentCode);
                }
            })
        }
    }   
    
    export class ItemCloseDate{
        closeDateCode: number;
        closeDatename: string;
        constructor(closeDateCode: number, closeDatename: string){
            this.closeDateCode = closeDateCode;
            this.closeDatename = closeDatename;    
        }
    }
    
    export class ItemProcessingDate{
        processingDateCode: number;
        processingDatename: string;
        constructor(processingDateCode: number, processingDatename: string){
            this.processingDateCode = processingDateCode;
            this.processingDatename = processingDatename;    
        }   
    }
   
}