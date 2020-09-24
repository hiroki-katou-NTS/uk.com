module nts.uk.com.view.cmf004.k {

  import getShared = nts.uk.ui.windows.getShared;
  import getText = nts.uk.resource.getText;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    // interval 1000ms request to server
    interval: any;

    // received storeProcessingId from H
    storeProcessingId: string;

    // time when start process
    timeStart: any;

    // K1_6
    timeOver: KnockoutObservable<string>;

    // K1_7
    status: KnockoutObservable<string>;
    categoryCount: KnockoutObservable<number>;
    categoryTotalCount: KnockoutObservable<number>;
    errorCount: KnockoutObservable<number>;

    operatingCondition: number;

    // K2_2
    dataSaveSetName: string;
    dayStartValue: string;
    dayEndValue: string;
    monthStartValue: string;
    monthEndValue: string;
    yearStartValue: string;
    yearEndValue: string;

    // dialog mode
    dialogMode: KnockoutObservable<string>;

    // check file has been downloaded already
    isDownloaded: KnockoutObservable<boolean>;

    errorText: string = nts.uk.text.format(nts.uk.resource.getText("CMF003_190"), 0);

    mounted() {
      const vm = this;
      const params = nts.uk.ui.windows.getShared("CMF004KParams");
      vm.timeStart = new Date();
      vm.timeOver = ko.observable('00:00:00');
      vm.storeProcessingId = params.storeProcessingId;
      vm.dataSaveSetName = params.dataSaveSetName;

      if (!params.dayValue.startDate) {
        vm.dayStartValue = ""
      } else {
        vm.dayStartValue = moment.utc(params.dayValue.startDate, 'YYYY/MM/DD').format("YYYY/MM/DD");
      }

      if (!params.dayValue.endDate) {
        vm.dayEndValue = ""
      } else {
        vm.dayEndValue = moment.utc(params.dayValue.endDate, 'YYYY/MM/DD').format("YYYY/MM/DD");
      }

      if (!params.monthValue.startDate) {
        vm.monthStartValue = "";
      } else {
        vm.monthStartValue = moment.utc(params.monthValue.startDate, 'YYYY/MM/DD').format("YYYY/MM");
      }

      if (!params.monthValue.endDate) {
        vm.monthEndValue = "";
      } else {
        vm.monthEndValue = moment.utc(params.monthValue.endDate, 'YYYY/MM/DD').format("YYYY/MM");
      }

      if (!params.yearValue.startDate) {
        vm.yearStartValue = "";
      } else {
        vm.yearStartValue = moment.utc(params.yearValue.startDate, 'YYYY/MM/DD').format("YYYY");
      }

      if (!params.yearValue.endDate) {
        vm.yearEndValue = "";
      } else {
        vm.yearEndValue = moment.utc(params.yearValue.endDate, 'YYYY/MM/DD').format("YYYY");
      }

      // init K1_7
      vm.status = ko.observable('');
      vm.categoryCount = ko.observable(0);
      vm.categoryTotalCount = ko.observable(0);
      vm.errorCount = ko.observable(0);
      vm.dialogMode = ko.observable("saving");
      vm.isDownloaded = ko.observable(false);
    }

    //開始
    start(): JQueryPromise<any> {
      let vm = this,
        dfd = $.Deferred();

      //データ保存監視処理: 
      vm.interval = setInterval(vm.confirmProcess, 1000, vm);

      dfd.resolve();
      return dfd.promise();
    }

    /**
    * confirm process after 1s
    */
    public confirmProcess(vm): void {
      let storeProcessingId = vm.storeProcessingId;

      service.findDataStorageMng(storeProcessingId).then(function (res: any) {
        let storageMng = res;
        // K1_6 set time over 
        let timeNow = new Date();
        let over = (timeNow.getSeconds() + timeNow.getMinutes() * 60 + timeNow.getHours() * 60 * 60) - (vm.timeStart.getSeconds() + vm.timeStart.getMinutes() * 60 + vm.timeStart.getHours() * 60 * 60);
        let time = new Date(null);
        time.setSeconds(over); // specify value for SECONDS here
        let result = time.toISOString().substr(11, 8);
        vm.timeOver(result);

        // K1_7
        vm.status(vm.getStatusEnum(storageMng.operatingCondition));
        vm.categoryCount(storageMng.categoryCount);
        vm.categoryTotalCount(storageMng.categoryTotalCount);
        vm.errorCount(storageMng.errorCount);

        vm.operatingCondition = storageMng.operatingCondition;

        // update mode when end: DONE, INTERRUPTION_END, ABNORMAL_TERMINATION
        // 完了, 中断終了, 異常終了
        if ((storageMng.operatingCondition == 4) || (storageMng.operatingCondition == 5) || (storageMng.operatingCondition == 6)) {
          // stop auto request to server
          clearInterval(vm.interval);

          // end: update dialog to complete mode
          if (storageMng.operatingCondition == 4) {
            vm.dialogMode("done");
            let fileId = null;
            service.findResultOfSaving(storeProcessingId).then(function (res: any) {
              fileId = res.fileId;
              service.updateFileSize(storeProcessingId, fileId).then(function (data: any) {
              });
            }).fail(function (res: any) {
              console.log("Get fileId fail");
            });
            // confirm down load when done
            nts.uk.ui.dialog.confirm({ messageId: "Msg_334" })
              .ifYes(() => {
                if (fileId) {
                  nts.uk.request.specials.donwloadFile(fileId);
                  vm.isDownloaded(true);
                  $('#K3_3').focus();
                }
              })
              .ifNo(() => {
                $('#K3_3').focus();
                return;
              });
          }

          // end: update dialog to Error/Interrupt mode
          if ((storageMng.operatingCondition == 5) || (storageMng.operatingCondition == 6)) {
            vm.dialogMode("error_interrupt");
            $('#K3_3').focus();
          }

          // delete dataStorageMng of process when end
          let dataStorageMng = new DataStorageMng(storeProcessingId, 0, 0, 0, 0, 0);
          service.deleteDataStorageMng(dataStorageMng).then(function (res: any) {
            console.log("delete success");
          }).fail(function (res: any) {
            console.log("delete fails");
          });
        }
      }).fail(function (res: any) {
        console.log("findDataStorageMng fail");
      });
    }

    // interrupt process when click button
    public interrupt(): void {
      let vm = this;
      let dataStorageMng = new DataStorageMng(vm.storeProcessingId, 1, vm.categoryCount(), vm.categoryTotalCount(), vm.errorCount(), vm.operatingCondition);

      nts.uk.ui.dialog.confirm({ messageId: "Msg_387" })
        .ifYes(() => {
          vm.dialogMode("error_interrupt");
          vm.status(vm.getStatusEnum(5));
          $('#K3_3').focus();

          // stop auto request to server
          clearInterval(vm.interval);

          // delete dataStorageMng of process when interrupt
          let dataStorageMng = new DataStorageMng(vm.storeProcessingId, 0, 0, 0, 0, 0);
          service.deleteDataStorageMng(dataStorageMng).then(function (res: any) {
            console.log("delete success");
          }).fail(function (res: any) {
            console.log("delete fails");
          });
        })
        .ifNo(() => {
          return;
        });
    }

    public download(): void {
      let vm = this;

      // confirm down load when click button
      nts.uk.ui.dialog.confirm({ messageId: "Msg_388" })
        .ifYes(() => {
          service.findResultOfSaving(vm.storeProcessingId).then(function (res: any) {
            let fileId = res.fileId;
            nts.uk.request.specials.donwloadFile(fileId);
            vm.isDownloaded(true);
          }).fail(function (res: any) {
            console.log("Get fileId fail");
          });
        })
        .ifNo(() => {
          return;
        });
    }

    // close popup
    public close(): void {
      nts.uk.ui.windows.close();
      $("#H1_1").focus();
    }

    public getStatusEnum(value): string {
      if (value && value === 0) {
        return getText('Enum_OperatingCondition_INPREPARATION');
      } else if (value && value === 1) {
        return getText('Enum_OperatingCondition_SAVING');
      } else if (value && value === 2) {
        return getText('Enum_OperatingCondition_INPROGRESS');
      } else if (value && value === 3) {
        return getText('Enum_OperatingCondition_DELETING');
      } else if (value && value === 4) {
        return getText('Enum_OperatingCondition_DONE');
      } else if (value && value === 5) {
        return getText('Enum_OperatingCondition_INTERRUPTION_END');
      } else if (value && value === 6) {
        return getText('Enum_OperatingCondition_ABNORMAL_TERMINATION');
      }
    }
  }

  class DataStorageMng {
    storeProcessingId: string;
    doNotInterrupt: number;
    categoryCount: number;
    categoryTotalCount: number;
    errorCount: number;
    operatingCondition: number;

    constructor(storeProcessingId: string, doNotInterrupt: number, categoryCount: number,
      categoryTotalCount: number, errorCount: number, operatingCondition: number) {
      this.storeProcessingId = storeProcessingId;
      this.doNotInterrupt = doNotInterrupt;
      this.categoryCount = categoryCount;
      this.categoryTotalCount = categoryTotalCount;
      this.errorCount = errorCount;
      this.operatingCondition = operatingCondition;
    }
  }
}