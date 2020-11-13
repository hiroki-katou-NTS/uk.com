/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.d.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    toppageSelectedCode: KnockoutObservable<string> = ko.observable('');
    listTopPage: KnockoutObservableArray<Node> = ko.observableArray<Node>([]);
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    itemList: KnockoutObservableArray<ItemModel>;
    isRequired: KnockoutObservable<boolean> = ko.observable(true);
    contentUrlDisabled: KnockoutObservable<boolean> = ko.observable(true);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    urlIframe2: KnockoutObservable<string> = ko.observable('');
    topPageCd: KnockoutObservable<string> = ko.observable('');
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    topPageCode: KnockoutObservable<string> = ko.observable('');
    layoutNo: KnockoutObservable<number> = ko.observable(0);
    layoutType: KnockoutObservable<number> = ko.observable(0);
    flowMenuCd: KnockoutObservable<string> = ko.observable('');
    flowMenuUpCd: KnockoutObservable<string> = ko.observable('');
    url: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      const vm = this;
      vm.topPageCd(params.topPageModel.topPageCode);
      vm.listTopPage = ko.observableArray<Node>([]);
      vm.itemList = ko.observableArray([
          new ItemModel(0, 'フローメニュー'),
          new ItemModel(1, 'フローメニュー（アップロード）'),
          new ItemModel(2, '外部URL')
      ]);
      vm.columns = ko.observableArray([
        { headerText: this.$i18n('CCG015_68').toString(), width: "50px", key: 'code'},
        { headerText: this.$i18n('CCG015_69').toString(), width: "260px", key: 'nodeText'}
      ]);
      vm.layoutType = ko.observable(0);
      vm.changeLayout();
      vm.checkDataLayout(params);
    }

    mounted() {
      const vm = this;
      vm.layoutType.subscribe(value => {
        if (value === 1 || value === 0) {
          vm.changeLayout();
          vm.contentUrlDisabled(true);
        } else {
          vm.changeLayout()
          vm.contentUrlDisabled(false);
        }
      });
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
          vm.isNewMode(false);
          console.log(result);
        } else {
          vm.isNewMode(true);
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
        console.log(result);
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
        flowMenuCd: '0001',
        flowMenuUpCd: '0001',
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

  class LayoutModel {
    topPageCode: string;
    layoutNo: number;
    constructor(topPageCode: string, layoutNo: number) {
      this.topPageCode = topPageCode;
      this.layoutNo = layoutNo;
    }
  }
}