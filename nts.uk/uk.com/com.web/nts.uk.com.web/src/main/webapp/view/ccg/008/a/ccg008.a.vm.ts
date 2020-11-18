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
    layoutType: KnockoutObservable<number> = ko.observable(0);
    isShowSwitch: KnockoutObservable<boolean> = ko.observable(false);
    isShowButtonRefresh: KnockoutObservable<boolean> = ko.observable(false);
    isShowButtonSetting: KnockoutObservable<boolean> = ko.observable(false);
   
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
                            if(endDate.add(self.topPageSetting.switchingDate).isSameOrAfter(new Date)){
                              self.selectedSwitch(1);
                            } else {
                              self.selectedSwitch(2);
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
          service.getTopPage(param).then((data: DataTopPage) => {
            self.getToppage(data);
            self.layoutType(data.displayTopPage.layoutDisplayType);
            $( ".content-top" ).resizable();
            self.contentF1 = $('#F1');
            self.contentF2 = $('#F2');
            if (self.layoutType() === LayoutType.LAYOUT_TYPE_0) {
              self.isVisiableContentF2(false);
              self.isVisiableContentF3(false);
            } else if (self.layoutType() === LayoutType.LAYOUT_TYPE_1) {
              self.isVisiableContentF3(false);
              const tcontentF1 = self.contentF1.clone();
              const tcontentF2 = self.contentF2.clone();

              if(!self.contentF2.is(':empty')) {
                self.contentF1.replaceWith(tcontentF2);
                self.contentF2.replaceWith(tcontentF1);
              }
            } else {
              self.isVisiableContentF3(false);
            }
          });
          
        });
      });
        
      // 会社の締めを取得する - Lấy closure company
      service.getClosure().done((data: any) => {
        self.lstClosure(data);
        //   service.getTopPageByCode(fromScreen, self.topPageCode()).done((data: model.LayoutAllDto) => {
        //     self.dataSource(data);
        //     dfd.resolve();
        // }); 
      });
    }
    mounted(){
      const vm = this;
      vm.selectedSwitch.subscribe(function(value){
          character.save('cache', new Cache(vm.closureSelected(), value));
          nts.uk.ui.windows.setShared('cache', new Cache(vm.closureSelected(), value));
          const transferData = __viewContext.transferred.value;
          const fromScreen = transferData && transferData.screen ? transferData.screen : "other";
      });
                    
      vm.closureSelected.subscribe(function(value){
        vm.selectedSwitch.valueHasMutated();
      });
        
      vm.reloadInterval.subscribe((data: any) => {
        const minutes = vm.getMinutes(data);
        const miliSeconds = minutes * MINUTESTOMILISECONDS;
        if(data !== 0) {
          setInterval(() => {
            vm.callApiTopPage(self);
          }, miliSeconds);
        }
      });
    }

    callApiTopPage(self: any){
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
          self.getToppage(data);
        });
      });
    }
      
    onClickReload(){
      let self = this;
      this.callApiTopPage(self);
    }

    getToppage(data: DataTopPage){
      const self = this;
      const origin: string = window.location.origin;
      if(self.layoutType() !== 0 && data.displayTopPage.layout2) {
        self.isShowButtonRefresh(true);
      }
        if(self.topPageSetting.menuClassification !== MenuClassification.TopPage) {
          if (data.standardMenu.url) {// show standardmenu
            const res = "/" + data.standardMenu.url.split("web/")[1];
            const topPageUrl = "/view/ccg/008/a/index.xhtml";
            if (res && topPageUrl != res.trim()) { 
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
              self.isShowUrlLayout1(true);
              self.urlIframe1(data.displayTopPage.urlLayout1);
            } else {
              const lstFileId = ko.observableArray([]);
                _.each(data.displayTopPage.layout1, (item: any) => {
                  let fileId = item.fileId;
                  lstFileId().push(fileId);
                });
                const param = {
                  lstFileId: lstFileId()
                };
                service.extractFile(param).then((data: any) => {
                  const mappedList: any =
                    _.map(data, (item:any) => {
                      const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                      const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                        return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>`};
                    });
                  self.lstHtml(mappedList);
                });
            }
          }
          let dataLayout2: ItemLayout[] = [];
          let dataLayout3: ItemLayout[] = [];
          if (layout2) {
            _.each(layout2, (item: WidgetSettingDto) => {
              if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                self.isShowSwitch(true);
              }
              if(item.widgetType === 1) {
                self.isShowClosure(true);
              }
              let itemLayout: ItemLayout = new ItemLayout();
              itemLayout.url = origin + self.getUrl(item.widgetType);
              itemLayout.html = `<iframe style="width:450px" src=  ${itemLayout.url}/>`; 
              itemLayout.order = item.order;
              dataLayout2.push(itemLayout);
            });
            dataLayout2 = _.orderBy(dataLayout2, ["order"], ["asc"]);
            self.lstWidgetLayout2(dataLayout2);
          }
          
          if (layout3) {
            _.each(layout3, (item: WidgetSettingDto) => {
              if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                self.isShowSwitch(true);
              }
              if(item.widgetType === 1) {
                self.isShowClosure(true);
              }
              let itemLayout: ItemLayout = new ItemLayout();
              itemLayout.url = origin + self.getUrl(item.widgetType);
              itemLayout.html = `<iframe style="width:450px" src= "${itemLayout.url}"'/>`;
              itemLayout.order = item.order;
              dataLayout3.push(itemLayout);
            });
            dataLayout3 = _.orderBy(dataLayout3, ["order"], ["asc"]);
            self.lstWidgetLayout3(dataLayout3);
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
      switch(type) {
        case 0:
          return '/nts.uk.at.web/view/ktg/005/a/index.xhtml';
        case 1:
          return '/nts.uk.at.web/view/ktg/001/a/index.xhtml';
        case 2:
          return '/nts.uk.at.web/view/ktg/004/a/index.xhtml';
        case 3:
          return '/nts.uk.at.web/view/ktg/026/a/index.xhtml';
        case 4:
          return '/nts.uk.at.web/view/ktg/027/a/index.xhtml';
        case 5:
          return '/nts.uk.at.web/view/kdp/001/a/index.xhtml';
        case 6:
          return '/nts.uk.at.web/view/ktg/031/a/index.xhtml';
        case 7:
          return '/view/ccg/005/a/index.xhtml';
      }
    }
    getMinutes(value: number) {
      switch(value) {
        case 0:
          return 0;
        case 1:
          return 1;
        case 2:
          return 5;
        case 3:
          return 10;
        case 4:
          return 20;
        case 5:
          return 30;
        case 6:
          return 40;
        case 7: 
          return 50;
        case 8:
          return 60;
      }
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
