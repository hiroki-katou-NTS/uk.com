/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kal012.b {
    @bean()
    export class KAL013BViewModel extends ko.ViewModel {

        constructor(params: any) {
            super();
            const vm = this;
        }

        created(params: any) {
            const vm = this;

            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
        }

        submitAndCloseDialog() {
            const vm = this;

            vm.$window.close({
                // data return to parent
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }
    }
}