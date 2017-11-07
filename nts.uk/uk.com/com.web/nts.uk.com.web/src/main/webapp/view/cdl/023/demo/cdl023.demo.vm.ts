module nts.uk.com.view.cdl023.demo.viewmodel {

    export class ScreenModel {

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        valueReturn: KnockoutObservable<string>;
        
        constructor() {
            let self = this;
            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.valueReturn = ko.observable(null);
        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        /**
         * closeDialog
         */
        public openDialog() {
            let self = this;
            
            // validate
            if (!self.validate()) {
                 nts.uk.ui.dialog.alert("Code and name are requred.");
                return;
            }
            
            // share data
            let object: IObjectDuplication = {
                code: self.code(),
                name: self.name()
            };
            nts.uk.ui.windows.setShared("ObjectDuplication", object);
            
            // open dialog
            nts.uk.ui.windows.sub.modal('/view/cdl/023/a/index.xhtml').onClosed(() => {
                // show data respond
                let lstSelection: Array<string> = nts.uk.ui.windows.getShared("ListSelection");
                self.valueReturn(lstSelection.join("、"));
            });
        }
        
        /**
         * validate
         */
        private validate(): boolean  {
            // clear error
            $('#code').ntsError('clear');
            $('#name').ntsError('clear');
            
            // validate
            $('#code').ntsEditor('validate');
            $('#name').ntsEditor('validate');
            
            return !$('.nts-input').ntsError('hasError');
        }
        
    }
    
    /**
     * IObjectDuplication
     */
    interface IObjectDuplication {
        code: string;
        name: string;
    }
}