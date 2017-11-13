module nts.uk.at.view.kmk003.a {

    export module viewmodel {

        export class ScreenModel {

            workFormOptions: KnockoutObservable<Array<ItemWorkForm>>;
            selectedWorkForm: KnockoutObservable<string>;

            settingMethodOptions: KnockoutObservable<Array<ItemSettingMethod>>;
            selectedSettingMethod: KnockoutObservable<string>;
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
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

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