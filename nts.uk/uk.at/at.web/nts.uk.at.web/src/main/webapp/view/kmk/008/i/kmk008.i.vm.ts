module nts.uk.at.view.kmk008.i {
    export module viewmodel {

        export class ScreenModel {
            monthSettingItemList: KnockoutObservableArray<ComboBoxModel>;
            selectedMonthSettingItemList: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self._init();

            }
            _init(): void {
                let self = this;
                self.monthSettingItemList = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("3", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("4", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("5", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("6", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("7", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("8", nts.uk.resource.getText("KMK008_32"))
                ]);
                self.selectedMonthSettingItemList = ko.observable("1");
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }

        export class ComboBoxModel {
            code: string;
            name: string;
            label: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}
