module nts.uk.at.ksu008.a {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {


        constructor() {
            super();
            const vm = this;

        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }

        mounted() {
            const vm = this;
        }

    }
}


