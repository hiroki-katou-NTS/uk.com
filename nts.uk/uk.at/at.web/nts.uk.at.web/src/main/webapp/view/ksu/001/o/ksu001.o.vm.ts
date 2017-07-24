module ksu001.o.viewmodel {

    export class ScreenModel {
        
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        time1: KnockoutObservable<string>;
        time2: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        
        constructor() {
            let self = this;
        self.itemList = ko.observableArray([
            new ItemModel('1', '基本給'),
            new ItemModel('2', '役職手当'),
            new ItemModel('3', '基本給')
        ]);
            
        self.roundingRules = ko.observableArray([
            //KSU001_71
            { code: '1', name: nts.uk.resource.getText("リスト内検索") },
            //KSU001_72
            { code: '2', name: nts.uk.resource.getText("全件検索") }
            ]);
         self.selectedRuleCode = ko.observable(1);
        self.itemName = ko.observable('');
        self.currentCode = ko.observable(3);
        self.selectedCode = ko.observable('1')
        self.time1 = ko.observable('12:00')
        self.time2 = ko.observable('15:00')    
            
        }
        start() {
            let self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }
    }
    class ItemModel {
    code: string;
    name: string;
    label: string;
    
    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
    }
}