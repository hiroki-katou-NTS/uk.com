module nts.uk.com.view.cmf002.x.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import shareModel = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;

    export class ScreenModel {
        exePeriod: KnockoutObservable<any>;
        cndSetList: KnockoutObservableArray<CndSet>;
        colCndSet: KnockoutObservableArray<NtsGridListColumn>;
        selectorCndSet: KnockoutObservable<any>;

        execHistList: KnockoutObservableArray<ExecHist>;
        execHistColumns: [];
        execHistControl: [];
        selectorExeHist: KnockoutObservable<any>;

        constructor() {
            let self = this;
            self.exePeriod = ko.observable({});
            self.cndSetList = ko.observable([]);
            self.colCndSet = ko.observableArray([
                { headerText: getText("CMF002_307"), key: 'typeCnd', width: 40, hidden: false },
                { headerText: getText("CMF002_308"), key: 'code', width: 70, hidden: false },
                { headerText: getText("CMF002_309"), key: 'name', width: 200, hidden: false },
            ]);
            self.selectorCndSet = ko.observable({});
            self.execHistList = ko.observable([]);
            self.selectorExeHist = ko.observable({});
            self.execHistColumns = [
                { headerText: "", key: 'outputProcessId', dataType: 'string' },
                { headerText: "", dataType: 'string', key: 'fileId', hidden: true },
                // X5_H1_2
                { headerText: getText("CMF002_310"), dataType: 'string', key: 'deleteFile', width: '80px', unbound: false, ntsControl: 'ButtonDel' },
                // X5_H1_3
                { headerText: getText("CMF002_311"), dataType: 'string', key: 'fileDowload', width: '80px', unbound: false, ntsControl: 'FlexImage' },
                // X5_H1_4
                { headerText: getText("CMF002_312"), dataType: 'datetime', key: 'processStartDateTime', width: '170px' },
                // X5_H1_5
                { headerText: getText("CMF002_313"), dataType: 'string', key: 'empName', width: '150px' },
                // X5_H1_6
                { headerText: getText("CMF002_314"), dataType: 'string', key: 'nameSetting', width: '100px' },
                // X5_H1_7
                //{ headerText: getText("CMF002_315"), dataType: 'string', key: 'standardClass', width: '70px' },
                // X5_H1_8
                { headerText: getText("CMF002_316"), dataType: 'string', key: 'executeFormName', width: '70px' },
                // X5_H1_9
                { headerText: getText("CMF002_317"), dataType: 'string', key: 'numberOfPerson', width: '70px' },
                // X5_H1_10
                { headerText: getText("CMF002_318"), dataType: 'string', key: 'resultStatusName', width: '70px' },
                // X5_H1_11
                { headerText: getText("CMF002_319"), dataType: 'string', key: 'totalErrorCount', width: '70px' },
                // X5_H1_12
                { headerText: getText("CMF002_320"), dataType: 'string', key: 'totalErrorCountBtn', width: '50px', unbound: false, ntsControl: 'ButtonLog' },
                // X5_H1_13
                { headerText: getText("CMF002_321"), dataType: 'string', key: 'fileName', width: '190px' },
                // X5_H1_14
                { headerText: getText("CMF002_322"), dataType: 'string', key: 'fileSize', width: '100px' },
            ];
            self.execHistControl = [
                { name: 'ButtonDel', text: getText('CMF002_323'), click: function(item) { self.deleteFile(item); }, controlType: 'Button', enable: true },
                { name: 'FlexImage', source: 'img-icon icon-download', click: function(key, outputProcessId) { self.downloadFile(outputProcessId); }, controlType: 'FlexImage' },
                { name: 'ButtonLog', text: getText('CMF002_324'), controlType: 'Button', enable: true },
            ];
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getExOutCondSetAndExecHist().done((data) => {
                _.forEach(data.condSetList, item => {
                    self.cndSetList().push(CndSet.fromApp(item));
                });
                _.forEach(data.execHistList, item => {
                    self.execHistList().push(ExecHist.fromApp(item));
                });
                self.loadGrid();
                block.clear();
                dfd.resolve();
            }).fail(err => {
                alertError(err);
                block.clear();
                dfd.reject();
            })
            return dfd.promise();
        }

        loadGrid() {
            let self = this;
            let cellStates = self.getCellStates(self.execHistList());

            $("#execHistGrid").ntsGrid({
                width: "1220px",
                height: '230px',
                dataSource: self.execHistList(),
                primaryKey: 'outputProcessId',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                columns: self.execHistColumns,
                ntsControls: self.execHistControl,
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false
                    },
                    {
                        name: 'Paging',
                        pageSize: 10,
                        currentPageIndex: 0
                    }
                ],
                ntsFeatures: [
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: cellStates
                    },
                ],
            });
        }

        getCellStates(items: Array<ExecHist>): Array<CellState> {
            let self = this;
            let result = [];
            _.each(items, function(item) {
                let rowId = item.outputProcessId;
                // ファイル削除
                if (item.deleteFile == shareModel.NOT_USE_ATR.USE) {
                    result.push(new CellState(rowId, 'deleteFile', ['hide']));
                }
                // ダウンロード
                if (item.fileDowload == shareModel.NOT_USE_ATR.USE) {
                    result.push(new CellState(rowId, 'fileDowload', ['hide']));
                }
                if (item.totalErrorCount == 0) {
                    result.push(new CellState(rowId, 'totalErrorCount', ['hide']));
                }
                result.push(new CellState(rowId, 'empName', ['text-limited']));
            });
            return result;
        }

        deleteFile(log) {
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                // サーバーの「ファイルID」のファイルを削除する
                nts.uk.request.specials.donwloadFile(log.fileId);
                // ドメインモデル「外部出力実行結果ログ」を更新する
                // TODO update domain
                service.useDeleteFile(log.outputProcessId).done(data => {

                }).fail(err => {
                    alertError(err);
                }).always(() => {
                    block.clear();
                })
                // 対象行のファイル削除ボタン・ダウンロードアイコンを消去する
                // TODO update grid
            });
            console.log(log.outputProcessId);
        }

        downloadFile(outputProcessId) {
            let self = this;
            let fileId = _.find(self.execHistList(), { outputProcessId: outputProcessId }).fileId;
            nts.uk.request.specials.donwloadFile(fileId);
        }
    }

    class CndSet {
        typeCnd: string;
        code: string;
        name: string
        constructor(stdAtr: boolean, code: string, name: string) {
            if (stdAtr == shareModel.STANDARD_ATR.STANDARD) {
                this.typeCnd = "〇";
            } else {
                this.typeCnd = "";
            }
            this.code = code;
            this.name = name;
        }

        static fromApp(app): CndSet {
            return new CndSet(app.standardAttr, app.conditionSetCode, app.conditionSetName);
        }
    }

    class ExecHist {
        // 外部出力処理ID
        outputProcessId: string;
        // ファイル削除
        deleteFile: number;
        // ファイルID
        fileId: string;
        fileDowload: number;
        // 処理開始日時
        processStartDateTime: string;
        empName: string;
        // 設定名称
        nameSetting: string;
        // 定型区分
        standardClass: number;
        // 実行形態
        executeForm: number;
        executeFormName: string;
        // トータルカウント
        totalCount: number;
        // 処理単位
        processUnit: string;
        // 対象人数
        numberOfPerson: string;
        // 結果状態
        resultStatus: number;
        resultStatusName: string;
        // トータルエラーカウント
        totalErrorCount: number;
        // ファイル名
        fileName: string;
        // ファイルサイズ
        fileSize: string;
        constructor(outputProcessId: string, deleteFile: number, fileId: string, processStartDateTime: string, empName: string, nameSetting: string,
            standardClass: number, executeForm: number, totalCount: number, processUnit: string, resultStatus: number,
            totalErrorCount: number, fileName: string, fileSize: number) {
            this.outputProcessId = outputProcessId;
            this.deleteFile = deleteFile;
            this.fileId = fileId;
            this.fileDowload = deleteFile;
            this.processStartDateTime = moment.utc(processStartDateTime).format("YYYY/MM/DD HH:mm:ss");
            this.empName = empName;
            this.nameSetting = nameSetting;
            this.standardClass = standardClass;
            this.executeForm = executeForm;
            switch (this.executeForm) {
                case 0: this.executeFormName = getText("CMF002_510"); break;
                case 1: this.executeFormName = getText("CMF002_511"); break;
                default: this.executeFormName = "";
            }
            this.totalCount = totalCount;
            this.processUnit = processUnit;
            this.numberOfPerson = this.totalCount + this.processUnit;
            this.resultStatus = resultStatus;
            switch (this.resultStatus) {
                case 0: this.resultStatusName = getText("CMF002_512"); break;
                case 1: this.resultStatusName = getText("CMF002_513"); break;
                case 2: this.resultStatusName = getText("CMF002_514"); break;
                default: this.resultStatusName = "";
            }
            this.totalErrorCount = totalErrorCount + getText("CMF002_241");
            this.fileName = fileName;
            this.fileSize = this.fileName == "" ? "" : fileSize + "KB";
        }
        static fromApp(app): ExecHist {
            return new ExecHist(app.outputProcessId, app.deleteFile, app.fileId, app.processStartDateTime,
                app.empName, app.nameSetting, app.standardClass, app.executeForm, app.totalCount,
                app.processUnit, app.resultStatus, app.totalErrorCount, app.fileName, app.fileSize);
        }
    }

    class CellState {
        rowId: number;
        columnKey: string;
        state: Array<any>
        constructor(rowId: any, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
}