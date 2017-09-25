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
        itemList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, text('CPS002_26')),
            new BoxModel(2, text('CPS002_27')),
            new BoxModel(3, text('CPS002_28'))
        ]);
        selectedId: KnockoutObservable<number> = ko.observable(1);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {


        }

        next() {
            $('#wizard').ntsWizard("next");
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