/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.b.screenModel {
  import commonModel = ccg.model;
  import TopPageItemDto = ccg015.b.service.model.TopPageItemDto;
  import TopPageDto = ccg015.b.service.model.TopPageDto;
  @bean()
  export class ViewModel extends ko.ViewModel {
    listTopPage: KnockoutObservableArray<Node>;
    toppageSelectedCode: KnockoutObservable<string>;
    topPageModel: KnockoutObservable<TopPageModel>;
    columns: KnockoutObservable<any>;
    isNewMode: KnockoutObservable<boolean>;
    languageListOption: KnockoutObservableArray<ItemCbbModel>;
    languageSelectedCode: KnockoutObservable<string>;
    listLinkScreen: KnockoutObservableArray<any>;
    selectedId: KnockoutObservable<number> = ko.observable(1);
    isVisiableButton1: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableButton2: KnockoutObservable<boolean> = ko.observable(false);
    isVisiableButton3: KnockoutObservable<boolean> = ko.observable(false);

    isProcess: KnockoutObservable<boolean>;
    breakNewMode: boolean;
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray(getHistoryEditMethod());
    button1Text: KnockoutComputed<string> = ko.computed(() => {
      if (this.selectedId() === LayoutType.LAYOUT_TYPE_3 || this.selectedId() === LayoutType.LAYOUT_TYPE_4) {
        return nts.uk.resource.getText("CCG015_60");
      }
      return nts.uk.resource.getText("CCG015_59");
    });
    button2Text: KnockoutComputed<string> = ko.computed(() => {
      if (this.selectedId() === LayoutType.LAYOUT_TYPE_3 || this.selectedId() === LayoutType.LAYOUT_TYPE_4) {
        return nts.uk.resource.getText("CCG015_59");
      }
      return nts.uk.resource.getText("CCG015_60");
    });

    created() {
      const vm = this;
      vm.listTopPage = ko.observableArray<Node>([]);
      vm.toppageSelectedCode = ko.observable(null);
      vm.topPageModel = ko.observable(new TopPageModel());
      vm.columns = ko.observableArray([
        { headerText: nts.uk.resource.getText("CCG015_11"), width: "50px", key: 'code', dataType: "string", hidden: false },
        { headerText: nts.uk.resource.getText("CCG015_12"), width: "260px", key: 'nodeText', dataType: "string", formatter: _.escape }
      ]);
      vm.isNewMode = ko.observable(true);
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
      vm.languageListOption = ko.observableArray([
        new ItemCbbModel("0", "日本語"),
        new ItemCbbModel("1", "英語"),
        new ItemCbbModel("2", "ベトナム語")
      ]);
      vm.languageSelectedCode = ko.observable("0");

      vm.isProcess = ko.observable(false);
      vm.breakNewMode = false;
      //end constructor

      $("#preview-iframe").on("load", function () {
        if (vm.isNewMode() == true)
          $("#inp_code").focus();
        else
          $("#inp_name").focus();
      });
    }

    mounted() {
      const vm = this;
      // visiable button
      vm.selectedId.subscribe(value => {
        if (vm.selectedId() === LayoutType.LAYOUT_TYPE_1) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(false);
          vm.isVisiableButton3(false);
        } else if (vm.selectedId() === LayoutType.LAYOUT_TYPE_2) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(false);
        } else if (vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
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
          vm.changePreviewIframe(null);
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
      vm.topPageModel().layoutId(data.layoutId);
      vm.changePreviewIframe(data.layoutId);
    }

    private collectData(): TopPageDto {
      const vm = this;
      //mock data
      var data: TopPageDto = { topPageCode: vm.topPageModel().topPageCode(), topPageName: vm.topPageModel().topPageName(), languageNumber: 0, layoutId: vm.topPageModel().layoutId() };
      return data;
    }

    private saveTopPage() {
      const vm = this;
      $('.nts-input').ntsEditor('validate');
      if (!$('.nts-input').ntsError('hasError')) {
        //check update or create
        vm.isProcess(true);
        vm.$blockui("show");
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

    private copyTopPage() {
      const vm = this;
      nts.uk.ui.windows.setShared('topPageCode', vm.topPageModel().topPageCode());
      nts.uk.ui.windows.setShared('topPageName', vm.topPageModel().topPageName());
      nts.uk.ui.windows.setShared('layoutId', vm.topPageModel().layoutId());
      nts.uk.ui.windows.sub.modal("/view/ccg/015/c/index.xhtml").onClosed(() => {
        var codeOfNewTopPage = nts.uk.ui.windows.getShared("codeOfNewTopPage");
        vm.loadTopPageList().done(() => {
          vm.toppageSelectedCode(codeOfNewTopPage);
        });
      });

    }

    private newTopPage() {
      const vm = this;
      vm.topPageModel(new TopPageModel());
      vm.isNewMode(true);
      vm.breakNewMode = true;
      vm.toppageSelectedCode("");
      if (nts.uk.ui.errors.hasError()) {
        nts.uk.ui.errors.clearAll();
      }
      //wait clear error
      vm.changePreviewIframe(null);
      $("#preview-iframe").trigger("load");
    }

    private removeTopPage() {
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

    //for frame review layout
    private changePreviewIframe(layoutID: string): void {
      $("#preview-iframe").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
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

    private openDialogButton1() {
      const vm = this;
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_1 || vm.selectedId() === LayoutType.LAYOUT_TYPE_2) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml');
      }
      vm.$window.modal('/view/ccg/015/e/index.xhtml');
      
    }

    private openDialogButton2() {
      const vm = this;
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_3 || vm.selectedId() === LayoutType.LAYOUT_TYPE_4) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml');
      } else {
        vm.$window.modal('/view/ccg/015/e/index.xhtml');
      }
    }

    private openDialogButton3() {
      const vm = this;
      vm.$window.modal('/view/ccg/015/e/index.xhtml');
    }

    private openDialogCCG015F() {
      const vm = this,
        data = {
          selectedId: this.selectedId()
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
    placement: KnockoutObservableArray<PlacementModel>;
    layoutId: KnockoutObservable<string>;
    constructor() {
      this.topPageCode = ko.observable('');
      this.topPageName = ko.observable('');
      this.placement = ko.observableArray([]);
      this.layoutId = ko.observable('');
    }
  }

  export class PlacementModel {
    row: KnockoutObservable<number>;
    column: KnockoutObservable<number>;
    topPagePart: KnockoutObservable<TopPagePartModel>;
    constructor() {
      this.row = ko.observable(0);
      this.column = ko.observable(0);
      this.topPagePart = ko.observable(new TopPagePartModel());
    }
  }

  export class TopPagePartModel {
    topPagePartType: KnockoutObservable<number>;
    topPagePartCode: KnockoutObservable<string>;
    topPagePartName: KnockoutObservable<string>;
    width: KnockoutObservable<number>;
    height: KnockoutObservable<number>;
    constructor() {
      this.topPagePartType = ko.observable(0);
      this.topPagePartCode = ko.observable("");
      this.topPagePartName = ko.observable("");
      this.width = ko.observable(0);
      this.height = ko.observable(0);
    }
  }

  export class ItemCbbModel {
    code: string;
    name: string;
    label: string;
    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  export function getHistoryEditMethod(): Array<ItemModel> {
    return [
      new ItemModel(1, ''),
      new ItemModel(2, ''),
      new ItemModel(3, ''),
      new ItemModel(4, '')
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
    LAYOUT_TYPE_1 = 1,
    LAYOUT_TYPE_2 = 2,
    LAYOUT_TYPE_3 = 3,
    LAYOUT_TYPE_4 = 4,
  }
}