module nts.uk.at.view.kaf011.b.viewmodel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import confirm = nts.uk.ui.dialog.confirm;
    import alError = nts.uk.ui.dialog.alertError;
    import appcommon = nts.uk.at.view.kaf000.shr.model;

    export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {

        screenModeNew: KnockoutObservable<boolean> = ko.observable(false);

        employeeName: KnockoutObservable<string> = ko.observable('');

        drawalReqSet: KnockoutObservable<common.DrawalReqSet> = ko.observable(new common.DrawalReqSet(null));

        recWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        absWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        prePostText: KnockoutObservable<string> = ko.observable('');

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);

        appComSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appReasons = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        showReason: KnockoutObservable<number> = ko.observable(0);

        employeeID: KnockoutObservable<string> = ko.observable('');

        displayPrePostFlg: KnockoutObservable<number> = ko.observable(0);

        appTypeSet: KnockoutObservable<common.AppTypeSet> = ko.observable(new common.AppTypeSet(null));

        firstLoad: KnockoutObservable<boolean> = ko.observable(true);
        
        remainDays: KnockoutObservable<number> = ko.observable(null);
        appCur: any = null;
        kaf011ReasonIsEditable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.editable() ? this.appTypeSet().displayAppReason() != 0 : false;
            });
        kaf011FixedReasonIsEditable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.editable() ;
            });
        kdl003BtnEnable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.editable();
            });
        recTimeSwitchEnable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.editable();
            });
        recTimeInputEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.editable() ? this.drawalReqSet().permissionDivision() != 0 : false;
        });
        absKdl003BtnEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.editable() ? this.absWk().changeWorkHoursType() : false;
        });
        absTimeInputEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.editable() ? this.absWk().enableWkTime() == true : false;
        });
        changeWorkHoursTypeEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.editable();
        });
        requiredReason: KnockoutObservable<boolean> = ko.observable(false);
        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            let self = this;
            self.appCur = currentApp;
            self.startPage(self.appID());
            
            self.prePostSelectedCode.subscribe((newCd) => {
                let prePostItem = _.find(self.prePostTypes(), { 'code': newCd });
                if (prePostItem) {
                    self.prePostText(prePostItem.text);
                }
            });

            self.recWk().wkTimeCD.subscribe((newWkTimeCD) => {
                if (newWkTimeCD && nts.uk.ui._viewModel) {
                    $('#recTimeBtn').ntsError("clear");
                }
            });

            self.absWk().wkTimeCD.subscribe((newWkTimeCD) => {
                if (newWkTimeCD && nts.uk.ui._viewModel) {
                    $('#absTimeBtn').ntsError("clear");
                }
            });

            self.absWk().wkTypeCD.subscribe((newWkTypeCd) => {
                if (nts.uk.ui._viewModel) {
                    $('.absWkingTime').ntsError("clear");
                }
            });
            
        }

        genSaveCmd(): common.ISaveHolidayShipmentCommand {
            let self = this, returnCmd: common.ISaveHolidayShipmentCommand = {
                recCmd: ko.mapping.toJS(self.recWk()),
                absCmd: ko.mapping.toJS(self.absWk()),
                comType: self.appComSelectedCode(),
                usedDays: 1,
                appCmd: {
                    appReasonText: self.appReasonSelectedID(),
                    applicationReason: self.reason(),
                    prePostAtr: self.prePostSelectedCode(),
                    employeeID: self.employeeID(),
                    appVersion: self.version,
                    remainDays: self.remainDays()
                },
                screenB: true,
                isNotSelectYes: true
            }, selectedReason = self.appReasonSelectedID() ? _.find(self.appReasons(), { 'reasonID': self.appReasonSelectedID() }) : null;
            returnCmd.absCmd.changeWorkHoursType = returnCmd.absCmd.changeWorkHoursType ? 1 : 0;
            if (selectedReason) {
                returnCmd.appCmd.appReasonText = selectedReason.reasonTemp;
            }
            return returnCmd;

        }

        enablePrepost() {
            let self = this;
            return self.screenModeNew() && self.appTypeSet().canClassificationChange() != 0;
        }
        showAppReason(): boolean {
            let self = this;
            if (self.screenModeNew()) {
                return self.appTypeSet().displayAppReason() != 0;
            } else {
                return self.appTypeSet().displayAppReason() != 0 || self.appTypeSet().displayFixedReason() != 0;
            }

        }
        update() {
            let self = this,
                saveCmd = self.genSaveCmd();
            self.validateControl();
            if (nts.uk.ui.errors.hasError()) { return; }
            
            /*
            let isCheckReasonError = !self.checkReason();
            if (isCheckReasonError) {
                return;
            }
            */

            block.invisible();
            service.update(saveCmd).done(() => {
                dialog({ messageId: 'Msg_15' }).then(function() {
                    self.startPage(self.appID(), true);
                });
            }).fail((error) => {
                alError({ messageId: error.messageId, messageParams: error.parameterIds });

            }).always(() => {
                block.clear();
            });
        }
        
        getBoxReason(){
            var self = this;
            let result = "";
            let selectedReason = self.appReasonSelectedID() ? _.find(self.appReasons(), { 'reasonID': self.appReasonSelectedID() }) : null
            if (selectedReason) {
                result = selectedReason.reasonTemp;
            }
            return result;
        }
    
        getAreaReason(){
            var self = this;
            return self.reason(); 
        }
        
        resfreshReason(appReason: string){
            var self = this;
            self.appReasonSelectedID('');   
            self.reason(appReason); 
        }

        checkReason(): boolean {
            let self = this,
                appReason = self.getReason();
            let appReasonError = !nts.uk.at.view.kaf000.shr.model.CommonProcess.checkAppReason(true, self.appTypeSet().displayFixedReason() != 0, self.appTypeSet().displayAppReason() != 0, appReason);
            if (appReasonError) {
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_115' });
                return false;
            }
            let isCheckLengthError: boolean = !nts.uk.at.view.kaf000.shr.model.CommonProcess.checklenghtReason(appReason, "#appReason");
            if (isCheckLengthError) {
                return false;
            }
            return true;

        }
        validateControl() {
            $(".kaf-011-combo-box ,.nts-input").trigger("validate");
        }
        getReason(): string {
            let appReason = '',
                self = this,
                inputReasonID = self.appReasonSelectedID(),
                inputReasonList = self.appReasons(),
                detailReason = self.reason();
            let inputReason: string = '';
            if (!nts.uk.util.isNullOrEmpty(inputReasonID)) {
                inputReason = _.find(inputReasonList, { 'reasonID': inputReasonID }).reasonTemp;
            }
            if (!nts.uk.util.isNullOrEmpty(inputReason) && !nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = inputReason + ":" + detailReason;
            } else if (!nts.uk.util.isNullOrEmpty(inputReason) && nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = inputReason;
            } else if (nts.uk.util.isNullOrEmpty(inputReason) && !nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = detailReason;
            }
            return appReason;
        }

        startPage(appID: string, isReload?: boolean): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred(),
                appParam = { appID: appID };
            block.invisible();
//            service.findById(appParam).done((data) => {
            service.startPageBRefactor(appParam).done((data) => {
                self.setDataFromStart(data);
                self.absWk().wkTypeCD.valueHasMutated();  
                if (isReload) {
                    self.start(data.application.applicationDate, false).done(() => {
                        nts.uk.ui.block.clear();
                    });
                }
                dfd.resolve();
            }).fail((error) => {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId, messageParams: error.parameterIds }).then(function() {
                    nts.uk.ui.block.clear();
                    if (error.messageId === "Msg_198" || error.messageId == 'Msg_426') {
                        appcommon.CommonProcess.callCMM045();
                    } else {
                        nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");        
                    }
                });
                dfd.reject();
            }).always(() => {
                block.clear();
            });

            return dfd.promise();

        }
        setDataFromStart(data: common.IHolidayShipment) {
            let self = this;
            if (data) {
                let appDispInfoStartupOutput = data.appDispInfoStartup,
                    appDispInfoNoDateOutput = appDispInfoStartupOutput.appDispInfoNoDateOutput,
                    appDispInfoWithDateOutput = appDispInfoStartupOutput.appDispInfoWithDateOutput,
                    listAppTypeSet = appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting,
                    appTypeSet = _.find(listAppTypeSet, o => o.appType == 2),
                    appWorkChangeDto = data.appWorkChange,
                    appDetailScreenInfo = appDispInfoStartupOutput.appDetailScreenInfo,
                    applicationDto = appDetailScreenInfo.application;
                let appType = applicationDto.applicationType
                if (appType != 0) {
                    let paramLog = {
                        programId: 'KAF000',
                        screenId: 'B',
                        queryString: 'apptype=' + appType
                    };
                    nts.uk.at.view.kaf000.b.service.writeLog(paramLog);
                }
                self.inputCommandEvent().version = applicationDto.version;
                self.version = applicationDto.version;
                self.dataApplication(applicationDto);
                self.appType(applicationDto.applicationType);
                self.approvalRootState(ko.mapping.fromJS(appDetailScreenInfo.approvalLst)());
                self.displayReturnReasonPanel(!nts.uk.util.isNullOrEmpty(applicationDto.reversionReason));
                if (self.displayReturnReasonPanel()) {
                    let returnReason = applicationDto.reversionReason;
                    $("#returnReason").html(returnReason.replace(/\n/g, "\<br/>"));
                }
                self.reasonToApprover(appDetailScreenInfo.authorComment);
                self.setControlButton(
                    appDetailScreenInfo.user,
                    appDetailScreenInfo.approvalATR,
                    appDetailScreenInfo.reflectPlanState,
                    appDetailScreenInfo.authorizableFlags,
                    appDetailScreenInfo.alternateExpiration,
                    data.loginInputOrApproval);
                self.editable(appDetailScreenInfo.outputMode == 0 ? false : true);
                
                
                self.remainDays(data.remainingHolidayInfor.remainDays);
                self.drawalReqSet(new common.DrawalReqSet(data.drawalReqSet || null));
                self.employeeName(data.employeeName || null);
                self.employeeID(data.application.employeeID || null);
                self.displayPrePostFlg(data.appDispInfoStartup.appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.prePostAtrDisp == 1);
                self.appTypeSet(new common.AppTypeSet(data.appTypeSet || null));
                self.recWk().setWkTypes(data.applicationForHoliday?data.applicationForHoliday.workTypeList : []);
                self.absWk().setWkTypes(data.applicationForWorkingDay?data.applicationForWorkingDay.workTypeList : []);
                if (data.application) {
                    self.setDataCommon(data);
                }

                if (data.absApp && data.recApp) {
                    self.setDataBothApp(data);

                } else {
                    if (data.recApp) {
                        self.setDataApp(self.recWk(), data.recApp, 1);
                        self.absWk(new common.AppItems());
                    }
                    if (data.absApp) {
                        self.setDataApp(self.absWk(), data.absApp, 2);
                        self.recWk(new common.AppItems());
                    }

                }
                
                self.requiredReason(data.appDispInfoStartup.appDispInfoNoDateOutput.requestSetting.applicationSetting.appLimitSetting.requiredAppReason == 1 ? true : false);
            }
            self.firstLoad(false);
        }

        setDataCommon(data) {
            let self = this,
                app = data.application;
            self.appReasons(data.appDispInfoStartup.appDispInfoNoDateOutput.appReasonLst || []);
            self.appReasonSelectedID('');
            self.prePostSelectedCode(app.prePostAtr);
            self.prePostSelectedCode.valueHasMutated();
            //self.showReason(data.applicationSetting.appReasonDispAtr);
            self.reason(data.application.applicationReason);
        }

        setDataBothApp(data) {
            let self = this;
            self.appComSelectedCode(0);
            self.setDataApp(self.absWk(), data.absApp);
            self.setDataApp(self.recWk(), data.recApp);
        }

        removeAbs() {
            let self = __viewContext['viewModel'],
                removeCmd = self.getHolidayCmd();
            confirm({ messageId: 'Msg_18' }).ifYes(function() {
                block.invisible();
                service.removeAbs(removeCmd).done(function(data) {
                    self.reBinding(self.listAppMeta, self.appCur, false);
                }).fail(function(res: any) {
                    alError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                    });
                }).always(() => {
                    block.clear();
                });
            });
        }

        cancelAbs() {
            let self = __viewContext['viewModel'],
                cancelCmd = self.getHolidayCmd();
            confirm({ messageId: 'Msg_249' }).ifYes(function() {
                block.invisible();
                service.cancelAbs(cancelCmd).done(function(data) {
                    self.reBinding(self.listAppMeta, self.appCur, false);
                }).fail(function(res: any) {
                    alError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                    });
                }).always(() => {
                    block.clear();
                });
            });
        }


        getHolidayCmd() {
            let self = this,
                shipmentCmd;

            shipmentCmd = {
                absAppID: self.absWk().appID(),
                recAppID: null,
                appVersion: self.version,
                memo: ""
            }

            return shipmentCmd;

        }




        setDataApp(control: common.AppItems, data, comType?) {
            let self = this;
            if (data) {
                control.wkTypeCD(data.workTypeCD);
                control.wkTimeCD(data.workTimeCD);
                control.changeWorkHoursType(data.changeWorkHoursType);
                control.appDate(data.appDate);
                control.appID(data.appID);
                control.wkTimeName(data.workTimeName);
                if (data.wkTime1) {
                    control.wkTime1().startTime(data.wkTime1.startTime);
                    control.wkTime1().endTime(data.wkTime1.endTime);
                    control.wkTime1().startType(data.wkTime1.startUseAtr);
                    control.wkTime1().endType(data.wkTime1.endUseAtr);

                }

                if (data.timeZoneUseDtos && data.timeZoneUseDtos.length) {
                    let timeZone1 = data.timeZoneUseDtos[0];
                    control.wkTime1().startTimeDisplay(timeZone1.startTime);
                    control.wkTime1().endTimeDisplay(timeZone1.endTime);
                }
                if (comType) {
                    self.appComSelectedCode(comType);
                }
                if(control.wkTimeCD() != null){
                    control.updateWorkingText();
                }
            }
        }

    }


}