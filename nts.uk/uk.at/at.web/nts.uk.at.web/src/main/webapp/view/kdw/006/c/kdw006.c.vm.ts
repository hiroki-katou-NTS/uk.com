module nts.uk.at.view.kdw006.c {
    export module viewmodel {
        export class ScreenModel {

            //Init value for switch button1
            selectedRuleCode = ko.observable(1);

            //Nit value for checkbox1
            enable = ko.observable(true);
            constructor() {
                let self = this;

            }

            saveData() {
                alert('screen c');
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }
    }
}
