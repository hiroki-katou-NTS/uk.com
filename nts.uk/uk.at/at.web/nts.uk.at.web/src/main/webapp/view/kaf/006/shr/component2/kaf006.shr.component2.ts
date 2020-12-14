module nts.uk.at.view.kaf006.shr.component2.viewmodel {

    @component({
        name: 'kaf006-shr-component2',
        template: `
        <div id="kaf006component2">
            <div class = "table">
                <div class="cell col-1">
                    <div class="cell valign-center" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_15')"></div>
                </div>
                <div class="cell">
                    <div data-bind="ntsSwitchButton: { 
                        name: $i18n('KAF006_15'),
                        options: hdAppSet,
                        optionsValue: 'holidayAppType',
                        optionsText: 'displayName',
                        value: selectedType
                    }"></div>
                </div>
            </div>
        </div>
        `
    })

    class Kaf006Component2ViewModel extends ko.ViewModel {
        // {holidayAppType: number, displayName: "string"}
        hdAppSet: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedType: KnockoutObservable<any> = ko.observable();

        created(params: any) {
            const vm = this;

            if (params) {
                vm.hdAppSet = params.hdAppSet;
                vm.selectedType = params.selectedType;
            }
        }

        mounted() {
            const vm = this;

            vm.hdAppSet.subscribe(() => {
                if (vm.hdAppSet().length > 0) {
                    vm.selectedType(vm.hdAppSet()[0].holidayAppType);
                }
            });

            // check selected item
            vm.selectedType.subscribe(() => {
                console.log(this.selectedType())
            });
        }
    }
}