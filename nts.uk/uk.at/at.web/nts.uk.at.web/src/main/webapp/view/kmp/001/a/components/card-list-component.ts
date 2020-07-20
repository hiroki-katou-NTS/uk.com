/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const templateCardList = `
	<!-- ko if: ko.unwrap(model.stampCard).length === 0 -->
			<div
				data-bind="ntsFormLabel: {
					constraint: '10', 
					required: true, 
					text: $i18n('KMP001_22') }">
			</div>
			<input 
				data-bind="ntsTextEditor: {
					value: ko.observable(''),
					constraint: '10',
					enabled: true
				}"/>		
	<!-- /ko -->
	<!-- ko if: ko.unwrap(model.stampCard).length !== 0 -->
	<div data-bind="foreach: model.stampCard">
		<!-- ko if: $index() === $component.model.selectedStampCardIndex() -->
			<div
				data-bind="ntsFormLabel: {
					constraint: '', 
					required: true, 
					text: $i18n('KMP001_22') }">
			</div>
			<input 
				data-bind="ntsTextEditor: {
					value: stampNumber,
					constraint: ''
				}"/>
		<!-- /ko -->
	</div>
	<!-- /ko -->
	<table id="stampcard-list"></table>
	`;
	
	const KMP001A_CARD_LIST = {
		GET_STAMPCARDDIGIT: 'screen/pointCardNumber/getStampCardDigit'
	};
	
	@component({
		name: 'card-list-component',
		template: templateCardList
	})
	export class CardListComponent extends ko.ViewModel {
		model!: share.Model;
		
		public constraint: KnockoutObservable<string> = ko.observable('StampNumber');

		created(params: any) {
			const vm = this;

			vm.model = params.model;
			
			vm.$ajax(KMP001A_CARD_LIST.GET_STAMPCARDDIGIT)
				.then((data: any) => {
					const ck = ko.toJS(vm.constraint);
					const constraint = _.get(__viewContext.primitiveValueConstraints, ck);

					if (constraint) {
						_.extend(constraint, {
							maxLength: data.stampCardDigitNumber
						});

						(nts.uk.ui.validation as any).writeConstraint(ck, constraint);
						vm.constraint.valueHasMutated();
					}
				});
		}

		mounted() {
			const vm = this;
			const row = 4;

			const $grid = $(vm.$el)
				.find('#stampcard-list')
				.igGrid({
					columns: [
						{ headerText: vm.$i18n('KMP001_31'), key: "stampCardId", dataType: "string", width: 1, hidden: true },
						{ headerText: vm.$i18n('KMP001_22'), key: "stampNumber", dataType: "string", width: 200, hidden: false }
					],
					height: `${24 + (23 * row)}px`,
					dataSource: [],
					features: [{
						name: "Selection",
						mode: "row",
						multipleSelection: true,
						activation: true,
						rowSelectionChanged: function(evt, ui) {
							const selectedRows = ui.selectedRows.map(m => m.index) as number[];
							const stampCard = ko.unwrap(vm.model.stampCard);

							vm.model.selectedStampCardIndex(ui.row.index);

							_.each(stampCard, (stamp: any, index: number) => {
								if (selectedRows.indexOf(index) > -1) {
									// check
									_.extend(stamp, { checked: true });
								} else {
									// uncheck
									_.extend(stamp, { checked: false });
								}
							})

							vm.model.stampCard(stampCard);
						}
					}, {
						name: "RowSelectors",
						enableCheckBoxes: true,
						enableRowNumbering: false,
						enableSelectAllForPaging: false // this option is true by default
					}],
					cellClick: function(evt, ui) {
						// vm.selectedCardNo(ui.rowIndex);
					},
					rendered: function() {
						$(vm.$el).find('.ui-iggrid-rowselector-header').html('').append($('<span>', { class: 'ui-iggrid-headertext', text: vm.$i18n('KMP001_31') }));
					}
				});
				
				/*if ($grid.data('igGrid') && $grid.data('igGridSelection') && $grid.igGrid('option', 'dataSource').length) {
					$grid.igGridSelection("selectRow", 0);
				}*/

			ko.computed(() => {
				const stampCard = ko.unwrap(vm.model.stampCard);

				$grid.igGrid('option', 'dataSource', ko.toJS(stampCard));
			});
		}
	}
}