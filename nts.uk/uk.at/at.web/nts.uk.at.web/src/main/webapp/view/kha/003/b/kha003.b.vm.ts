module nts.uk.at.kha003.b {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        enable: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        isCsvOutPut: KnockoutObservable<boolean>;
        totalUnit: KnockoutObservable<any>;

        constructor() {
            super();
            const vm = this;
            vm.enable = ko.observable(true);
            vm.required = ko.observable(true);

            vm.startDateString = ko.observable("");
            vm.endDateString = ko.observable("");
            vm.dateValue = ko.observable({});
            vm.isCsvOutPut = ko.observable(false);
            vm.totalUnit = ko.observable(null);
            vm.startDateString.subscribe(function (value) {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });

            vm.endDateString.subscribe(function (value) {
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('kha003AShareData').done((data: any) => {
                vm.isCsvOutPut(data.isCsvOutPut);
                vm.totalUnit(data.totalUnit);
            })
        }

        mounted() {
            const vm = this;
        }

        /**
         * Event on click cancel button.
         */
        public cancel(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Event on click decide button.
         */
        public decide(): void {
            const vm = this;
            nts.uk.ui.windows.close();
            if (!vm.isCsvOutPut()) {
                vm.$window.modal("/view/kha/003/c/index.xhtml").then(() => {

                });
            } else {
                alert("Api for csv output")
                vm.isCsvOutPut(false)
            }
        }
    }
}


