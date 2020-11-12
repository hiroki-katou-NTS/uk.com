/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.d {

  @bean()
  export class ScreenModel extends ko.ViewModel {
    params: any = {};
    flowMenuSelectedCode: KnockoutObservable<string> = ko.observable('');
    toppageSelectedCode: KnockoutObservable<string> = ko.observable('');
    listFlowMenu: KnockoutObservableArray<FlowMenuItem> = ko.observableArray<FlowMenuItem>([]);
    listTopPagePart: KnockoutObservableArray<TopPagePartItem> = ko.observableArray<TopPagePartItem>([]);
    columnsFlowMenu: KnockoutObservableArray<any> = ko.observableArray([]);
    columnsTopPagePart: KnockoutObservableArray<any> = ko.observableArray([]);
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    isRequired: KnockoutObservable<boolean> = ko.observable(true);
    contentFlowMenu: KnockoutObservable<boolean> = ko.observable(true);
    contentTopPagePart: KnockoutObservable<boolean> = ko.observable(false);
    contentUrl: KnockoutObservable<boolean> = ko.observable(false);
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    urlIframe2: KnockoutObservable<string> = ko.observable('');
    urlIframe3: KnockoutObservable<string> = ko.observable('');
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    topPageCode: KnockoutObservable<string> = ko.observable('');
    layoutNo: KnockoutObservable<number> = ko.observable(0);
    layoutType: KnockoutObservable<number> = ko.observable(null);
    flowMenuCd: KnockoutObservable<string> = ko.observable('');
    flowMenuUpCd: KnockoutObservable<string> = ko.observable('');
    url: KnockoutObservable<string> = ko.observable('');
    html: KnockoutObservable<string> = ko.observable('');
    html2: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      const vm = this;
      vm.params = params;
      vm.topPageCode(params.topPageModel.topPageCode);

      vm.itemList([
        new ItemModel(0, 'フローメニュー'),
        new ItemModel(1, 'フローメニュー（アップロード）'),
        new ItemModel(2, '外部URL')
      ]);
      vm.columnsFlowMenu([
        { headerText: vm.$i18n('CCG015_68'), width: "50px", key: 'flowCode' },
        { headerText: vm.$i18n('CCG015_69'), width: "260px", key: 'flowName' },
      ]);
      vm.columnsTopPagePart([
        { headerText: vm.$i18n('CCG015_68'), width: "50px", key: 'flowCode' },
        { headerText: vm.$i18n('CCG015_69'), width: "260px", key: 'flowName' },
      ]);
      vm.layoutType.subscribe(value => {
        vm.$blockui('grayout');
        vm.changeLayout()
          .then(() => {
            if (value === 0) {
              vm.contentFlowMenu(true);
              vm.contentTopPagePart(false);
              vm.contentUrl(false);
            } else if (value === 1) {
              vm.contentFlowMenu(false);
              vm.contentTopPagePart(true);
              vm.contentUrl(false);
            } else {
              vm.contentFlowMenu(false);
              vm.contentFlowMenu(false);
              vm.contentUrl(true);
            }
          })
          .always(() => vm.$blockui('clear'));
      });
    }

    mounted() {
      const vm = this;
      vm.checkDataLayout(vm.params);
    }


    private changeLayout(): JQueryPromise<any> {
      const vm = this;
      const data = {
        topPageCd: vm.topPageCode(),
        layoutType: vm.layoutType()
      };
      return vm.$ajax('/toppage/changeFlowMenu', data)
        .then((result: any) => {
          if (result && vm.layoutType() === 0) {
            vm.listFlowMenu(result);
          } else if (result && vm.layoutType() === 1) {
            vm.listTopPagePart(result);
          }
        });
    }

    private checkDataLayout(params: any) {
      const vm = this;
      if (params && params.frame === 1) {
        vm.layoutNo(0);
      }
      const layoutRquest = {
        topPageCode: vm.topPageCode(),
        layoutNo: vm.layoutNo()
      }
      vm.$blockui("grayout");
      vm.$ajax('/toppage/getLayout', layoutRquest)
        .then((result: any) => {
          if (result) {
            vm.isNewMode(false)
            vm.layoutType(result.layoutType);
            if (result.flowMenuCd) {
              const flowMenuChoose = _.findIndex(vm.listFlowMenu(), (item: FlowMenuItem) => { return item.flowCode === result.flowMenuCd });
              vm.flowMenuSelectedCode(vm.listFlowMenu()[flowMenuChoose].flowCode);
              const fileIdChoose: string = vm.listFlowMenu()[flowMenuChoose].fileId;
              vm.$ajax('sys/portal/createflowmenu/extract/' + fileIdChoose).then((item: any) => {
                const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>` };
              });
            }
            if (result.flowMenuUpCd) {
              const topPagePartChoose = _.findIndex(vm.listTopPagePart(), (item: TopPagePartItem) => { return item.flowCode === result.flowMenuCd });
              vm.toppageSelectedCode(vm.listTopPagePart()[topPagePartChoose].flowCode);
              const fileIdChoose: string = vm.listFlowMenu()[topPagePartChoose].fileId;
              vm.$ajax('sys/portal/createflowmenu/extract/' + fileIdChoose).then((item: any) => {
                const width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                const height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                return { html2: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>` };
              });
            }
            vm.url(result.url);
          } else {
            vm.isNewMode(true);
            vm.layoutType(0);
          }
        })
        .always(() => vm.$blockui("clear"));
    }

    saveLayout() {
      const vm = this;
      vm.$blockui("show");
      let data: any = {
        widgetSettings: null,
        topPageCode: vm.topPageCode(),
        layoutNo: vm.layoutNo(),
        layoutType: vm.layoutType(),
        cid: __viewContext.user.companyId,
        flowMenuCd: vm.flowMenuSelectedCode(),
        flowMenuUpCd: vm.toppageSelectedCode(),
        url: vm.urlIframe3()
      };
      vm.$ajax('/toppage/saveLayoutFlowMenu', data).done(function () {
        vm.$dialog.info({ messageId: "Msg_15" })
      }).then((result: any) => {
        vm.isNewMode(false);
      }).always(() => {
        vm.$blockui("hide");
      });
    }

    // URLの内容表示するを
    showUrl() {
      const vm = this;
      vm.urlIframe3(vm.url());
    }

    close() {
      const vm = this;
      vm.$window.close();
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
}