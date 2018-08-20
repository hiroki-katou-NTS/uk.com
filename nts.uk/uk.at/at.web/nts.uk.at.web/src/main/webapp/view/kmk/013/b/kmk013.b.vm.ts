module nts.uk.at.view.kmk013.b {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            itemsB23: KnockoutObservableArray<any>;
            itemsB29: KnockoutObservableArray<any>;
            itemsB215: KnockoutObservableArray<any>;
            selectedB23: KnockoutObservable<number>;
            selectedB29: KnockoutObservable<number>;
            selectedB215: KnockoutObservable<number>;
            enableB217: KnockoutObservable<boolean>;
            readonly: KnockoutObservable<boolean>;
            timeOfDay: KnockoutObservable<number>;
            timeB219: KnockoutObservable<number>;
            timeB221: KnockoutObservable<number>;
            timeB223: KnockoutObservable<number>;
            time2: KnockoutObservable<number>;
            inline: KnockoutObservable<boolean>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            checkedB33: KnockoutObservable<boolean>;
            checkedB34: KnockoutObservable<boolean>;
            checkedB35: KnockoutObservable<boolean>;
            enableB29: KnockoutObservable<boolean>;
            enableB215: KnockoutObservable<boolean>;
            //B5 inner
            selectedValueB54: KnockoutObservable<any>;
            selectedValueB59: KnockoutObservable<any>;
            selectedValueB515: KnockoutObservable<any>;
            enableB1: KnockoutObservable<boolean>;
            checkedB57: KnockoutObservable<boolean>;
            checkedB512: KnockoutObservable<boolean>;
            checkedB513: KnockoutObservable<boolean>;
            checkedB514: KnockoutObservable<boolean>;
            checkedB518: KnockoutObservable<boolean>;
            checkedB519: KnockoutObservable<boolean>;
            checkedB520: KnockoutObservable<boolean>;
            checkedB521: KnockoutObservable<boolean>;
            itemListB59: KnockoutObservableArray<any>;
            selectedIdB59: KnockoutObservable<number>;
            enableB54: KnockoutObservable<boolean>;
            enableB59: KnockoutObservable<boolean>;
            enableB515: KnockoutObservable<boolean>;
            //B6
            itemListB64: KnockoutObservableArray<any>;
            selectedValueB64: KnockoutObservable<any>;
            checkedB67: KnockoutObservable<boolean>;
            checkedB68: KnockoutObservable<boolean>;
            checkedB69: KnockoutObservable<boolean>;
            checkedB610: KnockoutObservable<boolean>;
            checkedB611: KnockoutObservable<boolean>;
            selectedValueB612: KnockoutObservable<any>;
            checkedB615: KnockoutObservable<boolean>;
            checkedB616: KnockoutObservable<boolean>;
            checkedB617: KnockoutObservable<boolean>;
            checkedB618: KnockoutObservable<boolean>;
            checkedB619: KnockoutObservable<boolean>;
            checkedB620: KnockoutObservable<boolean>;
            enableB64: KnockoutObservable<boolean>;
            enableB612: KnockoutObservable<boolean>;
            enableB68: KnockoutObservable<boolean>;
            enableB616: KnockoutObservable<boolean>;
            //B7
            itemListB74: KnockoutObservableArray<any>;
            selectedValueB74: KnockoutObservable<any>;
            checkedB77: KnockoutObservable<boolean>;
            itemListB79: KnockoutObservableArray<any>;
            selectedIdB79: KnockoutObservable<any>;
            checkedB712: KnockoutObservable<boolean>;
            checkedB713: KnockoutObservable<boolean>;
            checkedB714: KnockoutObservable<boolean>;
            selectedValueB715: KnockoutObservable<any>;
            checkedB718: KnockoutObservable<boolean>;
            checkedB719: KnockoutObservable<boolean>;
            checkedB720: KnockoutObservable<boolean>;
            checkedB721: KnockoutObservable<boolean>;
            checkedB722: KnockoutObservable<boolean>;
            enableB74: KnockoutObservable<boolean>;
            enableB79: KnockoutObservable<boolean>;
            enableB715: KnockoutObservable<boolean>;
            oldData:KnockoutObservable<any>;
            
            
            itemListB3_8: KnockoutObservableArray<any>;
            selectedIdB3_8: KnockoutObservable<number>;
            enableB3_8: KnockoutObservable<boolean>;
            itemListB2_28: KnockoutObservableArray<any>;
            selectedIdB2_28: KnockoutObservable<number>;
            enableB2_28: KnockoutObservable<boolean>;
            itemListB2_33: KnockoutObservableArray<any>;
            selectedIdB2_33: KnockoutObservable<number>;
            enableB2_33: KnockoutObservable<boolean>;
            
            checkedB5_22: KnockoutObservable<boolean>;
            enableB5_22: KnockoutObservable<boolean>;
            checkedB5_23: KnockoutObservable<boolean>;
            enableB5_23: KnockoutObservable<boolean>;
            
            
            selectedValueB8_5: KnockoutObservable<any>;
            selectedValueB8_6: KnockoutObservable<any>;
            checkedB8_7: KnockoutObservable<boolean>;
            enableB8_7: KnockoutObservable<boolean>;
            checkedB8_12: KnockoutObservable<boolean>;
            enableB8_12: KnockoutObservable<boolean>;
            checkedB8_13: KnockoutObservable<boolean>;
            enableB8_13: KnockoutObservable<boolean>;
            checkedB8_14: KnockoutObservable<boolean>;
            enableB8_14: KnockoutObservable<boolean>;
            checkedB8_22: KnockoutObservable<boolean>;
            enableB8_22: KnockoutObservable<boolean>;
            
            itemListB8_9: KnockoutObservableArray<any>;
            selectedIdB8_9: KnockoutObservable<number>;
            enableB8_9: KnockoutObservable<boolean>;
            
            selectedValueB8_16: KnockoutObservable<boolean>;
            selectedValueB8_17: KnockoutObservable<boolean>;
            
            checkedB8_18: KnockoutObservable<boolean>;
            enableB8_18: KnockoutObservable<boolean>;
            checkedB8_19: KnockoutObservable<boolean>;
            enableB8_19: KnockoutObservable<boolean>;
            checkedB8_20: KnockoutObservable<boolean>;
            enableB8_20: KnockoutObservable<boolean>;
            checkedB8_21: KnockoutObservable<boolean>;
            enableB8_21: KnockoutObservable<boolean>;
            checkedB8_23: KnockoutObservable<boolean>;
            enableB8_23: KnockoutObservable<boolean>;
            enableB6_21: KnockoutObservable<boolean>;
            checkedB6_21: KnockoutObservable<boolean>;
            enableB6_22: KnockoutObservable<boolean>;
            checkedB6_22: KnockoutObservable<boolean>;
            enableB6_23: KnockoutObservable<boolean>;
            checkedB6_23: KnockoutObservable<boolean>;
            
            enableB7_23: KnockoutObservable<boolean>;
            checkedB7_23: KnockoutObservable<boolean>;
            enableB7_24: KnockoutObservable<boolean>;
            checkedB7_24: KnockoutObservable<boolean>;
            
            workClass1: KnockoutObservable<any>;
            workClass2: KnockoutObservable<any>;
            
            conditionDisplay15: KnockoutObservable<boolean>;
            conditionDisplay26: KnockoutObservable<boolean>;
            
            isLoadAfterGetData: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                
                self.isLoadAfterGetData = ko.observable(true);
                
                self.conditionDisplay15 = ko.observable(true);
                self.conditionDisplay26 = ko.observable(true);
                
                self.workClass1 = ko.observable(0);
                self.workClass2 = ko.observable(1);
                
                self.checkedB5_22 = ko.observable(false);
                self.enableB5_22 = ko.observable(false);
                self.checkedB5_23 = ko.observable(false);
                self.enableB5_23 = ko.observable(false);
                
                self.checkedB7_24= ko.observable(false);
                self.enableB7_24= ko.observable(false);
                self.checkedB7_23= ko.observable(false);
                self.enableB7_23= ko.observable(false);
                
                self.checkedB6_23= ko.observable(false);
                self.enableB6_23= ko.observable(false);
                self.checkedB6_22= ko.observable(false);
                self.enableB6_22= ko.observable(false);
                self.checkedB6_21= ko.observable(false);
                self.enableB6_21= ko.observable(false);
                
                self.checkedB8_18= ko.observable(false);
                self.enableB8_18= ko.observable(false);
                self.checkedB8_19= ko.observable(false);
                self.enableB8_19= ko.observable(false);
                self.checkedB8_20= ko.observable(false);
                self.enableB8_20= ko.observable(false);
                self.checkedB8_21= ko.observable(false);
                self.enableB8_21= ko.observable(false);
                self.checkedB8_23= ko.observable(false);
                self.enableB8_23= ko.observable(false);
                
                self.selectedValueB8_16= ko.observable(true);
                self.selectedValueB8_17= ko.observable(!self.selectedValueB8_16());
                

                self.checkedB8_22= ko.observable(false);
                self.enableB8_22= ko.observable(false);
                
                self.checkedB8_14= ko.observable(false);
                self.enableB8_14= ko.observable(false);
                
                self.checkedB8_13= ko.observable(false);
                self.enableB8_13= ko.observable(false);
                
                self.checkedB8_12= ko.observable(false);
                self.enableB8_12= ko.observable(false);
                
                self.itemListB8_9 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_37'))
                ]);
                self.selectedIdB8_9 = ko.observable(0);
                self.enableB8_9 = ko.observable(false);
                
                self.checkedB8_7= ko.observable(false);
                self.enableB8_7= ko.observable(false);
                
                self.selectedValueB8_5 = ko.observable(true);
                self.selectedValueB8_6 = ko.observable(false);
                
                self.checkedB7_23.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB74() == 1 && self.selectedValueB715() == 1) {
                            self.checkedB7_24(true);
                        }    
                    } else {
                        if (self.selectedValueB74() == 1 && self.selectedValueB715() == 1) {
                            self.checkedB7_24(false);
                        }
                    }
                    
                });
                
                self.checkedB8_7.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB8_5() == true) {
                            self.enableB8_9(true);    
                        }
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_18(true);    
                        } 
                    } else {
                        self.enableB8_9(false);
                    }
                });
                
                self.checkedB8_12.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_19(true);    
                        } 
                    }
                });
                
                self.checkedB8_13.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_20(true);    
                        } 
                        if (self.selectedValueB8_5() == true) {
                            self.enableB8_22(true);
                        }
                        self.checkedB8_14(true);
                    } else {
                        self.enableB8_22(false);    
                    }
                });
                
                self.checkedB8_22.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_23(true);    
                        } 
                    } else {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_23(false);    
                        }
                    }
                });
                
                self.checkedB8_14.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_21(true);    
                        } 
                    }
                });
                
                self.checkedB8_18.subscribe((v) => {
                    if (v == false) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_7(false);    
                        } 
                    }
                });
                
                self.checkedB8_19.subscribe((v) => {
                    if (v == false) {
                        if (self.selectedValueB8_5() == true && self.selectedValueB8_16() == true) {
                            self.checkedB8_12(false);    
                        } 
                    }
                });
                
                self.checkedB8_20.subscribe((v) => {
                    if (v == false) {
                        if (self.selectedValueB8_6() == true && self.selectedValueB8_17() == true) {
                            self.checkedB8_13(false);    
                        } 
                    }
                });
                
                self.checkedB8_21.subscribe((v) => {
                    if (v == false) {
                        if (self.selectedValueB8_6() == true && self.selectedValueB8_17() == true) {
                            self.checkedB8_14(false);    
                        }
                        self.checkedB8_20(false);
                        self.checkedB8_14(false);
                    }
                });
                
                self.checkedB8_20.subscribe((v) => {
                    if (v == false) {
                        self.enableB8_23(false);
                        self.checkedB8_13(false);
                    } 
//                    if (v == true) {
//                        if (self.selectedValueB8_16() == true) {
//                            self.enableB8_22(true);
//                        }    
//                    }
                });
                
                self.checkedB5_22.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB54() == 1 && self.selectedValueB515() == 1) {
                            // temparory don't action B5_23
                            self.checkedB5_23(true);
                        }
                    } else {
                        if (self.selectedValueB54() == 1 && self.selectedValueB515() == 1) {
                            // temparory don't action B5_23
                            self.checkedB5_23(false);
                        }
                    }
                });
                
                self.selectedValueB8_16.subscribe((v) => {
                    if (v == true) {
                        self.selectedValueB8_17(false);   
                        self.enableB8_18(true);
                        self.enableB8_19(true);
                        self.enableB8_20(true);
                        self.enableB8_21(true);
                    }
                    
//                    if (v == true && self.checkedB8_20() == true) {
//                        self.enableB8_22(true);
//                    }
                });
                
                self.selectedValueB8_17.subscribe((v) => {
                    if (v == true) {
                        self.selectedValueB8_16(false);
                        self.enableB8_18(false);
                        self.enableB8_19(false);
                        self.enableB8_20(false);
                        self.enableB8_21(false);
                        self.enableB8_23(false);
                        if (self.selectedValueB8_5() == true) {
                            nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                            });
                        }
                        self.selectedValueB8_6(true);
                    }
                });
                
                self.checkedB6_21.subscribe((v) => {
                    if (v == true) {
                        if (self.selectedValueB64() == 1 && self.selectedValueB612() == 1) {
                            self.checkedB6_22(true);
                        }
                    } else {
                        if (self.selectedValueB64() == 1 && self.selectedValueB612() == 1) {
                            self.checkedB6_22(false);
                        }    
                    }
                });
                
                self.checkedB6_22.subscribe((v) => {
                    if (v == false) {
                        if (self.selectedValueB64() == 0 && self.selectedValueB612() == 0) {
                            self.checkedB6_21(false);
                        }
                    }
                });
                
                self.selectedValueB8_5.subscribe((v) => {
                    if (v == true) {
                        self.selectedValueB8_6(false);
                        self.selectedValueB8_16(true);
                        self.enableB8_7(true);
                        self.enableB8_12(true);
                        self.enableB8_13(true);
                        self.enableB8_14(true);
                        
                        if (self.checkedB8_13() == true) {
                            self.enableB8_22(true);
                        }
                        if (self.checkedB8_7() == true) {
                            self.enableB8_9(true);
                        }
                    }
                });
                
                self.selectedValueB8_6.subscribe((v) => {
                    if (v == true) {
                        self.selectedValueB8_5(false);
                        self.enableB8_7(false);
                        self.enableB8_12(false);
                        self.enableB8_13(false);
                        self.enableB8_14(false);
                        self.enableB8_9(false);
                        self.enableB8_22(false);
                    }
                });
                
                self.itemListB3_8 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_255')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_256'))
                ]);
                self.selectedIdB3_8 = ko.observable(0);
                self.enableB3_8 = ko.observable(true);
                
                self.itemListB2_28 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_248')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_249'))
                ]);
                self.selectedIdB2_28 = ko.observable(0);
                self.enableB2_28 = ko.observable(true);
                
                self.itemListB2_33 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_248')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_249'))
                ]);
                self.selectedIdB2_33 = ko.observable(0);
                self.enableB2_33 = ko.observable(true);
                
                
                self.oldData = ko.observable();
                self.itemsB23 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_5')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_6')),
                ]);
                self.itemsB29 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_9')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_10')),
                ]);
                self.itemsB215 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_13')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_14')),
                ]);
                self.selectedB23 = ko.observable(1);
                self.selectedB29 = ko.observable(1);
                self.selectedB215 = ko.observable(1);
                self.enableB29 = ko.observable(false);
                self.enableB215 = ko.observable(false);
                self.enableB217 = ko.observable(false);
                self.readonly = ko.observable(false);

                self.timeOfDay = ko.observable(0);
                self.timeB219 = ko.observable(0);
                self.timeB221 = ko.observable(0);
                self.timeB223 = ko.observable(0);
                self.inline = ko.observable(false);
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK013_25"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK013_422"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK013_26"), content: '.tab-content-3', enable: ko.observable(true), visible: self.conditionDisplay15 },
                    { id: 'tab-4', title: nts.uk.resource.getText("KMK013_27"), content: '.tab-content-4', enable: ko.observable(true), visible: self.conditionDisplay26 },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.checkedB33 = ko.observable(false);
                self.checkedB34 = ko.observable(false);
                self.checkedB35 = ko.observable(false);
                //B5 inner
                self.enableB1 = ko.observable(false);
                self.selectedValueB54 = ko.observable(1);
                self.selectedValueB59 = ko.observable(0);
                self.selectedValueB515 = ko.observable(1);
                self.checkedB57 = ko.observable(false);
                self.checkedB512 = ko.observable(false);
                self.checkedB513 = ko.observable(false);
                self.checkedB514 = ko.observable(false);
                self.checkedB518 = ko.observable(false);
                self.checkedB519 = ko.observable(false);
                self.checkedB520 = ko.observable(false);
                self.checkedB521 = ko.observable(false);
                self.itemListB59 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB59 = ko.observable(0);
                self.enableB54 = ko.observable(false);
                self.enableB59 = ko.observable(false);
                self.enableB515 = ko.observable(false);
                //B6 inner
                self.selectedValueB64 = ko.observable(1);
                self.checkedB67 = ko.observable(false);
                self.checkedB68 = ko.observable(false);
                self.checkedB69 = ko.observable(false);
                self.checkedB610 = ko.observable(false);
                self.checkedB611 = ko.observable(false);
                self.selectedValueB612 = ko.observable(1);
                self.checkedB615 = ko.observable(false);
                self.checkedB616 = ko.observable(false);
                self.checkedB617 = ko.observable(false);
                self.checkedB618 = ko.observable(false);
                self.checkedB619 = ko.observable(false);
                self.checkedB620 = ko.observable(false);

                self.itemListB64 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_51')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_52')),
                ]);
                self.enableB64 = ko.observable(false);
                self.enableB612 = ko.observable(false);
                self.enableB68 = ko.observable(false);
                self.enableB616 = ko.observable(false);
                //B7
                self.selectedValueB74 = ko.observable(1);
                self.itemListB74 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_69')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_70')),
                ]);
                self.itemListB79 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB79 = ko.observable(0);
                self.checkedB77 = ko.observable(false);
                self.checkedB712 = ko.observable(false);
                self.checkedB713 = ko.observable(false);
                self.checkedB714 = ko.observable(false);
                self.selectedValueB715 = ko.observable(1);
                self.checkedB718 = ko.observable(false);
                self.checkedB719 = ko.observable(false);
                self.checkedB720 = ko.observable(false);
                self.checkedB721 = ko.observable(false);
                self.checkedB722 = ko.observable(false);
                self.enableB74 = ko.observable(false);
                self.enableB79 = ko.observable(false);
                self.enableB715 = ko.observable(false);
                self.selectedB23.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.enableB29(false);
                        self.enableB215(false);
                        self.enableB217(false);
                        nts.uk.ui.errors.clearAll();
                    } else {
                        self.enableB29(true);
                        if (self.selectedB29() == 0) {
                            self.enableB215(true);
                            if (self.selectedB215() == 0) {
                                self.enableB217(true);
                            }
                        }
                        if (self.enableB215() == true && self.selectedB215() == 0) {
                            $('.input-time').ntsError('check');    
                        }
                        self.selectedB215.valueHasMutated();
                    }
                });
                self.selectedB29.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.enableB215(false);
                        self.enableB217(false);
                        nts.uk.ui.errors.clearAll();
                    } else {
                        if(self.enableB29()==true){
                            self.enableB215(true);
                        }
                        if (self.enableB215() == true && self.selectedB215() == 0) {
                            $('.input-time').ntsError('check');    
                        }
                        self.selectedB215.valueHasMutated();
                    }
                });
                self.selectedB215.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.enableB217(false);
                        nts.uk.ui.errors.clearAll();                        
                    } else {
                        if(self.enableB215()==true){
                            self.enableB217(true);
                        }
                        if (self.selectedB23() == 0 && self.selectedB29() == 0) {
                            $('.input-time').ntsError('check');                            
                        }
                    }
                });
                //B5
                self.selectedValueB54.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB515(1);
                        self.enableB54(true);
                        if(self.checkedB57()==true){
                            self.enableB59(true);    
                        }
                        if (self.checkedB513() == true) {
                            self.enableB5_22(true);
                        }
                    } else {
                        self.enableB54(false);
                        self.enableB5_22(false);
                    }
                });
                self.enableB54.subscribe((newValue) => {
                    if (newValue == false) {
                        self.enableB59(false);
                    }
                });
                self.selectedValueB515.subscribe((newValue) => {
                    if (newValue == 0) {
                        if (self.selectedValueB54() == 1) {
                            nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                                self.selectedValueB54(0);
                                self.enableB515(false);
                            });    
                        } else {
                            self.selectedValueB54(0);
                            self.enableB515(false);
                        }
                        
                    } else {
                        self.enableB515(true);
                    }
                });
                self.checkedB57.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB518(true);
                        if(self.enableB54()==true){
                           self.enableB59(true);
                        }
                    } else {
                        self.enableB59(false);
                    }
                });
                self.checkedB518.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB57(false);
                    }
                });
                self.checkedB512.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB519(true);
                    }
                });
                self.checkedB519.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB512(false);
                    }
                });
                self.checkedB513.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB514(true);
                        self.checkedB520(true);
                        
                        if (self.selectedValueB54() == 1) {
                            self.enableB5_22(true);
                        }
                    } else {
                        self.enableB5_22(false);    
                    }
                });
                self.checkedB520.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB513(false);
                    } else {
                        self.checkedB521(true);
                    }
                });
                self.checkedB514.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB521(true);
                    } else {
                        self.checkedB513(false);
                    }
                });
                self.checkedB521.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB514(false);
                        self.checkedB520(false);
                    }
                });
                //B6
                self.selectedValueB64.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB612(1);
                        self.enableB64(true);
                        if(self.checkedB67() ==true){
                            self.enableB68(true);
                        }
                        if (self.checkedB610() == true) {
                            self.enableB6_21(true);    
                        }
                    } else {
                        self.enableB64(false);
                        self.enableB6_21(false);
                    }
                });
                self.enableB64.subscribe((newValue) => {
                    if (newValue == false) {
                        self.enableB68(false);
                    }
                });
                self.selectedValueB612.subscribe((newValue) => {
                    if (newValue == 0) {
                        if (self.selectedValueB64() == 1) {
                            nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                                self.selectedValueB64(0);
                                self.enableB612(false);
                                if(self.checkedB616() == true){
                                    self.enableB616(true);    
                                }
                            });    
                        } else {
                            self.selectedValueB64(0);
                                self.enableB612(false);
                                if(self.checkedB616() == true){
                                    self.enableB616(true);    
                                }
                        }
                        
                        self.enableB6_23(false);
                    } else {
                        self.enableB612(true);
                        if (self.checkedB615() == true) {
                            self.enableB6_23(true);
                        }
                    }
                });
                self.checkedB67.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB615(true);
                        if(self.enableB64()==true){
                            self.enableB68(true);
                        }
                    } else {
                        self.enableB68(false);
                    }
                });
                self.checkedB615.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB67(false);
                        self.enableB616(false);
                        self.enableB6_23(false);
                    } else {
                        if(self.enableB612()==true){
                            self.enableB616(true);
                        }
                        if (self.selectedValueB612() == 1) {
                            self.enableB6_23(true);
                        } 
                    }
                });
                self.checkedB68.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB616(true);
                    }
                });
                self.checkedB616.subscribe(newValue => {
                    if (newValue == false) {
                        if (!self.isLoadAfterGetData()) {
                            self.checkedB68(false);                            
                        }
                    }
                });
                self.checkedB69.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB617(true);
                    }
                });
                self.checkedB617.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB69(false);
                    }
                });
                self.checkedB610.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB618(true);
                        self.checkedB611(true);
                        if (self.selectedValueB64() == 1) {
                            self.enableB6_21(true);
                        }
                    } else {
                        self.enableB6_21(false);    
                    }
                });
                self.checkedB618.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB610(false);
                    } else {
                        self.checkedB619(true);
                    }
                });
                self.checkedB611.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB619(true);
                    } else {
                        self.checkedB610(false);
                    }
                });
                self.checkedB619.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB611(false);
                        self.checkedB618(false);
                    }
                });
                //B7
                self.selectedValueB74.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB715(1);
                        self.enableB74(true);
                        if(self.checkedB77()==true){
                            self.enableB79(true);    
                        }
                        if (self.checkedB713() == true) {
                            self.enableB7_23(true);
                        }
                    } else {
                        self.enableB74(false);
                        self.enableB7_23(false);
                    }
                });
                self.enableB74.subscribe((newValue) => {
                    if (newValue == false) {
                        self.enableB79(false);
                    }
                });
                self.selectedValueB715.subscribe((newValue) => {
                    if (newValue == 0) {
                        if (self.selectedValueB74() == 1) {
                            nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                                self.selectedValueB74(0);
                                self.enableB715(false);
                            });        
                        } else {
                            self.selectedValueB74(0);
                            self.enableB715(false);
                        }
                    } else {
                        self.enableB715(true);
                    }
                });
                self.checkedB77.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB718(true);
                        if(self.enableB74() == true){
                            self.enableB79(true);
                        }
                    } else {
                        self.enableB79(false);
                    }
                });
                self.checkedB718.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB77(false);
                    }
                });
                self.checkedB712.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB719(true);
                    }
                });
                self.checkedB719.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB712(false);
                    }
                });
                self.checkedB713.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB714(true);
                        self.checkedB720(true);
                        if (self.selectedValueB74() == 1) {
                            self.enableB7_23(true);
                        }
                    } else {
                        self.enableB7_23(false);    
                    }
                });
                self.checkedB720.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB713(false);
                    } else {
                        self.checkedB721(true);
                    }
                });
                self.checkedB714.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB721(true);
                    } else {
                        self.checkedB713(false);
                    }
                });
                self.checkedB721.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB714(false);
                        self.checkedB720(false);
                    }
                });
                
                self.changeTabPanel();
            }
            
            changeTabPanel(): void {
                let self = this;
                $( document ).keydown(function( event ) {
                    // catch event press tab button
                    if (event.which == 9) {
                       switch(_.toNumber($( "*:focus" ).attr("tabindex"))) { 
                           case lastTabIndexTabPanel1: { 
                                self.selectedTab("tab-2");
                                break; 
                           } 
                           case lastTabIndexTabPanel2 : { 
                                self.selectedTab("tab-3"); 
                                break; 
                           }
                           case lastTabIndexTabPanel3 : { 
                                self.selectedTab("tab-4"); 
                              break; 
                           }
                           case lastTabIndexBeforeTabPanel1 : { 
                                self.selectedTab("tab-1"); 
                                break; 
                           }
                           default: { 
                           } 
                        }
                    }
                });
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initData().done(() => {
                    $( "#b23" ).focus();
                    dfd.resolve(); 
                });
                return dfd.promise();
            }
            
            initData(): JQueryPromise<any> {
                var dfd = $.Deferred();
                let self = this;
                self.isLoadAfterGetData(true);
                $.when(service.findByCompanyId(), service.getDomainSet()).done(function(data, dataDomainSet){
                    if (dataDomainSet != null) {
                        // condition 15
                        self.conditionDisplay15(dataDomainSet.flexWorkManagement == 1? true : false);
                        
                        // condition 26
                        self.conditionDisplay26(dataDomainSet.useAggDeformedSetting == 1? true : false);
                    }
                    
                    if (data[0] == null) {
                        self.oldData({  "companyId":null, "referComHolidayTime":0, "oneDay":0, "morning":0, "afternoon":0, "referActualWorkHours":0, "notReferringAch":0,
                                        "annualHoliday":0, "specialHoliday":0, "yearlyReserved":0,
                                        "regularWork":{
                                                "companyId":null, "calcActualOperationPre":0, "exemptTaxTimePre":0, "incChildNursingCarePre":0, "additionTimePre":0, 
                                                "notDeductLateleavePre":0, "deformatExcValuePre":0, "exemptTaxTimeWork":0, "calcActualOperationWork":0, "incChildNursingCareWork":0, 
                                                "notDeductLateleaveWork":0, "additionTimeWork":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                },
                                        "flexWork":{
                                                "companyId":null, "calcActualOperationPre":0, "exemptTaxTimePre":0, "incChildNursingCarePre":0, "predeterminedOvertimePre":0,
                                                "additionTimePre":0, "notDeductLateleavePre":0, "exemptTaxTimeWork":0, "minusAbsenceTimeWork":0, "calcActualOperationWork":0, 
                                                "incChildNursingCareWork":0,"notDeductLateleaveWork":0, "predeterminDeficiencyWork":0,"additionTimeWork":0, "enableSetPerWorkHour1":0, 
                                                "enableSetPerWorkHour2":0, "additionWithinMonthlyStatutory":0
                                                },
                                        "irregularWork":{
                                                "companyId":null, "calcActualOperationPre":0, "exemptTaxTimePre":0, "incChildNursingCarePre":0, "predeterminedOvertimePre":0,
                                                "additionTimePre":0, "notDeductLateleavePre":0, "deformatExcValue":0, "exemptTaxTimeWork":0, "minusAbsenceTimeWork":0,
                                                "calcActualOperationWork":0, "incChildNursingCareWork":0, "notDeductLateleaveWork":0, "predeterminDeficiencyWork":0,
                                                "additionTimeWork":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                },
                                        "hourlyPaymentAdditionSet":{
                                                "companyId":null, "calcPremiumVacation":0, "addition1":0, "deformatExcValue":0, "incChildNursingCare":0, "deduct":0,
                                                "calculateIncludeIntervalExemptionTime1":0, "calcWorkHourVacation":0, "addition2":0, "calculateIncludCareTime":0,
                                                "notDeductLateLeaveEarly":0, "calculateIncludeIntervalExemptionTime2":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                },
                                        "addSetManageWorkHour":0, "addingMethod1":0, "workClass1":0, "addingMethod2":0, "workClass2":1
                                        })
                                        self.notifyVarKnockoutchange();
                        return;
                    }
                    
                    if (_.isNull(data[0].hourlyPaymentAdditionSet)) {
                        data[0].hourlyPaymentAdditionSet = <any>{};
                        data[0].hourlyPaymentAdditionSet = {
                                                "companyId":null, "calcPremiumVacation":0, "addition1":0, "deformatExcValue":0, "incChildNursingCare":0, "deduct":0,
                                                "calculateIncludeIntervalExemptionTime1":0, "calcWorkHourVacation":0, "addition2":0, "calculateIncludCareTime":0,
                                                "notDeductLateLeaveEarly":0, "calculateIncludeIntervalExemptionTime2":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                };
                    }
                    
                    if (_.isNull(data[0].irregularWork)) {
                        data[0].irregularWork = <any>{};
                        data[0].irregularWork = {
                                                "companyId":null, "calcActualOperationPre":0, "exemptTaxTimePre":0, "incChildNursingCarePre":0, "predeterminedOvertimePre":0,
                                                "additionTimePre":0, "notDeductLateleavePre":0, "exemptTaxTimeWork":0, "minusAbsenceTimeWork":0, "calcActualOperationWork":0, 
                                                "incChildNursingCareWork":0,"notDeductLateleaveWork":0, "predeterminDeficiencyWork":0,"additionTimeWork":0, "enableSetPerWorkHour1":0, 
                                                "enableSetPerWorkHour2":0, "additionWithinMonthlyStatutory":0
                                                };
                    }
                    
                    if (_.isNull(data[0].flexWork)) {
                        data[0].flexWork = <any>{};
                        data[0].flexWork = {
                                                "companyId":null, "calcPremiumVacation":0, "addition1":0, "deformatExcValue":0, "incChildNursingCare":0, "deduct":0,
                                                "calculateIncludeIntervalExemptionTime1":0, "calcWorkHourVacation":0, "addition2":0, "calculateIncludCareTime":0,
                                                "notDeductLateLeaveEarly":0, "calculateIncludeIntervalExemptionTime2":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                };
                    }
                    
                    if (_.isNull(data[0].hourlyPaymentAdditionSet)) {
                        data[0].hourlyPaymentAdditionSet = <any>{};
                        data[0].hourlyPaymentAdditionSet = {
                                                "companyId":null, "calcPremiumVacation":0, "addition1":0, "deformatExcValue":0, "incChildNursingCare":0, "deduct":0,
                                                "calculateIncludeIntervalExemptionTime1":0, "calcWorkHourVacation":0, "addition2":0, "calculateIncludCareTime":0,
                                                "notDeductLateLeaveEarly":0, "calculateIncludeIntervalExemptionTime2":0, "enableSetPerWorkHour1":0, "enableSetPerWorkHour2":0
                                                };
                    }
//                    self.notifyVarKnockoutchange();
                    
                    self.oldData(data[0]);
                    let obj = data[0];
                    //実績の就業時間帯を参照する
                    self.selectedB23(obj.referActualWorkHours);
                    //実績を参照しない場合の参照先
                    self.selectedB29(obj.notReferringAch);
                    //会社単位の休暇時間を参照する
                    self.selectedB215(obj.referComHolidayTime);
                    if (obj.referComHolidayTime == 1) {
                        self.enableB217(false);
                    }
                    //加算時間.1日
                    self.timeB219(obj.oneDay);
                    //加算時間.午前
                    self.timeB221(obj.morning);
                    //加算時間.午後
                    self.timeB223(obj.afternoon);

                    //加算休暇設定.年休
                    self.checkedB33(convertToBoolean(obj.annualHoliday));
                    //加算休暇設定.積立年休
                    self.checkedB34(convertToBoolean(obj.yearlyReserved));
                    //加算休暇設定.特別休暇
                    self.checkedB35(convertToBoolean(obj.specialHoliday));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB54(obj.regularWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB57(convertToBoolean(obj.regularWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.通常、変形の所定超過時
                    self.selectedIdB59(obj.regularWork.deformatExcValuePre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB512(convertToBoolean(obj.regularWork.incChildNursingCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB513(convertToBoolean(obj.regularWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB514(convertToBoolean(obj.regularWork.exemptTaxTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB515(obj.regularWork.calcActualOperationWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB518(convertToBoolean(obj.regularWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB519(convertToBoolean(obj.regularWork.incChildNursingCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB520(convertToBoolean(obj.regularWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB521(convertToBoolean(obj.regularWork.exemptTaxTimeWork));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB64(obj.flexWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB67(convertToBoolean(obj.flexWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.フレックスの所定超過時
                    self.checkedB68(convertToBoolean(obj.flexWork.predeterminedOvertimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB69(convertToBoolean(obj.flexWork.incChildNursingCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB610(convertToBoolean(obj.flexWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB611(convertToBoolean(obj.flexWork.exemptTaxTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB612(obj.flexWork.calcActualOperationWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB615(convertToBoolean(obj.flexWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.フレックスの所定不足時
                    self.checkedB616(convertToBoolean(obj.flexWork.predeterminDeficiencyWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB617(convertToBoolean(obj.flexWork.incChildNursingCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB618(convertToBoolean(obj.flexWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB619(convertToBoolean(obj.flexWork.exemptTaxTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.欠勤時間をマイナスする
                    self.checkedB620(convertToBoolean(obj.flexWork.minusAbsenceTimeWork));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB74(obj.irregularWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB77(convertToBoolean(obj.irregularWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.通常、変形の所定超過時
                    self.selectedIdB79(obj.irregularWork.deformatExcValue);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB712(convertToBoolean(obj.irregularWork.incChildNursingCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB713(convertToBoolean(obj.irregularWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB714(convertToBoolean(obj.irregularWork.exemptTaxTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB715(obj.irregularWork.calcActualOperationWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB718(convertToBoolean(obj.irregularWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB719(convertToBoolean(obj.irregularWork.incChildNursingCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB720(convertToBoolean(obj.irregularWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB721(convertToBoolean(obj.irregularWork.exemptTaxTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.欠勤時間をマイナスする
                    self.checkedB722(convertToBoolean(obj.irregularWork.minusAbsenceTimeWork));
                    
                    
                    /**  休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する */
                    self.selectedValueB8_5(convertToBoolean(obj.hourlyPaymentAdditionSet.calcPremiumVacation));
                    self.selectedValueB8_6(!self.selectedValueB8_5());
                    self.selectedValueB8_5.valueHasMutated();
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する */
                    self.checkedB8_7(convertToBoolean(obj.hourlyPaymentAdditionSet.addition1));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.通常、変形の所定超過時 */
                    self.selectedIdB8_9(obj.hourlyPaymentAdditionSet.deformatExcValue);
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する */
                    self.checkedB8_12(convertToBoolean(obj.hourlyPaymentAdditionSet.incChildNursingCare));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.控除する */
                    self.checkedB8_13(convertToBoolean(obj.hourlyPaymentAdditionSet.deduct)),
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB8_22(convertToBoolean(obj.hourlyPaymentAdditionSet.enableSetPerWorkHour1));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する */
                    self.checkedB8_14(convertToBoolean(obj.hourlyPaymentAdditionSet.calculateIncludeIntervalExemptionTime1));
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する */
                    self.selectedValueB8_16(convertToBoolean(obj.hourlyPaymentAdditionSet.calcWorkHourVacation));
                    self.selectedValueB8_17(!self.selectedValueB8_16());
                    self.selectedValueB8_16.valueHasMutated();
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する */
                    self.checkedB8_18(convertToBoolean(obj.hourlyPaymentAdditionSet.addition2));
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する */
                    self.checkedB8_19(convertToBoolean(obj.hourlyPaymentAdditionSet.calculateIncludCareTime));
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除する */
                    self.checkedB8_20(convertToBoolean(obj.hourlyPaymentAdditionSet.notDeductLateLeaveEarly));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB8_23(convertToBoolean(obj.hourlyPaymentAdditionSet.enableSetPerWorkHour2));
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する */
                    self.checkedB8_21(convertToBoolean(obj.hourlyPaymentAdditionSet.calculateIncludeIntervalExemptionTime2));
                    
                    /** 時間外超過の加算設定 */
                    self.selectedIdB3_8(obj.addSetManageWorkHour);
                    
                    /** 時間休暇加算.加算方法 */
                    self.selectedIdB2_28(obj.addingMethod1);
                    /** 時間休暇加算.加算方法 */
                    self.selectedIdB2_33(obj.addingMethod2);
                    self.workClass1(obj.workClass1);
                    self.workClass2(obj.workClass2);
                    
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB5_22(convertToBoolean(obj.regularWork.enableSetPerWorkHour1));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB5_23(convertToBoolean(obj.regularWork.enableSetPerWorkHour2));
                    
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */     
                    self.checkedB6_21(convertToBoolean(obj.flexWork.enableSetPerWorkHour1));
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */                               
                    self.checkedB6_22(convertToBoolean(obj.flexWork.enableSetPerWorkHour2));
                    /** 休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.月次法定内のみ加算*/
                    self.checkedB6_23(convertToBoolean(obj.flexWork.additionWithinMonthlyStatutory));
                    
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB7_23(convertToBoolean(obj.irregularWork.enableSetPerWorkHour1));
                    
                    /** 休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない.就業時間帯毎の設定を可能とする */
                    self.checkedB7_24(convertToBoolean(obj.irregularWork.enableSetPerWorkHour2));
                    
                    self.notifyVarKnockoutchange();
                    self.isLoadAfterGetData(false);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            notifyVarKnockoutchange(): void {
                let self = this;
                self.selectedValueB54.valueHasMutated();
                self.selectedValueB515.valueHasMutated();
                self.selectedValueB8_5.valueHasMutated();
                self.selectedValueB8_16.valueHasMutated();
                self.selectedValueB64.valueHasMutated();
                self.selectedValueB612.valueHasMutated();
                self.selectedValueB74.valueHasMutated();
                self.selectedValueB715.valueHasMutated();    
                self.checkedB615.valueHasMutated();
                self.timeB219.valueHasMutated();
                self.timeB221.valueHasMutated();
                self.timeB223.valueHasMutated();
            }
            
            save(): void {
                let self = this;
                let obj :any = {};
                
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
                blockUI.grayout();

                obj.referActualWorkHours = self.selectedB23();
                obj.notReferringAch = self.oldData().notReferringAch;
                obj.referComHolidayTime = self.oldData().referComHolidayTime;
                obj.oneDay = self.oldData().oneDay;
                obj.morning = self.oldData().morning;
                obj.afternoon = self.oldData().afternoon;
                if (self.selectedB23() == 0) {
                    obj.notReferringAch = self.selectedB29();
                    if (self.selectedB29() == 0) {
                        obj.referComHolidayTime = self.selectedB215();
                        if (self.selectedB215() == 0 && self.enableB215()) {
                            obj.oneDay = self.timeB219();
                            obj.morning = self.timeB221();
                            obj.afternoon = self.timeB223();
                        }
                    }
                }
                obj.annualHoliday = convertToInt(self.checkedB33());
                obj.specialHoliday = convertToInt(self.checkedB35());
                obj.yearlyReserved = convertToInt(self.checkedB34());
                //regularWork
                obj.regularWork = {};
                obj.regularWork.companyId = "";
                obj.regularWork.calcActualOperationPre = self.selectedValueB54();
                if (self.selectedValueB54() == 1) {
                    obj.regularWork.additionTimePre = convertToInt(self.checkedB57());
                    obj.regularWork.incChildNursingCarePre = convertToInt(self.checkedB512());
                    obj.regularWork.notDeductLateleavePre = convertToInt(self.checkedB513());
                    obj.regularWork.exemptTaxTimePre = convertToInt(self.checkedB514());
                    if (self.checkedB513() == true) {
                        obj.regularWork.enableSetPerWorkHour1 = convertToInt(self.checkedB5_22());    
                    } else {
                        obj.regularWork.enableSetPerWorkHour1 = self.oldData().regularWork.enableSetPerWorkHour1;    
                    }
                } else {
                    obj.regularWork.exemptTaxTimePre = self.oldData().regularWork.exemptTaxTimePre;
                    obj.regularWork.incChildNursingCarePre =self.oldData().regularWork.incChildNursingCarePre;
                    obj.regularWork.additionTimePre = self.oldData().regularWork.additionTimePre;
                    obj.regularWork.notDeductLateleavePre = self.oldData().regularWork.notDeductLateleavePre;
                    obj.regularWork.deformatExcValuePre = self.oldData().regularWork.deformatExcValuePre;
                    obj.regularWork.enableSetPerWorkHour1 = self.oldData().regularWork.enableSetPerWorkHour1;
                }
                if (self.enableB59() == true) {
                    obj.regularWork.deformatExcValuePre = self.selectedIdB59();
                } else {
                    obj.regularWork.deformatExcValuePre = self.oldData().regularWork.deformatExcValuePre;
                }
                obj.regularWork.calcActualOperationWork = self.selectedValueB515();
                if (self.selectedValueB515() == 1) {
                    obj.regularWork.additionTimeWork = convertToInt(self.checkedB518());
                    obj.regularWork.incChildNursingCareWork = convertToInt(self.checkedB519());
                    obj.regularWork.notDeductLateleaveWork = convertToInt(self.checkedB520());
                    obj.regularWork.exemptTaxTimeWork = convertToInt(self.checkedB521());
                    if (self.checkedB520() == true && self.enableB5_23()) {
                        obj.regularWork.enableSetPerWorkHour2 = convertToInt(self.checkedB5_23());    
                    } else {
                        obj.regularWork.enableSetPerWorkHour2 = self.oldData().regularWork.enableSetPerWorkHour2;
                    }
                } else {
                    obj.regularWork.additionTimeWork = self.oldData().regularWork.additionTimeWork;
                    obj.regularWork.incChildNursingCareWork = self.oldData().regularWork.incChildNursingCareWork;
                    obj.regularWork.notDeductLateleaveWork = self.oldData().regularWork.notDeductLateleaveWork;
                    obj.regularWork.exemptTaxTimeWork = self.oldData().regularWork.exemptTaxTimeWork;
                    obj.regularWork.enableSetPerWorkHour2 = self.oldData().regularWork.enableSetPerWorkHour2;
                }

                //flexWork
                obj.flexWork = {};
                obj.flexWork.companyId = "";
                obj.flexWork.calcActualOperationPre = self.selectedValueB64();
                if (self.selectedValueB64() == 1) {
                    obj.flexWork.additionTimePre = convertToInt(self.checkedB67());
                    obj.flexWork.incChildNursingCarePre = convertToInt(self.checkedB69());
                    obj.flexWork.notDeductLateleavePre = convertToInt(self.checkedB610());
                    obj.flexWork.exemptTaxTimePre = convertToInt(self.checkedB611());
                    obj.flexWork.predeterminedOvertimePre = convertToInt(self.checkedB68());
                    if (self.checkedB610() == true) {
                        obj.flexWork.enableSetPerWorkHour1 = convertToInt(self.checkedB6_21());    
                    } else {
                        obj.flexWork.enableSetPerWorkHour1 = self.oldData().flexWork.enableSetPerWorkHour1;
                    }
                } else {
                    obj.flexWork.additionTimePre = self.oldData().flexWork.additionTimePre;
                    obj.flexWork.incChildNursingCarePre = self.oldData().flexWork.incChildNursingCarePre;
                    obj.flexWork.notDeductLateleavePre = self.oldData().flexWork.notDeductLateleavePre;
                    obj.flexWork.exemptTaxTimePre = self.oldData().flexWork.exemptTaxTimePre;
                    obj.flexWork.enableSetPerWorkHour1 = self.oldData().flexWork.enableSetPerWorkHour1;
                }
                if (self.enableB68()) {
                    obj.flexWork.predeterminedOvertimePre = convertToInt(self.checkedB68());
                } else {
                    obj.flexWork.predeterminedOvertimePre = self.oldData().flexWork.predeterminedOvertimePre;
                }
                obj.flexWork.calcActualOperationWork = self.selectedValueB612();
                if (self.selectedValueB612() == 1) {
                    obj.flexWork.additionTimeWork = convertToInt(self.checkedB615());
                    obj.flexWork.incChildNursingCareWork = convertToInt(self.checkedB617());
                    obj.flexWork.notDeductLateleaveWork = convertToInt(self.checkedB618());
                    obj.flexWork.exemptTaxTimeWork = convertToInt(self.checkedB619());
                    obj.flexWork.minusAbsenceTimeWork = convertToInt(self.checkedB620());
                    if (self.checkedB615() == true) {
                        if (self.enableB616()) {
                            obj.flexWork.predeterminDeficiencyWork = convertToInt(self.checkedB616());    
                        } else {
                            obj.flexWork.predeterminDeficiencyWork = self.oldData().flexWork.predeterminDeficiencyWork;        
                        }
                        if (self.enableB6_23()) {
                            obj.flexWork.additionWithinMonthlyStatutory = convertToInt(self.checkedB6_23());    
                        } else {
                            obj.flexWork.additionWithinMonthlyStatutory = self.oldData().flexWork.additionWithinMonthlyStatutory;
                        }
                    } else {
                        obj.flexWork.predeterminDeficiencyWork = self.oldData().flexWork.predeterminDeficiencyWork;
                        obj.flexWork.additionWithinMonthlyStatutory = self.oldData().flexWork.additionWithinMonthlyStatutory;
                    }
                    if (self.checkedB618() == true) {
                        obj.flexWork.enableSetPerWorkHour2 = convertToInt(self.checkedB6_22());    
                    }
                } else {
                    obj.flexWork.additionTimeWork = self.oldData().flexWork.additionTimeWork;
                    obj.flexWork.incChildNursingCareWork = self.oldData().flexWork.incChildNursingCareWork;
                    obj.flexWork.notDeductLateleaveWork = self.oldData().flexWork.notDeductLateleaveWork;
                    obj.flexWork.exemptTaxTimeWork = self.oldData().flexWork.exemptTaxTimeWork;
                    obj.flexWork.minusAbsenceTimeWork = self.oldData().flexWork.minusAbsenceTimeWork;
                    obj.flexWork.enableSetPerWorkHour2 = self.oldData().enableSetPerWorkHour2;  
                }
                //irregularWork
                obj.irregularWork = {};
                obj.irregularWork.companyId = "";
                obj.irregularWork.calcActualOperationPre = self.selectedValueB74();
                if (self.selectedValueB74() == 1) {
                    obj.irregularWork.additionTimePre = convertToInt(self.checkedB77());
                    obj.irregularWork.incChildNursingCarePre = convertToInt(self.checkedB712());
                    obj.irregularWork.notDeductLateleavePre = convertToInt(self.checkedB713());
                    obj.irregularWork.exemptTaxTimePre = convertToInt(self.checkedB714());
                    if (self.checkedB713() && self.enableB7_23()) {
                        obj.irregularWork.enableSetPerWorkHour1 = convertToInt(self.checkedB7_23());
                    } else {
                        obj.irregularWork.enableSetPerWorkHour1 = self.oldData().irregularWork.enableSetPerWorkHour1;    
                    }
                } else {
                    obj.irregularWork.additionTimePre = self.oldData().irregularWork.additionTimePre;
                    obj.irregularWork.incChildNursingCarePre = self.oldData().irregularWork.incChildNursingCarePre;
                    obj.irregularWork.notDeductLateleavePre = self.oldData().irregularWork.notDeductLateleavePre;
                    obj.irregularWork.exemptTaxTimePre = self.oldData().irregularWork.exemptTaxTimePre;
                    obj.irregularWork.enableSetPerWorkHour1 = self.oldData().irregularWorkenableSetPerWorkHour1;
                }
                if (self.enableB79() == true && self.checkedB77()) {
                    obj.irregularWork.deformatExcValue = convertToInt(self.selectedIdB79());
                } else {
                    obj.irregularWork.deformatExcValue = self.oldData().irregularWork.deformatExcValue;
                }
                obj.irregularWork.calcActualOperationWork = self.selectedValueB715();
                if (self.selectedValueB715() == 1) {
                    obj.irregularWork.additionTimeWork = convertToInt(self.checkedB718());
                    obj.irregularWork.incChildNursingCareWork = convertToInt(self.checkedB719());
                    obj.irregularWork.notDeductLateleaveWork = convertToInt(self.checkedB720());
                    obj.irregularWork.exemptTaxTimeWork = convertToInt(self.checkedB721());
                    obj.irregularWork.minusAbsenceTimeWork = convertToInt(self.checkedB722());
                    if (self.checkedB720()) {
                        obj.irregularWork.enableSetPerWorkHour2 = convertToInt(self.checkedB7_24());
                    }
                } else {
                    obj.irregularWork.additionTimeWork = self.oldData().irregularWork.additionTimeWork;
                    obj.irregularWork.incChildNursingCareWork = self.oldData().irregularWork.incChildNursingCareWork;
                    obj.irregularWork.notDeductLateleaveWork = self.oldData().irregularWork.notDeductLateleaveWork;
                    obj.irregularWork.exemptTaxTimeWork = self.oldData().irregularWork.exemptTaxTimeWork;
                    obj.irregularWork.minusAbsenceTimeWork = self.oldData().irregularWork.minusAbsenceTimeWork;
                    obj.irregularWork.enableSetPerWorkHour2 = self.oldData().enableSetPerWorkHour2;
                }
                
                obj.addingMethod1 = self.selectedIdB2_28();
                obj.addingMethod2 = self.selectedIdB2_33();
                obj.workClass1 = self.workClass1();
                obj.workClass2 = self.workClass2();
                obj.addSetManageWorkHour = self.selectedIdB3_8();
                
                // hourlyPaymentAddSet
                obj.hourlyPaymentAddCommand = {};
                obj.hourlyPaymentAddCommand.companyId = "";
                
                if (self.selectedValueB8_6() == true) {
                    obj.hourlyPaymentAddCommand.calcPremiumVacation = convertToInt(self.selectedValueB8_5());
                    obj.hourlyPaymentAddCommand.addition1 = self.oldData().hourlyPaymentAdditionSet.addition1;
                    obj.hourlyPaymentAddCommand.deformatExcValue = self.oldData().hourlyPaymentAdditionSet.deformatExcValue;
                    obj.hourlyPaymentAddCommand.incChildNursingCare = self.oldData().hourlyPaymentAdditionSet.incChildNursingCare;
                    obj.hourlyPaymentAddCommand.deduct = self.oldData().hourlyPaymentAdditionSet.deduct;
                    obj.hourlyPaymentAddCommand.calculateIncludeIntervalExemptionTime1 = self.oldData().hourlyPaymentAdditionSet.calculateIncludeIntervalExemptionTime1;
                    obj.hourlyPaymentAddCommand.enableSetPerWorkHour1 = self.oldData().hourlyPaymentAdditionSet.enableSetPerWorkHour1;
                } else {
                    obj.hourlyPaymentAddCommand.calcPremiumVacation = convertToInt(self.selectedValueB8_5());
                    obj.hourlyPaymentAddCommand.addition1 = convertToInt(self.checkedB8_7());
                    obj.hourlyPaymentAddCommand.incChildNursingCare = convertToInt(self.checkedB8_12());
                    obj.hourlyPaymentAddCommand.deduct = convertToInt(self.checkedB8_13());
                    obj.hourlyPaymentAddCommand.calculateIncludeIntervalExemptionTime1 = convertToInt(self.checkedB8_14());
                    if (self.checkedB8_13() == true) {
                       obj.hourlyPaymentAddCommand.enableSetPerWorkHour1 = convertToInt(self.checkedB8_22());
                    } else {
                       obj.hourlyPaymentAddCommand.enableSetPerWorkHour1 = self.oldData().hourlyPaymentAdditionSet.enableSetPerWorkHour1; 
                    }
                    if (self.checkedB8_7() == true && self.enableB8_9()) {
                        obj.hourlyPaymentAddCommand.deformatExcValue = self.selectedIdB8_9();
                    } else {
                        obj.hourlyPaymentAddCommand.deformatExcValue = self.oldData().hourlyPaymentAdditionSet.deformatExcValue;
                    }
                }
                
                if (self.selectedValueB8_17() == true) {
                    obj.hourlyPaymentAddCommand.calcWorkHourVacation = convertToInt(self.selectedValueB8_16());
                    obj.hourlyPaymentAddCommand.addition2 = self.oldData().hourlyPaymentAdditionSet.addition2;
                    obj.hourlyPaymentAddCommand.calculateIncludCareTime = self.oldData().hourlyPaymentAdditionSet.calculateIncludCareTime;
                    obj.hourlyPaymentAddCommand.notDeductLateLeaveEarly = self.oldData().hourlyPaymentAdditionSet.notDeductLateLeaveEarly;
                    obj.hourlyPaymentAddCommand.calculateIncludeIntervalExemptionTime2 = self.oldData().hourlyPaymentAdditionSet.calculateIncludeIntervalExemptionTime2;
                    obj.hourlyPaymentAddCommand.enableSetPerWorkHour2 = self.oldData().hourlyPaymentAdditionSet.enableSetPerWorkHour2;
                } else {
                    obj.hourlyPaymentAddCommand.calcWorkHourVacation = convertToInt(self.selectedValueB8_16());
                    obj.hourlyPaymentAddCommand.addition2 = convertToInt(self.checkedB8_18());
                    obj.hourlyPaymentAddCommand.calculateIncludCareTime = convertToInt(self.checkedB8_19());
                    obj.hourlyPaymentAddCommand.notDeductLateLeaveEarly = convertToInt(self.checkedB8_20());
                    obj.hourlyPaymentAddCommand.calculateIncludeIntervalExemptionTime2 = convertToInt(self.checkedB8_21());
                    if (self.checkedB8_20() == true) {
                        obj.hourlyPaymentAddCommand.enableSetPerWorkHour2 = convertToInt(self.checkedB8_23());
                    } else {
                        obj.hourlyPaymentAddCommand.enableSetPerWorkHour2 = self.oldData().hourlyPaymentAdditionSet.enableSetPerWorkHour2;    
                    }
                }
                
                service.save(obj).done(() => {
                    self.initData();
                    nts.uk.ui.dialog.info({messageId: 'Msg_15'});
                    $( "#b23" ).focus();
                }).fail((error) => {
                   nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    blockUI.clear();
                });
            }
        }
        
        const lastTabIndexTabPanel1 = 28;
        const lastTabIndexTabPanel2 = 41;
        const lastTabIndexTabPanel3 = 57;
        const lastTabIndexBeforeTabPanel1  = 14;
        
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        function convertToBoolean(x: number) {
            if (x == 1)
                return true;
            else
                return false;
        }
        function convertToInt(x: boolean) {
            if (x == true) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}