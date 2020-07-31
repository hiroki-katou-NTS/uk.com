/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const templateCardList = `
	<!-- ko if: ko.unwrap(model.stampCardDto).length === 0 -->
			<div
				data-bind="ntsFormLabel: {
					constraint: $component.constraint, 
					required: true,
					text: $i18n('KMP001_22') }">
			</div>
			<input class="ip-stamp-card"
				data-bind="ntsTextEditor: {
					value: ko.observable(''),
					constraint: $component.constraint,
					enabled: true,
					width: 200
			}"/>		
		<!-- /ko -->
		<!-- ko if: ko.unwrap(model.stampCardDto).length !== 0 -->
		<div data-bind="foreach: model.stampCardDto">
			<!-- ko if: $index() === 0 -->
				<div
					data-bind="ntsFormLabel: {
						constraint: $component.constraint, 
						required: true, 
						text: $i18n('KMP001_22') }">
				</div>
				<input class="ip-stamp-card"
					data-bind="ntsTextEditor: {
						value: stampNumber,
						constraint: $component.constraint,
						enabled: true,
						width: 200
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
		maxLength: KnockoutObservable<string>;

		public constraint: KnockoutObservable<string> = ko.observable('StampNumber');

		created(params: any) {
			const vm = this;

			vm.model = params.model;
			vm.maxLength = params.maxLength;

			vm.reloadSetting();

			vm.maxLength
				.subscribe(() => {
					vm.reloadSetting();
				})

			vm.$errors('.nts-editor', { messageId: 'Msg_09' });
		}

		mounted() {
			const vm = this;
			const row = 5;

			const $grid = $(vm.$el)
				.find('#stampcard-list')
				.igGrid({
					columns: [
						{ headerText: vm.$i18n('KMP001_31'), key: "stampCardId", dataType: "string", width: 1, hidden: true },
						{ headerText: vm.$i18n('KMP001_22'), key: "stampNumber", dataType: "string", width: 200, hidden: false }
					],
					height: `${26 + (23 * row)}px`,
					dataSource: [],
					features: [{
						name: "Selection",
						mode: "row",
						multipleSelection: true,
						activation: true,
						rowSelectionChanging: function(evt, ui) {
							const el = document.querySelector('.sidebar-content-header');

							if (el) {
								const $vm = ko.dataFor(el);

								if ($vm) {
									if (ko.unwrap($vm.mode) === 'new') {
										return false;
									}
								}
							}
						},
						rowSelectionChanged: function(evt, ui) {
							const selectedRows = ui.selectedRows.map(m => m.index) as number[];
							const stampCard = ko.unwrap(vm.model.stampCardDto);

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

							vm.model.stampCardDto(stampCard);
						}
					}, {
						name: "RowSelectors",
						enableCheckBoxes: true,
						enableRowNumbering: false,
						enableSelectAllForPaging: false // this option is true by default
					}],
					rendered: function() {
						$(vm.$el).find('.ui-iggrid-rowselector-header').html('').append($('<span>', { class: 'ui-iggrid-headertext', text: vm.$i18n('KMP001_31') }));
					},
					dataRendered: function() {
						$(vm.$el).find('.ui-icon.ui-icon-triangle-1-e').remove();
					}
				});

			ko.computed(() => {
				const stampCard = ko.unwrap(vm.model.stampCardDto);

				$grid.igGrid('option', 'dataSource', ko.toJS(stampCard));

				if ($grid.data('igGrid') && $grid.data('igGridSelection') && $grid.igGrid('option', 'dataSource').length) {
					$grid.igGridSelection("selectRow", 0);
				}
			});

			const el = document.querySelector('.sidebar-content-header');

			if (el) {
				const $vm = ko.dataFor(el);

				if ($vm) {
					ko.computed(() => {
						const mode = ko.unwrap($vm.mode);

						if (mode === 'new') {
							$grid.igGridSelection('clearSelection');
						}
					});
				}
			}

			vm.$errors('clear');

			vm.$nextTick(() => {
				vm.$errors('clear');
			})
		}

		reloadSetting() {
			const vm = this;

			vm.$ajax(KMP001A_CARD_LIST.GET_STAMPCARDDIGIT)
				.then((data: any) => {
					const ck = ko.toJS(vm.constraint);

					vm.$validate.constraint(ck)
						.then((constraint) => {
							if (constraint) {
								_.extend(constraint, {
									maxLength: data.stampCardDigitNumber
								});

								vm.$validate.constraint(ck, constraint);
								vm.constraint.valueHasMutated();
							}
						});
				});

		}
	}
}