module nts.uk.at.view.kdw006.b.viewmodel {
    export class ScreenModelB {
        //Daily perform id from other screen
        settingUnit: KnockoutObservable<number>;
        comment: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
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
            let dfd = $.Deferred();
            service.getDailyPerform().done(function(data) {
                self.settingUnit(data.settingUnit);
                self.comment(data.comment);
                dfd.resolve();
            });
            return dfd.promise();

        }

        saveData() {
            let self = this;
            let perform = {
                settingUnit: self.settingUnit(),
                comment: self.comment()
            };
            service.update(perform).done(function(data) {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
        };

    }
}
