/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.b.screenModel {
  import TopPageItemDto = ccg015.b.service.model.TopPageItemDto;
  import TopPageDto = ccg015.b.service.model.TopPageDto;
  @bean()
  export class ViewModel extends ko.ViewModel {
    listTopPage: KnockoutObservableArray<Node>;
    toppageSelectedCode: KnockoutObservable<string>;
    topPageModel: KnockoutObservable<TopPageModel>;
    topPageModelParam: KnockoutObservable<TopPageModelParams> = ko.observable(new TopPageModelParams());
    columns: KnockoutObservable<any> = ko.observableArray([]);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    selectedId: KnockoutObservable<number> = ko.observable(1);
    isVisiableButton1: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableButton2: KnockoutObservable<boolean> = ko.observable(false);
    isVisiableButton3: KnockoutObservable<boolean> = ko.observable(false);

    isProcess: KnockoutObservable<boolean>;
    breakNewMode: boolean;
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray(getHistoryEditMethod());
    button1Text: KnockoutComputed<string> = ko.computed(() => {
      if (this.selectedId() === LayoutType.LAYOUT_TYPE_2 || this.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        return nts.uk.resource.getText("CCG015_60");
      }
      return nts.uk.resource.getText("CCG015_59");
    });
    button2Text: KnockoutComputed<string> = ko.computed(() => {
      if (this.selectedId() === LayoutType.LAYOUT_TYPE_2 || this.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        return nts.uk.resource.getText("CCG015_59");
      }
      return nts.uk.resource.getText("CCG015_60");
    });

    created() {
      // トップページを選択する
      const vm = this;
      vm.listTopPage = ko.observableArray<Node>([]);
      vm.toppageSelectedCode = ko.observable(null);
      vm.topPageModel = ko.observable(new TopPageModel());
      vm.columns = ko.observableArray([
        { headerText: nts.uk.resource.getText("CCG015_11"), width: "50px", key: 'code', dataType: "string", hidden: false },
        { headerText: nts.uk.resource.getText("CCG015_12"), width: "260px", key: 'nodeText', dataType: "string", formatter: _.escape }
      ]); 
      vm.toppageSelectedCode.subscribe(function (selectedTopPageCode: string) {
        if (nts.uk.text.isNullOrEmpty(selectedTopPageCode)) {
          vm.isNewMode(true);
          vm.newTopPage();
        }
        else {
          vm.$blockui("grayout");
          service.loadDetailTopPage(selectedTopPageCode).done(function (data: TopPageDto) {
            vm.loadTopPageItemDetail(data);
            $('.save-error').ntsError('clear');
          }).always(() => vm.$blockui("clear"));;
          vm.isNewMode(false);
          vm.breakNewMode = false;
          $("#inp_name").focus();
        }
      });

      vm.isProcess = ko.observable(false);
      vm.breakNewMode = false;

    }

    mounted() {
      const vm = this;
      // visiable button
      vm.selectedId.subscribe(value => {
        if (vm.selectedId() === LayoutType.LAYOUT_TYPE_0) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(false);
          vm.isVisiableButton3(false);
        } else if (vm.selectedId() === LayoutType.LAYOUT_TYPE_1) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(false);
        } else if (vm.selectedId() === LayoutType.LAYOUT_TYPE_2) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(false);
        } else {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(true);
        }
      })
      vm.loadTopPageList();
    }

    private loadTopPageList(): JQueryPromise<void> {
      const vm = this;
      var dfd = $.Deferred<void>();
      vm.$blockui("grayout");
      vm.listTopPage([]);
      service.loadTopPage().done(function (data: Array<TopPageItemDto>) {
        //if data # empty
        if (data.length > 0) {
          data.forEach(function (item, index) {
            vm.listTopPage.push(new Node(item.topPageCode, item.topPageName, null));
          });
          if (vm.listTopPage().length > 0) {
            //focus first item
            vm.toppageSelectedCode(vm.listTopPage()[0].code);
          }
        }
        else {
          vm.topPageModel(new TopPageModel());
          vm.isNewMode(true);
          $("#inp_code").focus();
        }
        dfd.resolve();
      }).always(() => vm.$blockui("clear"));
      return dfd.promise();
    }

    //load top page Item 
    private loadTopPageItemDetail(data: TopPageDto) {
      const vm = this;
      vm.topPageModel().topPageCode(data.topPageCode);
      vm.topPageModel().topPageName(data.topPageName);
      vm.topPageModel().layoutDisp(data.layoutDisp);
      vm.selectedId(data.layoutDisp);
    }

    private collectData(): TopPageDto {
      const vm = this;
      //mock data
      let data: TopPageDto = { cid: __viewContext.user.companyId, topPageCode: vm.topPageModel().topPageCode(), topPageName: vm.topPageModel().topPageName(), layoutDisp: vm.selectedId() };
      return data;
    }

    saveTopPage() {
      const vm = this;
      $('.nts-input').ntsEditor('validate');
      if (!$('.nts-input').ntsError('hasError')) {
        //check update or create
        vm.isProcess(true);
        vm.$blockui("show");
        if (vm.listTopPage().length === 0) {
          vm.isNewMode(true);
        }
        if (vm.isNewMode()) {
          service.registerTopPage(vm.collectData()).done(function () {
            vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
              vm.isProcess(false);
            });
            vm.loadTopPageList().done(function () {
              vm.toppageSelectedCode(vm.collectData().topPageCode);
            });
          }).fail(function (res) {
            vm.$dialog.alert({ messageId: res.messageId, messageParams: res.parameterIds }).then(() => {
              vm.isProcess(false);
            });
          }).always(() => {
            vm.$blockui("hide");
          });
        }
        else {
          service.updateTopPage(vm.collectData()).done(function () {
            vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
              vm.isProcess(false);
            });
            vm.loadTopPageList().done(function () {
              vm.toppageSelectedCode(vm.collectData().topPageCode);
            });
          }).always(function () {
            vm.$blockui("hide");
          });
        }
      }
    }

    copyTopPage() {
      const vm = this,
      dataCopy = {
        topPageCode: vm.topPageModel().topPageCode(),
        topPageName: vm.topPageModel().topPageName(),
        layoutDisp: vm.topPageModel().layoutDisp()
      }
      vm.$window.modal("/view/ccg/015/c/index.xhtml", dataCopy).then(() => {
        let codeOfNewTopPage = nts.uk.ui.windows.getShared("codeOfNewTopPage");
        vm.loadTopPageList().done(() => {
          vm.toppageSelectedCode(codeOfNewTopPage);
        });
      });

    }

    // 新規のトップページを作成する
    private newTopPage() {
      const vm = this;
      vm.topPageModel(new TopPageModel());
      vm.isNewMode(true);
      vm.breakNewMode = true;
      vm.toppageSelectedCode("");
      if (nts.uk.ui.errors.hasError()) {
        nts.uk.ui.errors.clearAll();
      }
    }

    removeTopPage() {
      const vm = this;
      nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18")).ifYes(function () {
        var removeCode = vm.toppageSelectedCode();
        var removeIndex = vm.getIndexOfRemoveItem(removeCode);
        var listLength = vm.listTopPage().length;
        service.deleteTopPage(vm.toppageSelectedCode()).done(function () {
          //delete success
          nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function () {
            //remove follow
            vm.loadTopPageList().done(function () {
              var lst = vm.listTopPage();
              if (lst.length > 0) {
                if (removeIndex < listLength - 1) {
                  vm.toppageSelectedCode(lst[removeIndex].code);
                }
                else {
                  vm.toppageSelectedCode(lst[removeIndex - 1].code);
                }
              }
            });
          });
        }).fail();
      }).ifNo(function () {
      });
    }

    private getIndexOfRemoveItem(code: string): number {
      const vm = this;
      var ind = 0;
      vm.listTopPage().forEach(function (item, index) {
        if (item.code == code)
          ind = index;
      });
      return ind;
    }

    // レイアウト設定を起動する
    openDialogButton1() {
      const vm = this;
      let frame: number;
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_0 || vm.selectedId() === LayoutType.LAYOUT_TYPE_1) {
        frame = 1;
      } else {
        frame = 2;
      }
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      topPageModel.layoutDisp = vm.topPageModel().layoutDisp();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
          topPageModel: vm.topPageModelParam(),
          frame: frame
        };
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_0 || vm.selectedId() === LayoutType.LAYOUT_TYPE_1) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
      } else {
        vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
      }
    }

    // レイアウト設定を起動する
    openDialogButton2() {
      const vm = this;
      let frame: number;
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_2 || vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        frame = 1;
      } else {
        frame = 2;
      }
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
          topPageModel: vm.topPageModelParam(),
          frame: frame
        };
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_2 || vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
      } else {
        vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
      }
    }

    // レイアウト設定を起動する
    openDialogButton3() {
      const vm = this;
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
          topPageModel: vm.topPageModelParam(),
          frame: 3
        };
      vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
    }

    // プレビューを表示する
    openDialogCCG015F() {
      const vm = this;
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      vm.topPageModelParam(topPageModel);
      const data = {
          selectedId: this.selectedId(),
          topPageModel: vm.topPageModelParam(),
        };
      vm.$window.modal('/view/ccg/015/f/index.xhtml', data);
    }
  }

  export class Node {
    code: string;
    name: string;
    nodeText: string;
    custom: string;
    childs: Array<Node>;
    constructor(code: string, name: string, childs: Array<Node>) {
      const vm = this;
      vm.code = code;
      vm.name = name;
      vm.nodeText = name;
      vm.childs = childs;
      vm.custom = 'Random' + new Date().getTime();
    }
  }

  export class TopPageModel {
    topPageCode: KnockoutObservable<string>;
    topPageName: KnockoutObservable<string>;
    layoutDisp: KnockoutObservable<number>;
    constructor() {
      this.topPageCode = ko.observable('');
      this.topPageName = ko.observable('');
      this.layoutDisp = ko.observable(0);
    }
  }

  export class TopPageModelParams {
    topPageCode?: string;
    topPageName?: string;
    layoutDisp?: number;
  }

  export function getHistoryEditMethod(): Array<ItemModel> {
    return [
      new ItemModel(0, ''),
      new ItemModel(1, ''),
      new ItemModel(2, ''),
      new ItemModel(3, '')
    ];
  }
  class ItemModel {
    code: number;
    name: string;
    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  enum LayoutType {
    LAYOUT_TYPE_0 = 0,
    LAYOUT_TYPE_1 = 1,
    LAYOUT_TYPE_2 = 2,
    LAYOUT_TYPE_3 = 3,
  }
}