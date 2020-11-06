/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.d.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    flowMenuSelectedCode: KnockoutObservable<string> = ko.observable('');
    toppageSelectedCode: KnockoutObservable<string> = ko.observable('');
    listFlowMenu: KnockoutObservableArray<FlowMenuItem> = ko.observableArray<FlowMenuItem>([]);
    listTopPagePart: KnockoutObservableArray<TopPagePartItem> = ko.observableArray<TopPagePartItem>([]);
    columnsFlowMenu: KnockoutObservableArray<any> = ko.observableArray([]);
    columnsTopPagePart: KnockoutObservableArray<any> = ko.observableArray([]);
    itemList: KnockoutObservableArray<ItemModel>;
    isRequired: KnockoutObservable<boolean> = ko.observable(true);
    contentFlowMenu: KnockoutObservable<boolean> = ko.observable(true);
    contentTopPagePart: KnockoutObservable<boolean> = ko.observable(false);
    contentUrl: KnockoutObservable<boolean> = ko.observable(false);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    urlIframe2: KnockoutObservable<string> = ko.observable('');
    urlIframe3: KnockoutObservable<string> = ko.observable('');
    topPageCd: KnockoutObservable<string> = ko.observable('');
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    topPageCode: KnockoutObservable<string> = ko.observable('');
    layoutNo: KnockoutObservable<number> = ko.observable(0);
    layoutType: KnockoutObservable<number> = ko.observable(-1);
    flowMenuCd: KnockoutObservable<string> = ko.observable('');
    flowMenuUpCd: KnockoutObservable<string> = ko.observable('');
    url: KnockoutObservable<string> = ko.observable('');
    params: any = {};

    created(params: any) {
      const vm = this;
      vm.topPageCd(params.topPageModel.topPageCode);
      vm.itemList = ko.observableArray([
          new ItemModel(0, 'フローメニュー'),
          new ItemModel(1, 'フローメニュー（アップロード）'),
          new ItemModel(2, '外部URL')
      ]);
      vm.columnsFlowMenu = ko.observableArray([
        { headerText: this.$i18n('CCG015_68').toString(), width: "50px", key: 'flowCode'},
        { headerText: this.$i18n('CCG015_69').toString(), width: "260px", key: 'flowName'}
      ]);
      vm.columnsTopPagePart = ko.observableArray([
        { headerText: this.$i18n('CCG015_68').toString(), width: "50px", key: 'flowCode'},
        { headerText: this.$i18n('CCG015_69').toString(), width: "260px", key: 'flowName'}
      ]);
      vm.params = params;
      vm.layoutType.subscribe(value => {
        if (value === 0) {
          vm.changeLayout();
          vm.contentFlowMenu(true);
          vm.contentTopPagePart(false);
          vm.contentUrl(false);
        } else if (value === 1) { 
          vm.changeLayout();
          vm.contentTopPagePart(true);
          vm.contentFlowMenu(false);
          vm.contentUrl(false);
        } else {
          vm.changeLayout();
          vm.contentUrl(true);
          vm.contentTopPagePart(false);
          vm.contentFlowMenu(false);
        }
      });
    }

    mounted() {
      const vm = this;
      vm.checkDataLayout(vm.params);
    }

    checkDataLayout(params: any) {
      const vm = this;
      let topPageCode: string = '';
      let layoutNo: number = 0;
      if (params) {
        if (params.topPageModel && params.topPageModel.topPageCode) {
          vm.topPageCode(params.topPageModel.topPageCode);
          topPageCode = params.topPageModel.topPageCode;
        }
        if (params.layoutNo && params.topPageModel.layoutNo === 1) {
          layoutNo = 0;
          vm.layoutNo(0);
        }
      }
      const layoutRquest = {
        topPageCode: topPageCode,
        layoutNo: layoutNo
      }
      vm.$blockui("show");
      vm.$ajax('/toppage/getLayout', layoutRquest).then((result: any) => {
        if (result) {
          vm.isNewMode(false)
          vm.layoutType(result.layoutType);
          if (result.flowMenuCd) {
            const flowMenuChoose = _.findIndex(vm.listFlowMenu(), (item: FlowMenuItem) => { return item.flowCode === result.flowMenuCd});
            vm.flowMenuSelectedCode(vm.listFlowMenu()[flowMenuChoose].flowCode);
          }
          vm.url(result.url);
          console.log(result);
        } else {
          vm.isNewMode(true);
          vm.layoutType(0);
        }
      }).always(() => {
        vm.$blockui("hide");
      });
    }

    changeLayout() {
      const vm = this,
      data = {
        topPageCd: vm.topPageCd(),
        layoutType: vm.layoutType()
      };
      vm.$ajax('/toppage/changeFlowMenu', data).then((result: any) => {
        if (result && vm.layoutType() === 0) {
          vm.listFlowMenu(result);
        } else if (result && vm.layoutType() === 1) {
          vm.listTopPagePart(result);
        }
      })
    }

    saveLayout() {
      const vm = this;
      let data: any = {
        widgetSettings: null,
        topPageCode: vm.topPageCode(),
        layoutNo: vm.layoutNo(),
        layoutType: vm.layoutType(),
        cid: __viewContext.user.companyId,
        flowMenuCd: vm.flowMenuSelectedCode(),
        flowMenuUpCd: vm.toppageSelectedCode(),
        url: vm.url()
      };
      vm.$ajax('/toppage/saveLayoutFlowMenu', data).then((result: any) => {
        console.log(result);
      })
    }

    // URLの内容表示するを
    showUrl() {
      const vm = this;
      vm.urlIframe2(vm.url());
    }

    close() {
      nts.uk.ui.windows.close();
    }
  }

  class ItemModel {
    code: number;
    name: string;
    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  interface FlowMenuItem {
    fileId: string;
    flowCode: string;
    flowName: string;
  }

  interface TopPagePartItem {
    fileId: string;
    flowCode: string;
    flowName: string;
  }

  class LayoutModel {
    topPageCode: string;
    layoutNo: number;
    constructor(topPageCode: string, layoutNo: number) {
      this.topPageCode = topPageCode;
      this.layoutNo = layoutNo;
    }
  }
}