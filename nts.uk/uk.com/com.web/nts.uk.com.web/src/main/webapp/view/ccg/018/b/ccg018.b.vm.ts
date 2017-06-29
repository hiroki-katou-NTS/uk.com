module ccg018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        selectedItemAfterLogin: KnockoutObservable<string>;
        selectedItemAsTopPage: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<any>;

        comboItemsAfterLogin: KnockoutObservableArray<ItemModel1>;
        comboItemsAsTopPage: KnockoutObservableArray<ItemModel1>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);

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
                    self.employeeName(_.find(self.items(), ['code', codeChange]).name);
                } else {
                    self.employeeCode('');
                    self.employeeName('');
                }
            });
        }

        start(): void {
            let self = this;
            self.categorySet(__viewContext.viewModel.viewmodelA.categorySet());
            self.findTopPagePersonSet();
            $.when(self.findBySystemMenuCls()).done(function() {
                self.findDataForAfterLoginDis();
            });
            var listComponentOption = {
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
            var self = this;
            var dfd = $.Deferred();
            var listSid = [];
            for (var i = 1; i < 10; i++) {
                listSid.push('90000000-0000-0000-0000-00000000000' + i);
                listSid.push('90000000-0000-0000-0000-00000000001' + i);
            }
            ccg018.b.service.findTopPagePersonSet(listSid)
                .done(function(data) {
                    for (var i = 0; i < listSid.length; i++) {
                        var topPagePersonSet = _.find(data, ['sid', listSid[i]]);
                        if (!!topPagePersonSet) {
                            self.items.push(new ItemModel('A00000' + i, '山本' + i, '名古屋市' + i, listSid[i], topPagePersonSet.loginMenuCode, topPagePersonSet.topMenuCode, true));
                        } else {
                            self.items.push(new ItemModel('A00000' + i, '山本' + i, '名古屋市' + i, listSid[i], '', '', false));
                        }
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return 2 array comboItemsAsTopPage and comboItemsAfterLogin
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.comboItemsAsTopPage.removeAll();
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
            var self = this;
            var dfd = $.Deferred();
            self.comboItemsAfterLogin.removeAll();
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
            var self = this;
            var dfd = $.Deferred();
            var obj = {
                //                ctgSet: self.categorySet(),
                //                sId:
                //                topMenuCode
                //                  loginMenuCode
                //                  loginSystem
                //                  loginMenuCls
            };
            ccg018.b.service.update(obj).done(function() {
                nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_15"));
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
            var self = this;
            var dfd = $.Deferred();
            var obj = {
                //                sId: 
            };
            ccg018.b.service.remove(obj).done(function() {
                nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_16"));
                dfd.resolve();
            }).fail();
            return dfd.promise();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            var self = this;
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
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