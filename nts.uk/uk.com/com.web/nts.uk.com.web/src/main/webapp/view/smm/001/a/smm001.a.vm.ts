/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.a {

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
  class ViewModel extends ko.ViewModel {
    tabs: KnockoutObservableArray<any>;
    selectedTab: KnockoutObservable<string>;
    checked: KnockoutObservable<boolean>;
    enable: KnockoutObservable<boolean>;
    selectedValue: KnockoutObservable<boolean>;
    itemList: KnockoutObservableArray<ItemModel>;
    selectedCode: KnockoutObservable<string>;
    isEnable: KnockoutObservable<boolean>;
    isEditable: KnockoutObservable<boolean>;
    enumSmileCooperationAcceptanceItem: KnockoutObservableArray<any>;
    enumDoOrDoNot: KnockoutObservableArray<any>;
    ORGANIZATION_INFORMATION: KnockoutObservable<string>;
    BASIC_PERSONNEL_INFORMATION: KnockoutObservable<string>;
    JOB_STRUCTURE_INFORMATION: KnockoutObservable<string>;
    ADDRESS_INFORMATION: KnockoutObservable<string>;
    LEAVE_INFORMATION: KnockoutObservable<string>;
    AFFILIATED_MASTER: KnockoutObservable<string>;
    EMPLOYEE_MASTER: KnockoutObservable<string>;
    enumDoOrDoNot2: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;
    enumPaymentCategoryList: KnockoutObservableArray<any>;

    created() {
      const vm = this;
      vm.tabs = ko.observableArray([
        { id: 'tab-1', title: vm.$i18n('SMM001_3'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-2', title: vm.$i18n('SMM001_4'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
      ]);
      vm.selectedTab = ko.observable('tab-1');

      vm.checked = ko.observable(true);
      vm.enable = ko.observable(true);
      vm.selectedValue = ko.observable(true);
      vm.itemList = ko.observableArray([
        new ItemModel('1', '基本給'),
        new ItemModel('2', '役職手当'),
        new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
      ]);

      vm.selectedCode = ko.observable('1');
      vm.isEnable = ko.observable(true);
      vm.isEditable = ko.observable(false);

      vm.setDefault();
    }

    setDefault() {
      const vm = this;
      // Init Do Or DoNot Enum
      vm.enumDoOrDoNot2 = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceClassification);
      console.log(">>> 2 ", vm.enumDoOrDoNot2());
      vm.DO_NOT_TEXT = ko.observable(vm.enumDoOrDoNot2()[0].name);
      vm.DO_TEXT = ko.observable(vm.enumDoOrDoNot2()[1].name);

      // Init info item
      vm.enumSmileCooperationAcceptanceItem = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceItem);
      console.log(">>> ", vm.enumSmileCooperationAcceptanceItem());
      vm.ORGANIZATION_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[0].name);
      vm.BASIC_PERSONNEL_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[1].name);
      vm.JOB_STRUCTURE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[2].name);
      vm.ADDRESS_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[3].name);
      vm.LEAVE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[4].name);
      vm.AFFILIATED_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[5].name);
      vm.EMPLOYEE_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[6].name);

      // Init payment category
      vm.enumPaymentCategoryList = ko.observableArray(__viewContext.enums.PaymentCategory);
      console.log(">>> 2 ", vm.enumPaymentCategoryList());

      this.getInitialStartupInformation(null);
    }

    getInitialStartupInformation(param: any) {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax('com', API.getInitialStartupInformation, param).then((response: any[]) => {
        if (response) {
          console.log("response: ", response)
        }
      })
    }

    save() {
      console.log("Hello");
    }

    moveItemToRight() {

    }

    moveItemToLeft() {

    }

  }
}