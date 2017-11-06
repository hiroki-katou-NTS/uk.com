module nts.uk.com.view.cdl023.a.viewmodel {

    export class ScreenModel {

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        totalSelection: KnockoutObservable<number>;
        totalSelDisp: KnockoutObservable<string>;
        isOverride: KnockoutObservable<boolean>;
        
        listSelection: KnockoutObservableArray<string>;
        
        constructor() {
            let self = this;
            
            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.totalSelection = ko.observable(0);
            
            // display number of destinations
            self.totalSelDisp = ko.computed(() => {
                return nts.uk.resource.getText("CDL023_5", [self.totalSelection()]);
            });
            self.isOverride = ko.observable(false);
            
            self.listSelection = ko.observableArray([]);
        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            // get data shared
            let object: any = nts.uk.ui.windows.getShared("ObjectDuplication");
            self.code(object.code);
            self.name(object.name);
            
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }

        /**
         * execution
         */
        public execution() {
            let self = this;
            nts.uk.ui.windows.setShared("ListSelection", self.listSelection());
            nts.uk.ui.windows.close();
        }
    }
}