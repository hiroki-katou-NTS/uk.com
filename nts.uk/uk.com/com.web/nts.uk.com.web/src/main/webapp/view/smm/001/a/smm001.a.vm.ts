/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.a {
  import ScreenModelB = b.ScreenModelB;
  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    registerSmileCooperationAcceptanceSetting: 'com/screen/smm001/register-smile-cooperation-acceptance-setting'
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
  export class ScreenModelA extends ko.ViewModel {
    screenB: ScreenModelB = new ScreenModelB();

    tabs: KnockoutObservableArray<any>;
    selectedTab: KnockoutObservable<string>;

    // Start: Init list checkbox
    checkedOrganizationInformation: KnockoutObservable<boolean> = ko.observable(false);
    checkedBasicPersonnelInformation: KnockoutObservable<boolean> = ko.observable(false);
    checkedJobStructureInformation: KnockoutObservable<boolean> = ko.observable(false);
    checkedAddressInformation: KnockoutObservable<boolean> = ko.observable(false);
    checkedLeaveInformation: KnockoutObservable<boolean> = ko.observable(false);
    checkedAffiliatedMaster: KnockoutObservable<boolean> = ko.observable(false);
    checkedEmployeeMaster: KnockoutObservable<boolean> = ko.observable(false);
    // End: Init list checkbox

    // Start: Selected select option => choose value
    selectedOrganizationInformation: KnockoutObservable<string> = ko.observable(null);
    selectedBasicPersonnelInformation: KnockoutObservable<string> = ko.observable(null);
    selectedJobStructureInformation: KnockoutObservable<string> = ko.observable(null);
    selectedAddressInformation: KnockoutObservable<string> = ko.observable(null);
    selectedLeaveInformation: KnockoutObservable<string> = ko.observable(null);
    selectedAffiliatedMaster: KnockoutObservable<string> = ko.observable(null);
    selectedEmployeeMaster: KnockoutObservable<string> = ko.observable(null);
    // End: Selected select option => choose value

    selectedValue: KnockoutObservable<boolean>;
    itemList: KnockoutObservableArray<ItemModel>;
    isEnable: KnockoutObservable<boolean>;
    enumSmileCooperationAcceptanceItem: KnockoutObservableArray<any>;
    enumDoOrDoNot: KnockoutObservableArray<any>;
    ORGANIZATION_INFORMATION: KnockoutObservable<string>;
    BASIC_PERSONNEL_INFORMATION: KnockoutObservable<string>;
    JOB_STRUCTURE_INFORMATION: KnockoutObservable<string>;
    ADDRESS_INFORMATION: KnockoutObservable<string>;
    LEAVE_INFORMATION: KnockoutObservable<string>;
    AFFILIATED_MASTER: KnockoutObservable<string>;
    EMPLOYEE_MASTER: KnockoutObservable<string>;

    ENUM_IS_CHECKED = 1;
    ENUM_IS_NOT_CHECKED = 0;

    created() {
      const vm = this;
      vm.tabs = ko.observableArray([
        { id: 'tab-1', title: vm.$i18n('SMM001_3'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-2', title: vm.$i18n('SMM001_4'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
      ]);
      vm.selectedTab = ko.observable('tab-1');

      vm.selectedValue = ko.observable(true);
      vm.itemList = ko.observableArray([]);

      vm.isEnable = ko.observable(true);

      vm.setDefault();
    }

    setDefault() {
      const vm = this;

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

      this.getInitialStartupInformation(null);
    }

    getInitialStartupInformation(param: any) {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax('com', API.getInitialStartupInformation, param).then((response: any) => {
        if (response) {
          console.log("response: ", response)
          // Get list data select option a screen
          const externalImportSettings = response.externalImportSettings;
          vm.itemList().push({
            code: '0',
            name: ''
          })
          if (!_.isEmpty(externalImportSettings)) {
            vm.itemList().push(...externalImportSettings);
          } else { // when externalImportSettings empty
            vm.$dialog.info({ messageId: "Msg_3249" });
          }
          let smileCooperationAcceptanceSettings = response.smileCooperationAcceptanceSettings;
          if (!_.isEmpty(smileCooperationAcceptanceSettings)) {
            smileCooperationAcceptanceSettings = _.sortBy(
              smileCooperationAcceptanceSettings, ["cooperationAcceptance"]
            );
            this.mappingDataAfterGetInitAScreen(smileCooperationAcceptanceSettings)
          }
        }
      })
    }

    mappingDataAfterGetInitAScreen(smileCooperationAcceptanceSettings: any) {
      const vm = this;
      vm.checkedOrganizationInformation(smileCooperationAcceptanceSettings[0].cooperationAcceptanceClassification == 1)
      vm.selectedOrganizationInformation(smileCooperationAcceptanceSettings[0].cooperationAcceptanceConditions)

      vm.checkedBasicPersonnelInformation(smileCooperationAcceptanceSettings[1].cooperationAcceptanceClassification == 1)
      vm.selectedBasicPersonnelInformation(smileCooperationAcceptanceSettings[1].cooperationAcceptanceConditions)

      vm.checkedJobStructureInformation(smileCooperationAcceptanceSettings[2].cooperationAcceptanceClassification == 1)
      vm.selectedJobStructureInformation(smileCooperationAcceptanceSettings[2].cooperationAcceptanceConditions)

      vm.checkedAddressInformation(smileCooperationAcceptanceSettings[3].cooperationAcceptanceClassification == 1)
      vm.selectedAddressInformation(smileCooperationAcceptanceSettings[3].cooperationAcceptanceConditions)

      vm.checkedLeaveInformation(smileCooperationAcceptanceSettings[4].cooperationAcceptanceClassification == 1)
      vm.selectedLeaveInformation(smileCooperationAcceptanceSettings[4].cooperationAcceptanceConditions)

      vm.checkedAffiliatedMaster(smileCooperationAcceptanceSettings[5].cooperationAcceptanceClassification == 1)
      vm.selectedAffiliatedMaster(smileCooperationAcceptanceSettings[5].cooperationAcceptanceConditions)

      vm.checkedEmployeeMaster(smileCooperationAcceptanceSettings[6].cooperationAcceptanceClassification == 1)
      vm.selectedEmployeeMaster(smileCooperationAcceptanceSettings[6].cooperationAcceptanceConditions)
    }

    validateBeforeSave() {
      const vm = this;
      if (vm.checkedOrganizationInformation() && vm.selectedOrganizationInformation().code === '0') {
        return false;
      }
      if (vm.checkedBasicPersonnelInformation() && vm.selectedBasicPersonnelInformation().code === '0') {
        return false;
      }
      if (vm.checkedJobStructureInformation() && vm.selectedJobStructureInformation().code === '0') {
        return false;
      }
      if (vm.checkedAddressInformation() && vm.selectedAddressInformation().code === '0') {
        return false;
      }
      if (vm.checkedLeaveInformation() && vm.selectedLeaveInformation().code === '0') {
        return false;
      }
      if (vm.checkedAffiliatedMaster() && vm.selectedAffiliatedMaster().code === '0') {
        return false;
      }
      if (vm.checkedEmployeeMaster() && vm.selectedEmployeeMaster().code === '0') {
        return false;
      }
      return true;
    }

    saveSmile() {
      if (this.validateBeforeSave() === false) {
        return;
      }
      const vm = this;
      vm.$blockui('show');
      const command = {
        paymentCode: 1,
        checkedOrganizationInformation:
          vm.checkedOrganizationInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedBasicPersonnelInformation:
          vm.checkedOrganizationInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedJobStructureInformation:
          vm.checkedJobStructureInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedAddressInformation: vm.checkedAddressInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedLeaveInformation: vm.checkedLeaveInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedAffiliatedMaster: vm.checkedAffiliatedMaster() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        checkedEmployeeMaster: vm.checkedEmployeeMaster() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,

        selectedOrganizationInformation: vm.selectedOrganizationInformation(),
        selectedBasicPersonnelInformation: vm.selectedBasicPersonnelInformation(),
        selectedJobStructureInformation: vm.selectedJobStructureInformation(),
        selectedAddressInformation: vm.selectedAddressInformation(),
        selectedLeaveInformation: vm.selectedLeaveInformation(),
        selectedAffiliatedMaster: vm.selectedAffiliatedMaster(),
        selectedEmployeeMaster: vm.selectedEmployeeMaster(),
      };
      vm.$ajax('com', API.registerSmileCooperationAcceptanceSetting, command)
        .then((res: any) => {
            console.log(res);
            vm.$dialog.info({ messageId: "Msg_15" });
        }).fail((err) => {
          vm.$dialog.error(err);
        }).always(() => vm.$blockui('clear'));
    }

    moveItemToRight() {
    }

    moveItemToLeft() {
    }
  }
}