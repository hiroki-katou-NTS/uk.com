module nts.uk.at.view.kmf003.a.viewmodel {
    export class ScreenModel {
        //Grid data
        columns: KnockoutObservable<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KMF003_8"), prop: 'code', width: 50 },
            { headerText: nts.uk.resource.getText("KMF003_9"), prop: 'name', width: 200, formatter: _.escape }
        ]);
        singleSelectedCode: KnockoutObservable<any> = ko.observable("");
        //Grid data
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentCode: KnockoutObservable<any> = ko.observable();

        //Top input form
        code: KnockoutObservable<string> = ko.observable("");
        editMode: KnockoutObservable<boolean> = ko.observable(true);
        name: KnockoutObservable<string> = ko.observable("");
        useConditionCls: KnockoutObservable<boolean> = ko.observable(false);
        grantDate: KnockoutObservable<string> = ko.observable("");
        enableGrantDate: KnockoutObservable<boolean> = ko.observable(true);
        A6_2Data: KnockoutObservableArray<any> = ko.observableArray([
            { code: '0', name: nts.uk.resource.getText("KMF003_17") },
            { code: '1', name: nts.uk.resource.getText("KMF003_18") }
        ]);
        A6_2SelectedRuleCode: any = ko.observable(0);
        A7_4Data: KnockoutObservableArray<any> = ko.observableArray([
            { code: '0', name: nts.uk.resource.getText("KMF003_21") },
            { code: '1', name: nts.uk.resource.getText("KMF003_22") }
        ]);
        A7_4SelectedRuleCode: any = ko.observable(0);
        symbols: KnockoutObservable<string> = ko.observable("%");
        limitedValue01: KnockoutObservable<string> = ko.observable("100");
        limitedValue02: KnockoutObservable<string> = ko.observable("");
        limitedValue03: KnockoutObservable<string> = ko.observable("");
        limitedValue04: KnockoutObservable<string> = ko.observable("");
        limitedValue05: KnockoutObservable<string> = ko.observable("");
        
        //Bottom input form
        useCls01: KnockoutObservable<boolean> = ko.observable(false);
        useCls02: KnockoutObservable<boolean> = ko.observable(false);
        useCls03: KnockoutObservable<boolean> = ko.observable(false);
        useCls04: KnockoutObservable<boolean> = ko.observable(false);
        useCls05: KnockoutObservable<boolean> = ko.observable(false);
        
        useCls02Enable: KnockoutObservable<boolean> = ko.observable(true);
        useCls03Enable: KnockoutObservable<boolean> = ko.observable(true);
        useCls04Enable: KnockoutObservable<boolean> = ko.observable(true);
        useCls05Enable: KnockoutObservable<boolean> = ko.observable(true);
        
        
        conditionValue01Day: KnockoutObservable<string> = ko.observable("");
        conditionValue01Percent: KnockoutObservable<string> = ko.observable("");
        conditionValue02Day: KnockoutObservable<string> = ko.observable("");
        conditionValue02Percent: KnockoutObservable<string> = ko.observable("");
        conditionValue03Day: KnockoutObservable<string> = ko.observable("");
        conditionValue03Percent: KnockoutObservable<string> = ko.observable("");
        conditionValue04Day: KnockoutObservable<string> = ko.observable("");
        conditionValue04Percent: KnockoutObservable<string> = ko.observable("");
        conditionValue05Day: KnockoutObservable<string> = ko.observable("");
        conditionValue05Percent: KnockoutObservable<string> = ko.observable("");
        
        note: KnockoutObservable<string> = ko.observable("");
        conditionValue01Enable: KnockoutObservable<boolean> = ko.observable(false);
        conditionValue02Enable: KnockoutObservable<boolean> = ko.observable(false);
        conditionValue03Enable: KnockoutObservable<boolean> = ko.observable(false);
        conditionValue04Enable: KnockoutObservable<boolean> = ko.observable(false);
        conditionValue05Enable: KnockoutObservable<boolean> = ko.observable(false);
        btnSetting02Enable: KnockoutObservable<boolean> = ko.observable(false);
        btnSetting03Enable: KnockoutObservable<boolean> = ko.observable(false);
        btnSetting04Enable: KnockoutObservable<boolean> = ko.observable(false);
        btnSetting05Enable: KnockoutObservable<boolean> = ko.observable(false);
        
        isNewMode: KnockoutObservable<boolean>= ko.observable(false);
        grantHTData: any;
        
        showLblSet01: KnockoutObservable<boolean> = ko.observable(false);
        showLblSet02: KnockoutObservable<boolean> = ko.observable(false);
        showLblSet03: KnockoutObservable<boolean> = ko.observable(false);
        showLblSet04: KnockoutObservable<boolean> = ko.observable(false);
        showLblSet05: KnockoutObservable<boolean> = ko.observable(false);
        
        
        constructor() {
            var self = this;
            //Enable or disable for setting form
            self.conditionSettingForm();
            
            //Bind data to from when user select item on grid
            self.singleSelectedCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                self.isNewMode(true);
     
                $('.a9_1').show();
                $('.a9_2').show();
                $('.a9_3').show();
                $('.a9_4').show();
                $('.a9_5').show();
                $('.a9_6').hide();
                
                if(value.length > 0){
                    service.findByCode(value).done(function(data) {
                        self.editMode(false);
                        self.useCls01(true);
                        
                        self.conditionValue01Enable(true);
                        
                        self.useCls02Enable(true);
                        self.useCls03Enable(true);
                        self.useCls04Enable(true);
                        self.useCls05Enable(true);
                        
                        if(data.grantConditions.length > 0) {
                            for(var i = 0; i < data.grantConditions.length; i++) {
                                self.getGHTdata(data.grantConditions[i].conditionNo, data.grantConditions[i].yearHolidayCode);                                
                            }
                        }
                        
                        self.code(data.yearHolidayCode);
                        self.name(data.yearHolidayName);
                        self.A6_2SelectedRuleCode(data.standardCalculation);  
                        self.A7_4SelectedRuleCode(data.calculationMethod);
                        self.useConditionCls(data.useSimultaneousGrant === 1 ? true : false);
                        self.grantDate(data.simultaneousGrandMonthDays.toString());
                        self.note(data.yearHolidayNote);                    
                        self.conditionValue01(data.grantConditions[0] && (data.grantConditions[0].conditionValue != null ? data.grantConditions[0].conditionValue.toString() : ""));
                        self.useCls02(data.grantConditions[1] && data.grantConditions[1].useConditionAtr == 1 ? true : false);
                        self.conditionValue02(data.grantConditions[1] && (data.grantConditions[1].conditionValue != null ? data.grantConditions[1].conditionValue.toString() : ""));
                        self.useCls03(data.grantConditions[2] && data.grantConditions[2].useConditionAtr == 1 ? true : false);
                        self.conditionValue03(data.grantConditions[2] && (data.grantConditions[2].conditionValue != null ? data.grantConditions[2].conditionValue.toString() : ""));
                        self.useCls04(data.grantConditions[3] && data.grantConditions[3].useConditionAtr == 1 ? true : false);
                        self.conditionValue04(data.grantConditions[3] && (data.grantConditions[3].conditionValue != null ? data.grantConditions[3].conditionValue.toString() : ""));
                        self.useCls05(data.grantConditions[4] && data.grantConditions[4].useConditionAtr == 1 ? true : false);
                        self.conditionValue05(data.grantConditions[4] && (data.grantConditions[4].conditionValue != null ? data.grantConditions[4].conditionValue.toString() : ""));
                        self.btnSetting02Enable(data.grantConditions[1] && data.grantConditions[1].useConditionAtr == 1);
                        self.btnSetting03Enable(data.grantConditions[2] && data.grantConditions[2].useConditionAtr == 1);
                        self.btnSetting04Enable(data.grantConditions[3] && data.grantConditions[3].useConditionAtr == 1);
                        self.btnSetting05Enable(data.grantConditions[4] && data.grantConditions[4].useConditionAtr == 1);  
                        self.showLblSet01(data.grantConditions[0] ? data.grantConditions[0].hadSet : false);
                        self.showLblSet02(data.grantConditions[1] ? data.grantConditions[1].hadSet : false);
                        self.showLblSet03(data.grantConditions[2] ? data.grantConditions[2].hadSet : false);
                        self.showLblSet04(data.grantConditions[3] ? data.grantConditions[3].hadSet : false);
                        self.showLblSet05(data.grantConditions[4] ? data.grantConditions[4].hadSet : false); 
                                    
                        self.setFocus();  
                        
                        self.setConditionValueChanges();
                        
                        // clear all error
                        nts.uk.ui.errors.clearAll();
                    }).fail(function(res) {
                          
                    });
                }
            });    
            
            if(self.useConditionCls() == true){
                self.enableGrantDate(true);
            } else {
                self.enableGrantDate(false);
            }
            
            self.useConditionCls.subscribe(function(value) {
                if(!value){
                    self.enableGrantDate(false);
                } else {
                    self.enableGrantDate(true);
                }
            });  
        }
        
        
        
            conditionValue01(data ?){
                let self = this,
                    isDayMode = self.A7_4SelectedRuleCode() == 1;
                if (data) {
                    if (isDayMode) {
                        self.conditionValue01Day(data);
                    } else {
                        self.conditionValue01Percent(data);
                    }
                } else {
                    return isDayMode ? self.conditionValue01Day() : self.conditionValue01Percent();
                }
            }
            
            conditionValue02(data ?){
                let self = this,
                    isDayMode = self.A7_4SelectedRuleCode() == 1;
                if (data) {
                    if (isDayMode) {
                        self.conditionValue02Day(data);
                    } else {
                        self.conditionValue02Percent(data);
                    }
                } else {
                    return isDayMode ? self.conditionValue02Day() : self.conditionValue02Percent();
                }
            }
            
            conditionValue03(data ?){
                let self = this,
                    isDayMode = self.A7_4SelectedRuleCode() == 1;
                if (data) {
                    if (isDayMode) {
                        self.conditionValue03Day(data);
                    } else {
                        self.conditionValue03Percent(data);
                    }
                } else {
                    return isDayMode ? self.conditionValue03Day() : self.conditionValue03Percent();
                }
            }
            
            conditionValue04(data ?){
                let self = this,
                    isDayMode = self.A7_4SelectedRuleCode() == 1;
                if (data) {
                    if (isDayMode) {
                        self.conditionValue04Day(data);
                    } else {
                        self.conditionValue04Percent(data);
                    }
                } else {
                    return isDayMode ? self.conditionValue04Day() : self.conditionValue04Percent();
                }
            }
            
            conditionValue05(data ?){
                let self = this,
                    isDayMode = self.A7_4SelectedRuleCode() == 1;
                if (data) {
                    if (isDayMode) {
                        self.conditionValue05Day(data);
                    } else {
                        self.conditionValue05Percent(data);
                    }
                } else {
                    return isDayMode ? self.conditionValue05Day() : self.conditionValue05Percent();
                }
            }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            $.when(self.getData()).done(function() {
                                
                if (self.items().length > 0) {
                    self.singleSelectedCode(self.items()[0].code);
                } else {
                    self.cleanForm();
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });

            return dfd.promise();
        }
        
        /**
         * Get data from db.
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.items([]);
            service.findAll().done(function(data) {
                _.forEach(data, function(item) {
                    self.items.push(new ItemModel(item.yearHolidayCode, item.yearHolidayName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        getGHTdata(conditionNo: number, yearHolidayCode: string) {
            var self = this;
            
            service.findGrantHolidayTblByCodes(conditionNo, yearHolidayCode).done(function(data) {
                if(data.length > 0) {
                    self.grantHTData = data;
                    
                    _.forEach(self.grantHTData, function(item) {
                        if(item.conditionNo === 1){
                            $('.a9_1').hide();
                        } else {
                            $('.a9_1').show();
                        }
                        
                        if(item.conditionNo === 2){
                            $('.a9_2').hide();
                        } else {
                            $('.a9_2').show();
                        }
                        
                        if(item.conditionNo === 3){
                            $('.a9_3').hide();
                        } else {
                            $('.a9_3').show();
                        }
                        
                        if(item.conditionNo === 4){
                            $('.a9_4').hide();
                        } else {
                            $('.a9_4').show();
                        }
                        
                        if(item.conditionNo === 5){
                            $('.a9_5').hide();
                        } else {
                            $('.a9_5').show();
                        }
                    });
                }
            }).fail(function(res) {
            });
        }
        
        /**
         * Clear data input on form
         */
        cleanForm(){
            var self = this;

            // clear all error
            nts.uk.ui.errors.clearAll();
            
            self.isNewMode(true);
            
            //Top input form
            self.code("");
            self.name("");              
            self.useConditionCls(false);            
            self.grantDate("101"); 
            self.A6_2SelectedRuleCode(0);  
            self.A7_4SelectedRuleCode(0);
            self.symbols("%");
            self.limitedValue01("100");
            self.limitedValue02("");
            self.limitedValue03("");
            self.limitedValue04("");
            self.limitedValue05("");
            
            //Bottom input form
            self.conditionValue01Day("");
            self.conditionValue02Day("");
            self.conditionValue03Day("");
            self.conditionValue04Day("");
            self.conditionValue05Day("");
            self.conditionValue01Percent("");
            self.conditionValue02Percent("");
            self.conditionValue03Percent("");
            self.conditionValue04Percent("");
            self.conditionValue05Percent("");
            self.note("");
            self.conditionValue01Enable(false);
            self.conditionValue02Enable(false);
            self.conditionValue03Enable(false);
            self.conditionValue04Enable(false);
            self.conditionValue05Enable(false);
            self.btnSetting02Enable(false);
            self.btnSetting03Enable(false);
            self.btnSetting04Enable(false);
            self.btnSetting05Enable(false); 
            
            self.showLblSet01(false);
            self.showLblSet02(false);
            self.showLblSet03(false);
            self.showLblSet04(false);
            self.showLblSet05(false);
            
            self.useCls01(false);
            self.useCls02(false);
            self.useCls03(false);
            self.useCls04(false);
            self.useCls05(false);
            self.useCls02Enable(false);
            self.useCls03Enable(false);
            self.useCls04Enable(false);
            self.useCls05Enable(false);
            
            //Grid data
            self.singleSelectedCode([]);
            
            self.editMode(true);
            
            self.setFocus();
            $('.a9_6').show();
        }
        
        setFocus() {
            var self = this;
            if (self.editMode()) {
                $("#input-code").focus();
            } else {
                $("#input-name").focus();    
            }   
        }
        
        /**
         * Execute add or update data to db
         */
        addFunction(){
            var self = this;
            nts.uk.ui.block.invisible();
            // clear all error
            nts.uk.ui.errors.clearAll();
            
            nts.uk.ui.block.invisible();
            
            // validate
            $(".input-code").trigger("validate");
            $(".input-name").trigger("validate");
            $(".nts-input:visible").trigger("validate");
            $(".a7_7:visible").trigger("validate");
            
            if(self.name().trim() === "") {
                self.name("");
                $(".input-name").trigger("validate");
            }
            
            if(!self.editMode()) {
              
                $("#a8_2").trigger("validate");
            }            
            
            if (nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.clear(); 
                return;    
            }
                
            var code = self.code();
            var name = self.name();
            var calMethod = Number(self.A7_4SelectedRuleCode());
            var standMethod = Number(self.A6_2SelectedRuleCode());
            var useConditionCls = self.useConditionCls() == true ? 1 : 0;
            var grantMd = Number(self.grantDate());
            var memo = self.note();
            var grandConditions = new Array<GrantCondition>();
            
            if(self.conditionValue01() != null || self.editMode()){
                grandConditions.push(new GrantCondition({
                    conditionNo: 1,
                    yearHolidayCode: code,
                    useConditionAtr: 1,
                    conditionValue: self.conditionValue01() !== "" ? Number(self.conditionValue01()) : self.conditionValue01()
                }));
            } else {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                nts.uk.ui.block.clear(); 
                return;
            }
            
            if(self.conditionValue02() != null){
                grandConditions.push(new GrantCondition({
                    conditionNo: 2,
                    yearHolidayCode: code,
                    useConditionAtr: self.useCls02() == true ? 1 : 0,
                    conditionValue: self.conditionValue02() !== "" ? Number(self.conditionValue02()) : self.conditionValue02()
                }));
            } else if(self.useCls02() && self.conditionValue02().trim() === "") {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                nts.uk.ui.block.clear(); 
                return;
            }
            
            if(self.conditionValue03() != null){
                grandConditions.push(new GrantCondition({
                    conditionNo: 3,
                    yearHolidayCode: code,
                    useConditionAtr: self.useCls03() == true ? 1 : 0,
                    conditionValue: self.conditionValue03() !== "" ? Number(self.conditionValue03()) : self.conditionValue03()
                }));
            } else if(self.useCls03() && self.conditionValue03().trim() === "") {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                nts.uk.ui.block.clear(); 
                return;
            }
            
            if(self.conditionValue04() != null){
                grandConditions.push(new GrantCondition({
                    conditionNo: 4,
                    yearHolidayCode: code,
                    useConditionAtr: self.useCls04() == true ? 1 : 0,
                    conditionValue: self.conditionValue04() !== "" ? Number(self.conditionValue04()) : self.conditionValue04()
                }));
            } else if(self.useCls04() && self.conditionValue04().trim() === "") {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                nts.uk.ui.block.clear(); 
                return;
            }
            
            if(self.conditionValue05() != null){
                grandConditions.push(new GrantCondition({
                    conditionNo: 5,
                    yearHolidayCode: code,
                    useConditionAtr: self.useCls05() == true ? 1 : 0,
                    conditionValue: self.conditionValue05() !== "" ? Number(self.conditionValue05()) : self.conditionValue05()
                }));
            } else if(self.useCls05() && self.conditionValue05().trim() === "") {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                nts.uk.ui.block.clear(); 
                return;
            }
            
            var data = new YearHolidayGrantDto(code, name, calMethod, standMethod, useConditionCls, grantMd, memo, grandConditions);
            
            if(!self.editMode()){
                let check = [];
                let flag = true;
                
                _.forEach(data.grantConditions, function(item) {
                    if(isNaN(item.conditionValue)) {
                        flag = false;
                        
                        if(self.A7_4SelectedRuleCode() == 0) {
                            if(item.conditionNo == 2) {
                             
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 3) {
                            
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 4) {
                            
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 5) {
                             
                                nts.uk.ui.block.clear();
                                return;
                            }
                        } else {
                            if(item.conditionNo == 2) {
                            
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 3) {
                              
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 4) {
                              
                                nts.uk.ui.block.clear();
                                return;
                            } else if(item.conditionNo == 5) {
                             
                                nts.uk.ui.block.clear();
                                return;
                            }
                        }
                    }
                });
                
                _.forEach(data.grantConditions, function(item) {
                    if(item.useConditionAtr == 1) {
                        check.push(item);
                    }
                });
                
                if(check.length > 1) {
                    _.forEach(check, function(item, index) {
                        var value = check[index + 1] != null ? check[index + 1].conditionValue : 0;
                        
                        if(flag && item.conditionValue <= value) {
                            if(flag && value == 0 && item.conditionValue == value) {
                                flag = true;
                            } else {
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_264" });
                                flag = false;
                                nts.uk.ui.block.clear();
                                return;
                            }
                        }
                    });
                    
                    if(flag) {
                        self.updateMode(data);
                        nts.uk.ui.block.clear();
                    }
                } else {
                    if(flag) {
                        self.updateMode(data);
                        nts.uk.ui.block.clear();
                    }
                }
            } else {
                self.addMode(data);
                nts.uk.ui.block.clear();
            }
        }
        
        //Add data to db
        addMode(data: YearHolidayGrantDto){
            var self = this;
            
            if (nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.clear(); 
                return;    
            }
            
            service.addYearHolidayGrant(data).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.getData();
                self.singleSelectedCode(data.yearHolidayCode);
            }).fail(function(error) {
                $('.input-code').ntsError('set', error);
            }).always(function() {
                nts.uk.ui.block.clear();      
            });
        }
        
        //Update data to db
        updateMode(data: YearHolidayGrantDto){
            var self = this;
            
            service.updateYearHolidayGrant(data).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.getData();
                self.singleSelectedCode(data.yearHolidayCode);
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();      
            });
        }
        
        /**
         * Delete data by code
         */
        deleteFunction(){
            var self = this;
            let count = 0;
            for (let i = 0; i <= self.items().length; i++){
                if(self.items()[i].code == self.singleSelectedCode()){
                    count = i;
                    break;
                }
            }
            
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteYearHolidayGrant(self.singleSelectedCode()).done(function() {
                    self.getData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.items().length==0){
                            self.cleanForm();
                            return;
                        }
                        // delete the last item
                        if(count == ((self.items().length))){
                            self.singleSelectedCode(self.items()[count-1].code);
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.singleSelectedCode(self.items()[0].code);
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.items().length){
                            self.singleSelectedCode(self.items()[count].code);    
                            return;
                        }
                    });
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);
                }).always(function() {
                    nts.uk.ui.block.clear();      
                });
            })
        }
        
        /**
         * Open screen B in dialog
         */
        openBDialog(conditionNo: number) {
            var self = this;
            
            var optionValue = '';
            var conditionValue = '';
            var afterValue = '';
            
            //Call validate
            var validateFlag = self.validateToOpenDialogB(conditionNo);
            if(!validateFlag){
                return false;    
            }
            
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            if(conditionNo === 1){
                conditionValue = self.conditionValue01();
                afterValue = $('.a7_8').text();
            } else if(conditionNo === 2){
                conditionValue = self.conditionValue02();
                afterValue = $('.a7_12').text();
            } else if(conditionNo === 3){
                conditionValue = self.conditionValue03();
                afterValue = $('.a7_16').text();
            } else if(conditionNo === 4){
                conditionValue = self.conditionValue04();
                afterValue = $('.a7_20').text();
            } else if(conditionNo === 5){
                conditionValue = self.conditionValue05();
                afterValue = $('.a7_24').text();
            }
            
            if(Number(self.A7_4SelectedRuleCode()) === 0) {
                optionValue = nts.uk.resource.getText("KMF003_21");
            } else {
                optionValue = nts.uk.resource.getText("KMF003_22");
            }
            
            var data = {
                code: self.code(),
                name: self.name(),
                conditionValue: {
                    option: optionValue,
                    value: conditionValue,
                    afterValue: afterValue
                },
                useCondition: self.useConditionCls(),
                dateSelected: self.useConditionCls() ? self.grantDate() : "",
                conditionNo: conditionNo
            };
            
            nts.uk.ui.windows.setShared("KMF003_CONDITION_NO", data);
            
            if(conditionNo === 1) {
                nts.uk.ui.windows.sub.modal("/view/kmf/003/b/index.xhtml").onClosed(() => {
                    var dataIsNotNull = nts.uk.ui.windows.getShared("KMF003_HAVE_DATA");
                    
                    if(dataIsNotNull) {
                        if(conditionNo === 1){
                            self.showLblSet01(true);
                        } else if(conditionNo === 2){
                            self.showLblSet02(true);
                        } else if(conditionNo === 3){
                            self.showLblSet03(true);
                        } else if(conditionNo === 4){
                            self.showLblSet04(true);
                        } else if(conditionNo === 5){
                            self.showLblSet05(true);
                        }
                    } else {
                        if(conditionNo === 1){
                            self.showLblSet01(false);
                        } else if(conditionNo === 2){
                            self.showLblSet02(false);
                        } else if(conditionNo === 3){
                            self.showLblSet03(false);
                        } else if(conditionNo === 4){
                            self.showLblSet04(false);
                        } else if(conditionNo === 5){
                            self.showLblSet05(false);
                        }
                    }
                    
                    nts.uk.ui.windows.setShared("KMF003_CANCEL_DATA", true);
                });   
            } else {
                nts.uk.ui.windows.sub.modal("/view/kmf/003/b1/index.xhtml").onClosed(() => {
                    var dataIsNotNull = nts.uk.ui.windows.getShared("KMF003_HAVE_DATA");
                    
                    if(dataIsNotNull) {
                        if(conditionNo === 1){
                            self.showLblSet01(true);
                        } else if(conditionNo === 2){
                            self.showLblSet02(true);
                        } else if(conditionNo === 3){
                            self.showLblSet03(true);
                        } else if(conditionNo === 4){
                            self.showLblSet04(true);
                        } else if(conditionNo === 5){
                            self.showLblSet05(true);
                        }
                    } else {
                        if(conditionNo === 1){
                            self.showLblSet01(false);
                        } else if(conditionNo === 2){
                            self.showLblSet02(false);
                        } else if(conditionNo === 3){
                            self.showLblSet03(false);
                        } else if(conditionNo === 4){
                            self.showLblSet04(false);
                        } else if(conditionNo === 5){
                            self.showLblSet05(false);
                        }
                    }
                    
                    nts.uk.ui.windows.setShared("KMF003_CANCEL_DATA", true);
                });   
            }
        }
        
        /**
         * Validate before open dialog B.
         */
        validateToOpenDialogB(conditionNo: number){
            var self = this;
            
            if(self.useConditionCls() && self.grantDate() === ""){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_261" });
                return false;
            }
            
            if(conditionNo === 1 && (self.conditionValue01() === "" || self.conditionValue01() === undefined)){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                return false;
            } else if(conditionNo === 2 && (self.conditionValue02() === "" || self.conditionValue02() === undefined)){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                return false;
            } else if(conditionNo === 3 && (self.conditionValue03() === "" || self.conditionValue03() === undefined)){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                return false;
            } else if(conditionNo === 4 && (self.conditionValue04() === "" || self.conditionValue04() === undefined)){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                return false;
            } else if(conditionNo === 5 && (self.conditionValue05() === "" || self.conditionValue05() === undefined)){
                nts.uk.ui.dialog.alertError({ messageId: "Msg_271" });
                return false;
            }
            
            return true;
        }
        
        /**
         * Enable or disable for setting form.
         */
        conditionSettingForm() {
            var self = this;
            var count = 0;
            
            self.A7_4SelectedRuleCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value == 0){
                    self.symbols("%");
                    self.limitedValue01("100");
                    self.setConditionValueChanges();
                    
                    if((Number(self.conditionValue01()) > 100 || Number(self.conditionValue01()) < 0) && !self.isNewMode()) {
                   
                    }
                } else if(value == 1) {
                    self.symbols("日");
                    self.limitedValue01("366");
                    self.setConditionValueChanges();
                    
                    if((Number(self.conditionValue01()) > 366 || Number(self.conditionValue01()) < 0) && !self.isNewMode()) {
                     
                    }
                }
                
                self.isNewMode(false);
            });
            
             self.conditionValue01Percent.subscribe(function(value) {
                var result = 0;
                
                if(value === "") {
                    return false;
                }
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
                self.changeConditionValue01(value);
              
            });
            
            self.conditionValue01Day.subscribe(function(value) {
                var result = 0;
                
                if(value === "") {
                    return false;
                }
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
                self.changeConditionValue01(value);
              
            });
            
            self.conditionValue02Day.subscribe(function(value) {
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
              self.changeConditionValue02(value);
            });
            
            
             self.conditionValue02Percent.subscribe(function(value) {
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                
              self.changeConditionValue02(value);
            });
            
            self.conditionValue03Day.subscribe(function(value) {
              
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
               self.changeConditionValue03(value);
            });
            
            self.conditionValue03Percent.subscribe(function(value) {
              
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
               self.changeConditionValue03(value);
            });
            
            self.conditionValue04Day.subscribe(function(value) {
                
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                self.changeConditionValue04(value);
               
            });
            
            self.conditionValue04Percent.subscribe(function(value) {
                
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                self.changeConditionValue04(value);
               
            });
            
            self.useCls02.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value == true){
                    self.conditionValue02Enable(true);
                    self.btnSetting02Enable(!self.editMode());
                    self.setConditionValues(Number(self.conditionValue01()), 2);
                    
                    if(self.useCls03()) {
                        self.setConditionValues(Number(self.conditionValue02()), 3);
                        
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue03()), 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5);
                            }
                        } else {
                            self.setConditionValues(0, 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue03()), 5);
                            }
                        }
                    } else {
                        self.setConditionValues(0, 3);
                        
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue02()), 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5);
                            }
                        } else {
                            self.setConditionValues(0, 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue02()), 5);
                            }
                        }
                    }
                } else {
                    self.conditionValue02Enable(false);
                    self.btnSetting02Enable(false);
                    self.limitedValue02("");
                    
                    if(self.useCls03()) {
                        self.setConditionValues(Number(self.conditionValue01()), 3);
                    } else if(self.useCls04()) {
                        self.setConditionValues(Number(self.conditionValue01()), 4);
                    } else if(self.useCls05()) {
                        self.setConditionValues(Number(self.conditionValue01()), 5);
                    }  
                }
            });
            
            self.useCls03.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value == true){
                    self.conditionValue03Enable(true);
                    self.btnSetting03Enable(true);
                    
                    if(self.useCls02()) {
                        self.setConditionValues(Number(self.conditionValue02()), 3); 
                        
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue03()), 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5);
                            }
                        } else {
                            self.setConditionValues(0, 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue03()), 5);
                            }
                        }
                    } else {
                        self.setConditionValues(Number(self.conditionValue01()), 3); 
                        
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue03()), 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5);
                            }
                        } else {
                            self.setConditionValues(0, 4);
                            
                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue03()), 5);
                            }
                        }
                    }
                } else {
                    self.conditionValue03Enable(false);
                    self.btnSetting03Enable(false);
                    self.limitedValue03("");
                    
                    if(self.useCls02()) {
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue02()), 4);
                        } else {
                            self.setConditionValues(0, 4);
                            
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue02()), 5);
                            }
                        }
                    } else {
                        if(self.useCls04()) {
                            self.setConditionValues(Number(self.conditionValue01()), 4);
                        } else {
                            self.setConditionValues(0, 4);

                            if (self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue01()), 5);
                            }
                        }
                    }
                }
            });
            
            self.useCls04.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value == true){
                    self.conditionValue04Enable(true);
                    self.btnSetting04Enable(true);
                    
                    if(self.useCls02()) {
                        if(self.useCls03()) {
                            self.setConditionValues(Number(self.conditionValue03()), 4); 
                            
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5); 
                            }
                        } else {
                            self.setConditionValues(Number(self.conditionValue02()), 4); 
                            
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5); 
                            }
                        }
                    } else {
                        if(self.useCls03()) {
                            self.setConditionValues(Number(self.conditionValue03()), 4); 
                            
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5); 
                            }
                        } else {
                            self.setConditionValues(Number(self.conditionValue01()), 4); 
                            
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue04()), 5); 
                            }
                        }
                    }
                } else {
                    self.conditionValue04Enable(false);
                    self.btnSetting04Enable(false);
                    self.limitedValue04("");
                    
                    if(self.useCls02()) {
                        if(self.useCls03()) {
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue03()), 5); 
                            }
                        } else {
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue02()), 5); 
                            }
                        }
                    } else {
                        if(self.useCls03()) {
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue03()), 5); 
                            }
                        } else {
                            if(self.useCls05()) {
                                self.setConditionValues(Number(self.conditionValue01()), 5); 
                            }
                        }
                    }
                }
            });
            
            self.useCls05.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(value == true){
                    self.conditionValue05Enable(true);
                    self.btnSetting05Enable(true);
                    
                    if(self.useCls04()) {
                        self.setConditionValues(Number(self.conditionValue04()), 5);
                    } else if(self.useCls03()) {
                        self.setConditionValues(Number(self.conditionValue03()), 5); 
                    } else if(self.useCls02()) {
                        self.setConditionValues(Number(self.conditionValue02()), 5); 
                    } else {
                        self.setConditionValues(Number(self.conditionValue01()), 5); 
                    }
                } else {
                    self.conditionValue05Enable(false);
                    self.btnSetting05Enable(false);
                    self.limitedValue05("");
                    self.setConditionValues(0, 5);
                }
            });
        }
        
        changeConditionValue04(value) {
            let self = this;
            let result = Number(value) - 1;

            if (self.A7_4SelectedRuleCode() == 0) {
                if ((Number(value) > (Number(self.limitedValue04())) || Number(self.conditionValue04()) < 0)) {

                } else {
                    if (self.useCls05()) {
                        if (value == undefined || value == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }

                        self.limitedValue05(result < 0 ? "" : result.toString());
                    }
                }
            } else if (self.A7_4SelectedRuleCode() == 1) {
                if ((Number(value) > (Number(self.limitedValue04())) || Number(self.conditionValue04()) < 0)) {

                } else {
                    if (self.useCls05()) {
                        if (value == undefined || value == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }

                        self.limitedValue05(result < 0 ? "" : result.toString());
                    }
                }
            } else {
                if (self.useCls05()) {
                    if (value == undefined || value == "") {
                        result = 0;
                    } else {
                        result = Number(value) - 1;
                    }

                    self.limitedValue05(result < 0 ? "" : result.toString());
                }
            }
        }
        
        changeConditionValue03(value) {
            let self = this;

            let result = Number(value) - 1;

            if (self.A7_4SelectedRuleCode() == 0) {
                if ((Number(value) > (Number(self.limitedValue03())) || Number(self.conditionValue03()) < 0)) {

                } else {
                    if (self.useCls04()) {
                        if (self.conditionValue03() == undefined || self.conditionValue03() == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }

                        self.limitedValue04(result < 0 ? "" : result.toString());
                    } else {
                        if (self.useCls05()) {
                            if (self.conditionValue03() == undefined || self.conditionValue03() == "") {
                                result = 0;
                            } else {
                                result = Number(value) - 1;
                            }

                            self.limitedValue05(result < 0 ? "" : result.toString());
                        }
                    }
                }
            } else if (self.A7_4SelectedRuleCode() == 1) {
                if ((Number(value) > (Number(self.limitedValue03())) || Number(self.conditionValue03()) < 0)) {

                } else {
                    if (self.useCls04()) {
                        if (self.conditionValue03() == undefined || self.conditionValue03() == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }

                        self.limitedValue04(result < 0 ? "" : result.toString());
                    } else {
                        if (self.useCls05()) {
                            if (self.conditionValue03() == undefined || self.conditionValue03() == "") {
                                result = 0;
                            } else {
                                result = Number(value) - 1;
                            }

                            self.limitedValue05(result < 0 ? "" : result.toString());
                        }
                    }
                }
            }
        }
        
        
        changeConditionValue02(value) {
            let self = this;

            let result = Number(value) - 1;
            if (self.useCls03()) {
                if (self.conditionValue02() == undefined || self.conditionValue02() == "") {
                    result = 0;
                } else {
                    result = Number(value) - 1;
                }
                self.limitedValue03(result < 0 ? "" : result.toString());
            } else {
                if (self.useCls04()) {
                    if (self.conditionValue02() == undefined || self.conditionValue02() == "") {
                        result = 0;
                    } else {
                        result = Number(value) - 1;
                    }
                    self.limitedValue04(result < 0 ? "" : result.toString());
                } else {
                    if (self.useCls05()) {
                        if (self.conditionValue02() == undefined || self.conditionValue02() == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }
                        self.limitedValue05(result < 0 ? "" : result.toString());
                    }
                }
            }
        }
        changeConditionValue01(value) {
            let self = this, result = 0;
            if (self.useCls02()) {
                if (value == undefined || value == "") {
                    result = 0;
                } else {
                    result = Number(value) - 1;
                }
                self.limitedValue02(result < 0 ? "" : result.toString());
            } else {
                if (self.useCls03()) {
                    if (value == undefined || value == "") {
                        result = 0;
                    } else {
                        result = Number(value) - 1;
                    }
                    self.limitedValue03(result < 0 ? "" : result.toString());
                } else {
                    if (self.useCls04()) {
                        if (value == undefined || value == "") {
                            result = 0;
                        } else {
                            result = Number(value) - 1;
                        }
                        self.limitedValue04(result < 0 ? "" : result.toString());
                    } else {
                        if (self.useCls05()) {
                            if (value == undefined || value == "") {
                                result = 0;
                            } else {
                                result = Number(value) - 1;
                            }
                            self.limitedValue05(result < 0 ? "" : result.toString());
                        }
                    }
                }
            }
        }
        
        /**
         * Set condition values.
         */
        setConditionValues(value: number, position: number) {
            var self = this;
            var result = value - 1;
            
            if(position == 2) {
                self.limitedValue02(result < 0 ? "" : (result.toString() === "NaN" ? "" : result.toString()));
            } else if (position == 3) {
                self.limitedValue03(result < 0 ? "" : (result.toString() === "NaN" ? "" : result.toString()));
            } else if (position == 4) {
                self.limitedValue04(result < 0 ? "" : (result.toString() === "NaN" ? "" : result.toString()));
            } else if (position == 5) {
                self.limitedValue05(result < 0 ? "" : (result.toString() === "NaN" ? "" : result.toString()));
            }
        }
        
        /**
         * Set condition values when change condition type.
         */
        setConditionValueChanges() {
            var self = this;
            var result02 = Number(self.conditionValue01()) - 1;
            var result03 = Number(self.conditionValue02()) - 1;
            var result04 = Number(self.conditionValue03()) - 1;
            var result05 = Number(self.conditionValue04()) - 1;
            
            self.limitedValue02(self.useCls02() ? (result02 <= 0 ? "" : result02.toString()) : "");
            self.limitedValue03(self.useCls03() ? (result03 <= 0 ? "" : result03.toString()) : "");
            self.limitedValue04(self.useCls04() ? (result04 <= 0 ? "" : result04.toString()) : "");
            self.limitedValue05(self.useCls05() ? (result05 <= 0 ? "" : result05.toString()) : "");
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;       
        }
    } 
    
    class YearHolidayGrantDto {
        yearHolidayCode: string;
        yearHolidayName: string;
        calculationMethod: number;
        standardCalculation: number;
        useSimultaneousGrant: number;
        simultaneousGrandMonthDays: number;
        yearHolidayNote: string;
        grantConditions: Array<GrantCondition>;  
        constructor(yearHolidayCode: string, yearHolidayName: string, calculationMethod: number, standardCalculation: number, useSimultaneousGrant: number, 
                simultaneousGrandMonthDays: number, yearHolidayNote: string, grantConditions: Array<GrantCondition>) {
            this.yearHolidayCode = yearHolidayCode;
            this.yearHolidayName = yearHolidayName;     
            this.calculationMethod = calculationMethod;
            this.standardCalculation = standardCalculation; 
            this.useSimultaneousGrant = useSimultaneousGrant;
            this.simultaneousGrandMonthDays = simultaneousGrandMonthDays; 
            this.yearHolidayNote = yearHolidayNote;
            this.grantConditions = grantConditions;   
        }
    }
    
    class GrantCondition {
        yearHolidayCode: string;
        conditionNo: number;
        conditionValue: any;
        useConditionAtr: number; 
        constructor(param: IGrantCondition) {
            this.yearHolidayCode = param.yearHolidayCode;
            this.conditionNo = param.conditionNo;
            this.conditionValue = param.conditionValue;
            this.useConditionAtr = param.useConditionAtr;       
        }
    }
    
    interface IGrantCondition {
        yearHolidayCode: string;
        conditionNo: number;
        conditionValue: any;
        useConditionAtr: number;     
    }
}
