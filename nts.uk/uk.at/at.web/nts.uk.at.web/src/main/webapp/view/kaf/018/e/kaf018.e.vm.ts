 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.e.viewmodel {

	@bean()
	class Kaf018EViewModel extends ko.ViewModel {
		items: Array<model.ItemModel> = [];
		
		created() {
			const vm = this;
			let a = [];
			for(let i = 1; i <= 20; i++) {
				a.push(new model.ItemModel(
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
					i.toString(),
				));
			}
			vm.items = a;
			$("#dpGrid").igGrid({
				height: 501,
				width: screen.availWidth - 70,
				dataSource: vm.items,
				virtualization: true,
				virtualizationMode: 'continuous',
				columns: [
					{ headerText: vm.$i18n('KAF018_385'), key: 'col1', width: 150 },
					{ headerText: vm.$i18n('KAF018_383'), key: 'col2', width: 150 },
					{ headerText: vm.$i18n('KAF018_384'), key: 'col3', width: 100 },
					{ headerText: vm.$i18n('KAF018_386'), key: 'col4', width: 600 },
					{ headerText: vm.$i18n('KAF018_387'), key: 'col5', width: 150 },
					{ headerText: vm.$i18n('KAF018_388'), key: 'col6', width: 150 },
					{ headerText: vm.$i18n('KAF018_389'), key: 'col7', width: 250 },
					{ headerText: vm.$i18n('KAF018_390'), key: 'col8', width: 250 },
					{ headerText: vm.$i18n('KAF018_391'), key: 'col9', width: 250 },
					{ headerText: vm.$i18n('KAF018_392'), key: 'col10', width: 250 },
					{ headerText: vm.$i18n('KAF018_393'), key: 'col11', width: 250 }
				],
				features: [
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: [
							{ columnKey: 'col1', isFixed: true },
							{ columnKey: 'col2', isFixed: true },
							{ columnKey: 'col3', isFixed: true }
						]
					}
				],
			});
		}

		close() {
			const vm = this;
			vm.$window.close({});
		}
	}
	
	export module model {
		export class ItemModel {
			col1: string;
			col2: string;
			col3: string;
			col4: string;
			col5: string;
			col6: string;
			col7: string;
			col8: string;
			col9: string;
			col10: string;
			col11: string;
			constructor(col1: string, col2: string, col3: string, col4: string, col5: string,
				col6: string, col7: string, col8: string, col9: string, col10: string, col11: string) {
				this.col1 = col1;
				this.col2 = col2;
				this.col3 = col3;
				this.col4 = col4;
				this.col5 = col5;
				this.col6 = col6;
				this.col7 = col7;
				this.col8 = col8;
				this.col9 = col9;
				this.col10 = col10;
				this.col11 = col11;
			}
		}
	}

	const API = {
	
	}
}