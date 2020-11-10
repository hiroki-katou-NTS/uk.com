/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.b.viewmodel {
	import character = nts.uk.characteristics;
	import InitDisplayOfApprovalStatus = nts.uk.at.view.kaf018.a.viewmodel.InitDisplayOfApprovalStatus;
	import DisplayWorkplace = nts.uk.at.view.kaf018.a.viewmodel.DisplayWorkplace;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	import KAF018DParam = nts.uk.at.view.kaf018.d.viewmodel.KAF018DParam;
	import ApprovalStatusMailType = kaf018.share.model.ApprovalStatusMailType;
	import KAF018CParam = nts.uk.at.view.kaf018.c.viewmodel.KAF018CParam;
	
	@bean()
	class Kaf018BViewModel extends ko.ViewModel {
		appNameLst: Array<any> = [];
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		dataSource: Array<ApprSttExecutionDto> = [];
		selectWorkplaceInfo: Array<DisplayWorkplace> = [];
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
		pageData: Array<string> = [];
		
		created(params: KAF018BParam) {
			const vm = this;
			vm.$blockui('show');
			vm.appNameLst = params.appNameLst;
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.selectWorkplaceInfo = params.selectWorkplaceInfo;
			vm.dataSource = _.map(vm.selectWorkplaceInfo, x => {
				return {
					wkpID: x.id,
					wkpCD: x.code,
					wkpName: x.name,
					hierarchyCode: x.hierarchyCode,
					empPeriodLst: [],
					level: x.level,
					countEmp: null,
					countUnApprApp: null
				} 
			});
			vm.createIggrid();
			
		}
		
		loadData(initFlg: boolean) {
			const vm = this;
			vm.$blockui('show');
			$.Deferred((dfd) => {
				if(!initFlg) {
					return dfd.resolve();
				}
				return character.restore('InitDisplayOfApprovalStatus').then((obj: InitDisplayOfApprovalStatus) => {
					if(obj) {
						vm.initDisplayOfApprovalStatus = obj;	
					}
					dfd.resolve();
				});
			}).promise()
	      	.then(() => {
				let closureId = vm.closureItem.closureId,
					processingYm = vm.closureItem.processingYm,
					startDate = vm.startDate,
					endDate = vm.endDate,
					wkpInfoLst = _.filter(vm.selectWorkplaceInfo, o => _.includes(vm.pageData, o.id)),
					initDisplayOfApprovalStatus = vm.initDisplayOfApprovalStatus,
					wsParam = { closureId, processingYm, startDate, endDate, wkpInfoLst, initDisplayOfApprovalStatus };
				return vm.$ajax('at', API.getStatusExecution, wsParam);
			}).then((data: Array<ApprSttExecutionDto>) => {
				_.forEach(vm.dataSource, x => {
					let exist = _.find(data, y => y.wkpID == x.wkpID);
					if(exist) {
						x.wkpCD = exist.wkpCD;
						x.wkpName = exist.wkpName;
						x.empPeriodLst = exist.empPeriodLst;
						x.countEmp = exist.countEmp;
						x.countUnApprApp = exist.countUnApprApp;
					}
				});
				$("#bGrid").igGrid("option", "dataSource", vm.dataSource);
			}).always(() => {
				vm.$blockui('hide');
				$("#fixed-table").focus();
			});
		}
		
		createIggrid() {
			const vm = this;
			$("#bGrid").igGrid({
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
				rendered: () => {
					vm.$blockui('hide');
					vm.getPageData();
					vm.loadData(true);
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
						key: 'wkpID',
						dataType: 'string',
						headerCssClass: 'kaf018-b-header-wkpName',
						columnCssClass: 'kaf018-b-column-wkpName',
						formatter: (key: string) => vm.getWkpInfo(key),
					},
					{ 
						headerText: vm.$i18n('KAF018_332'), 
						key: 'countEmp', 
						dataType: 'number', 
						width: '50px', 
						headerCssClass: 'kaf018-b-header-countEmp',
						columnCssClass: 'kaf018-b-column-countEmp',
						formatter: (key: string) => {
							if(!key) {
								return "";
							}
							return key;
						},
					},
					{ 
						headerText: vm.$i18n('KAF018_333'),
						headerCssClass: 'kaf018-b-header-countUnApprApp',
						group: [
							{ 
								headerText: vm.createButtonHtml(0), 
								key: 'countUnApprApp', 
								dataType: 'number', 
								width: '75px', 
								columnCssClass: 'kaf018-b-column-countUnApprApp',
								formatter: (key: string) => {
									if(!key) {
										return "";
									}
									return key;
								},
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
						},
						pageIndexChanged: () => {
							vm.getPageData();
							vm.loadData(false);
						},
						pageSizeChanged: () => {
							vm.getPageData();
							vm.loadData(false);
						}
					},
				],
			});
		}
		
		getPageData() {
			const vm = this;
			vm.pageData = _.map($('#bGrid').igGrid("allRows"), (o: any) => o.getAttribute("data-id"));
		}
		
		createButtonHtml(mailType: ApprovalStatusMailType) {
			return `<button class="kaf018-b-mailButton" data-bind="click: function() { $vm.buttonMailAction(` + mailType + `); }, text: $i18n('KAF018_346')"></button>`;
		}
		
		getWkpInfo(key: string) {
			const vm = this;
			let currentWkpInfo = _.find(vm.dataSource, o => o.wkpID==key);
			let displayWkpInfo = '<div style="width: 150px; display: inline-block;">' + currentWkpInfo.wkpCD + '</div>';
			for(let i = 1; i < currentWkpInfo.level; i++) {
				displayWkpInfo += '<div style="width: 10px; display: inline-block;"></div>';
			}
			displayWkpInfo += '<span>' + currentWkpInfo.wkpName + '</span>';
			return displayWkpInfo;
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="countUnApprApp") {
				let countUnApprApp = _.find(vm.dataSource, o => o.wkpID == ui.rowKey).countUnApprApp;
				if(!countUnApprApp) {
					return;	
				}
				let closureItem = vm.closureItem,
					startDate = vm.startDate,
					endDate = vm.endDate,
					apprSttExeDtoLst = _.filter(vm.dataSource, o => {
						let countUnApprApp = o.countUnApprApp ? true : false;
						return countUnApprApp && _.includes(vm.pageData, o.wkpID);
					}),
					currentWkpID = ui.rowKey,
					appNameLst: Array<any> = vm.appNameLst,
					dParam: KAF018DParam = { closureItem, startDate, endDate, apprSttExeDtoLst, currentWkpID, appNameLst };
				vm.$window.modal('/view/kaf/018/d/index.xhtml', dParam);
			}
		}
		
		buttonMailAction(mailTypeParam: ApprovalStatusMailType) {
			const vm = this;
			let height = screen.availHeight;
			if(screen.availHeight > 820) {
				height = 820
			}
			if(screen.availHeight < 600) {
				height = 600;
			}
			let dialogSize = { width: 900, height: height },
				mailType = mailTypeParam,
				selectWorkplaceInfo = vm.selectWorkplaceInfo,
				cParam: KAF018CParam = { mailType, selectWorkplaceInfo };
			vm.$window.modal('/view/kaf/018/c/index.xhtml', cParam, dialogSize);
		}
		
		goBackA() {
			const vm = this;
			vm.$jump('/view/kaf/018/a/index.xhtml');
		}
	}
	
	export interface KAF018BParam {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		selectWorkplaceInfo: Array<DisplayWorkplace>;
		appNameLst: Array<any>;
	}

	export interface ApprSttExecutionDto {
		wkpID: string;
		wkpCD: string;
		wkpName: string;
		hierarchyCode: string;
		empPeriodLst: Array<any>;
		level: number;
		countEmp: number;
		countUnApprApp: number;
	}	

	const API = {
		getStatusExecution: "at/request/application/approvalstatus/getStatusExecution",
		deleteTmpTable: "at/request/application/approvalstatus/deleteTmpTable"
	}
}