/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {

	// <div class="panel" id="stamp-date"
	// 	data-bind="style: {
	// 		'color': ko.toJS(settings).textColor,
	// 		'background-color': ko.toJS(settings).backGroundColor
	// 	}">
	// 	<span id="stamp-date-text" data-bind="date: time, format: 'YYYY年M月D日(ddd)'"></span>
	// </div>
	// <div class="panel" id="stamp-time"
	// 	data-bind="style: {
	// 		'color': ko.toJS(settings).textColor,
	// 		'background-color': ko.toJS(settings).backGroundColor
	// 	}">
	// 	<span id="stamp-time-text" data-bind="date: time, format: 'HH:mm'"></span>
	// </div>
	// <div class="button-group" data-bind="if: !!events">
	// 	<div data-bind="if: !!ko.unwrap(events.setting.show)">
	// 		<button class="btn-setting" data-bind="icon: 5, click: events.setting.click"></button>
	// 	</div>
	// 	<div data-bind="if: !!ko.unwrap(events.company.show)">
	// 		<button class="btn-company proceed small" data-bind="i18n: 'KDP003_2', click: events.company.click"></button>
	// 	</div>
	// </div>

	const template = `
	<div class="clock">
		<div>
			<span class="text-time" data-bind="i18n: 'KDP001_5'"></span>
			<span class="date" data-bind="date: time, format: 'YYYY年M月D日(ddd)'"></span>
		</div>
		<div>
			<span class="hours-minute" data-bind="date: time, format: 'HH:mm'"></span>
			<span class="seconds" data-bind="date: time, format: ':ss'"></span>
		</div>
		<div class="button-group" data-bind="if: !!events">
			<div data-bind="if: !!ko.unwrap(events.setting.show)">
				<button class="btn-setting" data-bind="icon: 5, click: events.setting.click"></button>
			</div>
			<div data-bind="if: !!ko.unwrap(events.company.show)">
				<a href="#" data-bind="i18n: 'KDP003_2', click: events.company.click"></a>
			</div>
		</div>
	</div>

	<style>
		.clock {
			text-align: center;
		}
		.clock .text-time {
			position: absolute;
			font-size: 60px;
			color: white;
			left: 405px;
			top: -15px;
		}
		.clock .date {
			color: #7F7F7F;
			padding-right: 65px;
		}
		.clock .hours-minute {
			color: #7F7F7F;
			font-size: 60px;
		}
		.clock .seconds {
			color: #7F7F7F;
			font-size: 40px;
		}
	</style>
`;
	const COMPONENT_NAME = 'stamp-clock';

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class EmployeeComponentBindingHandler implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => any, __ab: KnockoutAllBindingsAccessor, ___vm: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
			const name = COMPONENT_NAME;
			const params = valueAccessor();

			ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class StampClock extends ko.ViewModel {
		time: KnockoutObservable<Date> = ko.observable(new Date());

		events!: ClickEvent;
		settings!: KnockoutComputed<StampColor>;

		created(params?: StampClocParam) {
			const vm = this;

			if (params) {
				const { setting, events } = params;

				if (events) {
					// convert setting event to binding object
					if (_.isFunction(events.setting)) {
						const click = events.setting;

						events.setting = {
							click,
							show: true
						} as any;
					}

					// convert company event to binding object
					if (_.isFunction(events.company)) {
						const click = events.company;

						events.company = {
							click,
							show: true
						} as any;
					}

					vm.events = events;
				} else {
					vm.events = {
						company: {
							show: false,
							click: () => { }
						} as any,
						setting: {
							show: false,
							click: () => { }
						} as any
					};
				}

				vm.settings = ko.computed(() => {
					const { textColor, backGroundColor } = ko.toJS(setting || {});

					if (textColor && backGroundColor) {
						return { textColor, backGroundColor };
					} else {
						return { textColor: 'rgb(255, 255, 255)', backGroundColor: 'rgb(0, 51, 204)' };
					}
				});
			}

			setInterval(() => vm.time(vm.$date.now()), 100);
		}

		mounted() {
			const vm = this;

			$(vm.$el).attr('id', 'stamp-header');
		}
	}

	export interface StampClocParam {
		events?: ClickEvent;
		setting?: StampColor;
	}

	export interface ClickEvent {
		setting: () => void | {
			show: KnockoutObservable<boolean>;
			click: () => void;
		};
		company: () => void | {
			show: KnockoutObservable<boolean>;
			click: () => void;
		};
	}

	export interface StampColor {
		textColor: string;
		backGroundColor: string;
	}
}