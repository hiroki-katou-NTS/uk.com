module nts.uk.pr.view.qmm016.a {
    export module viewmodel {
        export class ScreenModel extends base.simplehistory.viewmodel.ScreenBaseModel<model.WageTable, model.WageTableHistory> {
            // UI
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            wageTableList: any;
            monthRange: KnockoutComputed<string>;

            // Process
            selectedCode: KnockoutObservable<string>;
            selectedTab: KnockoutObservable<string>;

            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            selectedDimensionType: KnockoutObservable<number>;
            selectedDimensionName: KnockoutComputed<string>;

            generalTableTypes: KnockoutObservableArray<any>;
            specialTableTypes: KnockoutObservableArray<any>;

            constructor() {
                super('test', service.instance);
                var self = this;

                // Tabs.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '賃金テーブルの情報', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);

                // General table type init.
                self.generalTableTypes = ko.observableArray([
                    { code: '0', name: '一次元' },
                    { code: '1', name: '二次元' },
                    { code: '2', name: '三次元' }
                ]);

                // Speical table type intit.
                self.specialTableTypes = ko.observableArray([
                    { code: '3', name: '資格' },
                    { code: '4', name: '精皆勤手当' }
                ]);

                // Process
                self.selectedCode = ko.observable('0001');
                self.selectedTab = ko.observable('tab-1');

                self.code = ko.observable('');
                self.name = ko.observable('');
                self.startMonth = ko.observable('');
                self.endMonth = ko.observable('9999/12');
                self.monthRange = ko.computed(function() { return self.startMonth() + " ~ " + self.endMonth(); });
                self.selectedDimensionName = ko.computed(function() { return '一次元' });
                self.memo = ko.observable('');
                self.selectedDimensionType = ko.observable(0);
            }
        }
    }
}