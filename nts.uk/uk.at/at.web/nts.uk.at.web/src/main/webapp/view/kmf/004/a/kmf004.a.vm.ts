module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        selectedValue: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        option1: KnockoutObservable<any>;
        option2: KnockoutObservable<any>;
        date: KnockoutObservable<string>;
        roundingRules1: KnockoutObservableArray<any>;
        roundingRules2: KnockoutObservableArray<any>;
        roundingRules3: KnockoutObservableArray<any>;
        roundingRules4: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        selectedRuleCode: any;

        constructor() {
            var self = this;

            self.items = ko.observableArray([]);
            self.enable = ko.observable(true);
            self.selectedValue = ko.observable(false);
            self.option1 = ko.observable({ value: 0, text: nts.uk.resource.getText('KMF004_23') });
            self.option2 = ko.observable({ value: 1, text: nts.uk.resource.getText('KMF004_24') });
            self.date = ko.observable('');
            self.selectedCode = ko.observable('0002')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            for (let i = 1; i < 5; i++) {
                self.items.push(new ItemModel('00' + i, '基本給'));
            }

            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);



            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);


            self.roundingRules1 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_38') },
                { code: '2', name: nts.uk.resource.getText('KMF004_39') }
            ]);

            self.roundingRules2 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_51') },
                { code: '2', name: nts.uk.resource.getText('KMF004_52') }
            ]);

            self.roundingRules3 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_125') },
                { code: '2', name: nts.uk.resource.getText('KMF004_126') }
            ]);

            self.roundingRules4 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_61') },
                { code: '2', name: nts.uk.resource.getText('KMF004_62') }
            ]);

            self.selectedRuleCode = ko.observable(1);

            self.currentCode = ko.observable();

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText('KMF004_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText('KMF004_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText('KMF004_19'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: nts.uk.resource.getText('KMF004_20'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: nts.uk.resource.getText('KMF004_21'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        
        openBDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        openDDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        
        openGDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/g/index.xhtml', { title: '続柄に対する付与日数', height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }                                                                                 

        openHDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/h/index.xhtml', { title: '続柄の登録', height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
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