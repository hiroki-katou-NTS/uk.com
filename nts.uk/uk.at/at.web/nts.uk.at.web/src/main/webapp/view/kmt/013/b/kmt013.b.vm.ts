module nts.uk.at.view.kmt013.b {

    const PATH = {
        init: 'at/shared/scherec/workmanagement/work/kmt001/init',
        saveRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/register',
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        param: any;
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        count: number = 100;

        created(params: any) {
            const vm = this;
            // vm.param = params;
            console.log(params)
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
            vm.$window.close({
                // data return to parent
            });
        }
    }

    class PARAM {
        public title: string = '';
        public lstSelected: Array<string> = [];
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