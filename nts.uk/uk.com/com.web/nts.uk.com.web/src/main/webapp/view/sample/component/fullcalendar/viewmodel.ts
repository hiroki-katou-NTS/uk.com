module nts.uk.ui.com.sample.fullcalendar {
    @bean()
    export class ViewModel extends ko.ViewModel {
        show: KnockoutObservable<boolean> = ko.observable(true);

        changeVisible() {
            const vm = this;

            vm.show(!vm.show());
        }
    }
}