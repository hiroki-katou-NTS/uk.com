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
        allPart: KnockoutObservableArray<any>;
        listStandardMenu: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectedStandardMenuKey: KnockoutObservable<string>;
        textOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;

        constructor() {
            var self = this;
            self.nameMenuBar = ko.observable("");
            //Combo box
            self.listSystemSelect = ko.observableArray([]);
            self.selectedCodeSystemSelect = ko.observable(null);
            //Radio button
            self.itemRadioAtcClass = ko.observableArray([]);
            self.selectedRadioAtcClass = ko.observable(0);
            //color picker
            self.letterColor = ko.observable('#FFFFFF');
            self.backgroundColor = ko.observable('#92D050');
            //GridList
            self.allPart = ko.observableArray([]);
            self.listStandardMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_26"), prop: 'code', key: 'code', width: '60px', hidden: true },
                { headerText: nts.uk.resource.getText("CCG013_26"), prop: 'index', key: 'index', width: '60px' },
                { headerText: nts.uk.resource.getText("CCG013_27"), prop: 'displayName', key: 'displayName', width: '200px' },
                { headerText: '', prop: 'uniqueCode', key: 'uniqueCode', width: '0px', display: 'none' }
            ]);
            self.selectedStandardMenuKey = ko.observable('');
            //Follow SystemSelect
            self.selectedCodeSystemSelect.subscribe((value) => { self.changeSystem(value); });
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
                const item1: any[] = [];
                item1.push(new EnumConstant(5, nts.uk.resource.getText("CCG013_137"), nts.uk.resource.getText("CCG013_137")));
                _.forEach(editMenuBar.listSystem, x => {
                    item1.push(x);
                })
                self.listSystemSelect(item1);
                _.forEach(editMenuBar.listStandardMenu, (item, index) => {
                    self.allPart.push(new MenuBarDto(
                        index,
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
                self.selectedCodeSystemSelect(5);
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
            $(".ntsColorPicker_Container").trigger("validate");
            var menuCls = null;
            var code = null;
            var name = null;

            validateNameInput($(".menu-bar-name"), '#[CCG013_18]', self.nameMenuBar().trim(), 'MenuBarName');

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            var standMenu = _.find(self.listStandardMenu(), function(item: MenuBarDto) {
                return item.uniqueCode == self.selectedStandardMenuKey();
            });
            if (standMenu) {
                menuCls = standMenu.classification;
                code = standMenu.code;
                name = self.nameMenuBar();
            }

            if (self.selectedRadioAtcClass() == 1) {
                if (self.selectedStandardMenuKey() !== '') {
                    var menuBar = new MenuBar({
                        code: code,
                        nameMenuBar: name,
                        letterColor: self.letterColor(),
                        backgroundColor: self.backgroundColor(),
                        selectedRadioAtcClass: self.selectedRadioAtcClass(),
                        system: self.selectedCodeSystemSelect(),
                        menuCls: menuCls,
                    });
                    windows.setShared("CCG013B_MenuBar", menuBar);
                    self.cancel_Dialog();
                } else {
                    var textMsg218 = nts.uk.resource.getMessage("Msg_218",[nts.uk.resource.getText("CCG013_105")]);
                    nts.uk.ui.dialog.alertError(textMsg218);
                    return;
                }
            } else {
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
        }

        /** Select by Index: Start & Delete case */
        private selectStandardMenuByIndex(index: number) {
            var self = this;
            var selectStdMenuByIndex = _.nth(self.listStandardMenu(), index);
            if (selectStdMenuByIndex !== undefined)
                self.selectedStandardMenuKey(selectStdMenuByIndex.uniqueCode);
            else
                self.selectedStandardMenuKey(null);
        }

        private changeSystem(value: number): void {
            var self = this;
            if (value === 5) {
                var list001 = _.chain(self.allPart())
                    .uniqBy("displayName")
                    .forEach((item, index) => {
                        item.index = index + 1;
                    })
                    .value();
                self.listStandardMenu(list001);
            } else {
                var standardMenus: any = _.chain(self.allPart())
                    .filter((item: any) => {
                        if (item.system == 0 && item.classification == 8) return true;
                        if (item.system == value) return true;
                    }).sortBy(['classification', 'code'])
                    .forEach((item, index) => {
                        item.index = index + 1;
                    })
                    .value();
                self.listStandardMenu(standardMenus);
            }
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
        index: number;
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

        constructor(index: number, afterLoginDisplay: number, classification: number, code: string, companyId: string, displayName: string, displayOrder: number, logSettingDisplay: number, menuAtr: number, system: number, targetItems: string, url: string, webMenuSetting: number) {
            this.index = index;
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

    export class EnumConstant {
        value: number;
        fieldName: string;
        localizedName: string;
        constructor(value: number, fieldName: string, localizedName: string) {
            this.value = value;
            this.fieldName = fieldName;
            this.localizedName = localizedName;
        }
    }
}