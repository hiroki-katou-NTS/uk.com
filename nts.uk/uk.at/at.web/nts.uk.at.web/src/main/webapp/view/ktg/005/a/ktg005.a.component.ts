module nts.uk.ui.ktg005.a {

    const requestUrl = {
		startScreenA: 'screen/at/ktg/ktg005/start_screen_a',
		getOptionalWidgetDisplay: "screen/at/OptionalWidget/getOptionalWidgetDisplay"
	}
    @component({
        name: 'ktg-005-a',
        template: `
		<div>
			<div id="top_title">
			<!-- A1_1 -->

				<div class="limited-label"
					style="display: inline-block; margin-left: 7px; vertical-align: middle; line-height: 45px; width: 135px;"
					data-bind="text:executionAppResult().topPagePartName"></div>


				<div class="col_line_2">
					<!-- A1_2 -->
					<button style="margin: 8px 10px 0px 0px;" class="button-group"
						data-bind="icon: 85 ,click:jumpToCmm045"></button>
					<!-- A1_3 -->
					<div
						style="display: inline-block; margin-right: 15px; vertical-align: middle; line-height: 45px;" data-bind="text: $i18n('KTG005_1')"></div>
					<!-- A1_4 -->

					<button style="margin-top: 8px" class="button-group"
						data-bind="icon: 5, click:openScreenB ,visible:executionAppResult().employeeCharge == true"></button>
				</div>
			</div>
			<div id="content" data-bind="with:executionAppResult"
				style="padding: 0px 10px">
				<!-- ko foreach: appSettings -->
					<div class="div_line border">
						<div class="col_line_1">
							<!-- A2_1 -->
							<div class="label" data-bind="text: $component.getLabel(item)"></div>
						</div>
						<div class="col_line_2">
							<!-- A2_2 -->
							<div class="label" data-bind="text: $component.getText(item)"></div>
						</div>
					</div>
				<!-- /ko -->
			</div>
		</div>
		`
    })
    export class KTG005AComponent extends ko.ViewModel {

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
        
        mounted() {
            const vm = this;
            vm.$el.classList.add('chung-dep-trai')
        }

		loadData() {

			let vm = this,
				cache = nts.uk.ui.windows.getShared('cache'),
                topPagePartCode = $(location).attr('search').split('=')[1];

			vm.$ajax('at', requestUrl.getOptionalWidgetDisplay, topPagePartCode).done((widDisplay: IOptionalWidgetDisplay) => {
				let
					query = cache ? {
						companyId: vm.$user.companyId,
						startDate: cache.currentOrNextMonth == "1" ? widDisplay.datePeriodDto.strCurrentMonth : widDisplay.datePeriodDto.strNextMonth,
						endDate: cache.currentOrNextMonth == "1" ? widDisplay.datePeriodDto.endCurrentMonth : widDisplay.datePeriodDto.endNextMonth,
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
				vm.$ajax('at',requestUrl.startScreenA, query).done((setting: IExecutionAppResult) => {
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

			vm.$jump.self("/view/cmm/045/a/index.xhtml?a=0");
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