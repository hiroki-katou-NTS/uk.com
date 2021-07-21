/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kbt002.g {

  @bean()
  export class KBT002GViewModel extends ko.ViewModel {
    execLog: any = {};
    sharedObj = {};
    webApp = '';
    modalLink = '';
    execItemCd = '';
    taskLogExecId = '';
    overallError: KnockoutObservable<string> = ko.observable('');
    overallStatus: KnockoutObservable<string> = ko.observable('');
    taskLogList: KnockoutObservableArray<any> = ko.observableArray([]);
    eachProcessPeriod: EachProcessPeriod = new EachProcessPeriod();

    // Start page
    created(params: any) {
      let self = this;
      var sharedData = params;
      if (sharedData) {
        sharedData.taskLogList = _.sortBy(sharedData.taskLogList, ["taskId"]);
        self.taskLogExecId = sharedData.execId;
        self.execItemCd = sharedData.execItemCd;
        self.overallStatus(self.getStatus(sharedData.overallStatus));
        self.overallError(self.getError(sharedData.overallError));
        self.taskLogList(_.map(sharedData.taskLogList, (item: any) => {
          item.lastExecDateTime = item.lastExecDateTime ? moment.utc(item.lastExecDateTime).format("YYYY/MM/DD HH:mm:ss") : null;
          item.lastEndExecDateTime = item.lastEndExecDateTime ? moment.utc(item.lastEndExecDateTime).format("YYYY/MM/DD HH:mm:ss") : null;
          return item;
        }));
        self.eachProcessPeriod = new EachProcessPeriod({
          schCreateStart: sharedData.schCreateStart,
          schCreateEnd: sharedData.schCreateEnd,
          dailyCreateStart: sharedData.dailyCreateStart,
          dailyCreateEnd: sharedData.dailyCreateEnd,
          dailyCalcStart: sharedData.dailyCalcStart,
          dailyCalcEnd: sharedData.dailyCalcEnd,
          reflectApprovalResultStart: sharedData.reflectApprovalResultStart,
          reflectApprovalResultEnd: sharedData.reflectApprovalResultEnd
        });
      }
    }

    // 閉じる button
    closeDialog() {
      const vm = this;
      // 画面を閉じる
      vm.$window.close();
    }

    openDetailDialog(data: any, event: any) {
      let self = this;
      service.getLogHistory(self.execItemCd, self.taskLogExecId).done(logHistory => {
        if (logHistory) {
          var taskId = data.taskId;
          if (taskId in [2, 3, 4, 5]) {
            service.getDailyResultsLogParam(logHistory.execId).then(data => self.sharedObj = data)
              .then(() => {
                self.createLinkAndSharedObject(taskId, logHistory);
                self.$window.modal(self.webApp, self.modalLink, self.sharedObj);
              });
          } else {
            self.createLinkAndSharedObject(taskId, logHistory);
            self.$window.modal(self.webApp, self.modalLink, self.sharedObj);
          }
        }
      });

    }
    
    private getStatus(value: number): string {
      switch (value) {
        case 0: return "未実施";
        case 1: return "完了";
        case 2: return "強制終了";
        case 3: return "終了中";
        default: return "";
      }
    }

    private getError(value: number): string {
      switch (value) {
        case 0: return "更新処理の途中で終了時刻を超過したため中断しました。";
        case 1: return "前回の更新処理が完了していなかったため実行されませんでした。";
        case 2: return "画面から強制終了しました。";
        case 3: return "システム利用停止中のため実行されませんでした。";
        default: return "";
      }
    }

    private createLinkAndSharedObject(taskId: any, logHistory: any) {
      let self = this;
      // スケジュール作成のエラー内容を表示する
      switch (taskId) {
        case 0: // 外部受入
          self.sharedObj = {
            imexProcessId: logHistory.execId,
          };
          nts.uk.ui.windows.setShared('CMF001-R', self.sharedObj);
          //画面「CMF001_外部受入」の「R:エラー一覧ダイアログ」を起動する
          self.modalLink = "/view/cmf/001/r/index.xhtml";
          self.webApp = 'com';
          break;
        case 1: // スケジュールの作成
          self.sharedObj = {
            executionId: logHistory.execId
          };
          // Step アルゴリズム「個人スケジュール作成エラー内容表示処理」を実行する
          nts.uk.ui.windows.setShared('executionData', self.sharedObj);
          // 「個人スケジュールの作成」K画面を起動する
          self.modalLink = "/view/ksc/001/h/index.xhtml";
          self.webApp = 'at';
          break;
        case 2: // 日別作成
          nts.uk.ui.windows.setShared("openI", self.sharedObj);
          self.modalLink = "/view/kdw/001/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 3: // 日別計算
          nts.uk.ui.windows.setShared("openI", self.sharedObj);
          self.modalLink = "/view/kdw/001/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 4: // 承認結果反映
          nts.uk.ui.windows.setShared("openI", self.sharedObj);
          self.modalLink = "/view/kdw/001/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 5: // 月別集計
          nts.uk.ui.windows.setShared("openI", self.sharedObj);
          self.modalLink = "/view/kdw/001/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 6: // 任意期間の集計
          // 任意期間の集計のエラー内容を表示する
          self.sharedObj = {
            logId: logHistory.execId, //・任意期間集計実行ログID
          };
          nts.uk.ui.windows.setShared('Kfp001gParams', self.sharedObj);
          //画面「KFP001_任意期間の集計」の「G:エラー内容一覧」を起動する
          self.modalLink = "/view/kfp/001/g/index.xhtml";
          self.webApp = 'at';
          break;
        case 7: // 外部出力
          // 外部出力のエラー内容を表示する
          self.sharedObj = logHistory.execId; // 外部出力処理ID
          nts.uk.ui.windows.setShared('CMF002_Y_PROCESINGID', self.sharedObj);
          //画面「CMF002_外部出力」の「Y:エラーログダイアログ」を起動する
          self.modalLink = "/view/cmf/002/y/index.xhtml";
          self.webApp = 'com';
          break;
        case 8: // アラーム抽出
          break;
        case 9: // 承認ルート更新（日次）
          self.sharedObj = {
            executionId: logHistory.execId,
            execItemCd: self.execItemCd,
            isDaily: true,
            nameObj: "承認ルート更新（日次）"
          };
          self.modalLink = "/view/kbt/002/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 10: // 承認ルート更新（月次）
          self.sharedObj = {
            executionId: logHistory.execId,
            execItemCd: self.execItemCd,
            isDaily: false,
            nameObj: "承認ルート更新（月次）"
          };
          self.modalLink = "/view/kbt/002/i/index.xhtml";
          self.webApp = 'at';
          break;
        case 11: // データの保存
          break;
        case 12: // データの削除
          break;
        case 13: // インデックス再構成
          self.sharedObj = {
            executionId: logHistory.execId,
          };
          self.modalLink = '/view/kbt/002/l/index.xhtml';
          self.webApp = 'at';
          break;
        default:
          break;
      }
    }
  }

  export class EachProcessPeriod {
    schCreateStart: moment.Moment;
    schCreateEnd: moment.Moment;
    dailyCreateStart: moment.Moment;
    dailyCreateEnd: moment.Moment;
    dailyCalcStart: moment.Moment;
    dailyCalcEnd: moment.Moment;
    reflectApprovalResultStart: moment.Moment;
    reflectApprovalResultEnd: moment.Moment;
    constructor(init?: Partial<EachProcessPeriod>) {
      $.extend(this, init);
    }
  }
}
