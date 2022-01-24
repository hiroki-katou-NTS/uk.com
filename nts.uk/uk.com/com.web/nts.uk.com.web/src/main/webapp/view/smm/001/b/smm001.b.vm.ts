/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.b {

  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information'
  };

  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }
  @bean()
  export class ScreenModelB extends ko.ViewModel {
    selectedCode: KnockoutObservable<string> = ko.observable();
    isEnable: KnockoutObservable<boolean> = ko.observable(false);
    itemList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedValue: KnockoutObservable<boolean> = ko.observable(false);

    enumDoOrDoNot2: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;

    enumPaymentCategoryList: KnockoutObservableArray<any>;


    constructor() {
      super();
      const vm = this;
      vm.setDefault();
    }

    setDefault() {
      const vm = this;
      // Init Do Or DoNot Enum
      vm.enumDoOrDoNot2 = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceClassification);
      console.log(">>> 2 ", vm.enumDoOrDoNot2());
      vm.DO_NOT_TEXT = ko.observable(vm.enumDoOrDoNot2()[0].name);
      vm.DO_TEXT = ko.observable(vm.enumDoOrDoNot2()[1].name);

      // Init payment category
      vm.enumPaymentCategoryList = ko.observableArray(__viewContext.enums.PaymentCategory);
      console.log(">>> 2 ", vm.enumPaymentCategoryList());
    }

    moveItemToRight() {

    }

    moveItemToLeft() {

    }

  }
}