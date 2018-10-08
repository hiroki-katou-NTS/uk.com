module nts.uk.pr.view.qmm040.a.viewmodel {
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        yearMonth: KnockoutObservable<number> = ko.observable(201804);
        onTab: KnockoutObservable<number>=ko.observable(0);
        titleTab:KnockoutObservable<string>=ko.observable(getText('QMM040_3'));

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


        onSelectTab(param) {
            let self=this;

            switch (param) {
                case 0:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    break;
                case 1:
                    //TODO
                    self.onTab(1);
                    break;
                case 2:
                    //TODO
                    self.onTab(2);
                    break;
                case 3:
                    //TODO
                    self.onTab(3);
                    break;
                default:
                    //TODO
                    self.onTab(0);
                    break;
            }
        }

    }



}