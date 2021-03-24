module nts.uk.com.view.kcp017.test.viewmodel {
    const API = {
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        init: KnockoutObservable<number>; // WORKPLACE = 0, WORKPLACE GROUP = 1
        multiple: KnockoutObservable<boolean>;
        onDialog: KnockoutObservable<boolean>;
        showAlreadySetting: KnockoutObservable<boolean>;
        selectType: KnockoutObservable<number>;
        rows: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        alreadySettingWorkplaces: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<string>;
        selectedIds: KnockoutObservableArray<any> | KnockoutObservable<any>;

        selectTypes: KnockoutObservableArray<any>;

        componentName: KnockoutObservable<string> = ko.observable("kcp017-component");

        created(params: any) {
            const vm = this;
            vm.init = ko.observable(0);
            vm.multiple = ko.observable(true);
            vm.onDialog = ko.observable(false);
            vm.showAlreadySetting = ko.observable(false);
            vm.selectType = ko.observable(1);
            vm.rows = ko.observable(10);
            vm.baseDate = ko.observable(new Date);
            vm.alreadySettingWorkplaces = ko.observableArray([]);
            vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
            vm.selectedIds = ko.observableArray([]);

            vm.selectTypes = ko.observableArray([
                {value: 1, name: "Selected List"},
                {value: 2, name: "Select All", enable: vm.multiple},
                {value: 3, name: "Select First"},
                {value: 4, name: "Select None"}
            ]);
        }

        mounted() {
            const vm = this;
            vm.init.subscribe(value => {
                vm.componentName.valueHasMutated();
            });
            vm.selectType.subscribe(value => {
                vm.componentName.valueHasMutated();
            });
            vm.multiple.subscribe(value => {
                if (value) {
                    vm.selectedIds = ko.observableArray([]);
                } else {
                    vm.selectedIds = ko.observable(null);
                }
                vm.componentName.valueHasMutated();
            });
            vm.onDialog.subscribe(value => {
                vm.componentName.valueHasMutated();
            });
            vm.rows.subscribe(value => {
                vm.componentName.valueHasMutated();
            });
            vm.showAlreadySetting.subscribe(value => {
                vm.componentName.valueHasMutated();
            });
        }

    }

}