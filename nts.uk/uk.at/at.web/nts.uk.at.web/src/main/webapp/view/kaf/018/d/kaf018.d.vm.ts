 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.d.viewmodel {

	@bean()
	class Kaf018DViewModel extends ko.ViewModel {
		closureName: string = '対象締め';
		processingYm: string = '対象締め';
		dataSource: any = [];
		headers: any = [];
		columns: any = [];
		
		created(params: any) {
			const vm = this;
			for(let i = 1; i <= 20; i++) {
				vm.dataSource.push({ wkpName: 'wkpName' + i, appInfo: 'x' });
			}
			vm.columns.push(
				{ 
					headerText: vm.$i18n('KAF018_373'), 
					key: 'wkpName',
					headerCssClass: 'kaf018-d-header-wkpName',
					columnCssClass: 'kaf018-d-column-wkpName'
				}
			);
			for(let i = 1; i <= 31; i++) {
				vm.headers.push(moment.utc().day(i));
			}
			_.forEach(vm.headers, (dateloop) => {
				vm.columns.push(
					{ 
						headerText: moment(dateloop).date(),
						headerCssClass: 'kaf018-d-header-date',
						group: [
							{ 
								headerText: moment(dateloop).format('ddd'), 
								key: 'appInfo',
								width: '28px',
								headerCssClass: 'kaf018-d-header-date',
								columnCssClass: 'kaf018-d-column-date'
							}
						]
					}
				);
			});
			vm.createMGrid();
		}
		
		createMGrid() {
			const vm = this;
			$("#dpGrid").ntsGrid({
				height: 508,
				width: screen.availWidth - 70,
				dataSource: vm.dataSource,
				virtualization: true,
				virtualizationMode: 'continuous',
				cellClick: (evt: any, ui: any) => {
					vm.cellGridClick(evt, ui); 
				},
				columns: vm.columns,
				features: [
					{
						name: 'MultiColumnHeaders'
					}
				],
			});
		}
		
		formatSubHeader(value: any) {
			const vm = this;
			return value+1;
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="wkpName") {
				vm.$window.modal('/view/kaf/018/e/index.xhtml');
			}
		}

		getTargetDate(): string {
			const vm = this;
			return "";
		}
		
		close() {
			const vm = this;
			vm.$window.close({});
		}
	}

	const API = {
	
	}
}