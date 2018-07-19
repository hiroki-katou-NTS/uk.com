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
        exOutCtgIdList: Array<any>;
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
            self.selectorCndSet = ko.observable(null);
            self.execHistList = ko.observable([]);
            self.selectorExeHist = ko.observable({});
            self.execHistColumns = [
                { headerText: "", key: 'outputProcessId', dataType: 'string' },
                // X5_H1_2
                { headerText: getText("CMF002_310"), dataType: 'string', key: 'deleteFile', width: '80px', unbound: false, ntsControl: 'ButtonDel' },
                // X5_H1_3
                { headerText: getText("CMF002_311"), dataType: 'string', key: 'fileDowload', width: '80px', unbound: false, ntsControl: 'FlexImage' },
                // X5_H1_4
                { headerText: getText("CMF002_312"), template: '<div class="limited-label">${processStartDateTime}</div>', dataType: 'datetime', key: 'processStartDateTime', width: '170px' },
                // X5_H1_5
                { headerText: getText("CMF002_313"), template: '<div class="limited-label">${empName}</div>', dataType: 'string', key: 'empName', width: '150px' },
                // X5_H1_6
                { headerText: getText("CMF002_314"), template: '<div class="limited-label">${nameSetting}</div>', dataType: 'string', key: 'nameSetting', width: '100px' },
                // X5_H1_7
                //{ headerText: getText("CMF002_315"), dataType: 'string', key: 'standardClass', width: '70px' },
                // X5_H1_8
                { headerText: getText("CMF002_316"), template: '<div class="limited-label">${executeFormName}</div>', dataType: 'string', key: 'executeFormName', width: '70px' },
                // X5_H1_9
                { headerText: getText("CMF002_317"), template: '<div class="limited-label">${numberOfPerson}</div>', dataType: 'string', key: 'numberOfPerson', width: '70px' },
                // X5_H1_10
                { headerText: getText("CMF002_318"), template: '<div class="limited-label">${resultStatusName}</div>', dataType: 'string', key: 'resultStatusName', width: '70px' },
                // X5_H1_11
                { headerText: getText("CMF002_319"), template: '<div class="limited-label">${totalErrorCountName}</div>', dataType: 'string', key: 'totalErrorCountName', width: '70px' },
                // X5_H1_12
                { headerText: getText("CMF002_320"), dataType: 'string', key: 'totalErrorCountBtn', width: '50px', unbound: false, ntsControl: 'ButtonLog' },
                // X5_H1_13
                { headerText: getText("CMF002_321"), template: '<div class="limited-label">${fileName}</div>', dataType: 'string', key: 'fileName', width: '190px' },
                // X5_H1_14
                { headerText: getText("CMF002_322"), template: '<div class="limited-label">${fileSize}</div>', dataType: 'string', key: 'fileSize', width: '100px' },
            ];
            self.execHistControl = [
                { name: 'ButtonDel', text: getText('CMF002_323'), click: function(item, item2) { self.deleteFile(item.outputProcessId, item2); }, controlType: 'Button', enable: true },
                { name: 'FlexImage', source: 'img-icon icon-download', click: function(key, outputProcessId) { self.downloadFile(outputProcessId); }, controlType: 'FlexImage' },
                { name: 'ButtonLog', text: getText('CMF002_324'), controlType: 'Button', enable: true },
            ];
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getExecHist().done((data) => {
                console.log(data);
                self.exePeriod().startDate = data.startDate;
                self.exePeriod().endDate = data.endDate;
                self.exOutCtgIdList = data.exOutCtgIdList;
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
                    result.push(new CellState(rowId, 'totalErrorCountBtn', ['hide']));
                }
                result.push(new CellState(rowId, 'empName', ['text-limited']));
            });
            return result;
        }

        deleteFile(outputProcessId, item2) {
            let self = this;
            console.log(item2.target);
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let checkFile = nts.uk.request.specials.isFileExist(execHist.fileId);
                // ドメインモデル「外部出力実行結果ログ」を更新する
                let updateDeleteFile = service.useDeleteFile(execHist.outputProcessId);
                $.when(checkFile, updateDeleteFile).done((isExist, useDelFile) => {
                    if (useDelFile == shareModel.NOT_USE_ATR.USE) {
                        if (isExist) {
                            // サーバーの「ファイルID」のファイルを削除する
                            nts.uk.request.specials.deleteFile(execHist.fileId)
                        }
                        execHist.updateDeleteFile(shareModel.NOT_USE_ATR.USE);
                        // update grid
                        $("#execHistGrid").igGrid("dataSourceObject", self.execHistList()).igGrid("dataBind");
                    }
                }).fail(err => {
                    alertError(err);
                }).always(() => {
                    block.clear();
                })
            });
        }

        downloadFile(outputProcessId) {
            let self = this;
            let fileId = _.find(self.execHistList(), { outputProcessId: outputProcessId }).fileId;
            nts.uk.request.specials.donwloadFile(fileId);
        }

        searchExecHist() {
            let self = this;
            block.invisible();
            let param = {
                startDate: self.exePeriod().startDate == null ? null : new Date(self.exePeriod().startDate),
                endDate: self.exePeriod().endDate == null ? null : new Date(self.exePeriod().endDate),
                exOutCtgIdList: self.exOutCtgIdList,
                condSetCd: self.selectorCndSet(),
            }
            service.getExOutExecHistSearch(param).done(data => {
                let listHist: Array<ExecHist> = [];
                _.forEach(data, item => {
                    listHist.push(ExecHist.fromApp(item));
                });
                self.execHistList(listHist);
                $("#execHistGrid").igGrid("dataSourceObject", self.execHistList()).igGrid("dataBind");
            }).fail(err => {
                alertError(err);
            }).always(() => {
                block.clear();
            })
        }
    }

    class CndSet {
        typeCnd: string;
        code: string;
        name: string
        constructor(stdAtr: number, code: string, name: string) {
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
        totalErrorCountName: string;
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
            this.totalErrorCount = totalErrorCount;
            this.totalErrorCountName = this.totalErrorCount.toString() + getText("CMF002_241");
            this.fileName = fileName;
            this.fileSize = this.fileName == "" ? "" : fileSize + "KB";
        }

        updateDeleteFile(deleteFile) {
            this.deleteFile = deleteFile;
            this.fileDowload = deleteFile;
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