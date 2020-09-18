module nts.uk.at.view.kaf022.m.viewmodel {
    import SettingData = z.viewmodel.SettingData;
    import text = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;

    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModelM {
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        selectedWorkplaceId: KnockoutObservable<String> = ko.observable("");
        alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);

        kcp004WorkplaceListOption: any = {
            maxRows: 11,
            treeType: 1,
            tabindex: 1,
            systemType: 2,
            selectType: 4,
            isDialog: false,
            isMultipleUse: false,
            isMultiSelect: false,
            isShowAlreadySet: true,
            isShowSelectButton: true
        };
        settings: KnockoutObservableArray<SettingData>;
        workplaceName: KnockoutObservable<string>;
        workplaceCode: KnockoutObservable<string>;
        colAtrs: Array<any> = [
            { width: 130 },
            { width: 140 },
            { width: jQuery(window).width() - 940 }
        ];
        tableId: string = "fixed-table-wkp";
        alreadySettingData: Array<any>;
        isUpdate: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            const self = this;
            self.workplaceName = ko.observable("");
            self.workplaceCode = ko.observable("");
            const labels = ["KAF022_3", "KAF022_4", "KAF022_5", "KAF022_6", "KAF022_7", "KAF022_8", "KAF022_11", "KAF022_707", "KAF022_10", "KAF022_12", "KAF022_705"];
            const listAppType = __viewContext.enums.ApplicationType;
            self.settings = ko.observableArray([]);
            for (let i = 0; i < listAppType.length; i++) {
                self.settings.push(new SettingData(text(labels[i]), listAppType[i].value));
            }
            _.extend(self.kcp004WorkplaceListOption, {
                baseDate: self.baseDate,
                alreadySettingList: self.alreadySettingList,
                selectedId: self.selectedWorkplaceId
            });

            self.selectedWorkplaceId.subscribe(value => {
                if (nts.uk.text.isNullOrEmpty(value)) {
                    self.workplaceName("");
                    self.workplaceCode("");
                    self.settings().forEach(s => {
                        s.useAtr(1);
                        s.memo("");
                    });
                    self.isUpdate(false);
                } else {
                    const wkpList: Array<any> = $('#wkp-list').getDataList();
                    const tmpList = [];
                    self.treeToList(tmpList, wkpList);
                    let selectedWkp = _.find(tmpList, {'id': value});
                    if (selectedWkp) {
                        self.workplaceName(selectedWkp.name || "");
                        self.workplaceCode(selectedWkp.code || "");
                        const settingData = _.find(self.alreadySettingData, {'workplaceId': value});
                        if (settingData) {
                            self.settings().forEach(s => {
                                const setting: any = _.find(settingData.settings, {'appType': s.appType});
                                if (setting) {
                                    s.useAtr(setting.useDivision);
                                    s.memo(setting.memo);
                                } else {
                                    s.useAtr(1);
                                    s.memo("");
                                }
                            });
                            self.isUpdate(true)
                        } else {
                            self.settings().forEach(s => {
                                s.useAtr(1);
                                s.memo("");
                            });
                            self.isUpdate(false);
                        }
                    } else {
                        self.workplaceName("");
                        self.workplaceCode("");
                        self.settings().forEach(s => {
                            s.useAtr(1);
                            s.memo("");
                        });
                        self.isUpdate(false);
                    }
                }
            });
        }

        treeToList(lst: Array<any>, tree: Array<any>) {
            const self = this;
            tree.forEach(w => {
                lst.push(w);
                if (w.children != null && w.children.length > 0)
                    self.treeToList(lst, w.children);
            });
        }

        start(): JQueryPromise<any> {
            const self = this, dfd = $.Deferred();
            block.grayout();
            $('#wkp-list').ntsTreeComponent(self.kcp004WorkplaceListOption).done(() => {
                self.reloadData().done(() => {
                    const wkpList: Array<any> = $('#wkp-list').getDataList();
                    if (wkpList.length > 0) {
                        self.selectedWorkplaceId(wkpList[0].id);
                    } else {
                        self.selectedWorkplaceId.valueHasMutated();
                    }
                }).fail((error) => {
                    dialog.alertError(error);
                }).always(() => {
                    block.clear();
                });
                // $('#wkp-list').focusTreeGridComponent();
            }).fail(error => {
                block.clear();
                dialog.alertError(error);
            });
            dfd.resolve();
            return dfd.promise();
        }

        reloadData(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            service.getAll().done(data => {
                self.alreadySettingData = data || [];
                self.alreadySettingList(self.alreadySettingData.map((data) => ({
                    workplaceId: data.workplaceId,
                    isAlreadySetting: true
                })));

                dfd.resolve();
            }).fail((res) => {
                dfd.reject();
            });
            return dfd.promise();
        }

        registerSetting() {
            let self = this;
            $("input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                service.registerSetting({
                    workplaceId: self.selectedWorkplaceId(),
                    settings: ko.toJS(self.settings)
                }).done(() => {
                    dialog.info({messageId: "Msg_15"}).then(() => {
                        block.invisible();
                        //Load data setting
                        self.reloadData().done(() => {
                            self.selectedWorkplaceId.valueHasMutated();
                        }).fail(error => {
                            dialog.alertError(error);
                        }).always(() => {
                            block.clear();
                        });
                    });
                }).fail(error => {
                    dialog.alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        deleteSetting() {
            let self = this;
            dialog.confirm({messageId: 'Msg_18'}).ifYes(function () {
                block.invisible();
                let command = {
                    workplaceId: self.selectedWorkplaceId()
                };
                service.deleteSetting(command).done(() => {
                    dialog.info({messageId: 'Msg_16'});
                    //Remove already setting data
                    self.alreadySettingList.remove(function (item) {
                        return item.workplaceId === self.selectedWorkplaceId();
                    });
                    self.alreadySettingList.valueHasMutated();

                    _.remove(self.alreadySettingData, function (currentData: any) {
                        return currentData.workplaceId === self.selectedWorkplaceId();
                    });

                    self.selectedWorkplaceId.valueHasMutated();
                }).fail(error => {
                    dialog.alertError(error);
                }).always(() => {
                    block.clear();
                });
            }).ifNo(function () {

            });
        }

        copySetting() {
            let self = this;

            const listSetting = _.map(self.alreadySettingList(), function (item: any) {
                return item.workplaceId;
            });
            //CDL023：複写ダイアログを起動する
            let param = {
                code: self.workplaceCode(),
                name: self.workplaceName(),
                targetType: 4,
                itemListSetting: listSetting,
                baseDate: ko.toJS(self.baseDate)
            };
            nts.uk.ui.windows.setShared("CDL023Input", param);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/023/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("CDL023Output");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    block.invisible();
                    let command = {
                        targetWorkplaceIds: data,
                        workplaceId: self.selectedWorkplaceId()
                    };
                    service.copySetting(command).done(() => {
                        //情報メッセージ（Msg_15）を表示する
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            block.invisible();
                            self.reloadData().done(() => {
                                self.selectedWorkplaceId.valueHasMutated();
                            }).fail(error => {
                                dialog.alertError(error);
                            }).always(() => {
                                block.clear();
                            });
                        });
                    }).fail(error => {
                        dialog.alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
        }
    }

}