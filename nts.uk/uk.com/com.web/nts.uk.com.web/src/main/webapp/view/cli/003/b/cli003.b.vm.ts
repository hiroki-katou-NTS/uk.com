/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cli003.b {
  import service = nts.uk.com.view.cli003.b.service;
  @bean()
  export class ScreenModel extends ko.ViewModel {

    //Log info
    logSets: KnockoutObservableArray<ItemLogSetModel> = ko.observableArray([]);
    logSetId: KnockoutObservable<string> = ko.observable("");
    recordType: KnockoutObservable<string> = ko.observable("");
    dataType: KnockoutObservable<string> = ko.observable("");
    systemType: KnockoutObservable<string> = ko.observable("");

    //Display on screen
    currentLogDisplaySet: KnockoutObservable<ItemLogSetModel> = ko.observable();
    currentRecordTypeName: KnockoutObservable<string> = ko.observable("");
    currentDataTypeName: KnockoutObservable<string> = ko.observable("");
    currentLogSetCode: KnockoutObservable<string> = ko.observable("");
    currentLogSetName: KnockoutObservable<string> = ko.observable("");
    currentSystemTypeName: KnockoutObservable<string> = ko.observable("");
    currentCode: KnockoutObservable<string> = ko.observable(null);
    showOperator: KnockoutObservable<boolean> = ko.observable(false); //show Operator or not
    showDataType: KnockoutObservable<boolean> = ko.observable(false);
    showPersonInfo: KnockoutObservable<boolean> = ko.observable(false);

    //Default list
    recordTypeList: KnockoutObservableArray<ItemTypeModel> = ko.observableArray([
      new ItemTypeModel(0, this.$i18n("Enum_RecordType_Login")),
      new ItemTypeModel(1, this.$i18n("Enum_RecordType_StartUp")),
      new ItemTypeModel(2, this.$i18n("Enum_RecordType_UpdateMaster")),
      new ItemTypeModel(3, this.$i18n("Enum_RecordType_UpdatePersionInfo")),
      new ItemTypeModel(4, this.$i18n("Enum_RecordType_DataReference")),
      new ItemTypeModel(5, this.$i18n("Enum_RecordType_DataManipulation")),
      new ItemTypeModel(6, this.$i18n("Enum_RecordType_DataCorrect")),
      new ItemTypeModel(7, this.$i18n("Enum_RecordType_MyNumber")),
      new ItemTypeModel(8, this.$i18n("Enum_RecordType_TerminalCommucationInfo")),
      new ItemTypeModel(9, this.$i18n("Enum_RecordType_DataStorage")),
      new ItemTypeModel(10, this.$i18n("Enum_RecordType_DataRecovery")),
      new ItemTypeModel(11, this.$i18n("Enum_RecordType_DataDeletion")),
    ]);
    dataTypeList: KnockoutObservableArray<ItemTypeModel> = ko.observableArray([
      new ItemTypeModel(0, this.$i18n("Enum_DataType_Schedule")),
      new ItemTypeModel(1, this.$i18n("Enum_DataType_DailyResults")),
      new ItemTypeModel(2, this.$i18n("Enum_DataType_MonthlyResults")),
      new ItemTypeModel(3, this.$i18n("Enum_DataType_AnyPeriodSummary")),
      new ItemTypeModel(4, this.$i18n("Enum_DataType_ApplicationApproval")),
      new ItemTypeModel(5, this.$i18n("Enum_DataType_Notification")),
      new ItemTypeModel(6, this.$i18n("Enum_DataType_SalaryDetail")),
      new ItemTypeModel(7, this.$i18n("Enum_DataType_BonusDetail")),
      new ItemTypeModel(8, this.$i18n("Enum_DataType_YearEndAdjustment")),
      new ItemTypeModel(9, this.$i18n("Enum_DataType_MonthlyCalculation")),
      new ItemTypeModel(10, this.$i18n("Enum_DataType_RisingSalaryBack")),
    ]);
    systemTypeList: KnockoutObservableArray<ItemTypeModel> = ko.observableArray([
      new ItemTypeModel(0, this.$i18n('Enum_SystemType_PERSON_SYSTEM')),
      new ItemTypeModel(1, this.$i18n('Enum_SystemType_ATTENDANCE_SYSTEM')),
      new ItemTypeModel(2, this.$i18n('Enum_SystemType_PAYROLL_SYSTEM')),
      new ItemTypeModel(3, this.$i18n('Enum_SystemType_OFFICE_HELPER')),
    ]);
    symbolList: KnockoutObservableArray<ItemTypeModel> = ko.observableArray([
      new ItemTypeModel(0, this.$i18n("Enum_Symbol_Include")),
      new ItemTypeModel(1, this.$i18n("Enum_Symbol_Equal")),
      new ItemTypeModel(2, this.$i18n("Enum_Symbol_Different")),
    ]);

    //B1
    b1Columns: KnockoutObservableArray<any> = ko.observableArray([
      {
        headerText: this.$i18n("CLI003_88"),
        key: "code",
        width: "50px",
      },
      {
        headerText: this.$i18n("CLI003_89"),
        key: "name",
        width: "100px",
      },
    ]);

    //B2
    b2_10Datasource: KnockoutObservableArray<LogSetItemDetailModalDisplay> = ko.observableArray([]);
    b2_10Columns: KnockoutObservableArray<any> = ko.observableArray([
      {
        headerText: this.$i18n("CLI003_90"),
        prop: "displayItem",
        width: 125,
      },
      {
        headerText: this.$i18n("CLI003_81"),
        prop: "cond1",
        width: 125,
      },
      {
        headerText: this.$i18n("CLI003_82"),
        prop: "cond2",
        width: 125,
      },

      {
        headerText: this.$i18n("CLI003_83"),
        prop: "cond3",
        width: 125,
      },
      {
        headerText: this.$i18n("CLI003_84"),
        prop: "cond4",
        width: 125,
      },
      {
        headerText: this.$i18n("CLI003_85"),
        prop: "cond5",
        width: 125,
      },
    ]);
    b2_10CurrentCode: KnockoutObservable<any> = ko.observable();
    logSetOutputs: KnockoutObservableArray<any> = ko.observableArray([]);  //5 condition for Log

    //B3
    startDateNameOperator: KnockoutObservable<string> = ko.observable(this.$i18n("CLI003_52"));
    endDateNameOperator: KnockoutObservable<string> = ko.observable(this.$i18n("CLI003_53"));
    startDateOperator: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD 0:00:00"));
    endDateOperator: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD 23:59:59"));

    //B4
    b4_2SelectedRuleCode: KnockoutObservable<number> = ko.observable(2);
    roundingRules: KnockoutObservableArray<any> = ko.observableArray([
      { code: EMPLOYEE_SPECIFIC.SPECIFY, name: this.$i18n("CLI003_17") },
      { code: EMPLOYEE_SPECIFIC.ALL, name: this.$i18n("CLI003_18") },
    ]);
    operatorEmployeeCount: KnockoutObservable<string> = ko.observable(nts.uk.text.format(this.$i18n("CLI003_57"), 0));
    selectedEmployeeCodeOperator: KnockoutObservableArray<any> = ko.observableArray([]);

    //B5
    startDateString: KnockoutObservable<string> = ko.observable("");
    endDateString: KnockoutObservable<string> = ko.observable("");
    checkFormatDate: KnockoutObservable<string> = ko.observable('1'); //1: Display YYYY/MM/DD , 2: Display YYYY/MM
    b5_2dateValue: KnockoutObservable<any> = ko.observable({});

    //B6
    b6_2SelectedRuleCode: KnockoutObservable<number> = ko.observable(2);
    targetEmployeeCount: KnockoutObservable<string> = ko.observable(nts.uk.text.format(this.$i18n("CLI003_57"), 0));
    selectedEmployeeCodeTarget: KnockoutObservableArray<any> = ko.observableArray([]);

    created() {
      const vm = this;
      vm.$window.storage('VIEW_B_DATA')
        .then((data) => {
          if (data !== undefined) {
            vm.currentCode(data.currentCode);
            vm.checkFormatDate(data.checkFormatDate);
            vm.selectedEmployeeCodeOperator(data.operatorEmployeeIdList);
            vm.b5_2dateValue(data.dateValue);
            vm.startDateOperator(data.startDateOperator);
            vm.endDateOperator(data.endDateOperator);
            vm.b4_2SelectedRuleCode(data.selectedRuleCodeOperator);
            vm.b6_2SelectedRuleCode(data.selectedRuleCodeTarget);
            vm.selectedEmployeeCodeTarget(data.targetEmployeeIdList);
            vm.targetEmployeeCount(data.targetEmployeeCount);
            vm.operatorEmployeeCount(data.operatorEmployeeCount);
          }
        })
        .then(() => vm.$window.storage('VIEW_B_DATA', undefined))
        .then(() => {
          vm.getAllLogDisplaySet();
          vm.obsSelectedLogSet();
        })
    }

    //get logDisplaySet from service and convert to UI Dto
    private getAllLogDisplaySet() {
      const vm = this;
      vm.$blockui("grayout");
      vm.logSets.removeAll();
      service
        .getAllLogDisplaySet()
        .then((logDisplaySets: any) => {
          if (logDisplaySets && logDisplaySets.length > 0) {
            for (let i = 0; i < logDisplaySets.length; i++) {
              const logDisplaySet = logDisplaySets[i];
              vm.logSets.push(
                new ItemLogSetModel(
                  logDisplaySet.logSetId,
                  logDisplaySet.code,
                  logDisplaySet.name,
                  logDisplaySet.recordType,
                  logDisplaySet.dataType,
                  logDisplaySet.systemType,
                  logDisplaySet.logSetOutputItems
                )
              );
            }
            //pick first when current code null
            if (vm.currentCode() === null) {
              const logDisplaySetFirst = logDisplaySets[0];
              $("#B1").ntsGridList("setSelected", logDisplaySetFirst.code);
              vm.currentCode(logDisplaySetFirst.code);
              //pick current display log
            } else {
              $("#B1").ntsGridList("setSelected", vm.currentCode());
              const logSet = vm.logSets().filter(logSet => (logSet.code === vm.currentCode()))[0];
              vm.getLogItems(logSet);
              vm.getTargetDate(logSet);
            }
          }
        })
        .fail((error: string) => {
          vm.$dialog.alert(error);
        })
        .always(() => {
          vm.$blockui("clear");
          vm.$errors("clear");
        });
    }

    //get current display log code on click
    private obsSelectedLogSet() {
      const vm = this;
      vm.currentCode.subscribe((newValue) => {
        vm.$errors("clear");
        const logSet = vm.logSets().filter(logSet => (logSet.code === newValue))[0];
        vm.getLogItems(logSet);
        vm.getTargetDate(logSet);
      });
    }

    //get data for table B2_10
    private getLogItems(logSet: any) {
      const vm = this;
      service
        .getLogOutputItemByRecordType(String(logSet.recordType))
        .then((logOutputItems: LogOutputItem[]) => {
          vm.setLogSetInfo(logSet);
          const logSetItemDetailsList = _.map(logSet.logSetOutputs, (item: any, index: number) => {
            const listCond: string[] = ["", "", "", "", "", ""];
            item.logSetItemDetails.map((itemDetail: any, index: number) => {
              const condSymbol = vm.symbolList().filter((symbol) => symbol.code === itemDetail.sybol)[0].name;
              if (itemDetail.condition !== "" && itemDetail.condition !== null) {
                listCond[index] = condSymbol + " " + itemDetail.condition;
              }
            });
            const itemName = _.filter(logOutputItems, (logOutputItem => logOutputItem.itemNo === item.itemNo))[0].itemName;
            return new LogSetItemDetailModalDisplay(
              itemName,
              listCond[0],
              listCond[1],
              listCond[2],
              listCond[3],
              listCond[4]
            );
          });
          vm.b2_10Datasource(logSetItemDetailsList);
        })
        .fail(() => {
          vm.$dialog.alert({ messageId: "Msg_1221" });
          return null;
        })
        .always(() => {
          vm.$blockui("clear");
        });
    }

    //set current logSet to UI
    private setLogSetInfo(logSet: any) {
      const vm = this;
      vm.logSetOutputs(logSet.logSetOutputs);
      vm.currentLogDisplaySet(logSet);
      vm.logSetId(logSet.logSetId);
      vm.currentLogSetCode(vm.currentCode());
      vm.currentLogSetName(logSet.name);
      const recordTypeName = vm.getRecordTypeName(logSet.recordType);
      const dataTypeName = vm.getDataTypeName(logSet.dataType);
      const systemTypeName = vm.getSystemTypeName(logSet.systemType);
      vm.recordType(logSet.recordType);
      vm.currentRecordTypeName(recordTypeName);
      vm.systemType(logSet.systemType);
      vm.currentSystemTypeName(systemTypeName);
      if (logSet.recordType === 3) {
        vm.showPersonInfo(false);
      } else {
        vm.showPersonInfo(true);
      }
      if (logSet.recordType === 6) {
        vm.dataType(logSet.dataType);
        vm.currentDataTypeName(dataTypeName);
        vm.showDataType(true);
      } else {
        vm.showDataType(false);
        vm.dataType("");
        vm.currentDataTypeName("");
      }
      if (logSet.recordType === 0 ||
        logSet.recordType === 1 ||
        logSet.recordType === 9 ||
        logSet.recordType === 10 ||
        logSet.recordType === 11) {
        vm.showOperator(false);
        vm.b6_2SelectedRuleCode(2);
      } else {
        vm.showOperator(true);
      }
    }

    //get recordType Name
    private getRecordTypeName(currentRecordType: number): string {
      const vm = this;
      return vm.recordTypeList().filter((recordType) => recordType.code === currentRecordType)[0].name;
    }

    //get dataType Name
    private getDataTypeName(currentDataType: number): string {
      const vm = this;
      return currentDataType === null ? '' : vm.dataTypeList().filter((dataType) => dataType.code === currentDataType)[0].name;
    }

    //get systemType Name
    private getSystemTypeName(currentSystemType: number): string {
      const vm = this;
      return vm.systemTypeList().filter((systemType) => systemType.code === currentSystemType)[0].name;
    }

    //Change display type for B5_2 (1 -> YYYY/MM/dd :: 2 -> YYYY/MM)
    private getTargetDate(logSet: any) {
      const vm = this;
      vm.checkFormatDate('1');
      vm.b5_2dateValue.valueHasMutated();
      if (logSet.recordType === RECORD_TYPE.DATA_CORRECT
        && (logSet.dataType === 2 || logSet.dataType === 3
          || logSet.dataType === 6 || logSet.dataType === 7)) {
        vm.checkFormatDate('2');
      }
    }

    //Open B4_3
    public openDialogForB4_3() {
      const vm = this;
      vm.$window
        .storage("CLI003_C_FormLabel", vm.$i18n("CLI003_23"))
        .then(() => {
          vm.$window.modal("/view/cli/003/c/index.xhtml").then(() => {
            vm.$window.storage("operatorEmployeeCount").then((data) => {
              if (data !== undefined)
                vm.operatorEmployeeCount(data);
            })
            vm.$window.storage("selectedEmployeeCodeOperator").then((data) => {
              if (data !== undefined)
                vm.selectedEmployeeCodeOperator(data);
            })
          })
        })
    }

    //Open B6_3
    public openDialogForB6_3() {
      const vm = this;
      vm.$window
        .storage("CLI003_C_FormLabel", vm.$i18n("CLI003_16"))
        .then(() => {
          vm.$window.modal("/view/cli/003/c/index.xhtml").then(() => {
            vm.$window.storage("targetEmployeeCount").then((data) => {
              if (data !== undefined)
                vm.targetEmployeeCount(data);
            })
            vm.$window.storage("selectedEmployeeCodeTarget").then((data) => {
              if (data !== undefined)
                vm.selectedEmployeeCodeTarget(data);
            })
          })
        })
    }

    //check before jump to screen F
    private validateBeforeJumpToF(): boolean {
      const vm = this;
      const noOne = nts.uk.text.format(vm.$i18n("CLI003_57"), 0);
      if (vm.b4_2SelectedRuleCode() === 1 && vm.operatorEmployeeCount() === noOne &&
        vm.b6_2SelectedRuleCode() === 1 && vm.targetEmployeeCount() === noOne) {
        const bundledErrors = [{
          message: resource.getMessage('Msg_1718'),
          messageId: "Msg_1718",
          supplements: {}
        }, {
          message: resource.getMessage('Msg_1719'),
          messageId: "Msg_1719",
          supplements: {}
        }];
        nts.uk.ui.dialog.bundledErrors({ errors: bundledErrors });
        return false;
      } else if (vm.b4_2SelectedRuleCode() === 1 && vm.operatorEmployeeCount() === noOne) {
        vm.$dialog.error({ messageId: "Msg_1718" });
        return false;
      }
      else if (vm.b6_2SelectedRuleCode() === 1 && vm.targetEmployeeCount() === noOne) {
        vm.$dialog.error({ messageId: "Msg_1719" });
        return false;
      } else {
        return true;
      };
    }

    //jump to screen F
    public jumpToScreenF() {
      const vm = this;
      if (vm.validateBeforeJumpToF()) {
        const data = {
          currentCode: vm.currentCode(),
          logSetOutputs: vm.logSetOutputs(),
          targetEmployeeCount: vm.targetEmployeeCount(),
          operatorEmployeeCount: vm.operatorEmployeeCount(),
          logTypeSelectedCode: vm.recordType(),
          dataTypeSelectedCode: vm.dataType(),
          systemTypeSelectedCode: vm.systemType(),
          checkFormatDate: vm.checkFormatDate(),
          operatorEmployeeIdList: vm.selectedEmployeeCodeOperator(),
          dateValue: vm.b5_2dateValue(),
          startDateOperator: vm.startDateOperator(),
          endDateOperator: vm.endDateOperator(),
          selectedRuleCodeOperator: vm.b4_2SelectedRuleCode(),
          selectedRuleCodeTarget: vm.b6_2SelectedRuleCode(),
          targetEmployeeIdList: vm.selectedEmployeeCodeTarget(),
        }
        vm.$window
          .storage('VIEW_B_DATA', data)
          .then(() => vm.$jump.self("/view/cli/003/f/index.xhtml", data));
      }
    }
  }

  export enum EMPLOYEE_SPECIFIC {
    SPECIFY = 1,
    ALL = 2
  }

  export enum RECORD_TYPE {
    LOGIN = 0,
    START_UP = 1,
    UPDATE_MASTER = 2,
    UPDATE_PERSION_INFO = 3,
    DATA_REFERENCE = 4,
    DATA_MANIPULATION = 5,
    DATA_CORRECT = 6,
    MY_NUMBER = 7,
    TERMINAL_COMMUNICATION_INFO = 8,
    DATA_STORAGE = 9,
    DATA_RECOVERY = 10,
    DATA_DELETION = 11,
  }

  export enum DATA_TYPE {
    SCHEDULE = 0,
    DAILY_RESULTS = 1,
    MONTHLY_RESULTS = 2,
    ANY_PERIOD_SUMMARY = 3,
    APPLICATION_APPROVAL = 4,
    NOTIFICATION = 5,
    SALARY_DETAIL = 6,
    BONUS_DETAIL = 7,
    YEAR_END_ADJUSTMENT = 8,
    MONTHLY_CALCULATION = 9,
    RISING_SALARY_BACK = 10,
  }

  export enum SYMBOL {
    INCLUDE = 0,
    EQUAL = 1,
    DIFFERENT = 2,
  }
  export class ItemTypeModel {
    code: number;
    name: string;

    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }
  export class ItemLogSetModel {
    id: string;
    code: string;
    name: string;
    recordType: number;
    dataType: number;
    systemType: number;
    logSetOutputs: any;

    constructor(
      id: string,
      code: any,
      name: string,
      recordType: number,
      dataType: number,
      systemType: number,
      logSetOutputs: any
    ) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.recordType = recordType;
      this.dataType = dataType;
      this.systemType = systemType;
      this.logSetOutputs = logSetOutputs;
    }
  }

  export class LogSetOutputItemModal {
    logSetId: string;
    itemNo: number;
    itemName: string;
    displayOrder: number;
    isUseFlag: number;
    logSetItemDetails: LogSetItemDetailModal[];

    constructor(
      logSetId: string,
      itemNo: number,
      itemName: string,
      displayOrder: number,
      isUseFlag: number,
      logSetItemDetails: LogSetItemDetailModal[]
    ) {
      this.logSetId = logSetId;
      this.itemNo = itemNo;
      this.itemName = itemName;
      this.displayOrder = displayOrder;
      this.isUseFlag = isUseFlag;
      this.logSetItemDetails = logSetItemDetails;
    }
  }

  export class LogSetItemDetailModal {
    logSetId: string;
    itemNo: number;
    frame: number;
    isUseCondFlg: number;
    condition: string;
    sybol: number;

    constructor(
      logSetId: string,
      itemNo: number,
      frame: number,
      isUseCondFlg: number,
      condition: string,
      symbol: number
    ) {
      this.logSetId = logSetId;
      this.itemNo = itemNo;
      this.frame = frame;
      this.isUseCondFlg = isUseCondFlg;
      this.condition = condition;
      this.sybol = symbol;
    }
  }

  export class LogSetItemDetailModalDisplay {
    displayItem: string;
    cond1: string;
    cond2: string;
    cond3: string;
    cond4: string;
    cond5: string;

    constructor(
      displayItem: string,
      cond1: string,
      cond2: string,
      cond3: string,
      cond4: string,
      cond5: string
    ) {
      this.displayItem = displayItem,
        this.cond1 = cond1,
        this.cond2 = cond2,
        this.cond3 = cond3,
        this.cond4 = cond4,
        this.cond5 = cond5;
    }
  }
  export interface LogOutputItem {
    itemNo : number,
    itemName : string,
    recordType : number,
    sortOrder : number
  }
}
