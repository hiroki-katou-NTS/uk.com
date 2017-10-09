module nts.uk.at.view.ksc001.h {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            executionId: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            targetRange: KnockoutObservable<string>;
            detailContentMethod: KnockoutObservableArray<string>;
            executionContent: KnockoutObservableArray<string>;
            executionDateTime: KnockoutObservable<string>;
            exeStart: KnockoutObservable<string>;
            exeEnd: KnockoutObservable<string>;
            executionNumber: KnockoutObservable<string>;
            errorNumber: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.executionId = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.targetRange = ko.observable('');
                self.detailContentMethod = ko.observableArray([]);
                self.executionContent = ko.observableArray([]);
                self.exeStart = ko.observable('');
                self.exeEnd = ko.observable('');
                self.executionDateTime = ko.observable('');
                self.executionNumber = ko.observable('');
                self.errorNumber = ko.observable('');
            }

            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                blockUI.invisible();
                self.executionId(nts.uk.ui.windows.getShared("executionData").executionId);
                self.loadDetailData(self.executionId()).done(function() {
                    dfd.resolve();
                    blockUI.clear();
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
                        //define format datetime string
                        let dateTimeFormat = "YYYY/MM/DD HH:mm:ss";
                        let timeFormat = "HH:mm:ss";
                        self.startDate(data.startDate);
                        self.endDate(data.endDate);
                        self.targetRange = ko.observable(nts.uk.resource.getText("KSC001_46", [data.startDate, data.endDate]));
                        self.detailContentMethod = ko.observableArray(self.detailCreateMethod(data));
                        self.executionContent = ko.observableArray(self.detailContentString(data));
                        self.exeStart = ko.observable(moment(data.executionStart).format(dateTimeFormat));
                        self.exeEnd = ko.observable(moment(data.executionEnd).format(dateTimeFormat));
                        //get diff time execution
                        let diffTime = moment.utc(moment(data.executionEnd, dateTimeFormat).diff(moment(data.executionStart, dateTimeFormat))).format(timeFormat);
                        self.executionDateTime = ko.observable(diffTime);
                        self.executionNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [data.countExecution]));
                        self.errorNumber = ko.observable(nts.uk.resource.getText("KSC001_47", [data.countError]));
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * open EmployeeTargetListDialog
             */
            openEmployeeTargetListDialog(): void {
                let self = this;
                var data: any = {
                    executionId: self.executionId(),
                    startDate: self.startDate(),
                    endDate: self.endDate()
                }
                blockUI.invisible();
                nts.uk.ui.windows.setShared('dataFromDetailDialog', data);
                nts.uk.ui.windows.sub.modal("/view/ksc/001/i/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }

            /**
             * open ErrorContentDialog
             */
            openErrorContentDialog(): void {
                let self = this;
                blockUI.invisible();
                var data: any = {
                    executionId: self.executionId(),
                    startDate: self.startDate(),
                    endDate: self.endDate()
                }
                nts.uk.ui.windows.setShared('dataFromDetailDialog', data);
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

            /**
             * function to load detail content string
             */
            private detailContentString(data: any): string[] {
                let self = this;
                let str: string[] = [];
                let spaceString = "　";
                //==
                if (data.implementAtr == ImplementAtr.GENERALLY_CREATED) {
                    str.push(nts.uk.resource.getText("KSC001_35"));
                } else {
                    str.push(nts.uk.resource.getText("KSC001_36"));
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

                        //==
                        if (data.resetWorkingHours) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_15"));
                        }
                        //==
                        if (data.resetDirectLineBounce) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_11"));
                        }
                        //==
                        if (data.resetMasterInfo) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_12"));
                        }
                        //==
                        if (data.resetTimeChildCare) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_13"));
                        }
                        //==
                        if (data.resetAbsentHolidayBusines) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_14"));
                        }
                        //==
                        if (data.resetTimeAssignment) {
                            str.push(spaceString + nts.uk.resource.getText("KSC001_38") + nts.uk.resource.getText("KSC001_16"));
                        }
                    }
                }
                if (data.confirm) {
                    str.push(nts.uk.resource.getText("KSC001_17"));
                }
                //return
                return str;
            }

            /**
             * function to detail create method
             */
            private detailCreateMethod(data: any): string[] {
                let self = this;
                let str: string[] = [];
                if (!((data.implementAtr == ImplementAtr.RECREATE) && (data.processExecutionAtr == ProcessExecutionAtr.RECONFIG))) {
                    str.push(nts.uk.resource.getText("KSC001_22"));
                    str.push(nts.uk.resource.getText("KSC001_23"));
                    str.push(nts.uk.resource.getText("KSC001_39", [data.copyStartDate]));
                }
                return str;
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