module nts.uk.at.view.kdw006.b1.viewmodel {
    export class ScreenModel {
        //Daily perform id from other screen
        settingUnit: KnockoutObservable<number>;
        comment: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.settingUnit = ko.observable(null);
            self.comment = ko.observable(null);
            self.roundingRules = ko.observableArray([
                { code: 0, name: '権限' },
                { code: 1, name: '勤務種別' }
            ]);
        }

        start() {
            let self = this;
            let dfd = $.Deferred();
            self.getDailyPerform().done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        getDailyPerform(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            service.getDailyPerform().done(function(data) {
                self.settingUnit(data.settingUnit);
                self.comment(data.comment);
                dfd.resolve();
            });
            return dfd.promise();

        }

        saveData() {
            var self = this;
            var perform = {
                settingUnit: self.settingUnit(),
                comment: self.comment()
            };
            service.update(perform).done(function(data) {
                self.getDailyPerform();
            });
        };

    }
}
