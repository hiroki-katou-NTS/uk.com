module nts.uk.com.view.cdl023.demo.viewmodel {

    export class ScreenModel {

        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        valueReturn: KnockoutObservable<string>;
        
        // list target type
        targetList: KnockoutObservableArray<any>;
        selectedTarget: KnockoutObservable<number>;
        
        // base date
        baseDate: KnockoutObservable<Date>;
        enableBaseDate: KnockoutObservable<boolean>;
        
        constructor() {
            let self = this;
            self.code = ko.observable(null);
            self.name = ko.observable(null);
            self.valueReturn = ko.observable(null);
            
            self.targetList = ko.observableArray([
                {value: 1, name: '雇用'},
                {value: 2, name: '分類'},
                {value: 3, name: '職位'},
                {value: 4, name: '職場'},
                {value: 5, name: '部門'},
                {value: 6, name: '職場個人'},
                {value: 7, name: '部門個人'}
            ]);
            self.selectedTarget = ko.observable(1);
            
            self.baseDate = ko.observable(moment(new Date()).toDate());
            self.enableBaseDate = ko.computed(() => {
                return self.selectedTarget() >= 4 && self.selectedTarget() <= 7;
            });
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
                 nts.uk.ui.dialog.alert("Somethings field are requred.");
                return;
            }
            
            // share data
            let object: IObjectDuplication = {
                code: self.code(),
                name: self.name(),
                targetType: self.selectedTarget(),
                baseDate: moment(self.baseDate()).toDate()
            };
            nts.uk.ui.windows.setShared("ObjectDuplication", object);
            
            // open dialog
            nts.uk.ui.windows.sub.modal('/view/cdl/023/a/index.xhtml').onClosed(() => {
                // show data respond
                let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
                if (!lstSelection) {
                    return;
                }
                self.valueReturn(lstSelection.join(", "));
            });
        }
        
        /**
         * validate
         */
        private validate(): boolean  {
            // clear error
            $('#code').ntsError('clear');
            $('#name').ntsError('clear');
            $('#base-date').ntsError('clear');
            
            // validate
            $('#code').ntsEditor('validate');
            $('#name').ntsEditor('validate');
            $('#base-date').ntsEditor('validate');
            
            return !$('.nts-input').ntsError('hasError');
        }
        
    }
    
    /**
     * IObjectDuplication
     */
    interface IObjectDuplication {
        code: string;
        name: string;
        targetType: number;
        baseDate: Date;
    }
}