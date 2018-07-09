module nts.uk.com.view.cmf002.x.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import shareModel = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

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
            for (let i = 100; i < 110; i++) {
                self.cndSetList().push(new CndSet(true, i.toString(), "Cnd Set" + i));
            }
            self.colCndSet = ko.observableArray([
                { headerText: getText("CMF002_307"), key: 'typeCnd', width: 40, hidden: false },
                { headerText: getText("CMF002_308"), key: 'code', width: 70, hidden: false },
                { headerText: getText("CMF002_309"), key: 'name', width: 200, hidden: false },
            ]);
            self.selectorCndSet = ko.observable({});

            self.execHistList = ko.observable([]);
            for (let i = 0; i < 100; i++) {
                let del = 0;
                if (i % 2 == 0) {
                    del = 1
                }
                self.execHistList().push(new ExecHist(i.toString(), del, new Date(), "emp " + i.toString(), "set " + i.toString(),
                    1, 1, i, "unit " + i.toString(), 1, i, "file " + i.toString(), i))
            }
            self.selectorExeHist = ko.observable({});
            self.execHistColumns = [
                { headerText: "", key: 'outputProcessId', dataType: 'string' },
                // X5_H1_2
                { headerText: getText("CMF002_310"), dataType: 'string', key: 'deleteFile', width: '80px', unbound: false, ntsControl: 'ButtonDel', formatter: formatBtnDel },
                // X5_H1_3
                { headerText: getText("CMF002_311"), dataType: 'string', key: 'deleteFile2', width: '80px', unbound: false, ntsControl: 'FlexImage' },
                // X5_H1_4
                { headerText: getText("CMF002_312"), dataType: 'datetime', key: 'processStartDateTime', width: '170px', formatter: formatDateTime },
                // X5_H1_5
                { headerText: getText("CMF002_313"), dataType: 'string', key: 'sName', width: '150px' },
                // X5_H1_6
                { headerText: getText("CMF002_314"), dataType: 'string', key: 'nameSetting', width: '80px' },
                // X5_H1_7
                //{ headerText: getText("CMF002_315"), dataType: 'string', key: 'standardClass', width: '70px' },
                // X5_H1_8
                { headerText: getText("CMF002_316"), dataType: 'string', key: 'executeForm', width: '80px' },
                // X5_H1_9
                { headerText: getText("CMF002_317"), dataType: 'string', key: 'totalCount', width: '100px' },
                // X5_H1_10
                { headerText: getText("CMF002_318"), dataType: 'string', key: 'processUnit', width: '100px' },
                // X5_H1_11
                { headerText: getText("CMF002_319"), dataType: 'string', key: 'resultStatus', width: '100px' },
                // X5_H1_12
                { headerText: getText("CMF002_320"), dataType: 'string', key: 'totalErrorCount', width: '50px', unbound: false, ntsControl: 'ButtonLog' },
                // X5_H1_13
                { headerText: getText("CMF002_321"), dataType: 'string', key: 'fileName', width: '80px' },
                // X5_H1_14
                { headerText: getText("CMF002_322"), dataType: 'string', key: 'fileSize', width: '80px' },
            ];
            self.execHistControl = [
                { name: 'ButtonDel', text: getText('CMF002_323'), controlType: 'Button', enable: true },
                { name: 'FlexImage', source: 'ui-icon ui-icon-folder-open', click: function() { alert('Show!'); }, controlType: 'FlexImage' },
                { name: 'ButtonLog', text: getText('CMF002_324'), controlType: 'Button', enable: true },
            ];
            self.loadGrid();
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }

        loadGrid() {
            let self = this;

            $("#execHistGrid").ntsGrid({
                width: "1200px",
                height: '300px',
                dataSource: self.execHistList(),
                primaryKey: 'outputProcessId',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                showNumbering: true,
                columns: self.execHistColumns,
                ntsControls: self.execHistControl,
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false
                    }
                ],
            });
        }

        getGridHeader(): Array<any> {

        }

        /**
         * Grid Setting features
         */
        getGridFeatures(): Array<any> {
            let self = this;
            let features = [
                {
                    name: 'Resizing',
                    columnSettings: [{
                        columnKey: 'id', allowResizing: false, minimumWidth: 0
                    }]
                },
                { name: 'MultiColumnHeaders' },
                {
                    name: 'Paging',
                    pageSize: 100,
                    currentPageIndex: 0
                },
                {
                    name: 'ColumnFixing', fixingDirection: 'left',
                    showFixButtons: false,
                    columnSettings: self.fixHeaders()
                },
                {
                    name: 'Summaries',
                    showSummariesButton: false,
                    showDropDownButton: false,
                    columnSettings: self.columnSettings(),
                    resultTemplate: '{1}'
                }
            ];
            return features;
        }

    }

    class CndSet {
        typeCnd: string;
        code: string;
        name: string
        constructor(isStd: boolean, code: string, name: string) {
            if (isStd) {
                this.typeCnd = "〇";
            } else {
                this.typeCnd = "";
            }
            this.code = code;
            this.name = name;
        }
    }

    class ExecHist {
        // 外部出力処理ID
        outputProcessId: string;
        //  ファイル削除
        deleteFile: number;
        deleteFile2: number;
        // 処理開始日時
        processStartDateTime: Date;
        sName: string;
        // 設定名称
        nameSetting: string;
        // 定型区分
        standardClass: number;
        // 実行形態
        executeForm: number;
        // トータルカウント
        totalCount: number;
        // 処理単位
        processUnit: string;
        // 結果状態
        resultStatus: number;
        // トータルエラーカウント
        totalErrorCount: number;
        // ファイル名
        fileName: string;
        // ファイルサイズ
        fileSize: number;
        constructor(outputProcessId: string, deleteFile: number, processStartDateTime: Date, sName: string, nameSetting: string,
            standardClass: number, executeForm: number, totalCount: number, processUnit: string, resultStatus: number,
            totalErrorCount: number, fileName: string, fileSize: number) {
            this.outputProcessId = outputProcessId;
            this.deleteFile = deleteFile;
            this.deleteFile2 = deleteFile;
            this.processStartDateTime = processStartDateTime;
            this.sName = sName;
            this.nameSetting = nameSetting;
            this.standardClass = standardClass;
            this.executeForm = executeForm;
            this.totalCount = totalCount;
            this.processUnit = processUnit;
            this.resultStatus = resultStatus;
            this.totalErrorCount = totalErrorCount;
            this.fileName = fileName;
            this.fileSize = fileSize;
        }
    }
}

function formatBtnDel(value, row) {
    if (value == 0)
        return '';
}
function formatIcon(value, row) {
    if (value == 0)
        return '';
}
function formatDateTime(value, row) {
    return nts.uk.time.formatDate(value, "yyyy/MM/dd hh:mm:ss")
}