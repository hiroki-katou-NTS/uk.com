/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cli003.f {
    import service = nts.uk.com.view.cli003.f.service;

  export enum EMPLOYEE_SPECIFIC {
    SPECIFY = 1,
    ALL = 2
  }

  export enum ITEM_PROPERTY {
    ITEM_SRT = "string",
    ITEM_USER_NAME_LOGIN = "userNameLogin",
    ITEM_EMP_CODE_LOGIN = "employeeCodeLogin",
    ITEM_MODIFY_DATE = "modifyDateTime",
    ITEM_LOGIN_STATUS = "loginStatus",
    ITEM_METHOD_NAME = "methodName",
    ITEM_NOTE = "note",
    ITEM_MENU_NAME = "menuName",
    ITEM_USER_NAME_TAGET = "userNameTaget",
    ITEM_EMP_CODE_TAGET = "employeeCodeTaget",
    ITEM_PROCESS_ATTR = "processAttr",
    ITEM_CATEGORY_NAME = "categoryName",
    ITEM_TAGET_DATE = "targetDate",
    ITEM_INFO_OPERATE_ATTR = "infoOperateAttr",
    ITEM_NAME = "itemName",
    ITEM_VALUE_BEFOR = "valueBefore",
    ITEM_VALUE_AFTER = "valueAfter",
    ITEM_CORRECT_ATTR = "correctionAttr",
    ITEM_OPERATION_ID = "operationId",
    ITEM_PARRENT_KEY = "parentKey"
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

export enum ITEM_NO {
    ITEM_NO1 = 1,
    ITEM_NO2 = 2,
    ITEM_NO3 = 3,
    ITEM_NO4 = 4,
    ITEM_NO5 = 5,
    ITEM_NO6 = 6,
    ITEM_NO7 = 7,
    ITEM_NO8 = 8,
    ITEM_NO9 = 9,
    ITEM_NO10 = 10,
    ITEM_NO11 = 11,
    ITEM_NO12 = 12,
    ITEM_NO13 = 13,
    ITEM_NO14 = 14,
    ITEM_NO15 = 15,
    ITEM_NO16 = 16,
    ITEM_NO17 = 17,
    ITEM_NO18 = 18,
    ITEM_NO19 = 19,
    ITEM_NO20 = 20,
    ITEM_NO21 = 21,
    ITEM_NO22 = 22,
    ITEM_NO23 = 23,
    ITEM_NO24 = 24,
    ITEM_NO25 = 25,
    ITEM_NO26 = 26,
    ITEM_NO27 = 27,
    ITEM_NO28 = 28,
    ITEM_NO29 = 29,
    ITEM_NO30 = 30,
    ITEM_NO31 = 31,
    ITEM_NO32 = 32,
    ITEM_NO33 = 33,
    ITEM_NO34 = 34,
    ITEM_NO35 = 35,
    ITEM_NO36 = 36,
    ITEM_NO99 = 99
}
export interface PersionCorrectParam {
  operationId: string;
  targetDate: string;
  categoryName: string;
  itemName: string;
  valueBefore: string;
  valueAfter: string;
  infoOperateAttr: string;
}
export interface LogBasicInfoParam {
  loginBasicInfor: LogBasicInfoModel;
  lstLogDataCorrectRecordRefeDto: KnockoutObservableArray<DataCorrectLogModel>;
  lstLogPerCateCorrectRecordDto: KnockoutObservableArray<PerCateCorrectRecordModel>
}
export interface DataCorrectParam {
  operationId: string;
  targetDate: string;
  targetDataType: number;
  itemName: string;
  valueBefore: string;
  valueAfter: string;
  remarks: string;
  correctionAttr: string;
}
  class IgGridColumnSwitchModel {
    headerText: string;
    key: string;
    dataType: string;
    hidden: boolean;
    itemName: string;
    width: string;
    constructor(headerText: string, itemNo: number, recordType: number) {
        this.headerText = headerText;
        this.hidden = false;
        this.dataType = ITEM_PROPERTY.ITEM_SRT;
        this.itemName = headerText;
        switch (itemNo) {
            case -1: {
                this.key = ITEM_PROPERTY.ITEM_OPERATION_ID;
                this.hidden = true;
                break;
            }
            case -2: {
                this.key = ITEM_PROPERTY.ITEM_PARRENT_KEY;
                this.hidden = true;
                break;
            }
            case ITEM_NO.ITEM_NO2: {
                this.key = ITEM_PROPERTY.ITEM_USER_NAME_LOGIN;
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.width = "120px";
                } else {
                    this.width = "170px";
                }

                break;
            }
            case ITEM_NO.ITEM_NO3: {
                this.key = ITEM_PROPERTY.ITEM_EMP_CODE_LOGIN;
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.width = "120px";
                } else {
                    this.width = "170px";
                }
                break;
            }
            case ITEM_NO.ITEM_NO7: {
                this.key = ITEM_PROPERTY.ITEM_MODIFY_DATE;
                this.width = "170px";
                break;
            }
            case ITEM_NO.ITEM_NO18: {
                this.key = ITEM_PROPERTY.ITEM_NOTE;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO19: {
                if (recordType == RECORD_TYPE.LOGIN) {
                    this.key = ITEM_PROPERTY.ITEM_LOGIN_STATUS;
                    this.width = "120px";
                }
                if (recordType == RECORD_TYPE.START_UP) {
                    this.key = ITEM_PROPERTY.ITEM_MENU_NAME;
                    this.width = "170px";
                }
                break;
            }
            case ITEM_NO.ITEM_NO20: {
                if (recordType == RECORD_TYPE.LOGIN) {
                    this.key = ITEM_PROPERTY.ITEM_METHOD_NAME;
                }
                if (recordType == RECORD_TYPE.DATA_CORRECT
                    || recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_USER_NAME_TAGET;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO21: {
                if (recordType == RECORD_TYPE.DATA_CORRECT
                    || recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_EMP_CODE_TAGET;
                }
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.width = "170px";
                } else {
                    this.width = "120px";
                }
                break;
            }
            case ITEM_NO.ITEM_NO22: {
                if (recordType == RECORD_TYPE.LOGIN) {
                    this.key = ITEM_PROPERTY.ITEM_NOTE;
                }
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.key = ITEM_PROPERTY.ITEM_TAGET_DATE;
                }
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_PROCESS_ATTR;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO23: {
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.key = ITEM_PROPERTY.ITEM_TAGET_DATE;
                }
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_CATEGORY_NAME;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO24: {
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.key = ITEM_PROPERTY.ITEM_TAGET_DATE;
                }
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_INFO_OPERATE_ATTR;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO26: {
                this.key = ITEM_PROPERTY.ITEM_CORRECT_ATTR;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO27: {
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.key = ITEM_PROPERTY.ITEM_NAME;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO29: {
                this.key = ITEM_PROPERTY.ITEM_NAME;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO30: {
                this.key = ITEM_PROPERTY.ITEM_VALUE_BEFOR;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO31: {
                if (recordType == RECORD_TYPE.DATA_CORRECT) {
                    this.key = ITEM_PROPERTY.ITEM_VALUE_AFTER;
                }
                if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    this.key = ITEM_PROPERTY.ITEM_VALUE_BEFOR;
                }
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO33: {
                this.key = ITEM_PROPERTY.ITEM_VALUE_AFTER;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO36: {
                this.key = ITEM_PROPERTY.ITEM_NOTE;
                this.width = "120px";
                break;
            }
            case ITEM_NO.ITEM_NO99: {
                this.key = ITEM_PROPERTY.ITEM_TAGET_DATE;
                this.width = "120px";
                break;
            }
            default: {
                break;
            }
        }
    }
}
class LogOutputItemDto {
  itemNo: number;
  itemName: string;
  recordType: number;
  sortOrder: number;
  parentKey: string;
  constructor(itemNo: number, itemName: string, recordType: number, sortOrder: number, parentKey: string) {
      this.itemNo = itemNo;
      this.itemName = itemName;
      this.recordType = recordType;
      this.sortOrder = sortOrder;
      this.parentKey = parentKey;
  }
}
class IgGridColumnModel {
  headerText: string;
  key: string;
  dataType: string;
  hidden: boolean;
  // setting for using export csv
  itemName: string;
  constructor(headerText: string, key: string, dataType: string, hidden: boolean) {
      this.headerText = headerText;
      this.key = key;
      this.dataType = dataType;
      this.hidden = hidden;
      this.itemName = headerText;
  }
}
class PerCateCorrectRecordModel {
  parentKey: string;
  childrentKey: string;
  operationId: string;
  targetDate: string;
  categoryName: string;
  itemName: string;
  valueBefore: string;
  valueAfter: string;
  infoOperateAttr: string;
  constructor(param: PersionCorrectParam) {
      this.operationId = param.operationId;
      this.targetDate = param.targetDate;
      this.itemName = param.itemName;
      this.valueBefore = param.valueBefore;
      this.valueAfter = param.valueAfter;
      this.infoOperateAttr = param.infoOperateAttr;
      this.categoryName = param.categoryName;
  }
}
class LogBasicInfoModel {
  parentKey: string;
  operationId: string;
  userNameLogin: string;
  employeeCodeLogin: string;
  userIdTaget: string;
  userNameTaget: string;
  employeeCodeTaget: string;
  modifyDateTime: string;
  menuName: string;
  note: string;
  methodName: string;
  loginStatus: string;
  processAttr: string;
  lstLogDataCorrectRecordRefeDto: KnockoutObservableArray<DataCorrectLogModel>;
  lstLogLoginDto: KnockoutObservableArray<any>;
  lstLogOutputItemDto: KnockoutObservableArray<LogOutputItemDto>;
  subColumnsHeaders: KnockoutObservableArray<IgGridColumnModel>;
  lstLogPerCateCorrectRecordDto: KnockoutObservableArray<PerCateCorrectRecordModel>;
  isDisplayText: boolean;
  constructor(param: LogBasicInfoParam) {
      this.userNameLogin = param.loginBasicInfor.userNameLogin;
      this.employeeCodeLogin = param.loginBasicInfor.employeeCodeLogin;
      this.userIdTaget = param.loginBasicInfor.userIdTaget;
      this.userNameTaget = param.loginBasicInfor.userNameTaget;
      this.employeeCodeTaget = param.loginBasicInfor.employeeCodeTaget;
      this.modifyDateTime = param.loginBasicInfor.modifyDateTime;
      this.processAttr = param.loginBasicInfor.processAttr;
      this.lstLogLoginDto = param.loginBasicInfor.lstLogLoginDto;
      this.lstLogOutputItemDto = param.loginBasicInfor.lstLogOutputItemDto;
      this.menuName = param.loginBasicInfor.menuName;
      this.note = param.loginBasicInfor.note;
      this.methodName = param.loginBasicInfor.methodName;
      this.loginStatus = param.loginBasicInfor.loginStatus;
      this.lstLogDataCorrectRecordRefeDto = param.lstLogDataCorrectRecordRefeDto ? param.lstLogDataCorrectRecordRefeDto : [];
      this.lstLogPerCateCorrectRecordDto = param.lstLogPerCateCorrectRecordDto ? param.lstLogPerCateCorrectRecordDto : [];
  }
}
class DataCorrectLogModel {
  parentKey: string;
  childrentKey: string;
  operationId: string;
  targetDate: string;
  targetDataType: number;
  itemName: string;
  valueBefore: string;
  valueAfter: string;
  remarks: string;
  correctionAttr: string;
  showOrder: number
  constructor(param: DataCorrectParam) {
      this.operationId = param.operationId;
      this.targetDate = param.targetDate;
      this.targetDataType = param.targetDataType;
      this.itemName = param.itemName;
      this.valueBefore = param.valueBefore;
      this.valueAfter = param.valueAfter;
      this.remarks = param.remarks;
      this.correctionAttr = param.correctionAttr;
  }
}
  @bean()
  export class ScreenModel extends ko.ViewModel {
    dataFromB: any;
    columnsIgGrid: KnockoutObservableArray<IgGridColumnSwitchModel>= ko.observableArray([]);
    supColumnsIgGrid: KnockoutObservableArray<IgGridColumnSwitchModel>= ko.observableArray([]);
    columnsHeaderLogRecord: KnockoutObservableArray<String> = ko.observableArray(['2', '3', '7', '19', '20', '22']);
    columnsHeaderLogStartUp: KnockoutObservableArray<String> = ko.observableArray(['2', '3', '7', '18', '19']);
    columnsHeaderLogPersionInfo: KnockoutObservableArray<String> = ko.observableArray(['2', '3', '7', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '31', '33', '36']);
    columnsHeaderLogDataCorrect: KnockoutObservableArray<String> = ko.observableArray(['2', '3', '7', '20', '21', '22', '23', '24', '26', '27', '30', '31']);
    listLogBasicInforModel: LogBasicInfoModel[] = [];
    logBasicInforCsv: LogBasicInfoModel[] = [];
    isDisplayText: KnockoutObservable<boolean> = ko.observable(false);
    maxlength: KnockoutObservable<number> = ko.observable(1000);
    selectedEmployeeCodeTarget: KnockoutObservableArray<any>= ko.observableArray([]);
    
    ////just add
    //B
    logTypeSelectedCode: KnockoutObservable<string>= ko.observable('');
    dataTypeSelectedCode: KnockoutObservable<string>= ko.observable('');
    systemTypeSelectedCode: KnockoutObservable<string>= ko.observable('');
    checkFormatDate: KnockoutObservable<string>= ko.observable('');
    //C
    targetEmployeeIdList: KnockoutObservableArray<any>= ko.observableArray([]);
    dateValue: KnockoutObservable<any>= ko.observable();
    //D
    startDateOperator: KnockoutObservable<string>= ko.observable('');
    endDateOperator: KnockoutObservable<string>= ko.observable('');
   
    ////just add 2
    selectedRuleCode: KnockoutObservable<number> = ko.observable(2);
    selectedRuleCodeOperator: KnockoutObservable<number> = ko.observable(2);
    listEmployeeIdOperator: KnockoutObservableArray<any> = ko.observableArray([]);

    initComponentScreenF(data:any) {
        const vm = this
        if(data){
            vm.logTypeSelectedCode = ko.observable(data.logTypeSelectedCode);
            vm.dataTypeSelectedCode= ko.observable(data.dataTypeSelectedCode);
            vm.systemTypeSelectedCode= ko.observable(data.systemTypeSelectedCode);
            vm.checkFormatDate= ko.observable(data.checkFormatDate);
            vm.dateValue= ko.observable(data.dateValue);
            vm.startDateOperator= ko.observable(data.startDateOperator);
            vm.endDateOperator= ko.observable(data.endDateOperator);
            vm.selectedRuleCode= ko.observable(data.selectedRuleCode);
            vm.selectedRuleCodeOperator= ko.observable(data.selectedRuleCodeOperator);
            if(vm.selectedRuleCode() === 2){
                vm.targetEmployeeIdList= ko.observableArray([]);
            }
            if(vm.selectedRuleCodeOperator() === 2){
                vm.listEmployeeIdOperator= ko.observableArray([]);
            }
            vm.targetEmployeeIdList= ko.observableArray(data.targetEmployeeIdList);
            vm.listEmployeeIdOperator= ko.observableArray(data.listEmployeeIdOperator);
        }

        let dfd = $.Deferred<any>();
        //F igGrid
        let recordType = Number(vm.logTypeSelectedCode());

        // set param log
        const format = 'YYYY/MM/DD HH:mm:ss';
        let paramLog = {
            listTagetEmployeeId: vm.targetEmployeeIdList(),
            listOperatorEmployeeId: vm.listEmployeeIdOperator(),
            startDateTaget: moment(vm.dateValue().startDate, "YYYY/MM/DD").toISOString(),
            endDateTaget: moment(vm.dateValue().endDate, "YYYY/MM/DD").toISOString(),
            startDateOperator: moment.utc(vm.startDateOperator(), format).toISOString(),
            endDateOperator: moment.utc(vm.endDateOperator(), format).toISOString(),
            recordType: vm.logTypeSelectedCode(),
            targetDataType: vm.dataTypeSelectedCode()
        };
        if (vm.checkFormatDate() === '2') {
            paramLog.endDateTaget = moment.utc(vm.dateValue().endDate, "YYYY/MM/DD" ).endOf('month').toISOString();
        } else {
            paramLog.endDateTaget = moment.utc(vm.dateValue().endDate, "YYYY/MM/DD").toISOString();
        }
        // set param for get parent header name
        let paramOutputItem = {
            recordType: vm.logTypeSelectedCode(),
            itemNos: null,
        };
        let checkProcess = false;
        switch (recordType) {
            case RECORD_TYPE.LOGIN: {
                paramOutputItem.itemNos = vm.columnsHeaderLogRecord();
                checkProcess = true;
                break;
            }
            case RECORD_TYPE.START_UP: {
                paramOutputItem.itemNos = vm.columnsHeaderLogStartUp();
                checkProcess = true;
                break;
            }
            case RECORD_TYPE.UPDATE_PERSION_INFO: {
                paramOutputItem.itemNos = vm.columnsHeaderLogPersionInfo();
                checkProcess = true;
                break;
            }
            case RECORD_TYPE.DATA_CORRECT: {
                paramOutputItem.itemNos = vm.columnsHeaderLogDataCorrect();
                checkProcess = true;
                break;
            }
            default: {
                break;
            }
        }
        if (checkProcess) {
            // get log out put items
            vm.$blockui('grayout');
            service.getLogOutputItemsByRecordTypeItemNos(paramOutputItem)
            .done((dataOutputItems: Array<any>) => {
                if (dataOutputItems.length > 0) {
                    // Get Log basic info
                    service.getLogBasicInfoByModifyDate(paramLog).done(function(data: Array<LogBasicInfoModel>) {
                        if (data.length > 0) {
                            // order by list
                            if (recordType == RECORD_TYPE.LOGIN || recordType == RECORD_TYPE.START_UP) {
                                data = _.orderBy(data, ['modifyDateTime', 'employeeCodeLogin'], ['desc', 'asc']);
                            }
                            if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO || recordType == RECORD_TYPE.DATA_CORRECT) {
                                data = _.orderBy(data, ['modifyDateTime', 'employeeCodeTaget'], ['desc', 'asc']);
                            }
                            // generate columns header parent
                            vm.setListColumnHeaderLog(recordType, dataOutputItems);
                            let countLog = 1;
                            if (data.length > vm.maxlength()) {
                                vm.isDisplayText(true);
                            }
                            // process sub header with record type = persion info and data correct
                            _.forEach(data, function(logBasicInfoModel) {
                                if (countLog <= vm.maxlength()) {
                                    if (recordType == RECORD_TYPE.LOGIN || recordType == RECORD_TYPE.START_UP) {
                                        vm.listLogBasicInforModel.push(logBasicInfoModel);
                                    }
                                    if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                                        const logtemp = vm.getSubHeaderPersionInfo(logBasicInfoModel);
                                        vm.listLogBasicInforModel.push(logtemp);
                                    }
                                    if (recordType == RECORD_TYPE.DATA_CORRECT) {
                                        const logtemp = vm.getSubHeaderDataCorrect(logBasicInfoModel);
                                        vm.listLogBasicInforModel.push(logtemp);
                                    }
                                    countLog++;
                                } else {
                                    return false;
                                }
                            });
                            // Generate table
                            if (recordType == RECORD_TYPE.DATA_CORRECT) {
                                vm.generateDataCorrectLogGrid();
                            } else if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                                vm.generatePersionInforGrid();
                            } else {
                                vm.generateIgGrid();
                            }

                        } else {
                            
                            vm.$dialog.alert({ messageId: "Msg_1220" }).then(function() {
                                vm.$blockui('clear');
                            });
                        }
                        dfd.resolve();
                    }).always(() => {
                        vm.$blockui('clear');
                        nts.uk.ui.errors.clearAll();
                    }).fail(function(error) {
                        vm.$dialog.alert(error);
                        vm.$blockui('clear');
                        vm.$errors('clear');
                        dfd.resolve();
                    });

                } else {
                    vm.$dialog.alert({ messageId: "Msg_1221" }).then(function() {
                    vm.$blockui('clear');
                    });
                    vm.$blockui('clear');
                    vm.$errors('clear');
                    dfd.resolve();
                }
            })
            .fail((error:any) => {
                vm.$blockui('clear');
                vm.$errors('clear');
                vm.$dialog.alert(error);
                dfd.resolve();
            });
        }
        return dfd.promise();
    }

    getSubHeaderDataCorrect(logBasicInfoModel: LogBasicInfoModel) {
        let tempList = logBasicInfoModel.lstLogOutputItemDto;
        const subColumHeaderTemp: IgGridColumnModel[] = [];
        _.forEach(logBasicInfoModel.lstLogOutputItemDto, function(logOutputItemDto) {
            // generate columns header chidrent
            switch (logOutputItemDto.itemNo) {
                case ITEM_NO.ITEM_NO22:
                case ITEM_NO.ITEM_NO23:
                case ITEM_NO.ITEM_NO24: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_TAGET_DATE, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO26: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_CORRECT_ATTR, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO27: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_NAME, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO30: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_VALUE_BEFOR, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO31: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_VALUE_AFTER, ITEM_PROPERTY.STR, false));
                    break;
                }
            }
        });
        logBasicInfoModel.subColumnsHeaders = subColumHeaderTemp;
        return logBasicInfoModel;
    }
    getSubHeaderPersionInfo(logBasicInfoModel: LogBasicInfoModel) {
        let tempList = logBasicInfoModel.lstLogOutputItemDto;
        const subColumHeaderTemp: IgGridColumnModel[] = [];
        _.forEach(logBasicInfoModel.lstLogOutputItemDto, function(logOutputItemDto) {
            // generate columns header chidrent
            switch (logOutputItemDto.itemNo) {
                case ITEM_NO.ITEM_NO23: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_CATEGORY_NAME, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO99: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_TAGET_DATE, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO24: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_INFO_OPERATE_ATTR, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO29: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_NAME, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO31: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_VALUE_BEFOR, ITEM_PROPERTY.STR, false));
                    break;
                }
                case ITEM_NO.ITEM_NO33: {
                    subColumHeaderTemp.push(new IgGridColumnModel(logOutputItemDto.itemName, ITEM_PROPERTY.ITEM_VALUE_AFTER, ITEM_PROPERTY.STR, false));
                    break;
                }
            }
        });
        logBasicInfoModel.subColumnsHeaders = subColumHeaderTemp;
        return logBasicInfoModel;
    }

    generateIgGrid() {
        const vm = this;
        $("#igGridLog").igGrid({
            width: "100%",
            height: "calc(100% - 5px)",
            features: [
                {
                    name: "Tooltips"
                },
                {
                    name: "Paging",
                    type: "local",
                    pageSize: 100
                },
                {
                    name: "Sorting",
                    type: "local"
                },
                {
                    name: "Resizing",
                    deferredResizing: false,
                    allowDoubleClickToResize: true
                },
                {
                    name: "Filtering",
                    type: "local",
                    filterDropDownItemIcons: false,
                    filterDropDownWidth: 200,
                    filterDialogHeight : "390px",
                    filterDialogWidth : "515px",
                    columnSettings: [
                        { columnKey: "parentKey", allowFiltering: false },
                        { columnKey: "operationId", allowFiltering: false }
                    ]
                }
            ],
            enableTooltip : true,
            rowVirtualization: true,
            virtualization: true,
            virtualizationMode: 'continuous',
            dataSource: vm.listLogBasicInforModel,
            columns: vm.columnsIgGrid()
        });
    }
    generatePersionInforGrid() {
        const vm = this;
        let listLogBasicInfor = vm.listLogBasicInforModel;
        //generate generateHierarchialGrid
        $("#igGridLog").igHierarchicalGrid({
            width: "100%",
            height: "calc(100% - 15px)",
            dataSource: listLogBasicInfor,
            features: [
                {
                    name: "Tooltips",
                    inherit: true
                },
                {
                    name: "Responsive",
                    enableVerticalRendering: false
                },
                {
                    name: "Resizing",
                    deferredResizing: false,
                    allowDoubleClickToResize: true,
                    inherit: true
                },
                {
                    name: "Sorting",
                    inherit: false

                },
                {
                    name: "Paging",
                    pageSize: 100,
                    type: "local",
                    inherit: true
                },
                {
                    name: "Filtering",
                    type: "local",
                    filterDropDownItemIcons: false,
                    filterDropDownWidth: 200,
                     filterDialogHeight : "390px",
                    filterDialogWidth : "515px",
                    columnSettings: [
                        { columnKey: "parentKey", allowFiltering: false },
                        { columnKey: "operationId", allowFiltering: false }
                    ]
                }
            ],
            autoGenerateColumns: false,
            primaryKey: "parentKey",
            hidePrimaryKey: true,
            columns: vm.columnsIgGrid(),
            autoGenerateLayouts: false,
            virtualizationMode: 'continuous',
            columnLayouts: [
                {
                    width: "100%",
                    childrenDataProperty: "lstLogPerCateCorrectRecordDto",
                    autoGenerateColumns: false,
                    hidePrimaryKey: true,
                    primaryKey: "childrentKey",
                    foreignKey: "operationId",
                    columns: [
                        { key: "childrentKey", headerText: "", dataType: "string", hidden: true },
                        { key: "categoryName", headerText: "categoryName", dataType: "string", width: "120px", formatter: _.escape },
                        { key: "targetDate", headerText: "targetDate", dataType: "string", width: "120px" },
                        { key: "itemName", headerText: "itemName", dataType: "string", width: "120px", formatter: _.escape },
                        { key: "infoOperateAttr", headerText: "infoOperateAttr", dataType: "string", width: "120px" },
                        { key: "valueBefore", headerText: "valueBefore", dataType: "string", width: "150px", formatter: _.escape },
                        { key: "valueAfter", headerText: "valueAfter", dataType: "string", width: "150px", formatter: _.escape }

                    ],
                    features: [
                        {
                            name: 'Selection',
                            mode: "row",
                            multipleSelection: false
                        },
                        {
                            name: "Responsive",
                            enableVerticalRendering: false,
                            columnSettings: []
                        }
                    ]
                }
            ],
        });
        vm.checkSubHeader();
    }
    generateDataCorrectLogGrid() {
        const vm = this;
        let listLogBasicInfor = vm.listLogBasicInforModel;
        //generate generateHierarchialGrid
        $("#igGridLog").igHierarchicalGrid({
            width: "100%",
            height: "calc(100% - 15px)",
            dataSource: listLogBasicInfor,
            features: [
                {
                    name: "Tooltips",
                    inherit: true
                },
                {
                    name: "Responsive",
                    enableVerticalRendering: false
                },
                {
                    name: "Resizing",
                    deferredResizing: false,
                    allowDoubleClickToResize: true,
                    inherit: true
                },
                {
                    name: "Sorting",
                    inherit: false
                },
                {
                    name: "Paging",
                    pageSize: 100,
                    type: "local",
                    inherit: true
                },
                {
                    name: "Filtering",
                    type: "local",
                    filterDropDownItemIcons: false,
                    filterDropDownWidth: 200,
                    filterDialogHeight : "390px",
                    filterDialogWidth : "515px",
                    columnSettings: [
                        { columnKey: "parentKey", allowFiltering: false },
                        { columnKey: "operationId", allowFiltering: false }
                    ]
                }
            ],
            autoGenerateColumns: false,
            primaryKey: "parentKey",
            hidePrimaryKey: true,
            columns: vm.columnsIgGrid(),
            autoGenerateLayouts: false,
            virtualizationMode: 'continuous',
            columnLayouts: [
                {
                    width: "100%",
                    childrenDataProperty: "lstLogDataCorrectRecordRefeDto",
                    hidePrimaryKey: true,
                    autoGenerateColumns: false,
                    primaryKey: "childrentKey",
                    foreignKey: "operationId",
                    columns: [
                        { key: "childrentKey", headerText: "", dataType: "string", hidden: true },
                        { key: "targetDate", headerText: "targetDate", dataType: "string", width: "170px" },
                        { key: "itemName", headerText: "itemName", dataType: "string", width: "170px", formatter: _.escape },
                        { key: "valueBefore", headerText: "valueBefore", dataType: "string", width: "170px", formatter: _.escape },
                        { key: "valueAfter", headerText: "valueAfter", dataType: "string", width: "170px", formatter: _.escape },
                        { key: "correctionAttr", headerText: "correctionAttr", dataType: "string", width: "170px" }
                    ],
                    features: [
                        
                        {
                            name: 'Selection',
                            multipleSelection: false
                        },
                        {
                            name: "Responsive",
                            enableVerticalRendering: false,
                            columnSettings: []
                        }
                       
                    ]
                }
            ],
        });

        vm.checkSubHeader();

    }
    checkSubHeader() {
        $("#igGridLog").scroll(function() {
            const showedIcon = $("#igGridLog").data("icon-showed");
            if (!_.isNil(showedIcon)) {
                showedIcon.click();
            }
        });
        const vm = this;
        $(document).delegate("#igGridLog", "igchildgridcreated", function(evt, ui) {
            const headerSetting = $(ui.element).data("headersetting");
            const header = ui.element.find("th[role='columnheader']");
            ui.element.parent().addClass("default-overflow");
            ui.element.parent().css("overflow-x","");

            let helpButton = $('<button>', {
                text: vm.$i18n('?'),
                'data-bind': 'ntsHelpButton: { textId: "CLI003_68", textParams: ["{#CLI003_68}"], position: "right center" }'
            });

            let textHeaderCheck = vm.$i18n('CLI003_61');
            for (var i = 0; i < headerSetting.length; i++) {
                const currentSetting = headerSetting[i];

                if (currentSetting.headerText == textHeaderCheck) {
                    const xHeader = header.filter("th[aria-label='" + currentSetting.key + "']").find(".ui-iggrid-headertext");
                    const x = xHeader.text(currentSetting.headerText);
                    x.append(helpButton);
                    xHeader.attr("id","help-button-id");
                } else {
                    header.filter("th[aria-label='" + currentSetting.key + "']")
                        .find(".ui-iggrid-headertext").text(currentSetting.headerText)
                }
            }
            helpButton.click(function() {
                const container = helpButton.closest(".igscroll-touchscrollable");
                const tooltip = helpButton.parent().find(".nts-help-button-image");
                $(".ui-iggrid-header.ui-widget-header").css("overflow", "visible");
                $("#help-button-id").css({"overflow":"visible"});
                tooltip.css("width","350px");
                if (tooltip.css("display") !== "none") {
                    container.addClass("default-overflow");
                    container.removeClass("overflow-show");
                    container.css("overflow-x","");
                    $("#igGridLog").data("icon-showed", helpButton);
                } else {
                    container.removeClass("default-overflow");
                    container.addClass("overflow-show");
                    container.css("overflow-x","auto");
                    $("#igGridLog").data("icon-showed", null);
                }
            });
            //  binding new viewmodel for only button help
            ko.applyBindings({}, helpButton[0]);
        });

        $(document).delegate("#igGridLog", "igchildgridcreating", function(evt, ui) {
            evt;
            const childSource = ui.options.dataSource;
            const ds = $("#igGridLog").igGrid("option", "dataSource");
            const parentSource = _.isArray(ds) ? ds : ds._data;
            const headerSetting = [];
            const newSource = [];
            let recordType = parseInt(vm.logTypeSelectedCode());
            if (childSource.length > 0) {
                for (var i = 0; i < parentSource.length; i++) {
                    if (parentSource[i].parentKey === childSource[0].parentKey) {
                        headerSetting = parentSource[i].subColumnsHeaders;
                        if (recordType == RECORD_TYPE.DATA_CORRECT) {
                            newSource = _.cloneDeep(parentSource[i].lstLogDataCorrectRecordRefeDto);
                            newSource = _.orderBy(newSource, ['targetDate', 'showOrder'], ['asc', 'asc']);
                        }
                        if (recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                            newSource = _.cloneDeep(parentSource[i].lstLogPerCateCorrectRecordDto);
                        }
                    }
                }
                ui.options.dataSource = newSource;
                $(ui.element).data("headersetting", headerSetting);
            }
        });
        $(document).delegate("#igGridLog", "iggridresizingcolumnresizing", function(evt, ui) {
            $(".ui-iggrid-scrolldiv.ui-widget-content.igscroll-touchscrollable.default-overflow").css("overflow-x", "auto");
            $(".ui-iggrid-header.ui-widget-header").css({"width":"100% !important"}); // th
            $(".ui-iggrid-headertext").css({"white-space":"nowrap","overflow":"hidden","display":"block"}); // span
        });
    }

    setListColumnHeaderLog(recordType: number, listOutputItem: Array<any>) {
        const vm = this;
        vm.columnsIgGrid.push(new IgGridColumnSwitchModel("primarykey", -1, recordType));
        vm.columnsIgGrid.push(new IgGridColumnSwitchModel("parentkey", -2, recordType));
        let lstSubHeader = [22, 23, 24, 29, 30, 31, 33, 25, 26, 27, 28];
        let flg = true;
        let lstSubHeaderPersion = [25, 26, 27, 28];
        let lstSubHeaderDataCorrect = [22, 23, 24];
        _.forEach(listOutputItem, function(item) {
            if (lstSubHeader.indexOf(item.itemNo) > -1) {
                if ((recordType == RECORD_TYPE.LOGIN || recordType == RECORD_TYPE.UPDATE_PERSION_INFO)
                    && ITEM_NO.ITEM_NO22 == item.itemNo) {
                    vm.columnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
                }
                if (lstSubHeaderPersion.indexOf(item.itemNo) > -1 && recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
                    if (flg) {
                        vm.supColumnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
                        flg = false;
                    }
                } else {
                    vm.supColumnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
                }
                if (lstSubHeaderDataCorrect.indexOf(item.itemNo) > -1 && recordType == RECORD_TYPE.DATA_CORRECT) {
                    if (flg) {
                        vm.supColumnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
                        flg = false;
                    }
                } else {
                    vm.supColumnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
                }
            }
            else {
                vm.columnsIgGrid.push(new IgGridColumnSwitchModel(item.itemName, item.itemNo, recordType));
            }
        });
    }


    exportCsvF() {
        //CLI003: fix bug #108873, #108865
        let vm = this,
            format = 'YYYY/MM/DD HH:mm:ss',
            recordType = Number(vm.logTypeSelectedCode()),
            paramOutputItem = {
            recordType: vm.logTypeSelectedCode(),
            itemNos: null,
            },
            checkProcess = false,
            paramLog = {
            // recordType=0,1 k co taget
            listTagetEmployeeId: vm.targetEmployeeIdList(),
            listOperatorEmployeeId: vm.listEmployeeIdOperator(),
            startDateTaget: moment.utc(vm.dateValue().startDate, "YYYY/MM/DD").toISOString(),
            endDateTaget: moment.utc(vm.dateValue().endDate, "YYYY/MM/DD").toISOString(),
            startDateOperator: moment.utc(vm.startDateOperator(), format).toISOString(),
            endDateOperator: moment.utc(vm.endDateOperator(), format).toISOString(),
            //CLI003: fix bug #108971, #108970
            recordType: vm.logTypeSelectedCode(),
            targetDataType: vm.dataTypeSelectedCode(),
            systemType: vm.systemTypeSelectedCode(),
            };

        if (vm.checkFormatDate() === '2') {             
            paramLog.endDateTaget = moment.utc(vm.dateValue().endDate, "YYYY/MM/DD" ).endOf('month').toISOString();              
        } else {
            paramLog.endDateTaget = moment.utc(vm.dateValue().endDate, "YYYY/MM/DD").toISOString();
        }

        switch (recordType) {
            case RECORD_TYPE.LOGIN: {
                paramOutputItem.itemNos = vm.columnsHeaderLogRecord();
                checkProcess = true;
                break
            }
            case RECORD_TYPE.START_UP: {
                paramOutputItem.itemNos = vm.columnsHeaderLogStartUp();
                checkProcess = true;
                break;
            }
            case RECORD_TYPE.UPDATE_PERSION_INFO: {
                paramOutputItem.itemNos = vm.columnsHeaderLogPersionInfo();
                checkProcess = true;
                break
            }
            case RECORD_TYPE.DATA_CORRECT: {
                paramOutputItem.itemNos = vm.columnsHeaderLogDataCorrect();
                checkProcess = true;
                break;
            }
            default: {
                break;
            }
        }
        
        $('#contents-area').focus();
        
        if(checkProcess == true){
          let params = {
              logParams: paramLog,
              paramOutputItem: paramOutputItem,
              lstHeaderDto: vm.columnsIgGrid(),
              lstSupHeaderDto: vm.supColumnsIgGrid()
          };
            vm.$blockui('grayout');
            //CLI003: fix bug #108971, #108970
          service.logSettingExportCsv(params).done(() => {
          }).always(() => {
            vm.$blockui('clear');
            vm.$errors('clear');
          });              
        }
    }
    checkDestroyIgGrid() {
        let vm = this;
        let recordType = Number(vm.logTypeSelectedCode());
        if (recordType == RECORD_TYPE.DATA_CORRECT ||
            recordType == RECORD_TYPE.UPDATE_PERSION_INFO) {
            //generate table data correct
            if ($('#igGridLog_container').length > 0 || $('#igGridLog_employeeCodeTaget').length > 0) {
                $("#igGridLog").igHierarchicalGrid("destroy");
            }
        } else {
            if ($('#igGridLog_container').length > 0) {
                $("#igGridLog").igGrid("destroy");
            }
        }
    }

    previousScreenB() {
      const vm = this;
      vm.$jump("/view/cli/003/b/index.xhtml");
  }

    constructor(data: any) {
      super();
      const vm = this;
      vm.dataFromB = data;
      vm.initComponentScreenF(vm.dataFromB);
    }

    created(params: any) {
      const vm = this;
    }
    mounted() {
      const vm = this;
    }

  }
}
