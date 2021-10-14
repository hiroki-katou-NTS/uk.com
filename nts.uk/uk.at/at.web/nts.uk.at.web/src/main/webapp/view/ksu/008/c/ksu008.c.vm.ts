module nts.uk.at.ksu008.c {
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

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }

    class Model {
        sourceCode: any;
        sourceName: any;
        destinationCode: any;
        destinationName: any;
        isOverRight:KnockoutObservable<boolean>;

        constructor() {
            var self = this
            self.sourceCode = ko.observable("Sample001");
            self.sourceName = ko.observable("dest name12336647899999999eeeeeeeeeeeeee");
            self.destinationCode = ko.observable("dest001");
            self.destinationName = ko.observable("dest name12336647899999999");
            self.isOverRight=ko.observable(false);
        }
    }
}


