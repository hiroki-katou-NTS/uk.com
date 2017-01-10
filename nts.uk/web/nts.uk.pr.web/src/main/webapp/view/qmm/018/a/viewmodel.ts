module qmm018.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCodeList: KnockoutObservableArray<any>;
        texteditor1: any;
        texteditor2: any;
        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '就業からの連携' },
                { code: '2', name: '明細書項目から選択' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.itemList = ko.observableArray([
                { code: '1', name: '足した後' },
                { code: '2', name: '足す前' }
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCodeList = ko.observableArray(['']);
            self.texteditor1 = {
                value: ko.computed(function(){
                    let s: string = self.selectedCodeList()[0];
                    for(let i=1;i<self.selectedCodeList().length;i++){
                        s += self.selectedCodeList().pop[i];   
                    } 
                    return s;
                }),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "500px",
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
                    placeholder: "",
                    width: "30px",
                    textalign: "center"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            qmm018.a.service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
        
        
        
        openSubWindow() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", {title: "労働日数項目一覧", dialogClass: "no-close"}).onClosed(function(){
                //self.selectedCodeList.push(nts.uk.ui.windows.getShared('selectedCodeList')); 
                //console.log(self.selectedCodeList);
                //$("#selected-inp").val(self.selectedCodeList()[0]);
                let selectedList = nts.uk.ui.windows.getShared('selectedCodeList');
                console.log(selectedList()[0]);
                self.selectedCodeList.removeAll();
                self.selectedCodeList.push(selectedList()[0]);
                console.log(self.selectedCodeList()[0]);
                for(let i=1;i<selectedList().length;i++){
                    self.selectedCodeList.push(selectedList()[i]);   
                } 
                console.log(self.selectedCodeList()[1]);
            }); 
        }
    }
}