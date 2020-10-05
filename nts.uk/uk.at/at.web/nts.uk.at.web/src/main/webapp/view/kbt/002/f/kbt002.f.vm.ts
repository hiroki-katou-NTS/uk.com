/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kbt002.f {

  const API = {
    getExecItemInfoList: "at/function/processexec/getExecItemInfoList",
  };

  @bean()
  export class KBT002FViewModel extends ko.ViewModel {
    $grid!: JQuery;

    dataSource: KnockoutObservableArray<any> = ko.observableArray([]);
    dataSourceModel: KnockoutObservableArray<ExecutionItemInfomationModel> = ko.observableArray([]);
    selectedExecCd: KnockoutObservable<string> = ko.observable('');

    mounted() {
      const vm = this;
      vm.$grid = $("#F2_1");
      vm.getExecItemInfoList();
    }

    /**
     * アルゴリズム「起動時処理」を実行する
     */
    private getExecItemInfoList() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.getExecItemInfoList)
        .then((response) => {
          vm.$blockui('clear');
          vm.dataSource(response);
          const dataSourceModel = _.map(response, (item) => new ExecutionItemInfomationModel(vm, item));
          vm.dataSourceModel(dataSourceModel);
          // Render grid list
          vm.initGridList();
        })
        .always(() => vm.$blockui('clear'));
    }

    private initGridList() {
      const vm = this;
      if (vm.$grid.data("igGrid")) {
        vm.$grid.ntsGrid("destroy");
      }
      vm.$nextTick(() => {
        vm.$grid.ntsGrid({
          name: '#[KBT002_126]',
          width: "1200px",
          height: "415px",
          dataSource: vm.dataSourceModel(),
          primaryKey: 'execItemCd',
          columns: [
            { headerText: '', key: 'execItemCd', hidden: true },
            { headerText: vm.$i18n("KBT002_201"), key: 'execItem', width: 150, formatter: _.escape },
            {
              headerText: "",
              key: 'showWarningIcon',
              width: 30,
              formatter: (value: number) => {
                if (value === 0) {
                  return `<div class="cell-center"><i class="img-icon icon-warning" title="${vm.$i18n("KBT002_314")}"></i></div>`;
                } else if (value === 1) {
                  return `<div class="cell-center"><i class="img-icon icon-warning" title="${vm.$i18n("KBT002_315")}"></i></div>`;
                } else {
                  return '';
                }
              }
            },
            {
              headerText: vm.$i18n("KBT002_121"),
              key: 'execStatus',
              width: 80,
              formatter: (value: number) => {
                if (value === 0) {
                  // 現在の実行状態 = 実行中
                  return `<div class="color-holiday">${vm.$i18n('KBT002_265')}</div>`;
                } else if (value === 1) {
                  // 現在の実行状態 = 待機中
                  return `<div class="color-daily-extra-holiday">${vm.$i18n('KBT002_266')}</div>`;
                } else if (value === 2) {
                  // 現在の実行状態 = 無効
                  return `<div class="color-weekdays">${vm.$i18n('KBT002_124')}</div>`;
                } else {
                  // 現在の実行状態 = empty
                  return "";
                }
              }
            },
            {
              headerText: vm.$i18n("KBT002_257"),
              key: 'execItemCd',
              width: 80,
              template: `<div class="cell-center"><button class="btn-borderless"><i class="img-icon icon-start"></i>${vm.$i18n('KBT002_262')}</button></div>`
            },
            {
              headerText: vm.$i18n("KBT002_258"),
              key: 'execItemCd',
              width: 80,
              template: `<div class="cell-center"><button class="btn-borderless"><i class="img-icon icon-stop"></i>${vm.$i18n('KBT002_133')}</button></div>`
            },
            { headerText: vm.$i18n("KBT002_131"), key: 'nextExecDate', width: 180, formatter: _.escape },
            { headerText: vm.$i18n("KBT002_259"), key: 'isTaskExecution', width: 100, ntsControl: 'Switch' },
            { headerText: vm.$i18n("KBT002_260"), key: 'lastStartDateTime', width: 180, formatter: _.escape },
            { headerText: vm.$i18n("KBT002_261"), key: 'lastEndDateTime', width: 180, formatter: _.escape },
            { headerText: vm.$i18n("KBT002_204"), key: 'processingTime', width: 80, formatter: _.escape },
            {
              headerText: vm.$i18n("KBT002_143"),
              key: 'overallStatus',
              width: 80,
              formatter: (value: number) => {
                if (value === 0) {
                  // 全体の終了状態 = 未実施
                  return `<div class="color-daily-extra-holiday">${vm.$i18n('KBT002_267')}</div>`;
                } else if (value === 1) {
                  // 全体の終了状態 = 完了
                  return `<div class="color-working-schedule">${vm.$i18n('KBT002_268')}</div>`;
                } else if (value === 2) {
                  // 全体の終了状態 =強制終了
                  return `<div class="color-holiday">${vm.$i18n('KBT002_258')}</div>`;
                } else if (value === 3) {
                  // 全体の終了状態 = 終了中
                  return `<div class="color-holiday">${vm.$i18n('KBT002_269')}</div>`;
                } else {
                  // 全体の終了状態 = empty
                  return "";
                }
              }
            },
            { headerText: vm.$i18n("KBT002_206"), key: 'systemError', width: 80, formatter: _.escape },
            { headerText: vm.$i18n("KBT002_207"), key: 'businessError', width: 80, formatter: _.escape },
            { headerText: vm.$i18n("KBT002_144"), key: 'btnDetail', width: 80, ntsControl: 'ButtonDetail' },
            { headerText: vm.$i18n("KBT002_147"), key: 'btnHistory', width: 80, ntsControl: 'ButtonHistory' },
          ],
          features: [
            {
              name: "Selection",
              mode: "row",
              multipleSelection: false,
              activation: false,
              rowSelectionChanged: (event: any, ui: any) => vm.selectedExecCd(ui.row.id),
            },
          ],
          ntsControls: [
            {
              name: 'Switch',
              options: [
                { value: true, text: vm.$i18n('KBT002_123') },
                { value: false, text: vm.$i18n('KBT002_124') }
              ],
              optionsValue: 'value',
              optionsText: 'text',
              controlType: 'SwitchButtons'
            },
            {
              name: 'ButtonDetail',
              text: vm.$i18n('KBT002_144'),
              click: (evt: ExecutionItemInfomationModel) => { console.log(evt); },
              controlType: 'Button'
            },
            {
              name: 'ButtonHistory',
              text: vm.$i18n('KBT002_147'),
              click: (evt: ExecutionItemInfomationModel) => { console.log(evt); },
              controlType: 'Button'
            },
          ],
          virtualization: true,
          virtualizationMode: 'continuous',
        });
      });
    }

    /**
     * 各処理の前回終了状態を見る
     */
    private openDialogG(execItemCd: string) {
      const vm = this;
      const params = _.find(vm.dataSource(), (o) => o.execItemCd === execItemCd);
      // TODO check param
      if (params) {
        // 画面G「前回終了状態詳細」を起動する
        vm.$blockui('grayout');
        vm.$window.modal('/view/kbt/002/g/index.xhtml', params)
          .then((result: any) => {
            // bussiness logic after modal closed
            vm.$blockui('clear');
          });
      }
    }

    /**
     * 実行履歴を見る
     */
    private openDialogH(execItemCd: string) {
      const vm = this;
      const params = { execItemCd: execItemCd };
      // H画面「実行履歴」を起動する
      vm.$blockui('grayout');
      vm.$window.modal('/view/kbt/002/h/index.xhtml', params)
        .then((result: any) => {
          // bussiness logic after modal closed
          vm.$blockui('clear');
        });
    }

    /**
     * 更新処理を即時実行する
     */
    private executeUpdateProcess() {
      // TODO
    }

    /**
     * 実行中の更新処理を終了する
     */
    private stopRunningUpdateProcess() {
      // TODO
    }

    /**
     * 実行タスクの設定変更する
     */
    private changeSetting() {
      // TODO
    }

    /**
     * 最新の情報に更新する
     */
    public updateLastestInfo() {
      // TODO
    }

    /**
     * 実行履歴ログを出力する
     */
    public exportHistoryLog() {
      // TODO
    }

    // private getProcExecLogList(savedExecItemCd?: string): JQueryPromise<void> {
    //   let self = this;
    //   let dfd = $.Deferred<void>();
    //   self.execLogList([]);
    //   service.getProcExecLogList().done(function (execLogList) {
    //     if (execLogList && execLogList.length > 0) {
    //       self.execLogList(execLogList);
    //       if (nts.uk.text.isNullOrEmpty(savedExecItemCd)) {
    //         self.selectedExecCd(execLogList[0].execItemCd);
    //       } else {
    //         self.selectedExecCd(savedExecItemCd);
    //       }

    //     } else {
    //       alert({ messageId: "Msg_851" });
    //     }
    //     dfd.resolve();
    //   });
    //   return dfd.promise();
    // }

    // openDialogH(execItemCd) {
    //   let self = this;
    //   block.grayout();
    //   //                let cd = execItemCd;
    //   //                if(execItemCd<10)
    //   //                    cd = "0"+cd;

    //   setShared('inputDialogH', { execItemCd: execItemCd });
    //   modal("/view/kbt/002/h/index.xhtml").onClosed(function () {
    //     block.clear();
    //   });
    // }

    /**
     * 最新の情報に更新する
     */
    // public updateInfo() {
    //   const vm = this;
    //   ko.cleanNode(igrid);
    //   $.when(vm.getProcExecLogList()).done(() => {
    //     ko.applyBindings(self, igrid);
    //   });
    // }

    // execute(idd) {
    //   let self = this;
    //   //                var dfd = $.Deferred();
    //   let cd = "" + idd;
    //   if (idd < 10)
    //     cd = "0" + cd;
    //   //$("#A"+cd).prop('disabled',true);
    //   block.grayout();
    //   let command: any = self.toJsonObject();
    //   service.execute(command).done(function (x) {
    //     self.taskId(x.id);
    //     self.repeatCheckAsyncResult();
    //     /* ko.cleanNode(igrid);
    //        $.when(self.getProcExecLogList()).done(()=>{
    //             ko.applyBindings(self,igrid);
    //             block.clear();
    //        });
    //    */
    //     block.clear();
    //   }).fail(function (res) {
    //     nts.uk.ui.dialog.alertError({ messageId: res.messageId });
    //     self.getProcExecLogList();
    //   });

    //   let newExecLogList = [];
    //   _.forEach(self.execLogList(), function (x) {
    //     if (x.execItemCd == command.execItemCd) {
    //       newExecLogList.push({
    //         execItemCd: x.execItemCd,
    //         companyId: x.companyId,
    //         execItemName: x.execItemName,
    //         currentStatusCd: 0,
    //         currentStatus: "実行中",
    //         overallStatusCd: x.overallStatusCd,
    //         overallStatus: " ",
    //         overallError: x.overallError,
    //         prevExecDateTime: x.prevExecDateTime,
    //         schCreateStart: x.schCreateStart,
    //         schCreateEnd: x.schCreateEnd,
    //         dailyCreateStart: x.dailyCreateStart,
    //         dailyCreateEnd: x.dailyCreateEnd,
    //         dailyCalcStart: x.dailyCalcStart,
    //         dailyCalcEnd: x.dailyCalcEnd,
    //         execId: x.execId,
    //         prevExecDateTimeEx: x.prevExecDateTimeEx,
    //         taskLogList: x.taskLogList,
    //         //                            lastEndExecDateTime: x.lastEndExecDateTime,
    //         //                            errorSystem:         x.errorSystem,
    //         //                            errorBusiness:       x.errorBusiness
    //       });
    //     } else {
    //       newExecLogList.push(x);
    //     }

    //   });

    //   ko.cleanNode(igrid);
    //   self.execLogList(newExecLogList);
    //   ko.applyBindings(self, igrid);
    //   $("#A" + cd).prop('disabled', true);

    //   //                return dfd.promise();
    // }

    // logOutput() { }

    // private repeatCheckAsyncResult(): void {
    //   var self = this;
    //   nts.uk.deferred.repeat(conf => conf
    //     .task(() => {
    //       return nts.uk.request.asyncTask.getInfo(self.taskId()).done(info => {
    //         //ExecuteProcessExecCommandHandler
    //         var message101 = self.getAsyncData(info.taskDatas, "message101").valueAsString;
    //         if (message101 == "Msg_1101" && self.isOnceMessage101()) {
    //           self.isOnceMessage101(false);
    //           nts.uk.ui.dialog.alertError({ messageId: m });
    //           ko.cleanNode(igrid);
    //           $.when(self.getProcExecLogList()).done(() => {
    //             ko.applyBindings(self, igrid);
    //           });
    //         }

    //         //TerminateProcessExecutionCommandHandler
    //         var currentStatusIsOneOrTwo = self.getAsyncData(info.taskDatas, "currentStatusIsOneOrTwo").valueAsString;
    //         if (currentStatusIsOneOrTwo == "Msg_1102" && self.isOnceCurrentStatus()) {
    //           self.isOnceCurrentStatus(false);
    //           nts.uk.ui.dialog.alertError({ messageId: currentStatusIsOneOrTwo });
    //           ko.cleanNode(igrid);
    //           $.when(self.getProcExecLogList()).done(() => {
    //             ko.applyBindings(self, igrid);
    //           });
    //         }
    //         var interupt = self.getAsyncData(info.taskDatas, "interupt").valueAsString;
    //         if (interupt == "true" && self.isOnceInterupt()) {
    //           self.isOnceInterupt(false);
    //           ko.cleanNode(igrid);
    //           $.when(self.getProcExecLogList()).done(() => {
    //             ko.applyBindings(self, igrid);
    //           });
    //         }
    //         var createSchedule = self.getAsyncData(info.taskDatas, "createSchedule").valueAsString;
    //         if (createSchedule == "done" && self.isCreateSchedule()) {
    //           self.isCreateSchedule(false);
    //           ko.cleanNode(igrid);
    //           $.when(self.getProcExecLogList()).done(() => {
    //             ko.applyBindings(self, igrid);
    //           });
    //         }
    //         var task = self.getAsyncData(info.taskDatas, "taskId").valueAsString;
    //         if (!nts.uk.text.isNullOrEmpty(task)) {
    //           self.taskTerminate(task);
    //         }
    //       });
    //     }).while(info => info.pending || info.running)
    //     .pause(1000)
    //   );
    // }

    // private getAsyncData(data: Array<any>, key: string): any {
    //   var result = _.find(data, (item) => {
    //     return item.key == key;
    //   });
    //   return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
    // }

    // terminate() {
    //   let self = this;
    //   block.grayout();
    //   //                var dfd = $.Deferred();
    //   let command: any = self.toJsonObject();
    //   service.terminate(command);
    //   ko.cleanNode(igrid);
    //   $.when(self.getProcExecLogList()).done(() => {
    //     ko.applyBindings(self, igrid);
    //     block.clear();
    //   });
    //   //                return dfd.promise();
    // }

    // /**
    //  * toJsonObject
    //  */
    // private toJsonObject(): any {
    //   let self = this;

    //   // to JsObject
    //   let command: any = {};
    //   command.execType = 1;
    //   command.companyId = self.currentExecLog().companyId();
    //   command.execItemCd = self.currentExecLog().execItemCd();
    //   command.execItemName = self.currentExecLog().execItemName();
    //   command.currentStatusCd = self.currentExecLog().currentStatusCd();
    //   command.currentStatus = self.currentExecLog().currentStatus();
    //   command.overallStatusCd = self.currentExecLog().overallStatusCd();
    //   command.overallStatus = self.currentExecLog().overallStatus();
    //   command.overallError = self.currentExecLog().overallError();
    //   command.prevExecDateTime = self.currentExecLog().prevExecDateTime();
    //   command.schCreateStart = self.currentExecLog().schCreateStart();
    //   command.schCreateEnd = self.currentExecLog().schCreateEnd();
    //   command.dailyCreateStart = self.currentExecLog().dailyCreateStart();
    //   command.dailyCreateEnd = self.currentExecLog().dailyCreateEnd();
    //   command.dailyCalcStart = self.currentExecLog().dailyCalcStart();
    //   command.dailyCalcEnd = self.currentExecLog().dailyCalcEnd();
    //   command.execId = self.currentExecLog().execId();
    //   command.taskTerminate = self.taskTerminate();
    //   return command;
    // }

  }

  // export interface ITaskLog {
  //   taskId: string;
  //   status: number;
  // }

  // export class TaskLog {
  //   taskId: KnockoutObservable<string> = ko.observable('');
  //   status: KnockoutObservable<number> = ko.observable(null);
  //   constructor(param: ITaskLog) {
  //     let self = this;
  //     self.taskId(param.taskId || '');
  //     self.status(param.status);
  //   }
  // }

  // export interface IExecutionLog {
  //   execItemCd: string;
  //   companyId: string;
  //   execItemName: string;
  //   currentStatusCd: number;
  //   currentStatus: string;
  //   overallStatusCd: number;
  //   overallStatus: string;
  //   overallError: number;
  //   prevExecDateTime: string;
  //   schCreateStart: string;
  //   schCreateEnd: string;
  //   dailyCreateStart: string;
  //   dailyCreateEnd: string;
  //   dailyCalcStart: string;
  //   dailyCalcEnd: string;
  //   execId: string;
  //   prevExecDateTimeEx: string;
  //   taskLogList: Array<TaskLog>;
  //   taskLogExecId: string;


  // }

  // export class ExecutionLog {
  //   execItemCd: KnockoutObservable<string> = ko.observable('');
  //   companyId: KnockoutObservable<string> = ko.observable('');
  //   execItemName: KnockoutObservable<string> = ko.observable('');
  //   currentStatusCd: KnockoutObservable<number> = ko.observable(null);
  //   currentStatus: KnockoutObservable<string> = ko.observable('');
  //   overallStatusCd: KnockoutObservable<number> = ko.observable(null);
  //   overallStatus: KnockoutObservable<string> = ko.observable('');
  //   overallError: KnockoutObservable<number> = ko.observable(null);
  //   prevExecDateTime: KnockoutObservable<string> = ko.observable('');
  //   schCreateStart: KnockoutObservable<string> = ko.observable('');
  //   schCreateEnd: KnockoutObservable<string> = ko.observable('');
  //   dailyCreateStart: KnockoutObservable<string> = ko.observable('');
  //   dailyCreateEnd: KnockoutObservable<string> = ko.observable('');
  //   dailyCalcStart: KnockoutObservable<string> = ko.observable('');
  //   dailyCalcEnd: KnockoutObservable<string> = ko.observable('');
  //   execId: KnockoutObservable<string> = ko.observable('');
  //   prevExecDateTimeEx: KnockoutObservable<string> = ko.observable('');
  //   taskLogList: KnockoutObservableArray<TaskLog> = ko.observableArray([]);
  //   taskLogExecId: string;
  //   constructor(param: IExecutionLog) {
  //     let self = this;
  //     self.execItemCd(param.execItemCd || '');
  //     self.companyId(param.companyId || '');
  //     self.execItemName(param.execItemName || '');
  //     self.currentStatusCd(param.currentStatusCd);
  //     self.currentStatus(param.currentStatus || '');
  //     self.overallStatusCd(param.overallStatusCd);
  //     self.overallStatus(param.overallStatus || '');
  //     self.overallError(param.overallError);
  //     self.prevExecDateTime(param.prevExecDateTime || '');
  //     self.schCreateStart(param.schCreateStart || '');
  //     self.schCreateEnd(param.schCreateEnd || '');
  //     self.dailyCreateStart(param.dailyCreateStart || '');
  //     self.dailyCreateEnd(param.dailyCreateEnd || '');
  //     self.dailyCalcStart(param.dailyCalcStart || '');
  //     self.dailyCalcEnd(param.dailyCalcEnd || '');
  //     self.execId(param.execId || '');
  //     self.prevExecDateTimeEx(param.prevExecDateTimeEx || '');
  //     self.taskLogList(param.taskLogList || []);
  //     self.taskLogExecId = param.taskLogExecId || '';
  //   }
  // }

  // export interface EnumConstantDto {
  //   value: number;
  //   fieldName: string;
  //   localizedName: string;
  // }

  export class ExecutionItemInfomationModel {
    execItemCd: string;
    execItem: string;
    showWarningIcon: number;
    execStatus: number;
    nextExecDate: string;
    isTaskExecution: boolean;
    lastStartDateTime: string;
    lastEndDateTime: string;
    processingTime: string;
    overallStatus: number;
    systemError: string;
    businessError: string;

    constructor(vm: ComponentViewModel, dto: any) {
      // PK
      this.execItemCd = dto.execItemCd;
      // 警告アイコン
      if (dto.isOverAverageExecTime) {
        this.showWarningIcon = 0;
      } else {
        if (dto.isPastNextExecDate) {
          this.showWarningIcon = 1;
        }
      }

      if (dto.updateProcessAutoExec) {
        // 実行項目
        this.execItem = dto.execItemCd + ' ' + dto.updateProcessAutoExec.execItemName;
      }
      if (dto.updateProcessAutoExecManage) {
        // 実行状態
        this.execStatus = dto.updateProcessAutoExecManage.currentStatus;
        // 終了状態
        this.overallStatus = dto.updateProcessAutoExecManage.overallStatus;
        // 前回開始日時
        this.lastStartDateTime = dto.updateProcessAutoExecManage.lastExecDateTime
          ? moment.utc(dto.updateProcessAutoExecManage.lastExecDateTime).format('YYYY/MM/DD HH:mm:ss')
          : "";
        // 前回終了日時
        this.lastEndDateTime = dto.updateProcessAutoExecManage.lastEndExecDateTime
          ? moment.utc(dto.updateProcessAutoExecManage.lastEndExecDateTime).format('YYYY/MM/DD HH:mm:ss')
          : "";
        // 処理時間
        if (dto.updateProcessAutoExecManage.lastExecDateTime && dto.updateProcessAutoExecManage.lastEndExecDateTime) {
          const end = moment.utc(dto.updateProcessAutoExecManage.lastEndExecDateTime);
          const start = moment.utc(dto.updateProcessAutoExecManage.lastExecDateTime);
          this.processingTime = moment.utc(moment.duration(end.diff(start)).as('milliseconds')).format("HH:mm:ss");
        }
      }
      // タスク実行
      this.isTaskExecution = (dto.executionTaskSetting) ? dto.executionTaskSetting.enabledSetting : false;
      // 次回実行日時
      this.nextExecDate = dto.nextExecDate
        ? moment.utc(dto.nextExecDate).format('YYYY/MM/DD HH:mm:ss')
        : vm.$i18n('KBT002_165');
      // ｼｽﾃﾑｴﾗｰ
      this.systemError = (dto.updateProcessAutoExecManage && dto.updateProcessAutoExecManage.errorSystem)
        ? vm.$i18n('KBT002_61')
        : vm.$i18n('KBT002_62');
      // 業務エラー
      this.businessError = (dto.updateProcessAutoExecManage && dto.updateProcessAutoExecManage.errorBusiness)
        ? vm.$i18n('KBT002_61')
        : vm.$i18n('KBT002_62');
    }

  }

}