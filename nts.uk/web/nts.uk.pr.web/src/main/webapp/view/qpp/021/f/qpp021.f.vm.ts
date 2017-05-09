module qpp021.f.viewmodel {
    export class ScreenModel {
        myModel: KnockoutObservable<MyModel>;
        splitLineOutput: KnockoutObservable<any>;
        constructor() {
            this.myModel = ko.observable(new MyModel());
            this.splitLineOutput = ko.observableArray<SelectionModel>([
                new SelectionModel('1', 'する'),
                new SelectionModel('2', 'しない')
            ]);
        }

        /**
         * Start page.
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Event when click save button.
         */
        public onSaveBtnClicked(): void {
            let self = this;
            // Validate.
            if ($('.nts-input').ntsError('hasError')) {
                return;
            }
            // Save.
            service.save(ko.toJS(self.myModel));
        }

        /**
         * Event when click close button.
         */
        public onCloseBtnClicked(): void {
            nts.uk.ui.windows.close();
        }

    }

    export class MyModel {
        inp1: KnockoutObservable<string>;
        inp2: KnockoutObservable<string>;
        inp3: KnockoutObservable<string>;
        inp4: KnockoutObservable<string>;
        selectedSplitLineOutput: KnockoutObservable<string>;

        constructor() {
            this.inp1 = ko.observable('1');
            this.inp2 = ko.observable('2');
            this.inp3 = ko.observable('3');
            this.inp4 = ko.observable('4');
            this.selectedSplitLineOutput = ko.observable('1');
        }
    }

    /**
     *  Class SelectionModel.
     */
    export class SelectionModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}