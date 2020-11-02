/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


module nts.uk.at.ktg005.a {

	const requestUrl = {
		startScreenA: 'screen/at/ktg/ktg005/start_screen_a',
		getOptionalWidgetDisplay: "screen/at/OptionalWidget/getOptionalWidgetDisplay"
	}

	@bean()
	export class ViewModel extends ko.ViewModel {

		executionAppResult: KnockoutObservable<IExecutionAppResult> = ko.observable({
			topPagePartName: '',
			numberApprovals: 0,
			numberNotApprovals: 0,
			numberDenials: 0,
			numberRemand: 0,
			dueDate: '0',
			deadlineSetting: false,
			appSettings: [],
			employeeCharge: false
		});


		constructor() {
			super();
		}

		created(params: any) {

			let vm = this;

			vm.loadData();
		}

		loadData() {

			let vm = this,
				cache = nts.uk.ui.windows.getShared('cache'),
				topPagePartCode = $(location).attr('search').split('=')[1];

			vm.$ajax(requestUrl.getOptionalWidgetDisplay, topPagePartCode).done((widDisplay: IOptionalWidgetDisplay) => {
				let
					query = cache ? {
						companyId: vm.$user.companyId,
						startDate: cache.currentOrNextMonth ? widDisplay.datePeriodDto.strCurrentMonth : widDisplay.datePeriodDto.strNextMonth,
						endDate: cache.currentOrNextMonth ? widDisplay.datePeriodDto.endCurrentMonth : widDisplay.datePeriodDto.endNextMonth,
						employeeId: vm.$user.employeeId
					} :
						{
							companyId: vm.$user.companyId,
							startDate: widDisplay.datePeriodDto.strCurrentMonth,
							endDate: widDisplay.datePeriodDto.endCurrentMonth,
							employeeId: vm.$user.employeeId
						}
					;
				vm.$blockui("invisible");
				vm.$ajax(requestUrl.startScreenA, query).done((setting: IExecutionAppResult) => {
					setting.appSettings = _.chain(setting.appSettings).sortBy(['item']).filter(['displayType', 1]).value();
					vm.executionAppResult(setting);
				}).fail((error) => {
					this.$dialog.alert({ messageId: error.messageId });
				}).always(() => {
					this.$blockui("clear");
				});
			});
		}

		getText(item: ApplicationStatusWidgetItem) {
			let vm = this,

				result = "";
			if (item === ApplicationStatusWidgetItem.ApplicationDeadlineForThisMonth) {
				if (vm.executionAppResult().deadlineSetting === true) {
					result = moment(vm.executionAppResult().dueDate, 'yyyy/MM/DD HH:mm:ss').format('MM/DD(dd)')
				} else {
					result = vm.$i18n.text("KTG005_8");
				}
			} else {
				if (item === ApplicationStatusWidgetItem.NumberOfApprovedCases) {
					result = vm.$i18n.text("KTG005_7", [vm.executionAppResult().numberApprovals.toString()]);
				}
				if (item === ApplicationStatusWidgetItem.NumberOfUnApprovedCases) {
					result = vm.$i18n.text("KTG005_7", [vm.executionAppResult().numberNotApprovals.toString()]);
				}
				if (item === ApplicationStatusWidgetItem.NumberOfDenial) {
					result = vm.$i18n.text("KTG005_7", [vm.executionAppResult().numberDenials.toString()]);
				}
				if (item === ApplicationStatusWidgetItem.NumberOfRemand) {
					result = vm.$i18n.text("KTG005_7", [vm.executionAppResult().numberRemand.toString()]);
				}

			}
			return result;
		}

		getLabel(itemType: number) {
			const itemText = [
				{ itemType: ApplicationStatusWidgetItem.NumberOfApprovedCases, text: 'KTG005_3' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfUnApprovedCases, text: 'KTG005_4' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfDenial, text: 'KTG005_5' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfRemand, text: 'KTG005_2' }
				, { itemType: ApplicationStatusWidgetItem.ApplicationDeadlineForThisMonth, text: 'KTG005_6' }];
			let item = _.find(itemText, ['itemType', itemType]),
				vm = new ko.ViewModel();

			return item ? vm.$i18n.text(item.text) : "";
		}

		jumpToCmm045() {
			let vm = this;

			vm.$jump.self("/view/cmm/045/a/index.xhtml?a=1");
		}

		openScreenB() {
			let vm = this;

			vm.$window.modal("/view/ktg/005/b/index.xhtml").then(() => {
				vm.loadData();
			});
		}

	}

	export interface IOptionalWidgetDisplay {
		datePeriodDto: IDatePeriodDto;
		optionalWidgetImport: IOptionalWidgetImport;
	}

	export interface IDatePeriodDto {
		endCurrentMonth: string;
		endNextMonth: string;
		strCurrentMonth: string;
		strNextMonth: string;
	}

	export interface IOptionalWidgetImport {
		height: number;
		topPageCode: string;
		topPageName: string;
		topPagePartID: string;
		widgetDisplayItemExport: Array<IWidgetDisplayItemExport>;
		width: number
	}

	export interface IWidgetDisplayItemExport {
		displayItemType: number;
		notUseAtr: number;
	}



	export interface IExecutionAppResult {
		//名称
		topPagePartName: string;
		//承認件数
		numberApprovals: number;
		//未承認件数
		numberNotApprovals: number;
		//否認件数
		numberDenials: number;
		//差し戻し件数
		numberRemand: number;
		//締め切り日									
		dueDate: string;
		//申請締切利用設定
		deadlineSetting: boolean;
		//申請状況の詳細設定
		appSettings: Array<IDetailSettingAppStatus>;
		//勤怠担当者である
		employeeCharge: boolean;
	}

	export interface IDetailSettingAppStatus {
		// 表示区分
		displayType: number;

		// 項目
		item: number;
	}

	enum ApplicationStatusWidgetItem {
		//承認された件数
		NumberOfApprovedCases = 0,
		//未承認件数
		NumberOfUnApprovedCases = 1,
		//否認された件数
		NumberOfDenial = 2,
		//差し戻し件数
		NumberOfRemand = 3,
		//今月の申請締め切り日
		ApplicationDeadlineForThisMonth = 4

	}


}

