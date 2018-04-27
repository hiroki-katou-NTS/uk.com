module nts.uk.at.view.kmw006.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getLogInfor().done((result: Array<any>) => {
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        let r = result[i];
                        self.items.push(new ItemModel(r.monthlyClosureLogId, r.closureId, r.closureName, r.yearMonth, r.executeDT, r.totalEmployee, r.alarmCount, r.errorCount));
                    }
                }
                self.initIGrid();
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        private closeDialog() {
            nts.uk.ui.windows.close();
        }

        private initIGrid() {
            let self = this;
            $("#list").igGrid({
                height: '400px',
                dataSource: self.items(),
                primaryKey: 'logId',
                columns: [
                    { headerText: "", key: 'logId', dataType: 'string', hidden: true },
                    { headerText: getText('KMW006_2'), key: 'column1', dataType: 'string', width: '120px' },
                    { headerText: getText('KMW006_33'), key: 'column2', dataType: 'string', width: '120px' },
                    { headerText: getText('KMW006_34'), key: 'column3', dataType: 'string', width: '200px' },
                    { headerText: "", key: 'totalEmp', dataType: 'number', hidden: true },
                    { headerText: getText('KMW006_35'), key: 'column4', dataType: 'string', width: '100px', template: "{{if ${totalEmp} > 0}} <a href=\"javascript:void(0)\" class=\"linkLabel dialogDLink\" logId=\"${logId}\">${column4}</a> {{else}} ${column4} {{/if}}" },
                    { headerText: "", key: 'alarmCount', dataType: 'number', hidden: true },
                    { headerText: getText('KMW006_56'), key: 'column5', dataType: 'string', width: '100px', template: "{{if ${alarmCount} > 0}} <a href=\"javascript:void(0)\" class=\"linkLabel dialogEALink\" logId=\"${logId}\">${column5}</a> {{else}} ${column5} {{/if}}" },
                    { headerText: "", key: 'errorCount', dataType: 'number', hidden: true },
                    { headerText: getText('KMW006_36'), key: 'column6', dataType: 'string', width: '100px', template: "{{if ${errorCount} > 0}} <a href=\"javascript:void(0)\" class=\"linkLabel dialogEELink\" logId=\"${logId}\">${column6}</a> {{else}} ${column6} {{/if}}" }
                ],
                features: [
                    {
                        name: 'Paging',
                        pageSize: 15,
                        currentPageIndex: 0,
                        showPageSizeDropDown: false,
                        pageIndexChanged: () => {
                            self.bindLinkClick();
                        },
                        pageCountLimit: 20
                    }
                ]
            });
        }

        private openKmw006dDialog(logId) {
            let self = this;
            block.invisible();
            service.getListEmpId(logId, ATR.ALL).done((result: Array<string>) => {
                if (result && result.length) {
                    let tmp = _.find(self.items(), (x: ItemModel) => x.logId == logId);
                    setShared("Kmw006dParams", { logId: logId, listEmpId: result, closure: tmp.column1, targetYm: tmp.column2, executionDt: tmp.column3 });
                    modal("/view/kmw/006/d/index.xhtml").onClosed(() => {
                    });
                }
            }).fail((error) => {
                alert(error);
            }).always(() => {
                block.clear();
            });
        }

        private openKmw006eDialog(logId, atr) {
            let self = this;
            block.invisible();
            service.getListEmpId(logId, atr).done((result: Array<string>) => {
                if (result && result.length) {
                    let tmp = _.find(self.items(), (x: ItemModel) => x.logId == logId);
                    setShared("Kmw006eParams", { logId: logId, listEmpId: result, closure: tmp.column1, targetYm: tmp.column2, executionDt: tmp.column3, atr: atr });
                    modal("/view/kmw/006/e/index.xhtml").onClosed(() => {
                    });
                }
            }).fail((error) => {
                alert(error);
            }).always(() => {
                block.clear();
            });
        }

        private openKmw006eDialogA(logId: string) {
            let self = this;
            self.openKmw006eDialog(logId, ATR.ALARM);
        }

        private openKmw006eDialogE(logId: string) {
            let self = this;
            self.openKmw006eDialog(logId, ATR.ERROR);
        }
        
        public bindLinkClick() {
            let self = this;
            $('.dialogDLink').click(function() {
                var logId = $(this).attr("logId");
                self.openKmw006dDialog(logId);
            });
            $('.dialogEALink').click(function() {
                var logId = $(this).attr("logId");
                self.openKmw006eDialogA(logId);
            });
            $('.dialogEELink').click(function() {
                var logId = $(this).attr("logId");
                self.openKmw006eDialogE(logId);
            });
        }

    }

    class ItemModel {
        logId: string;
        column1: string;
        column2: string;
        column3: string;
        column4: string;
        column5: string;
        column6: string;
        totalEmp: number;
        alarmCount: number;
        errorCount: number;

        constructor(logId: string, closureId: number, closureName: string, targetYm: number, executionDt: string, totalEmployee: number, alarmCount: number, errorCount: number) {
            this.column1 = getText("KMW006_55", [closureId, closureName]);
            this.column2 = nts.uk.time.formatYearMonth(targetYm);
            this.column3 = moment.utc(executionDt).format("YYYY/MM/DD HH:mm:ss");
            this.column4 = getText("KMW006_37", [totalEmployee]);
            this.column5 = getText("KMW006_37", [alarmCount]);
            this.column6 = getText("KMW006_37", [errorCount]);
            this.logId = logId;
            this.totalEmp = totalEmployee;
            this.alarmCount = alarmCount;
            this.errorCount = errorCount;
        }
    }

    enum ATR {
        ALL = 0,
        ALARM = 1,
        ERROR = 2
    }

}

