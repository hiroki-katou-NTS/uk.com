module nts.uk.at.view.kmf002.f {
    
    import viewModelTabA = nts.uk.at.view.kmf002.a.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            
            selectUnitCheck: KnockoutObservable<boolean>= ko.observable(false);
            typeSelectUnitRadioBox: KnockoutObservableArray<any>;
            valueDefaultTypeSelect: KnockoutObservable<number> = ko.observable(0);;
            selectEmployee: KnockoutObservable<boolean> = ko.observable(true);
            
            isManageWkpPublicHd: KnockoutObservable<number> = ko.observable(BoolValue.FALSE);
            isManageEmpPublicHd: KnockoutObservable<number> = ko.observable(BoolValue.FALSE);
            isManageEmployeePublicHd: KnockoutObservable<number> = ko.observable(BoolValue.FALSE);
            enableTypeSelectUnitRadioBox: KnockoutObservable<boolean>= ko.observable(false);
            
            constructor() {
                let self = this;               
                self.typeSelectUnitRadioBox = ko.observableArray([
                                                                    {"id":0,"name": nts.uk.resource.getText("Com_Employment"),"enable":true},
                                                                    {"id":1,"name": nts.uk.resource.getText("Com_Workplace"),"enable":true}
                                                                ]); 
                self.selectUnitCheck.subscribe(function(newValue) {
                        if (newValue == false) {
                            self.isManageWkpPublicHd(BoolValue.FALSE);
                            self.isManageEmpPublicHd(BoolValue.FALSE);
                            self.enableTypeSelectUnitRadioBox(false);
                        } else if (self.isManageWkpPublicHd() == BoolValue.FALSE && self.isManageEmpPublicHd() == BoolValue.FALSE) {
                            self.isManageEmpPublicHd(BoolValue.TRUE);
                            self.valueDefaultTypeSelect(0);
                            self.enableTypeSelectUnitRadioBox(true);
                        } else {
                            self.enableTypeSelectUnitRadioBox(true);
                        }   
                });
                
                self.selectEmployee.subscribe(function(newValue) {
                    if (newValue == true) {
                        self.isManageEmployeePublicHd(BoolValue.TRUE);
                    } else {
                        self.isManageEmployeePublicHd(BoolValue.FALSE);
                    }
                });
                
                self.valueDefaultTypeSelect.subscribe(function(newValue) {
                    if (self.selectUnitCheck() == true) {
                        if (newValue == BoolValue.TRUE) {
                            self.isManageWkpPublicHd(BoolValue.TRUE);
                            self.isManageEmpPublicHd(BoolValue.FALSE);
                        } else {
                            self.isManageWkpPublicHd(BoolValue.FALSE);
                            self.isManageEmpPublicHd(BoolValue.TRUE);
                        }
                    }
                });   
            }
            
            private notifyVariableChange(): void {
                let self = this;
                self.isManageEmployeePublicHd.valueHasMutated();
                self.isManageWkpPublicHd.valueHasMutated();
                self.isManageEmpPublicHd.valueHasMutated();    
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                 service.findAll().then(data => {
                  self.isManageEmpPublicHd(data.isManageEmpPublicHd);
                  self.isManageEmployeePublicHd(data.isManageEmployeePublicHd);
                  self.isManageWkpPublicHd(data.isManageWkpPublicHd);
                  self.notifyVariableChange();

                  if (self.isManageEmpPublicHd() == BoolValue.FALSE && self.isManageWkpPublicHd()  == BoolValue.FALSE){
                      self.selectUnitCheck(false);
                  } else {
                      self.selectUnitCheck(true);
                  }
                  if (self.isManageEmpPublicHd() == BoolValue.TRUE) {
                      self.valueDefaultTypeSelect(0);
                  } else {
                      self.valueDefaultTypeSelect(1);
                  }
                  
                  if (self.isManageEmployeePublicHd() == BoolValue.TRUE) {
                      self.selectEmployee(true);
                  } else {
                      self.selectEmployee(false);    
                  }
                    dfd.resolve();  
                });
                  return dfd.promise();
            }
            
            private closeSaveDialog(): void {
                let self = this;
                service.saveManageUnit(self.isManageEmployeePublicHd(), self.isManageWkpPublicHd(), self.isManageEmpPublicHd())
                .always(() => 
                   { nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () {
                                                    nts.uk.ui.windows.close();
                                                })}
                    ); 
                
            }
            
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
        
        export enum BoolValue {
            TRUE = 1,
            FALSE = 0    
        }
    }
}