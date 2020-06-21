/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template ='<div></div>'

interface Params {
	
}

@component({
	name: 'card-list',
	template: template
})
class CardListComponent extends ko.ViewModel {
	public params!: Params;
	
	cardNos!: KnockoutObservableArray<CardNo>;
	selectedCardNo!: KnockoutObservable<number>;

	created(params: Model) {
		this.cardNos = params.cardNos;
		this.selectedCardNo = params.selectedCardNo;
	}

	mounted() {
		const vm = this;
		const row = 4;

		const $grid = $(vm.$el)
			.igGrid({
				columns: [
					{ headerText: vm.$i18n('KMP001_31'), key: "checked", dataType: "boolean", width: 50, template: `<input type="checkbox" value="" />` },
					{ headerText: vm.$i18n('KMP001_32'), key: "no", dataType: "string", width: 200 }
				],
				height: `${24 + (23 * row)}px`,
				dataSource: [],
				cellClick: function(evt, ui) {
					vm.selectedCardNo(ui.rowIndex);
				}
			});

		ko.computed(() => {
			const cardNos = ko.unwrap(vm.cardNos);

			$grid.igGrid('option', 'dataSource', ko.toJS(cardNos));
		});
	}
}

interface ICardNo {
	checked: boolean;
	no: string;
}

class CardNo {
	checked: KnockoutObservable<boolean> = ko.observable(false);
	no: KnockoutObservable<string> = ko.observable('');

	constructor(params?: ICardNo) {
		const model = this;

		model.update(params);
	}

	public update(params?: ICardNo) {
		const model = this;

		if (params) {
			model.checked(!!params.checked);
			model.no(`${params.no}`);
		}
	}
}