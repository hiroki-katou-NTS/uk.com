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
            
            constructor() {
                let _self = this;
                _self.selectUnitCheck = ko.observable(false);
                _self.typeSelectUnitRadioBox = ko.observableArray([
                                                                    {"id":0,"name": nts.uk.resource.getText("Com_Employment"),"enable":true},
                                                                    {"id":1,"name": nts.uk.resource.getText("Com_Workplace"),"enable":true}
                                                                ]);
                _self.valueDefaultTypeSelect = ko.observable(1);
                _self.selectEmployee = ko.observable(true);
                _self.isManageWkpPublicHd = ko.observable(1);
                _self.isManageEmpPublicHd = ko.observable(1);
                _self.isManageEmployeePublicHd = ko.observable(1);
                
                _self.selectUnitCheck.subscribe(function(newValue) {
                    if (newValue == false) {
                        _self.isManageWkpPublicHd(0);
                        _self.isManageEmpPublicHd(0);
                        _self.valueDefaultTypeSelect(-1);
                    }
                    setShared('isManageWkpPublicHd', _self.isManageWkpPublicHd());
                    setShared('isManageEmpPublicHd', _self.isManageEmpPublicHd());
                });
                
                _self.selectEmployee.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.isManageEmployeePublicHd(1);
                    } else {
                        _self.isManageEmployeePublicHd(0);    
                    }
                    setShared('isManageEmployeePublicHd', _self.isManageEmployeePublicHd());
                });
                
                _self.valueDefaultTypeSelect.subscribe(function(newValue) {
                    if (_self.selectUnitCheck() == true) {
                        if (newValue == 1) {
                            _self.isManageWkpPublicHd(1);
                            _self.isManageEmpPublicHd(0);
                        } else {
                            _self.isManageWkpPublicHd(0);
                            _self.isManageEmpPublicHd(1);    
                        }
                        setShared('isManageWkpPublicHd', _self.isManageWkpPublicHd());
                        setShared('isManageEmpPublicHd', _self.isManageEmpPublicHd());    
                    }
                });
                
            }
            
            public start_page(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                _self.isManageWkpPublicHd(getShared('isManageWkpPublicHd'));
                _self.isManageEmpPublicHd(getShared('isManageEmpPublicHd'));
                _self.isManageEmployeePublicHd(getShared('isManageEmployeePublicHd'));
                if (_self.isManageEmpPublicHd() == 0 && _self.isManageWkpPublicHd()  == 0){
                    _self.selectUnitCheck(false);
                } else {
                    _self.selectUnitCheck(true);
                }
                
                if (_self.isManageEmpPublicHd() == 1) {
                    _self.valueDefaultTypeSelect(0);
                } else if (_self.isManageWkpPublicHd() == 1) {
                    _self.valueDefaultTypeSelect(1);
                }
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public closeSaveDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
        
    }
}