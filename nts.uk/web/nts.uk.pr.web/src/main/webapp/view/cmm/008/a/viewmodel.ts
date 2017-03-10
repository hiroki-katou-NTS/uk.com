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
        
        constructor(){
            var self = this;
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
            self.currentCode = ko.observable("");
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
                    if(employ.employmentCode === newValue && !self.isEnable()){
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
        
        dataSourceItem(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            self.dataSource([]);
            $.when(service.getAllEmployments()).done(function(listResult : Array<service.model.employmentDto>){
                if(listResult.length === 0 || listResult === undefined){
                    self.isEnable(true);    
                }else{
                    self.isEnable(false);
                    _.forEach(listResult, function(employ){
                        if (employ.displayFlg == 1) {
                            employ.displayStr = "<span style='color: #00B050; font-size: 18px'>●</span>";    
                        } else {
                            employ.displayStr = "";
                        }
                            
                        self.dataSource.push(employ); 
                    })
                    if(self.currentCode() === ""){
                        var obEmployment =_.first(self.dataSource()); 
                        self.currentCode(obEmployment.employmentCode);    
                    }
                }
                dfd.resolve(listResult);
            })            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'employmentCode', width: 100 },
                { headerText: '名称', prop: 'employmentName', width: 160 },
                { headerText: '締め日', prop: 'closeDateNo', width: 150 },
                { headerText: '処理日区分', prop: 'processingNo', width: 150 },
                { headerText: '初期表示', prop: 'displayStr', width: 100 }
            ]);
            self.singleSelectedCode = ko.observable(null);
            return dfd.promise();
        }
        
         //登録ボタンを押す
        createEmployment() : any{
            var self = this;
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
//                //コード重複チェック
//                service.getEmploymentByCode(self.currentCode()).done(function(employmentChk: service.model.employmentDto){
//                     if(employmentChk !== undefined && employmentChk !== null){
//                         alert("入力したコードは既に存在しています。\r\nコードを確認してください。");
//                         isCheck = true;
//                         return;   
//                     }
//                })
//                if(isCheck){
//                    $("#INP_002").focus();
//                    return;                    
//                }
//                
                service.createEmployment(employment).done(function(){
                    $.when(self.dataSource()).done(function(){
                        $.when(self.dataSourceItem()).done(function(){
                            self.currentCode(employment.employmentCode);
                        })    
                    })
                }).fail(function(error){
                    alert(error.message);    
                })   
            //更新の時 
            }else{
                $.when(service.updateEmployment(employment)).done(function(){                    
                    $.when(self.dataSourceItem()).done(function(){
                        self.currentCode(employment.employmentCode);
                    })
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
            if(self.currentCode() === "")
                return;
            var employment = new service.model.employmentDto();
            employment.employmentCode = self.currentCode();
            if(self.isCheckbox())
                employment.displayFlg = 1;
            else
                employment.displayFlg = 0;
            let indexItemDelete = _.findIndex(self.dataSource(), function(item) { return item.employmentCode == self.currentCode(); });
            service.deleteEmployment(employment).done(function(){
                $.when(self.dataSourceItem()).done(function(){
                    if (self.dataSource().length === 0) {
                        self.isEnable(true);
                        self.newCreateEmployment();
                    } else if (self.dataSource().length === indexItemDelete) {
                        self.isEnable(false);
                        self.currentCode(self.dataSource()[indexItemDelete - 1].employmentCode);
                    } else {
                        self.isEnable(false);
                        if(indexItemDelete > self.dataSource().length) {
                            self.currentCode(self.dataSource()[0].employmentCode);
                        } else {
                            self.currentCode(self.dataSource()[indexItemDelete].employmentCode);
                        }
                        
                    }
                })
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