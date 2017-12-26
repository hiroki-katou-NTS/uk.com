module nts.uk.at.view.kal003.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = kal003.share.model;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        
        listAlarmCheckCondition: KnockoutObservableArray<model.AlarmCheckConditionByCategory>;
        selectedAlarmCheckCondition: KnockoutObservable<model.AlarmCheckConditionByCategory>;

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL003_15'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL003_16'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給')
            ]);
            self.selectedCode = ko.observable('1');
            
            self.listAlarmCheckCondition = ko.observableArray([]);
            self.selectedAlarmCheckCondition = ko.observable(new model.AlarmCheckConditionByCategory('001', 'name1', 'category1', [], new model.AlarmCheckTargetCondition(true, true, false, false, [], [], [], [])));
        }
        
        
        
        
        
        test(): void{
            console.log("success!");
            nts.uk.ui.windows.sub.modal("../../004/b/index.xhtml").onClosed(() => {
                console.log("success!");
            });
        }
    }
    
    class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}
}