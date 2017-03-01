module cmm008.a.viewmodel{
    import option = nts.uk.ui.option;
    
    export class ScreenModel {
        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        isCheckbox: KnockoutObservable<Boolean>;
        
        //search box
        dataSource: any;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
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
        memoValue: KnockoutObservable<string>;
        //list values
        listResult : KnockoutObservableArray<service.model.employmentDto>;
        
        constructor(){
            var self = this;
            self.employmentCode = ko.observable("");
            self.employmentName = ko.observable("");
            self.isCheckbox = ko.observable(true);
            self.closeDateList = ko.observableArray([]);
            self.selectedCloseCode = ko.observable(0);
            self.managementHolidays = ko.observableArray([]);
            self.holidayCode = ko.observable(1);
            self.processingDateList = ko.observableArray([]);
            self.selectedProcessCode = ko.observable(0);
            self.employmentOutCode = ko.observable("");
            self.memoValue = ko.observable("");
            self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            self.dataSource = ko.observableArray([]);
            self.listResult = ko.observableArray([]);
            self.isEnable = ko.observable(false);
            self.multilineeditor = {
                memoValue: ko.observable(''),
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
            if(self.listResult() !== undefined && self.listResult().length >0
                || self.currentCode() === null 
                || self.currentCode() === undefined){
                var obEmployment =_.first(self.listResult()); 
                //self.currentCode(obEmployment.employmentCode);    
            }
            //list data click
            self.currentCode.subscribe(function(newValue){
                let newEmployment = _.find(self.listResult(), function(employ){
                    if(employ.employmentCode === newValue){
                        self.currentCode(employ.employmentCode);
                        self.employmentCode(employ.employmentCode);
                        self.employmentName(employ.employmentName);
                        self.selectedCloseCode(employ.closeDateNo);
                        self.selectedProcessCode(employ.processingNo);
                        self.memoValue(employ.memo);
                        self.employmentOutCode(employ.employementOutCd); 
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
                self.listResult(listResult);
                if(listResult.length === 0 || listResult === undefined){
                    self.isEnable(true);    
                }else{
                    self.isEnable(false);
                    for(let employ of listResult){
                        var closeDate = employ.closeDateNo.toString();
                        var processingNo = employ.processingNo.toString();
                        var displayText = employ.displayFlg.toString();
                        self.dataSource.push(new ItemModel(employ.employmentCode, employ.employmentName, closeDate, processingNo, displayText));                        
                    }
                }
            })            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '締め日', prop: 'closeDate', width: 150 },
                { headerText: '処理日区分', prop: 'processingNo', width: 150 },
                { headerText: '初期表示', prop: 'displayFlg', width: 150 }
            ]);
            this.currentCode = ko.observable();
            self.singleSelectedCode = ko.observable(null);
        }
        
         //登録ボタンを押す
        createEmployment() : any{
            var self = this;
            self.employmentCode(nts.uk.text.padLeft(self.employmentCode(),'0',10));
            //必須項目の未入力チェック
            if(self.employmentCode() === ""
                || self.employmentName() === ""){
                alert("コード/名称が入力されていません。");    
                return;
            }
            var employment = new service.model.employmentDto();
            employment.employmentCode = self.employmentCode();
            employment.employmentName = self.employmentName();
            employment.closeDateNo = self.selectedCloseCode();
            employment.processingNo = self.selectedProcessCode();
            employment.statutoryHolidayAtr = self.holidayCode();
            employment.employementOutCd = self.employmentOutCode();
            employment.memo = self.memoValue();
            if(self.isCheckbox)
                employment.displayFlg = 1;
            else
                employment.displayFlg = 0;
            if(self.isEnable()){
                var isCheck = false;
                //コード重複チェック
                service.getEmploymentByCode(self.employmentCode()).done(function(employmentChk: service.model.employmentDto){
                     if(employmentChk !== null || employmentChk !== undefined){
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
                    
                })    
            }else{
                service.updateEmployment(employment).done(function(){
                        
                })                
            }
            
            self.start();
        }
        //新規ボタンを押す
        newCreateEmployment(): any{
            var self = this;
            self.employmentCode("");
            self.employmentName("");
            self.isEnable(true);
            self.memoValue("");
            self.employmentOutCode("");
            self.currentCode("");
                
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
   class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        //childs: any;
        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;    
            //this.childs = childs;     
        }
    }
   
}