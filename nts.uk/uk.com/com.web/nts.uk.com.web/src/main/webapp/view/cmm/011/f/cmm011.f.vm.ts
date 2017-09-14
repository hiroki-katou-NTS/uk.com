module nts.uk.com.view.cmm011.f {
    export module viewmodel {
        
        import CreationType = base.CreationWorkplaceType;
        
        export class ScreenModel {
            
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            itemList: KnockoutObservableArray<BoxModel>;
            selectedValBox: KnockoutObservable<number>;
            
            enableRadioGroup: KnockoutObservable<boolean>;
            isLess999Heirarchies: KnockoutObservable<boolean>;
            isLessTenthHeirarchy: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                
                self.workplaceCode = ko.observable(null);
                self.workplaceName = ko.observable(null);
                
                self.enableRadioGroup = ko.observable(true);
                self.isLess999Heirarchies = ko.observable(true);
                self.isLessTenthHeirarchy = ko.observable(true);
                
                self.itemList = ko.observableArray([
                    new BoxModel(CreationType.CREATE_ON_TOP, nts.uk.resource.getText("CMM011_33", ['Com_Workplace']), self.isLess999Heirarchies),
                    new BoxModel(CreationType.CREATE_BELOW, nts.uk.resource.getText("CMM011_34", ['Com_Workplace']), self.isLess999Heirarchies),
                    new BoxModel(CreationType.CREATE_TO_CHILD, nts.uk.resource.getText("CMM011_35", ['Com_Workplace']), self.isLessTenthHeirarchy)
                ]);
                self.selectedValBox = ko.observable(1);
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                // get condition to initial screen.
                let objTransfer: any = nts.uk.ui.windows.getShared("ObjectTransfer");
                self.workplaceCode(objTransfer.code);
                self.workplaceCode(objTransfer.name);
                self.isLess999Heirarchies(objTransfer.isLess999Heirarchies);
                self.isLessTenthHeirarchy(objTransfer.isLessTenthHeirarchy);
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                let self = this;
                nts.uk.ui.windows.setShared("CreatedWorkplaceCondition", self.selectedValBox());
                nts.uk.ui.windows.close();
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        /**
         * BoxModel
         */
        class BoxModel {
            value: number;
            name: string;
            enable: KnockoutObservable<boolean>;
            
            constructor(value: number, name: string, enable: KnockoutObservable<boolean>){
                let self = this;
                self.value = value;
                self.name = name;
                self.enable = enable;
            }
        }
    }
}