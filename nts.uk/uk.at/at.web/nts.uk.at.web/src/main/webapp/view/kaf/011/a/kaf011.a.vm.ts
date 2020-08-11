module nts.uk.at.view.kaf011.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    export class ViewModel {
        screenModeNew: KnockoutObservable<boolean> = ko.observable(true);
        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);
        appComSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appDate: KnockoutObservable<Date> = ko.observable(moment().toDate());

        recWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        absWk: KnockoutObservable<common.AppItems> = ko.observable(new common.AppItems());

        appReasons = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        employeeID: KnockoutObservable<string> = ko.observable('');

        employeeName: KnockoutObservable<string> = ko.observable('');

        checkBoxValue: KnockoutObservable<boolean> = ko.observable(false);
        enableSendMail: KnockoutObservable<boolean> = ko.observable(false);

        drawalReqSet: KnockoutObservable<common.DrawalReqSet> = ko.observable(new common.DrawalReqSet(null));

        showReason: KnockoutObservable<number> = ko.observable(0);

        displayPrePostFlg: KnockoutObservable<number> = ko.observable(0);

        appTypeSet: KnockoutObservable<common.AppTypeSet> = ko.observable(new common.AppTypeSet(null));

        employeeList = ko.observableArray([]);

        selectedEmployee = ko.observable(null);

        totalEmployeeText = ko.observable('');

        transferDate: KnockoutObservable<string> = ko.observable(null);

        firstLoad: KnockoutObservable<boolean> = ko.observable(false);

        remainDays: KnockoutObservable<number> = ko.observable('');

        requiredReason: KnockoutObservable<boolean> = ko.observable(false);

        kaf011ReasonIsEditable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.appTypeSet().displayAppReason() != 0;
            });
        kaf011FixedReasonIsEditable: KnockoutObservable<boolean> = ko.computed(() => {
                return true;
            });
        kdl003BtnEnable: KnockoutObservable<boolean> = ko.observable(true);
        recTimeSwitchEnable: KnockoutObservable<boolean> = ko.observable(true);
        recTimeInputEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.drawalReqSet().permissionDivision() == 1;
        });
        absKdl003BtnEnable: KnockoutObservable<boolean> = ko.computed(() => {
                return this.absWk().changeWorkHoursType();
            });
        absTimeInputEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return this.absWk().enableWkTime() == true;
        });
        changeWorkHoursTypeEnable: KnockoutObservable<boolean> = ko.computed(() => {
            return true;
        });
        displayInforWhenStarting: any = null;
        constructor() {
            let self = this,
                recWk = ko.unwrap(self.recWk),
                absWk = ko.unwrap(self.absWk);

            recWk.appDate.subscribe(newDate => {
                let absDate = self.absWk().appDate(),
                    recDate = self.recWk().appDate(),
                    changeDateParam = {
                        workingDate: self.appComSelectedCode() != 2 ? recDate : null,
                        holidayDate: self.appComSelectedCode() != 1 ? absDate : null,
                        displayInforWhenStarting: self.displayInforWhenStarting
                    }

                if (!newDate || !self.screenModeNew() || $("#recDatePicker").ntsError("hasError") || $("#absDatePicker").ntsError("hasError")) { return; }
                block.invisible();
                service.changeWorkingDateRefactor(changeDateParam).done((data: IHolidayShipment) => {
                    self.displayInforWhenStarting.appDispInfoStartup = data.appDispInfoStartup;
                    self.recWk().setWkTypes(data.applicationForWorkingDay.workTypeList || []);
                    self.absWk().setWkTypes(data.applicationForHoliday.workTypeList || []);
                    if (self.displayPrePostFlg() == 0) {
                        self.prePostSelectedCode(self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.prePostAtr);
                    }
                    self.kaf000_a.initData({
                        errorFlag: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.errorFlag,
                        listApprovalPhaseStateDto: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.listApprovalPhaseState,
                        isSystemDate: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoNoDateOutput.requestSetting.applicationSetting.recordDate
                    });
                }).fail((error) => {
                    alError({ messageId: error.messageId, messageParams: error.parameterIds });
                }).always(() => {
                    block.clear();
                });
            });

            absWk.appDate.subscribe(newDate => {
                let absDate = self.absWk().appDate(),
                    recDate = self.recWk().appDate(),
                    changeDateParam = {
                        workingDate: self.appComSelectedCode() != 2 ? recDate : null,
                        holidayDate: self.appComSelectedCode() != 1 ? absDate : null,
                        displayInforWhenStarting: self.displayInforWhenStarting
                    }

                if (!newDate || !self.screenModeNew() || $("#recDatePicker").ntsError("hasError") || $("#absDatePicker").ntsError("hasError")) { return; }
                block.invisible();
                service.changeHolidayDateRefactor(changeDateParam).done((data: IHolidayShipment) => {
                    self.displayInforWhenStarting.appDispInfoStartup = data.appDispInfoStartup;
                    self.recWk().setWkTypes(data.applicationForWorkingDay.workTypeList || []);
                    self.absWk().setWkTypes(data.applicationForHoliday.workTypeList || []);
                    if (self.displayPrePostFlg() == 0) {
                        self.prePostSelectedCode(self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.prePostAtr);
                    }
                    self.kaf000_a.initData({
                        errorFlag: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.errorFlag,
                        listApprovalPhaseStateDto: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.listApprovalPhaseState,
                        isSystemDate: self.displayInforWhenStarting.appDispInfoStartup.appDispInfoNoDateOutput.requestSetting.applicationSetting.recordDate
                    });
                }).fail((error) => {
                    alError({ messageId: error.messageId, messageParams: error.parameterIds });
                }).always(() => {
                    block.clear();
                });
            });

            self.appComSelectedCode.subscribe((newCode) => {
                self.absWk().appDate(null);
                self.recWk().appDate(null);
                setTimeout(()=>self.clearTextboxError(), 100);

            });
            self.appReasons.subscribe((appReasons) => {
                if (appReasons) {
                    let defaultReason = _.find(appReasons, { 'defaultFlg': 1 }),
                        displayFixedReason = self.appTypeSet().displayFixedReason() != 0;

                    if (defaultReason && displayFixedReason) {
                        self.appReasonSelectedID(defaultReason.reasonID);
                    }
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

            self.absWk().wkTypeCD.subscribe((newWkTypeCd)=>{
                if (nts.uk.ui._viewModel) {
                    $('.absWkingTime').ntsError("clear");
                }
            });

            self.employeeList.subscribe((datas) => {
                if (datas.length) {
                    self.totalEmployeeText(text('KAF011_79', [datas.length]));
                    self.selectedEmployee(datas[0]);
                }

            });
        }

        clearTextboxError() {
            nts.uk.ui.errors.clearAll();
            return;
        }
        enablePrepost() {
            let self = this;
            return self.screenModeNew() && self.appTypeSet().canClassificationChange() != 0;
        }

        start(): JQueryPromise<any> {
            block.invisible();
            var self = this,
                dfd = $.Deferred(),
                employeeIDs = [],
                transferDate;

            __viewContext.transferred.ifPresent(data => {
                employeeIDs = data.employeeIds;
                if (!nts.uk.util.isNullOrUndefined(data.appDate)) {
                    transferDate = moment(data.appDate).toDate();
                    self.appDate(transferDate);

                    self.transferDate(transferDate);
                }
            });
            let startParam = {
                sIDs: employeeIDs,
                appDate: nts.uk.util.isNullOrUndefined(transferDate) ? [] : [transferDate]
            };

            service.startPageARefactor(startParam).done((data: any) => {
                self.setDataFromStart(data);
                $("#fixed-table").ntsFixedTable({ width: 100 });
                dfd.resolve(data);
            }).fail((error) => {
                alError({ messageId: error.messageId, messageParams: error.parameterIds }).then(function(){
                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                    nts.uk.ui.block.clear();
                });
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }



        clearData() {
            let self = this;
            self.recWk().wkTime1(new common.WorkingHour());
            self.recWk().wkTime2(new common.WorkingHour());
            self.recWk().wkTimeName('');
            self.recWk().wkTimeCD('');
            self.recWk().wkText('');
        }

        openCMM018() {
            let self = this;
            jump("com", "/view/cmm/018/a/index.xhtml", { screen: "Application", employeeId: self.employeeID() });
        }

        setDataFromStart(data: any) {
            let self = this,
                appDispInfoNoDateOutput = data.appDispInfoStartup.appDispInfoNoDateOutput,
                appDispInfoWithDateOutput = data.appDispInfoStartup.appDispInfoWithDateOutput,
                listAppTypeSet = appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting,
                appTypeSet = _.find(listAppTypeSet, o => o.appType == 10),
                applicationForWorkingDay = data.applicationForWorkingDay,
                applicationForHoliday = data.applicationForHoliday;
            self.displayInforWhenStarting = data;
            if (data) {
                self.remainDays(data.remainingHolidayInfor.remainDays);
                if(data.appDispInfoStartup.appDispInfoNoDateOutput.employeeInfoLst[0].sid != __viewContext.user.employeeId){
                    self.employeeList(_.map(data.appDispInfoStartup.appDispInfoNoDateOutput.employeeInfoLst, (emp) => { return { sid: emp.sid, code: emp.scd, name: emp.bussinessName } }));
                }
                self.employeeName(appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
                self.prePostSelectedCode(appDispInfoWithDateOutput.prePostAtr);
                self.appTypeSet(new common.AppTypeSet(appTypeSet || null));
                self.recWk().setWkTypes(applicationForWorkingDay.workTypeList || []);
                self.absWk().setWkTypes(applicationForHoliday.workTypeList || []);
                self.appReasons(appDispInfoNoDateOutput.appReasonLst || []);
                self.employeeID(appDispInfoNoDateOutput.employeeInfoLst[0].sid);
                self.checkBoxValue(appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.manualSendMailAtr == 1 ? true : false);
                self.enableSendMail(!appTypeSet.sendMailWhenRegister);
                self.drawalReqSet(new common.DrawalReqSet(data.drawalReqSet || null));
                // self.showReason(data.applicationSetting.appReasonDispAtr);
                self.displayPrePostFlg(appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.prePostAtrDisp == 1 ? true : false);
                self.recWk().wkTimeCD(applicationForWorkingDay.selectionWorkTime || null);
                self.recWk().wkTimeName(self.getWorkTimeName(self.recWk().wkTimeCD(), appDispInfoWithDateOutput.workTimeLst));
                self.requiredReason(appDispInfoNoDateOutput.requestSetting.applicationSetting.appLimitSetting.requiredAppReason);
                self.recWk().workTimeCDs(_.map(appDispInfoWithDateOutput.workTimeLst, o => {return {
                    workTypeCode: o.worktimeCode, name: o.workTimeDisplayName.workTimeName} })  || null);
            }
        }

        getWorkTypeName(code, workTypeLst) {
            let currentWorkType = _.find(workTypeLst, o => o.workTypeCode == code);
            if(nts.uk.util.isNullOrUndefined(currentWorkType)) {
                return text("マスタ未登録");
            } else {
                return currentWorkType.name;
            }
        }

        getWorkTimeName(code, workTimeLst) {
            let currentWorkTime = _.find(workTimeLst, o => o.worktimeCode == code);
            if(nts.uk.util.isNullOrUndefined(currentWorkTime)) {
                return text("マスタ未登録");
            } else {
                return currentWorkTime.workTimeDisplayName.workTimeName;
            }
        }

        validateControl() {
            let self = this,
                isRecError = self.checkRecTime(),
                isAbsError = self.checkAbsTime();
            $(".kaf-011-combo-box ,.nts-input,#swb_pre_post_type").trigger("validate");
            let isKibanControlError = nts.uk.ui.errors.hasError();
            return isRecError || isAbsError || isKibanControlError;

        }
        checkRecTime() {
            let self = this,
                comCode = self.appComSelectedCode(),
                isRecCreate = comCode == 1 || comCode == 0,
                isError = false;
            if (isRecCreate) {
                let wkTimeCd = self.recWk().wkTimeCD();
                if (!wkTimeCd) {
                    $('#recTimeBtn').ntsError('set', { messageId: 'MsgB_2', messageParams: [text('KAF011_30')] });
                    isError = true;
                }
            }
            return isError;

        }
        checkAbsTime() {
            let self = this,
                comCode = self.appComSelectedCode(),
                isAbsCreate = comCode == 2 || comCode == 0,
                isError = false;
            if (isAbsCreate) {
                let isUseWkTime = self.absWk().changeWorkHoursType();
                if (isUseWkTime) {
                    let wkTimeCd = self.absWk().wkTimeCD();
                    if (!wkTimeCd) {
                        $('#absTimeBtn').ntsError('set', { messageId: 'MsgB_2', messageParams: [text('KAF011_30')] });
                        isError = true;
                    }
                }
            }
            return isError;
        }
        genSaveCmd(): common.ISaveHolidayShipmentCommand {
            let self = this,
                returnCmd = common.ISaveHolidayShipmentCommand = {
                    recCmd: ko.mapping.toJS(self.recWk()),
                    absCmd: ko.mapping.toJS(self.absWk()),
                    comType: self.appComSelectedCode(),
                    usedDays: 1,
                    appCmd: {
                        appReasonText: '',
                        applicationReason: self.reason(),
                        prePostAtr: self.prePostSelectedCode(),
                        employeeID: self.employeeList()[0] ? self.employeeList()[0].sid : null,
                        appVersion: 0,
                        remainDays: self.remainDays()
                    },
                    screenB: false,
                    isNotSelectYes: true
                }, selectedReason = self.appReasonSelectedID() ? _.find(self.appReasons(), { 'reasonID': self.appReasonSelectedID() }) : null;
            if (selectedReason) {
                returnCmd.appCmd.appReasonText = selectedReason.reasonTemp;
            }
            returnCmd.absCmd.changeWorkHoursType = returnCmd.absCmd.changeWorkHoursType ? 1 : 0;
            return returnCmd;

        }
        register() {
            let self = this,
                saveCmd = self.genSaveCmd();

            let isCheckLengthError: boolean = !appcommon.CommonProcess.checklenghtReason(self.getReason(), "#appReason");
            if (isCheckLengthError) {
                return;
            }

            let isControlError = self.validateControl();
            if (isControlError) { return; }

            // let isCheckReasonError = !self.checkReason(),
            let checkBoxValue = self.checkBoxValue();
            // if (isCheckReasonError) { return; }
            block.invisible();
            // saveCmd.checkOver1Year = true;
            saveCmd.displayInforWhenStarting = self.displayInforWhenStarting;
            service.checkBeforeRegister(saveCmd).done((data) => {
                self.processConfirmMsg(saveCmd, data, 0);
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { nts.uk.ui.block.clear(); });
            });
        }
        saveDone(data, checkBoxValue) {
            dialog({ messageId: 'Msg_15' }).then(function() {
                if (data.autoSendMail) {
                    appcommon.CommonProcess.displayMailResult(data);
                } else {
                    if (checkBoxValue) {
                        appcommon.CommonProcess.openDialogKDL030(data.appID);
                    } else {
                        location.reload();
                    }
                }
            });
        }

        showAppReason(): boolean {
            let self = this;
            if (self.screenModeNew()) {
                return self.appTypeSet().displayAppReason() != 0;
            } else {
                return self.appTypeSet().displayAppReason() != 0 || self.appTypeSet().displayFixedReason() != 0;
            }

        }

        checkReason(): boolean {
            let self = this,
                appReason = self.getReason();
            let appReasonError = !appcommon.CommonProcess.checkAppReason(true, self.appTypeSet().displayFixedReason() != 0, self.appTypeSet().displayAppReason() != 0, appReason);
            if (appReasonError) {
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_115' });
                return false;
            }
            let isCheckLengthError: boolean = !appcommon.CommonProcess.checklenghtReason(appReason, "#appReason");
            if (isCheckLengthError) {
                return false;
            }
            return true;
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

        openKDL009() {
            let self = this;
            let lstid = [];
            _.each(self.employeeList(), function(emp) {
                lstid.push(emp.sid);
            });
            let data = {
                employeeIds: lstid.length > 0 ? lstid : [self.employeeID()],
                baseDate: moment(new Date()).format("YYYYMMDD")
            }
            setShared('KDL009_DATA', data);
            if (data.employeeIds.length > 1) {
                modal("/view/kdl/009/a/multi.xhtml");
            } else {
                modal("/view/kdl/009/a/single.xhtml");
            }
        }

        openTestKDL009Multi() {
            let self = this;
            let lstid = [];
            _.each(self.employeeList(), function(emp) {
                lstid.push(emp.sid);
            });
            const testArray = [
                'd3191da9-b6ad-475b-b219-3d961fb86e13',
                'd3191da9-b6ad-475b-b219-3d961fb86e13',
                '0117dcfc-200b-46e8-a734-d87f1d563b82',
                'ba4d5d23-6ac6-4c88-ae4a-758b479d3206',
                '67107229-64e2-4335-9d1a-94ad48cd0476',
                '62688cef-ed10-42bb-b63f-2c5a0adfe68c',
                'f0cbaa71-5af6-44e2-9f37-cd71b3fd07da',
                'f0cbaa71-5af6-44e2-9f37-cd71b3fd07da',
                '184c5ba0-2840-47de-9421-2f5dd62ce991',
                '2270eaf5-ff29-4253-bf6a-4c761b8c6fd4',
                '2e2d0f5c-82d7-4da4-87c8-52afd63b0338',
                '8e5e1421-88c7-4ff4-b10f-9ff95cbbaa2f',
                'f2a989d8-cc68-4b77-a13a-54217e3b85e3',
                'da830ec1-c347-4cdc-8aaf-74dd14c66f74',
                '2270eaf5-ff29-4253-bf6a-4c761b8c6fd4',
                '1ea599bd-04b1-4973-8e85-ca609c1e8443',
                '8e5e1421-88c7-4ff4-b10f-9ff95cbbaa2f',
                '09b8c28d-31c0-4fc2-a66a-e337172449d4',
                '8e5e1421-88c7-4ff4-b10f-9ff95cbbaa2f',
                '335f20c9-9740-43c3-87f2-80e5fa6aa7d5',
                '8e5e1421-88c7-4ff4-b10f-9ff95cbbaa2f',
                '1ea599bd-04b1-4973-8e85-ca609c1e8443',
                '2270eaf5-ff29-4253-bf6a-4c761b8c6fd4',
                '2270eaf5-ff29-4253-bf6a-4c761b8c6fd4',
                '4f0b31c3-9ac3-4cce-a68c-41f524a7619b',
                '3e2d7518-5519-4acb-9787-e10a829356dc',
                '5364eedf-c423-4b52-865b-acb4e3e8ae08',
                '8fb90bfe-5965-494b-bf28-7583792d2949',
                '86b54db7-826d-4084-b4c3-3bb4200fd19f',
                '401d0028-b735-4f86-8714-7374a98e97c5',
                'e4eb5553-ae49-45f8-90bb-bb4f7372a9c4',
                '973b68a2-ef37-4c71-8bba-9ea204c7cbd6',
                '1febc8f1-ac4e-4407-8b5b-344c0c9ae24c',
                '2f4dd263-dad0-439d-95ac-7f8175fd457b',
            ];
            _.each(testArray, function(emp) {
                lstid.push(emp);
            });
            let data = {
                employeeIds: lstid.length > 0 ? lstid : [self.employeeID()],
                baseDate: moment(new Date()).format("YYYYMMDD")
            }
            setShared('KDL009_DATA', data);
            if (data.employeeIds.length > 1) {
                modal("/view/kdl/009/a/multi.xhtml");
            } else {
                modal("/view/kdl/009/a/single.xhtml");
            }
        }

        processConfirmMsg(paramInsert: any, result: any, confirmIndex: number) {
            let self = this;
            let confirmMsgLst = result;
            let confirmMsg = confirmMsgLst[confirmIndex];
            if(_.isUndefined(confirmMsg)) {
                paramInsert.holidayDateLst = result.holidayDateLst;
                service.save(paramInsert).done((data) => {
                    self.saveDone(data, self.checkBoxValue());
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }).always(() => {
                    block.clear();
                    $("#recDatePicker").focus();
                });
                return;
            }

            nts.uk.ui.dialog.confirm({ messageId: confirmMsg.msgID, messageParams: confirmMsg.paramLst }).ifYes(() => {
                self.processConfirmMsg(paramInsert, result, confirmIndex + 1);
            }).ifNo(() => {
                nts.uk.ui.block.clear();
            });
        }
    }

}