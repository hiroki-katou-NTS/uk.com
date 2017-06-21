module nts.uk.sys.view.ccg013.b.viewmodel {

    export class ScreenModel {
        selectCode: KnockoutObservable<any>;
        //Combobox
        listSystemSelect: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //Radio button
        itemRadio: KnockoutObservableArray<any>;
        selectedIdRadio: KnockoutObservable<number>;
        //GridList
        listStandardMenu: KnockoutObservableArray<StandardMenu>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentListStandardMenu: KnockoutObservableArray<any>;
        
        
        items: KnockoutObservableArray<StandardMenu>;
        currentCode: KnockoutObservable<any>;
        selectCode: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            this.selectCode = ko.observable([]);
            self.simpleValue = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([
                /**  人事郎  :JINJIROU (0) */
                new SystemSelect('0', '人事郎'),
                /** 勤次郎  :TIME_SHEET(1) */
                new SystemSelect('1', '勤次郎'),
                /** オフィスヘルパー :OFFICE_HELPER(2) */
                new SystemSelect('2', 'オフィスヘルパー'),
                /** Ｑ太郎 :KYUYOU(3) */
                new SystemSelect('3', 'Ｑ太郎'),
                /** 共通 :COMMON(4) */
                new SystemSelect('4', '共通')
            ]);
            self.selectedCode = ko.observable('')
            //Radio button
            self.itemRadio = ko.observableArray([
                new RadioButton(0, 'ツリーメニューから起動'),
                new RadioButton(1, '直接起動')
            ]);
            self.selectedId = ko.observable(1);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            this.listStandardMenu = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key:'code', width: 100 },
                { headerText: '名称', prop: 'displayName', key:'displayName', width: 230 }
            ]);
            this.currentCode = ko.observable();
            this.currentListStandardMenu = ko.observableArray([]);
            
            //test
            this.currentCode = ko.observable();
            this.selectCode = ko.observableArray([]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get List StandrdMenu*/
            service.getAllStandardMenu().done(function(listStandardMenu: Array<viewmodel.StandardMenu>) {
                listStandardMenu = _.orderBy(listStandardMenu, ["code"], ["asc"]);
                self.listStandardMenu(listStandardMenu);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
        }
           cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;
            var selectStandardMenu = _.find(self.listStandardMenu(), ['code', self.selectCode()]);
            if (selectWorkLocation !== undefined) {
                nts.uk.ui.windows.setShared("KDL010workLocation", selectWorkLocation.workLocationCD);
            }
             else {
                nts.uk.ui.windows.setShared("KDL010workLocation", null, true);
                }
            self.cancel_Dialog();
        }

    }
    class SystemSelect {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
    class RadioButton {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }

    }
     export class StandardMenu {
        code: string;
        displayName: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.displayName = displayName;
        }
         
    }
}