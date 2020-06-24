/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const settingPeriodTemplate = `
	<div class="d-inline">
		<button class="btn-preview-period" data-bind="click: previewMonth, timeClick: -1"></button>
		<span data-bind="date: range().start"></span>
		<span data-bind="text: $component.$i18n('KSU001_66')"></span>
		<span data-bind="date: range().end"></span>
		<button class="btn-next-period" data-bind="click: nextMonth, timeClick: -1"></button>
	</div>
	<div class="d-inline cf" data-bind="ntsSwitchButton: {
		name: $component.$i18n('KSU001_38'),
		options: [{
			name: $component.$i18n('KSU001_39'),
			code: '1'
		}, {
			name: $component.$i18n('KSU001_40'),
			code: '2'
		}, {
			name: $component.$i18n('KSU001_41'),
			code: '3'
		}],
		optionsValue: 'code',
		optionsText: 'name',
		value: ko.observable('2'),
		required: true,
	}"></div>
`;

@component({
	name: 'ksu-setting-period',
	template: settingPeriodTemplate
})
class KSU001ASettingPeriodComponent extends ko.ViewModel {
	baseDate: KnockoutObservable<Date> = ko.observable(moment().startOf('day').toDate());

	range!: KnockoutComputed<{
		start: Date;
		end: Date;
	}>;

	created() {
		const vm = this;

		vm.range = ko.computed({
			read() {
				const date = moment(ko.toJS(vm.baseDate));

				return {
					start: date.startOf('month').toDate(),
					end: date.endOf('month').toDate()
				};
			}
		});
	}

	mounted() {
		const vm = this;

		$(vm.$el).addClass('setting-period');

		_.extend(window, { vm });
	}

	previewMonth() {
		const vm = this;
		const date = moment(ko.toJS(vm.baseDate));

		vm.baseDate(date.add(-1, 'month').toDate());
	}

	nextMonth() {
		const vm = this;
		const date = moment(ko.toJS(vm.baseDate));

		vm.baseDate(date.add(1, 'month').toDate());
	}
}