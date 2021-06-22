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

        constructor() {
            super();
            const vm = this;
            vm.enable = ko.observable(true);
            vm.required = ko.observable(true);

            vm.startDateString = ko.observable("");
            vm.endDateString = ko.observable("");
            vm.dateValue = ko.observable({});

            vm.startDateString.subscribe(function(value){
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });

            vm.endDateString.subscribe(function(value){
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
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
            vm.$window.modal("/view/kha/003/c/index.xhtml").then(() => {

            });
        }
    }
}


