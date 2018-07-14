module nts.uk.at.view.kmf004.d.viewmodel {
    export class ScreenModel {
        grantDates: KnockoutObservableArray<GrantDateTbl>;
        lstGrantDate: KnockoutObservableArray<GrantDateItem>;
        columns: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        grantDateCode: KnockoutObservable<string>;
        grantDateName: KnockoutObservable<string>;
        provisionCheck: KnockoutObservable<boolean>;
        provisionDeactive: KnockoutObservable<boolean>;
        items: KnockoutObservableArray<Item>;
        editMode: KnockoutObservable<boolean>;
        fixedAssignCheck: KnockoutObservable<boolean>;
        numberOfDays: KnockoutObservable<number>;
        sphdCode: any;
        codeEnable: KnockoutObservable<boolean>;
        numberOfDaysEnable: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            
            self.sphdCode = nts.uk.ui.windows.getShared("KMF004_A_DATA");
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_5"), key: 'grantDateCode', width: 60 },
                { headerText: nts.uk.resource.getText("KMF004_6"), key: 'grantDateName', width: 160, formatter: _.escape}
            ]);
            
            self.grantDates = ko.observableArray([]);
            self.lstGrantDate = ko.observableArray([]);
            self.selectedCode = ko.observable("");

            self.grantDateCode = ko.observable("");
            self.grantDateName = ko.observable("");

            self.provisionCheck = ko.observable(false);
            self.provisionDeactive = ko.observable(true);

            self.items = ko.observableArray([]);
            self.editMode = ko.observable(false); 

            self.fixedAssignCheck = ko.observable(false); 
            self.numberOfDays = ko.observable();
            
            self.codeEnable = ko.observable(true);
            
            self.numberOfDaysEnable = ko.observable(false);

            self.selectedCode.subscribe(function(grantDateCode) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(grantDateCode > 0){
                    var selectedItem = _.find(self.grantDates, function(o) { return o.grantDateCode == grantDateCode; });
                    
                    self.grantDateCode(selectedItem.grantDateCode);
                    self.grantDateName(selectedItem.grantDateName);
                    self.provisionCheck(selectedItem.specified);
                    self.fixedAssignCheck(selectedItem.fixedAssign);
                    self.numberOfDays(selectedItem.numberOfDays);
                    
                    self.codeEnable(false);
                    self.editMode(true);
                    
                    $("#inpPattern").focus();
                    
                    service.findByGrantDateCd(self.sphdCode, selectedItem.grantDateCode).done(function(data) {
                        
                    }).fail(function(res) {
                           
                    });
                }
            });  
            
            self.fixedAssignCheck.subscribe(function(value) {
                if(value){
                    self.numberOfDaysEnable(true);
                } else {
                    self.numberOfDaysEnable(false);
                }
            });  
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            $.when(self.getData()).done(function() {
                                    
                if (self.lstGrantDate().length > 0) {
                    self.selectedCode(self.lstGrantDate()[0].grantDateCode);
                    self.selectedCode.valueHasMutated();
                } else {
                    self.newMode();
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            self.lstGrantDate([]);
            
            service.findBySphdCd(self.sphdCode).done(function(data) {
                self.grantDates = data;
                
                _.forEach(data, function(item) {
                    self.lstGrantDate.push(new GrantDateItem(item.grantDateCode, item.grantDateName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /** update or insert data when click button register **/
        register() {  
            let self = this; 
            nts.uk.ui.block.invisible();
                        
            $("#inpCode").trigger("validate");
            $("#inpPattern").trigger("validate");
                
            if (nts.uk.ui.errors.hasError()) {
                return;       
            }
            
            
        } 
        
        //  new mode 
        newMode() {
            let self = this;
            
            self.codeEnable(true);
            self.editMode(false);
            self.selectedCode("");
            self.grantDateCode("");
            self.grantDateName("");
            self.provisionCheck(false);
            self.fixedAssignCheck(false);
            self.numberOfDays("");
            
            $("#inpCode").focus();
            
            nts.uk.ui.errors.clearAll();  
        }
        
        /** remove item from list **/
        remove() {
            let self = this;
            
            
        } 
        
        
        closeDialog(){
            nts.uk.ui.windows.close();
        }
        
        /**
             * Set error
             */
        addListError(errorsRequest: Array<string>) {
            var errors = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });
            
            nts.uk.ui.dialog.bundledErrors({ errors: errors});
        }
    }
    
    class GrantDateItem {
        grantDateCode: KnockoutObservable<string>;
        grantDateName: KnockoutObservable<string>;
        
        constructor(grantDateCode: string, grantDateName: string) {
            this.grantDateCode = grantDateCode;
            this.grantDateName = grantDateName;       
        }
    }

    export class GrantDateTbl {
        grantDateCode: KnockoutObservable<string>;
        grantDateName: KnockoutObservable<string>;
        isSpecified: KnockoutObservable<number>;
        fixedAssign: KnockoutObservable<number>;
        numberOfDays: KnockoutObservable<number>;

        constructor(grantDateCode: string, grantDateName: string, isSpecified: number, fixedAssign: number, numberOfDays: number) {
            this.grantDateCode = grantDateCode;
            this.grantDateName = grantDateName;
            this.isSpecified = isSpecified;
            this.fixedAssign = fixedAssign;
            this.numberOfDays = numberOfDays;
        }
    }

    export interface IItem{
        grantDateNo: KnockoutObservable<number>;
        months: KnockoutObservable<number>;
        years: KnockoutObservable<number>;
        grantedDays: KnockoutObservable<number>;
    }

    export class Item {
        grantDateNo: KnockoutObservable<number>;
        months: KnockoutObservable<number>;
        years: KnockoutObservable<number>;
        grantedDays: KnockoutObservable<number>;

        constructor(param: IItem) {
            var self = this;
            self.grantDateNo = ko.observable(param.grantDateNo);
            self.months = ko.observable(param.months);
            self.years = ko.observable(param.years);
            self.grantedDays = ko.observable(param.grantedDays);
        }
    }
}