module nts.uk.at.view.kbt002.g {
  export module viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
      execLog: any = {};
      sharedObj = {};
      modalLink = '';
      execItemCd = '';

      constructor() {
        let self = this;
      }

      // Start page
      start() {
        let self = this;
        var dfd = $.Deferred();
        var sharedData = nts.uk.ui.windows.getShared('inputDialogG');
        sharedData.execLog.taskLogList = _.sortBy(sharedData.execLog.taskLogList, [function (o) { return o.taskId; }]);
        if (sharedData) {
          self.execLog = sharedData.execLog;

        }

        dfd.resolve();
        return dfd.promise();
      }

      // 閉じる button
      closeDialog() {
        windows.close();
      }

      openDetailDialog(data, event) {
        let self = this;
        block.grayout();
        service.getLogHistory(self.execLog.execItemCd, self.execLog.taskLogExecId).done(function (logHistory) {
          var taskId = data.taskId;
          self.createLinkAndSharedObject(taskId, logHistory);

          setShared('inputDialogG', self.sharedObj);
          modal(self.modalLink).onClosed(function () {
            block.clear();
          });
        });

      }

      private createLinkAndSharedObject(taskId, logHistory) {
        let self = this;
        // スケジュール作成のエラー内容を表示する
        switch (taskId) {
          case 0: // 外部受入
            break;
          case 1: // スケジュールの作成
            self.sharedObj = {
              executionId: logHistory.execId,
              startDate: logHistory.schCreateStart,
              endDate: logHistory.schCreateEnd
            };
            // Step アルゴリズム「個人スケジュール作成エラー内容表示処理」を実行する
            nts.uk.ui.windows.setShared('dataFromDetailDialog', self.sharedObj);
            // 「個人スケジュールの作成」K画面を起動する
            self.modalLink = "/view/ksc/001/k/index.xhtml";
            break;
          case 2: // 日別作成
            self.sharedObj = {
              empCalAndSumExecLogID: logHistory.execId, //・就業計算と集計実行ログID
              executionContentName: "日別作成",
              executionContent: 0,  // 日別作成
              listTargetPerson: [], //・社員ID（list）  ・従業員の実行状況
              executionStartTime: logHistory.lastExecDateTime, //・実行開始日時
              objectPeriod: { startDate: null, endDate: null }, //・対象期間
              nameClosue: null, //・選択した締め
              processingMonth: null, //・処理月
              height: 550
            };
            nts.uk.ui.windows.setShared("openH", self.sharedObj);
            self.modalLink = "/view/kdw/001/h/index.xhtml";
            break;
          case 3: // 日別計算
            self.sharedObj = {
              empCalAndSumExecLogID: logHistory.execId, //・就業計算と集計実行ログID
              executionContentName: "日別計算",
              executionContent: 1,  // 日別計算
              listTargetPerson: [], //・社員ID（list）  ・従業員の実行状況
              executionStartTime: logHistory.lastExecDateTime, //・実行開始日時
              objectPeriod: { startDate: null, endDate: null }, //・対象期間
              nameClosue: null, //・選択した締め
              processingMonth: null, //・処理月
              height: 550
            };
            nts.uk.ui.windows.setShared("openH", self.sharedObj);
            self.modalLink = "/view/kdw/001/h/index.xhtml";
            break;
          case 4: // 承認結果反映
            // 承認結果反映のエラー内容を表示する
            self.sharedObj = {
              empCalAndSumExecLogID: logHistory.execId, //・就業計算と集計実行ログID
              executionContentName: "承認結果反映", //・実施内容
              executionContent: 2, //ExecutionContent = 承認結果反映 
              listTargetPerson: [], //・社員ID（list）  ・従業員の実行状況 ・対象者の人数
              executionStartTime: logHistory.lastExecDateTime, //・実行開始日時
              objectPeriod: { startDate: null, endDate: null }, //・対象期間
              nameClosue: null, //・選択した締め
              processingMonth: null, //・処理月
              height: 550
            };
            // 「就業計算と集計」H画面を起動する
            nts.uk.ui.windows.setShared("openH", self.sharedObj);
            self.modalLink = "/view/kdw/001/h/index.xhtml";
            break;
          case 5: // 月別集計
            self.sharedObj = {
              empCalAndSumExecLogID: logHistory.execId, //・就業計算と集計実行ログID
              executionContentName: "月別集計",
              executionContent: 3,  // 月別集計
              listTargetPerson: [], //・社員ID（list）  ・従業員の実行状況
              executionStartTime: logHistory.lastExecDateTime, //・実行開始日時
              objectPeriod: { startDate: null, endDate: null }, //・対象期間
              nameClosue: null, //・選択した締め
              processingMonth: null, //・処理月
              height: 550
            };
            nts.uk.ui.windows.setShared("openH", self.sharedObj);
            self.modalLink = "/view/kdw/001/h/index.xhtml";
            break;
          case 6: // 任意期間の集計
            // 任意期間の集計のエラー内容を表示する
            //画面「KFP001_任意期間の集計」の「G:エラー内容一覧」を起動する
            self.sharedObj = {
              arbitraryPeriodAggrExecId: logHistory.execId, //・任意期間集計実行ログID
              execItemCd: self.execLog.execItemCd,
              isDaily: false,
              nameObj: "任意期間の集計"
            };
            nts.uk.ui.windows.setShared('inputDialogG', { sharedObj: self.sharedObj });
            self.modalLink = "/view/kfp/001/g/index.xhtml";  
            break;
          case 7: // 外部出力
            // 外部出力のエラー内容を表示する
            //画面「CMF002_外部出力」の「Y:エラーログダイアログ」を起動する
            break;
          case 8: // アラーム抽出
            break;
          case 9: // 承認ルート更新（日次）
            self.sharedObj = {
              executionId: logHistory.execId,
              execItemCd: self.execLog.execItemCd,
              isDaily: true,
              nameObj: "承認ルート更新（日次）"
            };
            nts.uk.ui.windows.setShared('inputDialogI', { sharedObj: self.sharedObj });
            self.modalLink = "/view/kbt/002/i/index.xhtml";
            break;
          case 10: // 承認ルート更新（月次）
            self.sharedObj = {
              executionId: logHistory.execId,
              execItemCd: self.execLog.execItemCd,
              isDaily: false,
              nameObj: "承認ルート更新（月次）"
            };
            nts.uk.ui.windows.setShared('inputDialogI', { sharedObj: self.sharedObj });
            self.modalLink = "/view/kbt/002/i/index.xhtml";
            break;
          case 11: // データの保存
            break;
          case 12: // データの削除
            break;
          case 13: // インデックス再構成
            break;
          default:
            break;
        }
      }
    }
  }
}