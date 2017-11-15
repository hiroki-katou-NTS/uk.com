module nts.uk.at.view.kmk003.a {

    export module viewmodel {

        export class ScreenModel {

            workFormOptions: KnockoutObservableArray<ItemWorkForm>;
            selectedWorkForm: KnockoutObservable<string>;

            settingMethodOptions: KnockoutObservableArray<ItemSettingMethod>;
            selectedSettingMethod: KnockoutObservable<string>;

            workTimezoneItems: KnockoutObservableArray<any>;
            columns: KnockoutObservable<any>;
            selectedWorkTimezone: KnockoutObservable<string>;

            //screen mode
            isUpdateMode: KnockoutObservable<boolean>;
            //sift code input
            siftCode: KnockoutObservable<string>;
            siftCodeOption: KnockoutObservable<any>;

            siftName: KnockoutObservable<string>;
            siftNameOption: KnockoutObservable<any>;

            siftShortName: KnockoutObservable<string>;
            siftShortNameOption: KnockoutObservable<any>;

            siftSymbolName: KnockoutObservable<string>;
            siftSymbolNameOption: KnockoutObservable<any>;

            //color
            pickColor: KnockoutObservable<string>;

            siftRemark: KnockoutObservable<string>;
            siftRemarkOption: KnockoutObservable<any>;

            memo: KnockoutObservable<string>;
            memoOption: KnockoutObservable<any>;

            //tab mode
            tabModeOptions: KnockoutObservableArray<any>;
            tabMode: KnockoutObservable<string>;

            //use half day
            useHalfDayOptions: KnockoutObservableArray<any>;
            useHalfDay: KnockoutObservable<string>;

            //tabs
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;

            //data
            data: any;
            constructor() {
                let self = this;
                self.workFormOptions = ko.observableArray([
                    new ItemWorkForm('1', '基本給'),
                    new ItemWorkForm('2', '役職手当'),
                    new ItemWorkForm('3', '基本給')
                ]);
                self.selectedWorkForm = ko.observable('1');
                self.settingMethodOptions = ko.observableArray([
                    new ItemSettingMethod('1', '基本給'),
                    new ItemSettingMethod('2', '役職手当'),
                    new ItemSettingMethod('3', '基本給')
                ]);
                self.selectedSettingMethod = ko.observable('1');

                self.workTimezoneItems = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'name', width: 130 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'description', width: 50 }
                ]);
                self.selectedWorkTimezone = ko.observable('');

                self.isUpdateMode = ko.observable(true);

                self.siftCode = ko.observable('');
                self.siftCodeOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "60"
                }));
                self.siftName = ko.observable('');
                self.siftNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "150"
                }));
                self.siftShortName = ko.observable('');
                self.siftShortNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "60"
                }));

                self.siftSymbolName = ko.observable('');
                self.siftSymbolNameOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "50"
                }));

                //color
                self.pickColor = ko.observable('');

                self.siftRemark = ko.observable('');
                self.siftRemarkOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "200"
                }));

                self.memo = ko.observable('');
                self.memoOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                    width: "200"
                }));

                //tab mode
                self.tabModeOptions = ko.observableArray([
                    { code: "1", name: nts.uk.resource.getText("KMK003_190") },
                    { code: "2", name: nts.uk.resource.getText("KMK003_191") }
                ]);

                self.tabMode = ko.observable("1");

                //use half day

                self.useHalfDayOptions = ko.observableArray([
                    { code: "1", name: nts.uk.resource.getText("KMK003_49") },
                    { code: "2", name: nts.uk.resource.getText("KMK003_50") }
                ]);

                self.useHalfDay = ko.observable("1");

                //
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK003_17"), content: '.tab-a1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK003_18"), content: '.tab-a2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK003_89"), content: '.tab-a3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: nts.uk.resource.getText("KMK003_19"), content: '.tab-a4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-5', title: nts.uk.resource.getText("KMK003_20"), content: '.tab-a5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-6', title: nts.uk.resource.getText("KMK003_90"), content: '.tab-a6', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-7', title: nts.uk.resource.getText("KMK003_21"), content: '.tab-a7', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-8', title: nts.uk.resource.getText("KMK003_200"), content: '.tab-a8', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-9', title: nts.uk.resource.getText("KMK003_23"), content: '.tab-a9', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-10', title: nts.uk.resource.getText("KMK003_24"), content: '.tab-a10', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-11', title: nts.uk.resource.getText("KMK003_25"), content: '.tab-a11', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-12', title: nts.uk.resource.getText("KMK003_26"), content: '.tab-a12', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-13', title: nts.uk.resource.getText("KMK003_27"), content: '.tab-a13', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-14', title: nts.uk.resource.getText("KMK003_28"), content: '.tab-a14', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-15', title: nts.uk.resource.getText("KMK003_29"), content: '.tab-a15', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-16', title: nts.uk.resource.getText("KMK003_30"), content: '.tab-a16', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');

                //data get from service
                self.data = { test: "abc" };
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                //                service.
                // set ntsFixedTable style
                dfd.resolve();
                return dfd.promise();
            }

        }

        export class ItemWorkForm {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemSettingMethod {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}