/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.a {
	const stampEmployeeSelectionTemplate = `
		<div data-bind="ntsDatePicker: { value: baseDate }"></div>
		<div class="button-group-filter" data-bind="foreach: buttons">
			<button class="large filter" data-bind="
					i18n: text, 
					css: { 'extend': width === 2, selected: ko.toJS($component.button) === text },
					click: function() { $component.button(text); }
				"></button>
		</div>
		<div class="list-group">
			<div class="list-title" data-bind="i18n: '一覧にない社員で打刻する'"></div>
			<div class="list-employee">
				<table id="grid-employee"></table>
			</div>
		</div>
	`;

	@component({
		name: 'stamp-employee-selection',
		template: stampEmployeeSelectionTemplate
	})
	export class StampEmployeeSelectionComponent extends ko.ViewModel {
		baseDate: KnockoutObservable<Date> = ko.observable(new Date());
		button: KnockoutObservable<string> = ko.observable('KDP003_111');

		buttons: KnockoutObservableArray<Button> = ko.observableArray([]);
		employees: KnockoutObservableArray<Employee> = ko.observableArray([]);

		created() {
			const vm = this;
			const buttons: Button[] = [];
			const employees: Employee[] = [];

			// mock data
			['ア', 'カ', 'サ', 'タ', 'ナ', 'ハ', 'マ', 'ヤ', 'ラ', 'ワ']
				.forEach((t, i) => {
					_.extend(window.names, {
						[`KDP003_10${i}`]: t
					});
				});

			_.extend(window.names, {
				'KDP003_111': '全員'
			});

			[
				'ア大塚', 'イ大塚', 'ウ大塚', 'エ大塚', 'オ大塚', 'ァ大塚', 'ァ大塚', 'ィ大塚', 'ゥ大塚', 'ェ大塚', 'ォ大塚', 'ヴ大塚',
				'カ大塚', 'キ大塚', 'ク大塚', 'ケ大塚', 'コ大塚', 'ガ大塚', 'ギ大塚', 'グ大塚', 'ゲ大塚', 'ゴ大塚', 'ヵ大塚', 'ヶ大塚',
				'サ大塚', 'シ大塚', 'ス大塚', 'セ大塚', 'ソ大塚', 'ザ大塚', 'ジ大塚', 'ズ大塚', 'ゼ大塚', 'ゾ大塚'
			]
				.forEach((t, i) => {
					employees.push({
						id: _.padStart(`${i + 1}`, 12, '0'),
						code: _.padStart(`${i + 1}`, 6, '0'),
						name: t
					})
				});

			vm.employees(employees);
			// end mock data

			ko.computed({
				read: () => {
					const type = ko.unwrap(vm.button);
					const dataSource = ko.unwrap(vm.employees);

					const $grid = $(vm.$el).find('#grid-employee');

					const filtereds = [];
					const doFilter = (codes: string[]) => {
						return _.filter(dataSource, (record: Employee) => codes.indexOf(record.name[0]) > -1);
					};

					if ($grid.data('igGrid')) {
						switch (vm.$i18n(type)) {
							default:
							case '全員':
								filtereds.push(...dataSource);
								break;
							case 'ア':
								filtereds.push(...doFilter(['ア', 'イ', 'ウ', 'エ', 'オ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ヴ']));
								break;
							case 'カ':
								filtereds.push(...doFilter(['カ', 'キ', 'ク', 'ケ', 'コ', 'ガ', 'ギ', 'グ', 'ゲ', 'ゴ', 'ヵ', 'ヶ']));
								break;
							case 'サ':
								filtereds.push(...doFilter(['サ', 'シ', 'ス', 'セ', 'ソ', 'ザ', 'ジ', 'ズ', 'ゼ', 'ゾ']));
								break;
							case 'タ':
								filtereds.push(...doFilter(['タ', 'チ', 'ツ', 'テ', 'ト', 'ダ', 'ヂ', 'ヅ', 'デ', 'ド', '	ッ']));
								break;
							case 'ナ':
								filtereds.push(...doFilter(['ナ', 'ニ', 'ヌ', 'ネ', 'ノ']));
								break;
							case 'ハ':
								filtereds.push(...doFilter(['ハ', 'ヒ', 'フ', 'ヘ', 'ホ', 'バ', 'ビ', 'ブ', 'ベ', 'ボ', 'パ', 'ピ', 'プ', 'ペ', 'ポ']));
								break;
							case 'マ':
								filtereds.push(...doFilter(['マ', 'ミ', 'ム', 'メ', 'モ']));
								break;
							case 'ヤ':
								filtereds.push(...doFilter(['ヤ', 'ユ', 'ヨ', 'ャ', 'ュ', 'ョ']));
								break;
							case 'ラ':
								filtereds.push(...doFilter(['ラ', 'リ', 'ル', 'レ', 'ロ']));
								break;
							case 'ワ':
								filtereds.push(...doFilter(['ワ', 'ヲ', 'ン', 'ヮ']));
								break;
						}
						
						$grid.igGridSelection('clearSelection');

						$grid.igGrid('option', 'dataSource', _.orderBy(filtereds, ['name', 'code'], ['asc', 'asc']));
					}
				}
			});

			_.each([
				'KDP003_100',
				'KDP003_101',
				'KDP003_102',
				'KDP003_103',
				'KDP003_104',
				'KDP003_105',
				'KDP003_106',
				'KDP003_107',
				'KDP003_108',
				'KDP003_109'
			], (text) => buttons.push({ text, width: 1 }));

			buttons.push({ text: 'KDP003_111', width: 2 });

			vm.buttons(buttons);
		}

		mounted() {
			const vm = this;
			const $grid = $(vm.$el).find('#grid-employee');

			$(vm.$el).attr('id', 'stamp-employee-selection');

			$grid
				.igGrid({
					showHeader: false,
					columns: [
						{ headerText: "", key: "code", width: '80px', dataType: "string" },
						{ headerText: "", key: "name", dataType: "string" }
					],
					features: [
						{
							name: "Selection",
							mode: "row",
							rowSelectionChanged: function(evt, ui) {
								const { index } = ui.row;
								const dataSources = ko.toJS(vm.employees);

								if (dataSources[index]) {
									console.log(dataSources[index]);
								}
							}
						}
					],
					width: "240px",
					height: `${65 * 7}px`,
					dataSource: _.orderBy(ko.toJS(vm.employees), ['name', 'code'], ['asc', 'asc'])
				});

			_.extend(window, { vmm: vm, $grid });

			$(vm.$el).find('[data-bind]').removeAttr('data-bind');
		}
	}

	interface Button {
		text: string;
		width: 1 | 2;
	}

	interface Employee {
		id: string;
		code: string;
		name: string;
	}
}