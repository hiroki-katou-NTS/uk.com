module nts.uk.at.view.kmk008.i {
    export module viewmodel {

        export class ScreenModel {
            startingMonth: KnockoutObservableArray<ComboBoxModel>;
            selectedStartingMonth: KnockoutObservable<string>;

            closingDate: KnockoutObservableArray<ComboBoxModel>;
            selectedClosingDate: KnockoutObservable<string>;

            closingDateClassification: KnockoutObservableArray<RadioModel>;
            selectedClosingDateClassification: KnockoutObservable<number>;
            isEnableClosingDateClassification: KnockoutObservable<boolean>;

            numberOfTimesOverLimit: KnockoutObservableArray<ComboBoxModel>;
            selectedNumberOfTimesOverLimit: KnockoutObservable<string>;

            targetClassification: KnockoutObservableArray<any>;
            selectedTargetClassificationAlarm: KnockoutObservable<string>;
            selectedTargetClassificationWorkSchedule: KnockoutObservable<string>;
            isEnableTargetClassification: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self._init();
                self.selectedClosingDateClassification.subscribe(function(newValue) {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    if (newValue == 0) {
                        self.isEnableClosingDateClassification(false);
                        self.isEnableTargetClassification(false)
                        return;
                    }
                    self.isEnableClosingDateClassification(true);
                    self.isEnableTargetClassification(true);
                });

            }
            _init(): void {
                let self = this;
                self.startingMonth = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("3", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("4", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("5", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("6", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("7", nts.uk.resource.getText("KMK008_32")),
                    new ComboBoxModel("8", nts.uk.resource.getText("KMK008_32"))

                ]);
                self.selectedStartingMonth = ko.observable("1");

                self.closingDate = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("3", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("4", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("5", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("6", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("7", nts.uk.resource.getText("KMK008_33")),
                    new ComboBoxModel("8", nts.uk.resource.getText("KMK008_33"))
                ]);
                self.selectedClosingDate = ko.observable("1");

                self.closingDateClassification = ko.observableArray([
                    new RadioModel(0, "勤怠の締め日と同じ"),
                    new RadioModel(1, "締め日を指定"),
                ]);
                self.selectedClosingDateClassification = ko.observable(0);

                self.numberOfTimesOverLimit = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("3", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("4", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("5", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("6", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("7", nts.uk.resource.getText("KMK008_34")),
                    new ComboBoxModel("8", nts.uk.resource.getText("KMK008_34"))
                ]);
                self.selectedNumberOfTimesOverLimit = ko.observable("1");

                self.targetClassification = ko.observableArray([
                    new ComboBoxModel("1", nts.uk.resource.getText("KMK008_35")),
                    new ComboBoxModel("2", nts.uk.resource.getText("KMK008_34")),
                ]);
                self.selectedTargetClassificationAlarm = ko.observable("1");
                self.selectedTargetClassificationWorkSchedule = ko.observable("1");

                //init enable
                self.isEnableClosingDateClassification = ko.observable(false);
                self.isEnableTargetClassification = ko.observable(false);

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
