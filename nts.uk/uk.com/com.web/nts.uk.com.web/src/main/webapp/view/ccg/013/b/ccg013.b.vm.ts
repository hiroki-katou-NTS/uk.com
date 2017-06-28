module nts.uk.sys.view.ccg013.b.viewmodel {
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        //Text edittor
        nameMenuBar: KnockoutObservable<string>;
        //Combobox
        listSystemSelect: KnockoutObservableArray<any>;
        selectedCodeSystemSelect: KnockoutObservable<number>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //Radio button
        itemRadioAtcClass: KnockoutObservableArray<any>;
        selectedRadioAtcClass: KnockoutObservable<number>;
        //GridList
        listStandardMenu: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        currentListStandardMenu: KnockoutObservable<string>;
        selectCodeStandardMenu: KnockoutObservable<string>;
        allPart: KnockoutObservableArray<any>;
        selectedSystemID: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.nameMenuBar = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([]);
            self.selectedCodeSystemSelect = ko.observable(0);
            //Radio button
            self.itemRadioAtcClass = ko.observableArray([]);
            self.selectedRadioAtcClass = ko.observable(1);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            self.allPart = ko.observableArray([]);
            self.listStandardMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key: 'code', width: '60px' },
                { headerText: '名称', prop: 'displayName', key: 'displayName', width: '200px' }
            ]);
            self.selectCodeStandardMenu = ko.observable('');
            self.currentListStandardMenu = ko.observable('');
            //Follow SystemSelect
            self.selectedSystemID = ko.observable(null);
            self.selectedCodeSystemSelect.subscribe((value) => { self.changeSystem(value); });
                
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var data = windows.getShared("CCG013A_StandardMeNu");
            if (data) {
                self.nameMenuBar(data.nameMenuBar);
                self.letterColor(data.pickerLetter);
                self.backgroundColor(data.pickerBackground);
                self.selectedRadioAtcClass(data.radioActlass);

            }

            /** Get EditMenuBar*/
            service.getEditMenuBar().done(function(editMenuBar: any) {
                self.itemRadioAtcClass(editMenuBar.listSelectedAtr);
                self.listSystemSelect(editMenuBar.listSystem);
                console.log(editMenuBar);
                self.allPart(editMenuBar.listStandardMenu);
                let listStandardMenu: Array<any> = _.orderBy((editMenuBar.listStandardMenu, ["code"], ["asc"]));
                self.listStandardMenu(editMenuBar.listStandardMenu);
                self.selectedRadioAtcClass(editMenuBar.listSelectedAtr[0].value);
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
            var menuCls = "";
            var standMenu = _.find(self.listStandardMenu(), 'code', self.currentListStandardMenu());
            if (standMenu) {
                menuCls = standMenu.classification;
            }            
            var menuBar = new MenuBar(self.currentListStandardMenu(), self.nameMenuBar(), self.letterColor(), self.backgroundColor(), self.selectedRadioAtcClass(), self.selectedCodeSystemSelect(), menuCls);
            windows.setShared("CCG013B_MenuBar", menuBar);
            self.cancel_Dialog();
        }

        /** Change System */
        private changeSystem(value): void {
            var self = this;
            var standardMenus =  _.chain(self.allPart()).filter(['system', value]).value();
            self.listStandardMenu(standardMenus);
        }


    }

    class MenuBar {
        code: string;
        nameMenuBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedRadioAtcClass: number;
        system: number;
        menuCls: number;

        constructor(code: string, nameMenuBar: string, letterColor: string, backgroundColor: string, selectedRadioAtcClass: number, system: number, menuCls: number) {
            this.code = code;
            this.nameMenuBar = nameMenuBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.selectedRadioAtcClass = selectedRadioAtcClass;
            this.system = system;
            this.menuCls = menuCls;
        }
    }
}