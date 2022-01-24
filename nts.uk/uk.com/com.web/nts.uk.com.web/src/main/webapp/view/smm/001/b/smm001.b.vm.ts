/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.smm001.b {

  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    getInformationOnExternal: 'com/screen/smm001/get-information-on-external',

    // getCndSet
    getCndSet: "exio/exo/condset/getCndSet",
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
      console.log(">>> 2 ", vm.enumDoOrDoNot2());
      vm.DO_NOT_TEXT = ko.observable(vm.enumDoOrDoNot2()[0].name);
      vm.DO_TEXT = ko.observable(vm.enumDoOrDoNot2()[1].name);

      // Init payment category
      vm.enumPaymentCategoryList = ko.observableArray(__viewContext.enums.PaymentCategory);
      console.log(">>> 2 ", vm.enumPaymentCategoryList());

      vm.getInformationOnExternal();
      vm.getCndSet();
    }

    getInformationOnExternal() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax('com', API.getInformationOnExternal).then((response: any) => {
        if (response) {
          console.log("response >>>: ", response)
          const smileLinkageOutputSetting = response.smileLinkageOutputSetting;
          vm.salaryCooperationClassification(smileLinkageOutputSetting.salaryCooperationClassification);

          vm.monthlyLockClassification(smileLinkageOutputSetting.monthlyLockClassification === 0);
          vm.monthlyApprovalCategory(smileLinkageOutputSetting.monthlyApprovalCategory === 0);
          vm.salaryCooperationConditions(smileLinkageOutputSetting.salaryCooperationConditions);
          // monthlyApprovalCategory: 0
          // monthlyLockClassification: 0
          // salaryCooperationClassification: 0
          // salaryCooperationConditions: "00000"
        }
      })
    }

    // getCndSet
    getCndSet() {
      const vm = this;
      vm.$blockui('show');
      vm.itemListCndSet().push({
        code: '0',
        name: ''
      })
      const param = { "inChargeRole": ["ROLE_ID_3_000000000007-0002"], "empRole": ["ROLE_ID_3_000000000007-0002"] }

      vm.$ajax('com', API.getCndSet, param).then((response: any) => {
        response = [
          {
            "standardAtr": 1,
            "cid": "000000000007-0002",
            "userId": null,
            "conditionSetCode": "00001",
            "categoryId": 100,
            "delimiter": 1,
            "itemOutputName": 0,
            "autoExecution": 1,
            "conditionSetName": "HangT",
            "conditionOutputName": 0,
            "stringFormat": 0
          },
          {
            "standardAtr": 1,
            "cid": "000000000007-0002",
            "userId": null,
            "conditionSetCode": "00002",
            "categoryId": 603,
            "delimiter": 1,
            "itemOutputName": 0,
            "autoExecution": 1,
            "conditionSetName": "HangT",
            "conditionOutputName": 0,
            "stringFormat": 0
          },
          {
            "standardAtr": 1,
            "cid": "000000000007-0002",
            "userId": null,
            "conditionSetCode": "00023",
            "categoryId": 100,
            "delimiter": 1,
            "itemOutputName": 0,
            "autoExecution": 1,
            "conditionSetName": "nw",
            "conditionOutputName": 0,
            "stringFormat": 0
          },
          {
            "standardAtr": 1,
            "cid": "000000000007-0002",
            "userId": null,
            "conditionSetCode": "00123",
            "categoryId": 100,
            "delimiter": 1,
            "itemOutputName": 0,
            "autoExecution": 1,
            "conditionSetName": "data export",
            "conditionOutputName": 0,
            "stringFormat": 0
          }
        ]
        console.log("response getCndSet>>>: ", response)
        const finalArray = response.map((obj: any) => {
          return {
            code: obj.conditionSetCode,
            name: obj.conditionSetName
          };
        });
        vm.itemListCndSet().push(...finalArray);

      })
    }

    moveItemToRight() {

    }

    moveItemToLeft() {

    }

  }
}