/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.smm001.a {
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

      // Start: Init info 7 item
      vm.enumSmileCooperationAcceptanceItem = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceItem);
      vm.ORGANIZATION_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[0].name);
      vm.BASIC_PERSONNEL_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[1].name);
      vm.JOB_STRUCTURE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[2].name);
      vm.ADDRESS_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[3].name);
      vm.LEAVE_INFORMATION = ko.observable(vm.enumSmileCooperationAcceptanceItem()[4].name);
      vm.AFFILIATED_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[5].name);
      vm.EMPLOYEE_MASTER = ko.observable(vm.enumSmileCooperationAcceptanceItem()[6].name);
      // End: Init info 7 item
      vm.getInitialStartupInformation();
    }

    /**
     * Check has element in
     * @param possible List data get from api
     * @param selected List data selected in screen
     * @returns
     */
    validateIfElementSelectedNotContainInArray(possible: any, selected: any) {
      const possibleCode = possible.map((e:any) => e.cooperationAcceptanceConditions);
      const unchosen = selected.filter((itm: any) => {
        return possibleCode.includes(itm) == false;
      });
      return unchosen.length > 0;
    }

    // Data when init screen
    getInitialStartupInformation() {
      const vm = this;
      vm.$blockui('grayout');
      vm.itemList().push({
        code: '0',
        name: ''
      })
      vm.$ajax('com', API.getInitialStartupInformation).then((response: any) => {
        if (response) {
          // Get list data select option a screen
          const externalImportSettings = response.externalImportSettings;
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
            vm.mappingDataAfterGetInitAScreen(smileCooperationAcceptanceSettings);
            // Check logic after mapping data to validate exist in select option
            const listValueSelected = [
              vm.selectedOrganizationInformation(),
              vm.selectedBasicPersonnelInformation(),
              vm.selectedJobStructureInformation(),
              vm.selectedAddressInformation(),
              vm.selectedLeaveInformation(),
              vm.selectedAffiliatedMaster(),
              vm.selectedEmployeeMaster(),
            ];
            if (vm.validateIfElementSelectedNotContainInArray(smileCooperationAcceptanceSettings, listValueSelected)) {
              vm.$dialog.info({ messageId: "Msg_3265" });
            }
          }
        }
      })
    }

    /**
     * return data from list smile
     * @param smileCooperationAcceptanceSettings
     */
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
      if (vm.checkedOrganizationInformation() && vm.selectedOrganizationInformation() === '0'
        || vm.checkedBasicPersonnelInformation() && vm.selectedBasicPersonnelInformation() === '0'
        || vm.checkedJobStructureInformation() && vm.selectedJobStructureInformation() === '0'
        || vm.checkedAddressInformation() && vm.selectedAddressInformation() === '0'
        || vm.checkedLeaveInformation() && vm.selectedLeaveInformation() === '0'
        || vm.checkedAffiliatedMaster() && vm.selectedAffiliatedMaster() === '0'
        || vm.checkedEmployeeMaster() && vm.selectedEmployeeMaster() === '0') {
        return false;
      }
      return true;
    }

    setDefaultBeforeSave() {
      const vm = this;
      // if (vm.checkedOrganizationInformation() && vm.selectedOrganizationInformation() === '0'
      //   || vm.checkedBasicPersonnelInformation() && vm.selectedBasicPersonnelInformation() === '0'
      //   || vm.checkedJobStructureInformation() && vm.selectedJobStructureInformation() === '0'
      //   || vm.checkedAddressInformation() && vm.selectedAddressInformation() === '0'
      //   || vm.checkedLeaveInformation() && vm.selectedLeaveInformation() === '0'
      //   || vm.checkedAffiliatedMaster() && vm.selectedAffiliatedMaster() === '0'
      //   || vm.checkedEmployeeMaster() && vm.selectedEmployeeMaster() === '0') {
      //   return false;
      // }
      // return true;
    }

    saveSmile() {
      const vm = this;
      if (this.validateBeforeSave() === false) {
        vm.$dialog.info({ messageId: "Msg_3250" });
        return;
      }
      vm.$blockui('grayout');
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
          vm.$dialog.info({ messageId: "Msg_15" });
        }).fail((err) => {
          vm.$dialog.error(err);
        }).always(() => vm.$blockui('clear'));

        vm.screenB.saveCommandBScreen();
    }
  }
}