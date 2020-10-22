 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.b.viewmodel {
	
	@bean()
	class Kaf018BViewModel extends ko.ViewModel {
		closureId: number = 0;
		closureName: string = '対象締め';
		maxItemDisplay: KnockoutObservable<number> = ko.observable(100);
		statesTable: any = [];
		dataSource: any = [
			new model.TableItem('wkp1', 1, 1),
			new model.TableItem('wkp2', 2, 2),
			new model.TableItem('wkp3', 3, 3)
		];
		
		created(params: any) {
			const vm = this;
			window.onresize = function(event: any) {
				$("#gridB_scrollContainer").height(window.innerHeight - 269);
				$("#gridB_displayContainer").height(window.innerHeight - 269);
				$("#gridB_container").height(window.innerHeight - 240);
			};
			vm.closureName = 'closureName';
			vm.createMGrid();
//			let wkpInfoLst = params.selectWorkplaceInfo,
//				wsParam = { wkpInfoLst };
//			vm.$ajax('at', API.getStatusExecution, wkpInfoLst).done(() => {
//				vm.dataSource = [];
//			});
//			window.onbeforeunload = function() {
//				console.log('unload');
//			};
//			self.startPage().done(() => {
//				$("#fixed-table").focus();
//			});
		}
		
		createMGrid() {
			const vm = this;
			let buttonHtml = `<button class="kaf018-b-mailButton" data-bind="click: buttonMailAction, text: $i18n('KAF018_346')"></button>`;
			$("#dpGrid").ntsGrid({
				height: 300,
				dataSource: vm.dataSource,
				primaryKey: 'wkpName',
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
						headerText: vm.$i18n('KAF018_331'), 
						key: 'wkpName', 
						dataType: 'string',
						width: '500px',
						headerCssClass: 'kaf018-b-header-wkpName',
						columnCssClass: 'kaf018-b-column-wkpName'
					},
					{ 
						headerText: vm.$i18n('KAF018_332'), 
						key: 'numberPeople', 
						dataType: 'string', 
						width: '50px', 
						headerCssClass: 'kaf018-b-header-numberPeople',
						columnCssClass: 'kaf018-b-column-numberPeople'
					},
					{ 
						headerText: vm.$i18n('KAF018_333'),
						headerCssClass: 'kaf018-b-header-appInfo',
						group: [
							{ 
								headerText: buttonHtml, 
								key: 'appInfo', 
								width: '75px', 
								columnCssClass: 'kaf018-b-column-appInfo'
							}
						]
					}	
				],
				features: [
					{
						name: 'MultiColumnHeaders'
					}
				],
			});
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="appInfo") {
				vm.$window.modal('/view/kaf/018/d/index.xhtml');
			}
		}
		
		buttonMailAction() {
			const vm = this;
			vm.$window.modal('/view/kaf/018/c/index.xhtml');
		}
		
		goBackA() {
			const vm = this;
			nts.uk.request.jump('/view/kaf/018/a/index.xhtml');
		}
		
		getTargetDate(): string {
			const vm = this;
//			let startDate = nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd');
//			let endDate = nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd');
//			return self.processingYm + " (" + startDate + " ～ " + endDate + ")";
			return "Date Range";
		}
	}

	export module model {
		export class TableItem {
			wkpName: string;	
			numberPeople: number;
			appInfo: number;
			constructor(wkpName: string, numberPeople: number, appInfo: number) {
				this.wkpName = wkpName;
				this.numberPeople = numberPeople;
				this.appInfo = appInfo;
			}
		}	
	}

	const API = {
		getStatusExecution: "at/request/application/approvalstatus/getStatusExecution"
	}
}