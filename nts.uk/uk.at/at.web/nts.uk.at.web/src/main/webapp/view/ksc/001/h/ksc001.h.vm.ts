module nts.uk.at.view.ksc001.h {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            exeDetailModel: KnockoutObservable<ExecutionDetailModel>;
            
            constructor() {
                var self = this;
                self.exeDetailModel = ko.observable(null);
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let executionId = nts.uk.ui.windows.getShared("executionId");
                self.loadDetailData(executionId).done(function() {
                    dfd.resolve();
                });
                $("#fixed-table").ntsFixedTable();
                return dfd.promise();
            }
            /**
             * Load excution detail data
             */
            private loadDetailData(executionId: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findExecutionDetail(executionId).done(function(data: any) {
                    if (data) {
                        self.exeDetailModel(new ExecutionDetailModel(data,self.detailContentString(data)));
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            /**
             * 
             */
            openDialogI(): void {
                let self = this;
                //TODO
                var data: any = {
                    executionId: '',
                    strD: '',
                    endD: ''    
                }
                blockUI.invisible();
                nts.uk.ui.windows.setShared('dataFromDetailDialog', data);
                nts.uk.ui.windows.sub.modal("/view/ksc/001/i/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
            /**
             * 
             */
            openDialogK(): void {
                let self = this;
                blockUI.invisible();
                // the default value of categorySet = undefined
                //nts.uk.ui.windows.setShared('', );
                nts.uk.ui.windows.sub.modal("/view/ksc/001/k/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
             /**
             * close dialog 
             */
            closeDialog(): void {
                let self = this;
                nts.uk.ui.windows.close();   
            }
            
            private detailContentString(data:any):string[]{
                let self = this;
                let str: string[] = [];
                let spaceString = "　";
                //==
                if (data.implementAtr == ImplementAtr.GENERALLY_CREATED) {
                    str.push(nts.uk.resource.getText("KSC001_35"));
                } else {
                    str.push(nts.uk.resource.getText("KSC001_36"));
                }
                //==
                if (data.reCreateAtr == ReCreateAtr.ALLCASE) {
                    str.push(nts.uk.resource.getText("KSC001_37") + nts.uk.resource.getText("KSC001_4"));
                } else {
                    str.push(nts.uk.resource.getText("KSC001_37") + nts.uk.resource.getText("KSC001_5"));
                }
                //==
                if (data.processExecutionAtr == ProcessExecutionAtr.REBUILD) {
                    str.push(nts.uk.resource.getText("KSC001_37") + nts.uk.resource.getText("KSC001_7"));
                } else {
                    str.push(nts.uk.resource.getText("KSC001_37") + nts.uk.resource.getText("KSC001_8"));
                }
                //==
                if (data.resetWorkingHours) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_15"));
                }
                //==
                if (data.resetDirectLineBounce) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_11"));
                }
                //==
                if (data.resetMasterInfo) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_12"));
                }
                //==
                if (data.resetTimeChildCare) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_13"));
                }
                //==
                if (data.resetAbsentHolidayBusines) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_14"));
                }
                //==
                if (data.resetTimeAssignment) {
                    str.push(spaceString+nts.uk.resource.getText("KSC001_38")+nts.uk.resource.getText("KSC001_16"));
                }
                if (data.confirm) {
                    str.push(nts.uk.resource.getText("KSC001_17"));
                }
                //return
                return str;
            } 

        }
        
        export class ExecutionDetailModel {
            a:KnockoutObservable<string>;
            b:KnockoutObservable<string>;
            targetRange: KnockoutObservable<string>;
            completionStatus: KnockoutObservable<string>;
            executionId: KnockoutObservable<string>;
            executionContent: KnockoutObservableArray<string>;
            executionDateTime: KnockoutObservable<string>;
            executionEmployeeId: KnockoutObservable<string>;
            period: KnockoutObservable<Period>;
            executionNumber: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;
            constructor(data:any,detailString :string[]){
                var self = this;
                self.a = ko.observable('2016/11/11');
                self.b = ko.observable('2016/11/11');
                self.targetRange = ko.observable(nts.uk.resource.getText("KSC001_46", [self.a(), self.b()]));
                self.completionStatus = ko.observable('1');
                self.executionId = ko.observable('1');
                self.executionContent = ko.observableArray(detailString);
                self.executionEmployeeId = ko.observable('1');
                self.period = ko.observable(new Period());
                //get diff time execution
                let diffTime = moment.utc(moment(self.period().endDateTime, "YYYY/MM/DD HH:mm:ss").diff(moment(self.period().startDateTime, "YYYY/MM/DD HH:mm:ss"))).format("HH:mm:ss");
                self.executionDateTime = ko.observable(diffTime);
                self.executionNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [33]));
                self.errorNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [2]));
            }
        }
        export class Period{
            startDateTime: string;
            endDateTime: string;
            constructor() {
                this.startDateTime = "2016/11/30 12:19:10";
                this.endDateTime = "2016/11/30 12:29:50";
            }
        }
        
        // 実施区分
        export enum ImplementAtr {
            // 通常作成
            GENERALLY_CREATED = 0,
            // 再作成
            RECREATE = 1
        }

        // 再作成区分
        export enum ReCreateAtr {
            // 全件
            ALLCASE = 0,
            // 未確定データのみ
            ONLYUNCONFIRM = 1
        }

        // 作成方法区分
        export enum CreateMethodAtr {
            // 個人情報
            PERSONAL_INFO = 0,
            // パターンスケジュール
            PATTERN_SCHEDULE = 1,
            // 過去スケジュールコピー
            COPY_PAST_SCHEDULE = 2
        }

        // 処理実行区分
        export enum ProcessExecutionAtr {
            // もう一度作り直す 
            REBUILD = 0,
            // 再設定する
            RECONFIG = 1
        }

        // 利用区分
        export enum UseAtr {
            // 使用しない
            NOTUSE = 0,
            // 使用する
            USE = 1
        }
    }
}