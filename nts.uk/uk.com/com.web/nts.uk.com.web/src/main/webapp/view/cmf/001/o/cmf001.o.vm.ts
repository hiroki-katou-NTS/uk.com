module nts.uk.com.view.cmf001.o.viewmodel {
    import model = cmf001.share.model;
    import getText = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import vc = nts.layout.validation;
    import dialog = nts.uk.ui.dialog.info;
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
        selectedSysType: KnockoutObservable<number> = ko.observable(0);

        listCondition: KnockoutObservableArray<model.StandardAcceptanceConditionSetting> = ko.observableArray([]);
        selectedConditionCd: KnockoutObservable<string> = ko.observable('');
        selectedConditionName: KnockoutObservable<string> = ko.observable('');
        selectedConditionLineNumber: KnockoutObservable<number> = ko.observable(0);
        selectedConditionStartLine: KnockoutObservable<number> = ko.observable(0);

        //upload file
        stereoType: KnockoutObservable<string> = ko.observable('');
        fileId: KnockoutObservable<string> = ko.observable('');
        filename: KnockoutObservable<string> = ko.observable('');
        fileInfo: KnockoutObservable<any> = ko.observable(null);
        textId: KnockoutObservable<string> = ko.observable("CMF001_447");
        accept: KnockoutObservableArray<string> = ko.observableArray(['.csv']);
        asLink: KnockoutObservable<boolean> = ko.observable(false);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        immediate: KnockoutObservable<boolean> = ko.observable(true);
        onchange: (filename) => void;
        onfilenameclick: (filename) => void;

        listAccept: KnockoutObservableArray<AcceptItems> = ko.observableArray([]);
        selectedAccept: KnockoutObservable<any> = ko.observable('');
        totalRecord: KnockoutObservable<number> = ko.observable(0);
        totalLine: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            self.loadSystemType();
            //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
            self.selectedConditionCd.subscribe(function(data: any) {
                //取込情報を選択する
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: model.StandardAcceptanceConditionSetting) => x.dispConditionSettingCode == data);
                    //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
                    self.selectedConditionName(item.dispConditionSettingName);
                    self.selectedConditionLineNumber(item.csvDataItemLineNumber);
                    self.selectedConditionStartLine(item.csvDataStartLine);
                }
                else {
                    self.selectedConditionName('');
                    self.selectedConditionLineNumber(0);
                    self.selectedConditionStartLine(0);
                }
            });
            //self.loadListAccept();   
            $("#grd_Accept").ntsFixedTable({ height: 373 });
        }

        //次の画面へ遷移する
        next() {
            let self = this;

            //条件コードは選択されているか判別
            if (self.selectedConditionCd() == '') {
                //Msg_963　を表示する。受入条件が選択されていません。
                dialog({ messageId: "Msg_963" });
                return;
            }
            //受入ファイルがアップロードされているか判別
            if (self.fileId() == '') {
                //Msg_964　を表示する。受入ファイルがアップロードされていません。
                dialog({ messageId: "Msg_964" });
                return;
            }
            //P:外部受入サマリー画面へ遷移する
            $('#ex_accept_wizard').ntsWizard("next");
            self.loadListAccept();
        }
        previous() {
            //受入設定選択に戻る
            $('#ex_accept_wizard').ntsWizard("prev");
        }

        private uploadFile(): void {
            var self = this;
            block.grayout();
            $("#file-upload").ntsFileUpload({ stereoType: "csvfile" }).done(function(res) {
                service.getNumberOfLine(res[0].id).done(function(totalLine: any) {
                    self.totalLine(totalLine);
                    //アップロードCSVが取込開始行に満たない場合                   
                    if (totalLine < self.selectedConditionStartLine()) {
                        self.fileId('');
                        self.filename('');
                        dialog({ messageId: "Msg_1059" });
                    }
                    //アップロードCSVが取込開始行以上ある
                    else {
                        //基盤からファイルIDを取得する
                        self.fileId(res[0].id);
                    }
                }).fail(function(err) {
                    dialog({ messageId: "Msg_1059" });
                }).always(() => {
                    block.clear();
                });
            }).fail(function(err) {
                self.fileId('');
                //エラーメッセージ　Msg_910　　ファイルアップロードに失敗しました。
                dialog({ messageId: "Msg_910" });
            }).always(() => {
                block.clear();
            });
        }

        loadSystemType() {
            let self = this;

            //Imported(共通)　「システムコード」を取得する

            //「システムコード」の取得結果からシステム種類に変換する

            self.listSysType = ko.observableArray([
                new model.ItemModel(model.SYSTEM_TYPE.PERSON_SYS, '人事システム'),
                new model.ItemModel(model.SYSTEM_TYPE.ATTENDANCE_SYS, '勤怠システム'),
                new model.ItemModel(model.SYSTEM_TYPE.PAYROLL_SYS, '給与システム'),
                new model.ItemModel(model.SYSTEM_TYPE.OFFICE_HELPER, 'オフィスヘルパー')
            ]);
            //1件以上取得できた場合
            if (self.listSysType().length > 0) {
                //システム種類を画面セットする
                self.selectedSysType(self.listSysType()[0].code);

                //ドメインモデル「受入条件設定（定型）」を取得する
                self.loadListCondition(self.selectedSysType());
            }
            //システム種類が取得できない場合
            else {
                //トップページに戻る
                nts.uk.request.jump("/view/cmf/001/a/index.xhtml");
            }
            //システム種類を変更する
            self.selectedSysType.subscribe(function(data: any) {
                //画面上の条件コード/名称をクリアする
                self.selectedConditionCd('');
                self.selectedConditionName('');
                //ドメインモデル「受入条件設定（定型）」を取得する
                self.loadListCondition(data);
            });
        }

        loadListCondition(sysType) {
            let self = this;
            block.grayout();
            //「条件設定一覧」を初期化して取得した設定を表示する
            $('.clear-btn.ntsSearchBox_Component').click();
            self.listCondition([]);
            //ドメインモデル「受入条件設定（定型）」を取得する
            service.getConditionList(sysType).done(function(data: Array<any>) {
                self.listCondition.removeAll();
                //表示するデータがある場合   
                if (data && data.length) {
                    let _rspList: Array<model.StandardAcceptanceConditionSetting> = _.map(data, rsp => {
                        return new model.StandardAcceptanceConditionSetting(rsp.conditionSetCd, rsp.conditionSetName, rsp.deleteExistData, rsp.acceptMode, rsp.csvDataLineNumber, rsp.csvDataStartLine, rsp.deleteExtDataMethod);
                    });
                    self.listCondition(_rspList);

                    //取得した設定を「条件設定一覧」に表示する
                    self.selectedConditionCd(self.listCondition()[0].conditionSettingCode());
                    self.selectedConditionName(self.listCondition()[0].conditionSettingName());
                }
                //取得データが0件の場合      
                else {
                    //エラーメッセージ表示　Msg_907　外部受入設定が作成されていません。
                    dialog({ messageId: "Msg_907" });
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        loadListAccept() {
            let self = this;
            block.grayout();
            //ドメインモデル「受入項目（定型）」を取得する
            service.getStdAcceptItem(self.selectedSysType(), self.selectedConditionCd()).done(function(data: Array<any>) {
                self.listAccept.removeAll();
                if (data && data.length) {
                    let _rspList: Array<AcceptItems> = _.map(data, rsp => {
                        return new AcceptItems('', rsp.csvItemName, rsp.csvItemNumber, '', rsp.itemType);
                    });
                    self.listAccept(_rspList);
                }
                //アップロードしたファイルを読み込む
                self.readUploadFile(self.listAccept().length);
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        readUploadFile(numOfCol: number) {
            let self = this;
            //アップロードしたファイルの「取込開始行」のＣＳＶ項Ｎｏの値
            service.getRecord(self.fileId(), numOfCol, self.selectedConditionStartLine()).done(function(data: Array<any>) {
                for (let i = 0; i < numOfCol; i++) {
                    let temp = data[self.listAccept()[i].csvItemNumber()];
                    self.listAccept()[i].sampleData(temp);
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });

            //ファイルの行数を取得する
            let count = self.totalLine() - self.selectedConditionStartLine();
            if (count < 0) {
                count = 0;
            }
            self.totalRecord(count);
        }

        editIngestion(item: any) {
            var self = this;
            switch (item.itemType()) {
                case 0:
                    //数値型の場合                    
                    //G:「数値型設定」ダイアログをモーダルで表示する
                    let settingG = new model.NumericDataFormatSetting(0, 0, 0, 0, 0, 0, 0, null, 0, "");
                    setShared("CMF001gParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(settingG) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/g/index.xhtml");
                    break;
                case 1:
                    //文字型の場合
                    //H:「文字型設定」ダイアログをモーダルで表示する
                    let settingH = new model.CharacterDataFormatSetting(0, 0, 0, 0, 0, 0, null, 0, "");
                    setShared("CMF001hParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(settingH) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/h/index.xhtml");
                    break;
                case 2:
                    //日付型の場合  
                    //I:「日付型設定」ダイアログをモーダルで表示する
                    let settingI = new model.DateDataFormatSetting(2, model.NOT_USE_ATR.USE, '1223');
                    setShared("CMF001iParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(settingI) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/i/index.xhtml");
                    break;
                case 3:
                    //時間型の場合, 時刻型の場合 
                    //J:「時刻型・時間型設定」ダイアログをモーダルで表示する
                    let settingJ = new model.InstantTimeDataFormatSetting(0, null, null, 0, 0, 0, 0, 0, 0, '111');
                    setShared("CMF001jParams", { inputMode: false, lineNumber: null, formatSetting: ko.toJS(settingJ) });
                    nts.uk.ui.windows.sub.modal("/view/cmf/001/j/index.xhtml");
                    break;
            }
        }

        receiveCondition(item) {
            //L:「受入条件設定ダイアログをモーダルで表示する
            let settingL = new model.AcceptScreenConditionSetting("", 0, 0, 0, 0, 0, "", "", "", "", 0, 0);
            setShared("CMF001lParams", { inputMode: false, dataType: 0, formatSetting: ko.toJS(settingL) });
            nts.uk.ui.windows.sub.modal("/view/cmf/001/l/index.xhtml");
        }

        exeAccept() {
            let self = this;
            //Q:「外部受入処理中ダイアログ」をチェック中で起動する 
            setShared("CMF001qParams", {
                mode: 0,
                systemType: self.selectedSysType(),
                conditionCd: self.selectedConditionCd(),
                fileName: self.filename(),
                fileId: self.fileId(),
                totalRecord: self.totalRecord()
            });
            nts.uk.ui.windows.sub.modal("/view/cmf/001/q/index.xhtml");
        }
    }

    class AcceptItems {
        infoName: KnockoutObservable<string>;
        csvItemName: KnockoutObservable<string>;
        csvItemNumber: KnockoutObservable<number>;
        sampleData: KnockoutObservable<string>;
        itemType: KnockoutObservable<number>;
        itemTypeName: KnockoutObservable<string>;
        constructor(infoName: string, csvItemName: string, csvItemNumber: number, sampleData: string, itemType: number) {
            this.infoName = ko.observable(infoName);
            this.csvItemName = ko.observable(csvItemName);
            this.csvItemNumber = ko.observable(csvItemNumber);
            this.sampleData = ko.observable(sampleData);
            this.itemType = ko.observable(itemType);
            this.itemTypeName = ko.observable(this.getItemTypeName(itemType));
        }

        getItemTypeName(typeCd: number) {
            switch (typeCd) {
                case 0: return "数値型";
                case 1: return "文字型";
                case 2: return "日付型";
                case 3: return "時刻型";
                case 4: return "時間型";
            }
            return "";
        }
    }
}