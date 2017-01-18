module qrm001.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        multilineeditor: any;
        
        texteditor1: any;
        texteditor2: any;
        texteditor3: any;
        texteditor4: any;
        texteditor5: any;
        texteditor6: any;
        texteditor7: any;
        texteditor8: any;
        texteditor9: any;
        texteditor10: any;
        texteditor11: any;
        texteditor12a: any;
        texteditor12b: any;
        texteditor12c: any;
        texteditor12d: any;
        texteditor12e: any;
        selectList1: KnockoutObservableArray<any>;
        selectCode1: KnockoutObservable<number>;
        selectList2: KnockoutObservableArray<any>;
        selectCode2: KnockoutObservable<number>;
        selectList3: KnockoutObservableArray<any>;
        selectCode3: KnockoutObservable<number>;
        selectList4: KnockoutObservableArray<any>;
        selectCode4: KnockoutObservable<number>;
        selectList5: KnockoutObservableArray<any>;
        selectCode5: KnockoutObservable<number>;
        selectList12:  KnockoutObservableArray<any>;
        selectList12a: KnockoutObservableArray<any>;
        selectCode12a: KnockoutObservable<number>;
        selectList12b: KnockoutObservableArray<any>;
        selectCode12b: KnockoutObservable<number>;
        selectList12c: KnockoutObservableArray<any>;
        selectCode12c: KnockoutObservable<number>;
        selectList12d: KnockoutObservableArray<any>;
        selectCode12d: KnockoutObservable<number>;
        selectList12e: KnockoutObservableArray<any>;
        selectCode12e: KnockoutObservable<number>;
        
        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.selectList1 = ko.observableArray([
                { code: '無し' },
                { code: '有り' }
            ]);
            self.selectCode1 = ko.observable(1);
            self.selectList2 = ko.observableArray([
                { code: '自己都合' },
                { code: '会社都合' },
                { code: '定年退職' },
                { code: '死亡退職' },
                { code: 'その他' }
            ]);
            self.selectCode2 = ko.observable(1);
            self.selectList3 = ko.observableArray([
                { code: '通常' },
                { code: '障害により退職' },
                { code: '死亡により退職' }
            ]);
            self.selectCode3 = ko.observable(1);
            self.selectList4 = ko.observableArray([
                { code: '有り（他から退職金の支払い無し）' },
                { code: '有り（他から退職金支払い有り）' },
                { code: '申告書無し（税率　一律20.42％）' }
            ]);
            self.selectCode4 = ko.observable(1);
            self.selectList5 = ko.observableArray([
                { code: '自動計算' },
                { code: '手入力' }
            ]);
            self.selectCode5 = ko.observable(1);
            self.selectList12a = ko.observableArray([
                { code: '支給1' },
                { code: '支給2' },
                { code: '支給3' },
                { code: '支給4' },
                { code: '支給5' }
            ]);
            self.selectCode12a = ko.observable(1);
            self.selectList12b = ko.observableArray([{ code: '使用しない' }]);
            self.selectCode12b = ko.observable(1);
            self.selectCode12b.subscribe(function(value){
                if(value.toString() === '使用しない') self.texteditor12b.enable(false);   
                else self.texteditor12b.enable(true); 
            });
            self.selectList12c = ko.observableArray([{ code: '使用しない' }]);
            self.selectCode12c = ko.observable(1);
            self.selectCode12c.subscribe(function(value){
                if(value.toString() === '使用しない') self.texteditor12c.enable(false);   
                else self.texteditor12c.enable(true); 
            });
            self.selectList12d = ko.observableArray([{ code: '使用しない' }]);
            self.selectCode12d = ko.observable(1);
            self.selectCode12d.subscribe(function(value){
                if(value.toString() === '使用しない') self.texteditor12d.enable(false);   
                else self.texteditor12d.enable(true); 
            });
            self.selectList12e = ko.observableArray([{ code: '使用しない' }]);
            self.selectCode12e = ko.observable(1);
            self.selectCode12e.subscribe(function(value){
                if(value.toString() === '使用しない') self.texteditor12e.enable(false);   
                else self.texteditor12e.enable(true); 
            });
            
            self.texteditor1 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor2 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor3 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor4 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor5 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor6 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor7 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor8 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor9 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor10 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor11 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor12a = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.texteditor12b = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
            self.texteditor12c = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
            self.texteditor12d = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
            self.texteditor12e = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };

            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            this.loadData(self.selectList12a, self.selectList12b);
            this.loadData(self.selectList12a, self.selectList12c);
            this.loadData(self.selectList12a, self.selectList12d);
            this.loadData(self.selectList12a, self.selectList12e);
            var dfd = $.Deferred();
            qrm001.a.service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {
                
            });
            return dfd.promise();
            
        }
        
        loadData(src : KnockoutObservableArray<any>, dst : KnockoutObservableArray<any>): void {
            ko.utils.arrayForEach(src(), function(item){
                dst.push(item);
            });
        } 
        
        openDialog(){
            nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', {title: '入力欄の背景色について', dialogClass: "no-close"});
        }
    }
}

class BankDataSet{
    dataSetName: string;
    bankName: string;
    branchName: string;
    accountNumber: string;
    transferMoney: string;
        
    constructor(dataSetName: string, bankName: string, branchName: string, accountNumber: string, transferMoney: string){
        this.dataSetName = dataSetName;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountNumber = accountNumber;
        this.transferMoney = transferMoney;    
    }
}

