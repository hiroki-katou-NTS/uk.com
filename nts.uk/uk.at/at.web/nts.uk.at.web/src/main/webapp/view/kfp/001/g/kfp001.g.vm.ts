module nts.uk.at.view.kfp001.g.viewmodel {
  import block = nts.uk.ui.block;
  import getText = nts.uk.resource.getText;
  import alertError = nts.uk.ui.dialog.alertError;
  import getShared = nts.uk.ui.windows.getShared;

  export class ScreenModel {

    items: KnockoutObservableArray<MonthlyClosureErrorInfor>;
    code: KnockoutObservable<string> = ko.observable("");
    name: KnockoutObservable<string> = ko.observable("");
    start: KnockoutObservable<string> = ko.observable("");
    end: KnockoutObservable<string> = ko.observable("");
    errorNum: KnockoutObservable<string> = ko.observable("");
    currentCode: KnockoutObservable<string> = ko.observable("");
    params: any;
    arbitraryPeriodAggrExecId: KnockoutObservable<string> = ko.observable("");
    aggrPeriodExecution: KnockoutObservable<AggrPeriodExcutionDto>;
    aggrPeriod: KnockoutObservable<OptionalAggrPeriodDto>;
    periodTarget: KnockoutObservable<AggrPeriodTargetDto>;
    aggrPeriodErrorInfos: KnockoutObservableArray<MonthlyClosureErrorInfor>;
    aggrPeriodDto: KnockoutObservable<AggrPeriodDto>;

    constructor() {
      var self = this;
      self.params = getShared("Kfp001gParams");
      self.code(self.params.code);
      self.name(self.params.name);
      self.start(self.params.start);
      self.end(self.params.end);
      self.errorNum(self.params.dispErrorPeopleNum);
      self.items = ko.observableArray([]);
      let param = nts.uk.ui.windows.getShared("openH");
      if (param != null) {
        self.arbitraryPeriodAggrExecId = param.arbitraryPeriodAggrExecId;
      }
    }

    startPage(): JQueryPromise<any> {
      let self = this,
      dfd = $.Deferred();
      block.invisible();
      
      service.getErrorInfos(self.params.logId).done((result) => {
        let listErr: Array<MonthlyClosureErrorInfor> = [];
        for (let i = 0; i < result.length; i++) {
          let item = result[i];
          listErr.push(new MonthlyClosureErrorInfor(i + 1, item.employeeCode, item.employeeName, item.procDate, item.errorMessage));
        }
        self.items(listErr);
        dfd.resolve();
      }).fail((error) => {
        dfd.reject();
        alertError(error);
      }).always(() => {
        block.clear();
      });

      // アルゴリズム「起動時処理」を実行する
      service.getAggrPeriod(self.arbitraryPeriodAggrExecId).done((result) => {
        self.aggrPeriodDto(result);
        dfd.resolve();
      }).fail((error) => {
        dfd.reject();
        alertError(error);
      }).always(() => {
        block.clear();
      });
      return dfd.promise();
    }

    private exportCsv(): void {
      let self = this;
      block.invisible();
      service.exportCsv({ aggrPeriodId: self.params.logId }).always(() => {
        block.clear();
      });
    }

    private closeDialog() {
      nts.uk.ui.windows.close();
    }


  }

  class MonthlyClosureErrorInfor {
    no: number;
    employeeCode: string;
    employeeName: string;
    processDate: string;
    errorMessage: string;

    constructor(no: number, employeeCode: string, employeeName: string, procDate: string, errorMessage: string) {
      this.no = no;
      this.employeeCode = employeeCode;
      this.employeeName = employeeName;
      this.errorMessage = errorMessage;
      this.processDate = procDate;
    }
  }

  class AggrPeriodExcutionDto {
    companyId: string;
    executionEmpId: string;
    aggrFrameCode: string;
    aggrId: string;
    startDateTime: string;
    endDateTime: string;
    executionAtr: number;
    executionStatus: number;
    presenceOfError: number;

    constructor(
      companyId: string, 
      executionEmpId: string, 
      aggrFrameCode: string, 
      aggrId: string, 
      startDateTime: string,
      endDateTime: string,
      executionAtr: number,
      executionStatus: number,
      presenceOfError: number
    ) {
      this.companyId = companyId;
      this.executionEmpId = executionEmpId;
      this.aggrFrameCode = aggrFrameCode;
      this.aggrId = aggrId;
      this.startDateTime = startDateTime;
      this.endDateTime = endDateTime;
      this.executionAtr = executionAtr;
      this.executionStatus = executionStatus;
      this.presenceOfError = presenceOfError;
    }
  }

  class OptionalAggrPeriodDto {
    companyId: string;
    aggrFrameCode: string;
    startDate: string;
    endDate: string;
    optionalAggrName: number;

    constructor(
      companyId: string, 
      aggrFrameCode: string, 
      startDate: string,
      endDate: string,
      optionalAggrName: number
    ) {
      this.companyId = companyId;
      this.aggrFrameCode = aggrFrameCode;
      this.startDate = startDate;
      this.endDate = endDate;
      this.optionalAggrName = optionalAggrName;
    }
  }
  class AggrPeriodTargetDto {
    aggrId: string;
    employeeId: string;
    state: number;

    constructor(
      aggrId: string, 
      employeeId: string, 
      state: number
    ) {
      this.aggrId = aggrId;
      this.employeeId = employeeId;
      this.state = state;
    }
  }

  class EmployeeDto {
    sid: string;
    scd: string;
    bussinessName: string;

    constructor( sid: string, scd: string, bussinessName: string) {
      this.sid = sid;
      this.scd = scd;
      this.bussinessName = bussinessName;
    }
  }

  class AggrPeriodDto {
    aggrPeriodExcutionDto: AggrPeriodExcutionDto;
    optionalAggrPeriodDto: OptionalAggrPeriodDto;
    periodTargetDto: AggrPeriodTargetDto[];
    errorInfos: MonthlyClosureErrorInfor[];
    employeeDto: EmployeeDto[];

  }
}