module nts.uk.at.view.kaf012.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import DataModel = nts.uk.at.view.kaf012.shr.viewmodel2.DataModel;
    import TimeZone = nts.uk.at.view.kaf012.shr.viewmodel2.TimeZone;
    import AppTimeType = nts.uk.at.view.kaf012.shr.viewmodel2.AppTimeType;

    const API = {
        checkRegister: "at/request/application/timeLeave/checkBeforeRegister",
        updateApplication: "at/request/application/timeLeave/update",
        getDetail: "at/request/application/timeLeave/init"
    };

    const template = `
        <div>
            <div data-bind="component: { name: 'kaf000-b-component1', 
                                        params: {
                                            appType: appType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput	
                                        } }"></div>
            <div data-bind="component: { name: 'kaf000-b-component2', 
                                        params: {
                                            appType: appType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                        } }"></div>
            <div data-bind="component: { name: 'kaf000-b-component3', 
                                        params: {
                                            appType: appType,
                                            approvalReason: approvalReason,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                        } }"></div>
            <div data-bind="component: { name: 'kaf012-share-component1',
                                                    params: {
                                                        reflectSetting: reflectSetting,
                                                        timeLeaveManagement: timeLeaveManagement,
                                                        timeLeaveRemaining: timeLeaveRemaining,
                                                        leaveType: leaveType
                                                    }}"/>
            <div class="table">
                <div class="cell" style="min-width: 825px; padding-right: 10px;">
                    <div data-bind="component: { name: 'kaf000-b-component4',
							params: {
								appType: appType,
								application: application,
								appDispInfoStartupOutput: appDispInfoStartupOutput
							} }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component5', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component6', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf012-share-component2',
                                        params: {
                                            reflectSetting: reflectSetting,
                                            timeLeaveManagement: timeLeaveManagement,
                                            timeLeaveRemaining: timeLeaveRemaining,
                                            leaveType: leaveType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput,
                                            application: application,
                                            applyTimeData: applyTimeData,
                                            specialLeaveFrame: specialLeaveFrame
                                        }}"/>
                    <div data-bind="component: { name: 'kaf000-b-component7', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component8', 
                                                params: {
                                                    appType: appType,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                </div>
                <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
							params: {
								appType: appType,
								application: application,
								appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
							} }"></div>
            </div>
        </div>
    `
    @component({
        name: 'kaf012-b',
        template: template
    })
    class Kaf012BViewModel extends ko.ViewModel {

		appType: KnockoutObservable<number> = ko.observable(AppType.ANNUAL_HOLIDAY_APPLICATION);
        appDispInfoStartupOutput: KnockoutObservable<any>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        mode: KnockoutObservable<String> = ko.observable('edit');
        application: KnockoutObservable<Application>;
        reflectSetting: KnockoutObservable<any> = ko.observable(null);
        timeLeaveManagement: KnockoutObservable<any> = ko.observable(null);
        timeLeaveRemaining: KnockoutObservable<any> = ko.observable(null);
        leaveType: KnockoutObservable<number> = ko.observable(null);

        applyTimeData: KnockoutObservableArray<DataModel> = ko.observableArray([]);
        specialLeaveFrame: KnockoutObservable<number> = ko.observable(null);

        created(
            params: {
				appType: any,
				application: any,
				printContentOfEachAppDto: PrintContentOfEachAppDto,
            	approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void ) => void,
				eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;
			vm.approvalReason = params.approvalReason;
            for (let i = 0; i < 5; i++) {
                vm.applyTimeData.push(new DataModel(i, vm.reflectSetting, vm.appDispInfoStartupOutput, vm.application));
            }
            vm.getAppData();

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));
        }

		reload() {
			const vm = this;
			if(vm.appType() === AppType.ANNUAL_HOLIDAY_APPLICATION) {
				vm.getAppData();
			}
		}

        getAppData() {
            let vm = this;
            vm.$blockui('show');
			if (ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo) {
                let mode = ko.toJS(vm.appDispInfoStartupOutput).appDetailScreenInfo.outputMode == 1 ? 'edit' : 'view';
				vm.mode(mode); 
            }
            return vm.$ajax(API.getDetail, {
                appId: vm.application().appID(),
                appDispInfoStartupOutput: vm.appDispInfoStartupOutput()
            }).done(res => {
                if (res) {
                    vm.reflectSetting(res.reflectSetting);
                    vm.timeLeaveManagement(res.timeLeaveManagement);
                    vm.timeLeaveRemaining(res.timeLeaveRemaining);
                    res.details.forEach((detail: TimeLeaveAppDetail) => {
                        detail.timeZones.forEach(z => {
                            vm.applyTimeData()[4].timeZone[z.workNo - 1].startTime(z.startTime);
                            vm.applyTimeData()[4].timeZone[z.workNo - 1].endTime(z.endTime);
                        });
                        if (detail.appTimeType <= 4) {
                            vm.applyTimeData()[detail.appTimeType].timeZone[0].startTime(detail.timeZones[0].startTime);
                            vm.applyTimeData()[detail.appTimeType].timeZone[0].endTime(detail.timeZones[0].endTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].substituteAppTime(detail.applyTime.substituteAppTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].annualAppTime(detail.applyTime.annualAppTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].childCareAppTime(detail.applyTime.childCareAppTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].careAppTime(detail.applyTime.careAppTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].super60AppTime(detail.applyTime.super60AppTime);
                            vm.applyTimeData()[detail.appTimeType].applyTime[0].specialAppTime(detail.applyTime.specialAppTime);
                        } else {
                            vm.applyTimeData()[4].applyTime[1].substituteAppTime(detail.applyTime.substituteAppTime);
                            vm.applyTimeData()[4].applyTime[1].annualAppTime(detail.applyTime.annualAppTime);
                            vm.applyTimeData()[4].applyTime[1].childCareAppTime(detail.applyTime.childCareAppTime);
                            vm.applyTimeData()[4].applyTime[1].careAppTime(detail.applyTime.careAppTime);
                            vm.applyTimeData()[4].applyTime[1].super60AppTime(detail.applyTime.super60AppTime);
                            vm.applyTimeData()[4].applyTime[1].specialAppTime(detail.applyTime.specialAppTime);
                        }
                    });
                    // vm.printContentOfEachAppDto().opInforGoBackCommonDirectOutput = ko.toJS(vm.dataFetch);
                }
            }).fail(err => {
                vm.handleError(err);
            }).always(() => vm.$blockui('hide'));
        }

        mounted() {
            const vm = this;
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            // if (!vm.appDispInfoStartupOutput().appDetailScreenInfo) {
            //     return;
            // }
            // let application = ko.toJS(vm.application);
            // vm.applicationTest = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;
            //
            // vm.applicationTest.prePostAtr = application.prePostAtr;
            // vm.applicationTest.opAppReason = application.opAppReason;
            // vm.applicationTest.opAppStandardReasonCD = application.opAppStandardReasonCD;
            // vm.applicationTest.opReversionReason = application.opReversionReason;
            // if (vm.model) {
            //     let isCondition1 = vm.model.checkbox3() == true && !vm.model.workTypeCode() && (vm.dataFetch().goBackReflect().reflectApplication === 3 || vm.dataFetch().goBackReflect().reflectApplication === 2);
				// let isCondition2 = vm.model.checkbox3() == null && !vm.model.workTypeCode() && vm.dataFetch().goBackReflect().reflectApplication === 1;
            //     if (isCondition1 || isCondition2) {
            //        // $('#workSelect').focus();
				// 	let el = document.getElementById('workSelect');
	         //        if (el) {
	         //            el.focus();
	         //        }
            //         return;
            //     }
            // }
            // let model = ko.toJS( vm.model );
            // let goBackApp = new GoBackApplication(
            //     model.checkbox1 ? 1 : 0,
            //     model.checkbox2 ? 1 : 0,
            // );
            // // is change can be null
            // if (!_.isNull(model.checkbox3) || vm.dataFetch().goBackReflect().reflectApplication == 2 || vm.dataFetch().goBackReflect().reflectApplication == 3) {
            //     goBackApp.isChangedWork = model.checkbox3 ? 1 : 0;
            //
            // }
            //
            // if	(!(vm.dataFetch().goBackReflect().reflectApplication == 2 || vm.dataFetch().goBackReflect().reflectApplication == 3)) {
				// goBackApp.isChangedWork = null;
            // }
            // if (vm.mode && vm.model.checkbox3() || vm.dataFetch().goBackReflect().reflectApplication == 1) {
            //     let dw = new DataWork( model.workTypeCode );
            //     if ( model.workTimeCode ) {
            //         dw.workTime = model.workTimeCode
            //     }
            //     goBackApp.dataWork = dw;
            //
            // }
			
            vm.$blockui("show");

            return vm.$validate('.nts-input', '#kaf000-b-component3-prePost', '#kaf000-b-component5-comboReason')
                .then(isValid => {
                    if (isValid) {
                        return true;

                    }
                }).then(result => {
                    if(!result) return;
                    let param = {
                            companyId: this.$user.companyId,
                            agentAtr: true,
                            applicationDto: vm.applicationTest,
                            goBackDirectlyDto: null,
                            inforGoBackCommonDirectDto: ko.toJS( vm.dataFetch ),
                            mode: false
                        };


                    return vm.$ajax(API.checkRegister, param);
                }).then(res => {
                    if (res == undefined) return;
                    if ( _.isEmpty( res ) ) {
                        return vm.registerData( null );
                    } else {
                        let listTemp = _.clone( res );
                        return vm.handleConfirmMessage( listTemp, null );

                    }

                }).done(result => {
                    if (result != undefined) {
                        vm.$dialog.info( { messageId: "Msg_15" } ).then(() => {
                        	ko.contextFor($('#contents-area')[0]).$vm.loadData();
                        });
                    }
                }).fail(err => {
                    vm.handleError(err);

                }).always(() => vm.$blockui("hide"));

        }

        handleConfirmMessage(listMes: any, res: any): any {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                return vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
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
            // let vm = this;
            // let paramsUpdate = {
            //     applicationDto: vm.applicationTest,
            //     goBackDirectlyDto: goBackApp,
            //     inforGoBackCommonDirectDto: ko.toJS(vm.dataFetch)
            // }
            //
            //  return vm.$ajax(API.updateApplication, paramsUpdate);

        }

        public handleError(err: any) {
            const vm = this;
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
            vm.$dialog.error(param).then((err: any) => {
                if (err.messageId == 'Msg_197') {
                	ko.contextFor($('#contents-area')[0]).$vm.loadData();
                }
            });
        }
    }

    interface TimeLeaveAppDetail {
        appTimeType: number,
        applyTime: {
            annualAppTime: number,
            super60AppTime: number,
            careAppTime: number,
            childCareAppTime: number,
            substituteAppTime: number,
            specialAppTime: number,
            specialLeaveFrameNo: number
        }
        timeZones: Array<{workNo: number, startTime: number, endTime: number}>
    }

}