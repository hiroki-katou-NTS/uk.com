module nts.uk.at.view.smm001.b {
  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    getInformationOnExternal: 'com/screen/smm001/get-information-on-external',
    registerSmileLinkageExternalOutput: 'com/screen/smm001/register-smile-linkage-external-output'
  };

  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class ScreenModelB extends ko.ViewModel {
    selectedCode: KnockoutObservable<number> = ko.observable(1);
    itemListCndSet: KnockoutObservableArray<any> = ko.observableArray([]);

    enumDoOrDoNot2: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;

    // Start: Init b screen
    salaryCooperationClassification: KnockoutObservable<boolean> = ko.observable(false);
    monthlyLockClassification: KnockoutObservable<number> = ko.observable(0);
    monthlyApprovalCategory: KnockoutObservable<number> = ko.observable(0);
    salaryCooperationConditions: KnockoutObservable<string> = ko.observable();
    // End: Init b screen

    ENUM_IS_CHECKED = 1;
    ENUM_IS_NOT_CHECKED = 0;
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
      let finalArray = [{
        code: '0',
        name: ''
      }];
      vm.$ajax('com', API.getInformationOnExternal).then((response: any) => {
        if (response) {
          console.log("response >>>: ", response)
          const smileLinkageOutputSetting = response.smileLinkageOutputSetting;
          vm.salaryCooperationClassification(smileLinkageOutputSetting.salaryCooperationClassification === 1);
          vm.monthlyLockClassification(smileLinkageOutputSetting.monthlyLockClassification);
          vm.monthlyApprovalCategory(smileLinkageOutputSetting.monthlyApprovalCategory);
          vm.salaryCooperationConditions(smileLinkageOutputSetting.salaryCooperationConditions);
          const stdOutputCondSetDtos = response.stdOutputCondSetDtos;
          stdOutputCondSetDtos.forEach((obj: any) => {
            finalArray.push({
              code: obj.conditionSetCode,
              name: obj.conditionSetName
            });
          });
          vm.itemListCndSet(finalArray);
        }
      })
    }

    validateBeforeSave() {
      const vm = this;
      return !(vm.salaryCooperationClassification() && vm.salaryCooperationConditions() === '0');
    }

    saveCommandBScreen() {
      const vm = this;
      if (this.validateBeforeSave() === false) {
        vm.$dialog.info({ messageId: "Msg_3250" });
        return;
      }
      vm.$blockui('show');
      const command = {
        paymentCode: 1,
        salaryCooperationClassification: vm.salaryCooperationClassification() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        monthlyLockClassification: vm.monthlyLockClassification() == 1 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        monthlyApprovalCategory: vm.monthlyApprovalCategory() == 0 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        salaryCooperationConditions: vm.salaryCooperationConditions()
      };
      vm.$ajax('com', API.registerSmileLinkageExternalOutput, command)
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