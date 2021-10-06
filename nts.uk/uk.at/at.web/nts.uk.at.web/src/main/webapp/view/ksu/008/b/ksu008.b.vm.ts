module nts.uk.at.ksu008.b {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: Model = new Model();

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

    }

    class Model {

        constructor() {
            var self = this

        }
    }
}


