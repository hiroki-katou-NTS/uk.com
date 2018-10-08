module nts.uk.pr.view.qmm040.a.viewmodel {
    export class ScreenModel {

        yearMonth:KnockoutObservable<number>=ko.observable(201804);


        constructor() {
            var self = this;
            $("#A5_9").ntsFixedTable({height: 350, width: 520});


        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }
    }
}