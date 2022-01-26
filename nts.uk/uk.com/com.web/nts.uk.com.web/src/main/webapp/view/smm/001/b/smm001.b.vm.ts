module nts.uk.com.view.smm001.b {
  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    getInformationOnExternal: 'com/screen/smm001/get-information-on-external',
    registerSmileLinkageExternalOutput: 'com/screen/smm001/register-smile-linkage-external-output'
  };

  class GridItem {
    code: string
    name: string;
    empMonth: string
    checked: KnockoutObservable<boolean> = ko.observable(false);

    constructor(code: string, name: string, doText: string, doNotText: string) {
      this.code = code;
      this.name = name;
      this.empMonth =
      `
      <div class="flex" style="height: 25px">
        <label class="radio-emp"
          data-bind="ntsRadioButton: { checked: ${this.checked()}, optionText: '${doText}', checkedValue: 1, group: 'lockClassification' }"></label>
        <label class="radio-emp"
          data-bind="ntsRadioButton: { checked: ${this.checked()}, optionText: '${doNotText}', checkedValue: 0, group: 'lockClassification' }"></label>
      </div>
      `
    }
  }

  export class ScreenModelB extends ko.ViewModel {
    paymentCode: KnockoutObservable<number> = ko.observable(1);
    itemListCndSet: KnockoutObservableArray<any> = ko.observableArray([]);

    enumDoOrDoNot2: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;

    // Start: Init b screen
    salaryCooperationClassification: KnockoutObservable<boolean> = ko.observable(false);
    monthlyLockClassification: KnockoutObservable<number> = ko.observable(0);
    monthlyApprovalCategory: KnockoutObservable<number> = ko.observable(0);
    salaryCooperationConditions: KnockoutObservable<string> = ko.observable("");

    employmentDtos: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    rightEmployments: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    // End: Init b screen

    ENUM_IS_CHECKED = 1;
    ENUM_IS_NOT_CHECKED = 0;
    enumPaymentCategoryList: KnockoutObservableArray<any>;
    currentCode: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    currentCodeRight: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    columnEmp: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor() {
      super();
      const vm = this;
      vm.setDefault();
      vm.onChangePaymentCode();
    }

    onChangePaymentCode() {
      const vm = this;
      vm.paymentCode.subscribe(value => {
        vm
          .$blockui('grayout')
          .then(() => vm.$ajax('com', API.getInformationOnExternal, { paymentCode: value }))
          .then(response => {

          })
          .fail(error => vm.$dialog.error(error))
          .always(() => vm.$blockui('clear'));
      });
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

      vm.columnEmp([
        { headerText: nts.uk.resource.getText("SMM001_14"), key: 'code', width: 50 },
        { headerText: nts.uk.resource.getText("SMM001_15"), key: 'name', width: 100 },
        {
          headerText: nts.uk.resource.getText("SMM001_16"),
          key: 'empMonth',
          dataType: 'string',
          width: 250
        }
      ]);
    }

    reloadRightGrid() {
      const vm = this;
      const $nodes = $('.radio-emp');
      _.each($nodes, (item, index) => {
        if (!$('.radio-emp')[index]) return;
        ko.cleanNode($('.radio-emp')[index]);
        ko.applyBindings(vm, item);
      });
    }

    getInformationOnExternal() {
      const vm = this;
      vm.$blockui('grayout');
      let finalArray = [{
        code: '0',
        name: ''
      }];
      vm.$ajax('com', API.getInformationOnExternal).then((response: any) => {
        if (response) {
          // After has response - Process for setting behind
          const smileLinkageOutputSetting = response.smileLinkageOutputSetting;
          vm.salaryCooperationClassification(smileLinkageOutputSetting.salaryCooperationClassification === 1);
          vm.salaryCooperationConditions = ko.observable(smileLinkageOutputSetting.salaryCooperationConditions);

          vm.monthlyLockClassification(smileLinkageOutputSetting.monthlyLockClassification);
          vm.monthlyApprovalCategory(smileLinkageOutputSetting.monthlyApprovalCategory);
          // After has response - Process for setting after
          const stdOutputCondSetDtos = response.stdOutputCondSetDtos;
          stdOutputCondSetDtos.forEach((obj: any) => {
            finalArray.push({
              code: obj.conditionSetCode,
              name: obj.conditionSetName
            });
          });
          vm.itemListCndSet(finalArray);
          const employmentDtos = _.map(response.employmentDtos, (item: any) =>
            new GridItem(item.employmentCode, item.employmentName, vm.DO_TEXT(), vm.DO_NOT_TEXT())
          );
          vm.employmentDtos(employmentDtos);
          vm.reloadRightGrid();
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
      vm.$blockui('grayout');
      const command = {
        paymentCode: vm.paymentCode(),
        salaryCooperationClassification: vm.salaryCooperationClassification() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        monthlyLockClassification: vm.monthlyLockClassification() == 1 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        monthlyApprovalCategory: vm.monthlyApprovalCategory() == 0 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
        salaryCooperationConditions: vm.salaryCooperationConditions()
      };
      vm.$ajax('com', API.registerSmileLinkageExternalOutput, command)
        .then((res: any) => {
          vm.$dialog.info({ messageId: "Msg_15" });
        }).fail((err) => {
          vm.$dialog.error(err);
        }).always(() => vm.$blockui('clear'));
    }

    moveItemToRight() {
      const vm = this;
      const filterEmp: GridItem[] = _.filter(vm.employmentDtos(), (item: any) => _.includes(vm.currentCode(), item.code));
      _.remove(vm.employmentDtos(), (item: any) => _.includes(vm.currentCode(), item.code));
      vm.rightEmployments().push(...filterEmp);
      vm.employmentDtos.valueHasMutated();
      vm.rightEmployments.valueHasMutated();
      vm.reloadRightGrid();
    }

    moveItemToLeft() {
      const vm = this;
      const filterEmp: GridItem[] = _.filter(vm.rightEmployments(), (item: any) => _.includes(vm.rightEmployments(), item.code));
      _.remove(vm.rightEmployments(), (item: any) => _.includes(vm.currentCodeRight(), item.code));
      vm.employmentDtos().push(...filterEmp);
      vm.employmentDtos.valueHasMutated();
      vm.rightEmployments.valueHasMutated();
      vm.reloadRightGrid();
    }
  }
}