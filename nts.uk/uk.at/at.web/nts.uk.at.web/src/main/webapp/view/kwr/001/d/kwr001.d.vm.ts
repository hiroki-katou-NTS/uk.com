module nts.uk.at.view.kwr001.d {
    
    import service = nts.uk.at.view.kwr001.d.service;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            D1_6_value: KnockoutObservable<string>;
            D1_7_value: KnockoutObservable<string>;
        
            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.itemList = ko.observableArray([]);
        
                self.selectedCode = ko.observable('');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.D1_6_value = ko.observable('');
                self.D1_7_value = ko.observable('');
            }
            
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                
                service.getDataStartPage().done(function(data: any) {
                    let arr: ItemModel[] = [];
                    _.forEach(data, function(value, index) {
                        arr.push(new ItemModel(value.code, value.name));
                    })
                    self.itemList(arr);
                    dfd.resolve();
                }).fail((err) => {
                    nts.uk.ui.dialog.alertError({ messageId: err.messageId, messageParams: err.parameterIds });
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            closeDialog(): void {
                nts.uk.ui.windows.setShared('KWR001_D', null);
                nts.uk.ui.windows.close();
            }
        
            /*
             * execute copy
            */
            executeCopy(): void {
                let self = this,
                    dataReturnScrC: any = {};
                $('.save-error').ntsError('check');
                if (nts.uk.ui.errors.hasError()) {
                    return;    
                }
                blockUI.grayout();
                service.executeCopy(self.D1_6_value(), self.selectedCode(), nts.uk.ui.windows.getShared('KWR001_D')).done(function(data: any) {
                    dataReturnScrC.lstAtdChoose = data;
                    dataReturnScrC.codeCopy = self.D1_6_value();
                    dataReturnScrC.nameCopy = self.D1_7_value();
                    nts.uk.ui.windows.setShared('KWR001_D', dataReturnScrC);
                    nts.uk.ui.windows.close();
                }).fail(function(err) {
                    if (err.messageId == "Msg_3") {
                        $(".D1_6").ntsError('set', { messageId: "Msg_3"});
                    } else {
                        dataReturnScrC.error = err;
                        nts.uk.ui.windows.setShared('KWR001_D', dataReturnScrC);
                        nts.uk.ui.windows.close();
                    }
                }).always(function() {
                    blockUI.clear();  
                })
                
            }
        };
        
        class ItemModel {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}