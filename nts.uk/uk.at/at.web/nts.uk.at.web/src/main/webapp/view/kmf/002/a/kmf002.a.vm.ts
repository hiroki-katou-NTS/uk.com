module nts.uk.at.view.kmf002.a {
    
    import viewModelTabB = nts.uk.at.view.kmf002.b.viewmodel;
    import viewModelTabC = nts.uk.at.view.kmf002.c.viewmodel;
    import viewModelTabD = nts.uk.at.view.kmf002.d.viewmodel;
    import viewModelTabA = nts.uk.at.view.kmf002.a;
    import viewModelTabE = nts.uk.at.view.kmf002.e.viewmodel;
    
    import viewModelTabF = nts.uk.at.view.kmf002.f.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    
    export module viewmodel {
        export class ScreenModel {
            
            screenB: KnockoutObservable<any>;
            screenC: KnockoutObservable<any>;
            screenD: KnockoutObservable<any>;
            screenE: KnockoutObservable<any>;
            
            A9_6_7: KnockoutObservable<string>;
            
            /* main define variable code */
            publicHolidaySetting: KnockoutObservable<viewModelTabA.ObjDomain.PublicHolidaySetting>;
            companyManageClassification: KnockoutObservableArray<any>;
            valDefaultcompanyManageClassification: KnockoutObservable<number>;
            valDefaultpublicHDPeriod: KnockoutObservable<number>;
            lstManagementPeriod: KnockoutObservableArray<any>;
            publicHDPeriod: KnockoutObservableArray<any>;
            selectedManagementPeriod: KnockoutObservable<number>;
            dayOfPublicHoliday: KnockoutObservableArray<any>;
            valDefaultDayOfPublicHoliday: KnockoutObservable<number>;
            
            // TODO: tao san constraint cho A9_6 va 9_7 de switch
            constraintDayOfPublicHD: KnockoutObservable<string>;
            
            isWeeklyHdCheck: KnockoutObservable<boolean>;
            isTransferWhenPublicHdIsMinus: KnockoutObservable<boolean>;
            
            lstCarryoverDeadline: KnockoutObservableArray<any>;
            selectedCodeCarryoverDeadline: KnockoutObservable<number>;
            
            lstStartDayOfWeek: KnockoutObservableArray<any>;
            selectedStartDayOfWeek: KnockoutObservable<number>;
            
            inLegalHDOfWeekHDSetting: KnockoutObservable<number>;
            outLegalHDOfWeekHDSetting: KnockoutObservable<number>;
            
            isOneWeekHoliday: KnockoutObservable<boolean>;
            isFourWeekHoliday: KnockoutObservable<boolean>;
            
            isPublicHDForMonth: KnockoutObservable<boolean>;
            isPublicHDFor4Week: KnockoutObservable<boolean>;
            
            enable4Weeks4HDSet: KnockoutObservable<boolean>;
            enableLastPeriod4Weeks4HDSet: KnockoutObservable<boolean>;
            
            inLegalHD_FWPH: KnockoutObservable<number>;
            outLegalHD_FWPH: KnockoutObservable<number>;
            inLegalHD_LWHNOFW: KnockoutObservable<number>;
            outLegalHD_LWHNOFW: KnockoutObservable<number>;
            
            inLegalHD_OWPH: KnockoutObservable<number>;
            outLegalHD_OWPH: KnockoutObservable<number>;
            inLegalHD_LWHNOOW: KnockoutObservable<number>;
            outLegalHD_LWHNOOW: KnockoutObservable<number>;
            
            constructor(){
                let _self = this;
//                _self.screenA = ko.observable(new viewModelTabA.viewmodel.ScreenModel());
                _self.screenC = ko.observable(new viewModelTabC.ScreenModel());
                _self.screenD = ko.observable(new viewModelTabD.ScreenModel());
                _self.screenB = ko.observable(new viewModelTabB.ScreenModel());
                _self.screenE = ko.observable(new viewModelTabE.ScreenModel());

                _self.A9_6_7 = ko.observable();
                
                /** 
                  *    main define variable code 
                **/
                _self.publicHolidaySetting = ko.observable();
                _self.enable4Weeks4HDSet = ko.observable(true);
                _self.enableLastPeriod4Weeks4HDSet = ko.observable(true);
                
                /* variable for A3_2, A3_3, A3_4 */
                _self.companyManageClassification = ko.observable([
                                                                    {"id": 0, "name": CompanyManagementClassification.NOT_MANAE,"enable": true},
                                                                    {"id": 1, "name": CompanyManagementClassification.MANAGE,"enable":true}
                                                                ]);
                _self.valDefaultcompanyManageClassification = ko.observable(1);
                _self.valDefaultcompanyManageClassification.subscribe(function(newValue) {
//                    console.log("_self.valDefaultcompanyManageClassification.subscribe: " + newValue);
//                    console.log(_self.valDefaultcompanyManageClassification());
                });
                
                
                /* variable for A4_2, A4_3, A4_4 */
                _self.publicHDPeriod = ko.observable([
                                                        {"id": 0, "name": viewModelTabA.ObjDomain.PublicHolidayManagementClassification._1MONTH,"enable":true},
                                                        {"id": 1, "name": viewModelTabA.ObjDomain.PublicHolidayManagementClassification._4WEEKS_4DAYS_OFF,"enable": true}
                                                    ]);
                _self.valDefaultpublicHDPeriod = ko.observable(1);
                _self.valDefaultpublicHDPeriod.subscribe(function(newValue) {
                    newValue == 1 ? _self.enable4Weeks4HDSet(true) : _self.enable4Weeks4HDSet(false), _self.enableLastPeriod4Weeks4HDSet(false); 
                });
                
                /* variable for A4_6 */
                _self.lstManagementPeriod = ko.observableArray([
                                                        {"code":0, "name":viewModelTabA.ObjDomain.PublicHolidayPeriod.CLOSURE_PERIOD},
                                                        {"code":1, "name":viewModelTabA.ObjDomain.PublicHolidayPeriod.FIRST_DAY_TO_LAST_DAY}
                                                    ]);
                _self.selectedManagementPeriod = ko.observable(1);
                
                /* variable for A9_2, A9_3, A9_4 */
                _self.dayOfPublicHoliday = ko.observable([
                                                    {"id":0,"name": viewModelTabA.ObjDomain.DayOfPublicHoliday.DESIGNATE_BY_YEAR_MONTH_DAY,"enable":true},
                                                    {"id":1,"name":viewModelTabA.ObjDomain.DayOfPublicHoliday.DESIGNATE_BY_MONTH_DAY,"enable": true}
                                            ]);
                _self.valDefaultDayOfPublicHoliday = ko.observable(1);
                _self.valDefaultDayOfPublicHoliday.subscribe(function(newValue) {
                    newValue == 1 ? _self.enableLastPeriod4Weeks4HDSet(true) : _self.enableLastPeriod4Weeks4HDSet(false);
                    newValue == 1 && _self.valDefaultpublicHDPeriod() == 1 ? _self.enableLastPeriod4Weeks4HDSet(true) : _self.enableLastPeriod4Weeks4HDSet(false);
                });
                
                /* variable for A16_1 */
                _self.isWeeklyHdCheck = ko.observable(false);
                _self.isWeeklyHdCheck.subscribe(function(newValue) { 
                });
                
                /* variable for A3_8, A3_9 */
                _self.isTransferWhenPublicHdIsMinus = ko.observable(false);
                _self.isTransferWhenPublicHdIsMinus.subscribe(function(newValue) {
                });
                _self.selectedCodeCarryoverDeadline = ko.observable(1);
                _self.lstCarryoverDeadline = ko.observableArray([
                                                        {"code": 0, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._1_MONTH},
                                                        {"code": 1, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._2_MONTH},
                                                        {"code": 2, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._3_MONTH},
                                                        {"code": 3, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._4_MONTH},
                                                        {"code": 4, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._5_MONTH},
                                                        {"code": 5, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._6_MONTH},
                                                        {"code": 6, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._7_MONTH},
                                                        {"code": 7, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._8_MONTH},
                                                        {"code": 8, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._9_MONTH},
                                                        {"code": 9, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._10_MONTH},
                                                        {"code": 10, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._11_MONTH},
                                                        {"code": 11, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline._12_MONTH},
                                                        {"code": 12, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline.YEAR_END},
                                                        {"code": 13, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline.INDEFINITE},
                                                        {"code": 14, "name": viewModelTabA.ObjDomain.PublicHolidayCarryOverDeadline.CURRENT_MONTH},
                                                    ]);
                _self.selectedCodeCarryoverDeadline.subscribe(function(newValue) {
                });
                
                
                /* variable for A16_3 */
                _self.lstStartDayOfWeek = ko.observableArray([
                                                        {"code":0, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.MONDAY},
                                                        {"code":1, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.TUESDAY},
                                                        {"code":2, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.WEDNESDAY},
                                                        {"code":3, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.THURSDAY},
                                                        {"code":4, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.FRIDAY},
                                                        {"code":5, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.SATURDAY},
                                                        {"code":6, "name":viewModelTabA.ObjDomain.DaySOfTheWeek.SUNDAY}
                                                    ]);
                _self.selectedStartDayOfWeek = ko.observable(1);
                
                /* variable for A16_6, A16_8 */
                _self.inLegalHDOfWeekHDSetting = ko.observable(1);
                _self.outLegalHDOfWeekHDSetting = ko.observable(2);
                
                /* variable for A11_1 */
                _self.isOneWeekHoliday = ko.observable(true);
                
                /* variable for A12_1 */
                _self.isFourWeekHoliday = ko.observable(true);
                
                /* variable for A12_5, A12_6 */
                _self.inLegalHD_FWPH = ko.observable(1);
                _self.outLegalHD_FWPH = ko.observable(2);
                
                /* variable for A14_5, A14_6 */
                _self.inLegalHD_LWHNOFW = ko.observable(3);
                _self.outLegalHD_LWHNOFW = ko.observable(4);
                
                /* variable for A11_5, A11_6 */
                _self.inLegalHD_OWPH = ko.observable(1);
                _self.outLegalHD_OWPH = ko.observable(2);
                
                /* variable for A13_5, A13_6 */
                _self.inLegalHD_LWHNOOW = ko.observable(3);
                _self.outLegalHD_LWHNOOW = ko.observable(4);
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
                    dfd.resolve(_self);    
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
//                let publicHolidayGrantDate = new viewModelTabA.ObjDomain.PublicHolidayGrantDate(viewModelTabA.ObjDomain.PublicHolidayPeriod.closurePeriod);
                let publicHoliday = new viewModelTabA.ObjDomain.PublicHoliday(12192017, 1219, viewModelTabA.ObjDomain.DayOfPublicHoliday.designateByYearMonthDay);
                let publicHolidayManagementUsageUnit = new viewModelTabA.ObjDomain.PublicHolidayManagementUsageUnit(true, false, true);
                let publicHolidayManagementStartDate = new viewModelTabA.ObjDomain.PublicHolidayGrantDate(viewModelTabA.ObjDomain.PublicHolidayPeriod.closurePeriod);
                _self.publicHolidaySetting = new ObjDomain.PublicHolidaySetting(true, publicHolidayManagementUsageUnit, 
                                                                                            viewModelTabA.ObjDomain.PublicHolidayManagementClassification._1Month,
                                                                                            publicHolidayManagementStartDate, true );
//                console.log(_self.publicHolidaySetting);
            }
            
            private settingOfUsageUnit(): void {
                var self = this;
                setShared('valScreenF', "valScreenF", true);
                nts.uk.ui.windows.sub.modal("/view/kmf/002/f/index.xhtml").onClosed(function() {
//                    console.log("close screen F");
                });
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
    
    enum CompanyManagementClassification {
        MANAGE = "管理する",
        NOT_MANAE = "管理しない"
    }
    
    module PublicHolidayPeriod {
        export const DAY_IN_MONTH = "1ヶ月の日数を管理する";
        export const FOUR_DAY_FOR_FOUR_WEEK = "4週4休を管理する";
    }
    
    module TypeDate {
        export const FULL_DATE = "年月日を指定する";
        export const MONTH_DAY = "月日を指定する";
    }
    
    export module ObjDomain {
        export class PublicHolidaySetting {
            CID: KnockoutObservable<string>;
            isManageComPublicHD: KnockoutObservable<boolean>;
            publicHdManagementUsageUnit: KnockoutObservable<PublicHolidayManagementUsageUnit>;
            publicHdManagementClassification: KnockoutObservable<PublicHolidayManagementClassification>;
            publicHdManagementStartDate: KnockoutObservable<PublicHolidayManagementStartDate>;
            isWeeklyHdCheck: KnockoutObservable<boolean>;
            
            constructor( isManageComPublicHD: boolean, publicHdManagementUsageUnit: PublicHolidayManagementUsageUnit, 
                        publicHdManagementClassification: PublicHolidayManagementClassification,
                        publicHdManagementStartDate: PublicHolidayManagementStartDate,
                        isWeeklyHdCheck, boolean ) {
                let _self = this;
//                _self.CID = ko.observable();
                _self.isManageComPublicHD = ko.observable(isManageComPublicHD);
                _self.publicHdManagementUsageUnit = ko.observable(publicHdManagementUsageUnit);
                _self.publicHdManagementClassification = ko.observable(publicHdManagementClassification);
                _self.publicHdManagementStartDate = ko.observable(publicHdManagementStartDate);
                _self.isWeeklyHdCheck = ko.observable(isWeeklyHdCheck);
            }
        }   
        
        export class PublicHolidayManagementUsageUnit {
            isManageEmployeePublicHd: KnockoutObservable<boolean>;
            isManageWkpPublicHd: KnockoutObservable<boolean>;
            isManageEmpPublicHd: KnockoutObservable<boolean>;
            
            constructor(isManageEmployeePublicHd: boolean, isManageWkpPublicHd: boolean, isManageEmpPublicHd: boolean) {                
                let _self = this;
                _self.isManageEmployeePublicHd = ko.observable(isManageEmployeePublicHd);
                _self.isManageWkpPublicHd = ko.observable(isManageWkpPublicHd);
                _self.isManageEmpPublicHd = ko.observable(isManageEmpPublicHd);
            }
        }
        
        export interface PublicHolidayManagementStartDate {
        }
        
        export class PublicHolidayGrantDate implements PublicHolidayManagementStartDate{
            period: KnockoutObservable<PublicHolidayPeriod>;
            
            constructor(period: PublicHolidayPeriod) {                
                let _self = this;
                _self.period = ko.observable(period);
            }
        }
        
        export class PublicHoliday implements PublicHolidayManagementStartDate{
            date: KnockoutObservable<number>;
            dayMonth: KnockoutObservable<number>;
            determineStartDate: KnockoutObservable<DayOfPublicHoliday>;
            
            constructor(date: number, dayMonth: number, determineStartDate: DayOfPublicHoliday) {                
                let _self = this;
                _self.date = ko.observable(date);
                _self.dayMonth = ko.observable(dayMonth);
                _self.determineStartDate = ko.observable(determineStartDate);
            }
        }
        
        export enum PublicHolidayPeriod {
            CLOSURE_PERIOD = "締め期間",
            FIRST_DAY_TO_LAST_DAY = "１日～末日",
        }
        
        export enum DayOfPublicHoliday {
            DESIGNATE_BY_YEAR_MONTH_DAY = "年月日で指定する",
            DESIGNATE_BY_MONTH_DAY ="月日で指定する",
        }
        
        export enum PublicHolidayManagementClassification {
            _1MONTH = "1カ月の日数を管理する",
            _4WEEKS_4DAYS_OFF = "4週4休を管理する",
        }
        
        export enum PublicHolidayCarryOverDeadline{
            _1_MONTH = "1ヶ月",
            _2_MONTH = "2ヶ月",
            _3_MONTH = "3ヶ月",
            _4_MONTH = "4ヶ月",
            _5_MONTH = "5ヶ月",
            _6_MONTH = "6ヶ月",
            _7_MONTH = "7ヶ月",
            _8_MONTH = "8ヶ月",
            _9_MONTH = "9ヶ月",
            _10_MONTH = "10ヶ月",
            _11_MONTH = "11ヶ月",
            _12_MONTH = "12ヶ月",
            YEAR_END = "年度末",
            INDEFINITE = "無期限",
            CURRENT_MONTH = "当月",
        }
        
        export enum DaySOfTheWeek {
            MONDAY = "月曜日",
            TUESDAY = "火曜日",
            WEDNESDAY = "水曜日",
            THURSDAY = "木曜日",
            FRIDAY = "金曜日",
            SATURDAY = "土曜日",
            SUNDAY = "日曜日"
        }
        
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
                _self.lastWeekAddedDays = ko.observable(LastWeekHolidayNumberOfOneWeek);
            }
        }
        
        export class FourWeekFourHolidayNumberSetting {
            isOneWeekHoliday: KnockoutObservable<boolean>;
            oneWeek: KnockoutObservable<OneWeekPublicHoliday>;
            isFourWeekHoliday: KnockoutObservable<boolean>;
            fourWeek: KnockoutObservable<FourWeekPublicHoliday>;
            
            constructor(isOneWeekHoliday: boolean, oneWeek: OneWeekPublicHoliday, isFourWeekHoliday: boolean, fourWeek: FourWeekPublicHoliday) {
                _self = this;
                _self.isOneWeekHoliday = ko.observable(isOneWeekHoliday);
                _self.oneWeek = ko.observable(oneWeek);
                _self.isFourWeekHoliday = ko.observable(isFourWeekHoliday);
                _self.fourWeek = ko.observable(fourWeek);
            }
            
            
            
        }
    }
}