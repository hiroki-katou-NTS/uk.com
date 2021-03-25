module nts.uk.ui.at.kdp013.d {

    export type DataContent = {
        id: string;
        targetDate: string;
        description: string;
        link: string;
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        dataSource: KnockoutObservableArray<DataContent> = ko.observableArray([]);

        constructor(params: DataContent[]) {
            super();

            if (!params || !params.length) {
                this.close();
            }

            // binding to grid
            this.dataSource(params);
        }

        // close dialog
        close() {
            const vm = this;

            vm.$window.close();
        }

        linkProceed() {
            const vm = this;

            if (vm !== this) {
                vm.$jump('at', '/view/kdf/005/a/index.xhtml');
            } else {
                vm.$jump('at', '/view/kdf/006/a/index.xhtml');
            }
        }
    }
}