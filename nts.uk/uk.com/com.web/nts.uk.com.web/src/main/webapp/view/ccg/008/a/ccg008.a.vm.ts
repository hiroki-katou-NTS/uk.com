module nts.uk.com.view.ccg008.a.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import ntsFile = nts.uk.request.file;
  import character = nts.uk.characteristics;

  const MINUTESTOMILISECONDS = 60000;
  const API = {
    getCache: "screen/com/ccg008/get-cache",
    getClosure: "screen/com/ccg008/get-closure",
    getSetting: "screen/com/ccg008/get-setting",
    getDisplayTopPage: "toppage/getTopPage",
    extract: "sys/portal/createflowmenu/extractListFileId",
    getLoginUser: "screen/com/ccg008/get-user"
}
  @bean()
  export class ViewModel extends ko.ViewModel {
    topPageCode: KnockoutObservable<string> = ko.observable("");
    isStart: boolean;
    dateSwitch: KnockoutObservableArray<any> = ko.observableArray([
      { code: "1", name: nts.uk.resource.getText("CCG008_14") },
      { code: "2", name: nts.uk.resource.getText("CCG008_15") },
    ]);
    selectedSwitch: KnockoutObservable<any> = ko.observable(null);
    switchVisible: KnockoutObservable<boolean>;
    isShowClosure: KnockoutObservable<boolean> = ko.observable(false);
    closureSelected: KnockoutObservable<number> = ko.observable(1);
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
    layoutDisplayType: KnockoutObservable<number> = ko.observable(null);
    visible: KnockoutObservable<boolean> = ko.observable(false);

    created() {
      const vm = this;
      var transferData = __viewContext.transferred.value;
      var code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
      var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
      vm.$blockui('grayout');
      vm.$ajax("com", API.getLoginUser).then((user) => {
        vm.$ajax("com", API.getSetting).then((res) => {
          if (res.reloadInterval) {
            vm.reloadInterval(res.reloadInterval);
          }
          if (user || res) {
            vm.isShowButtonSetting(true);
          }
          vm.topPageSetting = res;
          //var fromScreen = "login";
          if (fromScreen == "login") {
            vm.$ajax("com", API.getCache).then((data: any) => {
              character.save("cache", data).then(() => {
                vm.topPageCode(code);
                character.restore("cache").then((obj: any) => {
                  if (obj) {
                    const endDate = moment.utc(obj.endDate, "YYYY/MM/DD");
                    if (
                      endDate
                        .add(vm.topPageSetting.switchingDate)
                        .isSameOrAfter(moment.utc(new Date(), "YYYY/MM/DD"))
                    ) {
                      vm.selectedSwitch(1);
                    } else {
                      vm.selectedSwitch(2);
                      obj.currentOrNextMonth = 2;
                    }
                    vm.closureSelected(obj.closureId);
                    nts.uk.ui.windows.setShared("cache", obj);
                  } else {
                    vm.closureSelected(1);
                    vm.selectedSwitch(null);
                  }
                });
              });
            });
          } else {
            // get combobox and switch button
            character.restore("cache").done((obj: any) => {
              if (obj) {
                if (obj.currentOrNextMonth) {
                  vm.selectedSwitch(obj.currentOrNextMonth);
                } else {
                  vm.selectedSwitch(null);
                }
                vm.closureSelected(obj.closureId);
                nts.uk.ui.windows.setShared("cache", obj);
              } else {
                vm.closureSelected(1);
                vm.selectedSwitch(null);
              }
            });
          }
          vm.dataToppage(null);
        });
      }).always(() => vm.$blockui("clear"));

      // 会社の締めを取得する - Lấy closure company
      service.getClosure().done((data: any) => {
        vm.lstClosure(data);
      });
      $('#content-top').resizable({
        grid: [10000, 1]
    });
      _.extend(window, { vm: self });
    }

    mounted() {
      const vm = this;
      vm.selectedSwitch.subscribe(function (value) {
        character.save("cache", new Cache(vm.closureSelected(), value));
        nts.uk.ui.windows.setShared("cache", new Cache(vm.closureSelected(), value));
        vm.callApiTopPage(vm);
      });

      vm.closureSelected.subscribe(function (value) {
        vm.selectedSwitch.valueHasMutated();
      });

      vm.reloadInterval.subscribe((data: any) => {
        const minutes = vm.getMinutes(data);
        const miliSeconds = minutes * MINUTESTOMILISECONDS;
        if (data !== 0) {
          setInterval(() => {
            vm.callApiTopPage(vm);
          }, miliSeconds);
        }
      });
    }

    callApiTopPage(vm: any) {
      const transferData = __viewContext.transferred.value;
      const code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
      const fromScreen = transferData && transferData.screen ? transferData.screen : "other";
      let topPageSetting: any;
      vm.$blockui('grayout');
      vm.$ajax("com", API.getSetting).then((res: any) => {
        topPageSetting = res;
        const param = {
          topPageSetting: topPageSetting,
          fromScreen: fromScreen,
          topPageCode: code,
        };
        vm.layoutDisplayType(null);
        vm.$ajax("com", API.getDisplayTopPage, param).then((data: DataTopPage) => {
          if (data.displayTopPage) {
          vm.layoutDisplayType(data.displayTopPage.layoutDisplayType);
          }
          vm.getToppage(data);
        });
      })
      .always(() => vm.$blockui("clear"));
    }

    onClickReload() {
      const vm = this;
      this.callApiTopPage(vm);
    }

    getToppage(data: DataTopPage) {
      const vm = this;
      const origin: string = window.location.origin;
      if (data.displayTopPage && data.displayTopPage.layoutDisplayType !== 0 && data.displayTopPage.layout2) {
        vm.isShowButtonRefresh(true);
      }
      if (vm.topPageSetting.menuClassification !== MenuClassification.TopPage) {
        if (data.standardMenu.url) {
          // show standardmenu
          const res = "/" + data.standardMenu.url.split("web/")[1];
          const topPageUrl = "/view/ccg/008/a/index.xhtml";
          if (res && topPageUrl !== res.trim()) {
            if (_.includes(data.standardMenu.url, ".at.")) {
              nts.uk.request.jump("at", res);
            } else {
              nts.uk.request.jump(res);
            }
          }
        }
        // show toppage
      } else {
        const layout1 = data.displayTopPage.layout1;
        const layout2 = data.displayTopPage.layout2;
        const layout3 = data.displayTopPage.layout3;
        if (layout1) {
          vm.paramIframe1(data.displayTopPage);
          vm.visible(true)
        }
        if (layout2) {
          _.each(layout2, (item: WidgetSettingDto) => {
            if([0,1,2,3,4].indexOf(item.widgetType) > -1) {
              vm.isShowSwitch(true);
            }
            if (item.widgetType === 1) {
              vm.isShowClosure(true);
            }
          });
          vm.paramWidgetLayout2(layout2);
        }
        if (layout3) {
          _.each(layout3, (item: WidgetSettingDto) => {
            if([0,1,2,3,4].indexOf(item.widgetType) > -1) {
              vm.isShowSwitch(true);
            }
          
            if (item.widgetType === 1) {
              vm.isShowClosure(true);
            }
          });
          vm.paramWidgetLayout3(layout3);
        }
      }
    }

    openScreenE() {
      const self = this;
      nts.uk.ui.windows.setShared("DataFromScreenA", self.reloadInterval());
      nts.uk.ui.windows.sub
        .modal("/view/ccg/008/e/index.xhtml")
        .onClosed(() => {
          const result = nts.uk.ui.windows.getShared("DataFromScreenE");
          self.reloadInterval(result);
        });
    }
    
    getMinutes(value: number) {
      let minutes = 0;
      switch (value) {
        case 0:
          minutes = 0;
          break;
        case 1:
          minutes = 1;
          break;
        case 2:
          minutes = 5;
          break;
        case 3:
          minutes = 10;
          break;
        case 4:
          minutes = 20;
          break;
        case 5:
          minutes = 30;
          break;
        case 6:
          minutes = 40;
          break;
        case 7:
          minutes = 50;
          break;
        case 8:
          minutes = 60;
          break;
      }
      return minutes;
    }
  }
  export class Cache {
    closureId: number;
    currentOrNextMonth: number;
    constructor(closureId: number, currentOrNextMonth: number) {
      this.closureId = closureId;
      this.currentOrNextMonth = currentOrNextMonth;
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

  enum LayoutType {
    LAYOUT_TYPE_0 = 0,
    LAYOUT_TYPE_1 = 1,
    LAYOUT_TYPE_2 = 2,
    LAYOUT_TYPE_3 = 3,
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
    layoutDisplayType: number;
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
