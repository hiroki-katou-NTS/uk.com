module nts.uk.com.view.cmm011.d {
    export module viewmodel {
        
        export class ScreenModel {
            
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.startDate = ko.observable('');
                self.endDate = ko.observable("9999/12/31");//TODO: nts.uk.resource.getText("CMM011_27")
                
                self.startDate.subscribe((newValue) => {
//                    nts.uk.ui.windows.getSelf().setSize(290, 500);
                });
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                let self = this;
                if (!self.validate()) {
                    return;
                }
                nts.uk.ui.block.grayout();
                service.saveWorkplaceHistory(self.toJSObject()).done(function(){
                    nts.uk.ui.block.clear();
                    nts.uk.ui.windows.setShared("ModeAddHistory", true);
                    self.close();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }
            
            /**
             * toJSObject
             */
            private toJSObject(): any {
                let self = this;
                return {
                    isAddMode: true,
                    workplaceId: nts.uk.ui.windows.getShared("selectedWkpId"),
                    workplaceHistory: {
                        historyId: '',
                        period: {
                            startDate: self.startDate(),
                            endDate: new Date(self.endDate())
                        }
                    }
                }
            }
            
            
            /**
             * validate
             */
            private validate() {
                let self = this;

                // clear error
                $('#start-date').ntsError('clear');

                // validate
                $('#start-date').ntsEditor('validate');

                return !$('.nts-input').ntsError('hasError');
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
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }
}