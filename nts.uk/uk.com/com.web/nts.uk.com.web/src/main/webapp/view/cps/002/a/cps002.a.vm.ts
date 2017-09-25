module cps002.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;



    export class ViewModel {
        stepList = [
            { content: '.step-1' },
            { content: '.step-2' },
            { content: '.step-3' },
            { content: '.step-4' }
        ];
        stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
        date: KnockoutObservable<Date> = ko.observable(nts.uk.time.UTCDate(2000, 0, 1));
        simpleValue: KnockoutObservable<String> = ko.observable('pikamieo');
        constructor() {


        }

        next() {
            $('#wizard').ntsWizard("next");
        }
    }

}