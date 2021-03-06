module nts.uk.at.view.ksu001.g.test {
	import setShare = nts.uk.ui.windows.setShared;
	import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
	import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
	// Import
	export module viewmodel {
		export class ScreenModel {
			currentScreen: any = null;
			dateValue: KnockoutObservable<any>;
			startDateString: KnockoutObservable<string>;
			endDateString: KnockoutObservable<string>;
			enable: KnockoutObservable<boolean>;
			required: KnockoutObservable<boolean>;

			//Declare kcp005 list properties
			listComponentOption: any;
			selectedCode: KnockoutObservable<string>;
			multiSelectedCode: KnockoutObservableArray<string>;
			isShowAlreadySet: KnockoutObservable<boolean>;
			isDialog: KnockoutObservable<boolean>;
			isShowNoSelectRow: KnockoutObservable<boolean>;
			isMultiSelect: KnockoutObservable<boolean>;
			isShowWorkPlaceName: KnockoutObservable<boolean>;
			isShowSelectAllButton: KnockoutObservable<boolean>;
			employeeList: KnockoutObservableArray<UnitModel>;
			// startDate for validate
			startDateValidate: KnockoutObservable<string>;
			alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;

			//Declare employee filter component
			ccg001ComponentOption: any;
			showinfoSelectedEmployee: KnockoutObservable<boolean> = ko.observable(false);
			// Options
			baseDate: KnockoutObservable<Date>;
			selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
			constructor() {
				var self = this;
				self.baseDate = ko.observable(new Date());
				self.selectedCode = ko.observable(null);
				self.multiSelectedCode = ko.observableArray([]);
				self.isShowAlreadySet = ko.observable(false);
				self.alreadySettingList = ko.observableArray([
					{ code: '1', isAlreadySetting: true },
					{ code: '2', isAlreadySetting: true }
				]);
				self.isDialog = ko.observable(true);
				self.isShowNoSelectRow = ko.observable(false);
				self.isMultiSelect = ko.observable(true);
				self.isShowWorkPlaceName = ko.observable(true);
				self.isShowSelectAllButton = ko.observable(true);

				this.employeeList = ko.observableArray<UnitModel>([]);

				self.enable = ko.observable(true);
				self.required = ko.observable(true);
				self.startDateString = ko.observable("2020/10/01");
				self.endDateString = ko.observable("2020/10/31");
				self.dateValue = ko.observable({ startDate: new Date(), endDate: new Date() });
				self.startDateString.subscribe(function (value) {
					self.dateValue().startDate = value;
					self.dateValue.valueHasMutated();
				});

				self.endDateString.subscribe(function (value) {
					self.dateValue().endDate = value;
					self.dateValue.valueHasMutated();
				});
				// Set component option

				let ccg001ComponentOption: any = {

					/** Common properties */
					systemType: 2, // ??????????????????
					showEmployeeSelection: false, // ???????????????
					showQuickSearchTab: true, // ??????????????????
					showAdvancedSearchTab: true, // ????????????
					showBaseDate: false, // ???????????????
					showClosure: true, // ?????????????????????
					showAllClosure: false, // ???????????????
					showPeriod: true, // ??????????????????
					periodFormatYM: false, // ??????????????????

					/** Required parameter */
					baseDate: moment().toISOString(), // ?????????
					//periodEndDate: self.dateValue().endDate,
					dateRangePickerValue: self.dateValue,
					inService: true, // ????????????
					leaveOfAbsence: true, // ????????????
					closed: true, // ????????????
					retirement: false, // ????????????

					/** Quick search tab options */
					showAllReferableEmployee: true, // ??????????????????????????????
					showOnlyMe: true, // ????????????
					showSameWorkplace: true, // ?????????????????????
					showSameWorkplaceAndChild: true, // ????????????????????????????????????

					/** Advanced search properties */
					showEmployment: false, // ????????????
					showWorkplace: true, // ????????????
					showClassification: true, // ????????????
					showJobTitle: true, // ????????????
					showWorktype: true, // ????????????
					isMutipleCheck: true, // ???????????????

					/** Return data */
					returnDataFromCcg001: function (data: Ccg001ReturnedData) {
						self.showinfoSelectedEmployee(true);
						self.selectedEmployee(data.listEmployee);

						//Convert list Object from server to view model list
						let items = _.map(data.listEmployee, item => {
							return {
								id: item.employeeId,
								code: item.employeeCode,
								name: item.employeeName,
								affiliationName: item.affiliationName,
								isAlreadySetting: true
							}
						});
						self.employeeList(items);

						//Fix bug 42, bug 43
						let selectList = _.map(data.listEmployee, item => {
							return item.employeeId;
						});
						self.multiSelectedCode(selectList);
					}
				}

				let listComponentOption: any = {
					isShowAlreadySet: self.isShowAlreadySet(),
					isMultiSelect: true,
					listType: ListType.EMPLOYEE,
					employeeInputList: self.employeeList,
					selectType: SelectType.SELECT_ALL,
					selectedCode: self.selectedCode,
					isDialog: false,
					isShowNoSelectRow: self.isShowNoSelectRow(),
					alreadySettingList: self.alreadySettingList,
					isShowWorkPlaceName: self.isShowWorkPlaceName(),
					isShowSelectAllButton: false,
					maxRows: 10,
					isSelectAllAfterReload: true
				};

				$('#ccgcomponent').ntsGroupComponent(ccg001ComponentOption);
				$('#component-items-list').ntsListComponent(listComponentOption);

			}
			openDialog(): void {
				let self = this;
				let request: any = {};
				request.startDate = moment(self.dateValue().startDate).format('YYYY/MM/DD');
				request.endDate = moment(self.dateValue().endDate).format('YYYY/MM/DD');
				let lisId: Array<string> = []; 
				_.forEach(self.selectedCode(), code => {
					_.map(self.employeeList(), item =>{
						if(item.code === code){
							lisId.push(item.id);							
						}
					});
				})
				request.employeeIDs = lisId;
				setShare('dataShareDialogG', request);
				self.currentScreen = nts.uk.ui.windows.sub.modal("/view/ksu/001/g/index.xhtml");
			}
			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();

				dfd.resolve();
				return dfd.promise();
			}


		}
		export interface UnitAlreadySettingModel {
			code: string;
			isAlreadySetting: boolean;
		}
		export class ListType {
			static EMPLOYMENT = 1;
			static Classification = 2;
			static JOB_TITLE = 3;
			static EMPLOYEE = 4;
		}


		export class SelectType {
			static SELECT_BY_SELECTED_CODE = 1;
			static SELECT_ALL = 2;
			static SELECT_FIRST_ITEM = 3;
			static NO_SELECT = 4;
		}
		export class UnitModel {
			id: string;
			code: string;
			name: string;
			affiliationName: string;
			isAlreadySetting: boolean;
			constructor(x: EmployeeSearchDto) {
				let self = this;
				if (x) {
					self.code = x.employeeCode;
					self.name = x.employeeName;
					self.affiliationName = x.workplaceName;
					self.isAlreadySetting = false;
				} else {
					self.code = "";
					self.name = "";
					self.affiliationName = "";
					self.isAlreadySetting = false;
				}
			}
		}
	}
}