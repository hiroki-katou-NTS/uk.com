/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kbt002.g {

  @bean()
  export class KBT002GViewModel extends ko.ViewModel {
    execLog: any = {};
    sharedObj = {};
    modalLink = '';
    execItemCd = '';
    taskLogExecId = '';
    overallError = '';
    overallStatus = '';
    taskLogList: any = {};

    // Start page
    created(params: any) {
      let self = this;
      var sharedData = params;
      sharedData.taskLogList = _.sortBy(sharedData.taskLogList, [function (o: any) { return o.taskId; }]);
      if (sharedData) {
        self.taskLogExecId = sharedData.execId;
        self.execItemCd = sharedData.execItemCd;
        self.overallStatus = sharedData.overallStatus;
        self.overallError = sharedData.overallError;
        self.taskLogList = sharedData.taskLogList;
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
      service.getLogHistory(self.execItemCd, self.taskLogExecId).done(function (logHistory) {
        var taskId = data.taskId;
        self.createLinkAndSharedObject(taskId, logHistory);

        self.$window.modal(self.modalLink, self.sharedObj);
      });

    }

    private createLinkAndSharedObject(taskId: any, logHistory: any) {
      let self = this;
      // スケジュール作成のエラー内容を表示する
      switch (taskId) {
        case 0: // 外部受入
          self.sharedObj = {
            executionId: logHistory.execId,
            isDaily: false,
            nameObj: "外部受入"
          };
          nts.uk.ui.windows.setShared('inputDialogR', { sharedObj: self.sharedObj });
          //画面「CMF001_外部受入」の「R:エラー一覧ダイアログ」を起動する
          self.modalLink = "/view/cmf/001/r/index.xhtml";
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
          self.sharedObj = {
            code: logHistory.execId, //・任意期間集計実行ログID
            execItemCd: self.execItemCd,
            isDaily: false,
            name: "任意期間の集計",
            start: logHistory.schCreateStart,
            end: logHistory.schCreateEnd
          };
          nts.uk.ui.windows.setShared('Kfp001gParams', { sharedObj: self.sharedObj });
          //画面「KFP001_任意期間の集計」の「G:エラー内容一覧」を起動する
          self.modalLink = "/view/kfp/001/g/index.xhtml";
          break;
        case 7: // 外部出力
          // 外部出力のエラー内容を表示する
          self.sharedObj = {
            executionId: logHistory.execId,
            isDaily: false,
            nameObj: "外部出力"
          };
          nts.uk.ui.windows.setShared('inputDialogY', { sharedObj: self.sharedObj });
          //画面「CMF002_外部出力」の「Y:エラーログダイアログ」を起動する
          self.modalLink = "/view/cmf/002/y/index.xhtml";
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
          nts.uk.ui.windows.setShared('inputDialogI', { sharedObj: self.sharedObj });
          self.modalLink = "/view/kbt/002/i/index.xhtml";
          break;
        case 10: // 承認ルート更新（月次）
          self.sharedObj = {
            executionId: logHistory.execId,
            execItemCd: self.execItemCd,
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
          const params = {
            executionId: logHistory.execId,
            isDaily: false,
            nameObj: "インデックス再構成"
          };
          nts.uk.ui.windows.setShared('inputDialogL', { sharedObj: params });
          self.$window.modal('/view/kbt/002/l/index.xhtml');
          break;
        default:
          break;
      }
    }
  }
}