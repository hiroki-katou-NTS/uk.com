module qrm007.a.viewmodel {
    export class ScreenModel {
        retirementPayItemList: KnockoutObservableArray<RetirementPayItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<RetirementPayItemModel>;
        texteditor: any;
        multilineeditor: any;
        constructor() {
            var self = this;
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 50 },
                { headerText: '名称', prop: 'name', width: 145 },
                { headerText: '印刷用名称', prop: 'printName', width: 145 }
            ]);
            self.retirementPayItemList = ko.observableArray([
                new RetirementPayItemModel("1","2","3","4"),
                new RetirementPayItemModel("5","6","7","8")]);
            self.currentCode = ko.observable(_.first(self.retirementPayItemList()).code);  
            self.currentItem = ko.observable(_.first(self.retirementPayItemList()));
            self.currentCode.subscribe(function(newValue) {
                self.currentItem(_.find(self.retirementPayItemList(), function(item) { return item.code === newValue;}));
            });
            self.texteditor = {
                value: ko.computed(function(){ return self.currentItem().printName; }),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "200px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            self.multilineeditor = {
                value: ko.computed(function(){ return self.currentItem().memo; }),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            
        }
        
        /*
        getItem(newValue): ItemModel {
            let self = this;
            let item: ItemModel = _.find(self.items(), function(item) {
                return item.code === newValue;
            });
            return item;
        }
        */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            qrm007.a.service.getRetirementPayItemList().done(function(data) {
                dfd.resolve();
            }).fail(function(res) {

            });
            
            
            return dfd.promise();
        }
        
        
        updateData(){
            /*
            var self = this;
            var dfd = $.Deferred();
            var data = {
                    
            }
            qrm007.a.service.updateData().done(function(data) {
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
            */
        }
        
    }
    
    class RetirementPayItemModel {
        code: string;
        name: string;
        printName: string;
        memo: string;
        
        constructor(code: string, name: string, printName: string, memo: string) {
            this.code = code;
            this.name = name;
            this.printName = printName;
            this.memo = memo;
        }
    }
}