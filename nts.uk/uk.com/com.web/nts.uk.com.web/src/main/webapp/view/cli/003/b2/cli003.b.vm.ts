/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cli003.b {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;
  import service = nts.uk.com.view.cli003.b.service;
  
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
  export class RecordTypeModel {
    code: number;
    name: string;

    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class DataTypeModel {
    code: number;
    name: string;

    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }
  export class SymbolModel {
    code: number;
    name: string;

    constructor(code: number, name: string) {
      this.code = code;
      this.name = name;
    }
  }
  export class ItemLogSetModel {
    id: string;
    code: any;
    name: string;
    recordType: number;
    dataType: number;
    logSetOutputs: any;

    constructor(
      id: string,
      code: any,
      name: string,
      recordType: number,
      dataType: number,
      logSetOutputs: any
    ) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.recordType = recordType;
      this.dataType = dataType;
      this.logSetOutputs = logSetOutputs;
    }
  }

  export class LogSetOutputItemModal {
    logSetId: string;
    itemNo: number;
    itemName: string;
    displayOrder: number;
    isUseFlag: number;
    logSetItemDetails: Array<LogSetItemDetailModal>;

    constructor(
      logSetId: string,
      itemNo: number,
      itemName: string,
      displayOrder: number,
      isUseFlag: number,
      logSetItemDetails: Array<LogSetItemDetailModal>
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
      (this.displayItem = displayItem),
        (this.cond1 = cond1),
        (this.cond2 = cond2),
        (this.cond3 = cond3),
        (this.cond4 = cond4),
        (this.cond5 = cond5);
    }
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    //from G - start
    logSets: KnockoutObservableArray<ItemLogSetModel> = ko.observableArray([]);
    logDisplaySets: KnockoutObservableArray<any> = ko.observableArray([]);
    currentLogDisplaySet: KnockoutObservable<ItemLogSetModel> = ko.observable();
    logSetId: KnockoutObservable<string> = ko.observable("");
    currentLogSetCode: KnockoutObservable<string> = ko.observable("");
    currentLogSetName: KnockoutObservable<string> = ko.observable("");
    recordType: KnockoutObservable<string> = ko.observable("");
    currentRecordTypeName: KnockoutObservable<string> = ko.observable("");
    currentDataTypeName: KnockoutObservable<string> = ko.observable("");
    dataType: KnockoutObservable<string> = ko.observable("");
    targetEmployeeCount:KnockoutObservable<string>  = ko.observable(nts.uk.text.format(this.$i18n("CLI003_57"), 0));
    operatorEmployeeCount:KnockoutObservable<string>  = ko.observable(nts.uk.text.format(this.$i18n("CLI003_57"), 0));
    selectedEmployeeCodeTarget: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedEmployeeCodeOperator: KnockoutObservableArray<any> = ko.observableArray([]);
    employeeList : KnockoutObservableArray<any> = ko.observableArray([]);
    logOutputItems: KnockoutObservableArray<any> = ko.observableArray([]);
    recordTypeList: KnockoutObservableArray<
      RecordTypeModel
    > = ko.observableArray([
      new RecordTypeModel(0, this.$i18n("Enum_RecordType_Login")),
      new RecordTypeModel(1, this.$i18n("Enum_RecordType_StartUp")),
      new RecordTypeModel(2, this.$i18n("Enum_RecordType_UpdateMaster")),
      new RecordTypeModel(3, this.$i18n("Enum_RecordType_UpdatePersionInfo")),
      new RecordTypeModel(4, this.$i18n("Enum_RecordType_DataReference")),
      new RecordTypeModel(5, this.$i18n("Enum_RecordType_DataManipulation")),
      new RecordTypeModel(6, this.$i18n("Enum_RecordType_DataCorrect")),
      new RecordTypeModel(7, this.$i18n("Enum_RecordType_MyNumber")),
      new RecordTypeModel(
        8,
        this.$i18n("Enum_RecordType_TerminalCommucationInfo")
      ),
      new RecordTypeModel(9, this.$i18n("Enum_RecordType_DataStorage")),
      new RecordTypeModel(10, this.$i18n("Enum_RecordType_DataRecovery")),
      new RecordTypeModel(11, this.$i18n("Enum_RecordType_DataDeletion")),
    ]);

    dataTypeList: KnockoutObservableArray<DataTypeModel> = ko.observableArray([
      new DataTypeModel(0, this.$i18n("Enum_DataType_Schedule")),
      new DataTypeModel(1, this.$i18n("Enum_DataType_DailyResults")),
      new DataTypeModel(2, this.$i18n("Enum_DataType_MonthlyResults")),
      new DataTypeModel(3, this.$i18n("Enum_DataType_AnyPeriodSummary")),
      new DataTypeModel(4, this.$i18n("Enum_DataType_ApplicationApproval")),
      new DataTypeModel(5, this.$i18n("Enum_DataType_Notification")),
      new DataTypeModel(6, this.$i18n("Enum_DataType_SalaryDetail")),
      new DataTypeModel(7, this.$i18n("Enum_DataType_BonusDetail")),
      new DataTypeModel(8, this.$i18n("Enum_DataType_YearEndAdjustment")),
      new DataTypeModel(9, this.$i18n("Enum_DataType_MonthlyCalculation")),
      new DataTypeModel(10, this.$i18n("Enum_DataType_RisingSalaryBack")),
    ]);
    symbolList: KnockoutObservableArray<DataTypeModel> = ko.observableArray([
      new SymbolModel(0, this.$i18n("Enum_Symbol_Include")),
      new SymbolModel(1, this.$i18n("Enum_Symbol_Equal")),
      new SymbolModel(2, this.$i18n("Enum_Symbol_Different")),
    ]);
    currentCode: KnockoutObservable<string> = ko.observable(null);
    b5_2dateValue: KnockoutObservable<any> = ko.observable({});
    b2_10CurrentCode: KnockoutObservable<any> = ko.observable();
    b4_2SelectedRuleCode: any = ko.observable(2);
    b6_2SelectedRuleCode: any = ko.observable(2);
    roundingRules = ko.observableArray([
      { code: EMPLOYEE_SPECIFIC.SPECIFY, name: this.$i18n("CLI003_17") },
      { code: EMPLOYEE_SPECIFIC.ALL, name: this.$i18n("CLI003_18") },
    ]);
    b1Columns: KnockoutObservableArray<any> = ko.observableArray([
      {
        headerText: "",
        key: "rowNumber",
        width: "40px",
      },
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
    b2_10Datasource: KnockoutObservableArray<
      LogSetItemDetailModalDisplay
    > = ko.observableArray([]);
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
    startDateNameOperator: KnockoutObservable<string>= ko.observable(this.$i18n("CLI003_52"));
    endDateNameOperator: KnockoutObservable<string> = ko.observable(this.$i18n("CLI003_53"));
    startDateString: KnockoutObservable<string>= ko.observable("");
    endDateString: KnockoutObservable<string>= ko.observable("");
    checkFormatDate: KnockoutObservable<string>= ko.observable('1');

    startDateOperator: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD 0:00:00"));
    endDateOperator: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD 23:59:59"));

    constructor() {
      super();
      const vm = this;
      vm.startDateString.subscribe((value) => {
        vm.b5_2dateValue().startDate = value;
        vm.b5_2dateValue.valueHasMutated();
    });
    vm.endDateString.subscribe((value) => {
      vm.b5_2dateValue().endDate = value;
      vm.b5_2dateValue.valueHasMutated();
    });
    vm.b5_2dateValue = ko.observable({
        startDate: moment.utc().format("YYYY/MM/DD"),
        endDate: moment.utc().format("YYYY/MM/DD")
    });
    }

    created(data :any) {
      const vm = this;
      console.log(data);
      if(data){
        const currentData = data.data;
        vm.currentCode(currentData.currentCode);
        vm.checkFormatDate(currentData.checkFormatDate);
        vm.selectedEmployeeCodeTarget(currentData.targetEmployeeIdList);
        vm.b5_2dateValue(currentData.dateValue);
        vm.startDateOperator(currentData.startDateOperator);
        vm.endDateOperator(currentData.endDateOperator);
        vm.b4_2SelectedRuleCode(currentData.selectedRuleCode);
        vm.b6_2SelectedRuleCode(currentData.selectedRuleCodeOperator);
        vm.selectedEmployeeCodeOperator(currentData.listEmployeeIdOperator);
        vm.operatorEmployeeCount(currentData.operatorEmployeeCount);
        vm.targetEmployeeCount(currentData.targetEmployeeCount);
      }
      vm.getAllLogDisplaySet();
      vm.obsSelectedLogSet();
    }
    mounted() {
      const vm = this;
      // new nts.uk.ui.mgrid.MGrid($("#B1")[0], {
      //   height: 600,
      //   width: 300,
      //   primaryKey: "code",
      //   primaryKeyDataType: "number",
      //   dataSource: vm.b1Datasource(),
      //   columns: vm.b1Columns(),
      //   enter: "right",
      //   features: [],
      // }).create();
    }

    getAllLogDisplaySet() {
      const vm = this;
      let dfd = $.Deferred<any>();
      vm.$blockui("grayout");
      vm.logSets.removeAll();
      service
        .getAllLogDisplaySet()
        .done((logDisplaySets: any) => {
          if (logDisplaySets && logDisplaySets.length > 0) {
            vm.logDisplaySets(logDisplaySets);
            for (let i = 0; i < logDisplaySets.length; i++) {
              const logDisplaySet = logDisplaySets[i];
              vm.logSets.push(
                new ItemLogSetModel(
                  logDisplaySet.logSetId,
                  logDisplaySet.code,
                  logDisplaySet.name,
                  logDisplaySet.recordType,
                  logDisplaySet.dataType,
                  logDisplaySet.logSetOutputItems
                )
              );
            }
            if(vm.currentCode() === null){
              const logDisplaySetFirst = logDisplaySets[0];
            $("#B1").ntsGridList("setSelected", logDisplaySetFirst.code);
            vm.currentCode(logDisplaySetFirst.code);
            }else{
              $("#B1").ntsGridList("setSelected", vm.currentCode());
              const logSet = vm.logSets().filter(logSet => (logSet.code === vm.currentCode()))[0];
              vm.getLogItems(logSet);
              vm.getTargetDate(logSet);
            }
          }
          dfd.resolve();
        })
        .fail(function (error: string) {
          vm.$dialog.alert(error);
          dfd.resolve();
        })
        .always(() => {
          vm.$blockui("clear");
          vm.$errors("clear");
        });
      return dfd.promise();
    }

    obsSelectedLogSet() {
      const vm = this;
      vm.currentCode.subscribe((newValue) => {
        vm.$errors("clear");
        const logSet = vm.logSets().filter(logSet => (logSet.code === newValue))[0];
        vm.getLogItems(logSet);
        vm.getTargetDate(logSet);
      });
    }

    getLogItems(logSet: any) {
      const vm = this;
      vm.$blockui("grayout");
      service
        .getLogOutputItemByRecordType(String(logSet.recordType))
        .done((logOutputItems: any) => {
          vm.setLogSetInfo(logSet);
          const logSetItemDetailsList = [];
          logSet.logSetOutputs.map((item :any,index:number) => {
            const listCond: string[] = ["", "", "", "", "", ""];
            item.logSetItemDetails.map((itemDetail:any, index:number) => {
              const condSymbol = vm
                .symbolList()
                .filter((symbol) => symbol.code === itemDetail.sybol)[0].name;
              if (itemDetail.condition !== "" && itemDetail.condition !== null) {
                listCond[index] = condSymbol + " " + itemDetail.condition;
              }
            });
            logSetItemDetailsList.push(
              new LogSetItemDetailModalDisplay(
                logOutputItems[index].itemName,
                listCond[0],
                listCond[1],
                listCond[2],
                listCond[3],
                listCond[4]
              )
            );
          });
          vm.b2_10Datasource(logSetItemDetailsList);
        })
        .fail(function (error: string) {
          vm.$dialog.alert({ messageId: "Msg_1221" });
          return null;
        })
        .always(() => {
          vm.$blockui("clear");
        });
    }
    setLogSetInfo(logSet: any) {
      const vm = this;
      vm.currentLogDisplaySet(logSet);
      vm.logSetId(logSet.logSetId);
      vm.currentLogSetCode(vm.currentCode());
      vm.currentLogSetName(logSet.name);
      const recordTypeName = vm.getRecordTypeName(logSet.recordType);
      const dataTypeName = vm.getDataTypeName(logSet.dataType);
      vm.recordType(logSet.recordType);
      vm.currentRecordTypeName(recordTypeName);
      if (logSet.recordType === 6) {
        vm.dataType(logSet.dataType);
        vm.currentDataTypeName(dataTypeName);
      } else {
        vm.dataType("");
        vm.currentDataTypeName("");
      }
    }
    getRecordTypeName(currentRecordType : number) : string{
      const vm = this;
      return vm.recordTypeList().filter((recordType) => recordType.code === currentRecordType)[0].name;
    }
    getDataTypeName(currentDataType : number) : string{
      const vm = this;
      return vm.dataTypeList().filter((dataType) => dataType.code === currentDataType)[0].name;
    }
    getTargetDate(logSet : any) {
      const vm = this;
      vm.checkFormatDate('1');
      vm.b5_2dateValue.valueHasMutated();
      if (logSet.recordType == RECORD_TYPE.DATA_CORRECT
          && (logSet.dataType === 2 || logSet.dataType === 3
              || logSet.dataType === 6 || logSet.dataType === 7)) {
          vm.checkFormatDate('2');
      }
    }
    openDialogForB4_3() {
      const vm = this;
      setShared("CLI003_C_FormLabel", vm.$i18n("CLI003_23"));
      vm.$window.modal("/view/cli/003/c/index.xhtml").then((result: any) => {
        vm.targetEmployeeCount(getShared("targetEmployeeCount"));
        vm.selectedEmployeeCodeTarget(getShared("selectedEmployeeCodeTarget"));
      });
    }
    openDialogForB6_3() {
      const vm = this;
      setShared("CLI003_C_FormLabel", vm.$i18n("CLI003_16"));
      vm.$window.modal("/view/cli/003/c/index.xhtml").then((result: any) => {
        vm.operatorEmployeeCount(getShared("operatorEmployeeCount"));
        vm.selectedEmployeeCodeOperator(getShared("selectedEmployeeCodeOperator"));
      });
    }
    jumpToScreenF() { 
      const vm = this;
      vm.$jump.self("/view/cli/003/f/index.xhtml",{
        'data' : {
          currentCode : vm.currentCode(),
          operatorEmployeeCount : vm.operatorEmployeeCount(),
          targetEmployeeCount : vm.targetEmployeeCount(),
          logTypeSelectedCode : vm.recordType(),
          dataTypeSelectedCode : vm.dataType(),
          checkFormatDate : vm.checkFormatDate(),
          targetEmployeeIdList: vm.selectedEmployeeCodeTarget(),
          dateValue: vm.b5_2dateValue(),
          startDateOperator: vm.startDateOperator(),
          endDateOperator: vm.endDateOperator(),
          selectedRuleCode: vm.b4_2SelectedRuleCode(),
          selectedRuleCodeOperator: vm.b6_2SelectedRuleCode(),
          listEmployeeIdOperator: vm.selectedEmployeeCodeOperator(),
        }
      });
    }
  }
}
