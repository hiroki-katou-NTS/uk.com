module kml002.i.viewmodel {
    export class ScreenModel {
        settingItems: KnockoutObservableArray<SettingItemModel>;
        useCls: KnockoutObservableArray<any>;
        useClsSelected: any;
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        timeOfDay: KnockoutObservable<number>;
        time: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.settingItems = ko.observableArray([]);
            self.useCls = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("KML002_99") },
                { code: '1', name: nts.uk.resource.getText("KML002_100") }
            ]);
            self.useClsSelected = ko.observable(0);
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            self.time = ko.observable(1200);
        }
        start() {
            var self = this,
                dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        openKDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kml/002/k/index.xhtml').onClosed(function(): any {
            });
        }

    }
    export class SettingItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}