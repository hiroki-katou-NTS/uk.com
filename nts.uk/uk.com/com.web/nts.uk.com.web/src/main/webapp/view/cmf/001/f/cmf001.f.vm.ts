module nts.uk.com.view.cmf001.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        codeConvertList: KnockoutObservableArray<AcceptanceCodeConvert> = ko.observableArray([]);
        selectedConvertCode: KnockoutObservable<string> = ko.observable('');
        selectedConvertDetail: KnockoutObservable<number> = ko.observable(0); 
        screenMode: KnockoutObservable<number>;
        codeConvertData: KnockoutObservable<AcceptanceCodeConvert> = ko.observable(new AcceptanceCodeConvert('', '', 0, []));
        acceptWithoutSettingItems: KnockoutObservableArray<model.ItemModel>;
        
        constructor() {
            let self = this;
            self.screenMode = ko.observable(model.SCREEN_MODE.UPDATE);
            $("#detail-code-convert").ntsFixedTable({ height: 184 });
            
            self.acceptWithoutSettingItems =  ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_209')),
                new model.ItemModel(model.NOT_USE_ATR.USE    , getText('CMF001_210')), 
            ]);

            //登録済のコード変換を参照する
            //コード一覧でいづれか選択する
            self.selectedConvertCode.subscribe(function(convertCode: any) {
                if (convertCode) {
                    block.invisible();
                    //ドメインモデル「受入コード変換」を取得し内容を画面（右側）にセットする
                    service.getAcceptCodeConvert(convertCode).done(function(codeConvert) {
                        if (codeConvert) {
                            self.codeConvertData().cdConvertDetails.removeAll();
                            self.selectedConvertCode(codeConvert.convertCd);
                            self.codeConvertData().convertCd(codeConvert.convertCd);
                            self.codeConvertData().convertName(codeConvert.convertName);
                            self.codeConvertData().acceptWithoutSetting(codeConvert.acceptWithoutSetting);
                            var detail: Array<any> = _.sortBy(codeConvert.cdConvertDetails, ['lineNumber']);
                            for (let i = 0; i < detail.length; i++) {
                                self.codeConvertData().cdConvertDetails.push(new CodeConvertDetail(detail[i].convertCd, detail[i].lineNumber, detail[i].outputItem, detail[i].systemCd));
                            }

                            //UI設計の「項目制御」のl更新モードに画面設定する
                            self.screenMode(model.SCREEN_MODE.UPDATE);

                            //フォーカス制御
                            self.setFocusItem(FOCUS_TYPE.ROW_PRESS, model.SCREEN_MODE.UPDATE);
                        }
                    }).fail(function(error) {
                        dialog.alertError(error);
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    self.settingCreateMode();
                    setTimeout(() => {
                        nts.uk.ui.errors.clearAll();
                    }, 10);
                }
            });

            self.codeConvertData().cdConvertDetails.subscribe(() => {
                for (var i = 0; i < self.codeConvertData().cdConvertDetails().length; i++) {
                    self.codeConvertData().cdConvertDetails()[i].lineNumber(i + 1);
                }
            });
        }

        /**
         * 起動する
         * - アルゴリズム「一覧登録画面を表示する」を実行する
         */
        initialScreen(convertCodeParam?: string) {
            let self = this;
            block.invisible();
            nts.uk.ui.errors.clearAll();
            //ドメインモデル「受入コード変換」を取得する
            service.getCodeConvertByCompanyId().done(function(result: Array<any>) {
                //データが存在するか判別する
                //データが存在する場合
                if (result && result.length) {
                    let _codeConvertResult: Array<any> = _.sortBy(result, ['convertCd']);
                    let _codeConvertList: Array<AcceptanceCodeConvert> = _.map(_codeConvertResult, x => {
                        return new AcceptanceCodeConvert(x.convertCd, x.convertName, x.acceptWithoutSetting, x.cdConvertDetails);
                    });

                    //F:「コード変換一括登録」ダイアログを更新モードでモーダル表示する
                    self.screenMode(model.SCREEN_MODE.UPDATE);

                    let _codeConvert: string;
                    if (convertCodeParam) {
                        _codeConvert = convertCodeParam;
                    } else {
                        _codeConvert = _codeConvertList[0].convertCd();
                    }
                    self.selectedConvertCode(_codeConvert);
                    //取得した受入コード変換を「コード変換一覧」（グリッドリスト）に表示する
                    self.codeConvertList(_codeConvertList);

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
                dialog.alertError(error);
            }).always(function() {
                block.clear();
            });
        }

        //新規登録を始める
        //「新規」ボタンを押下
        addCodeConvert_click() {
            let self = this;
            block.invisible();
            self.settingCreateMode();
            block.clear();
        }

        //フォーカス制御
        setFocusItem(focus: number, screenMode: number, index?: number) {
            let self = this;
            if (focus == FOCUS_TYPE.ADD_ROW_PRESS || focus == FOCUS_TYPE.DEL_ROW_PRESS) {
                $('tr[data-id=' + index + ']').find("input").first().focus();
            } else {
                if (screenMode == model.SCREEN_MODE.NEW) {
                    $('#F4_2').focus();
                } else if (screenMode == model.SCREEN_MODE.UPDATE) {
                    $('#F4_3').focus();
                }
            }
            _.defer(() => {nts.uk.ui.errors.clearAll()});
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            
            self.initialScreen();
            
            dfd.resolve();
            return dfd.promise();
        }

        //一覧の行追加
        //行追加ボタンを押下
        addDetailConvert_click() {
            let self = this;
            block.invisible();

            if (self.codeConvertData().cdConvertDetails == null || self.codeConvertData().cdConvertDetails == undefined) {
                self.codeConvertData().cdConvertDetails = ko.observableArray([]);
            }

            //コード変換一覧の最下行に1行追加する
            self.codeConvertData().cdConvertDetails.push(new CodeConvertDetail(self.codeConvertData().convertCd(), self.codeConvertData().cdConvertDetails().length + 1, '', ''));
            self.selectedConvertDetail(self.codeConvertData().cdConvertDetails().length);
            $("#detail-code-convert tr")[self.codeConvertData().cdConvertDetails().length - 1].scrollIntoView();

            let indexFocus:number = self.codeConvertData().cdConvertDetails().length;
            //フォーカス制御
            self.setFocusItem(FOCUS_TYPE.ADD_ROW_PRESS, model.SCREEN_MODE.UPDATE, indexFocus);
            
            block.clear();
        }

        //一覧の行削除
        //行削除ボタンを押下
        deleteDetailConvert_click() {
            let self = this;
            let indexFocus: number = 0;
            block.invisible();

            //選択行がない場合
            if (self.selectedConvertDetail() < 1 || self.selectedConvertDetail() > self.codeConvertData().cdConvertDetails().length) {
                //エラーメッセージの表示　Msg_897　削除行が選択されていません。
                dialog.alertError({ messageId: "Msg_897" });
                block.clear();
                return;
            }

            //現在の行を「コード変換コード表」グリッドリストから1削除する（削除後に記入する）
            self.codeConvertData().cdConvertDetails.remove(function(item) { return item.lineNumber() == (self.selectedConvertDetail()); })
            nts.uk.ui.errors.clearAll();
            for (var i = 0; i < self.codeConvertData().cdConvertDetails().length; i++) {
                self.codeConvertData().cdConvertDetails()[i].lineNumber(i + 1);
            }
            if (self.selectedConvertDetail() >= self.codeConvertData().cdConvertDetails().length) {
                self.selectedConvertDetail(self.codeConvertData().cdConvertDetails().length);
                indexFocus = self.codeConvertData().cdConvertDetails().length;
            } else {
                indexFocus = self.selectedConvertDetail();
            }

            //フォーカス制御
            self.setFocusItem(FOCUS_TYPE.DEL_ROW_PRESS, model.SCREEN_MODE.UPDATE, indexFocus);
            self.selectedConvertDetail.valueHasMutated();
            
            block.clear();
        }
        
        //登録ボタンを押下
        regAcceptCodeConvert_Click() {
            
            let self = this;
            nts.uk.ui.errors.clearAll();
            block.invisible();
            for (var i = 0; i < self.codeConvertData().cdConvertDetails().length; i++) {
                self.codeConvertData().cdConvertDetails()[i].convertCd(self.codeConvertData().convertCd());
            }

            let currentAcceptCodeConvert = self.codeConvertData;

            //新規モードか更新モードを判別する
            if (model.SCREEN_MODE.NEW == self.screenMode()) {
                //コード変換コードが既に登録されていないか判別
                var existCode = self.codeConvertList().filter(x => x.convertCd() === currentAcceptCodeConvert().convertCd());
                //同一コード有の場合
                if (existCode.length > 0) {
                    //エラーメッセージ　Msg_1094　を表示する 
                    dialog.alertError({ messageId: "Msg_1094" });
                    block.clear();
                    return;
                }
            }

            //コード一覧に変換データの有無を判別
            if (_.isEmpty(currentAcceptCodeConvert().cdConvertDetails())) {
                //エラーメッセージ　Msg_906　変換コードが設定されていません
                dialog.alertError({ messageId: "Msg_906" });
                block.clear();
                return;
            }

            let _lineError: Array<any> = [];
            let _codeDuplicate: Array<any> = [];
            let _emptyData: Boolean = true;
            for (let detail of currentAcceptCodeConvert().cdConvertDetails()) {
                if (_.isEmpty(detail.outputItem()) && _.isEmpty(detail.systemCd())) {
                    continue;
                }
                 _emptyData = false;
                if (_.isEmpty(detail.outputItem()) || _.isEmpty(detail.systemCd())) {
                    _lineError.push(detail.lineNumber());
                }
                let data = currentAcceptCodeConvert().cdConvertDetails().filter(x => x.outputItem() === detail.outputItem());
                if (data.length >= 2) {
                    _codeDuplicate.push(detail);
                }
            }
            
            if(_emptyData){
                dialog.alertError({ messageId: "Msg_906" });
                block.clear();
                return;
            }

            if (!_.isEmpty(_lineError)) {
                //コードの未入力チェックを全行行いエラーの場合エラーリストにセットする　　　　Msg_1016
                $('tr[data-id=' + _lineError[0] + ']').find("input").first().ntsError('set', { messageId: 'Msg_1016', messageParams: [_lineError.join(',')] });
            }

            if (!_.isEmpty(_codeDuplicate)) {
                // Remove duplicate outputItem
                let _errorCodeDuplicate: Array<any> = _.uniqBy(ko.toJS(_codeDuplicate), 'outputItem');
                //受入コードに同一の値がないか全行のチェックを行いエラーをエラーリストにセットする　　Msg_1015
                for (let i = 0; i < _errorCodeDuplicate.length; i++) {
                    $('tr[data-id=' + _errorCodeDuplicate[i].lineNumber + ']').find("input").first().ntsError('set', { messageId: 'Msg_1015', messageParams: [_errorCodeDuplicate[i].outputItem] });
                }
            }
            $('.nts-input').trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                self.codeConvertData().cdConvertDetails(_.filter(self.codeConvertData().cdConvertDetails(), x => !_.isEmpty(x.outputItem()) && !_.isEmpty(x.systemCd())));

                //画面の項目を、ドメインモデル「受入コード変換」に登録/更新する
                if (model.SCREEN_MODE.NEW == self.screenMode()) {
                    service.addAcceptCodeConvert(ko.toJS(self.codeConvertData())).done((acceptConvertCode) => {

                        //情報メッセージ　Msg_15 登録しました。を表示する。
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            //登録した受入コード変換を「コード変換一覧パネル」へ追加/更新する
                            self.initialScreen(self.codeConvertData().convertCd());
                        });
                    }).fail(function(error) {
                        dialog.alertError(error);
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    service.updateAcceptCodeConvert(ko.toJS(self.codeConvertData())).done((acceptConvertCode) => {
                        //情報メッセージ　Msg_15 登録しました。を表示する。
                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            //登録した受入コード変換を「コード変換一覧パネル」へ追加/更新する
                            self.initialScreen(self.selectedConvertCode());
                        });
                    }).fail(function(error) {
                        dialog.alertError(error);
                    }).always(function() {
                        block.clear();
                    });
                }
            } else {
                block.clear();
            }
        }
        
        //削除ボタンを押下
        delAcceptCodeConvert_Click(){
            let self = this
            let listAcceptCodeConvert = self.codeConvertList;
            let currentCodeConvert = self.codeConvertData;
            block.invisible();

            //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteAcceptCodeConvert(ko.toJS(currentCodeConvert)).done(function() {
                    //select next code convert
                    let index: number = _.findIndex(listAcceptCodeConvert(), function(x)
                    { return x.convertCd() == currentCodeConvert().convertCd() });

                    if (index > -1) {
                        self.codeConvertList.splice(index, 1);
                        if (index >= listAcceptCodeConvert().length) {
                            index = listAcceptCodeConvert().length - 1;
                        }
                    }

                    //情報メッセージ　Msg-16を表示する
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        if (listAcceptCodeConvert().length > 0) {
                            self.initialScreen(listAcceptCodeConvert()[index].convertCd());
                            self.screenMode(model.SCREEN_MODE.UPDATE);
                        } else {
                            self.settingCreateMode();
                        }
                    });
                }).fail(function(error) {
                    dialog.alertError(error);
                }).always(function() {
                    block.clear();
                });
            }).then(() => {
                block.clear();
            });
        }
        
        settingCreateMode() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            
            //コード変換一覧のカレントを解除する
            self.selectedConvertCode('');

            //画面右側のコード/名称/上記の設定にないコードをクリア
            self.codeConvertData().convertCd('');
            self.codeConvertData().convertName('');
            self.codeConvertData().acceptWithoutSetting(0);

            //コード変換コード表をクリア
            self.codeConvertData().cdConvertDetails.removeAll();
            self.selectedConvertDetail(0);

            //UI設計の「項目制御」新規モードで表示する
            self.screenMode(model.SCREEN_MODE.NEW);

            //フォーカス制御
            self.setFocusItem(FOCUS_TYPE.ADD_PRESS, model.SCREEN_MODE.NEW);
        }

        //終了する
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

    export class AcceptanceCodeConvert {

        //コード変換コード
        convertCd: KnockoutObservable<string>;
        dispConvertCode: string;

        //コード変換名称
        convertName: KnockoutObservable<string>;
        dispConvertName: string;

        //設定のないコードの受入
        acceptWithoutSetting: KnockoutObservable<number>;

        //コード変換詳細
        cdConvertDetails: KnockoutObservableArray<CodeConvertDetail>;

        constructor(code: string, name: string, acceptWithoutSettings: number, cdConvertDetails:Array<any>) {
            this.convertCd            = ko.observable(code);
            this.convertName          = ko.observable(name);
            this.cdConvertDetails     = ko.observableArray(cdConvertDetails);
            this.acceptWithoutSetting = ko.observable(acceptWithoutSettings);
            this.dispConvertCode      = code;
            this.dispConvertName      = name;
        } 
    }

    export class CodeConvertDetail {
        //コード変換コード
        convertCd: KnockoutObservable<string>;

        //行番号
        lineNumber: KnockoutObservable<number>;

        //出力項目
        outputItem: KnockoutObservable<string>;

        //本システムのコード
        systemCd: KnockoutObservable<string>;

        constructor(code: string, lineNumber: number, outputItem: string, systemCd: string) {
            this.convertCd  = ko.observable(code);
            this.lineNumber = ko.observable(lineNumber);
            this.outputItem = ko.observable(outputItem);
            this.systemCd   = ko.observable(systemCd);
        }
    }
}//end module

$(function() {
    $("#detail-code-convert").on("click focus", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui.errors.clearAll();
        nts.uk.ui._viewModel.content.selectedConvertDetail(id);
    })
})
