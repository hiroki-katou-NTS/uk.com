module nts.uk.at.view.kmk013.d {
    export module viewmodel {
        export class ScreenModel {
            roundingRules1: KnockoutObservableArray<any>;
            roundingRules2: KnockoutObservableArray<any>;
            selectedRuleCode1: any;
            selectedRuleCode2: any;
            itemListD310: KnockoutObservableArray<any>;
            itemListD410: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            selectedId310: KnockoutObservable<number>;
            selectedId410: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            selectedValueD3: KnockoutObservable<any>;
            selectedValueD4: KnockoutObservable<any>;

            constructor() {
                var self = this;
                self.roundingRules1 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_172') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_173') }
                ]);
                self.roundingRules2 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_176') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_177') }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                self.selectedRuleCode2 = ko.observable(1);

                self.itemListD310 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_186')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_187')),
                ]);
                self.itemListD410 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_196')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_197')),
                ]);
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
                self.selectedValueD3 = ko.observable(1);
                self.selectedValueD4 = ko.observable(1);
                self.selectedId310 = ko.observable(1);
                self.selectedId410 = ko.observable(1);

            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }