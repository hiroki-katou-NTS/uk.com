module nts.uk.at.view.kmk008.i {
    export module viewmodel {

        export class ScreenModel {
            monthSettingItemList: KnockoutObservableArray<ComboBoxModel>;
            selectedMonthSettingItemList: KnockoutObservable<string>;

            closingDateSettingItemList: KnockoutObservableArray<ComboBoxModel>;
            selectedClosingDateSettingItemList: KnockoutObservable<string>;

            closingDateClassificationItemList: KnockoutObservableArray<RadioModel>;
            selectedClosingDateClassificationItemList: KnockoutObservable<number>;
            
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

                self.closingDateSettingItemList = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("3", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("4", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("5", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("6", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("7", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("8", nts.uk.resource.getText("KMK008_33"))
                ]);
                self.selectedClosingDateSettingItemList = ko.observable("1");

                self.closingDateClassificationItemList = ko.observableArray([
                    new RadioModel("0", "勤怠の締め日と同じ"),
                    new RadioModel("1", "締め日を指定"),
                ]);
                self.selectedClosingDateClassificationItemList = ko.observable(0);
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }

        export class ComboBoxModel {
            value: string;
            localizedName: string;
            label: string;
            constructor(value: string, localizedName: string) {
                this.value = value;
                this.localizedName = localizedName;
            }
        }

        class RadioModel {
            value: number;
            localizedName: string;
            constructor(value, localizedName) {
                let self = this;
                self.value = value;
                self.localizedName = localizedName;
            }
        }
    }
}
