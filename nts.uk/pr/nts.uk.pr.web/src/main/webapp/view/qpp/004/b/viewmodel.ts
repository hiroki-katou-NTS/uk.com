module qpp004.b.viewmodel {
    export class Step {
        id: KnockoutObservable<any>;
        content: KnockoutObservable<any>;

        constructor(id, content) {
            var self = this;
            this.id = ko.observable(id);
            this.content = ko.observable(content);
        }
    }

    export class ItemModel {
        id: any;
        code: any;
        name: any;
        constructor(id, code, name) {
            var self = this;
            this.id = id;
            this.code = code;
            this.name = name;
        }
    }

    export class Listbox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<any>;
        currentCode: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000001', 'A00000000001', '日通　社員１'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000002', 'A00000000002', '日通　社員2'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000003', 'A00000000003', '日通　社員3'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000004', 'A00000000004', '日通　社員4'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000005', 'A00000000005', '日通　社員5'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000006', 'A00000000006', '日通　社員6'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000007', 'A00000000007', '日通　社員7'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000008', 'A00000000008', '日通　社員8'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000009', 'A00000000009', '日通　社員9'),
                new qpp004.b.viewmodel.ItemModel('A00000000000000000000000000000000010', 'A00000000010', '日通　社員１0'),
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);
        }
    }

    export class Wizard {
        stepList: KnockoutObservableArray<any>;
        stepSelected: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.stepList = ko.observableArray([
                new qpp004.b.viewmodel.Step('step-1', '.step-1'),
                new qpp004.b.viewmodel.Step('step-2', '.step-2'),
                new qpp004.b.viewmodel.Step('step-3', '.step-3'),
            ]);
            self.stepSelected = ko.observable(new Step('step-2', '.step-2'));
        }

        begin(): any {
            (<any>$('#wizard')).begin();
        }

        end(): any {
            $('#wizard').end();
        }

        next(): any {
            var currStep = $('#wizard').steps('getCurrentIndex');                    
            $('#wizard').steps('next');
            if(currStep == 0) {
               $('#list-box').trigger('validate'); 
            }
        }

        previous(): any {
            (<any>$('#wizard')).steps('previous');
        }
        
        step1(): any {
            $('#wizard').setStep(0);
        }
        step2(): any {
            $('#wizard').setStep(1);
        }
        step3(): any {
            $('#wizard').setStep(2);
        }
    }

    export class ScreenModel {
        wizard: Wizard;
        currentProcessingYearMonth: KnockoutObservable<any>;
        processingNo: KnockoutObservable<any>;
        processingYM: KnockoutObservable<any>;
        listbox: Listbox;
        constructor(data: any) {
            var self = this;
            self.wizard = new qpp004.b.viewmodel.Wizard();
            self.listbox = new qpp004.b.viewmodel.Listbox();
            
            self.currentProcessingYearMonth = ko.observable(data.displayCurrentProcessingYm);
            self.processingNo = ko.observable(data.processingNo);
            self.processingYM = ko.observable(data.currentProcessingYm);
        }
        processCreateData(): any {
            var self = this;
            
            var result = [];
            _.forEach(self.listbox.itemList(), function(item) {
                  if (self.listbox.selectedCodes().indexOf(item.id) >= 0) {
                    result.push(item);
                  }
            }); 
            
            var data = {
                personIdList: result, // list id person
                processingNo: self.processingNo(),
                processingYearMonth: self.processingYM()
            };

            nts.uk.ui.windows.setShared("data", data);
            nts.uk.ui.windows.sub.modal("/view/qpp/004/l/index.xhtml", { title: "給与データの作成", dialogClass: "no-close" });
        }
        
        backqpp004a() : any {
            nts.uk.request.jump("/view/qpp/004/a/index.xhtml", {});    
        }
    }
}