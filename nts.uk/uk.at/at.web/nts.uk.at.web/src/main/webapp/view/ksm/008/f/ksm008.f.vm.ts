/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.ksm008.f {
    @bean()
    export class KSM008FViewModel extends ko.ViewModel {


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

            vm.$dialog.error("Where are you, domain");
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

        close() {
            let self = this;
            self.$window.close();
        }
    }
}
