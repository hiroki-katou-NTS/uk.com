module nts.uk.com.view.smm001.b {
  const API = {
    // <<ScreenQuery>> 初期起動の情報取得する
    getInitialStartupInformation: 'com/screen/smm001/get-initial-startup-information',
    getInformationOnExternal: 'com/screen/smm001/get-information-on-external',
    registerSmileLinkageExternalOutput: 'com/screen/smm001/register-smile-linkage-external-output',
    selectListPaymentDate: 'com/screen/smm001/select-list-payment-date',
    selectAPaymentDate: 'com/screen/smm001/select-a-payment-date'

  };

  class GridItem {
    code: string
    name: string;
    empMonth: string
    checked: KnockoutObservable<number> = ko.observable(0);
    index: number = 0;
    currentMonth: string = '';
    lastMonth: string = '';

    constructor(code: string, name: string, currentMonth: string, lastMonth: string, checkedString?: number, index?: number) {
      const vm = this;
      vm.checked(checkedString);
      vm.code = code;
      vm.name = name;
      vm.index = index;
      vm.currentMonth = currentMonth;
      vm.lastMonth = lastMonth;

      vm.empMonth =
        `
          <div class="flex group-check-lock-classification" style="height: 22px">
            <label class="radio-emp"
              data-bind="ntsRadioButton: { checked: rightEmployments()[${vm.index}].checked, optionText: '${currentMonth}', checkedValue: 0, group: 'lockClassification-${vm.code}' }"></label>
            <label class="radio-emp"
              data-bind="ntsRadioButton: { checked: rightEmployments()[${vm.index}].checked, optionText: '${lastMonth}', checkedValue: 1, group: 'lockClassification-${vm.code}' }"></label>
          </div>
        `;
    }

    updateEmpMonth() {
      const vm = this;
      vm.empMonth =
        `
          <div class="flex group-check-lock-classification" style="height: 22px">
            <label class="radio-emp"
              data-bind="ntsRadioButton: { checked: rightEmployments()[${vm.index}].checked, optionText: '${vm.currentMonth}', checkedValue: 0, group: 'lockClassification-${vm.code}' }"></label>
            <label class="radio-emp"
              data-bind="ntsRadioButton: { checked: rightEmployments()[${vm.index}].checked, optionText: '${vm.lastMonth}', checkedValue: 1, group: 'lockClassification-${vm.code}' }"></label>
          </div>
        `;
    }

    defaultSelect() {
      const vm = this;
      vm.checked(0);
    }
  }

  export class ScreenModelB extends ko.ViewModel {
    // Start: Init b screen
    paymentCode: KnockoutObservable<number> = ko.observable(1);
    itemListCndSet: KnockoutObservableArray<any> = ko.observableArray([]);
    enumDoOrDoNotArray: KnockoutObservableArray<any>;
    DO_TEXT: KnockoutObservable<string>;
    DO_NOT_TEXT: KnockoutObservable<string>;
    salaryCooperationClassification: KnockoutObservable<boolean> = ko.observable(false);
    monthlyLockClassification: KnockoutObservable<number> = ko.observable(0);
    monthlyApprovalCategory: KnockoutObservable<number> = ko.observable(0);
    salaryCooperationConditions: KnockoutObservable<string> = ko.observable('0');
    employmentDtos: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    rightEmployments: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    isDisableRightButton: KnockoutComputed<boolean> = ko.computed(() => {
      // Check status of button
      return _.isEmpty(this.rightEmployments());
    }); // Check disable button when list is empty
    isDisableLeftButton: KnockoutComputed<boolean> = ko.computed(() => {
      return _.isEmpty(this.employmentDtos());
    }); // Check disable button when list is empty
    ENUM_IS_CHECKED = 1;
    ENUM_IS_NOT_CHECKED = 0;
    enumPaymentCategoryList: KnockoutObservableArray<any>;
    enumLinkedMonthSettingClassification: KnockoutObservableArray<any>;
    CURRENT_MONTH_TEXT: KnockoutObservable<string>;
    LAST_MONTH_TEXT: KnockoutObservable<string>;
    currentCode: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    currentCodeRight: KnockoutObservableArray<GridItem> = ko.observableArray([]);
    columnEmp: KnockoutObservableArray<any> = ko.observableArray([]);
    employmentListWithSpecifiedCompany: KnockoutObservableArray<any> = ko.observableArray([]);
    empListTemp: any = [];
    resB: KnockoutObservable<string> = ko.observable("");
    // End: Init b screen

    constructor() {
      super();
      const vm = this;
      vm.setDefault();
      vm.onChangePaymentCode();
      vm.paymentCode.valueHasMutated();
    }

    findEmp(empListTemp: any, scd: any) {
      const emp = _.find(empListTemp, (e: any) => {
        return (e.employmentCode === scd);
      })
      return _.isUndefined(emp) ? nts.uk.resource.getText("SMM001_17") : emp.employmentName;
    }

    onChangePaymentCode() {
      const vm = this;
      vm.paymentCode.subscribe(value => {
        vm.rightEmployments([]);
        vm.$blockui('grayout')
          .then(() => vm.$ajax('com', `${API.selectAPaymentDate}/${value}`))
          .then(response => {
            const filterEmp = _.orderBy(response.employmentListWithSpecifiedCompany, ['scd'], ['asc']);
            if (_.isEmpty(filterEmp)) {
              vm.rightEmployments([]);
              vm.employmentDtos.valueHasMutated();
              vm.rightEmployments.valueHasMutated();
              vm.reloadRightGrid();
              return;
            }
            const employmentDtos = _.map(filterEmp, (item: any, index: number) =>
              new GridItem(item.scd, vm.findEmp(vm.empListTemp, item.scd),
                vm.CURRENT_MONTH_TEXT(),
                vm.LAST_MONTH_TEXT(),
                item.interlockingMonthAdjustment === 'CURRENT_MONTH' ? 0 : 1,
                index)
            );
            vm.rightEmployments().push(...employmentDtos);
            vm.employmentDtos.valueHasMutated();
            vm.rightEmployments.valueHasMutated();
            vm.reloadRightGrid();
          })
          .fail(error => vm.$dialog.error(error))
          .always(() => vm.$blockui('clear'));
      });
    }

    setDefault() {
      const vm = this;
      // Init Do Or DoNot Enum
      vm.enumDoOrDoNotArray = ko.observableArray(__viewContext.enums.SmileCooperationAcceptanceClassification);
      vm.enumLinkedMonthSettingClassification = ko.observableArray(__viewContext.enums.LinkedMonthSettingClassification);
      vm.CURRENT_MONTH_TEXT = ko.observable(vm.enumLinkedMonthSettingClassification()[0].name);
      vm.LAST_MONTH_TEXT = ko.observable(vm.enumLinkedMonthSettingClassification()[1].name);
      vm.DO_NOT_TEXT = ko.observable(vm.enumDoOrDoNotArray()[0].name);
      vm.DO_TEXT = ko.observable(vm.enumDoOrDoNotArray()[1].name);
      // Init payment category
      vm.enumPaymentCategoryList = ko.observableArray(__viewContext.enums.PaymentCategory);
      vm.getInformationOnExternal();

      vm.columnEmp([
        { headerText: nts.uk.resource.getText("SMM001_14"), key: 'code', width: 50 },
        { headerText: nts.uk.resource.getText("SMM001_15"), key: 'name', width: 200 },
        {
          headerText: nts.uk.resource.getText("SMM001_16"),
          key: 'empMonth',
          dataType: 'string',
          width: 400
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
          const stdOutputCondSetDtos = response.stdOutputCondSetDtos;
          const isNullOrEmpty = stdOutputCondSetDtos === null || stdOutputCondSetDtos.length === 0;
          if (isNullOrEmpty) {
            vm.$dialog.info({ messageId: "Msg_3270" });
          }
          stdOutputCondSetDtos.forEach((obj: any) => {
            finalArray.push({
              code: obj.conditionSetCode,
              name: obj.conditionSetName
            });
          });
          vm.itemListCndSet(finalArray);
          const smileLinkageOutputSetting = response.smileLinkageOutputSetting;
          vm.salaryCooperationClassification(smileLinkageOutputSetting.salaryCooperationClassification === 1);
          const value = _.cloneDeep(smileLinkageOutputSetting.salaryCooperationConditions);
          const existCondSetDto = _.find(stdOutputCondSetDtos, (e: any) => {
            return (e.conditionSetCode === value);
          })
          if (!isNullOrEmpty && vm.salaryCooperationClassification() === true && _.isUndefined(existCondSetDto)) {
            vm.$dialog.info({ messageId: "Msg_3266" });
          }
          vm.salaryCooperationConditions(value);
          vm.monthlyLockClassification(smileLinkageOutputSetting.monthlyLockClassification);
          vm.monthlyApprovalCategory(smileLinkageOutputSetting.monthlyApprovalCategory);
          // After has response - Process for setting after
          vm.empListTemp = [...response.employmentDtos];
          vm.initGetAllListEmpByListPaymentCode(response.employmentDtos);
          vm.reloadRightGrid();
        }
      })
    }

    /**
     * initGetAllListEmpByListPaymentCode
     */
    initGetAllListEmpByListPaymentCode(empList: any) {
      const vm = this;
      const listPaymentCode = vm.enumPaymentCategoryList().map(e => e.value);
      vm.$ajax('com', API.selectListPaymentDate, listPaymentCode)
        .then((res: any) => {
          let listEmploymentListWithSpecifiedCompany: any[] = [];
          res.forEach((e: any) => {
            listEmploymentListWithSpecifiedCompany.push(...e.employmentListWithSpecifiedCompany);
          });
          vm.employmentListWithSpecifiedCompany(vm.unique(listEmploymentListWithSpecifiedCompany));
          const oldEmp = [...empList];
          // After get by list selected code
          const unchosen = vm.getListElementSelectedNotContainInArray(oldEmp, vm.employmentListWithSpecifiedCompany());
          const employmentDtos = _.map(unchosen, (item: any) =>
            new GridItem(item.employmentCode, item.employmentName, vm.CURRENT_MONTH_TEXT(), vm.LAST_MONTH_TEXT())
          );
          vm.employmentDtos(employmentDtos);
        }).fail((err) => {
          vm.$dialog.error(err);
        }).always(() => vm.$blockui('clear'));
    }

    getListElementSelectedNotContainInArray(possible: any, selected: any) {
      const listScd = selected.map((e: any) => e.scd);
      const unchosen = possible.filter((itm: any) => {
        return listScd.includes(itm.employmentCode) == false;
      });
      return unchosen;
    }

    unique(arr: any[]) {
      let newArr: any = [];
      for (var i = 0; i < arr.length; i++) {
        if (!newArr.includes(arr[i])) {
          newArr.push(arr[i])
        }
      }
      return newArr
    }

    validateBeforeSave() {
      const vm = this;
      if (vm.salaryCooperationClassification() && vm.salaryCooperationConditions() === '0') {
        return false;
      }
      return true;
    }

    registerRegisterSmileLinkageExternalIOutput(): any {
      const vm = this;
      if (this.validateBeforeSave() === false) {
        vm.resB("");
        vm.$dialog.info({ messageId: "Msg_3252" });
      } else {
        vm.$blockui('grayout');
        const command = {
          paymentCode: vm.paymentCode(),
          salaryCooperationClassification: vm.salaryCooperationClassification() ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
          monthlyLockClassification: vm.monthlyLockClassification() == 1 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
          monthlyApprovalCategory: vm.monthlyApprovalCategory() == 1 ? vm.ENUM_IS_CHECKED : vm.ENUM_IS_NOT_CHECKED,
          salaryCooperationConditions: vm.salaryCooperationConditions(),
          rightEmployments: vm.convertToRightEmploymentsList()
        };
        vm.$ajax('com', API.registerSmileLinkageExternalOutput, command)
          .then((res: any) => {
            //vm.$dialog.info({ messageId: "Msg_15" });
            vm.resB("Msg_15");
          }).fail((err) => {
            vm.$dialog.error(err);
          }).always(() => vm.$blockui('clear'));
      }
    }

    convertToRightEmploymentsList() {
      const vm = this;
      let rightEmployments: any = [];
      vm.rightEmployments().forEach((e: any) => {
        rightEmployments.push({
          interlockingMonthAdjustment: e.checked(),
          scd: e.code
        })
      });
      return rightEmployments;
    }

    moveItemToRight() {
      const vm = this;
      const filterEmp: GridItem[] = _.filter(vm.employmentDtos(), (item: any) => _.includes(vm.currentCode(), item.code));
      _.map(filterEmp, emp => emp.defaultSelect());
      _.remove(vm.employmentDtos(), (item: any) => _.includes(vm.currentCode(), item.code));
      let rightEmployments = _.cloneDeep(vm.rightEmployments());
      rightEmployments.push(...filterEmp);
      rightEmployments = _.orderBy(rightEmployments, ['code'], ['asc']);
      vm.employmentDtos(_.orderBy(vm.employmentDtos(), ['code'], ['asc']));
      _.map(rightEmployments, (item, index) => {
        item.index = index;
        item.updateEmpMonth();
      });
      vm.rightEmployments(rightEmployments);
      vm.employmentDtos.valueHasMutated();
      vm.reloadRightGrid();
    }

    moveItemToLeft() {
      const vm = this;
      const filterEmp: GridItem[] = _.filter(vm.rightEmployments(), (item: any) => _.includes(vm.currentCodeRight(), item.code));
      let employmentDtos = _.cloneDeep(vm.employmentDtos());
      let rightEmployments = _.cloneDeep(vm.rightEmployments());
      _.remove(rightEmployments, (item: any) => _.includes(vm.currentCodeRight(), item.code));
      employmentDtos.push(...filterEmp);
      employmentDtos = _.orderBy(employmentDtos, ['code'], ['asc']);
      rightEmployments = _.orderBy(rightEmployments, ['code'], ['asc']);
      _.map(rightEmployments, (item, index) => {
        item.index = index;
        item.updateEmpMonth();
      });
      vm.employmentDtos(employmentDtos);
      vm.rightEmployments(rightEmployments);
      vm.reloadRightGrid();
    }
  }
}