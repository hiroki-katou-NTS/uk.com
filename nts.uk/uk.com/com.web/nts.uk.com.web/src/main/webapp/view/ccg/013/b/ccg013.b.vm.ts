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
        textOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;

        constructor() {
            var self = this;
            self.nameMenuBar = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([]);
            self.selectedCodeSystemSelect = ko.observable(0);
            //Radio button
            self.itemRadioAtcClass = ko.observableArray([]);
            self.selectedRadioAtcClass = ko.observable(0);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            self.allPart = ko.observableArray([]);
            self.listStandardMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key: 'code', width: '60px' },
                { headerText: '名称', prop: 'displayName', key: 'displayName', width: '200px' },
                { headerText: '', prop: 'uniqueCode', key: 'uniqueCode', width: '0px', display: 'none' }
            ]);
            self.selectCodeStandardMenu = ko.observable('');
            self.currentListStandardMenu = ko.observable('');
            //Follow SystemSelect
            self.selectedSystemID = ko.observable(null);
            self.selectedCodeSystemSelect.subscribe((value) => { self.changeSystem(value); });
            self.selectedRadioAtcClass.subscribe(function(value) {
                if (value == 0) {
                    self.currentListStandardMenu('');
                }
            });
            self.textOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                width: "160px"
            }));
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
                self.allPart(editMenuBar.listStandardMenu);
                let listStandardMenu: Array<MenuBarDto> = _.orderBy(editMenuBar.listStandardMenu, "code", "asc");
                _.forEach(editMenuBar.listStandardMenu, (item) => {
                    self.listStandardMenu.push(new MenuBarDto(
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
                self.selectedRadioAtcClass(editMenuBar.listSelectedAtr[0].value);
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
            var menuCls = null;
            var code = null;
            if (nts.uk.ui.errors.hasError() || (self.letterColor() === "" || self.backgroundColor() === "")) {
                return;
            }
            var standMenu = _.find(self.listStandardMenu(), function(item: MenuBarDto) {
                return item.uniqueCode == self.currentListStandardMenu();
            });
            if (standMenu) {
                menuCls = standMenu.classification;
                code = standMenu.code;
            }
            var menuBar = new MenuBar({
                code: code,
                nameMenuBar: self.nameMenuBar(),
                letterColor: self.letterColor(),
                backgroundColor: self.backgroundColor(),
                selectedRadioAtcClass: self.selectedRadioAtcClass(),
                system: self.selectedCodeSystemSelect(),
                menuCls: menuCls,
            });
            windows.setShared("CCG013B_MenuBar", menuBar);
            self.cancel_Dialog();
        }

        /** Change System */
        private changeSystem(value): void {
            var self = this;
            var standardMenus = _.chain(self.allPart()).filter(['system', value]).value();
            self.listStandardMenu(standardMenus);
        }
    }

    interface IMenuBar {
        code: string;
        nameMenuBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedRadioAtcClass: number;
        system: number;
        menuCls: number;
    }

    class MenuBar {
        code: string;
        nameMenuBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedRadioAtcClass: number;
        system: number;
        menuCls: number;
        uniqueCode: string;

        constructor(param: IMenuBar) {
            this.code = param.code;
            this.nameMenuBar = param.nameMenuBar;
            this.letterColor = param.letterColor;
            this.backgroundColor = param.backgroundColor;
            this.selectedRadioAtcClass = param.selectedRadioAtcClass;
            this.system = param.system;
            this.menuCls = param.menuCls;
            this.uniqueCode = this.code + this.system + this.menuCls;
        }
    }

    class MenuBarDto {
        afterLoginDisplay: number;
        classification: number;
        code: string;
        companyId: string;
        displayName: string;
        displayOrder: number;
        logSettingDisplay: number;
        menuAtr: number;
        system: number;
        targetItems: string;
        url: string;
        webMenuSetting: number;
        uniqueCode: string;

        constructor(afterLoginDisplay: number, classification: number, code: string, companyId: string, displayName: string, displayOrder: number, logSettingDisplay: number, menuAtr: number, system: number, targetItems: string, url: string, webMenuSetting: number) {
            this.afterLoginDisplay = afterLoginDisplay;
            this.classification = classification;
            this.code = code;
            this.companyId = companyId;
            this.displayName = displayName;
            this.displayOrder = displayOrder;
            this.logSettingDisplay = logSettingDisplay;
            this.menuAtr = menuAtr;
            this.system = system;
            this.targetItems = targetItems;
            this.url = url;
            this.webMenuSetting = webMenuSetting;
            this.uniqueCode = nts.uk.text.format("{0}{1}{2}", code, system, classification);;
        }
    }
}