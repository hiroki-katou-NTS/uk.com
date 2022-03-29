/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.smm001.a {
  import ScreenModelB = b.ScreenModelB;
  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    registerSmileCooperationAcceptanceSetting: 'com/screen/smm001/register-smile-cooperation-acceptance-setting',
    initDataRegister: 'ctx/link/smile/accept/setting/generatedata'
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
    // Start: Init tabs and screen
    screenB: ScreenModelB = new ScreenModelB();
    tabs: KnockoutObservableArray<any>;
    selectedTab: KnockoutObservable<string>;
    // End: Init tabs and screen

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

    // Start: Init one list
    selectedValue: KnockoutObservable<boolean>;
    itemList: KnockoutObservableArray<ItemModel>;
    isEnable: KnockoutObservable<boolean>;
    // End: Init one list

    // Start: Init enum
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
    resA: KnockoutObservable<string> = ko.observable("");
    // End: Init enum

    // Initial data: A4_1 -> A4_14
    initDataText1:   KnockoutObservableArray<string> = ko.observable("");
    initDataText2:   KnockoutObservableArray<string> = ko.observable("");
    initDataText3:   KnockoutObservableArray<string> = ko.observable("");
    initDataText4:   KnockoutObservableArray<string> = ko.observable("");
    initDataText5:   KnockoutObservableArray<string> = ko.observable("");
    initDataText6:   KnockoutObservableArray<string> = ko.observable("");
    initDataText7:   KnockoutObservableArray<string> = ko.observable("");
      
    created() {
      const vm = this;
      vm.setDefault();
    }

    mounted() {
      $('#checkbox_A2_2 input:not(:disabled)').focus();
    }

    setDefault() {
      const vm = this;
      vm.tabs = ko.observableArray([
        { id: 'tab-1', title: vm.$i18n('SMM001_3'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
        { id: 'tab-2', title: vm.$i18n('SMM001_4'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
      ]);
      vm.selectedTab = ko.observable('tab-1');
      vm.selectedValue = ko.observable(true);
      vm.itemList = ko.observableArray([]);
      vm.isEnable = ko.observable(true);
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
      const possibleCode = possible.map((e: any) => e.cooperationAcceptanceConditions);
      const unchosen = selected.filter((itm: any) => {
        return possibleCode.includes(itm) == false;
      });
      return unchosen.length > 0;
    }

    // Data when init screen
    getInitialStartupInformation() {
      const vm = this;
      vm.$blockui('grayout');
      // Init item list with one item has code = 0
//      vm.itemList().push({
//        code: '0',
//        name: ''
//      })
      // Call API at screen A
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
            const listValueSelected = vm.addListValueSelected();
            if (vm.validateIfElementSelectedNotContainInArray(smileCooperationAcceptanceSettings, listValueSelected)) {
              vm.$dialog.info({ messageId: "Msg_3265" });
            }
          }
        }
      })
        .always(() => $('#checkbox_A2_2 input:not(:disabled)').focus());
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
      // If checkbox is checked and select option set code = 0 => false
      if (vm.checkedOrganizationInformation() && !vm.selectedOrganizationInformation() 
        || vm.checkedBasicPersonnelInformation() && !vm.selectedBasicPersonnelInformation()
        || vm.checkedJobStructureInformation() && !vm.selectedJobStructureInformation()
        || vm.checkedAddressInformation() && !vm.selectedAddressInformation()
        || vm.checkedLeaveInformation() && !vm.selectedLeaveInformation()
        || vm.checkedAffiliatedMaster() && !vm.selectedAffiliatedMaster()
        || vm.checkedEmployeeMaster() && !vm.selectedEmployeeMaster()) {
        return false;
      }
      return true;
    }

    addListValueSelected() {
      let listValueSelected = [];
      const vm = this;
      if (vm.checkedOrganizationInformation()) { listValueSelected.push(vm.selectedOrganizationInformation()) }
      if (vm.checkedBasicPersonnelInformation()) { listValueSelected.push(vm.selectedBasicPersonnelInformation()) }
      if (vm.checkedJobStructureInformation()) { listValueSelected.push(vm.selectedJobStructureInformation()) }
      if (vm.checkedAddressInformation()) { listValueSelected.push(vm.selectedAddressInformation()) }
      if (vm.checkedLeaveInformation()) { listValueSelected.push(vm.selectedLeaveInformation()) }
      if (vm.checkedAffiliatedMaster()) { listValueSelected.push(vm.selectedAffiliatedMaster()) }
      if (vm.checkedEmployeeMaster()) { listValueSelected.push(vm.selectedEmployeeMaster()) }
      return listValueSelected;
    }

    // setDefaultBeforeSave() {
    //   const vm = this;
    //   if (!vm.checkedOrganizationInformation()) { vm.selectedOrganizationInformation('0') }
    //   if (!vm.checkedBasicPersonnelInformation()) { vm.selectedBasicPersonnelInformation('0') }
    //   if (!vm.checkedJobStructureInformation()) { vm.selectedJobStructureInformation('0') }
    //   if (!vm.checkedAddressInformation()) { vm.selectedAddressInformation('0') }
    //   if (!vm.checkedLeaveInformation()) { vm.selectedLeaveInformation('0') }
    //   if (!vm.checkedAffiliatedMaster()) { vm.selectedAffiliatedMaster('0') }
    //   if (!vm.checkedEmployeeMaster()) { vm.selectedEmployeeMaster('0') }
    // }

    /**
     * Event when clicked save button
     */
    registerSmileSetting() {
      const vm = this;
      vm.registerSmileCooperationAcceptanceSetting();
      vm.screenB.registerRegisterSmileLinkageExternalIOutput();
      vm.resA.valueHasMutated();
      vm.screenB.resB.valueHasMutated();
      setTimeout(() => {
        if (
          vm.resA() == "Msg_15" &&
          vm.screenB.resB() == "Msg_15"
        ) {
          vm.$dialog.info({ messageId: "Msg_15" });
        }
      }, 500)
    }

    /**
     * save at A Screen
     */
    registerSmileCooperationAcceptanceSetting(): any {
      const vm = this;
      if (this.validateBeforeSave() === false) {
        vm.resA(null)
        vm.$dialog.info({ messageId: "Msg_3250" });
        return;
      } else {
        // vm.setDefaultBeforeSave();
        vm.$blockui('grayout');
        // Start: Init json body
        const command = {
          checkedOrganizationInformation:
            vm.checkedOrganizationInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
          checkedBasicPersonnelInformation:
            vm.checkedBasicPersonnelInformation() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
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
        // End: Init json body

        // Start: Process send request
        vm.$ajax('com', API.registerSmileCooperationAcceptanceSetting, command)
            .done(() => {
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15", []));
          }).fail((err) => {
            vm.$dialog.error(err);
          }).always(() => {
            vm.$blockui('clear');
            $('#checkbox_A2_2 input:not(:disabled)').focus();
          });
        // End: Process send request
      }
    }
    
    /**
     * event when click init data create button 
     */
    createInitialData(acceptanceItem : number, settingCode: String){
        const vm = this;
        let initData = {
            acceptanceItem : acceptanceItem,
            settingCode : settingCode
        }
        vm.$ajax('com', API.initDataRegister, initData).done(() => {
            // 登録内容のリロード
            vm.getInitialStartupInformation();
            nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_3339", []));
        }).fail((err) => {
            vm.$dialog.error(err);
        }).always(() =>{
            vm.$blockui('clear');
        })
    }
    
    createOrganization(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText1()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }
        $('.init-text1').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(1, vm.initDataText1());
    }
    
    createHuman(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText2()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;    
        }
        $('.init-text2').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(2, vm.initDataText2());        
    }
      
    jobSystem(){   
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText3()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }
        $('.init-text3').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(3, vm.initDataText3());         
    }
      
    address(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText4()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }            
        $('.init-text4').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(4, vm.initDataText4());            
    } 
        
    leaveWork(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText5()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }            
        $('.init-text5').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(5, vm.initDataText5());         
    }

    belongs(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText6()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }            
        $('.init-text6').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(6, vm.initDataText6());         
    }
      
    employee(){
        const vm = this;
        nts.uk.ui.errors.clearAll()
        if(!vm.initDataText7()){
            vm.$dialog.error({ messageId: "Msg_3325" });
            return;
        }            
        $('.init-text7').ntsError('check');
        if(!nts.uk.ui.errors.hasError())
            vm.createInitialData(7, vm.initDataText7());          
    }      
}