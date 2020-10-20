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

    created() {
      const vm = this;
      vm.listTopPage = ko.observableArray<Node>([]);
      vm.itemList = ko.observableArray([
          new ItemModel('1', 'フローメニュー'),
          new ItemModel('2', '外部URL')
      ]);
      vm.columns = ko.observableArray([
        { headerText: this.$i18n('CCG015_68').toString(), width: "50px", key: 'code'},
        { headerText: this.$i18n('CCG015_69').toString(), width: "260px", key: 'nodeText'}
      ]);

      vm.selectedCode = ko.observable('1');
    }

    close() {
      nts.uk.ui.windows.close();
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
  class ItemModel {
    code: string;
    name: string;
    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }
}