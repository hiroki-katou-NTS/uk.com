module nts.uk.ui.at.kdp013.d {
    @bean()
    export class ViewModel extends ko.ViewModel {
        constructor(private params: any[]) {
            super();
        }

        close() {
            const vm = this;

            vm.$window.close();
        }
    }
}