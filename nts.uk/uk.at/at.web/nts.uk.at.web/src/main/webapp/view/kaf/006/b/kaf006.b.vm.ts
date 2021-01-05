module nts.uk.at.view.kaf006_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import WorkType = nts.uk.at.view.kaf006.shr.viewmodel.WorkType;
    import Kaf006ShrViewModel = nts.uk.at.view.kaf006.shr.viewmodel.Kaf006ShrViewModel;

    @component({
        name: 'kaf006-b',
        template: `/nts.uk.at.web/view/kaf/006/b/index.html`
    })
    export class Kaf006BViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.ABSENCE_APPLICATION);
        application: KnockoutObservable<Application>;
        appDispInfoStartupOutput: any;
        data: any = null;
		hdAppSet: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedType: KnockoutObservable<any> = ko.observable();
		workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedWorkTypeCD: KnockoutObservable<string> = ko.observable(null);
		selectedWorkType: KnockoutObservable<WorkType> = ko.observable(new WorkType({workTypeCode: '', name: ''}));
		selectedWorkTimeCD: KnockoutObservable<string> = ko.observable();
		selectedWorkTimeName: KnockoutObservable<string> = ko.observable();
		selectedWorkTimeDisp: KnockoutComputed<string>;
		dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedDateSpec: KnockoutObservable<any> = ko.observable();
		relationshipReason: KnockoutObservable<string> = ko.observable();
		maxNumberOfDay: KnockoutComputed<any>;
		specAbsenceDispInfo: KnockoutObservable<any> = ko.observable();
		isDispMourn: any = ko.observable(false);
		isCheckMourn: any = ko.observable(false);
		requiredVacationTime: KnockoutObservable<number> = ko.observable(0);
		timeRequired: KnockoutObservable<string> = ko.observable();
		leaveComDayOffManas: KnockoutObservableArray<any> = ko.observableArray([]);
		payoutSubofHDManagements: KnockoutObservable<any> = ko.observableArray([]);
		workTypeBefore: KnockoutObservable<any> = ko.observable();
		workTypeAfter: KnockoutObservable<any> = ko.observable();
		isEnableSwitchBtn: boolean = false;

		yearRemain: KnockoutObservable<number> = ko.observable();
		subHdRemain: KnockoutObservable<number> = ko.observable();
		subVacaRemain: KnockoutObservable<number> = ko.observable();
		remainingHours: KnockoutObservable<number> = ko.observable();

		over60HHourRemain: KnockoutObservable<string> = ko.observable();
		subVacaHourRemain: KnockoutObservable<string> = ko.observable();
		timeYearLeave: KnockoutObservable<string> = ko.observable();
		childNursingRemain: KnockoutObservable<string> = ko.observable();
		nursingRemain: KnockoutObservable<string> = ko.observable();
		isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);
		startTime1: KnockoutObservable<number> = ko.observable();
        endTime1: KnockoutObservable<number> = ko.observable();
        startTime2: KnockoutObservable<number> = ko.observable();
		endTime2: KnockoutObservable<number> = ko.observable();
		
        // 60H超休
        over60H: KnockoutObservable<number> = ko.observable();
        // 時間代休
        timeOff: KnockoutObservable<number> = ko.observable();
        // 時間年休
        annualTime: KnockoutObservable<number> = ko.observable();
        // 子の看護
        childNursing: KnockoutObservable<number> = ko.observable();
        // 介護時間
        nursing: KnockoutObservable<number> = ko.observable();

        isSendMail: KnockoutObservable<Boolean>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;

        // Condition
		condition10: KnockoutObservable<boolean> = ko.observable(true);
		condition11: KnockoutObservable<boolean> = ko.observable(true);
		condition30: KnockoutObservable<boolean> = ko.observable(true);
		condition12: KnockoutObservable<boolean> = ko.observable(true);
		condition19Over60: KnockoutObservable<boolean> = ko.observable(true);
		condition19Substitute: KnockoutObservable<boolean> = ko.observable(true);
		condition19Annual: KnockoutObservable<boolean> = ko.observable(true);
		condition19ChildNursing: KnockoutObservable<boolean> = ko.observable(true);
		condition19Nursing: KnockoutObservable<boolean> = ko.observable(true);
		condition14: KnockoutObservable<boolean> = ko.observable(true);
		condition15: KnockoutObservable<boolean> = ko.observable(true);
		condition21: KnockoutObservable<boolean> = ko.observable(true);
		condition22: KnockoutObservable<boolean> = ko.observable(true);
		condition23: KnockoutObservable<boolean> = ko.observable(true);
		condition24: KnockoutObservable<boolean> = ko.observable(true);
		condition1: KnockoutObservable<boolean> = ko.observable(true);
		condition6: KnockoutObservable<boolean> = ko.observable(true);
		condition7: KnockoutObservable<boolean> = ko.observable(true);
		condition8: KnockoutObservable<boolean> = ko.observable(true);
		condition9: KnockoutObservable<boolean> = ko.observable(true);
        
        created(params: {
            appType: any,
            application: any,
            printContentOfEachAppDto: PrintContentOfEachAppDto,
            approvalReason: any,
            appDispInfoStartupOutput: any,
            eventUpdate: (evt: () => void) => void,
            eventReload: (evt: () => void) => void
        }) {
            const vm = this;

            vm.isSendMail = ko.observable(true);
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.approvalReason = params.approvalReason;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;
			vm.createParamKAF006();
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));
        };

        mounted() {
            const vm = this;

            vm.maxNumberOfDay = ko.computed(() => {
				let data = vm.$i18n("KAF006_44").concat("\n");
				if (vm.specAbsenceDispInfo()) {
					if (vm.isDispMourn() && vm.isCheckMourn()) {
						let param = vm.specAbsenceDispInfo().maxDay + vm.specAbsenceDispInfo().dayOfRela;
						data = data + vm.$i18n("KAF006_46", param.toString());
					} else {
						let param = vm.specAbsenceDispInfo().maxDay;
						data = data + vm.$i18n("KAF006_46", param.toString());
					}

				}
				return data;
            });
            
            vm.selectedWorkTimeDisp = ko.computed(() => {
				const vm = this;

				if (vm.selectedWorkTimeCD()) {
					return vm.selectedWorkTimeCD() + " " + vm.selectedWorkTimeName();
				}

				return vm.$i18n("KAF006_21");
            });
            
            vm.selectedDateSpec.subscribe(() => {
				if (vm.selectedType() !== 3 || vm.dateSpecHdRelationLst().length === 0) {
					return;
				}
				let command = {
					frameNo: vm.specAbsenceDispInfo() ? vm.specAbsenceDispInfo().frameNo : null,
					specHdEvent: vm.specAbsenceDispInfo() ? vm.specAbsenceDispInfo().specHdEvent : null,
					relationCD: vm.selectedDateSpec()
				};

				vm.$blockui("show");
                vm.$ajax(API.changeRela, command).done((success) => {
					if (success) {
						if (vm.specAbsenceDispInfo()) {
							vm.specAbsenceDispInfo().maxDay = success.maxDayObj.maxDay;
							vm.specAbsenceDispInfo().dayOfRela = success.maxDayObj.dayOfRela;
						}
					}
                }).fail((error) => {
					if (error) {
						vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
					}
                }).always(() => {
                    vm.$blockui("hide");
                })
			});

			// Subscribe workType value after change
			vm.selectedWorkTypeCD.subscribe(() => {
				if (_.isNil(vm.selectedWorkTypeCD()) || _.isEmpty(vm.workTypeLst())) {
					return;
				}

				// return;
				let commandCheckTyingManage = {
					wtBefore: vm.workTypeBefore(),
					wtAfter: vm.workTypeAfter(),
					leaveComDayOffMana: vm.leaveComDayOffManas(),
					payoutSubofHDManagements: vm.payoutSubofHDManagements()
				};

				// Check vacation tying manage
				// 休暇紐付管理をチェックする
				vm.$blockui("show");
				vm.$ajax(API.checkVacationTyingManage, commandCheckTyingManage)
					.done((success) => {
						if (success) {
							if (success.clearManageSubsHoliday) {
								vm.leaveComDayOffManas([]);
								vm.data.leaveComDayOffManas = [];
							}
							if (success.clearManageHolidayString) {
								vm.payoutSubofHDManagements([]);
								vm.data.payoutSubofHDManas = [];
							}
						}
					}).fail((error) => {
						if (error) {
							vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
						}
					}).always(() => {
						vm.$blockui("hide");
					});
					
					let dates = [];
					if (vm.application().opAppStartDate()) {
						dates.push(vm.application().opAppStartDate());
					}
					if (vm.application().opAppEndDate() && (vm.application().opAppEndDate() !== vm.application().opAppStartDate())) {
						dates.push(vm.application().opAppEndDate());
					}
					
					let commandChangeWorkType = {
						appDates: dates,
						startInfo: vm.data,
						holidayAppType: vm.selectedType(),
						workTypeCd: vm.selectedWorkTypeCD()
					};
					// Process change workType
					// 勤務種類変更時処理
					vm.$blockui("show");
					vm.$ajax(API.changeWorkType, commandChangeWorkType)
					.done((success) => {
						if (success) {
							vm.specAbsenceDispInfo(success.specAbsenceDispInfo);
							return success;
						}
					}).then((data) => {
						if (data) {
							vm.fetchData(data);
							return data;
						}
					}).then((data) => {
						if (data) {
							// vm.checkCondition(data);
							return data;
						}
					}).fail((error) => {
						if (error) {
							vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
						}
					}).always(() => {
						vm.$blockui("hide");
					});
			});
        };

        reload() {
			const vm = this;
			if(vm.appType() === AppType.ABSENCE_APPLICATION) {
				vm.createParamKAF006();
			}
        };

        update() {
            const vm = this;
        };
		
		/**
		 * Start screen B
		 */
        private createParamKAF006() {
            const vm = this;

            let command = {
				appID: vm.application().appID(),
				appDispInfoStartupOutput: vm.appDispInfoStartupOutput()
            };

            vm.$blockui("show");
            vm.$ajax(API.initPageB, command)
                .done((success) => {
					vm.data = success.appAbsenceStartInfo;
					let hdAppSetInput: any[] = vm.data.hdAppSet.dispNames;
						if (hdAppSetInput && hdAppSetInput.length > 0) {
							vm.hdAppSet(hdAppSetInput);
						}
						vm.fetchData(success.appAbsenceStartInfo);
						vm.fetchDataAppForLeave(success.applyForLeave);
                }).fail((error) => {
                    vm.$dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
                }).always(() => vm.$blockui('hide'));
		};
		
		private fetchDataAppForLeave(param: any) {
			const vm = this;
			
			// B3_2
			vm.selectedType(param.vacationInfo.holidayApplicationType);
			// B4_2
			vm.selectedWorkTypeCD(param.reflectFreeTimeApp.workInfo.workType);
			// B5_1
			vm.isChangeWorkHour(param.reflectFreeTimeApp.workChangeUse === 0 ? false : true);
			// B5_5
			// 就業時間帯コード
			vm.selectedWorkTimeCD(param.reflectFreeTimeApp.workInfo.workTime);

			// 表示名
			if (vm.selectedWorkTimeCD()) {
				let qr: any[] = _.filter(vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, { 'worktimeCode': vm.selectedWorkTimeCD() });
				if (qr.length > 0) {
					vm.selectedWorkTimeName(qr[0].workTimeDisplayName.workTimeName);
				}
			}

			let time1: any[] = _.filter(param.reflectFreeTimeApp.workingHours, { 'workNo': 1 });
			if (time1.length > 0) {
				// B5_7
				vm.startTime1(time1[0].timeZone.startTime);
				// B5_9
				vm.endTime1(time1[0].timeZone.endTime);
			}
			let time2: any[] = _.filter(param.reflectFreeTimeApp.workingHours, { 'workNo': 2 });
			if (time2.length > 0) {
				// B5_11
				vm.startTime2(time2[0].timeZone.startTime);
				// B5_13
				vm.endTime2(time2[0].timeZone.endTime);
			}

			if (vm.selectedType() === 6) {
				// B8_3
				vm.over60H(param.reflectFreeTimeApp.timeDegestion.overtime60H);
				// B8_5
				vm.timeOff(param.reflectFreeTimeApp.timeDegestion.timeOff);
				// B8_7
				vm.annualTime(param.reflectFreeTimeApp.timeDegestion.timeAnualLeave);
				// B8_9
				vm.childNursing(param.reflectFreeTimeApp.timeDegestion.childTime);
				// B8_11
				vm.nursing(param.reflectFreeTimeApp.timeDegestion.nursingTime);
			}

			if (vm.selectedType() === 3) {
				// B9_2
				vm.selectedDateSpec(param.vacationInfo.info.applyForSpeLeave.relationshipCD);
				// B9_3
				vm.isCheckMourn(param.vacationInfo.info.applyForSpeLeave.mournerFlag);
				// B9_5
				vm.relationshipReason(param.vacationInfo.info.applyForSpeLeave.relationshipReason);
			}
		};

        private fetchData(data: any) {
			const vm = this;
			let workTypeLstOutput = data.workTypeLst;
			
			// Get value workType before change workType List
			let workTypesBefore = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});
			vm.workTypeBefore(workTypesBefore.length > 0 ? workTypesBefore[0] : null);
			
			vm.data = data;
			vm.workTypeLst(_.map(workTypeLstOutput, item => new WorkType({workTypeCode: item.workTypeCode, name: item.workTypeCode + ' ' + item.name})));
			// vm.checkCondition10(data);
			// vm.checkCondition11(data);
			// vm.checkCondition12(data);
			// vm.checkCondition30(data);
			// vm.checkCondition19(data);
			// vm.checkCondition14(data);
			// vm.checkCondition15(data);
			// vm.checkCondition21(data);
			// vm.checkCondition22(data);
			// vm.checkCondition23(data);
			// vm.checkCondition24(data);
			// vm.checkCondition1(data);
			// vm.checkCondition6(data);
			// vm.checkCondition7(data);
			// vm.checkCondition8(data);
			// vm.checkCondition9(data);

			let workTypesAfter = _.filter(vm.data.workTypeLst, {'workTypeCode': data.selectedWorkTypeCD});
			vm.workTypeAfter(workTypesAfter.length > 0 ? workTypesAfter[0] : null);

			vm.appDispInfoStartupOutput(data.appDispInfoStartupOutput);
			vm.specAbsenceDispInfo(data.specAbsenceDispInfo);

			if (data.requiredVacationTime) {
				vm.requiredVacationTime(data.requiredVacationTime);
			}

			if (data.remainVacationInfo) {
				vm.yearRemain(data.remainVacationInfo.yearRemain);
				vm.subHdRemain(data.remainVacationInfo.subHdRemain);
				vm.subVacaRemain(data.remainVacationInfo.subVacaRemain);
				vm.remainingHours(data.remainVacationInfo.remainingHours);
				vm.fetchRemainTime(data.remainVacationInfo);
			}

			vm.requiredVacationTime(data.requiredVacationTime);

			if (vm.selectedType() === 6) {
				vm.timeRequired(nts.uk.time.format.byId("Clock_Short_HM", vm.requiredVacationTime()));
			}

			if (vm.specAbsenceDispInfo()) {
				vm.dateSpecHdRelationLst(vm.specAbsenceDispInfo().dateSpecHdRelationLst);
				
				if (vm.dateSpecHdRelationLst() && vm.dateSpecHdRelationLst().length > 0) {
					vm.selectedDateSpec(vm.dateSpecHdRelationLst()[0].relationCD);
				}
			}
		};
		
		fetchRemainTime(remainVacationInfo: any) {
			const vm = this;

			// set over60HHourRemain
			if (remainVacationInfo.over60HHourRemain) {
				vm.over60HHourRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.over60HHourRemain));
			}

			// set subVacaHourRemain
			if (remainVacationInfo.subVacaHourRemain) {
				vm.subVacaHourRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.subVacaHourRemain));
			}

			// set yearRemain
			if (remainVacationInfo.yearRemain && remainVacationInfo.yearRemain > 0) {
				if (remainVacationInfo.yearHourRemain && remainVacationInfo.yearHourRemain > 0) {
					vm.timeYearLeave(remainVacationInfo.yearRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.yearHourRemain)));
				} else {
					vm.timeYearLeave(remainVacationInfo.yearRemain.toString().concat("日"));
				}
			} else {
				vm.timeYearLeave(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.yearHourRemain));
			}

			// set childNursingRemain
			if (remainVacationInfo.childNursingRemain && remainVacationInfo.childNursingRemain > 0) {
				if (remainVacationInfo.childNursingHourRemain && remainVacationInfo.childNursingHourRemain > 0) {
					vm.childNursingRemain(remainVacationInfo.childNursingRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.childNursingHourRemain)));
				} else {
					vm.childNursingRemain(remainVacationInfo.childNursingRemain.toString().concat("日"));
				}
			} else {
				vm.childNursingRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.childNursingHourRemain));
			}

			// set nursingRemain
			if (remainVacationInfo.nursingRemain && remainVacationInfo.nursingRemain > 0) {
				if (remainVacationInfo.nursingRemain && remainVacationInfo.nirsingHourRemain > 0) {
					vm.nursingRemain(remainVacationInfo.nursingRemain.toString().concat("日と").concat(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.nirsingHourRemain)));
				} else {
					vm.nursingRemain(remainVacationInfo.nursingRemain.toString().concat("日"));
				}
			} else {
				vm.nursingRemain(nts.uk.time.format.byId("Clock_Short_HM", remainVacationInfo.nirsingHourRemain));
			}
		};

		public openKDL003() {
			const vm = this;
			let workTypeCodes = _.map(vm.data.workTypeLst, 'workTypeCode');
			let workTimeCodes = _.map(vm.data.workTimeLst, 'workNo');

			nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: vm.selectedWorkTypeCD(),
                workTimeCodes: workTimeCodes,
                selectedWorkTimeCode: vm.selectedWorkTimeCD(),
			}, true);
			
			nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                let childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
					vm.selectedWorkTypeCD(childData.selectedWorkTypeCode);
					vm.selectedWorkTimeCD(childData.selectedWorkTimeCode);
					vm.selectedWorkTimeName(childData.selectedWorkTimeName);

					vm.startTime1(childData.first.start);
					vm.endTime1(childData.first.end);
					vm.startTime2(childData.second.start);
					vm.endTime2(childData.second.end);
                }
            });
		};
    };

    const API = {
        initPageB: 'at/request/application/appforleave/getAppForLeaveStartB',
		changeRela: 'at/request/application/appforleave/changeRela',
		checkVacationTyingManage: 'at/request/application/appforleave/checkVacationTyingManage',
		changeWorkType: 'at/request/application/appforleave/findChangeWorkType'
    };
}