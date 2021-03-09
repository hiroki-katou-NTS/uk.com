/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.e {

  import ccg = nts.uk.com.view.ccg025.a;
  import model = nts.uk.com.view.ccg025.a.component.model;

  import ccg026 = nts.uk.com.view.ccg026;
  import ROLE_TYPE = ccg026.component.ROLE_TYPE;
  import IPermision = ccg026.component.IPermision;


  const fetch = {
    getRoleInfor: '',
    saveRoleSetting: '',
  };
  
  @bean()
  class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    tabs: KnockoutObservableArray<NtsTabPanelModel>;
    selectedTab: KnockoutObservable<string>;
    basicFunctionControl: KnockoutObservable<number> = ko.observable(0);
    daysList: KnockoutObservableArray<any> = ko.observableArray([]);
    dateSelected: KnockoutObservable<number> = ko.observable(0);

    //ccg025    
    componentViewmodel: ccg.component.viewmodel.ComponentModel;
    listRole: KnockoutObservableArray<model.Role> = ko.observableArray([]);
    roleName: KnockoutObservable<string> = ko.observable(null);

    //ccg026    
    roleId: KnockoutObservable<string> = ko.observable(null);// role id    
    roleType: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.COMPANY_MANAGER);
    roleType1: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.PERSONAL_INFO);
    roleType2: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.PERSONAL_INFO);
    
    permissionCommon : KnockoutObservableArray<IPermision> = ko.observableArray([]);
    permissionByWorkplace : KnockoutObservableArray<IPermision> = ko.observableArray([]);
    permissionByIndividual : KnockoutObservableArray<IPermision> = ko.observableArray([]);

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

      vm.initialCCG025026();

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

    
    initialCCG025026() {

      const vm = this;
      //ccg025
      vm.componentViewmodel = new ccg.component.viewmodel.ComponentModel({
        roleType: 3,
        multiple: false,
        isAlreadySetting: false
      });

      vm.getDataCCG025().done(() => { });
      vm.componentViewmodel.currentCode.subscribe((roleId) => {
        if (vm.listRole().length <= 0) vm.listRole(vm.componentViewmodel.listRole());

        vm.roleId(roleId);
        vm.findRole(roleId);
      });

      vm.roleId.subscribe((newRoleId) => {
        //vm.getRoleInfor(newRoleId);
      });       
    }

    changeDataCommon = (data: Array<IPermision>) => {
      const vm = this;
      vm.permissionCommon(data);
      console.log(data);
    }

    changeDataByWorkplace = (data: Array<IPermision>) => {
      const vm = this;
      vm.permissionByWorkplace(data);
      console.log(data);
    }

    changeDataByIndividual = (data: Array<IPermision>) => {
      const vm = this;
      vm.permissionByIndividual(data);
      console.log(data);
    }

    getRoleInfor(roleId?: string) {
      const vm = this;
      vm.$ajax(fetch.saveRoleSetting, { roleId: roleId}).done(() => {

      });
    }
  }
}