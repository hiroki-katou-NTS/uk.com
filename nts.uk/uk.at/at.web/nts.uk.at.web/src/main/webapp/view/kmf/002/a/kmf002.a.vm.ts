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
            publicHolidaySetting: KnockoutObservable<PublicHolidaySetting>;
            forwardSetOfPubHD: KnockoutObservable<ForwardSettingOfPublicHoliday>;
            weekHDSet: KnockoutObservable<WeekHolidaySetting>;
            fourWkFourHDNumSet: KnockoutObservable<FourWeekFourHolidayNumberSetting>;
            companyManageClassification: KnockoutObservableArray<any>;
            lstManagementPeriod: KnockoutObservableArray<any>;
            publicHDPeriod: KnockoutObservableArray<any>;
            dayOfPublicHoliday: KnockoutObservableArray<any>;
            
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
            
            isManageEmployeePublicHd: KnockoutObservable<number>;
            isManageWkpPublicHd: KnockoutObservable<number>;
            isManageEmpPublicHd: KnockoutObservable<number>;
            
            date: KnockoutObservable<string>;
            
            constructor(){
                let _self = this;
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
                
                _self.isManageEmployeePublicHd = ko.observable(1);
                _self.isManageWkpPublicHd = ko.observable(1);
                _self.isManageEmpPublicHd = ko.observable(1);
                
                _self.date = ko.observable('20000101');
                /** 
                  *    main define variable code 
                **/
                _self.publicHolidaySetting = ko.observable(new PublicHolidaySetting(1, 0, new PublicHolidayGrantDate(0),
                                                                                    new PublicHoliday('', '', 1), 0));
                _self.forwardSetOfPubHD = ko.observable(new ForwardSettingOfPublicHoliday(0, 0));
                _self.weekHDSet = ko.observable(new WeekHolidaySetting(0, 0, 0));
                _self.fourWkFourHDNumSet = ko.observable(new FourWeekFourHolidayNumberSetting(0, new OneWeekPublicHoliday(0, 0, new LastWeekHolidayNumberOfOneWeek(0, 0)), 0, new FourWeekPublicHoliday(0, 0, new LastWeekHolidayNumberOfFourWeek(0, 0))));
                
                _self.companyManageClassification = ko.observableArray();
                _self.publicHDPeriod = ko.observableArray();
                _self.lstManagementPeriod = ko.observableArray();
                _self.dayOfPublicHoliday = ko.observableArray();
                _self.enableFullDate = ko.observable(true);
                _self.lstCarryoverDeadline = ko.observableArray();
                _self.lstStartDayOfWeek = ko.observableArray();
                
                // start subscribe
                _self.publicHolidaySetting().pubHD().determineStartDate.subscribe(function(newValue) {
                    _self.condition1And2();
                    _self.condition7();
                    _self.condition8();
                });
            
                _self.publicHolidaySetting().publicHdManagementClassification.subscribe(function(newValue) {
                    // setting for button Setting Unit
                    if (newValue == 0 && _self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE) {
                        _self.isDisableSetUnitBtn(true);
                    } else {
                        _self.isDisableSetUnitBtn(false);
                    }
                    _self.condition3And4();
                    _self.condition5();
                    _self.condition6();
                    _self.condition7();
                    _self.condition8();
                    _self.conditionSideBar1();
                    _self.conditionSideBar2();
                    _self.conditionSideBar3();
                    _self.conditionSideBar4();
                });
            
                _self.publicHolidaySetting().isManageComPublicHd.subscribe(function(newValue) {
                    _self.condition3And4();
                    _self.condition5();
                    _self.condition6();
                    _self.condition9();
                    _self.condition7();
                    _self.condition8();
                    _self.conditionSideBar5();
                });
                
                _self.fourWkFourHDNumSet().isOneWeekHoliday.subscribe(function(newValue) {
                    _self.condition5();
                    _self.condition7();
                });
            
                _self.fourWkFourHDNumSet().isFourWeekHoliday.subscribe(function(newValue) {
                    _self.condition6();
                    _self.condition8();
                });
            
                _self.forwardSetOfPubHD().isTransferWhenPublicHdIsMinus.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.forwardSetOfPubHD().isTransferWhenPublicHdIsMinus(BoolValue.TRUE);        
                    } else {
                        _self.forwardSetOfPubHD().isTransferWhenPublicHdIsMinus(BoolValue.FALSE);
                    }
                });
                
                _self.publicHolidaySetting().isWeeklyHdCheck.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.publicHolidaySetting().isWeeklyHdCheck(BoolValue.TRUE);
                    } else {
                        _self.publicHolidaySetting().isWeeklyHdCheck(BoolValue.FALSE);
                    }
                });
                
                _self.fourWkFourHDNumSet().isOneWeekHoliday.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.fourWkFourHDNumSet().isOneWeekHoliday(BoolValue.TRUE);    
                    } else {
                        _self.fourWkFourHDNumSet().isOneWeekHoliday(BoolValue.FALSE);
                    }
                });
                
                _self.fourWkFourHDNumSet().isFourWeekHoliday.subscribe(function(newValue) {
                    if (newValue == true) {
                        _self.fourWkFourHDNumSet().isFourWeekHoliday(BoolValue.TRUE);    
                    } else {
                        _self.fourWkFourHDNumSet().isFourWeekHoliday(BoolValue.FALSE);
                    }
                });
                
                _self.isManageEmpPublicHd.subscribe(function(newValue) {
                    _self.conditionSideBar3();
                });
                
                _self.isManageEmployeePublicHd.subscribe(function(newValue) {
                    _self.conditionSideBar4();
                });
                
                _self.isManageWkpPublicHd.subscribe(function(newValue) {
                    _self.conditionSideBar2();
                });
                // end subscribe
            }
            
            public start_page(typeStart: number): JQueryPromise<any> {
                
                var dfd = $.Deferred<any>();
                
                let _self = this;
                // load all
                if (typeStart == SideBarTabIndex.THIRD) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    _self.screenB(new viewModelTabB.ScreenModel());
                    $.when(_self.screenB().start_page()).done(function() {
                        dfd.resolve(_self);
                        blockUI.clear();
                        $( "#scrB .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FIRST) {
                    // Process for screen A (Mother of all screen)
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.getAllEnum(), _self.getAllData(), _self.findAllManageUseUnit()).done(function() {
                        $( "#managePubHD" ).focus();
                        dfd.resolve(_self);    
                        blockUI.clear();
                    });
                } else if (typeStart == SideBarTabIndex.SECOND) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    _self.screenE(new viewModelTabE.ScreenModel());
                    $.when(_self.screenE().start_page()).done(function() {
                        dfd.resolve(_self);
                        blockUI.clear();
                        $( "#scrE .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FOURTH) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    _self.screenD(new viewModelTabD.ScreenModel());
                    $.when(_self.screenD().start_page()).done(function() {
                        dfd.resolve(_self);
                        blockUI.clear();
                        $( "#scrD .datePickerYear" ).focus();
                    });    
                } else if (typeStart == SideBarTabIndex.FIFTH) {
                    nts.uk.ui.errors.clearAll();
                    blockUI.grayout();
                    _self.screenC(new viewModelTabC.ScreenModel());
                    $.when(_self.screenC().start_page()).done(function() {
                        dfd.resolve(_self);
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
                        let _self = this;
                        _self.start_page(SideBarTabIndex.FIRST);
                    }
                });
            }
            
            public onSelectTabB(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.THIRD,
                        activate: (event, info) => {
                            _self.start_page(SideBarTabIndex.THIRD);
                        }
                    });
                }
            }
            
            public onSelectTabC(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.FIFTH,
                        activate: (event, info) => {
                            _self.start_page(SideBarTabIndex.FIFTH);
                        }
                    });
                }
            }
            
            public onSelectTabD(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.FOURTH,
                        activate: (event, info) => {
                            _self.start_page(SideBarTabIndex.FOURTH);
                        }
                    });    
                }
            }
            
            public onSelectTabE(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.SECOND,
                        activate: (event, info) => {
                            _self.start_page(SideBarTabIndex.SECOND);
                        }
                    });        
                }
            }
            
            private isDisableTab(): boolean {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE) {
                    return false;
                }
                return true;
            }
            
            /**
             *  process for screen A
             */
            public save(): void {
                let _self = this;
                nts.uk.ui.errors.clearAll();
                _self.validateInput();
                _self.checkValidate();
                if (!nts.uk.ui.errors.hasError()) {
                    blockUI.invisible();
                    $.when(service.save(_self.publicHolidaySetting(), _self.forwardSetOfPubHD(), 
                                        _self.weekHDSet(), _self.fourWkFourHDNumSet())).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        _self.getAllData();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res);
                    }).always(()=> blockUI.clear());    
                }
            }
            
            private checkValidate(): void {
                let _self = this;
                if ( _self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.NOT_MANAGE ) {
                    nts.uk.ui.errors.clearAll();    
                } else {
                    if (_self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH) {
                        $('#fullDate').ntsEditor("clear");
                        $('#inLegalHoliday18').ntsEditor("clear");
                        $('#outLegalHoliday19').ntsEditor("clear");
                        $('#inLegalHoliday20').ntsEditor("clear");
                        $('#outLegalHoliday21').ntsEditor("clear");
                        $('#inLegalHoliday22').ntsEditor("clear");
                        $('#outLegalHoliday23').ntsEditor("clear");
                        $('#inLegalHoliday24').ntsEditor("clear");
                        $('#outLegalHoliday25').ntsEditor("clear");
                    } else {
                        if (_self.publicHolidaySetting().pubHD().determineStartDate() == TypeDate.MONTH_DAY) {
                            $('#fullDate').ntsError('clear');
                        } else {
                            $('#fullDate').ntsEditor("validate");
                            $('#inLegalHoliday20').ntsEditor("clear");
                            $('#outLegalHoliday21').ntsEditor("clear");     
                            $('#inLegalHoliday24').ntsEditor("clear");
                            $('#outLegalHoliday25').ntsEditor("clear");               
                        }
                    }
                    
                    if (_self.fourWkFourHDNumSet().isOneWeekHoliday() == false) {
                        $('#inLegalHoliday18').ntsEditor("clear");
                        $('#outLegalHoliday19').ntsEditor("clear");     
                        $('#inLegalHoliday20').ntsEditor("clear");
                        $('#outLegalHoliday21').ntsEditor("clear");      
                    }
                    if (_self.fourWkFourHDNumSet().isFourWeekHoliday() == false) {
                        $('#inLegalHoliday18').ntsEditor("clear");
                        $('#outLegalHoliday19').ntsEditor("clear");     
                        $('#inLegalHoliday20').ntsEditor("clear");
                        $('#outLegalHoliday21').ntsEditor("clear");      
                    }
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
                let _self = this;
                var dfd = $.Deferred();
                $.when(service.findAll()).done(function(data: any) {
                    if (!_.isUndefined(data.pubHdSet) && !_.isNull(data.pubHdSet) && !_.isEmpty(data.pubHdSet)) {
                        // For domain publicHolidaySetting
                        _self.publicHolidaySetting().isManageComPublicHd(data.pubHdSet.isManageComPublicHd);
                        _self.publicHolidaySetting().publicHdManagementClassification(data.pubHdSet.publicHdManagementClassification);
                        _self.publicHolidaySetting().isWeeklyHdCheck(data.pubHdSet.isWeeklyHdCheck);
                        _self.publicHolidaySetting().pubHDGrantDate().period(data.pubHdSet.period);
                        _self.publicHolidaySetting().pubHD().date(data.pubHdSet.fullDate);
                        _self.publicHolidaySetting().pubHD().dayMonth(data.pubHdSet.dayMonth);
                        _self.publicHolidaySetting().pubHD().determineStartDate(data.pubHdSet.determineStartD);
                    } 
                
                    // For domain ForwardSettingOfPublicHoliday
                    if (!_.isUndefined(data.forwardSetOfPubHd) && !_.isNull(data.forwardSetOfPubHd) && !_.isEmpty(data.forwardSetOfPubHd)) {
                        _self.forwardSetOfPubHD().carryOverDeadline(data.forwardSetOfPubHd.carryOverDeadline);
                        _self.forwardSetOfPubHD().isTransferWhenPublicHdIsMinus(data.forwardSetOfPubHd.isTransferWhenPublicHdIsMinus);
                    } 
                    
                    // For domain WeekHolidaySetting
                    if (!_.isUndefined(data.weekHdSet) && !_.isNull(data.weekHdSet) && !_.isEmpty(data.weekHdSet)) {   
                        _self.weekHDSet().inLegalHoliday(data.weekHdSet.inLegalHoliday);
                        _self.weekHDSet().outLegalHoliday(data.weekHdSet.outLegalHoliday);
                        _self.weekHDSet().startDay(data.weekHdSet.startDay); 
                    }
                    
                    // For domain FourWeekFourHolidayNumberSetting
                    if (!_.isUndefined(data.fourWeekfourHdNumbSet) && !_.isNull(data.fourWeekfourHdNumbSet) && !_.isEmpty(data.fourWeekfourHdNumbSet)) {
                        _self.fourWkFourHDNumSet().isOneWeekHoliday(data.fourWeekfourHdNumbSet.isOneWeekHoliday);
                        _self.fourWkFourHDNumSet().oneWeek().inLegalHoliday(data.fourWeekfourHdNumbSet.inLegalHdOwph);
                        _self.fourWkFourHDNumSet().oneWeek().outLegalHoliday(data.fourWeekfourHdNumbSet.outLegalHdOwph);
                        _self.fourWkFourHDNumSet().oneWeek().lastWeekAddedDays().inLegalHoliday(data.fourWeekfourHdNumbSet.inLegalHdLwhnoow);
                        _self.fourWkFourHDNumSet().oneWeek().lastWeekAddedDays().outLegalHoliday(data.fourWeekfourHdNumbSet.outLegalHdLwhnoow);
                        _self.fourWkFourHDNumSet().isFourWeekHoliday(data.fourWeekfourHdNumbSet.isFourWeekHoliday);
                        _self.fourWkFourHDNumSet().fourWeek().inLegalHoliday(data.fourWeekfourHdNumbSet.inLegalHdFwph);
                        _self.fourWkFourHDNumSet().fourWeek().outLegalHoliday(data.fourWeekfourHdNumbSet.outLegalHdFwph);
                        _self.fourWkFourHDNumSet().fourWeek().lastWeekAddedDays().inLegalHoliday(data.fourWeekfourHdNumbSet.inLegalHdLwhnofw);
                        _self.fourWkFourHDNumSet().fourWeek().lastWeekAddedDays().outLegalHoliday(data.fourWeekfourHdNumbSet.outLegalHdLwhnofw);
                    }
                    
                    // notify variable observable
                    _self.publicHolidaySetting().isManageComPublicHd.valueHasMutated();
                    _self.publicHolidaySetting().publicHdManagementClassification.valueHasMutated();
                    _self.publicHolidaySetting().pubHD().determineStartDate.valueHasMutated();
                    _self.fourWkFourHDNumSet().isOneWeekHoliday.valueHasMutated();
                    _self.fourWkFourHDNumSet().isFourWeekHoliday.valueHasMutated();
                    
                    dfd.resolve();   
                });
                return dfd.promise();
            }
            
            private conditionSideBar1(): void {
                let _self = this;
                if (_self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.SECOND);        
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.SECOND);    
                }
            }
            
            private conditionSideBar2(): void {
                let _self = this;
                if (_self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH && _self.isManageWkpPublicHd() == BoolValue.TRUE ) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.THIRD);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.THIRD);
                }
            }
            
            private conditionSideBar3(): void {
                let _self = this;
                if (_self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH && _self.isManageEmpPublicHd() == BoolValue.TRUE) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.FOURTH);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.FOURTH);
                }
            }
            
            private conditionSideBar4(): void {
                let _self = this;
                if (_self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH && _self.isManageEmployeePublicHd() == BoolValue.TRUE) {
                    $("#sidebar").ntsSideBar("show", SideBarTabIndex.FIFTH);
                } else {
                    $("#sidebar").ntsSideBar("hide", SideBarTabIndex.FIFTH);
                }
            }
            
            private conditionSideBar5(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE) {
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
            
            private condition7(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE 
                        && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.FOUR_WEEK_FOUR_DAYOFF
                        && (_self.fourWkFourHDNumSet().isOneWeekHoliday() == true || _self.fourWkFourHDNumSet().isOneWeekHoliday() == BoolValue.TRUE)
                        && _self.publicHolidaySetting().pubHD().determineStartDate() == TypeDate.MONTH_DAY) {
                    _self.enableLastPeriod1WeekPubHD(true);
                } else {
                    _self.enableLastPeriod1WeekPubHD(false);
                }    
            }
            
            private condition8(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.FOUR_WEEK_FOUR_DAYOFF
                        && (_self.fourWkFourHDNumSet().isFourWeekHoliday() == true || _self.fourWkFourHDNumSet().isFourWeekHoliday() == BoolValue.TRUE)
                        && _self.publicHolidaySetting().pubHD().determineStartDate() == TypeDate.MONTH_DAY) {
                    _self.enableLastPeriod4WeekPubHD(true);
                } else {
                    _self.enableLastPeriod4WeekPubHD(false);
                }    
            }
            
            private condition6(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.FOUR_WEEK_FOUR_DAYOFF
                        && (_self.fourWkFourHDNumSet().isFourWeekHoliday() == true || _self.fourWkFourHDNumSet().isFourWeekHoliday() == BoolValue.TRUE)) {
                    _self.enable4WeeksPubHDInLegalHD(true);
                    _self.enable4WeeksPubHDOutLegalHD(true);    
                } else {
                    _self.enable4WeeksPubHDInLegalHD(false);
                    _self.enable4WeeksPubHDOutLegalHD(false);    
                }  
            }
            
            private condition1And2(): void {
                let _self = this;
                if (_self.publicHolidaySetting().pubHD().determineStartDate() == TypeDate.YEAR_MONTH_DAY) {
                    _self.enableFullDate(true);
                } else {
                    _self.enableFullDate(false);
                }    
            }
            
            private condition9(): void {
                let _self = this;
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE) {
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
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE 
                        && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.FOUR_WEEK_FOUR_DAYOFF
                        && (_self.fourWkFourHDNumSet().isOneWeekHoliday() == true || _self.fourWkFourHDNumSet().isOneWeekHoliday() == BoolValue.TRUE)) {
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
                if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE 
                        && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.ONE_MONTH) {
                    _self.isDisableSetUnitBtn(false);
                    _self.enablePubHDGrantDatePeriod(true);
                    _self.enableDetermineStartDate(false);
                    _self.enablePubHDDateAndDayMonth(false);
                    _self.enable4Weeks4HDSet(false);
                }
                // condtion 4
                else if (_self.publicHolidaySetting().isManageComPublicHd() == ManagePubHD.MANAGE 
                            && _self.publicHolidaySetting().publicHdManagementClassification() == PublicHDPeriod.FOUR_WEEK_FOUR_DAYOFF) {
                    _self.isDisableSetUnitBtn(true);
                    _self.enablePubHDGrantDatePeriod(false);
                    _self.enableDetermineStartDate(true);
                    _self.enablePubHDDateAndDayMonth(true);
                    _self.enable4Weeks4HDSet(true);
                } 
                // another    
                else {
                    _self.isDisableSetUnitBtn(true);
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
                        service.getDaysOfTheWeek(), service.getManageEnum()).done(function(pubHDPeriodEnum: any, 
                                                                    dayOfPubHDEnum: any,
                                                                    pubHDManageClassificationEnum: any, 
                                                                    publicHolidayCarryOverDeadline: any, 
                                                                    daySOfTheWeek: any,
                                                                    manage: any) {
                    // todo: set enum
                    _self.setPubHDPeriodEnum(pubHDPeriodEnum);
                    _self.setDayOfPubHDEnum(dayOfPubHDEnum);
                    _self.setPubHDManageClassificationEnum(pubHDManageClassificationEnum);
                    _self.setPublicHolidayCarryOverDeadlineEnum(publicHolidayCarryOverDeadline);
                    _self.setDaysOfTheWeekEnum(daySOfTheWeek);
                    _self.setManageEnum(manage);
                    dfd.resolve();
                });    
                return dfd.promise();
            }
                
            private setPubHDPeriodEnum(pubHDPeriodEnum: any): void {
                let _self = this;
                if (pubHDPeriodEnum.length > 0 && pubHDPeriodEnum.length != _self.lstManagementPeriod.length) {
                    _self.lstManagementPeriod.removeAll();
                    _.forEach(pubHDPeriodEnum, function(obj) {
                        _self.lstManagementPeriod.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }
                
            private setDayOfPubHDEnum(dayOfPubHDEnum: any): void {
                let _self = this;
                if (dayOfPubHDEnum.length > 0 && dayOfPubHDEnum.length != _self.dayOfPublicHoliday.length) {
                  _self.dayOfPublicHoliday.removeAll();
                    _.forEach(dayOfPubHDEnum, function(obj) {
                        _self.dayOfPublicHoliday.push({"id":obj.value , "name":obj.localizedName, "enable":true});  
                    });                    
                }
            }
                
            private setPubHDManageClassificationEnum(pubHDManageClassificationEnum: any): void {
                let _self = this;
                if (pubHDManageClassificationEnum.length > 0 && pubHDManageClassificationEnum.length != _self.publicHDPeriod.length) {
                    _self.publicHDPeriod.removeAll();
                    _.forEach(pubHDManageClassificationEnum, function(obj) {
                        _self.publicHDPeriod.push({"id":obj.value , "name":obj.localizedName, "enable":true});  
                    });    
                }
            }
                
            private setPublicHolidayCarryOverDeadlineEnum(pubHDCarryOverDeadline: any): void {
                let _self = this;
                if (pubHDCarryOverDeadline.length > 0 && pubHDCarryOverDeadline.length != _self.lstCarryoverDeadline.length) {
                    _self.lstCarryoverDeadline.removeAll();
                    _.forEach(pubHDCarryOverDeadline, function(obj) {
                        _self.lstCarryoverDeadline.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }    
                
            private setDaysOfTheWeekEnum(daySOfTheWeek: any): void {
                let _self = this;
                if (daySOfTheWeek.length > 0 && daySOfTheWeek.length != _self.lstStartDayOfWeek.length) {
                    _self.lstStartDayOfWeek.removeAll();
                    _.forEach(daySOfTheWeek, function(obj) {
                        _self.lstStartDayOfWeek.push({"code":obj.value , "name":obj.localizedName});  
                    });    
                }
            }    
            
            private setManageEnum(manage: any): void {
                let _self = this;
                _self.companyManageClassification.removeAll();
                    _.forEachRight(manage, function(obj) {
                        _self.companyManageClassification.push({"id":obj.value , "name":obj.localizedName});  
                    });    
            }
            
            private settingOfUsageUnit(): void {
                let _self = this;
                $.when(_self.findAllManageUseUnit()).done(function() {
                    setShared('valScreenF', "valScreenF", true);
                    setShared('isManageEmpPublicHd', _self.isManageEmpPublicHd());
                    setShared('isManageEmployeePublicHd', _self.isManageEmployeePublicHd());
                    setShared('isManageWkpPublicHd', _self.isManageWkpPublicHd());
                    nts.uk.ui.windows.sub.modal("/view/kmf/002/f/index.xhtml").onClosed(function() {
                        if (getShared('saveManageUnit') == true) {
                            // F2_6
                            _self.isManageEmployeePublicHd(getShared('isManageEmployeePublicHd'));
                            // F2_5
                            _self.isManageWkpPublicHd(getShared('isManageWkpPublicHd'));
                            // F2_4
                            _self.isManageEmpPublicHd(getShared('isManageEmpPublicHd'));
                            _self.isManageEmployeePublicHd.valueHasMutated();
                            _self.isManageWkpPublicHd.valueHasMutated();
                            _self.isManageEmpPublicHd.valueHasMutated();
                            $.when(service.saveManageUnit(_self.isManageEmployeePublicHd(), _self.isManageWkpPublicHd(), 
                                                            _self.isManageEmpPublicHd())).done(function(data: any) {
                            });    
                        }
                    });    
                });
            } 
            
            private findAllManageUseUnit(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                $.when(service.findAllManageUseUnit()).done(function(data: any) {                     
                    _self.isManageEmployeePublicHd(data.isManageEmployeePublicHd);
                    _self.isManageWkpPublicHd(data.isManageWkpPublicHd);
                    _self.isManageEmpPublicHd(data.isManageEmpPublicHd);
                    dfd.resolve(_self);
                });
                return dfd.promise();    
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
            isTransferWhenPublicHdIsMinus: KnockoutObservable<any>;   
            carryOverDeadline: KnockoutObservable<number>; 
            
            constructor(isTransferWhenPublicHdIsMinus:any, carryOverDeadline:number) {
                let _self = this;
                _self.isTransferWhenPublicHdIsMinus = ko.observable(isTransferWhenPublicHdIsMinus);
                _self.carryOverDeadline = ko.observable(carryOverDeadline);
            }
        }
        
        export class PublicHolidaySetting {
            isManageComPublicHd: KnockoutObservable<number>;
            publicHdManagementClassification: KnockoutObservable<number>;
            pubHDGrantDate: KnockoutObservable<PublicHolidayGrantDate>;
            pubHD: KnockoutObservable<PublicHoliday>;
            isWeeklyHdCheck: KnockoutObservable<any>;
            
            constructor(isManageComPublicHd:number, publicHdManagementClassification: number, 
                            pubHDGrantDate: PublicHolidayGrantDate,
                            pubHD: PublicHoliday, isWeeklyHdCheck: number) {
                let _self = this;
                
                _self.isManageComPublicHd = ko.observable(isManageComPublicHd);
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
            date: KnockoutObservable<string>;
            dayMonth: KnockoutObservable<string>;
            determineStartDate: KnockoutObservable<number>;
            
            constructor(date: string, dayMonth: string, determineStartDate: number) {                
                let _self = this;
                _self.date = ko.observable(date);
                _self.dayMonth = ko.observable(dayMonth);
                _self.determineStartDate = ko.observable(determineStartDate);
            }
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