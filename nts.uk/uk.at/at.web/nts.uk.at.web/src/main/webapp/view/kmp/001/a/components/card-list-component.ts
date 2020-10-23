/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const templateCardList = `
	<div>
		<div
			data-bind="ntsFormLabel: {
				constraint: $component.constraint, 
				required: true,
				text: $i18n('KMP001_22')}">
		</div>
		<input id="card-input" class="ip-stamp-card"
			data-bind="ntsTextEditor: {
				name:'#[KMP001_30]',
				value: textInput,
				constraint: $component.constraint,
				enabled: true,
				width: 200
		}"/>
		<button class="read-card" data-bind="text: $i18n('KMP001_150'), click: showDaiLogI"></button>
	</div>
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
		stampCardEdit!: StampCardEdit;
		methodEdit: KnockoutObservable<boolean>;

		public textInput: KnockoutObservable<string> = ko.observable('');
		public constraint: KnockoutObservable<string> = ko.observable('StampNumber');
		public textInputTemporary: KnockoutObservable<string> = ko.observable('-1');

		created(params: any) {
			const vm = this;

			vm.model = params.model;
			vm.stampCardEdit = params.stampCardEdit;
			vm.textInput = params.textInput;
			vm.methodEdit = params.methodEdit;

			vm.reloadSetting();

			vm.stampCardEdit.stampCardDigitNumber
				.subscribe(() => {
					vm.reloadSetting();
				});

			vm.textInput
				.subscribe(() => {
					if (ko.unwrap(vm.methodEdit)) {
						vm.methodEdit(false);
					}
					if (ko.unwrap(vm.textInput) === ko.unwrap(vm.textInputTemporary)) {
						vm.methodEdit(true);
					}
				});

			window.onclick = (() => {
				console.log(ko.unwrap(vm.methodEdit));
				console.log(ko.unwrap(vm.textInputTemporary));
				if (ko.unwrap(vm.methodEdit)){
					vm.$errors('clear');
				}
			});
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
					height: `${30 + (23.5 * row)}px`,
					dataSource: [],
					features: [{
						name: "Selection",
						mode: "row",
						multipleSelection: true,
						activation: false,
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
						setTimeout(() => {
							vm.$nextTick(() => {
								$('.ip-stamp-card').focus();
							})
						}, 50);
					}
				});

			ko.computed(() => {
				const stampCard = ko.unwrap(vm.model.stampCardDto);

				$grid.igGrid('option', 'dataSource', ko.toJS(stampCard));
			});

			ko.computed(() => {
				const index = ko.unwrap(vm.model.selectedStampCardIndex);

				vm.$nextTick(() => {
					if ($grid.data('igGrid') && $grid.data('igGridSelection') && $grid.igGrid('option', 'dataSource').length) {

						$('.ip-stamp-card').focus();
					}
				});
			});

			const el = document.querySelector('.sidebar-content-header');

			if (el) {
				const $vm = ko.dataFor(el);

				if ($vm) {
					ko.computed(() => {
						const mode = ko.unwrap($vm.mode);
					});
				}
			}
		}

		reloadSetting() {
			const vm = this;

			vm.$ajax(KMP001A_CARD_LIST.GET_STAMPCARDDIGIT)
				.then((data: IStampCardEdit) => {
					const ck = ko.toJS(vm.constraint);
					vm.stampCardEdit.update(data);

					vm.$validate.constraint(ck)
						.then((constraint) => {
							if (constraint) {
								_.extend(constraint, {
									maxLength: data.stampCardDigitNumber
								});

								vm.$validate.constraint(ck, constraint);
								vm.constraint.valueHasMutated();

								setTimeout(() => {
									vm.$nextTick(() => {
										$('.ip-stamp-card').focus();
									})
								}, 50);
							}
						});
				});
		}

		showDaiLogI() {
			const vm = this;

			vm.$window
				.modal('/view/kmp/001/i/index.xhtml')
				.then((data: string) => {
					vm.textInput(data);
					vm.methodEdit(true);
					vm.textInputTemporary(data);
				})
				.then(() => {
					vm.$errors('clear');
				});
		}
	}
}
