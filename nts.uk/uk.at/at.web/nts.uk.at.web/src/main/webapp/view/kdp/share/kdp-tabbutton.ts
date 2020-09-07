/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {
	const tabButtonTempate = `
		<!-- ko if: ko.unwrap(filteredTabs).length -->
		<div id="stamp-desc" data-bind="text: ko.unwrap(currentTab).stampPageComment"></div>
		<div id="tab-button-group" class="ui-tabs ui-corner-all ui-widget ui-widget-content horizontal">
			<ul class="ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" data-bind="foreach: filteredTabs">
				<li class="ui-tabs-tab ui-corner-top ui-state-default ui-tab"
						data-bind="
							css: {
								'ui-tabs-active ui-state-active': stampPageName === ko.toJS($component.selected)
							}
						">
					<a href="#" tabindex="-1" class="ui-tabs-anchor"
							data-bind="
								text: stampPageName,
								click: function() { $component.selected(stampPageName); }
							"></a>
				</li>
			</ul>
			<div class="ui-tabs-panel ui-corner-bottom ui-widget-content" data-bind="foreach: filteredTabs">
				<div class="grid-container" data-bind="
						if: ko.toJS($component.selected) === stampPageName,
						css: 'btn-layout-type-' + buttonLayoutType">
					<!-- ko foreach: buttonSettings -->
					<button class="stamp-rec-btn" data-bind="btn-setting: $data, click: function() { $component.params.click($data, ko.toJS($component.currentTab)); }"></button>
					<!-- /ko -->
				</div>
			</div>
		</div>
		<!-- /ko -->
	`;

	const DEFAULT_GRAY = '#E8E9EB';

	@handler({
		bindingName: 'btn-setting'
	})
	export class ButtonSettingBindingHandler implements KnockoutBindingHandler {
		update(element: any, valueAccessor: () => ButtonSetting): void {
			const data: ButtonSetting = ko.unwrap(valueAccessor());

			$(element)
				.text(data.btnName)
				.css({
					'color': data.btnTextColor,
					'background-color': data.btnBackGroundColor,
					'visibility': data.btnPositionNo === -1 ? 'hidden' : 'visible'
				});
		}
	}

	@component({
		name: 'kdp-tab-button-panel',
		template: tabButtonTempate
	})
	export class kdpTabButtonComponent extends ko.ViewModel {
		selected: KnockoutObservable<string> = ko.observable('');

		currentTab!: KnockoutComputed<PageLayout>;

		filteredTabs!: KnockoutComputed<PageLayout[]>;

		constructor(public params: StampParam) {
			super();

			const vm = this;

			if (!params.click) {
				params.click = () => { };
			}

			if (!params.tabs) {
				params.tabs = ko.observableArray([]);
			}

			if (!params.stampToSuppress) {
				params.stampToSuppress = ko.observable({
					departure: false,
					goOut: false,
					goingToWork: false,
					turnBack: false
				});
			}

			ko.computed({
				read: () => {
					const data = ko.unwrap(params.tabs);

					if (!vm.selected()) {
						if (data && data.length) {
							const first = data[0];

							vm.selected(first.stampPageName);
						}
					}
				}
			});

			vm.currentTab = ko.computed({
				read: () => {
					const $el = $(vm.$el);
					const selected = ko.unwrap(vm.selected);
					const filteredTabs = ko.unwrap(vm.filteredTabs);

					vm.$nextTick(() => {
						$el
							.find('button')
							.attr('tabindex', $el.data('tabindex'));
					})

					return _.find(filteredTabs, (d) => d.stampPageName === selected) || {
						pageNo: -1,
						buttonLayoutType: -1,
						buttonSettings: [],
						stampPageComment: '',
						stampPageCommentColor: '',
						stampPageName: ''
					};
				}
			});

			vm.filteredTabs = ko.computed({
				read: () => {
					const data = ko.unwrap(params.tabs);
					const setting: StampToSuppress = ko.unwrap(params.stampToSuppress as any) || {
						goingToWork: false,
						departure: false,
						goOut: false,
						turnBack: false
					};
					const filters = [];

					for (let i = 1; i <= 5; i++) {
						const tab = _.find(data, (d) => d.pageNo === i);

						if (tab) {
							const cloned = _.cloneDeep(tab);
							const buttons: ButtonSetting[] = [];
							const { buttonSettings } = cloned;
							const size = (cloned.buttonLayoutType === LAYOUT_TYPE.LARGE_2_SMALL_4) ? 6 : 8;

							for (let j = 1; j <= size; j++) {
								const btn = _.find(buttonSettings, (btn) => btn.btnPositionNo === j);

								if (btn) {
									switch (btn.btnDisplayType) {
										default:
										case 1:
											if (setting.goingToWork) {
												btn.btnBackGroundColor = DEFAULT_GRAY;
											}
											break;
										case 2:
											if (setting.departure) {
												btn.btnBackGroundColor = DEFAULT_GRAY;
											}
											break;
										case 3:
											if (setting.goOut) {
												btn.btnBackGroundColor = DEFAULT_GRAY;
											}
											break;
										case 4:
											if (setting.turnBack) {
												btn.btnBackGroundColor = DEFAULT_GRAY;
											}
											break
									}

									buttons.push(btn);
								} else {
									buttons.push({
										audioType: -1,
										btnBackGroundColor: '',
										btnDisplayType: -1,
										btnName: '',
										btnPositionNo: -1,
										btnReservationArt: -1,
										btnTextColor: '',
										changeCalArt: -1,
										changeClockArt: -1,
										changeHalfDay: -1,
										goOutArt: -1,
										setPreClockArt: -1,
										usrArt: -1
									});
								}
							}

							cloned.buttonSettings = buttons;

							filters.push(cloned);
						}
					}

					return filters;
				}
			});
		}

		mounted() {
			const vm = this;
			const tid = 'tabindex';
			const $el = $(vm.$el);
			const tabindex = $el.attr(tid);

			if (tabindex) {
				$el
					.find('.ui-tabs')
					.attr(tid, tabindex);

				$el
					.find('.ui-tabs-tab')
					.attr(tid, tabindex)
					.on('keydown', (evt: JQueryEventObject) => {
						if (evt.keyCode === 13) {
							$(evt.target).find('a').trigger('click');
						}
					});

				$el
					.find('button')
					.attr(tid, tabindex);

				$el
					.removeAttr(tid)
					.data(tid, tabindex);
			}
		}
	}

	export enum LAYOUT_TYPE {
		LARGE_2_SMALL_4 = 0,
		SMALL_8 = 1
	}

	export interface StampParam {
		click: () => void;
		tabs: KnockoutObservableArray<PageLayout>;
		stampToSuppress: KnockoutObservable<StampToSuppress>;
	}

	export interface StampToSuppress {
		departure: boolean;
		goOut: boolean;
		goingToWork: boolean;
		turnBack: boolean;
	}

	export interface PageLayout {
		buttonLayoutType: number;
		pageNo: number;
		stampPageComment: string;
		stampPageCommentColor: string;
		stampPageName: string;
		buttonSettings: ButtonSetting[];
	}

	export interface ButtonSetting {
		audioType: number;
		btnBackGroundColor: string;
		btnDisplayType: number;
		btnName: string;
		btnPositionNo: number;
		btnReservationArt: number;
		btnTextColor: string;
		changeCalArt: number;
		changeClockArt: number;
		changeHalfDay: number;
		goOutArt: number;
		setPreClockArt: number;
		usrArt: number;
	}
}