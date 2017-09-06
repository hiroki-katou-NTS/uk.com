module nts.uk.at.view.cmm018.n {
    export module viewmodel {
        export class ScreenModel {

            //Right table's properties.
            items2: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCodeList2: KnockoutObservableArray<any>;
            
            //Left table's properties.
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;

            //Date picker's properties.
            date: KnockoutObservable<string>;

            //Tab Panel's properties.
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            constructor() {
                var self = this;

                //Init right table.
                self.items2 = ko.observableArray([]);

                for (let i = 1; i < 15; i++) {
                    self.items2.push(new ItemModel('00' + i, '基本給' + i));
                }

                self.columns2 = ko.observableArray([
                    { headerText: '社員CD', key: 'code', width: 89 },
                    { headerText: '氏名', key: 'name', width: 89 }
                ]);

                self.currentCodeList2 = ko.observableArray([]);
                
                //Init left table.
                self.items = ko.observableArray([]);

                for (let i = 1; i < 15; i++) {
                    self.items.push(new ItemModel('00' + i, '基本給' + i));
                }

                self.columns = ko.observableArray([
                    { headerText: '社員CD', key: 'code', width: 89 },
                    { headerText: '氏名', key: 'name', width: 89 }
                ]);

                self.currentCodeList = ko.observableArray([]);

                //Init date picker.
                //self.date = ko.observable('20000101');
                var currentDate = (new Date()).toISOString().split('T')[0];
                self.date = ko.observable(currentDate);

                //Init TabPanel.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: 'クイック検索', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '詳細検索', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-2');
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
}
