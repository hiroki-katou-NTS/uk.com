/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.kdp001.a {

    @bean()
    class KDP001AViewModel extends ko.ViewModel {
        mode: KnockoutObservable<string> = ko.observable('');

        param: string = '';

        created() {

        }
    }
}