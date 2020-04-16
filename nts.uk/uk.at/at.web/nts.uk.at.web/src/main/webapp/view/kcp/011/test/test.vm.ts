module test.viewmodel {

    export class ScreenModel {
        options: Option;
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        constructor() {
            let self = this;
            self.options = {
                currentCodes: self.currentCodes,
                multiple: true,
                tabindex:2,
                isResize: true,
                isAlreadySetting: true,
                showSearch: true,
                showEmptyItem: true,
            };
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            
            dfd.resolve();

            return dfd.promise();
        }

    }
}