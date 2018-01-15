module nts.uk.com.view.cmm021.a {

    export module viewModel {

        import UserDto = service.model.UserDto;
        import WindowAccountFinderDto = service.model.WindownAccountFinderDto;
        import SaveWindowAccountCommand = service.model.SaveWindowAccountCommand;
        import WindowAccountDto = service.model.WindowAccountDto;
        import SaveOtherSysAccountCommand = service.model.SaveOtherSysAccountCommand;


        //screen C
        import OtherSysAccFinderDto = service.model.OtherSysAccFinderDto;

        export class ScreenModel {
            baseDate: KnockoutObservable<Date>;
            listUserDto: UserDto[];
            checked: KnockoutObservable<boolean>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;

            items: KnockoutObservableArray<ItemModel>;

            useSet: KnockoutObservableArray<any>;
            selectUse: KnockoutObservable<number>;
            enableSave: KnockoutObservable<boolean>;

            listWindowAccCommand: WindowAccountDto[];
            selectedEmployeeId: KnockoutObservable<string>;

            personName: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            loginId: KnockoutObservable<string>;
            windowAcc1: WindowAccountDto;
            windowAcc2: WindowAccountDto;
            windowAcc3: WindowAccountDto;
            windowAcc4: WindowAccountDto;
            windowAcc5: WindowAccountDto;

            // declare properties in window account
            hostName1: KnockoutObservable<string>;
            userName1: KnockoutObservable<string>;

            hostName2: KnockoutObservable<string>;
            userName2: KnockoutObservable<string>;

            hostName3: KnockoutObservable<string>;
            userName3: KnockoutObservable<string>;

            hostName4: KnockoutObservable<string>;
            userName4: KnockoutObservable<string>;

            hostName5: KnockoutObservable<string>;
            userName5: KnockoutObservable<string>;

            isLoading: KnockoutObservable<boolean>;

            enable_A2_1: KnockoutObservable<boolean>;
            enable_A2_2: KnockoutObservable<boolean>;

            enable_B1_1: KnockoutObservable<boolean>;
            enable_B2_1: KnockoutObservable<boolean>;
            enable_B3_1: KnockoutObservable<boolean>;
            enable_B4_1: KnockoutObservable<boolean>;
            enable_B5_1: KnockoutObservable<boolean>;

            userId: KnockoutObservable<string>;
            listUserUnsetting: UserDto[];

            userIdBeChoosen: KnockoutObservable<string>;

            isScreenBSelected: KnockoutObservable<boolean>;
            isScreenCSelected: KnockoutObservable<boolean>;

            //Screen C
            companyCode6: KnockoutObservable<string>;
            userName6: KnockoutObservable<string>;

            otherSysAcc: KnockoutObservable<OtherSysAccFinderDto>;

            enable_C1_1: KnockoutObservable<boolean>;


            constructor() {
                let _self = this;
                _self.listUserDto = [];
                _self.checked = ko.observable(true);
                _self.items = ko.observableArray([]);
                _self.currentCode = ko.observable();
                _self.baseDate = ko.observable(moment(new Date()).toDate());

                _self.useSet = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CMM021_11") },
                    { code: '0', name: nts.uk.resource.getText("CMM021_10") },
                ]);
                _self.selectUse = ko.observable(1);

                _self.enableSave = ko.observable(true);
                _self.windowAcc1 = new WindowAccountDto("", "", "", 0, 0);
                _self.windowAcc2 = new WindowAccountDto("", "", "", 0, 0);
                _self.windowAcc3 = new WindowAccountDto("", "", "", 0, 0);
                _self.windowAcc4 = new WindowAccountDto("", "", "", 0, 0);
                _self.windowAcc5 = new WindowAccountDto("", "", "", 0, 0);

                _self.selectedEmployeeId = ko.observable(null);
                _self.selectedEmployeeId.subscribe((newValue) => {
                    if (newValue) {
                        _self.findUserDtoByEmployeeId(newValue);  
                    } else {
                        _self.unselectedMode();
                    }
                                      
                });
                _self.personName = ko.observable("");
                _self.employeeCode = ko.observable("");
                _self.loginId = ko.observable("");

                // B screen model
                // construct properties in window account
                _self.hostName1 = ko.observable("");
                _self.userName1 = ko.observable("");

                _self.hostName2 = ko.observable("");
                _self.userName2 = ko.observable("");

                _self.hostName3 = ko.observable("");
                _self.userName3 = ko.observable("");

                _self.hostName4 = ko.observable("");
                _self.userName4 = ko.observable("");

                _self.hostName5 = ko.observable("");
                _self.userName5 = ko.observable("");

                _self.enable_A2_1 = ko.observable(true);
                _self.enable_A2_2 = ko.observable(true);

                // UI
                _self.enable_B1_1 = ko.observable(false);
                _self.enable_B2_1 = ko.observable(false);
                _self.enable_B3_1 = ko.observable(false);
                _self.enable_B4_1 = ko.observable(false);
                _self.enable_B5_1 = ko.observable(false);

                //SUBSCRIBLE 
                _self.hostName1.subscribe(() => {
                    _self.windowAcc1.isChange = true;
                });
                _self.userName1.subscribe(() => {
                    _self.windowAcc1.isChange = true;
                });
                _self.enable_B1_1.subscribe((value) => {
                    if (value == false) {
                        _self.windowAcc1.isChange = true;
                    }
                });

                _self.hostName2.subscribe(() => {
                    _self.windowAcc2.isChange = true;
                });
                _self.userName2.subscribe(() => {
                    _self.windowAcc2.isChange = true;
                });
                _self.enable_B2_1.subscribe((value) => {
                    if (value == false) {
                        _self.windowAcc2.isChange = true;
                    }
                });

                _self.hostName3.subscribe(() => {
                    _self.windowAcc3.isChange = true;
                });
                _self.userName3.subscribe(() => {
                    _self.windowAcc3.isChange = true;
                });
                _self.enable_B3_1.subscribe((value) => {
                    if (value == false) {
                        _self.windowAcc3.isChange = true;
                    }
                });

                _self.hostName4.subscribe(() => {
                    _self.windowAcc4.isChange = true;
                });
                _self.userName4.subscribe(() => {
                    _self.windowAcc4.isChange = true;
                });
                _self.enable_B4_1.subscribe((value) => {
                    if (value == false) {
                        _self.windowAcc4.isChange = true;
                    }
                });

                _self.hostName5.subscribe(() => {
                    _self.windowAcc5.isChange = true;
                });
                _self.userName5.subscribe(() => {
                    _self.windowAcc5.isChange = true;
                });
                _self.enable_B5_1.subscribe((value) => {
                    if (value == false) {
                        _self.windowAcc5.isChange = true;
                    }
                });

                _self.userId = ko.observable("");
                _self.userIdBeChoosen = ko.observable("");
                _self.userId.subscribe((newValue) => {
                    if (!newValue) {
                        return;
                    }
                    _self.userIdBeChoosen(newValue);

                    if (_self.isScreenBSelected()) {
                        _self.findListWindowAccByUserId(newValue);
                        
                    }
                    if (_self.isScreenCSelected()) {
                        _self.findFirstOtherAcc(newValue);
                    }
                });

                _self.selectUse.subscribe((newValue) => {
                    if (newValue == 1) {
                        _self.loadUserSetting();
                    } else {
                        _self.loadUserUnsetting();
                    }
                });


                _self.listUserUnsetting = [];

                _self.isScreenBSelected = ko.observable(false);
                _self.isScreenCSelected = ko.observable(false);
                _self.isScreenBSelected.subscribe((newValue) => {
                    if (newValue) {
                        if (!_.isEmpty(_self.listUserDto)) {
                            _self.userId("");
                            _self.userId(_self.listUserDto[0].userId);

                            // show first item in list item
                            _self.selectedEmployeeId("");
                            _self.selectedEmployeeId(_self.listUserDto[0].employeeId);

                        }                                                                                          
                    }
                });
                _self.isScreenCSelected.subscribe((newValue) => {
                    if (newValue) {
                        _self.userId("");
                        _self.userId(_self.listUserDto[0].userId);
                        _self.findFirstOtherAcc(_self.userId());

                        // show first item in list item
                        _self.selectedEmployeeId("");
                        _self.selectedEmployeeId(_self.listUserDto[0].employeeId);
                    }
                });

                // Screen C
                _self.companyCode6 = ko.observable("");
                _self.userName6 = ko.observable("");

                _self.otherSysAcc = ko.observable(null);
                _self.enable_C1_1 = ko.observable(false);

                this.columns = ko.observableArray([
                    { headerText: '', key: 'employeeId', width: 150, hidden: true },
                    { headerText: nts.uk.resource.getText('CMM021_13'), key: 'loginId', width: 150 },
                    { headerText: nts.uk.resource.getText('CMM021_14'), key: 'employeeCode', width: 150 },
                    { headerText: nts.uk.resource.getText('CMM021_15'), key: 'personName', width: 150 },
                    { headerText: nts.uk.resource.getText('CMM021_17'), key: 'other', width: 70, formatter: lockIcon }
                ]);
            }

            /**
            * Start page
            */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                $.when(service.findListUserInfo(_self.baseDate()))
                    .done((data: UserDto[]) => {
                        _self.listUserDto = data;
                        _self.loadUserDto();
                        _self.onSelectScreenB();

                        // show first item in list item
                        if(!_.isEmpty(_self.listUserDto)){
                        _self.selectedEmployeeId(_self.listUserDto[0].employeeId);
                        }
                        _self.addLockIcon();
                        
                        dfd.resolve();                       
                    })
                    .fail((res: any) => {
                        _self.showMessageError(res);
                    });
                return dfd.promise();
            }


            /**
            * Show Error Message
            */
            private showMessageError(res: any) {
                // check error business exception
                if (!res.businessException) {
                    return;
                }

                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }

            //find list Window Acc
            private findListWindowAccByUserId(userId: string): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.findListWindowAccByUserIdAndUseAtr(userId).done((data: any) => {
                    
                    // check data null or empty
                    if (data && data.length) {
                        if (data[0]) {
                            _self.hostName1(data[0].hostName);
                            _self.userName1(data[0].userName);
                            _self.enable_B1_1(true);
                            _self.windowAcc1.isChange = false;   
                        } else {
                            _self.hostName1("");
                            _self.userName1("");
                            _self.enable_B1_1(false);
                            _self.windowAcc1.isChange = true;                                                    
                        }
                       
                        if (data[1]) {
                            _self.hostName2(data[1].hostName);
                            _self.userName2(data[1].userName);
                            _self.enable_B2_1(true);
                            _self.windowAcc2.isChange = false;
                        } else {
                            _self.hostName2("");
                            _self.userName2("");
                            _self.enable_B2_1(false);
                            _self.windowAcc2.isChange = true;
                        }

                        if (data[2]) {
                            _self.hostName3(data[2].hostName);
                            _self.userName3(data[2].userName);
                            _self.enable_B3_1(true);
                            _self.windowAcc3.isChange = false;
                        } else {
                            _self.hostName3("");
                            _self.userName3("");
                            _self.enable_B3_1(false);
                            _self.windowAcc3.isChange = true;
                        }

                        if (data[3]) {
                            _self.hostName4(data[3].hostName);
                            _self.userName4(data[3].userName);
                            _self.enable_B4_1(true);
                            _self.windowAcc4.isChange = false;
                        } else {
                            _self.hostName4("");
                            _self.userName4("");
                            _self.enable_B4_1(false);
                            _self.windowAcc4.isChange = true;
                        }

                        if (data[4]) {
                            _self.hostName5(data[4].hostName);
                            _self.userName5(data[4].userName);
                            _self.enable_B5_1(true);
                            _self.windowAcc5.isChange = false;
                        } else {
                            _self.hostName5("");
                            _self.userName5("");
                            _self.enable_B5_1(false);
                            _self.windowAcc5.isChange = true;
                        }
                        _self.updateMode();
                         
                    } else {
                        _self.newMode();
                        _self.loadNewWindowAccount();
                    }  
                    $('#focus-B1_4').focus();               
                    dfd.resolve();                   
                }).fail((res: any) => {
                    dfd.reject(res);
                });
                return dfd.promise();

            }


            // find user dto                      
            private findUserDtoByEmployeeId(selectedEmployeeId: string) {
                let _self = this;
                let user = _self.listUserDto.filter(item => selectedEmployeeId == item.employeeId)[0];
                _self.personName(user.personName);
                _self.employeeCode(user.employeeCode);
                _self.loginId(user.loginId);
                _self.userId(user.userId);                                            
            }                      

            private saveWindowAcc(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let user = _self.listUserDto.filter(item => _self.selectedEmployeeId() == item.employeeId)[0];

                let win1 = _self.windowAcc1;
                let win2 = _self.windowAcc2;
                let win3 = _self.windowAcc3;
                let win4 = _self.windowAcc4;
                let win5 = _self.windowAcc5;

                let saveCommand = new SaveWindowAccountCommand();

                if (_self.enable_B1_1()) {
                    win1.userId = _self.userId();
                    win1.hostName = _self.hostName1();
                    win1.userName = _self.userName1();
                    win1.no = 1;
                    win1.useAtr = 1;
                    saveCommand.winAcc1 = win1;
                }

                if (_self.enable_B2_1()) {
                    win2.userId = _self.userId();
                    win2.hostName = _self.hostName2();
                    win2.userName = _self.userName2();
                    win2.no = 2;
                    win2.useAtr = 1;
                    saveCommand.winAcc2 = win2;
                }

                if (_self.enable_B3_1()) {
                    win3.userId = _self.userId();
                    win3.hostName = _self.hostName3();
                    win3.userName = _self.userName3();
                    win3.no = 3;
                    win3.useAtr = 1;
                    saveCommand.winAcc3 = win3;
                }

                if (_self.enable_B4_1()) {
                    win4.userId = _self.userId();
                    win4.hostName = _self.hostName4();
                    win4.userName = _self.userName4();
                    win4.no = 4;
                    win4.useAtr = 1;
                    saveCommand.winAcc4 = win4;
                }

                if (_self.enable_B5_1()) {
                    win5.userId = _self.userId();
                    win5.hostName = _self.hostName5();
                    win5.userName = _self.userName5();
                    win5.no = 5;
                    win5.useAtr = 1;
                    saveCommand.winAcc5 = win5;
                }

                saveCommand.userId = _self.userId();
                service.saveWindowAccount(saveCommand)
                    .done((data: any) => {
                        _self.loadUserInfo();
                        _self.updateMode();
                        $('#focus-B1_4').focus();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        dfd.resolve();
                    }).fail(function(res: any) {
                        nts.uk.ui.dialog.bundledErrors(res);
                        dfd.reject(); 
                    });
                 return dfd.promise();
            }

            // common function load list user info
            private loadUserInfo(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findListUserInfo(_self.baseDate())
                    .done((data: UserDto[]) => {
                        _self.listUserDto = data;
                        _self.loadUserDto();
                        _self.addLockIcon();                       
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();

            }

            // load new mode
            private loadNewWindowAccount() {
                let _self = this;

                _self.hostName1("");
                _self.userName1("");
                _self.enable_B1_1(true);

                _self.hostName2("");
                _self.userName2("");
                _self.enable_B2_1(false);

                _self.hostName3("");
                _self.userName3("");
                _self.enable_B3_1(false);

                _self.hostName4("");
                _self.userName4("");
                _self.enable_B4_1(false);

                _self.hostName5("");
                _self.userName5("");
                _self.enable_B5_1(false);

            }

            public addLockIcon() {
                var iconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/cmm/021/icon/icon78.png').serialize();

                $('.icon-78').attr('style', "background: url('" + iconLink + "'); width: 17.727px; height: 17.727px; background-size: 17.727px 17.727px; margin-left: 23px;");
            }


           private loadUserDto() {
                let _self = this;
                _self.items([]);
            // check user info loaded is not empty
            if (!_.isEmpty(_self.listUserDto)) {                
                for (let userDto of _self.listUserDto) {
                        if (userDto.isSetting) {
                         _self.items.push(new ItemModel(userDto.personName, userDto.employeeCode, userDto.loginId, userDto.employeeId, userDto.userId, userDto.isSetting, 1));

                        } else {
                         _self.items.push(new ItemModel(userDto.personName, userDto.employeeCode, userDto.loginId, userDto.employeeId, userDto.userId, userDto.isSetting, 0));
                        }

                    }
                // if user info loaded is empty, set default empty element   
                }else{
                    _self.items.push(new ItemModel("", "", "", "", "", false, 0));
                    _self.unselectedMode();
                }       
          
            }                           

            private loadUserUnsetting() {
                let _self = this;
                _self.items([]);
                for (let userDto of _self.listUserDto) {
                    if (!userDto.isSetting) {
                        _self.listUserUnsetting.push(new ItemModel(userDto.personName, userDto.employeeCode, userDto.loginId, userDto.employeeId, userDto.userId, userDto.isSetting, userDto.other));
                    }

                }
                // select first item in list unsetting
                _self.selectedEmployeeId("");
                _self.selectedEmployeeId(_self.listUserUnsetting[0].employeeId);
                _self.newMode();

                _self.items(_self.listUserUnsetting);
                if (_self.listUserUnsetting.length = 0) {
                    _self.unselectedMode();
                }
            }

            private loadUserSetting() {
                let _self = this;
                _self.loadUserInfo();

                // select first item in list setting
                _self.selectedEmployeeId("");
                _self.selectedEmployeeId(_self.listUserDto[0].employeeId);
                _self.newMode();

            }

            // new mode
            private newMode() {
                let _self = this;
                _self.enable_A2_1(true);
                _self.enable_A2_2(false);

                _self.enable_B1_1(true);
                _self.enable_B2_1(false);
                _self.enable_B3_1(false);
                _self.enable_B4_1(false);
                _self.enable_B5_1(false);

            }

            // update mode
            private updateMode() {
                let _self = this;
                _self.enable_A2_1(true);
                _self.enable_A2_2(true);
            }

            //unselected mode
            private unselectedMode() {
                let _self = this;

                _self.enable_A2_1(false);
                _self.enable_A2_2(false);

                _self.enable_B1_1(false);
                _self.enable_B2_1(false);
                _self.enable_B3_1(false);
                _self.enable_B4_1(false);
                _self.enable_B5_1(false);

            }

            private deleteAccount() {
                let _self = this;

                if (_self.isScreenBSelected()) {
                    _self.deleteWindowAccount();
                } else if (_self.isScreenCSelected()) {
                    _self.deleteOtherAcc();
                }
            }

            private saveAccount() {
                let _self = this;

                if (_self.isScreenBSelected()) {
                    _self.saveWindowAcc();
                } else if (_self.isScreenCSelected()) {
                    _self.SaveOtherAcc();
                }

            }

            private deleteWindowAccount() {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    // nts.uk.ui.dialog.info({ messageId: "Msg_35" }).then(() => {
                    service.removeWindowAccount(_self.userIdBeChoosen()).done((data: any) => {
                        _self.loadUserInfo();
                        _self.newMode();
                        _self.hostName1("");
                        _self.userName1("");
                        _self.enable_B1_1(true);
                        $('#focus-B1_4').focus();
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                    //});
                }).ifNo(() => {
                    //nts.uk.ui.dialog.info({ messageId: "Msg_36" });
                    // cancel button delete
                    _self.loadUserInfo();
                    $('#focus-B1_4').focus();
                    return;
                });

            }


            public onSelectScreenB() {
                let _self = this;

                _self.isScreenBSelected(true);
                _self.isScreenCSelected(false);
                _self.selectUse(1);                
            }

            public onSelectScreenC() {
                let _self = this;
                _self.selectUse(1);
                _self.isScreenBSelected(false);
                _self.isScreenCSelected(true);
            }

            private findFirstOtherAcc(userId: string) {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findOtherSysAccByUserId(userId).done((data: any) => {
                    if (data != null) {
                        if (data) {
                            _self.companyCode6(data.companyCode);
                            _self.userName6(data.userName);
                            _self.enable_C1_1(true);
                        } else {
                            _self.companyCode6("");
                            _self.userName6("");
                            _self.enable_C1_1(true);
                        }                       
                        _self.updateMode();
                    } else {
                        _self.newMode();
                    }
                    $('#focus-C1_4').focus();
                    dfd.resolve();
                })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }


            private findOtherAccByUserId() {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findOtherSysAccByUserId(_self.userIdBeChoosen()).done((data: any) => {
                    if (data) {
                        _self.companyCode6(data.companyCode);
                        _self.userName6(data.userName);
                        _self.enable_C1_1(true);
                    }
                    dfd.resolve();
                })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();

            }

            private SaveOtherAcc(): JQueryPromise<any> {
                let _self = this;
                let otherAcc: SaveOtherSysAccountCommand = new SaveOtherSysAccountCommand(
                    _self.userIdBeChoosen(),
                    _self.companyCode6(),
                    _self.userName6(),
                    1);

                let dfd = $.Deferred<any>();
                service.saveOtherSysAccount(otherAcc)
                    .done((data: any) => {
                        _self.loadUserInfo();
                        _self.updateMode();
                        $('#focus-C1_4').focus();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                         dfd.resolve();
                    })
                    .fail((res: any) => {                                              
                       nts.uk.ui.dialog.bundledErrors(res);      
                       dfd.reject();          
                    });
                return dfd.promise();
            }


            private deleteOtherAcc() {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.removeOtherSysAccount(_self.userIdBeChoosen()).done((data: any) => {
                            _self.loadUserInfo();
                            _self.newMode();
                            _self.companyCode6("");
                            _self.userName6("");
                            _self.enable_C1_1(true);
                            $('#focus-C1_4').focus();
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                       
                    });
                }).ifNo(() => {
                    _self.loadUserInfo();
                    $('#focus-C1_4').focus();
                    return;
                });
            }

        }
    }


    function lockIcon(value: any) {
        let _self = this;
        if (value == 1)
            return "<i class='icon-78 icon'></i>";
        return '';
    }


    class ItemModel {
        personName: string;
        employeeCode: string;
        loginId: string;
        other: number;
        employeeId: string;
        userId: string;
        isSetting: boolean;

        constructor(personName: string, employeeCode: string, loginId: string, employeeId: string, userId: string, isSetting: boolean, other?: number) {
            this.personName = personName;
            this.employeeCode = employeeCode;
            this.loginId = loginId;
            this.employeeId = employeeId;
            this.userId = userId;
            this.isSetting = isSetting;
            this.other = other;

        }
    }
}


































