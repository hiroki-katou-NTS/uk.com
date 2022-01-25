/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.b {

  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    getInformationOnExternal: 'com/screen/smm001/get-information-on-external',
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
    itemListCndSet: KnockoutObservableArray<any> = ko.observableArray([]);

    enumDoOrDoNot2: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;

    // Start: Init b screen
    salaryCooperationClassification: KnockoutObservable<boolean> = ko.observable(false);
    monthlyLockClassification: KnockoutObservable<boolean> = ko.observable(false);
    monthlyApprovalCategory: KnockoutObservable<boolean> = ko.observable(false);
    salaryCooperationConditions: KnockoutObservable<string> = ko.observable();
    // End: Init b screen

    ENUM_IS_CHECKED = true;
    ENUM_IS_NOT_CHECKED = false;
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
      vm.DO_NOT_TEXT = ko.observable(vm.enumDoOrDoNot2()[0].name);
      vm.DO_TEXT = ko.observable(vm.enumDoOrDoNot2()[1].name);

      // Init payment category
      vm.enumPaymentCategoryList = ko.observableArray(__viewContext.enums.PaymentCategory);

      vm.getInformationOnExternal();
    }

    getInformationOnExternal() {
      const vm = this;
      vm.$blockui('show');
      vm.itemListCndSet().push({
        code: '0',
        name: ''
      })
      vm.$ajax('com', API.getInformationOnExternal).then((response: any) => {
        if (response) {
          console.log("response >>>: ", response)
          const smileLinkageOutputSetting = response.smileLinkageOutputSetting;
          vm.salaryCooperationClassification(smileLinkageOutputSetting.salaryCooperationClassification);

          vm.monthlyLockClassification(smileLinkageOutputSetting.monthlyLockClassification === 0);
          vm.monthlyApprovalCategory(smileLinkageOutputSetting.monthlyApprovalCategory === 0);
          vm.salaryCooperationConditions(smileLinkageOutputSetting.salaryCooperationConditions);
          const stdOutputCondSetDtos = response.stdOutputCondSetDtos;
          const finalArray = stdOutputCondSetDtos.map((obj: any) => {
            return {
              code: obj.conditionSetCode,
              name: obj.conditionSetName
            };
          });
          vm.itemListCndSet().push(...finalArray);
        }
      })
    }

    moveItemToRight() {

    }

    moveItemToLeft() {

    }

  }
}