/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cmm024.a {
	import service = nts.uk.com.view.cmm024.a.service;
	import EmployeeDto = nts.uk.com.view.cmm024.a.service.EmployeeDto;
	import ScheduleHistoryDto = nts.uk.com.view.cmm024.a.service.ScheduleHistoryDto;
	import Model = nts.uk.com.view.cmm024.a.service.Model;
	import HistoryRes = nts.uk.com.view.cmm024.a.service.HistoryRes;
	import HistoryUpdate = nts.uk.com.view.cmm024.a.service.HistoryUpdate;
	import ScreenModel = nts.uk.com.view.cmm024.a.service.ScreenModel;
	import ScheduleHistory = nts.uk.com.view.cmm024.a.service.ScheduleHistory;
	import ScheduleHistoryModel = service.ScheduleHistoryModel;

	@bean()
	class ViewModel extends ko.ViewModel {

		ntsHeaderColumns: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([]);

		//Screen A
		modelA: KnockoutObservable<Model> = ko.observable(new Model());
		enableRegisterA: KnockoutObservable<boolean> = ko.observable(true);
		company: KnockoutObservable<any> = ko.observable({ name: null, id: null });
		screenAModeAddNew: KnockoutObservable<boolean> = ko.observable(true);
		screenAModeEdit: KnockoutObservable<boolean> = ko.observable(false);
		screenAMode: KnockoutObservable<number> = ko.observable(ScreenModel.EDIT);
		enableScheduleHistoryA: KnockoutObservable<boolean> = ko.observable(true);
		allowSettingA: KnockoutObservable<boolean> = ko.observable(true);
		companyScheduleHistoryList: KnockoutObservableArray<ScheduleHistoryDto> = ko.observableArray([]);
		companyScheduleHistorySelected: KnockoutObservable<string> = ko.observable();
		companyScheduleHistoryObjSelected: KnockoutObservable<ScheduleHistoryDto> = ko.observable(null);
		//Screen B
		modelB: KnockoutObservable<Model> = ko.observable(new Model());
		enableRegisterB: KnockoutObservable<boolean> = ko.observable(false);
		screenBModeAddNew: KnockoutObservable<boolean> = ko.observable(true);
		screenBModeEdit: KnockoutObservable<boolean> = ko.observable(false);
		screenBMode: KnockoutObservable<number> = ko.observable(ScreenModel.EDIT);
		enableScheduleHistoryB: KnockoutObservable<boolean> = ko.observable(true);
		allowSettingB: KnockoutObservable<boolean> = ko.observable(true);
		isShowPanelB: KnockoutObservable<boolean> = ko.observable(false);
		workplaceScheduleHistoryList: KnockoutObservableArray<ScheduleHistoryDto> = ko.observableArray([]);
		workplaceScheduleHistorySelected: KnockoutObservable<string> = ko.observable();
		workplaceScheduleHistoryObjSelected: KnockoutObservable<ScheduleHistoryDto> = ko.observable(null);
		// KCP010
		kcp010Model: kcp010.viewmodel.ScreenModel;
		listComponentOption: service.ComponentOption;
		selectedWkpId: KnockoutObservable<string>;
		selectedCode: any;

		constructor(params: any) {
			// start point of object
			super();

			let vm = this;

			vm.ntsHeaderColumns = ko.observableArray([
				{ headerText: vm.$i18n('CMM024_7'), key: 'code', hidden: true },
				{ headerText: vm.$i18n('CMM024_7'), key: 'display', formatter: _.escape }
			]);

			//Screen B
			vm.selectedWkpId = ko.observable('');
			// Initial listComponentOption
			vm.listComponentOption = {
				targetBtnText: vm.$i18n("KCP010_3"),
				tabIndex: -1
			};

			vm.kcp010Model = $('#wkp-component').ntsLoadListComponent(vm.listComponentOption);
			vm.kcp010Model.workplaceId.subscribe(function (wkpId: string) {
				if (wkpId) {
					vm.selectedWkpId(wkpId);
					vm.workplaceScheduleHistoryListing();
				}
			});
		}

		// start point of object
		created(params: any) {
			let vm = this;

			_.extend(window, { vm });

			//Screen A			
			vm.screenAMode(ScreenModel.EDIT);
			vm.companyScheduleHistoryListing();

			let company = JSON.parse(nts.uk.sessionStorage.nativeStorage.getItem('nts.uk.session.COMPANY'));
			vm.company({ name: company[0].companyName, id: company[0].companyId });
			vm.modelA().workPlaceCompanyId = vm.company().id;
		}

		// raise event when view initial success full
		mounted() {
			let vm = this;

			vm.companyScheduleHistorySelected.subscribe(function (value: string) {
				vm.dispplayInfoOnScreenA(value);
			});

			//Screen B
			vm.workplaceScheduleHistorySelected.subscribe(function (value: string) {
				vm.dispplayInfoOnScreenB(value);
			});

			//responsive
			if ($(window).width() < 1360) {
				$('.contents-area').addClass('fix1280');
			} else if ($(window).width() < 1400) {
				$('.contents-area').addClass('fix1366');
			} else
				$('.contents-area')
					.removeClass('fix1366')
					.removeClass('fix1280');
		}

		/***********************************************************************
		 * Screen A
		 * ********************************************************************/
		dispplayInfoOnScreenA(value: string = null) {
			let vm = this,
				isAllowSetting: boolean = false,
				objFind: ScheduleHistoryDto = null,
				scheduleHistory: Array<any> = [];

			if (vm.companyScheduleHistoryList().length > 0) {
				value = (value === null) ? vm.companyScheduleHistoryList()[0].code : value;
				//allow/not allow add new or updated with the first item
				isAllowSetting = vm.companyScheduleHistoryList()[0].code === value;
				vm.resetSettingsScreenA(isAllowSetting, isAllowSetting, isAllowSetting, true, ScreenModel.EDIT);
				//highlight history that has selected
				scheduleHistory = vm.companyScheduleHistoryList();
				objFind = _.find(scheduleHistory, (x) => x.code === value);

				if (!nts.uk.util.isNullOrEmpty(objFind)) {
					vm.companyScheduleHistoryObjSelected(objFind); //send to screen B, D
				}
				//36承認者パネル
				vm.createEmployeesPanelList('A', 1, objFind.personalInfoApprove);
				//従業員代表パネル
				vm.createEmployeesPanelList('A', 2, objFind.personalInfoConfirm);
			} else {
				vm.resetSettingsScreenA(false, true, false, true, ScreenModel.ADDNEW);
			}
		}

		/**
		 * Register data on Screen A
		 * */
		RegisterA() {
			let vm = this,
				currentScheduleItem = vm.modelA();;

			let tempList = currentScheduleItem.approverPanel().filter(function (item) {
				return (item.employeeCode !== '-1' && item.employeeCode !== null);
			});

			if (tempList.length <= 0) {
				vm.$dialog.error({ messageId: 'Msg_1790' });
				$('.employee-lists-a').focus();
				return;
			}

			switch (vm.screenAMode()) {
				case ScreenModel.EDIT:
					vm.updateScheduleHistoryByCompany();
					break;

				case ScreenModel.ADDNEW:
					vm.registerScheduleHistoryByCompany();
					break;
			}

			//reset
			vm.screenAModeAddNew(true);
			vm.screenAModeEdit(true);
			vm.enableScheduleHistoryA(true);
		}

		/**
		 * Registration new schedule history
		*/
		registerScheduleHistoryByCompany() {
			let vm = this,
				currentScheduleItem = vm.modelA();

			vm.$blockui('show');

			let params = {
				companyId: vm.company().id, //ログイン会社ID				
				startDate: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate(), 'YYYY-MM-DD'), //期間
				approveList: [], //36承認者一覧リンクラベル
				confirmedList: []// 従業員代表指定リンクラベル
			};

			currentScheduleItem.approverPanel().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.approveList.push(item.employeeId);
			})

			currentScheduleItem.employeesRepresentative().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.confirmedList.push(item.employeeId);
			})

			service.registerScheduleHistoryByCompany(params)
				.done((response) => {
					vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
						vm.companyScheduleHistoryListing();
						vm.$blockui('hide');
					});
				}).fail((error) => {
					console.log(error);
				}).always(() => {
					vm.$blockui('hide');
				});
		}

		/**
		 * Registration new schedule history
		*/
		updateScheduleHistoryByCompany() {
			let vm = this,
				currentScheduleItem = vm.modelA();

			vm.$blockui('show');


			let params = {
				companyId: vm.company().id, //ログイン会社ID								 
				startDate: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate(), 'YYYY-MM-DD'), //期間
				approveList: [], //36承認者一覧リンクラベル
				confirmedList: [], // 従業員代表指定リンクラベル
				startDateBeforeChange: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD')//期間
			};

			currentScheduleItem.approverPanel().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.approveList.push(item.employeeId);
			})

			currentScheduleItem.employeesRepresentative().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.confirmedList.push(item.employeeId);
			})

			service.updateScheduleHistoryByCompany(params)
				.done((response) => {
					vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
						vm.companyScheduleHistoryListing();
						vm.$blockui('hide');
					});
				}).fail((error) => {
					console.log(error);
				}).always(() => {
					vm.$blockui('hide');
				});
		}

		/**
		 * Display page C
		 * */
		screenAShowDialogC() {

			let vm = this,
				currentScheduleHistoryList: Array<ScheduleHistoryDto> = vm.companyScheduleHistoryList();

			vm.$window.storage("scheduleHistorySelected", vm.companyScheduleHistoryObjSelected());
			vm.$window.modal("/view/cmm/024/c/index.xhtml", { title: vm.$i18n('CMM024_92') }).then(function () {
				//開始年月日テキストボックス
				vm.$window.storage("newScheduleHistory").then((data: any) => {

					if (!nts.uk.util.isNullOrUndefined(data.scheduleHistoryItem)) {
						currentScheduleHistoryList = vm.updateScheduleHistoryListing( vm.companyScheduleHistoryList(), data);
						//re-update the list
						vm.companyScheduleHistoryList(currentScheduleHistoryList);
						vm.companyScheduleHistorySelected(currentScheduleHistoryList[0].code);
						if (data.registrationHistoryType == HistoryRes.HISTORY_NEW) {
							vm.modelA(new Model(
								vm.$user.companyCode,
								data.scheduleHistoryItem.startDate,
								data.scheduleHistoryItem.endDate,
								vm.addEmptySetting(),
								vm.addEmptySetting()
							));
						} else { //clone
							vm.modelA().startDate(data.scheduleHistoryItem.startDate);
							vm.modelA().endDate(data.scheduleHistoryItem.endDate);
						}
						//disable/enable buttons
						vm.resetSettingsScreenA(true, false, false, false, ScreenModel.ADDNEW);
					} else {
						if (currentScheduleHistoryList.length > 0)
							vm.resetSettingsScreenA(true, true, true, true, ScreenModel.EDIT);
						else
							vm.resetSettingsScreenA(false, true, false, false, ScreenModel.EDIT);
					}
				});
			});
		}


		resetSettingsScreenA(register: boolean, addnew: boolean,
			edit: boolean, enablePeriodlist: boolean, mode: number) {

			let vm = this;
			vm.enableRegisterA(register);
			vm.screenAModeAddNew(addnew);
			vm.screenAModeEdit(edit);
			vm.enableScheduleHistoryB(enablePeriodlist);
			vm.screenAMode(mode);
			vm.allowSettingA(register);
		}

		/**
		 * Display page D
		 * */
		screenShowDialogD(screen: string = 'A') {
			let vm = this,
				allowStartDate: string = null,
				scheduleHistory: Array<ScheduleHistoryDto> = [];
			//get second item
			let data: ScheduleHistoryModel = {};

			scheduleHistory = (screen === 'A') ? vm.companyScheduleHistoryList() : vm.workplaceScheduleHistoryList();
			allowStartDate = (scheduleHistory.length > 1)
				? scheduleHistory[1].startDate
				: null;

			data.allowStartDate = allowStartDate;
			data.scheduleHistoryUpdate = (screen === 'A')
				? vm.companyScheduleHistoryObjSelected()
				: vm.workplaceScheduleHistoryObjSelected();
			data.workPlaceCompanyId = (screen === 'A') ? vm.company().id : vm.selectedWkpId();
			data.screen = screen;

			if (!vm.screenAModeEdit()) {
				vm.$dialog.error({ messageId: 'Msg_154' });
				return;
			}

			//ScheduleHistoryModel
			vm.$window.storage("CMM024_D_INPUT", data);
			vm.$window.modal("/view/cmm/024/d/index.xhtml",
				{ title: vm.$i18n('CMM024_93') }).then(function () {

					vm.$window.storage("CMM024_D_RESULT").then((data: any) => {

						if (!nts.uk.util.isNullOrEmpty(data)) {
							if (data.RegistrationHistoryType === HistoryUpdate.HISTORY_EDIT) {
								if (!nts.uk.util.isNullOrUndefined(data.newScheduleHistory)
									&& (scheduleHistory.length > 0)) {
									vm.updateHistoricalPeriod(scheduleHistory, data);
								}
							}

							if (screen === 'A') {
								vm.companyScheduleHistoryListing();
								vm.resetSettingsScreenA(true, true, true, true, ScreenModel.EDIT);
							} else {
								vm.workplaceScheduleHistoryListing();
								vm.resetSettingsScreenB(true, true, true, true, ScreenModel.EDIT);
							}
						} else {
							if (screen === 'A') {
								vm.resetSettingsScreenA(
									vm.enableRegisterA(),
									true, true,
									vm.allowSettingA(),
									vm.screenAMode()
								);
							} else {
								vm.resetSettingsScreenB(
									vm.enableRegisterB(),
									true, true,
									vm.allowSettingB(),
									vm.screenBMode()
								);
							}
						}
					});
				});
		}

		/**
		 * Display page F
		 * */
		showDialogScreenF(panel: number, screen: string = 'A') {
			let vm = this,
				isAllowSetting: boolean = false,
				employeesList: Array<EmployeeDto> = [];

			let model = (screen === 'A') ? vm.modelA() : vm.modelB();

			employeesList = (panel === 1) ? model.approverPanel() : model.employeesRepresentative();

			switch (screen) {
				case 'A':
					isAllowSetting = vm.allowSettingA()
					break;

				case 'B':
					isAllowSetting = vm.allowSettingB()
					break;
			}

			if (isAllowSetting) {

				vm.$window.storage("workPlaceCodeList", {
					workplaceId: (screen === 'A') ? null : vm.selectedWkpId(),
					codeList: employeesList
				});

				vm.$window.modal("/view/cmm/024/f/index.xhtml",
					{ title: vm.$i18n('CMM024_94') }).then(function () {
						//36承認者パネル
						vm.$window.storage("newWorkPlaceCodeList").then((data) => {
							let dataList: any = [];
							if (!nts.uk.util.isNullOrEmpty(data)) {
								if (data.codeList) {
									dataList = data.codeList;
									dataList = (dataList.length <= 0) ? vm.addEmptySetting() : dataList;
									dataList = dataList.slice(0, 5); //max 5
								}

								if (panel === 1)
									model.approverPanel(dataList);
								else
									model.employeesRepresentative(dataList);

								if ((screen === 'A'))
									vm.modelA(model);
								else
									vm.modelB(model);
							}
						});
					});
			}
		}

		/*
		* Get history listing
		* */
		companyScheduleHistoryListing() {
			let vm = this,
				selectedHistory: any = null,
				tempScheduleList: Array<ScheduleHistoryDto> = [];

			vm.$blockui('show');

			//get Schedule of a company
			service.getScheduleHistoryListByCompany()
				.done((response) => {

					if (!nts.uk.util.isNullOrUndefined(response)) {
						//A1_6
						vm.company().id = response.companyId;
						vm.company().name = response.companyName;

						//A2-6 - Schedule history listing
						if (!nts.uk.util.isNullOrEmpty(response.scheduleHistory)) {
							response.scheduleHistory.map((history) => {
								tempScheduleList.push(
									new ScheduleHistoryDto(
										history.startDate,
										history.endDate,
										history.personalInfoApprove,
										history.personalInfoConfirm
									)
								);
							});
							//sort DECS
							tempScheduleList = _.orderBy(tempScheduleList, 'code', 'desc');
						}
						//create schedule history listing
						vm.companyScheduleHistoryList(tempScheduleList);
						if (vm.companyScheduleHistoryList().length > 0) {
							//get the first item of list
							selectedHistory = vm.companyScheduleHistoryList()[0];
							vm.companyScheduleHistorySelected(selectedHistory.code);
							vm.companyScheduleHistoryObjSelected(selectedHistory);
							vm.dispplayInfoOnScreenA(selectedHistory.code);
						} else {
							vm.companyScheduleHistorySelected(null);
							vm.companyScheduleHistoryObjSelected(null);
							vm.enableRegisterA(false);
							vm.modelB().approverPanel([]);
							vm.modelB().employeesRepresentative([]);
							vm.dispplayInfoOnScreenA(null);
						}

					}

					vm.$blockui('hide');

				})
				.always(() => {
					vm.$blockui('hide');
				});
		}

		/**
		 * 36承認者一覧リンクラベル
		 * @model string : screen A | B
		 * @panel nuner	: 1: approver | 2: representative
		 * @listOfEmployees array : the list of employees
		 * return the list of employees who have authority to approve schedule history
		 */
		createEmployeesPanelList(model: string = 'A', panel: number = 1, listOfEmployees: Array<any> = []) {

			let vm = this,
				employeesList: Array<EmployeeDto> = [];

			listOfEmployees &&
				listOfEmployees.map((employee) => {
					employeesList.push(
						new EmployeeDto(
							_.trim(employee.employeeCode),
							_.trim(employee.employeeName),
							_.trim(employee.personId),
							_.trim(employee.employeeId)
						)
					);
				});

			if (employeesList.length <= 0) {
				employeesList = vm.addEmptySetting();
			}

			switch (model) {
				case 'A':
					if (panel === 1)
						vm.modelA().approverPanel(employeesList);
					else
						vm.modelA().employeesRepresentative(employeesList); //従業員代表指定リンクラベル
					break;

				case 'B':
					if (panel === 1)
						vm.modelB().approverPanel(employeesList);
					else
						vm.modelB().employeesRepresentative(employeesList); //従業員代表指定リンクラベル
					break;
			}
		}

		/**
		 * Update the schedule history listing
		 * @params currentList : Array
		 * @params data	:
		*/
		updateScheduleHistoryListing(currentList: any, data: any) {

			let vm = this,
				newScheduleHistoryList: Array<ScheduleHistoryDto> = [],
				oldStartDate: string = null,
				newEndDate: string = service.END_DATE;

			newScheduleHistoryList = currentList;

			if (newScheduleHistoryList.length > 0) {
				//update endDate before insert to first
				let beforeNewStartDate = moment(
					data.scheduleHistoryItem.startDate,
					"YYYY/MM/DD")
					.subtract(1, "days").format("YYYY/MM/DD");

				//update newEndDate for old first element of the listing
				oldStartDate = newScheduleHistoryList[0].startDate;
				newScheduleHistoryList[0] = new ScheduleHistoryDto(
					oldStartDate, beforeNewStartDate,
					newScheduleHistoryList[0].personalInfoApprove,
					newScheduleHistoryList[0].personalInfoConfirm,
				);

				//update new element to first element of the new listing
				newScheduleHistoryList.unshift(data.scheduleHistoryItem);

			} else {
				let tempDate = moment(data.scheduleHistoryItem.startDate, "YYYY/MM/DD").format("YYYY/MM/DD");
				newScheduleHistoryList.push(new ScheduleHistoryDto(tempDate, newEndDate));
			}

			return newScheduleHistoryList;
		}

		/**
		 * re-update the historical period
		*/
		updateHistoricalPeriod(scheduleHistory: any, data: any) {

			let vm = this,
				newScheduleHistory = scheduleHistory;

			newScheduleHistory[0] = new ScheduleHistoryDto(
				data.newScheduleHistory.startDate,
				newScheduleHistory[0].endDate,
				newScheduleHistory[0].personalInfoApprove,
				newScheduleHistory[0].personalInfoConfirm,
			);

			if (newScheduleHistory.length > 1) {
				newScheduleHistory[1] = new ScheduleHistoryDto(
					newScheduleHistory[1].startDate,
					data.newScheduleHistory.newEndDate,
					newScheduleHistory[1].personalInfoApprove,
					newScheduleHistory[1].personalInfoConfirm
				);
			}

			return newScheduleHistory;
		}

		/***********************************************************************
		 * Screen B
		 * ********************************************************************/
		dispplayInfoOnScreenB(value: string = null) {
			let vm = this,
				isAllowSetting: boolean = false,
				objFind: ScheduleHistoryDto = null,
				scheduleHistory: Array<any> = [];

			if (vm.workplaceScheduleHistoryList().length > 0) {
				value = (value === null) ? vm.workplaceScheduleHistoryList()[0].code : value;
				isAllowSetting = (vm.workplaceScheduleHistoryList()[0].code === value);
				vm.resetSettingsScreenB(isAllowSetting, isAllowSetting, isAllowSetting, true, ScreenModel.EDIT);
				scheduleHistory = vm.workplaceScheduleHistoryList();
				objFind = _.find(scheduleHistory, (x) => x.code === value);

				if (!nts.uk.util.isNullOrEmpty(objFind)) {
					vm.workplaceScheduleHistoryObjSelected(objFind); //send to screen B, D
				}

				//36承認者パネル
				vm.createEmployeesPanelList('B', 1, objFind.personalInfoApprove);
				//従業員代表パネル
				vm.createEmployeesPanelList('B', 2, objFind.personalInfoConfirm);
			} else {
				vm.resetSettingsScreenB(false, true, false, true, ScreenModel.ADDNEW);
			}
		}

		/**
		 * Active Panel B
		*/
		showPanelB() {
			let vm = this;

			vm.isShowPanelB(true);
		}

		/**
		 * Register data on Screen B
		 * */
		RegisterB() {
			let vm = this,
				currentScheduleItem = vm.modelB();

			let tempList = currentScheduleItem.approverPanel().filter(function (item) {
				return (item.employeeCode !== '-1' && item.employeeCode !== null);
			});

			if (tempList.length <= 0) {
				vm.$dialog.error({ messageId: 'Msg_1790' });
				$('.employee-lists-b').focus();
				return;
			}

			switch (vm.screenBMode()) {
				case ScreenModel.EDIT:
					vm.updateScheduleHistoryByWorkplace();
					break;

				case ScreenModel.ADDNEW:
					vm.registerScheduleHistoryByWorkplace();
					break;
			}

			//reset screen to nomal model
			vm.screenBModeAddNew(true);
			vm.screenBModeEdit(true);
			vm.enableScheduleHistoryB(true);
		}

		/**
		 * Display page C
		 * */
		screenBShowDialogC() {

			let vm = this,
				currentScheduleHistoryList: Array<ScheduleHistoryDto> = vm.workplaceScheduleHistoryList();

			vm.$window.storage("scheduleHistorySelected", vm.workplaceScheduleHistoryObjSelected());
			vm.$window.modal("/view/cmm/024/c/index.xhtml", { title: vm.$i18n('CMM024_92') }).then(function () {
				//開始年月日テキストボックス
				vm.$window.storage("newScheduleHistory").then((data: any) => {

					if (!nts.uk.util.isNullOrUndefined(data.scheduleHistoryItem)) {

						currentScheduleHistoryList = vm.updateScheduleHistoryListing(
							vm.workplaceScheduleHistoryList(),
							data);

						//re-update the list
						vm.workplaceScheduleHistoryList(currentScheduleHistoryList);
						vm.workplaceScheduleHistorySelected(currentScheduleHistoryList[0].code);
						//update schedule history
						if (data.registrationHistoryType == HistoryRes.HISTORY_NEW) {
							vm.modelB(new Model(
								vm.$user.companyCode,
								data.scheduleHistoryItem.startDate,
								data.scheduleHistoryItem.endDate,
								vm.addEmptySetting(),
								vm.addEmptySetting()
							));
						} else { //clone
							vm.modelB().startDate(data.scheduleHistoryItem.startDate);
							vm.modelB().endDate(data.scheduleHistoryItem.endDate);
						}
						vm.resetSettingsScreenB(true, false, false, false, ScreenModel.ADDNEW);
					} else {
						if (currentScheduleHistoryList.length > 0)
							vm.resetSettingsScreenB(true, true, true, true, ScreenModel.EDIT);
						else
							vm.resetSettingsScreenB(false, true, false, false, ScreenModel.EDIT);
					}
				});
			});
		}

		resetSettingsScreenB(register: boolean, addnew: boolean,
			edit: boolean, enablePeriodlist: boolean, mode: number) {
			let vm = this;

			vm.enableRegisterB(register);
			vm.screenBModeAddNew(addnew);
			vm.screenBModeEdit(edit);
			vm.enableScheduleHistoryB(enablePeriodlist);
			vm.screenBMode(mode);
			vm.allowSettingB(register);
		}
		/**
		 * 
		*/
		workplaceScheduleHistoryListing() {
			let vm = this,
				selectedHistory: any = null,
				tempScheduleList: Array<ScheduleHistoryDto> = [];

			vm.screenBModeEdit(false);
			vm.screenBMode(ScreenModel.EDIT);

			vm.$blockui('show');

			//get Schedule of a workplace
			service.getScheduleHistoryListWorkPlace(vm.selectedWkpId())
				.done((response) => {

					if (!nts.uk.util.isNullOrUndefined(response)) {
						//A2-6 - Schedule history listing
						if (!nts.uk.util.isNullOrEmpty(response.scheduleHistory)) {
							response.scheduleHistory.map((history) => {
								tempScheduleList.push(
									new ScheduleHistoryDto(
										history.startDate,
										history.endDate,
										history.personalInfoApprove,
										history.personalInfoConfirm,
									)
								);
							});
							//sort DECS
							tempScheduleList = _.orderBy(tempScheduleList, 'code', 'desc');
						}

						//create schedule history listing
						vm.workplaceScheduleHistoryList(tempScheduleList);
						if (vm.workplaceScheduleHistoryList().length > 0) {
							//get the first item of list
							selectedHistory = vm.workplaceScheduleHistoryList()[0];
							vm.workplaceScheduleHistorySelected(selectedHistory.code);
							vm.workplaceScheduleHistoryObjSelected(selectedHistory);
							vm.dispplayInfoOnScreenB(selectedHistory.code);
						} else {
							vm.workplaceScheduleHistorySelected(null);
							vm.workplaceScheduleHistoryObjSelected(null);
							vm.modelB().approverPanel([]);
							vm.modelB().employeesRepresentative([]);
							vm.dispplayInfoOnScreenB(null);
						}
					}

					vm.$blockui('hide');

				})
				.fail(() => vm.$blockui('hide'))
				.always(() => {
					vm.$blockui('hide');
				});
		}

		/**
		 * 
		 */
		updateScheduleHistoryByWorkplace() {
			let vm = this,
				currentScheduleItem = vm.modelB();

			vm.$blockui('show');

			let params = {
				workPlaceId: vm.selectedWkpId(), //最新履歴の職場ID			
				startDate: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate(), 'YYYY-MM-DD'), //期間
				approveList: [], //36承認者一覧リンクラベル
				confirmedList: [], // 従業員代表指定リンクラベル
				startDateBeforeChange: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD')//期間
			};

			currentScheduleItem.approverPanel().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.approveList.push(item.employeeId);
			})

			currentScheduleItem.employeesRepresentative().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.confirmedList.push(item.employeeId);
			})

			service.updateScheduleHistoryByWorlplace(params)
				.done((response) => {
					vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
						vm.workplaceScheduleHistoryListing();
						vm.$blockui('hide');
					});
				}).fail((error) => {
					vm.$dialog.info({ messageId: error.messageId });
				}).always(() => {
					vm.$blockui('hide');
				});
		}


		registerScheduleHistoryByWorkplace() {
			let vm = this,
				currentScheduleItem = vm.modelB();

			vm.$blockui('show');

			let params = {
				workPlaceId: vm.selectedWkpId(), //最新履歴の職場ID
				startDate: moment.utc(currentScheduleItem.startDate(), 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate(), 'YYYY-MM-DD'), //期間
				approveList: [], //36承認者一覧リンクラベル
				confirmedList: []// 従業員代表指定リンクラベル
			};

			currentScheduleItem.approverPanel().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.approveList.push(item.employeeId);
			})

			currentScheduleItem.employeesRepresentative().map((item) => {
				if (item.employeeCode != '-1' && item.employeeCode != null)
					params.confirmedList.push(item.employeeId);
			})

			service.registerScheduleHistoryByWorlplace(params)
				.done((response) => {
					vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
						vm.workplaceScheduleHistoryListing();
						vm.$blockui('hide');
					});
				}).fail((error) => {
					vm.$dialog.info({ messageId: error.messageId }).then(() => vm.$blockui('hide'));
				}).always(() => {
					vm.$blockui('hide');
				});
		}
		/**
		 * */
		addEmptySetting() {
			let vm = this,
				employeesList: Array<EmployeeDto> = [];
			let newEmployee = new EmployeeDto('-1', '未設定');
			employeesList.push(newEmployee);

			return employeesList;
		}
	}
}