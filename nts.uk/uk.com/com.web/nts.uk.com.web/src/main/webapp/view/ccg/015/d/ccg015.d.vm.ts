/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.d.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    listTopPage: KnockoutObservableArray<Node> = ko.observableArray<Node>([]);
    toppageSelectedCode: KnockoutObservable<string> = ko.observable('');
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    itemList: KnockoutObservableArray<ItemModel>;
    selectedCode: KnockoutObservable<string> = ko.observable('');
    isRequired: KnockoutObservable<boolean> = ko.observable(true);
    contentUrlDisabled: KnockoutObservable<boolean> = ko.observable(true);
    isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
    url: KnockoutObservable<string> = ko.observable('');
    urlIframe1: KnockoutObservable<string> = ko.observable('');
    urlIframe2: KnockoutObservable<string> = ko.observable('');
    topPageCd: KnockoutObservable<string> = ko.observable('');
    valueSelected:  KnockoutObservable<number> = ko.observable(0);

    created(params: any) {
      const vm = this;
      vm.topPageCd(params.topPageModel.topPageCode);
      vm.listTopPage = ko.observableArray<Node>([]);
      vm.itemList = ko.observableArray([
          new ItemModel('0', 'フローメニュー'),
          new ItemModel('1', 'フローメニュー（アップロード）'),
          new ItemModel('2', '外部URL')
      ]);
      vm.columns = ko.observableArray([
        { headerText: this.$i18n('CCG015_68').toString(), width: "50px", key: 'code'},
        { headerText: this.$i18n('CCG015_69').toString(), width: "260px", key: 'nodeText'}
      ]);

      vm.selectedCode = ko.observable('0');

      if (params) {
        if (params.updateMode  === true) {
          vm.isUpdateMode(true);
        } else {
          vm.isUpdateMode(false);
        }
      } 

      vm.$ajax('/toppage/getLayout'+ '/' + vm.topPageCd()).then((result: any) => {
        if (result) {
          console.log(result);
        }
      })
    }

    mounted() {
      const vm = this;
      vm.selectedCode.subscribe(value => {
        vm.valueSelected(parseInt(value));
        if (value === '1' || value === '0') {
          vm.changeLayout();
          vm.contentUrlDisabled(true);
        } else {
          vm.changeLayout()
          vm.contentUrlDisabled(false);
        }
      });
    }

    changeLayout() {
      const vm = this,
      data = {
        topPageCd: vm.topPageCd(),
        layoutType: vm.valueSelected()
      };
      vm.$ajax('/toppage/getFlowMenu', data).then((result: any) => {
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
    code: string;
    name: string;
    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }
}