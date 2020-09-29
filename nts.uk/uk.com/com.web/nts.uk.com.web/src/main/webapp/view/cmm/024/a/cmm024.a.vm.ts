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
		settingSpace: string = '　　　　　　　　　　　　';

		//Screen A
		modelA: KnockoutObservable<Model> = ko.observable(new Model());
		companyScheduleHistoryList: KnockoutObservableArray<ScheduleHistoryDto> = ko.observableArray([]);
		companyScheduleHistorySelected: KnockoutObservable<string> = ko.observable();
		companyScheduleHistoryObjSelected: KnockoutObservable<ScheduleHistoryDto> = ko.observable(null);
		enableRegisterA: KnockoutObservable<boolean> = ko.observable(false);
		company: KnockoutObservable<any> = ko.observable({ name: null, id: null });
		screenAModeAddNew: KnockoutObservable<boolean> = ko.observable(true);
		screenAModeEdit: KnockoutObservable<boolean> = ko.observable(true);
		screenAMode: KnockoutObservable<number> = ko.observable(ScreenModel.NORMAL);
		enableScheduleHistoryA: KnockoutObservable<boolean> = ko.observable(true);
		//Screen B
		modelB: KnockoutObservable<Model> = ko.observable(new Model());
		enableRegisterB: KnockoutObservable<boolean> = ko.observable(false);
		screenBModeAddNew: KnockoutObservable<boolean> = ko.observable(true);
		screenBModeEdit: KnockoutObservable<boolean> = ko.observable(true);
		screenBMode: KnockoutObservable<number> = ko.observable(ScreenModel.NORMAL);
		enableScheduleHistoryB: KnockoutObservable<boolean> = ko.observable(true);

		workplaceScheduleHistoryList: KnockoutObservableArray<ScheduleHistoryDto> = ko.observableArray([]);
		workplaceScheduleHistorySelected: KnockoutObservable<string> = ko.observable();
		workplaceScheduleHistoryTextSelected: KnockoutObservable<string> = ko.observable(null);
		workplaceScheduleHistoryObjSelected: KnockoutObservable<ScheduleHistoryDto> = ko.observable(null);
		workplaceEmployeesListApproved: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);
		//従業員代表指定リンクラベル - B3_10
		workplaceEmployeesWaitingApprove: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);

		// KCP010
		kcp010Model: kcp010.viewmodel.ScreenModel;
		listComponentOption: ComponentOption;
		selectedWkpId: KnockoutObservable<string>;
		selectedCode: any;

		max_items: number = 5;
		linkSetting: string = 'linkSetting';

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
				tabIndex: 4
			};
			vm.kcp010Model = $('#wkp-component').ntsLoadListComponent(vm.listComponentOption);
			vm.kcp010Model.workplaceId.subscribe(function (wkpId) {
				if (wkpId) {
					vm.selectedWkpId(wkpId);
				}
			});

			//Screen A
			let company = JSON.parse(nts.uk.sessionStorage.nativeStorage.getItem('nts.uk.session.COMPANY'));
			vm.company({ name: company[0].companyName, id: company[0].companyId });
			vm.modelA().workPlaceCompanyId = vm.company().id;

		}

		// start point of object
		created(params: any) {
			let vm = this;

			_.extend(window, { vm });

			//Screen A
			vm.screenAMode(ScreenModel.NORMAL);
			vm.companyScheduleHistoryListing();

			//Screen B
			vm.screenBMode(ScreenModel.NORMAL);
			vm.workplaceScheduleHistoryListing();
		}

		// raise event when view initial success full
		mounted() {
			let vm = this,
				objFind: ScheduleHistoryDto = null,
				scheduleHistory: Array<any> = [];

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
		dispplayInfoOnScreenA(value: string) {
			let vm = this,
				isAllowUpdate = false,
				objFind: ScheduleHistoryDto = null,
				scheduleHistory: Array<any> = [];

			vm.screenAMode(ScreenModel.NORMAL);

			if (vm.companyScheduleHistoryList().length > 0) {
				//allow/not allow add new or updated with the first item
				isAllowUpdate = vm.companyScheduleHistoryList()[0].code === value;
				vm.screenAModeAddNew(isAllowUpdate);
				vm.screenAModeEdit(isAllowUpdate);

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
				vm.screenAModeAddNew(true);
				vm.screenAModeEdit(false);
			}
		}

		/**
		 * Register data on Screen A
		 * */
		RegisterA() {
			let vm = this;
			switch (vm.screenAMode()) {
				case ScreenModel.EDIT:
					vm.updateScheduleHistoryByCompany();
					break;

				case ScreenModel.ADDNEW:
					vm.registerScheduleHistoryByCompany();
					break;
				case ScreenModel.NORMAL:
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
				currentScheduleItem = vm.companyScheduleHistoryObjSelected();

			vm.$blockui('show');


			let params = {
				companyId: vm.company().id, //ログイン会社ID
				starDate: moment.utc(currentScheduleItem.startDate, 'YYYY-MM-DD'), //期間				 
				startDate: moment.utc(currentScheduleItem.startDate, 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate, 'YYYY-MM-DD'), //期間
				approveList: [], //36承認者一覧リンクラベル
				confirmedList: []// 従業員代表指定リンクラベル
			};

			if (currentScheduleItem.personalInfoApprove.length <= 0) {
				vm.$dialog.error({ messageId: 'Msg_18' });
				$('.employees-list-approved').focus();
				vm.$blockui('hide');
			} else {

				currentScheduleItem.personalInfoApprove.map((item) => {
					params.approveList.push(item.employeeId);
				})

				currentScheduleItem.personalInfoConfirm.map((item) => {
					params.confirmedList.push(item.employeeId);
				})

				service.registerScheduleHistoryByCompany(params)
					.done((response) => {
						vm.$dialog.info({ messageId: 'Msg_15' });
						vm.enableRegisterA(false);
						vm.$blockui('hide');
					}).fail((error) => {
						console.log(error);
					}).always(() => {
						vm.$blockui('hide');
					});
			}
		}

		/**
		 * Registration new schedule history
		*/
		updateScheduleHistoryByCompany() {
			let vm = this,
				currentScheduleItem = vm.companyScheduleHistoryObjSelected();

			vm.$blockui('show');

			let params = {
				companyId: vm.company().id, //ログイン会社ID			
				starDate: moment.utc(currentScheduleItem.startDate, 'YYYY-MM-DD'), //期間				 
				startDate: moment.utc(currentScheduleItem.startDate, 'YYYY-MM-DD'), //期間
				endDate: moment.utc(currentScheduleItem.endDate, 'YYYY-MM-DD'), //期間
				approveList: currentScheduleItem.personalInfoApprove, //36承認者一覧リンクラベル
				confirmedList: currentScheduleItem.personalInfoConfirm, // 従業員代表指定リンクラベル
				startDateBeforeChange: moment.utc(currentScheduleItem.startDate, 'YYYY-MM-DD')//期間
			};

			service.updateScheduleHistoryByCompany(params)
				.done((response) => {
					vm.$dialog.info({ messageId: 'Msg_18' });
					vm.screenAMode(ScreenModel.NORMAL);
					vm.$blockui('hide');
				}).fail((error) => {
					console.log(error);
				}).always(() => {
					vm.$blockui('hide');
				});
		}

		/**
		 * Display page C
		 * */
		screenADialogC(model: string = 'A') {

			let vm = this,
				currentScheduleHistoryList: Array<ScheduleHistoryDto>,
				newStartDate: Date = moment(new Date()).toDate(),
				newEndDate: string = service.END_DATE;

			vm.$window.storage("scheduleHistorySelected", vm.companyScheduleHistoryObjSelected());
			vm.$window.modal("/view/cmm/024/c/index.xhtml", { title: 'Test Title' }).then(function () {
				//開始年月日テキストボックス
				vm.$window.storage("newScheduleHistory").then((data: any) => {

					if (!nts.uk.util.isNullOrUndefined(data.scheduleHistoryItem)) {

						currentScheduleHistoryList = vm.companyScheduleHistoryList();
						if (currentScheduleHistoryList.length > 0) {

							//update endDate before insert to first
							let beforeNewStartDate = moment(
								data.scheduleHistoryItem.startDate,
								"YYYY/MM/DD")
								.subtract(1, "days").format("YYYY/MM/DD");

							//update newEndDate for old first element of the listing
							let oldStartDate = currentScheduleHistoryList[0].startDate;
							currentScheduleHistoryList[0] = new ScheduleHistoryDto(
								oldStartDate, beforeNewStartDate,
								currentScheduleHistoryList[0].personalInfoApprove,
								currentScheduleHistoryList[0].personalInfoConfirm,
							);

							//update new element to first element of the new listing
							currentScheduleHistoryList.unshift(data.scheduleHistoryItem);
							vm.companyScheduleHistoryList(currentScheduleHistoryList);

						} else {
							let tempDate = moment(data.scheduleHistoryItem.startDate, "YYYY/MM/DD").format("YYYY/MM/DD");
							currentScheduleHistoryList.push(new ScheduleHistoryDto(tempDate, newEndDate));
							vm.companyScheduleHistoryList(currentScheduleHistoryList);
						}

						currentScheduleHistoryList = vm.companyScheduleHistoryList();
						vm.companyScheduleHistorySelected(currentScheduleHistoryList[0].code);

						vm.enableRegisterA(true);
						vm.screenAModeAddNew(false);
						vm.screenAModeEdit(false);
						vm.enableScheduleHistoryA(false);
						vm.screenAMode(ScreenModel.ADDNEW);
						//update schedule history
						if (data.registrationHistoryType == HistoryRes.HISTORY_NEW) {
							vm.modelA(new Model(
								vm.$user.companyCode,
								data.scheduleHistoryItem.startDate,
								data.scheduleHistoryItem.endDate,
								vm.addEmptySetting(),
								vm.addEmptySetting()
							));
						} else { //clone
							vm.modelA().startDate(newStartDate);
							vm.modelA().endDate(moment(newEndDate, "YYYY/MM/DD").toDate());
						}

					} else {
						vm.enableRegisterA(vm.screenAMode() !== ScreenModel.NORMAL);
						vm.screenAModeAddNew(true);
						vm.screenAModeEdit(true);
						vm.enableScheduleHistoryA(true);
					}
				});
			});
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

			scheduleHistory = vm.companyScheduleHistoryList();
			allowStartDate = (scheduleHistory.length > 1)
				? scheduleHistory[1].startDate
				: null;

			data.allowStartDate = allowStartDate;
			data.scheduleHistoryUpdate = vm.companyScheduleHistoryObjSelected();
			data.workPlaceCompanyId = vm.company().id;
			data.screen = screen;

			if (!vm.screenAModeEdit()) {
				vm.$dialog.error({ messageId: 'Msg_154' });
				return;
			}

			//ScheduleHistoryModel
			vm.$window.storage("CMM024_D_INPUT", data);
			vm.$window.modal("/view/cmm/024/d/index.xhtml",
				{ title: 'Test Title' }).then(function () {

					vm.$window.storage("CMM024_D_RESULT").then((data: any) => {

						if (!nts.uk.util.isNullOrEmpty(data)) {
							if (data.RegistrationHistoryType === HistoryUpdate.HISTORY_EDIT) {
								if (!nts.uk.util.isNullOrUndefined(data.newScheduleHistory)
									&& (scheduleHistory.length > 0)) {

									scheduleHistory[0] = new ScheduleHistoryDto(
										data.newScheduleHistory.startDate,
										scheduleHistory[0].endDate,
										scheduleHistory[0].personalInfoApprove,
										scheduleHistory[0].personalInfoConfirm,
									);

									scheduleHistory[1] = new ScheduleHistoryDto(
										scheduleHistory[1].startDate,
										data.newScheduleHistory.newEndDate,
										scheduleHistory[1].personalInfoApprove,
										scheduleHistory[1].personalInfoConfirm
									);
								}
								vm.screenAMode(ScreenModel.EDIT);
							} else {
								vm.screenAMode(ScreenModel.DELETE);
							}

							if (screen === 'A') {
								vm.companyScheduleHistoryListing();
								vm.screenAModeAddNew(false);
								vm.screenAModeEdit(false);
							} else {
								//vm.companyScheduleHistoryList(scheduleHistory);
								//vm.companyScheduleHistorySelected(scheduleHistory[0].code);
							}
						} else {
							if (screen === 'A') {
								vm.screenAModeAddNew(true);
								vm.screenAModeEdit(true);
							} else {
								vm.screenBModeAddNew(true);
								vm.screenBModeEdit(true);
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
				employeesList: Array<EmployeeDto> = [];

			let model = (screen === 'A') ? vm.modelA() : vm.modelB();

			employeesList = (panel === 1) ? model.approverPanel() : model.employeesRepresentative();

			vm.$window.storage("workPlaceCodeList", {
				workplaceID: null,
				codeList: employeesList
			});

			vm.$window.modal("/view/cmm/024/f/index.xhtml",
				{ title: 'Test Title' }).then(function () {
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
										history.personalInfoConfirm,
									)
								);
							});

							//sort DECS
							tempScheduleList = _.orderBy(tempScheduleList, 'code', 'desc');
							//create schedule history listing
							vm.companyScheduleHistoryList(tempScheduleList);
						}

						if (vm.companyScheduleHistoryList().length > 0) {
							//get the first item of list
							selectedHistory = vm.companyScheduleHistoryList()[0];
							vm.companyScheduleHistorySelected(selectedHistory.code);
							vm.companyScheduleHistoryObjSelected(selectedHistory);
							vm.dispplayInfoOnScreenA(selectedHistory.code);
						} else {
							vm.companyScheduleHistorySelected(null);
							vm.companyScheduleHistoryObjSelected(null);
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

		/***********************************************************************
		 * Screen B
		 * ********************************************************************/
		dispplayInfoOnScreenB(value: string) {
			let vm = this,
				objFind: ScheduleHistoryDto = null,
				scheduleHistory: Array<any> = [];

			let isAllowUpdate = vm.workplaceScheduleHistoryList()[0].code === value;

			vm.screenBModeAddNew(isAllowUpdate);
			vm.screenBModeEdit(isAllowUpdate);

			scheduleHistory = vm.workplaceScheduleHistoryList();
			objFind = _.find(scheduleHistory, (x) => x.code === value);

			if (!nts.uk.util.isNullOrEmpty(objFind)) {
				vm.workplaceScheduleHistoryObjSelected(objFind); //send to screen B, D
			}

			vm.enableRegisterB(false);

			//36承認者パネル
			vm.createEmployeesPanelList('B', 1, objFind.personalInfoApprove);

			//従業員代表パネル
			vm.createEmployeesPanelList('B', 2, objFind.personalInfoConfirm);

		}

		/**
		 * Register data on Screen B
		 * */
		RegisterB() {

		}

		/**
		 * Display page C
		 * */
		screenBDialogC() {

			let vm = this,
				newScheduleHistoryList: Array<ScheduleHistoryDto>,
				newStartDate: Date = moment(new Date()).toDate(),
				newEndDate: Date = moment('9999/12/31', "YYYY/MM/DD").format("YYYY/MM/DD");

			vm.$window.storage("scheduleHistorySelected", vm.workplaceScheduleHistoryObjSelected());
			vm.$window.modal("/view/cmm/024/c/index.xhtml",
				{ title: 'Test Title' }).then(function () {
					//開始年月日テキストボックス
					vm.$window.storage("newScheduleHistory").then((data: ScheduleHistory) => {

						if (!nts.uk.util.isNullOrUndefined(data.newScheduleHistory)) {

							newScheduleHistoryList = vm.workplaceScheduleHistoryList();
							if (newScheduleHistoryList.length > 0) {
								//update endDate before insert to first
								let beforeNewStartDate = moment(
									data.newScheduleHistory.startDate,
									"YYYY/MM/DD")
									.subtract(1, "days").format("YYYY/MM/DD");

								let oldStartDate = newScheduleHistoryList[0].startDate;
								//update newEndDate for old first element of the listing
								newScheduleHistoryList[0] = new ScheduleHistoryDto(oldStartDate, beforeNewStartDate);
								//update new element to first element of the new listing
								newScheduleHistoryList.unshift(data.newScheduleHistory);
								vm.workplaceScheduleHistoryList(newScheduleHistoryList);
							} else {
								newStartDate = data.newScheduleHistory.startDate;
								newScheduleHistoryList.push(new ScheduleHistoryDto(newStartDate, newEndDate));
								vm.workplaceScheduleHistoryList(newScheduleHistoryList);
							}

							//update schedule history
							if (data.RegistrationHistoryType == HistoryRes.HISTORY_NEW) {
								vm.modelB(new Model(
									vm.$user.companyCode,
									data.newScheduleHistory.startDate,
									data.newScheduleHistory.endDate,
									vm.addEmptySetting(),
									vm.addEmptySetting()
								));
							} else { //clone
								vm.modelB().startDate(newStartDate);
								vm.modelB().endDate(newEndDate);
							}

							newScheduleHistoryList = vm.workplaceScheduleHistoryList();
							vm.workplaceScheduleHistorySelected(newScheduleHistoryList[0].code);

							vm.enableRegisterB(true);
							vm.screenBModeAddNew(false);
							vm.screenBModeEdit(false);
							vm.enableScheduleHistoryB(false);
							vm.screenBMode(ScreenModel.ADDNEW);

						} else {
							vm.enableRegisterB(false);
							vm.screenBModeAddNew(true);
							vm.screenBModeEdit(true);
							vm.enableScheduleHistoryB(true);
						}
					});
				});
		}

		/**
		 * Display page D
		 * */
		screenBDialogD() {
			let vm = this;

			if (!vm.screenBModeEdit()) {
				vm.$dialog.error(vm.$i18n.message('Msg_154'));
				return;
			}

		}

		screenBDialogFB310(data) {
			let vm = this,
				employeesList: Array<EmployeeDto> = [];
		}

		workplaceScheduleHistoryListing() {
			let vm = this,
				selectedHistory: any = null,
				tempScheduleList: Array<ScheduleHistoryDto> = [];

			vm.$blockui('show');

			//get Schedule of a company
			service.getScheduleHistoryListWorkPlace(vm.selectedWkpId())
				.done((response) => {

					if (!nts.uk.util.isNullOrUndefined(response)) {
						//A1_6
						//vm.company().id = response.companyId;
						//vm.company().name = response.companyName;

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
							//create schedule history listing
							vm.workplaceScheduleHistoryList(tempScheduleList);
						}

						if (vm.workplaceScheduleHistoryList().length > 0) {
							//get the first item of list
							selectedHistory = vm.workplaceScheduleHistoryList()[0];
							vm.workplaceScheduleHistorySelected(selectedHistory.code);
							vm.workplaceScheduleHistoryObjSelected(selectedHistory);
							vm.dispplayInfoOnScreenB(selectedHistory.code);
						} else {
							vm.workplaceScheduleHistorySelected(null);
							vm.workplaceScheduleHistoryObjSelected(null);
							vm.dispplayInfoOnScreenB(null);
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
		 */
		workplaceApproverPanelList() {
			let vm = this,
				employeesList: Array<EmployeeDto> = [];

			if (employeesList.length <= 0) {
				employeesList = vm.addEmptySetting();
			}
			vm.modelB().approverPanel(employeesList);
		}

		/**
		 * 従業員代表指定リンクラベル
		 */
		workplaceEmployeesRepresentativeList() {
			let vm = this,
				employeesList: Array<EmployeeDto> = [];

			if (employeesList.length <= 0) {
				employeesList = vm.addEmptySetting();
			}
			vm.modelB().employeesRepresentative(employeesList);
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

		updateModel(keyID: string, startDate: string, endDate: string,
			approverPanel: Array<EmployeeDto>, employeesRep: Array<EmployeeDto>) {
			let vm = this;
			vm.modelA().startDate(startDate);
			vm.modelA().endDate(endDate);
			vm.modelA().approverPanel(approverPanel);
			vm.modelA().employeesRepresentative(employeesRep);
		}
	}
}