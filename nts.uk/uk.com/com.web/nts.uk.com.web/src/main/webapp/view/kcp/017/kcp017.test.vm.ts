module nts.uk.com.view.kcp017.test.viewmodel {
    const API = {
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        init: KnockoutObservable<number>; // WORKPLACE = 0, WORKPLACE GROUP = 1
        selectType: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        alreadySettingWorkplaces: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<string>;
        selectedIds: KnockoutObservableArray<string>;

        selectTypes: KnockoutObservableArray<any>;

        componentName: KnockoutObservable<string> = ko.observable("kcp017-component");

        created(params: any) {
            const vm = this;
            vm.init = ko.observable(0);
            vm.selectType = ko.observable(3);
            vm.baseDate = ko.observable(new Date);
            vm.alreadySettingWorkplaces = ko.observableArray([]);
            vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
            vm.selectedIds = ko.observableArray([]);

            vm.selectTypes = ko.observableArray([
                {value: 1, name: "Selected List"},
                {value: 2, name: "Select All"},
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
        }

    }

}