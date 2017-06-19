module nts.uk.sys.view.ccg013.b.viewmodel {

    export class ScreenModel {
        simpleValue: KnockoutObservable<string>;
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
        currentCodeList: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.simpleValue = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([
                new SystemSelect('基本給1', '基本給'),
                new SystemSelect('基本給2', '役職手当'),
                new SystemSelect('0003', '基本給')
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
            this.currentCodeList = ko.observableArray([]);
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
    }
    class SystemSelect {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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