module nts.uk.at.kha003.c {

    const API = {
        //TODO api path
    };

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


