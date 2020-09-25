/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.ksm008.g {
    @bean()
    export class KSM008GViewModel extends ko.ViewModel {
        // Flag
        isCompanySelected: KnockoutObservable<boolean> = ko.observable(false);
        isOrganizationSelected: KnockoutObservable<boolean> = ko.observable(false);
        isLoading: KnockoutObservable<boolean> = ko.observable(false);

        constructor(params: any) {
            super();
            const vm = this;

        }

        created() {
            const vm = this;
            vm.initData().done().fail();

            _.extend(window, {vm});
        }

        mounted() {
            $("#G5_2").focus();
        }

        initData(): JQueryPromise<any> {
            const vm = this;
            // vm.$blockui("invisible");
            let dfd = $.Deferred<void>();

            // Initial settings.

            vm.onSelectCompany();
            // vm.onSelectCompany().done(function() {
            //     // dfd.resolve(vm);
            // }).always(() => {
            //     vm.$blockui("clear");
            // });

            return dfd.promise();
        }

        /**
         * on click tab panel company action event
         */
        onSelectCompany(): JQueryPromise<void> {
            // $('.nts-input').ntsError('clear');
            const vm = this;
            // vm.$blockui("invisible");
            let dfd = $.Deferred<void>();

            // Update flags.
            vm.isCompanySelected(true);
            vm.isOrganizationSelected(false);
            vm.isLoading(true);

            $("#G5_2").focus();
            // vm.$blockui("clear");

            return dfd.promise();
        }

        /**
         * on click tab panel Organization action event
         */
        onSelectOrganization(): JQueryPromise<void> {
            const vm = this;
            // vm.$blockui("invisible");
            let dfd = $.Deferred<void>();

            // Update flags.
            vm.isCompanySelected(false);
            vm.isOrganizationSelected(true);
            vm.isLoading(true);

            $("#H2_2").focus();
            // vm.$blockui("clear");

            return dfd.promise();
        }

        /**
         * function on click saveConsecutiveDays action
         */
        saveConsecutiveDays() {
            // if ($('.nts-input').ntsError('hasError')) {
            //     return;
            // };
            const vm = this;
            // vm.$blockui("invisible");
            // var dto: CompanyEstablishmentDto = {
            //     estimateTime: self.companyEstablishmentModel.estimateTimeModel.toDto(),
            //     estimatePrice: self.companyEstablishmentModel.estimatePriceModel.toDto(),
            //     estimateNumberOfDay: self.companyEstablishmentModel.estimateDaysModel.toDto()
            //
            // };
            // service.saveCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), dto).done(function() {
            //     // show message 15
            //     nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
            //         // reload pa
            //         self.loadCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), false);
            //     });
            // }).fail(function(error) {
            //     nts.uk.ui.dialog.alertError(error);
            // }).always(() => {
            //     $('#comboTargetYear').focus();
            //     nts.uk.ui.block.clear();
            // });
            vm.$dialog.error("Where are you, domain");
        }

        /**
         * function on click deleteConsecutiveDays action
         */
        deleteConsecutiveDays() {
            const vm = this;
            vm.$dialog.confirm({messageId: ''}).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    // // vm.$blockui("invisible");
                    // service.deleteCompanyEstablishment(self.companyEstablishmentModel.selectedYear()).done(function() {
                    //     nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                    //         // reload page
                    //         self.loadCompanyEstablishment(self.companyEstablishmentModel.selectedYear(), false);
                    //         $('.nts-input').ntsError('clear');
                    //     });
                    // }).fail(function(error) {
                    //     vm.$dialog.alertError(error);
                    // }).always(() => {
                    //     // $("#G5_2").focus();
                    //     // vm.$blockui("clear");
                    // });
                    vm.$dialog.error("Where are you, domain");
                }
            });
        }

        close() {
            let self = this;
            self.$window.close();
        }
    }
}
