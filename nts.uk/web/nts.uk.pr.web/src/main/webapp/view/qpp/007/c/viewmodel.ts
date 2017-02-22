module nts.uk.pr.view.qpp007.c {
    export module viewmodel {

        export class ScreenModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.code = ko.observable('1234');
                self.name = ko.observable('4312');
                self.items = ko.observableArray<ItemModel>([]);
                this.currentCode = ko.observable();

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "other" + i));
                }
                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '名称', key: 'name', width: 150, hidden: true },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150 }
                ]);

                self.tabs = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: '勤怠', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: '記事・その他', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-2');
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
                this.deletable = deletable;
            }
        }
    }
}