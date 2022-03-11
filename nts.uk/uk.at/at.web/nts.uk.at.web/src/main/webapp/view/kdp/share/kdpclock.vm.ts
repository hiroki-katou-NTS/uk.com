/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {

	const template = `
		<div class="time">
			<div class="date" data-bind="clock-day: time, setting: settings"></div>
			<div class="hour-second" >
				<span class="hour" data-bind="date: time, format: 'HH:mm', style:{
					'color': ko.toJS(settings).textColor}"></span>
				<span class="second" data-bind="date: time, format: ' ss', style:{
					'color': ko.toJS(settings).textColor}"></span>
			</div>
		</div>
		<div class="button-group" data-bind="events: $component.events"></div>
		<style>
			.time-container {
				position: relative;
			}
			.time-container .time {
				width: 270px;
				margin: auto;
				position: relative;
				white-space: nowrap;
			}
			
			.time-container .time .date {
				text-align: center;
			}
			
			.time-container .time .date .ymd {
				font-size: 32px;
				font-family: Quicksand !important;
			}
			
			.time-container .time .date .ddd {
				font-size: 26px;
				font-family: Quicksand !important;
				font-weight: bold;
			}

			.time-container .time .hour-second {
				padding-top: 10px;
			}
			.time-container .time .hour {
				position: relative;
				z-index: 1;
				font-size: 80px;
				line-height: 80px;
				font-family: Quicksand !important;
			}
			.time-container .time .second {
				font-size: 50px;
				line-height: 50px;
				position: relative;
				left: 16px;
				z-index: 1;
				font-family: Quicksand !important;
			}

			.time-container .button-group {
				position: absolute;
				bottom: -55px;
				right: 15px;
			}
			.time-container .button-group button {
				width: 80px;
				margin-left: 10px;
			}
		</style>
	`;
	const COMPONENT_NAME = 'stamp-clock';

	@handler({
		bindingName: 'clock-day'
	})
	export class ClockDayBinding implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => any, allBindingAccessor: KnockoutAllBindingsAccessor) {
			const time = valueAccessor();
			const setting = allBindingAccessor.get('setting');

			const $elem = $(element);
			const $ymd = $("<span>").addClass("ymd").appendTo($elem);
			const $ddd = $("<span>").addClass("ddd").appendTo($elem);

			ko.applyBindingsToNode($ymd[0], { date: time, format: `YYYY/MM/DD`, style: { color: ko.unwrap(setting).textColor } });
			ko.applyBindingsToNode($ddd[0], { date: time, format: `（ddd）`, style: { color: ko.unwrap(setting).textColor } });

			element.removeAttribute('data-bind');
		}
	}


	/*
	<div data-bind="if: !!ko.unwrap(events.setting.show)">
		<button class="btn-setting" data-bind="icon: 5, click: events.setting.click"></button>
	</div>
	<div data-bind="if: !!ko.unwrap(events.company.show)">
		<a href="#" data-bind="i18n: 'KDP003_2', click: events.company.click"></a>
	</div>
	*/

	interface ClickEvents {
		setting: {
			show: KnockoutObservable<boolean>;
			click: () => void;
		};
		company: {
			show: KnockoutObservable<boolean>;
			click: () => void;
		};
	}

	@handler({
		bindingName: 'events'
	})
	export class SettingBinding implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => ClickEvents, allBindingAccessor: KnockoutAllBindingsAccessor) {
			const { setting, company } = valueAccessor();

			if (setting) {
				const button = $('<button>', { class: 'btn-setting' }).get(0);

				ko.applyBindingsToNode(button, { text: "設定", click: setting.click });

				ko.computed({
					read: () => {
						const show = ko.unwrap(setting.show);

						if (show) {
							$(element).prepend(button);
						} else {
							$(button).remove();
						}
					},
					disposeWhenNodeIsRemoved: element
				});
			}

			if (company) {
				const button = $('<button>', { class: 'btn-link' }).get(0);

				ko.applyBindingsToNode(button, { i18n: 'KDP003_2', click: company.click });

				ko.computed({
					read: () => {
						const show = ko.unwrap(company.show);

						if (show) {
							$(element).append(button);
						} else {
							$(button).remove();
						}
					},
					disposeWhenNodeIsRemoved: element
				});
			}

			element.removeAttribute('data-bind');
		}
	}

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class EmployeeComponentBindingHandler implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => any, __ab: KnockoutAllBindingsAccessor, ___vm: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
			const name = COMPONENT_NAME;
			const params = valueAccessor();

			element.classList.add('time-container');

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
		regionalTime: KnockoutObservable<number> = ko.observable(0);

        created(params?: StampClocParam) {
            const vm = this;

            if (params) {
                const {regionalTime } = params;

                if (regionalTime) {
                    vm.regionalTime = regionalTime;
                } else {
                    vm.$window.storage("workLocationInfo").then((workLocInfo) => {
                        vm.regionalTime = workLocInfo.regional;
                    });
                }
                vm.initStart(params);

            }

            vm.getServerTime();

            setInterval(() => vm.time(moment(vm.$date.now()).add(ko.unwrap(vm.regionalTime), 'm').toDate()), 300);
        }
        initStart(params) {
            let vm = this;
            let { setting, events, regionalTime } = params;
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
                const { textColor } = ko.toJS(setting || {});

                if (textColor) {
                    return { textColor };
                } else {
                    return { textColor: '#7F7F7F' };
                }
            });
        }

		mounted() {
			const vm = this;

			$(vm.$el).attr('id', 'stamp-header');
		}
        getServerTime() {
            let vm = this;
            vm.$ajax('at', '/server/time/now')
                .then((c) => {
                    const date = moment(moment(c).add(ko.unwrap(vm.regionalTime), 'm').toDate(), 'YYYY-MM-DDTHH:mm:ss').toDate();
                    vm.time(date);
                });
        }
	}

	export interface StampClocParam {
		events?: ClickEvent;
		setting?: StampColor;
		regionalTime?: KnockoutObservable<number>;
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
	}
}