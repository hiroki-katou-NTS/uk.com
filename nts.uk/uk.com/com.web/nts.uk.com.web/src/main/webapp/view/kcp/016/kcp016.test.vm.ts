module nts.uk.com.view.kcp016.test.viewmodel {
    const API = {
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        multiple: KnockoutObservable<boolean>;
        onDialog: KnockoutObservable<boolean>;
        selectType: KnockoutObservable<number>;
        selectTypes: KnockoutObservableArray<any>;
        value: any;
        valueDisplay: any;

        created(params: any) {
            const vm = this;
            vm.multiple = ko.observable(true);
            vm.onDialog = ko.observable(false);
            vm.selectType = ko.observable(3);
            vm.selectTypes = ko.observableArray([
                {value: 1, name: "Selected List"},
                {value: 2, name: "Select All"},
                {value: 3, name: "Select First"},
                {value: 4, name: "Select None"}
            ]);
            vm.value = ko.observableArray([]);
            vm.valueDisplay = ko.computed(() => {
                return vm.value().join(", ");
            });
        }

        mounted() {
            const vm = this;

        }

    }

}