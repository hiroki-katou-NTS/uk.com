module nts.uk.at.view.kmf002.a {
    
    import viewModelTabB = nts.uk.at.view.kmf002.b.viewmodel;
    import viewModelTabC = nts.uk.at.view.kmf002.c.viewmodel;
    import viewModelTabD = nts.uk.at.view.kmf002.d.viewmodel;
    import viewModelTabA = nts.uk.at.view.kmf002.a;
    import viewModelTabE = nts.uk.at.view.kmf002.e.viewmodel;
    
    import viewModelTabF = nts.uk.at.view.kmf002.f.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    
    import service = nts.uk.at.view.kmf002.a.service;
    
    export module viewmodel {
        export class ScreenModel {
            screenB: KnockoutObservable<any>;
            screenC: KnockoutObservable<any>;
            screenD: KnockoutObservable<any>;
            screenE: KnockoutObservable<any>;
            
            /* main define variable code */
            managePublicHoliday: KnockoutObservable<number> = ko.observable(0);;
            publicHdCarryOverDeadline: KnockoutObservable<number> = ko.observable(0);;
            carryOverNumberOfPublicHdIsNegative: KnockoutObservable<number> = ko.observable(0);;
            publicHolidayPeriod: KnockoutObservable<number> = ko.observable(0);;

            companyManageClassification: KnockoutObservableArray<any> = ko.observableArray([]);
            lstManagementPeriod: KnockoutObservableArray<any> = ko.observableArray([]);         
            dayOfPublicHoliday: KnockoutObservableArray<any> = ko.observableArray([]);
            
            enableFullDate: KnockoutObservable<boolean> = ko.observable(true); 
            lstCarryOverDeadline: KnockoutObservableArray<any> = ko.observableArray([]);
            
            lstStartDayOfWeek: KnockoutObservableArray<any> = ko.observableArray([]); 
            
            isDisableSetUnitBtn: KnockoutObservable<boolean> = ko.observable(false);
            enablePubHDPeriod: KnockoutObservable<boolean> = ko.observable(true);            
            enableCarryOverDeadline: KnockoutObservable<boolean> = ko.observable(true);
            enablecarryOverNumberOfPublicHdIsNegative: KnockoutObservable<boolean> = ko.observable(true);

            
            isManageEmployeePublicHd: KnockoutObservable<number>;
            isManageWkpPublicHd: KnockoutObservable<number>;
            isManageEmpPublicHd: KnockoutObservable<number>;
            
            date: KnockoutObservable<string> = ko.observable('20000101');
            
            constructor(){
                let self = this;
                self.screenC = ko.observable(new viewModelTabC.ScreenModel());
                self.screenD = ko.observable(new viewModelTabD.ScreenModel());
                self.screenB = ko.observable(new viewModelTabB.ScreenModel());
                self.screenE = ko.observable(new viewModelTabE.ScreenModel());   

                self.isManageEmployeePublicHd = ko.observable(1);
                self.isManageWkpPublicHd = ko.observable(1);
                self.isManageEmpPublicHd = ko.observable(1);
                
                // start subscribe
                self.managePublicHoliday.subscribe( (newValue: number) => {
                    self.condition();
                    self.conditionSideBar5();
                });

                // self.publicHolidayPeriod.subscribe(function(newValue) {
                //     // setting for button Setting Unit
                //     if (newValue == 0 && self.managePublicHoliday() == ManagePubHD.MANAGE) {
                //         self.isDisableSetUnitBtn(true);
                //     } else {
                //         self.isDisableSetUnitBtn(false);
                //     }                    
                //     // self.conditionSideBar1();
                //     self.conditionSideBar2();
                //     self.conditionSideBar3();
                //     self.conditionSideBar4();
                // });  
                self.carryOverNumberOfPublicHdIsNegative.subscribe(function(newValue) {
                    if (newValue == 1) {
                        self.carryOverNumberOfPublicHdIsNegative(BoolValue.TRUE);        
                    } else {
                        self.carryOverNumberOfPublicHdIsNegative(BoolValue.FALSE);
                    }
                });                
                
                self.isManageEmpPublicHd.subscribe(function(newValue) {
                    self.conditionSideBar3();
                });
                
                self.isManageEmployeePublicHd.subscribe(function(newValue) {
                    self.conditionSideBar4();
                });
                
                self.isManageWkpPublicHd.subscribe(function(newValue) {
                    self.conditionSideBar2();
                });
            }
            
            public startPage(typeStart: number): JQueryPromise<any> {                
                var dfd = $.Deferred<any>();
                
                let self = this;
                // load all
                if (typeStart == SideBarTabIndex.THIRD) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    self.screenB(new viewModelTabB.ScreenModel());
                    $.when(self.screenB().start_age()).done(function() {
                        dfd.resolve(self);
                        blockUI.clear();
                        $( "#scrB .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FIRST) {
                    // Process for screen A (Mother of all screen)
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(self.getAllEnum(), self.getAllData(), self.findAllManageUseUnit()).done(function() {
                        $( "#managePubHD" ).focus();
                        dfd.resolve(self);    
                        blockUI.clear();
                    });
                } else if (typeStart == SideBarTabIndex.SECOND) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    self.screenE(new viewModelTabE.ScreenModel());
                    $.when(self.screenE().start_page()).done(function() {
                        dfd.resolve(self);
                        blockUI.clear();
                        $( "#scrE .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FOURTH) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    self.screenD(new viewModelTabD.ScreenModel());
                    $.when(self.screenD().start_page()).done(function() {
                        dfd.resolve(self);
                        blockUI.clear();
                        $( "#scrD .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FIFTH) {
                    nts.uk.ui.errors.clearAll();
                    blockUI.grayout();
                    self.screenC(new viewModelTabC.ScreenModel());
                    $.when(self.screenC().start_page()).done(function() {
                        dfd.resolve(self);
                        blockUI.clear();
                        $( "#scrC .datePickerYear" ).focus();
                    });    
                }
                
                return dfd.promise();
            }
            
            /**
             * on select tab handle
             */
            
            public onSelectTabA(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIRST,
                    activate: (event, info) => {
                        let self = this;
                        self.startPage(SideBarTabIndex.FIRST);
                    }
                });
            }
            
            public onSelectTabB(): void {
                let self = this;
                if (self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.THIRD,
                        activate: (event, info) => {
                            self.startPage(SideBarTabIndex.THIRD);
                        }
                    });
                }
            }
            
            public onSelectTabC(): void {
                let self = this;
                if (self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.FIFTH,
                        activate: (event, info) => {
                            self.startPage(SideBarTabIndex.FIFTH);
                        }
                    });
                }
            }
            
            public onSelectTabD(): void {
                let self = this;
                if (self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.FOURTH,
                        activate: (event, info) => {
                            self.startPage(SideBarTabIndex.FOURTH);
                        }
                    });    
                }
            }
            
            public onSelectTabE(): void {
                let self = this;
                if (self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.SECOND,
                        activate: (event, info) => {
                            self.startPage(SideBarTabIndex.SECOND);
                        }
                    });        
                }
            }
            
            private isDisableTab(): boolean {
                let self = this;
                if (self.managePublicHoliday() == ManagePubHD.MANAGE) {
                    return false;
                }
                return true;
            }
            
            /**
             *  process for screen A
             */
            public save(): void {
                let self = this,
                data: any = {};
                data.managePublicHoliday = self.managePublicHoliday();
                data.publicHdCarryOverDeadline = self.publicHdCarryOverDeadline();
                data.carryOverNumberOfPublicHdIsNegative = self.carryOverNumberOfPublicHdIsNegative();
                data.publicHolidayPeriod = self.publicHolidayPeriod();

                nts.uk.ui.errors.clearAll();
                self.validateInput();
                self.checkValidate();
               
                if (!nts.uk.ui.errors.hasError()) {
                    blockUI.invisible();
                    $.when(service.save(data).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.getAllData();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res);
                    }).always(()=> blockUI.clear()));    
                }
            }
            
            private checkValidate(): void {
                let self = this;
                if ( self.managePublicHoliday() == ManagePubHD.NOT_MANAGE ) {
                    nts.uk.ui.errors.clearAll();    
                } 
            }
            
            private validateInput(): void {
                $('#inLegalHoliday16').ntsEditor("validate");
                $('#outLegalHoliday17').ntsEditor("validate");
                $('#inLegalHoliday18').ntsEditor("validate");
                $('#outLegalHoliday19').ntsEditor("validate");
                $('#inLegalHoliday20').ntsEditor("validate");
                $('#outLegalHoliday21').ntsEditor("validate");
                $('#inLegalHoliday22').ntsEditor("validate");
                $('#outLegalHoliday23').ntsEditor("validate");
                $('#inLegalHoliday24').ntsEditor("validate");
                $('#outLegalHoliday25').ntsEditor("validate");
            }
            
            private getAllData(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                $.when(service.findAll()).done(function(data: any) {

                    if (!_.isUndefined(data) && !_.isNull(data) && !_.isEmpty(data)) {
                        self.managePublicHoliday(data.managePublicHoliday);
                        
                        self.carryOverNumberOfPublicHdIsNegative(data.carryOverNumberOfPublicHdIsNegative);
                        self.publicHolidayPeriod(data.publicHolidayPeriod);
                        self.publicHdCarryOverDeadline(data.publicHdCarryOverDeadline);
                    }
                   
                    // notify variable observable
                    self.managePublicHoliday.valueHasMutated();
                    self.publicHolidayPeriod.valueHasMutated();
                    self.carryOverNumberOfPublicHdIsNegative.valueHasMutated();                    
                    
                    dfd.resolve();   
                });
                return dfd.promise();
            }
            
            // private conditionSideBar1(): void {
            //     let self = this;
            //     if (self.publicHolidayPeriod() == PublicHDPeriod.ONE_MONTH) {
            //         $("#sidebar").ntsSideBar("show", SideBarTabIndex.SECOND);        
            //     } else {
            //         $("#sidebar").ntsSideBar("hide", SideBarTabIndex.SECOND);    
            //     }
            // }
            
            private conditionSideBar2(): void {
                let self = this;
                if (self.isManageWkpPublicHd() == BoolValue.TRUE ) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.THIRD);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.THIRD);
                }
            }
            
            private conditionSideBar3(): void {
                let self = this;
                if (self.isManageEmpPublicHd() == BoolValue.TRUE) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.FOURTH);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.FOURTH);
                }
            }
            
            private conditionSideBar4(): void {
                let self = this;
                if (self.isManageEmployeePublicHd() == BoolValue.TRUE) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.FIFTH);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.FIFTH);
                }
            }
            
            
            private conditionSideBar5(): void {
                let self = this;
                if (self.managePublicHoliday() == ManagePubHD.MANAGE) {
                    $("#sidebar").ntsSideBar("enable", SideBarTabIndex.SECOND);
                    $("#sidebar").ntsSideBar("enable", SideBarTabIndex.THIRD);
                    $("#sidebar").ntsSideBar("enable", SideBarTabIndex.FOURTH);
                    $("#sidebar").ntsSideBar("enable", SideBarTabIndex.FIFTH);
                    setShared('conditionSidebar5', true);
                } else {
                    $("#sidebar").ntsSideBar("disable", SideBarTabIndex.SECOND);
                    $("#sidebar").ntsSideBar("disable", SideBarTabIndex.THIRD);
                    $("#sidebar").ntsSideBar("disable", SideBarTabIndex.FOURTH);
                    $("#sidebar").ntsSideBar("disable", SideBarTabIndex.FIFTH);
                    setShared('conditionSidebar5', false);
                }
            }
            
            private condition(): void {
                let self = this;
                if (self.managePublicHoliday() == ManagePubHD.MANAGE) {
                    self.enableCarryOverDeadline(true);    
                    self.enablecarryOverNumberOfPublicHdIsNegative(true);
                    self.enablePubHDPeriod(true);       
                    self.isDisableSetUnitBtn(false)             
                } else {
                    self.enableCarryOverDeadline(false);    
                    self.enablecarryOverNumberOfPublicHdIsNegative(false);
                    self.enablePubHDPeriod(false);     
                    self.isDisableSetUnitBtn(true);
                }    
            }            
            
            private getAllEnum(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                $.when(service.getPubHDPeriodEnum(), service.getDayOfPubHDEnum(), service.getPublicHolidayCarryOverDeadline(),
                        service.getDaysOfTheWeek(), service.getManageEnum()).done(function(pubHDPeriodEnum: any, 
                                                                    dayOfPubHDEnum: any,                                                                    
                                                                    publicHolidayCarryOverDeadline: any, 
                                                                    daySOfTheWeek: any,
                                                                    manage: any) {
                    // todo: set enum
                    self.setPubHDPeriodEnum(pubHDPeriodEnum);
                    self.setDayOfPubHDEnum(dayOfPubHDEnum);
                    self.setPublicHolidayCarryOverDeadlineEnum(publicHolidayCarryOverDeadline);
                    self.setDaysOfTheWeekEnum(daySOfTheWeek);
                    self.setManageEnum(manage);
                    dfd.resolve();
                });    
                return dfd.promise();
            }
                
            private setPubHDPeriodEnum(pubHDPeriodEnum: any): void {
                let self = this;
                if (pubHDPeriodEnum.length > 0 && pubHDPeriodEnum.length != self.lstManagementPeriod.length) {
                    self.lstManagementPeriod.removeAll();
                    _.forEach(pubHDPeriodEnum, function(obj) {
                        self.lstManagementPeriod.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }
                
            private setDayOfPubHDEnum(dayOfPubHDEnum: any): void {
                let self = this;
                if (dayOfPubHDEnum.length > 0 && dayOfPubHDEnum.length != self.dayOfPublicHoliday.length) {
                  self.dayOfPublicHoliday.removeAll();
                    _.forEach(dayOfPubHDEnum, function(obj) {
                        self.dayOfPublicHoliday.push({"id":obj.value , "name":obj.localizedName, "enable":true});  
                    });                    
                }
            }
            private setPublicHolidayCarryOverDeadlineEnum(pubHDCarryOverDeadline: any): void {
                let self = this;
                if (pubHDCarryOverDeadline.length > 0 && pubHDCarryOverDeadline.length != self.lstCarryOverDeadline.length) {
                    self.lstCarryOverDeadline.removeAll();
                    _.forEach(pubHDCarryOverDeadline, function(obj) {
                        self.lstCarryOverDeadline.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }    
                
            private setDaysOfTheWeekEnum(daySOfTheWeek: any): void {
                let self = this;
                if (daySOfTheWeek.length > 0 && daySOfTheWeek.length != self.lstStartDayOfWeek.length) {
                    self.lstStartDayOfWeek.removeAll();
                    _.forEach(daySOfTheWeek, function(obj) {
                        self.lstStartDayOfWeek.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }    
            
            private setManageEnum(manage: any): void {
                let self = this;
                self.companyManageClassification.removeAll();
                    _.forEachRight(manage, function(obj) {
                        self.companyManageClassification.push({"id":obj.value , "name":obj.localizedName});  
                    });    
            }
            
            private settingOfUsageUnit(): void {
                let self = this;
                $.when(self.findAllManageUseUnit()).done(function() {
                    setShared('valScreenF', "valScreenF", true);
                    setShared('isManageEmpPublicHd', self.isManageEmpPublicHd());
                    setShared('isManageEmployeePublicHd', self.isManageEmployeePublicHd());
                    setShared('isManageWkpPublicHd', self.isManageWkpPublicHd());
                    nts.uk.ui.windows.sub.modal("/view/kmf/002/f/index.xhtml").onClosed(function() {
                        if (getShared('saveManageUnit') == true) {
                            // F2_6
                            self.isManageEmployeePublicHd(getShared('isManageEmployeePublicHd'));
                            // F2_5
                            self.isManageWkpPublicHd(getShared('isManageWkpPublicHd'));
                            // F2_4
                            self.isManageEmpPublicHd(getShared('isManageEmpPublicHd'));
                            self.isManageEmployeePublicHd.valueHasMutated();
                            self.isManageWkpPublicHd.valueHasMutated();
                            self.isManageEmpPublicHd.valueHasMutated();
                            $.when(service.saveManageUnit(self.isManageEmployeePublicHd(), self.isManageWkpPublicHd(), 
                                                            self.isManageEmpPublicHd())).done(function(data: any) {
                            });    
                        }
                    });    
                });
            } 
            
            private findAllManageUseUnit(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred<any>();
                $.when(service.findAllManageUseUnit()).done(function(data: any) {                     
                    self.isManageEmployeePublicHd(data.isManageEmployeePublicHd);
                    self.isManageWkpPublicHd(data.isManageWkpPublicHd);
                    self.isManageEmpPublicHd(data.isManageEmpPublicHd);
                    dfd.resolve(self);
                });
                return dfd.promise();    
            }
            
            
            // excle
           public opencdl028Dialog() {
                var self = this;
                let params = {
                    date: moment(new Date()).toDate(),
                    mode: 5
                };
    
                nts.uk.ui.windows.setShared("CDL028_INPUT", params);
    
                nts.uk.ui.windows.sub.modal("com", "/view/cdl/028/a/index.xhtml").onClosed(function() {
                    var params = nts.uk.ui.windows.getShared("CDL028_A_PARAMS");
                    console.log(params);
                    
                    if (params.status) {
                        let startDate = moment.utc(params.startDateFiscalYear ,"YYYY/MM/DD");
                        let endDate = moment.utc(params.endDateFiscalYear ,"YYYY/MM/DD") ;
                        self.exportExcel(params.mode, startDate, endDate);
                     }
                });
            }                                                           
        
            /**
             * Print file excel
             */
            exportExcel(mode: string, startDate: string, endDate: string) : void {
                var self = this;
                nts.uk.ui.block.grayout();
                service.saveAsExcel(mode, startDate, endDate).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            
            
       }
        
        // export class WeekHolidaySetting {
        //     inLegalHoliday: KnockoutObservable<number>;
        //     outLegalHoliday: KnockoutObservable<number>;
        //     startDay: KnockoutObservable<number>;
            
        //     constructor(inLegalHoliday:number, outLegalHoliday:number, startDay:number) {
        //         let self = this;
        //         self.inLegalHoliday = ko.observable(inLegalHoliday);
        //         self.outLegalHoliday = ko.observable(outLegalHoliday);
        //         self.startDay = ko.observable(startDay);
        //     }
        // }
        
        // export class ForwardSettingOfPublicHoliday {
        //     isTransferWhenPublicHdIsMinus: KnockoutObservable<any>;   
        //     carryOverDeadline: KnockoutObservable<number>; 
            
        //     constructor(isTransferWhenPublicHdIsMinus:any, carryOverDeadline:number) {
        //         let self = this;
        //         self.isTransferWhenPublicHdIsMinus = ko.observable(isTransferWhenPublicHdIsMinus);
        //         self.carryOverDeadline = ko.observable(carryOverDeadline);
        //     }
        // }
        
        // export class PublicHolidaySetting {
        //     isManageComPublicHd: KnockoutObservable<number>;
        //     publicHdManagementClassification: KnockoutObservable<number>;
        //     pubHDGrantDate: KnockoutObservable<PublicHolidayGrantDate>;
        //     pubHD: KnockoutObservable<PublicHoliday>;
        //     isWeeklyHdCheck: KnockoutObservable<any>;
            
        //     constructor(isManageComPublicHd:number, publicHdManagementClassification: number, 
        //                     pubHDGrantDate: PublicHolidayGrantDate,
        //                     pubHD: PublicHoliday, isWeeklyHdCheck: number) {
        //         let self = this;
                
        //         self.isManageComPublicHd = ko.observable(isManageComPublicHd);
        //         self.publicHdManagementClassification = ko.observable(publicHdManagementClassification);
        //         self.pubHDGrantDate = ko.observable(pubHDGrantDate);
        //         self.pubHD = ko.observable(pubHD);
        //         self.isWeeklyHdCheck = ko.observable(isWeeklyHdCheck);  
                
        //     }
        // }   
        
        // export class PublicHolidayManagementUsageUnit {
        //     isManageEmployeePublicHd: KnockoutObservable<number>;
        //     isManageWkpPublicHd: KnockoutObservable<number>;
        //     isManageEmpPublicHd: KnockoutObservable<number>;
            
        //     constructor(isManageEmployeePublicHd: number, isManageWkpPublicHd: number, isManageEmpPublicHd: number) {                
        //         let self = this;
        //         self.isManageEmployeePublicHd = ko.observable(isManageEmployeePublicHd);
        //         self.isManageWkpPublicHd = ko.observable(isManageWkpPublicHd);
        //         self.isManageEmpPublicHd = ko.observable(isManageEmpPublicHd);
        //     }
        // }
        
        // export interface PublicHolidayManagementStartDate {
        // }
        
        // export class PublicHolidayGrantDate implements PublicHolidayManagementStartDate{
        //     period: KnockoutObservable<number>;
            
        //     constructor(period: number) {                
        //         let self = this;
        //         self.period = ko.observable(period);
        //     }
        // }
        
//        export class PublicHoliday implements PublicHolidayManagementStartDate{
        // export class PublicHoliday{
        //     date: KnockoutObservable<string>;
        //     dayMonth: KnockoutObservable<string>;
        //     determineStartDate: KnockoutObservable<number>;
            
        //     constructor(date: string, dayMonth: string, determineStartDate: number) {                
        //         let self = this;
        //         self.date = ko.observable(date);
        //         self.dayMonth = ko.observable(dayMonth);
        //         self.determineStartDate = ko.observable(determineStartDate);
        //     }
        // }
        
        // export class LastWeekHolidayNumberOfFourWeek{
        //     inLegalHoliday: KnockoutObservable<number>;
        //     outLegalHoliday: KnockoutObservable<number>;
            
        //     constructor(inLegalHoliday: number, outLegalHoliday: number) {
        //         let self = this;
        //         self.inLegalHoliday = ko.observable(inLegalHoliday);
        //         self.outLegalHoliday = ko.observable(outLegalHoliday);
        //     }
        // }
        
        // export class FourWeekPublicHoliday{
        //     inLegalHoliday: KnockoutObservable<number>;
        //     outLegalHoliday: KnockoutObservable<number>;
        //     lastWeekAddedDays: KnockoutObservable<LastWeekHolidayNumberOfFourWeek>;
            
        //     constructor(inLegalHoliday: number, outLegalHoliday: number, lastWeekAddedDays: LastWeekHolidayNumberOfFourWeek) {
        //         let self = this;
        //         self.inLegalHoliday = ko.observable(inLegalHoliday);
        //         self.outLegalHoliday = ko.observable(outLegalHoliday);
        //         self.lastWeekAddedDays = ko.observable(lastWeekAddedDays);
        //     }
        // }
        // f
        // export class LastWeekHolidayNumberOfOneWeek{
        //     inLegalHoliday: KnockoutObservable<number>;
        //     outLegalHoliday: KnockoutObservable<number>;
            
        //     constructor(inLegalHoliday: number, outLegalHoliday: number) {
        //         let self = this;
        //         self.inLegalHoliday = ko.observable(inLegalHoliday);
        //         self.outLegalHoliday = ko.observable(outLegalHoliday);
        //     }
        // }
        
        // export class OneWeekPublicHoliday{
        //     inLegalHoliday: KnockoutObservable<number>;
        //     outLegalHoliday: KnockoutObservable<number>;
        //     lastWeekAddedDays: KnockoutObservable<LastWeekHolidayNumberOfOneWeek>;
            
        //     constructor(inLegalHoliday: number, outLegalHoliday: number, lastWeekAddedDays: LastWeekHolidayNumberOfOneWeek) {
        //         let self = this;
        //         self.inLegalHoliday = ko.observable(inLegalHoliday);
        //         self.outLegalHoliday = ko.observable(outLegalHoliday);
        //         self.lastWeekAddedDays = ko.observable(lastWeekAddedDays);
        //     }
        // }
        
        // export class FourWeekFourHolidayNumberSetting {
        //     isOneWeekHoliday: KnockoutObservable<any>;
        //     oneWeek: KnockoutObservable<OneWeekPublicHoliday>;
        //     isFourWeekHoliday: KnockoutObservable<any>;
        //     fourWeek: KnockoutObservable<FourWeekPublicHoliday>;
            
        //     constructor(isOneWeekHoliday: any, oneWeek: OneWeekPublicHoliday, isFourWeekHoliday: any, fourWeek: FourWeekPublicHoliday) {
        //         let self = this;
        //         self.isOneWeekHoliday = ko.observable(isOneWeekHoliday);
        //         self.oneWeek = ko.observable(oneWeek);
        //         self.isFourWeekHoliday = ko.observable(isFourWeekHoliday);
        //         self.fourWeek = ko.observable(fourWeek);
        //     } 
        // }
        
        export enum ManagePubHD {
            MANAGE = 1,
            NOT_MANAGE = 0,
        }
        
        export enum PublicHDPeriod {
            ONE_MONTH = 0,
            FOUR_WEEK_FOUR_DAYOFF = 1
        }
        
        export enum BoolValue {
            TRUE = 1,
            FALSE = 0    
        }
        
        export enum TypeDate {
            YEAR_MONTH_DAY = 0,
            MONTH_DAY = 1    
        }
    }
    
    module SideBarTabIndex {
        export const FIRST = 0;                        
        export const SECOND = 1;
        export const THIRD = 2;
        export const FOURTH = 3;
        export const FIFTH = 4;
    }
}