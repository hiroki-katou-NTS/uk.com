module nts.uk.at.view.kaf012.a.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;
	import DataModel = nts.uk.at.view.kaf012.shr.viewmodel2.DataModel;
    import AppTimeType = nts.uk.at.view.kaf012.shr.viewmodel2.AppTimeType;

    const API = {
        startNew: "at/request/application/timeLeave/initNewApp",
        changeAppDate: "at/request/application/timeLeave/changeAppDate",
        checkRegister: "at/request/application/timeLeave/checkBeforeRegister",
        register: "at/request/application/timeLeave/register"
    };

    @bean()
    class Kaf012AViewModel extends Kaf000AViewModel {
		appType: KnockoutObservable<number> = ko.observable(AppType.ANNUAL_HOLIDAY_APPLICATION);
		isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
        isSendMail: KnockoutObservable<boolean>;
		application: KnockoutObservable<Application>;
        reflectSetting: KnockoutObservable<any> = ko.observable(null);
        timeLeaveManagement: KnockoutObservable<any> = ko.observable(null);
        timeLeaveRemaining: KnockoutObservable<any> = ko.observable(null);
        leaveType: KnockoutObservable<number> = ko.observable(null);

        applyTimeData: KnockoutObservableArray<DataModel>;
        specialLeaveFrame: KnockoutObservable<number>;

        created(params: AppInitParam) {
            const vm = this;
			let empLst: Array<string> = [],
				dateLst: Array<string> = [];
            vm.isSendMail = ko.observable(false);
            vm.application = ko.observable(new Application(vm.appType()));
			if (!_.isEmpty(params)) {
				if (!_.isEmpty(params.employeeIds)) {
					empLst = params.employeeIds;
				}
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateLst = [paramDate];
					vm.application().appDate(paramDate);
					vm.application().opAppStartDate(paramDate);
                    vm.application().opAppEndDate(paramDate);
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
			}
            vm.applyTimeData = ko.observableArray([]);
            for (let i = 0; i < 5; i++) {
                vm.applyTimeData.push(new DataModel(i, vm.reflectSetting, vm.appDispInfoStartupOutput, vm.application));
            }
            vm.specialLeaveFrame = ko.observable(null);
            vm.$blockui("show");
            vm.loadData(empLst, dateLst, vm.appType())
            .then((loadDataFlag: any) => {
                if(loadDataFlag) {
					vm.application().employeeIDLst(empLst);
                    const appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput);
                    return vm.$ajax(API.startNew, appDispInfoStartupOutput);
                }
            }).then((res: any) => {
                if(res) {
                    vm.reflectSetting(res.reflectSetting);
                    vm.timeLeaveManagement(res.timeLeaveManagement);
                    vm.timeLeaveRemaining(res.timeLeaveRemaining);
                }
            }).fail((error: any) => {
                vm.$dialog.error(error);
            }).always(() => vm.$blockui("hide"));
        }

        mounted() {
            const vm = this;
            vm.application().appDate.subscribe(value => {
                vm.handleChangeAppDate(value);
            });
        }

        handleChangeAppDate(value: string) {
            const vm = this;
            vm.$validate([
                '#kaf000-a-component4 .nts-input'
            ]).then((valid: boolean) => {
                if (valid) {
                    const command = {
                        appDate: new Date(value).toISOString(),
                        appDisplayInfo: {
                            appDispInfoStartupOutput: vm.appDispInfoStartupOutput()
                        }
                    };
                    return vm.$blockui("show").then(() => vm.$ajax(API.changeAppDate, command));
                }
            }).done((res: any) => {
                if (res) {
                    vm.timeLeaveManagement(res.timeLeaveManagement);
                }
            }).fail((error: any) => {
                vm.$dialog.error(error);
            }).always(() => vm.$blockui("hide"));
        }

        public handleConfirmMessage(listMes: any, res: any): any {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                return vm.$dialog.confirm({ messageId: item.msgID, messageParams: item.paramLst }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                            return vm.registerData(res);
                        } else {
                            return vm.handleConfirmMessage(listMes, res);
                        }

                    }
                });
            }
        }

        registerData(goBackApp: any) {
            let vm = this;
            // let paramsRegister = {
                    // companyId: vm.$user.companyId,
                    // applicationDto: vm.applicationTest,
                    // goBackDirectlyDto: goBackApp,
                    // inforGoBackCommonDirectDto: ko.toJS(vm.dataFetch),
                    // mode : vm.mode == 'edit'
            // };

            // return vm.$ajax(API.register, paramsRegister);
                
        }

        register() {
            const vm = this;
            const application = ko.toJS(vm.application);
            const details: Array<any> = [];
            vm.applyTimeData().forEach((row : DataModel) => {
                if (row.display() && row.timeZone.filter(i => !!i.startTime() || i.endTime()).length > 0) {
                    if (row.appTimeType < 4) {
                        details.push({
                            appTimeType: row.appTimeType,
                            timeZones: [{
                                workNo: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.OFFWORK ? 1 : 2,
                                startTime: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.ATWORK2 ? row.timeZone[0].startTime() : null,
                                endTime: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.ATWORK2 ? null : row.timeZone[0].startTime(),
                            }],
                            applyTime: {
                                substituteAppTime: row.applyTime[0].substituteAppTime(),
                                annualAppTime: row.applyTime[0].annualAppTime(),
                                childCareAppTime: row.applyTime[0].childCareAppTime(),
                                careAppTime: row.applyTime[0].careAppTime(),
                                super60AppTime: row.applyTime[0].super60AppTime(),
                                specialAppTime: row.applyTime[0].specialAppTime(),
                                specialLeaveFrameNo: vm.specialLeaveFrame(),
                            }
                        });
                    } else {
                        details.push({
                            appTimeType: AppTimeType.PRIVATE,
                            timeZones: row.timeZone
                                .filter(z => z.appTimeType() == AppTimeType.PRIVATE && z.display() && (!!z.startTime() || !!z.endTime()))
                                .map(z => ({workNo: z.workNo, startTime: z.startTime(), endTime: z.endTime()})),
                            applyTime: {
                                substituteAppTime: row.applyTime[0].substituteAppTime(),
                                annualAppTime: row.applyTime[0].annualAppTime(),
                                childCareAppTime: row.applyTime[0].childCareAppTime(),
                                careAppTime: row.applyTime[0].careAppTime(),
                                super60AppTime: row.applyTime[0].super60AppTime(),
                                specialAppTime: row.applyTime[0].specialAppTime(),
                                specialLeaveFrameNo: vm.specialLeaveFrame(),
                            }
                        });
                        details.push({
                            appTimeType: AppTimeType.UNION,
                            timeZones: row.timeZone
                                .filter(z => z.appTimeType() == AppTimeType.UNION && z.display() && (!!z.startTime() || !!z.endTime()))
                                .map(z => ({workNo: z.workNo, startTime: z.startTime(), endTime: z.endTime()})),
                            applyTime: {
                                substituteAppTime: row.applyTime[1].substituteAppTime(),
                                annualAppTime: row.applyTime[1].annualAppTime(),
                                childCareAppTime: row.applyTime[1].childCareAppTime(),
                                careAppTime: row.applyTime[1].careAppTime(),
                                super60AppTime: row.applyTime[1].super60AppTime(),
                                specialAppTime: row.applyTime[1].specialAppTime(),
                                specialLeaveFrameNo: vm.specialLeaveFrame(),
                            }
                        });
                    }
                }
            });

            console.log(application);
            console.log(details);

            let params = {
                timeDigestAppType: vm.leaveType(),
                application: application,
                details: details,
                timeLeaveAppDisplayInfo: {
                    appDispInfoStartupOutput: vm.appDispInfoStartupOutput(),
                    timeLeaveManagement: vm.timeLeaveManagement(),
                    timeLeaveRemaining: vm.timeLeaveRemaining(),
                    reflectSetting: vm.reflectSetting()
                },
                agentMode: vm.isAgentMode()
            };
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart).toISOString();
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd).toISOString();
            vm.$blockui("show");
            vm.$validate('.nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
                .then(isValid => {
                    if (isValid) {
                        return true;
                    }
                }).then(result => {
                    if (!result) return;
                    return vm.$ajax(API.checkRegister, params);
                }).then(res => {
                    console.log(res);
                    // if (res == undefined) return;
                    // if (_.isEmpty(res)) {
                    //     return vm.registerData(goBackApp);
                    // } else {
                    //     let listTemp = _.clone(res);
                    //     vm.handleConfirmMessage(listTemp, goBackApp);
                    // }
                }).done(result => {
                    if (result != undefined) {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            location.reload();
                        });
                    }
                }).fail(err => {
                    let param;
                    if (err.message && err.messageId) {
                        param = {messageId: err.messageId, messageParams: err.parameterIds};
                    } else {

                        if (err.message) {
                            param = {message: err.message, messageParams: err.parameterIds};
                        } else {
                            param = {messageId: err.messageId, messageParams: err.parameterIds};
                        }
                    }
                    vm.$dialog.error(param);
                }).always(() => vm.$blockui("hide"));
        }

    }

}