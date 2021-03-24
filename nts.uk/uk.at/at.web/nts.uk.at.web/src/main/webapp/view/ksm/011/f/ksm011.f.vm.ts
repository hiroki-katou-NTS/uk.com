module nts.uk.com.view.ksm011.f.viewmodel {

    @bean()
    class ViewModel extends ko.ViewModel {
        value: KnockoutObservableArray<string>;

        created(params: any) {
            const vm = this;
            vm.value = ko.observableArray(params || []);
        }

        proceed() {
            const vm = this;
            const items: Array<any> = ko.dataFor($("#kcp016-component")[0]).items();
            vm.$window.close({listItemsSelected: items.filter(i => vm.value().indexOf(i.code) >= 0)});
        }

        cancel() {
            const vm = this;
            vm.$window.close();
        }

    }

}