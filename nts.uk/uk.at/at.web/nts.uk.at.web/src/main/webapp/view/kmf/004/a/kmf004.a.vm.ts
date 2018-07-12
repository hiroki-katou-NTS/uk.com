module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        sphdList: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        specialHolidayCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isDisable: KnockoutObservable<boolean>;
        editMode: KnockoutObservable<boolean>;
        specialHolidayName: KnockoutObservable<string>;
        workTypeNames: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;

        constructor() {
            let self = this;
            
            self.sphdList = ko.observableArray([]);

            self.specialHolidayCode = ko.observable("");
            self.isEnable = ko.observable(true);
            self.isDisable = ko.observable(true);
            self.editMode = ko.observable(false);
            self.specialHolidayName = ko.observable("");
            self.workTypeNames = ko.observable("");
            self.memo = ko.observable("");
                
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMF004_5'), key: 'specialHolidayCode', width: 100 },
                { headerText: nts.uk.resource.getText('KMF004_6'), key: 'specialHolidayName', width: 150 }
            ]);
            
            self.currentCode = ko.observable();
            
            self.currentCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value > 0){
                    service.getSpecialHoliday(value).done(function(data) {
                        self.isEnable(true);
                        self.isDisable(false);
                        self.specialHolidayCode(data.specialHolidayCode);
                        self.specialHolidayName(data.specialHolidayName);
                        self.memo(data.memo);
                        self.editMode(true);
                        $("#input-name").focus();
                        nts.uk.ui.errors.clearAll();
                    }).fail(function(res) {
                          
                    });
                }
            });  
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            $.when(self.getSphdData()).done(function() {
                                    
                if (self.sphdList().length > 0) {
                    self.currentCode(self.sphdList()[0].specialHolidayCode);
                    self.currentCode.valueHasMutated();
                } else {
                    self.clearForm();
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });

            return dfd.promise();
        }
        
        getSphdData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            self.sphdList([]);
            service.findByCid().done(function(data) {
                _.forEach(data, function(item) {
                    self.sphdList.push(new ItemModel(item.specialHolidayCode, item.specialHolidayName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        openKDL002Dialog() {
            let self = this;
            
        }
        
        saveSpecialHoliday(): JQueryPromise<any> {
            let self = this;
            nts.uk.ui.errors.clearAll();
            
            $("#input-code").trigger("validate");
            $("#input-name").trigger("validate");
            
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            nts.uk.ui.block.invisible();
            
            var dataItem : service.SpecialHolidayItem = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                specialHolidayName: self.specialHolidayName(),
                memo: self.memo()
            };
            
            if(!self.editMode) {
                service.add(dataItem).done(function(errors) {
                    if (errors && errors.length > 0) {
                        self.addListError(errors);    
                    } else {  
                        self.getSphdData();         
                        self.currentCode(self.specialHolidayCode());
                        self.currentCode.valueHasMutated();
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                            $("#input-name").focus();
                            self.isDisable(false);
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            } else {
                service.update(dataItem).done(function(errors) {
                    if (errors && errors.length > 0) {
                        self.addListError(errors);    
                    } else {  
                        self.getSphdData();         
                        self.currentCode(self.specialHolidayCode());
                        self.currentCode.valueHasMutated();
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                            $("#input-name").focus();
                            self.isDisable(false);
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        
        deleteSpecialHoliday() {
            let self = this;
            
        }

        initSpecialHoliday(): void {
            let self = this;
            self.clearForm();
        }
        
        clearForm() {
            let self = this;
            
            self.editMode(false);
            self.currentCode("");
            self.specialHolidayCode("");
            self.isEnable(false);
            self.isDisable(true);
            self.specialHolidayName("");
            self.workTypeNames("");
            self.memo("");
        }

        /**
         * Set error
         */
        addListError(errorsRequest: Array<string>) {
            let self = this;
            
            let errors = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({ message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });

            nts.uk.ui.dialog.bundledErrors({ errors: errors });
        }
    }
    
    class ItemModel {
        specialHolidayCode: number;
        specialHolidayName: string;
        constructor(specialHolidayCode: number, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;       
        }
    } 
}

