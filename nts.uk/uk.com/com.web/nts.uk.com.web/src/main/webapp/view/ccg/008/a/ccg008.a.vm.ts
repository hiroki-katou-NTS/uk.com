module nts.uk.com.view.ccg008.a.screenModel {

  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import ntsFile = nts.uk.request.file; 
  import character = nts.uk.characteristics;
  const MINUTESTOMILISECONDS = 60000;
  @bean()
  export class ViewModel extends ko.ViewModel {
    topPageCode: KnockoutObservable<string> = ko.observable('');
    isStart: boolean;
    dateSwitch: KnockoutObservableArray<any>  = ko.observableArray([
                                                  { code: '1', name: nts.uk.resource.getText('CCG008_14')},
                                                  { code: '2', name: nts.uk.resource.getText('CCG008_15')}
                                                ]);
    selectedSwitch: KnockoutObservable<any> = ko.observable(null);
    switchVisible: KnockoutObservable<boolean>;
    isVisiableContentF1: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF2: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF3: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF4: KnockoutObservable<boolean> = ko.observable(true);
    contentF1: JQuery;
    contentF2: JQuery;
    contentF3: JQuery;
    isShowClosure: KnockoutObservable<boolean> = ko.observable(false);
    closureSelected: KnockoutObservable<number> = ko.observable(1);
    lstClosure: KnockoutObservableArray<ItemCbbModel> = ko.observableArray([]);
    reloadInterval: KnockoutObservable<number> = ko.observable(0);
    lstWidgetLayout2: KnockoutObservableArray<ItemLayout> = ko.observableArray([]);
    lstWidgetLayout3: KnockoutObservableArray<ItemLayout> = ko.observableArray([]);
    isShowUrlLayout1: KnockoutObservable<boolean> = ko.observable(false);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    lstHtml: KnockoutObservableArray<string> = ko.observableArray([]);
    topPageSetting: any;
    isShowSwitch: KnockoutObservable<boolean> = ko.observable(false);
    isShowButtonRefresh: KnockoutObservable<boolean> = ko.observable(false);
    isShowButtonSetting: KnockoutObservable<boolean> = ko.observable(false);
    dataToppage: KnockoutObservable<DataTopPage> = ko.observable(null);

    created(){
      var self = this;
      var transferData = __viewContext.transferred.value;
      var code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
      var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
      service.getLoginUser().done(user => {
        service.getSetting().done(res => {
          if(res.reloadInterval){
            self.reloadInterval(res.reloadInterval);
          }
          if(user || res) {
            self.isShowButtonSetting(true);
          }
          self.topPageSetting = res;
          //var fromScreen = "login"; 
          if(fromScreen == "login"){
            service.getCache().done((data: any) => {
                character.save('cache', data).done(() => {
                    self.topPageCode(code);
                    character.restore('cache').done((obj: any)=>{
                        if(obj){
                            const endDate = moment.utc(obj.endDate, 'YYYY/MM/DD');
                            if(endDate.add(self.topPageSetting.switchingDate).isSameOrAfter(moment.utc(new Date(), 'YYYY/MM/DD'))){
                              self.selectedSwitch(1);
                            } else {
                              self.selectedSwitch(2);
                              obj.currentOrNextMonth = 2;
                            }
                            self.closureSelected(obj.closureId)
                            nts.uk.ui.windows.setShared('cache', obj);
                        } else {
                            self.closureSelected(1);
                            self.selectedSwitch(null);
                        }
                    }); 
                });
            });  
          } else {
            // get combobox and switch button
            character.restore('cache').done((obj: any)=>{
                if(obj){
                  if(obj.currentOrNextMonth){
                      self.selectedSwitch(obj.currentOrNextMonth);
                  }else{
                      self.selectedSwitch(null);    
                  }
                  self.closureSelected(obj.closureId)
                  nts.uk.ui.windows.setShared('cache', obj);
                } else {
                  self.closureSelected(1);
                  self.selectedSwitch(null);
                }
            });    
          }
          const param = {
            topPageSetting: self.topPageSetting,
            fromScreen: fromScreen,
            topPageCode: code
          };
          self.dataToppage(null);
          self.callApiTopPage(self);
          
        });
      });
        
      // 会社の締めを取得する - Lấy closure company
      service.getClosure().done((data: any) => {
        self.lstClosure(data);
      });
    }

    mounted(){
      const vm = this;
      vm.selectedSwitch.subscribe(function(value){
          character.save('cache', new Cache(vm.closureSelected(), value));
          nts.uk.ui.windows.setShared('cache', new Cache(vm.closureSelected(), value));
          vm.callApiTopPage(vm);
      });

      vm.closureSelected.subscribe(function(value){
        vm.selectedSwitch.valueHasMutated();
      });

      vm.reloadInterval.subscribe((data: any) => {
        const minutes = vm.getMinutes(data);
        const miliSeconds = minutes * MINUTESTOMILISECONDS;
        if(data !== 0) {
          setInterval(() => {
            vm.callApiTopPage(vm);
          }, miliSeconds);
        }
      });
    }

    callApiTopPage(vm: any){
      const transferData = __viewContext.transferred.value;
      const code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
      const fromScreen = transferData && transferData.screen ? transferData.screen : "other";
      let topPageSetting: any;
      service.getSetting().done(res => {
        topPageSetting = res;
        const param = {
          topPageSetting: topPageSetting,
          fromScreen: fromScreen,
          topPageCode: code
        };
        service.getTopPage(param).then((data: DataTopPage) => {
          vm.getToppage(data);
            $( ".content-top" ).resizable();
            vm.isVisiableContentF1(true);
            vm.isVisiableContentF2(true);
            vm.isVisiableContentF3(true);
            vm.isVisiableContentF4(true);
            if (data.displayTopPage.layoutDisplayType === LayoutType.LAYOUT_TYPE_0) {
              vm.isVisiableContentF2(false);
              vm.isVisiableContentF3(false);
              vm.isVisiableContentF4(false);
            } else if (data.displayTopPage.layoutDisplayType === LayoutType.LAYOUT_TYPE_1) {
              vm.isVisiableContentF3(false);
              vm.isVisiableContentF2(false);
            } else if (data.displayTopPage.layoutDisplayType === LayoutType.LAYOUT_TYPE_2) {
              vm.isVisiableContentF3(false);
              vm.isVisiableContentF4(false);
            } else {
              vm.isVisiableContentF4(false);
            }
          vm.getToppage(data);
        });
      });
    }

    onClickReload(){
      const vm = this;
      this.callApiTopPage(vm);
    }

    getToppage(data: DataTopPage){
      const vm = this;
      const origin: string = window.location.origin;
      if(data.displayTopPage.layoutDisplayType !== 0 && data.displayTopPage.layout2) {
        vm.isShowButtonRefresh(true);
      }
        if(vm.topPageSetting.menuClassification !== MenuClassification.TopPage) {
          if (data.standardMenu.url) {// show standardmenu
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
            if(data.displayTopPage.urlLayout1){
              vm.isShowUrlLayout1(true);
              vm.urlIframe1(data.displayTopPage.urlLayout1);
            } else {
              const lstFileId = ko.observableArray([]);
                _.each(data.displayTopPage.layout1, (item: any) => {
                  const fileId = item.fileId;
                  lstFileId().push(fileId);
                });
                const param = {
                  lstFileId: lstFileId()
                };
                service.extractFile(param).then((res: any) => {
                  const mappedList: any =
                    _.map(res, (item:any) => {
                      const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                      const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                        return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>`};
                    });
                    vm.lstHtml(mappedList);
                });
            }
          }
          let dataLayout2: ItemLayout[] = [];
          let dataLayout3: ItemLayout[] = [];
          vm.lstWidgetLayout2([]);
          vm.lstWidgetLayout3([]);
          if (layout2) {
            _.each(layout2, (item: WidgetSettingDto) => {
              if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                vm.isShowSwitch(true);
              }
              if(item.widgetType === 1) {
                vm.isShowClosure(true);
              }
              const itemLayout: ItemLayout = new ItemLayout();
              itemLayout.url = origin + vm.getUrl(item.widgetType);
              itemLayout.html = `<iframe style="width:450px" src=  ${itemLayout.url}/>`; 
              itemLayout.order = item.order;
              dataLayout2.push(itemLayout);
            });
            dataLayout2 = _.orderBy(dataLayout2, ["order"], ["asc"]);
            vm.lstWidgetLayout2(dataLayout2);
          }
          if (layout3) {
            _.each(layout3, (item: WidgetSettingDto) => {
              if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                vm.isShowSwitch(true);
              }
              if(item.widgetType === 1) {
                vm.isShowClosure(true);
              }
              const itemLayout: ItemLayout = new ItemLayout();
              itemLayout.url = origin + vm.getUrl(item.widgetType);
              itemLayout.html = `<iframe style="width:450px" src= "${itemLayout.url}"'/>`;
              itemLayout.order = item.order;
              dataLayout3.push(itemLayout);
            });
            dataLayout3 = _.orderBy(dataLayout3, ["order"], ["asc"]);
            vm.lstWidgetLayout3(dataLayout3);
          }
        }
    }
      
      
    openScreenE() {
      const self = this;
      nts.uk.ui.windows.setShared('DataFromScreenA',self.reloadInterval());
      nts.uk.ui.windows.sub.modal("/view/ccg/008/e/index.xhtml").onClosed(() => {
        const result = nts.uk.ui.windows.getShared('DataFromScreenE');
        self.reloadInterval(result);
      });
    }

    getUrl(type: any){
      let url = '';
      switch(type) {
        case 0:
          url = '/nts.uk.at.web/view/ktg/005/a/index.xhtml';
          break;
        case 1:
          url = '/nts.uk.at.web/view/ktg/001/a/index.xhtml';
          break;
        case 2:
          url = '/nts.uk.at.web/view/ktg/004/a/index.xhtml';
          break;
        case 3:
          url = '/nts.uk.at.web/view/ktg/026/a/index.xhtml';
          break;
        case 4:
          url = '/nts.uk.at.web/view/ktg/027/a/index.xhtml';
          break;
        case 5:
          url = '/nts.uk.at.web/view/kdp/001/a/index.xhtml';
          break;
        case 6:
          url = '/nts.uk.at.web/view/ktg/031/a/index.xhtml';
          break;
        case 7:
          url = '/view/ccg/005/a/index.xhtml';
          break;
      }
      return url;
    }
    getMinutes(value: number) {
      let minutes = 0;
      switch(value) {
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
          minutes = 10
          break;
        case 4:
          minutes = 20
          break;
        case 5:
          minutes = 30
          break;
        case 6:
          minutes = 40
          break;
        case 7: 
          minutes = 50
          break;
        case 8:
          minutes = 60
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
    constructor(init?: Partial<StandardMenuDto>) {
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
    GroupCompanyMenu  = 5,
    /**6:カスタマイズ */
    Customize = 6,
    /**7:オフィスヘルパー稟議書*/
    OfficeHelper = 7,
    /**8:トップページ*/
    TopPage = 8,
    /**9:スマートフォン*/
    SmartPhone = 9
  }
}
