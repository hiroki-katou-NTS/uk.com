module nts.uk.com.view.cmf001.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import model = cmf001.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    export class ScreenModel {
        codeConvertList: KnockoutObservableArray<model.AcceptanceCodeConvert> = ko.observableArray([]);
        codeConvertDetailList: KnockoutObservableArray<model.CodeConvertDetail> = ko.observableArray([]);
        selectedConvertCode: KnockoutObservable<string> = ko.observable('');
        selectedConvertDetail: KnockoutObservable<number> = ko.observable(0);
        convertCode: KnockoutObservable<string> = ko.observable('');
        convertName: KnockoutObservable<string> = ko.observable('');
        selectedSettingKbn: any = ko.observable(0);
        screenMode: KnockoutObservable<number>;

        constructor() {
            let self = this;
            self.screenMode = ko.observable(model.SCREEN_MODE.UPDATE);
            $("#detail-code-convert").ntsFixedTable({ height: 200 });

            //起動する
            //アルゴリズム「一覧登録画面を表示する」を実行する
            self.displayListScreen();

            //登録済のコード変換を参照する
            //コード一覧でいづれか選択する
            self.selectedConvertCode.subscribe(function(convertCode: any) {
                nts.uk.ui.errors.clearAll();
                if (convertCode) {
                    //ドメインモデル「受入コード変換」を取得し内容を画面（右側）にセットする
                    self.displayAcceptCodeConvert(convertCode);

                    //ドメインモデル「コード変換詳細」を取得して、内容をコード変換コード表（右側）にセットする
                    self.displayCodeConvertDetails(convertCode);

                    //UI設計の「項目制御」のl更新モードに画面設定する
                    self.screenMode(model.SCREEN_MODE.UPDATE);

                    //フォーカス制御
                    self.setFocusItem(FOCUS_TYPE.ROW_PRESS, model.SCREEN_MODE.UPDATE);
                }
            });

            self.codeConvertDetailList.subscribe(() => {
                for (var i = 0; i < self.codeConvertDetailList().length; i++) {
                    self.codeConvertDetailList()[i].lineNumber(i + 1);
                }
            });
        }

        displayListScreen() {
            let self = this;

            //ドメインモデル「受入コード変換」を取得する
            service.getCodeConvert().done(function(result: Array<any>) {
                //データが存在するか判別する
                //データが存在する場合
                if (result && result.length) {
                    let _codeConvertList: Array<model.AcceptanceCodeConvert> = _.map(result, x => {
                        return new model.AcceptanceCodeConvert(x.convertCd, x.convertName, x.acceptWithoutSetting);
                    });

                    //取得した受入コード変換を「コード変換一覧」（グリッドリスト）に表示する
                    self.codeConvertList(_codeConvertList);

                    //Fist line of data
                    var codeConvert: model.AcceptanceCodeConvert = self.codeConvertList()[0];

                    //F:「コード変換一括登録」ダイアログを更新モードでモーダル表示する
                    self.screenMode(model.SCREEN_MODE.UPDATE);

                    ////「コード変換一覧」の1行目をカレント行とし、内容を右側に表示
                    self.displayCodeConvertInfo(codeConvert);

                    //ドメインモデル「受入変換コード値」を取得する
                    self.displayCodeConvertDetails(codeConvert.convertCode());

                    //フォーカス制御
                    self.setFocusItem(FOCUS_TYPE.INIT, model.SCREEN_MODE.UPDATE);
                }
                //データが存在しない場合
                else {
                    //F:「コード変換一括登録」ガイアログを新規モードでモーダル表示する
                    self.screenMode(model.SCREEN_MODE.NEW);

                    //フォーカス制御
                    self.setFocusItem(FOCUS_TYPE.INIT, model.SCREEN_MODE.NEW);
                }
            }).fail(function(error) {
                alertError(error);
            });
        }

        displayCodeConvertInfo(codeConvert: model.AcceptanceCodeConvert) {
            let self = this;

            //「コード変換一覧」グリッドの1行目にカレント行をセットし、「コード変換コード」「コード変換名称」「設定のないコードの受入」を画面右側にセットする
            self.selectedConvertCode(codeConvert.convertCode());
            self.convertCode(codeConvert.convertCode());
            self.convertName(codeConvert.convertName());
            self.selectedSettingKbn(codeConvert.acceptCodeWithoutSettings());
        }

        displayAcceptCodeConvert(convertCode: string) {
            let self = this;
            //ドメインモデル「受入コード変換」を取得し内容を画面（右側）にセットする
            service.getAcceptCodeConvert(convertCode).done(function(result: any) {
                if (result) {
                    self.convertCode(result.convertCd);
                    self.convertName(result.convertName);
                    self.selectedSettingKbn(result.acceptWithoutSetting);
                }
            }).fail(function(error) {
                alertError(error);
            });
        }

        displayCodeConvertDetails(convertCode: string) {
            let self = this;
            self.codeConvertDetailList.removeAll();
            self.selectedConvertDetail(0);

            //ドメインモデル「受入変換コード値」を取得する
            service.getCodeConvertDetails(convertCode).done(function(result: Array<any>) {
                let _details: Array<model.CodeConvertDetail> = _.map(result, x => {
                    return new model.CodeConvertDetail(x.lineNumber, x.outputItem, x.systemCd);
                });
                self.codeConvertDetailList(_details);
            }).fail(function(error) {
                alertError(error);
            });
        }

        //新規登録を始める
        //「新規」ボタンを押下
        addCodeConvert_click() {
            let self = this;
            nts.uk.ui.errors.clearAll();

            //コード変換一覧のカレントを解除する
            self.selectedConvertCode('');

            //画面右側のコード/名称/上記の設定にないコードをクリア
            self.convertCode('');
            self.convertName('');

            //コード変換コード表をクリア
            self.codeConvertDetailList.removeAll();
            self.selectedConvertDetail(0);

            //UI設計の「項目制御」新規モードで表示する
            self.screenMode(model.SCREEN_MODE.NEW);

            //フォーカス制御
            self.setFocusItem(FOCUS_TYPE.ADD_PRESS, model.SCREEN_MODE.NEW);
        }

        //フォーカス制御
        setFocusItem(focus: number, screenMode?: number) {
            let self = this;
            switch (focus) {
                case FOCUS_TYPE.INIT:
                    self.focusItemByMode(screenMode);
                    break;
                case FOCUS_TYPE.ADD_PRESS:
                    self.focusItemByMode(screenMode);
                    break;
                case FOCUS_TYPE.REG_PRESS:
                    self.focusItemByMode(screenMode);
                    break;
                case FOCUS_TYPE.DEL_PRESS:
                    self.focusItemByMode(screenMode);
                    break;
                case FOCUS_TYPE.ROW_PRESS:
                    self.focusItemByMode(screenMode);
                    break;
            }
        }
        focusItemByMode(screenMode: number) {
            if (screenMode == model.SCREEN_MODE.NEW) {
                $('#F4_2').focus();
            } else if (screenMode == model.SCREEN_MODE.UPDATE) {
                $('#F4_3').focus();
            }
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        //一覧の行追加
        //行追加ボタンを押下
        addDetailConvert_click() {
            let self = this;

            if (self.codeConvertDetailList == null || self.codeConvertDetailList == undefined) {
                self.codeConvertDetailList = ko.observableArray([]);
            }

            //コード変換一覧の最下行に1行追加する
            self.codeConvertDetailList.push(new model.CodeConvertDetail(self.codeConvertDetailList().length + 1, '', ''));
            self.selectedConvertDetail(self.codeConvertDetailList().length);
            $("#detail-code-convert tr")[self.codeConvertDetailList().length - 1].scrollIntoView();

            //フォーカス制御
            $('tr[data-id=' + self.codeConvertDetailList().length + ']').find("input").first().focus();
        }

        //一覧の行削除
        //行削除ボタンを押下
        deleteDetailConvert_click() {
            var self = this;

            //選択行がない場合
            if (self.selectedConvertDetail() < 1 || self.selectedConvertDetail() > self.codeConvertDetailList().length) {
                //エラーメッセージの表示　Msg_897　削除行が選択されていません。
                dialog({ messageId: "Msg_897" });
                return;
            }

            //現在の行を「コード変換コード表」グリッドリストから1削除する（削除後に記入する）
            self.codeConvertDetailList.remove(function(item) { return item.lineNumber() == (self.selectedConvertDetail()); })
            nts.uk.ui.errors.clearAll();
            for (var i = 0; i < self.codeConvertDetailList().length; i++) {
                self.codeConvertDetailList()[i].lineNumber(i + 1);
            }
            if (self.selectedConvertDetail() >= self.codeConvertDetailList().length) {
                self.selectedConvertDetail(self.codeConvertDetailList().length);
                //フォーカス制御
                $('tr[data-id=' + self.codeConvertDetailList().length + ']').find("input").first().focus();
            } else {
                //フォーカス制御
                $('tr[data-id=' + self.selectedConvertDetail() + ']').find("input").first().focus();
            }
            self.selectedConvertDetail.valueHasMutated();

        }

        closeDialog() {
            close();
        }
    }//end screenModel

    export enum FOCUS_TYPE {
        INIT = 0,
        ADD_PRESS = 1,
        REG_PRESS = 2,
        DEL_PRESS = 3,
        ROW_PRESS = 4,
        ADD_ROW_PRESS = 5,
        DEL_ROW_PRESS = 6
    }
}//end module

$(function() {
    $("#detail-code-convert").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.selectedConvertDetail(id);
    })
})
