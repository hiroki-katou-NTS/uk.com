module nts.uk.at.view.kaf022.v.viewmodel {
    import getText = nts.uk.resource.getText;

    export class ScreenModelV {
        menuList: KnockoutObservableArray<MenuModel>;
        allChecked: KnockoutObservable<boolean>;

        constructor() {
            const self = this;
            self.menuList = ko.observableArray([]);
            $("#fixed-table-v2").ntsFixedTable({});

            self.allChecked = ko.pureComputed({
                read: function () {
                    return self.menuList().length > 0 && self.menuList().filter(m => m.checked()).length == self.menuList().length;
                },
                write: function (value) {
                    if (value) {
                        self.menuList().forEach(m => m.checked(true));
                    } else {
                        self.menuList().forEach(m => m.checked(false));
                    }
                },
                owner: self
            });
        }

        initData(allData: any): void {
            const self = this;
            const menus: Array<StandardMenuNameExport> = allData.menus;
            if (menus) {
                const appSetForProxyApp: Array<any> = allData.applicationSetting ? allData.applicationSetting.appSetForProxyApp : [];
                const data = menus.map(m => {
                    return new MenuModel(
                        m.programId + m.screenId + (m.queryString ? m.queryString.substr(m.queryString.length - 1) : ""),
                        !!_.find(appSetForProxyApp, o => {
                            switch (m.programId) {
                                case "KAF005":
                                    return o.appType == 0
                                        && ((m.queryString == "overworkatr=0" && o.overtimeAppAtr == 0)
                                            || (m.queryString == "overworkatr=1" && o.overtimeAppAtr == 1)
                                            || (m.queryString == "overworkatr=2" && o.overtimeAppAtr == 2));
                                case "KAF006":
                                    return o.appType == 1;
                                case "KAF007":
                                    return o.appType == 2;
                                case "KAF008":
                                    return o.appType == 3;
                                case "KAF009":
                                    return o.appType == 4;
                                case "KAF010":
                                    return o.appType == 6;
                                case "KAF012":
                                    return o.appType == 8;
                                case "KAF004":
                                    return o.appType == 9;
                                case "KAF002":
                                    return o.appType == 7 && ((o.stampRequestMode == 0 && m.screenId == "A") || (o.stampRequestMode == 1 && m.screenId == "B"));
                                case "KAF011":
                                    return o.appType == 10;
                                case "KAF020":
                                    return o.appType == 15;
                                default:
                                    return false;
                            }
                        }),
                        m.displayName
                    );
                });
                self.menuList(data);
            }
        }

        collectData(): Array<any> {
            const self = this;
            const data: Array<any> = ko.toJS(self.menuList);
            return data.filter(o => o.checked)
                .map(o => {
                    switch (o.id) {
                        case "KAF005A0":
                            return {appType: 0, overtimeAppAtr: 0, stampRequestMode: null};
                        case "KAF005A1":
                            return {appType: 0, overtimeAppAtr: 1, stampRequestMode: null};
                        case "KAF005A2":
                            return {appType: 0, overtimeAppAtr: 2, stampRequestMode: null};
                        case "KAF006A":
                            return {appType: 1, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF007A":
                            return {appType: 2, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF008A":
                            return {appType: 3, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF009A":
                            return {appType: 4, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF010A":
                            return {appType: 6, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF012A":
                            return {appType: 8, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF004A":
                            return {appType: 9, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF002A":
                            return {appType: 7, overtimeAppAtr: null, stampRequestMode: 0};
                        case "KAF002B":
                            return {appType: 7, overtimeAppAtr: null, stampRequestMode: 1};
                        case "KAF011A":
                            return {appType: 10, overtimeAppAtr: null, stampRequestMode: null};
                        case "KAF020A":
                            return {appType: 15, overtimeAppAtr: null, stampRequestMode: null};
                        default:
                            return null;
                    }
                });
        }

    }

    class MenuModel {
        id: string;
        checked: KnockoutObservable<boolean>;
        name: string; //表示名称

        constructor(id: string, checked: boolean, name: string) {
            this.id = id;
            this.checked = ko.observable(checked);
            this.name = name;
        }
    }

    interface StandardMenuNameExport {
        // プログラムID
        programId: string;

        // 遷移先の画面ID
        screenId: string;

        // クエリ文字列
        queryString: string;

        //表示名称
        displayName: string;
    }

}