module nts.uk.com.view.cmf002.s {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            // interval 1000ms request to server
            interval: any;
            
            // time when start process
            timeStart: any;
            
            // S2_1_2
            timeOver: KnockoutObservable<string>;
             
            // S2_2_1
            status: KnockoutObservable<string>;
            proCnt: KnockoutObservable<number>;
            totalProCnt: KnockoutObservable<number>;
            proUnit: KnockoutObservable<string>;
            errCnt: KnockoutObservable<number>;
            
            opCond: number;
            
            // S3
            dataSaveSetName : string;
            dayStartValue : string;
            dayEndValue : string;
            
             // dialog mode
            dialogMode: KnockoutObservable<string>;
            
            // check file has been downloaded already
            isDownloaded: KnockoutObservable<boolean>;
            
            // received storeProcessingId from R
            storeProcessingId: string;
            
            constructor() {
                let self = this;
                
                self.timeStart = new Date();
                self.timeOver = ko.observable('00:00:00');
                self.dataSaveSetName = 'datasavesetname';
                self.storeProcessingId = '01';
                
                self.status = ko.observable('');
                self.proCnt = ko.observable(0);
                self.totalProCnt = ko.observable(0);
                self.proUnit = ko.observable('');
                self.errCnt = ko.observable(0);
                self.dialogMode = ko.observable("saving");
                self.isDownloaded = ko.observable(false);
                
                //date
                self.dayStartValue = '2018/01/03';
                self.dayEndValue = '2018/02/03';
//                 if(_.isNil(params.dayValue.startDate)) {
//                    self.dayStartValue = ""
//                } else {
//                    self.dayStartValue = moment.utc(params.dayValue.startDate, 'YYYY/MM/DD').format("YYYY/MM/DD");
//                }
//                
//                if(_.isNil(params.dayValue.endDate)) {
//                    self.dayEndValue = ""
//                } else {
//                    self.dayEndValue = moment.utc(params.dayValue.endDate, 'YYYY/MM/DD').format("YYYY/MM/DD");
//                }
                
            }
            //開始
            start(): JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                
                //データ保存監視処理: 
                self.interval = setInterval(self.confirmProcess, 1000, self);
                
                dfd.resolve();
                return dfd.promise();
            }
            
              public confirmProcess(self): void {
                  let storeProcessingId = self.storeProcessingId;
                  
                 // ドメインモデル「外部出力動作管理」
                   service.findExOutOpMng(storeProcessingId).done(function(res: any) {
                       //S1
                       let timeNow = new Date();
                       let over = (timeNow.getSeconds()+timeNow.getMinutes()*60+ timeNow.getHours()*60*60) - (self.timeStart.getSeconds()+self.timeStart.getMinutes()*60+ self.timeStart.getHours()*60*60);
                       let time = new Date(null);
                       time.setSeconds(over); // specify value for SECONDS here
                       let result = time.toISOString().substr(11, 8);
                       self.timeOver(result);
                       
                       //S3
                       self.status(self.getStatusEnum(res.opCond));
                       self.proCnt(res.proCnt);
                       self.totalProCnt(res.totalProCnt);
                       self.proUnit(res.proUnit);
                       self.errCnt(res.errCnt);
                       
                       self.opCond = res.opCond;
                       
                    // update mode when end: DONE, INTERRUPTION_END, ABNORMAL_TERMINATION
                    // 完了, 中断終了, 異常終了
                    if((res.opCond == 4) || (res.opCond == 5) || (res.opCond == 6)) {
                        // stop auto request to server
                        clearInterval(self.interval);
                        
                        // end: update dialog to complete mode
                        if(res.opCond == 4) {
                            self.dialogMode("done");
                            let fileId = null;
                        }
                        // end: update dialog to Error/Interrupt mode
                        if((res.opCond == 5) || (res.opCond == 6)) {
                            self.dialogMode("error_interrupt");
                        }
                        // delete dataStorageMng of process when end
                        let exOutOpMng = new ExOutOpMng(storeProcessingId, 0, 0, 0, 0, 0, 0);
                        service.deleteexOutOpMng(exOutOpMng).done(function(res: any) {
                            console.log("delete success");
                        }).fail(function(res: any) {
                            console.log("delete fails");
                        });
                    }
                }).fail(function(res: any) {
                    console.log("findexOutOpMng fail");
                });
              }
                
            //中断をする
              public interrupt(): void {
                  let self = this;
                  let exOutOpMng = new ExOutOpMng(self.storeProcessingId, self.proCnt(), self.errCnt(), self.totalProCnt(), 1, self.proUnit(), self.opCond);
                  nts.uk.ui.dialog.confirm({ messageId: "Msg_387" })
                      .ifYes(() => {
                          self.dialogMode("error_interrupt");
                          self.status(self.getStatusEnum(5));
                          // stop auto request to server
                          clearInterval(self.interval);

                          // delete dataStorageMng of process when interrupt
                          let exOutOpMng = new ExOutOpMng(self.storeProcessingId, 0, 0, 0, 0, '0', 0);
                          service.deleteexOutOpMng(exOutOpMng).done(function(res: any) {
                              console.log("delete success");
                          }).fail(function(res: any) {
                              console.log("delete fails");
                          });
                      })
                      .ifNo(() => {
                          return;
                      });
              }
            
              //ダウンロードをする
              public download(): void {
                  let self = this;

                  // confirm down load when click button
                  nts.uk.ui.dialog.confirm({ messageId: "Msg_388" })
                      .ifYes(() => {
                          service.getExterOutExecLog(self.storeProcessingId).done(function(res: any) {
                              let fileId = res.fileId;
                              nts.uk.request.specials.donwloadFile(fileId);
                              self.isDownloaded(true);
                          }).fail(function(res: any) {
                              console.log("Get fileId fail");
                          });
                      })
                      .ifNo(() => {
                          return;
                      });
              }
            public getStatusEnum(value: number): string {
                if (value && value === 0) {
                    return getText('CMF002_515');
                } if (value && value === 1) {
                    return getText('CMF002_516');
                } else if (value && value === 2) {
                    return getText('CMF002_517');
                } else if (value && value === 3) {
                    return getText('CMF002_518');
                } else if (value && value === 4) {
                    return getText('CMF002_519');
                } else if (value && value === 5) {
                    return getText('CMF002_520');
                } else if (value && value === 6) {
                    return getText('CMF002_521');
                }else if (value && value === 7) {
                    return getText('CMF002_522');
                }else if (value && value === 8) {
                    return getText('CMF002_523');
                }
            }
        }
        
        class ExOutOpMng{
            exOutProId: string;
            proCnt: number;
            errCnt: number;
            totalProCnt: number;
            doNotInterrupt: number;
            proUnit: string;
            opCond: number;
                constructor(exOutProId: string, proCnt: number,errCnt: number,totalProCnt: number,
                              doNotInterrupt: number,proUnit: string,opCond: number){
                    this.exOutProId = exOutProId;
                    this.proCnt = proCnt;
                    this.errCnt = errCnt;
                    this.totalProCnt = totalProCnt;
                    this.doNotInterrupt = doNotInterrupt;
                    this.proUnit = proUnit;
                    this.opCond = opCond;
                }
        }
    }
}