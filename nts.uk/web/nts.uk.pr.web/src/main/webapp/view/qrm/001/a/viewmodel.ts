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

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.selectList1 = ko.observableArray([
                { code: '0：無し' },
                { code: '1：有り' }
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
                { code: '0：通常' },
                { code: '1：障害により退職' },
                { code: '2：死亡により退職' }
            ]);
            self.selectCode3 = ko.observable(1);
            self.selectList4 = ko.observableArray([
                { code: '0：有り（他から退職金の支払い無し）' },
                { code: '1：有り（他から退職金支払い有り）' },
                { code: '2：申告書無し（税率　一律20.42％）' }
            ]);
            self.selectCode4 = ko.observable(1);
            self.selectList5 = ko.observableArray([
                { code: '0：自動計算' },
                { code: '1：手入力' }
            ]);
            self.selectCode5 = ko.observable(1);

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
            
            var dfd = $.Deferred();
            qrm001.a.service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {
                
            });
            return dfd.promise();
        }
        
        openDialog(){
            nts.uk.ui.windows.sub.modal('/view/qrm/001/b/index.xhtml', {title: 'å…¥åŠ›æ¬„ã�®èƒŒæ™¯è‰²ã�«ã�¤ã�„ã�¦', dialogClass: "no-close"});
        }
    }
    class ItemSelect {
    code: string;
    
    constructor(code: string) {
        this.code = code;
    }
}

}