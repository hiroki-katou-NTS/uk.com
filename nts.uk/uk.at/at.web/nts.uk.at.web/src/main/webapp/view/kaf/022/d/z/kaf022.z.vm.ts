module nts.uk.at.view.kaf022.z.viewmodel {
    import text = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModelZ {
        settings: KnockoutObservableArray<SettingData>;
        colAtrs: Array<any> = [
            { width: 150 },
            { width: 250 },
            { width: 426 }
        ];
        tableId: string = "fixed-table-cmp";

        constructor() {
            const self = this;
            const labels = ["KAF022_3", "KAF022_4", "KAF022_5", "KAF022_6", "KAF022_7", "KAF022_8", "KAF022_11", "KAF022_707", "KAF022_10", "KAF022_12", "KAF022_705"];
            self.settings = ko.observableArray([]);
            const listAppType = __viewContext.enums.ApplicationType;
            for (let i = 0; i < listAppType.length; i++) {
                self.settings.push(new SettingData(text(labels[i]), listAppType[i].value));
            }
        }

        start(): JQueryPromise<any> {
            const self = this, dfd = $.Deferred();
            block.grayout();
            service.getCom().done((data: Array<any>) => {
                if (data != null && data.length > 0) {
                    data.forEach(setting => {
                        const index = _.findIndex(self.settings(), s => s.appType == setting.appType);
                        if (index >= 0) {
                            self.settings()[index].useAtr(setting.useDivision);
                            self.settings()[index].memo(setting.memo);
                        }
                    });
                }
                dfd.resolve();
            }).fail(error => {
                dfd.reject();
                block.clear();
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        register(): void {
            const self = this;
            block.invisible();
            service.saveCom(ko.toJS(self.settings)).done(() => {
                dialog.info({messageId: "Msg_15"});
            }).fail(error => {
                dialog.alertError(error);
            }).always(() => {
                block.clear();
            });
        }

    }

    export class SettingData {
        label: string;
        appType: number;
        // 利用区分
        useAtr: KnockoutObservable<number>;
        // 備考
        memo: KnockoutObservable<string>;

        constructor(label: string, appType: number, radioValue?: number, textAreaValue?: string) {
            this.label = label;
            this.appType = appType;
            this.useAtr = ko.observable(radioValue == null ? 1 : radioValue);
            this.memo = ko.observable(textAreaValue || "");
        }
    }

}