module cmm008.a.viewmodel{
    import option = nts.uk.ui.option;
    
    export class ScreenModel {
        textSearch: any;
        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        isCheckbox: KnockoutObservable<Boolean>;
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
        //memo
        multilineeditor: any;
        memoValue: KnockoutObservable<string>;
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
            self.textSearch = {
                valueSearch: ko.observable(""),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "75%",
                        textalign: "left"
                        }))
            }  
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