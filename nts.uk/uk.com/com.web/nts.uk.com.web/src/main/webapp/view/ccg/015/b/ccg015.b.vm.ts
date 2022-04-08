/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.b {

  // URL API backend
  const API = {
    findAllTopPageItem: "/toppage/findAll",
    getTopPageItemDetail: "/toppage/topPageDetail",
    registerTopPage: "/toppage/create",
    updateTopPage: "/toppage/update",
    removeTopPage: "/toppage/remove"
  };
  @bean()
  export class ScreenModel extends ko.ViewModel {
    listTopPage: KnockoutObservableArray<Node> = ko.observableArray<Node>([]);
    toppageSelectedCode: KnockoutObservable<string> = ko.observable(null);
    topPageModel: KnockoutObservable<TopPageViewModel> = ko.observable(new TopPageViewModel());
    topPageModelParam: KnockoutObservable<TopPageModelParams> = ko.observable(new TopPageModelParams());
    columns: KnockoutObservable<any> = ko.observableArray([]);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    isDisableButton: KnockoutObservable<boolean> = ko.observable(true);
    selectedId: KnockoutObservable<number> = ko.observable(null);
    isVisiableButton1: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableButton2: KnockoutObservable<boolean> = ko.observable(false);
    isVisiableButton3: KnockoutObservable<boolean> = ko.observable(false);
    sizeText: KnockoutObservable<string>;
    displaySizeText1: KnockoutComputed<boolean>;
    displaySizeText2: KnockoutComputed<boolean>;
    displaySizeText3: KnockoutComputed<boolean>;
    button1Text: KnockoutComputed<string>;
    button2Text: KnockoutComputed<string>;
    button3Text: KnockoutComputed<string>;
    isDisableNewBtn: KnockoutObservable<boolean> = ko.observable(false);

    breakNewMode = false;
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel(0, ''),
      new ItemModel(1, ''),
      new ItemModel(2, ''),
      new ItemModel(3, '')
    ]);
    frameLayoutList: KnockoutObservableArray<ItemModel>;
    layoutPos1: KnockoutComputed<string>;
    layoutPos2: KnockoutComputed<string>;
    layoutPos3: KnockoutComputed<string>;

    created() {
      // トップページを選択する
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n("CCG015_11"), width: "50px", key: 'code', dataType: "string", hidden: false },
        { headerText: vm.$i18n("CCG015_12"), width: "260px", key: 'nodeText', dataType: "string", formatter: _.escape }
      ]);
      vm.sizeText = ko.observable(vm.$i18n("CCG015_62"));
      vm.selectedId.subscribe((value: number) => {
        // Render layout
        if (value === LayoutType.LAYOUT_TYPE_0) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(false);
          vm.isVisiableButton3(false);
        } else if (value === LayoutType.LAYOUT_TYPE_1 || value === LayoutType.LAYOUT_TYPE_2) {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(false);
        } else {
          vm.isVisiableButton1(true);
          vm.isVisiableButton2(true);
          vm.isVisiableButton3(true);
        }
      });
      vm.frameLayoutList = ko.observableArray([
        new ItemModel(0, vm.$i18n("CCG015_111")),
        new ItemModel(1, vm.$i18n("CCG015_112")),
        new ItemModel(2, vm.$i18n("CCG015_113")),
        new ItemModel(3, vm.$i18n("CCG015_114")),
      ]);
      vm.button1Text = ko.computed(() => vm.selectedId() === LayoutType.LAYOUT_TYPE_2 ? vm.$i18n("CCG015_60") : 
        (vm.selectedId() === LayoutType.LAYOUT_TYPE_3 ? vm.getLayoutName(vm.topPageModel().frameLayout1()) : vm.$i18n("CCG015_59")));
      vm.button2Text = ko.computed(() => vm.selectedId() === LayoutType.LAYOUT_TYPE_2 ? vm.$i18n("CCG015_59") : 
        (vm.selectedId() === LayoutType.LAYOUT_TYPE_3 ? vm.getLayoutName(vm.topPageModel().frameLayout2()) : vm.$i18n("CCG015_60")));
      vm.button3Text = ko.computed(() => vm.getLayoutName(vm.topPageModel().frameLayout3()));
      vm.layoutPos1 = ko.computed(() => vm.getLayoutPos(vm.topPageModel().frameLayout1()));
      vm.layoutPos2 = ko.computed(() => vm.getLayoutPos(vm.topPageModel().frameLayout2()));
      vm.layoutPos3 = ko.computed(() => vm.getLayoutPos(vm.topPageModel().frameLayout3()));
      vm.displaySizeText1 = ko.computed(() => vm.selectedId() === LayoutType.LAYOUT_TYPE_2
        || (vm.selectedId() === LayoutType.LAYOUT_TYPE_3 && vm.topPageModel().frameLayout1() !== 0));
      vm.displaySizeText2 = ko.computed(() => vm.selectedId() === LayoutType.LAYOUT_TYPE_1
        || (vm.selectedId() === LayoutType.LAYOUT_TYPE_3 && vm.topPageModel().frameLayout2() !== 0));
      vm.displaySizeText3 = ko.computed(() => vm.selectedId() === LayoutType.LAYOUT_TYPE_3 && vm.topPageModel().frameLayout3() !== 0);
      vm.loadTopPageList();
    }

    mounted() {
      const vm = this;
      vm.selectedId(0);
      vm.toppageSelectedCode.subscribe((selectedTopPageCode: string) => {
        if (selectedTopPageCode) {
          vm.isNewMode(false);
          vm.breakNewMode = false;
          vm.$blockui("grayout");
          vm.$ajax(`${API.getTopPageItemDetail}/${selectedTopPageCode}`)
            .then((data: TopPageDto) => {
              vm.loadTopPageItemDetail(data);
              $('.save-error').ntsError('clear');
            })
            .always(() => vm.$blockui("clear"));
          if (selectedTopPageCode !== "") {
            $("#inp_name").focus();
          }
        } else {
          // 新規のトップページを作成する
          vm.isNewMode(true);
          vm.breakNewMode = true;
          vm.topPageModel(new TopPageViewModel());
          if (nts.uk.ui.errors.hasError()) {
            nts.uk.ui.errors.clearAll();
          }
        }
      });
      
      vm.isNewMode.subscribe((x:any) => {
        if(x) {
          vm.isDisableButton(true);
        } else {
          vm.isDisableButton(false);
        }
      });
     

    }

    private loadTopPageList(selectedCode?: string): JQueryPromise<void> {
      const vm = this;
      const dfd = $.Deferred<void>();
      vm.$blockui("grayout");
      vm.$ajax(API.findAllTopPageItem)
        .then((data: Array<TopPageItemDto>) => {
          // if data # empty
          if (data.length > 0) {
            const listTopPage: Node[] = _.map(data, (item) => new Node(item.topPageCode, item.topPageName, null));
            const lstSort =  _.orderBy(listTopPage, ["code"], ["asc"]);
            vm.listTopPage(lstSort);
            const selectToppage = _.find(vm.listTopPage(), item => { return item.code === selectedCode; })
            vm.isDisableNewBtn(false);
            vm.toppageSelectedCode(selectedCode || lstSort[0].code);
            vm.topPageModel().topPageName(selectToppage?.name)
            if (vm.selectedId() !== 3) {
              vm.topPageModel().frameLayout1(1);
              vm.topPageModel().frameLayout2(0);
              vm.topPageModel().frameLayout3(2);
            }
            $("#inp_name").focus();
          } else {
            vm.listTopPage([]);
            vm.topPageModel(new TopPageViewModel());
            vm.isDisableNewBtn(true);
            vm.isNewMode(true);
            $("#inp_code").focus();
          }
          dfd.resolve();
        })
        .fail((err) => dfd.fail(err))
        .always(() => vm.$blockui("clear"));
      return dfd.promise();
    }

    //load top page Item
    private loadTopPageItemDetail(data: TopPageDto) {
      const vm = this;
      vm.topPageModel().topPageCode(data.topPageCode);
      vm.topPageModel().topPageName(data.topPageName);
      vm.topPageModel().layoutDisp(data.layoutDisp);
      vm.topPageModel().frameLayout1(data.layoutDisp === 3 ? (data.frameLayout1 ?? 1) : 1);
      vm.topPageModel().frameLayout2(data.layoutDisp === 3 ? (data.frameLayout2 ?? 0) : 0);
      vm.topPageModel().frameLayout3(data.layoutDisp === 3 ? (data.frameLayout3 ?? 2) : 2);
      vm.selectedId(data.layoutDisp);
    }

    private collectData(): TopPageModelParams {
      const vm = this;
      const param = new TopPageModelParams({
        topPageCode: vm.topPageModel().topPageCode(),
        topPageName: vm.topPageModel().topPageName(),
        layoutDisp: vm.selectedId()
      });
      switch (vm.selectedId()) {
        case LayoutType.LAYOUT_TYPE_0:
          param.frameLayout1 = 0; break;
        case LayoutType.LAYOUT_TYPE_1:
          param.frameLayout1 = 0;
          param.frameLayout2 = 1; break;
        case LayoutType.LAYOUT_TYPE_2:
          param.frameLayout1 = 1;
          param.frameLayout2 = 0; break;
        case LayoutType.LAYOUT_TYPE_3:
          param.frameLayout1 = vm.topPageModel().frameLayout1();
          param.frameLayout2 = vm.topPageModel().frameLayout2();
          param.frameLayout3 = vm.topPageModel().frameLayout3(); break;
      }
      return param;
    }

    newTopPage() {
      const vm = this;
      vm.topPageModel(new TopPageViewModel());
      vm.isNewMode(true);
      vm.selectedId(0);
      this.$nextTick(()=>  $("#inp_code").focus());
      vm.breakNewMode = true;
      vm.toppageSelectedCode("");
      if (nts.uk.ui.errors.hasError()) {
        nts.uk.ui.errors.clearAll();
      }
    }

    saveTopPage() {
      const vm = this;
      vm.$validate()
        .then((valid: boolean) => {
          if (valid && vm.validateFrameLayout()) {
            //check update or create
            if (vm.listTopPage().length === 0) {
              vm.isNewMode(true);
            }
            const param = vm.collectData();
            if (vm.isNewMode()) {
              vm.$blockui('grayout');
              vm.$ajax(API.registerTopPage, param)
                .then(() => {
                  vm.$blockui("clear");
                  vm.$dialog.info({ messageId: "Msg_15" });
                  $("#inp_name").focus();
                  vm.loadTopPageList(param.topPageCode);
                })
                .fail((err) => {
                  vm.$blockui("clear");
                  vm.$dialog.alert({ messageId: err.messageId, messageParams: err.parameterIds });
                });
            } else {
              vm.$blockui('grayout');
              vm.$ajax(API.updateTopPage, param)
                .then(() => {
                  vm.$blockui("clear");
                  vm.$dialog.info({ messageId: "Msg_15" });
                  vm.loadTopPageList(param.topPageCode);
                  $("#inp_name").focus();
                })
                .fail((err) => {
                  vm.$blockui("clear");
                  vm.$dialog.alert({ messageId: err.messageId, messageParams: err.parameterIds });
                });
            }
          }
        });
    }

    copyTopPage() {
      const vm = this;
      const dataCopy = {
        topPageCode: vm.topPageModel().topPageCode(),
        topPageName: vm.topPageModel().topPageName(),
        layoutDisp: vm.topPageModel().layoutDisp(),
        frameLayout1: vm.topPageModel().frameLayout1(),
        frameLayout2: vm.topPageModel().frameLayout2(),
        frameLayout3: vm.topPageModel().frameLayout3(),
      };
      vm.$window.modal("/view/ccg/015/c/index.xhtml", dataCopy)
        .then((codeOfNewTopPage: string) => {
          if (codeOfNewTopPage) {
            vm.loadTopPageList(codeOfNewTopPage);
          }
        });
    }

    removeTopPage() {
      const vm = this;
      vm.$dialog.confirm({ messageId: 'Msg_18' })
        .then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'yes') {
            const removeCode = vm.toppageSelectedCode();
            const removeIndex = vm.getIndexOfRemoveItem(removeCode);
            vm.$blockui("grayout");
            vm.$ajax(API.removeTopPage, { topPageCode: vm.toppageSelectedCode() })
              .then(() => {
                // delete success
                vm.$blockui("clear");
                vm.$dialog.info({ messageId: "Msg_16" })
                  .then(() => {
                    //remove follow
                      const lst = vm.listTopPage();
                      if (lst.length > 0) {
                        if (removeIndex === 0) {
                          vm.toppageSelectedCode(lst[removeIndex + 1]?.code);
                        } else {
                          vm.toppageSelectedCode(lst[removeIndex - 1].code);
                        }
                        vm.loadTopPageList(vm.toppageSelectedCode());
                      }
                  });
              })
              .always(() => vm.$blockui("clear"));
          }
        });
    }

    private getIndexOfRemoveItem(code: string): number {
      const vm = this;
      let itemIndex:any;
      _.forEach(vm.listTopPage(), (item) => {
        if (item.code === code) {
          itemIndex =  item
        }
      });
      return vm.listTopPage().indexOf(itemIndex);
    }

    // レイアウト設定を起動する
    openDialogButton1() {
      const vm = this;
      const frame: number = vm.selectedId() === LayoutType.LAYOUT_TYPE_2 ? 2 : 1;
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      topPageModel.layoutDisp = vm.topPageModel().layoutDisp();
      topPageModel.frameLayout1 = vm.topPageModel().frameLayout1();
      topPageModel.frameLayout2 = vm.topPageModel().frameLayout2();
      topPageModel.frameLayout3 = vm.topPageModel().frameLayout3();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
        topPageModel: vm.topPageModelParam(),
        frame: frame
      };
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_0 || vm.selectedId() === LayoutType.LAYOUT_TYPE_1) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
      } else if(vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        // Flowmenu frame layout
        if (topPageModel.frameLayout1 === 0) {
          vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
        } else {
          dataScreen.frame = topPageModel.frameLayout1 + 1;
          vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
        }
      } else {
        vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
      }
    }

    // レイアウト設定を起動する
    openDialogButton2() {
      const vm = this;
      const frame: number = vm.selectedId() === LayoutType.LAYOUT_TYPE_1 ? 2 : 1;
      const topPageModel: TopPageModelParams = vm.topPageModelParam();
      topPageModel.topPageCode = vm.topPageModel().topPageCode();
      topPageModel.topPageName = vm.topPageModel().topPageName();
      topPageModel.frameLayout1 = vm.topPageModel().frameLayout1();
      topPageModel.frameLayout2 = vm.topPageModel().frameLayout2();
      topPageModel.frameLayout3 = vm.topPageModel().frameLayout3();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
        topPageModel: vm.topPageModelParam(),
        frame: frame
      };
      if (vm.selectedId() === LayoutType.LAYOUT_TYPE_2) {
        vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
      } else if(vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        // Flowmenu frame layout
        if (topPageModel.frameLayout2 === 0) {
          vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
        } else {
          dataScreen.frame = topPageModel.frameLayout2 + 1;
          vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
        }
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
      topPageModel.frameLayout1 = vm.topPageModel().frameLayout1();
      topPageModel.frameLayout2 = vm.topPageModel().frameLayout2();
      topPageModel.frameLayout3 = vm.topPageModel().frameLayout3();
      vm.topPageModelParam(topPageModel);
      const dataScreen = {
        topPageModel: vm.topPageModelParam(),
        frame: 3
      };
      if(vm.selectedId() === LayoutType.LAYOUT_TYPE_3) {
        // Flowmenu frame layout
        if (topPageModel.frameLayout3 === 0) {
          vm.$window.modal('/view/ccg/015/d/index.xhtml', dataScreen);
        } else {
          dataScreen.frame = topPageModel.frameLayout3 + 1;
          vm.$window.modal('/view/ccg/015/e/index.xhtml', dataScreen);
        }
      }
    }

    // プレビューを表示する
    openDialogCCG015F() {
      const vm = this;
      const topPageModel: TopPageModelParams = vm.collectData();
      vm.topPageModelParam(topPageModel);
      const data = {
        topPageModel: vm.topPageModelParam(),
        selectedId: vm.selectedId(),
      };
      const size = {
        width: Math.round(Number(window.innerWidth) * 90 / 100),
        height: Math.round(Number(window.innerHeight) * 80 / 100),
        resizable: false, 
      }
      vm.$window.modal('/view/ccg/015/f/index.xhtml', data, size);
    }

    private getLayoutPos(frameLayout: number): string {
      const vm = this;
      switch (frameLayout) {
        case 0: return vm.$i18n("CCG015_115");
        case 1: return vm.$i18n("CCG015_116");
        case 2: return vm.$i18n("CCG015_117");
        case 3: return vm.$i18n("CCG015_118");
        default: return "";
      }
    }

    private getLayoutName(frameLayout: number): string {
      const vm = this;
      switch (frameLayout) {
        case 0: return vm.$i18n("CCG015_59");
        case 1: return vm.$i18n("CCG015_60");
        case 2: return vm.$i18n("CCG015_61");
        case 3: return vm.$i18n("CCG015_110");
        default: return "";
      }
    }

    private validateFrameLayout(): boolean {
      const vm = this;
      if (vm.selectedId() !== LayoutType.LAYOUT_TYPE_3) {
        return true;
      }
      const arr = [vm.topPageModel().frameLayout1(), vm.topPageModel().frameLayout2(), vm.topPageModel().frameLayout3()];
      const duplicateValue = _.chain(arr).groupBy().pickBy(data => data.length > 1).keys().value();
      if (_.isEmpty(duplicateValue)) {
        return true;
      }
      const textValue = _.map(duplicateValue, data => _.find(vm.frameLayoutList(), { code: Number(data) }).name);
      vm.$dialog.error({ messageId: "Msg_3273", messageParams: textValue });
      return false;
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

  export class TopPageViewModel {
    topPageCode: KnockoutObservable<string>;
    topPageName: KnockoutObservable<string>;
    layoutDisp: KnockoutObservable<number>;
    frameLayout1: KnockoutObservable<number>;
    frameLayout2: KnockoutObservable<number>;
    frameLayout3: KnockoutObservable<number>;

    constructor() {
      this.topPageCode = ko.observable('');
      this.topPageName = ko.observable('');
      this.layoutDisp = ko.observable(0);
      this.frameLayout1 = ko.observable(1);
      this.frameLayout2 = ko.observable(0);
      this.frameLayout3 = ko.observable(2);
    }
  }

  export interface TopPageItemDto {
    topPageCode: string;
    topPageName: string;
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

  export class TopPageModelParams {
    topPageCode?: string;
    topPageName?: string;
    layoutDisp?: number;
    frameLayout1?: number;
    frameLayout2?: number;
    frameLayout3?: number;

    constructor(init?: Partial<TopPageModelParams>) {
      $.extend(this, init);
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

  enum LayoutType {
    LAYOUT_TYPE_0 = 0,
    LAYOUT_TYPE_1 = 1,
    LAYOUT_TYPE_2 = 2,
    LAYOUT_TYPE_3 = 3,
  }
}