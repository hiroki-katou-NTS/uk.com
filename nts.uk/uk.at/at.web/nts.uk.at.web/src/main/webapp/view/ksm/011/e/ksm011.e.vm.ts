/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.e {

  import ccg = nts.uk.com.view.ccg025.a;
  import model = nts.uk.com.view.ccg025.a.component.model;

  @bean()
  class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    tabs: KnockoutObservableArray<NtsTabPanelModel>;
    selectedTab: KnockoutObservable<string>;
    basicFunctionControl: KnockoutObservable<number> = ko.observable(1);
    daysList: KnockoutObservableArray<any> = ko.observableArray([]);
    dateSelected: KnockoutObservable<number> = ko.observable(0);

    //ccg025    
    componentViewmodel: ccg.component.viewmodel.ComponentModel;
    listRole: KnockoutObservableArray<model.Role> = ko.observableArray([]);
    roleName: KnockoutObservable<string> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;

      vm.tabs = ko.observableArray([
        { id: 'tab-1', title: vm.$i18n('KSM011_96'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-2', title: vm.$i18n('KSM011_97'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-3', title: vm.$i18n('KSM011_98'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
      ]);
      vm.selectedTab = ko.observable('tab-1');

      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.getDayList();

      vm.componentViewmodel = new nts.uk.com.view.ccg025.a.component.viewmodel.ComponentModel({
        roleType: 3,
        multiple: false,
        isAlreadySetting: false
      });

      vm.getDataCCG025().done(() => { });
      vm.componentViewmodel.currentCode.subscribe((roleId) => {
        if (vm.listRole().length <= 0) vm.listRole(vm.componentViewmodel.listRole());
        vm.findRole(roleId);
      });

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    saveData() {

    }

    getDayList() {
      const vm = this;
      vm.daysList = ko.observableArray([]);
      let days = [];
      for (let day = 0; day < 30; day++) {
        days.push({ day: day, name: (day + 1) + vm.$i18n('KSM011_105') });
      }
      days.push({ day: 30, name: vm.$i18n('KSM011_106') });
      vm.daysList(days);
    }

    getDataCCG025(): JQueryPromise<any> {
      let vm = this;
      let dfd = $.Deferred();
      vm.componentViewmodel.startPage().done(() => {
        vm.listRole(vm.componentViewmodel.listRole());
      });
      dfd.resolve();
      return dfd.promise();
    }

    findRole(roleId?: string) {

      const vm = this;

      let role = _.find(vm.listRole(), (x) => { return x.roleId === roleId; });
      if (role) vm.roleName(role.roleName);
    }

  }
}