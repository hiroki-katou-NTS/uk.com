 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.b.viewmodel {
	import InitDisplayOfApprovalStatus = nts.uk.at.view.kaf018.a.viewmodel.InitDisplayOfApprovalStatus;
	
	@bean()
	class Kaf018BViewModel extends ko.ViewModel {
		closureId: number;
		closureName: string;
		dateRangeStr: string;
		dataSource: Array<ApprSttExecutionOutput> = [];
		initDisplayOfApprovalStatus: InitDisplayOfApprovalStatus = {
			// ページング行数
			numberOfPage: 0,
			// ユーザーID
			userID: '',
			// 会社ID
			companyID: __viewContext.user.companyId,
			// 月別実績の本人確認・上長承認の状況を表示する
			confirmAndApprovalMonthFlg: false,
			// 就業確定の状況を表示する
			confirmEmploymentFlg: false,
			// 申請の承認状況を表示する
			applicationApprovalFlg: false,
			// 日別実績の本人確認・上長承認の状況を表示する
			confirmAndApprovalDailyFlg: false
		};
		
		created(params: any) {
			const vm = this;
			vm.$blockui('show');
			vm.closureId = params.closureId;
			vm.closureName = params.closureName;
			vm.dateRangeStr = params.startDate + ' ' + vm.$i18n('KAF018_324') + ' ' + params.endDate;
			vm.initDisplayOfApprovalStatus = params.initDisplayOfApprovalStatus;
			vm.createMGrid();
			let closureId = params.closureId,
				startDate = params.startDate,
				endDate = params.endDate,
				wkpInfoLst = params.selectWorkplaceInfo,
				initDisplayOfApprovalStatus = params.initDisplayOfApprovalStatus,
				wsParam = { closureId, startDate, endDate, wkpInfoLst, initDisplayOfApprovalStatus };
			vm.$ajax('at', API.getStatusExecution, wsParam).done((data) => {
				vm.dataSource = data;
				$("#dpGrid").igGrid("option", "dataSource", vm.dataSource);
			}).always(() => {
				vm.$blockui('hide');
				$("#fixed-table").focus();
			});
//			window.onbeforeunload = function() {
//				console.log('unload');
//			};
		}
		
		createMGrid() {
			const vm = this;
			let buttonHtml = `<button class="kaf018-b-mailButton" data-bind="click: buttonMailAction, text: $i18n('KAF018_346')"></button>`;
			$("#dpGrid").igGrid({
				width: screen.availWidth - 24 < 1000 ? 1000 : screen.availWidth - 24,
				height: screen.availHeight - 260,
				dataSource: vm.dataSource,
				primaryKey: 'wkpID',
				primaryKeyDataType: 'string',
				rowVirtualization: true,
				virtualization: true,
				virtualizationMode: 'continuous',
				enter: 'right',
				autoFitWindow: false,
				hidePrimaryKey: true,
				avgRowHeight: 25,
				cellClick: (evt: any, ui: any) => {
					vm.cellGridClick(evt, ui); 
				},
				columns: [
					{ 
						headerText: '', 
						key: 'wkpID', 
						dataType: 'string',
						hidden: true
					},
					{ 
						headerText: vm.$i18n('KAF018_331'), 
						key: 'wkpName', 
						dataType: 'string',
						headerCssClass: 'kaf018-b-header-wkpName',
						columnCssClass: 'kaf018-b-column-wkpName'
					},
					{ 
						headerText: vm.$i18n('KAF018_332'), 
						key: 'countEmp', 
						dataType: 'string', 
						width: '50px', 
						headerCssClass: 'kaf018-b-header-countEmp',
						columnCssClass: 'kaf018-b-column-countEmp'
					},
					{ 
						headerText: vm.$i18n('KAF018_333'),
						headerCssClass: 'kaf018-b-header-countUnApprApp',
						group: [
							{ 
								headerText: buttonHtml, 
								key: 'countUnApprApp', 
								width: '75px', 
								columnCssClass: 'kaf018-b-column-countUnApprApp'
							}
						]
					}	
				],
				features: [
					{
						name: 'MultiColumnHeaders'
					},
					{
						name: "Paging",
						defaultDropDownWidth: 80,
						pageCountLimit : 1,
						pageSize: 100,
						pageSizeList: [100, 200, 500, 1000, 3000],
						locale: {
							pagerRecordsLabelTooltip: "Current records",
							pageSizeDropDownLabel: vm.$i18n('KAF018_325'),
							pageSizeDropDownTrailingLabel: ""
						}
					},
				],
			});
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="countUnApprApp") {
				vm.$window.modal('/view/kaf/018/d/index.xhtml');
			}
		}
		
		buttonMailAction() {
			const vm = this;
			let height = screen.availHeight;
			if(screen.availHeight > 820) {
				height = 820
			}
			if(screen.availHeight < 600) {
				height = 600;
			}
			let dialogSize = {
				width: 900,
				height: height
			}
			vm.$window.modal('/view/kaf/018/c/index.xhtml', {}, dialogSize);
		}
		
		goBackA() {
			const vm = this;
			vm.$jump('/view/kaf/018/a/index.xhtml');
		}
	}

	export class ApprSttExecutionOutput {
		wkpID: string;
		wkpCD: string;
		wkpName: string;
		countEmp: number;
		countUnApprApp: number;
		constructor(wkpID: string, wkpCD: string, wkpName: string, countEmp: number, countUnApprApp: number) {
			this.wkpID = wkpID;
			this.wkpCD = wkpCD;
			this.wkpName = wkpName;
			this.countEmp= countEmp;
			this.countUnApprApp = countUnApprApp;
		}
	}	

	const API = {
		getStatusExecution: "at/request/application/approvalstatus/getStatusExecution"
	}
}