module ccg018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        selectedItem: KnockoutObservable<ItemModel>;
        currentCode: KnockoutObservable<any>;
        selectedItemAfterLogin: KnockoutObservable<string>;
        selectedItemAsTopPage: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<any>;

        comboItemsAfterLogin: KnockoutObservableArray<ItemModel1>;
        comboItemsAsTopPage: KnockoutObservableArray<ItemModel1>;

        listSid: Array<any>;

        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.selectedItem = ko.observable(null);
            //fix Employee list received
            self.listSid = [];
            for (let i = 1; i < 10; i++) {
                self.listSid.push('90000000-0000-0000-0000-00000000000' + i);
                self.listSid.push('90000000-0000-0000-0000-00000000001' + i);
            }

            self.comboItemsAfterLogin = ko.observableArray([]);
            self.comboItemsAsTopPage = ko.observableArray([]);
            self.currentCode = ko.observable();
            self.employeeCode = ko.observable('');
            self.employeeName = ko.observable('');
            self.selectedItemAfterLogin = ko.observable('');
            self.selectedItemAsTopPage = ko.observable('');
            self.categorySet = ko.observable();
            self.isVisible = ko.computed(function() {
                return !!self.categorySet();
            });
            self.currentCode.subscribe(function(codeChange: any) {
                if (!!self.currentCode()) {
                    self.employeeCode(codeChange);
                    self.selectedItem(_.find(self.items(), ['code', codeChange]));
                    self.employeeName(self.selectedItem().name);
                    self.selectedItemAfterLogin(self.selectedItem().loginMenuCode());
                    self.selectedItemAsTopPage(self.selectedItem().topPageCode());
                } else {
                    self.employeeCode('');
                    self.employeeName('');
                    self.selectedItemAfterLogin('');
                    self.selectedItemAsTopPage('');
                }
            });

            self.findDataForAfterLoginDis();
            self.findBySystemMenuCls();
            self.findTopPagePersonSet();
        }

        start(): void {
            let self = this;
            self.categorySet(__viewContext.viewModel.viewmodelA.categorySet());
            self.bindGrid();
        }

        bindGrid(): any {
            let self = this;
            let listComponentOption = {
                isShowAlreadySet: true,
                alreadySettingList: self.items,
                isMultiSelect: false,
                listType: 4,
                isShowWorkPlaceName: true,
                selectedCode: self.currentCode,
                isShowNoSelectRow: false,
                isDialog: false,
                selectType: 3,
                isShowSelectAllButton: false,
                employeeInputList: self.items
            };
            $('#sample-component').ntsListComponent(listComponentOption);
        }

        findTopPagePersonSet(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            ccg018.b.service.findTopPagePersonSet(self.listSid)
                .done(function(data) {
                    self.items([]);
                    var arr = [];
                    for (let i = 0; i < self.listSid.length; i++) {
                        let index = self.listSid[i].slice(34);
                        let topPagePersonSet: any = _.find(data, ['sid', self.listSid[i]]);
                        if (!!topPagePersonSet) {
                            arr.push(new ItemModel('A0000' + index, '山本' + index, '名古屋市' + index, self.listSid[i], topPagePersonSet.topMenuCode, topPagePersonSet.loginMenuCode, true));
                        } else {
                            arr.push(new ItemModel('A0000' + index, '山本' + index, '名古屋市' + index, self.listSid[i], '', '', false));
                        }
                    }
                    self.items(arr);
                    self.bindGrid();

                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return 2 array comboItemsAsTopPage and comboItemsAfterLogin
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.comboItemsAsTopPage([]);
            ccg018.b.service.findBySystemMenuCls()
                .done(function(data) {
                    if (data.length >= 0) {
                        self.comboItemsAsTopPage.push(new ItemModel1('', '未設定'));
                        _.forEach(data, function(x) {
                            self.comboItemsAsTopPage.push(new ItemModel1(x.code, x.displayName, x.system, x.classification));
                        });
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * find data in talbel STANDARD_MENU with companyId and 
         * afterLoginDisplay = 1 (display)  or System = 0(common) and MenuClassification = 8(top page)
         */
        findDataForAfterLoginDis(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.comboItemsAfterLogin([]);
            ccg018.b.service.findDataForAfterLoginDis()
                .done(function(data) {
                    self.comboItemsAfterLogin.push(new ItemModel1('', '未設定'));
                    _.forEach(data, function(x) {
                        self.comboItemsAfterLogin.push(new ItemModel1(x.code, x.displayName, x.system, x.classification));
                    });
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Update/Insert data in to table TOPPAGE_PERSON_SET
         */
        saveData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let tmp = _.find(self.comboItemsAfterLogin(), ['code', self.selectedItem().loginMenuCode()]);
            let oldCode = self.selectedItem().code;
            let obj = {
                ctgSet: self.categorySet(),
                sId: self.selectedItem().employeeId,
                topMenuCode: self.selectedItemAsTopPage(),
                loginMenuCode: !!self.categorySet() ? self.selectedItemAfterLogin() : self.selectedItem().loginMenuCode(),
                loginSystem: tmp.system,
                loginMenuCls: tmp.menuCls,
            };
            ccg018.b.service.update(obj).done(function() {
                $.when(self.findTopPagePersonSet()).done(function() {
                    self.currentCode(oldCode);
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                });
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            });
            return dfd.promise();
        }

        /**
         * remove data in to table TOPPAGE_PERSON_SET
         */
        removeData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_18')).ifYes(() => {
                let obj = { sId: self.selectedItem().employeeId };
                ccg018.b.service.remove(obj).done(function() {
                    $.when(self.findTopPagePersonSet()).done(function() {
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_16'));
                    });
                }).fail();
            }).ifNo(() => { });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            let self = this;
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal('/view/ccg/018/c/index.xhtml', { dialogClass: 'no-close' }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('categorySetC') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('categorySetC')) {
                        self.categorySet(nts.uk.ui.windows.getShared('categorySetC'));
                    }
                }
            });
        }


    }

    class ItemModel {
        code: string;
        name: string;
        workplaceName: string;
        employeeId: string;
        topPageCode: KnockoutObservable<string>;
        loginMenuCode: KnockoutObservable<string>;
        isAlreadySetting: boolean;
        constructor(code: string, name: string, workplaceName: string, employeeId: string, topPageCode: string, loginMenuCode: string, isAlreadySetting: boolean) {
            this.code = code;
            this.name = name;
            this.workplaceName = workplaceName;
            this.employeeId = employeeId;
            this.topPageCode = ko.observable(topPageCode);
            this.loginMenuCode = ko.observable(loginMenuCode);
            this.isAlreadySetting = isAlreadySetting;
        }
    }

    class ItemModel1 {
        code: string;
        name: string;
        system: number;
        menuCls: number;

        constructor(code: string, name: string, system?: number, menuCls?: number) {
            this.code = code;
            this.name = name;
            this.system = system;
            this.menuCls = menuCls;
        }
    }
}