module nts.uk.com.view.cmm024.a {
	export module service {

		let CMM024_API = {
			//Sceen A
			screenA_GetScheduleHistoryList: 'approve/company/initial',
			screenA_RegisterScheduleHistory: 'approve/company/screen/a/register',
			screenA_UpdateScheduleHistory: 'approve/company/screen/a/update',
			//Screen B
			screenB_GetScheduleHistoryList: 'approve/byworkplace/initial',
			screenB_RegisterScheduleHistory: 'approve/byworkplace/screen/b/register',
			screenB_UpdateScheduleHistory: 'approve/byworkplace/screen/b/update',
			//Screen D
			screenD_UpdateScheduleHistoryByCompany: 'approve/company/screen/d/update',
			screenD_DeleteScheduleHistoryByCompany: 'approve/company/screen/d/delete',
			screenD_UpdateScheduleHistoryByWorkplace: 'approve/byworkplace/screen/d/update',
			screenD_DeleteScheduleHistoryByWorkplace: 'approve/byworkplace/screen/d/delete',
			//Screen F
			screenF_GetEmployeesList: 'show/inswap/employees',

		};

		export const END_DATE = '9999/12/31';

		export class ScheduleHistoryDto {
			code: string;
			startDate: string;
			endDate: string;
			display: string;
			personalInfoApprove: Array<EmployeeDto> = [];
			personalInfoConfirm: Array<EmployeeDto> = [];

			constructor(
				stDate: string, endDate: string,
				personalInfoApprove: Array<EmployeeDto> = [],
				personalInfoConfirm: Array<EmployeeDto> = []
			) {
				this.code = moment(stDate, 'YYYY/MM/DD').format('YYYYMMDD');
				this.startDate = stDate;
				this.endDate = endDate;
				this.display = this.startDate + ' ～ ' + this.endDate;
				this.personalInfoApprove = personalInfoApprove;
				this.personalInfoConfirm = personalInfoConfirm;
			}
		}

		export class EmployeeDto {
			personId: string;
			employeeCode: string;
			employeeName: string;
			employeeId: string;
			displayName: string;
			constructor(code?: string, name?: string, personId?: string, employeeId?: string) {
				let addChar: string = (!_.isEmpty(code) && !_.isNull(code)) ? '0' : ' ';

				this.employeeCode = code;
				this.employeeName = name;
				this.personId = personId;
				this.employeeId = employeeId;
				this.displayName = !nts.uk.util.isNullOrEmpty(_.trim(this.employeeCode)) ? _.trim(name) : '';
			}
		}

		export enum HistoryRes {
			HISTORY_TRANSFER = 0,
			HISTORY_NEW = 1
		}

		export enum HistoryUpdate {
			HISTORY_DELETE = 0,
			HISTORY_EDIT = 1
		}

		export enum ScreenModel {
			NORMAL = -1,
			ADDNEW = 0,
			EDIT = 1,
			DELETE = 2
		}

		export class Model {
			workPlaceCompanyId: string = null;
			startDate: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
			endDate: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
			//36承認者一覧リンクラベル - A3_5, A3_7
			approverPanel: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);
			//従業員代表指定リンクラベル - A3_8, A3_10
			employeesRepresentative: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);

			constructor(workPlaceCompanyId?: string, startDate?: Date, endDate?: Date,
				approverPanel?: Array<EmployeeDto>, empRepresentative?: Array<EmployeeDto>) {
				this.workPlaceCompanyId = workPlaceCompanyId;
				if (startDate) this.startDate(startDate);
				if (endDate) this.endDate(endDate);
				if (approverPanel) this.approverPanel(approverPanel);
				if (empRepresentative) this.employeesRepresentative(empRepresentative);
			}
		}

		export interface ScheduleHistory {
			newScheduleHistory: { code?: string, startDate?: string, endDate?: string, display?: string },
			RegistrationHistoryType: number; // 0 , 1, 2
		}

		export interface ScheduleHistoryModel {
			allowStartDate?: string,
			scheduleHistoryUpdate?: ScheduleHistoryDto, //最新履歴の期間 startDate & endDate
			workPlaceCompanyId?: string, //ログイン会社ID | 最新履歴の職場ID
			screen?: string,
		}

		export interface ComponentOption {
			targetBtnText: string;
			tabIndex: number;
		}
		export interface WorkplaceModel {
			workplaceId: string;
			workplaceCode: string;
			workplaceName: string;
		}

		/*
		* Functions on Screen A
		* */

		/**
		 * Returns information of a company
		 * @companyId string : ログイン会社ID
		 */
		export function getScheduleHistoryListByCompany(): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenA_GetScheduleHistoryList);
		}

		/**
		 * New registration a schedule history
		 * params {
		 * @companyId string : ログイン会社ID				 
		 * @startDate date : 期間
		 * @endDate date : 期間
		 * @approvedList string[]: 36承認者一覧リンクラベル
		 * @confirmedList string[]: 従業員代表指定リンクラベル
		 * }
		 */
		export function registerScheduleHistoryByCompany(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenA_RegisterScheduleHistory, params);
		}

		/**
		 * update a schedule history
		 * params {
		 * @companyId string : ログイン会社ID				 
		 * @startDate date : 期間
		 * @endDate date : 期間
		 * @approvedList string[]: 36承認者一覧リンクラベル
		 * @confirmedList string[]: 従業員代表指定リンクラベル
		 * @startDateBeforeChange : 期間
		 * }
		 */
		export function updateScheduleHistoryByCompany(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenA_UpdateScheduleHistory, params);
		}

		/**
		 * Screen B
		 */
		/**
		 * Returns information of a workplace
		 * @workPlaceId string : 選択されている職場ID
		 */
		export function getScheduleHistoryListWorkPlace(workPlaceId: string): JQueryPromise<any> {
			if (!nts.uk.util.isNullOrEmpty(workPlaceId))
				return nts.uk.request.ajax('at', CMM024_API.screenB_GetScheduleHistoryList + '/' + workPlaceId);
			else
				return null;
		}

		/**
		 * New registration a schedule history
		 * params {
		 * @workPlaceId string : 職場ID	 
		 * @startDate date : 期間
		 * @endDate date : 期間
		 * @approvedList string[]: 36承認者一覧リンクラベル
		 * @confirmedList string[]: 従業員代表指定リンクラベル
		 * }
		 */
		export function registerScheduleHistoryByWorlplace(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenB_RegisterScheduleHistory, params);
		}

		/**
		 * update a schedule history
		 * params {
		 * @workPlaceId string : 職場ID	 
		 * @startDate date : 期間
		 * @endDate date : 期間
		 * @approvedList string[]: 36承認者一覧リンクラベル
		 * @confirmedList string[]: 従業員代表指定リンクラベル
		 * @startDateBeforeChange : 期間
		 * }
		 */
		export function updateScheduleHistoryByWorlplace(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenB_UpdateScheduleHistory, params);
		}

		/**
		 * Screen D
		*/

		/**
		 * update a schedule history
		 * params {
		 * @companyId string : 会社ID
		 * @startDate date : 期間
		 * @endDate date : 期間	
		 * @startDateBeforeChange : 期間
		 * }
		 */
		export function updateAScheduleHistoryByCompany(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenD_UpdateScheduleHistoryByCompany, params);
		}

		/**
		 * delete a schedule history (delete all)
		 * params {
		 * @companyId string : 会社ID
		 * @startDate date : 期間
		 * @endDate date : 期間	
		 * }
		 */
		export function deleteAScheduleHistoryByCompany(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenD_DeleteScheduleHistoryByCompany, params);
		}

		/**
		 * update a schedule history
		 * params {
		 * @workPlaceId string : 職場ID	
		 * @startDate date : 期間
		 * @endDate date : 期間	
		 * @startDateBeforeChange : 期間
		 * }
		 */
		export function updateAScheduleHistoryByWorkplace(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenD_UpdateScheduleHistoryByWorkplace, params);
		}

		/**
		 * delete a schedule history (delete all)
		 * params {
		 * @workPlaceId string : 職場ID	
		 * @startDate date : 期間
		 * @endDate date : 期間	
		 * }
		 */
		export function deleteAScheduleHistoryByWorkplace(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenD_DeleteScheduleHistoryByWorkplace, params);
		}


		/**
		 * Screen F
		*/

		/**
		 * return employees listing
		 * params {
		 * @workPlaceId string : 職場ID	
		 * @baseDate date : 期間		
		 * }
		 */
		export function getEmployeesListByWorkplace(params): JQueryPromise<any> {
			return nts.uk.request.ajax('at', CMM024_API.screenF_GetEmployeesList, params);
		}
	}
}