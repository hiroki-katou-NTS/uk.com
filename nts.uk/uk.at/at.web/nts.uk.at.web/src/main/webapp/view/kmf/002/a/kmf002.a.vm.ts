module nts.uk.at.view.kmf002.a {
    
    import viewModelTabB = nts.uk.at.view.kmf002.b.viewmodel;
    import viewModelTabC = nts.uk.at.view.kmf002.c.viewmodel;
    import viewModelTabD = nts.uk.at.view.kmf002.d.viewmodel;
    import viewModelTabA = nts.uk.at.view.kmf002.a;
    import viewModelTabE = nts.uk.at.view.kmf002.e.viewmodel;
    
    import viewModelTabF = nts.uk.at.view.kmf002.f.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    import service = nts.uk.at.view.kmf002.a.service;
    
    export module viewmodel {
        export class ScreenModel {
            
            screenB: KnockoutObservable<any>;
            screenC: KnockoutObservable<any>;
            screenD: KnockoutObservable<any>;
            screenE: KnockoutObservable<any>;
            
            /* main define variable code */
            publicHolidaySetting: KnockoutObservable<PublicHolidaySetting>;
            forwardSetOfPubHD: KnockoutObservable<ForwardSettingOfPublicHoliday>;
            weekHDSet: KnockoutObservable<WeekHolidaySetting>;
            fourWkFourHDNumSet: KnockoutObservable<FourWeekFourHolidayNumberSetting>;
            companyManageClassification: KnockoutObservableArray<any>;
            lstManagementPeriod: KnockoutObservableArray<any>;
            publicHDPeriod: KnockoutObservableArray<any>;
            dayOfPublicHoliday: KnockoutObservableArray<any>;
            
            // TODO: tao san constraint cho A9_6 va 9_7 de switch
//            constraintDayOfPublicHD: KnockoutObservable<string>;
            enableFullDate: KnockoutObservable<boolean>;
            lstCarryoverDeadline: KnockoutObservableArray<any>;
            
            lstStartDayOfWeek: KnockoutObservableArray<any>; 
            
            isDisableSetUnitBtn: KnockoutObservable<boolean>;
            enableCarryoverDeadline: KnockoutObservable<boolean>;
            enableTransferWhenPublicHdIsMinus: KnockoutObservable<boolean>;
            enablePublicHdManagementClassification: KnockoutObservable<boolean>;
            enablePubHDGrantDatePeriod: KnockoutObservable<boolean>;
            enableDetermineStartDate: KnockoutObservable<boolean>;
            enablePubHDDateAndDayMonth: KnockoutObservable<boolean>;
            enableIsWeeklyHdCheck: KnockoutObservable<boolean>;
            enableStartDay: KnockoutObservable<boolean>;
            enableWeekHDSetInLegalHoliday: KnockoutObservable<boolean>;
            enableWeekHDSetOutLegalHoliday: KnockoutObservable<boolean>;
            enable4Weeks4HDSet: KnockoutObservable<boolean>;
            enableOneWeekPubHDInLegalHD: KnockoutObservable<boolean>;
            enableOneWeekPubHDOutLegalHD: KnockoutObservable<boolean>;
            enableLastPeriod1WeekPubHD: KnockoutObservable<boolean>;
            enable4WeeksPubHDInLegalHD: KnockoutObservable<boolean>;
            enable4WeeksPubHDOutLegalHD: KnockoutObservable<boolean>;
            enableLastPeriod4WeekPubHD: KnockoutObservable<boolean>;
            
            constructor(){
                let _self = this;
//                _self.screenA = ko.observable(new viewModelTabA.viewmodel.ScreenModel());
                _self.screenC = ko.observable(new viewModelTabC.ScreenModel());
                _self.screenD = ko.observable(new viewModelTabD.ScreenModel());
                _self.screenB = ko.observable(new viewModelTabB.ScreenModel());
                _self.screenE = ko.observable(new viewModelTabE.ScreenModel());
                
                _self.isDisableSetUnitBtn = ko.observable(false);
                _self.enableCarryoverDeadline = ko.observable(true);
                _self.enableTransferWhenPublicHdIsMinus = ko.observable(true);
                _self.enablePublicHdManagementClassification = ko.observable(true);
                _self.enablePubHDGrantDatePeriod = ko.observable(true);
                _self.enableDetermineStartDate = ko.observable(true);
                _self.enablePubHDDateAndDayMonth = ko.observable(true);
                _self.enableIsWeeklyHdCheck = ko.observable(true);
                _self.enableStartDay = ko.observable(true);
                _self.enableWeekHDSetInLegalHoliday = ko.observable(true);
                _self.enableWeekHDSetOutLegalHoliday = ko.observable(true);
                _self.enable4Weeks4HDSet = ko.observable(true);
                _self.enableOneWeekPubHDInLegalHD = ko.observable(true); 
                _self.enableOneWeekPubHDOutLegalHD = ko.observable(true);
                _self.enableLastPeriod1WeekPubHD = ko.observable(true);
                _self.enable4WeeksPubHDInLegalHD = ko.observable(true);
                _self.enable4WeeksPubHDOutLegalHD = ko.observable(true);
                _self.enableLastPeriod4WeekPubHD = ko.observable(true);
                
                /** 
                  *    main define variable code 
                **/
                _self.publicHolidaySetting = ko.observable(null);
                _self.forwardSetOfPubHD = ko.observable(null);
                _self.weekHDSet = ko.observable(null);
                _self.fourWkFourHDNumSet = ko.observable(null);
                
                _self.companyManageClassification = ko.observableArray();
                _self.publicHDPeriod = ko.observableArray();
                _self.lstManagementPeriod = ko.observableArray();
                _self.dayOfPublicHoliday = ko.observableArray();
                _self.enableFullDate = ko.observable(false);
                _self.lstCarryoverDeadline = ko.observableArray();
                _self.lstStartDayOfWeek = ko.observableArray();
            }
            
            public start_page(typeStart: number): JQueryPromise<any> {
                
                var dfd = $.Deferred<any>();
                
                let _self = this;
                
                // load all
                if (typeStart == 3) {
                    $.when(_self.screenB().start_page()).done(function() {
                        dfd.resolve(_self);
                    });    
                } else if (typeStart == 1) {
                    // Process for screen A (Mother of all screen)
//                    _self.getAllEnum();
//                    _self.getAllData()
//                    dfd.resolve(_self);    
                    
                    $.when(_self.getAllEnum(), _self.getAllData()).done(function() {
                        dfd.resolve(_self);    
                    });
                } else if (typeStart == 2) {
                    $.when(_self.screenE().start_page()).done(function() {
                        dfd.resolve(_self);
                    });    
                } else if (typeStart == 4) {
                    $.when(_self.screenD().start_page()).done(function() {
                        dfd.resolve(_self);
                    });    
                } else if (typeStart == 5) {
                    $.when(_self.screenC().start_page()).done(function() {
                        dfd.resolve(_self);
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
                        let _self = this;
                        _self.start_page(1);
                    }
                });
            }
            
            public onSelectTabB(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.THIRD,
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page(3);
                    }
                });
            }
            
            public onSelectTabC(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIFTH,
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page(5);
                    }
                });
            }
            
            public onSelectTabD(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FOURTH,
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page(4);
                    }
                });
            }
            
            public onSelectTabE(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.SECOND,
                    activate: (event, info) => {
                        let _self = this;
                        _self.start_page(2);
                    }
                });
            }
            
            
            /**
             *  process for screen A
             */
            public DataServiceForDto(): void {
                let _self = this;
//                let publicHoliday = new viewModelTabA.PublicHoliday(12192017, 1219, viewModelTabA.DayOfPublicHoliday.designateByYearMonthDay);
//                let publicHolidayManagementUsageUnit = new viewModelTabA.PublicHolidayManagementUsageUnit(true, false, true);
//                let publicHolidayManagementStartDate = new viewModelTabA.PublicHolidayGrantDate(viewModelTabA.PublicHolidayPeriod.closurePeriod);
//                _self.publicHolidaySetting = new PublicHolidaySetting(true, publicHolidayManagementUsageUnit, 
//                                                                                            viewModelTabA.PublicHolidayManagementClassification._1Month,
//                                                                                            publicHolidayManagementStartDate, true );
//                
//                
//                let pubHDSet = new PublicHolidaySetting(true, publicHolidayManagementUsageUnit, 
//                                                                                            viewModelTabA.PublicHolidayManagementClassification._1Month,
//                                                                                            publicHolidayManagementStartDate, true );
            }
            
            private getAllData(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred();
//                $.when(service.findAll()).done(function(data: any) {
//                    // todo: get data from result service
//                    let isManageComPublicHd:number = 1;
//                    let publicHdManagementClassification:number = 1;
//                    let isWeeklyHdCheck:number = 1;
//                    let publicHdManagementUsageUnit: PublicHolidayManagementUsageUnit = new PublicHolidayManagementUsageUnit(1, 1, 1);
//                    let pubHDGrantDate:PublicHolidayManagementStartDate = new PublicHolidayGrantDate(1);
//                    let pubHD:PublicHolidayManagementStartDate = new PublicHoliday(2011, 201102011, 1);
//                    _self.publicHolidaySetting(new PublicHolidaySetting(isManageComPublicHd, 
//                                                                                    publicHdManagementUsageUnit,
//                                                                                    publicHdManagementClassification, 
//                                                                                    pubHDGrantDate,
//                                                                                    pubHD, 
//                                                                                    isWeeklyHdCheck));
//                    dfd.resolve();    
//                });
                
                // For domain publicHolidaySetting
                    let isManageComPublicHd:number = 1;     // default is 1: manage
                    let publicHdManagementClassification:number = 0;
                    let isWeeklyHdCheck:number = 0;     // default is 0
                    let publicHdManagementUsageUnit: PublicHolidayManagementUsageUnit = new PublicHolidayManagementUsageUnit(1, 1, 1);
                    let pubHDGrantDate:PublicHolidayGrantDate = new PublicHolidayGrantDate(0);
                    let pubHD:PublicHoliday = new PublicHoliday(2011, 201102011, 1);
                    _self.publicHolidaySetting(new PublicHolidaySetting(isManageComPublicHd, 
                                                                                    publicHdManagementUsageUnit,
                                                                                    publicHdManagementClassification, 
                                                                                    pubHDGrantDate,
                                                                                    pubHD, 
                                                                                    isWeeklyHdCheck));
                
                    // start subscribe
                    _self.publicHolidaySetting().pubHD().determineStartDate.subscribe(function(newValue) {
                        _self.condition1And2();
                        _self.condition7();
                        _self.condition8();
                    });
                
                    _self.publicHolidaySetting().publicHdManagementClassification.subscribe(function(newValue) {
                        // setting for button Setting Unit
                        if (newValue == 0 && _self.publicHolidaySetting().isManageComPublicHd() == 1) {
                            _self.isDisableSetUnitBtn(true);
                        } else {
                            _self.isDisableSetUnitBtn(false);
                        }
                        _self.condition3And4();
                        _self.condition5();
                        _self.condition6();
                        _self.condition7();
                        _self.condition8();
                    });
                
                    
                
                    _self.publicHolidaySetting().isManageComPublicHd.subscribe(function(newValue) {
                        _self.condition3And4();
                        _self.condition5();
                        _self.condition6();
                        _self.condition9();
                        _self.condition7();
                        _self.condition8();
                    });
                
                // end subscribe
                
                    _self.publicHolidaySetting().isManageComPublicHd.valueHasMutated();
                    _self.publicHolidaySetting().publicHdManagementClassification.valueHasMutated();
                    _self.publicHolidaySetting().pubHD().determineStartDate.valueHasMutated();
                    
                
                // For domain ForwardSettingOfPublicHoliday 
                let isTransferWhenPublicHdIsMinus:number = 0;   
                let carryOverDeadline:number = 0; 
                _self.forwardSetOfPubHD(new ForwardSettingOfPublicHoliday(isTransferWhenPublicHdIsMinus, carryOverDeadline));
                
                
                // For domain WeekHolidaySetting
                let inLegalHoliday:number = 0;
                let outLegalHoliday:number = 0;
                let startDay:number = 0;        // 0 is default
                _self.weekHDSet(new WeekHolidaySetting(inLegalHoliday, outLegalHoliday, startDay));
                
                // For domain FourWeekFourHolidayNumberSetting
                let isOneWeekHoliday:any = 0;   // 0 is default
                let oneWeek:OneWeekPublicHoliday= new OneWeekPublicHoliday(0, 0, new LastWeekHolidayNumberOfOneWeek(0, 0)) ;
                let isFourWeekHoliday:any = 0;
                let fourWeek:FourWeekPublicHoliday = new FourWeekPublicHoliday(0, 0, new LastWeekHolidayNumberOfFourWeek(0, 0));
                
                _self.fourWkFourHDNumSet(new FourWeekFourHolidayNumberSetting(isOneWeekHoliday, oneWeek, isFourWeekHoliday, fourWeek));
                
                _self.fourWkFourHDNumSet().isOneWeekHoliday.subscribe(function(newValue) {
                    _self.condition5();
                    _self.condition7();
                });
            
                _self.fourWkFourHDNumSet().isFourWeekHoliday.subscribe(function(newValue) {
                    _self.condition6();
                    _self.condition8();
                });
                
                _self.fourWkFourHDNumSet().isOneWeekHoliday.valueHasMutated();
                _self.fourWkFourHDNumSet().isFourWeekHoliday.valueHasMutated();
                
                dfd.resolve(); 
                return dfd.promise();
            }
            
            private condition7(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 1
                        && (_self.fourWkFourHDNumSet().isOneWeekHoliday() == true || _self.fourWkFourHDNumSet().isOneWeekHoliday() == 1)
                        && _self.publicHolidaySetting().pubHD().determineStartDate() == 1) {
                    _self.enableLastPeriod1WeekPubHD(true);
                } else {
                    _self.enableLastPeriod1WeekPubHD(false);
                }    
            }
            
            private condition8(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 1
                        && (_self.fourWkFourHDNumSet().isFourWeekHoliday() == true || _self.fourWkFourHDNumSet().isFourWeekHoliday() == 1)
                        && _self.publicHolidaySetting().pubHD().determineStartDate() == 1) {
                    _self.enableLastPeriod4WeekPubHD(true);
                } else {
                    _self.enableLastPeriod4WeekPubHD(false);
                }    
            }
            
            private condition6(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 1 
                        && (_self.fourWkFourHDNumSet().isFourWeekHoliday() == true || _self.fourWkFourHDNumSet().isFourWeekHoliday() == 1)) {
                    _self.enable4WeeksPubHDInLegalHD(true);
                    _self.enable4WeeksPubHDOutLegalHD(true);    
                } else {
                    _self.enable4WeeksPubHDInLegalHD(false);
                    _self.enable4WeeksPubHDOutLegalHD(false);    
                }  
            }
            
            private condition1And2(): void {
                let _self = this;
                if (_self.publicHolidaySetting().pubHD().determineStartDate() == 0) {
                    _self.enableFullDate(true);
                } else {
                    _self.enableFullDate(false);
                }    
            }
            
            private condition9(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1) {
                    _self.enableCarryoverDeadline(true);    
                    _self.enableTransferWhenPublicHdIsMinus(true);
                    _self.enablePublicHdManagementClassification(true);
                    _self.enableIsWeeklyHdCheck(true);
                    _self.enableStartDay(true)
                    _self.enableWeekHDSetInLegalHoliday(true);
                    _self.enableWeekHDSetOutLegalHoliday(true);
                } else {
                    _self.enableCarryoverDeadline(false);
                    _self.enableTransferWhenPublicHdIsMinus(false);
                    _self.enablePublicHdManagementClassification(false);
                    _self.enableIsWeeklyHdCheck(false);
                    _self.enableStartDay(false);
                    _self.enableWeekHDSetInLegalHoliday(false);
                    _self.enableWeekHDSetOutLegalHoliday(false);
                }    
            }
            
            private condition5(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 1 
                        && (_self.fourWkFourHDNumSet().isOneWeekHoliday() == true || _self.fourWkFourHDNumSet().isOneWeekHoliday() == 1)) {
                    _self.enableOneWeekPubHDInLegalHD(true);
                    _self.enableOneWeekPubHDOutLegalHD(true);    
                } else {
                    _self.enableOneWeekPubHDInLegalHD(false);
                    _self.enableOneWeekPubHDOutLegalHD(false);    
                }
            }
            
            private condition3And4(): void {
                let _self = this;
                // condition 3
                if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 0) {
                    _self.isDisableSetUnitBtn(true);
                    _self.enablePubHDGrantDatePeriod(true);
                    _self.enableDetermineStartDate(false);
                    _self.enablePubHDDateAndDayMonth(false);
                    _self.enable4Weeks4HDSet(false);
                }
                // condtion 4
                else if (_self.publicHolidaySetting().isManageComPublicHd() == 1 && _self.publicHolidaySetting().publicHdManagementClassification() == 1) {
                    _self.isDisableSetUnitBtn(false);
                    _self.enablePubHDGrantDatePeriod(false);
                    _self.enableDetermineStartDate(true);
                    _self.enablePubHDDateAndDayMonth(true);
                    _self.enable4Weeks4HDSet(true);
                } 
                // another    
                else {
                    _self.isDisableSetUnitBtn(false);
                    _self.enablePubHDGrantDatePeriod(false);
                    _self.enableDetermineStartDate(false);
                    _self.enablePubHDDateAndDayMonth(false);
                    _self.enable4Weeks4HDSet(false);
                }
            }
            
            private getAllEnum(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred();
                $.when(service.getPubHDPeriodEnum(), service.getDayOfPubHDEnum(),
                        service.getPubHDManageClassificationEnum(), service.getPublicHolidayCarryOverDeadline(),
                        service.getDaysOfTheWeek()).done(function(pubHDPeriodEnum: any, 
                                                                    dayOfPubHDEnum: any,
                                                                    pubHDManageClassificationEnum: any, 
                                                                    publicHolidayCarryOverDeadline: any, 
                                                                    daySOfTheWeek: any) {
                    // todo: set enum
                    _self.setPubHDPeriodEnum(pubHDPeriodEnum);
                    _self.setDayOfPubHDEnum(dayOfPubHDEnum);
                    _self.setPubHDManageClassificationEnum(pubHDManageClassificationEnum);
                    _self.setPublicHolidayCarryOverDeadline(publicHolidayCarryOverDeadline);
                    _self.setDaysOfTheWeek(daySOfTheWeek);
                    _self.setManage();
                    dfd.resolve();
                });    
                return dfd.promise();
            }
                
            private setPubHDPeriodEnum(pubHDPeriodEnum: any): void {
                let _self = this;
                _.forEach(pubHDPeriodEnum, function(obj) {
                    _self.lstManagementPeriod.push({"code":obj.value , "name":obj.localizedName});  
                });
            }
                
            private setDayOfPubHDEnum(dayOfPubHDEnum: any): void {
                let _self = this;
                _.forEach(dayOfPubHDEnum, function(obj) {
                    _self.dayOfPublicHoliday.push({"id":obj.value , "name":obj.localizedName, "enable":true});  
                });
            }
                
            private setPubHDManageClassificationEnum(pubHDManageClassificationEnum: any): void {
                let _self = this;
                _.forEach(pubHDManageClassificationEnum, function(obj) {
                    _self.publicHDPeriod.push({"id":obj.value , "name":obj.localizedName, "enable":true});  
                });
            }
                
            private setPublicHolidayCarryOverDeadline(pubHDCarryOverDeadline: any): void {
                let _self = this;
                _.forEach(pubHDCarryOverDeadline, function(obj) {
                    _self.lstCarryoverDeadline.push({"code":obj.value , "name":obj.localizedName});  
                });
            }    
                
            private setDaysOfTheWeek(daySOfTheWeek: any): void {
                let _self = this;
                _.forEach(daySOfTheWeek, function(obj) {
                    _self.lstStartDayOfWeek.push({"code":obj.value , "name":obj.localizedName});  
                });
            }    
            
            private setManage(): void {
                let _self = this;
                _self.companyManageClassification.push({"id": 0, "name": CompanyManagementClassification.NOT_MANAGE,"enable": true});
                _self.companyManageClassification.push({"id": 1, "name": CompanyManagementClassification.MANAGE,"enable":true});    
            }
            
            
            private settingOfUsageUnit(): void {
                var self = this;
                setShared('valScreenF', "valScreenF", true);
                nts.uk.ui.windows.sub.modal("/view/kmf/002/f/index.xhtml").onClosed(function() {
//                    console.log("close screen F");
                });
            }
           
       }
        
        
        export class WeekHolidaySetting {
            inLegalHoliday: KnockoutObservable<number>;
            outLegalHoliday: KnockoutObservable<number>;
            startDay: KnockoutObservable<number>;
            
            constructor(inLegalHoliday:number, outLegalHoliday:number, startDay:number) {
                let _self = this;
                _self.inLegalHoliday = ko.observable(inLegalHoliday);
                _self.outLegalHoliday = ko.observable(outLegalHoliday);
                _self.startDay = ko.observable(startDay);
            }
        }
        
        export class ForwardSettingOfPublicHoliday {
            isTransferWhenPublicHdIsMinus: KnockoutObservable<number>;   
            carryOverDeadline: KnockoutObservable<number>; 
            
            constructor(isTransferWhenPublicHdIsMinus:number, carryOverDeadline:number) {
                let _self = this;
                _self.isTransferWhenPublicHdIsMinus = ko.observable(isTransferWhenPublicHdIsMinus);
                _self.carryOverDeadline = ko.observable(carryOverDeadline);
            }
        }
        
        export class PublicHolidaySetting {
            isManageComPublicHd: KnockoutObservable<number>;
            publicHdManagementUsageUnit: KnockoutObservable<PublicHolidayManagementUsageUnit>;
            publicHdManagementClassification: KnockoutObservable<number>;
            pubHDGrantDate: KnockoutObservable<PublicHolidayManagementStartDate>;
            pubHD: KnockoutObservable<PublicHoliday>;
            isWeeklyHdCheck: KnockoutObservable<any>;
            
            constructor(isManageComPublicHd:number, publicHdManagementUsageUnit: PublicHolidayManagementUsageUnit,
                        publicHdManagementClassification: number, pubHDGrantDate: PublicHolidayManagementStartDate,
                        pubHD: PublicHoliday, isWeeklyHdCheck: number) {
                let _self = this;
                
                _self.isManageComPublicHd = ko.observable(isManageComPublicHd);
                _self.publicHdManagementUsageUnit = ko.observable(publicHdManagementUsageUnit);
                _self.publicHdManagementClassification = ko.observable(publicHdManagementClassification);
                _self.pubHDGrantDate = ko.observable(pubHDGrantDate);
                _self.pubHD = ko.observable(pubHD);
                _self.isWeeklyHdCheck = ko.observable(isWeeklyHdCheck);  
                
            }
        }   
        
        export class PublicHolidayManagementUsageUnit {
            isManageEmployeePublicHd: KnockoutObservable<number>;
            isManageWkpPublicHd: KnockoutObservable<number>;
            isManageEmpPublicHd: KnockoutObservable<number>;
            
            constructor(isManageEmployeePublicHd: number, isManageWkpPublicHd: number, isManageEmpPublicHd: number) {                
                let _self = this;
                _self.isManageEmployeePublicHd = ko.observable(isManageEmployeePublicHd);
                _self.isManageWkpPublicHd = ko.observable(isManageWkpPublicHd);
                _self.isManageEmpPublicHd = ko.observable(isManageEmpPublicHd);
            }
        }
        
        export interface PublicHolidayManagementStartDate {
        }
        
        export class PublicHolidayGrantDate implements PublicHolidayManagementStartDate{
            period: KnockoutObservable<number>;
            
            constructor(period: number) {                
                let _self = this;
                _self.period = ko.observable(period);
            }
        }
        
//        export class PublicHoliday implements PublicHolidayManagementStartDate{
        export class PublicHoliday{
            date: KnockoutObservable<number>;
            dayMonth: KnockoutObservable<number>;
            determineStartDate: KnockoutObservable<number>;
            
            constructor(date: number, dayMonth: number, determineStartDate: number) {                
                let _self = this;
                _self.date = ko.observable(date);
                _self.dayMonth = ko.observable(dayMonth);
                _self.determineStartDate = ko.observable(determineStartDate);
            }
        }
        
//        export enum PublicHolidayPeriod_old {
//            CLOSURE_PERIOD = "締め期間",
//            FIRST_DAY_TO_LAST_DAY = "１日～末日",
//        }
        
//        export enum DayOfPublicHoliday_old {
//            DESIGNATE_BY_YEAR_MONTH_DAY = "年月日で指定する",
//            DESIGNATE_BY_MONTH_DAY ="月日で指定する",
//        }
        
//        export enum PublicHolidayManagementClassification_old {
//            _1MONTH = "1カ月の日数を管理する",
//            _4WEEKS_4DAYS_OFF = "4週4休を管理する",
//        }
        
//        export enum PublicHolidayCarryOverDeadline_old{
//            _1_MONTH = "1ヶ月",
//            _2_MONTH = "2ヶ月",
//            _3_MONTH = "3ヶ月",
//            _4_MONTH = "4ヶ月",
//            _5_MONTH = "5ヶ月",
//            _6_MONTH = "6ヶ月",
//            _7_MONTH = "7ヶ月",
//            _8_MONTH = "8ヶ月",
//            _9_MONTH = "9ヶ月",
//            _10_MONTH = "10ヶ月",
//            _11_MONTH = "11ヶ月",
//            _12_MONTH = "12ヶ月",
//            YEAR_END = "年度末",
//            INDEFINITE = "無期限",
//            CURRENT_MONTH = "当月",
//        }
        
//        export enum DaySOfTheWeek_old {
//            MONDAY = "月曜日",
//            TUESDAY = "火曜日",
//            WEDNESDAY = "水曜日",
//            THURSDAY = "木曜日",
//            FRIDAY = "金曜日",
//            SATURDAY = "土曜日",
//            SUNDAY = "日曜日"
//        }
        
        export class LastWeekHolidayNumberOfFourWeek{
            inLegalHoliday: KnockoutObservable<number>;
            outLegalHoliday: KnockoutObservable<number>;
            
            constructor(inLegalHoliday: number, outLegalHoliday: number) {
                let _self = this;
                _self.inLegalHoliday = ko.observable(inLegalHoliday);
                _self.outLegalHoliday = ko.observable(outLegalHoliday);
            }
        }
        
        export class FourWeekPublicHoliday{
            inLegalHoliday: KnockoutObservable<number>;
            outLegalHoliday: KnockoutObservable<number>;
            lastWeekAddedDays: KnockoutObservable<LastWeekHolidayNumberOfFourWeek>;
            
            constructor(inLegalHoliday: number, outLegalHoliday: number, lastWeekAddedDays: LastWeekHolidayNumberOfFourWeek) {
                let _self = this;
                _self.inLegalHoliday = ko.observable(inLegalHoliday);
                _self.outLegalHoliday = ko.observable(outLegalHoliday);
                _self.lastWeekAddedDays = ko.observable(lastWeekAddedDays);
            }
        }
        f
        export class LastWeekHolidayNumberOfOneWeek{
            inLegalHoliday: KnockoutObservable<number>;
            outLegalHoliday: KnockoutObservable<number>;
            
            constructor(inLegalHoliday: number, outLegalHoliday: number) {
                let _self = this;
                _self.inLegalHoliday = ko.observable(inLegalHoliday);
                _self.outLegalHoliday = ko.observable(outLegalHoliday);
            }
        }
        
        export class OneWeekPublicHoliday{
            inLegalHoliday: KnockoutObservable<number>;
            outLegalHoliday: KnockoutObservable<number>;
            lastWeekAddedDays: KnockoutObservable<LastWeekHolidayNumberOfOneWeek>;
            
            constructor(inLegalHoliday: number, outLegalHoliday: number, lastWeekAddedDays: LastWeekHolidayNumberOfOneWeek) {
                let _self = this;
                _self.inLegalHoliday = ko.observable(inLegalHoliday);
                _self.outLegalHoliday = ko.observable(outLegalHoliday);
                _self.lastWeekAddedDays = ko.observable(lastWeekAddedDays);
            }
        }
        
        export class FourWeekFourHolidayNumberSetting {
            isOneWeekHoliday: KnockoutObservable<any>;
            oneWeek: KnockoutObservable<OneWeekPublicHoliday>;
            isFourWeekHoliday: KnockoutObservable<any>;
            fourWeek: KnockoutObservable<FourWeekPublicHoliday>;
            
            constructor(isOneWeekHoliday: any, oneWeek: OneWeekPublicHoliday, isFourWeekHoliday: any, fourWeek: FourWeekPublicHoliday) {
                let _self = this;
                _self.isOneWeekHoliday = ko.observable(isOneWeekHoliday);
                _self.oneWeek = ko.observable(oneWeek);
                _self.isFourWeekHoliday = ko.observable(isFourWeekHoliday);
                _self.fourWeek = ko.observable(fourWeek);
            } 
        }
    }
    
    module SideBarTabIndex {
        export const FIRST = 0;                        
        export const SECOND = 1;
        export const THIRD = 2;
        export const FOURTH = 3;
        export const FIFTH = 4;
    }
    
//    module CompanyManagementClassification {
//        export const MANAGE = "管理する";
//        export const NOT_MANAE = "管理しない";
//    }
    
    module CompanyManagementClassification {
        export const MANAGE = "管理する";
        export const NOT_MANAGE = "管理しない";
    }
    
    module PublicHolidayPeriod_old {
        export const DAY_IN_MONTH = "1ヶ月の日数を管理する";
        export const FOUR_DAY_FOR_FOUR_WEEK = "4週4休を管理する";
    }
    
    module TypeDate {
        export const FULL_DATE = "年月日を指定する";
        export const MONTH_DAY = "月日を指定する";
    }
    
    export module ObjDomain {
        
        
    }
}