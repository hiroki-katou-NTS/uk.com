module nts.uk.at.view.kmf022.v.viewmodel {
    import getText = nts.uk.resource.getText;

    export class ScreenModelV {
        menuList: KnockoutObservableArray<MenuModel>;

        constructor() {
            const self = this;
            self.menuList = ko.observableArray([]);
            $("#fixed-table-v2").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            const menus: Array<StandardMenuNameExport> = allData.menus;
            if (menus) {
                const appSetForProxyApp: Array<any> = allData.applicationSetting.appSetForProxyApp;
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
                                    return o.appType == 7 && ((o.stampRequestMode == 0 && m.screenId == "A") || (o.stampRequestMode == 1 && m.screenId == "C"));
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
                        case "KAF005Aoverworkatr=0":
                            return {appType: 0, overtimeAtr: 0, stampMode: null};
                        case "KAF005Aoverworkatr=1":
                            return {appType: 0, overtimeAtr: 1, stampMode: null};
                        case "KAF005Aoverworkatr=2":
                            return {appType: 0, overtimeAtr: 2, stampMode: null};
                        case "KAF006A":
                            return {appType: 1, overtimeAtr: null, stampMode: null};
                        case "KAF007A":
                            return {appType: 2, overtimeAtr: null, stampMode: null};
                        case "KAF008A":
                            return {appType: 3, overtimeAtr: null, stampMode: null};
                        case "KAF009A":
                            return {appType: 4, overtimeAtr: null, stampMode: null};
                        case "KAF010A":
                            return {appType: 6, overtimeAtr: null, stampMode: null};
                        case "KAF012A":
                            return {appType: 8, overtimeAtr: null, stampMode: null};
                        case "KAF004A":
                            return {appType: 9, overtimeAtr: null, stampMode: null};
                        case "KAF002A":
                            return {appType: 7, overtimeAtr: null, stampMode: 0};
                        case "KAF002C":
                            return {appType: 7, overtimeAtr: null, stampMode: 1};
                        case "KAF011A":
                            return {appType: 10, overtimeAtr: null, stampMode: null};
                        case "KAF020A":
                            return {appType: 15, overtimeAtr: null, stampMode: null};
                        default:
                            return {};
                    }
                });
        }

    }

    class MenuModel {
        id: string;
        checked: KnockoutObservable<boolean>;
        name: string;

        constructor(id: string, checked: boolean, name: string) {
            this.id = id;
            this.checked = ko.observable(checked);
            this.name = name;
        }
    }

    interface StandardMenuNameExport {
        programId: string;
        screenId: string;
        queryString: string;
        displayName: string;
    }

}