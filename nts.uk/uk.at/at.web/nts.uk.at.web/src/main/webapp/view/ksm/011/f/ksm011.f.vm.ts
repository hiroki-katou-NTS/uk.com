module nts.uk.com.view.ksm011.f.viewmodel {

    @bean()
    class ViewModel extends ko.ViewModel {
        value: KnockoutObservableArray<string>;

        created(params: any) {
            const vm = this;
            vm.value = ko.observableArray(params || []);
            setTimeout(() => {
                $("#single-list_container").focus();
            }, 200);
        }

        proceed() {
            const vm = this;
            const items: Array<any> = ko.dataFor($("#kcp016-component")[0]).items();
            const selectedItems = items.filter(i => vm.value().indexOf(i.code) >= 0);
            if (selectedItems.length == 0) {
                vm.$dialog.error({messageId: "Msg_718"});
                return;
            }
            vm.$window.close({listItemsSelected: selectedItems});
        }

        cancel() {
            const vm = this;
            vm.$window.close();
        }

    }

}