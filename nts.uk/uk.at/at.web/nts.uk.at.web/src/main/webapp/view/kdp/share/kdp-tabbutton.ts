/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {
	const tabButtonTempate = `
		<!-- ko if: ko.unwrap(filteredTabs).length -->
		<div id="stamp-desc" data-bind="let: { $tab: ko.toJS(currentTab) }">
			<div data-bind="html: $tab.stampPageComment, style: { color: $tab.stampPageCommentColor }"></div>
		</div>
		<div id="tab-button-group" class="ui-tabs ui-corner-all ui-widget ui-widget-content horizontal">
			<ul class="ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" data-bind="foreach: filteredTabs">
				<li class="ui-tabs-tab ui-corner-top ui-state-default ui-tab"
						data-bind="
							css: {
								'ui-tabs-active ui-state-active': ko.toJS($component.selected) === pageNo
							}
						">
					<a href="#" tabindex="-1" class="ui-tabs-anchor"
							data-bind="
								text: stampPageName,
								click: function() { $component.selected(pageNo); }
							"></a>
				</li>
			</ul>
			<div class="ui-tabs-panel ui-corner-bottom ui-widget-content" data-bind="foreach: filteredTabs">
				<div class="grid-container" data-bind="
						if: ko.toJS($component.selected) === pageNo,
						css: 'btn-layout-type-' + buttonLayoutType">
					<!-- ko foreach: _.chunk(ko.unwrap(buttonSettings), buttonLayoutType === 0 ? 2 : 4) -->
					<div data-bind="foreach: $data">
						<button class="stamp-rec-btn"
							data-bind="
								btn-setting: $data,
								click: function() { 
									$component.params.click($data, ko.toJS($component.currentTab));
								},
								timeClick: -1
							"></button>
					</div>
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
					'visibility': data.btnPositionNo === -1 ? 'hidden' : 'visible',
					'height': data.height + 'px'
				});
		}
	}
	const COMPONENT_NAME = 'kdp-tab-button-panel';

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class ButtonSettingComponentBindingHandler implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => any, __ab: KnockoutAllBindingsAccessor, ___vm: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
			const name = COMPONENT_NAME;
			const params = valueAccessor();

			ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	@component({
		name: COMPONENT_NAME,
		template: tabButtonTempate
	})
	export class kdpTabButtonComponent extends ko.ViewModel {
		selected: KnockoutObservable<number> = ko.observable(0);

		currentTab!: KnockoutComputed<PageLayout>;

		filteredTabs!: KnockoutComputed<PageLayout[]>;

		buttonSize: KnockoutObservable<number> = ko.observable(0);

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

			if (!params.marginBottom) {
				params.marginBottom = ko.observable(0);
			}

			ko.computed({
				read: () => {
					const data = ko.unwrap(params.tabs);

					if (!vm.selected()) {
						if (data && data.length) {
							const first = data[0];

							vm.selected(first.pageNo);
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
					});
					const exist = _.find(filteredTabs, (d) => d.pageNo === selected);

					const currentTab = _.clone(exist) || {
						pageNo: -1,
						buttonLayoutType: -1,
						buttonSettings: [],
						stampPageComment: '',
						stampPageCommentColor: '',
						stampPageName: ''
					};

					// escape html and replace new line chars to break tag
					currentTab.stampPageComment = _.escape(currentTab.stampPageComment).replace(/(\r|\n)/g, '<br />');

					return currentTab;
				}
			});

			vm.filteredTabs = ko.computed({
				read: () => {
					const data = ko.unwrap(params.tabs);
					const buttonSize = ko.unwrap(vm.buttonSize);
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
							const { buttonSettings, buttonLayoutType } = cloned;
							const { SMALL_8, LARGE_2_SMALL_4 } = LAYOUT_TYPE;
							const size = (buttonLayoutType === LARGE_2_SMALL_4) ? 6 : 8;

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

									const constance = ((buttonLayoutType === LARGE_2_SMALL_4 && j < 3) || buttonLayoutType === SMALL_8) ? 2 : 1;

									btn.height = Math.max(buttonSize, 42) * constance + (buttonLayoutType === SMALL_8 ? 7 : 0);

									buttons.push(btn);
								} else {
									buttons.push({
										audioType: 0,
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
										usrArt: -1,
										height: buttonSize
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

			vm.selected.valueHasMutated();

			$(window)
				.on('resize', () => {
					if (vm.$el) {
						const marginBottom = ko.toJS(vm.params.marginBottom);
						const tabs = vm.$el.querySelector('#tab-button-group');

						if (tabs) {
							const bound = tabs.getBoundingClientRect();
							const height = Math.floor((window.innerHeight - bound.top - 110 - marginBottom) / 4);

							vm.buttonSize(height);
						}
					}
				})
				.trigger('resize');
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
		marginBottom: KnockoutObservable<number>;
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
		audioType: 0 | 1 | 2;
		btnBackGroundColor: string;
		btnDisplayType: number;
		btnName: string;
		btnPositionNo: number;
		btnReservationArt: number;
		btnTextColor: string;
		changeCalArt: number;
		changeClockArt: ChangeClockArt;
		changeHalfDay: number;
		goOutArt: number;
		setPreClockArt: number;
		usrArt: NotUseAtr;
		height: number;
	}

	export enum NotUseAtr {
		/** The use. */
		USE = 1,

		/** The not use. */
		NOT_USE = 0
	}

	export enum ChangeClockArt {
		/** 0. 出勤 */
		GOING_TO_WORK = 0,

		/** 1. 退勤 */
		WORKING_OUT = 1,

		/** 2. 入門 */
		OVER_TIME = 2,

		/** 3. 退門 */
		BRARK = 3,

		/** 4. 外出 */
		GO_OUT = 4,

		/** 5. 戻り */
		RETURN = 5,

		/** 6. 応援開始 */
		FIX = 6,

		/** 7. 臨時出勤 */
		TEMPORARY_WORK = 7,

		/** 8. 応援終了 */
		END_OF_SUPPORT = 8,

		/** 9. 臨時退勤 */
		TEMPORARY_LEAVING = 9,

		/** 10. PCログオン */
		PC_LOG_ON = 10,

		/** 11. PCログオフ */
		PC_LOG_OFF = 11,

		/** 12. 応援出勤 */
		SUPPORT = 12,

		/** 13. 臨時+応援出勤 */
		TEMPORARY_SUPPORT_WORK = 13
	}
}