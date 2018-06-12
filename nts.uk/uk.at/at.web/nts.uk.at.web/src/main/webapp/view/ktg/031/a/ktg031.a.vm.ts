module nts.uk.at.view.ktg031.a.viewmodel {

    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("KTG031_1") },
                { code: '1', name: nts.uk.resource.getText("KTG031_2") }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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
    //define
    export class InforOvertime {
        employee: string;
        timeLimit: number;
        actualTime: number;
        applicationTime: number;
        totalTime: number;
        constructor(employee: string, timeLimit: number, actualTime: number, applicationTime: number, totalTime: number) {
            this.employee = employee;
            this.timeLimit = timeLimit;
            this.actualTime = actualTime;
            this.applicationTime = applicationTime;
            this.totalTime = actualTime + totalTime;
        }
    }
}
