/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.f.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    isVisiableContentF2: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF3: KnockoutObservable<boolean> = ko.observable(true);
    topPageCd: KnockoutObservable<string> = ko.observable('');
    lstWidgetLayout2: KnockoutObservableArray<any> = ko.observableArray([]);
    lstWidgetLayout3: KnockoutObservableArray<any> = ko.observableArray([]);
    isShowUrlLayout1: KnockoutObservable<boolean> = ko.observable(false);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    lstHtml: KnockoutObservableArray<string> = ko.observableArray([]);
    contentF1: JQuery;
    contentF2: JQuery;
    contentF3: JQuery;
    created(params: any) {
      const vm =this;
      if (params.topPageModel && params.topPageModel.topPageCode) {
        vm.topPageCd(params.topPageModel.topPageCode);
      }
      vm.contentF1 = $('#F1');
      vm.contentF2 = $('#F2');

      if (params.selectedId === LayoutType.LAYOUT_TYPE_1) {
        vm.isVisiableContentF2(false);
        vm.isVisiableContentF3(false);
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_2) {
        vm.isVisiableContentF3(false);
        let tcontentF1 = vm.contentF1.clone();
        let tcontentF2 = vm.contentF2.clone();

        if(!vm.contentF2.is(':empty')) {
          vm.contentF1.replaceWith(tcontentF2);
          vm.contentF2.replaceWith(tcontentF1);
        }
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_3) {
        vm.isVisiableContentF3(false);
      }

      vm.$ajax('/toppage/getDisplayTopPage'+ '/' + vm.topPageCd()).then((result: any) => {
        vm.getToppage(result);
      })
    }

    getToppage(data: DisplayInTopPage){
      let vm = this;
      let origin: string = window.location.origin;
          let layout1 = data.layout1;
          let layout2 = data.layout2;
          let layout3 = data.layout3;
          if (layout1) {
            if(data.urlLayout1){
              vm.isShowUrlLayout1(true);
              vm.urlIframe1(data.urlLayout1);
            } else {
              let lstFileId = ko.observableArray([]);
                _.each(data.layout1, (item: any) => {
                  let fileId = item.fileId;
                  lstFileId().push(fileId);
                });
                let param = {
                  lstFileId: lstFileId()
                }
                vm.$ajax('sys/portal/createflowmenu/extractListFileId',param).then((res: any) => {
                  let mappedList: any =
                    _.map(res, (item:any) => {
                      let width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                      let height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                        return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>`};
                    });
                  vm.lstHtml(mappedList);
                })
            }
          }
          let dataLayout2: any;
          let dataLayout3: any;
          if (layout2) {
            _.each(layout2, (item: WidgetSettingDto) => {
             
              let itemLayout: any;
              itemLayout.url = origin + vm.getUrl(item.widgetType);
              itemLayout.html = `<iframe src=  ${itemLayout.url}/>`; 
              itemLayout.order = item.order;
              dataLayout2.push(itemLayout);
            });
            dataLayout2 = _.orderBy(dataLayout2, ["order"], ["asc"]);
            vm.lstWidgetLayout2(dataLayout2);
          }
          
          if (layout3) {
            _.each(layout3, (item: WidgetSettingDto) => {
              
              let itemLayout: any;
              itemLayout.url = origin + vm.getUrl(item.widgetType)
              itemLayout.html = `<iframe src=  ${itemLayout.url}/>`; 
              itemLayout.order = item.order;
              dataLayout3.push(itemLayout);
            });
            dataLayout3 = _.orderBy(dataLayout3, ["order"], ["asc"]);
            vm.lstWidgetLayout3(dataLayout3);
          }
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

    close() {
      nts.uk.ui.windows.close();
    }
  }

  enum LayoutType {
    LAYOUT_TYPE_1 = 0,
    LAYOUT_TYPE_2 = 1,
    LAYOUT_TYPE_3 = 2,
    LAYOUT_TYPE_4 = 3,
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
 }; 
}