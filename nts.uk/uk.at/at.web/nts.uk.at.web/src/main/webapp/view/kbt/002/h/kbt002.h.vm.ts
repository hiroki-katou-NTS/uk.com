/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kbt002.h {

  const DOM_DATA_VALUE = 'data-value';
  @bean()
  export class ScreenModel extends ko.ViewModel {
    execLogList: KnockoutObservableArray<ExecutionLog> = ko.observableArray([]);
    gridListColumns: KnockoutObservableArray<any>;
    enable: KnockoutObservable<boolean> = ko.observable(true);
    required: KnockoutObservable<boolean> = ko.observable(true);
    dateValue: KnockoutObservable<any> = ko.observable({});
    startDateString: KnockoutObservable<string> = ko.observable("");
    endDateString: KnockoutObservable<string> = ko.observable("");
    execItemCd: string;

    // Start page
    created(params: any) {
      const vm = this;
      if (params) {
        vm.execItemCd = params.execItemCd;
      }
    }

    mounted() {
      const vm = this;
      vm.startDateString.subscribe(function (value) {
        vm.dateValue().startDate = value;
        vm.dateValue.valueHasMutated();
      });

      vm.endDateString.subscribe(function (value) {
        vm.dateValue().endDate = value;
        vm.dateValue.valueHasMutated();
      });
      var today = new Date();
      var dd = today.getDate();
      var mm = today.getMonth() + 1;
      var yyyy = today.getFullYear();
      vm.startDateString(yyyy + "/" + mm + "/" + dd);
      vm.endDateString(yyyy + "/" + mm + "/" + dd);
      $("#daterangepicker").focus();

      $(document).on("click", ".button-open-g", function () {
        const key = $(this).attr(DOM_DATA_VALUE);
        vm.openDialogG(key);
      });

      vm.initData();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    loadIgrid() {
      const vm = this;
      // var execItemCdHeader = '<button tabindex="5" class="setting small" style="text-align: center;margin-top: 0px !important;margin-bottom: 0px !important;" onclick="$vm.openDialogG(this, \'${execItemCd}\',\'${execId}\')"  >' + vm.$i18n("KBT002_159") + '</button>';
      $("#grid").igGrid({
        primaryKey: "execId",
        height: 520,
        dataSource: vm.execLogList(),
        autoGenerateColumns: false,
        alternateRowStyles: false,
        dataSourceType: "json",
        columns: [
          { key: "lastExecDateTime", width: "180px", height: "25px", headerText: vm.$i18n('KBT002_214'), dataType: "string", formatter: _.escape },
          { key: "lastEndExecDateTime", width: "180px", height: "25px", headerText: vm.$i18n('KBT002_215'), dataType: "string", formatter: _.escape },
          { key: "rangeDateTime", width: "80px", height: "25px", headerText: vm.$i18n('KBT002_216'), dataType: "string", formatter: _.escape },
          { key: "overallStatusText", width: "70px", height: "25px", headerText: vm.$i18n('KBT002_217'), dataType: "string", formatter: _.escape },
          { key: "errorSystemText", width: "100px", height: "25px", headerText: vm.$i18n('KBT002_218'), dataType: "string", formatter: _.escape },
          { key: "errorBusinessText", width: "80px", height: "25px", headerText: vm.$i18n('KBT002_219'), dataType: "string", formatter: _.escape },
          {
            key: "execItemCd",
            width: "45px",
            height: "25px",
            headerText: '',
            dataType: "string",
            formatter: (value: any, item: ExecutionLog) => {
              const $button = $("<button>",
                {
                  "class": "setting small button-open-g", "tabindex": 5,
                  "style": "text-align: center;margin-top: 0px !important;margin-bottom: 0px !important;",
                  "text": vm.$i18n("KBT002_159")
                });
              $button.attr(DOM_DATA_VALUE, item["execId"]);
              return $button[0].outerHTML;
            }
          },
          { key: "overallErrorText", width: "430px", height: "25px", headerText: vm.$i18n('KBT002_220'), dataType: "string", formatter: _.escape },
          { key: "execId", dataType: "string", formatter: _.escape, hidden: true }
        ],
        features: [
          {
            name: "Updating",
            showDoneCancelButtons: false,
            enableAddRow: false,
            enableDeleteRow: false,
            editMode: 'cell',
            columnSettings: [
              { columnKey: "execItemCd", readOnly: true },
              { columnKey: "lastExecDateTime", readOnly: true },
              { columnKey: "lastEndExecDateTime", readOnly: true },
              { columnKey: "rangeDateTime", readOnly: true },
              { columnKey: "overallStatus", readOnly: true },
              { columnKey: "errorSystemText", readOnly: true },
              { columnKey: "errorBusinessText", readOnly: true },
              { columnKey: "overallErrorText", readOnly: true },
            ]
          },
          {
            name: "Selection",
            mode: "row",
            multipleSelection: false,
            multipleCellSelectOnClick: false
          },
          {
            name: 'Paging',
            pageSize: 15,
            currentPageIndex: 0
          },
          {
            name: "Sorting",
            persist: true
          },
        ],
        ntsControls: [{ name: 'execItemCd', text: 'execItemCd', controlType: 'button' }]
      });
    }

    initData() {
      const vm = this;
      vm.$blockui("grayout");
        service.getProcExecList(vm.execItemCd)
          .done(data => {
            let execLogs: ExecutionLog[] = [];
            _.forEach(data, function (item) {
              execLogs.push(new ExecutionLog(item));
            });
            vm.execLogList(execLogs);
            vm.loadIgrid();
          })
          .always(() => vm.$blockui("clear"));
    }

    search() {
      const vm = this;
      vm.$validate().then((valid: boolean) => {
        if (valid) {
          var today = new Date();
          var startDateSplit = vm.dateValue().startDate.split("/");
          var endDateSplit = vm.dateValue().endDate.split("/");
          var endDate = new Date(endDateSplit[0], endDateSplit[1] - 1, endDateSplit[2]);
          if (moment.utc(endDate, "YYYY/MM/DD").isBefore(moment.utc(today, "YYYY/MM/DD"))) {
            vm.$blockui("grayout");
            var startDate = new Date(startDateSplit[0], startDateSplit[1] - 1, startDateSplit[2]);
            //ProcessExecutionDateParam
            var param = new ProcessExecutionDateParam(vm.execItemCd, moment.utc(startDate, "YYYY/MM/DD"), moment.utc(endDate, "YYYY/MM/DD"));
            service.findListDateRange(param).done(data => {
              let execLogs: ExecutionLog[] = [];
              _.forEach(data, function (item) {
                execLogs.push(new ExecutionLog(item));
              });
              vm.execLogList(execLogs);
              $("#grid").igGrid("option", "dataSource", execLogs);
            }).always(() => vm.$blockui("clear"));
          } else {
            vm.$dialog.error({ messageId: "Msg_1077" });
          }
        }
      });
    }

    public openDialogG(execId: string) {
      const vm = this;
      const executionLog = _.find(vm.execLogList(), { execId: execId });
      const param = {
        schCreateStart: executionLog.schCreateStart,
        schCreateEnd: executionLog.schCreateEnd,
        dailyCreateStart: executionLog.dailyCreateStart,
        dailyCreateEnd: executionLog.dailyCreateEnd,
        dailyCalcStart: executionLog.dailyCalcStart,
        dailyCalcEnd: executionLog.dailyCalcEnd,
        reflectApprovalResultStart: executionLog.reflectApprovalResultStart,
        reflectApprovalResultEnd: executionLog.reflectApprovalResultEnd,
        execId: executionLog.execId,
        execItemCd: executionLog.execItemCd,
        overallError: executionLog.overallErrorText,
        overallStatus: executionLog.overallStatusText,
        taskLogList: executionLog.taskLogList
      };
      vm.$window.modal("/view/kbt/002/g/index.xhtml", param);
    }
  }
  export class ExecutionLog {
    lastExecDateTime: string;
    execItemCd: string;
    overallStatus: string;
    overallError: string;
    execId: string;
    lastEndExecDateTime: string;
    rangeDateTime: string;
    errorSystem: boolean;
    errorBusiness: boolean;
    errorSystemText: string;
    errorBusinessText: string;
    overallStatusText: string;
    overallErrorText: string;
    taskLogList: any[];
    schCreateStart: moment.Moment;
    schCreateEnd: moment.Moment;
    dailyCreateStart: moment.Moment;
    dailyCreateEnd: moment.Moment;
    dailyCalcStart: moment.Moment;
    dailyCalcEnd: moment.Moment;
    reflectApprovalResultStart: moment.Moment;
    reflectApprovalResultEnd: moment.Moment;
    constructor(param: any) {
      this.execItemCd = param.execItemCd;
      this.overallStatus = param.overallStatus;
      this.overallError = param.overallError;
      this.lastExecDateTime = moment.utc(param.lastExecDateTime).format("YYYY/MM/DD hh:mm:ss");
      this.execId = param.execId;
      this.lastEndExecDateTime = param.lastEndExecDateTime ? moment.utc(param.lastEndExecDateTime).format("YYYY/MM/DD hh:mm:ss") : null;
      this.rangeDateTime = param.rangeDateTime;
      this.errorSystem = param.errorSystem;
      this.errorBusiness = param.errorBusiness;
      this.errorSystemText = param.errorSystemText;
      this.errorBusinessText = param.errorBusinessText;
      this.overallErrorText = param.overallErrorText;
      this.overallStatusText = param.overallStatusText;
      this.schCreateStart = param.schCreateStart;
      this.schCreateEnd = param.schCreateEnd;
      this.dailyCreateStart = param.dailyCreateStart;
      this.dailyCreateEnd = param.dailyCreateEnd;
      this.dailyCalcStart = param.dailyCalcStart;
      this.dailyCalcEnd = param.dailyCalcEnd;
      this.reflectApprovalResultStart = param.reflectApprovalResultStart;
      this.reflectApprovalResultEnd = param.reflectApprovalResultEnd;
      this.taskLogList = param.taskLogListDto;
    }
  }

  export class ProcessExecutionDateParam {
    execItemCd: string;
    startDate: any;
    endDate: any;
    constructor(execItemCd: string, startDate: any, endDate: any) {
      let vm = this;
      vm.execItemCd = execItemCd;
      vm.startDate = startDate;
      vm.endDate = endDate;
    }
  }
}
