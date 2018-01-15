module nts.uk.com.view.cmm011.f {
    export module viewmodel {
        
        import CreationType = base.CreationWorkplaceType;
        
        export class ScreenModel {
            
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            
            itemList: KnockoutObservableArray<BoxModel>;
            selectedValBox: KnockoutObservable<number>;
            
            enableRadioGroup: KnockoutObservable<boolean>;
            isLess999Hierarchies: KnockoutObservable<boolean>;
            isLessTenthHierarchy: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                
                self.workplaceCode = ko.observable(null);
                self.workplaceName = ko.observable(null);
                
                self.enableRadioGroup = ko.observable(true);
                self.isLess999Hierarchies = ko.observable(true);
                self.isLessTenthHierarchy = ko.observable(true);
                
                self.itemList = ko.observableArray([
                    new BoxModel(CreationType.CREATE_ON_TOP, nts.uk.resource.getText("CMM011_33", ['Com_Workplace']),
                        self.isLess999Hierarchies),
                    new BoxModel(CreationType.CREATE_BELOW, nts.uk.resource.getText("CMM011_34", ['Com_Workplace']),
                        self.isLess999Hierarchies),
                    new BoxModel(CreationType.CREATE_TO_CHILD, nts.uk.resource.getText("CMM011_35", ['Com_Workplace']),
                        self.isLessTenthHierarchy)
                ]);
                self.selectedValBox = ko.observable(CreationType.CREATE_ON_TOP);
                
                // subscribe
                self.isLess999Hierarchies.subscribe((newValue) => {
                    if (!newValue) {
                        self.selectedValBox(CreationType.CREATE_TO_CHILD);
                    }
                });
                self.isLessTenthHierarchy.subscribe((newValue) => {
                    if (!newValue && self.isLess999Hierarchies()) {
                        self.selectedValBox(CreationType.CREATE_ON_TOP);
                    }
                });
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
                self.workplaceName(objTransfer.name);
                self.isLess999Hierarchies(objTransfer.isLess999Hierarchies);
                self.isLessTenthHierarchy(objTransfer.isLessTenthHierarchy);
                
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
                let dfd = $.Deferred<any>();
                
                // check error business exception
                if (!res.businessException) {
                    return;
                }
                
                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
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