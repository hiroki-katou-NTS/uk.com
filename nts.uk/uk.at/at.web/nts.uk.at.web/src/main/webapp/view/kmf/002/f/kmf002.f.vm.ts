module nts.uk.at.view.kmf002.f {
    
    import viewModelTabA = nts.uk.at.view.kmf002.a.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            
            selectUnitCheck: KnockoutObservable<boolean>;
            typeSelectUnitRadioBox: KnockoutObservableArray<any>;
            valueDefaultTypeSelect: KnockoutObservable<number>;
            selectEmployee: KnockoutObservable<boolean>;
            
            isManageWkpPublicHd: KnockoutObservable<number>;
            isManageEmpPublicHd: KnockoutObservable<number>;
            isManageEmployeePublicHd: KnockoutObservable<number>;
            enableTypeSelectUnitRadioBox: KnockoutObservable<boolean>;
            
            constructor() {
                let _self = this;
                _self.selectUnitCheck = ko.observable(false);
                _self.typeSelectUnitRadioBox = ko.observableArray([
                                                                    {"id":0,"name": nts.uk.resource.getText("Com_Employment"),"enable":true},
                                                                    {"id":1,"name": nts.uk.resource.getText("Com_Workplace"),"enable":true}
                                                                ]);
                _self.valueDefaultTypeSelect = ko.observable(0);
                _self.selectEmployee = ko.observable(true);
                _self.isManageWkpPublicHd = ko.observable(BoolValue.FALSE);
                _self.isManageEmpPublicHd = ko.observable(BoolValue.FALSE);
                _self.isManageEmployeePublicHd = ko.observable(BoolValue.FALSE);
                _self.enableTypeSelectUnitRadioBox = ko.observable(false);
                
                _self.selectUnitCheck.subscribe(function(newValue) {
                    if (newValue == false) {
                        _self.isManageWkpPublicHd(BoolValue.FALSE);
                        _self.isManageEmpPublicHd(BoolValue.FALSE);
                        _self.enableTypeSelectUnitRadioBox(false);
                    } else {
                        _self.isManageWkpPublicHd(BoolValue.TRUE);
                        _self.valueDefaultTypeSelect(0);
                        _self.enableTypeSelectUnitRadioBox(true);
                    }
                });
                
                _self.selectEmployee.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.isManageEmployeePublicHd(BoolValue.TRUE);
                    } else {
                        _self.isManageEmployeePublicHd(BoolValue.FALSE);
                    }
                });
                
                _self.valueDefaultTypeSelect.subscribe(function(newValue) {
                    if (_self.selectUnitCheck() == true) {
                        if (newValue == BoolValue.TRUE) {
                            _self.isManageWkpPublicHd(BoolValue.TRUE);
                            _self.isManageEmpPublicHd(BoolValue.FALSE);
                        } else {
                            _self.isManageWkpPublicHd(BoolValue.FALSE);
                            _self.isManageEmpPublicHd(BoolValue.TRUE);
                        }
                    }
                });
                
            }
            
            private notifyVariableChange(): void {
                let _self = this;
                _self.isManageEmployeePublicHd.valueHasMutated();
                _self.isManageWkpPublicHd.valueHasMutated();
                _self.isManageEmpPublicHd.valueHasMutated();    
            }
            
            public start_page(): JQueryPromise<any> {
                let _self = this;
                $( "#selectUnitCheck" ).focus();
                var dfd = $.Deferred<any>();
                _self.isManageWkpPublicHd(nts.uk.ui.windows.getShared('isManageWkpPublicHd'));
                _self.isManageEmpPublicHd(nts.uk.ui.windows.getShared('isManageEmpPublicHd'));
                _self.isManageEmployeePublicHd(nts.uk.ui.windows.getShared('isManageEmployeePublicHd'));
                _self.notifyVariableChange();
                if (_self.isManageEmpPublicHd() == BoolValue.FALSE && _self.isManageWkpPublicHd()  == BoolValue.FALSE){
                    _self.selectUnitCheck(false);
                } else {
                    _self.selectUnitCheck(true);
                    if (_self.isManageEmpPublicHd() == BoolValue.TRUE) {
                        _self.valueDefaultTypeSelect(0);
                    } else {
                        _self.valueDefaultTypeSelect(1);
                    }
                }
                
                if (_self.isManageEmployeePublicHd() == BoolValue.TRUE) {
                    _self.selectEmployee(true);
                } else {
                    _self.selectEmployee(false);    
                }
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private closeSaveDialog(): void {
                let _self = this;
                nts.uk.ui.windows.setShared('saveManageUnit', true);
                nts.uk.ui.windows.setShared('isManageWkpPublicHd', _self.isManageWkpPublicHd());
                nts.uk.ui.windows.setShared('isManageEmpPublicHd', _self.isManageEmpPublicHd());
                nts.uk.ui.windows.setShared('isManageEmployeePublicHd', _self.isManageEmployeePublicHd());  
                nts.uk.ui.windows.close();  
            }
            
            private closeDialog(): void {
                nts.uk.ui.windows.setShared('saveManageUnit', false);
                nts.uk.ui.windows.close();
            }
        }
        
        export enum BoolValue {
            TRUE = 1,
            FALSE = 0    
        }
    }
}