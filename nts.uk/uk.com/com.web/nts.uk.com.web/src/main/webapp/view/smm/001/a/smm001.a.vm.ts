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
    tabs: KnockoutObservableArray<any>;
    selectedTab: KnockoutObservable<string>;
    checked: KnockoutObservable<boolean>;
    enable: KnockoutObservable<boolean>;

    itemList: KnockoutObservableArray<ItemModel>;
    selectedCode: KnockoutObservable<string>;
    isEnable: KnockoutObservable<boolean>;
    isEditable: KnockoutObservable<boolean>;
    enumSmileCooperationAcceptanceItem: KnockoutObservableArray<any>;
    ORGANIZATION_INFORMATION: KnockoutObservable<string>;
    BASIC_PERSONNEL_INFORMATION: KnockoutObservable<string>;
    JOB_STRUCTURE_INFORMATION: KnockoutObservable<string>;
    ADDRESS_INFORMATION: KnockoutObservable<string>;
    LEAVE_INFORMATION: KnockoutObservable<string>;
    AFFILIATED_MASTER: KnockoutObservable<string>;
    EMPLOYEE_MASTER: KnockoutObservable<string>;
    created() {
      const vm = this;
      vm.tabs = ko.observableArray([
        { id: 'tab-1', title: vm.$i18n('SMM001_3'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-2', title: vm.$i18n('SMM001_4'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
      ]);
      vm.selectedTab = ko.observable('tab-1');

      vm.checked = ko.observable(true);
      vm.enable = ko.observable(true);

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
      const vm = this;
      vm.enumSmileCooperationAcceptanceItem = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceItem);
      console.log(">>> ", vm.enumSmileCooperationAcceptanceItem());
      vm.ORGANIZATION_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[0].name);
      vm.BASIC_PERSONNEL_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[1].name);
      vm.JOB_STRUCTURE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[2].name);
      vm.ADDRESS_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[3].name);
      vm.LEAVE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[4].name);
      vm.AFFILIATED_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[5].name);
      vm.EMPLOYEE_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[6].name);
    }

    save() {
      console.log("Hello");
    }

  }
}