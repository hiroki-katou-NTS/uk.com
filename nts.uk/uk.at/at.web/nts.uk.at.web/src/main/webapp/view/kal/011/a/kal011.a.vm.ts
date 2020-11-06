module nts.uk.at.kal014.a {

    const PATH_API = {

    }

    @bean()
    export class Kal011AViewModel extends ko.ViewModel {

        constructor(props: any) {
            super();
            const vm = this;

        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }

    }
}