module nts.uk.com.view.cps016.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listItems: KnockoutObservableArray<ISelectionItem1> = ko.observableArray([]);
        perInfoSelectionItem: KnockoutObservable<SelectionItem1> = ko.observable(new SelectionItem1({ selectionItemId: '', selectionItemName: '' }));
        rulesFirst: KnockoutObservableArray<IRule>;
        checkCreate: KnockoutObservable<boolean>;
        closeUp: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean> = ko.observable(false);
        isMaCoLog: KnockoutObservable<boolean> = ko.observable(false);
        param: any = getShared('CPS005B_PARAMS');

        constructor() {
            let self = this;
            let perInfoSelectionItem: SelectionItem1 = self.perInfoSelectionItem();
            self.checkCreate = ko.observable(true);
            self.closeUp = ko.observable(false);
            if (self.param) {
                self.isDialog(self.param.isDialog);

            };
            self.rulesFirst = ko.observableArray([
                { id: 0, name: getText('Enum_SelectionCodeCharacter_NUMBER_TYPE') },
                { id: 1, name: getText('Enum_SelectionCodeCharacter_CHARATERS_TYPE') }
            ]);



            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    nts.uk.ui.errors.clearAll();
                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem1) => {
                        if (_perInfoSelectionItem) {
                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);

                            perInfoSelectionItem.characterType(_perInfoSelectionItem.characterType);
                            perInfoSelectionItem.codeLength(_perInfoSelectionItem.codeLength);
                            perInfoSelectionItem.nameLength(_perInfoSelectionItem.nameLength);
                            perInfoSelectionItem.extraCodeLength(_perInfoSelectionItem.extraCodeLength);

                            perInfoSelectionItem.shareChecked(_perInfoSelectionItem.shareChecked);

                            perInfoSelectionItem.memo(_perInfoSelectionItem.memo);
                            perInfoSelectionItem.integrationCode(_perInfoSelectionItem.integrationCode);
                        }
                        $("#selectionItemName").focus();
                    });
                }
                self.checkCreate(false);
            });
        }

        //開始
        start(): JQueryPromise<any> {
            let self = this;
            let groupCompanyAdmin = __viewContext.user.role.groupCompanyAdmin;
            if (groupCompanyAdmin === 'null') {
                alertError({ messageId: "Msg_1103" }).then(() => {
                    uk.request.jumpToTopPage();
                })

            } else {
                let dfd = $.Deferred();

                nts.uk.ui.errors.clearAll();

                // get selection items
                self.getAllSelectionItems().done(() => {
                    if (self.param && !nts.uk.util.isNullOrUndefined(self.param.selectionItemId)) {
                        self.perInfoSelectionItem().selectionItemId(self.param.selectionItemId);
                    } else {
                        self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                    }
                    self.listItems.valueHasMutated();
                    $("#selectionItemName").focus();
                    dfd.resolve();
                });
                return dfd.promise();
            }
        }

        getAllSelectionItems(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem1>) => {
                if (itemList && itemList.length > 0) {
                    itemList.forEach(x => self.listItems.push(x));
                } else {//0件の場合: エラーメッセージの表示(#Msg_455)
                    alertError({ messageId: "Msg_455" });
                    self.registerDataSelectioItem();
                    //$("#selectionItemName").focus();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        //新規ボタン
        registerDataSelectioItem() {
            let self = this;
            let perInfoSelectionItem: SelectionItem1 = self.perInfoSelectionItem();
            nts.uk.ui.errors.clearAll();

            perInfoSelectionItem.selectionItemId('');
            perInfoSelectionItem.selectionItemName('');

            perInfoSelectionItem.characterType(false);
            perInfoSelectionItem.codeLength('');
            perInfoSelectionItem.nameLength('');
            perInfoSelectionItem.extraCodeLength('');

            perInfoSelectionItem.shareChecked(false);

            perInfoSelectionItem.integrationCode('');
            perInfoSelectionItem.memo('');

            self.checkCreate(true);
            $("#selectionItemName").focus();
        }

        //検証チェック
        validate() {
            $(".nts-editor").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        }

        //登録ボタン
        addDataSelectioItem() {
            var self = this;

            if (self.validate()) {
                if (self.checkCreate() == true) {
                    self.add();
                } else {
                    self.update();
                }
            }
        }

        //新規モード
        add() {
            let self = this;
            let command = ko.toJS(self.perInfoSelectionItem());

            //「個人情報の選択項目」を登録する
            service.addDataSelectionItem(command).done(function(selectId) {
                self.listItems.removeAll();
                //画面項目「選択項目名称一覧：選択項目名称一覧」を登録する
                self.getAllSelectionItems().done(() => {
                    $("#selectionItemName").focus();

                    //「CPS017_個人情報の選択肢の登録」をモーダルダイアログで起動する
                    confirm({ messageId: "Msg_456" }).ifYes(() => {
                        let params = {
                            isDialog: true,
                            selectionItemId: ko.toJS(self.perInfoSelectionItem().selectionItemId)
                        }
                        setShared('CPS017_PARAMS', params);

                        modal('/view/cps/017/a/index.xhtml', { title: '', height: 750, width: 1260 }).onClosed(function(): any {
                        });

                        self.listItems.valueHasMutated();
                        $("#selectionItemName").focus();
                    }).ifNo(() => {
                        self.listItems.valueHasMutated();
                        $("#selectionItemName").focus();
                        return;
                    })
                    self.listItems.valueHasMutated();
                    $("#selectionItemName").focus();
                });
                self.listItems.valueHasMutated();
                self.perInfoSelectionItem().selectionItemId(selectId);
                $("#selectionItemName").focus();

            }).fail(error => {
                alertError({ messageId: "Msg_513" });
            });
        }

        //更新モード
        update() {
            let self = this;
            let command = ko.toJS(self.perInfoSelectionItem());
            //「個人情報の選択項目」を更新する
            service.updateDataSelectionItem(command).done(function() {

                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    self.listItems.removeAll();
                    //画面項目「選択項目名称一覧：選択項目名称一覧」を更新する
                    self.getAllSelectionItems().done(() => {
                        $("#selectionItemName").focus();
                    });
                });
                self.listItems.valueHasMutated();

            }).fail(error => {
                alertError({ messageId: "Msg_513" });
            });
        }

        //削除ボタン
        removeDataSelectioItem() {
            let self = this;
            let currentItem: SelectionItem1 = self.perInfoSelectionItem();
            let listItems: Array<SelectionItem1> = self.listItems();

            let oldIndex = _.findIndex(listItems, x => x.selectionItemId == currentItem.selectionItemId());
            let lastIndex = listItems.length - 1;

            let command = ko.toJS(currentItem);

            confirm({ messageId: "Msg_551" }).ifYes(() => {
                service.removeDataSelectionItem(command).done(function() {
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                        self.listItems.removeAll();
                        self.getAllSelectionItems().done(() => {
                            if (self.listItems().length > 0) {
                                if (oldIndex == lastIndex) {
                                    oldIndex--;
                                }
                                let newItem = self.listItems()[oldIndex];
                                currentItem.selectionItemId(newItem.selectionItemId);
                            }
                            //                        self.listItems.valueHasMutated();
                        });
                    });
                    self.listItems.valueHasMutated();

                }).fail(error => {
                    alertError({ messageId: "Msg_521" });
                });

            });
        }

        // 選択肢の登録ボタン
        OpenCPS017() {
            let self = this,
                params = {
                    isDialog: true,
                    selectionItemId: ko.toJS(self.perInfoSelectionItem().selectionItemId)

                }
            setShared('CPS017_PARAMS', params);

            modal('/view/cps/017/a/index.xhtml', { title: '', height: 750, width: 1260 }).onClosed(function(): any {
            });
            $("#selectionItemName").focus();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        masterCorrLog() {
            //chua phat trien:    
        }

    }

    interface ISelectionItem1 {

        selectionItemId: string;
        selectionItemName: string;

        characterType: number;
        codeLength: number;
        nameLength: number;
        extraCodeLength: number;

        shareChecked: boolean;

        integrationCode?: string;
        memo?: string;

    }

    class SelectionItem1 {
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');

        characterType: KnockoutObservable<boolean> = ko.observable(false);
        codeLength: KnockoutObservable<number> = ko.observable('');
        nameLength: KnockoutObservable<number> = ko.observable('');
        extraCodeLength: KnockoutObservable<number> = ko.observable('');

        shareChecked: KnockoutObservable<boolean> = ko.observable(false);

        integrationCode: KnockoutObservable<string> = ko.observable('');
        memo: KnockoutObservable<string> = ko.observable('');

        constructor(param: ISelectionItem1) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');

            self.characterType(param.characterType === 1 ? true : false);
            self.codeLength(param.codeLength || '');
            self.nameLength(param.nameLength || '');
            self.extraCodeLength(param.extraCodeLength || '');

            self.shareChecked(param.shareChecked);

            self.integrationCode(param.integrationCode || '');
            self.memo(param.memo || '');
        }
    }

    interface IRule {
        id: number;
        name: string;
    }
}