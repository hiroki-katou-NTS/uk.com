module nts.uk.at.view.kaf007_ref.c.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import ModelDto = nts.uk.at.view.kaf007_ref.shr.viewmodel.ModelDto;
    import ReflectWorkChangeApp = nts.uk.at.view.kaf007_ref.shr.viewmodel.ReflectWorkChangeApp;

    @component({
        name: 'kaf007-b',
        template: '/nts.uk.at.web/view/kaf/007/b/index.html'
    })
    class Kaf007CViewModel extends ko.ViewModel {

        appType: KnockoutObservable<number> = ko.observable(AppType.WORK_CHANGE_APPLICATION);
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        appWorkChange: AppWorkChange;
        approvalReason: KnockoutObservable<string>;
        model: KnockoutObservable<ModelDto> = ko.observable(null);
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        reflectWorkChange: ReflectWorkChangeApp;
        comment1: KnockoutObservable<string> = ko.observable("");
        comment2: KnockoutObservable<string> = ko.observable("");
        isStraightGo: KnockoutObservable<boolean> = ko.observable(false);
        isStraightBack: KnockoutObservable<boolean> = ko.observable(false);
        setupType: number;
        workTypeLst: any[];
        isEdit: KnockoutObservable<boolean> = ko.observable(true);

        created(
            params: {
                appType: any,
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void,
                eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.application = params.application,
                vm.appType = params.appType,
                vm.appWorkChange = new AppWorkChange("", "", "", "", null, null, null, null);
            vm.approvalReason = params.approvalReason;
            vm.reflectWorkChange = new ReflectWorkChangeApp("", 1);
            vm.setupType = null;

            vm.createParamKAF007();

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
        }

        createParamKAF007() {
            let vm = this;
            vm.$blockui('show');

            let command = {
                companyId: vm.$user.companyId,
                appId: vm.application().appID(),
                appDispInfoStartupDto: ko.toJS(vm.appDispInfoStartupOutput)
            };
            return vm.$ajax(API.getWorkchangeByAppID_PC, command)
                .done(res => {
                    console.log(res);
                    if (res) {
                        vm.fetchData(res);
                        vm.printContentOfEachAppDto().opPrintContentOfWorkChange = res;
                    }
                }).fail(err => {
                    vm.handleError(err);
                }).always(() => vm.$blockui('hide'));
        }

        fetchData(params: any): any {
            const vm = this;
            let appWorkChangeDispInfo = params.appWorkChangeDispInfo;
            let appWorkChangeParam = params.appWorkChange;
            vm.model({
                workTypeCD: ko.observable(appWorkChangeParam.opWorkTypeCD),
                workTimeCD: ko.observable(appWorkChangeParam.opWorkTimeCD),
                appDispInfoStartupOutput: ko.observable(appWorkChangeDispInfo.appDispInfoStartupOutput),
                reflectWorkChangeAppDto: ko.observable(appWorkChangeDispInfo.reflectWorkChangeAppDto),
                workTypeLst: appWorkChangeDispInfo.workTypeLst,
                setupType: ko.observable(appWorkChangeDispInfo.setupType),
                predetemineTimeSetting: ko.observable(appWorkChangeDispInfo.predetemineTimeSetting),
                appWorkChangeSet: appWorkChangeDispInfo.appWorkChangeSet
            });

            vm.isEdit(vm.model().appDispInfoStartupOutput().appDetailScreenInfo.outputMode === 1);

            if (appWorkChangeDispInfo.reflectWorkChangeAppDto) {
                vm.reflectWorkChange.companyId = appWorkChangeDispInfo.reflectWorkChangeAppDto.companyId;
                vm.reflectWorkChange.whetherReflectAttendance(appWorkChangeDispInfo.reflectWorkChangeAppDto.whetherReflectAttendance);
            }
            vm.getWorkDispName(appWorkChangeDispInfo.workTypeLst,
                appWorkChangeParam.opWorkTypeCD,
                appWorkChangeParam.opWorkTimeCD,
                appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst);
            var lstTimezone = appWorkChangeParam.timeZoneWithWorkNoLst;
            var time1 = _.filter(lstTimezone, ['workNo', 1]);
            var time2 = _.filter(lstTimezone, ['workNo', 2]);

            vm.appWorkChange.startTime1((time1.length > 0) ? time1[0].timeZone.startTime : null);
            vm.appWorkChange.endTime1((time1.length > 0) ? time1[0].timeZone.endTime : null);
            vm.appWorkChange.startTime2((time2.length > 0) ? time2[0].timeZone.startTime : null);
            vm.appWorkChange.endTime2((time2.length > 0) ? time2[0].timeZone.endTime : null);
            vm.isStraightGo(appWorkChangeParam.straightGo == 1);
            vm.isStraightBack(appWorkChangeParam.straightBack == 1);
        }

        getWorkDispName(workTypeLst: any, workTypeCode: string, workTimeCode: string, workTimeLst: any) {
            const vm = this;

            vm.appWorkChange.workTimeCode(workTimeCode);
            vm.appWorkChange.workTypeCode(workTypeCode);
            var dataWorkType = _.filter(workTypeLst, (x) => { return workTypeCode === x.workTypeCode });
            vm.appWorkChange.workTypeName(dataWorkType.length > 0 ? dataWorkType[0].name : vm.$i18n('KAF007_79'));
            if (workTimeCode) {
                var dataWorktTime = _.filter(workTimeLst, (x) => { return workTimeCode === x.worktimeCode });
                vm.appWorkChange.workTimeName(dataWorktTime.length > 0 ? dataWorktTime[0].workTimeDisplayName.workTimeName : vm.$i18n('KAF007_79'));
            } else {
                vm.appWorkChange.workTimeName(null);
            }
        }

        handleError(err: any) {
            const vm = this;
            let param;
            if (err.message && err.messageId) {
                param = { messageId: err.messageId, messageParams: err.parameterIds };
            } else {

                if (err.message) {
                    param = { message: err.message, messageParams: err.parameterIds };
                } else {
                    param = { messageId: err.messageId, messageParams: err.parameterIds };
                }
            }
            vm.$dialog.error(param).then(res => {
                if (err.messageId == 'Msg_197') {
                    vm.reload();
                }
            });
        }


        mounted() {
            const vm = this;
        }

        reload() {
            const vm = this;
            if (vm.appType() === AppType.WORK_CHANGE_APPLICATION) {
                vm.createParamKAF007();
            }
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;

            let timeZone1 = null;
            if (vm.appWorkChange.startTime1() !== null && vm.appWorkChange.endTime1() !== null && ko.toJS(vm.appWorkChange.startTime1) !== "" && ko.toJS(vm.appWorkChange.endTime1) !== "") {
                timeZone1 = {
                    workNo: 1,
                    timeZone: {
                        startTime: vm.appWorkChange.startTime1(),
                        endTime: vm.appWorkChange.endTime1()
                    }
                }
            }

            let timeZone2 = null;
            if (vm.appWorkChange.startTime2() !== null && vm.appWorkChange.endTime2() !== null && ko.toJS(vm.appWorkChange.startTime2) !== "" && ko.toJS(vm.appWorkChange.endTime2) !== "") {
                timeZone2 = {
                    workNo: 2,
                    timeZone: {
                        startTime: vm.appWorkChange.startTime2(),
                        endTime: vm.appWorkChange.endTime2()
                    }
                }
            }

            let timeZoneWithWorkNoLst = [];

            if (timeZone1 !== null && vm.reflectWorkChange.whetherReflectAttendance() === 1 && vm.isEdit && vm.model().setupType() === 0) {
                timeZoneWithWorkNoLst.push(timeZone1);
            }
            if (timeZone2 !== null && vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.managementMultipleWorkCycles && vm.isEdit
                && vm.reflectWorkChange.whetherReflectAttendance() === 1 && vm.model().setupType() === 0) {
                timeZoneWithWorkNoLst.push(timeZone2);
            }

            let appWorkChangeDto = {
                straightGo: vm.isStraightGo() ? 1 : 0,
                straightBack: vm.isStraightBack() ? 1 : 0,
                opWorkTypeCD: vm.model().workTypeCD(),
                opWorkTimeCD: vm.model().workTimeCD(),
                timeZoneWithWorkNoLst: timeZoneWithWorkNoLst
            }

            let command = {
                mode: false,
                companyId: vm.$user.companyId,
                applicationDto: ko.toJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.application),
                appWorkChangeDto: ko.toJS(appWorkChangeDto),
                isError: vm.model().appDispInfoStartupOutput().appDispInfoWithDateOutput.opErrorFlag,
                appDispInfoStartupDto: ko.toJS(vm.model().appDispInfoStartupOutput)
            }

            command.applicationDto.opAppReason = vm.application().opAppReason();
            command.applicationDto.opAppStandardReasonCD = vm.application().opAppStandardReasonCD();
            command.applicationDto.opReversionReason = vm.application().opReversionReason();

            vm.$blockui("show");
            return vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then(isValid => {
                    if (isValid) {
                        if (vm.isEdit && vm.reflectWorkChange.whetherReflectAttendance() === 1 && vm.model().setupType() === 0) {
                            return vm.$validate('.nts-input');
                        }
                        return true;
                    }
                })
                .then((isValid) => {
                    if (isValid) {
                        if (!_.isLength(vm.appWorkChange.startTime2()) && _.isLength(vm.appWorkChange.endTime2())) {
                            vm.$errors({ '#time2Start': { messageId: 'Msg_1956' } });
                            return false;
                        }
                        if (_.isLength(vm.appWorkChange.startTime2()) && !_.isLength(vm.appWorkChange.endTime2())) {
                            vm.$errors({ '#time2End': { messageId: 'Msg_1956' } });
                            return false;
                        }
                        return true;
                    }
                })
                .then(result => {
                    if (result) {
                        return vm.$ajax(API.checkBeforeRegister, command);
                    }
                }).then(res => {
                    if (res) {
                        return vm.handleConfirmMessage(_.clone(res.confirmMsgLst), vm);
                    }
                }).then((result) => {
                    if (result) {
                        let param = {
                            mode: false,
                            companyId: vm.$user.companyId,
                            applicationDto: ko.toJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.application),
                            appWorkChangeDto: ko.toJS(appWorkChangeDto),
                            isMail: vm.model().appDispInfoStartupOutput().appDispInfoNoDateOutput.mailServerSet,
                            appDispInfoStartupDto: ko.toJS(vm.model().appDispInfoStartupOutput)
                        };
                        param.applicationDto.opAppReason = ko.toJS(vm.application().opAppReason);
                        param.applicationDto.opReversionReason = ko.toJS(vm.application().opReversionReason);
                        param.applicationDto.opAppStandardReasonCD = ko.toJS(vm.application().opAppStandardReasonCD);

                        return vm.$ajax(API.register, param)
                    }
                }).done(result => {
                    if (result) {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => vm.reload());
                    }
                })
                .fail(err => {
                    let messageId: any, messageParams: any;
                    if (err.errors) {
                        let errors = err.errors;
                        messageId = errors[0].messageId;

                    } else {
                        messageId = err.messageId;
                        messageParams = [err.parameterIds.join('、')];
                    }
                    return vm.$dialog.error({ messageId: messageId, messageParams: messageParams })
                        .then(() => {
                            if (messageId === "Msg_197") {
                                location.reload();
                            }
                        });
                })
                .always(() => vm.$blockui("hide"));
        }

        handleConfirmMessage(listMes: any, vmParam: any): any {
            const vm = this;

            return new Promise((resolve: any) => {
                if (_.isEmpty(listMes)) {
                    resolve(true);
                }
                let msg = listMes[0].value;

                return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
                    .then((value) => {
                        if (value === 'yes') {
                            return vm.handleConfirmMessage(listMes, vmParam);
                        } else {
                            resolve(false);
                        }
                    })
            });
        }

        registerData(params: any) {
            let vm = this;

            return vm.$ajax(API.register, params);

        }

        dispose() {
            const vm = this;

        }
    }

    const API = {
        getWorkchangeByAppID_PC: "at/request/application/workchange/getWorkchangeByAppID_PC",
        updateworkchange: "at/request/application/workchange/updateworkchange",
        checkBeforeRegister: "at/request/application/workchange/checkBeforeRegisterPC",
        register: "at/request/application/workchange/addworkchange"
    }
}