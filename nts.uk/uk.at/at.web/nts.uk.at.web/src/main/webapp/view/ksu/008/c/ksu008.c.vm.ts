module nts.uk.at.ksu008.c {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {


        constructor() {
            super();
            const self = this;

        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }

        mounted() {
            const vm = this;
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }
}


