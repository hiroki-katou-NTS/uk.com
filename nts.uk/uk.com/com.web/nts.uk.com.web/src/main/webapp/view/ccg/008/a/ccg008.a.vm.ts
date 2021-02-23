module nts.uk.com.view.ccg008.a.screenModel {
	import request = nts.uk.request;
	const MINUTESTOMILISECONDS = 60000;

	const D_FORMAT = 'YYYY/MM/DD';

	const API = {
		getCache: "screen/com/ccg008/get-cache",
		getClosure: "screen/com/ccg008/get-closure",
		getSetting: "screen/com/ccg008/get-setting",
		getDisplayTopPage: "toppage/getTopPage",
		extract: "sys/portal/createflowmenu/extractListFileId",
		getLoginUser: "screen/com/ccg008/get-user"
	};

	const getWidgetName = (type: number) => {
		switch (type) {
			case 0:
				return "ktg-005-a";
			case 1:
				return "ktg-001-a";
			case 2:
				return "ktg-004-a";
			case 3:
				return "ktg-026-a";
			case 4:
				return "ktg-027-a";
			case 5:
				return "kdp-001-a";
			case 6:
				return "ktg031-component";
			case 7:
				return 'ccg005-component';
			default:
				return '';
		}
	}

	type LAYOUT_DISPLAY_TYPE = null | 0 | 1 | 2 | 3;

	type WIDGET = {
		name: string;
		params: any;
	};

	@handler({
		bindingName: 'widget-group',
		validatable: true,
		virtual: false
	})
	export class WidgetGroupBindingHandler implements KnockoutBindingHandler {
		init = (element: HTMLElement, valueAccessor: () => KnockoutObservableArray<WIDGET>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: ViewModel, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } => {
			element.removeAttribute('data-bind');

			if (element.tagName !== 'DIV') {
				element.innerHTML = 'Please use this binding for only [DIV] tag.';

				return;
			}

			const items = valueAccessor();

			element.classList.add('widget-group');
			element.innerHTML = '<div data-bind="widget: { name: wg.name, params: wg.params }"></div>';

			ko.applyBindingsToNode(element, { foreach: { data: items, as: 'wg' } }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	@handler({
		bindingName: 'widget-frame',
		validatable: true,
		virtual: false
	})
	export class WidgetFrameBindingHandler implements KnockoutBindingHandler {
		init = (element: HTMLElement, valueAccessor: () => KnockoutObservable<string | null>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: ViewModel, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } => {
			element.removeAttribute('data-bind');

			if (element.tagName !== 'DIV') {
				element.innerHTML = 'Please use this binding for only [DIV] tag.';

				return;
			}

			const url = valueAccessor();

			element.classList.add('widget-frame');

			ko.computed({
				read: () => {
					const src = ko.unwrap<string | null>(url);

					if (!src) {
						element.innerHTML = 'NO_DATA_SETTING';
					} else {
						element.innerHTML = `<iframe src="${src}" />`;
					}
				},
				disposeWhenNodeIsRemoved: element
			});

			return { controlsDescendantBindings: true };
		}
	}

	@bean()
	export class ViewModel extends ko.ViewModel {
		dateSwitch: KnockoutObservableArray<any> = ko.observableArray([]);

		closureId: KnockoutObservable<number> = ko.observable(1);

		lstClosure: KnockoutObservableArray<ItemCbbModel> = ko.observableArray([]);
		reloadInterval: KnockoutObservable<number> = ko.observable(0);
		paramWidgetLayout2: KnockoutObservableArray<WidgetSettingDto> = ko.observableArray([]);
		paramWidgetLayout3: KnockoutObservableArray<WidgetSettingDto> = ko.observableArray([]);
		paramIframe1: KnockoutObservable<DisplayInTopPage> = ko.observable();
		topPageSetting: any;

		isShowSwitch: KnockoutObservable<boolean> = ko.observable(false);
		isShowButtonRefresh: KnockoutObservable<boolean> = ko.observable(false);
		isShowButtonSetting: KnockoutObservable<boolean> = ko.observable(false);

		dataToppage: KnockoutObservable<DataTopPage> = ko.observable(null);

		currentOrNextMonth: KnockoutObservable<null | 1 | 2> = ko.observable(null);
		layoutDisplayType: KnockoutObservable<LAYOUT_DISPLAY_TYPE> = ko.observable(null);

		widgetLeft: KnockoutObservableArray<WIDGET> = ko.observableArray([]);
		widgetCenter: KnockoutObservable<string | null> = ko.observable(null);
		widgetRight: KnockoutObservableArray<WIDGET> = ko.observableArray([]);

		classLayoutName!: KnockoutComputed<string>;
    startDateInClosure: KnockoutObservable<string> = ko.observable(''); 
    startDate : KnockoutObservable<string> = ko.observable('');
    endDate: KnockoutObservable<string> = ko.observable('');

    itemLayoutA1_7 = ko.computed(() => {
      return this.$i18n('CCG008_17', [`${this.startDate()}`, `${this.endDate()}`]);
    });

		reload: number | null = null;

		constructor() {
			super();

			const vm = this;

			vm.dateSwitch([
				{ code: "1", name: vm.$i18n("CCG008_14") },
				{ code: "2", name: vm.$i18n("CCG008_15") }
			]);

			vm.classLayoutName = ko.computed({
				read: () => {
					const ltpy = ko.unwrap<LAYOUT_DISPLAY_TYPE>(vm.layoutDisplayType);

					return `layout-type-${ltpy}`;
				}
			});

			vm.currentOrNextMonth
				.subscribe(function (currentOrNextMonth: 1 | 2) {
					if (currentOrNextMonth) {
						const closureId = ko.unwrap<number>(vm.closureId);

						vm.$window
							.storage('cache', {
								closureId,
								currentOrNextMonth
							})
							.then((cache: any) => vm.$window.shared('cache', cache));

              const dataProcess = currentOrNextMonth && currentOrNextMonth == 1 ? parseInt(vm.startDateInClosure()) : parseInt(vm.startDateInClosure()) + 1
              const params: any = {
                closureId:  closureId,
                processDate : dataProcess
              };
              vm.$ajax("com", API.getClosure, params).then((data:any) => {
                vm.startDate(moment.utc(data.startDate).format('MM/DD'));
                vm.endDate(moment.utc(data.endDate).format('MM/DD'));
              })
						vm.callApiTopPage();
					}
				});

			vm.closureId
				.subscribe(function (value) {
					vm.currentOrNextMonth.valueHasMutated();
				});

			vm.reloadInterval
				.subscribe((data: any) => {
					const minutes = vm.getMinutes(data);
					const miliSeconds = minutes * MINUTESTOMILISECONDS;

					clearInterval(vm.reload);

					if (data !== 0) {
						vm.reload = setInterval(() => {
							if (vm.paramWidgetLayout2().length > 0 || vm.paramWidgetLayout3().length > 0) {
								vm.callApiTopPage();
							}
						}, miliSeconds);
					}
				});
		}

		created() {
			const vm = this;
			const { isEmployee } = vm.$user;

			const GLOGIN = vm.$ajax("com", API.getLoginUser);

			const GCAHCE = isEmployee ? vm.$ajax("com", API.getCache) : $.Deferred().resolve(null);
			const GSETTING = isEmployee ? vm.$ajax("com", API.getSetting) : $.Deferred().resolve(null);

      const { value: transferData } = __viewContext.transferred || { value: undefined };
      const { screen, topPageCode } = transferData || { screen: 'other', topPageCode: '' };
			vm.$blockui('grayout')
				.then(() => $.when(GLOGIN, GSETTING, GCAHCE))
				.then((user, setting, cache) => {
					if (setting) {
						if (setting.reloadInterval) {
							vm.reloadInterval(setting.reloadInterval);
						}

						if (user) {
							vm.isShowButtonSetting(true);
						}

						vm.topPageSetting = setting;
					}

					//var fromScreen = "login"; 
            if (cache) {
              if(screen == 'login') {

                vm.$window
                  .storage("cache", cache)
                  .then(() => {
                    vm.$window
                      .storage('cache')
                      .then((obj: any) => {
                        if (obj) {
                          const { switchingDate } = vm.topPageSetting;
                          const endDate = moment.utc(obj.endDate, D_FORMAT).add(switchingDate, 'day').startOf('day');
    
                          if (endDate.isBefore(moment().startOf('day'))) {
                            vm.currentOrNextMonth(2);
    
                            obj.currentOrNextMonth = 2;
                          } else {
                            vm.currentOrNextMonth(1);
                          }
    
                          vm.closureId(obj.closureId);
    
                          vm.$window.shared('cache', obj);
                        } else {
                          vm.closureId(1);
                          vm.currentOrNextMonth(null);
                        }
                      });
                      vm.dataToppage(null);
                      vm.startDateInClosure(cache.startDate);
                      const dataProcess = vm.currentOrNextMonth() && vm.currentOrNextMonth() === 1 ? parseInt(cache.startDate) : parseInt(cache.startDate) + 1
                      const params: any = {
                        closureId:  cache.closureId,
                        processDate : dataProcess
                      };
                      vm.$ajax("com", API.getClosure, params).then((data:any) => {
                        vm.startDate(moment.utc(data.startDate).format('MM/DD'));
                        vm.endDate(moment.utc(data.endDate).format('MM/DD'));
                      })
                  });
                } else {
                    vm.$window.storage("cache").then((obj: any) => {
                      console.log(obj);
                      vm.dataToppage(null);
                      vm.startDateInClosure(cache.startDate);
                      vm.closureId(obj.closureId);
                      vm.currentOrNextMonth(obj.currentOrNextMonth);
                      const dataProcess = vm.currentOrNextMonth() && vm.currentOrNextMonth() == 1 ? parseInt(cache.startDate) : parseInt(cache.startDate) + 1
                      const params: any = {
                        closureId:  cache.closureId,
                        processDate : dataProcess
                      };
                      vm.$ajax("com", API.getClosure, params).then((data:any) => {
                        vm.startDate(moment.utc(data.startDate).format('MM/DD'));
                        vm.endDate(moment.utc(data.endDate).format('MM/DD'));
                      })
                 })
            }
          } 
          
					
				})
				.always(() => vm.$blockui("clear"));

		}

		mounted() {
			const vm = this;

			$(vm.$el)
				.removeAttr('data-bind')
				.find('[data-bind]')
				.removeAttr('data-bind');
		}

		callApiTopPage() {
			const vm = this;
			const qs = (request as any).QueryString;
			const { toppagecode } = qs.parseUrl(location.href).items;
			const { value: transferData } = __viewContext.transferred || { value: undefined };
			const { screen, topPageCode } = transferData || { screen: 'other', topPageCode: '' };

			// clear layout
			vm.layoutDisplayType(null);

			// clear widget data
			vm.widgetLeft([]);
			vm.widgetCenter(null);
			vm.widgetRight([]);

			vm
				.$blockui('grayout')
				.then(() => vm.$ajax("com", API.getSetting))
				.then((topPageSetting: any) => {
					vm
						.$ajax("com", API.getDisplayTopPage, {
							topPageSetting,
							fromScreen: screen || 'other',
							topPageCode: topPageCode || toppagecode || '',
						})
						.then((data: DataTopPage) => {
							// load widget data
							vm.getToppage(data, screen);
						});
				})
				.always(() => vm.$blockui("clear"));
		}

		getToppage(data: DataTopPage, screen: string = 'other') {
			const vm = this;
			const { topPageSetting, closureId, currentOrNextMonth } = vm;
			const { displayTopPage, standardMenu } = data;

			const loadWidget = () => {
				if (!displayTopPage) {
					return;
				}

				const { layout2, layout3, urlLayout1, layoutDisplayType } = displayTopPage;

				const layout2Widget = (settings: WidgetSettingDto[]) => {
					return _
						.chain(settings)
						.orderBy(['order', 'asc'])
						.map(({ widgetType }) => ({
							name: getWidgetName(widgetType),
							params: {
								closureId: ko.unwrap<number>(closureId),
								currentOrNextMonth: ko.unwrap<1 | 2>(currentOrNextMonth)
							}
						}))
						.value();
				};

				vm.widgetCenter(urlLayout1);

				switch (layoutDisplayType) {
					default:
					case 0:
						// clear widgets
						vm.widgetLeft([]);
						vm.widgetRight([]);
						break;
					case 1:
						// clear widget of left group
						vm.widgetLeft([]);

						const rightWidgets = layout2Widget(layout2);

						vm.widgetRight(rightWidgets);
						break;
					case 2:
						const leftWidgets = layout2Widget(layout2);

						vm.widgetLeft(leftWidgets);

						// clear widget of right group
						vm.widgetRight([]);
						break;
					case 3:
						const firstWidgets = layout2Widget(layout2);
						const thirstWidgets = layout2Widget(layout3);

						vm.widgetLeft(firstWidgets);
						vm.widgetRight(thirstWidgets);
						break;
				}


				if (layout2) {
					const showSwitch = _.filter(layout2, ({ widgetType }) => [0, 1, 2, 3, 4].indexOf(widgetType) > -1);
					vm.isShowSwitch(!!showSwitch.length);
				}

				if (layout3) {
					const showSwitch = _.filter(layout3, ({ widgetType }) => [0, 1, 2, 3, 4].indexOf(widgetType) > -1);
					const showClosure = _.filter(layout3, ({ widgetType }) => widgetType === 1);

					// not overwrite value of layout2
					if (ko.unwrap<boolean>(vm.isShowSwitch) === false) {
						vm.isShowSwitch(!!showSwitch.length);
					}

				}
			};

			if (screen === 'login') {
				const { menuClassification, loginMenuCode } = topPageSetting;

				if (menuClassification !== MenuClassification.TopPage && loginMenuCode !== '0000') {
					const { url } = standardMenu;

					if (url) {
						const [, res] = url.split('web/');

						if (!!res) {
							// show standardmenu
							const topPageUrl = "view/ccg/008/a/index.xhtml";

							if (topPageUrl !== res.trim()) {
								if (_.includes(url, ".at.")) {
									vm.$jump('at', `/${res}`);
								} else {
									vm.$jump('com', `/${res}`);
								}
							}
						}
					}
				} else {
					loadWidget();
				}
			} else {
				loadWidget();
			}

			if (displayTopPage) {
				const { layout2, layoutDisplayType } = displayTopPage;

				vm.layoutDisplayType(layoutDisplayType);
				vm.isShowButtonRefresh(layoutDisplayType !== 0 && !!layout2.length);
			}
		}

		openScreenE() {
			const vm = this;
			const interval = ko.unwrap<number>(vm.reloadInterval);

			vm.$window
				.shared('DataFromScreenA', interval)
				.then(() => vm.$window.modal('com', '/view/ccg/008/e/index.xhtml'))
				.then(() => vm.$window.shared('DataFromScreenE'))
				.then((value: number) => vm.reloadInterval(value))
				.then(() => vm.$window.shared('DataFromScreenE', null));
		}

		getMinutes(value: number) {
			return [0, 1, 5, 10, 20, 30, 40, 50, 60][value] || 0;
		}
	}

	export class ItemCbbModel {
		closureId: number;
		closureName: string;
		constructor(closureId: number, closureName: string) {
			this.closureId = closureId;
			this.closureName = closureName;
		}
	}

	export class DataTopPage {
		displayTopPage: DisplayInTopPage;
		menuClassification: number;
		standardMenu: StandardMenuDto;
		constructor(init?: Partial<DataTopPage>) {
			$.extend(this, init);
		}
	}

	export class DisplayInTopPage {
		layout1: Array<FlowMenuOutputCCG008>;
		layout2: Array<WidgetSettingDto>;
		layout3: Array<WidgetSettingDto>;
		urlLayout1: string;
		layoutDisplayType: LAYOUT_DISPLAY_TYPE;
		constructor(init?: Partial<DisplayInTopPage>) {
			$.extend(this, init);
		}
	}

	export class WidgetSettingDto {
		widgetType: number;
		order: number;
		constructor(init?: Partial<WidgetSettingDto>) {
			$.extend(this, init);
		}
	}

	export class FlowMenuOutputCCG008 {
		flowCode: string;
		flowName: string;
		fileId: string;
		isFlowmenu: boolean;
		constructor(init?: Partial<FlowMenuOutputCCG008>) {
			$.extend(this, init);
		}
	}

	export class StandardMenuDto {
		companyId: string;
		code: string;
		targetItems: string;
		displayName: string;
		displayOrder: number;
		menuAtr: number;
		url: string;
		system: number;
		classification: number;
		webMenuSetting: number;
		afterLoginDisplay: number;
		logLoginDisplay: number;
		logStartDisplay: number;
		logUpdateDisplay: number;
		logSettingDisplay: LogSettingDisplayDto;
		constructor(init?: Partial<StandardMenuDto>) {
			$.extend(this, init);
		}
	}

	export class LogSettingDisplayDto {
		logLoginDisplay: number;
		logStartDisplay: number;
		logUpdateDisplay: number;
	}

	export class ItemLayout {
		url: string;
		html: string;
		order: number;
		constructor(init?: Partial<ItemLayout>) {
			$.extend(this, init);
		}
	}

	export enum MenuClassification {
		/**0:標準 */
		Standard = 0,
		/**1:任意項目申請 */
		OptionalItemApplication = 1,
		/**2:携帯 */
		MobilePhone = 2,
		/**3:タブレット */
		Tablet = 3,
		/**4:コード名称 */
		CodeName = 4,
		/**5:グループ会社メニュー */
		GroupCompanyMenu = 5,
		/**6:カスタマイズ */
		Customize = 6,
		/**7:オフィスヘルパー稟議書*/
		OfficeHelper = 7,
		/**8:トップページ*/
		TopPage = 8,
		/**9:スマートフォン*/
		SmartPhone = 9,
	}
}