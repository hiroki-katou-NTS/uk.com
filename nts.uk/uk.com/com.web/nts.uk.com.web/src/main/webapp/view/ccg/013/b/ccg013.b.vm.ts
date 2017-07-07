module nts.uk.sys.view.ccg013.b.viewmodel {
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        //Text edittor
        nameMenuBar: KnockoutObservable<string>;
        //Combobox
        listSystemSelect: KnockoutObservableArray<any>;
        selectedCodeSystem: KnockoutObservable<number>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //Radio button
        itemRadioAtcClass: KnockoutObservableArray<any>;
        selectedRadioAtcClass: KnockoutObservable<number>;
        //GridList
        allPart: KnockoutObservableArray<any>;
        listStandardMenu: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectedStandardMenuKey: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.nameMenuBar = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([]);
            self.selectedCodeSystem = ko.observable(null);
            self.selectedCodeSystem.subscribe((value) => {
                
                self.changeSystem(value);
            });
            //Radio button
            var CCG013_31 = nts.uk.resource.getText('CCG013_21');
            self.itemRadioAtcClass = ko.observableArray([]);
            self.selectedRadioAtcClass = ko.observable(0);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            self.allPart = ko.observableArray([]);
            self.listStandardMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝�', prop: 'uniqueKey', width: 1, hidden: true },
                { headerText: '繧ｳ繝ｼ繝�', prop: 'code', key: 'code', width: '60px' },
                { headerText: '蜷咲ｧｰ', prop: 'displayName', key: 'displayName', width: '200px' }
            ]);
            self.selectedStandardMenuKey = ko.observable('');
            //Follow SystemSelect
            self.selectedRadioAtcClass.subscribe(function(value) {
                if (value == 0) {
                    self.selectedStandardMenuKey('');
                }
            });
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
            service.getEditMenuBar().done(function(editMenuBar: service.EditMenuBarDto) {
                self.itemRadioAtcClass(editMenuBar.listSelectedAtr);
                self.listSystemSelect(editMenuBar.listSystem);
                let listStandardMenu: Array<service.MenuBarDto> = []; 
                _.forEach(editMenuBar.listStandardMenu, (item) => {
                    listStandardMenu.push(new service.MenuBarDto(
                        item.afterLoginDisplay, 
                        item.classification,
                        item.code,
                        item.companyId,
                        item.displayName,
                        item.displayOrder,
                        item.logSettingDisplay,
                        item.menuAtr,
                        item.system,
                        item.targetItems,
                        item.url,
                        item.webMenuSetting
                    ));
                });
                self.allPart(listStandardMenu);
                self.selectedRadioAtcClass(editMenuBar.listSelectedAtr[0].value);
                self.selectedCodeSystem(0);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
        }

        cancel_Dialog(): any {
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            var standMenu = _.find(self.listStandardMenu(), function(item: service.MenuBarDto) {
                return item.uniqueKey == self.selectedStandardMenuKey();
            });
            
            var menuBar = new MenuBar(standMenu ? standMenu.code : null,
                                      self.nameMenuBar(),
                                      standMenu ? standMenu.nameStandard : null,
                                      self.letterColor(), 
                                      self.backgroundColor(),
                                      self.selectedRadioAtcClass(),
                                      self.selectedCodeSystem(), 
                                      standMenu ? standMenu.classification : null
                                      );
            windows.setShared("CCG013B_MenuBar", menuBar);
            
            self.cancel_Dialog(); 
        }

        /** Change System */
        private changeSystem(value): void {
            var self = this;
            var standardMenus = _.chain(self.allPart()).filter((item) => {
                if (item.system == 0 && item.classification == 8) return true;
                if (item.system == value) return true;
            }).orderBy(['classification', 'code'], ['asc', 'asc']).value();
            self.listStandardMenu(standardMenus);
        }
    }

    class MenuBar {
        code: string;
        nameMenuBar: string;
        nameStandard:String
        letterColor: string;
        backgroundColor: string;
        selectedRadioAtcClass: number;
        system: number;
        menuCls: number;

        constructor(code: string,,nameMenuBar: string,nameStandard: string, letterColor: string, backgroundColor: string, selectedRadioAtcClass: number, system: number, menuCls: number) {
            this.code = code;
            this.nameMenuBar = nameMenuBar;
            this.nameStandard = nameStandard;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.selectedRadioAtcClass = selectedRadioAtcClass;
            this.system = system;
            this.menuCls = menuCls;
        }
    }
}