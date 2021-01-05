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

			// Subscribe work time after change
			vm.selectedWorkTimeCD.subscribe(() => {
				if (_.isNil(vm.selectedWorkTimeCD())) {
					return;
				}

				let commandChangeWorkTime = {
					date: vm.application().appDate(),
					workTypeCode: vm.selectedWorkTypeCD(),
					workTimeCode: vm.selectedWorkTimeCD(),
					appAbsenceStartInfoDto: vm.data
				};

				vm.$blockui("show");
				vm.$ajax(API.changeWorkTime, commandChangeWorkTime)
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

			// validate
			if (!vm.validate()) {
				return;
			}

			// Create data Vacation Request/ 休暇申請
			// vm.createDataVacationApp();
			let appDates: any[] = [];

			let holidayAppDates = [];

			let commandCheckUpdate = {
				appAbsenceStartInfoDto: vm.data,
				applyForLeave: this.createDataVacationApp(),
				agentAtr: false,
				application: ko.toJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.application)
			};

			commandCheckUpdate.application.opAppReason = vm.application().opAppReason();
            commandCheckUpdate.application.opAppStandardReasonCD = vm.application().opAppStandardReasonCD();
            commandCheckUpdate.application.opReversionReason = vm.application().opReversionReason();

			let appTypeSettingLst = vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting;
			let qr = _.filter(appTypeSettingLst, { 'appType': vm.application().appType });


			let commandUpdate = {
				application: ko.toJS(vm.appDispInfoStartupOutput().appDetailScreenInfo.application),
				applyForLeave: this.createDataVacationApp(),
				appDispInfoStartupOutput: vm.appDispInfoStartupOutput,
				holidayAppDates: appDates,
				oldLeaveComDayOffMana: vm.data.leaveComDayOffManas,
				oldPayoutSubofHDManagements: vm.data.payoutSubofHDManas,
				leaveComDayOffMana: _.map(vm.leaveComDayOffManas(), (x: any) => {
					x.dateOfUse = new Date(x.dateOfUse).toISOString();
					x.outbreakDay = new Date(x.outbreakDay).toISOString();
					return x;
				}),
				payoutSubofHDManagements: _.map(vm.payoutSubofHDManagements(), (x: any) => {
					x.dateOfUse = new Date(x.dateOfUse).toISOString();
					x.outbreakDay = new Date(x.outbreakDay).toISOString();
					return x;
				})
			};

			commandUpdate.application.opAppReason = vm.application().opAppReason();
            commandUpdate.application.opAppStandardReasonCD = vm.application().opAppStandardReasonCD();
            commandUpdate.application.opReversionReason = vm.application().opReversionReason();

			vm.$blockui("show");
			vm.$ajax('at', API.checkBeforeUpdate, commandCheckUpdate)
			.then((result) => {
				if (result) {
					holidayAppDates = result.holidayDateLst;
					commandUpdate.holidayAppDates = holidayAppDates;
					// xử lý confirmMsg
					return vm.handleConfirmMessage(result.confirmMsgLst);
				}
			}).then((result) => {
				if(result) {
					// update 
					return vm.$ajax('at', API.update, commandUpdate);
				}
			}).done((result) => {
				if (result) {
					return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
						return true;
					});	
				}
			}).then((result) => {
				if(result) {
					// gửi mail sau khi update
					// return vm.$ajax('at', API.sendMailAfterRegisterSample);
					return true;
				}	
			}).fail((failData) => {
				// xử lý lỗi nghiệp vụ riêng
				vm.handleErrorCustom(failData).then((result: any) => {
					if(result) {
						// xử lý lỗi nghiệp vụ chung
						// vm.handleErrorCommon(failData);
					}
				});
			}).always(() => {
				vm.$blockui("hide");	
			});
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

		public openKDL035() {
			const vm = this;

			let workType = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});

			let params: any = {
				// 社員ID
				employeeId: __viewContext.user.employeeId,

				// 申請期間
				period: {startDate: vm.application().opAppStartDate(), endDate: vm.application().opAppEndDate()},

				// 日数単位（1.0 / 0.5）
				daysUnit: workType[0].workAtr === 0 ? 1.0 : 0.5,

				// 対象選択区分（自動 / 申請 / 手動
				targetSelectionAtr: 1,

				// List<表示する実績内容>
				actualContentDisplayList: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,

				// List<振出振休紐付け管理>
				managementData: ko.toJS(vm.payoutSubofHDManagements)
			};
			Kaf006ShrViewModel.openDialogKDL035(params, vm);
			// vm.payoutSubofHDManagements(payoutMana);
		}

		public openKDL036() {
			const vm = this;

			let workType = _.filter(vm.data.workTypeLst, {'workTypeCode': vm.selectedWorkTypeCD()});

			let params: any = {
				// 社員ID
				employeeId: __viewContext.user.employeeId,

				// 申請期間
				period: {startDate: vm.application().opAppStartDate(), endDate: vm.application().opAppEndDate()},

				// 日数単位（1.0 / 0.5）
				daysUnit: workType[0].workAtr === 0 ? 1.0 : 0.5,

				// 対象選択区分（自動 / 申請 / 手動
				targetSelectionAtr: 1,

				// List<表示する実績内容>
				actualContentDisplayList: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,

				// List<振出振休紐付け管理>
				managementData: ko.toJS(vm.leaveComDayOffManas)
			};
			Kaf006ShrViewModel.openDialogKDL036(params, vm);
			// vm.leaveComDayOffManas(leaveMana);
		}

		validate() {
			const vm = this;
			if (vm.condition11()) {
				if (!vm.checkTimeValid(vm.startTime1) && vm.checkTimeValid(vm.endTime1)) {
					vm.$dialog.error({messageId: "Msg_307"});
					return false;
				}
				if (vm.checkTimeValid(vm.startTime1) && !vm.checkTimeValid(vm.endTime1)) {
					vm.$dialog.error({messageId: "Msg_307"});
					return false;
				}
				if (vm.startTime1() > vm.endTime1()) {
					vm.$dialog.error({messageId: "Msg_307"});
					return false;
				}

				if (vm.condition12()) {
					if (vm.startTime2() > vm.endTime2()) {
						vm.$dialog.error({messageId: "Msg_307"});
						return false;
					}
					if (vm.checkTimeValid(vm.startTime2) && vm.endTime1() > vm.startTime2()) {
						vm.$dialog.error({messageId: "Msg_581"});
						return false;
					}
					if (!vm.checkTimeValid(vm.startTime2) && vm.checkTimeValid(vm.endTime2)) {
						vm.$dialog.error({messageId: "Msg_307"});
						return false;
					}
					if (vm.checkTimeValid(vm.startTime2) && !vm.checkTimeValid(vm.endTime2)) {
						vm.$dialog.error({messageId: "Msg_307"});
						return false;
					}
				}
			}

			return true;
		}

		private checkTimeValid(time: KnockoutObservable<number>): boolean {
			if (_.isNil(time()) || _.isEmpty(time())) {
				return false;
			}
			return true;
		}

		/**
		 * Create Data for for Vacation Application
		 */
		createDataVacationApp(): any {
			const vm = this;
			
			// application common

			// A4_2
			// Holiday Type
			let holidayAppType = vm.selectedType();

			// A5_2
			// List of workType
			let workType = vm.selectedWorkTypeCD();

			// A6_5
			// worktTime
			let workTime = vm.selectedWorkTimeCD();

			// A6_1
			let workChangeUse = vm.isChangeWorkHour();

			let startTime1 = vm.startTime1();
			let endTime1 = vm.endTime1();
			let startTime2 = vm.startTime2();
			let endTime2 = vm.endTime2();

			let workingHours = [];

			if (startTime1 && endTime1) {
				workingHours.push({
					workNo: 1,
					timeZone: {
						startTime: startTime1,
						endTime: endTime1
					}
				});
			}
			if (startTime2 && endTime2) {
				workingHours.push({
					workNo: 2,
					timeZone: {
						startTime: startTime2,
						endTime: endTime2
					}
				});
			}

			let timeDegestion = {};
			if (vm.selectedType() === 6) {
				timeDegestion = {
					overtime60H: vm.over60H(),
					nursingTime: vm.nursing(),
					childTime: vm.childNursing(),
					timeOff: vm.timeOff(),
					timeSpecialVacation: 0,
					timeAnualLeave: vm.annualTime(),
					specialVacationFrameNO: null
				};
			}

			let applyForSpeLeaveOptional = {};
			if (vm.selectedType() === 3) {
				applyForSpeLeaveOptional = {
					mournerFlag: vm.isCheckMourn(),
					relationshipCD: vm.selectedDateSpec(),
					relationshipReason: vm.relationshipReason()
				};
			}


			let appAbsence = {
				reflectFreeTimeApp: {
					workingHours: workingHours,
					timeDegestion: timeDegestion,
					workInfo: {
						workType: workType,
						workTime: workTime
					},
					workChangeUse: workChangeUse ? 1 : 0
				},
				vacationInfo: {
					holidayApplicationType: holidayAppType,
					info: {
						datePeriod: {
							startDate: vm.application().opAppStartDate(),
							endDate: vm.application().opAppEndDate()
						},
						applyForSpeLeave: applyForSpeLeaveOptional
					}
				}
			};

			return appAbsence;
		}


		/**
		 * Update data for AppAbsenceStartInfo
		 */
		updateAppAbsenceStartInfo() {
			const vm = this;

			if (vm.selectedType() === 1) {
				if (vm.leaveComDayOffManas().length > 0) {
					vm.data.leaveComDayOffManas = _.map(vm.leaveComDayOffManas(), (x: any) => {
						x.dateOfUse = new Date(x.dateOfUse).toISOString();
						x.outbreakDay = new Date(x.outbreakDay).toISOString();
						return x;
					});
				}
				if (vm.payoutSubofHDManagements().length > 0) {
					vm.data.payoutSubofHDManas = _.map(vm.payoutSubofHDManagements(), (x: any) => {
						x.dateOfUse = new Date(x.dateOfUse).toISOString();
						x.outbreakDay = new Date(x.outbreakDay).toISOString();
						return x;
					});
				}
			}
		}
		
		handleErrorCustom(failData: any): any {
			const vm = this;
			if(failData.messageId == "Msg_26") {
				vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
				.then(() => {
					vm.$jump("com", "/view/ccg/008/a/index.xhtml");	
				});
				return $.Deferred().resolve(false);		
			}
			return $.Deferred().resolve(true);
		}

		handleConfirmMessage(listMes: any): any {
			const vm = this;
			if(_.isEmpty(listMes)) {
				return $.Deferred().resolve(true);
			}
			let msg = listMes[0];

			return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
			.then((value) => {
				if (value === 'yes') {
					return vm.handleConfirmMessage(_.drop(listMes));
				} else {
					return $.Deferred().resolve(false);
				}
			});
		}
    };

    const API = {
        initPageB: 'at/request/application/appforleave/getAppForLeaveStartB',
		changeRela: 'at/request/application/appforleave/changeRela',
		changeWorkTime: 'at/request/application/appforleave/findChangeWorkTime',
		checkVacationTyingManage: 'at/request/application/appforleave/checkVacationTyingManage',
		changeWorkType: 'at/request/application/appforleave/findChangeWorkType',
		checkBeforeUpdate: 'at/request/application/appforleave/checkBeforeUpdate',
		update: 'at/request/application/appforleave/update'
    };
}