module nts.uk.pr.view.qpp007.c {
    export module viewmodel {

        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            outputSettingDetailModel: KnockoutObservable<OutputSettingDetailModel>;

            constructor() {
                var self = this;
                self.items = ko.observableArray<ItemModel>([]);
                self.currentCode = ko.observable();
                self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());

                for (let i = 1; i < 30; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "name " + i, i % 3 === 0));
                }
                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 50, hidden: true },
                    { headerText: '名称', key: 'name', width: 50, hidden: true },
                    { headerText: '説明', key: 'description', width: 100 }
                ]);
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

            public commonSettingBtnClick() {
                nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
            }
        }
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.deletable = deletable;
            }
        }

        export class OutputSettingDetailModel {
            settingCode: KnockoutObservable<string>;
            settingName: KnockoutObservable<string>;
            isPrintOnePageEachPer: KnockoutObservable<boolean>;
            categorySettingTabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedCategory: KnockoutObservable<string>;
            isCreateMode: KnockoutObservable<boolean>;
            categorySetting: KnockoutObservable<CategorySetting>;
            reloadReportItems: () => void;
            constructor() {
                this.settingCode = ko.observable('code');
                this.settingName = ko.observable('name 123');
                this.categorySetting = ko.observable(new CategorySetting());
                this.categorySettingTabs = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: 'supply', title: '支給', content: '#supply', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'deduction', title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'attendance', title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'article-others', title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                this.selectedCategory = ko.observable('supply');
            }
        }

        export class CategorySetting {
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            constructor() {
                var self = this;
                self.items = ko.observableArray<ItemModel>([]);
                self.currentCode = ko.observable();

                for (let i = 1; i < 15; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0));
                }

                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 50, hidden: true },
                    { headerText: '名称', key: 'name', width: 50, hidden: true },
                    { headerText: '説明', key: 'description', width: 100 }
                ]);
            }
        }
    }
}