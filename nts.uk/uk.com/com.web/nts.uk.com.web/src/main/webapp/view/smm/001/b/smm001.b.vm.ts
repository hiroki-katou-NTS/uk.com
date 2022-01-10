/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.a {

  import windows = nts.uk.ui.windows;

  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedValue: KnockoutObservable<boolean>;
    itemList: KnockoutObservableArray<ItemModel>;
    selectedCode: KnockoutObservable<string>;
    isEnable: KnockoutObservable<boolean>;
    isEditable: KnockoutObservable<boolean>;
    created() {
      const vm = this;
      vm.selectedValue = ko.observable(true);
      vm.itemList = ko.observableArray([
        new ItemModel('1', '基本給'),
        new ItemModel('2', '役職手当'),
        new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
      ]);

      vm.selectedCode = ko.observable('1');
      vm.isEnable = ko.observable(true);
      vm.isEditable = ko.observable(true);
      vm.setDefault();
    }

    setDefault() {
    }

    save() {
      console.log("Hello");
    }

  }
}