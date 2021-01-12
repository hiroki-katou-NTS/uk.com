module nts.uk.com.view.cmf001.o.viewmodel {
    import model = cmf001.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listSysType: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedSysType: KnockoutObservable<number> = ko.observable(-1);

        listCondition: KnockoutObservableArray<model.StandardAcceptanceConditionSetting> = ko.observableArray([]);
        selectedConditionItem: any;
        selectedConditionCd: KnockoutObservable<string> = ko.observable('');
        selectedConditionName: KnockoutObservable<string> = ko.observable('');
        selectedConditionLineNumber: KnockoutObservable<number> = ko.observable(0);
        selectedConditionStartLine: KnockoutObservable<number> = ko.observable(0);

        //upload file
        fileId: KnockoutObservable<string> = ko.observable(null);
        fileName: KnockoutObservable<string> = ko.observable(null);
        listAccept: KnockoutObservableArray<model.StandardAcceptItem> = ko.observableArray([]);
        selectedAccept: KnockoutObservable<any> = ko.observable('');
        totalRecord: KnockoutObservable<number> = ko.observable(0);
        totalLine: KnockoutObservable<number> = ko.observable(0);
        
        selectedEncoding: KnockoutObservable<number> = ko.observable(3);
        encodingList: KnockoutObservableArray<model.EncodingModel> = ko.observableArray([]);

        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            self.encodingList(model.getEncodingList());
            //システム種類を変更する
            self.selectedSysType.subscribe(function(data: any) {
                //画面上の条件コード/名称をクリアする
                self.selectedConditionCd('');
                self.selectedConditionName('');
                //ドメインモデル「受入条件設定（定型）」を取得する
                self.loadListCondition(data);
            });

            //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
            self.selectedConditionCd.subscribe(function(data: any) {
                //取込情報を選択する
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: any) => x.dispConditionSettingCode == data);
                    //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
                    self.selectedConditionItem = item;
                    self.selectedConditionName(item.dispConditionSettingName);
                    self.selectedConditionLineNumber(item.csvDataItemLineNumber);
                    self.selectedConditionStartLine(item.csvDataStartLine);
                    if (item.characterCode == null)
                        self.selectedEncoding = ko.observable(3);
                    else
                        self.selectedEncoding = ko.observable(item.characterCode);
                }
                else {
                    self.selectedConditionItem = null;
                    self.selectedConditionName('');
                    self.selectedConditionLineNumber(0);
                    self.selectedConditionStartLine(0);
                    self.selectedEncoding = ko.observable(3);
                }
                //「受入ファイルアップロード」をクリアする
                self.resetFile();
            });

            $("#grd_Accept").ntsFixedTable({ height: 265 });
        }
        /**
         * start page data    
        */
        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getSysTypes().done(function(data: Array<any>) {
                if (data && data.length) {
                    let _rsList: Array<model.ItemModel> = _.map(data, rs => {
                        return new model.ItemModel(rs.type, rs.name);
                    });
                    _rsList = _.sortBy(_rsList, ['code']);
                    self.listSysType(_rsList);
                    //システム種類を画面セットする
                    self.selectedSysType(self.listSysType()[0].code);
                } else {
                    //トップページに戻る
                    nts.uk.request.jump("/view/cmf/001/a/index.xhtml");
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        //次の画面へ遷移する
        private gotoP(): void {
            let self = this;

            //条件コードは選択されているか判別
            if (self.selectedConditionCd() == null || self.selectedConditionCd() == '') {
                //Msg_963　を表示する。受入条件が選択されていません。
                alertError({ messageId: "Msg_963" });
                $("#grd_Condition").focus();
                return;
            }
            //受入ファイルがアップロードされているか判別
            if (self.fileId() == null) {
                //Msg_964　を表示する。受入ファイルがアップロードされていません。
                alertError({ messageId: "Msg_964" });
                $("#file-upload").focus();
                return;
            }
            //P:外部受入サマリー画面へ遷移する
            $('#ex_accept_wizard').ntsWizard("next");
            self.loadListAccept();
            $("#P6_1").focus();
        }
        private gotoO(): void {
            //受入設定選択に戻る
            $('#ex_accept_wizard').ntsWizard("goto", 0);
            $("#grd_Condition tr[aria-selected='true']").focus();
        }

        private uploadFile(fileInfo: any): void {
            let self = this;
            block.invisible();
            self.fileId(fileInfo.id);
            service.getNumberOfLine(fileInfo.id, self.selectedEncoding()).done(function(totalLine: any) {
                self.totalLine(totalLine);
                //アップロードCSVが取込開始行に満たない場合                   
                if (totalLine < self.selectedConditionStartLine()) {
                    self.resetFile();
                    alertError({ messageId: "Msg_1059" });
                }
                //アップロードCSVが取込開始行以上ある
                else {
                    //基盤からファイルIDを取得する
                    self.fileId(fileInfo.id);
                }
            }).fail(function(err) {
                self.resetFile();
                alertError({ messageId: "Msg_910" });
            }).always(() => {
                block.clear();
                $("#file-upload").focus();
            });
        }

        private resetFile(): void {
            let self = this;
            self.fileId(null);
            self.fileName("");
        }

        private loadListCondition(sysType): void {
            let self = this;
            block.invisible();
            //「条件設定一覧」を初期化して取得した設定を表示する
            $('.clear-btn.ntsSearchBox_Component').click();
            self.listCondition([]);
            //ドメインモデル「受入条件設定（定型）」を取得する
            service.getConditionList(sysType).done(function(data: Array<any>) {
                self.listCondition.removeAll();
                //表示するデータがある場合   
                if (data && data.length) {
                    let _rspList: Array<model.StandardAcceptanceConditionSetting> = _.map(data, rsp => {
                        return new model.StandardAcceptanceConditionSetting(rsp.systemType, rsp.conditionSetCode,
                            rsp.conditionSetName, rsp.deleteExistData, rsp.acceptMode, rsp.csvDataItemLineNumber,
                            rsp.csvDataStartLine, rsp.characterCode, rsp.deleteExistDataMethod, rsp.categoryId);
                    });
                    self.listCondition(_rspList);

                    //取得した設定を「条件設定一覧」に表示する
                    self.selectedConditionCd(self.listCondition()[0].conditionSetCode());
                    self.selectedConditionName(self.listCondition()[0].conditionSetName());
                }
                //取得データが0件の場合      
                else {
                    //エラーメッセージ表示　Msg_907　外部受入設定が作成されていません。
                    alertError({ messageId: "Msg_907" });
                    $("#O6_1").focus();
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
                _.defer(() => {
                    $("#grd_Condition tr:first-child").focus();
                });
            });
        }

        private loadListAccept(): void {
            let self = this;
            block.invisible();
            //ドメインモデル「受入項目（定型）」を取得する      
            service.getStdAcceptItem(self.selectedSysType(), self.selectedConditionCd()).done(function(data: Array<any>) {
                self.listAccept.removeAll();
                self.totalRecord(null);
                if (data && data.length) {
                    let _rspList: Array<model.StandardAcceptItem> = _.map(data, rs => {
                        let formatSetting = null, fs = null, screenCondition: model.AcceptScreenConditionSetting = null;
                        switch (rs.itemType) {
                            case model.ITEM_TYPE.NUMERIC:
                                fs = rs.numberFormatSetting;
                                if (fs)
                                    formatSetting = new model.NumericDataFormatSetting(
                                        fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                        fs.decimalDivision, fs.decimalDigitNum, fs.decimalPointCls,
                                        fs.decimalFraction, fs.cdConvertCd, fs.fixedValue, fs.valueOfFixedValue);
                                break;
                            case model.ITEM_TYPE.CHARACTER:
                                fs = rs.charFormatSetting;
                                if (fs)
                                    formatSetting = new model.CharacterDataFormatSetting(
                                        fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                        fs.cdEditing, fs.cdEditDigit, fs.cdEditMethod,
                                        fs.cdConvertCd, fs.fixedValue, fs.fixedVal);
                                break;
                            case model.ITEM_TYPE.DATE:
                                fs = rs.dateFormatSetting;
                                if (fs)
                                    formatSetting = new model.DateDataFormatSetting(fs.formatSelection, fs.fixedValue, fs.valueOfFixedValue);
                                break;
                            case model.ITEM_TYPE.INS_TIME:
                                fs = rs.instTimeFormatSetting;
                                if (fs)
                                    formatSetting = new model.InstantTimeDataFormatSetting(
                                        fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                        fs.decimalSelect, fs.hourMinSelect, fs.delimiterSet,
                                        fs.roundProc, fs.roundProcCls, fs.fixedValue, fs.valueOfFixedValue);
                                break;
                            case model.ITEM_TYPE.TIME:
                                fs = rs.timeFormatSetting;
                                if (fs)
                                    formatSetting = new model.TimeDataFormatSetting(
                                        fs.effectiveDigitLength, fs.startDigit, fs.endDigit,
                                        fs.decimalSelect, fs.hourMinSelect, fs.delimiterSet,
                                        fs.roundProc, fs.roundProcCls, fs.fixedValue, fs.valueOfFixedValue);
                                break;
                        }
                        if (rs.screenConditionSetting) {
                            let sc = rs.screenConditionSetting;
                            screenCondition = new model.AcceptScreenConditionSetting(rs.acceptItemName, sc.selectComparisonCondition,
                                sc.timeConditionValue2, sc.timeConditionValue1,
                                sc.timeMomentConditionValue2, sc.timeMomentConditionValue1,
                                sc.dateConditionValue2, sc.dateConditionValue1,
                                sc.characterConditionValue2, sc.characterConditionValue1,
                                sc.numberConditionValue2, sc.numberConditionValue1,
                                rs.conditionCode, rs.acceptItemNumber);
                        }
                        return new model.StandardAcceptItem(rs.csvItemName, rs.csvItemNumber, rs.itemType, rs.acceptItemNumber, rs.acceptItemName, rs.systemType, rs.conditionCode, rs.categoryItemNo, formatSetting, screenCondition);
                    });

                    //アップロードしたファイルを読み込む
                    let columns = [];
                    _.each(_rspList, rs => {
                        columns.push(rs.csvItemNumber() - 1);
                    });
                    let sv1 = service.getRecord(self.fileId(), columns, self.selectedConditionStartLine() - 1, self.selectedEncoding());
                    let sv2 = service.getCategoryItem(self.selectedConditionItem.categoryId);

                    $.when(sv1, sv2).done(function(data1: Array<any>, data2: Array<any>) {
                        if (data1 && data1.length) {
                            _.each(_rspList, rs => {
                                let item1 = data1[0];
                                rs.sampleData(item1);
                                data1.shift();
                            });
                        }

                        if (data2 && data2.length) {
                            _.each(_rspList, rs => {
                                let item2 = _.find(data2, x => { return x.itemNo == rs.categoryItemNo(); });
                                rs.acceptItemName(item2.itemName);
                            });
                        }

                        self.listAccept(_rspList);

                        //ファイルの行数を取得する
                        let count = self.totalLine() - self.selectedConditionStartLine() + 1;
                        if (count < 0) {
                            count = 0;
                        }
                        self.totalRecord(count);
                    }).fail(function(error) {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
                else {
                    block.clear();
                    //エラーメッセージ表示　　Msg_1117　　項目が設定されていません。確認してください。
                    alertError({ messageId: "Msg_1117" });
                }
            }).fail(function(error) {
                alertError(error);
                block.clear();
            });
        }

        private editIngestion(item: any): void {
            var self = this;
            switch (item.itemType()) {
                case 0:
                    //数値型の場合                    
                    //G:「数値型設定」ダイアログをモーダルで表示する
                    setShared("CMF001gParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(item.numberFormatSetting) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/g/index.xhtml").onClosed(() => {
                        $("#P6_1").focus();
                    });
                    break;
                case 1:
                    //文字型の場合
                    //H:「文字型設定」ダイアログをモーダルで表示する
                    setShared("CMF001hParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(item.charFormatSetting) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/h/index.xhtml").onClosed(() => {
                        $("#P6_1").focus();
                    });
                    break;
                case 2:
                    //日付型の場合  
                    //I:「日付型設定」ダイアログをモーダルで表示する
                    setShared("CMF001iParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(item.dateFormatSetting) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/i/index.xhtml").onClosed(() => {
                        $("#P6_1").focus();
                    });
                    break;
                case 3:
                    //時刻型の場合 
                    //J:「時刻型・時間型設定」ダイアログをモーダルで表示する
                    setShared("CMF001jParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(item.instTimeFormatSetting) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/j/index.xhtml").onClosed(() => {
                        $("#P6_1").focus();
                    });
                    break;
                case 4:
                    //時間型の場合
                    //J:「時刻型・時間型設定」ダイアログをモーダルで表示する
                    setShared("CMF001jParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(item.timeFormatSetting) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/j/index.xhtml").onClosed(() => {
                        $("#P6_1").focus();
                    });
                    break;
            }
        }

        private receiveCondition(item): void {
            //L:「受入条件設定ダイアログをモーダルで表示する
            let settingL = null;
            if (item.screenConditionSetting) settingL = ko.toJS(item.screenConditionSetting);
            setShared("CMF001lParams", {             
                dataType: item.itemType(), 
                itemName: item.acceptItemName(), 
                condition: ko.toJS(settingL),
                inputMode: false });
            nts.uk.ui.windows.sub.modal("/view/cmf/001/l/index.xhtml").onClosed(() => {
                $("#P6_1").focus();
            });
        }

        private gotoA(): void {
            nts.uk.request.jump("/view/cmf/001/a/index.xhtml");
        }

        private gotoQ(): void {
            let self = this;
            //Q:「外部受入処理中ダイアログ」をチェック中で起動する 
            setShared("CMF001qParams", {
                mode: 0,
                systemType: self.selectedSysType(),
                conditionCd: self.selectedConditionCd(),
                conditionName: self.selectedConditionName(),
                fileName: self.fileName(),
                fileId: self.fileId(),
                totalRecord: self.totalRecord()
            });
            nts.uk.ui.windows.sub.modal("/view/cmf/001/q/index.xhtml");
        }

        private getItemTypeName(itemType: number): string {
            switch (itemType) {
                case model.ITEM_TYPE.NUMERIC: return getText('Enum_ItemType_NUMERIC');
                case model.ITEM_TYPE.CHARACTER: return getText('Enum_ItemType_CHARACTER');
                case model.ITEM_TYPE.DATE: return getText('Enum_ItemType_DATE');
                case model.ITEM_TYPE.INS_TIME: return getText('Enum_ItemType_INS_TIME');
                case model.ITEM_TYPE.TIME: return getText('Enum_ItemType_TIME');
            }
            return "";
        }
    }
}