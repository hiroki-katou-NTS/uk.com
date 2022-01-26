/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.f {

  @bean()
  export class ScreenModel extends ko.ViewModel {
    topPageCd: KnockoutObservable<string> = ko.observable(null);
    paramWidgetLayout1: KnockoutObservableArray<WidgetSettingDto> = ko.observableArray([]);
    paramWidgetLayout2: KnockoutObservableArray<WidgetSettingDto> = ko.observableArray([]);
    paramWidgetLayout3: KnockoutObservableArray<WidgetSettingDto> = ko.observableArray([]);
    paramIframe1: KnockoutObservable<DisplayInTopPageDto> = ko.observable();
    layoutDisplayType: KnockoutObservable<number> = ko.observable(null);
    visible: KnockoutObservable<boolean> = ko.observable(false);
    isNoContent : KnockoutObservable<boolean> = ko.observable(false);
    isNoContentLayout1 : KnockoutObservable<boolean> = ko.observable(false);
    isNoContentLayout2 : KnockoutObservable<boolean> = ko.observable(false);
    isNoContentLayout3 : KnockoutObservable<boolean> = ko.observable(false);
    frameLayout1: KnockoutObservable<number> = ko.observable(1);
    frameLayout2: KnockoutObservable<number> = ko.observable(0);
    frameLayout3: KnockoutObservable<number> = ko.observable(2);
    created(params: any) {
      const vm = this;
      if (params.topPageModel) {
        vm.topPageCd(params.topPageModel.topPageCode);
      }

      vm.$blockui('grayout');
      vm.$ajax(`/toppage/getDisplayTopPage/${vm.topPageCd()}`)
        .then((result: any) =>{
          vm.layoutDisplayType(result.layoutDisplayType);
          vm.getToppage(result);
        })
        .always(() => vm.$blockui('clear'));
    }

    private getToppage(data: DisplayInTopPageDto) {
      const vm = this;
      vm.frameLayout1(data.topPage.frameLayout1);
      vm.frameLayout2(data.topPage.frameLayout2);
      vm.frameLayout3(data.topPage.frameLayout3);
      if (!_.isEmpty(data.listLayout[0]) || !_.isNil(data.urlLayout1)) {
        vm.getLayout1(data);
      }
      if (!_.isEmpty(data.listLayout[1]) || !_.isNil(data.urlLayout1)) {
        vm.getLayout2(data);
      }
      if (!_.isEmpty(data.listLayout[2]) || !_.isNil(data.urlLayout1)) {
        vm.getLayout3(data);
      }
      if (_.isEmpty(data.listLayout[0]) && _.isEmpty(data.listLayout[1]) && _.isEmpty(data.listLayout[2]) && _.isNil(data.urlLayout1)) {
        vm.isNoContent(true);
      }
      if (_.isEmpty(data.listLayout[0]) && _.isNil(data.urlLayout1)) {
        vm.isNoContentLayout1(true);
      }
      if(_.isEmpty(data.listLayout[1]) && _.isNil(data.urlLayout1)) {
        vm.isNoContentLayout2(true);
      }
      if(_.isEmpty(data.listLayout[2]) && _.isNil(data.urlLayout1)) {
        vm.isNoContentLayout3(true);
      }

      // If 3 widgets are displayed
      if (!vm.visible()) {
        $(".contents_layout_ccg015").css("overflow-x", "auto");
      } else {
        $(".contents_layout_ccg015").css("overflow-x", "");
      }
    }

    private getLayout1(data: DisplayInTopPageDto) {
      const vm = this;
      if (vm.frameLayout1() !== 0) {
        vm.paramWidgetLayout1(data.listLayout[0]);
      } else {
        data.layout1 = data.listLayout[0] || [];
        vm.paramIframe1(data);
        vm.visible(true);
      }
      vm.switchLayoutType($(".contents_1"), vm.frameLayout1());
    }

    private getLayout2(data: DisplayInTopPageDto) {
      const vm = this;
      if (vm.frameLayout2() !== 0) {
        vm.paramWidgetLayout2(data.listLayout[1]);
      } else {
        data.layout1 = data.listLayout[1] || [];
        vm.paramIframe1(data);
        vm.visible(true);
      }
      vm.switchLayoutType($(".contents_2"), vm.frameLayout2());
    }

    private getLayout3(data: DisplayInTopPageDto) {
      const vm = this;
      if (vm.frameLayout3() !== 0) {
        vm.paramWidgetLayout3(data.listLayout[2]);
      } else {
        data.layout1 = data.listLayout[2] || [];
        vm.paramIframe1(data);
        vm.visible(true);
      }
      vm.switchLayoutType($(".contents_3"), vm.frameLayout3());
    }

    // 0 = flowmenu, other = widget
    private switchLayoutType(el: JQuery, layoutType: number) {
      const oldClass = layoutType === 0 ? "with-widget" : "with-flowmenu";
      const newClass = layoutType === 0 ? "with-flowmenu" : "with-widget";
      el.removeClass(oldClass);
      el.addClass(newClass);
    }

    close() {
      const vm = this;
      vm.$window.close();
    }
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
    listLayout: any[][];
    urlLayout1: string;
    layoutDisplayType: number;
    topPage: TopPageDto;

    constructor(init?: Partial<DisplayInTopPageDto>) {
      $.extend(this, init);
    }
  }

  export interface TopPageDto {
    cid: string;
    topPageCode: string;
    topPageName: string;
    layoutDisp: number;
    frameLayout1: number;
    frameLayout2: number;
    frameLayout3: number;
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