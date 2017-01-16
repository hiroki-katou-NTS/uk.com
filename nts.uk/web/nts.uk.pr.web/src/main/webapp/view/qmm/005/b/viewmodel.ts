let ko: any, $: any;

module nts.uk.pr.view.qmm005.b.viewmodel {
    export class ViewModel {
        date: any;
        selectedCodes: any;
        constructor() {
            this.date = ko.observable(new Date());
            this.selectedCodes = ko.observable('2');
        }
        toggleColumns(item, event): void {
            $('.toggle').toggleClass('hidden');
            ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
        }
    }
}