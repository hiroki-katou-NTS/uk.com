module nts.uk.at.view.kmt013.b {

    @bean()
    class ViewModel extends ko.ViewModel {

        items: KnockoutObservableArray<ItemModelKtm013> =ko.observableArray([]);
        currentCode: KnockoutObservable<any> = ko.observable('');
        count: number = 100;

        constructor(params: any) {
            // data transfer from parent view call modal
            super();
        }

        created(params: Array<ItemModelKtm013>) {
            const vm = this;
            $("#B3_btn").focus();
            vm.items(params);
        }

        mounted() {
            const vm = this;
            $("#B3_btn").focus();
        }

        closeModal() {
            const vm = this;
            vm.$window.close();
        }
    }

    export class ItemModelKtm013 {
        destination: string;
        state: string;

        constructor(destination: string, state: string) {
            this.destination = destination;
            this.state = state;
        }
    }
}