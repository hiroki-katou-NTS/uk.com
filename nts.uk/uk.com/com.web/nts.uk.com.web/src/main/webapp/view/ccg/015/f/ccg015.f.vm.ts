/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.f {

  @bean()
  export class ScreenModel extends ko.ViewModel {
    isVisiableContentF2: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF3: KnockoutObservable<boolean> = ko.observable(true);
    topPageCd: KnockoutObservable<string> = ko.observable(null);
    lstWidgetLayout2: KnockoutObservableArray<any> = ko.observableArray([]);
    lstWidgetLayout3: KnockoutObservableArray<any> = ko.observableArray([]);
    isShowUrlLayout1: KnockoutObservable<boolean> = ko.observable(false);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    lstHtml: KnockoutObservableArray<string> = ko.observableArray([]);
    $contentF1: JQuery;
    $contentF2: JQuery;

    created(params: any) {
      const vm = this;
      if (params.topPageModel) {
        vm.topPageCd(params.topPageModel.topPageCode);
      }
      vm.$contentF1 = $('#F1');
      vm.$contentF2 = $('#F2');

      if (params.selectedId === LayoutType.LAYOUT_TYPE_1) {
        vm.isVisiableContentF2(false);
        vm.isVisiableContentF3(false);
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_2) {
        vm.isVisiableContentF3(false);
        const tcontentF1 = vm.$contentF1.clone();
        const tcontentF2 = vm.$contentF2.clone();
        if (!vm.$contentF2.is(':empty')) {
          vm.$contentF1.replaceWith(tcontentF2);
          vm.$contentF2.replaceWith(tcontentF1);
        }
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_3) {
        vm.isVisiableContentF3(false);
      }

      vm.$blockui('grayout');
      vm.$ajax(`/toppage/getDisplayTopPage/${vm.topPageCd()}`)
        .then((result: any) => vm.getToppage(result))
        .always(() => vm.$blockui('clear'));
    }

    private getToppage(data: DisplayInTopPageDto) {
      const vm = this;
      if (data.layout1) {
        vm.getLayout1(data);
      }
      if (data.layout2) {
        vm.getLayout2(data);
      }
      if (data.layout3) {
        vm.getLayout3(data);
      }
    }

    private getLayout1(data: DisplayInTopPageDto) {
      const vm = this;
      if (data.urlLayout1) {
        vm.isShowUrlLayout1(true);
        vm.urlIframe1(data.urlLayout1);
      } else {
        const lstFileId: any[] = [];
        _.each(data.layout1, (item: any) => lstFileId.push(item.fileId));
        const param = {
          lstFileId: lstFileId
        };

        vm.$blockui('grayout');
        vm.$ajax('sys/portal/createflowmenu/extractListFileId', param)
          .then((res: any) => {
            const mappedList: any = _.map(res, (item: any) => {
              const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
              const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
              return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>` };
            });
            vm.lstHtml(mappedList);
          })
          .always(() => vm.$blockui('clear'));
      }
    }

    private getLayout2(data: DisplayInTopPageDto) {
      const vm = this;
      const origin: string = window.location.origin;
      const dataLayout2: any[] = _.chain(data.layout2)
        .map((item: WidgetSettingDto) => {
          let itemLayout: any;
          itemLayout.url = origin + vm.getUrl(item.widgetType);
          itemLayout.html = `<iframe src=  ${itemLayout.url}/>`;
          itemLayout.order = item.order;
          return itemLayout;
        })
        .orderBy(["order"], ["asc"])
        .value();
      vm.lstWidgetLayout2(dataLayout2);
    }

    private getLayout3(data: DisplayInTopPageDto) {
      const vm = this;
      const origin: string = window.location.origin;
      const dataLayout3: any[] = _.chain(data.layout3)
        .map((item: WidgetSettingDto) => {
          let itemLayout: any;
          itemLayout.url = origin + vm.getUrl(item.widgetType)
          itemLayout.html = `<iframe src=  ${itemLayout.url}/>`;
          itemLayout.order = item.order;
          return itemLayout;
        })
        .orderBy(["order"], ["asc"])
        .value();
      vm.lstWidgetLayout3(dataLayout3);
    }

    private getUrl(type: any) {
      switch (type) {
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
        default:
          return null;
      }
    }

    close() {
      const vm = this;
      vm.$window.close();
    }
  }

  enum LayoutType {
    LAYOUT_TYPE_1 = 0,
    LAYOUT_TYPE_2 = 1,
    LAYOUT_TYPE_3 = 2,
    LAYOUT_TYPE_4 = 3,
  }

  export class DataTopPageDto {
    displayTopPage: DisplayInTopPageDto;
    menuClassification: number;
    standardMenu: StandardMenuDto;

    constructor(init?: Partial<DataTopPageDto>) {
      $.extend(this, init);
    }
  }

  export class DisplayInTopPageDto {
    layout1: Array<FlowMenuOutputCCG008>;
    layout2: Array<WidgetSettingDto>;
    layout3: Array<WidgetSettingDto>;
    urlLayout1: string;
    layoutDisplayType: number;

    constructor(init?: Partial<DisplayInTopPageDto>) {
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

}