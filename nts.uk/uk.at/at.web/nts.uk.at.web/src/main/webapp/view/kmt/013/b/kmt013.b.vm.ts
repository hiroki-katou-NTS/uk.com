module nts.uk.at.view.kmt013.b {

    const PATH = {
        init: 'at/shared/scherec/workmanagement/work/kmt001/init',
        saveRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/register',
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        param: KnockoutObservableArray<string> = ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        count: number = 100;

        constructor(params: any) {
            // data transfer from parent view call modal
            super();
        }

        created(params: any) {
            const vm = this;
            $("#B3_btn").focus();
            vm.param(params);
            vm.items = ko.observableArray([]);
            // for(let i = 1; i < 30; i++) {
            //     vm.items.push(new ItemModel('0000000000' + i, '基本給'));
            // }
            this.currentCode = ko.observable();
        }

        mounted() {
            const vm = this;
            $("#B3_btn").focus();
        }

        closeModal() {
            const vm = this;
            console.log(vm.param());
            vm.$window.close();
        }
    }

    class ItemModel {
        destination: string;
        state: string;

        constructor(destination: string, state: string) {
            this.destination = destination;
            this.state = state;
        }
    }
}