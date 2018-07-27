module nts.uk.at.view.ktg031.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        listToppage: KnockoutObservableArray<TopPageAlarmDto>;
        period: KnockoutObservable<number> = ko.observable(3);
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KTG031_1") },
                { code: '0', name: nts.uk.resource.getText("KTG031_2") }
            ]);
            self.selectedRuleCode = ko.observable(0);
            self.listToppage = ko.observableArray([]);
            // subscribe switch button (rogerFlag)            
            self.selectedRuleCode.subscribe((value) => {
                let temp = self.period();
                if (value == 0) {
                    service.getToppage(self.selectedRuleCode(), temp).done((listData) => {
                        let listOrder = _.orderBy(listData, ["finishDateTime"], ['desc']);     
                        self.listToppage(_.map(listOrder, acc => {
                            let afterConvert = self.convertTime(acc.finishDateTime);
                            acc.finishDateTime = afterConvert;
                            return new TopPageAlarmDto(acc);
                        }));
                        self.period(temp);
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {  
                        block.clear();
                    });
                } else {
                    service.getAllToppage(temp).done((data) => {
                        let listOrder = _.orderBy(data, ["finishDateTime"], ['desc']);
                        self.listToppage(_.map(listOrder, acc => {
                            let afterConvert = self.convertTime(acc.finishDateTime);
                            acc.finishDateTime = afterConvert;
                            let a = new TopPageAlarmDto(acc);
                            if (acc.rogerFlag == 1) {
                                a.hidden(false);
                            }
                            return a;
                        }));
                        self.period(temp);
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
            // subscribe month
            self.period.subscribe((obj) => {
                let temp = self.period();
                self.listToppage([]);
                if (self.selectedRuleCode() == 0) {
                    service.getToppage(self.selectedRuleCode(), temp).done((listData) => {
                        let listOrder = _.orderBy(listData, ["finishDateTime"], ['desc']); 
                        self.listToppage(_.map(listOrder, acc => {
                            let afterConvert = self.convertTime(acc.finishDateTime);
                            acc.finishDateTime = afterConvert;
                            return new TopPageAlarmDto(acc);
                        }));
                    });
                }else{
                    service.getAllToppage(temp).done((data) => {
                        let listOrder = _.orderBy(data, ["finishDateTime"], ['desc']);
                        self.listToppage(_.map(listOrder, acc => {
                            let afterConvert = self.convertTime(acc.finishDateTime);
                            acc.finishDateTime = afterConvert;
                            let a = new TopPageAlarmDto(acc);
                            if (acc.rogerFlag == 1) {
                                a.hidden(false);
                            }
                            return a;
                        }));
                        self.period(temp);
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            block.grayout();
            var dfd = $.Deferred();
            // get toppage with roger = 0
            service.getToppage(self.selectedRuleCode(), self.period()).done((listData: Array<ITopPageAlarmDto>) => {
                let listOrder = _.orderBy(listData, ["finishDateTime"], ['desc']);
                self.listToppage(_.map(listOrder, acc => {
                    let afterConvert = self.convertTime(acc.finishDateTime);
                    acc.finishDateTime = afterConvert;
                    return new TopPageAlarmDto(acc);
                }));
                
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        //convert time follow the format
        convertTime(time: string): string {
            let self = this;
            // get and format time at the moment
            let now = moment(new Date()).utcOffset(0).format('YYYY-MM-DD');
            // format time in DB
            let data = moment(time).utcOffset(0).format('YYYY-MM-DD');
            if (now == data) {
                return moment(time).utcOffset(0).format('HH:mm'); 
            } else {
                return moment(time).utcOffset(0).format('MM/DD HH:mm');
            }
        }

        // click open dialog 詳細ボタン
        openDialog(index: number) {
            let self = this;
            let data = {
                executionLogId: self.listToppage()[index].executionLogId,
                processingName: self.listToppage()[index].processingName
            }
            parent.nts.uk.ui.windows.setShared('ktg031A', data);
            parent.nts.uk.ui.windows.sub.modal("/view/ktg/031/b/index.xhtml");
        }
        // click update 了解ボタン
        updateRoger(index: number) {
            let self = this;
            block.grayout;
            let cmd = {
                executionLogId: self.listToppage()[index].executionLogId,
                rogerFlag: 1
            }
            if (self.selectedRuleCode() == 0) {
                service.update(cmd).done(function() {
                    self.startPage();
                }).always(() => {
                    block.clear();
                });
            } else {
                self.listToppage()[index].hidden(false);
                service.update(cmd).done(function() {
                }).always(() => {
                    block.clear();
                });
            }
        }

        changeTime(period: number) {
            let self = this;
            console.log(period);
            self.period(period);
        }
    }

    export interface ITopPageAlarmDto {
        /** 実行ログID */
        executionLogId: string;
        /** 実行完了日時 */
        finishDateTime: string;
        /** 実行内容 AlarmCategory */
        executionContent: number;
        /** エラーの有無 */
        existenceError: boolean;
        /** 了解フラグ */
        rogerFlag: boolean;
        /** 処理名 */
        processingName: string;
        /** 処理結果 */
        processingResult: string;
    }

    export class TopPageAlarmDto {
        /** 実行ログID */
        executionLogId: string;
        /** 実行完了日時 */
        finishDateTime: string;
        /** 実行内容 AlarmCategory */
        executionContent: number;
        /** エラーの有無 */
        existenceError: KnockoutObservable<boolean>;
        /** 了解フラグ */
        rogerFlag: boolean;
        /** 処理名 */
        processingName: string;
        /** 処理結果 */
        processingResult: string;
        /** hide button */
        hidden: KnockoutObservable<boolean>;
        constructor(param: ITopPageAlarmDto) {
            let self = this;
            self.executionLogId = param.executionLogId;
            self.finishDateTime = param.finishDateTime;
            self.executionContent = param.executionContent;
            self.existenceError = ko.observable(param.existenceError);
            self.rogerFlag = param.rogerFlag;
            self.processingName = param.processingName;
            self.processingResult = param.processingResult;
            self.hidden = ko.observable(true);
        }
    }

}
